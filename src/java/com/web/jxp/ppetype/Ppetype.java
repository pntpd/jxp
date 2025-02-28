package com.web.jxp.ppetype;

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

public class Ppetype extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getPpetypeByName(String search, int next, int count)
    {
        ArrayList ppetypes = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_ppetypeid, s_name, i_status, i_type, s_description FROM t_ppetype WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("AND (s_name LIKE ?) ");        
        sb.append("ORDER BY i_status, s_name ");
        if(count > 0)
            sb.append(" LIMIT ").append(next*count).append(", ").append(count);
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("SELECT COUNT(1) FROM t_ppetype WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("AND (s_name LIKE ?) ");
       
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
            }
            logger.info("getPpetypeByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description;
            int ppetypeId, status, typeId;
            while (rs.next())
            {
                ppetypeId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                typeId = rs.getInt(4);
                description = rs.getString(5) != null ? rs.getString(5) : "";
                ppetypes.add(new PpetypeInfo(ppetypeId, name, status, 0,typeId,description));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            //print(this,"getPpetypeByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                ppetypeId = rs.getInt(1);
                ppetypes.add(new PpetypeInfo(ppetypeId, "", 0, 0,0,""));
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
        return ppetypes;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp)
    {
        ArrayList list = null;
        HashMap record = null;
        PpetypeInfo info;
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
                    info = (PpetypeInfo) l.get (i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            PpetypeInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (PpetypeInfo) l.get(i);
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
    public String getInfoValue(PpetypeInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getName() != null ? info.getName() : "";        
        if(i != null && i.equals("2"))
            infoval = ""+info.getTypeId();        
        if(i != null && i.equals("3"))
            infoval = ""+info.getAddvalue() != null ? info.getAddvalue(): "";        
        return infoval;
    }
    
    public PpetypeInfo getPpetypeDetailById(int ppetypeId)
    {
        PpetypeInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_status, i_type, s_description FROM t_ppetype WHERE i_ppetypeid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ppetypeId);
            print(this,"getPpetypeDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description;
            int status, type;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                type = rs.getInt(3);
                description = rs.getString(4) != null ? rs.getString(4) : "";
                info = new PpetypeInfo(ppetypeId, name, status, 0,type,description); 
            }
        }
        catch (Exception exception)
        {
            print(this,"getPpetypeDetailById :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public PpetypeInfo getPpetypeDetailByIdforDetail(int ppetypeId)
    {
        PpetypeInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_status, i_type, s_description FROM t_ppetype WHERE i_ppetypeid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ppetypeId);
            print(this,"getPpetypeDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description;
            int status = 0, typeId;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2); 
                typeId = rs.getInt(3); 
                description = rs.getString(4) != null ? rs.getString(4) : "";
                info = new PpetypeInfo(ppetypeId, name, status, 0,typeId,description); 
            }
            rs.close();            
        }
        catch (Exception exception)
        {
            print(this,"getPpetypeDetailByIdforDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int createPpetype(PpetypeInfo info)
    {
        int ppetypeId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_ppetype ");
            sb.append("(s_name, i_type, s_description, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName ()));
            pstmt.setInt(++scc, info.getTypeId());
            pstmt.setString(++scc, (info.getAddvalue()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());  
            print(this,"createPpetype :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                ppetypeId = rs.getInt(1);
            }
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"createPpetype :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return ppetypeId;
    }
    
    public int updatePpetype(PpetypeInfo info)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_ppetype SET ");
            sb.append("s_name = ?, ");
            sb.append("i_type = ?, ");
            sb.append("s_description = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_ppetypeid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setInt(++scc, info.getTypeId());
            pstmt.setString(++scc, info.getAddvalue());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getPpetypeId());
            print(this,"updatePpetype :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"updatePpetype :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int checkDuplicacy(int ppetypeId, String name)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_ppetypeid FROM t_ppetype WHERE s_name = ? AND i_status IN (1, 2)");
        if(ppetypeId > 0)
            sb.append(" AND i_ppetypeid != ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;           
            pstmt.setString(++scc, name);
            if(ppetypeId > 0)
                pstmt.setInt(++scc, ppetypeId);
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
    
    public Collection getPpetypes()
    {
        Collection coll = new LinkedList();
        coll.add(new PpetypeInfo(-1, "Select PPE Type"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("ppetype.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new PpetypeInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
   
   public Collection getPpetypesforclientindex()
    {
        Collection coll = new LinkedList();
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("ppetype.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new PpetypeInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
    
    public int deletePpetype(int ppetypeId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_ppetype SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_ppetypeid = ? ");
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
            pstmt.setInt(++scc, ppetypeId);
            
            cc = pstmt.executeUpdate();
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"deletePpetype :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 97, ppetypeId); 
        return cc;
    } 
    
    public ArrayList getListForExcel(String search) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_ppetypeid, s_name, i_status, i_type, s_description FROM t_ppetype WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("AND (s_name LIKE ?) ");        
        sb.append("ORDER BY i_status, s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            logger.info("getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description;
            int ppetypeId, status, typeId;
            while (rs.next())
            {
                ppetypeId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                typeId = rs.getInt(4);
                description = rs.getString(5) != null ? rs.getString(5) : "";
                list.add(new PpetypeInfo(ppetypeId, name, status, 0,typeId,description));
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
     
    public void createjson(Connection conn)
    {
       String query = ("SELECT i_ppetypeid, s_name FROM t_ppetype WHERE i_status = 1 ORDER BY  s_name ");
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
                name = rs.getString(2) != null ? rs.getString(2) : "";
                JSONObject jobj = new JSONObject();
                jobj.put("id", id);
                jobj.put("name", name);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "ppetype.json");
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

