package com.web.jxp.currency;

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
import org.json.JSONArray;
import org.json.JSONObject;

public class Currency extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getCurrencyByName(String search, int next, int count) {
        ArrayList currencys = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select i_currencyid, s_name, i_status ");
        sb.append("FROM t_currency where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (s_name like ?) ");
        }
        sb.append(" order by i_status, s_name ");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("select count(1) FROM t_currency where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (s_name like ?) ");
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
            }
            logger.info("getCurrencyByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int currencyId, status;
            while (rs.next()) 
            {
                currencyId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                currencys.add(new CurrencyInfo(currencyId, name, status, 0));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            print(this,"getCurrencyByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                currencyId = rs.getInt(1);
                currencys.add(new CurrencyInfo(currencyId, "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return currencys;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        CurrencyInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (CurrencyInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            CurrencyInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (CurrencyInfo) l.get(i);
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
    public String getInfoValue(CurrencyInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getName() != null ? info.getName() : "";
        }
        return infoval;
    }

    public CurrencyInfo getCurrencyDetailById(int currencyId) {
        CurrencyInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_status FROM t_currency where i_currencyid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, currencyId);
            //print(this,"getCurrencyDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2);
                info = new CurrencyInfo(currencyId, name, status, 0);
            }
        } catch (Exception exception) {
            print(this, "getCurrencyDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public CurrencyInfo getCurrencyDetailByIdforDetail(int currencyId) {
        CurrencyInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_status FROM t_currency where i_currencyid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, currencyId);
            //print(this,"getCurrencyDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status = 0;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2);
                info = new CurrencyInfo(currencyId, name, status, 0);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getCurrencyDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createCurrency(CurrencyInfo info) {
        int currencyId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_currency ");
            sb.append("(s_name, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            //print(this,"createCurrency :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                currencyId = rs.getInt(1);
            }
            createjson(conn);
        } catch (Exception exception) {
            print(this, "createCurrency :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return currencyId;
    }

    public int updateCurrency(CurrencyInfo info) {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_currency set ");
            sb.append("s_name = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_currencyid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getCurrencyId());
            //print(this,"updateCurrency :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "updateCurrency :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int currencyId, String name) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_currencyid FROM t_currency where s_name = ? and i_status in (1, 2)");
        if (currencyId > 0) {
            sb.append(" and i_currencyid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, name);
            if (currencyId > 0) {
                pstmt.setInt(++scc, currencyId);
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

    public int deleteCurrency(int currencyId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_currency set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_currencyid = ? ");
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
            pstmt.setInt(++scc, currencyId);

            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "deleteCurrency :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 10, currencyId);
        return cc;
    }

    public ArrayList getListForExcel(String search) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select s_name, i_status ");
        sb.append("FROM t_currency where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (s_name like ?) ");
        }
        sb.append(" order by i_status, s_name ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                list.add(new CurrencyInfo(0, name, status, 0));
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

    public void createjson(Connection conn) 
    {
        String query = ("Select i_currencyid, s_name FROM t_currency where i_status = 1 order by  s_name ");
        try 
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id;
            String name;
            JSONArray jarr = new JSONArray();
            while (rs.next()) 
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2): "";
                JSONObject jobj = new JSONObject();
                jobj.put("id", id);
                jobj.put("name", name);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "currency.json");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, pstmt, rs);
        }
    }

    public Collection getCurrencys() 
    {
        Collection coll = new LinkedList();
        coll.add(new CurrencyInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("currency.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) 
        {
            int len = arr.length();
            for (int i = 0; i < len; i++) 
            {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) 
                {
                    coll.add(new CurrencyInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
}
