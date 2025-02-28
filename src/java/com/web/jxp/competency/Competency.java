package com.web.jxp.competency;

import com.web.jxp.base.Base;
import com.web.jxp.common.Common;
import static com.web.jxp.common.Common.print;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

public class Competency extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
        
     // For Department
    public ArrayList getDepartmentList(int clientassetId, String searchDept) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT t_pdept.i_pdeptid, t_pdept.s_name ");
        sb.append("FROM t_fcrole ");
        sb.append("LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) ");
        sb.append("LEFT JOIN t_pdept ON (t_pdept.i_pdeptid = t_pcode.i_pdeptid) ");
        sb.append("WHERE t_fcrole.i_status = 1 AND t_pcode.i_status = 1 AND t_fcrole.i_clientassetid = ? ");
        if (searchDept != null && !searchDept.equals("")) 
        {
            sb.append("AND (t_pdept.s_name like ?) ");
        }
        sb.append("ORDER BY t_pdept.s_name");
        
        String query = sb.toString().intern();
        sb.setLength(0);        
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            if (searchDept != null && !searchDept.equals(""))
            {
                pstmt.setString(2, "%" + (searchDept) + "%");
            }
            Common.print(this, "getDepartmentList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int id;
            while (rs.next())
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                list.add(new CompetencyInfo(id, name));
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
   
    public ArrayList getPositionList(int clientassetId, String searchPosition) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT t_position.i_positionid, t_position.s_name, t_grade.s_name ");
        sb.append("FROM t_clientassetposition ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_clientassetposition.i_clientassetid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_clientassetposition.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("WHERE t_position.i_status = 1 AND t_clientasset.i_clientassetid = ?  ");
        sb.append("AND t_clientassetposition.i_positionid IN (SELECT i_positionid FROM t_candidate WHERE i_clientassetid = ?) ");
        if (searchPosition != null && !searchPosition.equals("")) 
        {
            sb.append("AND (t_position.s_name like ? OR t_grade.s_name like ?) ");
        }
        sb.append("ORDER BY t_position.s_name, t_grade.s_name ");
        
       
        String query = sb.toString().intern();
        sb.setLength(0);        
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, clientassetId);
            if (searchPosition != null && !searchPosition.equals(""))
            {
                pstmt.setString(3, "%" + (searchPosition) + "%");
                pstmt.setString(4, "%" + (searchPosition) + "%");
            }
            Common.print(this, "getPositionList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String position, grade;
            int id;
            while (rs.next())
            {
                id = rs.getInt(1);
                position = rs.getString(2) != null ? rs.getString(2) : "";
                grade = rs.getString(3) != null ? rs.getString(3) : "";
                if(!position.equals(""))
                {
                    if(!grade.equals(""))
                    {
                        position += " | " + grade;
                    }
                }
                list.add(new CompetencyInfo(id, position));
            }
            rs.close();
            pstmt.close();            
        } catch (Exception e) {
            print(this, "getPositionList ::" + e.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    // For personnel
    public ArrayList getCandidateList(int clientassetId, String searchName) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT cr.i_crewrotationid, CONCAT_WS(' ', s_firstname, s_middlename,s_lastname) ");
        sb.append("FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_crewrotation AS cr ON (c.i_candidateid = cr.i_candidateid) ");
        sb.append("WHERE cr.i_active = 1 AND cr.i_clientassetid = ? ");    
        if(searchName != null && !searchName.equals(""))
        {
            sb.append("AND (c.s_firstname LIKE ?  OR c.s_middlename LIKE ? OR c.s_lastname LIKE ?) ");
        }
        sb.append("ORDER BY c.s_firstname, c.s_middlename,c.s_lastname");
             
        String query = sb.toString().intern();
        sb.setLength(0);        
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            if (searchName != null && !searchName.equals(""))
            {
                pstmt.setString(2, "%" + (searchName) + "%");
                pstmt.setString(3, "%" + (searchName) + "%");
                pstmt.setString(4, "%" + (searchName) + "%");
            }
            Common.print(this, "getCandidateList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int id;
            while (rs.next())
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                list.add(new CompetencyInfo(id, name));
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
    
    // For Competency role
    public ArrayList getCompetencyRoleList(int clientassetId, String searchRole) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT t_pcode.i_pcodeid, t_pcode.s_role ");
        sb.append("FROM t_fcrole ");
        sb.append("LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) ");
        sb.append("WHERE t_fcrole.i_status = 1 AND t_pcode.i_status = 1 AND t_fcrole.i_clientassetid = ? ");
        if(searchRole != null && !searchRole.equals(""))
        {
            sb.append("AND (t_pcode.s_role LIKE ?) ");
        }
        sb.append("ORDER BY t_pcode.s_role ");
        
        String query = sb.toString().intern();
        sb.setLength(0);        
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            if (searchRole != null && !searchRole.equals(""))
            {
                pstmt.setString(2, "%" + (searchRole) + "%");                
            }
            Common.print(this, "getCompetencyRoleList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int id;
            while (rs.next())
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                list.add(new CompetencyInfo(id, name));
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
    
    // For competency table
    public ArrayList getRoleListForTable(int clientassetId, String dids, String rolids) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT t_pcode.i_pcodeid, t_pcode.s_role ");
        sb.append("FROM t_fcrole ");
        sb.append("LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) ");
        sb.append("WHERE t_fcrole.i_status = 1 AND t_pcode.i_status = 1 AND t_fcrole.i_clientassetid = ? ");
        if(dids != null && !dids.equals(""))
        {
            sb.append(" AND t_pcode.i_pdeptid IN ("+dids+") ");
        }
        if(rolids != null && !rolids.equals(""))
        {
            sb.append(" AND t_fcrole.i_pcodeid IN ("+rolids+") ");
        }
        sb.append("ORDER BY t_pcode.s_role ");
        
        String query = sb.toString().intern();
        sb.setLength(0);        
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            Common.print(this, "getRoleListForTable :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int id;
            while (rs.next())
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                list.add(new CompetencyInfo(id, name));
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
    
    //For graph count
    public ArrayList getPositionCountList(int clientassetId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t1.positionid, t1.positionname, t1.gradename, t2.ct, t3.ct, t4.ct ");
        sb.append("FROM (SELECT DISTINCT t_position.i_positionid AS positionid, t_position.s_name AS positionname, t_grade.s_name AS gradename ");
        sb.append("FROM t_fcrole ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_fcrole.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) WHERE t_position.i_status = 1 AND t_fcrole.i_clientassetid = ? ");
        sb.append("AND t_fcrole.i_positionid IN (SELECT i_positionid FROM t_candidate WHERE i_clientassetid = ?) ORDER BY t_position.s_name, t_grade.s_name) AS t1 ");
        sb.append("LEFT JOIN (SELECT t_fcrole.i_positionid AS i_positionid,COUNT(1) AS ct FROM t_tracker ");
        sb.append("LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) WHERE t_tracker.i_active = 1 AND t_tracker.i_status = 4 AND t_tracker.i_clientassetid = ? ");
        sb.append("GROUP BY t_fcrole.i_positionid ) AS t2 ON (t2.i_positionid = t1.positionid) ");
        sb.append("LEFT JOIN (SELECT t_fcrole.i_positionid AS i_positionid, COUNT(1) AS ct FROM t_tracker ");
        sb.append("LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) WHERE t_tracker.i_active = 1 AND t_tracker.i_status = 5 AND t_tracker.i_clientassetid = ? ");
        sb.append("GROUP BY t_fcrole.i_positionid ) AS t3 ON (t3.i_positionid = t1.positionid) ");
        sb.append("LEFT JOIN (SELECT t_fcrole.i_positionid AS i_positionid, COUNT(1) AS ct FROM t_tracker ");
        sb.append("LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) WHERE t_tracker.i_active = 1 AND t_tracker.i_status IN (2,3) AND t_tracker.i_clientassetid = ? ");
        sb.append("GROUP BY t_fcrole.i_positionid ) AS t4 ON (t4.i_positionid = t1.positionid) ");             
        String query = sb.toString().intern();
        sb.setLength(0);     
        
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, clientassetId);
            pstmt.setInt(3, clientassetId);
            pstmt.setInt(4, clientassetId);
            pstmt.setInt(5, clientassetId);
            Common.print(this, "getPositionCountList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String position, grade;
            int id, count1, count2, count3;
            while (rs.next())
            {
                id = rs.getInt(1);
                position = rs.getString(2) != null ? rs.getString(2) : "";
                grade = rs.getString(3) != null ? rs.getString(3) : "";
                count1 = rs.getInt(4);
                count2 = rs.getInt(5);
                count3 = rs.getInt(6);
                if(!position.equals(""))
                {
                    if(!grade.equals(""))
                    {
                        position += " | " + grade;
                    }
                }
                list.add(new CompetencyInfo(id, position, count1, count2, count3));
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
    
    //Count query
    public int[] getCountsCompetency(int assetIdIndex)
    {
        int arr[] = new int[17];
        int onshore = 0, offshore = 0, totalcrew = 0, total_positions = 0, assigned_positions = 0,
        assigned_crew = 0, unassigned_crew = 0, pass = 0, fail = 0, appeal = 0, totaltracker = 0,
        pendingtracker = 0, expired1 = 0, expired2 = 0, expired3 = 0, expired = 0, total_competencies= 0;
        if(assetIdIndex > 0)
        {            
            String query = "SELECT SUM(i_status1) AS ct1, SUM(if(i_status2 >= 1, 1, 0)) AS ct2 FROM t_crewrotation WHERE i_active = 1 AND i_clientassetid = ?";
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("totalcrew :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    onshore = rs.getInt(1);
                    offshore = rs.getInt(2);
                }
                rs.close();
                totalcrew = onshore + offshore;
               
                query = ("SELECT COUNT(1) FROM t_clientassetposition WHERE i_status = 1 AND i_positionid > 0 AND i_clientassetid = ? "); //total_positions
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("total_positions :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    total_positions = rs.getInt(1);
                }
                rs.close();
               
                query = ("SELECT COUNT(DISTINCT(t_fcrole.i_positionid)) FROM t_tracker LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) WHERE t_fcrole.i_status = 1 AND t_tracker.i_clientassetid = ?"); //assigned_positions
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("assigned_positions :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    assigned_positions = rs.getInt(1);
                }
                rs.close();
               
                query = ("SELECT COUNT(DISTINCT(i_candidateid)) FROM t_tracker WHERE i_active = 1 AND i_clientassetid = ?"); //assigned_crew
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("assigned_crew :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    assigned_crew = rs.getInt(1);
                }
                rs.close();
                unassigned_crew = totalcrew - assigned_crew;
                
                query = ("SELECT COUNT(1) AS ct FROM t_tracker WHERE t_tracker.i_active = 1 AND t_tracker.i_clientassetid = ?"); //pass
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("totaltracker :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    totaltracker = rs.getInt(1);
                }
                rs.close();
               
                query = ("SELECT COUNT(1) AS ct FROM t_tracker WHERE t_tracker.i_active = 1 AND t_tracker.i_clientassetid = ? AND t_tracker.i_status = 4"); //pass
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("pass :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    pass = rs.getInt(1);
                }
                rs.close();
                
                query = ("SELECT COUNT(1) AS ct FROM t_tracker WHERE t_tracker.i_active = 1 AND t_tracker.i_clientassetid = ? AND t_tracker.i_status = 5 "); //fail
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("fail :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    fail = rs.getInt(1);
                }
                rs.close();
               
                query = ("SELECT COUNT(1) AS ct FROM t_tracker WHERE t_tracker.i_active = 1 AND t_tracker.i_clientassetid = ? AND t_tracker.i_status = 6 "); //appeal
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("appeal :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    appeal = rs.getInt(1);
                }
                rs.close();
               
                query = ("SELECT COUNT(1) AS ct FROM t_tracker WHERE i_active = 1 AND i_clientassetid = ? AND i_status = 4 AND (d_date >= current_date() AND d_date <= current_date() + INTERVAL '45' DAY) "); //expire in 45 days
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("expired1 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    expired1 = rs.getInt(1);
                }
                rs.close();
               
                query = ("SELECT COUNT(1) AS ct FROM t_tracker WHERE i_active = 1 AND i_clientassetid = ? AND i_status = 4 AND (d_date > current_date() + INTERVAL '45' DAY AND d_date <= current_date() + INTERVAL '65' DAY) "); //expire in 45 - 65 days
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("expired2 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    expired2 = rs.getInt(1);
                }
                rs.close();
                
                query = ("SELECT COUNT(1) AS ct FROM t_tracker WHERE i_active = 1 AND i_clientassetid = ? AND i_status = 4 AND (d_date > current_date() + INTERVAL '65' DAY AND d_date <= current_date() + INTERVAL '90' DAY) "); //expire in 65 - 90 days
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("expired3 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    expired3 = rs.getInt(1);
                }
                rs.close();
               
                query = ("SELECT SUM(t1.ct) FROM t_crewrotation AS cr LEFT JOIN t_candidate AS c ON (c.i_candidateid = cr.i_candidateid) LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) LEFT JOIN (SELECT i_positionid, COUNT(1) AS ct FROM t_fcrole LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) WHERE t_pcode.i_status = 1 AND i_clientassetid = ? AND i_positionid IN (SELECT DISTINCT(i_positionid) FROM t_candidate WHERE i_clientassetid = ?) group by i_positionid) AS t1 ON (t_position.i_positionid = t1.i_positionid)  WHERE cr.i_active = 1 AND cr.i_clientassetid = ? ");
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                pstmt.setInt(2, assetIdIndex);
                pstmt.setInt(3, assetIdIndex);
                logger.info("total_competencies :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    total_competencies = rs.getInt(1);
                }
                rs.close();
                
                query = ("SELECT COUNT(1) AS ct FROM t_tracker WHERE i_active = 2 AND i_clientassetid = ? "); //expired
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("expired :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    expired = rs.getInt(1);
                }
                rs.close();
                pendingtracker = total_competencies - pass - fail - appeal;
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
        arr[0] = onshore;
        arr[1] = offshore;
        arr[2] = totalcrew;
        arr[3] = total_positions;
        arr[4] = assigned_positions;
        arr[5] = assigned_crew;
        arr[6] = unassigned_crew;
        arr[7] = pass;
        arr[8] = fail;
        arr[9] = appeal;
        arr[10] = pendingtracker;        
        arr[11] = expired1;
        arr[12] = expired2;
        arr[13] = expired3;
        arr[14] = expired;        
        arr[15] = totaltracker;
        arr[16] = total_competencies;
        return arr;
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
        coll.add(new CompetencyInfo(-1, " Select Client"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next())
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new CompetencyInfo(refId, refName));
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
            coll.add(new CompetencyInfo(-1, " Select Asset "));
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
                    coll.add(new CompetencyInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new CompetencyInfo(-1, " Select Asset "));
        }
        return coll;
    }
    
    public ArrayList getCompentencyListByName(int clientassetId, int positionId, String search, int type) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_tracker.i_trackerid, t_pdept.s_name, t_position.s_name, t_grade.s_name,  CONCAT_WS(' ', t_candidate.s_firstname, ");
        sb.append("t_candidate.s_middlename, t_candidate.s_lastname), t_pcode.s_role, t_priority.s_name, DATEDIFF( t_tracker.d_date, CURRENT_DATE()), t_tracker.i_status ");
        sb.append("FROM t_tracker ");
        sb.append("LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) ");
        sb.append("LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) ");
        sb.append("LEFT JOIN t_priority ON (t_priority.i_priorityid = t_fcrole.i_priorityid) ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_tracker.i_candidateid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_candidate.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_pdept ON (t_pdept.i_pdeptid = t_pcode.i_pdeptid) ");
        sb.append("WHERE t_tracker.i_clientassetid = ? AND t_tracker.i_active = 1 AND t_tracker.i_status IN (2,3,4) ");   
        if(positionId > 0)
        {
            sb.append("AND t_position.i_positionid = ? ");
        }
        if(search != null && !search.equals(""))
        {
            sb.append("AND (t_pdept.s_name LIKE ? OR  t_position.s_name LIKE ? OR t_grade.s_name LIKE ? OR t_candidate.s_firstname LIKE ? OR t_candidate.s_middlename LIKE ? OR t_candidate.s_lastname LIKE ? OR t_pcode.s_role LIKE ? OR t_priority.s_name LIKE ? ) ");
        }
        
        if(type == 1)
        {
            sb.append(" AND t_tracker.i_active = 2 ");
        }
        else if(type == 2)
        {
            sb.append(" AND t_tracker.i_active = 1 AND t_tracker.i_status = 4 AND (t_tracker.d_date >= current_date() AND t_tracker.d_date <= current_date() + INTERVAL '45' DAY)  ");
        }
        else if(type == 3)
        {
            sb.append(" AND t_tracker.i_active = 1 AND t_tracker.i_status = 4 AND (t_tracker.d_date > current_date() + INTERVAL '45' DAY AND t_tracker.d_date <= current_date() + INTERVAL '65' DAY)  ");
        }
        else if(type == 4)
        {
            sb.append(" AND t_tracker.i_active = 1 AND t_tracker.i_status = 4 AND (t_tracker.d_date > current_date() + INTERVAL '65' DAY AND t_tracker.d_date <= current_date() + INTERVAL '90' DAY)  ");
        }
        else if(type == 5)
        {
            sb.append(" AND t_tracker.i_active = 1 AND  t_tracker.i_status IN (2,3)  ");
        }        
        sb.append("ORDER BY t_pdept.s_name,  t_position.s_name, t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname ");
        
        String query = sb.toString().intern();
        sb.setLength(0);        
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientassetId);
            if(positionId > 0)
            {
                pstmt.setInt(++scc, positionId);
            }
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
            }          
            Common.print(this, "getCompentencyListByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();           
            while (rs.next())
            {
                int trackerId = rs.getInt(1);
                String deparment  = rs.getString(2) != null ? rs.getString(2) : "";
                String position  = rs.getString(3) != null ? rs.getString(3) : "";
                String grade  = rs.getString(4) != null ? rs.getString(4) : "";
                String name  = rs.getString(5) != null ? rs.getString(5) : "";
                String role  = rs.getString(6) != null ? rs.getString(6) : "";
                String priority  = rs.getString(7) != null ? rs.getString(7) : "";
                int dateDiff  = rs.getInt(8) ;
                int status  = rs.getInt(9) ;
                if(!position.equals(""))
                {
                    if(!grade.equals(""))
                    {
                        position += " | " + grade;
                    }
                }                
                list.add(new CompetencyInfo(trackerId, deparment, position, name, role, priority, dateDiff, status));
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
    
    //TrackerList
    public ArrayList getTrackerList(int clientassetId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();        
        sb.append("SELECT t_tracker.i_candidateid, t_tracker.i_trackerid, t_tracker.i_status, t_fcrole.i_pcodeid, t_tracker.i_active ");
        sb.append("FROM t_tracker ");
        sb.append("LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) ");
        sb.append("WHERE t_tracker.i_clientassetid = ? AND t_tracker.i_active IN(1,2) ORDER BY t_tracker.i_active, t_tracker.d_completebydate1 DESC");        
        String query = sb.toString().intern();
        sb.setLength(0);        
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            Common.print(this, "getTrackerList :: " + pstmt.toString());
            rs = pstmt.executeQuery();           
            while (rs.next())
            {
                int crewrotationId = rs.getInt(1);
                int trackerId = rs.getInt(2);
                int status = rs.getInt(3);
                int pcodeId = rs.getInt(4);
                int activeflag = rs.getInt(5);
                list.add(new CompetencyInfo(crewrotationId, trackerId, status, pcodeId, activeflag));
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
    
    //Candidate position
    public ArrayList getCandidatePositionList(int clientassetId, String pids, String crids) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT cr.i_crewrotationid, CONCAT_WS(' ', s_firstname, s_middlename,s_lastname),  t_position.s_name, t_grade.s_name, t_position.i_positionid ");
        sb.append("FROM t_crewrotation AS cr ");
        sb.append("LEFT JOIN t_candidate  AS c ON (c.i_candidateid = cr.i_candidateid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) "); 
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("WHERE cr.i_active = 1 AND cr.i_clientassetid = ?  ");
        if(pids != null && !pids.equals(""))
        {
            sb.append(" AND c.i_positionid IN ("+pids+") ");
        }
        if(crids != null && !crids.equals(""))
        {
            sb.append(" AND cr.i_crewrotationid IN ("+crids+") ");
        }
        sb.append("ORDER BY t_position.s_name, t_grade.s_name, c.s_firstname, c.s_middlename,c.s_lastname ");
             
        String query = sb.toString().intern();
        sb.setLength(0);        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            Common.print(this, "getCandidatePositionList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, position, grade, temp = "";
            int crewrotationId, positionId;
            while(rs.next())
            {
                crewrotationId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2): "";
                position = rs.getString(3) != null ? rs.getString(3): "";
                grade = rs.getString(4) != null ? rs.getString(4): "";
                positionId = rs.getInt(5);
                if(!position.equals(""))
                {
                    if(!grade.equals(""))
                    {
                        position += " | " + grade;
                    }
                }
                if(!temp.equals(position))
                {
                    list.add(new CompetencyInfo (crewrotationId, name, position, positionId));
                    temp = position;
                }
                else
                {
                    list.add(new CompetencyInfo (crewrotationId, name, "", positionId));
                }
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
    
    public String[] getStatus(int status, int active)
    {
        String s = "UA", cls = "";
        if (active == 1) 
        {
            if (status == 1) 
            {
                s = "UA";
                cls = "text-center text_white circle_unassigned";
            }
            else if (status == 2 || status == 3) 
            {
                s = "P";
                cls = "text-center text_white hand_cursor circle_pending";
            } 
            else if (status == 4) 
            {
                s = "Y";
                cls = "text-center text_white hand_cursor circle_complete";
            }
            else if (status == 5) 
            {
                s = "N";
                cls = "text-center text_white hand_cursor circle_exipred";
            }
            else if (status == 6) 
            {
                s = "A";
                cls = "text-center text_white hand_cursor circle_exipry";
            } 
        }
        else if (active == 2) 
        {
            s = "E";
            cls = "text-center text_white circle_expired_bg";
        }
        String arr[] = new String[2];
        arr[0] = s;
        arr[1] = cls;
    return arr;
}
    
    public boolean checkinavailablelist(ArrayList list, int positionId, int pcodeId)
    {
        boolean b = false;
        int size = list.size();
        for (int i = 0; i < size; i++) 
        {
            CompetencyInfo ckinfo = (CompetencyInfo) list.get(i);
            if (ckinfo != null && ckinfo.getPositionId() == positionId && ckinfo.getPcodeId() == pcodeId) 
            {
                b = true;
                break;
            }
        }
        return b;
    }

    public CompetencyInfo getfromList(ArrayList list, ArrayList alist, int positionId, int crewrotationId, int pcodeId)
    {
        CompetencyInfo info = null;
        String stname = "", classsName = "";
         boolean b = false;
        int size = list.size();
         b = checkinavailablelist(alist, positionId, pcodeId);
        for (int i = 0; i < size; i++) 
        {           
            if(b == true)
            {
                CompetencyInfo ckinfo = (CompetencyInfo) list.get(i);
                if (ckinfo != null && ckinfo.getCrewrotationId() == crewrotationId && ckinfo.getPcodeId() == pcodeId) 
                {
                    String arr[] = getStatus(ckinfo.getStatus(), ckinfo.getActiveflag());                   
                    if (arr != null)
                    {
                        stname = arr[0] != null ? arr[0] : "";
                        classsName = arr[1] != null ? arr[1] : "";
                    }
                    info = new CompetencyInfo(ckinfo.getTrackerId(), stname, classsName, -1);
                    break;
                }                
            }
        }
        if(b == true && stname.equals(""))
        {
            stname = "UA";
            classsName = "text-center text_white circle_unassigned";
            info = new CompetencyInfo(-1, stname, classsName, -1);
        }
        return info;
    }
    
    //For Department table count
    public ArrayList getDeptTableCountList(int clientassetId , String dids)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_pdept.i_pdeptid, t_pdept.s_name, t1.ct, t2.ct, t3.ct, t4.ct, t5.ct, t6.ct, t7.ct, t8.ct ");
        sb.append("FROM t_pdept ");
        sb.append("LEFT JOIN (SELECT t_clientassetposition.i_pdeptid, COUNT(1) AS ct FROM t_clientassetposition WHERE i_clientassetid = ? AND i_status = 1 GROUP BY i_pdeptid) AS t1 ON (t_pdept.i_pdeptid = t1.i_pdeptid) ");
        sb.append("LEFT JOIN (SELECT t_clientassetposition.i_pdeptid, COUNT(1)  AS ct FROM t_crewrotation LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_crewrotation.i_candidateid) ");
        sb.append("LEFT JOIN t_clientassetposition ON (t_clientassetposition.i_positionid = t_candidate.i_positionid AND t_clientassetposition.i_clientassetid = ?) WHERE t_crewrotation.i_clientassetid = ? AND t_crewrotation.i_active = 1 GROUP BY t_clientassetposition.i_pdeptid) AS t2 ON (t_pdept.i_pdeptid = t2.i_pdeptid) ");
        
        sb.append("LEFT JOIN (SELECT t_clientassetposition.i_pdeptid AS i_pdeptid, SUM(t1.ct) AS ct FROM t_crewrotation AS cr LEFT JOIN t_candidate AS c ON (c.i_candidateid = cr.i_candidateid)  LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_clientassetposition ON (t_clientassetposition.i_positionid = t_position.i_positionid AND t_clientassetposition.i_clientassetid = ?) ");
        sb.append("LEFT JOIN (SELECT i_positionid, COUNT(1) AS ct FROM t_fcrole LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) ");
        sb.append("WHERE t_pcode.i_status = 1 AND i_clientassetid = ? ");
        sb.append("AND i_positionid IN (SELECT DISTINCT(i_positionid) FROM t_candidate WHERE i_clientassetid = ?) group by i_positionid) AS t1 ON (t_position.i_positionid = t1.i_positionid) ");
        sb.append("WHERE cr.i_active = 1 AND cr.i_clientassetid = ? group by t_clientassetposition.i_pdeptid) AS t3 ON (t_pdept.i_pdeptid = t3.i_pdeptid) ");
        
        sb.append("LEFT JOIN (SELECT t_pcode.i_pdeptid, COUNT(1)  AS ct FROM t_tracker LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid)  WHERE t_fcrole.i_clientassetid = ? ");
        sb.append("AND t_fcrole.i_status = 1 AND t_tracker.i_status IN (2,3,4,5,6)  GROUP BY t_pcode.i_pdeptid) AS t4 ON (t_pdept.i_pdeptid = t4.i_pdeptid) ");
        
        sb.append("LEFT JOIN (SELECT t_pcode.i_pdeptid, COUNT(1)  AS ct FROM t_tracker LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid)  WHERE t_fcrole.i_clientassetid = ? ");
        sb.append("AND t_fcrole.i_status = 1 AND t_tracker.i_status IN (3,4,5,6)  GROUP BY t_pcode.i_pdeptid) AS t5 ON (t_pdept.i_pdeptid = t5.i_pdeptid) ");
        
        sb.append("LEFT JOIN (SELECT t_pcode.i_pdeptid, COUNT(1)  AS ct FROM t_tracker LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid)  WHERE t_fcrole.i_clientassetid = ? ");
        sb.append("AND t_fcrole.i_status = 1 AND t_tracker.i_status IN (4,5,6)  GROUP BY t_pcode.i_pdeptid) AS t6 ON (t_pdept.i_pdeptid = t6.i_pdeptid) ");
        
        sb.append("LEFT JOIN (SELECT t_pcode.i_pdeptid, COUNT(1)  AS ct FROM t_tracker LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid)  WHERE t_fcrole.i_clientassetid = ? ");
        sb.append("AND t_fcrole.i_status = 1 AND t_tracker.i_status IN (4)  GROUP BY t_pcode.i_pdeptid) AS t7 ON (t_pdept.i_pdeptid = t7.i_pdeptid) ");
        
        sb.append("LEFT JOIN (SELECT t_pcode.i_pdeptid, COUNT(1)  AS ct FROM t_tracker LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid)  WHERE t_fcrole.i_clientassetid = ? ");
        sb.append("AND t_fcrole.i_status = 1 AND t_tracker.i_status IN (5,6)  GROUP BY t_pcode.i_pdeptid) AS t8 ON (t_pdept.i_pdeptid = t8.i_pdeptid) ");
        sb.append("WHERE (t1.ct > 0 && t2.ct > 0) ");
        if(dids !=null && !dids.equals(""))
        {
            sb.append(" AND t_pdept.i_pdeptid IN("+dids+")");
        }
        sb.append("ORDER BY t_pdept.s_name ");        
        
        String query = sb.toString().intern();
        sb.setLength(0);        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, clientassetId);
            pstmt.setInt(3, clientassetId);
            pstmt.setInt(4, clientassetId);
            pstmt.setInt(5, clientassetId);
            pstmt.setInt(6, clientassetId);
            pstmt.setInt(7, clientassetId);
            pstmt.setInt(8, clientassetId);
            pstmt.setInt(9, clientassetId);
            pstmt.setInt(10, clientassetId);
            pstmt.setInt(11, clientassetId);
            pstmt.setInt(12, clientassetId);
            Common.print(this, "getDeptTableCountList :: " + pstmt.toString());
            rs = pstmt.executeQuery();           
            while (rs.next())
            {
                int id = rs.getInt(1);
                String department = rs.getString(2) != null ? rs.getString(2) : "";
                int count1 = rs.getInt(3);
                int count2 = rs.getInt(4);
                int count3 = rs.getInt(5);
                int count4 = rs.getInt(6);
                int count5 = rs.getInt(7);
                int count6 = rs.getInt(8);
                int count7 = rs.getInt(9);
                int count8 = rs.getInt(10);
                list.add(new CompetencyInfo(id, department, count1, count2, count3, count4, count5, count6, count7, count8));
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
    
    //Competency Graph Count  
    public ArrayList getCompetencyGraph(int assetIdIndex, int type)
    {
        ArrayList list = new ArrayList();
        if(assetIdIndex > 0)
        {      
            StringBuilder sb = new StringBuilder();            
            sb.append("SELECT t_pdept.s_name, COUNT(1) AS ct FROM t_tracker LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) ");
            sb.append("LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) ");
            sb.append("LEFT JOIN t_pdept ON (t_pdept.i_pdeptid = t_pcode.i_pdeptid) ");
            sb.append("WHERE t_tracker.i_clientassetid = ?  ");
            if(type == 1)
            {
                sb.append(" AND t_tracker.i_active = 2 ");
            }
            else if(type == 2)
            {
                sb.append(" AND t_tracker.i_active = 1 AND t_tracker.i_status = 4 AND (t_tracker.d_date >= current_date() AND t_tracker.d_date <= current_date() + INTERVAL '45' DAY)  ");
            }
            else if(type == 3)
            {
                sb.append(" AND t_tracker.i_active = 1 AND t_tracker.i_status = 4 AND (t_tracker.d_date > current_date() + INTERVAL '45' DAY AND t_tracker.d_date <= current_date() + INTERVAL '65' DAY)  ");
            }
            else if(type == 4)
            {
                sb.append(" AND t_tracker.i_active = 1 AND t_tracker.i_status = 4 AND (t_tracker.d_date > current_date() + INTERVAL '65' DAY AND t_tracker.d_date <= current_date() + INTERVAL '90' DAY)  ");
            }
            else if(type == 5)
            {
                sb.append(" AND t_tracker.i_active = 1 AND  t_tracker.i_status IN (2,3)  ");
            }
            sb.append("group by t_pdept.i_pdeptid ORDER BY t_pdept.s_name");
            
            String query = sb.toString().intern();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("getCompetencyGraph :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String name;
                int count;
                while (rs.next())
                {
                    name = rs.getString(1) != null ? rs.getString(1): "";
                    count = rs.getInt(2);
                    list.add(new CompetencyInfo(count, name));
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
    
    //Position-Rank
    public Collection getPostions(int clientassetId) 
    {
        Collection coll = new LinkedList();
        if (clientassetId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_position.i_positionid, t_position.s_name, t_grade.s_name FROM t_clientassetposition ");
            sb.append(" LEFT JOIN t_position ON (t_position.i_positionid = t_clientassetposition.i_positionid) ");
            sb.append(" LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append(" WHERE t_clientassetposition.i_status = 1 AND t_clientassetposition.i_clientassetid = ? ORDER BY t_position.s_name, t_grade.s_name");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new CompetencyInfo(-1, "Select Position | Rank"));
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                Common.print(this, "getPostions :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int id;
                String  grade, position;
                while (rs.next()) 
                {
                    id = rs.getInt(1);
                    position = rs.getString(2) != null ? rs.getString(2): "";
                    grade = rs.getString(3) != null ? rs.getString(3) : "";
                    if (!position.equals("")) 
                    {
                        if (!grade.equals("")) 
                        {
                            position += " | " + grade;
                        }
                    }
                    coll.add(new CompetencyInfo(id, position));
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new CompetencyInfo(-1, " Select Position | Rank "));
        }
        return coll;
    }
    
    public ArrayList availableList(int clientassetId) 
    {
        ArrayList list = new ArrayList();
        String query = ("SELECT i_positionid, i_pcodeid FROM t_fcrole WHERE t_fcrole.i_status = 1 AND t_fcrole.i_clientassetid = ?  ");   
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            Common.print(this, "availableList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int positionId, pcodeId;
            while(rs.next())
            {
                    positionId = rs.getInt(1);
                    pcodeId = rs.getInt(2);
                    list.add(new CompetencyInfo (positionId, pcodeId));
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
}

