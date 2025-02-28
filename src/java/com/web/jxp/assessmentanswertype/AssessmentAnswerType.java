package com.web.jxp.assessmentanswertype;

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

public class AssessmentAnswerType extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getAssessmentAnswerTypeByName(String search, int next, int count) 
    {
        ArrayList assessmentAnswerType = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select i_assessmentanswertypeid, s_name, i_status ");
        sb.append("FROM t_assessmentanswertype where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (s_name like ? OR i_assessmentanswertypeid = ?) ");
        }
        sb.append(" order by i_status, i_assessmentanswertypeid ");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("select count(1) FROM t_assessmentanswertype where 0 = 0 ");
        if (search != null && !search.equals("")) {
           sb.append("and (s_name like ? OR i_assessmentanswertypeid = ?) ");
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
                pstmt.setString(++scc, (search) );
            }
            logger.log(Level.INFO, "getAssessmentAnswerTypeByName :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int assessmentAnswerTypeId, status;
            while (rs.next()) 
            {
                assessmentAnswerTypeId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                assessmentAnswerType.add(new AssessmentAnswerTypeInfo(assessmentAnswerTypeId, name, status, 0));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, (search) );
            }
            print(this,"getAssessmentAnswerTypeByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                assessmentAnswerTypeId = rs.getInt(1);
                assessmentAnswerType.add(new AssessmentAnswerTypeInfo(assessmentAnswerTypeId, "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return assessmentAnswerType;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        AssessmentAnswerTypeInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (AssessmentAnswerTypeInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            AssessmentAnswerTypeInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (AssessmentAnswerTypeInfo) l.get(i);
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
    public String getInfoValue(AssessmentAnswerTypeInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) 
        {
            infoval = info.getName() != null ? info.getName() : "";
        }
        return infoval;
    }

    public AssessmentAnswerTypeInfo getAssessmentAnswerTypeDetailById(int assessmentAnswerTypeId) 
    {
        AssessmentAnswerTypeInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_status FROM t_assessmentanswertype where i_assessmentanswertypeid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assessmentAnswerTypeId);
            //print(this,"getAssessmentAnswerTypeDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                info = new AssessmentAnswerTypeInfo(assessmentAnswerTypeId, name, status, 0);
            }
        } catch (Exception exception) {
            print(this, "getAssessmentAnswerTypeDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public AssessmentAnswerTypeInfo getAssessmentAnswerTypeDetailByIdforDetail(int assessmentAnswerTypeId) 
    {
        AssessmentAnswerTypeInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_status FROM t_assessmentanswertype where i_assessmentanswertypeid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assessmentAnswerTypeId);
            //print(this,"getAssessmentAnswerTypeDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status = 0;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                info = new AssessmentAnswerTypeInfo(assessmentAnswerTypeId, name, status, 0);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getAssessmentAnswerTypeDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createAssessmentAnswerType(AssessmentAnswerTypeInfo info)
    {
        int assessmentAnswerTypeId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_assessmentanswertype ");
            sb.append("(s_name, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            //print(this,"createAssessmentAnswerType :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                assessmentAnswerTypeId = rs.getInt(1);
            }
            createjson(conn);
        } catch (Exception exception) {
            print(this, "createAssessmentAnswerType :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return assessmentAnswerTypeId;
    }

    public int updateAssessmentAnswerType(AssessmentAnswerTypeInfo info) 
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_assessmentanswertype set ");
            sb.append("s_name = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_assessmentanswertypeid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getAssessmentAnswerTypeId());
            //print(this,"updateAssessmentAnswerType :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "updateAssessmentAnswerType :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int assessmentAnswerTypeId, String name) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_assessmentanswertypeid FROM t_assessmentanswertype where s_name = ? and i_status in (1, 2)");
        if (assessmentAnswerTypeId > 0) {
            sb.append(" and i_assessmentanswertypeid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, name);
            if (assessmentAnswerTypeId > 0) 
            {
                pstmt.setInt(++scc, assessmentAnswerTypeId);
            }
            //print(this,"checkDuplicacy :: " + pstmt.toString());
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

    public Collection getAssessmentAnswerTypes() 
    {
        Collection coll = new LinkedList();
        coll.add(new AssessmentAnswerTypeInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("assessmentanswertype.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) 
        {
            int len = arr.length();
            for (int i = 0; i < len; i++) 
            {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) 
                {
                    coll.add(new AssessmentAnswerTypeInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }

    public int deleteAssessmentAnswerType(int assessmentAnswerTypeId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_assessmentanswertype set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_assessmentanswertypeid = ? ");
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
            pstmt.setInt(++scc, assessmentAnswerTypeId);
            
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "deleteAssessmentAnswerType :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data updated", 41, assessmentAnswerTypeId);
        return cc;
    }

    public ArrayList getListForExcel(String search) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select i_assessmentanswertypeid, s_name, i_status ");
        sb.append("FROM t_assessmentanswertype where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (s_name like ?) ");
        }
        sb.append(" order by i_status, i_assessmentanswertypeid ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status, assessmentAnswerTypeId;
             while (rs.next())
             {
                assessmentAnswerTypeId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                list.add(new AssessmentAnswerTypeInfo(assessmentAnswerTypeId, name, status, 0));
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
        String query = ("Select i_assessmentanswertypeid, s_name FROM t_assessmentanswertype where i_status = 1 order by  s_name ");
        try
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id;
            String name;
            JSONArray jarr = new JSONArray();
            while (rs.next())
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                JSONObject jobj = new JSONObject();
                jobj.put("id", id);
                jobj.put("name", name);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "assessmentanswertype.json");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, pstmt, rs);
        }
    }
}
