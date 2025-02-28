package com.web.jxp.assessmentparameter;

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

public class AssessmentParameter extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getAssessmentParameterByName(String search, int next, int count) 
    {
        ArrayList assessmentParameter = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select i_assessmentparameterid, s_name, i_status ");
        sb.append("FROM t_assessmentparameter where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (s_name like ? OR i_assessmentparameterid = ?) ");
        }
        sb.append(" order by i_status, i_assessmentparameterid ");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("select count(1) FROM t_assessmentparameter where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (s_name like ? OR i_assessmentparameterid = ?) ");
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
            logger.log(Level.INFO, "getAssessmentParameterByName :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int assessmentParameterId, status;
            while (rs.next()) 
            {
                assessmentParameterId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                assessmentParameter.add(new AssessmentParameterInfo(assessmentParameterId, name, status, 0));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, (search) );
            }
            print(this,"getAssessmentParameterByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                assessmentParameterId = rs.getInt(1);
                assessmentParameter.add(new AssessmentParameterInfo(assessmentParameterId, "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return assessmentParameter;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        AssessmentParameterInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (AssessmentParameterInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            AssessmentParameterInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (AssessmentParameterInfo) l.get(i);
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
    public String getInfoValue(AssessmentParameterInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getName() != null ? info.getName() : "";
        }
        return infoval;
    }

    public AssessmentParameterInfo getAssessmentParameterDetailById(int assessmentParameterId) 
    {
        AssessmentParameterInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_status,s_description FROM t_assessmentparameter where i_assessmentparameterid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assessmentParameterId);
            //print(this,"getAssessmentParameterDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description;
            int status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                description = rs.getString(3) != null ? rs.getString(3) : "";
                info = new AssessmentParameterInfo(assessmentParameterId, name, status, 0, description);
            }
        } catch (Exception exception) {
            print(this, "getAssessmentParameterDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public AssessmentParameterInfo getAssessmentParameterDetailByIdforDetail(int assessmentParameterId)
    {
        AssessmentParameterInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, s_description, i_status FROM t_assessmentparameter where i_assessmentparameterid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assessmentParameterId);
            //print(this,"getAssessmentParameterDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description;
            int status = 0;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                description = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                info = new AssessmentParameterInfo(assessmentParameterId, name, description, status);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getAssessmentParameterDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createAssessmentParameter(AssessmentParameterInfo info) 
    {
        int assessmentParameterId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_assessmentparameter ");
            sb.append("(s_name, s_description, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            //print(this,"createAssessmentParameter :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                assessmentParameterId = rs.getInt(1);
            }
            createjson(conn);
        } catch (Exception exception) {
            print(this, "createAssessmentParameter :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return assessmentParameterId;
    }

    public int updateAssessmentParameter(AssessmentParameterInfo info) 
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_assessmentparameter set ");
            sb.append("s_name = ?, ");
            sb.append("s_description = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_assessmentparameterid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getAssessmentParameterId());
            print(this,"updateAssessmentParameter :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "updateAssessmentParameter :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int assessmentParameterId, String name) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_assessmentparameterid FROM t_assessmentparameter where s_name = ? and i_status in (1, 2)");
        if (assessmentParameterId > 0) {
            sb.append(" and i_assessmentparameterid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, name);
            if (assessmentParameterId > 0) {
                pstmt.setInt(++scc, assessmentParameterId);
            }
            //print(this,"checkDuplicacy :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkDuplicacy :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public Collection getAssessmentParameters() 
    {
        Collection coll = new LinkedList();
        coll.add(new AssessmentParameterInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("assessmentparameter.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null)
        {
            int len = arr.length();
            for (int i = 0; i < len; i++) {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) 
                {
                    coll.add(new AssessmentParameterInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
    
    public Collection getAssessmentParametersindex() 
    {
        Collection coll = new LinkedList();
        coll.add(new AssessmentParameterInfo(-1, "Sort by Parameter"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("assessmentparameter.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) 
        {
            int len = arr.length();
            for (int i = 0; i < len; i++) 
            {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) 
                {
                    coll.add(new AssessmentParameterInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }   
        
    public ArrayList getAssessmentParameterArraylist() 
    {
        ArrayList aparameterlist = new ArrayList();
       // coll.add(new AssessmentParameterInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("assessmentparameter.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) 
        {
            int len = arr.length();
            for (int i = 0; i < len; i++) 
            {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) 
                {
                    aparameterlist.add(new AssessmentParameterInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return aparameterlist;
    }

    public int deleteAssessmentParameter(int assessmentParameterId, int userId, int status, String ipAddrStr, String iplocal) 
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_assessmentparameter set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_assessmentparameterid = ? ");
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
            pstmt.setInt(++scc, assessmentParameterId);
            
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "deleteAssessmentParameter :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data updated", 40, assessmentParameterId);
        return cc;
    }

    public ArrayList getListForExcel(String search) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select i_assessmentparameterid, s_name, i_status ");
        sb.append("FROM t_assessmentparameter where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (s_name like ?) ");
        }
        sb.append(" order by i_status, i_assessmentparameterid ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) 
            {
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status, assessmentParameterId;
            while (rs.next()) 
            {
                assessmentParameterId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                list.add(new AssessmentParameterInfo(assessmentParameterId, name, status, 0));
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
        String query = ("Select i_assessmentparameterid, s_name FROM t_assessmentparameter where i_status = 1 order by  s_name ");
        try 
        {
            pstmt = conn.prepareStatement(query);
            Common.print(this, "Assessment parameter createjson :: " + pstmt.toString());

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
            Common.print(this, "filePath :: " + filePath);            
            writeHTMLFile(str, filePath, "assessmentparameter.json");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, pstmt, rs);
        }
    }
    
    //view
    public Collection getAssessmentParametersChecked(String ids) 
    {
        Collection coll = new LinkedList();
        if(ids != null && !ids.equals(""))
        {
            coll.add(new AssessmentParameterInfo(-1, "- Select -"));
            String xml_path = getMainPath("json_path");
            String str = readHTMLFile("assessmentparameter.json", xml_path);
            JSONArray arr = new JSONArray(str);
            if (arr != null) {
                int len = arr.length();
                for (int i = 0; i < len; i++) {
                    JSONObject jobj = arr.optJSONObject(i);
                    if (jobj != null) 
                    {
                        int id = jobj.optInt("id");
                        if( checkToStr(ids, ""+id))
                        {
                            coll.add(new AssessmentParameterInfo(id, jobj.optString("name")));
                        }
                    }
                }
            }
        }
        else
        {
            coll.add(new AssessmentParameterInfo(-1, "- Select -"));
        }
        return coll;
    }
    
    public Collection getAssessmentParametersCheckedview(String ids) 
    {
        Collection coll = new LinkedList();
        if(ids != null && !ids.equals(""))
        {
            //coll.add(new AssessmentParameterInfo(-1, "- Select -"));
            String xml_path = getMainPath("json_path");
            String str = readHTMLFile("assessmentparameter.json", xml_path);
            JSONArray arr = new JSONArray(str);
            if (arr != null) {
                int len = arr.length();
                for (int i = 0; i < len; i++) {
                    JSONObject jobj = arr.optJSONObject(i);
                    if (jobj != null) 
                    {
                        int id = jobj.optInt("id");
                        if( checkToStr(ids, ""+id))
                        {
                            coll.add(new AssessmentParameterInfo(id, jobj.optString("name")));
                        }
                    }
                }
            }
        }        
        return coll;
    }
}
