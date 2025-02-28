package com.web.jxp.hoursofwork;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class HoursOfWork extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getHoursOfWorkByName(String search, int next, int count)
    {
        ArrayList hoursofworks = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select i_hoursofworkid, i_noofhours, s_name, i_status ");
        sb.append("FROM t_hoursofwork where 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("and (s_name like ? OR i_noofhours = ?) ");
        sb.append(" order by i_status, s_name ");
        if(count > 0)
            sb.append(" limit ").append(next*count).append(", ").append(count);
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("select count(1) FROM t_hoursofwork where 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("and (s_name like ? OR i_noofhours = ?) ");
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
                pstmt.setString(++scc, search);
            }
            logger.log(Level.INFO, "getHoursOfWorkByName :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int hoursofworkId, status, noOfHours;
            while (rs.next())
            {
                hoursofworkId = rs.getInt(1);
                noOfHours = rs.getInt(2);
                name = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4);
                hoursofworks.add(new HoursOfWorkInfo(hoursofworkId, noOfHours, name, status, 0));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, search);
            }
            print(this,"getHoursOfWorkByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                hoursofworkId = rs.getInt(1);
                hoursofworks.add(new HoursOfWorkInfo(hoursofworkId, 0, "", 0, 0));
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
        return hoursofworks;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp)
    {
        ArrayList list = null;
        HashMap record = null;
        HoursOfWorkInfo info;
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
                    info = (HoursOfWorkInfo) l.get (i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            HoursOfWorkInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (HoursOfWorkInfo) l.get(i);
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
    public String getInfoValue(HoursOfWorkInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getName() != null ? info.getName() : "";        
        return infoval;
    }
    public HoursOfWorkInfo getHoursOfWorkDetailById(int hoursofworkId)
    {
        HoursOfWorkInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_noofhours, s_name, i_status FROM t_hoursofwork where i_hoursofworkid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, hoursofworkId);
            //print(this,"getHoursOfWorkDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status, noOfHours;
            while (rs.next())
            {
                noOfHours = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2): "";
                status = rs.getInt(3);
                info = new HoursOfWorkInfo(hoursofworkId, noOfHours, name, status, 0); 
            }
        }
        catch (Exception exception)
        {
            print(this,"getHoursOfWorkDetailById :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public HoursOfWorkInfo getHoursOfWorkDetailByIdforDetail(int hoursofworkId)
    {
        HoursOfWorkInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_noofhours, s_name, i_status FROM t_hoursofwork where i_hoursofworkid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, hoursofworkId);
            //print(this,"getHoursOfWorkDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status = 0, noOfHours;
            while (rs.next())
            {
                noOfHours = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2): "";
                status = rs.getInt(3);
                info = new HoursOfWorkInfo(hoursofworkId, noOfHours, name, status, 0); 
            }
            rs.close();            
        }
        catch (Exception exception)
        {
            print(this,"getHoursOfWorkDetailByIdforDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int createHoursOfWork(HoursOfWorkInfo info)
    {
        int hoursofworkId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_hoursofwork ");
            sb.append("(i_noofhours, s_name, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, info.getNoOfHours());
            pstmt.setString(++scc, info.getName ());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());  
            //print(this,"createHoursOfWork :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                hoursofworkId = rs.getInt(1);
            }
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"createHoursOfWork :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return hoursofworkId;
    }
    public int updateHoursOfWork(HoursOfWorkInfo info)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_hoursofwork set ");
            sb.append("i_noofhours = ?, ");
            sb.append("s_name = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_hoursofworkid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, info.getNoOfHours());
            pstmt.setString(++scc, info.getName());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getHoursofworkId());
            //print(this,"updateHoursOfWork :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"updateHoursOfWork :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    public int checkDuplicacy(int hoursofworkId,  int noOfHours)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_hoursofworkid FROM t_hoursofwork where (i_noofhours = ?) and i_status in (1, 2)");
        if(hoursofworkId > 0)
            sb.append(" and i_hoursofworkid != ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;           
           
            pstmt.setInt(++scc, noOfHours);
            if(hoursofworkId > 0)
                pstmt.setInt(++scc, hoursofworkId);
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
    
    public Collection getHoursOfWorks()
    {
        Collection coll = new LinkedList();
        coll.add(new HoursOfWorkInfo(-1, "- Select -", "Select HoursOfWork"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("hoursofwork.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new HoursOfWorkInfo(jobj.optInt("id"), jobj.optString("no_of_hours"),  jobj.optString("name")));
                }
            }
        }
        return coll;
    }
    
    public int deleteHoursOfWork(int hoursofworkId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_hoursofwork set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_hoursofworkid = ? ");
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
            pstmt.setInt(++scc, hoursofworkId);
            
            cc = pstmt.executeUpdate();
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"deleteHoursOfWork :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data updated", 22, hoursofworkId); 
        return cc;
    } 
    
    public ArrayList getListForExcel(String search) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
         sb.append("select i_noofhours, s_name, i_status ");
        sb.append("FROM t_hoursofwork where 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("and (s_name like ? OR i_noofhours = ?) ");
        
        sb.append(" order by i_status, s_name ");      
        String query = sb.toString().intern();
        sb.setLength(0);        
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
           if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, search);
            }
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status, noOfHours;
            while (rs.next())
            {
                noOfHours = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                list.add(new HoursOfWorkInfo(0, noOfHours, name, status, 0));
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
       String query = ("Select i_hoursofworkid, i_noofhours, s_name FROM t_hoursofwork where i_status = 1 order by  s_name ");
        try
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id, noOfHours;
            String name;
            JSONArray jarr = new JSONArray();
            while (rs.next())
            {
                id = rs.getInt(1);
                noOfHours = rs.getInt(2);
                name = rs.getString(3) != null ? rs.getString(3): "";
                JSONObject jobj = new JSONObject();
                jobj.put("id", id);
                jobj.put("no_of_hours", noOfHours);
                jobj.put("name", name);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "hoursofwork.json");
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
}
