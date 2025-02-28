package com.web.jxp.wellnessfb;

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

public class Wellnessfb extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getWellnessfbByName(String search, int next, int count)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_categorywf.i_categorywfid, t_categorywf.s_name, t_categorywf.i_status , t1.ct , t2.ct ");
        sb.append(" FROM t_categorywf ");
        sb.append(" left join (select i_categorywfid, count(1) as ct from t_subcategorywf group by i_categorywfid) as t1 on (t_categorywf.i_categorywfid = t1.i_categorywfid) ");
        sb.append(" left join (select i_categorywfid, count(1) as ct from t_question group by i_categorywfid) as t2 on (t_categorywf.i_categorywfid = t2.i_categorywfid)  ");
        sb.append(" where 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("and (t_categorywf.s_name like ?) ");
        
        sb.append(" order by t_categorywf.i_status, t_categorywf.s_name ");
        if(count > 0)
            sb.append(" limit ").append(next*count).append(", ").append(count);
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("select count(1) FROM t_categorywf ");
        sb.append(" left join (select i_categorywfid, count(1) as ct from t_subcategorywf group by i_categorywfid) as t1 on (t_categorywf.i_categorywfid = t1.i_categorywfid) ");
        sb.append(" left join (select i_categorywfid, count(1) as ct from t_question group by i_categorywfid) as t2 on (t_categorywf.i_categorywfid = t2.i_categorywfid)  ");
        sb.append(" where 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("and (t_categorywf.s_name like ?) ");
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
            logger.log(Level.INFO, "getWellnessfbByName :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int categoryId, status, subcategorycount, questioncount;
            while (rs.next())
            {
                categoryId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                subcategorycount = rs.getInt(4);
                questioncount = rs.getInt(5);
                list.add(new WellnessfbInfo(categoryId, name, status, subcategorycount,questioncount));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            //print(this,"getWellnessfbByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                categoryId = rs.getInt(1);
                list.add(new WellnessfbInfo(categoryId, "", 0, 0,0));
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
        WellnessfbInfo info;
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
                    info = (WellnessfbInfo) l.get (i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map;
            if (colId.equals("1")) {
                map = sortByName(record, tp);
            } else {
                map = sortById(record, tp);
            }
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            WellnessfbInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (WellnessfbInfo) l.get(i);
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
    public String getInfoValue(WellnessfbInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getName() != null ? info.getName() : "";        
        if(i != null && i.equals("2"))
            infoval = info.getSubcategorycount()+"";        
        if(i != null && i.equals("3"))
            infoval = info.getQuestioncount()+"";        
        return infoval;
    }
    
    public WellnessfbInfo getWellnessfbDetailById(int categoryId)
    {
        WellnessfbInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_status, s_description FROM t_categorywf where i_categorywfid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            //print(this,"getWellnessfbDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description;
            int status;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2);
                description = rs.getString(3) != null ? rs.getString(3): "";
                info = new WellnessfbInfo(categoryId, name, status, description,0,0,0,"",""); 
            }
        }
        catch (Exception exception)
        {
            print(this,"getWellnessfbDetailById :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public WellnessfbInfo getWellnessfbDetailByIdforDetail(int categoryId)
    {
        WellnessfbInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_status, s_description FROM t_categorywf where i_categorywfid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            //print(this,"getWellnessfbDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description;
            int status = 0;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2); 
                description = rs.getString(3) != null ? rs.getString(3): "";
                info = new WellnessfbInfo(categoryId, name, status, description, 0, 0,0, "", ""); 
            }
            rs.close();            
        }
        catch (Exception exception)
        {
            print(this,"getWellnessfbDetailByIdforDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int createWellnessfb(WellnessfbInfo info)
    {
        int wellnessfbId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_categorywf ");
            sb.append("(s_name, s_description, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName ()));
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());  
            //print(this,"createWellnessfb :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                wellnessfbId = rs.getInt(1);
            }
        }
        catch (Exception exception)
        {
            print(this,"createWellnessfb :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return wellnessfbId;
    }
    public int updateWellnessfb(WellnessfbInfo info)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_categorywf set ");
            sb.append("s_name = ?, ");
            sb.append("s_description = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_categorywfid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getCategoryId());
            //print(this,"updateWellnessfb :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"updateWellnessfb :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    public int checkDuplicacy(int categoryId, String name)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_categorywfid FROM t_categorywf where s_name = ? and i_status in (1, 2)");
        if(categoryId > 0)
            sb.append(" and i_categorywfid != ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;           
            pstmt.setString(++scc, name);
            if(categoryId > 0)
                pstmt.setInt(++scc, categoryId);
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
    
    public Collection getWellnessfb()
    {
        Collection coll = new LinkedList();
        coll.add(new WellnessfbInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("wellnessfb.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new WellnessfbInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
    
    public int deleteWellnessfb(int wellnessfbId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_categorywf set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_categorywfid = ? ");
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
            pstmt.setInt(++scc, wellnessfbId);
            
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"deleteWellnessfb :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data updated", 60, wellnessfbId); 
        return cc;
    } 
    
    public ArrayList getListForExcel() 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
         sb.append("select t_categorywf.s_name,t_subcategorywf.s_name,t_question.s_question, t_subcategorywf.i_repeatdp,t_subcategorywf.s_schedulevalue, t_subcategorywf.s_notificationdp ");
        sb.append(" FROM t_question  ");
        sb.append(" left join t_subcategorywf on (t_question.i_subcategorywfid = t_subcategorywf.i_subcategorywfid) ");
        sb.append(" left join t_categorywf on (t_subcategorywf.i_categorywfid = t_categorywf.i_categorywfid)");
        sb.append(" where  t_question.i_status = 1 ");
        sb.append(" order by  t_categorywf.s_name , t_subcategorywf.s_name, t_question.s_question ");      
        String query = sb.toString().intern();
        sb.setLength(0);        
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String categoryname,subcategoryname,questionname, repeatvalue = "", schedulevalue, notificationdp;
            while (rs.next())
            {
                categoryname = rs.getString(1) != null ? rs.getString(1) : "";
                subcategoryname = rs.getString(2) != null ? rs.getString(2) : "";
                questionname = rs.getString(3) != null ? rs.getString(3) : "";
                repeatvalue = getrepeatvalue(rs.getInt(4)); 
                schedulevalue = rs.getString(5) != null ? rs.getString(5) : "";
                notificationdp = rs.getString(6) != null && !rs.getString(6).equals("") ? rs.getString(6) : "-";
                list.add(new WellnessfbInfo(categoryname,subcategoryname,questionname, repeatvalue, schedulevalue, notificationdp));
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
       String query = ("Select i_categorywfid, s_name FROM t_categorywf where i_status = 1 order by  s_name ");
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
            writeHTMLFile(str, filePath, "wellnessfb.json");
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
     
    public int getcategoryMaxId()
    {
        int id = 0;
        String query = "SELECT MAX(i_categorywfid) FROM t_categorywf";
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
    
    public ArrayList getsubcategoryDetailByIdforDetail(int categoryId)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_subcategorywf.s_name, t_subcategorywf.i_status, t_subcategorywf.s_description, t1.ct, t_subcategorywf.i_subcategorywfid, t_categorywf.i_status, i_repeatdp, s_notificationdp  FROM t_subcategorywf ");
        sb.append("left join (select i_subcategorywfid, count(1) as ct from t_question group by i_subcategorywfid) as t1 on (t_subcategorywf.i_subcategorywfid = t1.i_subcategorywfid) ");
        sb.append("left join t_categorywf on (t_subcategorywf.i_categorywfid = t_categorywf.i_categorywfid) ");
        sb.append(" where t_subcategorywf.i_categorywfid = ?  order by t_subcategorywf.i_status, t_subcategorywf.s_name ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            print(this,"getsubcategoryDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description, notification, repeatvalue;
            int status = 0,questioncount = 0, subcategoryId = 0, categorystatus = 0;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2); 
                description = rs.getString(3) != null ? rs.getString(3): "";
                questioncount = rs.getInt(4); 
                subcategoryId = rs.getInt(5); 
                categorystatus = rs.getInt(6); 
                repeatvalue = getrepeatvalue(rs.getInt(7)); 
                notification = rs.getString(8) != null && !rs.getString(8).equals("") ? rs.getString(8) : "-";
                
                list.add( new WellnessfbInfo(categoryId, name, status, description, questioncount, subcategoryId, categorystatus, repeatvalue, notification)); 
            }
            rs.close();            
        }
        catch (Exception exception)
        {
            print(this,"getsubcategoryDetailByIdforDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public String getrepeatvalue(int id)
    {
        String s1 = "";
        switch (id) 
        {
            case 1:
                s1 = "Daily";
                break;
            case 2:
                s1 = "Weekly";
                break;
            case 3:
                s1 = "Monthly";
                break;
            case 4:
                s1 = "Yearly";
                break;
            default:
                break;
        }
        return s1;
    }
    
    public int getsubcategoryMaxId()
    {
        int id = 0;
        String query = "SELECT MAX(i_subcategorywfid) FROM t_subcategorywf";
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
     
    public int checkDuplicacysubcategory(int categoryId, String name,int subcategoryId)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_subcategorywfid FROM t_subcategorywf where s_name = ? and i_categorywfid = ? and i_status in (1, 2)");
        if(subcategoryId > 0)
            sb.append(" and i_subcategorywfid != ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;           
            pstmt.setString(++scc, name);
            pstmt.setInt(++scc, categoryId);
            if(subcategoryId > 0)
                pstmt.setInt(++scc, subcategoryId);
            //print(this,"checkDuplicacysubcategory :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                ck = 1;
            }
        }
        catch (Exception exception)
        {
            print(this,"checkDuplicacysubcategory :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return ck;
    }
    
    public int createsubCategory(WellnessfbInfo info)
    {
        int categoryId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_subcategorywf ");
            sb.append("(s_name, s_description, i_categorywfid, i_repeatdp, s_notificationdp, s_schedulevalue, i_schedulecb, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName ()));
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, info.getCategoryId());
            pstmt.setInt(++scc, info.getRepeatdp());
            pstmt.setString(++scc, info.getNotification());
            pstmt.setString(++scc, info.getSchedulevalue());
            pstmt.setInt(++scc, info.getSchedulecb());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());  
            //print(this,"createWellnessfb :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                categoryId = rs.getInt(1);
            }
        }
        catch (Exception exception)
        {
            print(this,"createWellnessfb :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return categoryId;
    }
    
    public int updatesubCategory(WellnessfbInfo info)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_subcategorywf set ");
            sb.append("s_name = ?, ");
            sb.append("s_description = ?, ");
            sb.append("i_repeatdp = ?, ");
            sb.append("s_notificationdp = ?, ");
            sb.append("s_schedulevalue = ?, ");
            sb.append("i_schedulecb = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_categorywfid = ? and  i_subcategorywfid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, info.getRepeatdp());
            pstmt.setString(++scc, info.getNotification());
            pstmt.setString(++scc, info.getSchedulevalue());
            pstmt.setInt(++scc, info.getSchedulecb());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getCategoryId());
            pstmt.setInt(++scc, info.getSubcategoryId());
            //print(this,"updatesubCategory :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"updatesubCategory :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }    

    public Collection getSubcategory(int categoryId) 
    {
        Collection coll = new LinkedList();
        if (categoryId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_subcategorywfid, s_name FROM t_subcategorywf WHERE i_categorywfid = ? AND i_status = 1 ");
            sb.append("ORDER BY s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new WellnessfbInfo(-1, " Select Subcategory "));
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, categoryId);
                logger.info("getSubcategory :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) 
                {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2): "";
                    coll.add(new WellnessfbInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new WellnessfbInfo(-1, " Select Subcategory "));
        }
        return coll;
    }
    
    public int getquestionMaxId()
    {
        int id = 0;
        String query = "SELECT MAX(i_questionid) FROM t_question";
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
    
    public int createquestion(WellnessfbInfo info)
    {
        int categoryId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_question ");
            sb.append("(s_question, s_description, i_categorywfid,i_subcategorywfid, i_answertypeid, s_addvalues, ");
            sb.append(" s_assettypeids, s_responderids, i_recipientid, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getQuestion()));
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, info.getCategoryId());
            pstmt.setInt(++scc, info.getSubcategoryId());
            pstmt.setInt(++scc, info.getAnswertypeId());
            pstmt.setString(++scc, (info.getAddvalues()));
            pstmt.setString(++scc, (info.getAssettypeids()));
            pstmt.setString(++scc, (info.getResponderids()));
            pstmt.setInt(++scc, info.getRecipientId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());  
            //print(this,"createquestion :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                categoryId = rs.getInt(1);
            }
        }
        catch (Exception exception)
        {
            print(this,"createquestion :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return categoryId;
    }
    
    public int updatequestion(WellnessfbInfo info)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_question set ");
            sb.append("s_question = ?, ");
            sb.append("s_description = ?, ");
            sb.append("i_subcategorywfid = ?, ");
            sb.append("i_answertypeid = ?, ");
            sb.append("s_addvalues = ?, ");
            sb.append("s_assettypeids = ?, ");
            sb.append("s_responderids = ?, ");
            sb.append("i_recipientid = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_categorywfid = ? and i_questionid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, (info.getQuestion()));
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, info.getSubcategoryId());
            pstmt.setInt(++scc, info.getAnswertypeId());
            pstmt.setString(++scc, (info.getAddvalues()));
            pstmt.setString(++scc, (info.getAssettypeids()));
            pstmt.setString(++scc, (info.getResponderids()));
            pstmt.setInt(++scc, info.getRecipientId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getCategoryId());
            pstmt.setInt(++scc, info.getQuestionId());
            //print(this,"updatequestion :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"updatequestion :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public WellnessfbInfo getsubcategoryDetailById(int categoryId, int subcategoryId)
    {
        WellnessfbInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_subcategorywf.s_name, t_subcategorywf.i_status, t_subcategorywf.s_description, t_categorywf.s_name, i_repeatdp, s_notificationdp, s_schedulevalue, i_schedulecb FROM t_subcategorywf ");
        sb.append(" left join t_categorywf on (t_categorywf.i_categorywfid = t_subcategorywf.i_categorywfid ) ");
        sb.append(" where t_subcategorywf.i_categorywfid = ? and t_subcategorywf.i_subcategorywfid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            pstmt.setInt(2, subcategoryId);
            //print(this,"getWellnessfbDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description, categoryname, schedulevalue, notification;
            int status, repeatdp,  schedulecb;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2);
                description = rs.getString(3) != null ? rs.getString(3): "";
                categoryname = rs.getString(4) != null ? rs.getString(4): "";
                repeatdp = rs.getInt(5);
                notification = rs.getString(6) != null ? rs.getString(6): "";
                schedulevalue = rs.getString(7) != null ? rs.getString(7): "";
                schedulecb = rs.getInt(8);
                
                info = new WellnessfbInfo(categoryId, name, status, description,subcategoryId,categoryname, repeatdp, notification, schedulevalue, schedulecb); 
            }
        }
        catch (Exception exception)
        {
            print(this,"getWellnessfbDetailById :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public WellnessfbInfo getquestionDetailById(int questionId)
    {
        WellnessfbInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_question, s_description, i_answertypeid, s_addvalues, s_assettypeids, s_responderids, i_recipientid, i_status, i_categorywfid, i_subcategorywfid FROM t_question where  i_questionid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, questionId);
            //print(this,"getquestionDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String  description;
            int status, answertypeId, recipientId, categoryId, subcategoryId;
            String question, addvalues, assettypeids, responderids;
            while (rs.next())
            {
                question = rs.getString(1) != null ? rs.getString(1): "";
                description = rs.getString(2) != null ? rs.getString(2): "";
                answertypeId = rs.getInt(3);
                addvalues = rs.getString(4)  != null ? rs.getString(4): "";
                assettypeids = rs.getString(5) != null ? rs.getString(5): "";
                responderids = rs.getString(6) != null ? rs.getString(6): "";
                recipientId = rs.getInt(7);
                status = rs.getInt(8);
                categoryId = rs.getInt(9);
                subcategoryId = rs.getInt(10);
                info = new WellnessfbInfo(categoryId, subcategoryId, questionId, question,description, answertypeId, addvalues, assettypeids, responderids, recipientId, status); 
            }
        }
        catch (Exception exception)
        {
            print(this,"getquestionDetailById :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public ArrayList getAssettype()
    {
        ArrayList list = new ArrayList();
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("assettype.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    list.add(new WellnessfbInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return list;
    }   
    
    public ArrayList getquestionslistByIdandIndexforDetail(int categoryId, int subcategoryIdIndex)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_question.s_question, t_question.i_answertypeid, t_question.s_addvalues , t_question.s_responderids, ");
        sb.append(" t_question.i_recipientid, t_question.i_questionid, t_question.i_status, t_subcategorywf.i_status, t_categorywf.i_status FROM t_question ");
        sb.append(" left join t_categorywf on (t_categorywf.i_categorywfid = t_question.i_categorywfid) ");
        sb.append(" left join t_subcategorywf on (t_subcategorywf.i_subcategorywfid = t_question.i_subcategorywfid) ");
        sb.append(" where t_question.i_categorywfid = ?  ");
        if(subcategoryIdIndex > 0){
            sb.append(" and t_question.i_subcategorywfid = ?  ");
        }
        sb.append(" order by  t_question.i_status ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            if(subcategoryIdIndex > 0){
            pstmt.setInt(2, subcategoryIdIndex);
            }
            print(this,"getquestionslistByIdandIndexforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String question, addvalues, responderids;
            int status = 0, questionId = 0, answertypeId = 0, recipientId = 0, subcategorystatus = 0, categorystatus = 0;
            while (rs.next())
            {
                question = rs.getString(1) != null ? rs.getString(1): "";
                answertypeId = rs.getInt(2); 
                addvalues = rs.getString(3) != null ? rs.getString(3): "";
                responderids = rs.getString(4) != null ? rs.getString(4): "";
                recipientId = rs.getInt(5); 
                questionId = rs.getInt(6);
                status = rs.getInt(7); 
                subcategorystatus = rs.getInt(8); 
                categorystatus = rs.getInt(9); 
                String answertypevalue = "";
                if(answertypeId == 1)
                {
                    answertypevalue = getanswertypevalue(answertypeId);
                }
                else if(answertypeId == 2)
                {
                    answertypevalue = addvalues;
                }
                String recipientvalue = "";
                if(recipientId > 0)
                {
                    recipientvalue = getrecipientvalue(recipientId);
                }
                list.add( new WellnessfbInfo(categoryId, subcategoryIdIndex, question,answertypevalue, getrepondervalue(responderids),
                        recipientvalue, questionId, status, subcategorystatus, categorystatus)); 
            }
            rs.close();            
        }
        catch (Exception exception)
        {
            print(this,"getquestionslistByIdandIndexforDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getquestionslistBycategoryIdandIndexforDetail(int categoryId)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_question.s_question, t_question.i_answertypeid, t_question.s_addvalues , t_question.s_responderids, ");
        sb.append(" t_question.i_recipientid, t_question.i_questionid, t_question.i_status, t_question.i_subcategorywfid, t_subcategorywf.i_status, t_categorywf.i_status  FROM t_question ");
        sb.append(" left join t_categorywf on (t_categorywf.i_categorywfid = t_question.i_categorywfid) ");
        sb.append(" left join t_subcategorywf on (t_subcategorywf.i_subcategorywfid = t_question.i_subcategorywfid) ");
        sb.append(" where t_question.i_categorywfid = ?  ");
        sb.append(" order by  t_question.i_status ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            print(this,"getquestionslistBycategoryIdandIndexforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String question, addvalues, responderids;
            int status = 0, questionId = 0, answertypeId = 0, recipientId = 0, subcategoryId = 0, subcategorystatus = 0, categorystatus = 0;
            while (rs.next())
            {
                question = rs.getString(1) != null ? rs.getString(1): "";
                answertypeId = rs.getInt(2); 
                addvalues = rs.getString(3) != null ? rs.getString(3): "";
                responderids = rs.getString(4) != null ? rs.getString(4): "";
                recipientId = rs.getInt(5); 
                questionId = rs.getInt(6);
                status = rs.getInt(7); 
                subcategoryId = rs.getInt(8);
                subcategorystatus = rs.getInt(9);
                categorystatus = rs.getInt(10); 
                String answertypevalue = "";
                if(answertypeId == 1)
                {
                    answertypevalue = getanswertypevalue(answertypeId);
                }
                else if(answertypeId == 2)
                {
                    answertypevalue = addvalues;
                }
                String recipientvalue = "";
                if(recipientId > 0)
                {
                    recipientvalue = getrecipientvalue(recipientId);
                }
                list.add( new WellnessfbInfo(categoryId, subcategoryId, question,answertypevalue, getrepondervalue(responderids),
                        recipientvalue, questionId, status, subcategorystatus, categorystatus)); 
            }
            rs.close();            
        }
        catch (Exception exception)
        {
            print(this,"getquestionslistBycategoryIdandIndexforDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public String getanswertypevalue(int id)
    {
        String s1 = "";
        switch (id)
        {
            case 1:
                s1 = "Text Answer";
                break;
            default:
                break;
        }
        return s1;
    }
    
    public String getrecipientvalue(int id)
    {
        String s1 = "";
        switch (id) {
            case 1:
                s1 = "Crew Coordinator";
                break;
            default:
                break;
        }
        return s1;
    }
    
    public String getrepondervalue(String ids) 
    {
        String str = "";
        if (ids.contains("1")) 
        {
            if (str.equals("")) {
                str = "Crew";
            } else {
                str += ", Crew";
            }
        }
        if (ids.contains("2")) 
        {
            if (str.equals("")) {
                str = "Crew Coordinator";
            } else {
                str += ", Crew Coordinator";
            }
        }        
        return str;
    }
    
    public String getcategoryname(int categoryId)
    {
        String categoryname = "";
        String query = "SELECT s_name FROM t_categorywf where i_categorywfid = ?";
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                categoryname = rs.getString(1) != null ? rs.getString(1): "";
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return categoryname;
    }
    
    public int checkDuplicacyquestion(int categoryId, String question,int subcategoryId,int questionId)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_questionid FROM t_question where s_question = ? and i_categorywfid = ? and i_subcategorywfid = ? and i_status in (1, 2)");
        if(questionId > 0)
            sb.append(" and i_questionid != ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;           
            pstmt.setString(++scc, question);
            pstmt.setInt(++scc, categoryId);
            pstmt.setInt(++scc, subcategoryId);
            if(questionId > 0)
                pstmt.setInt(++scc, questionId);
            print(this,"checkDuplicacyquestion :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                ck = 1;
            }
        }
        catch (Exception exception)
        {
            print(this,"checkDuplicacyquestion :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return ck;
    }
    
    public int deletesubcategory(int subcategoryId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_subcategorywf set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_subcategorywfid = ? ");
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
            pstmt.setInt(++scc, subcategoryId);
            
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"deletesubcategory :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data updated", 60, subcategoryId); 
        return cc;
    }
    
    public int deletequestion(int questionId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_question set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_questionid = ? ");
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
            pstmt.setInt(++scc, questionId);
            
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"deletequestion :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data updated", 69, questionId); 
        return cc;
    }
    
    public WellnessfbInfo getcategorystatus(int categoryId)
    {
        WellnessfbInfo info = null;
        String query = "SELECT i_status FROM t_categorywf where i_categorywfid = ?";
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                int categorystatus = rs.getInt(1);
                info = new WellnessfbInfo(categorystatus,"");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
     
     public boolean checklistforday(ArrayList list, int day, int month)
     {
         boolean result = false;
         int list_size = list.size();
          for(int i = 0; i < list_size; i++)
            {
                WellnessfbInfo info = (WellnessfbInfo) list.get(i);
                if(info.getDay() == day && info.getMonth() == month)
                {
                    result = true;
                    break;
                }
            }
          return result;
     }
     
    public String getValFromList(ArrayList list)
    {
        int size = list.size();
        String s = "";
        for(int i = 0; i < size; i++)
        {
            WellnessfbInfo info = (WellnessfbInfo) list.get(i);
            if(info != null)
            {
                if(s.equals(""))
                    s = info.getMonth()+"-"+info.getDay();
                else
                    s += ","+info.getMonth()+"-"+info.getDay();
            }
        }
        return s;
    }
    
     public ArrayList getListfromVal(String s)
     {
         String year = "";
            ArrayList list = new ArrayList();
            if(s != null && !s.equals(""))
            {
                String arr[] = s.split(",");
                if(arr != null && arr.length > 0)
                {
                    for(int  i = 0; i < arr.length; i++)
                    {
                        String v = arr[i] != null ? arr[i] : "";
                        if(!v.equals(""))
                        {
                            String arr2[] = v.split("-");
                            if(arr2 != null && arr2.length > 0)
                            {
                                String month_s = arr2[0] != null ? arr2[0] : "";
                                String day_s = arr2[1] != null ? arr2[1] : "";
                                if(!month_s.equals("") && !day_s.equals(""))
                                {
                                    int month = Integer.parseInt(month_s);
                                    int day = Integer.parseInt(day_s);
                                    list.add(new WellnessfbInfo(month, day, day+"-"+month+year));
                                }
                            }
                        }
                    }
                }
            }
         return list;
     }
}