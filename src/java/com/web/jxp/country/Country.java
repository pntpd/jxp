package com.web.jxp.country;

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

public class Country extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getCountryByName(String search, int next, int count)
    {
        ArrayList countrys = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select i_countryid, s_name, s_nationality, s_stdcode, i_status ");
        sb.append("FROM t_country where 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("and (s_name like ? OR s_nationality like ? or s_stdcode = ?) ");
       
        sb.append(" order by i_status, s_name ");
        if(count > 0)
            sb.append(" limit ").append(next*count).append(", ").append(count);
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("select count(1) FROM t_country where 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("and (s_name like ? OR s_nationality like ? or s_stdcode = ?) ");
        
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
                pstmt.setString(++scc, search);
            }
           
            logger.info("getCountryByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, nationality, code;
            int countryId, status;
            while (rs.next())
            {
                countryId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                nationality = rs.getString(3) != null ? rs.getString(3): "";
                code = rs.getString(4) != null ? rs.getString(4): "";
                status = rs.getInt(5);
                countrys.add(new CountryInfo(countryId, name, nationality, code, status, 0));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, search);
            }            
            print(this,"getCountryByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                countryId = rs.getInt(1);
                countrys.add(new CountryInfo(countryId, "", "", "", 0, 0));
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
        return countrys;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp)
    {
        ArrayList list = null;
        HashMap record = null;
        CountryInfo info;
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
                    info = (CountryInfo) l.get (i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            CountryInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (CountryInfo) l.get(i);
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
    public String getInfoValue(CountryInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getName() != null ? info.getName() : "";        
        return infoval;
    }
    public CountryInfo getCountryDetailById(int countryId)
    {
        CountryInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, s_nationality, s_stdcode, i_status FROM t_country where i_countryid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, countryId);
            //print(this,"getCountryDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, nationality, code;
            int status;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                nationality = rs.getString(2) != null ? rs.getString(2): "";
                code = rs.getString(3) != null ? rs.getString(3): "";
                status = rs.getInt(4);
                info = new CountryInfo(countryId, name, nationality, code, status, 0); 
            }
        }
        catch (Exception exception)
        {
            print(this,"getCountryDetailById :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public CountryInfo getCountryDetailByIdforDetail(int countryId)
    {
        CountryInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, s_nationality, s_stdcode, i_status FROM t_country where i_countryid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, countryId);
            print(this,"getCountryDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, nationality, code;
            int status = 0;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                nationality = rs.getString(2) != null ? rs.getString(2): "";
                code = rs.getString(3) != null ? rs.getString(3): "";
                status = rs.getInt(4); 
                info = new CountryInfo(countryId, name, nationality, code, status, 0); 
            }
            rs.close();            
        }
        catch (Exception exception)
        {
            print(this,"getCountryDetailByIdforDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int createCountry(CountryInfo info)
    {
        int countryId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_country ");
            sb.append("(s_name, s_nationality, s_stdcode, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName ()));
            pstmt.setString(++scc, (info.getNationality()));
            pstmt.setString(++scc, (info.getCode()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());  
            //print(this,"createCountry :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                countryId = rs.getInt(1);
            }
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"createCountry :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return countryId;
    }
    public int updateCountry(CountryInfo info)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_country set ");
            sb.append("s_name = ?, ");
            sb.append("s_nationality = ?, ");
            sb.append("s_stdcode = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_countryid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setString(++scc, info.getNationality());
            pstmt.setString(++scc, info.getCode());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getCountryId());
            //print(this,"updateCountry :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"updateCountry :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    public int checkDuplicacy(int countryId, String name, String nationality)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_countryid FROM t_country where (s_name = ? OR s_nationality = ?) and i_status in (1, 2)");
        if(countryId > 0)
            sb.append(" and i_countryid != ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;           
            pstmt.setString(++scc, name);
            pstmt.setString(++scc, nationality);
            if(countryId > 0)
                pstmt.setInt(++scc, countryId);
            //print(this,"checkDuplicacy :: " + pstmt.toString());
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
   
    public int deleteCountry(int countryId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_country set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_countryid = ? ");
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
            pstmt.setInt(++scc, countryId);
            
            cc = pstmt.executeUpdate();
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"deleteCountry :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 5, countryId); 
        return cc;
    } 
    
    public ArrayList getListForExcel(String search) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
         sb.append("select s_name, s_nationality, s_stdcode, i_status ");
        sb.append("FROM t_country where 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("and (s_name like ? OR s_nationality like ? or s_stdcode = ?) ");
        
        sb.append(" order by i_status, s_name "); 
        String query = sb.toString().intern();
        sb.setLength(0);        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
           if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, search);
            }
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
             String name, nationality, code;
            int status;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                nationality = rs.getString(2) != null ? rs.getString(2): "";
                code = rs.getString(3) != null ? rs.getString(3): "";
                status = rs.getInt(4);
                list.add(new CountryInfo(0, name, nationality, code, status, 0));
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
       String query = ("Select i_countryid, s_name, s_nationality, s_stdcode FROM t_country where i_status = 1 order by  s_name ");
        try
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id;
            String name, nationality, stdCode;
            JSONArray jarr = new JSONArray();
            while (rs.next())
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2): "";
                nationality = rs.getString(3) != null ? rs.getString(3): "";
                stdCode = rs.getString(4) != null ? rs.getString(4): "";
                JSONObject jobj = new JSONObject();
                jobj.put("id", id);
                jobj.put("name", name);
                jobj.put("nationality", nationality);
                jobj.put("ISD_code", stdCode);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "country.json");
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
      public Collection getCountrys()
    {
        Collection coll = new LinkedList();
        coll.add(new CountryInfo(-1, "- Select -","- Select -","", "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("country.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new CountryInfo(jobj.optInt("id"), jobj.optString("name"),jobj.optString("nationality"),jobj.optString("ISD_code"), (jobj.optString("name")+" (+" + jobj.optString("ISD_code")+")") ));
                }
            }
        }
        return coll;
    }
      
      public Collection getCountrysclient()
    {
        Collection coll = new LinkedList();
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("country.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new CountryInfo(jobj.optInt("id"), jobj.optString("name"),jobj.optString("nationality"),jobj.optString("ISD_code"), (jobj.optString("name")+" (+" + jobj.optString("ISD_code")+")") ));
                }
            }
        }
        return coll;
    }
}
