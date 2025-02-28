package com.web.jxp.tracker;
import com.web.jxp.base.Base;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONObject;
import static com.web.jxp.common.Common.print;

public class Tracker extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getTrackerByName(int assetIdIndex, int positionIdIndex, int mode, String search,
        int pcodeIdIndex)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        if(assetIdIndex > 0)
        {
            if(mode == 1)
            {
                sb.append("SELECT c.i_positionid, c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name,  t1.ct, t2.ct ");
                sb.append("FROM t_candidate AS c ");
                sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
                sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
                sb.append("LEFT JOIN (SELECT i_positionid, COUNT(1) AS ct FROM t_fcrole LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) WHERE t_pcode.i_status = 1 AND t_fcrole.i_status = 1 AND t_fcrole.i_clientassetid = ? GROUP BY i_positionid) AS t1 ON (t_position.i_positionid = t1.i_positionid) ");
                sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_tracker WHERE i_active = 1 AND i_clientassetid = ? GROUP BY i_candidateid) AS t2 ON (c.i_candidateid = t2.i_candidateid) ");
                sb.append("WHERE c.i_status = 1  AND c.i_clientassetid = ? ");
                if(search != null && !search.equals(""))
                    sb.append(" AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ?) ");  
                if(positionIdIndex > 0)
                    sb.append("AND c.i_positionid = ? ");                
                sb.append("ORDER BY c.s_firstname, c.s_middlename, c.s_lastname ");
            }
            else
            {
                sb.append("SELECT c.i_pcodeid, t_pcode.s_role, t_passessmenttype.s_name, t_priority.s_name, ");
                sb.append("t1.ct, c.i_passessmenttypeid, c.i_priorityid FROM t_fcrole AS c LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = c.i_pcodeid) ");
                sb.append("LEFT JOIN t_passessmenttype ON (t_passessmenttype.i_passessmenttypeid = c.i_passessmenttypeid) ");
                sb.append("LEFT JOIN t_priority ON (t_priority.i_priorityid = c.i_priorityid) ");
                sb.append("LEFT JOIN (SELECT t_fcrole.i_pcodeid, COUNT(DISTINCT(t_fcrole.i_positionid)) AS ct FROM t_fcrole LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) LEFT JOIN t_candidate ON (t_candidate.i_positionid = t_fcrole.i_positionid AND t_candidate.i_positionid2 = t_fcrole.i_positionid ) ");
                sb.append("WHERE t_pcode.i_status = 1 AND t_fcrole.i_clientassetid = ? GROUP BY t_fcrole.i_pcodeid) AS t1 ON (c.i_pcodeid = t1.i_pcodeid)");
                sb.append("WHERE c.i_status = 1 AND t_pcode.i_status = 1 ");
                if (assetIdIndex > 0)
                    sb.append(" AND c.i_clientassetid = ? ");
                if(search != null && !search.equals(""))
                    sb.append(" AND (t_pcode.s_role LIKE ? OR t_passessmenttype.s_name LIKE ? OR t_priority.s_name LIKE ?) ");  
                if (pcodeIdIndex > 0)
                    sb.append(" AND c.i_pcodeid = ? ");
                sb.append(" GROUP BY c.i_pcodeid, c.i_passessmenttypeid, c.i_priorityid ORDER BY t_pcode.s_role ");
            }
            String query = (sb.toString()).intern();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                if(mode == 1)
                {
                    pstmt.setInt(++scc, assetIdIndex);
                    pstmt.setInt(++scc, assetIdIndex);
                    pstmt.setInt(++scc, assetIdIndex);
                    if(search != null && !search.equals(""))
                    {
                        pstmt.setString(++scc, "%"+(search)+"%");
                        pstmt.setString(++scc, "%"+(search)+"%");
                    }
                    if (positionIdIndex > 0)
                        pstmt.setInt(++scc, positionIdIndex);
                }
                else
                {
                    pstmt.setInt(++scc, assetIdIndex);
                    if (assetIdIndex > 0)
                        pstmt.setInt(++scc, assetIdIndex);
                    if(search != null && !search.equals(""))
                    {
                        pstmt.setString(++scc, "%"+(search)+"%");
                        pstmt.setString(++scc, "%"+(search)+"%");
                        pstmt.setString(++scc, "%"+(search)+"%");
                    }
                    if (pcodeIdIndex > 0)
                        pstmt.setInt(++scc, pcodeIdIndex);
                }
                logger.info("getTrackerByName :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String name, positionName, gradeName;
                int pcount, total, positionId, candidateId;
                while (rs.next())
                {
                    if(mode == 1)
                    {
                        positionId = rs.getInt(1);
                        candidateId = rs.getInt(2);
                        name = rs.getString(3) != null ? rs.getString(3) : "";
                        positionName = rs.getString(4) != null ? rs.getString(4) : "";
                        gradeName = rs.getString(5) != null ? rs.getString(5) : "";
                        total = rs.getInt(6);
                        pcount = rs.getInt(7);
                        if(!gradeName.equals(""))
                            positionName += " - " + gradeName;
                        list.add(new TrackerInfo(positionId, candidateId, name, positionName, pcount, total, 
                            0, "", "", "", 0, 0));
                    }
                    else
                    {
                        int pcodeId = rs.getInt(1);
                        String role = rs.getString(2) != null ? rs.getString(2) : "";
                        String passessmenttypeName = rs.getString(3) != null ? rs.getString(3) : "";
                        String priorityName = rs.getString(4) != null ? rs.getString(4) : "";
                        total = rs.getInt(5);
                        int passessmenttypeId = rs.getInt(6);
                        int priorityId = rs.getInt(7);
                        list.add(new TrackerInfo(0, 0, "", "", 0, total, pcodeId, role, passessmenttypeName, 
                            priorityName, passessmenttypeId, priorityId));
                    }
                }
                rs.close();
                
                if(mode == 1)
                {
                    sb.append("SELECT c.i_positionid2, c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name,  t1.ct, t2.ct ");
                    sb.append("FROM t_candidate AS c ");
                    sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid2) ");
                    sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
                    sb.append("LEFT JOIN (SELECT i_positionid, COUNT(1) AS ct FROM t_fcrole LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) WHERE t_pcode.i_status = 1 AND t_fcrole.i_status = 1 AND t_fcrole.i_clientassetid = ? GROUP BY i_positionid) AS t1 ON (t_position.i_positionid = t1.i_positionid) ");
                    sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_tracker WHERE i_active = 1 AND i_clientassetid = ? GROUP BY i_candidateid) AS t2 ON (c.i_candidateid = t2.i_candidateid) ");
                    sb.append("WHERE c.i_status = 1  AND c.i_clientassetid = ? AND c.i_positionid2 > 0 ");
                    if(search != null && !search.equals(""))
                        sb.append(" AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ?) ");  
                    if(positionIdIndex > 0)
                        sb.append("AND c.i_positionid2 = ? ");                
                    sb.append("ORDER BY c.s_firstname, c.s_middlename, c.s_lastname ");
                }
                query = (sb.toString()).intern();
                sb.setLength(0);
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                scc = 0;
                if(mode == 1)
                {
                    pstmt.setInt(++scc, assetIdIndex);
                    pstmt.setInt(++scc, assetIdIndex);
                    pstmt.setInt(++scc, assetIdIndex);
                    if(search != null && !search.equals(""))
                    {
                        pstmt.setString(++scc, "%"+(search)+"%");
                        pstmt.setString(++scc, "%"+(search)+"%");
                    }
                    if (positionIdIndex > 0)
                        pstmt.setInt(++scc, positionIdIndex);
                }
                logger.info("getTrackerByName2 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    if(mode == 1)
                    {
                        positionId = rs.getInt(1);
                        candidateId = rs.getInt(2);
                        name = rs.getString(3) != null ? rs.getString(3) : "";
                        positionName = rs.getString(4) != null ? rs.getString(4) : "";
                        gradeName = rs.getString(5) != null ? rs.getString(5) : "";
                        total = rs.getInt(6);
                        pcount = rs.getInt(7);
                        if(!gradeName.equals(""))
                            positionName += " - " + gradeName;
                        list.add(new TrackerInfo(positionId, candidateId, name, positionName, pcount, total, 
                            0, "", "", "", 0, 0));
                    }
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
        }
        return list;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp)
    {
        ArrayList list = null;
        HashMap record = null;
        TrackerInfo info;
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
                    info = (TrackerInfo) l.get (i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = null;
            if(colId.equals("3"))
                map = sortById(record, tp);
            else if(colId.equals("4"))
                map = sortByDouble(record, tp);
            else
                map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            TrackerInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (TrackerInfo) l.get(i);
                    String str = getInfoValue(rInfo, colId);
                    if(str.equals(key))
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
    public String getInfoValue(TrackerInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getName()!= null ? info.getName() : "";    
        else if(i != null && i.equals("2"))
            infoval = info.getPositionName()!= null ? info.getPositionName() : "";
        else if(i != null && i.equals("3"))
            infoval = ""+info.getPcount(); 
        return infoval;
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
        coll.add(new TrackerInfo(-1, " Select Client"));
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
                coll.add(new TrackerInfo(refId, refName));
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
            coll.add(new TrackerInfo(-1, " Select Asset "));
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
                    coll.add(new TrackerInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new TrackerInfo(-1, " Select Asset "));
        }
        return coll;
    }
    
    public Collection getPositions(int clientassetId)
    {
        Collection coll = new LinkedList();
        coll.add(new TrackerInfo(-1, " All "));
        if(clientassetId > 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_position.i_positionid, t_position.s_name, t_grade.s_name ");
            sb.append("FROM t_clientassetposition ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_clientassetposition.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("WHERE t_position.i_status = 1 AND t_clientassetposition.i_clientassetid = ? ");
            sb.append("AND t_position.i_positionid IN (SELECT DISTINCT(i_positionid) FROM t_candidate WHERE i_clientassetid = ?) ");
            sb.append("ORDER BY t_position.s_name, t_grade.s_name ");
            String query = sb.toString();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                pstmt.setInt(2, clientassetId);
                logger.info("getPositions :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int id;
                String name, gradeName;
                while (rs.next())
                {
                    id = rs.getInt(1);  
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    gradeName = rs.getString(3) != null ? rs.getString(3) : "";
                    if(!gradeName.equals(""))
                        name += " - " + gradeName;
                    coll.add(new TrackerInfo(id, name));
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
        }
        return coll;
    }
    
    public ArrayList getFinalRecord2(ArrayList l, String colId, int tp)
    {
        ArrayList list = null;
        HashMap record = null;
        TrackerInfo info;
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
                    info = (TrackerInfo) l.get (i);
                    record.put(getInfoValue2(info, colId), info);
                }
            }
            Map map = null;
            if(colId.equals("2"))
                map = sortById(record, tp);
            else
                map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            TrackerInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (TrackerInfo) l.get(i);
                    String str = getInfoValue2(rInfo, colId);
                    if(str.equals(key))
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
    public String getInfoValue2(TrackerInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getRole() != null ? info.getRole() : "";
        else if(i != null && i.equals("2"))
            infoval = "" +info.getTotal() ;   
        return infoval;
    }
    
    public int[] getCounts(int assetIdIndex, int candidateId, int pcodeId, int tp)
    {
        int arr[] = new int[8];
        int total_positions = 0, total_candidate = 0, total_competencies = 0, unassigned = 0, inprogress = 0,
            pass = 0, fail = 0, appeals = 0, total_count = 0;
        if(assetIdIndex > 0)
        {            
            String query = ("SELECT COUNT(1) AS ct FROM t_tracker LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) WHERE t_fcrole.i_status = 1 AND t_tracker.i_clientassetid = ? "); //total assigned and others
            if(candidateId > 0)
                query += " AND t_tracker.i_candidateid = ? ";
            if(pcodeId > 0)
                query += " AND t_fcrole.i_pcodeid = ? ";
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                pstmt.setInt(++scc, assetIdIndex);
                if(candidateId > 0)
                    pstmt.setInt(++scc, candidateId);
                if(pcodeId > 0)
                    pstmt.setInt(++scc, pcodeId);
                logger.info("total_count :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    total_count = rs.getInt(1);                    
                }
                rs.close();
               
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT SUM(t1.ct) FROM t_candidate AS c ");
                sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
                sb.append("LEFT JOIN (SELECT i_positionid, COUNT(1) AS ct FROM t_fcrole LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) ");
                sb.append("WHERE t_pcode.i_status = 1 AND i_clientassetid = ? ");
                if(pcodeId > 0)
                    sb.append(" AND t_pcode.i_pcodeid = ? ");
                sb.append("and i_positionid IN (SELECT DISTINCT(i_positionid) FROM t_candidate WHERE i_clientassetid = ?) GROUP BY i_positionid) AS t1 ON (t_position.i_positionid = t1.i_positionid) ");
                sb.append("WHERE c.i_status = 1 AND c.i_clientassetid = ? ");
                if(candidateId > 0)
                    sb.append(" AND c.i_candidateid = ? ");
                query = sb.toString();
                sb.setLength(0);
                scc = 0;
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(++scc, assetIdIndex);
                if(pcodeId > 0)
                    pstmt.setInt(++scc, pcodeId);
                pstmt.setInt(++scc, assetIdIndex);
                pstmt.setInt(++scc, assetIdIndex);
                if(candidateId > 0)
                    pstmt.setInt(++scc, candidateId);
                logger.info("total_competencies p1 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    total_competencies = rs.getInt(1);                    
                }
                rs.close();
                
                sb.append("SELECT SUM(t1.ct) FROM t_candidate AS c ");
                sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid2) ");
                sb.append("LEFT JOIN (SELECT i_positionid, COUNT(1) AS ct FROM t_fcrole LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) ");
                sb.append("WHERE t_pcode.i_status = 1 AND i_clientassetid = ? ");
                if(pcodeId > 0)
                    sb.append(" AND t_pcode.i_pcodeid = ? ");
                sb.append("and i_positionid IN (SELECT DISTINCT(i_positionid2) FROM t_candidate WHERE i_clientassetid = ?) GROUP BY i_positionid) AS t1 ON (t_position.i_positionid = t1.i_positionid) ");
                sb.append("WHERE c.i_status = 1 AND c.i_clientassetid = ? ");
                if(candidateId > 0)
                    sb.append(" AND c.i_candidateid = ? ");
                query = sb.toString();
                sb.setLength(0);
                scc = 0;
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(++scc, assetIdIndex);
                if(pcodeId > 0)
                    pstmt.setInt(++scc, pcodeId);
                pstmt.setInt(++scc, assetIdIndex);
                pstmt.setInt(++scc, assetIdIndex);
                if(candidateId > 0)
                    pstmt.setInt(++scc, candidateId);
                logger.info("total_competencies p2 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    total_competencies += rs.getInt(1);                    
                }
                rs.close();
               
                unassigned = total_competencies - total_count;
                if(unassigned < 0)
                    unassigned = 0;
               
                query = ("SELECT COUNT(1) AS ct FROM t_tracker LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) WHERE t_tracker.i_active = 1 AND t_tracker.i_clientassetid = ? AND t_tracker.i_status IN (2,3) "); //pass
                if(candidateId > 0)
                    query += " AND t_tracker.i_candidateid = ? ";
                if(pcodeId > 0)
                    query += " AND t_fcrole.i_pcodeid = ? ";
                pstmt = conn.prepareStatement(query);
                scc = 0;
                pstmt.setInt(++scc, assetIdIndex);
                if(candidateId > 0)
                    pstmt.setInt(++scc, candidateId);
                if(pcodeId > 0)
                    pstmt.setInt(++scc, pcodeId);                
                logger.info("inprogress :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    inprogress = rs.getInt(1);                    
                }
                rs.close();
                query = ("SELECT COUNT(1) AS ct FROM t_tracker LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) WHERE t_tracker.i_active = 1 AND t_tracker.i_clientassetid = ? AND t_tracker.i_status = 4"); //pass
                if(candidateId > 0)
                    query += " AND t_tracker.i_candidateid = ? ";
                if(pcodeId > 0)
                    query += " AND t_fcrole.i_pcodeid = ? ";
                pstmt = conn.prepareStatement(query);
                scc = 0;
                pstmt.setInt(++scc, assetIdIndex);
                if(candidateId > 0)
                    pstmt.setInt(++scc, candidateId);
                if(pcodeId > 0)
                    pstmt.setInt(++scc, pcodeId);
                logger.info("pass :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    pass = rs.getInt(1);                    
                }
                rs.close();
                query = ("SELECT COUNT(1) AS ct FROM t_tracker LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) WHERE t_tracker.i_active = 1 AND t_tracker.i_clientassetid = ? AND t_tracker.i_status = 5 "); //fail
                if(candidateId > 0)
                    query += " AND t_tracker.i_candidateid = ? ";
                if(pcodeId > 0)
                    query += " AND t_fcrole.i_pcodeid = ? ";
                pstmt = conn.prepareStatement(query);
                scc = 0;
                pstmt.setInt(++scc, assetIdIndex);
                if(candidateId > 0)
                    pstmt.setInt(++scc, candidateId);
                if(pcodeId > 0)
                    pstmt.setInt(++scc, pcodeId);
                logger.info("fail :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    fail = rs.getInt(1);                    
                }
                rs.close();
               
                query = ("SELECT COUNT(1) AS ct FROM t_tracker LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) WHERE t_tracker.i_active = 1 AND t_tracker.i_clientassetid = ? AND t_tracker.i_status = 6 "); //appeal
                if(candidateId > 0)
                    query += " AND t_tracker.i_candidateid = ? ";
                if(pcodeId > 0)
                    query += " AND t_fcrole.i_pcodeid = ? ";
                pstmt = conn.prepareStatement(query);
                scc = 0;
                pstmt.setInt(++scc, assetIdIndex);
                if(candidateId > 0)
                    pstmt.setInt(++scc, candidateId);
                if(pcodeId > 0)
                    pstmt.setInt(++scc, pcodeId);
                logger.info("appeals :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    appeals = rs.getInt(1);                    
                }
                rs.close();
               
                if(tp == 1)
                {
                    query = ("SELECT COUNT(DISTINCT(i_positionid)) AS ct FROM t_candidate WHERE i_status = 1 AND i_positionid > 0 AND i_clientassetid = ? "); //expired
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, assetIdIndex);
                    logger.info("total_positions :: " + pstmt.toString());
                    rs = pstmt.executeQuery();
                    while (rs.next())
                    {
                        total_positions = rs.getInt(1);                    
                    }
                    rs.close();
                    
                    query = ("SELECT COUNT(DISTINCT(i_positionid2)) AS ct FROM t_candidate WHERE i_status = 1 AND i_positionid2 > 0 AND i_clientassetid = ? AND i_positionid2 NOT IN (SELECT i_positionid AS ct FROM t_candidate WHERE i_status = 1 AND i_positionid > 0 AND i_clientassetid = ? )"); //expired
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, assetIdIndex);
                    pstmt.setInt(2, assetIdIndex);
                    logger.info("total_positions :: " + pstmt.toString());
                    rs = pstmt.executeQuery();
                    while (rs.next())
                    {
                        total_positions += rs.getInt(1);                    
                    }
                    rs.close();
                   
                    query = ("SELECT COUNT(1) AS ct FROM t_candidate WHERE i_status = 1 AND i_clientassetid = ? "); //total_candidate
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, assetIdIndex);
                    logger.info("total_candidate :: " + pstmt.toString());
                    rs = pstmt.executeQuery();
                    while (rs.next())
                    {
                        total_candidate = rs.getInt(1);                    
                    }
                    rs.close();
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
        }
        arr[0] = total_positions;
        arr[1] = total_candidate;
        arr[2] = total_competencies;
        arr[3] = unassigned;
        arr[4] = inprogress;
        arr[5] = pass;
        arr[6] = fail;
        arr[7] = appeals;  
        return arr;
    }
    
    public TrackerInfo getBasicDetail(int candidateId)
    {
        TrackerInfo info = null;
        if(candidateId > 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name, t_client.s_name, t_clientasset.s_name, ");
            sb.append("c.i_clientid, c.i_clientassetid, c.i_positionid, c.i_positionid2, t_clientasset.i_assettypeid, ");
            sb.append("p2.s_name, g2.s_name ");
            sb.append("FROM t_candidate AS c ");
            sb.append("LEFT JOIN t_client ON (t_client.i_clientid = c.i_clientid) ");
            sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = c.i_clientassetid) ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("LEFT JOIN t_position AS p2 ON (p2.i_positionid = c.i_positionid2) ");
            sb.append("LEFT JOIN t_grade AS g2 ON (g2.i_gradeid = p2.i_gradeid) ");
            sb.append("WHERE c.i_candidateid = ?");
            String query = sb.toString();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, candidateId);
                logger.info("getBasicDetail :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String name, positionName, gradeName, clientName, assetName, position2, grade2;
                int clientId, clientassetId, positionId, positionId2, assettypeId;
                while (rs.next())
                {
                    name = rs.getString(1) != null ? rs.getString(1) : "";
                    positionName = rs.getString(2) != null ? rs.getString(2) : "";
                    gradeName = rs.getString(3) != null ? rs.getString(3) : "";
                    clientName = rs.getString(4) != null ? rs.getString(4) : "";
                    assetName = rs.getString(5) != null ? rs.getString(5) : "";
                    clientId = rs.getInt(6);
                    clientassetId = rs.getInt(7);
                    positionId = rs.getInt(8);
                    positionId2 = rs.getInt(9);
                    assettypeId = rs.getInt(10);
                    position2 = rs.getString(11) != null ? rs.getString(11) : "";
                    grade2 = rs.getString(12) != null ? rs.getString(12) : "";
                    if(!gradeName.equals(""))
                        positionName += " - " + gradeName;
                    if(!grade2.equals(""))
                        position2 += " - " + grade2;
                    info = new TrackerInfo(candidateId, clientId, clientassetId, name, positionName, 
                        clientName, assetName, positionId, positionId2, assettypeId, position2);
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
        }
        return info;
    }
    
    public String getOutcomeName(int st)
    {
        String str = "";
        switch (st) {
            case 1:
                str = "Competent";
                break;
            case 2:
                str = "Not Yet Competent";
                break;
                default:
            break;
        }
        return str;
    }
    
    public String getStatus(int st)
    {
        String str = "";
        switch (st) {
            case 1:
            case 0:
                str = "Unassigned";
                break;
            case 2:
                str = "In Progress";
                break;
            case 3:
                str = "Completed";
                break;
            case 4:
                str = "Competent";
                break;
            case 5:
                str = "Not Yet Competent";
                break;
            case 6:
                str = "Appealed";
                break;
            default:
                break;
        }
        return str;
    }
    
    public String getStColour(int st)
    {
        String s = "";
        if(st == 1 || st <= 0)
            s = "<span class='round_circle circle_unassigned'></span>";
        else if(st == 2)
            s = "<span class='round_circle circle_pending'></span>";
        else if(st == 3)
            s = "<span class='round_circle circle_pending'></span>";
        else if(st == 4)
            s = "<span class='round_circle circle_complete'></span>";
        else if(st == 5)
            s = "<span class='round_circle circle_exipred'></span>";
        else if(st == 6)
            s = "<span class='round_circle circle_exipry'></span>";
        return s;
    }
    
    public String getStColourModal(int st)
    {
        String s = "";
        if(st == 1 || st <= 0)
            s = "circle_unassigned";
        else if(st == 2 || st == 3)
            s = "circle_pending";
        else if(st == 4)
            s = "circle_complete";
        else if(st == 5)
            s = "circle_exipred";
        else if(st == 6)
            s = "circle_exipry";
        return s;
    }
    
    public ArrayList getListAssign1(int assetIdIndex, int positionIdIndex, 
        int candidateId, int passessmenttypeId, int priorityId, int ftype, String search, int cassessor, int uId)
    {
        ArrayList list = new ArrayList();
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t1.i_trackerid, t_pcode.s_role, t_passessmenttype.s_name, ");
            sb.append("t_priority.s_name, t1.i_status, t1.regdate, t_pcode.i_pcodeid, c.i_fcroleid, t1.i_average, t1.i_assessorid ");
            sb.append("FROM t_fcrole AS c ");
            sb.append("LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = c.i_pcodeid) ");
            sb.append("LEFT JOIN t_pdept ON (t_pdept.i_pdeptid = t_pcode.i_pdeptid) ");
            sb.append("LEFT JOIN t_passessmenttype ON (t_passessmenttype.i_passessmenttypeid = c.i_passessmenttypeid) ");
            sb.append("LEFT JOIN t_priority ON (t_priority.i_priorityid = c.i_priorityid) ");
            sb.append("LEFT JOIN (SELECT i_fcroleid, i_trackerid AS i_trackerid, i_status AS i_status, DATE_FORMAT(d_date, '%d %b %Y') AS regdate, i_average, i_assessorid ");
            sb.append("FROM t_tracker WHERE i_candidateid = ? AND i_active = 1) AS t1 ON (t1.i_fcroleid = c.i_fcroleid) ");
            sb.append("WHERE c.i_status = 1 AND t_pcode.i_status = 1 AND c.i_clientassetid = ? AND c.i_positionid = ? ");
            if(search != null && !search.equals(""))
                sb.append(" AND (t_pcode.s_code LIKE ? OR t_pcode.s_role LIKE ? OR t_passessmenttype.s_name LIKE ? OR t_priority.s_name LIKE ?) ");
            if(passessmenttypeId > 0)
                sb.append(" AND c.i_passessmenttypeid = ? ");
            if(priorityId > 0)
                sb.append(" AND c.i_priorityid = ? ");
            if(ftype > 0)
            {
                if(ftype == 1)
                    sb.append(" AND (t1.i_status <= 1 OR t1.i_status is NULL) ");
                else if(ftype >= 2)
                    sb.append(" AND (t1.i_status = "+ftype+") ");                
            }
            if(cassessor > 0)
                sb.append(" AND t1.i_assessorid = ? ");
            sb.append("ORDER BY t_pdept.s_name, t_pcode.s_code, t_pcode.s_role ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setInt(++scc, positionIdIndex);
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
            }
            if(passessmenttypeId > 0)
                pstmt.setInt(++scc, passessmenttypeId);
            if(priorityId > 0)
                pstmt.setInt(++scc, priorityId);
            if(cassessor > 0)
                pstmt.setInt(++scc, uId);
            logger.info("getListAssign1 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String role, passessmenttypeName, priorityName, date, statusval;
            int trackerId, status, pcodeId, fcroleId, average;
            while (rs.next())
            {
                trackerId = rs.getInt(1);
                role = rs.getString(2) != null ? rs.getString(2) : "";
                passessmenttypeName = rs.getString(3) != null ? rs.getString(3) : "";
                priorityName = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                date = rs.getString(6) != null ? rs.getString(6) : "";
                pcodeId = rs.getInt(7);
                fcroleId = rs.getInt(8);
                average = rs.getInt(9);
                statusval = getStatus(status);  
                list.add(new TrackerInfo(trackerId, role, passessmenttypeName, priorityName, status, 
                    date, statusval, pcodeId, fcroleId, average));
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
    
    public Collection getAssessmenttypes(int clientassetId, int positionId) 
    {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT(c.i_passessmenttypeid), t_passessmenttype.s_name ");
        sb.append("FROM t_fcrole AS c LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = c.i_pcodeid) ");
        sb.append("LEFT JOIN t_passessmenttype ON (t_passessmenttype.i_passessmenttypeid = c.i_passessmenttypeid) ");
        sb.append("WHERE c.i_status = 1 AND t_pcode.i_status = 1 AND c.i_clientassetid = ? AND c.i_positionid = ? ");
        sb.append("ORDER BY t_passessmenttype.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new TrackerInfo(-1, "Assessment Type"));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, positionId);
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) 
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new TrackerInfo(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
    
    public Collection getPriorities(int clientassetId, int positionId) 
    {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT(c.i_priorityid), t_priority.s_name ");
        sb.append("FROM t_fcrole AS c LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = c.i_pcodeid) ");
        sb.append("LEFT JOIN t_priority ON (t_priority.i_priorityid = c.i_priorityid) ");
        sb.append("WHERE c.i_status = 1 AND t_pcode.i_status = 1 AND c.i_clientassetid = ? AND c.i_positionid = ? ");
        sb.append("ORDER BY t_priority.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new TrackerInfo(-1, "Priority"));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, positionId);            
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) 
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new TrackerInfo(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
    
    public Collection getPcodes(int clientassetId) 
    {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT(c.i_pcodeid), t_pcode.s_role ");
        sb.append("FROM t_fcrole AS c LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = c.i_pcodeid) ");
        sb.append("WHERE c.i_status = 1 AND t_pcode.i_status = 1 AND c.i_clientassetid = ? ");
        sb.append("ORDER BY t_pcode.s_role ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new TrackerInfo(-1, " All "));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) 
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new TrackerInfo(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
    
    public TrackerInfo getTrackerInfo(int fcroleId, int trackerIdpar)
    {
        TrackerInfo info = null;
        try
        {
            conn = getConnection();
            String code = "", role = "", description = "", completebydate1 = "";
            int trackerId = 0, status = 0;
            if(fcroleId > 0)
            {
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT t_pcode.s_code, t_pcode.s_role, t_pcode.s_description ");
                sb.append("FROM t_fcrole LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) WHERE t_fcrole.i_fcroleid = ?");
                String query = sb.toString();
                sb.setLength(0);
            
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, fcroleId);
                logger.info("getTrackerInfo :: " + pstmt.toString());
                rs = pstmt.executeQuery();                
                while (rs.next())
                {
                    code = rs.getString(1) != null ? rs.getString(1) : "";
                    role = rs.getString(2) != null ? rs.getString(2) : "";
                    description = rs.getString(3) != null ? rs.getString(3) : "";                    
                }
                rs.close();
            }
            if(trackerIdpar > 0)
            {
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT t.i_trackerid, DATE_FORMAT(t.d_completebydate1, '%d-%b-%Y'), t.i_status ");
                sb.append("FROM t_tracker AS t WHERE t.i_trackerid = ?");
                String query = sb.toString();
                sb.setLength(0);
            
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, trackerIdpar);
                logger.info("getTrackerInfo :: " + pstmt.toString());
                rs = pstmt.executeQuery();                
                while (rs.next())
                {
                    trackerId = rs.getInt(1);
                    completebydate1 = rs.getString(2) != null ? rs.getString(2) : "";
                    status = rs.getInt(3);                  
                }
                rs.close();
            }
            info = new TrackerInfo(code, role, description, completebydate1, trackerId, status);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }    
    
    public ArrayList getAssessorList(int assetIdIndex, int positionId)
    {
        ArrayList list = new ArrayList();
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT u.i_userid, u.s_name, u.s_cvfile ");
            sb.append("FROM t_userlogin AS u LEFT JOIN t_userclient ON (u.i_userid = t_userclient.i_userid) ");
            sb.append("WHERE u.i_status = 1 AND FIND_IN_SET(?, t_userclient.s_clientassetids) AND FIND_IN_SET(?, t_userclient.s_positionids) ");
            sb.append("ORDER BY u.s_name ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assetIdIndex);
            pstmt.setInt(2, positionId);
            logger.info("getAssessorList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, fileName;
            int userId;
            while (rs.next())
            {
                userId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                fileName = rs.getString(3) != null ? rs.getString(3) : "";
                list.add(new TrackerInfo(userId, name, fileName));
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
    
    public ArrayList getMultipleRole(String ids)
    {
        ArrayList list = new ArrayList();
        if(ids != null && !ids.equals(""))
        {
            try
            {            
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT t_pcode.s_role, t_passessmenttype.s_name, t_priority.s_name ");
                sb.append("FROM t_fcrole AS c LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = c.i_pcodeid) ");
                sb.append("LEFT JOIN t_passessmenttype ON (t_passessmenttype.i_passessmenttypeid = c.i_passessmenttypeid) ");
                sb.append("LEFT JOIN t_priority ON (t_priority.i_priorityid = c.i_priorityid) ");
                sb.append("WHERE c.i_fcroleid IN ("+ids+") ORDER BY t_pcode.s_role");
                String query = (sb.toString()).intern();
                sb.setLength(0);

                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                logger.info("getMultipleRole :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String role, passessmenttypeName, priorityName;
                while (rs.next())
                {
                    role = rs.getString(1) != null ? rs.getString(1) : "";
                    passessmenttypeName = rs.getString(2) != null ? rs.getString(2) : "";
                    priorityName = rs.getString(3) != null ? rs.getString(3) : "";
                    list.add(new TrackerInfo(role, passessmenttypeName, priorityName));
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
        }
        return list;
    }
    
    public int assignassessment(int clientassetId, String ids, int status, String completeby, 
        String completeby2, int auserId, int userId, int candidateId, String username, int positionId) 
    {
        int id = 0;
        try
        {
            conn = getConnection();  
            if(ids != null && !ids.equals(""))
            {
                String arr[] = ids.split(",");
                int len = 0;
                if(arr != null)
                    len = arr.length;
                for(int i = 0; i < len; i++)
                {
                    int fcroleId = Integer.parseInt(arr[i]);
                    if(fcroleId > 0)
                    {
                        String cq = "SELECT i_trackerid FROM t_tracker WHERE i_active = 1 AND i_clientassetid = ? AND i_candidateid = ? AND i_fcroleid = ?";
                        pstmt = conn.prepareStatement(cq);
                        pstmt.setInt(1, clientassetId);
                        pstmt.setInt(2, candidateId);
                        pstmt.setInt(3, fcroleId);
                        logger.info("check  :: " + pstmt.toString());
                        rs = pstmt.executeQuery();
                        int trackerId = 0;
                        while (rs.next()) {
                            trackerId = rs.getInt(1);
                        }
                        if(trackerId > 0)
                        {
                            StringBuilder sb = new StringBuilder(); 
                            sb.append("UPDATE t_tracker SET i_status = ?, i_fcroleid = ?, d_completebydate1 = ?, d_completebydate2 = ?, ");
                            sb.append("i_assessorid = ?, i_userid = ?, ts_moddate = ? WHERE i_trackerid = ? ");
                            String query = (sb.toString()).intern();
                            sb.setLength(0);
                            pstmt = conn.prepareStatement(query);
                            pstmt.setInt(1, status);
                            pstmt.setInt(2, fcroleId);
                            pstmt.setString(3, changeDate1(completeby));
                            pstmt.setString(4, changeDate1(completeby2));
                            pstmt.setInt(5, auserId);
                            pstmt.setInt(6, userId);
                            pstmt.setString(7, currDate1());
                            pstmt.setInt(8, trackerId);
                            logger.info("Update :: " + pstmt.toString());
                            pstmt.executeUpdate();
                            createHistory(conn, trackerId, username, "Assign Updated");
                        }
                        else
                        {
                            StringBuilder sb = new StringBuilder();            
                            sb.append("INSERT INTO t_tracker ");                            
                            sb.append("(i_clientassetid, i_candidateid, i_fcroleid, d_completebydate1, d_completebydate2, i_assessorid, i_status, ");
                            sb.append("i_userid, ts_regdate, ts_moddate, i_positionid) ");
                            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
                            String query = (sb.toString()).intern();
                            sb.setLength(0);

                            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); 
                            pstmt.setInt(1, clientassetId);
                            pstmt.setInt(2, candidateId);
                            pstmt.setInt(3, fcroleId);
                            pstmt.setString(4, changeDate1(completeby));
                            pstmt.setString(5, changeDate1(completeby2));
                            pstmt.setInt(6, auserId);
                            pstmt.setInt(7, 2);
                            pstmt.setInt(8, userId);
                            pstmt.setString(9, currDate1());
                            pstmt.setString(10, currDate1());
                            pstmt.setInt(11, positionId);
                            logger.info("create :: " + pstmt.toString());
                            pstmt.executeUpdate();
                            rs = pstmt.getGeneratedKeys();
                            while (rs.next()) 
                            {
                                id = rs.getInt(1);
                            }
                            createHistory(conn, id, username, "Assigned");
                        }
                    }
                }                
            } 
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        } 
        finally {
            close(conn, pstmt, rs);
        }
        return id;
    }
    
    public TrackerInfo getBasicDetailAssign2(int pcodeId, int clientassetId)
    {
        TrackerInfo info = null;
        if(pcodeId > 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_pcode.s_role FROM t_pcode WHERE i_pcodeid = ?");
            String query = sb.toString();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, pcodeId);
                logger.info("getBasicDetailAssign2 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String role = "", clientName = "", assetName = "";
                while (rs.next())
                {
                    role = rs.getString(1) != null ? rs.getString(1) : "";
                }
                rs.close();
                
                sb.append("SELECT t_client.s_name, t_clientasset.s_name ");
                sb.append("FROM t_client LEFT JOIN t_clientasset ON (t_client.i_clientid = t_clientasset.i_clientid) ");
                sb.append("WHERE t_clientasset.i_clientassetid = ?");
                query = sb.toString();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                logger.info("getBasicDetailAssign2 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    clientName = rs.getString(1) != null ? rs.getString(1) : "";
                    assetName = rs.getString(2) != null ? rs.getString(2) : "";
                }
                rs.close();
                int total = 0;
                query = "SELECT COUNT(1) FROM t_fcrole WHERE i_status = 1 AND i_pcodeid = ? AND i_clientassetid = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, pcodeId);
                pstmt.setInt(2, clientassetId);
                logger.info("getBasicDetailAssign2 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    total = rs.getInt(1);
                }
                rs.close();
                info = new TrackerInfo(role, clientName, assetName, pcodeId, total); 
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                close(conn, pstmt, rs);
            }
        }
        return info;
    }
    
    public Collection getPositionsAssign2(int pcodeId, int clientassetId)
    {
        Collection coll = new LinkedList();
        coll.add(new TrackerInfo(-1, " All "));
        if(pcodeId > 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT DISTINCT t_fcrole.i_positionid, t_position.s_name, t_grade.s_name ");
            sb.append("FROM t_fcrole ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_fcrole.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("WHERE t_fcrole.i_status = 1 AND t_position.i_status = 1 AND t_fcrole.i_pcodeid = ? AND t_fcrole.i_clientassetid = ? ");
            sb.append("AND t_fcrole.i_positionid IN (SELECT DISTINCT(i_positionid) FROM t_candidate WHERE i_clientassetid = ?) ");
            sb.append("ORDER BY t_position.s_name, t_grade.s_name ");
            String query = sb.toString();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, pcodeId);
                pstmt.setInt(2, clientassetId);
                pstmt.setInt(3, clientassetId);
                logger.info("getPositionsAssign2 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int id;
                String name, gradeName;
                while (rs.next())
                {
                    id = rs.getInt(1);  
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    gradeName = rs.getString(3) != null ? rs.getString(3) : "";
                    if(!gradeName.equals(""))
                        name += " - " + gradeName;
                    coll.add(new TrackerInfo(id, name));
                }
                rs.close();
            }
            catch (Exception e)
            {
               logger.info("getPositionsAssign2 :: " + e.getMessage());
            }
            finally
            {
                close(conn, pstmt, rs);
            }
        }
        return coll;
    }
    
    public ArrayList getListAssign2(int assetIdIndex, int pcodeId, int ftype, String search, int positionId2Index, int cassessor, int uId)
    {
        ArrayList list = new ArrayList();
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, ");
            sb.append("t_grade.s_name, t2.i_trackerid, t2.status, t2.regdate, t2.i_average, t2.i_assessorid, t_position.i_positionid ");
            sb.append("FROM t_candidate AS c ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("LEFT JOIN (SELECT t_tracker.i_candidateid, t_fcrole.i_positionid AS positionid, t_tracker.i_trackerid, t_tracker.i_status AS status, DATE_FORMAT(t_tracker.d_date, '%d %b %Y') AS regdate, i_average, i_assessorid ");
            sb.append("FROM t_tracker LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) WHERE t_tracker.i_clientassetid = ? AND t_fcrole.i_pcodeid = ?  AND t_tracker.i_active= 1) AS t2 ON (t2.i_candidateid = c.i_candidateid AND c.i_positionid = t2.positionid) ");
            sb.append("WHERE c.i_status = 1 AND c.i_clientassetid = ? ");
            sb.append("AND c.i_positionid IN (SELECT DISTINCT(i_positionid) FROM t_fcrole WHERE i_pcodeid = ? AND i_clientassetid = ?) ");
            if(search != null && !search.equals(""))
                sb.append(" AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? OR t_position.s_name LIKE ? OR t_grade.s_name LIKE ?) ");
            if(positionId2Index > 0)
            {
                sb.append(" AND c.i_positionid = ?  ");
            }
            if(ftype > 0)
            {
                if(ftype == 1)
                    sb.append(" AND (t2.status <= 1 OR t2.status is NULL) ");
                else if(ftype >= 2)
                    sb.append(" AND (t2.status = "+ftype+") ");
            }
            if(cassessor > 0)
                sb.append(" AND t2.i_assessorid = ? ");
            sb.append("ORDER BY c.s_firstname, c.s_middlename, c.s_lastname ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setInt(++scc, pcodeId);
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setInt(++scc, pcodeId);
            pstmt.setInt(++scc, assetIdIndex);
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
            }
            if(positionId2Index > 0)
            {
                pstmt.setInt(++scc, positionId2Index);
            }
            if(cassessor > 0)
                pstmt.setInt(++scc, uId);
            logger.info("getListAssign2 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, positionName, gradeName, statusval, date, positionName2, gradeName2;
            int candidateId, status, trackerId, average, positionId, assessorId;
            while (rs.next())
            {
                candidateId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                positionName = rs.getString(3) != null ? rs.getString(3) : "";
                gradeName = rs.getString(4) != null ? rs.getString(4) : "";
                trackerId = rs.getInt(5);
                status = rs.getInt(6);
                date = rs.getString(7) != null ? rs.getString(7) : "";
                average = rs.getInt(8);
                assessorId = rs.getInt(9);
                positionId = rs.getInt(10);
                statusval = getStatus(status);   
                if(!gradeName.equals(""))
                    positionName += " - " + gradeName;
                
                list.add(new TrackerInfo(candidateId, name, positionName, status, statusval, date, trackerId, average, assessorId, positionId));
            }
            rs.close();
            
            sb.append("SELECT c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, ");
            sb.append("t_grade.s_name, t2.i_trackerid, t2.status, t2.regdate, t2.i_average, t2.i_assessorid, t_position.i_positionid ");
            sb.append("FROM t_candidate AS c ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid2) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("LEFT JOIN (SELECT t_tracker.i_candidateid, t_fcrole.i_positionid AS positionid, t_tracker.i_trackerid, t_tracker.i_status AS status, DATE_FORMAT(t_tracker.d_date, '%d %b %Y') AS regdate, i_average, i_assessorid ");
            sb.append("FROM t_tracker LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) WHERE t_tracker.i_clientassetid = ? AND t_fcrole.i_pcodeid = ?  AND t_tracker.i_active= 1) AS t2 ON (t2.i_candidateid = c.i_candidateid AND c.i_positionid2 = t2.positionid) ");
            sb.append("WHERE c.i_status = 1 AND c.i_clientassetid = ? AND c.i_positionid2 > 0 ");
            sb.append("AND c.i_positionid2 IN (SELECT DISTINCT(i_positionid) FROM t_fcrole WHERE i_pcodeid = ? AND i_clientassetid = ?) ");
            if(search != null && !search.equals(""))
                sb.append(" AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? OR t_position.s_name LIKE ? OR t_grade.s_name LIKE ?) ");
            if(positionId2Index > 0)
            {
                sb.append(" AND c.i_positionid2 = ?  ");
            }
            if(ftype > 0)
            {
                if(ftype == 1)
                    sb.append(" AND (t2.status <= 1 OR t2.status is NULL) ");
                else if(ftype >= 2)
                    sb.append(" AND (t2.status = "+ftype+") ");
            }
            if(cassessor > 0)
                sb.append(" AND t2.i_assessorid = ? ");
            sb.append("ORDER BY c.s_firstname, c.s_middlename, c.s_lastname ");
            query = (sb.toString()).intern();
            sb.setLength(0);
            
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            scc = 0;
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setInt(++scc, pcodeId);
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setInt(++scc, pcodeId);
            pstmt.setInt(++scc, assetIdIndex);
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
            }
            if(positionId2Index > 0)
            {
                pstmt.setInt(++scc, positionId2Index);
            }
            if(cassessor > 0)
                pstmt.setInt(++scc, uId);
            logger.info("getListAssign2 p2 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                candidateId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                positionName = rs.getString(3) != null ? rs.getString(3) : "";
                gradeName = rs.getString(4) != null ? rs.getString(4) : "";
                trackerId = rs.getInt(5);
                status = rs.getInt(6);
                date = rs.getString(7) != null ? rs.getString(7) : "";
                average = rs.getInt(8);
                assessorId = rs.getInt(9);
                positionId = rs.getInt(10);
                statusval = getStatus(status);   
                if(!gradeName.equals(""))
                    positionName += " - " + gradeName;
                
                list.add(new TrackerInfo(candidateId, name, positionName, status, statusval, date, trackerId, average, assessorId,positionId ));
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
    
    public TrackerInfo TrackInfo(int trackerId)
    {
        TrackerInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name, t_pcode.s_role, ");
        sb.append("t_passessmenttype.s_name, t_priority.s_name, t_fcrole.i_month, ul1.s_name, t.i_status, DATE_FORMAT(t.d_completebydate1, '%d %b %Y'), t_pcode.s_description, t. s_file1, t. s_file2,  ");
        sb.append("t.s_loginname1, t.s_loginname2,  DATE_FORMAT( t.ts_submitdate1, '%d %b %Y %H:%i' ), DATE_FORMAT( t.ts_submitdate2, '%d %b %Y %H:%i' ) , ");
        sb.append("t.s_remarks, t.i_score, t.s_remarks2, t.i_score2, t.i_competencyresultid, t.i_competencyresultid2, t.i_average, DATE_FORMAT(t.d_completebydate2, '%d %b %Y'), ");
        sb.append("c1.s_name, c2.s_name, t.i_clientassetid, c.i_positionid, t_coursename.s_name , t.i_outcomeid,  t.s_remarks3,  ");
        sb.append("t.s_remarks4, t_appeal.s_name, t_appealrejection.s_name, t.s_rejection, t.i_online, ul1.s_cvfile, t_pcode.i_assettypeid, DATEDIFF(CURRENT_DATE(), d_completedate), ");
        sb.append("p2.i_positionid, p2.s_name, g2.s_name ");
        sb.append("FROM t_tracker AS t ");        
        sb.append("LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t.i_fcroleid)  ");
        sb.append("LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) ");
        sb.append("LEFT JOIN t_passessmenttype ON (t_passessmenttype.i_passessmenttypeid = t_fcrole.i_passessmenttypeid) ");
        sb.append("LEFT JOIN t_priority ON (t_priority.i_priorityid = t_fcrole.i_priorityid) ");
        sb.append("LEFT JOIN t_candidate AS c ON (c.i_candidateid = t.i_candidateid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_position AS p2 ON (p2.i_positionid = c.i_positionid2) ");
        sb.append("LEFT JOIN t_grade AS g2 ON (g2.i_gradeid = p2.i_gradeid) ");
        sb.append("LEFT JOIN t_userlogin AS ul1 ON (ul1.i_userid = t.i_assessorid) ");
        sb.append("LEFT JOIN t_competencyresult AS c1 ON (c1.i_competencyresultid = t.i_competencyresultid) ");
        sb.append("LEFT JOIN t_competencyresult AS c2 ON (c2.i_competencyresultid = t.i_competencyresultid2) ");
        sb.append("LEFT JOIN t_coursename ON (t_coursename.i_coursenameid = t.i_trainingid) ");
        sb.append("LEFT JOIN t_appeal ON (t_appeal.i_appealid = t.i_appealid) ");
        sb.append("LEFT JOIN t_appealrejection ON (t_appealrejection.i_appealrejectionid = t.i_appealrejectionid) ");
        sb.append("WHERE t.i_trackerid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, trackerId);
            print(this,"TrackInfo :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name , positionname, gradename, role, assessment, priority, username, positionname2, gradename2,
            completeddate, description, file1, file2, loginname1, loginname2, submitdate1, 
            submitdate2, remarks, remarks2, completeddate2, resultname1, resultname2, remarks3, courseName, 
            outcomeStr, remarks4, appeal, rejectionReason, rejection, cvfile;
            int month, status, score, score2, resultId1, resultId2, average, clientassetId, positionId, outcomeId, onlineflag, assettypeId, noofdays, positionId2 ;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                positionname = rs.getString(2) != null ? rs.getString(2) : "";
                gradename = rs.getString(3) != null ? rs.getString(3) : "";
                role = rs.getString(4) != null ? rs.getString(4) : "";
                assessment = rs.getString(5) != null ? rs.getString(5) : "";
                priority = rs.getString(6) != null ? rs.getString(6) : "";   
                month = rs.getInt(7);
                username = rs.getString(8) != null ? rs.getString(8) : "";   
                status = rs.getInt(9);
                completeddate = rs.getString(10) != null ? rs.getString(10) : "";                    
                description = rs.getString(11) != null ? rs.getString(11) : "";                    
                file1 = rs.getString(12) != null ? rs.getString(12) : "";                    
                file2 = rs.getString(13) != null ? rs.getString(13) : "";                    
                loginname1 = rs.getString(14) != null ? rs.getString(14) : "";                    
                loginname2 = rs.getString(15) != null ? rs.getString(15) : "";                    
                submitdate1 = rs.getString(16) != null ? rs.getString(16) : "";                    
                submitdate2 = rs.getString(17) != null ? rs.getString(17) : "";                    
                remarks = rs.getString(18) != null ? rs.getString(18) : "";                    
                score = rs.getInt(19);
                remarks2 = rs.getString(20) != null ? rs.getString(20) : "";                    
                score2 = rs.getInt(21);
                resultId1 = rs.getInt(22);
                resultId2 = rs.getInt(23);
                average = rs.getInt(24);
                completeddate2 = rs.getString(25) != null ? rs.getString(25) : "";
                resultname1 = rs.getString(26) != null ? rs.getString(26) : "";
                resultname2 = rs.getString(27) != null ? rs.getString(27) : "";
                clientassetId = rs.getInt(28);
                positionId = rs.getInt(29);
                courseName = rs.getString(30) != null ? rs.getString(30): "";
                outcomeId = rs.getInt(31);
                remarks3 = rs.getString(32) != null ? rs.getString(32): "";
                remarks4 = rs.getString(33) != null ? rs.getString(33): "";
                appeal = rs.getString(34) != null ? rs.getString(34): "";
                rejectionReason = rs.getString(35) != null ? rs.getString(35): "";
                rejection = rs.getString(36) != null ? rs.getString(36): "";
                onlineflag = rs.getInt(37);
                cvfile = rs.getString(38) != null ? rs.getString(38): "";
                assettypeId = rs.getInt(39);
                noofdays = rs.getInt(40);
                positionId2 = rs.getInt(41);
                positionname2 = rs.getString(42) != null ? rs.getString(42) : "";
                gradename2 = rs.getString(43) != null ? rs.getString(43) : "";
                outcomeStr = getOutcomeName(outcomeId);
                if(!positionname.equals("")){
                    {
                        if(!gradename.equals(""))  {
                            positionname += " | " + gradename;
                        }
                    }
                }
                if(!positionname2.equals("")){
                    {
                        if(!gradename2.equals(""))  {
                            positionname2 += " | " + gradename2;
                        }
                    }
                }
                info = new TrackerInfo(name, positionname, role, assessment, priority, month, username, status, 
                completeddate, description, file1, file2, loginname1, loginname2, submitdate1, submitdate2,
                remarks, score, remarks2,  score2, resultId1, resultId2, average, completeddate2,  resultname1, 
                resultname2, clientassetId, positionId, courseName, outcomeId, remarks3, remarks4, 
                appeal, rejectionReason, rejection, onlineflag, cvfile, assettypeId, noofdays, outcomeStr, positionId2, positionname2);
            }
            rs.close();
        }
        catch (Exception exception)
        {
            print(this,"TrackInfo :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public Collection getCompetencyResult()
    {
        Collection coll = new LinkedList();
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("appealreason.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new TrackerInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
    
    public Collection getRejectionReason()
    {
        Collection coll = new LinkedList();
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("appealrejection.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new TrackerInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
    
    public Collection getTraining(int clientassetId, int positionId) 
    {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientmatrixdetail.i_courseid, t_coursename.s_name ");
        sb.append("FROM t_clientmatrixdetail ");
        sb.append("LEFT JOIN  t_course ON (t_course.i_courseid = t_clientmatrixdetail.i_courseid) ");
        sb.append("LEFT JOIN t_coursename ON ((t_course.i_coursenameid) = t_coursename.i_coursenameid) ");
        sb.append("LEFT JOIN t_category ON (t_category.i_categoryid = t_clientmatrixdetail.i_categoryid) ");
        sb.append("LEFT JOIN  t_subcategory ON (t_subcategory.i_subcategoryid = t_clientmatrixdetail.i_subcategoryid) ");
        sb.append("WHERE t_category.i_status = 1 AND t_subcategory.i_status = 1 AND t_course.i_status = 1 AND t_clientmatrixdetail.i_clientassetid = ? ");
        sb.append("AND t_clientmatrixdetail.i_positionid = ? ORDER BY t_coursename.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new TrackerInfo(-1, "Select Training"));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, positionId);
            logger.info("getTraining :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) 
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new TrackerInfo(id, name));
            }
        } catch (Exception e) {
           print(this,"getTraining :: " + e.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
    
    //SaveUpdate tracker
    public int updatetracker(TrackerInfo info, String username)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE  t_tracker SET ");
            sb.append("s_remarks = ?, ");
            sb.append("i_score = ?, ");
            sb.append("i_competencyresultid = ?, ");
            sb.append("i_competencyresultid2 = ?, ");
            sb.append("ts_moddate = ?, ");
            sb.append("i_status = ?, ");
            sb.append("s_remarks2 = ?, ");
            sb.append("i_score2 = ?, ");            
            if(info.getFileName() != null && !info.getFileName().equals("") && info.getLoginname1().equals(""))
            {
                sb.append("s_loginname1 = ?, ");
                sb.append("ts_submitdate1 = ?, ");
            }
            if(info.getFileName2() != null && !info.getFileName2().equals("")  && info.getLoginname2().equals(""))
            {
                sb.append("s_loginname2 = ?, ");
                sb.append("ts_submitdate2 = ?, ");
            }
            if (info.getFileName()!= null && !info.getFileName().equals("")) 
            {
                sb.append("s_file1 = ?, ");
            }
            if (info.getFileName2()!= null && !info.getFileName2().equals("")) 
            {
                sb.append("s_file2 = ?, ");
            }
            sb.append("i_average = ? ");
            sb.append("WHERE  i_trackerid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getRemarks());
            pstmt.setInt(++scc, info.getScore());
            pstmt.setInt(++scc, info.getResultId());
            pstmt.setInt(++scc, info.getResultId2());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setString(++scc, info.getPracticalremarks());
            pstmt.setInt(++scc, info.getPrcaticalscore());
            if (info.getFileName() != null && !info.getFileName().equals("")  && info.getLoginname1().equals("")) 
            {
                pstmt.setString(++scc, username);
                pstmt.setString(++scc, currDate1());
            }
            if (info.getFileName2() != null && !info.getFileName2().equals("")  && info.getLoginname2().equals("")) 
            {
                pstmt.setString(++scc, username);
                pstmt.setString(++scc, currDate1());
            }
            
            if (info.getFileName()!= null && !info.getFileName().equals("")) 
            {
                pstmt.setString(++scc, info.getFileName());
            }
            if (info.getFileName2()!= null && !info.getFileName2().equals("")) 
            {
                pstmt.setString(++scc, info.getFileName2());
            }
            pstmt.setInt(++scc, info.getAverage());
            pstmt.setInt(++scc, info.getTrackerId());
            print(this,"updatetracker :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            String action = "";
            if(info.getStatus() == 2)
                action = "Partial Submitted";
            else if(info.getStatus() == 3)
                action = "Submitted";
            createHistory(conn, info.getTrackerId(), username, action);
        }
        catch (Exception exception)
        {
            print(this,"updatetracker :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int updateTrackerStatus(int trackerId, int trainingId, int outcomeId, String outcomeremarks, String username, int month)
    {
        int cc = 0;
        try
        {
            String validupto = getDateAfter(currDate(), 2, month, "yyyy-MM-dd", "yyyy-MM-dd");
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE  t_tracker SET ");
            sb.append("s_remarks3 = ?, ");
            sb.append("i_trainingid = ?, ");
            sb.append("i_outcomeid = ?, ");
            sb.append("d_completedate = ?, ");
            sb.append("i_status = ?, ");            
            if(outcomeId == 1)
            {                
                sb.append("d_date = ?, ");                
            }            
            sb.append("ts_moddate = ? ");
            sb.append("WHERE  i_trackerid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, outcomeremarks);
            pstmt.setInt(++scc, trainingId);
            pstmt.setInt(++scc, outcomeId);
            pstmt.setString(++scc, currDate());
            if(outcomeId == 2)           
                pstmt.setInt(++scc, 5);     
            else if(outcomeId == 1)
            {
                pstmt.setInt(++scc, 4);                
                pstmt.setString(++scc, validupto);
            }
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, trackerId);
            print(this,"updateTrackerStatus :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            String action = "";
            if(outcomeId == 2)
                action = "Not Yet Competent";
            else if(outcomeId == 1)
                action = "Competent";
            createHistory(conn, trackerId, username, action);
        }
        catch (Exception exception)
        {
            print(this,"updateTrackerStatus :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int reasign(int trackerId, String username, int userId)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE  t_tracker SET ");
            sb.append("i_active = 3, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE  i_trackerid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, trackerId);
            print(this,"reasign :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createHistory(conn, trackerId, username, "Reassigned");
        }
        catch (Exception exception)
        {
            print(this,"reasign :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int updateAppeal(int trackerId, String rejection, int reasonId, String username)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE  t_tracker SET ");
            sb.append("s_rejection = ?, ");
            sb.append("i_reasonid = ?, ");
            sb.append("i_status = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE  i_trackerid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, rejection);
            pstmt.setInt(++scc, reasonId);
            if(rejection.equals("Approved"))
                pstmt.setInt(++scc, 3);
            else
                pstmt.setInt(++scc, 5);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, trackerId);
            print(this,"updateAppeal :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            String action = "";
             if(rejection.equals("Approved"))
                action = "Appeal Approved";
             else if(rejection.equals("Reject"))
                action = "Appeal Reject";
            createHistory(conn, trackerId, username, action);
        }
        catch (Exception exception)
        {
            print(this,"updateAppeal :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int assignassessment2(int clientassetId, int positionId,  String crids, int status, String completeby, String completebydate2,  
        int auserId, int userId, int candidateId, String username) 
    {
        int id = 0;
        try
        {
            conn = getConnection(); 
            if(crids != null && !crids.equals(""))
            {
                String arr[] = crids.split(",");
                int len = 0;
                if(arr != null)
                    len = arr.length;
                for(int i = 0; i < len; i++)
                {
                    int fcroleId = Integer.parseInt(arr[i]);
                    if(fcroleId > 0)
                    {
                        if(fcroleId > 0)
                        {
                            if(status == 2)
                            {
                                String query = "SELECT i_month FROM t_fcrole WHERE i_fcroleid = ?";
                                pstmt = conn.prepareStatement(query);
                                pstmt.setInt(1, fcroleId);
                                rs = pstmt.executeQuery();
                                int month = 0;
                                while (rs.next()) 
                                {
                                    month = rs.getInt(1);
                                }
                                rs.close();
                            }
                            String cq = "SELECT i_trackerid FROM t_tracker WHERE i_active = 1 AND i_clientassetid = ? AND i_candidateid = ? AND i_fcroleid = ?";
                            pstmt = conn.prepareStatement(cq);
                            pstmt.setInt(1, clientassetId);
                            pstmt.setInt(2, candidateId);
                            pstmt.setInt(3, fcroleId);
                            logger.info("check  :: " + pstmt.toString());
                            rs = pstmt.executeQuery();
                            int trackerId = 0;
                            while (rs.next()) {
                                trackerId = rs.getInt(1);
                            }
                            if(trackerId > 0)
                            {
                                StringBuilder sb = new StringBuilder(); 
                                sb.append("UPDATE t_tracker SET i_status = ?, i_fcroleid = ?, d_completebydate1 = ?, d_completebydate2 = ?, ");
                                sb.append("i_assessorid = ?, i_userid = ?, ts_moddate = ? WHERE i_trackerid = ? ");
                                String query = (sb.toString()).intern();
                                sb.setLength(0);
                                pstmt = conn.prepareStatement(query);
                                pstmt.setInt(1, status);
                                pstmt.setInt(2, fcroleId);
                                pstmt.setString(3, changeDate1(completeby));
                                pstmt.setString(4, changeDate1(completebydate2));
                                pstmt.setInt(5, auserId);
                                pstmt.setInt(6, userId);
                                pstmt.setString(7, currDate1());
                                pstmt.setInt(8, trackerId);
                                print(this, "update :: " + pstmt.toString());
                                pstmt.executeUpdate();
                                createHistory(conn, id, username, "Assign Updated");
                            }
                            else
                            {
                                StringBuilder sb = new StringBuilder();            
                                sb.append("INSERT INTO t_tracker ");
                                sb.append("(i_clientassetid, i_candidateid, i_fcroleid, d_completebydate1, i_assessorid, i_status, ");
                                sb.append("i_userid, ts_regdate, ts_moddate, d_completebydate2, i_positionid) ");
                                sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                                String query = (sb.toString()).intern();
                                sb.setLength(0);

                                pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); 
                                pstmt.setInt(1, clientassetId);
                                pstmt.setInt(2, candidateId);
                                pstmt.setInt(3, fcroleId);
                                pstmt.setString(4, changeDate1(completeby));
                                pstmt.setInt(5, auserId);
                                pstmt.setInt(6, 2);
                                pstmt.setInt(7, userId);
                                pstmt.setString(8, currDate1());
                                pstmt.setString(9, currDate1());
                                pstmt.setString(10, changeDate1(completebydate2));
                                pstmt.setInt(11, positionId);
                                logger.info("create :: " + pstmt.toString());
                                pstmt.executeUpdate();
                                rs = pstmt.getGeneratedKeys();
                                while (rs.next()) 
                                {
                                    id = rs.getInt(1);
                                }
                                createHistory(conn, id, username, "Assigned");
                            }
                        }
                    }
                }                
            } 
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        } 
        finally {
            close(conn, pstmt, rs);
        }
        return id;
    }
    
    public TrackerInfo getAssessmentTypeName(int positionId, int pcodeId)
    {
        TrackerInfo info = null;
        int fcroleId = 0;
        String name = "";
        try
        {
            conn = getConnection();
            if(positionId > 0 && pcodeId > 0)
            {
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT t_fcrole.i_fcroleid, t_passessmenttype.s_name  ");
                sb.append("FROM t_fcrole LEFT JOIN t_passessmenttype ON (t_passessmenttype.i_passessmenttypeid = t_fcrole.i_passessmenttypeid) WHERE t_fcrole.i_positionid = ? AND t_fcrole.i_pcodeid = ? ");
                String query = sb.toString();
                sb.setLength(0);
            
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, positionId);
                pstmt.setInt(2, pcodeId);
                logger.info("getAssessmentTypeName :: " + pstmt.toString());
                rs = pstmt.executeQuery();                
                while (rs.next())
                {
                    fcroleId = rs.getInt(1);
                    name = rs.getString(2) != null ? rs.getString(2) : ""; 
                    info = new TrackerInfo(fcroleId, name);
                }
                rs.close();
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
        return info;
    }
    
    public int createHistory(Connection conn, int trackerId, String username, String action) 
    {
        int clientId = 0;
        try 
        {
            String query = ("INSERT INTO t_thistory (i_trackerid, s_username, s_action, ts_regdate) VALUES (?, ?, ?, ?)");
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, trackerId);
            pstmt.setString(2, username);
            pstmt.setString(3, action);
            pstmt.setString(4, currDate1());
            //print(this,"createHistory :: " + pstmt.toString());
            pstmt.executeUpdate();
        } 
        catch (Exception exception) {
            print(this, "createHistory :: " + exception.getMessage());
        } finally {
            close(null, pstmt, rs);
        }
        return clientId;
    }
    
    public ArrayList getHistory(int trackerId)
    {
        ArrayList list = new ArrayList();
        if(trackerId > 0)
        {
            try
            {            
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT DATE_FORMAT(ts_regdate, '%d %b %Y %H:%i'), s_username, s_action ");
                sb.append("FROM t_thistory WHERE i_trackerid = ? ORDER BY ts_regdate");
                String query = (sb.toString()).intern();
                sb.setLength(0);
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, trackerId);
                logger.info("getHistory :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String role, passessmenttypeName, priorityName;
                while (rs.next())
                {
                    role = rs.getString(1) != null ? rs.getString(1) : "";
                    passessmenttypeName = rs.getString(2) != null ? rs.getString(2) : "";
                    priorityName = rs.getString(3) != null ? rs.getString(3) : "";
                    list.add(new TrackerInfo(role, passessmenttypeName, priorityName));
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
        }
        return list;
    }
    
    public ArrayList getQuestionList(int trackerId)
    {
        ArrayList list = new ArrayList();
        if(trackerId > 0)
        {
            try
            {            
                StringBuilder sb = new StringBuilder();                
                sb.append("SELECT t_pcategory.s_name, t_pquestion.s_name, t_trackerdetail.s_answer, ");
                sb.append("DATE_FORMAT(t_tracker.d_onlinedate, '%d %b %Y') FROM t_trackerdetail ");
                sb.append("LEFT JOIN t_pquestion ON (t_pquestion.i_pquestionid = t_trackerdetail.i_pquestionid) ");
                sb.append("LEFT JOIN t_pcategory ON (t_pcategory.i_pcategoryid = t_pquestion.i_pcategoryid) ");
                sb.append("LEFT JOIN t_tracker ON (t_tracker.i_trackerid  = t_trackerdetail.i_trackerid) ");
                sb.append("WHERE t_trackerdetail.i_trackerid = ? ");
                sb.append("ORDER BY t_pcategory.s_name, t_pquestion.s_name");
                String query = (sb.toString()).intern();
                sb.setLength(0);
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, trackerId);
                logger.info("getQuestionList :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String categoryName, questionName, answer = "", temp = "", onlinedate;
                while(rs.next())
                {
                    categoryName = rs.getString(1) != null ? rs.getString(1): "";
                    questionName = rs.getString(2) != null ? rs.getString(2): "";
                    answer = rs.getString(3) != null ? rs.getString(3): "";
                    onlinedate = rs.getString(4) != null ? rs.getString(4): "";
                    if(!temp.equals(categoryName))
                    {
                        list.add(new QuestionInfo(categoryName, questionName, answer, onlinedate));
                            temp = categoryName;
                    }
                    else
                    {
                        list.add(new QuestionInfo("", questionName, answer, onlinedate));
                    }
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
        }
        return list;
    }
    
    public int getPositionByTrackerId(int trackerId) {
        int id = 0;        
        String query = ("SELECT i_positionid FROM t_tracker WHERE i_trackerid= ? AND i_active = 1").intern();
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, trackerId);
            print(this, "getPositionByTrackerId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }    
        return id;
    }
}