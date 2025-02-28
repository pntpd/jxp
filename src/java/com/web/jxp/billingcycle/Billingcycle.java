package com.web.jxp.billingcycle;

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

public class Billingcycle extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getBillingcycleByName(String search, int next, int count, int allclient, 
            String permission, String cids, String assetids, int clientIdIndex, int assetIdIndex)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.i_clientassetid, t_clientasset.i_clientid, t_client.s_name, t_clientasset.s_name, ");
        sb.append("t1.ct, t_billingcycle.i_type, t_billingcycle.i_status FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_billingcycle on (t_clientasset.i_clientassetid = t_billingcycle.i_clientassetid) ");
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
        sb.append("LEFT JOIN t_client on (t_client.i_clientid = t_clientasset.i_clientid) LEFT JOIN t_billingcycle on (t_clientasset.i_clientassetid = t_billingcycle.i_clientassetid)  ");
        sb.append("WHERE t_clientasset.i_status = 1 AND t_client.i_status = 1  ");
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
            logger.info("getBillingcycleByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAssetName,typevalue;
            int pcount, clientId, assetId, billingstatus;
            while (rs.next())
            {
                assetId = rs.getInt(1);
                clientId = rs.getInt(2);
                clientName = rs.getString(3) != null ? rs.getString(3) : "";
                clientAssetName = rs.getString(4) != null ? rs.getString(4) : "";
                pcount = rs.getInt(5);
                typevalue = getrepeatvalue(rs.getInt(6));
                billingstatus = rs.getInt(7);
                list.add(new BillingcycleInfo(clientId, assetId, clientName, clientAssetName, pcount, typevalue,billingstatus, 0));
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
            print(this,"getBillingcycleByNameCount :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                clientId = rs.getInt(1);
                list.add(new BillingcycleInfo(clientId, 0, "", "", 0, "", 0,0));
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
    
    public String getrepeatvalue(int id)
    {
        String s1 = "";
        switch (id) {
            case 1:
                s1 = "Weekly";
                break;
            case 2:
                s1 = "Monthly";
                break;
            default:
                break;
        }
        return s1;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        BillingcycleInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (BillingcycleInfo) l.get(i);
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
            BillingcycleInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (BillingcycleInfo) l.get(i);
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
    public String getInfoValue(BillingcycleInfo info, String i) 
    {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getClientName() != null ? info.getClientName() : "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getClientAssetName() != null ? info.getClientAssetName() : "";
        } else if (i != null && i.equals("3")) {
            infoval = "" + info.getPcount();
        } else if (i != null && i.equals("4")) {
            infoval = info.getTypevalue()!= null ? info.getTypevalue() : "";
        }
        return infoval;
    }   

    public BillingcycleInfo getBillingcycleDetailByIdforDetail(int clientId,int assetId) 
    {
        BillingcycleInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.i_clientassetid, t_clientasset.i_clientid, t_client.s_name, t_clientasset.s_name, ");
        sb.append("t1.ct, t2.ct, t_billingcycle.i_type, t_billingcycle.s_val  FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN (select t_crewrotation.i_clientassetid, count(DISTINCT(i_positionid)) as ct from t_crewrotation left join t_candidate ON (t_candidate.i_candidateid = t_crewrotation.i_candidateid) where t_crewrotation.i_active = 1 group by t_crewrotation.i_clientassetid) as t1 on (t_clientasset.i_clientassetid = t1.i_clientassetid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, count(1) as ct from t_crewrotation where i_active = 1 group by i_clientassetid) as t2 ON (t_clientasset.i_clientassetid = t2.i_clientassetid) ");
        sb.append("LEFT JOIN t_billingcycle ON (t_billingcycle.i_clientassetid = t_clientasset.i_clientassetid) ");
        sb.append("WHERE t_clientasset.i_status = 1 and t_clientasset.i_clientassetid = ?  ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assetId);
            print(this, "getBillingcycleDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAssetName, sValue;
            int pcount, personnelcount, type;
            while (rs.next()) 
            {
                assetId = rs.getInt(1);
                clientId = rs.getInt(2);
                clientName = rs.getString(3) != null ? rs.getString(3) : "";
                clientAssetName = rs.getString(4) != null ? rs.getString(4) : "";
                pcount = rs.getInt(5);
                personnelcount = rs.getInt(6);
                type = rs.getInt(7);
                sValue = rs.getString(8) != null ? rs.getString(8) : "";
                info = new BillingcycleInfo(clientId, assetId, clientName, clientAssetName, pcount,sValue,personnelcount,type);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getBillingcycleDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getListForExcel(String search, int allclient, 
            String permission, String cids, String assetids, int clientIdIndex, int assetIdIndex) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.i_clientassetid, t_clientasset.i_clientid, t_client.s_name, t_clientasset.s_name, ");
        sb.append("t1.ct, t_billingcycle.i_type, t_billingcycle.i_status FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_billingcycle on (t_clientasset.i_clientassetid = t_billingcycle.i_clientassetid) ");
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
        
        String query = sb.toString().intern();
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
            print(this,"getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
             String clientName, clientAssetName,typevalue;
            int pcount, clientId, assetId, billingstatus;
            while (rs.next())
            {
                assetId = rs.getInt(1);
                clientId = rs.getInt(2);
                clientName = rs.getString(3) != null ? rs.getString(3) : "";
                clientAssetName = rs.getString(4) != null ? rs.getString(4) : "";
                pcount = rs.getInt(5);
                typevalue = getrepeatvalue(rs.getInt(6));
                billingstatus = rs.getInt(7); 
                list.add(new BillingcycleInfo(clientId, assetId, clientName, clientAssetName, pcount, typevalue,billingstatus, 0));
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
  
    public ArrayList getPositionList( int clientId, int assetId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_position.i_positionid, t_position.s_name, t1.ct1, t1.ct2, t1.ct3, t_grade.s_name, t2.ct1 ");
        sb.append("from t_clientassetposition left join t_position on (t_clientassetposition.i_positionid = t_position.i_positionid) ");
        sb.append("left join t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("left join (select i_positionid, i_status, count(DISTINCT(i_categorywfid)) as ct1, count(DISTINCT(i_subcategorywfid)) as ct2, count(DISTINCT(i_questionid)) as ct3 from t_billingcycle where i_clientassetid = ?  group by i_positionid) as t1 on (t1.i_positionid = t_clientassetposition.i_positionid ) ");
        sb.append("left join (select i_positionid, count(DISTINCT(i_categorywfid)) as ct1 from t_billingcycle where i_status = 1 group by i_positionid) as t2 on (t2.i_positionid = t_clientassetposition.i_positionid) ");
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
                if (!gradeName.equals("")) {
                    name += " - " + gradeName;
                }
                list.add(new BillingcycleInfo(clientId, name, count1, count2, count3, positionId, assetId, statusCount));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int getBillingcycleId(int assetId) 
    {
        int billingcycleId = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_billingcycleid ");
        sb.append("from t_billingcycle ");
        sb.append("where i_clientassetid = ?");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assetId);
            logger.info("getPositionInfo :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                billingcycleId = rs.getInt(1);
                }
            
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return billingcycleId;
    }
    
    public int createBillingcycle(int type, String repeatValue, int userId, int clientId, int assetId)
    {
        int billingcycleId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_billingcycle ");
            sb.append("( i_clientassetid, i_type, s_val, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, assetId);
            pstmt.setInt(2, type);
            pstmt.setString(3, repeatValue);
            pstmt.setInt(4, 1);
            pstmt.setInt(5, userId);
            pstmt.setString(6, currDate1());
            pstmt.setString(7, currDate1());
            //print(this,"createBillingcyclePosition :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                billingcycleId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createBillingcycle :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return billingcycleId;
    }
    
    public int updateBillingcycleDetail(int type, String repeatValue, int userId, int clientId, int assetId) 
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_billingcycle set ");
            sb.append("i_type = ?, ");
            sb.append("s_val = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_clientassetid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, type);
            pstmt.setString(++scc, repeatValue);
            pstmt.setInt(++scc, 1);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, assetId);
            print(this, "updateBillingcycleDetail :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateBillingcycleDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int deleteBillingcycleDetail(int clientId, int assetId, int userId, int status) 
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_billingcycle set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where  i_clientassetid = ? ");
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
            pstmt.setInt(++scc, assetId);
            print(this, "deleteBillingcycleDetail :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteBillingcycleDetail :: " + exception.getMessage());
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
        coll.add(new BillingcycleInfo(-1, " All "));
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
                coll.add(new BillingcycleInfo(refId, refName));
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
            coll.add(new BillingcycleInfo(-1, " All "));
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
                    ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new BillingcycleInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new BillingcycleInfo(-1, " All "));
        }
        return coll;
    }    
}
