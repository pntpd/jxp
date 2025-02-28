package com.web.jxp.wellnessmatrix;

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

public class Wellnessmatrix extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getWellnessmatrixByName(String search, int next, int count, int allclient, 
            String permission, String cids, String assetids, int clientIdIndex, int assetIdIndex)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.i_clientassetid, t_clientasset.i_clientid, t_client.s_name, t_clientasset.s_name, ");
        sb.append("t1.ct, t_clientasset.i_status FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, COUNT(1) as ct FROM t_clientassetposition WHERE i_status = 1 GROUP BY i_clientassetid) as t1 on (t_clientasset.i_clientassetid = t1.i_clientassetid) ");
        sb.append("WHERE t_clientasset.i_status = 1 AND t_client.i_status = 1 ");
        if(search != null && !search.equals(""))
            sb.append("AND (t_client.s_name like ? OR t_clientasset.s_name like ?) ");        
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
        if(count > 0)
            sb.append(" LIMIT ").append(next*count).append(", ").append(count);
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("SELECT COUNT(1) FROM t_clientasset ");
        sb.append("LEFT JOIN t_client on (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("WHERE t_clientasset.i_status = 1 AND t_client.i_status = 1 ");
        if(search != null && !search.equals(""))
            sb.append("AND (t_client.s_name like ? OR t_clientasset.s_name like ?) ");
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
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            logger.info("getWellnessmatrixByName :: " + pstmt.toString());
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
                list.add(new WellnessmatrixInfo(clientId, assetId, clientName, clientAssetName, pcount, 0));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            print(this,"getWellnessmatrixByNameCount :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                clientId = rs.getInt(1);
                list.add(new WellnessmatrixInfo(clientId, 0, "", "", 0, 0));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        WellnessmatrixInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (WellnessmatrixInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = null;
            if ( colId.equals("3")) {
                map = sortById(record, tp);
            } else {
                map = sortByName(record, tp);
            }
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            WellnessmatrixInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (WellnessmatrixInfo) l.get(i);
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
    public String getInfoValue(WellnessmatrixInfo info, String i)
    {
        String infoval = "";
        if (i != null && i.equals("1")) 
        {
            infoval = info.getClientName() != null ? info.getClientName() : "";
        } else if (i != null && i.equals("2")) 
        {
            infoval = info.getClientAssetName() != null ? info.getClientAssetName() : "";
        } else if (i != null && i.equals("3"))
        {
            infoval = "" + info.getPcount();
        }
        return infoval;
    }
    
    public ArrayList getFinalRecordposition(ArrayList l, String colId, int tp)
    {
        ArrayList list = null;
        HashMap record = null;
        WellnessmatrixInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try 
        {
            if (total > 0) 
            {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) 
                {
                    info = (WellnessmatrixInfo) l.get(i);
                    record.put(getInfoValueposition(info, colId), info);
                }
            }
            Map map = null;
            if ( colId.equals("1")) {
                map = sortByName(record, tp);
            } else {
                map = sortById(record, tp);
            }
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            WellnessmatrixInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (WellnessmatrixInfo) l.get(i);
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
    public String getInfoValueposition(WellnessmatrixInfo info, String i)
    {
        String infoval = "";
        if (i != null && i.equals("1")) 
        {
            infoval = info.getName()!= null ? info.getName() : "";  
        }
        else if (i != null && i.equals("2")) 
        {
            infoval = info.getCount1()+"";
        } 
        else if (i != null && i.equals("3"))
        {
            infoval = info.getCount2()+"";
        } 
        else if (i != null && i.equals("4")) 
        {
            infoval = "" + info.getCount3();
        }
        return infoval;
    }

    public WellnessmatrixInfo getWellnessmatrixDetailByIdforDetail(int clientId,int assetId)
    {
        WellnessmatrixInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.i_clientassetid, t_clientasset.i_clientid, t_client.s_name, t_clientasset.s_name, ");
        sb.append("t1.ct, t_clientasset.i_status, t_clientasset.i_assettypeid FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, COUNT(1) as ct FROM t_clientassetposition WHERE i_status = 1 GROUP BY i_clientassetid) as t1 on (t_clientasset.i_clientassetid = t1.i_clientassetid) ");
        sb.append("WHERE t_clientasset.i_status = 1 and t_clientasset.i_clientassetid = ?  ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assetId);
            print(this, "getWellnessmatrixDetailByIdforDetail :: " + pstmt.toString());
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
                info = new WellnessmatrixInfo(clientId, assetId, clientName, clientAssetName, pcount, assettypeId);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getWellnessmatrixDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createWellnessmatrix(WellnessmatrixInfo info) 
    {
        int wellnessmatrixId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_wellnessmatrix ");
            sb.append("(s_name, i_assettypeid, s_description, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?)");
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
            //print(this,"createWellnessmatrix :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                wellnessmatrixId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createWellnessmatrix :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return wellnessmatrixId;
    }

    public int updateWellnessmatrix(WellnessmatrixInfo info) 
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_wellnessmatrix set ");
            sb.append("s_name = ?, ");
            sb.append("s_description = ?, ");
            sb.append("i_assettypeid = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_wellnessmatrixid = ? ");
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
            pstmt.setInt(++scc, info.getWellnessmatrixId());
            //print(this,"updateWellnessmatrix :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateWellnessmatrix :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int wellnessmatrixId, String name, int assettypeId) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_wellnessmatrixid FROM t_wellnessmatrix WHERE (s_name = ? OR i_assettypeid =?) AND i_status IN (1, 2) ");
        if (wellnessmatrixId > 0) {
            sb.append("AND i_wellnessmatrixid !=? ");
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

    public int deleteWellnessmatrix(int wellnessmatrixId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_wellnessmatrix set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_wellnessmatrixid = ? ");
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
            print(this, "deleteWellnessmatrix :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 64, wellnessmatrixId);
        return cc;
    }

    public ArrayList getListForExcel(int allclient, String cids, String assetids, int clientIdIndex, int assetIdIndex)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.i_clientassetid, t_clientasset.i_clientid, t_client.s_name, t_clientasset.s_name, ");
        sb.append("t1.ct, t_clientasset.i_status FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, COUNT(1) as ct FROM t_clientassetposition WHERE i_status = 1 GROUP BY i_clientassetid) as t1 on (t_clientasset.i_clientassetid = t1.i_clientassetid) ");
        sb.append("WHERE t_clientasset.i_status = 1 AND t_client.i_status = 1 ");
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
                list.add(new WellnessmatrixInfo(clientId, assetId, clientName, clientAssetName, pcount, 0));
            }
            rs.close();
        }
        catch (Exception e)
        {
            print(this, "getListForExcel :::" + e.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int getMaxId() {
        int maxId = 0;
        String query = ("SELECT MAX(i_wellnessmatrixid) FROM t_wellnessmatrix");
        try {
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

    public ArrayList getPositionList( int clientId, int assetId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_position.i_positionid, t_position.s_name, t1.ct1, t1.ct2, t1.ct3, t_grade.s_name, t2.ct1 ");
        sb.append("from t_clientassetposition left join t_position on (t_clientassetposition.i_positionid = t_position.i_positionid) ");
        sb.append("left join t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("left join (select i_positionid, t_wellnessmatrix.i_status, count(DISTINCT(t_wellnessmatrix.i_categorywfid)) as ct1, count(DISTINCT(t_wellnessmatrix.i_subcategorywfid)) as ct2, count(DISTINCT(i_questionid)) as ct3 ");
        sb.append("from t_wellnessmatrix left join t_categorywf on (t_categorywf.i_categorywfid = t_wellnessmatrix.i_categorywfid) left join t_subcategorywf on (t_subcategorywf.i_subcategorywfid = t_wellnessmatrix.i_subcategorywfid) ");
        sb.append("where i_clientassetid = ? and t_categorywf.i_status = 1 and t_subcategorywf.i_status = 1 group by i_positionid) as t1 on (t1.i_positionid = t_clientassetposition.i_positionid )");
        sb.append("left join (select i_positionid, count(DISTINCT(i_categorywfid)) as ct1 from t_wellnessmatrix where i_status = 1 group by i_positionid) as t2 on (t2.i_positionid = t_clientassetposition.i_positionid) ");
        sb.append("where t_position.i_status = 1 and t_clientassetposition.i_clientassetid = ?");
        
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
                if (!gradeName.equals("")) 
                {
                    name += " - " + gradeName;
                }
                list.add(new WellnessmatrixInfo(clientId, name, count1, count2, count3, positionId, assetId, statusCount));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public WellnessmatrixInfo getPositionInfo(int positionId) 
    {
        WellnessmatrixInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select t_position.s_name, t_grade.s_name ");
        sb.append("from t_position ");
        sb.append("left join t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("where t_position.i_positionid = ?");
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
                info = new WellnessmatrixInfo(0, name);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public Collection getCategories() {
        Collection coll = new LinkedList();
        coll.add(new WellnessmatrixInfo(-1, "Select Category"));
        String query = ("select i_categorywfid, s_name from t_categorywf where i_status = 1 order by s_name");
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            logger.info("getCategories :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) 
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new WellnessmatrixInfo(id, name));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public ArrayList getSubCategoryList(int categoryId) {
        ArrayList list = new ArrayList();
        String query = ("select i_subcategorywfid, s_name from t_subcategorywf where i_status = 1 and i_categorywfid = ? order by s_name");
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
                list.add(new WellnessmatrixInfo(id, name));
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
        sb.append("select d.i_wellnessmatrixid, t_question.s_question, t_question.i_subcategorywfid, t_question.i_questionid ");
        sb.append("from t_question ");
        sb.append("left join t_wellnessmatrix as d on (t_question.i_questionid = d.i_questionid and d.i_positionid = ? and d.i_categorywfid = ? and d.i_clientid = ? and d.i_clientassetid = ?) ");
        sb.append("where t_question.i_status = 1 AND FIND_IN_SET(?, t_question.s_assettypeids)");
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
            int wellnessmatrixId, subcategoryid, questionId;
            while (rs.next()) 
            {
                wellnessmatrixId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                subcategoryid = rs.getInt(3);
                questionId = rs.getInt(4);
                list.add(new WellnessmatrixInfo(name, wellnessmatrixId, categoryId, subcategoryid, questionId));
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
            WellnessmatrixInfo info = (WellnessmatrixInfo) list.get(i);
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
            WellnessmatrixInfo info = (WellnessmatrixInfo) list.get(i);
            if (info != null && info.getWellnessmatrixId() > 0 && info.getSubcategoryId() == subcategoryId) {
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
            WellnessmatrixInfo info = (WellnessmatrixInfo) list.get(i);
            if (info != null && info.getWellnessmatrixId() > 0 && info.getCategoryId() == categoryId) {
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
            WellnessmatrixInfo info = (WellnessmatrixInfo) list.get(i);
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

    public int createWellnessmatrixDetail(int categoryId, int positionId, int subcategoryId[], int questionId[], int userId, int clientId, int assetId) {
        int wellnessmatrixId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_wellnessmatrix ");
            sb.append("(i_clientid, i_clientassetid, i_positionid, i_categorywfid, i_subcategorywfid, i_questionid, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();

            String delquery = "delete from t_wellnessmatrix where i_positionid = ? and i_categorywfid = ? and i_clientid = ? and i_clientassetid = ?  ";
            pstmt = conn.prepareStatement(delquery);
            pstmt.setInt(1, positionId);
            pstmt.setInt(2, categoryId);
            pstmt.setInt(3, clientId);
            pstmt.setInt(4, assetId);
            print(this, "createWellnessmatrixDetail delete all :: " + pstmt.toString());
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
                    print(this, "createWellnessmatrixDetail :: " + pstmt.toString());
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception exception) {
            print(this, "createWellnessmatrixDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return wellnessmatrixId;
    }

    public int createWellnessmatrixPosition(int wellnessmatrixId, int positionId, int userId) {
        int wellnessmatrixpositionId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_wellnessmatrixposition ");
            sb.append("(i_wellnessmatrixid, i_positionid, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, wellnessmatrixId);
            pstmt.setInt(2, positionId);
            pstmt.setInt(3, 1);
            pstmt.setInt(4, userId);
            pstmt.setString(5, currDate1());
            pstmt.setString(6, currDate1());
            //print(this,"createWellnessmatrixPosition :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) 
            {
                wellnessmatrixpositionId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "wellnessmatrixpositionId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return wellnessmatrixpositionId;
    }

    public int deleteWellnessmatrixDetail(int clientId, int assetId, int positionId, int userId, int status) {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_wellnessmatrix set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_positionid = ? and i_clientid = ? and i_clientassetid = ? ");
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
            print(this, "deleteWellnessmatrixDetail :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteWellnessmatrixDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public Collection getClients(String cids, int allclient, String permission) 
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
        sb.append("order by s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new WellnessmatrixInfo(-1, " All "));
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
                coll.add(new WellnessmatrixInfo(refId, refName));
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
            coll.add(new WellnessmatrixInfo(-1, " All "));
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
                    coll.add(new WellnessmatrixInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new WellnessmatrixInfo(-1, " All "));
        }
        return coll;
    }
    
    //For type = 3
    public ArrayList getAssingListForExcel(int assetIdIndex, int categoryId, int positionId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();        
        sb.append("SELECT t_categorywf.s_name, t_subcategorywf.s_name, t_question.s_question, t_position.s_name, t_grade.s_name ");
        sb.append("FROM t_wellnessmatrix ");
        sb.append("LEFT JOIN  t_categorywf ON (t_categorywf.i_categorywfid = t_wellnessmatrix.i_categorywfid) ");
        sb.append("LEFT JOIN t_subcategorywf ON (t_subcategorywf.i_subcategorywfid = t_wellnessmatrix.i_subcategorywfid) ");
        sb.append("LEFT JOIN t_question ON (t_question.i_questionid =  t_wellnessmatrix.i_questionid) ");
        sb.append("LEFT JOIN t_position ON(t_position.i_positionid = t_wellnessmatrix.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("WHERE t_wellnessmatrix.i_clientassetid  = ? AND t_position.i_positionid = ? ");
        if(categoryId > 0)
        {
            sb.append("AND t_wellnessmatrix.i_categorywfid = ? ");
        }
        sb.append("ORDER BY  t_position.s_name, t_categorywf.s_name, t_subcategorywf.s_name, t_question.s_question ");
        String query = (sb.toString()).intern();
        sb.setLength(0);

        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc =0;
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setInt(++scc, positionId);
            if(categoryId > 0)
            {
                pstmt.setInt(++scc, categoryId);
            }
            logger.info("getAssingListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String category, subcategory, question, position , grade;
            while (rs.next())
            {                
                category = rs.getString(1) != null ? rs.getString(1) : "";
                subcategory = rs.getString(2) != null ? rs.getString(2) : "";
                question = rs.getString(3) != null ? rs.getString(3) : "";
                position = rs.getString(4) != null ? rs.getString(4) : "";
                grade = rs.getString(5) != null ? rs.getString(5) : "";
                if(!position.equals(""))
                {
                    if(!grade.equals(""))
                    {
                        position += " | " + grade;
                    }
                }                
                list.add(new WellnessmatrixInfo(category, subcategory, question, position));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
}
