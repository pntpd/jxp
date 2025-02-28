package com.web.jxp.managetraining;
import com.web.jxp.base.Base;
import static com.web.jxp.base.Base.parseCommaDelimString;
import com.web.jxp.base.StatsInfo;
import com.web.jxp.base.Template;
import static com.web.jxp.common.Common.print;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;
import javax.mail.MessagingException;

public class Managetraining extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getManagetrainingByName(int clientIdIndex, int assetIdIndex, int positionIdIndex, int mode, String search,
        int catIdIndex, int subcatIdIndex)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        if(clientIdIndex > 0 && assetIdIndex > 0)
        {
            if(mode == 1)
            {
                sb.append("SELECT c.i_positionid, c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name,  ");
                sb.append("t1.ct, t2.ct FROM t_candidate AS c ");
                sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
                sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
                sb.append("LEFT JOIN (SELECT i_positionid, COUNT(1) AS ct FROM t_clientmatrixdetail LEFT JOIN t_category ON (t_category.i_categoryid = t_clientmatrixdetail.i_categoryid) WHERE t_category.i_status = 1 AND i_clientid = ? AND i_clientassetid = ? GROUP BY i_positionid) AS t1 ON (t_position.i_positionid = t1.i_positionid) ");
                sb.append("LEFT JOIN (SELECT i_positionid, i_candidateid, COUNT(1) AS ct FROM t_managetraining WHERE i_active = 1 AND i_clientassetid = ? AND i_courseid IN (SELECT i_courseid FROM t_clientmatrixdetail WHERE i_clientassetid = ? ) GROUP BY i_candidateid, i_positionid) AS t2 ON (t_position.i_positionid = t2.i_positionid AND c.i_candidateid = t2.i_candidateid) ");
                sb.append("WHERE c.i_status = 1 ");
                if(search != null && !search.equals(""))
                sb.append(" AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ?) ");  
                sb.append("AND c.i_clientid = ? ");
                sb.append("AND c.i_clientassetid = ? ");
                if(positionIdIndex > 0)
                    sb.append("AND c.i_positionid = ? ");                
                sb.append("ORDER BY c.s_firstname, c.s_middlename, c.s_lastname ");
            }
            else
            {
                sb.append("SELECT c.i_courseid, t_coursename.s_name, t_category.s_name, t_subcategory.s_name, ");
                sb.append("t1.ct FROM t_clientmatrixdetail AS c LEFT JOIN t_course ON (t_course.i_courseid = c.i_courseid) ");
                sb.append("LEFT JOIN t_coursename ON (t_coursename.i_coursenameid = t_course.i_coursenameid) ");
                sb.append("LEFT JOIN t_category ON (t_category.i_categoryid = c.i_categoryid) ");
                sb.append("LEFT JOIN t_subcategory ON (t_subcategory.i_subcategoryid = c.i_subcategoryid) ");
                sb.append("LEFT JOIN (SELECT i_courseid, COUNT(DISTINCT(i_positionid)) AS ct FROM t_clientmatrixdetail LEFT JOIN t_category ON (t_category.i_categoryid = t_clientmatrixdetail.i_categoryid) ");
                sb.append("WHERE t_category.i_status = 1 AND i_clientid = ? AND i_clientassetid = ?  AND (i_positionid IN (SELECT DISTINCT(i_positionid) FROM t_candidate WHERE i_clientassetid = ?) OR i_positionid IN (SELECT DISTINCT(i_positionid2) FROM t_candidate WHERE i_clientassetid = ?)) GROUP BY i_courseid) AS t1 ON (c.i_courseid = t1.i_courseid)");
                sb.append("WHERE t_course.i_status = 1 AND t_category.i_status = 1 AND t_subcategory.i_status = 1 ");
                sb.append(" AND c.i_clientid = ? ");
                sb.append(" AND c.i_clientassetid = ? ");
                if(search != null && !search.equals(""))
                    sb.append(" AND (t_coursename.s_name LIKE ? OR t_category.s_name LIKE ? OR t_subcategory.s_name LIKE ?) ");  
                if (catIdIndex > 0)
                    sb.append(" AND c.i_categoryid = ? ");
                if (subcatIdIndex > 0)
                    sb.append(" AND c.i_subcategoryid = ? ");
                sb.append(" GROUP BY c.i_courseid ORDER BY t_coursename.s_name ");
            }
            String query = (sb.toString()).intern();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                if(mode == 1)
                {
                    pstmt.setInt(++scc, clientIdIndex);
                    pstmt.setInt(++scc, assetIdIndex);
                    pstmt.setInt(++scc, clientIdIndex);                    
                    pstmt.setInt(++scc, assetIdIndex);
                    if(search != null && !search.equals(""))
                    {
                        pstmt.setString(++scc, "%"+(search)+"%");
                        pstmt.setString(++scc, "%"+(search)+"%");
                    }                    
                    pstmt.setInt(++scc, clientIdIndex);
                    pstmt.setInt(++scc, assetIdIndex);
                    if (positionIdIndex > 0)
                        pstmt.setInt(++scc, positionIdIndex);                    
                }
                else
                {
                    pstmt.setInt(++scc, clientIdIndex);
                    pstmt.setInt(++scc, assetIdIndex);
                    pstmt.setInt(++scc, assetIdIndex);
                    pstmt.setInt(++scc, assetIdIndex);
                    pstmt.setInt(++scc, clientIdIndex);
                    pstmt.setInt(++scc, assetIdIndex);
                    if(search != null && !search.equals(""))
                    {
                        pstmt.setString(++scc, "%"+(search)+"%");
                        pstmt.setString(++scc, "%"+(search)+"%");
                        pstmt.setString(++scc, "%"+(search)+"%");
                    }
                    if (catIdIndex > 0)
                        pstmt.setInt(++scc, catIdIndex);
                    if (subcatIdIndex > 0)
                        pstmt.setInt(++scc, subcatIdIndex);
                }
                logger.info("getManagetrainingByName :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String name, positionName, gradeName, cids="";
                int pcount, total, candidateId, positionId;
                double percent = 0;
                while (rs.next())
                {
                    if(mode == 1)
                    {
                        positionId = rs.getInt(1);
                        candidateId = rs.getInt(2);
                        name = rs.getString(3) != null ? rs.getString(3) : "";
                        positionName = rs.getString(4) != null ? rs.getString(4) : "";
                        gradeName = rs.getString(5) != null ? rs.getString(5) : "";
                        total = rs.getInt(6);
                        pcount = rs.getInt(7);
                        if(total > 0)
                            percent = (double)pcount / total * 100.0;
                        else
                            percent = 0;
                        if(!gradeName.equals(""))
                            positionName += " - " + gradeName;
                        list.add(new ManagetrainingInfo(positionId, candidateId, name, positionName, pcount, total, percent, 0, "", "", ""));
                    }
                    else
                    {
                        int courseId = rs.getInt(1);
                        String courseName = rs.getString(2) != null ? rs.getString(2) : "";
                        String categoryName = rs.getString(3) != null ? rs.getString(3) : "";
                        String subcategoryName = rs.getString(4) != null ? rs.getString(4) : "";
                        total = rs.getInt(5);
                        if(cids.equals(""))
                            cids = ""+courseId;
                        else
                            cids += ","+courseId;
                        list.add(new ManagetrainingInfo(0, 0, "", "", 0, total, 0, courseId, courseName, categoryName, subcategoryName));
                    }
                }
                rs.close();
                
                if(mode == 1)
                {
                    sb.append("SELECT c.i_positionid2, c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name,  ");
                    sb.append("t1.ct, t2.ct FROM t_candidate AS c ");
                    sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid2) ");
                    sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
                    sb.append("LEFT JOIN (SELECT i_positionid, COUNT(1) AS ct FROM t_clientmatrixdetail LEFT JOIN t_category ON (t_category.i_categoryid = t_clientmatrixdetail.i_categoryid) WHERE t_category.i_status = 1 AND i_clientid = ? AND i_clientassetid = ? GROUP BY i_positionid) AS t1 ON (t_position.i_positionid = t1.i_positionid) ");
                    sb.append("LEFT JOIN (SELECT i_positionid, i_candidateid, COUNT(1) AS ct FROM t_managetraining WHERE i_active = 1 AND i_clientassetid = ? AND i_courseid IN (SELECT i_courseid FROM t_clientmatrixdetail WHERE i_clientassetid = ? ) GROUP BY i_candidateid, i_positionid) AS t2 ON (t_position.i_positionid = t2.i_positionid AND c.i_candidateid = t2.i_candidateid) ");
                    sb.append("WHERE c.i_status = 1 AND c.i_positionid2 > 0 ");
                    if(search != null && !search.equals(""))
                        sb.append(" AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ?) ");  
                    if (clientIdIndex > 0)
                        sb.append("AND c.i_clientid = ? ");
                    if (assetIdIndex > 0)
                        sb.append("AND c.i_clientassetid = ? ");
                    if(positionIdIndex > 0)
                        sb.append("AND c.i_positionid2 = ? ");                
                    sb.append("ORDER BY c.s_firstname, c.s_middlename, c.s_lastname ");
                }
                query = (sb.toString()).intern();
                sb.setLength(0);
                
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                scc = 0;
                if(mode == 1)
                {
                    pstmt.setInt(++scc, clientIdIndex);
                    pstmt.setInt(++scc, assetIdIndex);
                    pstmt.setInt(++scc, clientIdIndex);
                    pstmt.setInt(++scc, assetIdIndex);
                    if(search != null && !search.equals(""))
                    {
                        pstmt.setString(++scc, "%"+(search)+"%");
                        pstmt.setString(++scc, "%"+(search)+"%");
                    }                    
                    pstmt.setInt(++scc, clientIdIndex);
                    pstmt.setInt(++scc, assetIdIndex);
                    if (positionIdIndex > 0)
                        pstmt.setInt(++scc, positionIdIndex);
                }                
                logger.info("getManagetrainingByName2 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    if(mode == 1)
                    {
                        positionId = rs.getInt(1);
                        candidateId = rs.getInt(2);
                        name = rs.getString(3) != null ? rs.getString(3) : "";
                        positionName = rs.getString(4) != null ? rs.getString(4) : "";
                        gradeName = rs.getString(5) != null ? rs.getString(5) : "";
                        total = rs.getInt(6);
                        pcount = rs.getInt(7);
                        if(total > 0)
                            percent = (double)pcount / total * 100.0;
                        else
                            percent = 0;
                        if(!gradeName.equals(""))
                            positionName += " - " + gradeName;
                        list.add(new ManagetrainingInfo(positionId, candidateId, name, positionName, pcount, total, percent, 0, "", "", ""));
                    }
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
        }
        return list;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp)
    {
        ArrayList list = null;
        HashMap record = null;
        ManagetrainingInfo info;
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
                    info = (ManagetrainingInfo) l.get (i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = null;
            if(colId.equals("3"))
                map = sortById(record, tp);
            else if(colId.equals("4"))
                map = sortByDouble(record, tp);
            else
                map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            ManagetrainingInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (ManagetrainingInfo) l.get(i);
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
    public String getInfoValue(ManagetrainingInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getName()!= null ? info.getName() : "";    
        else if(i != null && i.equals("2"))
            infoval = info.getPositionName()!= null ? info.getPositionName() : "";
        else if(i != null && i.equals("3"))
            infoval = ""+info.getPcount(); 
        else if(i != null && i.equals("4"))
            infoval = ""+info.getPercent(); 
        return infoval;
    }
    
    public Collection getClients(String cids, int allclient, String permission) {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_clientid, s_name FROM t_client WHERE i_status = 1 ");
        if (allclient == 1) {
            sb.append("AND i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND i_clientid < 0 ");
            }
        }

        sb.append("order by s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new ManagetrainingInfo(-1, " Select Client"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new ManagetrainingInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getClientAsset(int clientId, String assetids, int allclient, String permission) 
    {
        Collection coll = new LinkedList();
        if (clientId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_clientid = ? AND i_status = 1 ");
            if (allclient == 1) {
                sb.append("AND i_clientassetid > 0 ");
            } else {
                if (!assetids.equals("")) {
                    sb.append("AND i_clientassetid IN (" + assetids + ") ");
                } else {
                    sb.append("AND i_clientassetid < 0 ");
                }
            }
            sb.append("ORDER BY s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new ManagetrainingInfo(-1, " Select Asset "));
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                logger.info("getClientAsset :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) 
                {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new ManagetrainingInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new ManagetrainingInfo(-1, " Select Asset "));
        }
        return coll;
    }
    
    public Collection getPositions(int clientassetId)
    {
        Collection coll = new LinkedList();
        coll.add(new ManagetrainingInfo(-1, " All "));
        if(clientassetId > 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_position.i_positionid, t_position.s_name, t_grade.s_name ");
            sb.append("FROM t_clientassetposition left join t_position on (t_position.i_positionid = t_clientassetposition.i_positionid) ");
            sb.append("left join t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("where t_position.i_status = 1 and t_clientassetposition.i_clientassetid = ? order by t_position.s_name, t_grade.s_name ");
            String query = sb.toString();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                logger.info("getPositions :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int id;
                String name, gradeName;
                while (rs.next())
                {
                    id = rs.getInt(1);  
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    gradeName = rs.getString(3) != null ? rs.getString(3) : "";
                    if(!gradeName.equals(""))
                        name += " - " + gradeName;
                    coll.add(new ManagetrainingInfo(id, name));
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
        }
        return coll;
    }
    
    public Collection getCategories(int clientassetId)
    {
        Collection coll = new LinkedList();
        coll.add(new ManagetrainingInfo(-1, "Select Category"));
        if(clientassetId > 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT DISTINCT c.i_categoryid, t_category.s_name ");
            sb.append("FROM t_clientmatrixdetail as c left join t_category on (t_category.i_categoryid = c.i_categoryid) ");
            sb.append("where t_category.i_status = 1 and c.i_clientassetid = ? order by t_category.s_name");
            String query = sb.toString();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                logger.info("getCategories :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int id;
                String name;
                while (rs.next())
                {
                    id = rs.getInt(1);  
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new ManagetrainingInfo(id, name));
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
        }
        return coll;
    }
    
    public Collection getSubCategories(int clientassetId, int categoryId)
    {
        Collection coll = new LinkedList();
        coll.add(new ManagetrainingInfo(-1, "Select Sub-category"));
        if(clientassetId > 0 && categoryId > 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT DISTINCT c.i_subcategoryid, t_subcategory.s_name ");
            sb.append("FROM t_clientmatrixdetail as c left join t_subcategory on (t_subcategory.i_subcategoryid = c.i_subcategoryid) ");
            sb.append("where t_subcategory.i_status = 1 and c.i_clientassetid = ? and c.i_categoryid = ? order by t_subcategory.s_name");
            String query = sb.toString();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                pstmt.setInt(2, categoryId);
                logger.info("getSubCategories :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int id;
                String name;
                while (rs.next())
                {
                    id = rs.getInt(1);  
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new ManagetrainingInfo(id, name));
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
        }
        return coll;
    }
    
    public ArrayList getFinalRecord2(ArrayList l, String colId, int tp)
    {
        ArrayList list = null;
        HashMap record = null;
        ManagetrainingInfo info;
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
                    info = (ManagetrainingInfo) l.get (i);
                    record.put(getInfoValue2(info, colId), info);
                }
            }
            Map map = null;
            if(colId.equals("4"))
                map = sortById(record, tp);
            else
                map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            ManagetrainingInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (ManagetrainingInfo) l.get(i);
                    String str = getInfoValue2(rInfo, colId);
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
    public String getInfoValue2(ManagetrainingInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getCourseName() != null ? info.getCourseName() : "";
        else if(i != null && i.equals("2"))
            infoval = info.getCategoryName()!= null ? info.getCategoryName() : "";    
        else if(i != null && i.equals("3"))
            infoval = info.getSubcategoryName()!= null ? info.getSubcategoryName() : "";
        else if(i != null && i.equals("4"))
            infoval = ""+info.getTotal(); 
        return infoval;
    }
    
    public int[] getCounts(int assetIdIndex, int tp)
    {
        int arr[] = new int[11];
        int completed_count = 0, pending_count = 0, total_count = 0, unassigned = 0, expired1 = 0, expired2 = 0, 
            expired3 = 0, expired = 0, total_positions = 0, total_candidate = 0, total_courses = 0;
        if(assetIdIndex > 0)
        {            
            String query = ("SELECT count(1) as ct from t_managetraining where i_active = 1 and i_clientassetid = ? and i_status = 3 and i_courseid IN (select i_courseid from t_clientmatrixdetail where i_clientassetid = ?) "); //completed
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                pstmt.setInt(2, assetIdIndex);
                logger.info("completed_count :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    completed_count = rs.getInt(1);                    
                }
                rs.close();
                
                query = ("SELECT count(1) as ct from t_managetraining where i_active = 1 and i_clientassetid = ? and i_status = 2"); //pending
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("pending_count :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    pending_count = rs.getInt(1);                    
                }
                rs.close();
                
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT SUM(t1.ct) from t_candidate as c ");
                sb.append("left join t_position on (t_position.i_positionid = c.i_positionid) ");
                sb.append("left join (select i_positionid, count(1) as ct from t_clientmatrixdetail left join t_category on (t_category.i_categoryid = t_clientmatrixdetail.i_categoryid) where t_category.i_status = 1 and i_clientassetid = ? group by i_positionid) as t1 on (t_position.i_positionid = t1.i_positionid) ");
                sb.append("WHERE c.i_status = 1 AND c.i_clientassetid = ? ");
                
                query = sb.toString();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                pstmt.setInt(2, assetIdIndex);
                logger.info("total_count :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    total_count = rs.getInt(1);                    
                }
                rs.close();
                
                sb.append("SELECT SUM(t1.ct) from t_candidate as c ");
                sb.append("left join t_position on (t_position.i_positionid = c.i_positionid2) ");
                sb.append("left join (select i_positionid, count(1) as ct from t_clientmatrixdetail left join t_category on (t_category.i_categoryid = t_clientmatrixdetail.i_categoryid) where t_category.i_status = 1 and i_clientassetid = ? group by i_positionid) as t1 on (t_position.i_positionid = t1.i_positionid) ");
                sb.append("WHERE c.i_status = 1 AND c.i_clientassetid = ? ");
                
                query = sb.toString();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                pstmt.setInt(2, assetIdIndex);
                logger.info("total_count2 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    total_count += rs.getInt(1);                    
                }
                rs.close();
                
                query = ("SELECT count(1) as ct from t_managetraining where i_active = 1 and i_clientassetid = ? and i_lifetime = 0 and i_status = 3 and (d_enddate >= current_date() and d_enddate <= current_date() + INTERVAL '45' DAY) "); //expire in 45 days
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("expired1 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    expired1 = rs.getInt(1);                    
                }
                rs.close();
                
                query = ("SELECT count(1) as ct from t_managetraining where i_active = 1 and i_clientassetid = ? and i_lifetime = 0 and i_status = 3 and (d_enddate > current_date() + INTERVAL '45' DAY and d_enddate <= current_date() + INTERVAL '65' DAY) "); //expire in 45 - 65 days
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("expired2 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    expired2 = rs.getInt(1);                    
                }
                rs.close();
                query = ("SELECT count(1) as ct from t_managetraining where i_active = 1 and i_clientassetid = ? and i_lifetime = 0 and i_status = 3 and (d_enddate > current_date() + INTERVAL '65' DAY and d_enddate <= current_date() + INTERVAL '90' DAY) "); //expire in 65 - 90 days
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("expired3 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    expired3 = rs.getInt(1);                    
                }
                rs.close();
                
                query = ("SELECT count(1) as ct from t_managetraining where i_active = 1 and i_clientassetid = ? and i_lifetime = 0 and i_status = 3 and current_date() > d_enddate "); //expired
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("expired :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    expired = rs.getInt(1);                    
                }
                rs.close();
                
                if(tp == 1)
                {
                    query = ("SELECT count(DISTINCT(i_positionid)) as ct from t_candidate where i_status = 1 and i_positionid > 0 and i_clientassetid = ? "); //total_positions
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, assetIdIndex);
                    logger.info("total_positions :: " + pstmt.toString());
                    rs = pstmt.executeQuery();
                    while (rs.next())
                    {
                        total_positions = rs.getInt(1);                    
                    }
                    rs.close();
                    
                    query = ("SELECT count(DISTINCT(i_positionid2)) as ct from t_candidate where i_status = 1 and i_positionid2 > 0 and i_clientassetid = ? and i_positionid2 NOT IN (select i_positionid as ct from t_candidate where i_status = 1 and i_positionid > 0 and i_clientassetid = ?) "); //total_positions
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, assetIdIndex);
                    pstmt.setInt(2, assetIdIndex);
                    logger.info("total_positions :: " + pstmt.toString());
                    rs = pstmt.executeQuery();
                    while (rs.next())
                    {
                        total_positions += rs.getInt(1);                    
                    }
                    rs.close();
                    
                    query = ("SELECT COUNT(1) AS ct from t_candidate WHERE i_status = 1 AND i_clientassetid = ? "); //total_candidate
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, assetIdIndex);
                    logger.info("total_candidate :: " + pstmt.toString());
                    rs = pstmt.executeQuery();
                    while (rs.next())
                    {
                        total_candidate = rs.getInt(1);                    
                    }
                    rs.close();
                    StringBuilder sbquery = new StringBuilder();
                    sbquery.append(" SELECT count(DISTINCT(i_courseid)) as ct from t_clientmatrixdetail  left join t_category on (t_category.i_categoryid = t_clientmatrixdetail.i_categoryid)  ");
                    sbquery.append("  where t_category.i_status = 1 and i_clientassetid = ? ");
                    query = sbquery.toString(); //total_courses
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, assetIdIndex);
                    logger.info("total_courses :: " + pstmt.toString());
                    rs = pstmt.executeQuery();
                    while (rs.next())
                    {
                        total_courses = rs.getInt(1);                    
                    }
                    rs.close();
                }
                
                unassigned = total_count - pending_count - completed_count;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                close(conn, pstmt, rs);
            }
        }
        arr[0] = completed_count;
        arr[1] = pending_count;
        arr[2] = unassigned;
        arr[3] = expired1;
        arr[4] = expired2;
        arr[5] = expired3;
        arr[6] = expired;
        arr[7] = total_count;  
        arr[8] = total_positions; 
        arr[9] = total_candidate; 
        arr[10] = total_courses; 
        return arr;
    }
    
    public ManagetrainingInfo getBasicDetail(int candidateId)
    {
        ManagetrainingInfo info = null;
        if(candidateId > 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name, ");
            sb.append("t_client.s_name, t_clientasset.s_name, c.i_clientid, c.i_clientassetid, c.i_positionid, ");
            sb.append("c.i_positionid2, p2.s_name, g2.s_name ");
            sb.append("FROM t_candidate AS c ");
            sb.append("LEFT JOIN t_client ON (t_client.i_clientid = c.i_clientid) ");
            sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = c.i_clientassetid) ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("LEFT JOIN t_position AS p2 ON (p2.i_positionid = c.i_positionid2) ");
            sb.append("LEFT JOIN t_grade AS g2 ON (g2.i_gradeid = p2.i_gradeid) ");
            sb.append("WHERE c.i_candidateid = ?");
            String query = sb.toString();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, candidateId);
                logger.info("getBasicDetail :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String name, positionName, gradeName, clientName, assetName, positionName2, gradeName2;
                int clientId, clientassetId, positionId,positionId2;
                while (rs.next())
                {
                    name = rs.getString(1) != null ? rs.getString(1) : "";
                    positionName = rs.getString(2) != null ? rs.getString(2) : "";
                    gradeName = rs.getString(3) != null ? rs.getString(3) : "";
                    clientName = rs.getString(4) != null ? rs.getString(4) : "";
                    assetName = rs.getString(5) != null ? rs.getString(5) : "";
                    clientId = rs.getInt(6);
                    clientassetId = rs.getInt(7);
                    positionId = rs.getInt(8);
                    positionId2 = rs.getInt(9);
                    positionName2 = rs.getString(10) != null ? rs.getString(10) : "";
                    gradeName2 = rs.getString(11) != null ? rs.getString(11) : "";
                    if(!gradeName.equals(""))
                        positionName += " - " + gradeName;
                    if(!gradeName2.equals(""))
                        positionName2 += " - " + gradeName2;
                    info = new ManagetrainingInfo(candidateId, clientId, clientassetId, name, positionName, 
                        clientName, assetName, positionId, positionId2, positionName2);
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
        }
        return info;
    }
    
    public ManagetrainingInfo getCourseDetail(int courseId, int clientassetId)
    {
        ManagetrainingInfo info = null;
        if(courseId > 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_coursename.s_name, t_category.s_name, t_subcategory.s_name, t_course.i_categoryid, t_course.i_subcategoryid ");
            sb.append("FROM t_course left join t_coursename on (t_coursename.i_coursenameid = t_course.i_coursenameid) ");
            sb.append("left join t_category on (t_category.i_categoryid = t_course.i_categoryid) ");
            sb.append("left join t_subcategory on (t_subcategory.i_subcategoryid = t_course.i_subcategoryid) ");
            sb.append("where t_course.i_courseid = ?");
            String query = sb.toString();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, courseId);
                logger.info("getCourseDetail :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String name = "", categoryName = "", subcategoryName = "", clientName = "", assetName = "";
                int categoryId = 0, subcategoryId = 0;
                while (rs.next())
                {
                    name = rs.getString(1) != null ? rs.getString(1) : "";
                    categoryName = rs.getString(2) != null ? rs.getString(2) : "";
                    subcategoryName = rs.getString(3) != null ? rs.getString(3) : "";
                    categoryId = rs.getInt(4);
                    subcategoryId = rs.getInt(5);
                }
                rs.close();
                sb.append("SELECT t_client.s_name, t_clientasset.s_name ");
                sb.append("FROM t_clientasset left join t_client on (t_client.i_clientid = t_clientasset.i_clientid) ");
                sb.append("where t_clientasset.i_clientassetid = ?");
                query = sb.toString();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                logger.info("getBasicDetail :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    clientName = rs.getString(1) != null ? rs.getString(1) : "";
                    assetName = rs.getString(2) != null ? rs.getString(2) : "";
                }
                info = new ManagetrainingInfo(name, categoryName, subcategoryName, clientName, assetName, categoryId, subcategoryId);
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
        }
        return info;
    }
    
    public String getStatus(int st)
    {
        String str = "";
        switch (st) {
            case 1:
            case 0:
                str = "Unassigned";
                break;
            case 2:
                str = "Assigned";
                break;
            case 3:
                str = "Completed";
                break;
            default:
                break;
        }
        return str;
    }
    
    public String getStColour(int st)
    {
        String s = "";
        if(st == 1 || st <= 0)
            s = "<span class='round_circle circle_unassigned'></span>";
        else if(st == 2)
            s = "<span class='round_circle circle_pending'></span>";
        else if(st == 3)
            s = "<span class='round_circle circle_complete'></span>";
        else if(st == 4 || st == 5 || st == 6)
            s = "<span class='round_circle circle_exipry'></span>";
        else if(st == 7)
            s = "<span class='round_circle circle_exipred'></span>";
        return s;
    }
    
    public ArrayList getListAssign1(int clientIdIndex, int assetIdIndex, int positionIdIndex, 
        int candidateId, int cid, int scid, int ftype, String search, int statusIndex)
    {
        ArrayList list = new ArrayList();
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT c.i_courseid, t_coursename.s_name, t_category.s_name, t_subcategory.s_name, ");
            sb.append("t_tctype.s_name, t_training.s_name, t1.status, t1.lifetime, t1.regdate, ");
            sb.append("(t1.enddate >= current_date() AND enddate <= current_date() + INTERVAL '45' DAY), ");
            sb.append("(t1.enddate > current_date() + INTERVAL '45' DAY AND enddate <= current_date() + INTERVAL '65' DAY), ");
            sb.append("(t1.enddate > current_date() + INTERVAL '65' DAY AND enddate <= current_date() + INTERVAL '90' DAY), ");
            sb.append("t1.enddate < current_date(), DATE_FORMAT(t1.enddate, '%d %b %Y') AS edate, c.i_clientmatrixdetailid, t1.s_filename, t1.s_url ");
            sb.append("FROM t_clientmatrixdetail AS c ");
            sb.append("LEFT JOIN t_course ON (t_course.i_courseid = c.i_courseid) ");
            sb.append("LEFT JOIN t_coursename ON (t_coursename.i_coursenameid = t_course.i_coursenameid) ");
            sb.append("LEFT JOIN t_category ON (t_category.i_categoryid = c.i_categoryid) ");
            sb.append("LEFT JOIN t_subcategory ON (t_subcategory.i_subcategoryid = c.i_subcategoryid) ");
            sb.append("LEFT JOIN t_tctype ON (t_tctype.i_tctypeid = c.i_tctypeid) ");
            sb.append("LEFT JOIN t_training ON (t_training.i_trainingid = c.i_trainingid) ");
            sb.append("LEFT JOIN (select i_courseid, i_positionid, i_lifetime AS lifetime, i_status AS status, d_enddate AS enddate, DATE_FORMAT(d_completeby, '%d %b %Y') AS regdate, s_filename, s_url FROM t_managetraining WHERE i_candidateid = ?) AS t1 ON (t1.i_courseid = c.i_courseid AND c.i_positionid = t1.i_positionid) ");
            sb.append("WHERE t_course.i_status = 1 AND t_category.i_status = 1 AND t_subcategory.i_status = 1  AND c.i_clientid = ? AND c.i_clientassetid = ? AND c.i_positionid = ? ");
            if(search != null && !search.equals(""))
                sb.append(" AND (t_coursename.s_name LIKE ? OR t_category.s_name LIKE ? OR t_subcategory.s_name LIKE ? OR t_tctype.s_name LIKE ? OR t_training.s_name LIKE ?) ");
            if(cid > 0)
                sb.append(" AND c.i_categoryid = ? ");
            if(scid > 0)
                sb.append(" AND c.i_subcategoryid = ? ");
            if(statusIndex > 0)
            {
                if(statusIndex == 1)
                    sb.append(" AND (t1.status <= 1 OR t1.status IS NULL) ");
                else if(statusIndex == 2)
                    sb.append(" AND (t1.status = 2) ");
                else if(statusIndex == 3)
                    sb.append(" AND (t1.status = 3) ");
            }
            if(ftype > 0)
            {
                if(ftype == 1)
                    sb.append(" AND (t1.status <= 1 OR t1.status IS NULL) ");
                else if(ftype == 2)
                    sb.append(" AND (t1.status = 2) ");
                else if(ftype == 3)
                    sb.append(" AND (t1.status = 3) ");
                else if(ftype == 4)
                    sb.append(" AND (t1.status = 3 AND  t1.enddate >= current_date() AND enddate <= current_date() + INTERVAL '45' DAY) ");
                else if(ftype == 5)
                    sb.append(" AND (t1.status = 3 AND t1.enddate > current_date() + INTERVAL '45' DAY AND enddate <= current_date() + INTERVAL '65' DAY) ");
                else if(ftype == 6)
                    sb.append(" AND (t1.status = 3 AND t1.enddate > current_date() + INTERVAL '65' DAY AND enddate <= current_date() + INTERVAL '90' DAY) ");
                else if(ftype == 7)
                    sb.append(" AND (t1.status = 3 AND t1.lifetime = 0 AND t1.enddate < current_date()) ");
            }
            sb.append("ORDER BY t_coursename.s_name ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, clientIdIndex);
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setInt(++scc, positionIdIndex);
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
            }
            if(cid > 0)
                pstmt.setInt(++scc, cid);
            if(scid > 0)
                pstmt.setInt(++scc, scid);
            logger.info("getListAssign1 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String courseName, categoryName, subcategoryName, coursetype, 
                    level, statusstr, date, filename, url;
            int courseId, status, lifetime, clientmatrixdetailid;
            while (rs.next())
            {
                courseId = rs.getInt(1);
                courseName = rs.getString(2) != null ? rs.getString(2) : "";
                categoryName = rs.getString(3) != null ? rs.getString(3) : "";
                subcategoryName = rs.getString(4) != null ? rs.getString(4) : "";
                coursetype = rs.getString(5) != null ? rs.getString(5) : "";
                level = rs.getString(6) != null ? rs.getString(6) : "";
                status = rs.getInt(7);
                lifetime = rs.getInt(8);
                date = rs.getString(9) != null ? rs.getString(9) : "";
                statusstr = getStatus(status);                
                String edate = rs.getString(14) != null ? rs.getString(14) : "";
                clientmatrixdetailid = rs.getInt(15);
                filename = ""; url = "";
                if(status == 3)
                {
                    filename = rs.getString(16) != null ? rs.getString(16) : "";
                    url = rs.getString(17) != null ? rs.getString(17) : "";
                    date = edate;
                }
                if(status == 3 && lifetime <= 0)
                {
                    int f1 = rs.getInt(10);
                    int f2 = rs.getInt(11);
                    int f3 = rs.getInt(12);
                    int f4 = rs.getInt(13);
                    if(f1 > 0)
                        status = 4;
                    if(f2 > 0)
                        status = 5;
                    if(f3 > 0)
                        status = 6;
                    if(f4 > 0)
                        status = 7;
                }
                list.add(new ManagetrainingInfo(courseId, courseName, categoryName, subcategoryName, 
                    coursetype, level, status, statusstr, date, clientmatrixdetailid, filename, url));
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
    
    public ArrayList getListAssign2(int clientIdIndex, int assetIdIndex, int courseId, int ftype, String search, int statusIndex, int positionId2Index)
    {
        ArrayList list = new ArrayList();
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, ");
            sb.append("t_grade.s_name, t_tctype.s_name, t_training.s_name, t2.status, t2.lifetime, t2.regdate, ");
            sb.append("(t2.enddate >= current_date() and enddate <= current_date() + INTERVAL '45' DAY), ");
            sb.append("(t2.enddate > current_date() + INTERVAL '45' DAY and enddate <= current_date() + INTERVAL '65' DAY), ");
            sb.append("(t2.enddate > current_date() + INTERVAL '65' DAY and enddate <= current_date() + INTERVAL '90' DAY), ");
            sb.append("t2.enddate < current_date(), DATE_FORMAT(t2.enddate, '%d %b %Y') as edate, t1.clientmatrixdetailid, t2.s_filename, t2.s_url, t_position.i_positionid ");
            sb.append("from t_candidate as c ");
            sb.append("LEFT JOIN t_position on (t_position.i_positionid = c.i_positionid) ");
            sb.append("LEFT JOIN t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("LEFT JOIN (select i_positionid, i_tctypeid, i_trainingid, i_clientmatrixdetailid as clientmatrixdetailid from t_clientmatrixdetail where i_clientid = ? and i_clientassetid = ? and i_courseid = ?) as t1 on (t1.i_positionid = t_position.i_positionid) ");
            sb.append("LEFT JOIN t_tctype on (t_tctype.i_tctypeid = t1.i_tctypeid) ");
            sb.append("LEFT JOIN t_training on (t_training.i_trainingid = t1.i_trainingid) ");
            sb.append("LEFT JOIN (select i_candidateid, i_positionid, i_lifetime as lifetime, i_status as status, d_enddate as enddate, DATE_FORMAT(d_completeby, '%d %b %Y') as regdate, s_filename, s_url from t_managetraining where i_courseid = ?) as t2 on (t2.i_candidateid = c.i_candidateid and c.i_positionid = t2.i_positionid) ");
            sb.append("WHERE c.i_clientid = ? AND c.i_clientassetid = ? ");
            sb.append("and c.i_positionid IN (select DISTINCT(i_positionid) from t_clientmatrixdetail where i_courseid = ? and i_clientassetid = ?) ");
            if(search != null && !search.equals(""))
                sb.append(" and (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? OR t_position.s_name LIKE ? OR t_grade.s_name LIKE ?) ");
            if(positionId2Index > 0)
            {
                    sb.append(" and c.i_positionid = ? ");
            }
            if(statusIndex > 0)
            {
                if(statusIndex == 1)
                    sb.append(" and (t2.status <= 1 OR t2.status is NULL) ");
                else if(statusIndex == 2)
                    sb.append(" and (t2.status = 2) ");
                else if(statusIndex == 3)
                    sb.append(" and (t2.status = 3) ");
            }
            if(ftype > 0)
            {
                if(ftype == 1)
                    sb.append(" and (t2.status <= 1 OR t2.status is NULL) ");
                else if(ftype == 2)
                    sb.append(" and (t2.status = 2) ");
                else if(ftype == 3)
                    sb.append(" and (t2.status = 3) ");
                else if(ftype == 4)
                    sb.append(" and (t2.status = 3 and t2.enddate >= current_date() and enddate <= current_date() + INTERVAL '45' DAY) ");
                else if(ftype == 5)
                    sb.append(" and (t2.status = 3 and t2.enddate > current_date() + INTERVAL '45' DAY and enddate <= current_date() + INTERVAL '65' DAY) ");
                else if(ftype == 6)
                    sb.append(" and (t2.status = 3 and t2.enddate > current_date() + INTERVAL '65' DAY and enddate <= current_date() + INTERVAL '90' DAY) ");
                else if(ftype == 7)
                    sb.append(" and (t2.status = 3 and t2.lifetime = 0 and t2.enddate < current_date()) ");
            }
            sb.append("ORDER BY c.s_firstname, c.s_middlename, c.s_lastname ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientIdIndex);
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setInt(++scc, courseId);
            pstmt.setInt(++scc, courseId);
            pstmt.setInt(++scc, clientIdIndex);
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setInt(++scc, courseId);
            pstmt.setInt(++scc, assetIdIndex);
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
            }
            if(positionId2Index > 0)
            {
                pstmt.setInt(++scc, positionId2Index);
            }
            logger.info("getListAssign2 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, positionName, gradeName, coursetype, level, statusstr, date, filename, url;
            int candidateId, status, lifetime, positionId;
            while (rs.next())
            {
                candidateId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                positionName = rs.getString(3) != null ? rs.getString(3) : "";
                gradeName = rs.getString(4) != null ? rs.getString(4) : "";
                coursetype = rs.getString(5) != null ? rs.getString(5) : "";
                level = rs.getString(6) != null ? rs.getString(6) : "";
                status = rs.getInt(7);
                lifetime = rs.getInt(8);
                date = rs.getString(9) != null ? rs.getString(9) : "";
                statusstr = getStatus(status);                
                String edate = rs.getString(14) != null ? rs.getString(14) : "";
                int clientmatrixdetailid = rs.getInt(15);
                filename = ""; url = "";
                if(status == 3)
                {
                    date = edate;
                    filename = rs.getString(16) != null ? rs.getString(16) : "";
                    url = rs.getString(17) != null ? rs.getString(17) : "";
                }
                positionId = rs.getInt(18);
                if(status == 3 && lifetime <= 0)
                {
                    int f1 = rs.getInt(10);
                    int f2 = rs.getInt(11);
                    int f3 = rs.getInt(12);
                    int f4 = rs.getInt(13);
                    if(f1 > 0)
                        status = 4;
                    if(f2 > 0)
                        status = 5;
                    if(f3 > 0)
                        status = 6;
                    if(f4 > 0)
                        status = 7;
                }
                if(!gradeName.equals(""))
                    positionName += " - " + gradeName;
                list.add(new ManagetrainingInfo(candidateId, name, positionName, coursetype, level, status, 
                        statusstr, date, clientmatrixdetailid,filename, url, positionId));
            }
            rs.close();
            
            sb.append("SELECT c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, ");
            sb.append("t_grade.s_name, t_tctype.s_name, t_training.s_name, t2.status, t2.lifetime, t2.regdate, ");
            sb.append("(t2.enddate >= current_date() and enddate <= current_date() + INTERVAL '45' DAY), ");
            sb.append("(t2.enddate > current_date() + INTERVAL '45' DAY and enddate <= current_date() + INTERVAL '65' DAY), ");
            sb.append("(t2.enddate > current_date() + INTERVAL '65' DAY and enddate <= current_date() + INTERVAL '90' DAY), ");
            sb.append("t2.enddate < current_date(), DATE_FORMAT(t2.enddate, '%d %b %Y') as edate, t1.clientmatrixdetailid, t2.s_filename, t2.s_url, t_position.i_positionid ");
            sb.append("from t_candidate as c ");
            sb.append("LEFT JOIN t_position on (t_position.i_positionid = c.i_positionid2) ");
            sb.append("LEFT JOIN t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("LEFT JOIN (select i_positionid, i_tctypeid, i_trainingid, i_clientmatrixdetailid as clientmatrixdetailid from t_clientmatrixdetail where i_clientid = ? and i_clientassetid = ? and i_courseid = ?) as t1 on (t1.i_positionid = t_position.i_positionid) ");
            sb.append("LEFT JOIN t_tctype on (t_tctype.i_tctypeid = t1.i_tctypeid) ");
            sb.append("LEFT JOIN t_training on (t_training.i_trainingid = t1.i_trainingid) ");
            sb.append("LEFT JOIN (select i_candidateid, i_positionid, i_lifetime as lifetime, i_status as status, d_enddate as enddate, DATE_FORMAT(d_completeby, '%d %b %Y') as regdate, s_filename, s_url from t_managetraining where i_courseid = ?) as t2 on (t2.i_candidateid = c.i_candidateid and c.i_positionid2 = t2.i_positionid) ");
            sb.append("WHERE c.i_positionid2 > 0 AND c.i_clientid = ? AND c.i_clientassetid = ? ");
            sb.append("and c.i_positionid2 IN (select DISTINCT(i_positionid) from t_clientmatrixdetail where i_courseid = ? and i_clientassetid = ?) ");
            if(search != null && !search.equals(""))
                sb.append(" and (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? OR t_position.s_name LIKE ? OR t_grade.s_name LIKE ?) ");
            if(positionId2Index > 0)
            {
                    sb.append(" and c.i_positionid2 = ? ");
            }
            if(statusIndex > 0)
            {
                if(statusIndex == 1)
                    sb.append(" and (t2.status <= 1 OR t2.status is NULL) ");
                else if(statusIndex == 2)
                    sb.append(" and (t2.status = 2) ");
                else if(statusIndex == 3)
                    sb.append(" and (t2.status = 3) ");
            }
            if(ftype > 0)
            {
                if(ftype == 1)
                    sb.append(" and (t2.status <= 1 OR t2.status is NULL) ");
                else if(ftype == 2)
                    sb.append(" and (t2.status = 2) ");
                else if(ftype == 3)
                    sb.append(" and (t2.status = 3) ");
                else if(ftype == 4)
                    sb.append(" and (t2.status = 3 and t2.enddate >= current_date() and enddate <= current_date() + INTERVAL '45' DAY) ");
                else if(ftype == 5)
                    sb.append(" and (t2.status = 3 and t2.enddate > current_date() + INTERVAL '45' DAY and enddate <= current_date() + INTERVAL '65' DAY) ");
                else if(ftype == 6)
                    sb.append(" and (t2.status = 3 and t2.enddate > current_date() + INTERVAL '65' DAY and enddate <= current_date() + INTERVAL '90' DAY) ");
                else if(ftype == 7)
                    sb.append(" and (t2.status = 3 and t2.lifetime = 0 and t2.enddate < current_date()) ");
            }
            sb.append("ORDER BY c.s_firstname, c.s_middlename, c.s_lastname ");
            query = (sb.toString()).intern();
            sb.setLength(0);
            
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            scc = 0;
            pstmt.setInt(++scc, clientIdIndex);
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setInt(++scc, courseId);
            pstmt.setInt(++scc, courseId);
            pstmt.setInt(++scc, clientIdIndex);
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setInt(++scc, courseId);
            pstmt.setInt(++scc, assetIdIndex);
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
            }
            if(positionId2Index > 0)
            {
                pstmt.setInt(++scc, positionId2Index);
            }
            logger.info("getListAssign2 2 :: " + pstmt.toString());
            rs = pstmt.executeQuery();          
            while (rs.next())
            {
                candidateId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                positionName = rs.getString(3) != null ? rs.getString(3) : "";
                gradeName = rs.getString(4) != null ? rs.getString(4) : "";
                coursetype = rs.getString(5) != null ? rs.getString(5) : "";
                level = rs.getString(6) != null ? rs.getString(6) : "";
                status = rs.getInt(7);
                lifetime = rs.getInt(8);
                date = rs.getString(9) != null ? rs.getString(9) : "";
                statusstr = getStatus(status);                
                String edate = rs.getString(14) != null ? rs.getString(14) : "";
                int clientmatrixdetailid = rs.getInt(15);
                filename = ""; url = "";
                if(status == 3)
                {
                    date = edate;
                    filename = rs.getString(16) != null ? rs.getString(16) : "";
                    url = rs.getString(17) != null ? rs.getString(17) : "";
                }
                positionId = rs.getInt(18);
                if(status == 3 && lifetime <= 0)
                {
                    int f1 = rs.getInt(10);
                    int f2 = rs.getInt(11);
                    int f3 = rs.getInt(12);
                    int f4 = rs.getInt(13);
                    if(f1 > 0)
                        status = 4;
                    if(f2 > 0)
                        status = 5;
                    if(f3 > 0)
                        status = 6;
                    if(f4 > 0)
                        status = 7;
                }
                if(!gradeName.equals(""))
                    positionName += " - " + gradeName;
                list.add(new ManagetrainingInfo(candidateId, name, positionName, coursetype, level, status, 
                        statusstr, date, clientmatrixdetailid,filename, url, positionId));
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
    
    public int createCourse(int clientassetId, int candidateId, int courseId, int positionId, int status, String completeby, 
        String link, String startdate, String enddate, int lifetime, String filename, int userId, String remarks) 
    {
        int id = 0;
        try
        {
            conn = getConnection();
            
            String cq = "select i_managetrainingid from t_managetraining where i_active = 1 and i_clientassetid = ? and i_candidateid = ? and i_positionid = ? and i_courseid = ?";
            pstmt = conn.prepareStatement(cq);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, candidateId);
            pstmt.setInt(3, positionId);
            pstmt.setInt(4, courseId);
            logger.info("check course :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int managetrainingId = 0;
            while (rs.next()) {
                managetrainingId = rs.getInt(1);
            }
            if(managetrainingId > 0)
            {
                StringBuilder sb = new StringBuilder(); 
                if(status == 2)
                {
                    sb.append("UPDATE t_managetraining SET i_status = ?, d_completeby = ?, s_link = ?, ");
                    sb.append("i_userid = ?, ts_moddate = ? where i_managetrainingid = ? ");
                    String query = (sb.toString()).intern();
                    sb.setLength(0);
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, status);
                    pstmt.setString(2, changeDate1(completeby));
                    pstmt.setString(3, link);
                    pstmt.setInt(4, userId);
                    pstmt.setString(5, currDate1());
                    pstmt.setInt(6, managetrainingId);
                    pstmt.executeUpdate();
                }
                else if(status == 3)
                {
                    sb.append("UPDATE t_managetraining SET i_status = ?, d_startdate = ?, d_enddate = ?, ");
                    sb.append("i_lifetime = ?, s_filename = ?, s_remarks = ?, i_userid = ?, ts_moddate = ? where i_managetrainingid = ? ");
                    String query = (sb.toString()).intern();
                    sb.setLength(0);
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, status);
                    pstmt.setString(2, changeDate1(startdate));
                    pstmt.setString(3, changeDate1(enddate));
                    pstmt.setInt(4, lifetime);
                    pstmt.setString(5, filename);
                    pstmt.setString(6, remarks);
                    pstmt.setInt(7, userId);
                    pstmt.setString(8, currDate1());
                    pstmt.setInt(9, managetrainingId);
                    pstmt.executeUpdate();
                }
            }
            else
            {
                StringBuilder sb = new StringBuilder();            
                sb.append("INSERT INTO t_managetraining ");
                sb.append("(i_clientassetid, i_candidateid, i_courseid, i_positionid, i_status, d_completeby, s_link, d_startdate, d_enddate, i_lifetime, ");
                sb.append("s_filename, s_remarks, i_userid, ts_regdate, ts_moddate) ");
                sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                String query = (sb.toString()).intern();
                sb.setLength(0);

                pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, clientassetId);
                pstmt.setInt(2, candidateId);
                pstmt.setInt(3, courseId);
                pstmt.setInt(4, positionId);
                pstmt.setInt(5, status);
                pstmt.setString(6, changeDate1(completeby));
                pstmt.setString(7, link);
                pstmt.setString(8, changeDate1(startdate));
                pstmt.setString(9, changeDate1(enddate));
                pstmt.setInt(10, lifetime);
                pstmt.setString(11, filename);
                pstmt.setString(12, remarks);
                pstmt.setInt(13, userId);
                pstmt.setString(14, currDate1());
                pstmt.setString(15, currDate1());
                logger.info("createCourse :: " + pstmt.toString());
                pstmt.executeUpdate();
                rs = pstmt.getGeneratedKeys();
                while (rs.next()) 
                {
                    id = rs.getInt(1);
                }
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        } 
        finally {
            close(conn, pstmt, rs);
        }
        return id;
    }
    
    public ManagetrainingInfo getModalInfo(int clientmatrixdetailId)
    {
        ManagetrainingInfo info = null;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT c.i_positionid, c.i_courseid, t_coursename.s_name, t_category.s_name, t_subcategory.s_name, ");
            sb.append("t_tctype.s_name, t_level.s_name, t_training.s_name, cn.s_name, t_course.s_description, t1.ct, c.i_subcategoryid, c.i_categoryid, t_coursename.i_coursenameid ");
            sb.append("from t_clientmatrixdetail as c ");
            sb.append("left join t_course on (t_course.i_courseid = c.i_courseid) ");
            sb.append("left join t_coursename on (t_coursename.i_coursenameid = t_course.i_coursenameid) ");
            sb.append("left join t_category on (t_category.i_categoryid = c.i_categoryid) ");
            sb.append("left join t_subcategory on (t_subcategory.i_subcategoryid = c.i_subcategoryid) ");
            sb.append("left join t_tctype on (t_tctype.i_tctypeid = c.i_tctypeid) ");            
            sb.append("left join t_level on (t_level.i_levelid = c.i_levelid) ");
            sb.append("left join t_training on (t_training.i_trainingid = c.i_trainingid) ");
            sb.append("left join t_course as c1 on (c1.i_courseid = c.i_courseidrel) ");
            sb.append("left join t_coursename cn on (cn.i_coursenameid = c1.i_coursenameid) ");
            sb.append("left join (select i_courseid, count(1) as ct from t_courseattachment where i_status = 1 group by i_courseid) as t1 on (t_course.i_courseid = t1.i_courseid) ");
            sb.append("WHERE c.i_clientmatrixdetailid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientmatrixdetailId);
            logger.info("getModalInfo :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String courseName, categoryName, subcategoryName, coursetype, level, priority, courseNamerel, description;
            int positionId, courseId, pcount, subcategoryId, categoryId, coursenameId;
            while (rs.next())
            {
                positionId = rs.getInt(1);
                courseId = rs.getInt(2);
                courseName = rs.getString(3) != null ? rs.getString(3) : "";
                categoryName = rs.getString(4) != null ? rs.getString(4) : "";
                subcategoryName = rs.getString(5) != null ? rs.getString(5) : "";
                coursetype = rs.getString(6) != null ? rs.getString(6) : "";
                level = rs.getString(7) != null ? rs.getString(7) : "";
                priority = rs.getString(8) != null ? rs.getString(8) : "";
                courseNamerel = rs.getString(9) != null ? rs.getString(9) : "";
                description = rs.getString(10) != null ? rs.getString(10) : "";
                pcount = rs.getInt(11);
                subcategoryId = rs.getInt(12);
                categoryId = rs.getInt(13);
                coursenameId = rs.getInt(14);
                info = new ManagetrainingInfo(positionId, courseId, courseName, categoryName, subcategoryName, 
                    coursetype, level, priority, courseNamerel, description, pcount, subcategoryId, categoryId, coursenameId);
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
        return info;
    }
    
    public ManagetrainingInfo getModalInfoPersonal(int candidateId)
    {
        ManagetrainingInfo info = null;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.i_positionid, t_position.s_name, t_grade.s_name, ");
            sb.append("p2.i_positionid, p2.s_name, g2.s_name ");            
            sb.append("from  t_candidate as c  ");            
            sb.append("left join t_position on (t_position.i_positionid = c.i_positionid) ");
            sb.append("left join t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("left join t_position AS p2 on (p2.i_positionid = c.i_positionid2) ");
            sb.append("left join t_grade AS g2 on (g2.i_gradeid = p2.i_gradeid) ");
            sb.append("WHERE c.i_candidateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getModalInfoPersonal :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, positionName, gradeName, position2, grade2;
            int positionId , positionId2;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                positionId = rs.getInt(2);
                positionName = rs.getString(3) != null ? rs.getString(3) : "";
                gradeName = rs.getString(4) != null ? rs.getString(4) : "";             
                positionId2 = rs.getInt(5);
                position2 = rs.getString(6) != null ? rs.getString(6) : "";
                grade2= rs.getString(7) != null ? rs.getString(7) : "";             
                if(!gradeName.equals(""))
                    positionName += " - " + gradeName;
                if(!grade2.equals(""))
                    position2 += " - " + grade2;
                
                info = new ManagetrainingInfo(name, positionId, positionName, positionId2, position2,candidateId);
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
        return info;
    }
    
    public int createCourseMultiple(int clientassetId, int candidateId, String courseids, int positionId,
        String completeby, String link, int userId)
    {
        int id = 0;
        try
        {
            conn = getConnection();
            String carr[] = parseCommaDelimString(courseids);
            if(carr != null)
            {
                int len = carr.length;
                for(int i = 0; i < len; i++)
                {
                    int courseId = Integer.parseInt(carr[i]);
                    if(courseId > 0)
                    {
                        String cq = "select i_managetrainingid from t_managetraining where i_active = 1 and i_clientassetid = ? and i_candidateid = ? and i_positionid = ? and i_courseid = ?";
                        pstmt = conn.prepareStatement(cq);
                        pstmt.setInt(1, clientassetId);
                        pstmt.setInt(2, candidateId);
                        pstmt.setInt(3, positionId);
                        pstmt.setInt(4, courseId);
                        logger.info("check course :: " + pstmt.toString());
                        rs = pstmt.executeQuery();
                        int managetrainingId = 0;
                        while (rs.next()) {
                            managetrainingId = rs.getInt(1);
                        }
                        if(managetrainingId > 0)
                        {
                            StringBuilder sb = new StringBuilder();
                            sb.append("UPDATE t_managetraining SET i_status = ?, d_completeby = ?, s_link = ?, ");
                            sb.append("i_userid = ?, ts_moddate = ? where i_managetrainingid = ? ");
                            String query = (sb.toString()).intern();
                            sb.setLength(0);
                            pstmt = conn.prepareStatement(query);
                            pstmt.setInt(1, 2);
                            pstmt.setString(2, changeDate1(completeby));
                            pstmt.setString(3, link);
                            pstmt.setInt(4, userId);
                            pstmt.setString(5, currDate1());
                            pstmt.setInt(6, managetrainingId);
                            pstmt.executeUpdate();
                        }
                        else
                        {
                            StringBuilder sb = new StringBuilder();            
                            sb.append("INSERT INTO t_managetraining ");
                            sb.append("(i_clientassetid, i_candidateid, i_courseid, i_positionid, i_status, d_completeby, s_link, ");
                            sb.append("i_userid, ts_regdate, ts_moddate) ");
                            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                            String query = (sb.toString()).intern();
                            sb.setLength(0);
                            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                            pstmt.setInt(1, clientassetId);
                            pstmt.setInt(2, candidateId);
                            pstmt.setInt(3, courseId);
                            pstmt.setInt(4, positionId);
                            pstmt.setInt(5, 2);
                            pstmt.setString(6, changeDate1(completeby));
                            pstmt.setString(7, link);
                            pstmt.setInt(8, userId);
                            pstmt.setString(9, currDate1());
                            pstmt.setString(10, currDate1());
                            logger.info("createCourse :: " + pstmt.toString());
                            pstmt.executeUpdate();
                            rs = pstmt.getGeneratedKeys();
                            while (rs.next())
                            {
                                id = rs.getInt(1);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            close(conn, pstmt, rs);
        }
        return id;
    }
    
    public ManagetrainingInfo getcourseModalInfo(int courseId)
    {
        ManagetrainingInfo info = null;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT  t_coursename.s_name, t_category.s_name, t_subcategory.s_name ");
            sb.append("from t_course ");
            sb.append("left join t_coursename on (t_coursename.i_coursenameid = t_course.i_coursenameid) ");
            sb.append("left join t_category on (t_category.i_categoryid = t_course.i_categoryid) ");
            sb.append("left join t_subcategory on (t_subcategory.i_subcategoryid = t_course.i_subcategoryid) ");
            sb.append("WHERE t_course.i_courseid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, courseId);
            logger.info("getcourseModalInfo :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String courseName, categoryName, subcategoryName;
            while (rs.next())
            {
                courseName = rs.getString(1) != null ? rs.getString(1) : "";
                categoryName = rs.getString(2) != null ? rs.getString(2) : "";
                subcategoryName = rs.getString(3) != null ? rs.getString(3) : "";               
                info = new ManagetrainingInfo(0, courseId, courseName, categoryName, subcategoryName,"", "", "", "", "", 0,0,0,0);
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
        return info;
    }
    
    public int createPersonalMultiple(int clientassetId, String candidateIds, int courseId,  
        String completeby, String link, int userId)
    {
        int id = 0;
        try
        {
            conn = getConnection();
            String carr[] = parseCommaDelimString(candidateIds);
            if(carr != null)
            {
                int len = carr.length;
                for(int i = 0; i < len; i++)
                {
                    String carrval[] = carr[i].split("@");
                    int candidateId = Integer.parseInt(carrval != null && carrval[0] != null ? carrval[0] : "0");
                    int positionId = Integer.parseInt(carrval != null && carrval[1] != null ? carrval[1] : "0");
                    if(candidateId > 0)
                    {                       
                        String cq = "select i_managetrainingid from t_managetraining where i_active = 1 and i_clientassetid = ? and i_candidateid = ? and i_positionid = ? and i_courseid = ?";
                        pstmt = conn.prepareStatement(cq);
                        pstmt.setInt(1, clientassetId);
                        pstmt.setInt(2, candidateId);
                        pstmt.setInt(3, positionId);
                        pstmt.setInt(4, courseId);
                        logger.info("check course :: " + pstmt.toString());
                        rs = pstmt.executeQuery();
                        int managetrainingId = 0;
                        while (rs.next()) {
                            managetrainingId = rs.getInt(1);
                        }
                        if(managetrainingId > 0)
                        {
                            StringBuilder sb = new StringBuilder();
                            sb.append("UPDATE t_managetraining SET i_status = ?, d_completeby = ?, s_link = ?, ");
                            sb.append("i_userid = ?, ts_moddate = ? where i_managetrainingid = ? ");
                            String query = (sb.toString()).intern();
                            sb.setLength(0);
                            pstmt = conn.prepareStatement(query);
                            pstmt.setInt(1, 2);
                            pstmt.setString(2, changeDate1(completeby));
                            pstmt.setString(3, link);
                            pstmt.setInt(4, userId);
                            pstmt.setString(5, currDate1());
                            pstmt.setInt(6, managetrainingId);
                            pstmt.executeUpdate();
                        }
                        else
                        {
                            StringBuilder sb = new StringBuilder();            
                            sb.append("INSERT INTO t_managetraining ");
                            sb.append("(i_clientassetid, i_candidateid, i_courseid, i_positionid, i_status, d_completeby, s_link, ");
                            sb.append("i_userid, ts_regdate, ts_moddate) ");
                            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                            String query = (sb.toString()).intern();
                            sb.setLength(0);
                            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                            pstmt.setInt(1, clientassetId);
                            pstmt.setInt(2, candidateId);
                            pstmt.setInt(3, courseId);
                            pstmt.setInt(4, positionId);
                            pstmt.setInt(5, 2);
                            pstmt.setString(6, changeDate1(completeby));
                            pstmt.setString(7, link);
                            pstmt.setInt(8, userId);
                            pstmt.setString(9, currDate1());
                            pstmt.setString(10, currDate1());
                            logger.info("createCourse :: " + pstmt.toString());
                            pstmt.executeUpdate();
                            rs = pstmt.getGeneratedKeys();
                            while (rs.next())
                            {
                                id = rs.getInt(1);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            close(conn, pstmt, rs);
        }
        return id;
    }
    
    public ManagetrainingInfo getCandidatemailId(int crewrotationId)
    {
        ManagetrainingInfo info = null;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), c.s_email, cr.i_candidateid ");
            sb.append("from t_crewrotation as cr left join t_candidate as c on (c.i_candidateid = cr.i_candidateid) ");
            sb.append("WHERE cr.i_crewrotationid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, crewrotationId);
            logger.info("getModalInfoPersonal :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, email;
            int candidateId = 0;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                email = rs.getString(2) != null ? rs.getString(2) : "";
                candidateId = rs.getInt(3);
                info = new ManagetrainingInfo(name, email,candidateId);
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
        return info;
    }
    
    public ArrayList getcourseattachment(int courseId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("Select t_courseattachment.i_courseattachmentid, t_courseattachment.s_filename, DATE_FORMAT(t_courseattachment.ts_regdate, '%d-%b-%Y %H:%i'), ");
        sb.append("t_userlogin.s_name ");
        sb.append("FROM t_courseattachment left join t_userlogin on (t_userlogin.i_userid = t_courseattachment.i_userid) ");
        sb.append("where t_courseattachment.i_courseid = ? ");
        sb.append(" order by t_courseattachment.i_courseattachmentid ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, courseId);
            logger.info("getcourseattachment :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename, date, name;
            int courseattachmentId;
            int cc = 0;
            while (rs.next()) 
            {
                courseattachmentId = rs.getInt(1);
                filename = rs.getString(2) != null ? rs.getString(2) : "";
                date = rs.getString(3) != null ? rs.getString(3) : "";
                name = rs.getString(4) != null ? rs.getString(4) : "";
                list.add(new ManagetrainingInfo(courseattachmentId, filename, date,"File"+(++cc)));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public String getpatchbody(String description, String htmlFile) {
        String adminpage = getMainPath("template_path") + htmlFile;
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("MAILBODY", description);
        return template.patch(hashmap);
    }

    public int sendMail(String fromval, String toval, String ccval, String bccval, String subject, String description,String []filePath,
            String []localFileName, String fn_maillist,String candidatename, String username) throws MessagingException {
        String toaddress[] = parseCommaDelimString(toval);
        String ccaddress[] = parseCommaDelimString(ccval);
        String bccaddress[] = parseCommaDelimString(bccval);
        String sdescription = description.replaceAll("\n", "<br/>");
        String mailbody = getpatchbody(sdescription, "mail_selection.html");

        String file_maillog = getMainPath("file_maillog");
        java.util.Date nowmail = new java.util.Date();
        String fn_mail = "mtma-" + String.valueOf(nowmail.getTime());
        String filePath_html = createFolder(file_maillog);
        String fname = writeHTMLFile(mailbody, file_maillog + "/" + filePath_html, fn_mail + ".html");

        StatsInfo sinfo = postMailAttachMF( toaddress, ccaddress, bccaddress, mailbody, subject, filePath,localFileName, -1);
        String from = "";
        if (sinfo != null) {
            from = sinfo.getDdlLabel();
        }
        int maillogId = createMailLog(7, candidatename, toval, ccval, bccval, from, subject, "/" + filePath_html + "/" + fname, fn_maillist, username);

        return maillogId;
    }
    
    public int createMailLog(int type, String name, String to, String cc, String bcc, String from, String subject, String filename, String attachmentpath, String username) {
        int id = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_maillog ");
            sb.append("(i_type, s_name, s_to, s_cc, s_bcc, s_from, s_subject, s_filename, ts_regdate, ts_moddate, s_attachmentpath3, s_sendby) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            String query = sb.toString().intern();
            sb.setLength(0);
            print(this, "createMailLog :: " + query);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, type);
            pstmt.setString(2, name);
            pstmt.setString(3, to);
            pstmt.setString(4, cc);
            pstmt.setString(5, bcc);
            pstmt.setString(6, from);
            pstmt.setString(7, subject);
            pstmt.setString(8, filename);
            pstmt.setString(9, currDate1());
            pstmt.setString(10, currDate1());
            pstmt.setString(11, attachmentpath);
            pstmt.setString(12, username);
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createMailLog :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return id;
    }
}