package com.web.jxp.framework;
import com.web.jxp.base.Base;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import static com.web.jxp.common.Common.*;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Framework extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getFrameworkByName(String search, int next, int count, int allclient, 
            String permission, String cids, String assetids, int clientIdIndex, int assetIdIndex)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientasset.i_clientassetid, t_clientasset.i_clientid, t_client.s_name, t_clientasset.s_name, ");
        sb.append("t1.ct, t2.ct FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, COUNT(1) as ct FROM t_clientassetposition WHERE i_status = 1 GROUP BY i_clientassetid) as t1 on (t_clientasset.i_clientassetid = t1.i_clientassetid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, COUNT(DISTINCT(i_positionid)) as ct FROM t_fcrole WHERE i_status = 1 GROUP BY i_clientassetid) as t2 on (t_clientasset.i_clientassetid = t2.i_clientassetid) ");
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
            logger.info("getFrameworkByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAssetName;
            int pcount, clientId, assetId, ccount, pending;
            while (rs.next())
            {
                assetId = rs.getInt(1);
                clientId = rs.getInt(2);
                clientName = rs.getString(3) != null ? rs.getString(3) : "";
                clientAssetName = rs.getString(4) != null ? rs.getString(4) : "";
                pcount = rs.getInt(5);
                ccount = rs.getInt(6);
                pending = pcount - ccount;
                list.add(new FrameworkInfo(clientId, assetId, clientName, clientAssetName, pcount, pending, ccount));
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
            print(this,"getFrameworkByNameCountQuery :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                clientId = rs.getInt(1);
                list.add(new FrameworkInfo(clientId, 0, "", "", 0, 0, 0));
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
        FrameworkInfo info;
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
                    info = (FrameworkInfo) l.get (i);
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
            FrameworkInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (FrameworkInfo) l.get(i);
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
    public String getInfoValue(FrameworkInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getClientName()!= null ? info.getClientName() : "";    
        else if(i != null && i.equals("2"))
            infoval = info.getClientAssetName()!= null ? info.getClientAssetName() : "";
        else if(i != null && i.equals("3"))
            infoval = ""+info.getPcount(); 
        else if(i != null && i.equals("4"))
            infoval = ""+info.getPending();
        return infoval;
    }
    
    public ArrayList getExcel(String search, int allclient, String permission, String cids, String assetids, int clientIdIndex, int assetIdIndex)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_name, t_clientasset.s_name, t1.ct, t2.ct FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, COUNT(1) as ct FROM t_clientassetposition WHERE i_status = 1 GROUP BY i_clientassetid) as t1 on (t_clientasset.i_clientassetid = t1.i_clientassetid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, COUNT(DISTINCT(i_positionid)) as ct FROM t_fcrole WHERE i_status = 1 GROUP BY i_clientassetid) as t2 on (t_clientasset.i_clientassetid = t2.i_clientassetid) ");
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
            logger.info("getFrameworkByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAssetName;
            int pcount, ccount, pending;
            while (rs.next())
            {
                clientName = rs.getString(1) != null ? rs.getString(1) : "";
                clientAssetName = rs.getString(2) != null ? rs.getString(2) : "";
                pcount = rs.getInt(3);
                ccount = rs.getInt(4);
                pending = pcount - ccount;
                list.add(new FrameworkInfo(0, 0, clientName, clientAssetName, pcount, pending, ccount));
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

        sb.append("order by s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new FrameworkInfo(-1, " All "));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new FrameworkInfo(refId, refName));
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
            coll.add(new FrameworkInfo(-1, " All "));
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
                    ddlLabel = rs.getString(2) != null ? rs.getString(2): "";
                    coll.add(new FrameworkInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new FrameworkInfo(-1, " All "));
        }
        return coll;
    }
    
    public FrameworkInfo getBasicInfo(int assetId)
    {
        FrameworkInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_name, t_clientasset.s_name, t1.ct, t_clientasset.i_assettypeid ");
        sb.append("FROM t_clientasset ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN (SELECT i_clientassetid, COUNT(1) as ct FROM t_clientassetposition WHERE i_status = 1 GROUP BY i_clientassetid) as t1 on (t_clientasset.i_clientassetid = t1.i_clientassetid) ");
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
            String clientName, clientAssetName;
            int pcount, assettypeId;
            while (rs.next())
            {
                clientName = rs.getString(1) != null ? rs.getString(1) : "";
                clientAssetName = rs.getString(2) != null ? rs.getString(2) : "";
                pcount = rs.getInt(3);
                assettypeId = rs.getInt(4);
                info = new FrameworkInfo(assetId, clientName, clientAssetName, pcount, assettypeId);
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
    
    public ArrayList getPositionList(int clientassetId, int pdeptIdParam)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_position.i_positionid, t_position.s_name, t_grade.s_name, t_pdept.s_name, ");
        sb.append("t_clientassetposition.i_pdeptid, t1.ct ");
        sb.append("FROM t_clientassetposition left join t_position on (t_position.i_positionid = t_clientassetposition.i_positionid) ");
        sb.append("left join t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("left join t_pdept on (t_pdept.i_pdeptid = t_clientassetposition.i_pdeptid) ");
        sb.append("LEFT JOIN (SELECT t_fcrole.i_positionid, COUNT(1) as ct FROM t_fcrole left join t_pcode on (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) WHERE t_fcrole.i_status = 1 and t_pcode.i_status = 1 and t_fcrole.i_clientassetid = ? GROUP BY t_fcrole.i_positionid) as t1 on (t_clientassetposition.i_positionid = t1.i_positionid)");
        sb.append("where t_position.i_status = 1 and t_clientassetposition.i_status = 1 and t_clientassetposition.i_clientassetid = ? ");
        if(pdeptIdParam > 0)
            sb.append(" and t_clientassetposition.i_pdeptid = ? ");
        sb.append("order by t_position.s_name, t_grade.s_name ");
        String query = sb.toString();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, clientassetId);
            if(pdeptIdParam > 0)
                pstmt.setInt(3, pdeptIdParam);
            logger.info("getPositionList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int id, pdeptId, rolecount;
            String positionName, gradeName, pdeptName;
            while (rs.next())
            {
                id = rs.getInt(1);  
                positionName = rs.getString(2) != null ? rs.getString(2) : "";
                gradeName = rs.getString(3) != null ? rs.getString(3) : "";
                pdeptName = rs.getString(4) != null ? rs.getString(4) : "";
                pdeptId = rs.getInt(5);
                rolecount = rs.getInt(6);
                if(!gradeName.equals(""))
                    positionName += " - " + gradeName;
                list.add(new FrameworkInfo(id, positionName, pdeptId, pdeptName, rolecount));
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
    
    public Collection getRoles(int pdeptId)
    {
        Collection coll = new LinkedList();
        coll.add(new FrameworkInfo(-1, "Select Competency (Code | Title)"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("pcode.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null && jobj.getInt("pdeptId") == pdeptId)
                {
                    coll.add(new FrameworkInfo(jobj.optInt("id"), jobj.optString("code")+ " | " + jobj.optString("role")));
                }
            }
        }
        return coll;
    }
    
    public Collection getAssessmenttypes(int pdeptId) 
    {
        Collection coll = new LinkedList();
        coll.add(new FrameworkInfo(-1, "Set Assessment Type"));
        try 
        {
            String xml_path = getMainPath("json_path");
            String str = readHTMLFile("competencyassessments.json", xml_path);
            JSONArray arr = new JSONArray(str);
            if(arr != null)
            {
                int len = arr.length();
                for(int i = 0; i < len; i++)
                {
                    JSONObject jobj = arr.optJSONObject(i);
                    if(jobj != null && jobj.optInt("departmentId") == pdeptId)
                    {
                        coll.add(new FrameworkInfo(jobj.optInt("id"), jobj.optString("name")));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
    
    public FrameworkInfo getBasicInfoForAssign(int clientassetId, int positionId, int pdeptId, String pids)
    {
        FrameworkInfo info = null;
        StringBuilder sb = new StringBuilder();        
        try
        {
            conn = getConnection();
            String positionName = "", gradeName = "", deptName = "", query = "";
            int count1 = 0, count2 = 0, count3 = 0;
            if(positionId > 0)
            {
                sb.append("SELECT t_position.s_name, t_grade.s_name, t_pdept.s_name ");
                sb.append("FROM t_clientassetposition left join t_position on (t_position.i_positionid = t_clientassetposition.i_positionid) ");
                sb.append("left join t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
                sb.append("left join t_pdept on (t_pdept.i_pdeptid = t_clientassetposition.i_pdeptid) ");
                sb.append("where t_clientassetposition.i_clientassetid = ? and t_clientassetposition.i_positionid = ? ");
                query = (sb.toString()).intern();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                pstmt.setInt(2, positionId);
                logger.info("getBasicInfoForAssign :: " + pstmt.toString());
                rs = pstmt.executeQuery();                
                while (rs.next())
                {
                    positionName = rs.getString(1) != null ? rs.getString(1) : "";
                    gradeName = rs.getString(2) != null ? rs.getString(2) : "";
                    deptName = rs.getString(3) != null ? rs.getString(3) : "";
                    if(!gradeName.equals(""))
                        positionName += " | " + gradeName;                
                }
                rs.close();
            }
            else
            {
                positionName = "SHOW SELECTED POSITIONS";
                sb.append("SELECT t_pdept.s_name FROM t_pdept where i_pdeptid = ? ");
                query = (sb.toString()).intern();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, pdeptId);
                logger.info("getBasicInfoForAssign :: " + pstmt.toString());
                rs = pstmt.executeQuery();                
                while (rs.next())
                {
                    deptName = rs.getString(1) != null ? rs.getString(1) : "";
                }
                rs.close();
            }
            query = "select count(1) from t_fcrole where i_clientassetid = ? and i_status = 1 ";
            if(positionId > 0)
                query += " and i_positionid = ? ";
            else if(!pids.equals(""))
                query += " and i_positionid IN ("+pids+") ";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            if(positionId > 0)
                pstmt.setInt(2, positionId);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                count1 = rs.getInt(1);
            }
            rs.close();
            sb.append("select count(DISTINCT(t_fcroledetail.i_pcategoryid)), count(DISTINCT(t_fcroledetail.i_pquestionid)) from t_fcroledetail ");
            sb.append("left join t_fcrole on (t_fcrole.i_fcroleid = t_fcroledetail.i_fcroleid) ");
            sb.append("left join t_pcategory on (t_pcategory.i_pcategoryid = t_fcroledetail.i_pcategoryid) ");
            sb.append("left join t_pquestion on (t_pquestion.i_pquestionid = t_fcroledetail.i_pquestionid) ");
            sb.append("where i_clientassetid = ? and t_pcategory.i_status = 1 and t_pquestion.i_status = 1 ");
            sb.append("and t_fcroledetail.i_status = 1 and t_fcrole.i_status = 1 ");
            if(positionId > 0)
                sb.append(" and t_fcrole.i_positionid = ? ");
            else if(!pids.equals(""))
                sb.append(" and t_fcrole.i_positionid IN ("+pids+") ");
            query = sb.toString();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            if(positionId > 0)
                pstmt.setInt(2, positionId);            
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                count2 = rs.getInt(1);
                count3 = rs.getInt(2);
            }
            rs.close();
            info = new FrameworkInfo(positionName, deptName, count1, count2, count3); 
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
    
    public ArrayList getCategoryList(int pcodeId, int pdeptId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select DISTINCT p.i_pcategoryid, t_pcategory.s_name from t_pcodequestion as p ");
        sb.append("left join t_pcategory on (t_pcategory.i_pcategoryid = p.i_pcategoryid) ");
        sb.append("left join t_pcode on (t_pcode.i_pcodeid = p.i_pcodeid) ");
        sb.append("where p.i_status = 1 and t_pcode.i_status = 1 and t_pcategory.i_status = 1 and p.i_pcodeid = ? and t_pcategory.i_pdeptid = ? ");
        sb.append("order by t_pcategory.s_name");
        String query = sb.toString();
        sb.setLength(0);        
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pcodeId);
            pstmt.setInt(2, pdeptId);            
            logger.info("getQuestionList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int pcategoryId;
            String name;
            while (rs.next()) 
            {
                pcategoryId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                list.add(new FrameworkInfo(pcategoryId, name));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getQuestionList(int pcodeId, int clientassetId, int positionId, int pdeptId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        if(positionId > 0)
            sb.append("select p.i_pquestionid, p.i_pcategoryid, q.s_name, t1.i_fcroledetailid from t_pcodequestion as p ");
        else
            sb.append("select p.i_pquestionid, p.i_pcategoryid, q.s_name, 0 from t_pcodequestion as p ");
        sb.append("left join t_pcategory on (t_pcategory.i_pcategoryid = p.i_pcategoryid) ");
        sb.append("left join t_pquestion as q on (q.i_pquestionid = p.i_pquestionid) ");
        sb.append("left join t_pcode on (t_pcode.i_pcodeid = p.i_pcodeid) ");
        if(positionId > 0)
            sb.append("left join (select t_fcroledetail.i_fcroledetailid, t_fcroledetail.i_pquestionid from t_fcroledetail left join t_fcrole on (t_fcrole.i_fcroleid = t_fcroledetail.i_fcroleid) where t_fcrole.i_status = 1 and t_fcroledetail.i_status = 1 and t_fcrole.i_clientassetid = ? and t_fcrole.i_positionid = ? and t_fcrole.i_pcodeid = ?) as t1 on (p.i_pquestionid = t1.i_pquestionid) ");
        sb.append("where p.i_status = 1 and t_pcode.i_status = 1 and t_pcategory.i_status = 1 and p.i_pcodeid = ? and t_pcategory.i_pdeptid = ?  ");
        sb.append("order by p.i_pcategoryid, q.s_name");
        String query = sb.toString();
        sb.setLength(0);        
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if(positionId > 0)
            {
                pstmt.setInt(++scc, clientassetId);
                pstmt.setInt(++scc, positionId);
                pstmt.setInt(++scc, pcodeId);
            }
            pstmt.setInt(++scc, pcodeId);
            pstmt.setInt(++scc, pdeptId);
            logger.info("getQuestionList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int pquestionId, pcategoryId, fcroledetailId;
            String name;
            while (rs.next()) 
            {
                pquestionId = rs.getInt(1);
                pcategoryId = rs.getInt(2);
                name = rs.getString(3) != null ? rs.getString(3) : "";
                fcroledetailId = rs.getInt(4);
                list.add(new FrameworkInfo(pquestionId, pcategoryId, name, fcroledetailId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getListFromList(ArrayList list, int pcategoryId) {
        ArrayList l = new ArrayList();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            FrameworkInfo info = (FrameworkInfo) list.get(i);
            if (info != null && info.getPcategoryId() == pcategoryId) {
                l.add(info);
            }
        }
        return l;
    }
    
    public boolean checkinlist(ArrayList list, int pcategoryId) 
    {
        boolean b = false;
        int size = list.size();
        for (int i = 0; i < size; i++)
        {
            FrameworkInfo info = (FrameworkInfo) list.get(i);
            if (info != null && info.getFcroledetailId() > 0 && info.getPcategoryId() == pcategoryId) 
            { 
                b = true;
                break;
            }
        }
        return b;
    }
    
    public int createQuestionDetail(int clientassetId, int positionId, int pcodeId, int priorityId, 
        int passessmenttypeId, int categoryId[], int questionId[], int userId, String pids, int monthId) 
    {
        int cc = 0;
        try 
        {
            String currdate = currDate1();
            conn = getConnection();
            if(pids != null && pids.equals("") && positionId > 0)
            {
                String query = "SELECT i_fcroleid FROM t_fcrole WHERE i_clientassetid = ? AND i_positionid = ? AND i_pcodeid = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                pstmt.setInt(2, positionId);
                pstmt.setInt(3, pcodeId);
                logger.info("createQuestionDetail :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int fcroleId = 0;
                while (rs.next()) 
                {
                    fcroleId = rs.getInt(1);
                }
                rs.close();  
                if(fcroleId <= 0)
                {
                    StringBuilder sb = new StringBuilder();
                    sb.append("INSERT INTO t_fcrole ");
                    sb.append("(i_clientassetid, i_positionid, i_pcodeid, i_priorityid, i_passessmenttypeid, i_status, i_userid, ts_regdate, ts_moddate, i_month) ");
                    sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    query = sb.toString();
                    sb.setLength(0);

                    pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setInt(1, clientassetId);
                    pstmt.setInt(2, positionId);
                    pstmt.setInt(3, pcodeId);
                    pstmt.setInt(4, priorityId);
                    pstmt.setInt(5, passessmenttypeId);
                    pstmt.setInt(6, 1);
                    pstmt.setInt(7, userId);
                    pstmt.setString(8, currdate);
                    pstmt.setString(9, currdate);
                    pstmt.setInt(10, monthId);
                    print(this, "insert into t_fcrole :: " + pstmt.toString());
                    pstmt.executeUpdate();
                    rs = pstmt.getGeneratedKeys();
                    while (rs.next()) 
                    {
                        fcroleId = rs.getInt(1);
                    } 
                    rs.close();
                }
                query = "UPDATE t_fcrole set i_priorityid = ?, i_passessmenttypeid = ?, i_userid = ?, ts_moddate = ? where i_fcroleid = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, priorityId);
                pstmt.setInt(2, passessmenttypeId);
                pstmt.setInt(3, userId);
                pstmt.setString(4, currdate);
                pstmt.setInt(5, fcroleId);
                pstmt.executeUpdate();

                StringBuilder sb = new StringBuilder();
                sb.append("insert into t_fcroledetail ");
                sb.append("(i_fcroleid, i_pcategoryid, i_pquestionid, i_status, i_userid, ts_regdate, ts_moddate) ");
                sb.append("values (?, ?, ?, ?, ?, ?, ?)");
                query = (sb.toString()).intern();
                sb.setLength(0);    

                String delquery = "delete from t_fcroledetail where i_fcroleid = ?";
                pstmt = conn.prepareStatement(delquery);
                pstmt.setInt(1, fcroleId);
                print(this, "delete all :: " + pstmt.toString());
                pstmt.executeUpdate();

                int qlen = questionId.length;
                pstmt = conn.prepareStatement(query);
                int added = 0;
                for (int i = 0; i < qlen; i++)
                {                    
                    if (categoryId[i] > 0 && questionId[i] > 0) 
                    {
                        added++;
                        pstmt.setInt(1, fcroleId);
                        pstmt.setInt(2, categoryId[i]);
                        pstmt.setInt(3, questionId[i]);
                        pstmt.setInt(4, 1);
                        pstmt.setInt(5, userId);
                        pstmt.setString(6, currdate);
                        pstmt.setString(7, currdate);
                        print(this, "createQuestionDetail :: " + pstmt.toString());
                        cc += pstmt.executeUpdate();
                    }
                }
                if(added <= 0)
                {
                    query = "delete from t_fcrole where i_clientassetid = ? and i_positionid = ? and i_pcodeid = ?";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, clientassetId);
                    pstmt.setInt(2, positionId);
                    pstmt.setInt(3, pcodeId);
                    logger.info("delete t_fcrole :: " + pstmt.toString());
                    pstmt.executeUpdate();
                }
            }
            else
            {
                String arr[] = parseCommaDelimString(pids);
                if(arr != null)
                {
                    int len = arr.length;
                    for(int a = 0; a < len; a++)
                    {
                        if(arr[a] != null && !arr[a].equals(""))
                        {
                            positionId = Integer.parseInt(arr[a]);
                            String query = "select i_fcroleid from t_fcrole where i_clientassetid = ? and i_positionid = ? and i_pcodeid = ?";
                            pstmt = conn.prepareStatement(query);
                            pstmt.setInt(1, clientassetId);
                            pstmt.setInt(2, positionId);
                            pstmt.setInt(3, pcodeId);
                            logger.info("createQuestionDetail :: " + pstmt.toString());
                            rs = pstmt.executeQuery();
                            int fcroleId = 0;
                            while (rs.next()) 
                            {
                                fcroleId = rs.getInt(1);
                            }
                            rs.close(); 
                            if(fcroleId <= 0)
                            {
                                StringBuilder sb = new StringBuilder();
                                sb.append("insert into t_fcrole ");
                                sb.append("(i_clientassetid, i_positionid, i_pcodeid, i_priorityid, i_passessmenttypeid, i_status, i_userid, ts_regdate, ts_moddate) ");
                                sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                                query = sb.toString();
                                sb.setLength(0);

                                pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                                pstmt.setInt(1, clientassetId);
                                pstmt.setInt(2, positionId);
                                pstmt.setInt(3, pcodeId);
                                pstmt.setInt(4, priorityId);
                                pstmt.setInt(5, passessmenttypeId);
                                pstmt.setInt(6, 1);
                                pstmt.setInt(7, userId);
                                pstmt.setString(8, currdate);
                                pstmt.setString(9, currdate);
                                print(this, "insert into t_fcrole :: " + pstmt.toString());
                                pstmt.executeUpdate();
                                rs = pstmt.getGeneratedKeys();
                                while (rs.next()) 
                                {
                                    fcroleId = rs.getInt(1);
                                } 
                                rs.close();
                            }  
                            query = "UPDATE t_fcrole set i_priorityid = ?, i_passessmenttypeid = ?, i_userid = ?, ts_moddate = ? where i_fcroleid = ?";
                            pstmt = conn.prepareStatement(query);
                            pstmt.setInt(1, priorityId);
                            pstmt.setInt(2, passessmenttypeId);
                            pstmt.setInt(3, userId);
                            pstmt.setString(4, currdate);
                            pstmt.setInt(5, fcroleId);
                            pstmt.executeUpdate();

                            StringBuilder sb = new StringBuilder();
                            sb.append("insert into t_fcroledetail ");
                            sb.append("(i_fcroleid, i_pcategoryid, i_pquestionid, i_status, i_userid, ts_regdate, ts_moddate) ");
                            sb.append("values (?, ?, ?, ?, ?, ?, ?)");
                            query = (sb.toString()).intern();
                            sb.setLength(0);    

                            String delquery = "delete from t_fcroledetail where i_fcroleid = ?";
                            pstmt = conn.prepareStatement(delquery);
                            pstmt.setInt(1, fcroleId);
                            print(this, "delete all :: " + pstmt.toString());
                            pstmt.executeUpdate();

                            int qlen = questionId.length;
                            pstmt = conn.prepareStatement(query);
                            int added = 0;
                            for (int i = 0; i < qlen; i++)
                            {
                                if (categoryId[i] > 0 && questionId[i] > 0) 
                                {
                                    added++;
                                    pstmt.setInt(1, fcroleId);
                                    pstmt.setInt(2, categoryId[i]);
                                    pstmt.setInt(3, questionId[i]);
                                    pstmt.setInt(4, 1);
                                    pstmt.setInt(5, userId);
                                    pstmt.setString(6, currdate);
                                    pstmt.setString(7, currdate);
                                    print(this, "createQuestionDetail :: " + pstmt.toString());
                                    cc += pstmt.executeUpdate();
                                }
                            }
                            if(added <= 0)
                            {
                                if(added <= 0)
                                {
                                    query = "delete from t_fcrole where i_clientassetid = ? and i_positionid = ? and i_pcodeid = ?";
                                    pstmt = conn.prepareStatement(query);
                                    pstmt.setInt(1, clientassetId);
                                    pstmt.setInt(2, positionId);
                                    pstmt.setInt(3, pcodeId);
                                    logger.info("delete t_fcrole :: " + pstmt.toString());
                                    pstmt.executeUpdate();
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception exception) {
            print(this, "createQuestionDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int deleterole(int clientassetId, int positionId, int status, int userId) 
    {
        int cc = 0;
        try 
        {
            String currdate = currDate1();
            conn = getConnection();
            String query = "UPDATE t_fcrole set i_status = ?, i_userid = ?, ts_moddate = ? where i_clientassetid = ? and i_positionid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, status);
            pstmt.setInt(2, userId);
            pstmt.setString(3, currdate);
            pstmt.setInt(4, clientassetId);            
            pstmt.setInt(5, positionId);
            pstmt.executeUpdate();
        } 
        catch (Exception exception) 
        {
            print(this, "deleterole :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public ArrayList getPositionlistfordept(String pids) 
    {
        ArrayList positionlist = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("Select t_position.s_name, t_grade.s_name ");
        sb.append("FROM t_position left join t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("where t_position.i_positionid IN ("+pids+") ");
        sb.append("order by t_position.s_name, t_grade.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            logger.info(" getPositionlistfordept:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String positionName, gradeName;
            while (rs.next()) 
            {
                positionName = rs.getString(1) != null ? rs.getString(1) : "";
                gradeName = rs.getString(2) != null ? rs.getString(2) : "";
                positionlist.add(new FrameworkInfo(positionName, gradeName));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return positionlist;
    }
    
    public ArrayList getExcelQuestion(int clientassetId) 
    {
        ArrayList positionlist = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_pquestion.s_name, t_pcategory.s_name, t_position.s_name, t_grade.s_name, t_pcode.s_code, t_pcode.s_role, ");
        sb.append("t_passessmenttype.s_name, t_priority.s_name, t_fcrole.i_month ");
        sb.append("from t_fcroledetail left join t_pquestion on (t_pquestion.i_pquestionid = t_fcroledetail.i_pquestionid) ");
        sb.append("left join t_pcategory on (t_pcategory.i_pcategoryid = t_fcroledetail.i_pcategoryid) ");
        sb.append("left join t_fcrole on (t_fcrole.i_fcroleid = t_fcroledetail.i_fcroleid) ");
        sb.append("left join t_position on (t_position.i_positionid = t_fcrole.i_positionid) ");
        sb.append("left join t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("left join t_pcode on (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) ");
        sb.append("left join t_passessmenttype on (t_passessmenttype.i_passessmenttypeid = t_fcrole.i_passessmenttypeid) ");
        sb.append("left join t_priority on (t_priority.i_priorityid = t_fcrole.i_priorityid) ");
        sb.append("where t_fcrole.i_status = 1 and t_fcroledetail.i_status = 1 and t_fcrole.i_clientassetid = ? order by t_position.s_name, t_grade.s_name, t_pcode.s_code, t_pcategory.s_name, t_pquestion.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            logger.info(" getExcelQuestion:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String questionName, categoryName, positionName, gradeName, code, role, passessmenttypeName, priorityName;
            int month;
            while (rs.next()) 
            {
                questionName = rs.getString(1) != null ? rs.getString(1) : "";
                categoryName = rs.getString(2) != null ? rs.getString(2) : "";
                positionName = rs.getString(3) != null ? rs.getString(3) : "";
                gradeName = rs.getString(4) != null ? rs.getString(4) : "";
                code = rs.getString(5) != null ? rs.getString(5) : "";
                role = rs.getString(6) != null ? rs.getString(6) : ""; 
                passessmenttypeName = rs.getString(7) != null ? rs.getString(7) : ""; 
                priorityName = rs.getString(8) != null ? rs.getString(8) : ""; 
                month = rs.getInt(9);
                if(!gradeName.equals(""))
                    positionName += " | " + gradeName;
                if(!role.equals(""))
                    code += " | " + role;
                positionlist.add(new FrameworkInfo(positionName, code, categoryName, questionName,
                    passessmenttypeName, priorityName, month));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return positionlist;
    }
    
    public Collection getPriorities(int clientassetId, int pdeptId) 
    {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_priorityid, s_name FROM t_priority WHERE i_status = 1 ");
        sb.append("AND i_clientassetid = ? and FIND_IN_SET(?, s_pdeptids) order by i_degree");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new FrameworkInfo(-1, "Set Priority"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, pdeptId);
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) 
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new FrameworkInfo(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
    
    public int[] getIdList(int clientassetId, int positionId, int pcodeId) 
    {
        int arr[] = new int[3];
        int passessmenttypeId = 0, priorityId = 0, month = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_passessmenttypeid, i_priorityid, i_month ");
        sb.append("from t_fcrole where i_clientassetid = ? and i_positionid = ? and i_pcodeid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, positionId);
            pstmt.setInt(3, pcodeId);
            logger.info(" getIdList:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                passessmenttypeId = rs.getInt(1);
                priorityId = rs.getInt(2);
                month = rs.getInt(3);
            }
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        arr[0] = passessmenttypeId;
        arr[1] = priorityId;
        arr[2] = month;
        return arr;
    }
    }