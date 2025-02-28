package com.web.jxp.trainingmatrix;
import com.web.jxp.base.Base;
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

public class Trainingmatrix extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getTrainingmatrixByName(String search, int next, int count, int allclient, 
            String permission, String cids, String assetids, int clientIdIndex, int assetIdIndex)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.i_clientassetid, t_clientasset.i_clientid, t_client.s_name, t_clientasset.s_name, ");
        sb.append("t1.ct, t_clientasset.i_status FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, COUNT(1) as ct FROM t_clientassetposition WHERE i_status = 1 GROUP BY i_clientassetid) as t1 on (t_clientasset.i_clientassetid = t1.i_clientassetid) ");
        sb.append("WHERE t_clientasset.i_status = 1 AND t_client.i_status = 1 ");
        if(search != null && !search.equals(""))
            sb.append("AND (t_client.s_name like ? OR t_clientasset.s_name like ?) ");        
        if (allclient == 1) {
            sb.append(" AND t_client.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND t_client.i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND t_client.i_clientid < 0 ");
            }
            if (!assetids.equals("")) {
                sb.append("AND t_clientasset.i_clientassetid IN (" + assetids + ") ");
            }
        }
        if (clientIdIndex > 0) {
            sb.append("AND t_client.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND t_clientasset.i_clientassetid = ? ");
        }
        sb.append("ORDER BY t_client.s_name, t_clientasset.s_name ");
        if(count > 0)
            sb.append(" LIMIT ").append(next*count).append(", ").append(count);
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("SELECT COUNT(1) FROM t_clientasset ");
        sb.append("LEFT JOIN t_client on (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("WHERE t_clientasset.i_status = 1 AND t_client.i_status = 1 ");
        if(search != null && !search.equals(""))
            sb.append("AND (t_client.s_name like ? OR t_clientasset.s_name like ?) ");
        if (allclient == 1) {
            sb.append(" AND t_client.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND t_client.i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND t_client.i_clientid < 0 ");
            }
            if (!assetids.equals("")) {
                sb.append("AND t_clientasset.i_clientassetid IN (" + assetids + ") ");
            }
        }
        if (clientIdIndex > 0) {
            sb.append("AND t_client.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND t_clientasset.i_clientassetid = ? ");
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
            }
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            logger.info("getTrainingmatrixByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAssetName;
            int pcount, clientId, assetId;
            while (rs.next())
            {
                assetId = rs.getInt(1);
                clientId = rs.getInt(2);
                clientName = rs.getString(3) != null ? rs.getString(3) : "";
                clientAssetName = rs.getString(4) != null ? rs.getString(4) : "";
                pcount = rs.getInt(5);
                list.add(new TrainingmatrixInfo(clientId, assetId, clientName, clientAssetName, pcount));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            print(this,"getTrainingmatrixByNameCountQuery :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                clientId = rs.getInt(1);
                list.add(new TrainingmatrixInfo(clientId, 0, "", "", 0));
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
        TrainingmatrixInfo info;
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
                    info = (TrainingmatrixInfo) l.get (i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = null;
            if(colId.equals("1") || colId.equals("2"))
                map = sortByName(record, tp);
            else
                map = sortById(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            TrainingmatrixInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (TrainingmatrixInfo) l.get(i);
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
    public String getInfoValue(TrainingmatrixInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getClientName()!= null ? info.getClientName() : "";    
        else if(i != null && i.equals("2"))
            infoval = info.getClientAssetName()!= null ? info.getClientAssetName() : "";
        else if(i != null && i.equals("3"))
            infoval = ""+info.getPcount(); 
        return infoval;
    }
    
    public ArrayList getExcel(String search, int allclient, String permission, String cids, String assetids, int clientIdIndex, int assetIdIndex)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_name, t_clientasset.s_name, t1.ct, t_clientasset.i_status FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, COUNT(1) as ct FROM t_clientassetposition WHERE i_status = 1 GROUP BY i_clientassetid) as t1 on (t_clientasset.i_clientassetid = t1.i_clientassetid) ");
        sb.append("WHERE t_clientasset.i_status = 1 ");
        if(search != null && !search.equals(""))
            sb.append("AND (t_client.s_name like ? OR t_clientasset.s_name like ?) ");
        
        if (allclient == 1) {
            sb.append(" AND t_client.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND t_client.i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND t_client.i_clientid < 0 ");
            }
            if (!assetids.equals("")) {
                sb.append("AND t_clientasset.i_clientassetid IN (" + assetids + ") ");
            }
        }
        if (clientIdIndex > 0) {
            sb.append("AND t_client.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND t_clientasset.i_clientassetid = ? ");
        }
        sb.append("ORDER BY t_client.s_name,t_clientasset.s_name ");        
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
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            logger.info("getTrainingmatrixByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAssetName;
            int pcount;
            while (rs.next())
            {
                clientName = rs.getString(1) != null ? rs.getString(1) : "";
                clientAssetName = rs.getString(2) != null ? rs.getString(2) : "";
                pcount = rs.getInt(3);
                list.add(new TrainingmatrixInfo(0, 0, clientName, clientAssetName, pcount));
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

        sb.append("ORDER BY s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new TrainingmatrixInfo(-1, " All "));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new TrainingmatrixInfo(refId, refName));
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
            coll.add(new TrainingmatrixInfo(-1, " All "));
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
                    coll.add(new TrainingmatrixInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new TrainingmatrixInfo(-1, " All "));
        }
        return coll;
    }
    
    public TrainingmatrixInfo getBasicInfo(int assetId)
    {
        TrainingmatrixInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_name, t_clientasset.s_name, t_assettype.s_name, t_clientasset.i_clientassetid ");
        sb.append("FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_assettype on (t_assettype.i_assettypeid = t_clientasset.i_assettypeid) ");
        sb.append("WHERE t_clientasset.i_clientassetid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assetId);
            logger.info("getBasicInfo :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAssetName, assettypeName;
            while (rs.next())
            {
                clientName = rs.getString(1) != null ? rs.getString(1) : "";
                clientAssetName = rs.getString(2) != null ? rs.getString(2) : "";
                assettypeName = rs.getString(3) != null ? rs.getString(3) : "";
                assetId = rs.getInt(4);
                info = new TrainingmatrixInfo(assetId, clientName, clientAssetName, assettypeName);
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
    
    public Collection getCategories()
    {
        Collection coll = new LinkedList();
        coll.add(new TrainingmatrixInfo(-1, "Select Category"));
        String query = ("select i_categoryid, s_name from t_category where i_status = 1 order by s_name");
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            logger.info("getCategories :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next())
            {
                id = rs.getInt(1);  
                name = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new TrainingmatrixInfo(id, name));
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
        return coll;
    }
    
    public Collection getPositions(int clientassetId)
    {
        Collection coll = new LinkedList();
        coll.add(new TrainingmatrixInfo(-1, "Select Position-Rank"));
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
                coll.add(new TrainingmatrixInfo(id, name));
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
        return coll;
    }
    
    public ArrayList getSubCategoryList(int categoryId)
    {
        ArrayList list = new ArrayList();
        String query = ("select i_subcategoryid, s_name from t_subcategory where i_status = 1 and i_categoryid = ? order by s_name");
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            logger.info("getSubCategoryList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next())
            {
                id = rs.getInt(1);  
                name = rs.getString(2) != null ? rs.getString(2) : "";
                list.add(new TrainingmatrixInfo(id, name));
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
    
    public ArrayList getCourseList(int clientId, int assetId, int positionId, int categoryId)
    {
        ArrayList list = new ArrayList();        
        try
        {
            conn = getConnection();
            String checkq = "select count(1) from t_clientmatrixdetail where i_clientid = ? and i_clientassetid = ? and i_positionid = ? and i_categoryid = ?";
            pstmt = conn.prepareStatement(checkq);
            pstmt.setInt(1, clientId);
            pstmt.setInt(2, assetId);
            pstmt.setInt(3, positionId);
            pstmt.setInt(4, categoryId);  
            int count = 0;
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            
            StringBuilder sb = new StringBuilder(); 
            if(count > 0)
            {
                sb.append("select d.i_clientmatrixdetailid, t_coursename.s_name, t_course.i_subcategoryid, t_course.i_courseid, ");
                sb.append("d.i_tctypeid, d.i_trainingid, d.i_levelid, d.i_courseidrel ");
                sb.append("from t_course left join t_coursename on (t_coursename.i_coursenameid = t_course.i_coursenameid) ");
                sb.append("left join t_clientmatrixdetail as d on (t_course.i_courseid = d.i_courseid and d.i_clientid = ? and d.i_clientassetid = ? and d.i_positionid = ? and d.i_categoryid = ?) ");
                sb.append("where t_course.i_status = 1");
            }
            else
            {
                sb.append("select t1.i_matrixdetailid, t_coursename.s_name, t_course.i_subcategoryid, t_course.i_courseid, ");
                sb.append("0, 0, 0, 0 ");
                sb.append("from t_course left join t_coursename on (t_coursename.i_coursenameid = t_course.i_coursenameid) ");                
                sb.append("left join (select m1.i_matrixdetailid as i_matrixdetailid, m1.i_courseid as i_courseid, m1.i_categoryid as i_categoryid, m2.i_positionid as i_positionid, m2.i_matrixid as i_matrixid ");
                sb.append(" from t_matrixdetail as m1 left join t_matrixposition as m2 on (m2.i_matrixpositionid = m1.i_matrixpositionid) left join t_matrix on (t_matrix.i_matrixid = m2.i_matrixid) where t_matrix.i_status = 1) as t1 on (t1.i_courseid = t_course.i_courseid and t1.i_positionid = ? and t1.i_categoryid = ?) ");
                sb.append("where t_course.i_status = 1");
            }
            String query = sb.toString();
            sb.setLength(0);
            
            pstmt = conn.prepareStatement(query);
            if(count > 0)
            {
                pstmt.setInt(1, clientId);
                pstmt.setInt(2, assetId);
                pstmt.setInt(3, positionId);
                pstmt.setInt(4, categoryId); 
            }
            else
            {
                pstmt.setInt(1, positionId);
                pstmt.setInt(2, categoryId); 
            }
            logger.info("getCourseList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int clientmatrixdetailId, subcategoryid, courseId, tctypeId, traingId, levelId, courseIdrel, matrixdetailId;
            while (rs.next())
            {
                clientmatrixdetailId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                subcategoryid = rs.getInt(3);  
                courseId = rs.getInt(4);
                tctypeId = rs.getInt(5);
                traingId = rs.getInt(6);
                levelId = rs.getInt(7);
                courseIdrel = rs.getInt(8);
                list.add(new TrainingmatrixInfo(name, clientmatrixdetailId, categoryId, subcategoryid, courseId, 
                    tctypeId, traingId, levelId, courseIdrel));
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
    
    public ArrayList getListFromList(ArrayList list, int subcategoryId)
    {
        ArrayList l = new ArrayList();
        int size = list.size();
        for(int i = 0; i < size; i ++)
        {
            TrainingmatrixInfo info = (TrainingmatrixInfo) list.get(i);
            if(info != null && info.getSubcategoryId() == subcategoryId)
                l.add(info);
        }
        return l;
    }
    
    public ArrayList getTraingKindList(int clientId, int clientassetId)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_tctypeid, s_name ");
        sb.append("FROM t_tctype where i_status = 1 and i_clientid = ? and i_clientassetid = ? order by s_name");
        String query = sb.toString();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientId);
            pstmt.setInt(2, clientassetId);
            logger.info("getTraingKindList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next())
            {
                id = rs.getInt(1);  
                name = rs.getString(2) != null ? rs.getString(2) : "";
                list.add(new TrainingmatrixInfo(id, name));
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
    
    public ArrayList getTraingList(int clientId, int clientassetId)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_trainingid, s_name ");
        sb.append("FROM t_training where i_status = 1 and i_clientid = ? and i_clientassetid = ? order by s_name");
        String query = sb.toString();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientId);
            pstmt.setInt(2, clientassetId);
            logger.info("getTraingList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next())
            {
                id = rs.getInt(1);  
                name = rs.getString(2) != null ? rs.getString(2) : "";
                list.add(new TrainingmatrixInfo(id, name));
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
    
    public ArrayList getLevelList()
    {
        ArrayList list = new ArrayList();
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("level.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    list.add(new TrainingmatrixInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return list;
    }
    
    public boolean checkinlist(ArrayList list, int subcategoryId)
    {
        boolean b = false;
        int size = list.size();
        for(int i = 0; i < size; i ++)
        {
            TrainingmatrixInfo info = (TrainingmatrixInfo) list.get(i);
            if(info != null && info.getClientmatrixdetailId() > 0 && info.getSubcategoryId() == subcategoryId)
            {
                b = true;
                break;
            }
        }
        return b;
    }
    
    public String getCollection1(ArrayList list, int id)
    {
        int size = list.size();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++)
        {
            TrainingmatrixInfo info = (TrainingmatrixInfo) list.get(i);
            if(info != null && info.getDdlValue() == id)
            {
                sb.append("<option selected value='"+info.getDdlValue()+"'>"+(info.getDdlLabel() != null ? info.getDdlLabel() : "")+"</option>");
            }
            else
            {
                sb.append("<option value='"+info.getDdlValue()+"'>"+(info.getDdlLabel() != null ? info.getDdlLabel() : "")+"</option>");
            }
        }
        String str = sb.toString();
        sb.setLength(0);
        return str;
    }
    
    public String getCollectionRel(ArrayList list, int id)
    {
        int size = list.size();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++)
        {
            TrainingmatrixInfo info = (TrainingmatrixInfo) list.get(i);
            if(info != null && info.getCourseId() == id)
            {
                sb.append("<option selected value='"+info.getCourseId()+"'>"+(info.getName() != null ? info.getName() : "")+"</option>");
            }
            else
            {
                sb.append("<option value='"+info.getCourseId()+"'>"+(info.getName() != null ? info.getName() : "")+"</option>");
            }
        }
        String str = sb.toString();
        sb.setLength(0);
        return str;
    }
    
    public ArrayList getListFromListCourse(ArrayList list, int courseId)
    {
        ArrayList l = new ArrayList();
        int size = list.size();
        for(int i = 0; i < size; i ++)
        {
            TrainingmatrixInfo info = (TrainingmatrixInfo) list.get(i);
            if(info != null && info.getCourseId() != courseId)
                l.add(info);
        }
        return l;
    }
    
    public int createMatrixDetail(int clientId, int assetId, int categoryId, int positionId, int subcategoryId[], int courseId[], 
        int tctypeId[], int trainingId[], int levelId[], int courseIdrel[], int userId)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_clientmatrixdetail ");
            sb.append("(i_clientid, i_clientassetid, i_positionid, i_categoryid, i_subcategoryid, i_courseid, i_tctypeid, ");
            sb.append("i_trainingid, i_levelid, i_courseidrel, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            
            String delquery = "delete from t_clientmatrixdetail where i_clientid = ? and i_clientassetid = ? and i_positionid = ? and i_categoryid = ? ";
            pstmt = conn.prepareStatement(delquery);
            pstmt.setInt(1, clientId);
            pstmt.setInt(2, assetId);
            pstmt.setInt(3, positionId);
            pstmt.setInt(4, categoryId);            
            print(this, "createMatrixDetail delete all :: " + pstmt.toString());
            pstmt.executeUpdate();
            
            int len = courseId.length;
            pstmt = conn.prepareStatement(query);
            for(int i = 0; i < len; i++)
            {
                if(subcategoryId[i] > 0 && courseId[i] > 0 && tctypeId[i] > 0 && trainingId[i] > 0)
                {
                    int scc = 0;
                    pstmt.setInt(++scc, clientId);
                    pstmt.setInt(++scc, assetId);
                    pstmt.setInt(++scc, positionId);
                    pstmt.setInt(++scc, categoryId);
                    pstmt.setInt(++scc, subcategoryId[i]);
                    pstmt.setInt(++scc, courseId[i]);
                    pstmt.setInt(++scc, tctypeId[i]);
                    pstmt.setInt(++scc, trainingId[i]);
                    pstmt.setInt(++scc, levelId[i]);
                    pstmt.setInt(++scc, courseIdrel[i]);
                    pstmt.setInt(++scc, 1);
                    pstmt.setInt(++scc, userId);
                    pstmt.setString(++scc, currDate1());
                    pstmt.setString(++scc, currDate1());  
                    print(this,"createMatrixDetail :: " + pstmt.toString());
                    cc += pstmt.executeUpdate();
                }
            }
        }
        catch (Exception exception)
        {
            print(this,"createMatrixDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }  
}