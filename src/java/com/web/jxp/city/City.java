package com.web.jxp.city;

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

public class City extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getCityByName(String search, int next, int count) 
    {
        ArrayList citys = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_city.i_cityid,  t_city.s_name , t_city.i_status,  t_country.s_name from t_city ");
        sb.append("left join t_country on t_city.i_countryid = t_country.i_countryid ");
        sb.append("where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_city.s_name like ? OR t_country.s_name like ?) ");
        }
        sb.append(" order by   t_city.i_status,t_country.s_name, t_city.s_name ");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("select count(1) FROM t_city ");
        sb.append("left join t_country on t_city.i_countryid = t_country.i_countryid ");
        sb.append("where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_city.s_name like ? OR t_country.s_name like ?) ");
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
            logger.info("getCityByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, countryName;
            int cityId, status;
            while (rs.next())
            {
                cityId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                countryName = rs.getString(4) != null ? rs.getString(4) : "";
                citys.add(new CityInfo(cityId, name, status, countryName, 0));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            //print(this,"getCityByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                cityId = rs.getInt(1);
                citys.add(new CityInfo(cityId, "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return citys;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        CityInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (CityInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            CityInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (CityInfo) l.get(i);
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
    public String getInfoValue(CityInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getCityName() != null ? info.getCityName() : "";
        }
        if (i != null && i.equals("2")) {
            infoval = info.getCountryName() != null ? info.getCountryName() : "";
        }
        return infoval;
    }

    public CityInfo getCityDetailById(int cityId) 
    {
        CityInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_countryid, i_status FROM t_city where i_cityid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, cityId);
            print(this, "getCityDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2);
                int countryId = rs.getInt(3);
                info = new CityInfo(cityId, name, status, countryId, 0);
            }
        } catch (Exception exception) {
            print(this, "getCityDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public CityInfo getCityDetailByIdforDetail(int cityId) 
    {
        CityInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_city.s_name,t_city.i_status, t_country.s_name  FROM t_city left join t_country on t_city.i_countryid = t_country.i_countryid where i_cityid = ?");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, cityId);
            print(this, "getCityDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status = 0;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2);
                String countryName = rs.getString(3) != null ? rs.getString(3): "";
                
                info = new CityInfo(cityId, name, status, countryName, 0);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getCityDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createCity(CityInfo info)
    {
        int cityId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_city ");
            sb.append("(s_name, i_countryid, i_status,  i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getCityName()));
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "createCity :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                cityId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createCity :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cityId;
    }

    public int updateCity(CityInfo info) 
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_city set ");
            sb.append("s_name = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ?, ");
            sb.append("i_countryid = ? ");
            sb.append("where i_cityid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getCityName());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getCityId());
            print(this, "updateCity :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateCity :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int cityId, String name, int countryId) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_cityid FROM t_city WHERE i_countryid =? AND s_name =? AND i_status IN (1, 2) ");
        if (cityId > 0) {
            sb.append("AND i_cityid !=? ");
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
            if (cityId > 0) {
                pstmt.setInt(++scc, cityId);
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
        String query = ("SELECT i_countryid, s_name FROM t_country where i_status = 1 order by s_name").intern();
        coll.add(new CityInfo(-1, "- Select -"));
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
                coll.add(new CityInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public int deleteCity(int cityId, int userId, int status, String ipAddrStr, String iplocal) 
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_city set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_cityid = ? ");
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
            pstmt.setInt(++scc, cityId);

            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteCity :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Updated", 24, cityId);
        return cc;
    }

    public ArrayList getExcel(String search) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_city.i_cityid,  t_city.s_name, ");
        sb.append(" t_city.i_status,  t_country.s_name from t_city ");
        sb.append("left join t_country on t_city.i_countryid = t_country.i_countryid ");
        sb.append("where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_city.s_name like ? OR t_country.s_name like ? ) ");
        }
        sb.append(" order by t_city.i_status, t_country.s_name, t_city.s_name  ");
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
            }
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                int cityId = rs.getInt(1);
                String cityname = rs.getString(2) != null ? rs.getString(2) : "";
                int Status = rs.getInt(3);
                String countryname = rs.getString(4) != null ? rs.getString(4) : "";

                list.add(new CityInfo(cityId, cityname, Status, countryname));
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

    public Collection getCities() 
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_cityid, s_name FROM t_city where i_status = 1 order by s_name").intern();
        coll.add(new CityInfo(-1, "--Select City --"));
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
                coll.add(new CityInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getCitybycountryId(int countryId) 
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_cityid, s_name FROM t_city where i_status = 1 and i_countryid = ? order by s_name").intern();
        coll.add(new CityInfo(-1, "--Select City --"));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, countryId);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new CityInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public int createCity(int countryId, String name, int userId) 
    {
        int cc = 0;
        try 
        {
            String query = ("insert into t_city (i_countryid, s_name, i_status, i_userid, ts_regdate, ts_moddate) values (?,?,?,?,?,?) ");

            conn = getConnection();
            String checkq = "select i_cityid from t_city where i_status IN (1,2) and i_countryid = ? and s_name = ?";
            pstmt = conn.prepareStatement(checkq);
            pstmt.setInt(1, countryId);
            pstmt.setString(2, name);
            rs = pstmt.executeQuery();
            int check = 0;
            while (rs.next()) {
                check = rs.getInt(1);
            }
            rs.close();
            if (check == 0) {
                pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, countryId);
                pstmt.setString(2, name);
                pstmt.setInt(3, 1);
                pstmt.setInt(4, userId);
                pstmt.setString(5, currDate1());
                pstmt.setString(6, currDate1());
                pstmt.executeUpdate();
                rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    cc = rs.getInt(1);
                }
                rs.close();
            } else {
                cc = -2;
            }
        } catch (Exception exception) {
            print(this, "createCity :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
}
