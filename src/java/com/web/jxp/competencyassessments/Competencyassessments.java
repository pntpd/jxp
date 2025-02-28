package com.web.jxp.competencyassessments;

import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
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
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Competencyassessments extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getCompetencyassessmentsByName(String search, int deptId, int assettypeIdIndex, int next, int count) 
    {
        ArrayList pdepts = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_passessmenttype.i_passessmenttypeid, t_passessmenttype.s_name, t_pdept.s_name, t_assettype.s_name, ");
        sb.append("t_passessmenttype.i_status, t_passessmenttype.i_schedule, t_passessmenttype.s_description ");
        sb.append("FROM t_passessmenttype ");
        sb.append("LEFT JOIN t_assettype ON (t_passessmenttype.i_assettypeid = t_assettype.i_assettypeid) ");
        sb.append("LEFT JOIN t_pdept ON (t_pdept.i_pdeptid = t_passessmenttype.i_pdeptid) ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_passessmenttype.s_name LIKE ? OR t_pdept.s_name LIKE ? OR t_assettype.s_name LIKE ?) ");
        }
        if (deptId > 0) {
            sb.append("AND t_pdept.i_pdeptid = ? ");
        }
        if(assettypeIdIndex > 0)
            sb.append(" AND t_passessmenttype.i_assettypeid = ? ");
        sb.append(" ORDER BY t_passessmenttype.i_status, t_assettype.s_name, t_pdept.s_name, t_passessmenttype.s_name ");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1) FROM t_passessmenttype ");
        sb.append("LEFT JOIN t_assettype ON (t_passessmenttype.i_assettypeid = t_assettype.i_assettypeid) ");
        sb.append("LEFT JOIN t_pdept ON (t_pdept.i_pdeptid = t_passessmenttype.i_pdeptid) ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_passessmenttype.s_name LIKE ? OR t_pdept.s_name LIKE ? OR t_assettype.s_name LIKE ?) ");
        }
        if (deptId > 0) {
            sb.append("AND t_pdept.i_pdeptid = ? ");
        }
        if(assettypeIdIndex > 0)
            sb.append("AND t_passessmenttype.i_assettypeid = ? ");
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (deptId > 0) {
                pstmt.setInt(++scc, deptId);
            }
            if(assettypeIdIndex > 0)
                pstmt.setInt(++scc, assettypeIdIndex);
            logger.info("getCompetencyassessmentsByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, assettypeName, department, description;
            int pdeptId, status,schedule;
            while (rs.next()) 
            {
                pdeptId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";                
                department = rs.getString(3) != null ? rs.getString(3) : "";                
                assettypeName = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                schedule = rs.getInt(6);
                description = rs.getString(7) != null ? rs.getString(7) : "";
                pdepts.add(new CompetencyassessmentsInfo(pdeptId, name, department, assettypeName, status, schedule, description));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (deptId > 0) {
                pstmt.setInt(++scc, deptId);
            }
            if(assettypeIdIndex > 0)
                pstmt.setInt(++scc, assettypeIdIndex);
            print(this,"getCompetencyassessmentsByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                pdeptId = rs.getInt(1);
                pdepts.add(new CompetencyassessmentsInfo(pdeptId, "", "", "", 0,0,""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return pdepts;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        CompetencyassessmentsInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (CompetencyassessmentsInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            CompetencyassessmentsInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (CompetencyassessmentsInfo) l.get(i);
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
    public String getInfoValue(CompetencyassessmentsInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getName() != null ? info.getName() : "";
        }
        else if (i != null && i.equals("2")) {
            infoval = info.getAssettypeName() != null ? info.getAssettypeName() : "";
        }
        else if (i != null && i.equals("3")) {
            infoval = info.getDepartment()!= null ? info.getDepartment(): "";
        }
        return infoval;
    }

    public CompetencyassessmentsInfo getCompetencyassessmentsDetailById(int competencyassessmentsId) {
        CompetencyassessmentsInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_assettypeid, i_pdeptid, s_description, i_status, i_schedule FROM t_passessmenttype WHERE i_passessmenttypeid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, competencyassessmentsId);
            print(this, "getCompetencyassessmentsDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description;
            int status, schedule;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                int assettypeId = rs.getInt(2);
                int departmentId = rs.getInt(3);
                description = rs.getString(4) != null ? rs.getString(4): "";
                status = rs.getInt(5);
                schedule = rs.getInt(6);
                info = new CompetencyassessmentsInfo(competencyassessmentsId, name, assettypeId, description, status, 0,departmentId, schedule );
            }
        } 
        catch (Exception exception) 
        {
            print(this, "getCompetencyassessmentsDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public CompetencyassessmentsInfo getCompetencyassessmentsDetailByIdforDetail(int pdeptId) 
    {
        CompetencyassessmentsInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_passessmenttype.s_name, t_passessmenttype.i_status, t_assettype.s_name, ");
        sb.append("t_passessmenttype.s_description, t_pdept.s_name, t_passessmenttype.i_schedule ");
        sb.append("FROM t_passessmenttype ");
        sb.append("LEFT JOIN t_assettype ON ( t_passessmenttype.i_assettypeid = t_assettype.i_assettypeid) ");
        sb.append("LEFT JOIN t_pdept ON ( t_pdept.i_assettypeid = t_assettype.i_assettypeid) ");
        sb.append("WHERE t_passessmenttype.i_passessmenttypeid = ?");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pdeptId);
            print(this, "getCompetencyassessmentsDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status = 0, schedule;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2);
                String assettypeName = rs.getString(3);
                String description = rs.getString(4) != null ? rs.getString(4): "";
                String department = rs.getString(5) != null ? rs.getString(5): "";
                schedule = rs.getInt(6);
                info = new CompetencyassessmentsInfo(pdeptId, name, assettypeName, status, description, department, schedule);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getCompetencyassessmentsDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createCompetencyassessments(CompetencyassessmentsInfo info) {
        int pdeptId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_passessmenttype ");
            sb.append("(s_name, i_assettypeid, i_pdeptid, s_description, i_status, i_schedule, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setInt(++scc, info.getDepartmentId());
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getSchedule());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "createCompetencyassessments :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                pdeptId = rs.getInt(1);
            }
            createjson(conn);
        } catch (Exception exception) {
            print(this, "createCompetencyassessments :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return pdeptId;
    }

    public int updateCompetencyassessments(CompetencyassessmentsInfo info) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_passessmenttype SET ");
            sb.append("s_name = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ?, ");
            sb.append("i_assettypeid = ?, ");
            sb.append("i_pdeptid = ?, ");
            sb.append("i_schedule = ?, ");
            sb.append("s_description = ? ");
            sb.append("WHERE i_passessmenttypeid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setInt(++scc, info.getDepartmentId());
            pstmt.setInt(++scc, info.getSchedule());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setInt(++scc, info.getCompetencyassessmentsId());
            print(this, "updateCompetencyassessments :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "updateCompetencyassessments :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int pdeptId, String name) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_passessmenttypeid FROM t_passessmenttype WHERE s_name = ? AND i_status IN (1, 2) ");
        if (pdeptId > 0) {
            sb.append("AND i_passessmenttypeid !=? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, name);
            if (pdeptId > 0) {
                pstmt.setInt(++scc, pdeptId);
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

    public int deleteCompetencyassessments(int competencyassessmentsId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_passessmenttype SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_passessmenttypeid = ? ");
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
            pstmt.setInt(++scc, competencyassessmentsId);            
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "deleteCompetencyassessments :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Updated", 84, competencyassessmentsId);
        return cc;
    }

    public ArrayList getExcel(String search, int departmentId, int assettypeIdIndex) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_passessmenttype.i_passessmenttypeid, t_passessmenttype.s_name, t_pdept.s_name, t_assettype.s_name, ");
        sb.append("t_passessmenttype.i_status, t_passessmenttype.i_schedule, t_passessmenttype.s_description ");
        sb.append("FROM t_passessmenttype ");
        sb.append("LEFT JOIN t_assettype ON (t_passessmenttype.i_assettypeid = t_assettype.i_assettypeid) ");
        sb.append("LEFT JOIN t_pdept ON (t_pdept.i_pdeptid = t_passessmenttype.i_pdeptid) ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_passessmenttype.s_name LIKE ? OR t_pdept.s_name LIKE ? OR t_assettype.s_name LIKE ?) ");
        }
        if (departmentId > 0) {
            sb.append("AND t_pdept.i_pdeptid = ? ");
        }
        if(assettypeIdIndex > 0)
            sb.append(" AND t_passessmenttype.i_assettypeid = ? ");
        sb.append(" ORDER BY t_passessmenttype.i_status, t_assettype.s_name, t_pdept.s_name, t_passessmenttype.s_name ");
        
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (departmentId > 0) {
                pstmt.setInt(++scc, departmentId);
            }
            if(assettypeIdIndex > 0)
                pstmt.setInt(++scc, assettypeIdIndex);
            logger.info("getCompetencyassessmentsByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, assettypeName, department, description;
            int pdeptId, status, schedule;
            while (rs.next()) 
            {
                pdeptId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";                
                department = rs.getString(3) != null ? rs.getString(3) : "";                
                assettypeName = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                schedule = rs.getInt(6);
                description = rs.getString(7) != null ? rs.getString(7) : "";
                list.add(new CompetencyassessmentsInfo(pdeptId, name, department, assettypeName, status, schedule, description));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public void createjson(Connection conn)
    {
       String query = ("SELECT i_passessmenttypeid, s_name, i_assettypeid, i_pdeptid FROM t_passessmenttype WHERE i_status = 1 ORDER BY i_pdeptid, s_name ");
        try
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id, assettypeId, departmentId;
            String name;
            JSONArray jarr = new JSONArray();
            while (rs.next())
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2): "";
                assettypeId = rs.getInt(3);
                departmentId = rs.getInt(4);
                JSONObject jobj = new JSONObject();
                jobj.put("id", id);
                jobj.put("assettypeId", assettypeId);                
                jobj.put("departmentId", departmentId);                
                jobj.put("name", name);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "competencyassessments.json");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(null, pstmt, rs);
        }
    }
    
    public Collection getAssettypes(int type)
    {
        Collection coll = new LinkedList();
        if(type ==1){
            coll.add(new CompetencyassessmentsInfo(-1, "All"));
        }
        else if(type ==2){
            coll.add(new CompetencyassessmentsInfo(-1, "Select Asset Type"));
        }
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("assettype.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new CompetencyassessmentsInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
    
    public Collection getCompetencyDept(int assettypeId, int type)
    {
        Collection coll = new LinkedList();
        if(assettypeId > 0)
        {
            if(type == 1){
                coll.add(new CompetencyassessmentsInfo(-1, "All"));
            }
            else if(type == 2){
                coll.add(new CompetencyassessmentsInfo(-1, "Select Department"));
            }
            String xml_path = getMainPath("json_path");
            String str = readHTMLFile("pdept.json", xml_path);
            JSONArray arr = new JSONArray(str);
            if(arr != null)
            {
                int len = arr.length();
                for(int i = 0; i < len; i++)
                {
                    JSONObject jobj = arr.optJSONObject(i);
                    if(jobj != null && jobj.optInt("assettypeId") == assettypeId)
                    {
                        coll.add(new CompetencyassessmentsInfo(jobj.optInt("id"), jobj.optString("name")));
                    }
                }
            }
        }
        else
        {
            if(type == 1){
                coll.add(new CompetencyassessmentsInfo(-1, "All"));
            }
            else if(type == 2){
                coll.add(new CompetencyassessmentsInfo(-1, "Select Department"));
            }
        }
        return coll;
    }
}