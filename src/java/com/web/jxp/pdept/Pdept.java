package com.web.jxp.pdept;

import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
import com.web.jxp.base.StatsInfo;
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
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Pdept extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getPdeptByName(String search, int statusIndex, int assettypeIdIndex, int next, int count) 
    {
        ArrayList pdepts = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_pdept.i_pdeptid, t_pdept.s_name, t_assettype.s_name, t_pdept.i_status FROM t_pdept ");
        sb.append("LEFT JOIN t_assettype ON (t_pdept.i_assettypeid = t_assettype.i_assettypeid) ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_pdept.s_name LIKE ? OR t_assettype.s_name LIKE ?) ");
        }
        if (statusIndex > 0) {
            sb.append("AND t_pdept.i_status = ? ");
        }
        if(assettypeIdIndex > 0)
            sb.append(" AND t_pdept.i_assettypeid = ? ");
        sb.append(" ORDER BY t_pdept.i_status, t_assettype.s_name, t_pdept.s_name ");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1) FROM t_pdept ");
        sb.append("LEFT JOIN t_assettype ON (t_pdept.i_assettypeid = t_assettype.i_assettypeid) ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_pdept.s_name LIKE ? OR t_assettype.s_name LIKE ?) ");
        }
        if (statusIndex > 0) {
            sb.append("AND t_pdept.i_status = ? ");
        }
        if(assettypeIdIndex > 0)
            sb.append("AND t_pdept.i_assettypeid = ? ");
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            if(assettypeIdIndex > 0)
                pstmt.setInt(++scc, assettypeIdIndex);
            logger.info("getPdeptByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, assettypeName;
            int pdeptId, status;
            while (rs.next()) {
                pdeptId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";                
                assettypeName = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4);
                pdepts.add(new PdeptInfo(pdeptId, name, assettypeName, status));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            if(assettypeIdIndex > 0)
                pstmt.setInt(++scc, assettypeIdIndex);
            //print(this,"getPdeptByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                pdeptId = rs.getInt(1);
                pdepts.add(new PdeptInfo(pdeptId, "", "", 0));
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
        PdeptInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (PdeptInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            PdeptInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (PdeptInfo) l.get(i);
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
    public String getInfoValue(PdeptInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getName() != null ? info.getName() : "";
        }
        else if (i != null && i.equals("2")) {
            infoval = info.getAssettypeName() != null ? info.getAssettypeName() : "";
        }
        return infoval;
    }

    public PdeptInfo getPdeptDetailById(int pdeptId) {
        PdeptInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_assettypeid, s_description, i_status FROM t_pdept WHERE i_pdeptid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pdeptId);
            print(this, "getPdeptDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description;
            int status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                int assettypeId = rs.getInt(2);
                description = rs.getString(3) != null ? rs.getString(3): "";
                status = rs.getInt(4);
                info = new PdeptInfo(pdeptId, name, assettypeId, description, status, 0);
            }
        } 
        catch (Exception exception) 
        {
            print(this, "getPdeptDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public PdeptInfo getPdeptDetailByIdforDetail(int pdeptId) 
    {
        PdeptInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_pdept.s_name, t_pdept.i_status, t_assettype.s_name, t_pdept.s_description FROM t_pdept LEFT JOIN t_assettype ON (t_pdept.i_assettypeid = t_assettype.i_assettypeid) WHERE i_pdeptid = ?");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pdeptId);
            print(this, "getPdeptDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status = 0;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2);
                String assettypeName = rs.getString(3) != null ? rs.getString(3): "";
                String description = rs.getString(4) != null ? rs.getString(4): "";
                info = new PdeptInfo(pdeptId, name, assettypeName, status, description);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getPdeptDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createPdept(PdeptInfo info) {
        int pdeptId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_pdept ");
            sb.append("(s_name, i_assettypeid, s_description, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "createPdept :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                pdeptId = rs.getInt(1);
            }
            createjson(conn);
        } catch (Exception exception) {
            print(this, "createPdept :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return pdeptId;
    }

    public int updatePdept(PdeptInfo info) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_pdept SET ");
            sb.append("s_name = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ?, ");
            sb.append("i_assettypeid = ?, ");
            sb.append("s_description = ? ");
            sb.append("WHERE i_pdeptid = ? ");
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
            pstmt.setString(++scc, info.getDescription());
            pstmt.setInt(++scc, info.getPdeptId());
            print(this, "updatePdept :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "updatePdept :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int pdeptId, String name, int assettypeId) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_pdeptid FROM t_pdept WHERE i_assettypeid = ? AND s_name = ? AND i_status IN (1, 2) ");
        if (pdeptId > 0) {
            sb.append("AND i_pdeptid !=? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, assettypeId);
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

    public int deletePdept(int pdeptId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_pdept SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_pdeptid = ? ");
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
            pstmt.setInt(++scc, pdeptId);            
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "deletePdept :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Updated", 82, pdeptId);
        return cc;
    }

    public ArrayList getExcel(String search, int assettypeIdIndex, int statusIndex) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_pdept.i_pdeptid, t_pdept.s_name, t_pdept.i_status, t_assettype.s_name, t_pdept.s_description ");
        sb.append("FROM t_pdept ");
        sb.append("LEFT JOIN t_assettype ON (t_pdept.i_assettypeid = t_assettype.i_assettypeid) ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_pdept.s_name LIKE ? OR t_assettype.s_name LIKE ? ) ");
        }
        if (statusIndex > 0) {
            sb.append("AND t_pdept.i_status = ? ");
        }
        if(assettypeIdIndex > 0)
            sb.append(" AND t_pdept.i_assettypeid = ? ");
        sb.append(" ORDER BY t_pdept.i_status, t_assettype.s_name, t_pdept.s_name  ");

        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (statusIndex > 0)
                pstmt.setInt(++scc, statusIndex);
            if(assettypeIdIndex > 0)
                pstmt.setInt(++scc, assettypeIdIndex);
            rs = pstmt.executeQuery();
            int pdeptId, status;
            String name, assettypeName, description;
            while (rs.next())
            {
                pdeptId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                assettypeName = rs.getString(4) != null ? rs.getString(4) : "";
                description = rs.getString(5) != null ? rs.getString(5) : "";
                list.add(new PdeptInfo(pdeptId, name, assettypeName, status, description));
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
    
    public Collection getPdepts(int assettypeId)
    {
        Collection coll = new LinkedList();
        if(assettypeId > 0)
        {
            coll.add(new PdeptInfo(-1, "Select Department"));
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
                        coll.add(new PdeptInfo(jobj.optInt("id"), jobj.optString("name")));
                    }
                }
            }
        }
        else
        {
            coll.add(new PdeptInfo(-1, "Select Department"));
        }
        return coll;
    }
    
    public void createjson(Connection conn)
    {
       String query = ("SELECT i_pdeptid, s_name, i_assettypeid FROM t_pdept WHERE i_status = 1 ORDER BY s_name ");
        try
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id, assettypeId;
            String name;
            JSONArray jarr = new JSONArray();
            while (rs.next())
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2): "";
                assettypeId = rs.getInt(3);
                JSONObject jobj = new JSONObject();
                jobj.put("id", id);
                jobj.put("assettypeId", assettypeId);                
                jobj.put("name", name);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "pdept.json");
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
    
    public Collection getAssettypes()
    {
        Collection coll = new LinkedList();
        coll.add(new PdeptInfo(-1, " All "));
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
                    coll.add(new PdeptInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
    
    public Collection getAssettypesForEdit()
    {
        Collection coll = new LinkedList();
        coll.add(new PdeptInfo(-1, " Select Asset Type "));
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
                    coll.add(new PdeptInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
    
    public Collection getStatusNew() {
        Collection status = new LinkedList();
        status.add(new StatsInfo("-1", " All "));
        status.add(new StatsInfo("1", "Active"));
        status.add(new StatsInfo("2", "Inactive"));
        return status;
    }
}
