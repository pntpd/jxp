package com.web.jxp.policy;

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
import java.util.logging.Logger;

public class Policy extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getPolicyByName(String search, int next, int count, int clientIndex, int assetIndex,
            int allclient, String permission, String cids, String assetids) {
        ArrayList policys = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_policy.i_policyid, t_client.s_name, t_clientasset.s_name, t_policy.s_name, t_policy.i_status ");
        sb.append("FROM t_policy ");
        sb.append("LEFT JOIN t_client ON (t_policy.i_clientid = t_client.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_policy.i_clientassetid) ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_policy.s_name LIKE ? OR  t_clientasset.s_name LIKE ? OR t_client.s_name LIKE ?) ");
        }
        if (allclient == 1) {
            sb.append("AND t_client.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND t_client.i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND t_client.i_clientid < 0 ");
            }
            if (!assetids.equals("")) {
                sb.append("AND t_clientasset.i_clientassetid IN (" + assetids + ") ");
            }
        }
        if (clientIndex > 0) {
            sb.append("AND t_client.i_clientid =? ");
        }
        if (assetIndex > 0) {
            sb.append("AND t_clientasset.i_clientassetid =? ");
        }
        sb.append("ORDER BY t_policy.i_status, t_client.s_name, t_policy.s_name ");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }

        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1) FROM t_policy ");
        sb.append("LEFT JOIN t_client ON (t_policy.i_clientid = t_client.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_policy.i_clientassetid) ");
        sb.append("WHERE 0=0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_policy.s_name LIKE ? OR  t_clientasset.s_name LIKE ? OR t_client.s_name LIKE ?) ");
        }
        if (allclient == 1) {
            sb.append("AND t_client.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND t_client.i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND t_client.i_clientid < 0 ");
            }
            if (!assetids.equals("")) {
                sb.append("AND t_clientasset.i_clientassetid IN (" + assetids + ") ");
            }
        }
        if (clientIndex > 0) {
            sb.append("AND t_client.i_clientid =? ");
        }
        if (assetIndex > 0) {
            sb.append("AND t_clientasset.i_clientassetid =? ");
        }
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
            if (clientIndex > 0) {
                pstmt.setInt(++scc, clientIndex);
            }
            if (assetIndex > 0) {
                pstmt.setInt(++scc, assetIndex);
            }
            logger.info("getPolicyByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, clientName, assetName;
            int policyId, status;
            while (rs.next()) {
                policyId = rs.getInt(1);
                clientName = rs.getString(2) != null ? rs.getString(2) : "";
                assetName = rs.getString(3) != null ? rs.getString(3) : "";
                name = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);

                policys.add(new PolicyInfo(policyId, clientName, assetName, name, status, 0));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (clientIndex > 0) {
                pstmt.setInt(++scc, clientIndex);
            }
            if (assetIndex > 0) {
                pstmt.setInt(++scc, assetIndex);
            }
            print(this, "getPolicyByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                policyId = rs.getInt(1);
                policys.add(new PolicyInfo(policyId, "", "", "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return policys;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        PolicyInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (PolicyInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            PolicyInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (PolicyInfo) l.get(i);
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
    public String getInfoValue(PolicyInfo info, String i) {
        String infoval = "";

        if (i != null && i.equals("1")) {
            infoval = info.getClientName() != null ? info.getClientName() : "";
        }
        if (i != null && i.equals("2")) {
            infoval = info.getAssetName() != null ? info.getAssetName() : "";
        }
        if (i != null && i.equals("3")) {
            infoval = info.getPolicyName() != null ? info.getPolicyName() : "";
        }

        return infoval;
    }

    public PolicyInfo getPolicyDetailById(int policyId) 
    {
        PolicyInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_clientid, i_clientassetid, i_status, s_filename FROM t_policy WHERE i_policyid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, policyId);
            print(this, "getPolicyDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, filename;
            int status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                int clientId = rs.getInt(2);
                int assetId = rs.getInt(3);
                status = rs.getInt(4);
                filename = rs.getString(5) != null ? rs.getString(5): "";
                info = new PolicyInfo(policyId, name, clientId, assetId, status, 0, filename);
            }
        } catch (Exception exception) {
            print(this, "getPolicyDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public PolicyInfo getPolicyDetailByIdforDetail(int policyId)
    {
        PolicyInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_policy.s_name,t_policy.i_status, t_client.s_name , t_clientasset.s_name, t_policy.s_filename  ");
        sb.append("FROM t_policy ");
        sb.append("LEFT JOIN t_client ON (t_policy.i_clientid = t_client.i_clientid) ");
        sb.append("LEFT JOIN  t_clientasset ON (t_clientasset.i_clientassetid = t_policy.i_clientassetid) ");
        sb.append("WHERE i_policyid = ?");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, policyId);
            print(this, "getPolicyDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status = 0;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2);
                String clientName = rs.getString(3) != null ? rs.getString(3): "";
                String assetName = rs.getString(4) != null ? rs.getString(4): "";
                String fileName = rs.getString(5) != null ? rs.getString(5): "";
                info = new PolicyInfo(policyId, name, status, clientName, assetName, fileName);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getPolicyDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createPolicy(PolicyInfo info) 
    {
        int policyId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_policy ");
            sb.append("(s_name, i_clientid, i_clientassetid, s_filename, i_status,  i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ? )");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getPolicyName()));
            pstmt.setInt(++scc, info.getClientId());
            pstmt.setInt(++scc, info.getAssetId());
            pstmt.setString(++scc, (info.getFileName()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "createPolicy :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                policyId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createPolicy :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return policyId;
    }

    public int updatePolicy(PolicyInfo info) 
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_policy SET ");
            sb.append("i_clientid = ?, ");
            sb.append("i_clientassetid = ?, ");
            sb.append("s_name = ?, ");
            if(info.getFileName()!= null && !info.getFileName().equals(""))
                sb.append("s_filename = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_policyid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, info.getClientId());
            pstmt.setInt(++scc, info.getAssetId());
            pstmt.setString(++scc, info.getPolicyName());
            if(info.getFileName()!= null && !info.getFileName().equals(""))
                pstmt.setString(++scc, info.getFileName());                
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getPolicyId());
            print(this, "updatePolicy :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updatePolicy :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int policyId, String name, int clientId) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_policyid FROM t_policy WHERE i_clientid = ?  AND s_name = ? AND i_status IN (1, 2)");
        if (policyId > 0) {
            sb.append(" AND i_policyid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientId);
            pstmt.setString(++scc, name);
            if (policyId > 0) {
                pstmt.setInt(++scc, policyId);
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

//    public Collection getClients() {
//        Collection coll = new LinkedList();
//        String query = ("SELECT i_clientid, s_name FROM t_client WHERE i_status = 1 ORDER BY s_name").intern();
//        coll.add(new PolicyInfo(-1, "- Select -"));
//        try 
//        {
//            conn = getConnection();
//            pstmt = conn.prepareStatement(query);
//            rs = pstmt.executeQuery();
//            int refId;
//            String refName;
//            while (rs.next())
//            {
//                refId = rs.getInt(1);
//                refName = rs.getString(2) != null ? rs.getString(2): "";
//                coll.add(new PolicyInfo(refId, refName));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            close(conn, pstmt, rs);
//        }
//        return coll;
//    }

    public Collection getAssets() {
        Collection coll = new LinkedList();
        String query = ("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_status = 1 ORDER BY s_name").intern();
        coll.add(new PolicyInfo(-1, "- Select -"));
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
                coll.add(new PolicyInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public int deletePolicy(int policyId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_policy SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_policyid = ? ");
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
            pstmt.setInt(++scc, policyId);

            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deletePolicy :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Updated", 95, policyId);
        return cc;
    }

    public ArrayList getExcel(String search, int allclient, String permission, String cids, String assetids, int clientIndex, int assetIndex) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_policy.i_policyid, t_client.s_name, t_clientasset.s_name, t_policy.s_name, t_policy.i_status ");
        sb.append("FROM t_policy ");
        sb.append("LEFT JOIN t_client ON (t_policy.i_clientid = t_client.i_clientid) ");
        sb.append("LEFT JOIN  t_clientasset ON (t_clientasset.i_clientassetid = t_policy.i_clientassetid) ");
        sb.append("WHERE 0=0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_policy.s_name LIKE ? OR t_clientasset.s_name LIKE ? OR t_client.s_name LIKE ?) ");
        }
        if (allclient == 1) {
            sb.append("AND t_client.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND t_client.i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND t_client.i_clientid < 0 ");
            }
        }
        if (clientIndex > 0) {
            sb.append("AND t_client.i_clientid =? ");
        }
        if (assetIndex > 0) {
            sb.append("AND t_clientasset.i_clientassetid =? ");
        }
        sb.append("ORDER BY t_policy.i_status, t_client.s_name, t_policy.s_name ");

        String query = sb.toString().intern();
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
             if (clientIndex > 0) {
                pstmt.setInt(++scc, clientIndex);
            }
            if (assetIndex > 0) {
                pstmt.setInt(++scc, assetIndex);
            }
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                int policyId = rs.getInt(1);
                String clientName = rs.getString(2) != null ? rs.getString(2) : "";
                String assetName = rs.getString(3) != null ? rs.getString(3) : "";
                String issuesAuthority = rs.getString(4) != null ? rs.getString(4) : "";
                int status = rs.getInt(5);

                list.add(new PolicyInfo(policyId, clientName, assetName, issuesAuthority, status));
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

//    public Collection getPolicys(int doctypeId) 
//    {
//        Collection coll = new LinkedList();
//        if (doctypeId > 0)
//        {
//            String query = ("SELECT i_policyid, s_name FROM t_policy where i_status = 1 and i_clientassetid = ? order by s_name").intern();
//            coll.add(new PolicyInfo(-1, "- Select -"));
//            try 
//            {
//                conn = getConnection();
//                pstmt = conn.prepareStatement(query);
//
//                pstmt.setInt(1, doctypeId);
//                rs = pstmt.executeQuery();
//                int refId;
//                String refName;
//                while (rs.next()) 
//                {
//                    refId = rs.getInt(1);
//                    refName = rs.getString(2) != null ? rs.getString(2): "";
//                    coll.add(new PolicyInfo(refId, refName));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                close(conn, pstmt, rs);
//            }
//        } else {
//            coll.add(new PolicyInfo(-1, "- Select -"));
//        }
//        return coll;
//    }

    public Collection getClients(String cids, int allclient, String permission) {
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
        coll.add(new PolicyInfo(-1, " All "));
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
                coll.add(new PolicyInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getClientAsset(int clientId, String assetids, int allclient, String permission) {
        Collection coll = new LinkedList();
        if (clientId > 0) {
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
            coll.add(new PolicyInfo(-1, " All "));
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
                    coll.add(new PolicyInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new PolicyInfo(-1, " All "));
        }
        return coll;
    }
    
    public Collection getClientsForAddEdit(String cids, int allclient, String permission) {
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
        coll.add(new PolicyInfo(-1, " Select Client"));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            logger.info("getClientsForAddEdit :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new PolicyInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getClientAssetForAddEdit(int clientId, String assetids, int allclient, String permission) {
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
            coll.add(new PolicyInfo(-1, " Select Asset "));
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                logger.info("getClientAssetForAddEdit :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) 
                {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2): "";
                    coll.add(new PolicyInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new PolicyInfo(-1, " Select Asset "));
        }
        return coll;
    }
}
