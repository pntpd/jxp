package com.web.jxp.clientinvoicing;

import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
import com.web.jxp.base.Mail;
import com.web.jxp.base.StatsInfo;
import com.web.jxp.base.Template;
import com.web.jxp.base.Words;
import com.web.jxp.common.Common;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import static com.web.jxp.common.Common.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.zefer.pd4ml.PD4Constants;

public class Clientinvoicing extends Base {

    Mail mail = new Mail();
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String view_path = getMainPath("view_candidate_file");
    String resumepdffile_path = getMainPath("add_resumetemplate_pdf");

    public ArrayList getClientinvoicingByName(String search,  int next, int count, int allclient,
            String permission, String cids, String assetids, int clientIdIndex, int assetIdIndex, int typeIndex) 
    {
        ArrayList clientinvoicings = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.i_clientassetid, t_client.i_clientid, t_client.s_name,t_clientasset.s_name, t_billingcycle.i_type, t1.ct ");
        sb.append("FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_billingcycle ON (t_clientasset.i_clientassetid = t_billingcycle.i_clientassetid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, COUNT(1) AS ct FROM t_timesheet WHERE i_status = 5 GROUP BY i_clientassetid) AS t1 ON (t1.i_clientassetid = t_clientasset.i_clientassetid ) ");
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
        if (search != null && !search.equals("")) {
            sb.append("AND (t_clientasset.s_name LIKE ? OR t_client.s_name LIKE ?) ");
        }
        if (clientIdIndex > 0) {
            sb.append("AND t_client.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND t_clientasset.i_clientassetid = ? ");
        }
        if (typeIndex > 0) {
            sb.append("AND t_billingcycle.i_type = ? ");
        }
        sb.append(" ORDER BY  t_client.s_name, t_clientasset.s_name ");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1)  ");
        sb.append("FROM t_clientasset LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_billingcycle ON (t_clientasset.i_clientassetid = t_billingcycle.i_clientassetid) ");
        sb.append(" WHERE t_clientasset.i_status = 1 AND t_client.i_status = 1 ");
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
        if (search != null && !search.equals("")) {
            sb.append("AND (t_clientasset.s_name LIKE ? OR t_client.s_name LIKE ?) ");
        }

        if (clientIdIndex > 0) {
            sb.append("AND t_client.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND t_clientasset.i_clientassetid = ? ");
        }
        if (typeIndex > 0) {
            sb.append("AND t_billingcycle.i_type = ? ");
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
            if (typeIndex > 0) {
                pstmt.setInt(++scc, typeIndex);
            }
            logger.log(Level.INFO, "getClientinvoicingByName :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientassetName;
            int clientassetId, clientId, type, invoiceCount;
            while (rs.next()) 
            {
                clientassetId = rs.getInt(1);
                clientId = rs.getInt(2);
                clientName = rs.getString(3) != null ? rs.getString(3) : "";
                clientassetName = rs.getString(4) != null ? rs.getString(4) : "";
                type = rs.getInt(5);
                invoiceCount = rs.getInt(6);
                clientinvoicings.add(new ClientinvoicingInfo(clientassetId, clientId, clientName, clientassetName, type, invoiceCount));
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
            if (typeIndex > 0) {
                pstmt.setInt(++scc, typeIndex);
            }
            //print(this,"getClientinvoicingByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                clientassetId = rs.getInt(1);
                clientinvoicings.add(new ClientinvoicingInfo(clientassetId, 0, "", "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return clientinvoicings;
    }

    public String getschedulevalue(int id) {
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
        ClientinvoicingInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (ClientinvoicingInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            ClientinvoicingInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (ClientinvoicingInfo) l.get(i);
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

    public ArrayList getFinalRecordTS(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        ClientinvoicingInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (ClientinvoicingInfo) l.get(i);
                    record.put(getInfoValueTS(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            ClientinvoicingInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (ClientinvoicingInfo) l.get(i);
                    String str = getInfoValueTS(rInfo, colId);
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
    public String getInfoValue(ClientinvoicingInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getClientName() != null ? info.getClientName() : "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getClientassetName() != null ? info.getClientassetName() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getType() + "";
        } else if (i != null && i.equals("4")) {
            infoval = info.getInvoiceCount() + "";
        }
        return infoval;
    }

    public String getInfoValueTS(ClientinvoicingInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getInvoiceId() + "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getInvoicestatus() + "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getGeneratedate() != null ? info.getGeneratedate() : "";
        } else if (i != null && i.equals("4")) {
            infoval = info.getSentdate() != null ? info.getSentdate() : "";
        } else if (i != null && i.equals("5")) {
            infoval = info.getFromdate() != null ? info.getFromdate() : "";
        } else if (i != null && i.equals("6")) {
            infoval = info.getTodate() != null ? info.getTodate() : "";
        }
        return infoval;
    }

    public ClientinvoicingInfo getClientDetailById(int clientassetId) 
    {
        ClientinvoicingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.s_name, t_client.s_name, t_clientasset.i_countryid FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_clientasset.i_clientid = t_client.i_clientid) WHERE i_clientassetid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            print(this, "getClientDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientassetname, clientname;
            int counrtyId;
            while (rs.next()) 
            {
                clientassetname = rs.getString(1) != null ? rs.getString(1): "";
                clientname = rs.getString(2) != null ? rs.getString(2): "";
                counrtyId = rs.getInt(3);
                info = new ClientinvoicingInfo(clientassetId, clientassetname, clientname, counrtyId);
            }
        } catch (Exception exception) {
            print(this, "getClientinvoicingDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ClientinvoicingInfo getTimesheetDetailByIdforDetail(int timesheetId) 
    {
        ClientinvoicingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.s_name,t_client.s_name, i_invoiceid, t_client.i_clientid, t_clientasset.i_clientassetid, ");
        sb.append("t_timesheet.s_file1, t_timesheet.s_file2,t_timesheet.s_file3, DATE_FORMAT(t_timesheet.ts_paymentdate,  '%d-%b-%Y'), ");
        sb.append("t_timesheet.i_paymentmodeid, t_timesheet.s_premarks, t_timesheet.i_invoicestatus, ");
        sb.append("t_timesheet.d_timesheettotal,t_timesheet.d_amount1 ");
        sb.append("FROM t_timesheet LEFT JOIN t_clientasset on (t_clientasset.i_clientassetid = t_timesheet.i_clientassetid) ");
        sb.append("LEFT JOIN t_client on (t_clientasset.i_clientid = t_client.i_clientid) ");
        sb.append("WHERE i_timesheetid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, timesheetId);
            print(this,"getClientinvoicingDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientassetname, clientname, generatedfile, mailattachedfile, attachedfile, paymentdate, paymentremark, paymentmode;
            int invoiceId = 0, clientId, clientassetId, paymentmodeId, invoicestatus;
            double amount1;
            while (rs.next()) 
            {
                clientassetname = rs.getString(1) != null ? rs.getString(1) : "";
                clientname = rs.getString(2) != null ? rs.getString(2) : "";
                invoiceId = rs.getInt(3);
                clientId = rs.getInt(4);
                clientassetId = rs.getInt(5);
                generatedfile = rs.getString(6) != null ? rs.getString(6) : "";
                mailattachedfile = rs.getString(7) != null ? rs.getString(7) : "";
                attachedfile = rs.getString(8) != null ? rs.getString(8) : "";
                paymentdate = rs.getString(9) != null ? rs.getString(9) : "";
                paymentmodeId = rs.getInt(10);
                paymentremark = rs.getString(11) != null ? rs.getString(11) : "";
                invoicestatus = rs.getInt(12);
                amount1 = rs.getDouble(14) > 0 ? rs.getDouble(14) : rs.getDouble(13);
                info = new ClientinvoicingInfo(timesheetId, invoiceId, clientId, clientassetId, clientassetname, clientname, generatedfile,
                        mailattachedfile, attachedfile, paymentdate, paymentmodeId, paymentremark, invoicestatus, amount1);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getClientinvoicingDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ClientinvoicingInfo getTimesheetDetailByIdforDetailMail(int timesheetId) 
    {
        ClientinvoicingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.s_name,t_client.s_name, i_invoiceid, t_client.i_clientid, t_clientasset.i_clientassetid, ");
        sb.append("t_timesheet.s_file1, t_timesheet.s_file2,t_timesheet.s_file3,DATE_FORMAT( t_timesheet.ts_approvaldate , '%D %M %Y'), ");
        sb.append("i_invoicestatus, t_client.s_email, DATE_FORMAT(d_fromdate, '%D %M %Y'), DATE_FORMAT(d_todate, '%D %M %Y') ");
        sb.append("FROM t_timesheet ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_timesheet.i_clientassetid) ");
        sb.append("LEFT JOIN t_client ON (t_clientasset.i_clientid = t_client.i_clientid) ");
        sb.append("WHERE i_timesheetid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, timesheetId);
            //print(this,"getTimesheetDetailByIdforDetailMail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientassetname, clientname, generatedfile, mailattachedfile, attachedfile, approvaldate, clientemail, fromdate, todate;
            int invoiceId = 0, clientId, clientassetId, invoicestatus;
            while (rs.next()) 
            {
                clientassetname = rs.getString(1) != null ? rs.getString(1) : "";
                clientname = rs.getString(2) != null ? rs.getString(2) : "";
                invoiceId = rs.getInt(3);
                clientId = rs.getInt(4);
                clientassetId = rs.getInt(5);
                generatedfile = rs.getString(6) != null ? rs.getString(6) : "";
                mailattachedfile = rs.getString(7) != null ? rs.getString(7) : "";
                attachedfile = rs.getString(8) != null ? rs.getString(8) : "";
                approvaldate = rs.getString(9) != null ? rs.getString(9) : "";
                invoicestatus = rs.getInt(10);
                clientemail = rs.getString(11) != null ? rs.getString(11) : "";
                fromdate = rs.getString(12) != null ? rs.getString(12) : "";
                todate = rs.getString(13) != null ? rs.getString(13) : "";
                info = new ClientinvoicingInfo(timesheetId, invoiceId, clientId, clientassetId, clientassetname, clientname, generatedfile,
                        mailattachedfile, attachedfile, approvaldate, invoicestatus, clientemail, fromdate, todate);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getTimesheetDetailByIdforDetailMail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getListForExcel(String search, int allclient, String permission, String cids, String assetids, int clientIdIndex, int assetIdIndex, int typeIndex) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.i_clientassetid, t_client.i_clientid, t_client.s_name,t_clientasset.s_name, t_billingcycle.i_type, t1.ct ");
        sb.append("FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_billingcycle ON (t_clientasset.i_clientassetid = t_billingcycle.i_clientassetid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, COUNT(1) AS ct FROM t_timesheet WHERE i_status = 5 GROUP BY i_clientassetid) AS t1 ON (t1.i_clientassetid = t_clientasset.i_clientassetid ) ");
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
        if (search != null && !search.equals("")) {
            sb.append("AND (t_clientasset.s_name LIKE ? OR t_client.s_name LIKE ?) ");
        }
        if (clientIdIndex > 0) {
            sb.append("AND t_client.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND t_clientasset.i_clientassetid = ? ");
        }
        if (typeIndex > 0) {
            sb.append("AND t_billingcycle.i_type = ? ");
        }
        sb.append(" ORDER BY  t_client.s_name, t_clientasset.s_name ");
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
            if (typeIndex > 0) {
                pstmt.setInt(++scc, typeIndex);
            }
            logger.log(Level.INFO, "getListForExcel :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientassetName;
            int clientassetId, clientId, type, invoiceCount;
            while (rs.next()) 
            {
                clientassetId = rs.getInt(1);
                clientId = rs.getInt(2);
                clientName = rs.getString(3) != null ? rs.getString(3) : "";
                clientassetName = rs.getString(4) != null ? rs.getString(4) : "";
                type = rs.getInt(5);
                invoiceCount = rs.getInt(6);
                list.add(new ClientinvoicingInfo(clientassetId, clientId, clientName, clientassetName, type, invoiceCount));
            }
            rs.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
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
            coll.add(new ClientinvoicingInfo(-1, " All "));
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
                    coll.add(new ClientinvoicingInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new ClientinvoicingInfo(-1, " All "));
        }
        return coll;
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
        sb.append("ORDER BY s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new ClientinvoicingInfo(-1, " All "));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new ClientinvoicingInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getBank() 
    {
        Collection coll = new LinkedList();
        coll.add(new ClientinvoicingInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("bank.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null)
        {
            int len = arr.length();
            for (int i = 0; i < len; i++) {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) {
                    coll.add(new ClientinvoicingInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }

    public ArrayList getClientassetdetailByName(String search, int invoicestatusIndex, int next, int count, int month, int yearId, int clientassetId)
    {
        ArrayList clientinvoicings = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_timesheet.i_timesheetid, t_timesheet.i_invoiceid, t_timesheet.i_invoicestatus, DATE_FORMAT(t_timesheet.ts_generatedon, '%d-%b-%Y'), ");
        sb.append("DATE_FORMAT(t_timesheet.ts_senton, '%d-%b-%Y'), DATE_FORMAT(t_timesheet.d_fromdate, '%d-%b-%Y'), DATE_FORMAT(t_timesheet.d_todate, '%d-%b-%Y'), ");
        sb.append("t_timesheet.s_appfile FROM t_timesheet ");
        sb.append("WHERE t_timesheet.i_clientassetid =? AND t_timesheet.i_status =5 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_timesheet.i_invoiceid LIKE ?) ");
        }
        if (invoicestatusIndex > 0) {
            sb.append("AND t_timesheet.i_invoicestatus =? ");
        }
        if (month > 0) {
            sb.append("AND (MONTH(t_timesheet.d_fromdate) =? OR MONTH(t_timesheet.d_todate) =?) ");
        }
        if (yearId > 0) {
            sb.append("AND (YEAR(t_timesheet.d_fromdate) =? OR YEAR(t_timesheet.d_todate) =?) ");
        }
        sb.append("ORDER BY t_timesheet.ts_regdate DESC ");
        if (count > 0) {
            sb.append("LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1) ");
        sb.append("FROM t_timesheet ");
        sb.append("WHERE t_timesheet.i_clientassetid =? AND t_timesheet.i_status =5 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_timesheet.i_invoiceid LIKE ?) ");
        }
        if (invoicestatusIndex > 0) {
            sb.append("AND t_timesheet.i_invoicestatus =? ");
        }
        if (month > 0) {
            sb.append("AND (MONTH(t_timesheet.d_fromdate) =? OR MONTH(t_timesheet.d_todate) =?) ");
        }
        if (yearId > 0) {
            sb.append("AND (YEAR(t_timesheet.d_fromdate) =? OR YEAR(t_timesheet.d_todate) =?) ");
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientassetId);
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
            }
            if (invoicestatusIndex > 0) {
                pstmt.setInt(++scc, invoicestatusIndex);
            }
            if (month > 0) {
                pstmt.setInt(++scc, month);
                pstmt.setInt(++scc, month);
            }
            if (yearId > 0) {
                pstmt.setInt(++scc, yearId);
                pstmt.setInt(++scc, yearId);
            }
            print(this, "getClientassetdetailByName :: {0} " + pstmt.toString());
            rs = pstmt.executeQuery();
            String generatedate, sentdate, fromdate, todate, sendappfile;
            int timesheetId, invoiceId, invoicestatus;
            while (rs.next()) 
            {
                timesheetId = rs.getInt(1);
                invoiceId = rs.getInt(2);
                invoicestatus = rs.getInt(3);
                generatedate = rs.getString(4) != null ? rs.getString(4) : "";
                sentdate = rs.getString(5) != null ? rs.getString(5) : "";
                fromdate = rs.getString(6) != null ? rs.getString(6) : "";
                todate = rs.getString(7) != null ? rs.getString(7) : "";
                sendappfile = rs.getString(8) != null ? rs.getString(8) : "";
                clientinvoicings.add(new ClientinvoicingInfo(timesheetId, invoiceId, invoicestatus,
                        generatedate, sentdate, fromdate, todate, sendappfile));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            pstmt.setInt(++scc, clientassetId);
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
            }
            if (invoicestatusIndex > 0) {
                pstmt.setInt(++scc, invoicestatusIndex);
            }
            if (month > 0) {
                pstmt.setInt(++scc, month);
                pstmt.setInt(++scc, month);
            }
            if (yearId > 0) {
                pstmt.setInt(++scc, yearId);
                pstmt.setInt(++scc, yearId);
            }
            //print(this,"getClientinvoicingByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                timesheetId = rs.getInt(1);
                clientinvoicings.add(new ClientinvoicingInfo(timesheetId, 0, 0, "", "", "", "", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return clientinvoicings;
    }

    public String getInvoicestatusvalue(int id) 
    {
        String s1 = "";
        switch (id) 
        {
            case 1:
                s1 = "Pending";
                break;
            case 2:
                s1 = "Generate";
                break;
            case 3:
                s1 = "Sent";
                break;
            case 4:
                s1 = "Payment received";
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
        coll.add(new ClientinvoicingInfo(0, "All"));
        try 
        {
            for (int i = curryear; i >= 2023; i--) 
            {
                coll.add(new ClientinvoicingInfo(i, "" + i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coll;
    }

    public Collection getinvoicetemplatesforclientindex(int clientId) 
    {
        Collection coll = new LinkedList();
        if (clientId > 0) 
        {
            String query = ("SELECT i_invoicetemplateid, s_name FROM t_invoicetemplate WHERE i_clientid =? AND i_status =1 ORDER BY s_name").intern();
            coll.add(new ClientinvoicingInfo(-1, "Select Template"));
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                logger.info("getResumetemplatesforclientindex :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int refId;
                String refName;
                while (rs.next()) 
                {
                    refId = rs.getInt(1);
                    refName = rs.getString(2) != null && !rs.getString(2).equals("") ? rs.getString(2) : "";
                    coll.add(new ClientinvoicingInfo(refId, refName));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new ClientinvoicingInfo(-1, "Select Template"));
        }
        return coll;
    }

    public int updateGeneratedtemplatedata(int timesheetId, int invoicetempId, int bankId, String invoicenos,
            String invoicedates, double amount1, double amount2, double amount3, int uId, String taxname, double percentage,
             String taxname1, double percentage1,  String taxname2, double percentage2,  String taxname3, double percentage3,  
             String taxname4, double percentage4) 
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_timesheet SET  ");
            sb.append(" i_invoicetemplateid = ?, ");
            sb.append(" i_bankid = ?, ");
            sb.append(" s_invoiceno = ?, ");
            sb.append(" d_invoicedate = ?, ");
            sb.append(" d_amount1 = ?, ");
            sb.append(" d_amount2 = ?, ");
            sb.append(" d_amount3 = ?, ");
            sb.append(" s_taxname1 = ?, ");
            sb.append(" d_percentage1 = ?, ");
            sb.append(" s_taxname2 = ?, ");
            sb.append(" d_percentage2 = ?, ");
            sb.append(" s_taxname3 = ?, ");
            sb.append(" d_percentage3 = ?, ");
            sb.append(" s_taxname4 = ?, ");
            sb.append(" d_percentage4 = ?, ");
            sb.append(" s_taxname5 = ?, ");
            sb.append(" d_percentage5 = ?, ");
            sb.append(" i_userid = ?, ");
            sb.append(" ts_moddate = ? ");
            sb.append("WHERE i_timesheetid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, invoicetempId);
            pstmt.setInt(++scc, bankId);
            pstmt.setString(++scc, invoicenos);
            pstmt.setString(++scc, changeDate1(invoicedates));
            pstmt.setDouble(++scc, amount1);
            pstmt.setDouble(++scc, amount2);
            pstmt.setDouble(++scc, amount3);
            pstmt.setString(++scc, taxname);
            pstmt.setDouble(++scc, percentage);
            pstmt.setString(++scc, taxname1);
            pstmt.setDouble(++scc, percentage1);
            pstmt.setString(++scc, taxname2);
            pstmt.setDouble(++scc, percentage2);
            pstmt.setString(++scc, taxname3);
            pstmt.setDouble(++scc, percentage3);
            pstmt.setString(++scc, taxname4);
            pstmt.setDouble(++scc, percentage4);
            pstmt.setInt(++scc, uId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, timesheetId);
            print(this, "updateGeneratedtemplatedata :: " + pstmt.toString());
            cc = pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updateGeneratedtemplatedata :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public String generateInvoice(int timesheetId) {
        String pdffilename = "";
        String assetName = "", currency = "", bank = "", branch = "", accno = "", ifsc = "", invoiceno = "", invoicedate = "", 
                invoicetemplate = "", taxname1 = "" , taxname2 = "" , taxname3 = "" ,  taxname4 = "" ,   taxname5 = "" ;
        double amount1 = 0, amount2 = 0, amount3 = 0, percentage1 =0, percentage2 =0 , percentage3 = 0, percentage4= 0, percentage5 =0;

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.s_name, t_currency.s_name, t_bank.s_name, t_bank.s_branch, ");
        sb.append("t_bank.s_accno, t_bank.s_ifsc, t_timesheet.d_amount1, t_timesheet.d_amount2, ");
        sb.append("t_timesheet.d_amount3, t_timesheet.s_invoiceno, DATE_FORMAT(t_timesheet.d_invoicedate, '%d-%b-%Y'), t_invoicetemplate.s_filename, ");
        sb.append(" t_timesheet.s_taxname1, t_timesheet.d_percentage1, t_timesheet.s_taxname2, t_timesheet.d_percentage2, ");
        sb.append("t_timesheet.s_taxname3, t_timesheet.d_percentage3, t_timesheet.s_taxname4, t_timesheet.d_percentage4, ");
        sb.append("t_timesheet.s_taxname5, t_timesheet.d_percentage5 ");
        sb.append("FROM t_timesheet ");
        sb.append("LEFT JOIN t_clientasset ON (t_timesheet.i_clientassetid = t_clientasset.i_clientassetid) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = t_clientasset.i_currencyid) ");
        sb.append("LEFT JOIN t_bank ON (t_bank.i_bankid = t_timesheet.i_bankid) ");
        sb.append("LEFT JOIN t_invoicetemplate ON (t_invoicetemplate.i_invoicetemplateid = t_timesheet.i_invoicetemplateid) ");
        sb.append("WHERE t_timesheet.i_timesheetid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, timesheetId);
            print(this, "generateInvoice :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                assetName = rs.getString(1) != null ? rs.getString(1) : "";
                currency = rs.getString(2) != null ? rs.getString(2) : "";
                bank = rs.getString(3) != null ? rs.getString(3) : "";
                branch = rs.getString(4) != null ? rs.getString(4) : "";
                accno = rs.getString(5) != null ? rs.getString(5) : "";
                ifsc = rs.getString(6) != null ? rs.getString(6) : "";
                amount1 = rs.getDouble(7);
                amount2 = rs.getDouble(8);
                amount3 = rs.getDouble(9);
                invoiceno = rs.getString(10) != null ? rs.getString(10) : "";
                invoicedate = rs.getString(11) != null ? rs.getString(11) : "";
                invoicetemplate = rs.getString(12) != null ? rs.getString(12) : "";
                taxname1 = rs.getString(13) != null ? rs.getString(13) : "";
                percentage1 = rs.getDouble(14);
                taxname2 = rs.getString(15) != null ? rs.getString(15) : "";
                percentage2 = rs.getDouble(16);
                taxname3 = rs.getString(17) != null ? rs.getString(17) : "";
                percentage3 = rs.getDouble(18);
                taxname4 = rs.getString(19) != null ? rs.getString(19) : "";
                percentage4 = rs.getDouble(20);
                taxname5 = rs.getString(21) != null ? rs.getString(21) : "";
                percentage5 = rs.getDouble(22);
            }
            rs.close();

            double subtotal = amount1 + amount2 + amount3;

            String amt1 = IndianFormat(amount1, 2);
            if (amt1.equals("0")) {
                amt1 = "";
            }
            String amt2 = IndianFormat(amount2, 2);
            if (amt2.equals("0")) {
                amt2 = "";
            }
            String amt3 = IndianFormat(amount3, 2);
            if (amt3.equals("0")) {
                amt3 = "";
            }
            double gst1  = 0, gst2 = 0, gst3 = 0, gst4 = 0, gst5 = 0;
            if(percentage1 > 0)
            {     
                gst1 = subtotal * percentage1 / 100.0;
                sb.append("<tr>");
                    sb.append("<td colspan='2' style='text-align:right;'><b>"+taxname1+"</b></td>");
                    sb.append("<td style='text-align:right;'><b>"+IndianFormat(percentage1, 2)+" %</b></td>");
                    sb.append("<td style='text-align:right;'><b>"+IndianFormat(gst1, 2)+"</b></td>");
                sb.append("</tr>");               
            }
            if(percentage2 > 0)
            {     
                gst2 = subtotal * percentage2 / 100.0;
                sb.append("<tr>");
                    sb.append("<td colspan='2' style='text-align:right;'><b>"+taxname2+"</b></td>");
                    sb.append("<td style='text-align:right;'><b>"+IndianFormat(percentage2, 2)+" %</b></td>");
                    sb.append("<td style='text-align:right;'><b>"+IndianFormat(gst2, 2)+"</b></td>");
                sb.append("</tr>");               
            }
            if(percentage3 > 0)
            {     
                gst3 = subtotal * percentage3 / 100.0;
                sb.append("<tr>");
                    sb.append("<td colspan='2' style='text-align:right;'><b>"+taxname3+"</b></td>");
                    sb.append("<td style='text-align:right;'><b>"+IndianFormat(percentage3, 2)+" %</b></td>");
                    sb.append("<td style='text-align:right;'><b>"+IndianFormat(gst3, 2)+"</b></td>");
                sb.append("</tr>");               
            }
            if(percentage4 > 0)
            {     
                gst4 = subtotal * percentage4 / 100.0;
                sb.append("<tr>");
                    sb.append("<td colspan='2' style='text-align:right;'><b>"+taxname4+"</b></td>");
                    sb.append("<td style='text-align:right;'><b>"+IndianFormat(percentage4, 2)+" %</b></td>");
                    sb.append("<td style='text-align:right;'><b>"+IndianFormat(gst4, 2)+"</b></td>");
                sb.append("</tr>");               
            }
            if(percentage5 > 0)
            {     
                gst5 = subtotal * percentage5 / 100.0;
                sb.append("<tr>");
                    sb.append("<td colspan='2' style='text-align:right;'><b>"+taxname5+"</b></td>");
                    sb.append("<td style='text-align:right;'><b>"+IndianFormat(percentage5, 2)+" %</b></td>");
                    sb.append("<td style='text-align:right;'><b>"+IndianFormat(gst5, 2)+"</b></td>");
                sb.append("</tr>");               
            }
            double total = subtotal + gst1 + gst2 + gst3 + gst4 + gst5;
            Words word = new Words();
            String inwords = word.convertFull(total);
            
            String gststr = sb.toString();
            sb.setLength(0);
            String content = getInvoiceContent(assetName, currency, amt1, amt2,
                    amt3, IndianFormat(subtotal, 2), gststr, IndianFormat(total, 2), inwords, bank, branch, accno, ifsc,
                    invoiceno, invoicedate, invoicetemplate);
            String filePath = getMainPath("add_invoice_pdf");
            java.util.Date now = new java.util.Date();
            String fname = String.valueOf(now.getTime()) + ".pdf";
            String htmlFolderName = dateFolder();
            File dir = new File(filePath + htmlFolderName);
            if (!dir.exists()) {
                dir.mkdir();
            }
            pdffilename = htmlFolderName + "/" + fname;
            File pdfFile = new File(filePath + pdffilename);
            generatePDFString(content, pdfFile, PD4Constants.A4, "", "","","", 1050);
            String updateq = "UPDATE t_timesheet SET s_file1 = ?, ts_generatedon = ?, i_invoicestatus = ? WHERE i_timesheetid = ?";
            pstmt = conn.prepareStatement(updateq);
            pstmt.setString(1, pdffilename);
            pstmt.setString(2, currDate1());
            pstmt.setInt(3, 2);
            pstmt.setInt(4, timesheetId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return pdffilename;
    }

    public String getInvoiceContent(String assetName, String currency, String amount1, String amount2,
            String amount3, String subtotal, String gststr, String total,
            String inwords, String bank, String branch, String accno, String ifsc,
            String invoiceno, String invoicedate, String invoicetemplate) {
        String adminpage = getMainPath("template_path") + invoicetemplate;
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("ASSETNAME", assetName);
        hashmap.put("CURRENCY", currency);
        hashmap.put("AMOUNT1", amount1);
        hashmap.put("AMOUNT2", amount2);
        hashmap.put("AMOUNT3", amount3);
        hashmap.put("SUBTOTAL", subtotal);
        hashmap.put("GSTSTR", gststr);
        hashmap.put("TOTAL", total);
        hashmap.put("INWORDS", inwords);
        hashmap.put("BANKNAME", bank);
        hashmap.put("BRANCH", branch);
        hashmap.put("ACCNO", accno);
        hashmap.put("IFSCCODE", ifsc);
        hashmap.put("INVOICENO", invoiceno);
        hashmap.put("INVOICEDATE", invoicedate);
        return template.patch(hashmap);
    }

    public static String currDate011() {
        SimpleDateFormat sdf = new SimpleDateFormat("mm-yyyy");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        java.util.Date dt = cal.getTime();
        return sdf.format(dt);
    }

    public int sendInvoiceMail(String fromval, String toval, String ccval, String bccval, String subject, String description,
            String generatedfile, String attachedfile, String clientName, int timesheetId, String username) throws MessagingException {
        String toaddress[] = parseCommaDelimString(toval);
        String ccaddress[] = parseCommaDelimString(ccval);
        String bccaddress[] = parseCommaDelimString(bccval);
        String filePath = getMainPath("add_invoice_pdf");
        String vfilePath = getMainPath("view_invoice_pdf");
        String attachmentpath1 = "";
        String attachmentpath2 = "";
        String sdescription = description.replaceAll("\n", "<br/>");
        String mailbody = getpatchbody(sdescription, "mail_selection.html");

        String file_maillog = getMainPath("file_maillog");
        java.util.Date nowmail = new java.util.Date();
        String fn_mail = "inma-" + String.valueOf(nowmail.getTime());
        String filePath_html = createFolder(file_maillog);
        String fname = writeHTMLFile(mailbody, file_maillog + "/" + filePath_html, fn_mail + ".html");
        String invoicefile = "", attachmentfile = "";
        if (generatedfile != null && !generatedfile.equals("")) {
            attachmentpath1 = vfilePath + generatedfile;
            invoicefile = filePath + generatedfile;
        }
        if (attachedfile != null && !attachedfile.equals("")) {
            attachmentpath2 = vfilePath + attachedfile;
            attachmentfile = filePath + attachedfile;
        }
        StatsInfo sinfo = mail.postMailAttach(toaddress, ccaddress, bccaddress, mailbody, subject, invoicefile, "invoice.pdf", attachmentfile, "invoice_attached.pdf", -1);
        String from = "";
        if (sinfo != null) {
            from = sinfo.getDdlLabel();
        }
        try {
            conn = getConnection();
            String updateq = "UPDATE t_timesheet SET s_file2 = ?, ts_senton = ?, i_invoicestatus = ? WHERE i_timesheetid = ?";
            pstmt = conn.prepareStatement(updateq);
            pstmt.setString(1, attachedfile);
            pstmt.setString(2, currDate1());
            pstmt.setInt(3, 3);
            pstmt.setInt(4, timesheetId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }

        int maillogId = createMailLog(14, clientName, toval, ccval, bccval, from, subject, "/" + filePath_html + "/" + fname, attachmentpath1, attachmentpath2, timesheetId, username);

        return maillogId;
    }

    public int createMailLog(int type, String name, String to, String cc, String bcc, String from, String subject, String filename, String attachmentpath1, String attachmentpath2, int timesheetId, String username) {
        int id = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_maillog ");
            sb.append("(i_type, s_name, s_to, s_cc, s_bcc, s_from, s_subject, s_filename, ts_regdate, ts_moddate,s_attachmentpath,s_attachmentpath1,i_timesheetid,s_sendby) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            String query = sb.toString().intern();
            sb.setLength(0);
            print(this, "createMailLog :: " + query);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, type);
            pstmt.setString(2, name);
            pstmt.setString(3, to);
            pstmt.setString(4, cc);
            pstmt.setString(5, bcc);
            pstmt.setString(6, from);
            pstmt.setString(7, subject);
            pstmt.setString(8, filename);
            pstmt.setString(9, currDate1());
            pstmt.setString(10, currDate1());
            pstmt.setString(11, attachmentpath1);
            pstmt.setString(12, attachmentpath2);
            pstmt.setInt(13, timesheetId);
            pstmt.setString(14, username);
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createMailLog :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return id;
    }

    public String getpatchbody(String description, String htmlFile) {
        String adminpage = getMainPath("template_path") + htmlFile;
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("MAILBODY", description);
        return template.patch(hashmap);
    }

    public ClientinvoicingInfo getTimesheetDetailfromMaillogtable(int timesheetId)
    {
        ClientinvoicingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_to, s_cc, s_bcc, s_from,s_name,s_subject,s_filename,s_attachmentpath,s_attachmentpath1,s_sendby ");
        sb.append(" FROM t_maillog ");
        sb.append("WHERE i_timesheetid = ? AND i_type = 14  ORDER BY ts_regdate DESC LIMIT 0,1");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, timesheetId);
            //print(this,"getTimesheetDetailfromMaillogtable :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String to, cc, bcc, from, name, subject, mailbody, generatedfile, mailattachedfile, username;
            while (rs.next()) 
            {
                to = rs.getString(1) != null ? rs.getString(1) : "";
                cc = rs.getString(2) != null ? rs.getString(2) : "";
                bcc = rs.getString(3) != null ? rs.getString(3) : "";
                from = rs.getString(4) != null ? rs.getString(4) : "";
                name = rs.getString(5) != null ? rs.getString(5) : "";
                subject = rs.getString(6) != null ? rs.getString(6) : "";
                mailbody = rs.getString(7) != null ? rs.getString(7) : "";
                generatedfile = rs.getString(8) != null ? rs.getString(8) : "";
                mailattachedfile = rs.getString(9) != null ? rs.getString(9) : "";
                username = rs.getString(10) != null ? rs.getString(10) : "";
                info = new ClientinvoicingInfo(timesheetId, to, cc, bcc, from, name, subject, mailbody, generatedfile, mailattachedfile, username);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getTimesheetDetailfromMaillogtable :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getTimesheetDetailfromMaillogtablelist(int timesheetId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_to, s_cc, s_bcc, s_from,s_name,s_subject,s_filename,s_attachmentpath, ");
        sb.append("s_attachmentpath1,s_sendby, DATE_FORMAT( ts_regdate, '%d-%b-%Y'), i_maillogid ");
        sb.append(" FROM t_maillog WHERE i_timesheetid = ?  AND i_type = 14 ORDER BY ts_regdate DESC ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, timesheetId);
            //print(this,"getTimesheetDetailfromMaillogtablelist :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String to, cc, bcc, from, name, subject, mailbody, generatedfile, mailattachedfile, username, date;
            int maillogId = 0;
            while (rs.next()) 
            {
                to = rs.getString(1) != null ? rs.getString(1) : "";
                cc = rs.getString(2) != null ? rs.getString(2) : "";
                bcc = rs.getString(3) != null ? rs.getString(3) : "";
                from = rs.getString(4) != null ? rs.getString(4) : "";
                name = rs.getString(5) != null ? rs.getString(5) : "";
                subject = rs.getString(6) != null ? rs.getString(6) : "";
                mailbody = rs.getString(7) != null ? rs.getString(7) : "";
                generatedfile = rs.getString(8) != null ? rs.getString(8) : "";
                mailattachedfile = rs.getString(9) != null ? rs.getString(9) : "";
                username = rs.getString(10) != null ? rs.getString(10) : "";
                date = rs.getString(11) != null ? rs.getString(11) : "";
                maillogId = rs.getInt(12);
                list.add(new ClientinvoicingInfo(timesheetId, to, cc, bcc, from, name, subject,
                        mailbody, generatedfile, mailattachedfile, username, date, maillogId));
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getTimesheetDetailfromMaillogtablelist :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int updatePaymentdata(int timesheetId, int paymentmodeId, String paymentremarks, String attachedfile3, int uId) 
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_timesheet SET  ");
            sb.append(" i_invoicestatus = 4, ");
            sb.append(" i_paymentmodeid = ?, ");
            sb.append(" s_premarks = ?, ");
            sb.append(" s_file3 = ?, ");
            sb.append(" i_userid = ?, ");
            sb.append(" ts_paymentdate = ?, ");
            sb.append(" ts_moddate = ? ");
            sb.append("WHERE i_timesheetid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, paymentmodeId);
            pstmt.setString(++scc, paymentremarks);
            pstmt.setString(++scc, attachedfile3);
            pstmt.setInt(++scc, uId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, timesheetId);
            print(this, "updatePaymentdata :: " + pstmt.toString());
            cc = pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updatePaymentdata :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public ClientinvoicingInfo getMaillogTimesheetbymaillogId(int maillogId) 
    {
        ClientinvoicingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_to, s_cc, s_bcc, s_from,s_name,s_subject,s_filename,s_attachmentpath,s_attachmentpath1,s_sendby ");
        sb.append(" FROM t_maillog WHERE i_maillogid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, maillogId);
            //print(this,"getMaillogTimesheetbymaillogId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String to, cc, bcc, from, name, subject, mailbody, generatedfile, mailattachedfile, username;
            while (rs.next()) 
            {
                to = rs.getString(1) != null ? rs.getString(1) : "";
                cc = rs.getString(2) != null ? rs.getString(2) : "";
                bcc = rs.getString(3) != null ? rs.getString(3) : "";
                from = rs.getString(4) != null ? rs.getString(4) : "";
                name = rs.getString(5) != null ? rs.getString(5) : "";
                subject = rs.getString(6) != null ? rs.getString(6) : "";
                mailbody = rs.getString(7) != null ? rs.getString(7) : "";
                generatedfile = rs.getString(8) != null ? rs.getString(8) : "";
                mailattachedfile = rs.getString(9) != null ? rs.getString(9) : "";
                username = rs.getString(10) != null ? rs.getString(10) : "";
                info = new ClientinvoicingInfo(maillogId, to, cc, bcc, from, name, subject, mailbody, 
                        generatedfile, mailattachedfile, username);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getMaillogTimesheetbymaillogId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public void unzip(String zippath, String destinationpath) {
        String command = "";
        if (getMainPath("run_place").equals("local")) {
            command = "powershell Expand-Archive -Path " + zippath + " -DestinationPath " + destinationpath;
        } else {
            command = "unzip " + zippath + " -d " + destinationpath;
        }
//        logger.info("############################################## " + command);
        try {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(command);
            p.waitFor();
            File file = new File(zippath);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int savesalaryslip(int countryId, int clientassetId, String path, String foldername)
    {
        int govdocId = 0;
        PreparedStatement pstmt_insert1 = null;
        PreparedStatement pstmt_insert2 = null;
        PreparedStatement pstmt_issuedby = null;
        try {
            String currdate = currDate1();
            conn = getConnection();
            String query = "SELECT i_candidateid FROM t_candidate WHERE s_empno =? AND i_clientassetid =?";
            pstmt = conn.prepareStatement(query);

            String query_issuedby = "SELECT i_documentissuedbyid FROM t_documentissuedby WHERE s_name ='Bank Authority' AND i_doctypeid =11 AND i_countryid =?";
            pstmt_issuedby = conn.prepareStatement(query_issuedby);

            String insert_govdoc = "INSERT INTO t_govdoc (i_doctypeid, s_docno, i_candidateid, i_documentissuedbyid, i_status, ts_regdate, ts_moddate) VALUES(?, ?, ?, ?, ?, ?, ?)";
            pstmt_insert1 = conn.prepareStatement(insert_govdoc, Statement.RETURN_GENERATED_KEYS);

            String insert_doc = "INSERT INTO t_documentfiles (i_govid, s_name, i_status, ts_regdate, ts_moddate) VALUES(?, ?, ?, ?, ?)";
            pstmt_insert2 = conn.prepareStatement(insert_doc);

            File f = new File(path);
            if (f.exists()) {
                File[] files = f.listFiles();
                for (int i = 0; i < files.length; i++) {
                    String fileName = files[i].getName();
                    if(fileName != null && !fileName.equals("") && fileName.contains("^"))
                    {
                        String savepath = foldername + files[i].getName();
                        String empno = fileName.substring(8, fileName.indexOf("^"));
                        String docno = fileName.substring(0, fileName.lastIndexOf("."));
                        pstmt.setString(1, empno);
                        pstmt.setInt(2, clientassetId);
                        rs = pstmt.executeQuery();
                        int candidateId = 0;
                        while (rs.next()) {
                            candidateId = rs.getInt(1);
                        }
                        rs.close();
                        if (candidateId > 0) {
                            pstmt_issuedby.setInt(1, countryId);
                          // print(this, "savesalaryslip 2 " + pstmt_issuedby.toString());
                            rs = pstmt_issuedby.executeQuery();
                            int documentissuedbyId = 0;
                            while (rs.next()) {
                                documentissuedbyId = rs.getInt(1);
                            }
                            rs.close();

                            pstmt_insert1.setInt(1, 11);
                            pstmt_insert1.setString(2, cipher(docno));
                            pstmt_insert1.setInt(3, candidateId);
                            pstmt_insert1.setInt(4, documentissuedbyId);
                            pstmt_insert1.setInt(5, 1);
                            pstmt_insert1.setString(6, currdate);
                            pstmt_insert1.setString(7, currdate);
                           // print(this, "savesalaryslip 3 " + pstmt_insert1.toString());
                            pstmt_insert1.executeUpdate();
                            rs = pstmt_insert1.getGeneratedKeys();
                            while (rs.next()) {
                                govdocId = rs.getInt(1);
                            }
                            rs.close();
                            if (govdocId > 0) {
                                pstmt_insert2.setInt(1, govdocId);
                                pstmt_insert2.setString(2, savepath);
                                pstmt_insert2.setInt(3, 1);
                                pstmt_insert2.setString(4, currdate);
                                pstmt_insert2.setString(5, currdate);
                                //print(this, "savesalaryslip 4 " + pstmt_insert2.toString());
                                pstmt_insert2.executeUpdate();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
            try {
                if (pstmt_insert1 != null) {
                    pstmt_insert1.close();
                }
                if (pstmt_insert2 != null) {
                    pstmt_insert2.close();
                }
                if (pstmt_issuedby != null) {
                    pstmt_issuedby.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return govdocId;
    }

    public int createPaySlipHistory(String foldername, int uId, int clientassetId) {
        int id = 0;
        try {

            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_invoicehistory (i_clientassetid, s_foldername, i_userid, ts_regdate, ts_moddate) VALUES(?, ?, ?, ?, ?)");

            String query = sb.toString().intern();
            sb.setLength(0);
            print(this, "createPaySlipHistory :: " + query);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, clientassetId);
            pstmt.setString(2, foldername);
            pstmt.setInt(3, uId);
            pstmt.setString(4, currDate1());
            pstmt.setString(5, currDate1());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createPaySlipHistory :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return id;
    }

    public ArrayList getPaySlipHistoryDtls(int clientassetId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_foldername, DATE_FORMAT( t_invoicehistory.ts_regdate, '%d-%b-%Y %H:%i'), t_userlogin.s_name ");
        sb.append("FROM t_invoicehistory ");
        sb.append("LEFT JOIN t_userlogin ON (t_userlogin.i_userid = t_invoicehistory.i_userid) ");
        sb.append("WHERE t_invoicehistory.i_clientassetid =? ORDER BY t_invoicehistory.ts_regdate DESC LIMIT 0,5 ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            //print(this,"getPaySlipHistoryDtls :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String foldername, date, name;
            while (rs.next()) 
            {
                foldername = rs.getString(1) != null ? rs.getString(1) : "";
                date = rs.getString(2) != null ? rs.getString(2) : "";
                name = rs.getString(3) != null ? rs.getString(3) : "";
                list.add(new ClientinvoicingInfo(foldername, date, name));
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getPaySlipHistoryDtls :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
}
