package com.web.jxp.assessmentquestion;

import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
import com.web.jxp.common.Common;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import static com.web.jxp.common.Common.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class AssessmentQuestion extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getAssessmentQuestionByName(String search, int next, int count) 
    {
        ArrayList assessmentQuestionList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_assessmentquestion.i_assessmentquestionid, t_assessmentquestion.s_name, t_assessmentanswertype.s_name, t_assessmentparameter.s_name,");
        sb.append("t_assessmentquestion.i_status FROM t_assessmentquestion");
        sb.append(" left join t_assessmentanswertype ON t_assessmentanswertype.i_assessmentanswertypeid = t_assessmentquestion.i_assessmentanswertypeid ");
        sb.append(" left join t_assessmentparameter ON t_assessmentparameter.i_assessmentparameterid = t_assessmentquestion.i_assessmentparameterid ");
        sb.append("where 0 = 0 ");

        if (search != null && !search.equals("")) {
            sb.append("and (t_assessmentquestion.s_name like ? OR t_assessmentanswertype.s_name like ? OR t_assessmentparameter.s_name like ?) ");
        }
        sb.append(" order by  t_assessmentquestion.i_status, t_assessmentquestion.s_name");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("select count(1) FROM t_assessmentquestion ");
        sb.append(" left join t_assessmentanswertype ON t_assessmentanswertype.i_assessmentanswertypeid = t_assessmentquestion.i_assessmentanswertypeid ");
        sb.append(" left join t_assessmentparameter ON t_assessmentparameter.i_assessmentparameterid = t_assessmentquestion.i_assessmentparameterid ");
        sb.append("where 0 = 0 ");
        if (search != null && !search.equals("")) {
           sb.append("and (t_assessmentquestion.s_name like ? OR t_assessmentanswertype.s_name like ? OR t_assessmentparameter.s_name like ?) ");
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.log(Level.INFO, "getAssessmentQuestionByName :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String name, assessmentAnswerTypeName, assessmentParameterName;
            int status, assessmentquestionId;
            while (rs.next()) 
            {
                assessmentquestionId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                assessmentAnswerTypeName = rs.getString(3) != null ? rs.getString(3) : "";
                assessmentParameterName = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                assessmentQuestionList.add(new AssessmentQuestionInfo(assessmentquestionId, name, assessmentAnswerTypeName, assessmentParameterName, status));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) 
            {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            print(this,"getAssessmentQuestionByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                assessmentquestionId = rs.getInt(1);
                assessmentQuestionList.add(new AssessmentQuestionInfo(assessmentquestionId, "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return assessmentQuestionList;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        AssessmentQuestionInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (AssessmentQuestionInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            AssessmentQuestionInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (AssessmentQuestionInfo) l.get(i);
                    String str = getInfoValue(rInfo, colId);
                    if (str.equals(key)) {
                        list.add(rInfo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // to sort search result
    public String getInfoValue(AssessmentQuestionInfo info, String i) 
    {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getName() != null ? info.getName() : "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getAssessmentAnswerTypeName() != null ? info.getAssessmentAnswerTypeName() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getAssessmentParameterName() != null ? info.getAssessmentParameterName() : "";
        }
        return infoval;
    }

    public AssessmentQuestionInfo getAssessmentQuestionDetailById(int assessmentquestionId) 
    {
        AssessmentQuestionInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_assessmentanswertypeid, i_assessmentparameterid, s_link, i_status FROM t_assessmentquestion where i_assessmentquestionid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assessmentquestionId);
            print(this, "getAssessmentQuestionDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, link;
            int assessmentParameterId, status, assessmentAnswerTypeId;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                assessmentAnswerTypeId = rs.getInt(2);
                assessmentParameterId = rs.getInt(3);
                link = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                info = new AssessmentQuestionInfo(name, assessmentAnswerTypeId, assessmentParameterId, link, status);
            }
        } catch (Exception exception) {
            print(this, "getAssessmentQuestionDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public AssessmentQuestionInfo getAssessmentQuestionDetailByIdforDetail(int assessmentquestionId) 
    {
        AssessmentQuestionInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select t_assessmentquestion.s_name, t_assessmentanswertype.s_name, t_assessmentparameter.s_name, t_assessmentquestion.s_link, ");
        sb.append("t_assessmentquestion.i_status FROM t_assessmentquestion");
        sb.append(" left join t_assessmentanswertype ON t_assessmentanswertype.i_assessmentanswertypeid = t_assessmentquestion.i_assessmentanswertypeid ");
        sb.append(" left join t_assessmentparameter ON t_assessmentparameter.i_assessmentparameterid = t_assessmentquestion.i_assessmentparameterid ");
        sb.append("where t_assessmentquestion.i_assessmentquestionid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assessmentquestionId);
            print(this, "getAssessmentQuestionDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, assessmentAnswerTypeName, assessmentParameterName, link;
            int status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                assessmentAnswerTypeName = rs.getString(2) != null ? rs.getString(2) : "";
                assessmentParameterName = rs.getString(3) != null ? rs.getString(3) : "";
                link = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);

                info = new AssessmentQuestionInfo(name, assessmentAnswerTypeName, assessmentParameterName, link, status);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getAssessmentQuestionDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createAssessmentQuestion(AssessmentQuestionInfo info)
    {
        int assessmentquestionId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_assessmentquestion ");
            sb.append("(s_name, i_assessmentanswertypeid, i_assessmentparameterid, s_link,  i_userid, i_status, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setInt(++scc, info.getAssessmentAnswerTypeId());
            pstmt.setInt(++scc, info.getAssessmentParameterId());
            pstmt.setString(++scc, info.getLink());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "createAssessmentQuestion :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                assessmentquestionId = rs.getInt(1);
            }
            createjson(conn);
        } catch (Exception exception) {
            print(this, "createAssessmentQuestion :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return assessmentquestionId;
    }

    public int updateAssessmentQuestion(AssessmentQuestionInfo info)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_assessmentquestion set ");
            sb.append("s_name = ?, ");
            sb.append("i_assessmentanswertypeid = ?, ");
            sb.append("i_assessmentparameterid = ?, ");
            sb.append("s_link = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("i_status = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_assessmentquestionid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, escapeApostrophes(info.getName()));
            pstmt.setInt(++scc, info.getAssessmentAnswerTypeId());
            pstmt.setInt(++scc, info.getAssessmentParameterId());
            pstmt.setString(++scc, info.getLink());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getAssessmentquestionId());
            print(this, "updateAssessmentQuestion :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "updateAssessmentQuestion :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int assessmentquestionId, int assessmentparameterId, String name) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_assessmentquestionid FROM t_assessmentquestion where i_assessmentparameterid = ? and s_name = ? and i_status in (1, 2)");
        if (assessmentquestionId > 0) {
            sb.append(" and i_assessmentquestionid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, assessmentparameterId);
            pstmt.setString(++scc, name);
            if (assessmentquestionId > 0) {
                pstmt.setInt(++scc, assessmentquestionId);
            }
            print(this, "checkDuplicacy :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkDuplicacy :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }
    
    public int deleteAssessmentQuestion(int assessmentquestionId, int userId, int status, String ipAddrStr, String iplocal) 
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_assessmentquestion set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_assessmentquestionid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (status == 1) {
                pstmt.setInt(++scc, 2);
            } else {
                pstmt.setInt(++scc, 1);
            }
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, assessmentquestionId);
            
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "deleteAssessmentQuestion :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Updated", 42, assessmentquestionId);
        return cc;
    }

     public ArrayList getExcel(String search) 
     {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_assessmentquestion.s_name, t_assessmentanswertype.s_name, t_assessmentparameter.s_name,");
        sb.append("t_assessmentquestion.i_status FROM t_assessmentquestion");
        sb.append(" left join t_assessmentanswertype ON t_assessmentanswertype.i_assessmentanswertypeid = t_assessmentquestion.i_assessmentanswertypeid ");
        sb.append(" left join t_assessmentparameter ON t_assessmentparameter.i_assessmentparameterid = t_assessmentquestion.i_assessmentparameterid ");
        sb.append("where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_assessmentquestion.s_name like ? OR t_assessmentanswertype.s_name like ? OR t_assessmentparameter.s_name like ? ) ");
        }
        sb.append(" order by  t_assessmentquestion.i_status, t_assessmentquestion.s_name");

        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            Common.print(this, "getListForExcel :: " + pstmt.toString());

            int scc = 0;
            if (search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            rs = pstmt.executeQuery();
            String name, assessmentAnswerTypeName, assessmentParameterName;
            int status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                assessmentAnswerTypeName = rs.getString(2) != null ? rs.getString(2) : "";
                assessmentParameterName = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4);
                list.add(new AssessmentQuestionInfo(0, name, assessmentAnswerTypeName, assessmentParameterName, status));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public void createjson(Connection conn) 
    {
        String query = ("Select i_assessmentquestionid, s_name FROM t_assessmentquestion where i_status = 1 order by  s_name ");
        try 
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int assessmentquestionId;
            String name;
            JSONArray jarr = new JSONArray();
            while (rs.next()) 
            {
                assessmentquestionId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                JSONObject jobj = new JSONObject();
                jobj.put("i_assessmentquestionid", assessmentquestionId);
                jobj.put("name", name);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "assessmentquestion.json");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, pstmt, rs);
        }
    }
    
    //used on index
    public Collection getAssessmentQuestions() 
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_assessmentquestionid, s_name FROM t_assessmentquestion where i_status = 1 order by s_name").intern();
        coll.add(new AssessmentQuestionInfo(-1, "- Select -"));
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new AssessmentQuestionInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
    
    //index
    public Collection getAssessmentQuestionsIndex() 
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_assessmentquestionid, s_name FROM t_assessmentquestion where i_status = 1 order by s_name").intern();
        coll.add(new AssessmentQuestionInfo(-1, "Sort by Question Type"));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new AssessmentQuestionInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
    
    //used  index
    public Collection getAssessmentQuestionsparameterindex(int assessmentparameterId) 
    {
        Collection coll = new LinkedList();
        coll.add(new AssessmentQuestionInfo(-1, "Sort by Question Type"));
        String query = ("SELECT i_assessmentquestionid, s_name FROM t_assessmentquestion where i_status = 1 and i_assessmentparameterid = ? order by s_name").intern();
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assessmentparameterId);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new AssessmentQuestionInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
    
    // used in edit 
    public Collection getAssessmentQuestionsparameter(int assessmentparameterId)
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_assessmentquestionid, s_name FROM t_assessmentquestion where i_status = 1 and i_assessmentparameterid = ? order by s_name").intern();
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assessmentparameterId);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next())
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new AssessmentQuestionInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
}
