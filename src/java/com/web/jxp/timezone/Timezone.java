package com.web.jxp.timezone;

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

public class Timezone extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getTimezoneByName(String search, int next, int count) {
        ArrayList timezones = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t.i_timezoneid, t.s_name, t.i_status, t_country.s_name, t.s_code FROM t_timezone AS t ");
        sb.append("LEFT JOIN t_country ON t.i_countryid = t_country.i_countryid ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t.s_name LIKE ? OR t.s_code LIKE ? OR t_country.s_name LIKE ?) ");
        }
        sb.append("ORDER BY t.i_status, t_country.s_name, t.s_name ");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1) FROM t_timezone AS t ");
        sb.append("LEFT JOIN t_country ON t.i_countryid = t_country.i_countryid ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t.s_name LIKE ? OR t.s_code LIKE ? OR t_country.s_name LIKE ?) ");
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
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getTimezoneByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, countryName, code;
            int timezoneId, status;
            while (rs.next()) 
            {
                timezoneId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                countryName = rs.getString(4) != null ? rs.getString(4) : "";
                code = rs.getString(5) != null ? rs.getString(5) : "";
                timezones.add(new TimezoneInfo(timezoneId, name, code, status, countryName, 0));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            print(this,"getTimezoneByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                timezoneId = rs.getInt(1);
                timezones.add(new TimezoneInfo(timezoneId, "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return timezones;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        TimezoneInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (TimezoneInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            TimezoneInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (TimezoneInfo) l.get(i);
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
    public String getInfoValue(TimezoneInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getTimezoneName() != null ? info.getTimezoneName() : "";
        }
        if (i != null && i.equals("2")) {
            infoval = info.getCountryName() != null ? info.getCountryName() : "";
        }

        return infoval;
    }

    public TimezoneInfo getTimezoneDetailById(int timezoneId) 
    {
        TimezoneInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_countryid, i_status, s_code FROM t_timezone WHERE i_timezoneid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, timezoneId);
            print(this, "getTimezoneDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                int countryId = rs.getInt(3);
                String code = rs.getString(4) != null ? rs.getString(4) : "";

                info = new TimezoneInfo(timezoneId, name, code, status, countryId, 0);
            }
        } catch (Exception exception) {
            print(this, "getTimezoneDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public TimezoneInfo getTimezoneDetailByIdforDetail(int timezoneId) {
        TimezoneInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t.s_name, t.i_status, t_country.s_name, t.s_code ");
        sb.append("FROM t_timezone AS t ");
        sb.append("LEFT JOIN t_country ON t.i_countryid = t_country.i_countryid WHERE t.i_timezoneid =?");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, timezoneId);
            print(this, "getTimezoneDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status = 0;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                String countryName = rs.getString(3) != null ? rs.getString(3) : "";
                String code = rs.getString(4) != null ? rs.getString(4) : "";

                info = new TimezoneInfo(timezoneId, name, code, status, countryName, 0);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getTimezoneDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createTimezone(TimezoneInfo info)
    {
        int timezoneId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_timezone ");
            sb.append("(s_name, i_countryid, s_code, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getTimezoneName()));
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setString(++scc, info.getTimezoneCode());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "createTimezone :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                timezoneId = rs.getInt(1);
            }
            createjson(conn);
        } catch (Exception exception) {
            print(this, "createTimezone :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return timezoneId;
    }

    public int updateTimezone(TimezoneInfo info)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_timezone set ");
            sb.append("s_name = ?, ");
            sb.append("s_code = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ?, ");
            sb.append("i_countryid = ? ");
            sb.append("WHERE i_timezoneid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getTimezoneName());
            pstmt.setString(++scc, info.getTimezoneCode());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getTimezoneId());
            print(this, "updateTimezone :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "updateTimezone :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int timezoneId, String name, int countryId) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_timezoneid FROM t_timezone WHERE i_countryid = ? AND s_name = ? AND i_status IN (1, 2)");
        if (timezoneId > 0) {
            sb.append(" AND i_timezoneid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, countryId);
            pstmt.setString(++scc, name);
            if (timezoneId > 0) {
                pstmt.setInt(++scc, timezoneId);
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

    public Collection getCountry() 
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_countryid, s_name FROM t_country WHERE i_status = 1 order by s_name").intern();
        coll.add(new TimezoneInfo(-1, "- Select -"));
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
                coll.add(new TimezoneInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public int deleteTimezone(int timezoneId, int userId, int status, String ipAddrStr, String iplocal) 
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_timezone set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_timezoneid = ? ");
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
            pstmt.setInt(++scc, timezoneId);
            
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "deleteTimezone :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Updated", 16, timezoneId);
        return cc;
    }

    public ArrayList getExcel(String search) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t.i_timezoneid, t.s_name, t.i_status, t_country.s_name, t.s_code FROM t_timezone AS t "
                + "LEFT JOIN t_country ON t.i_countryid = t_country.i_countryid ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t.s_name LIKE ? OR t.s_code LIKE ? OR t_country.s_name LIKE ?) ");
        }
        sb.append("ORDER BY t.i_status, t_country.s_name, t.s_name ");

        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            Common.print(this, "getListForExcel :: " + pstmt.toString());

            int scc = 0;
            if (search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                int timezoneId = rs.getInt(1);
                String timezonename = rs.getString(2) != null ? rs.getString(2) : "";
                int Status = rs.getInt(3);
                String countryname = rs.getString(4) != null ? rs.getString(4) : "";
                String code = rs.getString(5) != null ? rs.getString(5) : "";

                list.add(new TimezoneInfo(timezoneId, timezonename, code, Status, countryname));
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
        String query = ("SELECT i_countryid, i_timezoneid, s_name FROM t_timezone WHERE i_status = 1 order by  s_name ");
        try 
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int countryid;
            int timezoneid;
            String name;
            JSONArray jarr = new JSONArray();
            while (rs.next())
            {
                countryid = rs.getInt(1);
                timezoneid = rs.getInt(2);
                name = rs.getString(3) != null ? rs.getString(3) : "";
                JSONObject jobj = new JSONObject();
                jobj.put("countryid", countryid);
                jobj.put("timezoneid", timezoneid);
                jobj.put("name", name);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "timezone.json");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, pstmt, rs);
        }
    }

    public Collection getcountry() {
        Collection coll = new LinkedList();
        coll.add(new TimezoneInfo(-1, "Select Timezone"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("timezone.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) {
            int len = arr.length();
            for (int i = 0; i < len; i++) {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) {
                    coll.add(new TimezoneInfo(jobj.optInt("timezoneId"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }

    public Collection getCities()
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_timezoneid, s_name FROM t_timezone WHERE i_status = 1 order by s_name").intern();
        coll.add(new TimezoneInfo(-1, "- Select Timezone -"));
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
                coll.add(new TimezoneInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
}
