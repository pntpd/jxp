package com.web.jxp.createtraining;

import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import static com.web.jxp.common.Common.*;
import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Createtraining extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getCreatetrainingByName(String search, int next, int count)
    {
        ArrayList createtraining = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_category.i_categoryid, t_category.s_name, t_category.i_status , t1.ct , t2.ct ");
        sb.append(" FROM t_category ");
        sb.append(" LEFT JOIN (SELECT i_categoryid, COUNT(1) AS ct FROM t_subcategory WHERE i_status = 1 GROUP BY i_categoryid) AS t1 ON (t_category.i_categoryid = t1.i_categoryid) ");
        sb.append(" LEFT JOIN (SELECT i_categoryid, COUNT(1) AS ct FROM t_course WHERE i_status = 1 GROUP BY i_categoryid) AS t2 ON (t_category.i_categoryid = t2.i_categoryid)  ");
        sb.append(" WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("AND (t_category.s_name LIKE ?) ");
        sb.append(" ORDER BY t_category.i_status, t_category.s_name ");
        if(count > 0)
            sb.append(" limit ").append(next*count).append(", ").append(count);
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("SELECT COUNT(1) FROM t_category ");
        sb.append(" LEFT JOIN (SELECT i_categoryid, COUNT(1) AS ct FROM t_subcategory WHERE i_status = 1 GROUP BY i_categoryid) AS t1 ON (t_category.i_categoryid = t1.i_categoryid) ");
        sb.append(" LEFT JOIN (SELECT i_categoryid, COUNT(1) AS ct FROM t_course WHERE i_status = 1 GROUP BY i_categoryid) AS t2 ON (t_category.i_categoryid = t2.i_categoryid)  ");
        sb.append(" WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("AND (t_category.s_name LIKE ?) ");
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
            logger.log(Level.INFO, "getCreatetrainingByName :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int categoryId, status, subcategorycount, coursecount;
            while (rs.next())
            {
                categoryId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                subcategorycount = rs.getInt(4);
                coursecount = rs.getInt(5);
                createtraining.add(new CreatetrainingInfo(categoryId, name, status, subcategorycount,coursecount));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            print(this,"getCreatetrainingByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                categoryId = rs.getInt(1);
                createtraining.add(new CreatetrainingInfo(categoryId, "", 0, 0,0));
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
        return createtraining;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp)
    {
        ArrayList list = null;
        HashMap record = null;
        CreatetrainingInfo info;
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
                    info = (CreatetrainingInfo) l.get (i);
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
            CreatetrainingInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (CreatetrainingInfo) l.get(i);
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
    public String getInfoValue(CreatetrainingInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getName() != null ? info.getName() : "";        
        if(i != null && i.equals("2"))
            infoval = info.getSubcategorycount()+"";        
        if(i != null && i.equals("3"))
            infoval = info.getCoursecount()+"";        
        return infoval;
    }
    public CreatetrainingInfo getCreatetrainingDetailById(int categoryId)
    {
        CreatetrainingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_status, s_description FROM t_category WHERE i_categoryid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            //print(this,"getCreatetrainingDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description;
            int status;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2);
                description = rs.getString(3) != null ? rs.getString(3): "";
                info = new CreatetrainingInfo(categoryId, name, status, description,0,0); 
            }
        }
        catch (Exception exception)
        {
            print(this,"getCreatetrainingDetailById :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public CreatetrainingInfo getCreatetrainingDetailByIdforDetail(int categoryId)
    {
        CreatetrainingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_status, s_description FROM t_category WHERE i_categoryid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            //print(this,"getCreatetrainingDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description;
            int status = 0;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2); 
                description = rs.getString(3) != null ? rs.getString(3): "";
                info = new CreatetrainingInfo(categoryId, name, status, description, 0, 0); 
            }
            rs.close();            
        }
        catch (Exception exception)
        {
            print(this,"getCreatetrainingDetailByIdforDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int createCreatetraining(CreatetrainingInfo info)
    {
        int createtrainingId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_category ");
            sb.append("(s_name, s_description, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?)");
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
            //print(this,"createCreatetraining :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                createtrainingId = rs.getInt(1);
            }
        }
        catch (Exception exception)
        {
            print(this,"createCreatetraining :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return createtrainingId;
    }
    public int updateCreatetraining(CreatetrainingInfo info)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_category SET ");
            sb.append("s_name = ?, ");
            sb.append("s_description = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_categoryid = ? ");
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
            //print(this,"updateCreatetraining :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"updateCreatetraining :: " + exception.getMessage());
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
        sb.append("SELECT i_categoryid FROM t_category WHERE s_name = ? AND i_status IN (1, 2)");
        if(categoryId > 0)
            sb.append(" AND i_categoryid != ? ");
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
    
    public Collection getCreatetraining()
    {
        Collection coll = new LinkedList();
        coll.add(new CreatetrainingInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("createtraining.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new CreatetrainingInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
    
    public int deleteCreatetraining(int createtrainingId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_category SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_categoryid = ? ");
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
            pstmt.setInt(++scc, createtrainingId);
            
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"deleteCreatetraining :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data updated", 60, createtrainingId); 
        return cc;
    } 
    
    public ArrayList getListForExcel(String search) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_category.i_categoryid, t_category.s_name, t_category.i_status , t1.ct , t2.ct ");
        sb.append("FROM t_category ");
        sb.append("LEFT JOIN (SELECT i_categoryid, COUNT(1) AS ct FROM t_subcategory WHERE i_status = 1 GROUP BY i_categoryid) AS t1 ON (t_category.i_categoryid = t1.i_categoryid) ");
        sb.append("LEFT JOIN (SELECT i_categoryid, COUNT(1) AS ct FROM t_course WHERE i_status = 1 GROUP BY i_categoryid) AS t2 ON (t_category.i_categoryid = t2.i_categoryid)  ");
        sb.append("WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("AND (t_category.s_name LIKE ?) ");
        sb.append("ORDER BY t_category.i_status, t_category.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
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
            logger.log(Level.INFO, "getCreatetrainingByName :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int categoryId, status, subcategorycount, coursecount;
            while (rs.next())
            {
                categoryId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                subcategorycount = rs.getInt(4);
                coursecount = rs.getInt(5);
                list.add(new CreatetrainingInfo(categoryId, name, status, subcategorycount,coursecount));
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
       String query = ("SELECT i_categoryid, s_name FROM t_category WHERE i_status = 1 ORDER BY  s_name ");
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
            writeHTMLFile(str, filePath, "createtraining.json");
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
        String query = "SELECT MAX(i_categoryid) FROM t_category";
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
        sb.append("SELECT t_subcategory.s_name, t_subcategory.i_status, t_subcategory.s_description, t1.ct, t_subcategory.i_subcategoryid  FROM t_subcategory ");
        sb.append("LEFT JOIN (SELECT i_subcategoryid, COUNT(1) AS ct FROM t_course WHERE i_status = 1 GROUP BY i_subcategoryid) AS t1 ON (t_subcategory.i_subcategoryid = t1.i_subcategoryid) ");
        sb.append(" WHERE i_categoryid = ?  ORDER BY t_subcategory.i_status, t_subcategory.s_name ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            print(this,"getCreatetrainingDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description;
            int status = 0,coursecount = 0, subcategoryId = 0;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2); 
                description = rs.getString(3) != null ? rs.getString(3): "";
                coursecount = rs.getInt(4); 
                subcategoryId = rs.getInt(5); 
                list.add( new CreatetrainingInfo(categoryId, name, status, description, coursecount, subcategoryId)); 
            }
            rs.close();            
        }
        catch (Exception exception)
        {
            print(this,"getCreatetrainingDetailByIdforDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return list;
    }
     public int getsubcategoryMaxId()
    {
        int id = 0;
        String query = "SELECT MAX(i_subcategoryid) FROM t_subcategory";
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
        sb.append("SELECT i_subcategoryid FROM t_subcategory WHERE s_name = ? AND i_categoryid = ? AND i_status IN (1, 2)");
        if(subcategoryId > 0)
            sb.append(" AND i_subcategoryid != ? ");
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
     public int createsubCategory(CreatetrainingInfo info)
    {
        int categoryId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_subcategory ");
            sb.append("(s_name, s_description, i_categoryid, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName ()));
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, info.getCategoryId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());  
            //print(this,"createCreatetraining :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                categoryId = rs.getInt(1);
            }
        }
        catch (Exception exception)
        {
            print(this,"createCreatetraining :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return categoryId;
    }
    public int updatesubCategory(CreatetrainingInfo info)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_subcategory SET ");
            sb.append("s_name = ?, ");
            sb.append("s_description = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_categoryid = ? AND  i_subcategoryid = ? ");
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
    
    public ArrayList getcourseslistByIdforDetail(int categoryId)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_coursename.s_name, t_course.i_status, t_course.s_description, t_subcategory.i_subcategoryid,t_subcategory.s_name , ");
        sb.append(" t_coursetype.s_name, t_course.i_courseid, t1.ct, t_course.s_elearningfile ");
        sb.append("FROM t_course ");
        sb.append("LEFT JOIN t_subcategory ON (t_subcategory.i_subcategoryid = t_course.i_subcategoryid) ");
        sb.append("LEFT JOIN t_coursetype ON (t_course.i_coursetypeid = t_coursetype.i_coursetypeid) ");
        sb.append("LEFT JOIN t_coursename ON (t_coursename.i_coursenameid = t_course.i_coursenameid) ");
        sb.append("LEFT JOIN (SELECT i_courseid, COUNT(1) AS ct FROM t_courseattachment GROUP BY i_courseid) AS t1 ON (t1.i_courseid = t_course.i_courseid) ");
        sb.append("WHERE t_course.i_categoryid = ?  ORDER BY t_subcategory.s_name ,t_coursename.s_name ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            print(this,"getCreatetrainingDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String coursename, description, subcategoryname, coursetypename, elearningfile;
            int status = 0, subcategoryId = 0, courseId = 0, count=0;
            while (rs.next())
            {
                coursename = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2); 
                description = rs.getString(3) != null ? rs.getString(3): "";
                subcategoryId = rs.getInt(4); 
                subcategoryname = rs.getString(5) != null ? rs.getString(5): "";
                coursetypename = rs.getString(6) != null ? rs.getString(6): "";
                courseId = rs.getInt(7); 
                count = rs.getInt(8); 
                elearningfile = rs.getString(9) != null ? rs.getString(9): "";
                list.add( new CreatetrainingInfo(categoryId, coursename, status, description,
                        subcategoryname, coursetypename, subcategoryId, courseId, count, elearningfile)); 
            }
            rs.close();            
        }
        catch (Exception exception)
        {
            print(this,"getCreatetrainingDetailByIdforDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return list;
    }
    public Collection getSubcategory(int categoryId) {
        Collection coll = new LinkedList();
        if (categoryId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_subcategoryid, s_name FROM t_subcategory WHERE i_categoryid = ? AND i_status = 1 ");
            sb.append("ORDER BY s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new CreatetrainingInfo(-1, " Select Subcategory "));
            try {
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
                    coll.add(new CreatetrainingInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new CreatetrainingInfo(-1, " Select Subcategory "));
        }
        return coll;
    }
    
    public int getcourseMaxId()
    {
        int id = 0;
        String query = "SELECT MAX(i_courseid) FROM t_course";
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
    
    public int createcourse(CreatetrainingInfo info)
    {
        int categoryId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_course ");
            sb.append("(i_coursenameid, s_description, i_categoryid,i_subcategoryid, i_coursetypeid, s_elearningfile, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, (info.getCoursenameId()));
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, info.getCategoryId());
            pstmt.setInt(++scc, info.getSubcategoryId());
            pstmt.setInt(++scc, info.getCoursetypeId());
            pstmt.setString(++scc, info.getElearningfile());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());  
            print(this,"createcourse :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                categoryId = rs.getInt(1);
            }
        }
        catch (Exception exception)
        {
            print(this,"createcourse :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return categoryId;
    }
    public int updatecourse(CreatetrainingInfo info)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_course SET ");
            sb.append("i_coursenameid = ?, ");
            sb.append("s_description = ?, ");
            sb.append("i_subcategoryid = ?, ");
            sb.append("i_coursetypeid = ?, ");
            if (info.getElearningfile() != null && !info.getElearningfile().equals("")) {
                sb.append("s_elearningfile = ? ,");
            }
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_categoryid = ? AND i_courseid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, info.getCoursenameId());
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, info.getSubcategoryId());
            pstmt.setInt(++scc, info.getCoursetypeId());
            if (info.getElearningfile() != null && !info.getElearningfile().equals("")) {
                pstmt.setString(++scc, info.getElearningfile());
            }
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getCategoryId());
            pstmt.setInt(++scc, info.getCourseId());
            //print(this,"updatecourse :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"updatecourse :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int savecourseattachment(Connection conn, int courseId, String filename, int userId) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_courseattachment ");
            sb.append("(i_courseid, s_filename, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ? )");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, courseId);
            pstmt.setString(2, filename);
            pstmt.setInt(3, 1);
            pstmt.setInt(4, userId);
            pstmt.setString(5, currDate1());
            pstmt.setString(6, currDate1());
            //print(this,"createPic :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "createPic :: " + exception.getMessage());
        } finally {
            close(null, pstmt, rs);
        }
        return cc;
    }
    public ArrayList getcourseattachment(int courseId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_courseattachment.i_courseattachmentid, t_courseattachment.s_filename, DATE_FORMAT(t_courseattachment.ts_regdate, '%d-%b-%Y %H:%i'), ");
        sb.append("t_userlogin.s_name ");
        sb.append("FROM t_courseattachment LEFT JOIN t_userlogin ON (t_userlogin.i_userid = t_courseattachment.i_userid) ");
        sb.append("WHERE t_courseattachment.i_courseid = ? ");
        sb.append(" ORDER BY t_courseattachment.i_courseattachmentid ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, courseId);
            logger.info("getcourseattachment :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename, date, name;
            int courseattachmentId;
            while (rs.next()) 
            {
                courseattachmentId = rs.getInt(1);
                filename = rs.getString(2) != null ? rs.getString(2) : "";
                date = rs.getString(3) != null ? rs.getString(3) : "";
                name = rs.getString(4) != null ? rs.getString(4) : "";
                list.add(new CreatetrainingInfo(courseattachmentId, filename, date, name));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public int delcourseattachment(int courseattachmentId) {
        int cc = 0;
        String query = "SELECT s_filename FROM t_courseattachment WHERE i_courseattachmentid = ?";
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, courseattachmentId);
            logger.info("delcourseattachment :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename;
            while (rs.next()) 
            {
                filename = rs.getString(1) != null ? rs.getString(1) : "";
                if (!filename.equals("")) {
                    File f = new File(getMainPath("add_trainingfiles") + filename);
                    if (f.exists()) {
                        f.delete();
                    }
                }
            }
            rs.close();
            query = "delete FROM t_courseattachment WHERE i_courseattachmentid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, courseattachmentId);
            logger.info("delcourseattachment :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "delcourseattachment :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public CreatetrainingInfo getsubcategoryDetailById(int categoryId, int subcategoryId)
    {
        CreatetrainingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_subcategory.s_name, t_subcategory.i_status, t_subcategory.s_description, t_category.s_name ");
        sb.append("FROM t_subcategory ");
        sb.append("LEFT JOIN t_category ON (t_category.i_categoryid = t_subcategory.i_categoryid ) ");
        sb.append("WHERE t_subcategory.i_categoryid = ? AND t_subcategory.i_subcategoryid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            pstmt.setInt(2, subcategoryId);
            //print(this,"getCreatetrainingDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description, categoryname;
            int status ;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2);
                description = rs.getString(3) != null ? rs.getString(3): "";
                categoryname = rs.getString(4) != null ? rs.getString(4): "";                
                info = new CreatetrainingInfo(categoryId, name, status, description,0,subcategoryId,categoryname); 
            }
        }
        catch (Exception exception)
        {
            print(this,"getCreatetrainingDetailById :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public CreatetrainingInfo getcourseDetailById(int categoryId, int subcategoryId,int courseId)
    {
        CreatetrainingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_coursenameid, i_status, s_description, i_coursetypeid, s_elearningfile FROM t_course ");
        sb.append("WHERE i_categoryid = ? AND i_subcategoryid = ? AND i_courseid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            pstmt.setInt(2, subcategoryId);
            pstmt.setInt(3, courseId);
            print(this,"getcourseDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String  description, elearningfile;
            int status, coursetypeId, coursenameId;
            while (rs.next())
            {
                coursenameId = rs.getInt(1);
                status = rs.getInt(2);
                description = rs.getString(3) != null ? rs.getString(3): "";
                coursetypeId = rs.getInt(4);
                elearningfile = rs.getString(5) != null ? rs.getString(5): "";
                
                info = new CreatetrainingInfo(categoryId, coursenameId, status, description,courseId,
                subcategoryId,coursetypeId, elearningfile); 
            }
        }
        catch (Exception exception)
        {
            print(this,"getcourseDetailById :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
public Collection getCourseType()
    {
        Collection coll = new LinkedList();
        coll.add(new CreatetrainingInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("coursetype.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new CreatetrainingInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }

    public Collection getCoursename()
    {
        Collection coll = new LinkedList();
        coll.add(new CreatetrainingInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("coursename.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new CreatetrainingInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }    
    
    public ArrayList getcourseslistByIdandIndexforDetail(int categoryId, int subcategoryIdIndex)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_coursename.s_name, t_course.i_status, t_course.s_description, t_subcategory.i_subcategoryid,t_subcategory.s_name ,  ");
        sb.append(" t_coursetype.s_name, t_course.i_courseid, t1.ct, t_course.s_elearningfile FROM t_course ");
        sb.append("LEFT JOIN  t_subcategory  ON (t_subcategory.i_subcategoryid = t_course.i_subcategoryid) ");
        sb.append("LEFT JOIN  t_coursetype  ON (t_course.i_coursetypeid = t_coursetype.i_coursetypeid) ");
        sb.append("LEFT JOIN  t_coursename  ON (t_coursename.i_coursenameid = t_course.i_coursenameid) ");
        sb.append("LEFT JOIN (SELECT i_courseid, COUNT(1) AS ct FROM t_courseattachment GROUP BY i_courseid) AS t1 ON (t1.i_courseid = t_course.i_courseid) ");
        sb.append(" WHERE t_course.i_categoryid = ?  ");
        if(subcategoryIdIndex > 0){
            sb.append(" AND t_course.i_subcategoryid = ?  ");
        }
        sb.append(" ORDER BY  t_course.i_status, t_subcategory.s_name ,t_coursename.s_name ");
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
            print(this,"getCreatetrainingDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String coursename, description, subcategoryname, coursetypename, elearningfile;
            int status = 0, subcategoryId = 0, courseId = 0, count = 0;
            while (rs.next())
            {
                coursename = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2); 
                description = rs.getString(3) != null ? rs.getString(3): "";
                subcategoryId = rs.getInt(4); 
                subcategoryname = rs.getString(5) != null ? rs.getString(5): "";
                coursetypename = rs.getString(6) != null ? rs.getString(6): "";
                courseId = rs.getInt(7);
                count = rs.getInt(8); 
                elearningfile = rs.getString(9) != null ? rs.getString(9): "";
                list.add( new CreatetrainingInfo(categoryId, coursename, status, description,subcategoryname,
                coursetypename, subcategoryId,courseId,count, elearningfile)); 
            }
            rs.close();            
        }
        catch (Exception exception)
        {
            print(this,"getCreatetrainingDetailByIdforDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public String getcategoryname(int categoryId)
    {
        String categoryname = "";
        String query = "SELECT s_name FROM t_category WHERE i_categoryid = ?";
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
    public int checkDuplicacycourse(int categoryId, int coursenameId,int subcategoryId,int couresId)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_courseid FROM t_course WHERE i_coursenameid = ? AND i_categoryid = ? AND i_subcategoryid = ? AND i_status IN (1, 2)");
        if(couresId > 0)
            sb.append(" AND i_courseid != ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;           
            pstmt.setInt(++scc, coursenameId);
            pstmt.setInt(++scc, categoryId);
            pstmt.setInt(++scc, subcategoryId);
            if(couresId > 0)
                pstmt.setInt(++scc, couresId);
            print(this,"checkDuplicacycourse :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                ck = 1;
            }
        }
        catch (Exception exception)
        {
            print(this,"checkDuplicacycourse :: " + exception.getMessage());
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
            sb.append("UPDATE t_subcategory SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_subcategoryid = ? ");
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
    
    public int deletecourse(int courseId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_course SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_courseid = ? ");
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
            pstmt.setInt(++scc, courseId);
            
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"deletecourse :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data updated", 60, courseId); 
        return cc;
    }
    
    public void unzip(String zippath, String destinationpath) 
    {
        String command = "";
        if (getMainPath("run_place").equals("local")) {
            command = "powershell Expand-Archive -Path " + zippath + " -DestinationPath " + destinationpath;
        } else {
            command = "unzip " + zippath + " -d " + destinationpath;
        }
        logger.info("## " + command);
        try 
        {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(command);
            p.waitFor();
            File file = new File(zippath);
            if (file.exists()) 
            {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
