package com.web.jxp.airport;

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

public class Airport extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getAirportByName(String search, int next, int count)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_airport.i_airportid, t_airport.s_name, t_airport.s_shortname, t_airport.i_status, t_country.s_name, t_city.s_name ");
        sb.append("FROM t_airport ");
        sb.append("LEFT JOIN t_country ON t_airport.i_countryid = t_country.i_countryid ");
        sb.append("LEFT JOIN t_city ON t_airport.i_cityid = t_city.i_cityid ");
        sb.append("WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("AND (t_airport.s_name LIKE ? OR t_country.s_name LIKE ?  OR t_airport.s_shortname LIKE ? OR t_city.s_name LIKE ?) ");
        
        sb.append(" ORDER BY   t_airport.i_status,t_country.s_name, t_airport.s_name ");
        if(count > 0)
            sb.append(" limit ").append(next*count).append(", ").append(count);
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("SELECT COUNT(1) FROM t_airport ");
        sb.append("LEFT JOIN t_country ON t_airport.i_countryid = t_country.i_countryid ");
         sb.append("LEFT JOIN t_city ON t_airport.i_cityid = t_city.i_cityid ");
        sb.append("WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("AND (t_airport.s_name LIKE ? OR t_country.s_name LIKE ?  OR t_airport.s_shortname LIKE ? OR t_city.s_name LIKE ?) ");
      
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
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            logger.info("getAirportByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, countryName, shortname, cityName;
            int airportId, status;
            while (rs.next())
            {
                airportId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                shortname = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4);
                countryName = rs.getString(5) != null ? rs.getString(5) : "";
                cityName = rs.getString(6) != null ? rs.getString(6) : "";
                list.add(new AirportInfo(airportId, name,shortname, status, countryName,  cityName));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            //print(this,"getAirportByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                airportId = rs.getInt(1);
                list.add(new AirportInfo(airportId, "", 0, 0));
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
        AirportInfo info;
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
                    info = (AirportInfo) l.get (i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            AirportInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (AirportInfo) l.get(i);
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
    public String getInfoValue(AirportInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getAirportName()!= null ? info.getAirportName(): "";
        if(i !=null && i.equals("2"))
            infoval = info.getShortname()!= null ? info.getShortname() : "";
        if(i != null && i.equals("3"))
            infoval = info.getCityName()!= null ? info.getCityName(): "";
        if(i !=null && i.equals("4"))
            infoval = info.getCountryName()!= null ? info.getCountryName() : "";
        
        return infoval;
    }
    
    public AirportInfo getAirportDetailById(int airportId)
    {
        AirportInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_countryid,i_cityid, i_status,s_shortname FROM t_airport WHERE i_airportid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, airportId);
            print(this,"getAirportDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name,shortname;
            int status,countryId,cityId;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                countryId = rs.getInt(2);
                cityId = rs.getInt(3);
                status = rs.getInt(4);
                shortname = rs.getString(5) != null ? rs.getString(5) : "";
                
                info = new AirportInfo(airportId, name, status, countryId, 0,cityId,shortname);
            }
        }
        catch (Exception exception)
        {
            print(this,"getAirportDetailById :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public AirportInfo getAirportDetailByIdforDetail(int airportId)
    {
        AirportInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_airport.s_name,t_airport.i_status, t_country.s_name,t_city.s_name, t_airport.s_shortname FROM t_airport ");
        sb.append( " LEFT JOIN t_country ON t_airport.i_countryid = t_country.i_countryid ");
        sb.append( " LEFT JOIN t_city ON t_city.i_cityid = t_airport.i_cityid ");
        sb.append("WHERE i_airportid = ?");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, airportId);
            print(this,"getAirportDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status = 0;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2); 
                String countryName = rs.getString(3) != null ? rs.getString(3): "";
                String cityName = rs.getString(4) != null ? rs.getString(4): "";
                String shortname = rs.getString(5) != null ? rs.getString(5): "";
                
                info = new AirportInfo(airportId, name, status, countryName, 0,cityName,shortname);
            }
            rs.close();            
        }
        catch (Exception exception)
        {
            print(this,"getAirportDetailByIdforDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int createAirport(AirportInfo info)
    {
        int airportId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_airport ");
            sb.append("(s_name, s_shortname, i_countryid,i_cityid, i_status,  i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getAirportName()));
            pstmt.setString(++scc, (info.getShortname()));
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getCityId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());  
            print(this,"createAirport :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                airportId = rs.getInt(1);
            }
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"createAirport :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return airportId;
    }
    
    public int updateAirport(AirportInfo info)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_airport SET ");
            sb.append("s_name = ?, ");
            sb.append("s_shortname = ?, ");
            sb.append("i_countryid = ?, ");
            sb.append("i_cityid = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ?, ");
            sb.append("i_countryid = ? ");
            sb.append("WHERE i_airportid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getAirportName());
            pstmt.setString(++scc, info.getShortname());
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getCityId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getAirportId());
            print(this,"updateAirport :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"updateAirport :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int checkDuplicacy(int airportId, String name, int countryId)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_airportid FROM t_airport WHERE i_countryid = ? AND s_name = ? AND i_status in (1, 2)");
        if(airportId > 0)
            sb.append(" AND i_airportid != ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;        
            pstmt.setInt(++scc, countryId);
            pstmt.setString(++scc, name);
            if(airportId > 0)
                pstmt.setInt(++scc, airportId);
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
    
    public Collection getCountry()
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_countryid, s_name FROM t_country WHERE i_status = 1 ORDER BY s_name").intern();
        coll.add(new AirportInfo(-1, "- Select -"));
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while(rs.next())
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new AirportInfo(refId, refName));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(conn,pstmt,rs);
        }
        return coll;
    }
    
    public int deleteAirport(int airportId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_airport SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_airportid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if(status == 1)
                pstmt.setInt(++scc, 2);
            else
                pstmt.setInt(++scc, 1);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, airportId);
            
            cc = pstmt.executeUpdate();
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"deleteAirport :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Updated", 24, airportId); 
        return cc;
    } 
    
    public ArrayList getExcel(String search ) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
         sb.append("SELECT t_airport.i_airportid,  t_airport.s_name ,  t_airport.s_shortname, t_airport.i_status,  t_country.s_name , t_city.s_name from t_airport ");
        sb.append("LEFT JOIN t_country ON t_airport.i_countryid = t_country.i_countryid ");
        sb.append("LEFT JOIN t_city ON t_airport.i_cityid = t_city.i_cityid ");
        sb.append("WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("AND (t_airport.s_name LIKE ? OR t_country.s_name LIKE ?  OR t_airport.s_shortname LIKE ? OR t_city.s_name LIKE ?) ");
        
        sb.append(" ORDER BY t_airport.i_status, t_country.s_name, t_airport.s_name  ");
        
        String query = sb.toString().intern();
        sb.setLength(0);        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            
            int scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            rs = pstmt.executeQuery();
            String name, countryName, shortname, cityName;
            int airportId, status;
            while (rs.next()) 
            {
                airportId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                shortname = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4);
                countryName = rs.getString(5) != null ? rs.getString(5) : "";
                cityName = rs.getString(6) != null ? rs.getString(6) : "";
                list.add(new AirportInfo(airportId, name,shortname, status, countryName,  cityName));
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
       String query = ("SELECT i_countryid, i_airportid, s_name,s_shortname,i_cityid FROM t_airport WHERE i_status = 1 ORDER BY  s_name ");      
        try
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int countryid;
            int airportid,cityid;
            String name,shortname;
            JSONArray jarr = new JSONArray();
            while (rs.next())
            {
                countryid = rs.getInt(1);
                airportid = rs.getInt(2);
                name = rs.getString(3) != null ? rs.getString(3) : "";
                shortname = rs.getString(4) != null ? rs.getString(4) : "";
                cityid = rs.getInt(5);
                JSONObject jobj = new JSONObject();
                jobj.put("countryid", countryid);
                jobj.put("cityid", cityid);
                jobj.put("airportid", airportid);
                jobj.put("name", name);
                jobj.put("shortname", shortname);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "airport.json");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(null, pstmt, rs);
        }
    }

    public Collection getcountry()
    {
        Collection coll = new LinkedList();
        coll.add(new AirportInfo(-1, "Select Airport"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("airport.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new AirportInfo(jobj.optInt("airportId"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }

    public Collection getCities(int countryId)
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_cityid, s_name FROM t_city WHERE i_status = 1 AND i_countryid = ? ORDER BY s_name").intern();
        coll.add(new AirportInfo(-1, "--Select City --"));
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, countryId);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while(rs.next())
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new AirportInfo(refId, refName));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(conn,pstmt,rs);
        }
        return coll;
    }
    
    public Collection getAirportbycountryId(int countryId)
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_airportid, s_name FROM t_airport WHERE i_status = 1 AND i_countryid = ? ORDER BY s_name").intern();
        coll.add(new AirportInfo(-1, "--Select Airport --"));
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,countryId);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while(rs.next())
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new AirportInfo(refId, refName));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(conn,pstmt,rs);
        }
        return coll;
    }
}



