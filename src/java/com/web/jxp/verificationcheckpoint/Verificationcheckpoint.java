package com.web.jxp.verificationcheckpoint;

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
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Verificationcheckpoint extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getVerificationcheckpointByName(String search, int statusIndex, int next, int count)
    {
        ArrayList verificationcheckpoints = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_verificationcheckpoint.i_verificationcheckpointid,  t_verificationcheckpoint.s_name, t_tab.s_name, ");
        sb.append("t_verificationcheckpoint.s_displaynotes, t_verificationcheckpoint.i_minverification, ");
        sb.append("t_verificationcheckpoint.i_status  FROM t_verificationcheckpoint ");
        sb.append("LEFT JOIN t_tab ON (t_tab.i_tab = t_verificationcheckpoint.i_tab)  ");
        sb.append("WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("AND (t_verificationcheckpoint.s_name LIKE ? OR t_verificationcheckpoint.s_displaynotes LIKE ? OR t_tab.s_name LIKE ? ) ");
        if(statusIndex > 0)
        {
            sb.append("AND t_verificationcheckpoint.i_status = ? ");
        }
        sb.append(" ORDER BY t_verificationcheckpoint.i_status, t_verificationcheckpoint.i_tab , t_verificationcheckpoint.s_name");
        if(count > 0)
            sb.append(" LIMIT ").append(next*count).append(", ").append(count);
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("SELECT COUNT(1) FROM t_verificationcheckpoint ");
        sb.append("LEFT JOIN t_tab ON (t_tab.i_tab = t_verificationcheckpoint.i_tab) ");
        sb.append("WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("AND (t_verificationcheckpoint.s_name LIKE ? OR t_verificationcheckpoint.s_displaynotes LIKE ?  OR t_tab.s_name LIKE ?) ");
        if(statusIndex > 0)
        {
            sb.append("AND t_verificationcheckpoint.i_status = ? ");
        }
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
            }
            if (statusIndex > 0)
                pstmt.setInt(++scc, statusIndex);
            logger.info("getVerificationcheckpointByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, displaynotes,tabname;
            int verificationcheckpointId, status, minverification;
            while (rs.next())
            {
                verificationcheckpointId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                tabname = rs.getString(3)!= null ? rs.getString(3) : "" ;
                displaynotes = rs.getString(4) != null ? rs.getString(4) : "";
                minverification = rs.getInt(5);
                status = rs.getInt(6);
                verificationcheckpoints.add(new VerificationcheckpointInfo(verificationcheckpointId, name,tabname,displaynotes, minverification,status, 0));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            if (statusIndex > 0)
                pstmt.setInt(++scc, statusIndex);
            print(this,"getVerificationcheckpointByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                verificationcheckpointId = rs.getInt(1);
                verificationcheckpoints.add(new VerificationcheckpointInfo(verificationcheckpointId, "","","",0, 0, 0));
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
        return verificationcheckpoints;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp)
    {
        ArrayList list = null;
        HashMap record = null;
        VerificationcheckpointInfo info;
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
                    info = (VerificationcheckpointInfo) l.get (i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            VerificationcheckpointInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (VerificationcheckpointInfo) l.get(i);
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
    public String getInfoValue(VerificationcheckpointInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getVerificationcheckpointName()!= null ? info.getVerificationcheckpointName(): "";
       if(i != null && i.equals("2"))
            infoval = info.getTabname()!= null ? info.getTabname(): "";
       if(i != null && i.equals("3"))
            infoval = info.getDisplaynote()!= null ? info.getDisplaynote(): "";
        return infoval;
    }
    
    public VerificationcheckpointInfo getVerificationcheckpointDetailById(int verificationcheckpointId)
    {
        VerificationcheckpointInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_tab, s_displaynotes, i_minverification, i_status FROM t_verificationcheckpoint where i_verificationcheckpointid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, verificationcheckpointId);
            print(this,"getVerificationcheckpointDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, displaynote;
            int status, tabid, minverification;
            while (rs.next())
            {
                name = rs.getString(1)!= null ? rs.getString(1) : "";
                tabid = rs.getInt(2);
                displaynote = rs.getString(3)!= null ? rs.getString(3) : "";
                minverification = rs.getInt(4);
                status = rs.getInt(5);
                
                info = new VerificationcheckpointInfo(verificationcheckpointId, name,tabid,displaynote,minverification, status);
            }
        }
        catch (Exception exception)
        {
            print(this,"getVerificationcheckpointDetailById :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public VerificationcheckpointInfo getVerificationcheckpointDetailByIdforDetail(int verificationcheckpointId)
    {
        VerificationcheckpointInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select t_verificationcheckpoint.s_name,t_tab.s_name , ");
        sb.append("t_verificationcheckpoint.s_displaynotes, t_verificationcheckpoint.i_minverification, ");
        sb.append("t_verificationcheckpoint.i_status FROM t_verificationcheckpoint ");
        sb.append("LEFT JOIN t_tab ON (t_tab.i_tab = t_verificationcheckpoint.i_tab) WHERE i_verificationcheckpointid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, verificationcheckpointId);
            print(this,"getVerificationcheckpointDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            
            String name, displaynotes,tabname;
            int  status, minverification;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                tabname = rs.getString(2) != null ? rs.getString(2) : "";
                displaynotes = rs.getString(3) != null ? rs.getString(3) : "";
                minverification = rs.getInt(4);
                status = rs.getInt(5); 
                info = new VerificationcheckpointInfo(verificationcheckpointId,name,tabname,displaynotes,minverification, status,0);
            }
            rs.close();            
        }
        catch (Exception exception)
        {
            print(this,"getVerificationcheckpointDetailByIdforDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int createVerificationcheckpoint(VerificationcheckpointInfo info)
    {
        int verificationcheckpointId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_verificationcheckpoint ");
            sb.append("(s_name, i_tab, i_minverification, s_displaynotes, i_status,  i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getVerificationcheckpointName()));
            pstmt.setInt(++scc, info.getTabid());
            pstmt.setInt(++scc, info.getMinverification());
            pstmt.setString(++scc, info.getDisplaynote());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());  
            print(this,"createVerificationcheckpoint :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                verificationcheckpointId = rs.getInt(1);
            }
        }
        catch (Exception exception)
        {
            print(this,"createVerificationcheckpoint :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return verificationcheckpointId;
    }
    
    public int updateVerificationcheckpoint(VerificationcheckpointInfo info)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_verificationcheckpoint set ");
            sb.append("s_name = ?, ");
            sb.append("i_tab = ?, ");
            sb.append("i_minverification = ?, ");
            sb.append("s_displaynotes = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ?  ");
            sb.append("where i_verificationcheckpointid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getVerificationcheckpointName());
            pstmt.setInt(++scc, info.getTabid());
            pstmt.setInt(++scc, info.getMinverification());
            pstmt.setString(++scc, info.getDisplaynote());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getVerificationcheckpointId());
            print(this,"updateVerificationcheckpoint :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"updateVerificationcheckpoint :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int checkDuplicacy(int verificationcheckpointId, String name,int tabid )
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_verificationcheckpointid FROM t_verificationcheckpoint where i_tab = ? and s_name = ? and i_status in (1, 2)");
        if(verificationcheckpointId > 0)
            sb.append(" and i_verificationcheckpointid != ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;        
            pstmt.setInt(++scc, tabid);
            pstmt.setString(++scc, name);
            if(verificationcheckpointId > 0)
                pstmt.setInt(++scc, verificationcheckpointId);
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
      
    public int deleteVerificationcheckpoint(int verificationcheckpointId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_verificationcheckpoint set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_verificationcheckpointid = ? ");
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
            pstmt.setInt(++scc, verificationcheckpointId);
            
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"deleteVerificationcheckpoint :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Updated", 15, verificationcheckpointId); 
        return cc;
    } 
    
    public ArrayList getExcel(String search ) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_verificationcheckpoint.i_verificationcheckpointid,  t_verificationcheckpoint.s_name,t_tab.s_name, "
                + " t_verificationcheckpoint.s_displaynotes, t_verificationcheckpoint.i_minverification,  "
                + " t_verificationcheckpoint.i_status  from t_verificationcheckpoint left join t_tab on (t_tab.i_tab = t_verificationcheckpoint.i_tab) ");
                
        sb.append("where 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("and (t_verificationcheckpoint.s_name like ? OR t_verificationcheckpoint.s_displaynotes like ? OR t_tab.s_name like ? ) ");
        
        sb.append(" order by t_verificationcheckpoint.i_status,  t_verificationcheckpoint.i_tab, t_verificationcheckpoint.s_name ");
        
        String query = sb.toString().intern();
        sb.setLength(0);        
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            
            int scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            rs = pstmt.executeQuery();
            String name, displaynotes,tabname;
            int verificationcheckpointId, status, minverification;
            while (rs.next()) 
            {
                verificationcheckpointId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                tabname = rs.getString(3) != null ? rs.getString(3) : "";
                displaynotes = rs.getString(4) != null ? rs.getString(4) : "";
                minverification = rs.getInt(5);
                status = rs.getInt(6);                
                list.add(new VerificationcheckpointInfo(verificationcheckpointId, name,tabname,displaynotes, minverification,status, 0));
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
       String query = ("Select i_verificationtypeid, i_verificationcheckpointid, s_name FROM t_verificationcheckpoint where i_status = 1 order by  s_name ");
      
        try
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int verificationtypeid;
            int verificationcheckpointid;
            String name;
            JSONArray jarr = new JSONArray();
            while (rs.next())
            {
                verificationtypeid = rs.getInt(1);
                verificationcheckpointid = rs.getInt(2);
                name = rs.getString(3) != null ? rs.getString(3) : "";
                JSONObject jobj = new JSONObject();
                jobj.put("verificationtypeid", verificationtypeid);
                jobj.put("verificationcheckpointid", verificationcheckpointid);
                jobj.put("name", name);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "verificationcheckpoint.json");
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
  
    public int getMaxIdverificationcode()
    {
        int id = 0;
        String query = "SELECT MAX(i_verificationcheckpointid) FROM t_verificationcheckpoint";
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                id = rs.getInt(1);
            }
            id = id + 1;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return id;
    }
}



