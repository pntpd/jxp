package com.web.jxp.experiencewaterdepth;

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

public class ExperienceWaterDepth extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getExperienceWaterDepthByName(String search, int next, int count)
    {
        ArrayList experiencewaterdepths = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select i_experiencewaterdepthid, s_unitmeasurement, i_depth, i_status ");
        sb.append("FROM t_experiencewaterdepth where 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("and (s_unitmeasurement like ? OR i_depth = ?) ");
        sb.append(" order by i_status, s_unitmeasurement ");
        if(count > 0)
            sb.append(" limit ").append(next*count).append(", ").append(count);
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("select count(1) FROM t_experiencewaterdepth where 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("and (s_unitmeasurement like ? OR i_depth = ?) ");
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
            logger.log(Level.INFO, "getExperienceWaterDepthByName :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String unitMeasurement, depth;
            int experiencewaterdepthId, status;
            while (rs.next())
            {
                experiencewaterdepthId = rs.getInt(1);
                unitMeasurement = rs.getString(2) != null ? rs.getString(2): "";
                depth = rs.getString(3) != null ? rs.getString(3): "";
                status = rs.getInt(4);
                experiencewaterdepths.add(new ExperienceWaterDepthInfo(experiencewaterdepthId, unitMeasurement, depth, status, 0));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, search);
            }
            print(this,"getExperienceWaterDepthByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                experiencewaterdepthId = rs.getInt(1);
                experiencewaterdepths.add(new ExperienceWaterDepthInfo(experiencewaterdepthId, "", "", 0, 0));
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
        return experiencewaterdepths;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp)
    {
        ArrayList list = null;
        HashMap record = null;
        ExperienceWaterDepthInfo info;
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
                    info = (ExperienceWaterDepthInfo) l.get (i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            ExperienceWaterDepthInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (ExperienceWaterDepthInfo) l.get(i);
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
    public String getInfoValue(ExperienceWaterDepthInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getUnitMeasurement() != null ? info.getUnitMeasurement() : "";        
        return infoval;
    }
    
    public ExperienceWaterDepthInfo getExperienceWaterDepthDetailById(int experiencewaterdepthId)
    {
        ExperienceWaterDepthInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_unitmeasurement, i_depth, i_status FROM t_experiencewaterdepth where i_experiencewaterdepthid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, experiencewaterdepthId);
            //print(this,"getExperienceWaterDepthDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String unitMeasurement,depth;
            int status;
            while (rs.next())
            {
                unitMeasurement = rs.getString(1) != null ? rs.getString(1): "";
                depth = rs.getString(2) != null ? rs.getString(2): "";
                status = rs.getInt(3);
                info = new ExperienceWaterDepthInfo(experiencewaterdepthId, unitMeasurement, depth, status, 0); 
            }
        }
        catch (Exception exception)
        {
            print(this,"getExperienceWaterDepthDetailById :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public ExperienceWaterDepthInfo getExperienceWaterDepthDetailByIdforDetail(int experiencewaterdepthId)
    {
        ExperienceWaterDepthInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_unitmeasurement, i_depth, i_status FROM t_experiencewaterdepth where i_experiencewaterdepthid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, experiencewaterdepthId);
            //print(this,"getExperienceWaterDepthDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String unitMeasurement, depth;
            int status = 0;
            while (rs.next())
            {
                unitMeasurement = rs.getString(1) != null ? rs.getString(1): "";
                depth = rs.getString(2) != null ? rs.getString(2): "";
                status = rs.getInt(3);
                info = new ExperienceWaterDepthInfo(experiencewaterdepthId, unitMeasurement, depth, status, 0);
            }
            rs.close();            
        }
        catch (Exception exception)
        {
            print(this,"getExperienceWaterDepthDetailByIdforDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int createExperienceWaterDepth(ExperienceWaterDepthInfo info)
    {
        int experiencewaterdepthId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_experiencewaterdepth ");
            sb.append("(s_unitmeasurement, i_depth, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, info.getUnitMeasurement());
            pstmt.setString(++scc, info.getDepth());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());  
            //print(this,"createExperienceWaterDepth :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                experiencewaterdepthId = rs.getInt(1);
            }
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"createExperienceWaterDepth :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return experiencewaterdepthId;
    }
    
    public int updateExperienceWaterDepth(ExperienceWaterDepthInfo info)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_experiencewaterdepth set ");
            sb.append("s_unitmeasurement = ?, ");
            sb.append("i_depth = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_experiencewaterdepthid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getUnitMeasurement());
            pstmt.setString(++scc, info.getDepth());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getExperiencewaterdepthId());
            //print(this,"updateExperienceWaterDepth :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"updateExperienceWaterDepth :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int checkDuplicacy(int experiencewaterdepthId, String unitMeasurement, String depth)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_experiencewaterdepthid FROM t_experiencewaterdepth where (s_unitmeasurement = ? AND i_depth = ?) and i_status in (1, 2)");
        if(experiencewaterdepthId > 0)
            sb.append(" and i_experiencewaterdepthid != ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, unitMeasurement);
            pstmt.setString(++scc, depth);
            
            if(experiencewaterdepthId > 0)
                pstmt.setInt(++scc, experiencewaterdepthId);
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
    
    public Collection getExperienceWaterDepths()
    {
        Collection coll = new LinkedList();
        coll.add(new ExperienceWaterDepthInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("experiencewaterdepth.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new ExperienceWaterDepthInfo(jobj.optInt("id"), jobj.optString("depth") +" "+ jobj.optString("unit_measurement")));
                }
            }
        }
        return coll;
    }
    
    public int deleteExperienceWaterDepth(int experiencewaterdepthId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_experiencewaterdepth set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_experiencewaterdepthid = ? ");
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
            pstmt.setInt(++scc, experiencewaterdepthId);
            
            cc = pstmt.executeUpdate();
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"deleteExperienceWaterDepth :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data updated", 30, experiencewaterdepthId); 
        return cc;
    } 
    
    public ArrayList getListForExcel(String search) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
         sb.append("select s_unitmeasurement, i_depth, i_status ");
        sb.append("FROM t_experiencewaterdepth where 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("and (s_unitmeasurement like ? OR i_depth = ?) ");
        sb.append(" order by i_status, s_unitmeasurement ");      
        String query = sb.toString().intern();
        sb.setLength(0);        
        try {
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
            String unitMeasurement, depth;
            int status;
            while (rs.next())
            {
                unitMeasurement = rs.getString(1) != null ? rs.getString(1) : "";
                depth = rs.getString(2) != null ? rs.getString(2): "";
                status = rs.getInt(3);
                list.add(new ExperienceWaterDepthInfo(0, unitMeasurement, depth, status, 0));
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
       String query = ("Select i_experiencewaterdepthid, i_depth, s_unitmeasurement FROM t_experiencewaterdepth where i_status = 1 order by  s_unitmeasurement ");
        try
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id;
            String unitMeasurement,depth;
            JSONArray jarr = new JSONArray();
            while (rs.next())
            {
                id = rs.getInt(1);
                unitMeasurement = rs.getString(2) != null ? rs.getString(2): "";
                depth = rs.getString(3) != null ? rs.getString(3): "";
                JSONObject jobj = new JSONObject();
                jobj.put("id", id);
                jobj.put("depth", depth);
                jobj.put("unit_measurement", unitMeasurement);               
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "experiencewaterdepth.json");
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
