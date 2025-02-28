package com.web.jxp.competencypriorities;

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

public class Competencypriorities extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getCompetencyprioritiesByName(String search, int departmentIdIndex, int clientIdIndex, int assetIdIndex, int next, int count) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_priority.i_priorityid, t_priority.s_name, t_client.s_name, t_clientasset.s_name, t4.names, ");
        sb.append("t_priority.i_degree, t_priority.s_description, t_priority.i_status ");
        sb.append("FROM t_priority ");
        sb.append("LEFT JOIN (SELECT t_priority.i_priorityid, GROUP_CONCAT(DISTINCT t3.s_name ORDER BY t3.s_name SEPARATOR ', ') AS names FROM t_priority ");
        sb.append("LEFT JOIN t_pdept AS t3 ON FIND_IN_SET(t3.i_pdeptid, t_priority.s_pdeptids) GROUP BY t_priority.i_priorityid) AS t4 ON (t4.i_priorityid = t_priority.i_priorityid) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_priority.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_priority.i_clientassetid) ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_priority.s_name LIKE ? OR t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ?) ");
        }      
        if (clientIdIndex > 0) {
            sb.append("AND t_priority.i_clientid = ? ");
        }
        if ((assetIdIndex > 0)) {
            sb.append("AND t_priority.i_clientassetid = ? ");
        }
        if( departmentIdIndex > 0)
        {
            sb.append("AND FIND_IN_SET(?, t_priority.s_pdeptids) ");
        }            
        sb.append(" ORDER BY t_priority.i_status, t_client.s_name, t_clientasset.s_name ");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1) FROM t_priority ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_priority.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_priority.i_clientassetid) ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_priority.s_name LIKE ? OR t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ?) ");
        }
        if (clientIdIndex > 0) {
            sb.append("AND t_priority.i_clientid = ? ");
        }
        if ((assetIdIndex > 0)) {
            sb.append("AND t_priority.i_clientassetid = ? ");
        }
        if( departmentIdIndex > 0)
        {
            sb.append("AND FIND_IN_SET(?, t_priority.s_pdeptids) ");
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) 
            {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (clientIdIndex > 0) {
                    pstmt.setInt(++scc, clientIdIndex);
                }
            if ((assetIdIndex > 0)) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            if (departmentIdIndex > 0) {
                pstmt.setInt(++scc, departmentIdIndex);
            }
            logger.info("getCompetencyprioritiesByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, assetName, department,clientName, description;
            int competencyprioritiesId, degreeId, status;
            while (rs.next()) 
            {
                competencyprioritiesId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";                
                clientName = rs.getString(3) != null ? rs.getString(3) : "";                
                assetName = rs.getString(4) != null ? rs.getString(4) : "";
                department = rs.getString(5) != null ? rs.getString(5) : "";
                degreeId = rs.getInt(6);
                description = rs.getString(7) != null ? rs.getString(7) : "";
                status = rs.getInt(8);
                list.add(new CompetencyprioritiesInfo(competencyprioritiesId, name, clientName, assetName, department, degreeId, description, status));
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
            if (clientIdIndex > 0) {
                    pstmt.setInt(++scc, clientIdIndex);
                }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            if (departmentIdIndex > 0) {
                pstmt.setInt(++scc, departmentIdIndex);
            }
            print(this,"getCompetencyprioritiesByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                competencyprioritiesId = rs.getInt(1);
                list.add(new CompetencyprioritiesInfo(competencyprioritiesId, "", "", "", "", 0,"",0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        CompetencyprioritiesInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (CompetencyprioritiesInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
             if (colId.equals("2")) {
                map = sortById(record, tp);
            } else {
                map = sortByName(record, tp);
            }
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            CompetencyprioritiesInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (CompetencyprioritiesInfo) l.get(i);
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
    public String getInfoValue(CompetencyprioritiesInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getName() != null ? info.getName() : "";
        }
        else if (i != null && i.equals("2")) {
            infoval = "" + info.getDegreeId();
        }
        else if (i != null && i.equals("3")) {
            infoval = info.getClientName()!= null ? info.getClientName(): "";
        }
        else if (i != null && i.equals("4")) {
            infoval = info.getAssetName()!= null ? info.getAssetName(): "";
        }
        else if (i != null && i.equals("5")) {
            infoval = info.getDeptColumn()!= null ? info.getDeptColumn(): "";
        }
        return infoval;
    }

    public CompetencyprioritiesInfo getCompetencyprioritiesDetailById(int competencyprioritiesId) {
        CompetencyprioritiesInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_clientid, i_clientassetid , s_pdeptids, i_degree, s_description, i_status FROM t_priority WHERE i_priorityid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, competencyprioritiesId);
            print(this, "getCompetencyprioritiesDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description, pdeptids;
            int status, clientId, clientassetId, degreeId;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                clientId = rs.getInt(2);
                clientassetId = rs.getInt(3);
                pdeptids = rs.getString(4) != null ? rs.getString(4): "";
                degreeId = rs.getInt(5);
                description = rs.getString(6) != null ? rs.getString(6): "";
                status = rs.getInt(7);
                info = new CompetencyprioritiesInfo(competencyprioritiesId, name, clientId, 
                        clientassetId, pdeptids, degreeId, description, status );
            }
        } 
        catch (Exception exception) 
        {
            print(this, "getCompetencyprioritiesDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public CompetencyprioritiesInfo getCompetencyprioritiesDetailByIdforDetail(int competencyprioritiesId) 
    {
        CompetencyprioritiesInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_priority.s_name, t_client.s_name, t_clientasset.s_name, ");
        sb.append("(GROUP_CONCAT(DISTINCT t_pdept.s_name ORDER BY t_priority.s_pdeptids  SEPARATOR ', ') ) AS pnames, t_priority.i_degree, t_priority.i_status, t_priority.s_description ");
        sb.append("FROM t_priority  ");
        sb.append("LEFT JOIN t_pdept  ON FIND_IN_SET (t_pdept.i_pdeptid, t_priority.s_pdeptids) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_priority.i_clientid)  ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_priority.i_clientassetid) ");
        sb.append("WHERE t_priority.i_priorityid = ?");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, competencyprioritiesId);
            print(this, "getCompetencyprioritiesDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status = 0;
            while (rs.next()) 
            {
                name = rs.getString(1);
                String clientName = rs.getString(2) != null ? rs.getString(2): "";
                String assetName = rs.getString(3) != null ? rs.getString(3): "";
                String deptIds = rs.getString(4) != null ? rs.getString(4): "";
                int degreeId = rs.getInt(5);
                status = rs.getInt(6);
                String description = rs.getString(7) != null ? rs.getString(7): "";
                info = new CompetencyprioritiesInfo(competencyprioritiesId, name, clientName,assetName, deptIds, degreeId,status, description);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getCompetencyprioritiesDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createCompetencypriorities(CompetencyprioritiesInfo info) {
        int pdeptId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_priority ");
            sb.append("(i_clientid, i_clientassetid, s_pdeptids, s_name, i_degree, s_description, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, info.getClientId());
            pstmt.setInt(++scc, info.getAssetId());
            pstmt.setString(++scc, (info.getDeptColumn()));
            pstmt.setString(++scc, (info.getName()));
            pstmt.setInt(++scc, info.getDegreeId());
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "createCompetencypriorities :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                pdeptId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createCompetencypriorities :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return pdeptId;
    }

    public int updateCompetencypriorities(CompetencyprioritiesInfo info) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_priority SET ");
            sb.append("s_name = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ?, ");
            sb.append("i_clientid = ?, ");
            sb.append("i_clientassetid = ?, ");
            sb.append("s_pdeptids = ?, ");
            sb.append("i_degree = ?, ");
            sb.append("s_description = ? ");
            sb.append("WHERE i_priorityid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getClientId());
            pstmt.setInt(++scc, info.getAssetId());
            pstmt.setString(++scc, info.getDeptColumn());
            pstmt.setInt(++scc, info.getDegreeId());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setInt(++scc, info.getCompetencyprioritiesId());
            print(this, "updateCompetencypriorities :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateCompetencypriorities :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int competencyprioritiesId, String name, int assetId, int clientId) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_priorityid FROM t_priority WHERE i_clientassetid = ? AND i_clientid = ?  AND s_name = ? AND i_status IN (1, 2) ");
        if (competencyprioritiesId > 0) {
            sb.append("AND i_priorityid !=? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, assetId);
            pstmt.setInt(++scc, clientId);
            pstmt.setString(++scc, name);
            if (competencyprioritiesId > 0) {
                pstmt.setInt(++scc, competencyprioritiesId);
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

    public int deleteCompetencypriorities(int competencyprioritiesId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_priority SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_priorityid = ? ");
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
            pstmt.setInt(++scc, competencyprioritiesId);            
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteCompetencypriorities :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Updated", 85, competencyprioritiesId);
        return cc;
    }

    public ArrayList getExcel(String search, int departmentIdIndex, int clientIdIndex, int assetIdIndex) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_priority.i_priorityid, t_priority.s_name, t_client.s_name, t_clientasset.s_name, t4.names, ");
        sb.append("t_priority.i_degree, t_priority.s_description, t_priority.i_status ");
        sb.append("FROM t_priority ");
        sb.append("LEFT JOIN (SELECT t_priority.i_priorityid, GROUP_CONCAT(DISTINCT t3.s_name ORDER BY t3.s_name SEPARATOR ', ') AS names FROM t_priority ");
        sb.append("LEFT JOIN t_pdept AS t3 ON FIND_IN_SET(t3.i_pdeptid, t_priority.s_pdeptids) GROUP BY t_priority.i_priorityid) AS t4 ON (t4.i_priorityid = t_priority.i_priorityid) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_priority.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_priority.i_clientassetid) ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_priority.s_name LIKE ? OR t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ?) ");
        }      
        if (clientIdIndex > 0) {
            sb.append("AND t_priority.i_clientid = ? ");
        }
        if ((assetIdIndex > 0)) {
            sb.append("AND t_priority.i_clientassetid = ? ");
        }
        if( departmentIdIndex > 0)
        {
            sb.append("AND FIND_IN_SET(?, t_priority.s_pdeptids) ");
        }            
        sb.append(" ORDER BY t_priority.i_status, t_client.s_name, t_clientasset.s_name ");
        
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) 
            {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (clientIdIndex > 0) {
                    pstmt.setInt(++scc, clientIdIndex);
                }
            if ((assetIdIndex > 0)) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            if (departmentIdIndex > 0) {
                pstmt.setInt(++scc, departmentIdIndex);
            }
            logger.info("getCompetencyprioritiesByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, assetName, department,clientName, description;
            int competencyprioritiesId, degreeId, status;
            while (rs.next()) 
            {
                competencyprioritiesId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";                
                clientName = rs.getString(3) != null ? rs.getString(3) : "";                
                assetName = rs.getString(4) != null ? rs.getString(4) : "";
                department = rs.getString(5) != null ? rs.getString(5) : "";
                degreeId = rs.getInt(6);
                description = rs.getString(7) != null ? rs.getString(7) : "";
                status = rs.getInt(8);
                list.add(new CompetencyprioritiesInfo(competencyprioritiesId, name, clientName, assetName, department, degreeId, description, status));
            }
            rs.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;    
    }
    
    //Clientddl
    public Collection getClients(String cids, int allclient, String permission, int type)
    {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_clientid, s_name FROM t_client WHERE i_status = 1 ");
        if (allclient == 1) {
            sb.append("AND i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND i_clientid < 0 ");
            }
        }
        sb.append("ORDER BY s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        if(type == 2)
            coll.add(new CompetencyprioritiesInfo(-1, " Select Client"));
        else if(type == 1)
            coll.add(new CompetencyprioritiesInfo(-1, "All"));            
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
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new CompetencyprioritiesInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getClientAsset(int clientId, String assetids, int allclient, String permission, int type) 
    {
        Collection coll = new LinkedList();
        if (clientId > 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_clientid = ? AND i_status = 1 ");
            if (allclient == 1) {
                sb.append("AND i_clientassetid > 0 ");
            } else {
                if (!assetids.equals("")) {
                    sb.append("AND i_clientassetid IN (" + assetids + ") ");
                } else {
                    sb.append("AND i_clientassetid < 0 ");
                }
            }
            sb.append("ORDER BY s_name ");
            String query = sb.toString();
            sb.setLength(0);
            if(type == 2)
                coll.add(new CompetencyprioritiesInfo(-1, "Select Asset"));
            else if(type == 1)
                coll.add(new CompetencyprioritiesInfo(-1, "All"));
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                logger.info("getClientAsset :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) 
                {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2): "";
                    coll.add(new CompetencyprioritiesInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            if(type == 2)
                coll.add(new CompetencyprioritiesInfo(-1, "Select Asset"));
            else if(type == 1)
                coll.add(new CompetencyprioritiesInfo(-1, "All"));
        }
        return coll;
    }
    
    public Collection getPdepts(int clientassetId, int type)
    {
        Collection coll = new LinkedList();
        if(type == 2)
            coll.add(new CompetencyprioritiesInfo(-1, "Select Department"));
        else if(type == 1)
            coll.add(new CompetencyprioritiesInfo(-1, "All"));
        int assettypeId = 0;
        if(clientassetId > 0)
        {
            String query = ("SELECT i_assettypeid FROM t_clientasset WHERE i_clientassetid = ?");
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                print(this, "pstmt ::: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    assettypeId = rs.getInt(1);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(assettypeId > 0)
        {
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
                        coll.add(new CompetencyprioritiesInfo(jobj.optInt("id"), jobj.optString("name")));
                    }
                }
            }
        }
        return coll;
    }
    
    public String getPrioritDegreeById(int key) {
        String s = "";
        switch (key) {
            case 1:
                s = "High";
                break;
            case 2:
                s = "Medium";
                break;
            case 3:
                s = "Low";
                break;
            default:
                break;
        }
        return s;
    }
}
