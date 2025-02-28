package com.web.jxp.invoicetemplate;

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

public class Invoicetemplate extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getInvoicetemplateByName(String search, int next, int count, int allclient, String permission, String cids, String assetids) {
        ArrayList invoicetemplates = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_invoicetemplate.i_invoicetemplateid, t_invoicetemplate.s_name, t_invoicetemplate.i_status, t_client.s_name FROM t_invoicetemplate ");
        sb.append("LEFT JOIN t_client ON (t_invoicetemplate.i_clientid = t_client.i_clientid) ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_invoicetemplate.s_name LIKE ? OR t_client.s_name LIKE ? ) ");
        }
        if (allclient == 1) {
            sb.append(" AND t_client.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND t_client.i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND t_client.i_clientid < 0 ");
            }
        }
        sb.append("ORDER BY t_invoicetemplate.i_status,t_client.s_name, t_invoicetemplate.s_name ");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT count(1) FROM t_invoicetemplate ");
        sb.append("LEFT JOIN t_client ON (t_invoicetemplate.i_clientid = t_client.i_clientid) ");
        sb.append("WHERE 0=0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_invoicetemplate.s_name LIKE ? OR t_client.s_name LIKE ?) ");
        }
        if (allclient == 1) {
            sb.append(" AND t_client.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append(" AND t_client.i_clientid IN (" + cids + ") ");
            } else {
                sb.append(" AND t_client.i_clientid < 0 ");
            }
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
            }
            logger.info("getInvoicetemplateByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, clientname;
            int invoicetemplateId, status;
            while (rs.next()) 
            {
                invoicetemplateId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                clientname = rs.getString(4) != null ? rs.getString(4) : "";
                invoicetemplates.add(new InvoicetemplateInfo(invoicetemplateId, name, status, clientname));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            print(this, "getInvoicetemplateByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                invoicetemplateId = rs.getInt(1);
                invoicetemplates.add(new InvoicetemplateInfo(invoicetemplateId, "", 0, ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return invoicetemplates;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        InvoicetemplateInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (InvoicetemplateInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            InvoicetemplateInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (InvoicetemplateInfo) l.get(i);
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
    public String getInfoValue(InvoicetemplateInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getClientname() != null ? info.getClientname() : "";
        }
        if (i != null && i.equals("2")) {
            infoval = info.getName() != null ? info.getName() : "";
        }
        return infoval;
    }

    public InvoicetemplateInfo getInvoicetemplateDetailById(int invoicetemplateId)
    {
        InvoicetemplateInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_clientid, s_name, s_filename, i_status FROM t_invoicetemplate where i_invoicetemplateid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, invoicetemplateId);
            print(this, "getInvoicetemplateDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description;
            int status, clientId;
            while (rs.next())
            {
                clientId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";                
                description = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4);                
                info = new InvoicetemplateInfo(invoicetemplateId, name, clientId, "", description, status, 0);
            }
        } catch (Exception exception) {
            print(this, "getInvoicetemplateDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public InvoicetemplateInfo getInvoicetemplateDetailByIdforDetail(int invoicetemplateId)
    {
        InvoicetemplateInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_invoicetemplate.s_name, t_invoicetemplate.i_status , t_client.s_name, t_invoicetemplate.s_filename  ");
        sb.append("FROM t_invoicetemplate  ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid =  t_invoicetemplate.i_clientid) ");
        sb.append("WHERE t_invoicetemplate.i_invoicetemplateid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, invoicetemplateId);
            print(this, "getInvoicetemplateDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, clientname, description;
            int status = 0;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                clientname = rs.getString(3) != null ? rs.getString(3) : "";
                description = rs.getString(4) != null ? rs.getString(4) : "";                
                info = new InvoicetemplateInfo(invoicetemplateId, name, 0, clientname, description, status, 0);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getInvoicetemplateDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createInvoicetemplate(InvoicetemplateInfo info) {
        int invoicetemplateId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_invoicetemplate ");
            sb.append("(s_name, i_status, i_userid,i_clientid, s_filename, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setInt(++scc, info.getClientId());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "createInvoicetemplate :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                invoicetemplateId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createInvoicetemplate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return invoicetemplateId;
    }

    public int updateInvoicetemplate(InvoicetemplateInfo info) 
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_invoicetemplate set ");
            sb.append("s_name = ?, ");
            sb.append("i_clientid = ?, ");
            sb.append("s_filename = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_invoicetemplateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setInt(++scc, info.getClientId());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getInvoicetemplateId());
            print(this, "updateInvoicetemplate :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateInvoicetemplate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int invoicetemplateId, String name, int clientId) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_invoicetemplateid FROM t_invoicetemplate where s_name = ? and i_clientid = ? and i_status in (1, 2)");
        if (invoicetemplateId > 0) {
            sb.append(" and i_invoicetemplateid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, name);
            pstmt.setInt(++scc, clientId);
            if (invoicetemplateId > 0) {
                pstmt.setInt(++scc, invoicetemplateId);
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

    public int deleteInvoicetemplate(int invoicetemplateId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_invoicetemplate set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_invoicetemplateid = ? ");
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
            pstmt.setInt(++scc, invoicetemplateId);

            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteInvoicetemplate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 80, invoicetemplateId);
        return cc;
    }

    public ArrayList getListForExcel(String search, int allclient, String permission, String cids, String assetids) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_invoicetemplate.s_name, t_invoicetemplate.i_status, t_client.s_name FROM t_invoicetemplate ");
        sb.append("LEFT JOIN t_client ON (t_invoicetemplate.i_clientid = t_client.i_clientid) ");
        sb.append("WHERE 0=0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_invoicetemplate.s_name LIKE ? OR t_client.s_name LIKE ?) ");
        }
        if (allclient == 1) {
            sb.append(" AND t_client.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND t_client.i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND t_client.i_clientid < 0 ");
            }
        }
        sb.append(" ORDER BY t_invoicetemplate.i_status, t_client.s_name, t_invoicetemplate.s_name ");
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
            }
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, clientname;
            int status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                clientname = rs.getString(3) != null ? rs.getString(3) : "";
                list.add(new InvoicetemplateInfo(0, name, status, clientname));
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
        coll.add(new InvoicetemplateInfo(-1, " Select Client"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                refName = rs.getString(2)  != null ? rs.getString(2): "";
                coll.add(new InvoicetemplateInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
}
