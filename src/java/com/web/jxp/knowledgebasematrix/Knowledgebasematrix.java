package com.web.jxp.knowledgebasematrix;

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

public class Knowledgebasematrix extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getKnowledgebasematrixByName(String search, int next, int count, int allclient,
            String permission, String cids, String assetids, int clientIdIndex, int assetIdIndex) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.i_clientassetid, t_clientasset.i_clientid, t_client.s_name, t_clientasset.s_name, ");
        sb.append("t1.ct, t_clientasset.i_status FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, COUNT(1) AS ct FROM t_clientassetposition WHERE i_status = 1 GROUP BY i_clientassetid) AS t1 ON (t_clientasset.i_clientassetid = t1.i_clientassetid) ");
        sb.append("WHERE t_clientasset.i_status = 1 AND t_client.i_status = 1 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ?) ");
        }
        if (allclient == 1) {
            sb.append(" AND t_client.i_clientid > 0 ");
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
        if (clientIdIndex > 0) {
            sb.append("AND t_client.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND t_clientasset.i_clientassetid = ? ");
        }
        sb.append("ORDER BY t_client.s_name, t_clientasset.s_name ");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1) FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, COUNT(1) AS ct FROM t_clientassetposition WHERE i_status = 1 GROUP BY i_clientassetid) AS t1 ON (t_clientasset.i_clientassetid = t1.i_clientassetid) ");
        sb.append("WHERE t_clientasset.i_status = 1 AND t_client.i_status = 1 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ?) ");
        }
        if (allclient == 1) {
            sb.append(" AND t_client.i_clientid > 0 ");
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
        if (clientIdIndex > 0) {
            sb.append("AND t_client.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND t_clientasset.i_clientassetid = ? ");
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
            }
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            logger.info("getKnowledgebasematrixByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAssetName;
            int pcount, clientId, assetId;
            while (rs.next())
            {
                assetId = rs.getInt(1);
                clientId = rs.getInt(2);
                clientName = rs.getString(3) != null ? rs.getString(3) : "";
                clientAssetName = rs.getString(4) != null ? rs.getString(4) : "";
                pcount = rs.getInt(5);
                list.add(new KnowledgebasematrixInfo(clientId, assetId, clientName, clientAssetName, pcount, 0));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            print(this, "getKnowledgebasematrixByNameCount :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                clientId = rs.getInt(1);
                list.add(new KnowledgebasematrixInfo(clientId, 0, "", "", 0, 0));
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
        KnowledgebasematrixInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try 
        {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (KnowledgebasematrixInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = null;
            if (colId.equals("3")) {
                map = sortById(record, tp);
            } else {
                map = sortByName(record, tp);
            }
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            KnowledgebasematrixInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (KnowledgebasematrixInfo) l.get(i);
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
    public String getInfoValue(KnowledgebasematrixInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getClientName() != null ? info.getClientName() : "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getClientAssetName() != null ? info.getClientAssetName() : "";
        } else if (i != null && i.equals("3")) {
            infoval = "" + info.getPcount();
        }
        return infoval;
    }

    public ArrayList getFinalRecordposition(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        KnowledgebasematrixInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try
        {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (KnowledgebasematrixInfo) l.get(i);
                    record.put(getInfoValueposition(info, colId), info);
                }
            }
            Map map = null;
            if (colId.equals("1")) {
                map = sortByName(record, tp);
            } else {
                map = sortById(record, tp);
            }
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            KnowledgebasematrixInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (KnowledgebasematrixInfo) l.get(i);
                    String str = getInfoValueposition(rInfo, colId);
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
    public String getInfoValueposition(KnowledgebasematrixInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getName() + "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getCount1() + "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getCount2() + "";
        } else if (i != null && i.equals("4")) {
            infoval = "" + info.getCount3();
        }
        return infoval;
    }

    public KnowledgebasematrixInfo getKnowledgebasematrixDetailByIdforDetail(int clientId, int assetId)
    {
        KnowledgebasematrixInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.i_clientassetid, t_clientasset.i_clientid, t_client.s_name, t_clientasset.s_name, ");
        sb.append("t1.ct, t_clientasset.i_status, t_clientasset.i_assettypeid FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, COUNT(1) AS ct FROM t_clientassetposition WHERE i_status =1 GROUP BY i_clientassetid) AS t1 ON (t_clientasset.i_clientassetid = t1.i_clientassetid) ");
        sb.append("WHERE t_clientasset.i_status =1 AND t_clientasset.i_clientassetid =?  ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assetId);
            print(this, "getKnowledgebasematrixDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAssetName;
            int pcount;
            while (rs.next()) 
            {
                assetId = rs.getInt(1);
                clientId = rs.getInt(2);
                clientName = rs.getString(3) != null ? rs.getString(3) : "";
                clientAssetName = rs.getString(4) != null ? rs.getString(4) : "";
                pcount = rs.getInt(5);
                int assettypeId = rs.getInt(6);
                info = new KnowledgebasematrixInfo(clientId, assetId, clientName, clientAssetName, pcount, assettypeId);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getKnowledgebasematrixDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createKnowledgebasematrix(KnowledgebasematrixInfo info)
    {
        int wellnessmatrixId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_knowledgematrix ");
            sb.append("(s_name, i_assettypeid, s_description, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            //print(this,"createKnowledgebasematrix :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                wellnessmatrixId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createKnowledgebasematrix :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return wellnessmatrixId;
    }

    public int updateKnowledgebasematrix(KnowledgebasematrixInfo info)
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_knowledgematrix SET ");
            sb.append("s_name =?, ");
            sb.append("s_description =?, ");
            sb.append("i_assettypeid =?, ");
            sb.append("i_status =?, ");
            sb.append("i_userid =?, ");
            sb.append("ts_moddate =? ");
            sb.append("WHERE i_knowledgematrixid =? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getKnowledgebasematrixId());
            //print(this,"updateKnowledgebasematrix :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateKnowledgebasematrix :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int wellnessmatrixId, String name, int assettypeId) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_knowledgematrixid FROM t_knowledgematrix WHERE (s_name =? OR i_assettypeid =?) AND i_status IN (1, 2) ");
        if (wellnessmatrixId > 0) {
            sb.append("AND i_knowledgematrixid !=? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, name);
            pstmt.setInt(++scc, assettypeId);
            if (wellnessmatrixId > 0) {
                pstmt.setInt(++scc, wellnessmatrixId);
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

    public int deleteKnowledgebasematrix(int wellnessmatrixId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_knowledgematrix SET ");
            sb.append("i_status =?, ");
            sb.append("i_userid =?, ");
            sb.append("ts_moddate =? ");
            sb.append("WHERE i_knowledgematrixid =? ");
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
            pstmt.setInt(++scc, wellnessmatrixId);

            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteKnowledgebasematrix :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 64, wellnessmatrixId);
        return cc;
    }

    public ArrayList getListForExcel(String search, int allclient, String permission, String cids, String assetids, int clientIdIndex, int assetIdIndex)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.i_clientassetid, t_clientasset.i_clientid, t_client.s_name, t_clientasset.s_name, t1.ct, t_clientasset.i_status ");
        sb.append("FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, COUNT(1) AS ct FROM t_clientassetposition WHERE i_status = 1 GROUP BY i_clientassetid) AS t1 ON (t_clientasset.i_clientassetid = t1.i_clientassetid) ");
        sb.append("WHERE t_clientasset.i_status = 1 AND t_client.i_status = 1 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ?) ");
        }
        if (allclient == 1) {
            sb.append(" AND t_client.i_clientid > 0 ");
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
        if (clientIdIndex > 0) {
            sb.append("AND t_client.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND t_clientasset.i_clientassetid = ? ");
        }
        sb.append("ORDER BY t_client.s_name, t_clientasset.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);

        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            logger.info("getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAssetName;
            int pcount, clientId, assetId;
            while (rs.next()) 
            {
                assetId = rs.getInt(1);
                clientId = rs.getInt(2);
                clientName = rs.getString(3) != null ? rs.getString(3) : "";
                clientAssetName = rs.getString(4) != null ? rs.getString(4) : "";
                pcount = rs.getInt(5);
                list.add(new KnowledgebasematrixInfo(clientId, assetId, clientName, clientAssetName, pcount, 0));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getListForExcel1(int assetIdIndex)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_position.s_name, t_grade.s_name, t_kcategory.s_name, t_ksubcategory.s_name, t_topic.s_name FROM t_knowledgematrix ");
        sb.append("LEFT JOIN t_kcategory ON (t_kcategory.i_kcategoryid = t_knowledgematrix.i_categorykid) ");
        sb.append("LEFT JOIN t_ksubcategory ON (t_ksubcategory.i_ksubcategoryid = t_knowledgematrix.i_subcategorykid) ");
        sb.append("LEFT JOIN t_topic ON (t_topic.i_topicid = t_knowledgematrix.i_topicid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_knowledgematrix.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("WHERE 0=0 ");
        if (assetIdIndex > 0) {
            sb.append("AND i_clientassetid =? ");
        }
        sb.append("ORDER BY t_position.s_name, t_grade.s_name, t_kcategory.s_name, t_ksubcategory.s_name, t_topic.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);

        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            logger.info("getListForExcel1 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String position, grade, category, subcategory, topic;
            while (rs.next()) 
            {
                position = rs.getString(1) != null ? rs.getString(1) : "";
                grade = rs.getString(2) != null ? rs.getString(2) : "";
                category = rs.getString(3) != null ? rs.getString(3) : "";
                subcategory = rs.getString(4) != null ? rs.getString(4) : "";
                topic = rs.getString(5) != null ? rs.getString(5) : "";
                list.add(new KnowledgebasematrixInfo(position, grade, category, subcategory, topic));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int getMaxId() {
        int maxId = 0;
        String query = ("SELECT MAX(i_knowledgematrixid) FROM t_knowledgematrix");
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            while (rs.next()) {
                maxId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "getMaxId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return maxId + 1;
    }

    public ArrayList getPositionList(int clientId, int assetId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_position.i_positionid, t_position.s_name, t1.ct1, t1.ct2, t1.ct3, t_grade.s_name, t2.ct1 ");
        sb.append("FROM t_clientassetposition ");
        sb.append("LEFT JOIN t_position ON (t_clientassetposition.i_positionid = t_position.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN (SELECT i_positionid, t_knowledgematrix.i_status, COUNT(DISTINCT(t_knowledgematrix.i_categorykid)) AS ct1, COUNT(DISTINCT(t_knowledgematrix.i_subcategorykid)) AS ct2, COUNT(DISTINCT(i_topicid)) AS ct3 ");
        sb.append("FROM t_knowledgematrix LEFT JOIN t_kcategory ON (t_kcategory.i_kcategoryid = t_knowledgematrix.i_categorykid) LEFT JOIN t_ksubcategory ON (t_ksubcategory.i_ksubcategoryid = t_knowledgematrix.i_subcategorykid) ");
        sb.append("WHERE i_clientassetid = ? AND t_kcategory.i_status = 1 AND t_ksubcategory.i_status = 1 GROUP BY i_positionid) AS t1 ON (t1.i_positionid = t_clientassetposition.i_positionid )");
        sb.append("LEFT JOIN (select i_positionid, count(DISTINCT(i_categorykid)) AS ct1 FROM t_knowledgematrix WHERE i_status =1 GROUP BY i_positionid) AS t2 ON (t2.i_positionid = t_clientassetposition.i_positionid) ");
        sb.append("WHERE t_position.i_status =1 AND t_clientassetposition.i_clientassetid = ?");

        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assetId);
            pstmt.setInt(2, assetId);
            logger.info("getPositionList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, gradeName;
            int count1, count2, count3, positionId, statusCount;
            while (rs.next()) 
            {
                positionId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                count1 = rs.getInt(3);
                count2 = rs.getInt(4);
                count3 = rs.getInt(5);
                gradeName = rs.getString(6) != null ? rs.getString(6) : "";
                statusCount = rs.getInt(7);
                if (!gradeName.equals("")) {
                    name += " - " + gradeName;
                }
                list.add(new KnowledgebasematrixInfo(clientId, name, count1, count2, count3, positionId, assetId, statusCount));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public KnowledgebasematrixInfo getPositionInfo(int positionId) 
    {
        KnowledgebasematrixInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_position.s_name, t_grade.s_name FROM t_position ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("WHERE t_position.i_positionid =?");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, positionId);
            logger.info("getPositionInfo :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, gradeName;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                gradeName = rs.getString(2) != null ? rs.getString(2) : "";
                if (!gradeName.equals("")) 
                {
                    name += " - " + gradeName;
                }
                info = new KnowledgebasematrixInfo(0, name);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public Collection getCategories(int assettypeids) 
    {
        Collection coll = new LinkedList();
        coll.add(new KnowledgebasematrixInfo(-1, "Select Category"));
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT(t_topic.i_kcategoryid), t_kcategory.s_name FROM t_topic ");
        sb.append("LEFT JOIN t_kcategory ON (t_kcategory.i_kcategoryid = t_topic.i_kcategoryid) ");
        sb.append("WHERE find_in_set(?, t_topic.s_assettypeids) AND t_kcategory.i_status =1 AND t_topic.i_status =1 ORDER BY t_kcategory.s_name ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assettypeids);
            logger.info("getCategories :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next())
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new KnowledgebasematrixInfo(id, name));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public ArrayList getSubCategoryList(int categoryId) 
    {
        ArrayList list = new ArrayList();
        String query = ("SELECT i_ksubcategoryid, s_name FROM t_ksubcategory WHERE i_status =1 AND i_kcategoryid =? ORDER BY s_name");
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            logger.info("getSubCategoryList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next())
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                list.add(new KnowledgebasematrixInfo(id, name));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getQuestList(int clientId, int assetId, int positionId, int categoryId, int assettypeId)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT d.i_knowledgematrixid, t_topic.s_name, t_topic.i_ksubcategoryid, t_topic.i_topicid, t1.ct ");
        sb.append("FROM t_topic ");
        sb.append("LEFT JOIN t_knowledgematrix AS d ON (t_topic.i_topicid = d.i_topicid AND d.i_positionid = ? AND d.i_categorykid = ? AND d.i_clientid = ? AND d.i_clientassetid = ?) ");
        sb.append("LEFT JOIN (SELECT i_topicid, COUNT(1) AS ct FROM t_topicattachment GROUP BY i_topicid) AS t1 ON (t1.i_topicid = t_topic.i_topicid) ");
        sb.append("WHERE t_topic.i_status =1 AND FIND_IN_SET(?, t_topic.s_assettypeids) ORDER BY t_topic.s_name ");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, positionId);
            pstmt.setInt(2, categoryId);
            pstmt.setInt(3, clientId);
            pstmt.setInt(4, assetId);
            pstmt.setInt(5, assettypeId);
            logger.info("getQuestList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int wellnessmatrixId, subcategoryid, questionId, count;
            while (rs.next()) 
            {
                wellnessmatrixId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                subcategoryid = rs.getInt(3);
                questionId = rs.getInt(4);
                count = rs.getInt(5);
                list.add(new KnowledgebasematrixInfo(name, wellnessmatrixId, categoryId, subcategoryid, questionId, count));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getListFromList(ArrayList list, int subcategoryId) {
        ArrayList l = new ArrayList();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            KnowledgebasematrixInfo info = (KnowledgebasematrixInfo) list.get(i);
            if (info != null && info.getSubcategoryId() == subcategoryId) {
                l.add(info);
            }
        }
        return l;
    }

    public boolean checkinlist(ArrayList list, int subcategoryId) {
        boolean b = false;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            KnowledgebasematrixInfo info = (KnowledgebasematrixInfo) list.get(i);
            if (info != null && info.getKnowledgebasematrixId() > 0 && info.getSubcategoryId() == subcategoryId) {
                b = true;
                break;
            }
        }
        return b;
    }

    public boolean checkinlistcat(ArrayList list, int categoryId) {
        boolean b = false;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            KnowledgebasematrixInfo info = (KnowledgebasematrixInfo) list.get(i);
            if (info != null && info.getKnowledgebasematrixId() > 0 && info.getCategoryId() == categoryId) {
                b = true;
                break;
            }
        }
        return b;
    }

    public String getCollection1(ArrayList list, int id) {
        int size = list.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            KnowledgebasematrixInfo info = (KnowledgebasematrixInfo) list.get(i);
            if (info != null && info.getDdlValue() == id) {
                sb.append("<option selected value='" + info.getDdlValue() + "'>" + (info.getDdlLabel() != null ? info.getDdlLabel() : "") + "</option>");
            } else {
                sb.append("<option value='" + info.getDdlValue() + "'>" + (info.getDdlLabel() != null ? info.getDdlLabel() : "") + "</option>");
            }
        }
        String str = sb.toString();
        sb.setLength(0);
        return str;
    }

    public int createKnowledgebasematrixDetail(int categoryId, int positionId, int subcategoryId[], int questionId[], int userId, int clientId, int assetId) {
        int wellnessmatrixId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_knowledgematrix ");
            sb.append("(i_clientid, i_clientassetid, i_positionid, i_categorykid, i_subcategorykid, i_topicid, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();

            String delquery = "DELETE FROM t_knowledgematrix WHERE i_positionid =? AND i_categorykid =? AND i_clientid =? AND i_clientassetid =?  ";
            pstmt = conn.prepareStatement(delquery);
            pstmt.setInt(1, positionId);
            pstmt.setInt(2, categoryId);
            pstmt.setInt(3, clientId);
            pstmt.setInt(4, assetId);
            print(this, "createKnowledgebasematrixDetail delete all :: " + pstmt.toString());
            pstmt.executeUpdate();

            int len = questionId.length;
            pstmt = conn.prepareStatement(query);
            for (int i = 0; i < len; i++) 
            {
                if (subcategoryId[i] > 0 && questionId[i] > 0) 
                {
                    int scc = 0;
                    pstmt.setInt(++scc, clientId);
                    pstmt.setInt(++scc, assetId);
                    pstmt.setInt(++scc, positionId);
                    pstmt.setInt(++scc, categoryId);
                    pstmt.setInt(++scc, subcategoryId[i]);
                    pstmt.setInt(++scc, questionId[i]);
                    pstmt.setInt(++scc, 1);
                    pstmt.setInt(++scc, userId);
                    pstmt.setString(++scc, currDate1());
                    pstmt.setString(++scc, currDate1());
                    print(this, "createKnowledgebasematrixDetail :: " + pstmt.toString());
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception exception) {
            print(this, "createKnowledgebasematrixDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return wellnessmatrixId;
    }

    public int deleteKnowledgebasematrixDetail(int clientId, int assetId, int positionId, int userId, int status) {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_knowledgematrix SET ");
            sb.append("i_status =?, ");
            sb.append("i_userid =?, ");
            sb.append("ts_moddate =? ");
            sb.append("WHERE i_positionid =? AND i_clientid =? AND i_clientassetid =? ");
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
            pstmt.setInt(++scc, positionId);
            pstmt.setInt(++scc, clientId);
            pstmt.setInt(++scc, assetId);
            print(this, "deleteKnowledgebasematrixDetail :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteKnowledgebasematrixDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

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

        sb.append("order by s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new KnowledgebasematrixInfo(-1, " Select Client"));
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
                coll.add(new KnowledgebasematrixInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getClientAsset(int clientId, String assetids, int allclient, String permission) 
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
            coll.add(new KnowledgebasematrixInfo(-1, " Select Asset "));
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
                    coll.add(new KnowledgebasematrixInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new KnowledgebasematrixInfo(-1, " Select Asset "));
        }
        return coll;
    }

}
