package com.web.jxp.benefit;

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

public class Benefit extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getBenefitByName(String search, int next, int count)
    {
        ArrayList benefits = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_benefit.i_benefitid,  t_benefit.s_name , t_benefit.i_status,  t_benefittype.s_name ");
        sb.append("FROM t_benefit ");
        sb.append("LEFT JOIN t_benefittype ON (t_benefit.i_type = t_benefittype.i_benefittypeid) ");
        sb.append("WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("AND (t_benefit.s_name LIKE ? OR t_benefittype.s_name LIKE ?) ");
        
        sb.append(" ORDER BY t_benefit.i_status,t_benefittype.s_name, t_benefit.s_name ");
        if(count > 0)
            sb.append(" limit ").append(next*count).append(", ").append(count);
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("SELECT COUNT(1) FROM t_benefit ");
        sb.append("LEFT JOIN t_benefittype ON (t_benefit.i_type = t_benefittype.i_benefittypeid) ");
        sb.append("WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("AND (t_benefit.s_name LIKE ? OR t_benefittype.s_name LIKE ?) ");
        
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
            }
            logger.info("getBenefitByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, benefittypeName;
            int benefitId, status;
            while (rs.next())
            {
                benefitId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                benefittypeName = rs.getString(4) != null ? rs.getString(4) : "";
                benefits.add(new BenefitInfo(benefitId, name, status, benefittypeName,  0));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            //print(this,"getBenefitByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                benefitId = rs.getInt(1);
                benefits.add(new BenefitInfo(benefitId, "", 0, 0));
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
        return benefits;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp)
    {
        ArrayList list = null;
        HashMap record = null;
        BenefitInfo info;
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
                    info = (BenefitInfo) l.get (i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            BenefitInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (BenefitInfo) l.get(i);
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
    public String getInfoValue(BenefitInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getBenefitName()!= null ? info.getBenefitName(): "";
        if(i !=null && i.equals("2"))
            infoval = info.getBenefittypeName()!= null ? info.getBenefittypeName() : "";        
        return infoval;
    }
    
    public BenefitInfo getBenefitDetailById(int benefitId)
    {
        BenefitInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_type, i_status FROM t_benefit WHERE i_benefitid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, benefitId);
            print(this,"getBenefitDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                int benefittypeId = rs.getInt(3);
                
                info = new BenefitInfo(benefitId, name, status, benefittypeId, 0);
            }
        }
        catch (Exception exception)
        {
            print(this,"getBenefitDetailById :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public BenefitInfo getBenefitDetailByIdforDetail(int benefitId)
    {
        BenefitInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_benefit.s_name,t_benefit.i_status, t_benefittype.s_name ");
        sb.append("FROM t_benefit ");
        sb.append("LEFT JOIN t_benefittype ON (t_benefit.i_type = t_benefittype.i_benefittypeid) WHERE i_benefitid = ?");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, benefitId);
            print(this,"getBenefitDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status = 0;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2); 
                String benefittypeName = rs.getString(3) != null ? rs.getString(3) : "";
                
                info = new BenefitInfo(benefitId, name, status, benefittypeName, 0);
            }
            rs.close();            
        }
        catch (Exception exception)
        {
            print(this,"getBenefitDetailByIdforDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int createBenefit(BenefitInfo info)
    {
        int benefitId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_benefit ");
            sb.append("(s_name, i_type, i_status,  i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getBenefitName()));
            pstmt.setInt(++scc, info.getBenefittypeId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());  
            print(this,"createBenefit :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                benefitId = rs.getInt(1);
            }
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"createBenefit :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return benefitId;
    }
    
    public int updateBenefit(BenefitInfo info)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_benefit SET ");
            sb.append("s_name = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ?, ");
            sb.append("i_type = ? ");
            sb.append("WHERE i_benefitid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getBenefitName());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getBenefittypeId());
            pstmt.setInt(++scc, info.getBenefitId());
            print(this,"updateBenefit :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"updateBenefit :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int checkDuplicacy(int benefitId, String name, int benefittypeId)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_benefitid FROM t_benefit WHERE i_type = ? AND s_name = ? AND i_status IN (1, 2)");
        if(benefitId > 0)
            sb.append(" AND i_benefitid != ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;        
            pstmt.setInt(++scc, benefittypeId);
            pstmt.setString(++scc, name);
            if(benefitId > 0)
                pstmt.setInt(++scc, benefitId);
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
    
    public Collection getBenefittype()
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_benefittypeid, s_name FROM t_benefittype WHERE i_status = 1 ORDER BY s_name").intern();
        coll.add(new BenefitInfo(-1, "-Select -"));
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
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new BenefitInfo(refId, refName));
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
    
    public int deleteBenefit(int benefitId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_benefit SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_benefitid = ? ");
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
            pstmt.setInt(++scc, benefitId);
            
            cc = pstmt.executeUpdate();
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"deleteBenefit :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Updated", 43, benefitId); 
        return cc;
    } 
    
    public ArrayList getExcel(String search ) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_benefit.i_benefitid,  t_benefit.s_name, t_benefit.i_status,  t_benefittype.s_name ");
        sb.append("FROM t_benefit ");
        sb.append("LEFT JOIN t_benefittype ON (t_benefit.i_type = t_benefittype.i_benefittypeid) ");
        sb.append("WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("AND (t_benefit.s_name LIKE ? OR t_benefittype.s_name LIKE ? ) ");
        
        sb.append(" ORDER BY t_benefit.i_status, t_benefittype.s_name, t_benefit.s_name  ");
        
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
            }
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                 int benefitId = rs.getInt(1);
                 String benefitname = rs.getString(2) != null ? rs.getString(2) : "";
                 int Status = rs.getInt(3);
                 String benefittypename = rs.getString(4) != null ? rs.getString(4) : "";
                 
                list.add(new BenefitInfo(benefitId, benefitname, Status, benefittypename));
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
        String query = ("SELECT i_type, i_benefitid, s_name FROM t_benefit WHERE i_status = 1 ORDER BY  s_name ");      
        try
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int benefittypeid;
            int benefitid;
            String name;
            JSONArray jarr = new JSONArray();
            while (rs.next())
            {
                benefittypeid = rs.getInt(1);
                benefitid = rs.getInt(2);
                name = rs.getString(3) != null ? rs.getString(3) : "";
                JSONObject jobj = new JSONObject();
                jobj.put("benefittypeid", benefittypeid);
                jobj.put("benefitid", benefitid);
                jobj.put("name", name);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "benefit.json");
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

    public Collection getbenefittype()
    {
        Collection coll = new LinkedList();
        coll.add(new BenefitInfo(-1, "Select Benefit"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("benefit.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new BenefitInfo(jobj.optInt("benefitId"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }

    public Collection Benefit()
    {
         Collection coll = new LinkedList();
        String query = ("SELECT i_benefitid, s_name FROM t_benefit WHERE i_status = 1 ORDER BY s_name").intern();
        coll.add(new BenefitInfo(-1, "--Select Benefit --"));
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
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new BenefitInfo(refId, refName));
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
    
    public Collection getBenefitbybenefittypeId(int benefittypeId)
    {
         Collection coll = new LinkedList();
        String query = ("SELECT i_benefitid, s_name FROM t_benefit WHERE i_status = 1 AND i_type = ? ORDER BY s_name").intern();
        coll.add(new BenefitInfo(-1, "--Select Benefit --"));
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,benefittypeId);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while(rs.next())
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new BenefitInfo(refId, refName));
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



