package com.web.jxp.dashboard;

import com.web.jxp.base.Base;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import static com.web.jxp.common.Common.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.TimeZone;
import java.util.logging.Logger;

public class Dashboard extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

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
        coll.add(new DashboardInfo(-1, " Select Client"));
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
                coll.add(new DashboardInfo(refId, refName));
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
            coll.add(new DashboardInfo(-1, " Select Asset "));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                logger.info("getClientAsset :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2): "";
                    coll.add(new DashboardInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new DashboardInfo(-1, " Select Asset "));
        }
        return coll;
    }
    
    public ArrayList getClientSelectByName(
            int clientIdIndex, int assetIdIndex, String fromdateIndex, String todateIndex, String positioncb, String crewrotationcb) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select cr.i_crewrotationid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name,  ");
        sb.append(" t1.ct, ");
        sb.append(" coalesce (t2.ct1, 0)+coalesce (t3.ct1, 0)-coalesce (t3.ct2, 0)+coalesce (t4.ct1,0)-coalesce (t4.ct3,0)+coalesce (t15.normal,0), "); //normal days           +coalesce (t15.normal,0)
        sb.append(" coalesce (t3.ct2,0)+coalesce (t4.ct3,0)-coalesce (t4.ct2,0)+coalesce (t15.extended,0),     ");//extended days
        sb.append("   coalesce (t4.ct2,0)+coalesce (t15.overstay,0),   ");//overstaydays
        sb.append(" t5.noofdays,t6.noofdays,t7.noofdays,t8.noofdays,t9.noofdays,t10.noofdays , t11.ct1, t_rotation.i_noofdays ");
        sb.append(" from t_crewrotation as cr left join t_candidate as c on (c.i_candidateid = cr.i_candidateid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append(" left join t_rotation on (t_rotation.i_rotationid = t_position.i_rotationid)");
        sb.append(" left join (select i_crewrotationid, count(1) as ct from t_cractivity where i_activityid = 6 and i_signoffid > 0 and (ts_todate >= '"+ changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"') group by i_crewrotationid) as t1 on (t1.i_crewrotationid = cr.i_crewrotationid) "); //rotation
        
        sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_fromdate < '"+changeDate1(fromdateIndex)+"' then '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end)) as ct1 from t_cractivity ");//normal subtype=1,2
        sb.append(" left join t_signonoff on (t_signonoff.i_signonoffid = t_cractivity.i_signoffid and t_signonoff.i_subtype in(1,2) and t_signonoff.i_type = 2 and t_cractivity.i_status = 2 and t_cractivity.i_activityid = 6) where t_cractivity.i_status = 2 and i_activityid = 6 and t_signonoff.i_subtype in(1,2) and t_signonoff.i_type = 2  and ((ts_fromdate <= '"+changeDate1(fromdateIndex)+"' and ts_todate >= '"+changeDate1(fromdateIndex)+"') or (ts_fromdate <= '"+changeDate1(todateIndex)+"' and ts_todate >= '"+changeDate1(todateIndex)+"') or (ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"')) group by t_cractivity.i_crewrotationid) as t2 on (t2.i_crewrotationid = cr.i_crewrotationid) ");
        
        sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>   '"+changeDate1(todateIndex)+"' then   '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_fromdate <  '"+changeDate1(fromdateIndex)+"' then  '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end)) as ct1,SUM(DATEDIFF(case when ts_date2> '"+changeDate1(todateIndex)+"' then '"+changeDate1(todateIndex)+"' else ts_date2 end, case when ts_date1 > '"+changeDate1(todateIndex)+"' then '"+changeDate1(todateIndex)+"' else ts_date1 end)) as ct2 from t_cractivity ");//extended subtype =3
        sb.append(" left join t_signonoff on (t_signonoff.i_signonoffid = t_cractivity.i_signoffid  and t_signonoff.i_subtype = 3 and t_signonoff.i_type = 2 and t_cractivity.i_status = 2) where t_cractivity.i_status = 2 and i_activityid = 6 and t_signonoff.i_subtype = 3 and t_signonoff.i_type = 2  and ts_overstaydate = '0000-00-00' and ((ts_fromdate <= '"+changeDate1(fromdateIndex)+"' and ts_todate >= '"+changeDate1(fromdateIndex)+"') or (ts_fromdate <= '"+changeDate1(todateIndex)+"' and ts_todate >= '"+changeDate1(todateIndex)+"') or (ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"')) group by t_cractivity.i_crewrotationid) as t3 on (t3.i_crewrotationid = cr.i_crewrotationid) ");
        
        sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate >  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_fromdate <  '"+changeDate1(fromdateIndex)+"' then  '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end)) as ct1,SUM(DATEDIFF(case when ts_todate >  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_overstaydate >  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"'  when ts_overstaydate <  '"+changeDate1(fromdateIndex)+"' then  '"+changeDate1(fromdateIndex)+"' else ts_overstaydate end)) as ct2, SUM(DATEDIFF(case when ts_date2>  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"'  else ts_date2 end, case when ts_date1 >  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"'  else ts_date1 end)) as ct3 from t_cractivity ");// overstay subtype = 3 
        sb.append(" left join t_signonoff on (t_signonoff.i_signonoffid = t_cractivity.i_signoffid and t_signonoff.i_type = 2 and t_signonoff.i_subtype = 3 and t_cractivity.i_status = 2) where t_cractivity.i_status = 2 and i_activityid = 6  and t_signonoff.i_subtype = 3 and ts_overstaydate != '0000-00-00' and ((ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"') or ('"+changeDate1(fromdateIndex)+"' >= ts_fromdate and '"+changeDate1(fromdateIndex)+"' <= ts_todate)) group by t_cractivity.i_crewrotationid) as t4 on (t4.i_crewrotationid = cr.i_crewrotationid) ");
        sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_fromdate <  '"+changeDate1(fromdateIndex)+"' then  '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end)+1) as noofdays from t_cractivity where t_cractivity.i_activityid =1 and ((ts_fromdate <= '"+changeDate1(fromdateIndex)+"' and ts_todate >= '"+changeDate1(fromdateIndex)+"') or (ts_fromdate <= '"+changeDate1(todateIndex)+"' and ts_todate >= '"+changeDate1(todateIndex)+"') or(ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"')) group by t_cractivity.i_crewrotationid) as t5 on (t5.i_crewrotationid = cr.i_crewrotationid ) ");//officework
        sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_fromdate <  '"+changeDate1(fromdateIndex)+"' then  '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end)+1) as noofdays from t_cractivity where t_cractivity.i_activityid =2 and i_status = 1 and ((ts_fromdate <= '"+changeDate1(fromdateIndex)+"' and ts_todate >= '"+changeDate1(fromdateIndex)+"') or (ts_fromdate <= '"+changeDate1(todateIndex)+"' and ts_todate >= '"+changeDate1(todateIndex)+"') or(ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"')) group by t_cractivity.i_crewrotationid) as t6 on (t6.i_crewrotationid = cr.i_crewrotationid ) ");//training
        sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_fromdate <  '"+changeDate1(fromdateIndex)+"' then  '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end)+1) as noofdays from t_cractivity where t_cractivity.i_activityid =3 and ((ts_fromdate <= '"+changeDate1(fromdateIndex)+"' and ts_todate >= '"+changeDate1(fromdateIndex)+"') or (ts_fromdate <= '"+changeDate1(todateIndex)+"' and ts_todate >= '"+changeDate1(todateIndex)+"') or(ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"')) group by t_cractivity.i_crewrotationid) as t7 on (t7.i_crewrotationid = cr.i_crewrotationid ) ");//absent
        sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_fromdate <  '"+changeDate1(fromdateIndex)+"' then  '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end)+1) as noofdays from t_cractivity where t_cractivity.i_activityid =4 and ((ts_fromdate <= '"+changeDate1(fromdateIndex)+"' and ts_todate >= '"+changeDate1(fromdateIndex)+"') or (ts_fromdate <= '"+changeDate1(todateIndex)+"' and ts_todate >= '"+changeDate1(todateIndex)+"') or(ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"')) group by t_cractivity.i_crewrotationid) as t8 on (t8.i_crewrotationid = cr.i_crewrotationid ) ");//available
        sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_fromdate <  '"+changeDate1(fromdateIndex)+"' then  '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end)+1) as noofdays from t_cractivity where t_cractivity.i_activityid =5 and ((ts_fromdate <= '"+changeDate1(fromdateIndex)+"' and ts_todate >= '"+changeDate1(fromdateIndex)+"') or (ts_fromdate <= '"+changeDate1(todateIndex)+"' and ts_todate >= '"+changeDate1(todateIndex)+"') or(ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"')) group by t_cractivity.i_crewrotationid) as t9 on (t9.i_crewrotationid = cr.i_crewrotationid ) ");//not available
        sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(ts_todate, ts_fromdate)+1) as noofdays from t_cractivity where t_cractivity.i_activityid =3  group by t_cractivity.i_crewrotationid) as t10 on (t10.i_crewrotationid = cr.i_crewrotationid ) ");//total absent
        //for current signon
        sb.append(" LEFT JOIN (select t_cractivity.i_crewrotationid, SUM(coalesce (DATEDIFF(case when ts_todate > CURRENT_DATE then  CURRENT_DATE when ts_todate >  '"+changeDate1(todateIndex)+"' then   '"+changeDate1(todateIndex)+"' else ts_todate end,  case when ts_fromdate <  '"+changeDate1(fromdateIndex)+"' then  '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end), 0) -  coalesce (DATEDIFF(case when d_rdate2 =  '0000-00-00' then CURRENT_DATE  else d_rdate2 end, d_rdate1), 0) - coalesce (DATEDIFF( CURRENT_DATE, d_rdate2), 0)) as normal, ");
        sb.append("SUM(coalesce (DATEDIFF(case when d_rdate2 =  '0000-00-00' then CURRENT_DATE  else d_rdate2 end, d_rdate1), 0)) as extended, ");
        sb.append("SUM(coalesce (DATEDIFF( CURRENT_DATE, d_rdate2), 0)) as overstay  from t_cractivity ");
        sb.append(" left join t_crewrotation on (t_crewrotation.i_crewrotationid = t_cractivity.i_crewrotationid and t_crewrotation.i_active = 1 and t_cractivity.i_status = 2 and t_cractivity.i_activityid = 6) ");
        sb.append("where t_cractivity.i_signoffid <= 0 and CURRENT_DATE > ts_fromdate and ((ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"'))  and t_cractivity.i_status = 2 and t_cractivity.i_activityid = 6  group by t_cractivity.i_crewrotationid) as t15 on (t15.i_crewrotationid = cr.i_crewrotationid) ");
        
        sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_fromdate < '"+changeDate1(fromdateIndex)+"' then '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end)) as ct1 from t_cractivity ");//early subtype=2
        sb.append(" left join t_signonoff on (t_signonoff.i_signonoffid = t_cractivity.i_signoffid and t_signonoff.i_subtype = 2 and t_signonoff.i_type = 2 and t_cractivity.i_status = 2 ) where t_cractivity.i_status = 2 and i_activityid = 6 and t_signonoff.i_subtype = 2 and t_signonoff.i_type = 2  and ((ts_fromdate <= '"+changeDate1(fromdateIndex)+"' and ts_todate >= '"+changeDate1(fromdateIndex)+"') or (ts_fromdate <= '"+changeDate1(todateIndex)+"' and ts_todate >= '"+changeDate1(todateIndex)+"') or (ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"')) group by t_cractivity.i_crewrotationid) as t11 on (t11.i_crewrotationid = cr.i_crewrotationid) ");
        
        sb.append(" where  cr.i_active = 1 AND cr.i_clientid = ? AND cr.i_clientassetid = ? ");        
        if (positioncb != null && !positioncb.equals("")) {
            sb.append("AND c.i_positionid in ("+positioncb+") ");
        }
        if (crewrotationcb != null && !crewrotationcb.equals("")) {
            sb.append("AND cr.i_crewrotationid in ("+crewrotationcb+") ");
        }
        sb.append(" order by c.s_firstname, c.s_middlename, c.s_lastname ");
        
        String query = (sb.toString()).intern();
        sb.setLength(0);
         logger.info("getClientSelectByName:: " + query);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientIdIndex);
            pstmt.setInt(2, assetIdIndex);
            logger.info("getClientSelectByName:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String   candidateName, position, grade;
            int crewrotationId, normal, extended, rotations, overstay = 0, officework = 0, training = 0, available = 0, absent = 0, noavailable = 0, totalabsent = 0, early = 0, noofdays = 0;
            while (rs.next()) 
            {
                crewrotationId = rs.getInt(1);
                candidateName = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                grade = rs.getString(4) != null ? rs.getString(4) : "";
                rotations = rs.getInt(5) > 0 ? rs.getInt(5) : 0;
                normal = rs.getInt(6) > 0 ? rs.getInt(6) : 0;
                extended = rs.getInt(7) > 0 ? rs.getInt(7) : 0;
                overstay = rs.getInt(8) > 0 ? rs.getInt(8) : 0;
                officework = rs.getInt(9) > 0 ? rs.getInt(9) : 0;
                training = rs.getInt(10) > 0 ? rs.getInt(10) : 0;
                absent = rs.getInt(11) > 0 ? rs.getInt(11) : 0;
                available = rs.getInt(12) > 0 ? rs.getInt(12) : 0;
                noavailable = rs.getInt(13) > 0 ? rs.getInt(13) : 0;
                totalabsent = rs.getInt(14) > 0 ? rs.getInt(14) : 0;
                early = rs.getInt(15) > 0 ? rs.getInt(15) : 0;
                noofdays = rs.getInt(16) > 0 ? rs.getInt(16) : 0;                
                if (!grade.equals("")) {
                    position += " - " + grade;
                }
                list.add(new DashboardInfo(crewrotationId, candidateName, position, grade, rotations, normal, extended, overstay, officework, training, available, absent, noavailable, totalabsent, early, noofdays));
            }
            rs.close();
            
            sb.append("select cr.i_crewrotationid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name,  ");
            sb.append(" t1.ct, ");
            sb.append(" coalesce (t2.ct1, 0)+coalesce (t3.ct1, 0)-coalesce (t3.ct2, 0)+coalesce (t4.ct1,0)-coalesce (t4.ct3,0)+coalesce (t15.normal,0), "); //normal days           +coalesce (t15.normal,0)
            sb.append(" coalesce (t3.ct2,0)+coalesce (t4.ct3,0)-coalesce (t4.ct2,0)+coalesce (t15.extended,0),     ");//extended days
            sb.append("   coalesce (t4.ct2,0)+coalesce (t15.overstay,0),   ");//overstaydays
            sb.append(" t5.noofdays,t6.noofdays,t7.noofdays,t8.noofdays,t9.noofdays,t10.noofdays , t11.ct1, t_rotation.i_noofdays ");
            sb.append(" from t_crewrotation as cr left join t_candidate as c on (c.i_candidateid = cr.i_candidateid) ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid2) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append(" left join t_rotation on (t_rotation.i_rotationid = t_position.i_rotationid)");
            sb.append(" left join (select i_crewrotationid, count(1) as ct from t_cractivity where i_activityid = 6 and i_signoffid > 0 and (ts_todate >= '"+ changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"') group by i_crewrotationid) as t1 on (t1.i_crewrotationid = cr.i_crewrotationid) "); //rotation

            sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_fromdate < '"+changeDate1(fromdateIndex)+"' then '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end)) as ct1 from t_cractivity ");//normal subtype=1,2
            sb.append(" left join t_signonoff on (t_signonoff.i_signonoffid = t_cractivity.i_signoffid and t_signonoff.i_subtype in(1,2) and t_signonoff.i_type = 2 and t_cractivity.i_status = 2 and t_cractivity.i_activityid = 6) where t_cractivity.i_status = 2 and i_activityid = 6 and t_signonoff.i_subtype in(1,2) and t_signonoff.i_type = 2  and ((ts_fromdate <= '"+changeDate1(fromdateIndex)+"' and ts_todate >= '"+changeDate1(fromdateIndex)+"') or (ts_fromdate <= '"+changeDate1(todateIndex)+"' and ts_todate >= '"+changeDate1(todateIndex)+"') or (ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"')) group by t_cractivity.i_crewrotationid) as t2 on (t2.i_crewrotationid = cr.i_crewrotationid) ");

            sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>   '"+changeDate1(todateIndex)+"' then   '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_fromdate <  '"+changeDate1(fromdateIndex)+"' then  '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end)) as ct1,SUM(DATEDIFF(case when ts_date2> '"+changeDate1(todateIndex)+"' then '"+changeDate1(todateIndex)+"' else ts_date2 end, case when ts_date1 > '"+changeDate1(todateIndex)+"' then '"+changeDate1(todateIndex)+"' else ts_date1 end)) as ct2 from t_cractivity ");//extended subtype =3
            sb.append(" left join t_signonoff on (t_signonoff.i_signonoffid = t_cractivity.i_signoffid  and t_signonoff.i_subtype = 3 and t_signonoff.i_type = 2 and t_cractivity.i_status = 2) where t_cractivity.i_status = 2 and i_activityid = 6 and t_signonoff.i_subtype = 3 and t_signonoff.i_type = 2  and ts_overstaydate = '0000-00-00' and ((ts_fromdate <= '"+changeDate1(fromdateIndex)+"' and ts_todate >= '"+changeDate1(fromdateIndex)+"') or (ts_fromdate <= '"+changeDate1(todateIndex)+"' and ts_todate >= '"+changeDate1(todateIndex)+"') or (ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"')) group by t_cractivity.i_crewrotationid) as t3 on (t3.i_crewrotationid = cr.i_crewrotationid) ");

            sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate >  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_fromdate <  '"+changeDate1(fromdateIndex)+"' then  '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end)) as ct1,SUM(DATEDIFF(case when ts_todate >  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_overstaydate >  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"'  when ts_overstaydate <  '"+changeDate1(fromdateIndex)+"' then  '"+changeDate1(fromdateIndex)+"' else ts_overstaydate end)) as ct2, SUM(DATEDIFF(case when ts_date2>  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"'  else ts_date2 end, case when ts_date1 >  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"'  else ts_date1 end)) as ct3 from t_cractivity ");// overstay subtype = 3 
            sb.append(" left join t_signonoff on (t_signonoff.i_signonoffid = t_cractivity.i_signoffid and t_signonoff.i_type = 2 and t_signonoff.i_subtype = 3 and t_cractivity.i_status = 2) where t_cractivity.i_status = 2 and i_activityid = 6  and t_signonoff.i_subtype = 3 and ts_overstaydate != '0000-00-00' and ((ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"') or ('"+changeDate1(fromdateIndex)+"' >= ts_fromdate and '"+changeDate1(fromdateIndex)+"' <= ts_todate)) group by t_cractivity.i_crewrotationid) as t4 on (t4.i_crewrotationid = cr.i_crewrotationid) ");
            sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_fromdate <  '"+changeDate1(fromdateIndex)+"' then  '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end)+1) as noofdays from t_cractivity where t_cractivity.i_activityid =1 and ((ts_fromdate <= '"+changeDate1(fromdateIndex)+"' and ts_todate >= '"+changeDate1(fromdateIndex)+"') or (ts_fromdate <= '"+changeDate1(todateIndex)+"' and ts_todate >= '"+changeDate1(todateIndex)+"') or(ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"')) group by t_cractivity.i_crewrotationid) as t5 on (t5.i_crewrotationid = cr.i_crewrotationid ) ");//officework
            sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_fromdate <  '"+changeDate1(fromdateIndex)+"' then  '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end)+1) as noofdays from t_cractivity where t_cractivity.i_activityid =2 and i_status = 1 and ((ts_fromdate <= '"+changeDate1(fromdateIndex)+"' and ts_todate >= '"+changeDate1(fromdateIndex)+"') or (ts_fromdate <= '"+changeDate1(todateIndex)+"' and ts_todate >= '"+changeDate1(todateIndex)+"') or(ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"')) group by t_cractivity.i_crewrotationid) as t6 on (t6.i_crewrotationid = cr.i_crewrotationid ) ");//training
            sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_fromdate <  '"+changeDate1(fromdateIndex)+"' then  '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end)+1) as noofdays from t_cractivity where t_cractivity.i_activityid =3 and ((ts_fromdate <= '"+changeDate1(fromdateIndex)+"' and ts_todate >= '"+changeDate1(fromdateIndex)+"') or (ts_fromdate <= '"+changeDate1(todateIndex)+"' and ts_todate >= '"+changeDate1(todateIndex)+"') or(ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"')) group by t_cractivity.i_crewrotationid) as t7 on (t7.i_crewrotationid = cr.i_crewrotationid ) ");//absent
            sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_fromdate <  '"+changeDate1(fromdateIndex)+"' then  '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end)+1) as noofdays from t_cractivity where t_cractivity.i_activityid =4 and ((ts_fromdate <= '"+changeDate1(fromdateIndex)+"' and ts_todate >= '"+changeDate1(fromdateIndex)+"') or (ts_fromdate <= '"+changeDate1(todateIndex)+"' and ts_todate >= '"+changeDate1(todateIndex)+"') or(ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"')) group by t_cractivity.i_crewrotationid) as t8 on (t8.i_crewrotationid = cr.i_crewrotationid ) ");//available
            sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_fromdate <  '"+changeDate1(fromdateIndex)+"' then  '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end)+1) as noofdays from t_cractivity where t_cractivity.i_activityid =5 and ((ts_fromdate <= '"+changeDate1(fromdateIndex)+"' and ts_todate >= '"+changeDate1(fromdateIndex)+"') or (ts_fromdate <= '"+changeDate1(todateIndex)+"' and ts_todate >= '"+changeDate1(todateIndex)+"') or(ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"')) group by t_cractivity.i_crewrotationid) as t9 on (t9.i_crewrotationid = cr.i_crewrotationid ) ");//not available
            sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(ts_todate, ts_fromdate)+1) as noofdays from t_cractivity where t_cractivity.i_activityid =3  group by t_cractivity.i_crewrotationid) as t10 on (t10.i_crewrotationid = cr.i_crewrotationid ) ");//total absent
            //for current signon
            sb.append(" LEFT JOIN (select t_cractivity.i_crewrotationid, SUM(coalesce (DATEDIFF(case when ts_todate > CURRENT_DATE then  CURRENT_DATE when ts_todate >  '"+changeDate1(todateIndex)+"' then   '"+changeDate1(todateIndex)+"' else ts_todate end,  case when ts_fromdate <  '"+changeDate1(fromdateIndex)+"' then  '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end), 0) -  coalesce (DATEDIFF(case when d_rdate2 =  '0000-00-00' then CURRENT_DATE  else d_rdate2 end, d_rdate1), 0) - coalesce (DATEDIFF( CURRENT_DATE, d_rdate2), 0)) as normal, ");
            sb.append("SUM(coalesce (DATEDIFF(case when d_rdate2 =  '0000-00-00' then CURRENT_DATE  else d_rdate2 end, d_rdate1), 0)) as extended, ");
            sb.append("SUM(coalesce (DATEDIFF( CURRENT_DATE, d_rdate2), 0)) as overstay  from t_cractivity ");
            sb.append(" left join t_crewrotation on (t_crewrotation.i_crewrotationid = t_cractivity.i_crewrotationid and t_crewrotation.i_active = 1 and t_cractivity.i_status = 2 and t_cractivity.i_activityid = 6) ");
            sb.append("where t_cractivity.i_signoffid <= 0 and CURRENT_DATE > ts_fromdate and ((ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"'))  and t_cractivity.i_status = 2 and t_cractivity.i_activityid = 6  group by t_cractivity.i_crewrotationid) as t15 on (t15.i_crewrotationid = cr.i_crewrotationid) ");

            sb.append(" left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  '"+changeDate1(todateIndex)+"' then  '"+changeDate1(todateIndex)+"' when ts_todate < '"+ changeDate1(fromdateIndex)+"' then '"+ changeDate1(fromdateIndex)+"'  else ts_todate end, case when ts_fromdate < '"+changeDate1(fromdateIndex)+"' then '"+changeDate1(fromdateIndex)+"'  else ts_fromdate end)) as ct1 from t_cractivity ");//early subtype=2
            sb.append(" left join t_signonoff on (t_signonoff.i_signonoffid = t_cractivity.i_signoffid and t_signonoff.i_subtype = 2 and t_signonoff.i_type = 2 and t_cractivity.i_status = 2 ) where t_cractivity.i_status = 2 and i_activityid = 6 and t_signonoff.i_subtype = 2 and t_signonoff.i_type = 2  and ((ts_fromdate <= '"+changeDate1(fromdateIndex)+"' and ts_todate >= '"+changeDate1(fromdateIndex)+"') or (ts_fromdate <= '"+changeDate1(todateIndex)+"' and ts_todate >= '"+changeDate1(todateIndex)+"') or (ts_fromdate >= '"+changeDate1(fromdateIndex)+"' and ts_fromdate <= '"+changeDate1(todateIndex)+"') or (ts_todate >= '"+changeDate1(fromdateIndex)+"' and ts_todate <= '"+changeDate1(todateIndex)+"')) group by t_cractivity.i_crewrotationid) as t11 on (t11.i_crewrotationid = cr.i_crewrotationid) ");

            sb.append(" where  cr.i_active = 1 AND cr.i_clientid = ? AND cr.i_clientassetid = ? ");        
            if (positioncb != null && !positioncb.equals("")) {
                sb.append("AND c.i_positionid2 in ("+positioncb+") ");
            }
            if (crewrotationcb != null && !crewrotationcb.equals("")) {
                sb.append("AND cr.i_crewrotationid in ("+crewrotationcb+") ");
            }
            sb.append(" order by c.s_firstname, c.s_middlename, c.s_lastname ");

            query = (sb.toString()).intern();
            sb.setLength(0);
             logger.info("getClientSelectByName2:: " + query);
            
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientIdIndex);
                pstmt.setInt(2, assetIdIndex);
                logger.info("getClientSelectByName2:: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) 
                {
                    crewrotationId = rs.getInt(1);
                    candidateName = rs.getString(2) != null ? rs.getString(2) : "";
                    position = rs.getString(3) != null ? rs.getString(3) : "";
                    grade = rs.getString(4) != null ? rs.getString(4) : "";
                    rotations = rs.getInt(5) > 0 ? rs.getInt(5) : 0;
                    normal = rs.getInt(6) > 0 ? rs.getInt(6) : 0;
                    extended = rs.getInt(7) > 0 ? rs.getInt(7) : 0;
                    overstay = rs.getInt(8) > 0 ? rs.getInt(8) : 0;
                    officework = rs.getInt(9) > 0 ? rs.getInt(9) : 0;
                    training = rs.getInt(10) > 0 ? rs.getInt(10) : 0;
                    absent = rs.getInt(11) > 0 ? rs.getInt(11) : 0;
                    available = rs.getInt(12) > 0 ? rs.getInt(12) : 0;
                    noavailable = rs.getInt(13) > 0 ? rs.getInt(13) : 0;
                    totalabsent = rs.getInt(14) > 0 ? rs.getInt(14) : 0;
                    early = rs.getInt(15) > 0 ? rs.getInt(15) : 0;
                    noofdays = rs.getInt(16) > 0 ? rs.getInt(16) : 0;                
                    if (!grade.equals("")) {
                        position += " - " + grade;
                    }
                    list.add(new DashboardInfo(crewrotationId, candidateName, position, grade, rotations, normal, extended, overstay, officework, training, available, absent, noavailable, totalabsent, early, noofdays));
                }
                rs.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public DashboardInfo getListFromcractivity(int clientIdIndex, int assetIdIndex, String fromdateIndex, String todateIndex) 
    {
        DashboardInfo listinfo = null;  
        ArrayList list1 = new ArrayList();
        ArrayList list2 = new ArrayList();
        ArrayList list3 = new ArrayList();
        ArrayList list4 = new ArrayList();
        ArrayList list5 = new ArrayList();
        ArrayList list6 = new ArrayList();
        ArrayList list7 = new ArrayList();//offshore training
        ArrayList list8 = new ArrayList();//offshore Temporary Promotion
        ArrayList list9 = new ArrayList(); //for standby
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, DATE_FORMAT( t_signonoff.ts_date1, '%Y-%m-%d'),t_signonoff.i_subtype,  ");
        sb.append(" DATE_FORMAT( t_cractivity.ts_overstaydate, '%Y-%m-%d') , i_extendeddays,i_overstaydays, DATE_FORMAT( s2.ts_date1, '%Y-%m-%d'), ");
        sb.append("t_crewrotation.i_candidateid, t_cractivity.i_positionid " );
        sb.append("FROM t_cractivity " );
        sb.append(" left join t_signonoff on ( t_cractivity.i_signoffid = t_signonoff.i_signonoffid and t_signonoff.i_type = 2) ");
        sb.append(" left join t_crewrotation on ( t_crewrotation.i_crewrotationid = t_cractivity.i_crewrotationid) ");
        sb.append(" left join t_signonoff as s2 on ( t_cractivity.i_signonid = s2.i_signonoffid and s2.i_type = 1 and s2.i_subtype = 2) ");
        sb.append(" where t_cractivity.i_activityid = 6 and t_cractivity.i_signoffid > 0  ");        
        if (clientIdIndex > 0) {
            sb.append("AND t_crewrotation.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND t_crewrotation.i_clientassetid = ? ");
        }        
        if (fromdateIndex != null && !fromdateIndex.equals("") && todateIndex != null && !todateIndex.equals("")) {
            sb.append(" and (( t_cractivity.ts_fromdate  >= ?  and  t_cractivity.ts_fromdate  <= ? ) OR ( t_cractivity.ts_todate  >= ?  and  t_cractivity.ts_todate  <= ? ) OR ( ? >= ts_fromdate and ? <= ts_todate) )");
        }         
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
            if (fromdateIndex != null && !fromdateIndex.equals("") && todateIndex != null && !todateIndex.equals("")) {
                pstmt.setString(++scc, changeDate1(fromdateIndex));
                pstmt.setString(++scc, changeDate1(todateIndex));
                pstmt.setString(++scc, changeDate1(fromdateIndex));
                pstmt.setString(++scc, changeDate1(todateIndex));
                pstmt.setString(++scc, changeDate1(fromdateIndex));
                pstmt.setString(++scc, changeDate1(fromdateIndex));
            }
            logger.info("getListFromcractivity:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String fromdate, todate, expecteddate, overstaydate, fromdate2;
            int crewrotationId, subtype = 0,extendeddays=0,overstaydays=0,candidateId=0, positionId =0;
            while (rs.next()) 
            {
                crewrotationId = rs.getInt(1);
                fromdate = rs.getString(2) != null ? rs.getString(2) : "";
                todate = rs.getString(3) != null ? rs.getString(3) : "";
                expecteddate = rs.getString(4) != null && !rs.getString(4).equals("0000-00-00") ? rs.getString(4) : "";
                subtype = rs.getInt(5);
                overstaydate = rs.getString(6) != null && !rs.getString(6).equals("0000-00-00") ? rs.getString(6) : "";
                extendeddays = rs.getInt(7);
                overstaydays = rs.getInt(8);
                fromdate2 = rs.getString(9) != null && !rs.getString(9).equals("0000-00-00") ? rs.getString(9) : "0000-00-00";
                candidateId = rs.getInt(10);
                positionId = rs.getInt(11);
                if(subtype == 1 || subtype == 2){
                    list1.add(new DashboardInfo(crewrotationId,fromdate, todate,"","","",1, getDateAfter(fromdate2, 1, -1, "yyyy-MM-dd", "yyyy-MM-dd"),candidateId, positionId));
                }
                else if(subtype == 3)
                {
                    if(!overstaydate.equals(""))
                    {
                        list1.add(new DashboardInfo(crewrotationId,fromdate, expecteddate,"","","",3, getDateAfter(fromdate2, 1, -1, "yyyy-MM-dd", "yyyy-MM-dd"),candidateId, positionId));
                        list2.add(new DashboardInfo(crewrotationId, getDateAfter(expecteddate, 1, 1, "yyyy-MM-dd", "yyyy-MM-dd"),overstaydate,"","","",3, "",candidateId, positionId));
                        list3.add(new DashboardInfo(crewrotationId, getDateAfter(overstaydate, 1, 1, "yyyy-MM-dd", "yyyy-MM-dd"), todate,"","","",3, "", candidateId, positionId));
                    }
                    else if(overstaydate.equals(""))
                    {
                        list1.add(new DashboardInfo(crewrotationId,fromdate, expecteddate,"","","",2, getDateAfter(fromdate2, 1, -1, "yyyy-MM-dd", "yyyy-MM-dd"), candidateId, positionId));
                        list2.add(new DashboardInfo(crewrotationId, getDateAfter(expecteddate, 1, 1, "yyyy-MM-dd", "yyyy-MM-dd"), todate,"","","",2, "", candidateId, positionId));
                    }
                }
            }
            rs.close();
            pstmt.close();
        
            sb.append(" select t_crewrotation.i_crewrotationid, ts_fromdate, case when CURRENT_DATE >  ts_todate then CURRENT_DATE else ts_todate end, ");
            sb.append("CURRENT_DATE,  DATE_FORMAT(t_crewrotation.d_rdate1, '%Y-%m-%d'), DATE_FORMAT(t_crewrotation.d_rdate2 , '%Y-%m-%d'), ");
            sb.append("DATE_FORMAT( s2.ts_date1, '%Y-%m-%d'), t_crewrotation.i_candidateid, t_cractivity.i_positionid ");
            sb.append(" from t_cractivity ");
            sb.append("left join t_crewrotation on (t_crewrotation.i_crewrotationid = t_cractivity.i_crewrotationid) ");
            sb.append(" left join t_signonoff as s2 on ( t_cractivity.i_signonid = s2.i_signonoffid and s2.i_type = 1 and s2.i_subtype = 2) ");
            sb.append(" where i_signoffid <= 0 and t_cractivity.i_activityid = 6 and ((ts_fromdate >= ? and ts_fromdate <= ?) OR (CURRENT_DATE >= ? and CURRENT_DATE <= ?) OR ( ? >= ts_fromdate and ? <= ts_todate)) ");

            if (clientIdIndex > 0) {
                sb.append("AND t_crewrotation.i_clientid = ? ");
            }
            if (assetIdIndex > 0) {
                sb.append("AND t_crewrotation.i_clientassetid = ? ");
            }
            String query1 = (sb.toString()).intern();
            sb.setLength(0);

            pstmt = conn.prepareStatement(query1);
            scc = 0;
            
            pstmt.setString(++scc, changeDate1(fromdateIndex));
            pstmt.setString(++scc, changeDate1(todateIndex));
            pstmt.setString(++scc, changeDate1(fromdateIndex));
            pstmt.setString(++scc, changeDate1(todateIndex));
            pstmt.setString(++scc, changeDate1(fromdateIndex));
            pstmt.setString(++scc, changeDate1(fromdateIndex));            
            
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }

            logger.info("getList4Fromcractivity:: " + pstmt.toString());
            rs = pstmt.executeQuery();
             fromdate = "";
             todate = "";
             fromdate2 = "";
             String currentdate = "", rdate1 = "", rdate2 = "";
             crewrotationId = 0;
            while (rs.next()) {
                crewrotationId = rs.getInt(1);
                fromdate = rs.getString(2) != null ? rs.getString(2) : "";
                todate = rs.getString(3) != null ? rs.getString(3) : "";
                currentdate = rs.getString(4) != null ? rs.getString(4) : "";
                rdate1 = rs.getString(5) != null ? rs.getString(5) : "";
                rdate2 = rs.getString(6) != null ? rs.getString(6) : "";
                fromdate2 = rs.getString(7) != null && !rs.getString(7).equals("0000-00-00") ? rs.getString(7) : "0000-00-00";
                candidateId = rs.getInt(8);
                positionId = rs.getInt(9);
                list4.add(new DashboardInfo(crewrotationId,fromdate, todate, currentdate, rdate1, rdate2,4,  getDateAfter(fromdate2, 1, -1, "yyyy-MM-dd", "yyyy-MM-dd"), candidateId, positionId));
                
            }
            rs.close();
            //for planning
            sb.append(" select t_planning.i_crewrotationid, t_planning.d_fromdate, t_planning.d_todate, t_crewrotation.i_candidateid, t_candidate.i_positionid ");
            sb.append(" from t_planning ");
            sb.append("left join t_crewrotation on (t_crewrotation.i_crewrotationid = t_planning.i_crewrotationid) ");
            sb.append("left join t_candidate on (t_crewrotation.i_candidateid = t_candidate.i_candidateid) ");
            sb.append("where (t_planning.d_fromdate >= CURRENT_DATE() OR t_planning.d_todate >= CURRENT_DATE()) ");
            sb.append("AND t_crewrotation.i_clientid = ? AND t_crewrotation.i_clientassetid = ? AND t_planning.i_type = 1 ");
            query1 = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query1);
            pstmt.setInt(1, clientIdIndex);
            pstmt.setInt(2, assetIdIndex);

            logger.info("getList5Fromcractivity planning :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            fromdate = "";
            todate = "";
            crewrotationId = 0;
            while (rs.next()) 
            {
                crewrotationId = rs.getInt(1);
                fromdate = rs.getString(2) != null ? rs.getString(2) : "";
                todate = rs.getString(3) != null ? rs.getString(3) : "";     
                candidateId = rs.getInt(4);
                positionId = rs.getInt(5);
                list5.add(new DashboardInfo(crewrotationId, fromdate, todate, "", "", "", 5, "", candidateId, positionId));                
            }
            rs.close();            
            //for delayed signoff
            sb.append("SELECT s.i_crewrotationid, DATE_FORMAT(s.ts_date1, '%Y-%m-%d'), DATE_FORMAT(s.ts_date2, '%Y-%m-%d'), cr.i_candidateid, s.i_positionid ");
            sb.append("FROM t_signonoff as s ");
            sb.append("left join t_crewrotation as cr on (cr.i_crewrotationid = s.i_crewrotationid) ");
            sb.append("where cr.i_clientid = ?  AND cr.i_clientassetid = ? and s.i_type = 1 and s.i_subtype = 3 ");
            sb.append("and ((DATE_FORMAT(s.ts_date1, '%Y-%m-%d') >= ?  and DATE_FORMAT(s.ts_date1, '%Y-%m-%d')  <= ?) OR (DATE_FORMAT(s.ts_date2, '%Y-%m-%d') >= ?  and  DATE_FORMAT(s.ts_date2, '%Y-%m-%d') <= ?) OR (? >= DATE_FORMAT(s.ts_date1, '%Y-%m-%d') and ? <= DATE_FORMAT(s.ts_date2, '%Y-%m-%d'))) ");
            query1 = (sb.toString()).intern();
            sb.setLength(0);
            scc = 0;
            pstmt = conn.prepareStatement(query1);
            pstmt.setInt(++scc, clientIdIndex);
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setString(++scc, changeDate1(fromdateIndex));
            pstmt.setString(++scc, changeDate1(todateIndex));
            pstmt.setString(++scc, changeDate1(fromdateIndex));
            pstmt.setString(++scc, changeDate1(todateIndex));
            pstmt.setString(++scc, changeDate1(fromdateIndex));
            pstmt.setString(++scc, changeDate1(fromdateIndex));

            logger.info("getList6Fromcractivity delayed signoff :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            fromdate = "";
            todate = "";
            crewrotationId = 0;
            while (rs.next()) 
            {
                crewrotationId = rs.getInt(1);
                fromdate = rs.getString(2) != null ? rs.getString(2) : "";
                todate = rs.getString(3) != null ? rs.getString(3) : "";    
                candidateId = rs.getInt(4);
                positionId = rs.getInt(5);
                list6.add(new DashboardInfo(crewrotationId, fromdate, todate, "", "", "", 6, "",candidateId, positionId));                
            }
            rs.close();
            
            //for offshore training
            sb.append("SELECT s.i_crewrotationid, DATE_FORMAT(s.ts_fromdate, '%Y-%m-%d'), DATE_FORMAT(s.ts_todate, '%Y-%m-%d'), s.i_activityid, cr.i_candidateid, s.i_positionid ");
            sb.append("FROM t_cractivity as s left join t_crewrotation as cr on (cr.i_crewrotationid = s.i_crewrotationid) ");
            sb.append("where cr.i_clientid = ?  AND cr.i_clientassetid = ? and s.i_activityid IN (2,8) and s.i_status = 2 ");
            sb.append("and ((DATE_FORMAT(s.ts_fromdate, '%Y-%m-%d') >= ?  and DATE_FORMAT(s.ts_fromdate, '%Y-%m-%d')  <= ?) OR (DATE_FORMAT(s.ts_todate, '%Y-%m-%d') >= ?  and  DATE_FORMAT(s.ts_todate, '%Y-%m-%d') <= ?) OR (? >= DATE_FORMAT(s.ts_fromdate, '%Y-%m-%d') and ? <= DATE_FORMAT(s.ts_todate, '%Y-%m-%d'))) ");
            query1 = (sb.toString()).intern();
            sb.setLength(0);
            scc = 0;
            pstmt = conn.prepareStatement(query1);
            pstmt.setInt(++scc, clientIdIndex);
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setString(++scc, changeDate1(fromdateIndex));
            pstmt.setString(++scc, changeDate1(todateIndex));
            pstmt.setString(++scc, changeDate1(fromdateIndex));
            pstmt.setString(++scc, changeDate1(todateIndex));
            pstmt.setString(++scc, changeDate1(fromdateIndex));
            pstmt.setString(++scc, changeDate1(fromdateIndex));

            logger.info("getList6Fromcractivity offshore training:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            fromdate = "";
            todate = "";
            crewrotationId = 0;
            while (rs.next()) 
            {
                crewrotationId = rs.getInt(1);
                fromdate = rs.getString(2) != null ? rs.getString(2) : "";
                todate = rs.getString(3) != null ? rs.getString(3) : "";  
                int activityId = rs.getInt(4);
                candidateId = rs.getInt(5);
                positionId = rs.getInt(6);
                if(activityId == 2)
                    list7.add(new DashboardInfo(crewrotationId, fromdate, todate, "", "", "", 8, "",candidateId, positionId));  
                else
                    list9.add(new DashboardInfo(crewrotationId, fromdate, todate, "", "", "", 10, "",candidateId, positionId));  
            }
            rs.close();
            
            //for offshore Temporary Promotion
            sb.append("SELECT s.i_crewrotationid, DATE_FORMAT(s.ts_fromdate, '%Y-%m-%d'), DATE_FORMAT(s.ts_todate, '%Y-%m-%d'), cr.i_candidateid, s.i_positionid ");
            sb.append("FROM t_cractivity as s left join t_crewrotation as cr on (cr.i_crewrotationid = s.i_crewrotationid) ");
            sb.append("where cr.i_clientid = ?  AND cr.i_clientassetid = ? and s.i_activityid = 7 and s.i_status = 2 ");
            sb.append("and ((DATE_FORMAT(s.ts_fromdate, '%Y-%m-%d') >= ?  and DATE_FORMAT(s.ts_fromdate, '%Y-%m-%d')  <= ?) OR (DATE_FORMAT(s.ts_todate, '%Y-%m-%d') >= ?  and  DATE_FORMAT(s.ts_todate, '%Y-%m-%d') <= ?) OR (? >= DATE_FORMAT(s.ts_fromdate, '%Y-%m-%d') and ? <= DATE_FORMAT(s.ts_todate, '%Y-%m-%d'))) ");
            query1 = (sb.toString()).intern();
            sb.setLength(0);
            scc = 0;
            pstmt = conn.prepareStatement(query1);
            pstmt.setInt(++scc, clientIdIndex);
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setString(++scc, changeDate1(fromdateIndex));
            pstmt.setString(++scc, changeDate1(todateIndex));
            pstmt.setString(++scc, changeDate1(fromdateIndex));
            pstmt.setString(++scc, changeDate1(todateIndex));
            pstmt.setString(++scc, changeDate1(fromdateIndex));
            pstmt.setString(++scc, changeDate1(fromdateIndex));

            logger.info("getList6Fromcractivity Temporary Promotion :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            fromdate = "";
            todate = "";
            crewrotationId = 0;            
            while (rs.next()) 
            {
                crewrotationId = rs.getInt(1);
                fromdate = rs.getString(2) != null ? rs.getString(2) : "";
                todate = rs.getString(3) != null ? rs.getString(3) : "";  
                candidateId = rs.getInt(4);
                positionId = rs.getInt(5);
                list8.add(new DashboardInfo(crewrotationId, fromdate, todate, "", "", "", 9, "",candidateId, positionId));                
            }
            rs.close();
            
            listinfo = new DashboardInfo(list1, list2, list3, list4, list5, list6, list7, list8, list9,candidateId, positionId);  
                        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return listinfo;
    }
    
    public int checkDate(String fdate, String tdate, String date, String format)
    {
        int ch = 0;
        try
        {
            if(fdate != null && !fdate.equals("") && tdate != null && !tdate.equals("") && date != null && !date.equals(""))
            {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                long s = sdf.parse(fdate).getTime();
                long e = sdf.parse(tdate).getTime();
                long d = sdf.parse(date).getTime();
                if(e-d >= 0 && d-s >= 0)
                    ch = 1;
            }
        }
        catch(Exception e)
        {
            ch = 0;
        }
        return ch;
    }

    public int checkwork(ArrayList list4, ArrayList list3, ArrayList list2, ArrayList list1, String date, 
        ArrayList list5, ArrayList list6, ArrayList list7, ArrayList list8, ArrayList list9, int candidateId, int positionId )
    {
        
        int cc = 0;
        int size4 = list4.size();
        int size3 = list3.size();
        int size2 = list2.size();
        int size1 = list1.size();
        int size5 = list5.size();
        int size6 = list6.size();
        int size7 = list7.size();
        int size8 = list8.size();
        int size9 = list9.size();
        if(size9 > 0 && cc == 0)
        {
            for(int i = 0; i < size9; i++)
            {
                DashboardInfo info = (DashboardInfo) list9.get(i);
                if(info != null && info.getCandidateId()== candidateId && info.getPositionId() == positionId)
                {                    
                    if(checkDate(info.getFromdate(), info.getTodate(), date, "yyyy-MM-dd") == 1)
                    {
                        cc = 10;//standby
                        return cc;
                    }
                }
            }
        }
        if(size7 > 0 && cc == 0)
        {
            for(int i = 0; i < size7; i++)
            {
                DashboardInfo info = (DashboardInfo) list7.get(i);
                if(info != null && info.getCandidateId()== candidateId && info.getPositionId() == positionId)
                {                    
                    if(checkDate(info.getFromdate(), info.getTodate(), date, "yyyy-MM-dd")==1)
                    {
                        cc = 8;//Training
                        return cc;
                    }
                }
            }
        }
        if(size8 > 0 && cc == 0)
        {
            for(int i = 0; i < size8; i++)
            {
                DashboardInfo info = (DashboardInfo) list8.get(i);
                if(info != null && info.getCandidateId()== candidateId && info.getPositionId() == positionId)
                {     
                    if(checkDate(info.getFromdate(), info.getTodate(), date, "yyyy-MM-dd")==1)
                    {
                        cc = 9;//Temporary Promotion
                        return cc;
                    }
                }
            }
        }
        if(size3 > 0 && cc == 0)
        {
            for(int i = 0; i < size3; i++)
            {
                DashboardInfo info = (DashboardInfo) list3.get(i);
                if(info != null && info.getCandidateId()== candidateId && info.getPositionId() == positionId)
                {
                    if(info.getTodate().equals(date))
                    {
                        cc = 4;
                        return cc;
                    }
                    else if(checkDate(info.getFromdate(), info.getTodate(), date, "yyyy-MM-dd")==1)
                    {
                        cc = 3;
                        return cc;
                    }
                }
            }
        }
        if(size2 > 0 && cc == 0)
        {
            for(int i = 0; i < size2; i++)
            {
                DashboardInfo info = (DashboardInfo) list2.get(i);
                if(info != null && info.getCandidateId()== candidateId && info.getPositionId() == positionId)
                {
                    if(info.getTodate().equals(date))
                    {
                        if(info.getListflag() == 3)
                        {
                            cc = 3;
                        }
                        else if(info.getListflag() == 2)
                        {                      
                            cc = 4;
                        }
                        return cc;
                    }
                    else if(checkDate(info.getFromdate(), info.getTodate(), date, "yyyy-MM-dd")==1)
                    {
                        cc = 2;
                        return cc;
                    }
                }
            }
        }
        if(size1 > 0 && cc == 0)
        {
            for(int i = 0; i < size1; i++)
            {
                DashboardInfo info = (DashboardInfo) list1.get(i);
                if(info != null && info.getCandidateId()== candidateId && info.getPositionId() == positionId)
                {
                    if(info.getTodate().equals(date))
                    {
                        if(info.getListflag() == 3)
                        {
                            cc = 2;
                        }
                        else if(info.getListflag() == 2)
                        {
                            cc = 2;
                        }
                        else if(info.getListflag() == 1)
                        {                      
                            cc = 4;
                        }
                        return cc;
                    }
                    else if(info.getFromdate2() != null && !info.getFromdate2().equals("") && checkDate(info.getFromdate(), info.getFromdate2(), date, "yyyy-MM-dd") == 1)
                    {
                        cc = 5;
                        return cc;
                    }
                    else if(checkDate(info.getFromdate(), info.getTodate(), date, "yyyy-MM-dd")==1)
                    {
                        cc = 1;
                        return cc;
                    }
                }
            }
        }
        if(size4 > 0 && cc == 0)
        {
            for(int i = 0; i < size4; i++)
            {
                DashboardInfo info = (DashboardInfo) list4.get(i);
                if(info != null && info.getCandidateId()== candidateId && info.getPositionId() == positionId)
                {                    
                    if(checkDate(info.getRdate2(), info.getCurrentdate(), date, "yyyy-MM-dd")==1 && !info.getRdate2().equals("0000-00-00"))
                    {
                        cc = 3;
                        return cc;
                    }
                    else if(checkDate(info.getRdate1(), info.getRdate2(), date, "yyyy-MM-dd")==1&& !info.getRdate2().equals("0000-00-00")&& !info.getRdate1().equals("0000-00-00"))
                    {
                        cc = 2;
                        return cc;
                    }                    
                    else if(info.getFromdate2() != null && !info.getFromdate2().equals("") && checkDate(info.getFromdate(), info.getFromdate2(), date, "yyyy-MM-dd") == 1)
                    {
                        cc = 5;
                        return cc;
                    }
                    else if(checkDate(info.getFromdate(), info.getTodate(), date, "yyyy-MM-dd")==1)
                    {
                        cc = 1;
                        return cc;
                    }
                }
            }        
        }
        if(size6 > 0 && cc == 0)
        {
            for(int i = 0; i < size6; i++)
            {
                DashboardInfo info = (DashboardInfo) list6.get(i);
                if(info != null && info.getCandidateId()== candidateId && info.getPositionId() == positionId)
                {  
                    if(checkDate(info.getFromdate(), info.getTodate(), date, "yyyy-MM-dd") == 1)
                    {
                        cc = 7;
                        return cc;
                    }
                }
            }        
        }
        if(size5 > 0 && cc == 0)
        {
            for(int i = 0; i < size5; i++)
            {
                DashboardInfo info = (DashboardInfo) list5.get(i);
                if(info != null && info.getCandidateId()== candidateId && info.getPositionId() == positionId)
                {  
                    if(checkDate(info.getFromdate(), info.getTodate(), date, "yyyy-MM-dd") == 1)
                    {
                        cc = 6;
                        return cc;
                    }
                }
            }        
        }        
        return cc;
    }
    
    public DashboardInfo getByclientandAssetName(int clientIdIndex, int assetIdIndex) {

        DashboardInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select SUM(if(i_status1 = 1 && i_active = 1, 1, 0)), SUM(if(i_status2 = 1 && i_active = 1, 1, 0)) ");
        sb.append(" AS ct1,   SUM(if(i_status2 = 2 && i_active = 1, 1, 0)), SUM(if(i_status2 = 3 && i_active = 1, 1, 0)) AS ct2  from t_crewrotation ");
        sb.append(" where  t_crewrotation.i_active = 1 AND  t_crewrotation.i_clientid = ? AND t_crewrotation.i_clientassetid = ?  ");
        String query = (sb.toString()).intern();
        sb.setLength(0);

        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientIdIndex);
            pstmt.setInt(++scc, assetIdIndex);

            logger.info("getByclientandAssetName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int  status1, normal, delayed, extended;
            while (rs.next()) 
            {
                status1 = rs.getInt(1);
                normal = rs.getInt(2);
                delayed = rs.getInt(3);
                extended = rs.getInt(4);
                info = new DashboardInfo( status1, normal, delayed, extended);
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public DashboardInfo getByclientandAssetName1(int clientIdIndex, int assetIdIndex) {

        DashboardInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select SUM(if(i_status1 = 1 && i_active = 1, 1, 0)), SUM(if(i_status2 = 1 && i_active = 1, 1, 0)) ");
        sb.append(" AS ct1,   SUM(if(i_status2 = 2 && i_active = 1, 1, 0)), SUM(if(i_status2 = 3 && i_active = 1, 1, 0)) AS ct2  from t_crewrotation ");
        sb.append(" where  t_crewrotation.i_active = 1 AND  t_crewrotation.i_clientid = ? AND t_crewrotation.i_clientassetid = ?  ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientIdIndex);
            pstmt.setInt(++scc, assetIdIndex);

            logger.info("getByclientandAssetName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int  status1, normal, delayed, extended;
            while (rs.next()) 
            {
                status1 = rs.getInt(1);
                normal = rs.getInt(2);
                delayed = rs.getInt(3);
                extended = rs.getInt(4);
                info = new DashboardInfo( status1, normal, delayed, extended);
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public ArrayList datelist(String fromDate, String toDate)
    {
        Base base = new Base();
        ArrayList list = new ArrayList();
        try
        {            
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
            list.add(sdf3.format(sdf1.parse(fromDate)));
            while(!fromDate.equals(toDate))
            {
                fromDate = base.getDateAfter(fromDate, 1, 1, "dd-MMM-yyyy", "dd-MMM-yyyy");
                list.add(sdf3.format(sdf1.parse(fromDate))); 
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList datelistday(String fromDate, String toDate)
    {
        Base base = new Base();
        ArrayList list = new ArrayList();
        try
        {            
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
            list.add(sdf2.format(sdf1.parse(fromDate)));
            while(!fromDate.equals(toDate))
            {
                fromDate = base.getDateAfter(fromDate, 1, 1, "dd-MMM-yyyy", "dd-MMM-yyyy");
                list.add(sdf2.format(sdf1.parse(fromDate))); 
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    
    public String[] findFromToDate(int month, int year) 
    {
        month = month - 1;
        String date[] = new String[2];
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String fromDate = sdf.format(cal.getTime());

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        String toDate = sdf.format(cal.getTime());
        date[0] = fromDate;
        date[1] = toDate;
        return date;
    }
    
    public ArrayList getCandidatelistfromcrewrotation(
            int clientIdIndex, int assetIdIndex, String fromdateIndex, String todateIndex, String positioncb, String crewrotationcb) {

        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append(" select cr.i_crewrotationid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname),  t_position.s_name, t_grade.s_name, t1.ct,  IF(cr.i_status1 > 0 &&  cr.i_status2<=0 , 1, 0), cr.i_candidateid, t_position.i_positionid ");
        sb.append(" from t_crewrotation as cr left join t_candidate as c on (c.i_candidateid = cr.i_candidateid) ");
        sb.append(" left join (select i_crewrotationid, count(1) as ct from t_cractivity where i_activityid = 6 and i_signoffid > 0 and ( t_cractivity.ts_todate  >= ?  and  t_cractivity.ts_todate  <= ? ) group by i_crewrotationid) as t1 on (t1.i_crewrotationid = cr.i_crewrotationid) ");
        sb.append(" LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append(" LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append(" where  cr.i_active = 1 ");
        
        if (clientIdIndex > 0) {
            sb.append("AND cr.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND cr.i_clientassetid = ? ");
        }
        
        if (positioncb != null && !positioncb.equals("")) {
            sb.append("AND c.i_positionid in ("+positioncb+") ");
        }
        if (crewrotationcb != null && !crewrotationcb.equals("")) {
            sb.append("AND cr.i_crewrotationid in ("+crewrotationcb+") ");
        }
        sb.append("  order by t_position.s_name, t_grade.s_name, c.s_firstname ");
        
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, changeDate1(fromdateIndex));
            pstmt.setString(++scc, changeDate1(todateIndex));
            
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            logger.info("getCandidatelistfromcrewrotation:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String candidateName, position, grade;
            int crewrotationId,  rotations,  status = 0, candidateId, positionId;
            String temp = "";
            while (rs.next())
            {
                crewrotationId = rs.getInt(1);
                candidateName = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                grade = rs.getString(4) != null ? rs.getString(4) : "";
                rotations = rs.getInt(5);
                status = rs.getInt(6);                
                candidateId = rs.getInt(7);                
                positionId = rs.getInt(8);
                if (!grade.equals("")) {
                    position += " - " + grade;
                }
                if(position.equals(temp))
                {
                    list.add(new DashboardInfo(crewrotationId, candidateName, "", grade, rotations,status,candidateId, positionId));
                }
                else
                {
                    list.add(new DashboardInfo(crewrotationId, candidateName, position, grade, rotations,status, candidateId, positionId));
                    temp = position;
                }
            }
            rs.close();
            
            sb.append(" select cr.i_crewrotationid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname),  t_position.s_name, t_grade.s_name, t1.ct,  IF(cr.i_status1 > 0 &&  cr.i_status2<=0 , 1, 0), cr.i_candidateid, t_position.i_positionid ");
            sb.append(" from t_crewrotation as cr left join t_candidate as c on (c.i_candidateid = cr.i_candidateid) ");
            sb.append(" left join (select i_crewrotationid, count(1) as ct from t_cractivity where i_activityid = 6 and i_signoffid > 0 and ( t_cractivity.ts_todate  >= ?  and  t_cractivity.ts_todate  <= ? ) group by i_crewrotationid) as t1 on (t1.i_crewrotationid = cr.i_crewrotationid) ");
            sb.append(" LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid2) ");
            sb.append(" LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append(" where  cr.i_active = 1 ");

            if (clientIdIndex > 0) {
                sb.append("AND cr.i_clientid = ? ");
            }
            if (assetIdIndex > 0) {
                sb.append("AND cr.i_clientassetid = ? ");
            }

            if (positioncb != null && !positioncb.equals("")) {
                sb.append("AND c.i_positionid2 in ("+positioncb+") ");
            }
            if (crewrotationcb != null && !crewrotationcb.equals("")) {
                sb.append("AND cr.i_crewrotationid in ("+crewrotationcb+") ");
            }
            sb.append("  order by t_position.s_name, t_grade.s_name, c.s_firstname ");

            query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            scc = 0;
            pstmt.setString(++scc, changeDate1(fromdateIndex));
            pstmt.setString(++scc, changeDate1(todateIndex));

            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            logger.info("getCandidatelistfromcrewrotation2 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                crewrotationId = rs.getInt(1);
                candidateName = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                grade = rs.getString(4) != null ? rs.getString(4) : "";
                rotations = rs.getInt(5);
                status = rs.getInt(6);                
                candidateId = rs.getInt(7);   
                positionId = rs.getInt(8);
                if (!grade.equals("")) {
                    position += " - " + grade;
                }
                if(position.equals(temp))
                {
                    list.add(new DashboardInfo(crewrotationId, candidateName, "", grade, rotations,status, candidateId, positionId));
                }
                else
                {
                    list.add(new DashboardInfo(crewrotationId, candidateName, position, grade, rotations,status, candidateId, positionId));
                    temp = position;
                }
            }
            rs.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    public ArrayList getPersonnellistfromcrewrotation(
            int clientIdIndex, int assetIdIndex, String fromdateIndex, String todateIndex, String positioncb, String crewrotationcb,String searchPersonnel) {

        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append(" select cr.i_crewrotationid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname),  t_position.s_name, t_grade.s_name, t1.ct,  IF(cr.i_status1 > 0 &&  cr.i_status2<=0 , 1, 0), cr.i_candidateid, t_position.i_positionid ");
        sb.append(" from t_crewrotation as cr left join t_candidate as c on (c.i_candidateid = cr.i_candidateid) ");
        sb.append(" left join (select i_crewrotationid, count(1) as ct from t_cractivity where i_activityid = 6 and i_signoffid > 0 and ( t_cractivity.ts_todate  >= ?  and  t_cractivity.ts_todate  <= ? ) group by i_crewrotationid) as t1 on (t1.i_crewrotationid = cr.i_crewrotationid) ");
        sb.append(" LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append(" LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append(" where  cr.i_active = 1 ");
        
        if (clientIdIndex > 0) {
            sb.append("AND cr.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND cr.i_clientassetid = ? ");
        }
        if (positioncb != null && !positioncb.equals("")) {
            sb.append("AND c.i_positionid in ("+positioncb+") ");
        }
        if (crewrotationcb != null && !crewrotationcb.equals("")) {
            sb.append("AND cr.i_crewrotationid in ("+crewrotationcb+") ");
        }
        if (searchPersonnel != null && !searchPersonnel.equals("")) {
            sb.append("AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? ) ");
        }
        sb.append("  order by c.s_firstname ");
        
        String query = (sb.toString()).intern();
        sb.setLength(0);

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, changeDate1(fromdateIndex));
            pstmt.setString(++scc, changeDate1(todateIndex));
            
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
             if (searchPersonnel != null && !searchPersonnel.equals("")) {
                pstmt.setString(++scc, "%" + (searchPersonnel) + "%");
                pstmt.setString(++scc, "%" + (searchPersonnel) + "%");
             }
            logger.info("getPersonnellistfromcrewrotation:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String candidateName, position, grade;
            int crewrotationId,  rotations,  status = 0,candidateId, positionId;
            while (rs.next())
            {
                crewrotationId = rs.getInt(1);
                candidateName = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                grade = rs.getString(4) != null ? rs.getString(4) : "";
                rotations = rs.getInt(5);
                status = rs.getInt(6);   
                candidateId = rs.getInt(7);   
                positionId = rs.getInt(8);
                if (!grade.equals("")) {
                    position += " - " + grade;
                }
                list.add(new DashboardInfo(crewrotationId, candidateName, position, grade, rotations,status, candidateId, positionId));
            }
            rs.close();
            
            sb.append(" select cr.i_crewrotationid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname),  t_position.s_name, t_grade.s_name, t1.ct,  IF(cr.i_status1 > 0 &&  cr.i_status2<=0 , 1, 0), cr.i_candidateid, t_position.i_positionid ");
            sb.append(" from t_crewrotation as cr left join t_candidate as c on (c.i_candidateid = cr.i_candidateid) ");
            sb.append(" left join (select i_crewrotationid, count(1) as ct from t_cractivity where i_activityid = 6 and i_signoffid > 0 and ( t_cractivity.ts_todate  >= ?  and  t_cractivity.ts_todate  <= ? ) group by i_crewrotationid) as t1 on (t1.i_crewrotationid = cr.i_crewrotationid) ");
            sb.append(" LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid2) ");
            sb.append(" LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append(" where  cr.i_active = 1 ");

            if (clientIdIndex > 0) {
                sb.append("AND cr.i_clientid = ? ");
            }
            if (assetIdIndex > 0) {
                sb.append("AND cr.i_clientassetid = ? ");
            }
            if (positioncb != null && !positioncb.equals("")) {
                sb.append("AND c.i_positionid2 in ("+positioncb+") ");
            }
            if (crewrotationcb != null && !crewrotationcb.equals("")) {
                sb.append("AND cr.i_crewrotationid in ("+crewrotationcb+") ");
            }
            if (searchPersonnel != null && !searchPersonnel.equals("")) {
                sb.append("AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? ) ");
            }
            sb.append("  order by c.s_firstname ");

            query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            scc = 0;
            pstmt.setString(++scc, changeDate1(fromdateIndex));
            pstmt.setString(++scc, changeDate1(todateIndex));

            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
             if (searchPersonnel != null && !searchPersonnel.equals("")) {
                pstmt.setString(++scc, "%" + (searchPersonnel) + "%");
                pstmt.setString(++scc, "%" + (searchPersonnel) + "%");
             }
            logger.info("getPersonnellistfromcrewrotation2 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                crewrotationId = rs.getInt(1);
                candidateName = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                grade = rs.getString(4) != null ? rs.getString(4) : "";
                rotations = rs.getInt(5);
                status = rs.getInt(6);    
                candidateId = rs.getInt(7);   
                positionId = rs.getInt(8);
                if (!grade.equals("")) {
                    position += " - " + grade;
                }
                list.add(new DashboardInfo(crewrotationId, candidateName, position, grade, rotations,status,candidateId, positionId));
            }
            rs.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getPositionlist( int assetIdIndex, String searchPosition) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append(" select t_position.i_positionid, t_position.s_name, t_grade.s_name ");
        sb.append(" from t_clientassetposition LEFT JOIN t_position on (t_clientassetposition.i_positionid = t_position.i_positionid) ");
        sb.append(" LEFT JOIN t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append(" where  t_clientassetposition.i_status = 1 ");
        if (assetIdIndex > 0) {
            sb.append("AND t_clientassetposition.i_clientassetid = ? ");
        }
        if (searchPosition != null && !searchPosition.equals("")) {
            sb.append("AND (t_position.s_name like ? OR t_grade.s_name like ?) ");
        }
        sb.append("  order by t_position.s_name ");
        
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
            if (searchPosition != null && !searchPosition.equals("")) {
                pstmt.setString(++scc, "%" + (searchPosition) + "%");
                pstmt.setString(++scc, "%" + (searchPosition) + "%");
            }
            logger.info("getPositionlist:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String  position, grade;
            int positionId;
            while (rs.next())
            {
                positionId = rs.getInt(1);
                position = rs.getString(2) != null ? rs.getString(2) : "";
                grade = rs.getString(3) != null ? rs.getString(3) : "";
                
                if (!grade.equals("")) {
                    position += " - " + grade;
                }
                list.add(new DashboardInfo(position, grade, positionId));
            }
            rs.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public String getMonthName(int id)
    {
        String s1 = "";
        switch (id) {
            case 1:
                s1 = "Jan";
                break;
            case 2:
                s1 = "Feb";
                break;
            case 3:
                s1 = "Mar";
                break;
            case 4:
                s1 = "Apr";
                break;
            case 5:
                s1 = "May";
                break;
            case 6:
                s1 = "Jun";
                break;
            case 7:
                s1 = "Jul";
                break;
            case 8:
                s1 = "Aug";
                break;
            case 9:
                s1 = "Sep";
                break;
            case 10:
                s1 = "Oct";
                break;
            case 11:
                s1 = "Nov";
                break;
            case 12:
                s1 = "Dec";
                break;
            default:
                break;
        }
        return s1;
    }
    
    public int[] getcount(int clientId, int assetId) 
    {
        int arr[] = new int[4];
        int c1 = 0, c2 = 0, c3 = 0, c4 = 0;
        String query = "select SUM(i_status1), SUM(if(i_status2 = 1, 1, 0)) AS ct1, SUM(if(i_status2 = 2, 1, 0)) AS ct2, SUM(if(i_status2 = 3, 1, 0)) AS ct3 from t_crewrotation where i_clientid = ? and i_clientassetid = ? and i_active = 1";
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientId);
            pstmt.setInt(2, assetId);
            logger.info("getcount :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                c1 = rs.getInt(1);
                c2 = rs.getInt(2);
                c3 = rs.getInt(3);
                c4 = rs.getInt(4);
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
        arr[0] = c1;
        arr[1] = c2;
        arr[2] = c3;
        arr[3] = c4;
        return arr;
    }
    
    public DashboardInfo getBydate(String date, int crewrotationId) 
    {
        DashboardInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select t_signonoff.i_subtype, t_signonoff.s_remarks, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) ");
        sb.append(" from t_signonoff left join t_crewrotation on (t_crewrotation.i_crewrotationid = t_signonoff.i_crewrotationid) ");
        sb.append(" left join  t_candidate as c on (c.i_candidateid = t_crewrotation.i_candidateid) ");
        sb.append(" where  t_signonoff.i_crewrotationid = ? AND i_type = 2 AND (DATE_FORMAT(t_signonoff.ts_date1 , '%Y-%m-%d') = ? OR DATE_FORMAT(t_signonoff.ts_date2 , '%Y-%m-%d' )= ?) ");
        String query = (sb.toString()).intern();
        sb.setLength(0);

        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setString(++scc, date);
            pstmt.setString(++scc, date);

            logger.info("getBydate :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int  subtypeid;
            String subtype, remarks, name;
            while (rs.next()) 
            {
                subtypeid = rs.getInt(1);
                remarks = rs.getString(2) != null ? rs.getString(2): "";
                name = rs.getString(3) != null ? rs.getString(3): "";
                subtype = getsubtypeString(subtypeid);
                info = new DashboardInfo(subtype, remarks, name);
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public String getsubtypeString(int id)
    {
        String s1 = "";
        switch (id) {
            case 1:
                s1 = "Normal";
                break;
            case 2:
                s1 = "Early";
                break;
            case 3:
                s1 = "Extended";
                break;
            default:
                break;
        }
        return s1;
    }
    
    public int[] getverificationcount() 
    {
        int arr[] = new int[4];
        int c1 = 0, c2 = 0, c3 = 0, c4 = 0;
        String query = "select count(1) from t_candidate where i_status = 1 and i_vflag = 4";
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            logger.info("getverificationcount :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                c1 = rs.getInt(1);
            }
            rs.close();
            
            query = "select count(1) from t_candidate where i_status = 1 and i_vflag = 3";
            pstmt = conn.prepareStatement(query);
            logger.info("getverificationcount :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                c2 = rs.getInt(1);
            }
            rs.close();
            
            query = "select count(1) from t_candidate where i_status = 1 and i_vflag NOT IN (3,4) ";
            pstmt = conn.prepareStatement(query);
            logger.info("getverificationcount :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                c3 = rs.getInt(1);
            }            
            rs.close();
            c4 = c1 + c2 + c3;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            close(conn, pstmt, rs);
        }
        arr[0] = c1;
        arr[1] = c2;
        arr[2] = c3;
        arr[3] = c4;
        return arr;
    }
    
    public int[] getassessmentcount() 
    {
        int arr[] = new int[4];
        int c1 = 0, c2 = 0, c3 = 0, c4 = 0;
        String query = "select count(1) from t_candidate where i_status = 1 and i_pass = 2";
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            logger.info("getassessmentcount :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                c1 = rs.getInt(1);
            }
            rs.close();
            
            query = "select count(1) from t_candidate where i_status = 1 and i_pass = 3";
            pstmt = conn.prepareStatement(query);
            logger.info("getassessmentcount :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                c2 = rs.getInt(1);
            }
            rs.close();
            
            query = "select count(1) from t_candidate where i_status = 1 and i_vflag IN (3,4) and i_aflag = 1 AND i_pass = 1";
            pstmt = conn.prepareStatement(query);
            logger.info("getassessmentcount :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                c3 = rs.getInt(1);
            }
            rs.close();
            
            query = "select count(1) from t_candidate where i_status = 1 and i_vflag IN (3,4) and i_aflag = 0 AND i_pass = 1";
            pstmt = conn.prepareStatement(query);
            logger.info("getassessmentcount :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                c4 = rs.getInt(1);
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
        arr[0] = c1;
        arr[1] = c2;
        arr[2] = c3;
        arr[3] = c4;
        return arr;
    }
    
    public int[] getGraph4() 
    {
        int arr[] = new int[10];      
        try 
        {
            conn = getConnection();
            for(int i = 1; i <= 10; i++)
            {
                String query = "select count(1) from t_candidate where i_status = 1 and i_cflag"+i+" IN (1,2)";
                pstmt = conn.prepareStatement(query);
                print(this, "getGraph4 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) 
                {
                    if(i == 1)
                        arr[0] = rs.getInt(1);
                    else if(i == 2)
                        arr[1] = rs.getInt(1);
                    else if(i == 3)
                        arr[2] = rs.getInt(1);
                    else if(i == 4)
                        arr[3] = rs.getInt(1);
                    else if(i == 5)
                        arr[4] = rs.getInt(1);
                    else if(i == 6)
                        arr[5] = rs.getInt(1);
                    else if(i == 7)
                        arr[6] = rs.getInt(1);
                    else if(i == 8)
                        arr[7] = rs.getInt(1);
                    else if(i == 9)
                        arr[8] = rs.getInt(1);
                    else if(i == 10)
                        arr[9] = rs.getInt(1);
                }
                rs.close();
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
        return arr;
    }
    
    public ArrayList getGraph5() 
    {
        ArrayList list = new ArrayList();    
        try 
        {
            conn = getConnection();
            String open_query = "select count(1) from t_candidate where i_clientid <= 0";
            pstmt = conn.prepareStatement(open_query);
            logger.info("getGraph5 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                list.add(new DashboardInfo(rs.getInt(1), "Available", "Available", "6D9F29"));
            }
            rs.close();

            String client_query = "select i_clientid, s_name, s_shortname, s_colorcode from t_client where i_status = 1 order by s_name";
            pstmt = conn.prepareStatement(client_query);
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                int clientId = rs.getInt(1);
                String name = rs.getString(2) != null ? rs.getString(2) : "";
                String shortname = rs.getString(3) != null ? rs.getString(3) : "";
                String colorcode = rs.getString(4) != null ? rs.getString(4) : "";
                
                String query = "select count(1) from t_candidate where i_status = 1 and i_clientid = ?";
                PreparedStatement pstmt_inner = conn.prepareStatement(query);
                pstmt_inner.setInt(1, clientId);
                logger.info("pstmt_inner :: " + pstmt_inner.toString());
                ResultSet rs_inner = pstmt_inner.executeQuery();
                while (rs_inner.next()) 
                {
                    list.add(new DashboardInfo(rs_inner.getInt(1), name, shortname, colorcode));
                }
                pstmt_inner.close();
                rs_inner.close();
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
    
    public int[] getTalentPoolcount() 
    {
        int arr[] = new int[3];
        int total = 0, available = 0, employed = 0;
        String query = "SELECT COUNT(1) FROM t_candidate WHERE i_status != 4 AND i_vflag IN (3,4) AND i_pass =2 ";
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            logger.info("getTalentPoolcount Total :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                total = rs.getInt(1);
            }
            rs.close();
            
            query = "SELECT COUNT(1) FROM t_candidate WHERE i_status != 4 AND i_clientid >0 AND i_vflag IN (3,4) AND i_pass = 2";
            pstmt = conn.prepareStatement(query);
            logger.info("getTalentPoolcount Employed :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                employed = rs.getInt(1);
            }
            rs.close();
            
            query = "SELECT COUNT(1) FROM t_candidate WHERE i_clientid <= 0 AND i_progressid = 0 AND i_status != 4  ";
            pstmt = conn.prepareStatement(query);
            logger.info("getTalentPoolcount Available:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                available = rs.getInt(1);
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
        arr[0] = total;
        arr[1] = employed;
        arr[2] = available;
        return arr;
    }
    
    public DashboardInfo getearlyBydate(String date, int crewrotationId)
    {
        DashboardInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select t_signonoff.i_subtype, t_signonoff.s_remarks, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) ");
        sb.append(" from t_signonoff left join t_crewrotation on (t_crewrotation.i_crewrotationid = t_signonoff.i_crewrotationid) ");
        sb.append(" left join  t_candidate as c on (c.i_candidateid = t_crewrotation.i_candidateid) ");
        sb.append(" where  t_signonoff.i_crewrotationid = ? AND i_type = 1 and ((DATE_FORMAT(t_signonoff.ts_date1 , '%Y-%m-%d') <= ? and DATE_FORMAT(t_signonoff.ts_date2 , '%Y-%m-%d' ) >= ?)  OR (DATE_FORMAT(t_signonoff.ts_date2 , '%Y-%m-%d' ) <= ? and DATE_FORMAT(t_signonoff.ts_date1 , '%Y-%m-%d') >= ?)) ");
        String query = (sb.toString()).intern();
        sb.setLength(0);

        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setString(++scc, date);
            pstmt.setString(++scc, date);
            pstmt.setString(++scc, date);
            pstmt.setString(++scc, date);

            logger.info("getearlyBydate :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int  subtypeid;
            String subtype, remarks, name;
            while (rs.next()) 
            {
                subtypeid = rs.getInt(1);
                remarks = rs.getString(2) != null ? rs.getString(2): "";
                name = rs.getString(3) != null ? rs.getString(3): "";
                subtype = getearlysubtypeString(subtypeid);
                info = new DashboardInfo(subtype, remarks, name);
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    public String getearlysubtypeString(int id)
    {
        String s1 = "";
        switch (id) {
            case 1:
                s1 = "Normal";
                break;
            case 2:
                s1 = "Early";
                break;
            case 3:
                s1 = "Delayed";
                break;
            default:
                break;
        }
        return s1;
    }
    
    public DashboardInfo getTrainingBydate(String date, int crewrotationId) {

        DashboardInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s.i_activityid, s.s_remarks, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), CONCAT(t_position.s_name,' | ',t_grade.s_name)");
        sb.append("FROM t_cractivity as s left join t_crewrotation as cr on (cr.i_crewrotationid = s.i_crewrotationid) ");
        sb.append(" left join  t_candidate as c on (c.i_candidateid = cr.i_candidateid) ");
        sb.append("Left join t_position on t_position.i_positionid = s.i_positionid  ");
        sb.append("Left join t_grade on t_grade.i_gradeid = t_position.i_gradeid ");
        sb.append(" where  s.i_crewrotationid = ?  and s.i_activityid = 2 and s.i_status = 2 and ((DATE_FORMAT(s.ts_fromdate , '%Y-%m-%d') <= ? and DATE_FORMAT(s.ts_todate , '%Y-%m-%d' ) >= ?)  OR (DATE_FORMAT(s.ts_todate , '%Y-%m-%d' ) <= ? and DATE_FORMAT(s.ts_fromdate , '%Y-%m-%d') >= ?)) ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setString(++scc, date);
            pstmt.setString(++scc, date);
            pstmt.setString(++scc, date);
            pstmt.setString(++scc, date);

            logger.info("getTrainingBydate :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int  activityId;
            String subtype, remarks, name,position;
            while (rs.next()) 
            {
                activityId = rs.getInt(1);
                remarks = rs.getString(2) != null ? rs.getString(2): "";
                name = rs.getString(3) != null ? rs.getString(3): "";
                position = rs.getString(4) != null ? rs.getString(4): "";
                subtype = getoffshoreActivityString(activityId);
                info = new DashboardInfo(subtype, remarks, name, position);
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public DashboardInfo getTemporaryPromotionBydate(String date, int crewrotationId) 
    {
        DashboardInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s.i_activityid, s.s_remarks, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), CONCAT(t_position.s_name,' | ',t_grade.s_name)");
        sb.append("FROM t_cractivity AS s ");
        sb.append("LEFT JOIN t_crewrotation AS cr ON (cr.i_crewrotationid = s.i_crewrotationid) ");
        sb.append(" LEFT JOIN  t_candidate AS c ON (c.i_candidateid = cr.i_candidateid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = s.i_positionid_temp)  ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append(" WHERE  s.i_crewrotationid = ?  AND s.i_activityid = 7 AND s.i_status = 2 AND ((DATE_FORMAT(s.ts_fromdate , '%Y-%m-%d') <= ? AND DATE_FORMAT(s.ts_todate , '%Y-%m-%d' ) >= ?)  OR (DATE_FORMAT(s.ts_todate , '%Y-%m-%d' ) <= ? AND DATE_FORMAT(s.ts_fromdate , '%Y-%m-%d') >= ?)) ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setString(++scc, date);
            pstmt.setString(++scc, date);
            pstmt.setString(++scc, date);
            pstmt.setString(++scc, date);

            logger.info("getTemporaryPromotionBydate :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int  activityId;
            String subtype, remarks, name,position;
            while (rs.next())
            {
                activityId = rs.getInt(1);
                remarks = rs.getString(2) != null ? rs.getString(2): "";
                name = rs.getString(3) != null ? rs.getString(3): "";
                position = rs.getString(4) != null ? rs.getString(4): "";
                subtype = getoffshoreActivityString(activityId);
                info = new DashboardInfo(subtype, remarks, name, position);
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    public DashboardInfo getStandBydate(String date, int crewrotationId) {

        DashboardInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s.i_activityid, s.s_remarks, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), CONCAT(t_position.s_name,' | ',t_grade.s_name)");
        sb.append("FROM t_cractivity as s left join t_crewrotation as cr on (cr.i_crewrotationid = s.i_crewrotationid) ");
        sb.append(" left join  t_candidate as c on (c.i_candidateid = cr.i_candidateid) ");
        sb.append("Left join t_position on t_position.i_positionid = s.i_positionid  ");
        sb.append("Left join t_grade on t_grade.i_gradeid = t_position.i_gradeid ");
        sb.append(" where  s.i_crewrotationid = ?  and s.i_activityid = 8 and s.i_status = 2 and ((DATE_FORMAT(s.ts_fromdate , '%Y-%m-%d') <= ? and DATE_FORMAT(s.ts_todate , '%Y-%m-%d' ) >= ?)  OR (DATE_FORMAT(s.ts_todate , '%Y-%m-%d' ) <= ? and DATE_FORMAT(s.ts_fromdate , '%Y-%m-%d') >= ?)) ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setString(++scc, date);
            pstmt.setString(++scc, date);
            pstmt.setString(++scc, date);
            pstmt.setString(++scc, date);

            logger.info("getStandBydate :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int  activityId;
            String subtype, remarks, name,position;
            while (rs.next())
            {
                activityId = rs.getInt(1);
                remarks = rs.getString(2) != null ? rs.getString(2): "";
                name = rs.getString(3) != null ? rs.getString(3): "";
                position = rs.getString(4) != null ? rs.getString(4): "";
                subtype = getoffshoreActivityString(activityId);
                info = new DashboardInfo(subtype, remarks, name, position);
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public String getoffshoreActivityString(int id)
    {
        String s1 = "";
        switch (id) {
            case 2:
                s1 = "Training";
                break;
            case 7:
                s1 = "Temporary Promotion";
                break;
            case 8:
                s1 = "Standby";
                break;
            default:
                break;
        }
        return s1;
    }
    
    public int[] getcount_cm(int assetId)
    {
        int arr[] = new int[12];
        int unchecked = 0, minchecked = 0, checked = 0, offeraccepted = 0, totalpositions = 0,
            total_candidate = 0, open_job = 0, sortlisted = 0, selected = 0, onboarded = 0, sortlisted_total = 0, selected_total = 0;
        String query = "select s.i_status, count(1) FROM t_shortlist as s left join t_jobpost on (s.i_jobpostid = t_jobpost.i_jobpostid) " +
            "where 0 = 0 AND (t_jobpost.i_clientassetid = ?)  group by s.i_status";
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assetId);
            logger.info("getcount :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                int status = rs.getInt(1);
                int count = rs.getInt(2);
                if(status == 1)
                    unchecked = count;
                else if(status == 2)
                    minchecked = count;
                else if(status == 3)
                    checked = count;
            }
            rs.close();
            query = "select SUM(i_noofopening) from t_jobpost where i_status IN (1,3) and i_clientassetid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assetId);
            logger.info("totalpositions :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                totalpositions = rs.getInt(1);
            }
            rs.close();
           
            query = "select count(1) from t_shortlist left join t_jobpost on (t_shortlist.i_jobpostid = t_jobpost.i_jobpostid) where t_jobpost.i_status IN (1,3) AND t_shortlist.i_oflag = 4 AND t_jobpost.i_clientassetid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assetId);
            logger.info("offeraccepted :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                offeraccepted = rs.getInt(1);
            }
            rs.close();
           
            query = "select count(1) from t_candidate where i_status = 1 AND i_clientassetid <= 0 and i_vflag IN (3,4) and i_pass = 2 ";
            pstmt = conn.prepareStatement(query);
            logger.info("total_candidate :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                total_candidate = rs.getInt(1);
            }
            rs.close();
           
            query = "select SUM(i_noofopening) from t_jobpost where i_status = 1 and i_clientassetid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assetId);
            logger.info("open_job :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                open_job = rs.getInt(1);
            }
            rs.close();
           
            query = "select count(1) from t_shortlist left join t_jobpost on (t_shortlist.i_jobpostid = t_jobpost.i_jobpostid) where t_jobpost.i_status = 1 AND t_shortlist.i_sflag = 4 AND t_jobpost.i_clientassetid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assetId);
            logger.info("selected :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                selected = rs.getInt(1);
            }
            rs.close();
            
            query = "select count(1) from t_shortlist left join t_jobpost on (t_shortlist.i_jobpostid = t_jobpost.i_jobpostid) where t_jobpost.i_status IN (1,3) AND t_shortlist.i_sflag = 4 AND t_jobpost.i_clientassetid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assetId);
            logger.info("selected_total :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                selected_total = rs.getInt(1);
            }
            rs.close();
           
            query = "select count(1) from t_shortlist left join t_jobpost on (t_shortlist.i_jobpostid = t_jobpost.i_jobpostid) where t_jobpost.i_status IN (1) AND t_shortlist.i_onboard = 9 AND t_jobpost.i_clientassetid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assetId);
            logger.info("onboarded :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                onboarded = rs.getInt(1);
            }
            rs.close();
           
            query = "select count(1) from t_shortlist left join t_jobpost on (t_shortlist.i_jobpostid = t_jobpost.i_jobpostid) where t_jobpost.i_status = 1 AND t_jobpost.i_clientassetid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assetId);
            logger.info("sortlisted :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                sortlisted = rs.getInt(1);
            }
            rs.close();
            
            query = "select count(1) from t_shortlist left join t_jobpost on (t_shortlist.i_jobpostid = t_jobpost.i_jobpostid) where t_jobpost.i_status IN (1,3) AND t_jobpost.i_clientassetid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assetId);
            logger.info("sortlisted_total :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                sortlisted_total = rs.getInt(1);
            }
            rs.close();
            open_job = open_job - onboarded;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        arr[0] = unchecked;
        arr[1] = minchecked;
        arr[2] = checked;        
        arr[3] = offeraccepted;
        arr[4] = totalpositions;
        arr[5] = total_candidate;
        arr[6] = open_job;
        arr[7] = sortlisted;        
        arr[8] = selected;
        arr[9] = onboarded;
        arr[10] = sortlisted_total;
        arr[11] = selected_total;
        return arr;
    }
    
    public int[] getGraph1() 
    {
        int total = 0, completed = 0; 
        try 
        {
            conn = getConnection();
            String query = "select count(1) from t_candidate where i_status = 1 AND i_clientassetid <= 0 and i_vflag NOT IN (3,4) and i_pass != 2";
            pstmt = conn.prepareStatement(query);
            logger.info("getGraph1 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                total = rs.getInt(1);
            }
            rs.close();
            
            StringBuilder sb = new StringBuilder();
            sb.append("select count(1) from t_candidate as c ");
            sb.append("left join (select i_candidateid, count(1) as ct from t_bankdetail where i_status = 1 group by i_candidateid) as t1 on (c.i_candidateid = t1.i_candidateid) ");
            sb.append("left join (select i_candidateid, count(1) as ct  from t_candlang where i_status = 1 group by i_candidateid) as t2 on (c.i_candidateid = t2.i_candidateid) ");
            sb.append("left join (select i_candidateid, count(1) as ct  from t_healthdeclaration where i_status = 1 group by i_candidateid) as t3 on (c.i_candidateid = t3.i_candidateid) ");
            sb.append("left join (select i_candidateid, count(1) as ct  from t_vbd where i_status = 1 group by i_candidateid) as t4 on (c.i_candidateid = t4.i_candidateid) ");
            sb.append("left join (select i_candidateid, count(1) as ct from t_govdoc where i_status = 1 group by i_candidateid) as t5 on (c.i_candidateid = t5.i_candidateid) ");
            sb.append("left join (select i_candidateid, count(1) as ct  from t_trainingandcert where i_status = 1 group by i_candidateid) as t6 on (c.i_candidateid = t6.i_candidateid) ");
            sb.append("left join (select i_candidateid, count(1) as ct  from t_eduqual where i_status = 1 group by i_candidateid) as t7 on (c.i_candidateid = t7.i_candidateid) ");
            sb.append("left join (select i_candidateid, count(1) as ct from t_workexperience where i_status = 1 group by i_candidateid) as t8 on (c.i_candidateid = t8.i_candidateid) ");
            sb.append("where c.i_status = 1 AND c.i_clientassetid <= 0 and c.i_vflag NOT IN (3,4) and c.i_pass != 2 ");
            sb.append("and t1.ct > 0 and t2.ct > 0 and t3.ct > 0 and t4.ct > 0 and t5.ct > 0 and t6.ct > 0 and t7.ct > 0 and t8.ct > 0");
            query = sb.toString();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            logger.info("getGraph1 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                completed = rs.getInt(1);
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
        int arr[] = new int[3];
        arr[0] = total;
        arr[1] = completed;
        arr[2] = total - completed;
        return arr;
    }
}
