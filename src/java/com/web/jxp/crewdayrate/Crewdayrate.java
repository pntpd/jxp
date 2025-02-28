package com.web.jxp.crewdayrate;
import com.web.jxp.base.Base;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import static com.web.jxp.common.Common.*;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

public class Crewdayrate extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getCrewdayrateByName(String search, int next, int count, int allclient, 
            String permission, String cids, String assetids, int clientIdIndex, int assetIdIndex)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.i_clientassetid, t_client.s_name, t_clientasset.s_name, t_dayratemain.i_status, t1.ct, t2.ct, t3.ct ");
        sb.append("FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_dayratemain ON (t_clientasset.i_clientassetid = t_dayratemain.i_clientassetid) ");
        sb.append("LEFT JOIN (select t_candidate.i_clientassetid, COUNT(DISTINCT(i_positionid)) AS ct FROM t_candidate ");
        sb.append("WHERE t_candidate.i_status = 1 GROUP BY t_candidate.i_clientassetid) AS t1 ON (t_clientasset.i_clientassetid = t1.i_clientassetid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, COUNT(1) AS ct FROM t_candidate WHERE i_status = 1 GROUP BY i_clientassetid) AS t2 ON (t_clientasset.i_clientassetid = t2.i_clientassetid) ");
        sb.append("LEFT JOIN (SELECT t_dayrate.i_clientassetid, COUNT(1) AS ct FROM t_dayrate LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_dayrate.i_candidateid) WHERE t_candidate.i_status = 1 AND  t_dayrate.i_status = 1 AND t_dayrate.d_rate1 > 0 GROUP BY t_dayrate.i_clientassetid) AS t3 ON (t_clientasset.i_clientassetid = t3.i_clientassetid)  ");
        sb.append("WHERE t_clientasset.i_status = 1 ");
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
        if(search != null && !search.equals(""))
            sb.append(" AND (t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ?) ");
        if(clientIdIndex > 0)
            sb.append(" AND t_clientasset.i_clientid = ? ");
        if(assetIdIndex > 0)
            sb.append(" AND t_clientasset.i_clientassetid = ? ");
        sb.append("ORDER BY t_client.s_name, t_clientasset.s_name");
        if(count > 0)
            sb.append(" LIMIT ").append(next*count).append(", ").append(count);
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("SELECT COUNT(1) ");
        sb.append("FROM t_clientasset LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_dayratemain ON (t_clientasset.i_clientassetid = t_dayratemain.i_clientassetid) ");
        sb.append("WHERE t_clientasset.i_status = 1 ");
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
        if(search != null && !search.equals(""))
            sb.append(" AND (t_client.s_name like ? OR t_clientasset.s_name LIKE ?) ");
        if(clientIdIndex > 0)
            sb.append(" AND t_clientasset.i_clientid = ? ");
        if(assetIdIndex > 0)
            sb.append(" AND t_clientasset.i_clientassetid = ? ");
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
            logger.info("getCrewdayrateByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAssetName;
            int pcount, assetId,status, ccount, scount, pendingcount;
            while (rs.next())
            {
                assetId = rs.getInt(1);
                clientName = rs.getString(2) != null ? rs.getString(2) : "";
                clientAssetName = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4);
                pcount = rs.getInt(5);
                ccount = rs.getInt(6);
                scount = rs.getInt(7);            
                pendingcount = ccount - scount;
                if(pendingcount < 0)
                    pendingcount = 0;
                list.add(new CrewdayrateInfo(assetId, clientName, clientAssetName, status, pcount,
                        scount, pendingcount));
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
            print(this,"getCrewdayrateByNameCountQuery :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                assetId = rs.getInt(1);
                list.add(new CrewdayrateInfo(assetId,  "", "", 0,0,0,0));
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

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp)
    {
        ArrayList list = null;
        HashMap record = null;
        CrewdayrateInfo info;
        int total = 0;
        if(l != null)
            total = l.size();
        try
        {
            if(total > 0)
            {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++)
                {
                    info = (CrewdayrateInfo) l.get (i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = null;
            if(colId.equals("3"))
                map = sortById(record, tp);
            else
                map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            CrewdayrateInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                for(int i = 0; i < total; i++)
                {
                    rInfo = (CrewdayrateInfo) l.get(i);
                    String str = getInfoValue(rInfo, colId);                    
                    if(str.equalsIgnoreCase(key))
                    {    
                        list.add(rInfo);
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    // to sort search result
    public String getInfoValue(CrewdayrateInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getClientName()!= null ? info.getClientName() : "";    
        else if(i != null && i.equals("2"))
            infoval = info.getClientAssetName()!= null ? info.getClientAssetName() : "";
        else if(i != null && i.equals("3"))
            infoval = ""+info.getPcount(); 
        return infoval;
    }
    
    public ArrayList getExcel(String search, int allclient, String permission, String cids, String assetids, 
            int clientIdIndex, int assetIdIndex)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.i_clientassetid, t_client.s_name, t_clientasset.s_name, t_dayratemain.i_status, t1.ct, t2.ct, t3.ct ");
        sb.append("FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_dayratemain ON (t_clientasset.i_clientassetid = t_dayratemain.i_clientassetid) ");
        sb.append("LEFT JOIN (select t_crewrotation.i_clientassetid, COUNT(DISTINCT(i_positionid)) AS ct FROM t_crewrotation ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_crewrotation.i_candidateid) ");
        sb.append("WHERE t_crewrotation.i_active = 1 GROUP BY t_crewrotation.i_clientassetid) AS t1 ON (t_clientasset.i_clientassetid = t1.i_clientassetid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, COUNT(1) AS ct FROM t_crewrotation WHERE i_active = 1 GROUP BY i_clientassetid) AS t2 on (t_clientasset.i_clientassetid = t2.i_clientassetid) ");
        sb.append("LEFT JOIN (SELECT t_dayrate.i_clientassetid, COUNT(1) AS ct FROM t_dayrate LEFT JOIN t_crewrotation on (t_crewrotation.i_crewrotationid = t_dayrate.i_crewrotationid) WHERE t_crewrotation.i_active = 1 AND  t_dayrate.i_status = 1 AND t_dayrate.d_rate1 > 0 GROUP BY t_dayrate.i_clientassetid) AS t3 ON (t_clientasset.i_clientassetid = t3.i_clientassetid) ");
        sb.append("WHERE t_clientasset.i_status = 1 ");
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
        if(search != null && !search.equals(""))
            sb.append(" AND (t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ?) ");
        if(clientIdIndex > 0)
            sb.append(" AND t_clientasset.i_clientid = ? ");
        if(assetIdIndex > 0)
            sb.append(" AND t_clientasset.i_clientassetid = ? ");
        sb.append("ORDER BY t_client.s_name, t_clientasset.s_name");
        String query = (sb.toString()).intern();
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
            logger.info("getCrewdayrateByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAssetName;
            int pcount, assetId,status, ccount, scount, pendingcount;
            while (rs.next())
            {
                assetId = rs.getInt(1);
                clientName = rs.getString(2) != null ? rs.getString(2) : "";
                clientAssetName = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4);
                pcount = rs.getInt(5);
                ccount = rs.getInt(6);
                scount = rs.getInt(7);            
                pendingcount = ccount - scount;
                list.add(new CrewdayrateInfo(assetId, clientName, clientAssetName, status, pcount,
                        scount, pendingcount));
            }
            rs.close();
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
        coll.add(new CrewdayrateInfo(-1, " All "));
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
                coll.add(new CrewdayrateInfo(refId, refName));
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
            coll.add(new CrewdayrateInfo(-1, " All "));
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
                    coll.add(new CrewdayrateInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new CrewdayrateInfo(-1, " All "));
        }
        return coll;
    }
    // Listing
    public ArrayList getPositionList(int clientassetId)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT t_position.i_positionid, t_position.s_name, t_grade.s_name, ");
        sb.append("t_dayrateposition.d_rate FROM t_candidate ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_candidate.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_dayrateposition ON (t_position.i_positionid = t_dayrateposition.i_positionid AND t_dayrateposition.i_clientassetid = ?) ");
        sb.append("WHERE t_candidate.i_status = 1 AND t_candidate.i_clientassetid = ? AND t_candidate.i_positionid > 0 ");
        sb.append("ORDER BY t_position.s_name, t_grade.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, clientassetId);
            logger.info("getPositionList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String positionName, gradeName;
            int positionId;
            double prate;
            String pids = "";
            while (rs.next())
            {
                positionId = rs.getInt(1);
                positionName = rs.getString(2) != null ? rs.getString(2) : "";
                gradeName = rs.getString(3) != null ? rs.getString(3) : "";
                prate = rs.getDouble(4);
                if(!gradeName.equals(""))
                    positionName += " | " + gradeName;
                if(pids.equals(""))
                    pids = ""+positionId;
                else
                    pids += ","+positionId;
                list.add(new CrewdayrateInfo(positionId, positionName, prate));
            }
            rs.close();
            
            sb.append("SELECT DISTINCT t_position.i_positionid, t_position.s_name, t_grade.s_name, ");
            sb.append("t_dayrateposition.d_rate FROM t_candidate ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_candidate.i_positionid2) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("LEFT JOIN t_dayrateposition ON (t_position.i_positionid = t_dayrateposition.i_positionid AND t_dayrateposition.i_clientassetid = ?) ");
            sb.append("WHERE t_candidate.i_status = 1 AND t_candidate.i_clientassetid = ? AND t_candidate.i_positionid2 > 0 ");
            if(!pids.equals(""))
                sb.append(" AND t_position.i_positionid NOT IN ("+pids+") ");
            sb.append("ORDER BY t_position.s_name, t_grade.s_name ");
            query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, clientassetId);
            logger.info("getPositionList2 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                positionId = rs.getInt(1);
                positionName = rs.getString(2) != null ? rs.getString(2) : "";
                gradeName = rs.getString(3) != null ? rs.getString(3) : "";
                prate = rs.getDouble(4);
                if(!gradeName.equals(""))
                    positionName += " | " + gradeName;
                list.add(new CrewdayrateInfo(positionId, positionName, prate));
            }
            rs.close();
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
   
    public ArrayList getCrewList(int clientassetId, String ratetype, int type, int positionId2, String search2)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();        
        sb.append("SELECT c.i_candidateid, c.i_positionid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), ");
        sb.append("t_dayrate.d_rate1, t_dayrate.d_rate2, t_dayrate.d_rate3, DATE_FORMAT(t_dayrate.d_fromdate, '%d-%b-%Y'), DATE_FORMAT(t_dayrate.d_todate, '%d-%b-%Y') ");
        sb.append("FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_dayrate ON (t_dayrate.i_candidateid = c.i_candidateid AND c.i_positionid = t_dayrate.i_positionid AND t_dayrate.i_clientassetid = ? ) ");
        sb.append("WHERE c.i_clientassetid = ? and c.i_positionid > 0 ");
        if (search2 != null && !search2.equals("")) {
            sb.append("AND CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? ");
        }
        if(positionId2 > 0)
        {
            sb.append("AND c.i_positionid = ? ");
        }
        sb.append("ORDER BY c.i_positionid, c.s_firstname, c.s_middlename, c.s_lastname "); 
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientassetId);
            pstmt.setInt(++scc, clientassetId);
            if (search2 != null && !search2.equals("")) 
            {
                pstmt.setString(++scc, "%" + (search2) + "%");
                pstmt.setString(++scc, "%" + (search2) + "%");
            }
            if(positionId2 > 0)
            {
                pstmt.setInt(++scc, positionId2);
            }
            print(this, " getCrewList ::::" + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, fromDate, toDate;
            int positionId, candidateId;
            double rate1, rate2, rate3;
            while (rs.next())
            {
                candidateId = rs.getInt(1);
                positionId = rs.getInt(2);
                name = rs.getString(3) != null ? rs.getString(3) : "";
                rate1 = rs.getDouble(4);
                rate2 = rs.getDouble(5);
                rate3 = rs.getDouble(6);
                if((ratetype.equalsIgnoreCase("Day wise") || ratetype.equalsIgnoreCase("Monthly")) && rate1 <= 0)
                {
                    if(ratetype.equalsIgnoreCase("Monthly"))
                        rate1 = getDecimalDouble(rate1 / 30.0);
                }
                fromDate = rs.getString(7) != null ? rs.getString(7) : "";
                toDate = rs.getString(8) != null ? rs.getString(8) : "";
                list.add(new CrewdayrateInfo(candidateId, positionId, name, rate1, rate2, rate3, 1,fromDate, toDate));
            }
            rs.close();
            
            sb.append("SELECT c.i_candidateid, c.i_positionid2, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), ");
            sb.append("t_dayrate.d_rate1, t_dayrate.d_rate2, d_rate3, DATE_FORMAT(t_dayrate.d_fromdate, '%d-%b-%Y'), DATE_FORMAT(t_dayrate.d_todate, '%d-%b-%Y') ");
            sb.append("FROM t_candidate AS c ");
            sb.append("LEFT JOIN t_dayrate ON (t_dayrate.i_candidateid = c.i_candidateid AND c.i_positionid2 = t_dayrate.i_positionid AND t_dayrate.i_clientassetid = ? ) ");
            sb.append("WHERE c.i_clientassetid = ? AND c.i_positionid2 > 0 AND c.i_status = 1 ");
            if (search2 != null && !search2.equals(""))
            {
                sb.append("AND CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? ");
            }
            if(positionId2 > 0)
            {
                sb.append("AND c.i_positionid2 = ? ");
            }
            sb.append("ORDER BY c.i_positionid2, c.s_firstname, c.s_middlename, c.s_lastname "); 
            query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            scc = 0;
            pstmt.setInt(++scc, clientassetId);
            pstmt.setInt(++scc, clientassetId);
            if (search2 != null && !search2.equals("")) 
            {
                pstmt.setString(++scc, "%" + (search2) + "%");
                pstmt.setString(++scc, "%" + (search2) + "%");
            }
            if(positionId2 > 0)
            {
                pstmt.setInt(++scc, positionId2);
            }
            print(this, " getCrewList2 ::::" + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                candidateId = rs.getInt(1);
                positionId = rs.getInt(2);
                name = rs.getString(3) != null ? rs.getString(3) : "";
                rate1 = rs.getDouble(4);
                rate2 = rs.getDouble(5);
                rate3 = rs.getDouble(6);
                if((ratetype.equalsIgnoreCase("Day wise") || ratetype.equalsIgnoreCase("Monthly")) && rate1 <= 0)
                {
                    if(ratetype.equalsIgnoreCase("Monthly"))
                        rate1 = getDecimalDouble(rate1 / 30.0);
                }
                fromDate = rs.getString(7) != null ? rs.getString(7) : "";
                toDate = rs.getString(8) != null ? rs.getString(8) : "";
                list.add(new CrewdayrateInfo(candidateId, positionId, name, rate1, rate2, rate3,2,fromDate, toDate));
            }
            rs.close();
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
   
    public ArrayList getListFromList(ArrayList mainlist, int positionId)
    {
        int size = mainlist.size();
        ArrayList list = new ArrayList();
        for(int i = 0; i < size; i++)
        {
            CrewdayrateInfo info = (CrewdayrateInfo) mainlist.get(i);
            if(info != null && info.getPositionId() == positionId)
                list.add(info);
        }
        return list;
    }
    
    public CrewdayrateInfo getBasicDetail(int clientassetId)
    {
        CrewdayrateInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_name, t_clientasset.s_name, t_currency.s_name,t_client.i_clientid,");
        sb.append("t_clientasset.s_ratetype FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = t_clientasset.i_currencyid) ");
        sb.append("WHERE i_clientassetid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            print(this, "getBasicDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                String clientName = rs.getString(1) != null ? rs.getString(1) : "";
                String clientassetName = rs.getString(2) != null ? rs.getString(2) : "";
                String currencyName = rs.getString(3) != null ? rs.getString(3) : "";
                int clientId = rs.getInt(4);
                String ratetype = rs.getString(5) != null ? rs.getString(5) : "";
                info = new CrewdayrateInfo(clientName, clientassetName, currencyName, clientId, ratetype);
            }
        }
        catch (Exception exception)
        {
            print(this, "getBasicDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    public int deleteDayRateDetail(int assetId, int status, int userId) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_dayratemain SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE  i_clientassetid = ? ");
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
            print(this, "deleteDayRateDetail :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteDayRateDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public CrewdayrateInfo getPositionRatesAppraisal(int candidateId) 
    {
        CrewdayrateInfo info = null;
        StringBuilder sb = new StringBuilder();        
        sb.append("SELECT  t_dayrate.d_rate1,  t_dayrate.d_rate2,  t_dayrate.d_rate3, ");  
        sb.append("t_candidate.i_positionid, t_candidate.i_positionid2, d2.d_rate1, d2.d_rate2,  d2.d_rate3 ");
        sb.append("FROM t_candidate ");
        sb.append("LEFT JOIN t_dayrate ON(t_dayrate.i_candidateid = t_candidate.i_candidateid AND  t_dayrate.i_positionid= t_candidate.i_positionid) ");
        sb.append("LEFT JOIN t_dayrate AS d2 ON( d2.i_candidateid = t_candidate.i_candidateid AND  d2.i_positionid= t_candidate.i_positionid2) ");
        sb.append("WHERE t_candidate.i_candidateid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getPositionRatesAppraisal :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int positionId, positionId2;
            double rate1, rate2, rate3, prate1, prate2, prate3;
            while (rs.next()) 
            {
                rate1 = rs.getDouble(1);
                rate2 = rs.getDouble(2);
                rate3 = rs.getDouble(3);
                positionId = rs.getInt(4);
                positionId2 = rs.getInt(5);
                prate1 = rs.getDouble(6);
                prate2 = rs.getDouble(7);
                prate3 = rs.getDouble(8);
                info = new CrewdayrateInfo(rate1, rate2, rate3, positionId, positionId2,prate1, prate2, prate3);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int createdatacandidate(int clientassetId, int candidateId, int positionId,
        double rate1, double rate2, double rate3, String fromdate, String todate, int userId)
    {
        int cc = 0;
        if(clientassetId > 0)
        {
            try
            {
                String currdate = currDate1();
                conn = getConnection();
                String query = "SELECT i_dayratemainid FROM t_dayratemain WHERE i_clientassetid = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                logger.info("create get dayratemainid :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int dayratemainId = 0;
                while (rs.next())
                {
                    dayratemainId = rs.getInt(1);  
                }
                rs.close();
                
                if(dayratemainId <= 0)
                {
                    query = "INSERT INTO t_dayratemain (i_clientassetid, i_status, i_userid, ts_regdate, ts_moddate) VALUES (?, ?, ?, ?, ?) ";
                    pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setInt(1, clientassetId);
                    pstmt.setInt(2, 1);
                    pstmt.setInt(3, userId);
                    pstmt.setString(4, currdate);
                    pstmt.setString(5, currdate);  
                    print(this," createdata insert dayratemain :: " + pstmt.toString());
                    pstmt.executeUpdate();
                    rs = pstmt.getGeneratedKeys();
                    while (rs.next())
                    {
                        dayratemainId = rs.getInt(1);
                    }
                }
                
                query = "SELECT i_dayratehid, DATE_FORMAT(d_todate, '%d-%b-%Y ') FROM t_dayrateh WHERE i_clientassetid = ? AND i_candidateid = ? AND i_positionid = ? ORDER BY d_fromdate DESC LIMIT 0,1 ";
                pstmt = conn.prepareStatement(query);  
                pstmt.setInt(1, clientassetId);
                pstmt.setInt(2, candidateId);
                pstmt.setInt(3, positionId);
                print(this," Select dayratehid :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int dayratehId = 0;
                String lasttodate = "";
                while (rs.next())
                {
                    dayratehId = rs.getInt(1);
                    lasttodate = rs.getString(2) != null && !rs.getString(2).equals("0000-00-00") ? rs.getString(2) : "";
                }
                rs.close();
                
                if(dayratehId > 0 )
                {
                    String newdate = getDateAfter(fromdate, 1, -1, "dd-MMM-yyyy", "dd-MMM-yyyy");
                    query = "UPDATE t_dayrateh SET d_todate = ? WHERE i_dayratehid = ?";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, changeDate1(newdate));
                    pstmt.setInt(2, dayratehId);
                    print(this,"UPDATE t_dayrateh :: " + pstmt.toString());
                    pstmt.executeUpdate();
                }
                
                query = "SELECT i_dayrateid FROM t_dayrate WHERE i_clientassetid = ? AND i_candidateid = ? AND i_positionid = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                pstmt.setInt(2, candidateId);
                pstmt.setInt(3, positionId);
                print(this,"select i_dayrateid :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int dayrateId = 0;
                while (rs.next())
                {
                    dayrateId = rs.getInt(1);
                }
                
                if(dayrateId <= 0)
                {
                    StringBuilder sb = new StringBuilder();
                    sb.append("INSERT INTO t_dayrate ");
                    sb.append("(i_dayratemainid, i_clientassetid, i_candidateid, i_positionid, d_rate1, d_rate2, d_rate3, i_status, d_fromdate, d_todate, i_userid, ts_regdate, ts_moddate) ");
                    sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?)");
                    query = (sb.toString()).intern();
                    sb.setLength(0);
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, dayratemainId);
                    pstmt.setInt(2, clientassetId);
                    pstmt.setInt(3, candidateId);
                    pstmt.setInt(4, positionId);
                    pstmt.setDouble(5, rate1);
                    pstmt.setDouble(6, rate2);
                    pstmt.setDouble(7, rate3);
                    pstmt.setInt(8, 1);
                    pstmt.setString(9, changeDate1(fromdate));
                    pstmt.setString(10, changeDate1(todate));
                    pstmt.setInt(11, userId);
                    pstmt.setString(12, currdate);
                    pstmt.setString(13, currdate);  
                    print(this,"createdatacandidate :: " + pstmt.toString());
                    cc += pstmt.executeUpdate();
                }
                else
                {
                    StringBuilder sb = new StringBuilder();
                    sb.append("UPDATE t_dayrate SET d_rate1 = ?, d_rate2 = ?, d_rate3 = ?, i_userid = ?, ts_moddate = ?,  d_fromdate = ?, d_todate= ?  ");
                    sb.append("where i_clientassetid = ? AND i_candidateid = ? AND i_positionid = ? ");
                    query = sb.toString();
                    sb.setLength(0);
                    pstmt = conn.prepareStatement(query);                    
                    pstmt.setDouble(1, rate1);
                    pstmt.setDouble(2, rate2);
                    pstmt.setDouble(3, rate3);
                    pstmt.setInt(4, userId);
                    pstmt.setString(5, currdate);
                    pstmt.setString(6, changeDate1(fromdate));
                    pstmt.setString(7, changeDate1(todate));
                    pstmt.setInt(8, clientassetId);
                    pstmt.setInt(9, candidateId);
                    pstmt.setInt(10, positionId); 
                    print(this,"updateDayratedata :: " + pstmt.toString());
                    cc += pstmt.executeUpdate();
                }
                
                query = "insert into t_dayrateh (i_clientassetid, i_candidateid, i_positionid, d_rate1, d_rate2, d_rate3, d_fromdate, d_todate, i_status, i_userid, ts_regdate, ts_moddate)  VALUES (?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ? ) ";
                pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, clientassetId);
                pstmt.setInt(2, candidateId);
                pstmt.setInt(3, positionId);
                pstmt.setDouble(4, rate1);
                pstmt.setDouble(5, rate2);
                pstmt.setDouble(6, rate3);
                pstmt.setString(7, changeDate1(fromdate));
                pstmt.setString(8, changeDate1(todate));
                pstmt.setInt(9, 1);
                pstmt.setInt(10, userId);
                pstmt.setString(11, currdate);
                pstmt.setString(12, currdate);  
                print(this," insert dayrateh :: " + pstmt.toString());
                pstmt.executeUpdate();                
            }
            
            catch (Exception exception)
            {
                print(this,"createdatacandidate :: " + exception.getMessage());
                print(this,"createdatacandidate :: " + pstmt.toString());
            }
            finally
            {
                close(conn, pstmt, rs);
            }
        }
        return cc;
    }
    
    public int checkin2datesrate(String sdate, String edate, String date, String sformatv) 
    {
        int b = 0;
        try 
        {
            if (sdate != null && !sdate.equals("") && !sdate.equals("00-00-0000") && edate != null && !edate.equals("") && !edate.equals("00-00-0000")) 
            {
                SimpleDateFormat sdf = new SimpleDateFormat(sformatv);
                long l1 = sdf.parse(sdate).getTime();
                long l2 = 0;
                if(edate != null && !edate.equals(""))
                    l2 = sdf.parse(edate).getTime();
                long l3 = sdf.parse(date).getTime();
                if((l3 >= l1 && l3 <= l2) || (l3 >= l1 && l2 == 0))
                    b = 1;
            }
        } 
        catch (Exception e) 
        {
        }
        return b;
    }
    
    
    //For crew day rate history
    public ArrayList getHistory(int candidateId, int positionId, int clientassetId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_dayratehid, DATE_FORMAT(d_fromdate, '%d-%b-%Y'), DATE_FORMAT(d_todate, '%d-%b-%Y'), d_rate1, d_rate2, d_rate3 ");
        sb.append("FROM t_dayrateh WHERE i_candidateid = ? AND i_positionid= ? AND i_clientassetid = ? ORDER BY d_fromdate DESC");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            pstmt.setInt(2, positionId);
            pstmt.setInt(3, clientassetId);
            logger.info("getHistory :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String fromDate, toDate;
            int dayratehId;
            double rate1, rate2, rate3;
            while (rs.next()) 
            {
                dayratehId = rs.getInt(1);                
                fromDate = rs.getString(2) != null ? rs.getString(2) : "";
                toDate = rs.getString(3) != null ? rs.getString(3) : "";
                rate1 = rs.getDouble(4);
                rate2 = rs.getDouble(5);
                rate3 = rs.getDouble(6);      
                
                list.add(new CrewdayrateInfo(dayratehId, fromDate, toDate, rate1, rate2, rate3));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public int createPositionRate(int clientassetId, int userId, int positionId, double prate)
    {
        int cc = 0;
        if(clientassetId > 0)
        {
            try
            {
                String currdate = currDate1();
                conn = getConnection();
                String query = "SELECT i_dayratepositionid FROM t_dayrateposition WHERE i_clientassetid = ? AND i_positionid = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                pstmt.setInt(2, positionId);
                logger.info("get dayratepositionid :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int dayratepId = 0;
                while (rs.next())
                {
                    dayratepId = rs.getInt(1);  
                }
                rs.close();
                
                query = "SELECT i_dayratemainid FROM t_dayratemain WHERE i_clientassetid = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                logger.info("create get dayratemainid :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int dayratemainId = 0;
                while (rs.next())
                {
                    dayratemainId = rs.getInt(1);  
                }
                rs.close();
                
                if(dayratemainId <= 0)
                {
                    query = "INSERT INTO t_dayratemain (i_clientassetid, i_status, i_userid, ts_regdate, ts_moddate) VALUES (?, ?, ?, ?, ?) ";
                    pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setInt(1, clientassetId);
                    pstmt.setInt(2, 1);
                    pstmt.setInt(3, userId);
                    pstmt.setString(4, currdate);
                    pstmt.setString(5, currdate);  
                    print(this," createdata insert dayratemain :: " + pstmt.toString());
                    pstmt.executeUpdate();
                    rs = pstmt.getGeneratedKeys();
                    while (rs.next())
                    {
                        dayratemainId = rs.getInt(1);
                    }
                }                
                
                StringBuilder sb = new StringBuilder();
                if(dayratepId <= 0)
                {
                    sb.append("INSERT INTO t_dayrateposition ");
                    sb.append("(i_clientassetid, i_positionid, d_rate, i_status, i_userid, ts_regdate, ts_moddate, i_dayratemainid) ");
                    sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                    query = (sb.toString()).intern();
                    sb.setLength(0);

                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, clientassetId);
                    pstmt.setInt(2, positionId);
                    pstmt.setDouble(3, prate);
                    pstmt.setInt(4, 1);
                    pstmt.setInt(5, userId);
                    pstmt.setString(6, currdate);
                    pstmt.setString(7, currdate);  
                    pstmt.setInt(8, dayratemainId);  
                    print(this,"createPositionRate :: " + pstmt.toString());
                    cc +=pstmt.executeUpdate(); 
                }else
                {
                    sb.append("UPDATE t_dayrateposition SET d_rate = ?, i_userid = ?, ts_moddate = ? ");
                    sb.append("WHERE i_dayratepositionid = ?");
                    query = sb.toString();
                    sb.setLength(0);
                    pstmt = conn.prepareStatement(query);                    
                    pstmt.setDouble(1, prate);
                    pstmt.setInt(2, userId);
                    pstmt.setString(3, currdate);
                    pstmt.setInt(4, dayratepId);
                    print(this,"update t_dayrateposition :: " + pstmt.toString());
                    cc += pstmt.executeUpdate();
                }
            }
            catch (Exception exception)
            {
                print(this,"createPositionRate :: " + exception.getMessage());
            }
            finally
            {
                close(conn, pstmt, rs);
            }
        }
        return cc;
    }
    
    public int checkDuplicacy(int candidateId, String fromDate, String toDate, int clientassetId, int positionId)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_dayratehid FROM t_dayrateh WHERE i_clientassetid = ? AND i_candidateid= ? AND i_positionid= ?  ");        
        sb.append("AND (( ? >= d_fromdate AND ? <= d_todate )  ");        
        sb.append("OR ( ? >= d_fromdate AND  ? <= d_todate )  ");    
        sb.append("OR ( ? <= d_fromdate ))  ");        
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;  
            pstmt.setInt(++scc, clientassetId);
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, positionId);
            pstmt.setString(++scc, changeDate1(fromDate));
            pstmt.setString(++scc, changeDate1(fromDate));
            pstmt.setString(++scc, changeDate1(toDate));
            pstmt.setString(++scc, changeDate1(toDate));
            pstmt.setString(++scc, changeDate1(fromDate));            
            print(this,"checkDuplicacy :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                ck = 1;
            }
        }
        catch (Exception exception)
        {
            print(this,"checkDuplicacy :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return ck;
    }
    
    public Collection getPostionsFilter(int clientassetId)
    {
        Collection coll = new LinkedList();
        if(clientassetId > 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT DISTINCT t_position.i_positionid, t_position.s_name, t_grade.s_name ");
            sb.append("FROM t_candidate ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_candidate.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("LEFT JOIN t_dayrateposition ON (t_position.i_positionid = t_dayrateposition.i_positionid AND t_dayrateposition.i_clientassetid = ?) ");
            sb.append("WHERE t_candidate.i_status = 1 AND t_candidate.i_clientassetid = ? AND t_candidate.i_positionid > 0 ");
            sb.append("ORDER BY t_position.s_name, t_grade.s_name ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            coll.add(new CrewdayrateInfo(-1, "Select Position | Rank"));
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                pstmt.setInt(2, clientassetId);
                logger.info("getPostionsFilter1 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String positionName, gradeName;
                int positionId;
                String pids = "";
                while (rs.next())
                {
                    positionId = rs.getInt(1);
                    positionName = rs.getString(2) != null ? rs.getString(2) : "";
                    gradeName = rs.getString(3) != null ? rs.getString(3) : "";
                    if(!gradeName.equals(""))
                        positionName += " | " + gradeName;
                    if(pids.equals(""))
                        pids = ""+positionId;
                    else
                        pids += ","+positionId;
                    coll.add(new CrewdayrateInfo(positionId, positionName));
                }
                rs.close();

                sb.append("SELECT DISTINCT t_position.i_positionid, t_position.s_name, t_grade.s_name ");
                sb.append("FROM t_candidate ");
                sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_candidate.i_positionid2) ");
                sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
                sb.append("LEFT JOIN t_dayrateposition ON (t_position.i_positionid = t_dayrateposition.i_positionid AND t_dayrateposition.i_clientassetid = ?) ");
                sb.append("WHERE t_candidate.i_status = 1 AND t_candidate.i_clientassetid = ? AND t_candidate.i_positionid2 > 0 ");
                if(!pids.equals(""))
                    sb.append(" AND t_position.i_positionid NOT IN ("+pids+") ");
                sb.append("ORDER BY t_position.s_name, t_grade.s_name ");
                query = (sb.toString()).intern();
                sb.setLength(0);

                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                pstmt.setInt(2, clientassetId);
                logger.info("getPostionsFilter2 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    positionId = rs.getInt(1);
                    positionName = rs.getString(2) != null ? rs.getString(2) : "";
                    gradeName = rs.getString(3) != null ? rs.getString(3) : "";
                    if(!gradeName.equals(""))
                        positionName += " | " + gradeName;
                    coll.add(new CrewdayrateInfo(positionId, positionName));
                }
                rs.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            finally
            {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new CrewdayrateInfo(-1, " Select Position | Rank "));
        }
        return coll;
    }
}