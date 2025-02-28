package com.web.jxp.doccheck;

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
import org.json.JSONArray;
import org.json.JSONObject;

public class Doccheck extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getDoccheckByName(String search, int next, int count, int clientIndex, int assetIndex,
            int allclient, String permission, String cids, String assetids) {
        ArrayList docchecks = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_doccheck.i_doccheckid, t_client.s_name, t_clientasset.s_name, t_doccheck.s_name, t_doccheck.i_status FROM t_doccheck ");
        sb.append("LEFT JOIN t_client ON t_doccheck.i_clientid = t_client.i_clientid ");
        sb.append("LEFT JOIN t_clientasset ON t_clientasset.i_clientassetid = t_doccheck.i_clientassetid ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_doccheck.s_name LIKE ? OR  t_clientasset.s_name LIKE ? OR t_client.s_name LIKE ?) ");
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
        sb.append("ORDER BY t_doccheck.i_status, t_client.s_name, t_doccheck.s_name ");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }

        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1) FROM t_doccheck ");
        sb.append("LEFT JOIN t_client ON t_doccheck.i_clientid = t_client.i_clientid ");
        sb.append("LEFT JOIN t_clientasset ON t_clientasset.i_clientassetid = t_doccheck.i_clientassetid ");
        sb.append("WHERE 0=0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_doccheck.s_name LIKE ? OR  t_clientasset.s_name LIKE ? OR t_client.s_name LIKE ?) ");
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
            logger.info("getDoccheckByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, clientName, assetName;
            int doccheckId, status;
            while (rs.next()) {
                doccheckId = rs.getInt(1);
                clientName = rs.getString(2) != null ? rs.getString(2) : "";
                assetName = rs.getString(3) != null ? rs.getString(3) : "";
                name = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);

                docchecks.add(new DoccheckInfo(doccheckId, clientName, assetName, name, status, 0));
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
            print(this, "getDoccheckByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                doccheckId = rs.getInt(1);
                docchecks.add(new DoccheckInfo(doccheckId, "", "", "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return docchecks;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        DoccheckInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (DoccheckInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            DoccheckInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (DoccheckInfo) l.get(i);
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
    public String getInfoValue(DoccheckInfo info, String i) {
        String infoval = "";

        if (i != null && i.equals("1")) {
            infoval = info.getClientName() != null ? info.getClientName() : "";
        }
        if (i != null && i.equals("2")) {
            infoval = info.getAssetName() != null ? info.getAssetName() : "";
        }
        if (i != null && i.equals("3")) {
            infoval = info.getDoccheckName() != null ? info.getDoccheckName() : "";
        }

        return infoval;
    }

    public DoccheckInfo getDoccheckDetailById(int doccheckId) {
        DoccheckInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_clientid, i_clientassetid, i_status FROM t_doccheck where i_doccheckid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, doccheckId);
            print(this, "getDoccheckDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2);
                int clientId = rs.getInt(3);
                int assetId = rs.getInt(4);
                info = new DoccheckInfo(doccheckId, name, status, clientId, assetId, 0);
            }
        } catch (Exception exception) {
            print(this, "getDoccheckDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public DoccheckInfo getDoccheckDetailByIdforDetail(int doccheckId) {
        DoccheckInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_doccheck.s_name,t_doccheck.i_status, t_client.s_name , t_clientasset.s_name  ");
        sb.append("FROM t_doccheck ");
        sb.append("LEFT JOIN t_client ON (t_doccheck.i_clientid = t_client.i_clientid) ");
        sb.append("LEFT JOIN  t_clientasset ON (t_clientasset.i_clientassetid = t_doccheck.i_clientassetid) ");
        sb.append("WHERE i_doccheckid = ?");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, doccheckId);
            print(this, "getDoccheckDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status = 0;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2);
                String clientName = rs.getString(3) != null ? rs.getString(3): "";
                String assetName = rs.getString(4) != null ? rs.getString(4): "";

                info = new DoccheckInfo(doccheckId, name, status, clientName, assetName, 0);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getDoccheckDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createDoccheck(DoccheckInfo info) 
    {
        int doccheckId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_doccheck ");
            sb.append("(s_name, i_clientid, i_clientassetid, i_status,  i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ? )");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getDoccheckName()));
            pstmt.setInt(++scc, info.getClientId());
            pstmt.setInt(++scc, info.getAssetId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "createDoccheck :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                doccheckId = rs.getInt(1);
            }
            createjson(conn);
        } catch (Exception exception) {
            print(this, "createDoccheck :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return doccheckId;
    }

    public int updateDoccheck(DoccheckInfo info) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_doccheck set ");
            sb.append("s_name = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ?, ");
            sb.append("i_clientid = ?, ");
            sb.append("i_clientassetid = ? ");
            sb.append("where i_doccheckid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getDoccheckName());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getClientId());
            pstmt.setInt(++scc, info.getAssetId());
            pstmt.setInt(++scc, info.getDoccheckId());
            print(this, "updateDoccheck :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "updateDoccheck :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int doccheckId, String name, int clientId, int assetId) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_doccheckid FROM t_doccheck where i_clientid = ? and i_clientassetid = ? and s_name = ? and i_status in (1, 2)");
        if (doccheckId > 0) {
            sb.append(" and i_doccheckid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientId);
            pstmt.setInt(++scc, assetId);
            pstmt.setString(++scc, name);
            if (doccheckId > 0) {
                pstmt.setInt(++scc, doccheckId);
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

    public Collection getClients() {
        Collection coll = new LinkedList();
        String query = ("SELECT i_clientid, s_name FROM t_client where i_status = 1 order by s_name").intern();
        coll.add(new DoccheckInfo(-1, "- Select -"));
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
                coll.add(new DoccheckInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getAssets() {
        Collection coll = new LinkedList();
        String query = ("SELECT i_clientassetid, s_name FROM t_clientasset where i_status = 1 order by s_name").intern();
        coll.add(new DoccheckInfo(-1, "- Select -"));
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
                coll.add(new DoccheckInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public int deleteDoccheck(int doccheckId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_doccheck set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_doccheckid = ? ");
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
            pstmt.setInt(++scc, doccheckId);

            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "deleteDoccheck :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Updated", 58, doccheckId);
        return cc;
    }

    public ArrayList getExcel(String search, int allclient, String permission, String cids, String assetids, int clientIndex, int assetIndex) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_doccheck.i_doccheckid, t_client.s_name, t_clientasset.s_name, t_doccheck.s_name, t_doccheck.i_status FROM t_doccheck ");
        sb.append("LEFT JOIN t_client ON (t_doccheck.i_clientid = t_client.i_clientid) ");
        sb.append("LEFT JOIN  t_clientasset ON (t_clientasset.i_clientassetid = t_doccheck.i_clientassetid) ");
        sb.append("WHERE 0=0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_doccheck.s_name LIKE ? OR t_clientasset.s_name LIKE ? OR t_client.s_name LIKE ?) ");
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
        sb.append("ORDER BY t_doccheck.i_status, t_client.s_name, t_doccheck.s_name ");

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
                int doccheckId = rs.getInt(1);
                String clientName = rs.getString(2) != null ? rs.getString(2) : "";
                String assetName = rs.getString(3) != null ? rs.getString(3) : "";
                String issuesAuthority = rs.getString(4) != null ? rs.getString(4) : "";
                int status = rs.getInt(5);

                list.add(new DoccheckInfo(doccheckId, clientName, assetName, issuesAuthority, status));
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

    public void createjson(Connection conn) {
        String query = ("Select i_clientid, i_clientassetid, i_doccheckid, s_name FROM t_doccheck where i_status = 1 order by  s_name ");

        try 
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int clientid;
            int assetid;
            int doccheckid;
            String name;
            JSONArray jarr = new JSONArray();
            while (rs.next()) 
            {
                clientid = rs.getInt(1);
                doccheckid = rs.getInt(2);
                assetid = rs.getInt(3);
                name = rs.getString(4) != null ? rs.getString(4): "";
                JSONObject jobj = new JSONObject();
                jobj.put("clientid", clientid);
                jobj.put("doccheckid", doccheckid);
                jobj.put("assetid", assetid);
                jobj.put("name", name);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "doccheck.json");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, pstmt, rs);
        }
    }

    public Collection getDocchecks(int doctypeId) 
    {
        Collection coll = new LinkedList();
        if (doctypeId > 0)
        {
            String query = ("SELECT i_doccheckid, s_name FROM t_doccheck where i_status = 1 and i_clientassetid = ? order by s_name").intern();
            coll.add(new DoccheckInfo(-1, "- Select -"));
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);

                pstmt.setInt(1, doctypeId);
                rs = pstmt.executeQuery();
                int refId;
                String refName;
                while (rs.next()) 
                {
                    refId = rs.getInt(1);
                    refName = rs.getString(2) != null ? rs.getString(2): "";
                    coll.add(new DoccheckInfo(refId, refName));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new DoccheckInfo(-1, "- Select -"));
        }
        return coll;
    }

    public Collection getClients(String cids, int allclient, String permission) {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_clientid, s_name FROM t_client where i_status = 1 ");
        if (allclient == 1) {
            sb.append("AND i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND i_clientid < 0 ");
            }
        }

        sb.append("order by s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new DoccheckInfo(-1, " All "));
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
                coll.add(new DoccheckInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
    
    public Collection getClientsForAddEdit(String cids, int allclient, String permission) {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_clientid, s_name FROM t_client where i_status = 1 ");
        if (allclient == 1) {
            sb.append("AND i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND i_clientid < 0 ");
            }
        }

        sb.append("order by s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new DoccheckInfo(-1, " Select Client "));
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
                coll.add(new DoccheckInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    // Asset
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
            coll.add(new DoccheckInfo(-1, " All "));
            try {
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
                    coll.add(new DoccheckInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new DoccheckInfo(-1, " All "));
        }
        return coll;
    }
    
    public Collection getClientAssetForAddEdit(int clientId, String assetids, int allclient, String permission) {
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
            coll.add(new DoccheckInfo(-1, " Select Asset "));
            try {
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
                    coll.add(new DoccheckInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new DoccheckInfo(-1, " Select Asset "));
        }
        return coll;
    }
}
