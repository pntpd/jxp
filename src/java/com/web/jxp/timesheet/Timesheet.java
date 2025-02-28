package com.web.jxp.timesheet;
import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
import com.web.jxp.base.Template;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import static com.web.jxp.common.Common.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.TimeZone;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Timesheet extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getTimesheetByName( int next, int count, int allclient, String permission, String cids, String assetids, int clientIdIndex, int assetIdIndex, int type)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.i_clientassetid, t_client.s_name, t_clientasset.s_name, t_billingcycle.i_type, t1.ct ");
        sb.append("FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_billingcycle ON (t_clientasset.i_clientassetid = t_billingcycle.i_clientassetid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, count(1) AS ct FROM t_timesheet group by i_clientassetid) AS t1 ON (t_clientasset.i_clientassetid = t1.i_clientassetid) ");        
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
        if(clientIdIndex > 0)
            sb.append(" AND t_clientasset.i_clientid = ? ");
        if(assetIdIndex > 0)
            sb.append(" AND t_clientasset.i_clientassetid = ? ");
        if(type > 0)
            sb.append(" AND t_billingcycle.i_type = ? ");
        sb.append("ORDER BY t_client.s_name, t_clientasset.s_name");
        if(count > 0)
            sb.append(" LIMIT ").append(next*count).append(", ").append(count);
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("SELECT COUNT(1) ");
        sb.append("FROM t_clientasset LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_billingcycle ON (t_clientasset.i_clientassetid = t_billingcycle.i_clientassetid) ");
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
        if(clientIdIndex > 0)
            sb.append(" AND t_clientasset.i_clientid = ? ");
        if(assetIdIndex > 0)
            sb.append(" AND t_clientasset.i_clientassetid = ? ");
        if(type > 0)
            sb.append(" AND t_billingcycle.i_type = ? ");
        String countquery = (sb.toString()).intern();
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
            if (type > 0) {
                pstmt.setInt(++scc, type);
            }
            logger.info("getTimesheetByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAssetName,typevalue;
            int assetId, timesheet;
            while (rs.next())
            {
                assetId = rs.getInt(1);
                clientName = rs.getString(2) != null ? rs.getString(2) : "";
                clientAssetName = rs.getString(3) != null ? rs.getString(3) : "";
                typevalue = getrepeatvalue(rs.getInt(4));
                timesheet = rs.getInt(5);
                list.add(new TimesheetInfo(assetId, clientName, clientAssetName, typevalue, timesheet));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            if (type > 0) {
                pstmt.setInt(++scc, type);
            }
            print(this,"getTimesheetByNameCountQuery :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                assetId = rs.getInt(1);
                list.add(new TimesheetInfo(assetId,  "", "", "",0));
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
        TimesheetInfo info;
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
                    info = (TimesheetInfo) l.get (i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = null;
            if(colId.equals("4"))
                map = sortById(record, tp);
            else
                map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            TimesheetInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                for(int i = 0; i < total; i++)
                {
                    rInfo = (TimesheetInfo) l.get(i);
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
    public String getInfoValue(TimesheetInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getClientName()!= null ? info.getClientName() : "";    
        else if(i != null && i.equals("2"))
            infoval = info.getClientAssetName()!= null ? info.getClientAssetName() : "";
        else if(i != null && i.equals("3"))
            infoval = info.getTypevalue()!= null ? info.getTypevalue(): "";
        else if(i != null && i.equals("4"))
            infoval = ""+info.getTcount(); 
        return infoval;
    }
    
    public ArrayList getExcel(int allclient, String permission, String cids, String assetids, 
            int clientIdIndex, int assetIdIndex, int type)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.i_clientassetid, t_client.s_name, t_clientasset.s_name, t_billingcycle.i_type, t1.ct ");
        sb.append("FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_billingcycle ON (t_clientasset.i_clientassetid = t_billingcycle.i_clientassetid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, count(1) AS ct FROM t_timesheet group by i_clientassetid) AS t1 ON (t_clientasset.i_clientassetid = t1.i_clientassetid) ");        
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
        if(clientIdIndex > 0)
            sb.append(" AND t_clientasset.i_clientid = ? ");
        if(assetIdIndex > 0)
            sb.append(" AND t_clientasset.i_clientassetid = ? ");
        if(type > 0)
            sb.append(" AND t_billingcycle.i_type = ? ");
        sb.append("ORDER BY t_client.s_name, t_clientasset.s_name");
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
            if (type > 0) {
                pstmt.setInt(++scc, type);
            }
            logger.info("getExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAssetName,typevalue;
            int assetId, timesheet;
            while (rs.next())
            {
                assetId = rs.getInt(1);
                clientName = rs.getString(2) != null ? rs.getString(2) : "";
                clientAssetName = rs.getString(3) != null ? rs.getString(3) : "";
                typevalue = getrepeatvalue(rs.getInt(4));
                timesheet = rs.getInt(5);
                list.add(new TimesheetInfo(assetId, clientName, clientAssetName, typevalue, timesheet));
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
        coll.add(new TimesheetInfo(-1, " All "));
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
                coll.add(new TimesheetInfo(refId, refName));
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
            coll.add(new TimesheetInfo(-1, " All "));
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
                    coll.add(new TimesheetInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new TimesheetInfo(-1, " All "));
        }
        return coll;
    }
    // Listing
    public ArrayList getTimesheetList(int clientassetId, String search, int statusIndex,
            int month, int yearId, int next, int count, String fromDateIndex, String toDateIndex)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_timesheet.i_timesheetid, t_timesheet.i_status, DATE_FORMAT(t_timesheet.d_date,'%d-%b-%Y'), ");
        sb.append("DATE_FORMAT(t_timesheet.d_fromdate, '%d-%b-%Y') , DATE_FORMAT(t_timesheet.d_todate, '%d-%b-%Y'), ");
        sb.append("t_billingcycle.i_type, t_currency.s_name, t_timesheet.s_appfile, t_timesheet.i_repeatid ");
        sb.append("FROM t_timesheet " );
        sb.append("LEFT JOIN t_billingcycle ON (t_billingcycle.i_clientassetid = t_timesheet.i_clientassetid) ");
        sb.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = t_timesheet.i_currencyid) ");
        sb.append("WHERE t_timesheet.i_clientassetid = ? ");
        if(search != null && !search.equals(""))
           sb.append(" AND (t_timesheet.i_timesheetid LIKE ? ) ");        
        if(statusIndex > 0)
            sb.append(" AND t_timesheet.i_status = ? ");        
        if(month > 0)
            sb.append(" AND (MONTH(t_timesheet.d_fromdate) = ? OR MONTH(t_timesheet.d_todate) = ?) ");
        if(yearId > 0)
            sb.append(" AND (YEAR(t_timesheet.d_fromdate) = ? OR YEAR(t_timesheet.d_todate) = ?) ");
        
        if (fromDateIndex != null && !fromDateIndex.equals("") && !fromDateIndex.equals("DD-MMM-YYYY") && toDateIndex != null && !toDateIndex.equals("") && !toDateIndex.equals("DD-MMM-YYYY")) {
            sb.append(" AND ((d_fromdate >= ? AND d_fromdate <= ?) ");
            sb.append("OR (d_todate >= ? AND d_todate <= ?)) ");
        }
        
        sb.append("ORDER BY d_fromdate DESC");
        if(count > 0)
            sb.append(" LIMIT ").append(next*count).append(", ").append(count);
        String query = (sb.toString()).intern();
        sb.setLength(0);
       
        sb.append("SELECT COUNT(1) FROM t_timesheet ");
        sb.append("LEFT JOIN t_billingcycle ON (t_billingcycle.i_clientassetid = t_timesheet.i_clientassetid) ");
        sb.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = t_timesheet.i_currencyid) ");
        sb.append("WHERE t_timesheet.i_clientassetid = ? ");
       
        if(search != null && !search.equals(""))
           sb.append(" AND (t_timesheet.i_timesheetid LIKE ? ) ");        
        if(statusIndex > 0)
            sb.append(" AND t_timesheet.i_status = ? ");        
        if(month > 0)
            sb.append(" AND (MONTH(t_timesheet.d_fromdate) = ? OR MONTH(t_timesheet.d_todate) = ?) ");
        if(yearId > 0)
            sb.append(" AND (YEAR(t_timesheet.d_fromdate) = ? OR YEAR(t_timesheet.d_todate) = ?) ");
        if (fromDateIndex != null && !fromDateIndex.equals("") && !fromDateIndex.equals("DD-MMM-YYYY") && toDateIndex != null && !toDateIndex.equals("") && !toDateIndex.equals("DD-MMM-YYYY")) {
            sb.append(" AND ((d_fromdate >= ? AND d_fromdate <= ?) ");
            sb.append("OR (d_todate >= ? AND d_todate <= ?)) ");
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientassetId);
           
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
            }
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            if(month > 0)
            {
                pstmt.setInt(++scc, month);
                pstmt.setInt(++scc, month);
            }
            if(yearId > 0)
            {
                pstmt.setInt(++scc, yearId);
                pstmt.setInt(++scc, yearId);
            }            
            if (fromDateIndex != null && !fromDateIndex.equals("") && !fromDateIndex.equals("DD-MMM-YYYY") && toDateIndex != null && !toDateIndex.equals("") && !toDateIndex.equals("DD-MMM-YYYY")) {
                pstmt.setString(++scc, changeDate1(fromDateIndex));
                pstmt.setString(++scc, changeDate1(toDateIndex));
                pstmt.setString(++scc, changeDate1(fromDateIndex));
                pstmt.setString(++scc, changeDate1(toDateIndex));
            }
            logger.info("getTimesheetList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String generatedon, fromDate, toDate, statusValue, type, currency, approvalfile;
            int timesheetId, status, repeatId;
            while (rs.next())
            {
                timesheetId = rs.getInt(1);
                status = rs.getInt(2);                
                generatedon = rs.getString(3) != null ? rs.getString(3) : "";
                fromDate = rs.getString(4) != null ? rs.getString(4) : "";
                toDate = rs.getString(5) != null ? rs.getString(5) : "";
                type = getrepeatvalue(rs.getInt(6));
                currency = rs.getString(7) != null ? rs.getString(7) : "";
                approvalfile = rs.getString(8) != null ? rs.getString(8) : "";
                repeatId = rs.getInt(9);
                statusValue = getStatusvalue(status);
                list.add(new TimesheetInfo(timesheetId, statusValue, generatedon, fromDate, toDate, type,
                    currency, approvalfile, status, repeatId));               
            }
            rs.close();
           
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            pstmt.setInt(++scc, clientassetId);
           
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
            }
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            if(month > 0)
            {
                pstmt.setInt(++scc, month);
                pstmt.setInt(++scc, month);
            }
            if(yearId > 0)
            {
                pstmt.setInt(++scc, yearId);
                pstmt.setInt(++scc, yearId);
            }
            if (fromDateIndex != null && !fromDateIndex.equals("") && !fromDateIndex.equals("DD-MMM-YYYY") && toDateIndex != null && !toDateIndex.equals("") && !toDateIndex.equals("DD-MMM-YYYY")) {
                pstmt.setString(++scc, changeDate1(fromDateIndex));
                pstmt.setString(++scc, changeDate1(toDateIndex));
                pstmt.setString(++scc, changeDate1(fromDateIndex));
                pstmt.setString(++scc, changeDate1(toDateIndex));
            }
            print(this,"getTimesheetListCountQuery :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                timesheetId = rs.getInt(1);
                list.add(new TimesheetInfo(timesheetId, "", "", "","","","","",0, 0));
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
    
    public TimesheetInfo getBasicDetail(int clientassetId)
    {
        TimesheetInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_name, t_clientasset.s_name, t_currency.s_name,t_client.i_clientid, t_currency.i_currencyid, ");
        sb.append("t_clientasset.d_allowance ");
        sb.append("FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = t_clientasset.i_currencyid) ");
        sb.append("WHERE i_clientassetid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
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
                int currencyId = rs.getInt(5);
                double allowance = rs.getDouble(6);
                info = new TimesheetInfo(clientName, clientassetName, currencyName, clientId,currencyId, allowance);
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
    
    public TimesheetInfo getBasicTimesheet(int timesheetId)
    {
        TimesheetInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DATE_FORMAT(t_timesheet.d_fromdate,'%d-%b-%Y'), DATE_FORMAT(t_timesheet.d_todate,'%d-%b-%Y'), ");
        sb.append("t_timesheet.i_status, count(t_timesheetdetail.i_timesheetid), t_timesheet.d_timesheettotal ");
        sb.append("FROM t_timesheet ");
        sb.append("LEFT JOIN t_timesheetdetail ON (t_timesheet.i_timesheetid = t_timesheetdetail.i_timesheetid) ");
        sb.append("WHERE t_timesheet.i_timesheetid = ? GROUP BY t_timesheetdetail.i_timesheetid  ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, timesheetId);
            print(this, "getBasicTimesheet :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                String fromDate = rs.getString(1) != null ? rs.getString(1) : "";
                String toDate = rs.getString(2) != null ? rs.getString(2) : "";
                int status = rs.getInt(3);
                int tcount = rs.getInt(4);
                double grandtotal = rs.getDouble(5);
                info = new TimesheetInfo(timesheetId, fromDate, toDate, status, tcount, grandtotal);
            }
        }
        catch (Exception exception)
        {
            print(this, "getBasicTimesheet :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
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
    
    public String getStatusvalue(int id)
    {
        String s1 = "";
        switch (id) {
            case 1:
                s1 = "Pending";
                break;
             case 2:
                s1 = "Submitted";
                break;
            case 3:
                s1 = "Sent for approval";
                break;
            case 4:
                s1 = "Revision Required";
                break;
            case 5:
                s1 = "Approved";
                break;
            default:
                break;
        }
        return s1;
    }
    
    public Collection getYears() 
    {
        Collection coll = new LinkedList();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        int curryear = cal.get(Calendar.YEAR);
        coll.add(new TimesheetInfo(0, "All"));
        try 
        {
            for(int i = curryear; i >= 2023; i--)
            {
                coll.add(new TimesheetInfo(i, ""+i));
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return coll;
    }
    
    public int deleteTimesheet(int timesheetId)
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            String query = ("DELETE FROM t_timesheet WHERE i_timesheetid = ? ");
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, timesheetId);
            print(this, "deleteTimesheet :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
           
            query = ("DELETE FROM t_timesheetdetail WHERE i_timesheetid = ? ");
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, timesheetId);
            pstmt.executeUpdate();            
        }
        catch (Exception exception)
        {
            print(this,"deleteTimesheet :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int createTimesheet(int timesheetId, int userId, String filename, double[] amount, 
            double[] prate, double[] srate,  double[] gtotal, int[] totalcrew, double[] rate, int[] flag, int[] positionId, 
            int[] crewrotationId, int[] timesheetdetailId, int[] typeId, String[] fromDate, String[] toDate,
            int days[], int appPositionId[])
    {
        int cc = 0;
        PreparedStatement insert_pstmt = null;
        try
        {
            int size =crewrotationId.length;
            if(timesheetId > 0 && size > 0)
            {
                String currdate = currDate1();
                conn = getConnection();  
                
                StringBuilder sb = new StringBuilder();
                sb.append("UPDATE t_timesheetdetail SET i_flag = ?,  d_amount = ?, d_rate = ?, d_per = ?, ");
                sb.append("d_grandtotal = ?, i_userid = ?, ts_moddate = ?, i_noofdays = ?, i_totalcrew = ? WHERE i_timesheetdetailid = ? ");//10
                String updatequery = sb.toString();
                sb.setLength(0);
               
                sb.append("INSERT INTO t_timesheetdetail (i_timesheetid, i_flag, i_crewrotationid, i_positionid, i_type, ");
                sb.append("d_fromdate, d_todate, i_noofdays, d_rate, d_prate, d_per, d_amount, d_grandtotal, i_totalcrew, ");
                sb.append("i_status, i_userid, ts_regdate, ts_moddate, i_positionIdapp) VALUES (?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,   ?) ");//20
                String inseretquery = sb.toString();
                sb.setLength(0);
                insert_pstmt = conn.prepareStatement(inseretquery);
                pstmt = conn.prepareStatement(updatequery);
                double total = 0;
                for(int i = 0; i < size; i++)
                {
                    total += gtotal[i];
                    if(timesheetdetailId[i] > 0)
                    {
                        pstmt.setInt(1, flag[i]);
                        pstmt.setDouble(2, amount[i]);                            
                        pstmt.setDouble(3, rate[i]);  
                        pstmt.setDouble(4, srate[i]);  
                        pstmt.setDouble(5, gtotal[i]);
                        pstmt.setInt(6, userId);
                        pstmt.setString(7, currdate);
                        pstmt.setInt(8, days[i]);
                        pstmt.setInt(9, totalcrew[i]);
                        pstmt.setInt(10, timesheetdetailId[i]);
                        cc += pstmt.executeUpdate();
                        print(this,"UpdateTimesheet :: " + pstmt.toString());
                    }
                    else
                    {
                        insert_pstmt.setInt(1, timesheetId);
                        insert_pstmt.setInt(2, flag[i]);
                        insert_pstmt.setInt(3, crewrotationId[i]);
                        insert_pstmt.setInt(4, positionId[i]);
                        insert_pstmt.setInt(5, typeId[i]);
                        insert_pstmt.setString(6, changeDate1(fromDate[i]));
                        insert_pstmt.setString(7, changeDate1(toDate[i]));
                        insert_pstmt.setInt(8, days[i]);
                        insert_pstmt.setDouble(9, rate[i]);
                        insert_pstmt.setDouble(10, prate[i]);
                        insert_pstmt.setDouble(11, srate[i]);
                        insert_pstmt.setDouble(12, amount[i]);
                        insert_pstmt.setDouble(13, gtotal[i]);                            
                        insert_pstmt.setInt(14, totalcrew[i]);
                        insert_pstmt.setInt(15, 1);
                        insert_pstmt.setInt(16, userId);
                        insert_pstmt.setString(17, currdate);
                        insert_pstmt.setString(18, currdate);
                        insert_pstmt.setInt(19, appPositionId[i]);
                        cc += insert_pstmt.executeUpdate();
                        print(this,"createTimesheet :: " + insert_pstmt.toString());
                    }
                }
                String updateq = "UPDATE t_timesheet SET i_status = 2, d_timesheettotal = ?, i_userid = ?, ts_moddate = ?, s_excelfile = ? WHERE i_timesheetid = ? ";
                pstmt = conn.prepareStatement(updateq);
                pstmt.setDouble(1, total);
                pstmt.setInt(2, userId);
                pstmt.setString(3, currdate);
                pstmt.setString(4, filename);
                pstmt.setInt(5, timesheetId);
                pstmt.executeUpdate();
                print(this, "updateq :::::  " + pstmt.toString());
            }            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int createSheet(int timesheetId, int assetId, int currencyId,String fromDate, String toDate, int userId)
    {
        timesheetId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_timesheet ");
            sb.append("(i_clientassetid, i_currencyid, d_date, d_fromdate, d_todate, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?,?,?,?,?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();            
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, assetId);
            pstmt.setInt(++scc, currencyId);
            pstmt.setString(++scc, currDate());
            pstmt.setString(++scc, changeDate1(fromDate));
            pstmt.setString(++scc, changeDate1(toDate));
            pstmt.setInt(++scc, 1);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this,"createSheet :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                timesheetId = rs.getInt(1);
            }
        }
        catch (Exception exception)
        {
            print(this,"createSheet :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return timesheetId;
    }
      
    public TimesheetInfo getRevisionById(int timesheetId)
    {
        TimesheetInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  t_revision.s_name, t_timesheet.s_remarks ");
        sb.append("FROM t_revision ");
        sb.append("LEFT JOIN t_timesheet ON (t_timesheet.i_revisionid =  t_revision.i_revisionid) ");
        sb.append("WHERE t_timesheet.i_timesheetid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, timesheetId);
            print(this,"getRevisionById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String remarks, revision;
            while (rs.next())
            {
                revision = rs.getString(1) != null  ? rs.getString(1): "";
                remarks = rs.getString(2) != null ? rs.getString(2): "";
                info = new TimesheetInfo(revision,remarks); 
            }
        }
        catch (Exception exception)
        {
            print(this,"getRevisionById :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public ArrayList getRevisionReaons() {
        ArrayList list = new ArrayList();
        list.add(new TimesheetInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("revision.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) {
            int len = arr.length();
            for (int i = 0; i < len; i++) {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) {
                    list.add(new TimesheetInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return list;
    }
    
    public int updateRevision(TimesheetInfo info)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_timesheet SET ");
            sb.append("i_revisionid = ?, ");
            sb.append("s_remarks = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_revision = (i_revision+1), ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_timesheetid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, info.getRevisionId());
            pstmt.setString(++scc, info.getRemarks());
            pstmt.setInt(++scc, 4);
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getTimesheetId());
            print(this,"updateRevision :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"updateRevision :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int approveInvoicing(TimesheetInfo info, int assetId)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_timesheet SET ");
            sb.append("s_appremarks = ?, ");
            sb.append("s_appfile = ?, ");
            sb.append("i_invoiceid = ?, ");
            sb.append("i_status = ?, ");
            sb.append("ts_approvaldate = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_timesheetid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            
            String maxquery = "SELECT MAX(i_invoiceid) FROM t_timesheet WHERE i_clientassetid = ?";
            pstmt = conn.prepareStatement(maxquery);
            pstmt.setInt(1, assetId);
            rs = pstmt.executeQuery();
            int maxinvoiceId = 0;
            while (rs.next())
            {
                maxinvoiceId = rs.getInt(1);
            }
            rs.close();
            maxinvoiceId = maxinvoiceId + 1;
            
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getRemarks());
            pstmt.setString(++scc, info.getFilename());
            pstmt.setInt(++scc, maxinvoiceId);
            pstmt.setInt(++scc, 5);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getTimesheetId());
            print(this,"approveInvoicing :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"approveInvoicing :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public TimesheetInfo getClientEmailById(int timesheetId)
    {
        TimesheetInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_email, t_client.s_name, DATE_FORMAT(t_timesheet.d_fromdate, '%D %b %Y'), ");
        sb.append("DATE_FORMAT(t_timesheet.d_todate, '%D %b %Y'), t_client.s_ocsuserids, t_timesheet.s_excelfile ");
        sb.append("FROM t_timesheet ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_timesheet.i_clientassetid) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("WHERE t_timesheet.i_timesheetid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, timesheetId);
            print(this,"getClientEmailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String email, clientname, fromDate, toDate, ocsuserids, excelfile, ccaddress = "";
            while (rs.next())
            {
                email = rs.getString(1) != null  ? rs.getString(1): "";
                clientname = rs.getString(2) != null ? rs.getString(2): "";
                fromDate = rs.getString(3) != null ? rs.getString(3): "";
                toDate = rs.getString(4) != null ? rs.getString(4): "";
                ocsuserids = rs.getString(5) != null ? rs.getString(5): "";
                excelfile = rs.getString(6) != null ? rs.getString(6): "";
                if(!ocsuserids.equals(""))
                {
                    String emailquery = "SELECT GROUP_CONCAT(s_email) from t_userlogin WHERE i_userid IN ("+ocsuserids+")";
                    PreparedStatement pstmt_inner = conn.prepareStatement(emailquery); 
                    ResultSet rs_inner = pstmt_inner.executeQuery();
                    while (rs_inner.next())
                    {
                        ccaddress = rs_inner.getString(1) != null  ? rs_inner.getString(1): "";
                    }
                    rs_inner.close();
                    pstmt_inner.close();
                }
                info = new TimesheetInfo(email, clientname, fromDate, toDate, ccaddress,excelfile); 
            }
        }
        catch (Exception exception)
        {
            print(this,"getClientEmailById :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int sendForApproval(int timesheetId, String fileName, int userId, int repeatId)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_timesheet SET ");
            sb.append("s_sendappfile = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, "); 
            if(repeatId > 0)
            {
                sb.append("i_repeatid = ?, ");
            }
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_timesheetid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;            
            pstmt.setString(++scc, fileName);
            pstmt.setInt(++scc, 3);
            pstmt.setInt(++scc, userId);
            if(repeatId > 0)
            {
                pstmt.setInt(++scc, 2);
            }
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, timesheetId);
            print(this,"sendForApproval :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"sendForApproval :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int createAprovalMailLog(int type, String email, String cc, String bcc, 
            String subject, String filename, String attachment, String attachment2, int timesheetId) 
    {
        int id = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_maillog ");
            sb.append("(i_type, s_to, s_cc, s_bcc, s_subject, s_filename, ts_regdate, ts_moddate, i_timesheetid, s_attachmentpath, s_attachmentpath1) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)");
            String query = sb.toString().intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, type);
            pstmt.setString(++scc, email);
            pstmt.setString(++scc, cc);
            pstmt.setString(++scc, bcc);
            pstmt.setString(++scc, subject);
            pstmt.setString(++scc, filename);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, timesheetId);
            pstmt.setString(++scc, attachment);
            pstmt.setString(++scc, attachment2);
            print(this, "createAprovalMailLog :: " + query);
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) 
            {
                id = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createAprovalMailLog :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return id;
    }
    
    public String getTimesheetMessage(String message)
    {
        String adminpage = getMainPath("template_path") + "timesheet.html";
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("MESSAGE", message);
        return template.patch(hashmap);
    }
    
    public int getTimesheetCount(int clientassetId)
    {
        int count = 0;
        String query = ("SELECT COUNT(1) FROM t_timesheet WHERE t_timesheet.i_clientassetid = ? ");
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            logger.info("getTimesheetCount :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                count = rs.getInt(1);
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
        return count;
    }
    
    public int checkin2dates(String sdate, String edate, String date, String sformatv) 
    {
        int b = 0;
        try 
        {
            if (sdate != null && !sdate.equals("") && !sdate.equals("00-00-0000") 
                    && edate != null && !edate.equals("") && !edate.equals("00-00-0000")) 
            {
                SimpleDateFormat sdf = new SimpleDateFormat(sformatv);
                long l1 = sdf.parse(sdate).getTime();
                long l2 = sdf.parse(edate).getTime();
                long l3 = sdf.parse(date).getTime();
                if(l3 > l1 && l3 < l2)
                {
                    b = 1;
                }
                else if(l2 < l3){
                    b = 2;
                }
            }
        } 
        catch (Exception e) 
        {
        }
        return b;
    } 
    
    public TimesheetInfo checinlist(ArrayList alist, int candidateId, int positionId, String fromdate, String todate)
    {
        TimesheetInfo minfo = null;
        int size = alist.size();
        for(int i = 0; i < size; i++)
        {
            TimesheetInfo info = (TimesheetInfo) alist.get(i);
            if(info != null && info.getCandidateId() == candidateId && info.getPositionId() == positionId)
            {
                int b = checkin2dates(fromdate, todate, info.getDate(), "dd-MMM-yyyy");
                if(b > 0)
                {
                    minfo = new TimesheetInfo(b, info.getPosition(), info.getDate());                    
                } 
            }
        }
        return minfo;
    }
    
    public String checinlistforbreak(ArrayList ratelist, int candidateId, int positionId, String fromdate, String todate)
    {
        String dt = "";
        int size = ratelist.size();
        for(int i = 0; i < size; i++)
        {
            TimesheetInfo info = (TimesheetInfo) ratelist.get(i);
            if(info != null && info.getCandidateId() == candidateId && info.getPositionId() == positionId 
                    && (info.getToDate() != null && !info.getToDate().equals("")))
            {
                int b = checkin2dates(fromdate, todate, info.getToDate(), "dd-MMM-yyyy");
                if(b == 1)
                {
                    if(dt.equals(""))
                        dt = info.getToDate();
                    else
                        dt += ","+info.getToDate();
                } 
            }
        }
        return dt;
    }
    
    public ArrayList getactivityDetails(String fromDate, String toDate, int clientassetId, ArrayList rateList)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_cractivity.i_crewrotationid, DATE_FORMAT(t_cractivity.ts_fromdate, '%d-%b-%Y'), DATE_FORMAT(t_cractivity.ts_todate, '%d-%b-%Y'), ");
        sb.append("t_cractivity.i_positionid, t_cractivity.i_activityid, (DATEDIFF(t_cractivity.ts_todate, t_cractivity.ts_fromdate )), t_position.s_name, t_grade.s_name, ");
        sb.append("t_dayrateposition.d_rate, t_crewrotation.i_candidateid, t_candidate.i_traveldays, ");
        sb.append("CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname), t_candidate.s_empno ");
        sb.append("FROM t_cractivity ");
        sb.append("LEFT JOIN t_crewrotation ON (t_crewrotation.i_crewrotationid = t_cractivity.i_crewrotationid) ");
        sb.append("LEFT JOIN t_candidate ON (t_crewrotation.i_candidateid = t_candidate.i_candidateid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_cractivity.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_dayrateposition ON (t_dayrateposition.i_positionid = t_position.i_positionid AND t_dayrateposition.i_clientassetid = ?) ");
        sb.append("WHERE t_cractivity.i_activityid = 6 AND t_crewrotation.i_clientassetid = ? AND t_crewrotation.i_active = 1  ");  
        sb.append("AND ((t_cractivity.i_activityid = 6 && t_cractivity.i_signoffid > 0) ) ");
        sb.append("AND (t_cractivity.i_activityid = 6 && (t_cractivity.ts_todate >= ? && t_cractivity.ts_todate <= ?)) ");
        sb.append("ORDER BY t_cractivity.i_crewrotationid, t_cractivity.i_activityid, t_cractivity.ts_fromdate, t_cractivity.ts_todate ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            
            ArrayList alist = getAppriasalList2(conn, clientassetId, fromDate, toDate);
            
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientassetId);
            pstmt.setInt(++scc, clientassetId);
            pstmt.setString(++scc, changeDate1(fromDate));
            pstmt.setString(++scc, changeDate1(toDate));         
            logger.info("getactivityDetails :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int crewrotationId, positionId, activityId, dateDiff, candidateId, travelDays;
            String date, date2, statusValue, name, employeeNo;
            double rate;
            while (rs.next())
            {
                crewrotationId = rs.getInt(1);
                date = rs.getString(2) != null ? rs.getString(2): "";
                date2 = rs.getString(3) != null ? rs.getString(3): "";                
                positionId = rs.getInt(4);
                activityId = rs.getInt(5);
                dateDiff = rs.getInt(6);
                String position = rs.getString(7) != null ? rs.getString(7): "";
                String grade = rs.getString(8) != null ? rs.getString(8): "";
                candidateId = rs.getInt(10);
                travelDays = rs.getInt(11);
                name = rs.getString(12) != null ? rs.getString(12): "";
                employeeNo = rs.getString(13) != null ? rs.getString(13): "";
                if(activityId == 7)
                    rate = rs.getDouble(9);
                else
                {
                    rate = 0;
                }
                if(!position.equals(""))
                {
                    if(!grade.equals(""))
                    {
                        position += " | " + grade;
                    }
                }
                if(activityId == 6)
                {
                    activityId = 1;
                }
                statusValue = getActivityValue(activityId);
                TimesheetInfo chinfo = checinlist(alist, candidateId, positionId, date, date2);
                if(chinfo != null && chinfo.getDdlValue() > 0)
                {
                    String cdate = chinfo.getDate() != null && !chinfo.getDate().equals("00-00-0000") ? chinfo.getDate() : "";
                    if(chinfo.getDdlValue() == 1)
                    { 
                        String newdate = getDateAfter(cdate, 1, -1, "dd-MMM-yyyy", "dd-MMM-yyyy");
                        list.add(new TimesheetInfo(crewrotationId, date, newdate, positionId, activityId, getDiffTwoDateInt2(date, newdate, "dd-MMM-yyyy"), position, 
                            statusValue, rate, candidateId, travelDays, name, employeeNo, 1));
                        
                        list.add(new TimesheetInfo(crewrotationId, cdate, date2, positionId, activityId, getDiffTwoDateInt2(cdate, date2, "dd-MMM-yyyy"), chinfo.getDdlLabel(), 
                            statusValue, rate, candidateId, travelDays, name, employeeNo, 1));
                    }
                    else if(chinfo.getDdlValue() == 2)
                    {
                        list.add(new TimesheetInfo(crewrotationId, date, date2, positionId, activityId, dateDiff, position, 
                            statusValue, rate, candidateId, travelDays, name, employeeNo, 1));
                    }
                }
                else
                {
                    String dt = checinlistforbreak(rateList, candidateId, positionId, date, date2);
                    if(dt != null && !dt.equals(""))
                    {
                        String dtarr[] = dt.split(",");
                        if(dtarr.length == 1)
                        {
                            list.add(new TimesheetInfo(crewrotationId, date, dt, positionId, activityId, getDiffTwoDateInt2(date, dt, "dd-MMM-yyyy")+1, position, 
                                statusValue, rate, candidateId, travelDays, name, employeeNo, 0));
                            
                            list.add(new TimesheetInfo(crewrotationId, getDateAfter(dt, 1, 1, "dd-MMM-yyyy", "dd-MMM-yyyy"), date2, positionId, activityId, getDiffTwoDateInt2(dt, date2, "dd-MMM-yyyy")-1, position, 
                                statusValue, rate, candidateId, travelDays, name, employeeNo, 0)); 
                        }
                        else
                        {
                            for(int i = 0; i < dtarr.length; i++)
                            {
                                list.add(new TimesheetInfo(crewrotationId, date, dtarr[i], positionId, activityId, getDiffTwoDateInt2(date, dtarr[i], "dd-MMM-yyyy"), position, 
                                statusValue, rate, candidateId, travelDays, name, employeeNo, 0));
                                date = getDateAfter(dtarr[i], 1, 1, "dd-MMM-yyyy", "dd-MMM-yyyy");
                            }
                            list.add(new TimesheetInfo(crewrotationId, date, date2, positionId, activityId, getDiffTwoDateInt2(date, date2, "dd-MMM-yyyy"), position, 
                                statusValue, rate, candidateId, travelDays, name, employeeNo, 0));
                        }
                    }
                    else
                    {
                        list.add(new TimesheetInfo(crewrotationId, date, date2, positionId, activityId, getDiffTwoDateInt2(date, date2, "dd-MMM-yyyy"), position, 
                            statusValue, rate, candidateId, travelDays, name, employeeNo, 0));
                    }
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
        return list;
    }
    
    public int getDiffTwoDateInt2(String sdate, String edate, String sformatv) {
        int diff = 0;
        try {
            if (sdate != null && !sdate.equals("") && !sdate.equals("00-00-0000") && edate != null && !edate.equals("") && !edate.equals("00-00-0000")) {
                SimpleDateFormat sdf = new SimpleDateFormat(sformatv);
                long l1 = sdf.parse(sdate).getTime();
                long l2 = sdf.parse(edate).getTime();
                long d = l2 - l1;
                long div = (long) 24 * 60 * 60 * 1000;
                long f1 = d / div;
                diff = (int) f1;
            }
        } catch (Exception e) {
            diff = 0;
        }
        return diff;
    }
    
    
    public String getActivityValue(int id)
    {
        String s1 = "";
        switch (id)
        {
            case 1:
                s1 = "Rotation";
                break;
             case 2:
                s1 = "Overtime";
                break;
             case 3:
                s1 = "Temporary Promotion";
                break;
            case 4:
                s1 = "Standby";
                break;           
            case 5:
                s1 = "Travel";
                break;           
            default:
                break;
        }
        return s1;
    }
    
    public ArrayList getCrewList(int clientassetId)
    {        
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_dayrate.i_candidateid, CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname), ");
        sb.append("t_position.s_name, t_grade.s_name, t_dayrate.i_positionid ,t_crewrotation.i_crewrotationid, t_candidate.i_traveldays, ");
        sb.append("t_dayrate.i_clientassetid ");
        sb.append("FROM t_dayrate ");
        sb.append("LEFT JOIN t_candidate ON (t_dayrate.i_candidateid = t_candidate.i_candidateid) ");
        sb.append("LEFT JOIN t_crewrotation ON (t_candidate.i_candidateid = t_crewrotation.i_candidateid AND t_crewrotation.i_active = 1) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_dayrate.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");        
        sb.append("WHERE t_dayrate.i_clientassetid = ? ");
        sb.append("ORDER BY t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientassetId);            
            logger.info("getCrewList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int candidateId, positionId, crewrotationId, travelDays, assetId;
            String  name, position, grade;
            while (rs.next())
            {
                candidateId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2): "";
                position = rs.getString(3) != null ? rs.getString(3): "";
                grade = rs.getString(4) != null ? rs.getString(4): "";
                positionId = rs.getInt(5);
                crewrotationId = rs.getInt(6);
                travelDays = rs.getInt(7);
                assetId = rs.getInt(8);
                if(!position.equals(""))
                {
                    if(!grade.equals(""))
                    {
                        position += " | " + grade;
                    }
                }
                list.add(new TimesheetInfo(candidateId, name, position, positionId, crewrotationId, travelDays, assetId));
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
    
    public ArrayList getListFromList1(ArrayList list, int candidateId, int positionId) 
    {
        int size = list.size();
        ArrayList l = new ArrayList();
        for (int i = 0; i < size; i++) 
        {            
            TimesheetInfo info = (TimesheetInfo) list.get(i);
            if (info != null && (info.getCandidateId() == candidateId) && (info.getPositionId() == positionId) && (info.getActivityId() == 1))
            {
                l.add(info);
            }
        }
        return l;
    }

    public ArrayList getListFromList2(ArrayList list, int candidateId, int positionId) 
    {
        int size = list.size();
        ArrayList l = new ArrayList();
        for (int i = 0; i < size; i++) 
        {
            TimesheetInfo info = (TimesheetInfo) list.get(i);
            if (info != null && (info.getCandidateId() == candidateId) && (info.getPositionId() == positionId) 
                    && (info.getActivityId() == 3 || info.getActivityId() == 4)) 
            {
                l.add(info);
            }
        }
        return l;
    }
    
    public ArrayList getListFromList3(ArrayList list, int candidateId, int positionId) 
    {
        int size = list.size();
        ArrayList l = new ArrayList();
        for (int i = 0; i < size; i++) 
        {
            TimesheetInfo info = (TimesheetInfo) list.get(i);
            if (info != null && (info.getCandidateId() == candidateId) && (info.getPositionId() == positionId)) 
            {
                l.add(info);
            }
        }
        return l;
    }
    
    public TimesheetInfo getRateFromList(ArrayList list, int candidateId, int positionId, String toDate) 
    {
        TimesheetInfo minfo =  null;
        int size = list.size();
        for (int i = 0; i < size; i++) 
        {
            TimesheetInfo info = (TimesheetInfo) list.get(i);
            if (info != null && (info.getCandidateId() == candidateId) && (info.getPositionId() == positionId) 
                && (checkin2datesrate(info.getFromDate(), info.getToDate(), toDate, "dd-MMM-yyyy") == 1)) 
            {
                minfo = info;       
            }
        }
        return minfo;
    }

    public ArrayList getListFromListOld(ArrayList list, int crewrotationId, int positionId) 
    {
        int size = list.size();
        ArrayList l = new ArrayList();
        for (int i = 0; i < size; i++) {
            TimesheetInfo info = (TimesheetInfo) list.get(i);
            if (info != null && info.getCrewrotationId() == crewrotationId && (info.getPositionId() == positionId)) 
            {
                l.add(info);
            }
        }
        return l;
    }
    
    //For Excel list data
    public ArrayList getAllrecord(ArrayList list1, ArrayList list2, ArrayList list3, ArrayList list4, 
            int candidateId, int crewrotationId, int positionId) 
    {
        int size1 = list1.size(); // crewList
        int size2 = list2.size(); // activityList
        int size3 = list3.size(); // pendingList
        int size4 = list4.size(); // allowanceList
        
        ArrayList list = new ArrayList();
        if(size1 > 0)
        {
            for (int i = 0; i < size1; i++) 
            {            
                TimesheetInfo info1 = (TimesheetInfo) list1.get(i);
                if (info1 != null && (info1.getCandidateId() == candidateId) && (info1.getPositionId() == positionId) )
                {
                    list.add(info1);
                }
            }
            return list;
        }
        if(size2 > 0)
        {
            for (int i = 0; i < size2; i++) 
            {            
                TimesheetInfo info2 = (TimesheetInfo) list2.get(i);
                if (info2 != null && (info2.getCandidateId() == candidateId) && (info2.getPositionId() == positionId) && (info2.getActivityId() == 1) )
                {
                    list.add(info2);
                }
            }
            return list;
        }
        if(size3 > 0)
        {
            for (int i = 0; i < size3; i++) 
            {            
                TimesheetInfo info3 = (TimesheetInfo) list3.get(i);
                if (info3 != null && (info3.getCandidateId() == candidateId) && (info3.getPositionId() == positionId))
                {
                    list.add(info3);
                }
            }
            return list;
        }
        if(size4 > 0)
        {
            for (int i = 0; i < size4; i++) 
            {            
                TimesheetInfo info4 = (TimesheetInfo) list4.get(i);
                if (info4 != null && (info4.getCandidateId() == candidateId) && (info4.getPositionId() == positionId) )
                {
                    list.add(info4);                    
                }
            }
            return list;
        }
        return list;
    }
    
    public ArrayList getPendingList(int clientassetId, int timesheetIdpar)
    {
        ArrayList list = new ArrayList();
        try
        {
            conn = getConnection();          
            StringBuilder sb = new StringBuilder();
            String countq = "SELECT i_timesheetid FROM t_timesheet WHERE i_clientassetid = ? AND i_timesheetid != ? ORDER BY i_timesheetid DESC LIMIT 0,1";
            pstmt = conn.prepareStatement(countq);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, timesheetIdpar);
            logger.info("TimesheetList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int timesheetId = 0;
            while(rs.next())
            {
                timesheetId = rs.getInt(1);
            }
            rs.close();
            if(timesheetId > 0)
            {
                sb.append("SELECT  crmain.i_timesheetdetailid, cr.i_crewrotationid , ");
                sb.append("CONCAT_WS(' ', cm.s_firstname, cm.s_middlename, cm.s_lastname), ");
                sb.append("t_position.s_name, t_grade.s_name, crmain.i_type, crmain.i_noofdays, DATE_FORMAT(crmain.d_fromdate, '%d-%b-%Y'), ");
                sb.append("DATE_FORMAT(crmain.d_todate, '%d-%b-%Y'), crmain.d_rate, crmain.d_per, crmain.d_amount, crmain.d_grandtotal, ");
                sb.append("crmain.i_positionid, crmain.i_totalcrew ");
                sb.append("FROM t_timesheetdetail AS crmain ");
                sb.append("LEFT JOIN t_timesheet ON (t_timesheet.i_timesheetid = crmain.i_timesheetid) ");
                sb.append("LEFT JOIN t_crewrotation AS cr ON (cr.i_crewrotationid = crmain.i_crewrotationid) ");
                sb.append("LEFT JOIN t_candidate AS cm ON (cm.i_candidateid = cr.i_candidateid) ");
                sb.append("LEFT JOIN t_position ON (t_position.i_positionid = crmain.i_positionid) ");
                sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
                sb.append("WHERE crmain.i_timesheetid = ? AND crmain.i_flag = 0 AND t_timesheet.i_status = 3  ");
                sb.append("AND crmain.i_type != 5 ");
                sb.append("ORDER BY crmain.i_timesheetdetailid ,t_position.s_name, t_grade.s_name, ");
                sb.append("CONCAT_WS(' ', cm.s_firstname, cm.s_middlename, cm.s_lastname), crmain.d_fromdate, crmain.d_todate, crmain.i_type ");
                String query = sb.toString();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, timesheetId);
                logger.info("getPendingList :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String name, position, grade, activityValue;
                int timesheetdetailId, crewrotationId, type, noofdays, positionId, totalCrew;
                double dayrate,  amount, grandtotal, percent;
                while(rs.next())
                {
                    timesheetdetailId = rs.getInt(1);
                    crewrotationId = rs.getInt(2);
                    name = rs.getString(3) != null ? rs.getString(3): "";
                    position = rs.getString(4) != null ? rs.getString(4): "";
                    grade = rs.getString(5) != null ? rs.getString(5): "";
                    type = rs.getInt(6);
                    noofdays = rs.getInt(7);
                    String fromDate = rs.getString(8) != null ? rs.getString(8): "";
                    String toDate = rs.getString(9) != null ? rs.getString(9): "";
                    dayrate = rs.getDouble(10);
                    percent = rs.getDouble(11);
                    amount = rs.getDouble(12);
                    grandtotal = rs.getDouble(13);       
                    positionId = rs.getInt(14);
                    totalCrew = rs.getInt(15);
                    activityValue = getActivityValue(type);
                    if (!position.equals(""))
                    {
                        if (!grade.equals(""))
                        {
                            position += " | " + grade;
                        }                
                    }
                    list.add(new TimesheetInfo(timesheetdetailId, crewrotationId, name, position,type , noofdays, 
                            fromDate, toDate, dayrate, percent, amount, grandtotal, positionId,totalCrew, activityValue));
                }
                rs.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally    
        {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getModifyList(int timesheetId)
    {
        ArrayList list = new ArrayList();
        try
        {                          
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_timesheetdetail.i_timesheetdetailid, t_timesheetdetail.i_crewrotationid,  ");
            sb.append("CONCAT_WS(' ', cm.s_firstname, cm.s_middlename, cm.s_lastname), t_position.s_name, t_grade.s_name,");
            sb.append("t_timesheetdetail.i_type, t_timesheetdetail.i_noofdays, ");
            sb.append("DATE_FORMAT(t_timesheetdetail.d_fromdate, '%d-%b-%Y'), DATE_FORMAT(t_timesheetdetail.d_todate, '%d-%b-%Y'), ");
            sb.append("t_timesheetdetail.d_rate, t_timesheetdetail.d_per, t_timesheetdetail.d_amount, ");
            sb.append("t_timesheetdetail.d_grandtotal, t_timesheetdetail.d_prate,  t_timesheetdetail.i_flag, ");            
            sb.append("t_timesheetdetail.i_positionid, t_timesheetdetail.i_totalcrew, t_timesheet.i_clientassetid, t_timesheet.i_repeatid ");
            sb.append("FROM t_timesheetdetail ");
            sb.append("LEFT JOIN t_crewrotation AS cr ON (cr.i_crewrotationid = t_timesheetdetail.i_crewrotationid) ");
            sb.append("LEFT JOIN t_candidate AS cm ON (cm.i_candidateid = cr.i_candidateid) ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_timesheetdetail.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("LEFT JOIN t_timesheet ON(t_timesheet.i_timesheetid = t_timesheetdetail.i_timesheetid) ");
            sb.append("WHERE t_timesheetdetail.i_timesheetid = ? ORDER BY t_timesheetdetail.i_timesheetdetailid");
            String query = sb.toString();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, timesheetId);
            logger.info("getModifyList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, position, grade, activityValue;
            int timesheetdetailId, crewrotationId, type, noofdays, 
            flag, positionId, totalcrew,assetId, repeatId;
            double dayrate,  amount, grandtotal, percent,prate;
            while(rs.next())
            {
                timesheetdetailId = rs.getInt(1);
                crewrotationId = rs.getInt(2);
                name = rs.getString(3) != null ? rs.getString(3): "";
                position = rs.getString(4) != null ? rs.getString(4): "";
                grade = rs.getString(5) != null ? rs.getString(5): "";
                type = rs.getInt(6);
                noofdays = rs.getInt(7);
                String fromDate = rs.getString(8) != null ? rs.getString(8): "";
                String toDate = rs.getString(9) != null ? rs.getString(9): "";
                dayrate = rs.getDouble(10);
                percent = rs.getDouble(11);
                amount = rs.getDouble(12);
                grandtotal = rs.getDouble(13);
                prate = rs.getDouble(14);
                flag = rs.getInt(15);
                positionId = rs.getInt(16);     
                totalcrew = rs.getInt(17);
                assetId = rs.getInt(18);
                repeatId = rs.getInt(19);
                activityValue = getActivityValue(type);
                if (!position.equals(""))
                {
                    if (!grade.equals(""))
                    {
                        position += " | " + grade;
                    }
                }
                list.add(new TimesheetInfo(timesheetdetailId, crewrotationId, name, position,type , noofdays, 
                        fromDate, toDate, dayrate, percent, amount, grandtotal,prate, flag, positionId, 
                        activityValue, totalcrew, assetId, repeatId));
            }
            rs.close();
        }        
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally    
        {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getExcelList(int timesheetId)
    {
        ArrayList list = new ArrayList();
        try
        {
            StringBuilder sb = new StringBuilder();            
            sb.append("SELECT t_timesheetdetail.i_timesheetdetailid, t_candidate.i_candidateid, ");  
            sb.append("t_position.s_name, t_grade.s_name, t_candidate.s_empno, t_timesheetdetail.i_type, t1.days, t2.days, t3.days, t4.days, t5.days, CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname) "); 
            sb.append("FROM t_timesheetdetail ");
            sb.append("LEFT JOIN t_crewrotation AS cr ON (cr.i_crewrotationid = t_timesheetdetail.i_crewrotationid) "); 
            sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = cr.i_candidateid ) ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_timesheetdetail.i_positionid) "); 
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) "); 
            sb.append("LEFT JOIN (SELECT i_crewrotationid, i_positionid, SUM(i_noofdays) AS days FROM t_timesheetdetail where i_timesheetid = ? and i_type = 1 group by i_crewrotationid, i_positionid) as t1 on (t_timesheetdetail.i_crewrotationid = t1.i_crewrotationid and t_timesheetdetail.i_positionid = t1.i_positionid) "); 
            sb.append("LEFT JOIN (SELECT i_crewrotationid, i_positionid, SUM(i_noofdays) AS days FROM t_timesheetdetail where i_timesheetid = ? and i_type = 2 group by i_crewrotationid, i_positionid) as t2 on (t_timesheetdetail.i_crewrotationid = t2.i_crewrotationid and t_timesheetdetail.i_positionid = t2.i_positionid) "); 
            sb.append("LEFT JOIN (SELECT i_crewrotationid, i_positionid, SUM(i_noofdays) AS days FROM t_timesheetdetail where i_timesheetid = ? and i_type = 3 group by i_crewrotationid, i_positionid) as t3 on (t_timesheetdetail.i_crewrotationid = t2.i_crewrotationid and t_timesheetdetail.i_positionid = t2.i_positionid) "); 
            sb.append("LEFT JOIN (SELECT i_crewrotationid, i_positionid, SUM(i_noofdays) AS days FROM t_timesheetdetail where i_timesheetid = ? and i_type = 4 group by i_crewrotationid, i_positionid) as t4 on (t_timesheetdetail.i_crewrotationid = t2.i_crewrotationid and t_timesheetdetail.i_positionid = t2.i_positionid) "); 
            sb.append("LEFT JOIN (SELECT i_crewrotationid, i_positionid, (i_noofdays) AS days FROM t_timesheetdetail where i_timesheetid = ? and i_type = 5 group by i_crewrotationid, i_positionid) as t5 on (t_timesheetdetail.i_crewrotationid = t5.i_crewrotationid and t_timesheetdetail.i_positionid = t5.i_positionid) "); 
            sb.append("WHERE t_timesheetdetail.i_timesheetid = ? GROUP BY t_timesheetdetail.i_crewrotationid, t_timesheetdetail.i_positionid ORDER BY t_timesheetdetail.i_timesheetdetailid ");
            String query = sb.toString();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, timesheetId);
            pstmt.setInt(2, timesheetId);
            pstmt.setInt(3, timesheetId);
            pstmt.setInt(4, timesheetId);
            pstmt.setInt(5, timesheetId);
            pstmt.setInt(6, timesheetId);
            logger.info("getExcelList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String position, grade, employeeNo, name;
            int timesheetdetailId, candidateId, days1, days2, days3, days4, days5, type;             
            while(rs.next())
            {
                timesheetdetailId = rs.getInt(1);
                candidateId = rs.getInt(2);
                position = rs.getString(3) != null ? rs.getString(3): "";
                grade = rs.getString(4) != null ? rs.getString(4): "";
                employeeNo = rs.getString(5) != null ? rs.getString(5): "";
                type = rs.getInt(6);
                days1 = rs.getInt(7);
                days2 = rs.getInt(8);
                days3 = rs.getInt(9);
                days4 = rs.getInt(10);
                days5 = rs.getInt(11);
                name = rs.getString(12) != null ? rs.getString(12): "";
                if (!position.equals(""))
                {
                    if (!grade.equals(""))
                    {
                        position += " | " + grade;
                    }
                }
                list.add(new TimesheetInfo(timesheetdetailId, candidateId, position, employeeNo, type, days1, days2, days3, days4, days5, name));
            }
            rs.close();
        }        
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally    
        {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList submittedList(int timesheetId)
    {
        ArrayList list = new ArrayList();
        try
        {                          
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_timesheetdetail.i_timesheetdetailid, t_timesheetdetail.i_crewrotationid,  ");
            sb.append("CONCAT_WS(' ', cm.s_firstname, cm.s_middlename, cm.s_lastname), t_position.s_name, t_grade.s_name,");
            sb.append("t_timesheetdetail.i_type, t_timesheetdetail.i_noofdays, ");
            sb.append("DATE_FORMAT(t_timesheetdetail.d_fromdate, '%d-%b-%Y'), DATE_FORMAT(t_timesheetdetail.d_todate, '%d-%b-%Y'), ");
            sb.append("t_timesheetdetail.d_rate, t_timesheetdetail.d_per, t_timesheetdetail.d_amount, ");
            sb.append("t_timesheetdetail.d_grandtotal, t_timesheetdetail.d_prate,  t_timesheetdetail.i_flag, ");            
            sb.append("t_timesheetdetail.i_positionid, t_timesheetdetail.i_totalcrew ");
            sb.append("FROM t_timesheetdetail ");
            sb.append("LEFT JOIN t_crewrotation AS cr ON (cr.i_crewrotationid = t_timesheetdetail.i_crewrotationid) ");
            sb.append("LEFT JOIN t_candidate AS cm ON (cm.i_candidateid = cr.i_candidateid) ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_timesheetdetail.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("WHERE t_timesheetdetail.i_timesheetid = ? AND t_timesheetdetail.i_flag = 1 ORDER BY t_timesheetdetail.i_timesheetdetailid");
            String query = sb.toString();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, timesheetId);
            logger.info("submittedList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, position, grade, activityValue;
            int timesheetdetailId, crewrotationId, type, noofdays, flag, positionId, totalcrew;
            double dayrate,  amount, grandtotal, percent,prate;
            while(rs.next())
            {
                timesheetdetailId = rs.getInt(1);
                crewrotationId = rs.getInt(2);
                name = rs.getString(3) != null ? rs.getString(3): "";
                position = rs.getString(4) != null ? rs.getString(4): "";
                grade = rs.getString(5) != null ? rs.getString(5): "";
                type = rs.getInt(6);
                noofdays = rs.getInt(7);
                String fromDate = rs.getString(8) != null ? rs.getString(8): "";
                String toDate = rs.getString(9) != null ? rs.getString(9): "";
                dayrate = rs.getDouble(10);
                percent = rs.getDouble(11);
                amount = rs.getDouble(12);
                grandtotal = rs.getDouble(13);
                prate = rs.getDouble(14);
                flag = rs.getInt(15);
                positionId = rs.getInt(16);     
                totalcrew = rs.getInt(17);
                activityValue = getActivityValue(type);
                if (!position.equals(""))
                {
                    if (!grade.equals(""))
                    {
                        position += " | " + grade;
                    }
                }
                list.add(new TimesheetInfo(timesheetdetailId, crewrotationId, name, position,type , noofdays, 
                        fromDate, toDate, dayrate, percent, amount, grandtotal,prate, flag, positionId, 
                        activityValue, totalcrew,0,0));
            }
            rs.close();
        }        
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally    
        {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public int getTPNoofdays(ArrayList list, int crewrotationId) 
    {
        int size = list.size();
        int days = 0;
        for (int i = 0; i < size; i++) 
        {
            TimesheetInfo info = (TimesheetInfo) list.get(i);
            if (info != null && info.getCrewrotationId() == crewrotationId && info.getActivityId() == 3)
            {
                days += info.getDateDiff();
            }
        }
        return days;
    }
    
    public int getSBYNoofdays(ArrayList list, int crewrotationId) 
    {
        int size = list.size();
        int days = 0;
        for (int i = 0; i < size; i++) 
        {
            TimesheetInfo info = (TimesheetInfo) list.get(i);
            if (info != null && info.getCrewrotationId() == crewrotationId && info.getActivityId() == 4)
            {
                days += info.getDateDiff();
            }
        }
        return days;
    }
    
    public int getSBYdays(ArrayList list, int crewrotationId) 
    {
        int size = list.size();
        int days = 0;
        for (int i = 0; i < size; i++) 
        {
            TimesheetInfo info = (TimesheetInfo) list.get(i);
            if (info != null && info.getCrewrotationId() == crewrotationId && info.getActivityId() == 4)
            {
                days += info.getDateDiff();
            }
        }
        return days;
    }
    
    public ArrayList getAllowanceList(int clientassetId)
    {        
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_candidate.i_candidateid, CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname), ");
        sb.append("t_position.s_name, t_grade.s_name, t_position.i_positionid, t_dayrateh.d_rate3, t_candidate.i_traveldays, t_clientasset.d_allowance ");
        sb.append("FROM t_candidate ");
        sb.append("LEFT JOIN t_clientasset ON(t_candidate.i_clientassetid = t_clientasset.i_clientassetid) ");
        sb.append("LEFT JOIN t_dayrateh ON (t_dayrateh.i_candidateid = t_candidate.i_candidateid AND t_candidate.i_positionid = t_dayrateh.i_positionid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_candidate.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) "); 
        sb.append("WHERE t_candidate.i_clientassetid = ? AND t_candidate.i_positionid > 0 AND t_candidate.i_traveldays > 0 ");
        sb.append("GROUP BY t_candidate.i_positionid ORDER BY t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientassetId);
            logger.info("getAllowanceList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int candidateId, positionId, traveldays;
            String  name, position, grade;
            double rate1, allowance;
            while (rs.next())
            {
                candidateId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2): "";
                position = rs.getString(3) != null ? rs.getString(3): "";
                grade = rs.getString(4) != null ? rs.getString(4): "";
                positionId = rs.getInt(5);
                rate1 = rs.getDouble(6);
                traveldays = rs.getInt(7);
                allowance = rs.getInt(8);
                if(allowance > 0)
                {
                    rate1 = (rate1*(allowance/100.00));
                }
                if(!position.equals(""))
                {
                    if(!grade.equals(""))
                    {
                        position += " | " + grade;
                    }
                }
                
                list.add(new TimesheetInfo(candidateId, name, position, positionId, rate1, traveldays, allowance));
            }
            rs.close();
            
            sb.append("SELECT t_candidate.i_candidateid, CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname), ");
            sb.append("t_position.s_name, t_grade.s_name, t_position.i_positionid, t_dayrateh.d_rate3, t_candidate.i_traveldays, t_clientasset.d_allowance ");
            sb.append("FROM t_candidate ");
            sb.append("LEFT JOIN t_clientasset ON(t_candidate.i_clientassetid = t_clientasset.i_clientassetid) ");
            sb.append("LEFT JOIN t_dayrateh ON (t_dayrateh.i_candidateid = t_candidate.i_candidateid AND t_candidate.i_positionid2 = t_dayrateh.i_positionid) ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_candidate.i_positionid2) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) "); 
            sb.append("WHERE t_candidate.i_clientassetid = ? AND t_candidate.i_positionid2 > 0 AND t_candidate.i_traveldays > 0 ");
            sb.append("GROUP BY t_candidate.i_positionid ORDER BY t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname ");
            query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            scc = 0;
            pstmt.setInt(++scc, clientassetId);  
            logger.info("getAllowanceList2 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                candidateId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2): "";
                position = rs.getString(3) != null ? rs.getString(3): "";
                grade = rs.getString(4) != null ? rs.getString(4): "";
                positionId = rs.getInt(5);
                rate1 = rs.getDouble(6);
                traveldays = rs.getInt(7);
                allowance = rs.getInt(8);
                if(allowance > 0)
                {
                    rate1 = rate1*(allowance/100.00);
                }
                if(!position.equals(""))
                {
                    if(!grade.equals(""))
                    {
                        position += " | " + grade;
                    }
                }
                list.add(new TimesheetInfo(candidateId, name, position, positionId, rate1, traveldays, allowance));
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
    
    public int checkPendingData(int clientassetId, String fromDate, String toDate)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_timesheetid FROM t_timesheet WHERE d_fromdate = ? AND d_todate = ? AND i_status = 3 AND i_clientassetid = ? AND i_repeatid = 0");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, changeDate1(fromDate));
            pstmt.setString(++scc, changeDate1(toDate));
            pstmt.setInt(++scc, clientassetId);
            print(this,"checkPendingData :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                ck = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "checkPendingData :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }
    
    public int checkTimesheetData(int clientassetId, String fromDate, String toDate)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_timesheetid FROM t_timesheet WHERE d_fromdate = ? AND d_todate = ? AND i_status = 3 AND i_clientassetid = ? AND i_rflag = 1");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, changeDate1(fromDate));
            pstmt.setString(++scc, changeDate1(toDate));
            pstmt.setInt(++scc, clientassetId);
            print(this,"checkTimesheetData :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                ck = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "checkTimesheetData :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }
    
    //For duplicate timesheet date
    public ArrayList getPandingData(int timesheetId)
    {
        ArrayList list = new ArrayList();
        try
        {                          
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT  crmain.i_timesheetdetailid, cr.i_crewrotationid , ");
            sb.append("CONCAT_WS(' ', cm.s_firstname, cm.s_middlename, cm.s_lastname), ");
            sb.append("t_position.s_name, t_grade.s_name, crmain.i_type, crmain.i_noofdays, DATE_FORMAT(crmain.d_fromdate, '%d-%b-%Y'), ");
            sb.append("DATE_FORMAT(crmain.d_todate, '%d-%b-%Y'), crmain.d_rate, crmain.d_per, crmain.d_amount, crmain.d_grandtotal, ");
            sb.append("crmain.i_positionid, crmain.i_totalcrew ");
            sb.append("FROM t_timesheetdetail AS crmain ");
            sb.append("LEFT JOIN t_timesheet ON (t_timesheet.i_timesheetid = crmain.i_timesheetid) ");
            sb.append("LEFT JOIN t_crewrotation AS cr ON (cr.i_crewrotationid = crmain.i_crewrotationid) ");
            sb.append("LEFT JOIN t_candidate AS cm ON (cm.i_candidateid = cr.i_candidateid) ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = crmain.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("WHERE crmain.i_timesheetid = ? AND crmain.i_flag = 0 AND t_timesheet.i_status = 3 ");
            sb.append("ORDER BY crmain.i_timesheetdetailid ,t_position.s_name, t_grade.s_name, ");
            sb.append("CONCAT_WS(' ', cm.s_firstname, cm.s_middlename, cm.s_lastname), crmain.d_fromdate, crmain.d_todate, crmain.i_type ");
            String query = sb.toString();
            sb.setLength(0);
            
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, timesheetId);
            logger.info("getPandingData :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, position, grade, activityValue;
            int timesheetdetailId, crewrotationId, type, noofdays, positionId, totalCrew;
            double dayrate,  amount, grandtotal, percent;
            while(rs.next())
            {
                timesheetdetailId = rs.getInt(1);
                crewrotationId = rs.getInt(2);
                name = rs.getString(3) != null ? rs.getString(3): "";
                position = rs.getString(4) != null ? rs.getString(4): "";
                grade = rs.getString(5) != null ? rs.getString(5): "";
                type = rs.getInt(6);
                noofdays = rs.getInt(7);
                String fromDate = rs.getString(8) != null ? rs.getString(8): "";
                String toDate = rs.getString(9) != null ? rs.getString(9): "";
                dayrate = rs.getDouble(10);
                percent = rs.getDouble(11);
                amount = rs.getDouble(12);
                grandtotal = rs.getDouble(13);       
                positionId = rs.getInt(14);
                totalCrew = rs.getInt(15);
                activityValue = getActivityValue(type);
                if (!position.equals(""))
                {
                    if (!grade.equals(""))
                    {
                        position += " | " + grade;
                    }                
                }
                list.add(new TimesheetInfo(timesheetdetailId, crewrotationId, name, position,type , noofdays, 
                        fromDate, toDate, dayrate, percent, amount, grandtotal, positionId,totalCrew, activityValue));
            }
            rs.close();
        }        
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally    
        {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public int updateRepeatId(int timesheetId, int repeatId, int uId)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_timesheet SET ");
            sb.append("i_repeatid = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_timesheetid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, repeatId);
            pstmt.setInt(++scc, uId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, timesheetId);
            print(this,"updateRflag :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"updateRflag :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    //Repeated Sheet
    public int updateFlag(int repeatId, int uId)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_timesheetdetail SET ");
            sb.append("i_flag = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_timesheetid = ? AND i_flag = 0 ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, 2);
            pstmt.setInt(++scc, uId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, repeatId);
            print(this,"updateFlag :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"updateFlag :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }    
    
    public ArrayList getAppriasalList2(Connection conn, int clientassetId, String fromDate, String toDate)
    {
        ArrayList list = new ArrayList();
        try
        {                          
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_appraisal.i_candidateid, t_appraisal.i_positionid2, DATE_FORMAT(t_appraisal.d_date,'%d-%b-%Y'), ");
            sb.append("t_position.s_name, t_grade.s_name ");
            sb.append("FROM t_appraisal ");
            sb.append("LEFT JOIN t_candidate ON(t_candidate.i_candidateid = t_appraisal.i_candidateid) ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_appraisal.i_positionid1) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) "); 
            sb.append("LEFT JOIN t_timesheet ON ( t_timesheet.i_clientassetid =  t_candidate.i_clientassetid) ");
            sb.append("WHERE t_candidate.i_clientassetid = ? AND (t_appraisal.d_date > ? AND t_appraisal.d_date < ?) ");
            String query = sb.toString();
            sb.setLength(0);
            
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            pstmt.setString(2, changeDate1(fromDate));
            pstmt.setString(3, changeDate1(toDate));
            logger.info("getAppriasalList2 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String position, grade, date ;
            int positionId, candidateId; 
            while(rs.next())
            {
                candidateId = rs.getInt(1);
                positionId = rs.getInt(2);
                date = rs.getString(3) != null ? rs.getString(3): "";
                position = rs.getString(4) != null ? rs.getString(4): "";
                grade = rs.getString(5) != null ? rs.getString(5): "";
                if (!position.equals(""))
                {
                    if (!grade.equals(""))
                    {
                        position += " | " + grade;
                    }
                }
                list.add(new TimesheetInfo(candidateId, positionId, date, position));
            }
            rs.close();
        }        
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally    
        {
            close(null, pstmt, rs);
        }
        return list;
    } 
    
    public int checkin2datesrate(String sdate, String edate, String date, String sformatv) 
    {
        int b = 0;
        try 
        {
            if (sdate != null && !sdate.equals("") && !sdate.equals("00-00-0000")) 
            {
                SimpleDateFormat sdf = new SimpleDateFormat(sformatv);
                long l1 = sdf.parse(sdate).getTime();
                long l2 = 0;
                if(edate != null && !edate.equals("") && !edate.equals("0000-00-00"))
                    l2 = sdf.parse(edate).getTime();
                long l3 = sdf.parse(date).getTime();
                if((l3 >= l1 && l3 <= l2) || (l3 >= l1 && l2 == 0))
                    b = 1;
            }
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return b;
    }
    
    //Rate list
    public ArrayList getCrewRateList(int clientassetId, String  fromdate, String todate)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_candidateid, i_positionid, d_rate1, d_rate2, d_rate3, DATE_FORMAT(d_fromdate, '%d-%b-%Y'), DATE_FORMAT(d_todate, '%d-%b-%Y') ");
        sb.append("FROM t_dayrateh ");
        sb.append("WHERE i_clientassetid = ? ");
        sb.append("AND ((? >= d_fromdate AND (? <= d_todate OR d_todate = '0000-00-00')) ");
        sb.append("OR (? >= d_fromdate AND (? <= d_todate OR d_todate= '0000-00-00' )) ");
        sb.append("OR (d_fromdate >= ? AND d_fromdate <= ?) ");
        sb.append("OR ((d_todate >= ? AND d_todate <= ?)) OR d_todate= '0000-00-00') ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientassetId);
            pstmt.setString(++scc, changeDate1(fromdate));
            pstmt.setString(++scc, changeDate1(fromdate));
            pstmt.setString(++scc, changeDate1(todate));
            pstmt.setString(++scc, changeDate1(todate));
            pstmt.setString(++scc, changeDate1(fromdate));
            pstmt.setString(++scc, changeDate1(todate));
            pstmt.setString(++scc, changeDate1(fromdate));
            pstmt.setString(++scc, changeDate1(todate));
            logger.info("Crew RateList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int candidateId, positionId;
            double rate1, rate2, rate3;
            String fromDate, toDate;
            while (rs.next())
            {
                candidateId = rs.getInt(1);
                positionId = rs.getInt(2);
                rate1 = rs.getDouble(3);
                rate2 = rs.getDouble(4);
                rate3 = rs.getDouble(5);
                fromDate = rs.getString(6) != null ? rs.getString(6): "";
                toDate = rs.getString(7);
                if(toDate != null && toDate.equals("0000-00-00"))
                    toDate = "";
                list.add(new TimesheetInfo(candidateId, positionId, rate1, rate2, rate3, fromDate, toDate));
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
    
    public ArrayList getStanbyPromotionDetails(String fromDate, String toDate, int clientassetId)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_cractivity.i_crewrotationid, DATE_FORMAT(t_cractivity.ts_fromdate, '%d-%b-%Y'), DATE_FORMAT(t_cractivity.ts_todate, '%d-%b-%Y'), ");
        sb.append("t_cractivity.i_positionid, t_cractivity.i_activityid, (DATEDIFF(t_cractivity.ts_todate, t_cractivity.ts_fromdate )), ");
        sb.append("t_position.s_name, t_grade.s_name, t_dayrateposition.d_rate, t_crewrotation.i_candidateid ");
        sb.append("FROM t_cractivity ");
        sb.append("LEFT JOIN t_crewrotation ON (t_crewrotation.i_crewrotationid = t_cractivity.i_crewrotationid) ");
        sb.append("LEFT JOIN t_candidate ON (t_crewrotation.i_candidateid = t_candidate.i_candidateid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_cractivity.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_dayrateposition ON (t_dayrateposition.i_positionid = t_position.i_positionid AND t_dayrateposition.i_clientassetid = ?) ");
        sb.append("WHERE t_cractivity.i_activityid IN(7,8) AND t_crewrotation.i_clientassetid = ? AND t_crewrotation.i_active = 1  ");  
        sb.append("AND (t_cractivity.i_activityid IN (7,8) && (t_cractivity.ts_fromdate >= ? AND t_cractivity.ts_fromdate <= ?)) ");
        sb.append("ORDER BY t_cractivity.i_crewrotationid, t_cractivity.i_activityid, t_cractivity.ts_fromdate, t_cractivity.ts_todate ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {         
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientassetId);
            pstmt.setInt(++scc, clientassetId);
            pstmt.setString(++scc, changeDate1(fromDate));
            pstmt.setString(++scc, changeDate1(toDate));        
            logger.info("getStanbyPromotionDetails :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int crewrotationId, positionId, activityId, dateDiff, candidateId;
            String date, date2, statusValue;
            double rate;
            while (rs.next())
            {
                crewrotationId = rs.getInt(1);
                date = rs.getString(2) != null ? rs.getString(2): "";
                date2 = rs.getString(3) != null ? rs.getString(3): "";                
                positionId = rs.getInt(4);
                activityId = rs.getInt(5);
                dateDiff = rs.getInt(6);
                String position = rs.getString(7) != null ? rs.getString(7): "";
                String grade = rs.getString(8) != null ? rs.getString(8): "";
                candidateId = rs.getInt(10);
                if(activityId == 7)
                    rate = rs.getDouble(9);
                else
                {
                    rate = 0;
                }
                if(!position.equals(""))
                {
                    if(!grade.equals(""))
                    {
                        position += " | " + grade;
                    }
                }
                
                if(activityId == 7)
                {
                    activityId = 3;
                }
                else if(activityId == 8)
                {
                    activityId = 4;
                    dateDiff = dateDiff+1;
                }
                statusValue = getActivityValue(activityId);
                
                list.add(new TimesheetInfo(crewrotationId, date, date2, positionId, activityId, dateDiff, position, 
                    statusValue, rate, candidateId));
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
}