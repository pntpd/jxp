package com.web.jxp.managewellness;
import com.web.jxp.base.Base;
import static com.web.jxp.base.Base.parseCommaDelimString;
import static com.web.jxp.common.Common.print;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Collection;
import java.util.LinkedList;
import java.util.TimeZone;
import java.util.logging.Logger;

public class Managewellness extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getManagewellnessByName(int clientIdIndex, int assetIdIndex, int positionIdIndex, int mode, String search,
        int catIdIndex, int subcatIdIndex)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        if(clientIdIndex > 0 && assetIdIndex > 0)
        {
            if(mode == 1)
            {
                sb.append("SELECT cr.i_crewrotationid,  CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name, t1.ct, cr.i_candidateid ");
                sb.append("FROM t_crewrotation AS cr LEFT JOIN t_candidate AS c ON (c.i_candidateid = cr.i_candidateid) ");
                sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
                sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
                sb.append("LEFT JOIN (select i_crewrotationid, count(1) as ct from t_survey where i_status = 2 group by i_crewrotationid) AS t1 ON (cr.i_crewrotationid = t1.i_crewrotationid) ");
                sb.append("where  cr.i_active = 1  and cr.i_clientid = ? and cr.i_clientassetid = ? ");
                if(search != null && !search.equals(""))
                    sb.append(" AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ?) ");  
                if(positionIdIndex > 0)
                    sb.append(" and c.i_positionid = ? ");
                sb.append(" ORDER BY c.s_firstname, c.s_middlename, c.s_lastname ");
            }
            else
            {
                sb.append("select DISTINCT swf.i_subcategorywfid, swf.s_name, cwf.s_name, DATE_FORMAT(t_schedule.d_fromdate, '%d %b %Y'), DATE_FORMAT(t_schedule.d_todate, '%d %b %Y'), t1.ct, t2.ct , swf.i_repeatdp, swf.i_schedulecb, swf.i_categorywfid ");
                sb.append("from t_wellnessmatrix left join t_subcategorywf as swf ON (t_wellnessmatrix.i_subcategorywfid = swf.i_subcategorywfid and t_wellnessmatrix.i_clientassetid = ?) ");
                sb.append("LEFT JOIN t_categorywf as cwf ON (cwf.i_categorywfid = swf.i_categorywfid) ");
                sb.append("left join t_schedule ON (t_schedule.i_subcategorywfid = swf.i_subcategorywfid and t_schedule.i_clientassetid = ?) ");
                sb.append("left join (select i_subcategorywfid, count(distinct(i_positionid)) as ct from t_wellnessmatrix left join t_categorywf on (t_categorywf.i_categorywfid = t_wellnessmatrix.i_categorywfid) where t_categorywf.i_status = 1 and i_clientassetid = ? group by i_subcategorywfid) ");
                sb.append("as t1 ON (t1.i_subcategorywfid = swf.i_subcategorywfid) ");
                sb.append("left join (select i_subcategorywfid, count(distinct(i_questionid)) as ct from t_wellnessmatrix   left join t_categorywf on (t_categorywf.i_categorywfid = t_wellnessmatrix.i_categorywfid) where t_categorywf.i_status = 1 and i_clientassetid = ? group by i_subcategorywfid) ");
                sb.append("as t2 ON (t2.i_subcategorywfid = swf.i_subcategorywfid) ");
                sb.append("where swf.i_status = 1 and cwf.i_status = 1 and  t_wellnessmatrix.i_clientassetid = ? ");
                if (catIdIndex > 0) {
                    sb.append(" and cwf.i_categorywfid = ? ");
                }
                if (subcatIdIndex > 0) {
                    sb.append(" and t_wellnessmatrix.i_subcategorywfid = ? ");
                }
                if (search != null && !search.equals("")) {
                    sb.append(" and (swf.s_name like ? OR cwf.s_name like ?) ");
                }
                sb.append(" order by swf.s_name ");
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
                    if(search != null && !search.equals(""))
                    {
                        pstmt.setString(++scc, "%"+(search)+"%");
                        pstmt.setString(++scc, "%"+(search)+"%");
                    }
                    if (positionIdIndex > 0)
                        pstmt.setInt(++scc, positionIdIndex);
                }
                else
                {
                    pstmt.setInt(++scc, assetIdIndex);
                    pstmt.setInt(++scc, assetIdIndex);
                    pstmt.setInt(++scc, assetIdIndex);
                    pstmt.setInt(++scc, assetIdIndex);
                    pstmt.setInt(++scc, assetIdIndex);
                    if (catIdIndex > 0)
                        pstmt.setInt(++scc, catIdIndex);
                    if (subcatIdIndex > 0)
                        pstmt.setInt(++scc, subcatIdIndex);
                    
                    if(search != null && !search.equals(""))
                    {
                        pstmt.setString(++scc, "%"+(search)+"%");
                        pstmt.setString(++scc, "%"+(search)+"%");
                    }
                }
                logger.info("getManagewellnessByName :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String name, positionName, gradeName, repeatvalue = "";
                int total, crewrotationId, candidateId;
                while (rs.next())
                {
                    if(mode == 1)
                    {
                        crewrotationId = rs.getInt(1);
                        name = rs.getString(2) != null ? rs.getString(2) : "";
                        positionName = rs.getString(3) != null ? rs.getString(3) : "";
                        gradeName = rs.getString(4) != null ? rs.getString(4) : "";
                        total = rs.getInt(5);
                        candidateId = rs.getInt(6);
                        if(!gradeName.equals(""))
                            positionName += " - " + gradeName;
                        list.add(new ManagewellnessInfo(crewrotationId, name, positionName, total,0,"","","","",0,0,"", 0,candidateId,0));
                    }
                    else
                    {
                        int subcategoryId = rs.getInt(1);
                        String subcategoryName = rs.getString(2) != null ? rs.getString(2) : "";
                        String categoryName = rs.getString(3) != null ? rs.getString(3) : "";
                        String fromDate = rs.getString(4) != null ? rs.getString(4) : "";
                        String toDate = rs.getString(5) != null ? rs.getString(5) : "";
                        int pCount = rs.getInt(6);
                        int categoryCount = rs.getInt(7);
                        int repeatdp = rs.getInt(8);
                        int schedulecb = rs.getInt(9);
                        int categoryId = rs.getInt(10);
                        if(schedulecb == 1)
                        {
                        repeatvalue = getrepeatvalue(repeatdp);
                        }
                        else
                        {
                            repeatvalue = "-";
                        }
                        list.add(new ManagewellnessInfo(0,"", "", 0,subcategoryId, fromDate, toDate, categoryName, subcategoryName,pCount,categoryCount,repeatvalue, schedulecb,0,categoryId));
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
        ManagewellnessInfo info;
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
                    info = (ManagewellnessInfo) l.get (i);
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
            ManagewellnessInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (ManagewellnessInfo) l.get(i);
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
    public String getInfoValue(ManagewellnessInfo info, String i)
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
        coll.add(new ManagewellnessInfo(-1, " Select Client"));
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
                coll.add(new ManagewellnessInfo(refId, refName));
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
            coll.add(new ManagewellnessInfo(-1, " Select Asset "));
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
                    coll.add(new ManagewellnessInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new ManagewellnessInfo(-1, " Select Asset "));
        }
        return coll;
    }
    
    public Collection getPositions(int clientassetId)
    {
        Collection coll = new LinkedList();
        coll.add(new ManagewellnessInfo(-1, " All "));
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
                    coll.add(new ManagewellnessInfo(id, name));
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
        coll.add(new ManagewellnessInfo(-1, "Select Category"));
        if(clientassetId > 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT DISTINCT c.i_categorywfid, t_categorywf.s_name ");
            sb.append("FROM t_wellnessmatrix as c left join t_categorywf on (t_categorywf.i_categorywfid = c.i_categorywfid) ");
            sb.append("where t_categorywf.i_status = 1 and c.i_clientassetid = ? order by t_categorywf.s_name");
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
                    coll.add(new ManagewellnessInfo(id, name));
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
        coll.add(new ManagewellnessInfo(-1, "Select Sub-category"));
        if(clientassetId > 0 && categoryId > 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT DISTINCT c.i_subcategorywfid, t_subcategorywf.s_name ");
            sb.append("FROM t_wellnessmatrix as c left join t_subcategorywf on (t_subcategorywf.i_subcategorywfid = c.i_subcategorywfid) ");
            sb.append("where t_subcategorywf.i_status = 1 and c.i_clientassetid = ? and c.i_categorywfid = ? order by t_subcategorywf.s_name");
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
                    coll.add(new ManagewellnessInfo(id, name));
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
        ManagewellnessInfo info;
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
                    info = (ManagewellnessInfo) l.get (i);
                    record.put(getInfoValue2(info, colId), info);
                }
            }
            Map map = null;
            if(colId.equals("4") || colId.equals("5"))
                map = sortById(record, tp);
            else
                map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            ManagewellnessInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (ManagewellnessInfo) l.get(i);
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
    public String getInfoValue2(ManagewellnessInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
             infoval = info.getSubcategoryName()!= null ? info.getSubcategoryName() : "";
        else if(i != null && i.equals("2"))
            infoval = info.getCategoryName()!= null ? info.getCategoryName() : "";    
        else if(i != null && i.equals("3"))
            infoval = ""+info.getQuestCount(); 
        else if(i != null && i.equals("4"))
            infoval = ""+info.getPcount(); 
        else if(i != null && i.equals("5"))
            infoval = info.getRepeatvalue()!= null ? info.getRepeatvalue() : "";    
        else if(i != null && i.equals("6"))
            infoval = info.getFromdate()!= null ? info.getFromdate() : "";    
        else if(i != null && i.equals("7"))
            infoval = info.getTodate()!= null ? info.getTodate() : "";    
        return infoval;
    }
    
    public int[] getCounts(int assetIdIndex, int crewrotationId, int subcategorywfId)
    {
        int arr[] = new int[8];
        int total_position = 0, total_candidate = 0, filledin1 = 0, filledin3 = 0, filledin5 = 0, total_feedback = 0,
            filled = 0, sent = 0;
        if(assetIdIndex > 0)
        {  
            try
            {   String query = "";
                conn = getConnection();
                if(crewrotationId <= 0)
                {
                    query = ("SELECT count(DISTINCT(c.i_positionid)) as ct from t_crewrotation as cr left join t_candidate as c on (c.i_candidateid = cr.i_candidateid) where cr.i_active = 1 and cr.i_clientassetid = ? ");
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, assetIdIndex);
                    logger.info("total_position :: " + pstmt.toString());
                    rs = pstmt.executeQuery();
                    while (rs.next())
                    {
                        total_position = rs.getInt(1);                    
                    }
                    rs.close();

                    query = ("SELECT count(1) as ct from t_crewrotation where i_active = 1 and i_clientassetid = ?");
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, assetIdIndex);
                    logger.info("total_candidate :: " + pstmt.toString());
                    rs = pstmt.executeQuery();
                    while (rs.next())
                    {
                        total_candidate = rs.getInt(1);                    
                    }
                    rs.close();
                }
               
                StringBuilder sb = new StringBuilder();
                sb.append(" SELECT count(1) from t_survey left join t_crewrotation as cr ON (cr.i_crewrotationid = t_survey.i_crewrotationid)  where t_survey.i_status = 1 AND (t_survey.ts_expirydate > NOW() AND t_survey.ts_expirydate <= NOW() + INTERVAL '1' DAY) AND cr.i_clientassetid = ? ");
               if(crewrotationId > 0)
                   sb.append(" and t_survey.i_crewrotationid = ? ");
               if(subcategorywfId > 0)
                   sb.append(" and t_survey.i_subcategorywfid = ? ");
                query = sb.toString();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                int pcc = 0;
                pstmt.setInt(++pcc, assetIdIndex);
                if(crewrotationId > 0)
                    pstmt.setInt(++pcc, crewrotationId);
                if(subcategorywfId > 0)
                    pstmt.setInt(++pcc, subcategorywfId);
                logger.info("getCounts :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    filledin1 = rs.getInt(1);                    
                }
                rs.close();
               
                sb.append("SELECT count(1) from t_survey left join t_crewrotation as cr ON (cr.i_crewrotationid = t_survey.i_crewrotationid) where t_survey.i_status = 1 AND  (t_survey.ts_expirydate > NOW() + INTERVAL '1' DAY AND t_survey.ts_expirydate <= NOW() + INTERVAL '3' DAY) AND cr.i_clientassetid = ? ");
                if(crewrotationId > 0)
                   sb.append(" and t_survey.i_crewrotationid = ? ");
                if(subcategorywfId > 0)
                   sb.append(" and t_survey.i_subcategorywfid = ? ");
                query = sb.toString();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                pcc = 0;
                pstmt.setInt(++pcc, assetIdIndex);
                if(crewrotationId > 0)
                    pstmt.setInt(++pcc, crewrotationId);
                if(subcategorywfId > 0)
                    pstmt.setInt(++pcc, subcategorywfId);
                logger.info("getCounts :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    filledin3 = rs.getInt(1);                    
                }
                rs.close();
                
                sb.append("SELECT count(1) from t_survey left join t_crewrotation as cr ON (cr.i_crewrotationid = t_survey.i_crewrotationid) where t_survey.i_status = 1 AND  (t_survey.ts_expirydate > NOW() + INTERVAL '3' DAY AND t_survey.ts_expirydate <= NOW() + INTERVAL '5' DAY) AND cr.i_clientassetid = ? ");
                if(crewrotationId > 0)
                   sb.append(" and t_survey.i_crewrotationid = ? ");
                if(subcategorywfId > 0)
                   sb.append(" and t_survey.i_subcategorywfid = ? ");
                query = sb.toString();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                pcc = 0;
                pstmt.setInt(++pcc, assetIdIndex);
                if(crewrotationId > 0)
                    pstmt.setInt(++pcc, crewrotationId);
                if(subcategorywfId > 0)
                    pstmt.setInt(++pcc, subcategorywfId);
                logger.info("getCounts :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    filledin5 = rs.getInt(1);                    
                }
                rs.close();               
                
                sb.append("SELECT count(1) from t_survey left join t_crewrotation as cr ON (cr.i_crewrotationid = t_survey.i_crewrotationid) where t_survey.i_status = 2 and cr.i_clientassetid = ? ");
                if(crewrotationId > 0)
                   sb.append(" and t_survey.i_crewrotationid = ? ");
                if(subcategorywfId > 0)
                   sb.append(" and t_survey.i_subcategorywfid = ? ");
                query = sb.toString();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                pcc = 0;
                pstmt.setInt(++pcc, assetIdIndex);
                if(crewrotationId > 0)
                    pstmt.setInt(++pcc, crewrotationId);
                if(subcategorywfId > 0)
                    pstmt.setInt(++pcc, subcategorywfId);
                logger.info("getCounts :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    filled = rs.getInt(1);                    
                }
                rs.close();
               
                sb.append("SELECT count(1) from t_survey left join t_crewrotation as cr ON (cr.i_crewrotationid = t_survey.i_crewrotationid) where t_survey.i_status = 1 AND t_survey.ts_expirydate > NOW()  and cr.i_clientassetid = ? ");
                if(crewrotationId > 0)
                   sb.append(" and t_survey.i_crewrotationid = ? ");
                if(subcategorywfId > 0)
                   sb.append(" and t_survey.i_subcategorywfid = ? ");
                query = sb.toString();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                pcc = 0;
                pstmt.setInt(++pcc, assetIdIndex);
                if(crewrotationId > 0)
                    pstmt.setInt(++pcc, crewrotationId);
                if(subcategorywfId > 0)
                    pstmt.setInt(++pcc, subcategorywfId);
                logger.info("getCounts :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    sent = rs.getInt(1);                    
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
        total_feedback = filled + sent;
        arr[0] = total_position;
        arr[1] = total_candidate;
        arr[2] = filledin1;
        arr[3] = filledin3;
        arr[4] = filledin5;
        arr[5] = total_feedback;
        arr[6] = filled;
        arr[7] = sent;        
        return arr;
    }
    
    public ManagewellnessInfo getBasicDetail(int crewrotationId)
    {
        ManagewellnessInfo info = null;
        if(crewrotationId > 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name, t_client.s_name, t_clientasset.s_name, ");
            sb.append("t_crewrotation.i_clientid, t_crewrotation.i_clientassetid, c.i_positionid, t_crewrotation.i_candidateid ");
            sb.append("FROM t_crewrotation left join t_candidate as c on (c.i_candidateid = t_crewrotation.i_candidateid) ");
            sb.append("left join t_client on (t_client.i_clientid = t_crewrotation.i_clientid) ");
            sb.append("left join t_clientasset on (t_clientasset.i_clientassetid = t_crewrotation.i_clientassetid) ");
            sb.append("left join t_position on (t_position.i_positionid = c.i_positionid) ");
            sb.append("left join t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("where t_crewrotation.i_crewrotationid = ?");
            String query = sb.toString();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, crewrotationId);
                logger.info("getBasicDetail :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String name, positionName, gradeName, clientName, assetName;
                int clientId, clientassetId, positionId, candidateId;
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
                    candidateId = rs.getInt(9);
                    if(!gradeName.equals(""))
                        positionName += " - " + gradeName;
                    info = new ManagewellnessInfo(crewrotationId, clientId, clientassetId, name, positionName, 
                        clientName, assetName, positionId, candidateId);
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
    
    public ArrayList getListAssign1( int clientIdIndex,int assetIdIndex, int positionIdIndex, 
        int crewrotationId, String search, int statusIndex, int cid, int scid)
    {
        ArrayList list = new ArrayList();
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT distinct t_subcategorywf.s_name,  t_categorywf.s_name, t1.ct1, t_subcategorywf.i_repeatdp,DATE_FORMAT(t_schedule.d_fromdate, '%d %b %Y'), DATE_FORMAT(t_schedule.d_todate, '%d %b %Y') , t_schedule.i_status, t_subcategorywf.i_subcategorywfid,  t_subcategorywf.i_schedulecb  FROM t_wellnessmatrix ");
            sb.append("left join t_subcategorywf on (t_wellnessmatrix.i_subcategorywfid = t_subcategorywf.i_subcategorywfid) ");
            sb.append("left join t_categorywf on (t_categorywf.i_categorywfid = t_wellnessmatrix.i_categorywfid) ");
            sb.append("left join (select i_subcategorywfid,  count(i_questionid) as ct1 from t_wellnessmatrix left join t_categorywf on (t_categorywf.i_categorywfid = t_wellnessmatrix.i_categorywfid) where t_categorywf.i_status = 1 and i_clientassetid = ? and i_positionid = ? group by i_subcategorywfid) as t1 on (t_wellnessmatrix.i_subcategorywfid = t1.i_subcategorywfid) ");
            sb.append("left join t_schedule ON (t_schedule.i_subcategorywfid = t_subcategorywf.i_subcategorywfid and t_schedule.i_clientassetid = ?) ");
            sb.append(" where t_subcategorywf.i_status = 1 and t_categorywf.i_status = 1 and  t_wellnessmatrix.i_clientassetid = ? and t_wellnessmatrix.i_positionid = ? ");

            if (search != null && !search.equals("")) {
                sb.append(" AND (t_subcategorywf.s_name LIKE ? OR t_categorywf.s_name LIKE ?) ");
            }
            if(cid > 0)
            {
                sb.append(" and t_wellnessmatrix.i_categorywfid = ? ");
            }
            if(scid > 0)
            {
                sb.append(" and t_wellnessmatrix.i_subcategorywfid = ? ");
            }
            if(statusIndex == 1)
            {
                sb.append(" and (t_schedule.d_todate = '0000-00-00' OR t_schedule.d_todate is NULL) ");
            }
            else if(statusIndex == 2)
            {
                sb.append(" and (t_schedule.d_todate != '0000-00-00' )");
            }
            sb.append(" order by t_subcategorywf.s_name ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setInt(++scc, positionIdIndex);
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setInt(++scc, positionIdIndex);
            
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+search+"%");
                pstmt.setString(++scc, "%"+search+"%");
            }
            if(cid > 0)
            {
                pstmt.setInt(++scc, cid);
            }
            if(scid > 0)
            {
                pstmt.setInt(++scc, scid);
            }
            
            logger.info("getListAssign1 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String fromdate, todate, categoryName, subcategoryName, repeatvalue = "";
            int  status, repeatdp, questCount, subcategoryId, schedulecb;
            while (rs.next())
            {
                subcategoryName = rs.getString(1) != null ? rs.getString(1) : "";
                categoryName = rs.getString(2) != null ? rs.getString(2) : "";
                questCount = rs.getInt(3);
                repeatdp = rs.getInt(4);
                fromdate = rs.getString(5) != null ? rs.getString(5) : "";
                todate = rs.getString(6) != null ? rs.getString(6) : "";
                status = rs.getInt(7);
                subcategoryId = rs.getInt(8);
                schedulecb = rs.getInt(9);
                if(schedulecb == 1)
                {
                repeatvalue = getrepeatvalue(repeatdp);
                }
                else
                {
                    repeatvalue = "-";
                }
                
                list.add(new ManagewellnessInfo(categoryName, subcategoryName, questCount, repeatvalue, fromdate, todate, status, subcategoryId, schedulecb));
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
    
    public ArrayList getQuestionList( int clientIdIndex,int assetIdIndex, String cids)
    {
        ArrayList list = new ArrayList();
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("select DISTINCT swf.s_name, cwf.s_name, swf.i_repeatdp, swf.i_schedulecb ");
            sb.append("from t_wellnessmatrix left join t_subcategorywf as swf ON (t_wellnessmatrix.i_subcategorywfid = swf.i_subcategorywfid and t_wellnessmatrix.i_clientassetid = ?) ");
            sb.append("LEFT JOIN t_categorywf as cwf ON (cwf.i_categorywfid = swf.i_categorywfid) ");
            sb.append("where t_wellnessmatrix.i_clientassetid = ? and t_wellnessmatrix.i_subcategorywfid in ("+cids+")");
            
            String query = (sb.toString()).intern();
            sb.setLength(0);
            
            sb.append(" order by t_subcategorywf.s_name ");
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setInt(++scc, assetIdIndex);
            
            logger.info("getListAssign1 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String categoryName, subcategoryName, repeatvalue = "";
            int  repeatdp, schedulecb;
            while (rs.next())
            {
                subcategoryName = rs.getString(1) != null ? rs.getString(1) : "";
                categoryName = rs.getString(2) != null ? rs.getString(2) : "";
                repeatdp = rs.getInt(3);
                schedulecb = rs.getInt(4);
                if(schedulecb == 1)
                {
                    repeatvalue = getrepeatvalue(repeatdp);
                }
                else
                {
                    repeatvalue = "-";
                }
                list.add(new ManagewellnessInfo(categoryName, subcategoryName, repeatvalue, schedulecb));
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
    
     public int updateschedule(int clientIdIndex, int assetIdIndex, int subcategoryId, int uId) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_schedule set ");
            sb.append("d_fromdate = '0000-00-00', ");
            sb.append("d_todate = '0000-00-00', ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_clientid = ? and i_clientassetid = ? and i_subcategorywfid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, uId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, clientIdIndex);
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setInt(++scc, subcategoryId);
            //print(this,"updateschedule :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateschedule :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
     
     public int createSchedule(int clientIdIndex, int assetIdIndex, String crids, int uId, String fromDate, String toDate) {
        int cc = 0;
        try 
        {
            conn = getConnection();
            String carr[] = parseCommaDelimString(crids);
            print(this,"createSchedule+carr.length: "+carr.length);
            if(carr != null)
            {
                int len = carr.length;
                for(int i = 0; i < len; i++)
                {
                    int subcategoryId = Integer.parseInt(carr[i]);
                    if(subcategoryId > 0)
                    {
                        String cq = "select i_scheduleid from t_schedule where i_clientid = ? and i_clientassetid = ? and i_subcategorywfid = ? ";
                        pstmt = conn.prepareStatement(cq);
                        pstmt.setInt(1, clientIdIndex);
                        pstmt.setInt(2, assetIdIndex);
                        pstmt.setInt(3, subcategoryId);
                        logger.info("createSchedule :: " + pstmt.toString());
                        rs = pstmt.executeQuery();
                        int scheduleId = 0;
                        while (rs.next()) {
                            scheduleId = rs.getInt(1);
                        }
                        if(scheduleId > 0)
                        {
                            StringBuilder sb = new StringBuilder();
                            sb.append("UPDATE t_schedule SET  d_fromdate = ?, d_todate = ?, ");
                            sb.append("i_userid = ?, ts_moddate = ? where i_scheduleid = ? ");
                            String query = (sb.toString()).intern();
                            sb.setLength(0);
                            pstmt = conn.prepareStatement(query);
                            pstmt.setString(1, changeDate1(fromDate));
                            pstmt.setString(2, changeDate1(toDate));
                            pstmt.setInt(3, uId);
                            pstmt.setString(4, currDate1());
                            pstmt.setInt(5, scheduleId);
                            pstmt.executeUpdate();
                        }
                        else
                        {
                            StringBuilder sb = new StringBuilder();            
                            sb.append("insert into t_schedule ");
                            sb.append("(i_clientid, i_clientassetid, i_subcategorywfid, d_fromdate, d_todate, i_status, i_userid, ts_regdate, ts_moddate) ");
                            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                            String query = (sb.toString()).intern();
                            sb.setLength(0);
                            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                            pstmt.setInt(1, clientIdIndex);
                            pstmt.setInt(2, assetIdIndex);
                            pstmt.setInt(3, subcategoryId);
                            pstmt.setString(4, changeDate1(fromDate));
                            pstmt.setString(5, changeDate1(toDate));
                            pstmt.setInt(6, 1);
                            pstmt.setInt(7, uId);
                            pstmt.setString(8, currDate1());
                            pstmt.setString(9, currDate1());
                            logger.info("createSchedule :: " + pstmt.toString());
                            pstmt.executeUpdate();
                            rs = pstmt.getGeneratedKeys();
                            while (rs.next())
                            {
                                cc = rs.getInt(1);
                            }
                        }
                    }
                }
            }
            
        } catch (Exception exception) {
            print(this, "createSchedule :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public String getrepeatvalue(int id)
    {
        String s1 = "";
        switch (id) {
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
    
    public ManagewellnessInfo getSurveyInfo(int crewrotationId, int clientassetId, int subcategorywfId, int positionId)
    {
        ManagewellnessInfo info = null;
        String query = "SELECT t_survey.i_surveyid, t_survey.i_status, DATE_FORMAT(t_survey.ts_regdate, '%d-%b-%Y'),t_subcategorywf.s_name,"
                + " t_categorywf.s_name, DATE_FORMAT(t_survey.d_completedon, '%d-%b-%Y')  from t_survey "
                + "left join t_subcategorywf on (t_subcategorywf.i_subcategorywfid = t_survey.i_subcategorywfid ) "
                + " left join t_categorywf on (t_categorywf.i_categorywfid = t_subcategorywf.i_categorywfid )"
                + " where t_survey.i_crewrotationid = ? and t_survey.i_subcategorywfid = ? and t_survey.i_status = 2 ORDER BY t_survey.ts_regdate DESC limit 0,1";
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, crewrotationId);
            pstmt.setInt(2, subcategorywfId);
            logger.info(" getSurveyInfo:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int surveyId, status;
            String date, subcategoryName, categoryName, completedondate;
            while (rs.next()) 
            {
                surveyId = rs.getInt(1);
                status = rs.getInt(2);
                date = rs.getString(3) != null ? rs.getString(3) : "";
                subcategoryName = rs.getString(4) != null ? rs.getString(4) : "";
                categoryName = rs.getString(5) != null ? rs.getString(5) : "";
                completedondate = rs.getString(6) != null ? rs.getString(6) : "";
                info = new ManagewellnessInfo(surveyId, status, date,subcategoryName, categoryName, completedondate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public ArrayList getQuestionList(int clientassetId, int subcategorywfId, int positionId, int surveyId, int status)
    {
        ArrayList list = new ArrayList();
        if(status == 2)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_question.s_question, sd.s_answer from t_surveydetail AS sd ");
            sb.append(" LEFT JOIN t_question on (t_question.i_questionid = sd.i_questionid) ");
            sb.append("where sd.i_surveyid = ? ORDER BY t_question.s_question");
            String query = sb.toString();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                if(status == 2)
                {
                    pstmt.setInt(1, surveyId);
                }
                logger.info(" getQuestionList:: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String question, answer;
                while (rs.next())
                {
                    question = rs.getString(1) != null ? rs.getString(1) : "";
                    answer = rs.getString(2) != null ? rs.getString(2) : "";
                    list.add(new ManagewellnessInfo(question, answer));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return list;
    }    
    
    public int createCourse(int clientassetId, int crewrotationId, int courseId, int positionId, int status, String completeby, 
        String link, String startdate, String enddate, int lifetime, String filename, int userId, String remarks) 
    {
        int id = 0;
        try
        {
            conn = getConnection();
            String cq = "select i_managewellnessid from t_managewellness where i_active = 1 and i_clientassetid = ? and i_crewrotationid = ? and i_positionid = ? and i_courseid = ?";
            pstmt = conn.prepareStatement(cq);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, crewrotationId);
            pstmt.setInt(3, positionId);
            pstmt.setInt(4, courseId);
            logger.info("check course :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int managewellnessId = 0;
            while (rs.next()) {
                managewellnessId = rs.getInt(1);
            }
            if(managewellnessId > 0)
            {
                StringBuilder sb = new StringBuilder(); 
                if(status == 2)
                {
                    sb.append("UPDATE t_managewellness SET i_status = ?, d_completeby = ?, s_link = ?, ");
                    sb.append("i_userid = ?, ts_moddate = ? where i_managewellnessid = ? ");
                    String query = (sb.toString()).intern();
                    sb.setLength(0);
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, status);
                    pstmt.setString(2, changeDate1(completeby));
                    pstmt.setString(3, link);
                    pstmt.setInt(4, userId);
                    pstmt.setString(5, currDate1());
                    pstmt.setInt(6, managewellnessId);
                    pstmt.executeUpdate();
                }
                else if(status == 3)
                {
                    sb.append("UPDATE t_managewellness SET i_status = ?, d_startdate = ?, d_enddate = ?, ");
                    sb.append("i_lifetime = ?, s_filename = ?, s_remarks = ?, i_userid = ?, ts_moddate = ? where i_managewellnessid = ? ");
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
                    pstmt.setInt(9, managewellnessId);
                    pstmt.executeUpdate();
                }
            }
            else
            {
                StringBuilder sb = new StringBuilder();            
                sb.append("INSERT INTO t_managewellness ");
                sb.append("(i_clientassetid, i_crewrotationid, i_courseid, i_positionid, i_status, d_completeby, s_link, d_startdate, d_enddate, i_lifetime, ");
                sb.append("s_filename, s_remarks, i_userid, ts_regdate, ts_moddate) ");
                sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                String query = (sb.toString()).intern();
                sb.setLength(0);

                pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, clientassetId);
                pstmt.setInt(2, crewrotationId);
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
    
    public ManagewellnessInfo getModalInfo(int clientmatrixdetailId)
    {
        ManagewellnessInfo info = null;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT c.i_positionid, c.i_courseid, t_coursename.s_name, t_category.s_name, t_subcategory.s_name, ");
            sb.append("t_tctype.s_name, t_level.s_name, t_training.s_name, cn.s_name, t_course.s_description, t1.ct, c.i_subcategoryid, c.i_categoryid, t_coursename.i_coursenameid ");
            sb.append("from t_clientmatrixdetail as c left join t_course on (t_course.i_courseid = c.i_courseid) ");
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
                info = new ManagewellnessInfo(positionId, courseId, courseName, categoryName, subcategoryName, 
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
    
    public ManagewellnessInfo getModalInfoPersonal(int crewrotationId)
    {
        ManagewellnessInfo info = null;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name,  cr.i_candidateid ");
            sb.append("from t_crewrotation as cr left join t_candidate as c on (c.i_candidateid = cr.i_candidateid) ");
            sb.append("left join t_position on (t_position.i_positionid = c.i_positionid) ");
            sb.append("left join t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("WHERE cr.i_crewrotationid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, crewrotationId);
            logger.info("getModalInfoPersonal :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, positionName, gradeName;
            int candidateId;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                positionName = rs.getString(2) != null ? rs.getString(2) : "";
                gradeName = rs.getString(3) != null ? rs.getString(3) : "";
                candidateId = rs.getInt(4);
                if(!gradeName.equals(""))
                    positionName += " - " + gradeName;
                info = new ManagewellnessInfo(name, positionName,candidateId);
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
    
    public ManagewellnessInfo getAssign2Info(int clientIdIndex, int assetIdIndex, int subcategoryId)
    {
        ManagewellnessInfo info = null;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("select DISTINCT swf.i_subcategorywfid, swf.s_name, cwf.s_name, swf.i_categorywfid, t_clientasset.s_name, t_client.s_name ");
            sb.append("from t_wellnessmatrix left join t_subcategorywf as swf ON (t_wellnessmatrix.i_subcategorywfid = swf.i_subcategorywfid and t_wellnessmatrix.i_clientassetid = ?) ");
            sb.append("LEFT JOIN t_categorywf as cwf ON (cwf.i_categorywfid = swf.i_categorywfid) ");
            sb.append("LEFT JOIN t_clientasset  ON (t_clientasset.i_clientassetid = t_wellnessmatrix.i_clientassetid) ");
            sb.append("LEFT JOIN t_client  ON (t_client.i_clientid = t_clientasset.i_clientid) ");
              sb.append("where t_wellnessmatrix.i_clientassetid = ? and t_wellnessmatrix.i_subcategorywfid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assetIdIndex);
            pstmt.setInt(2, assetIdIndex);
            pstmt.setInt(3, subcategoryId);
            logger.info("getAssign2Info :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                int subcategoryid = rs.getInt(1);
                String subcategoryName = rs.getString(2) != null ? rs.getString(2) : "";
                String categoryName = rs.getString(3) != null ? rs.getString(3) : "";
                int categoryId = rs.getInt(4);
                String assetName = rs.getString(5) != null ? rs.getString(5) : "";
                String clientName = rs.getString(6) != null ? rs.getString(6) : "";
                info = new ManagewellnessInfo( subcategoryName,categoryName, assetName, clientName,subcategoryid, categoryId);
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
    
    public ArrayList getAssign2list(int clientIdIndex, int assetIdIndex, int positionIdIndex, String search, String positionIds, int subcategoryId)
    {
        ArrayList list = new ArrayList();
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT cr.i_crewrotationid,  CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name, t2.ct, cr.i_candidateid, c.i_positionid  ");
            sb.append("FROM t_crewrotation AS cr LEFT JOIN t_candidate AS c ON (c.i_candidateid = cr.i_candidateid) ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("left join (select i_positionid, count(distinct(i_questionid)) as ct from t_wellnessmatrix left join t_categorywf on (t_categorywf.i_categorywfid = t_wellnessmatrix.i_categorywfid) where t_categorywf.i_status = 1 and i_clientassetid = ? and ");
            sb.append(" i_subcategorywfid = ? group by i_positionid) as t2 ON (t_position.i_positionid = t2.i_positionid)  ");
            sb.append("where  cr.i_active = 1  and cr.i_clientid = ? and cr.i_clientassetid = ? and c.i_positionid in (" + positionIds + ")");
            if (search != null && !search.equals("")) {
                sb.append(" AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ?) ");
            }
            if (positionIdIndex > 0) {
                sb.append(" and c.i_positionid = ? ");
            }
            sb.append(" ORDER BY c.s_firstname, c.s_middlename, c.s_lastname ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            int scc = 0;
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setInt(++scc, subcategoryId);
            pstmt.setInt(++scc, clientIdIndex);
            pstmt.setInt(++scc, assetIdIndex);
            
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (positionIdIndex > 0) {
                pstmt.setInt(++scc, positionIdIndex);
            }
            logger.info("getAssign2list :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, positionName, gradeName;
                int  questCount, crewrotationId, candidateId, positionId;
            while (rs.next())
            {
                crewrotationId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                positionName = rs.getString(3) != null ? rs.getString(3) : "";
                gradeName = rs.getString(4) != null ? rs.getString(4) : "";
                questCount = rs.getInt(5);
                candidateId = rs.getInt(6);
                positionId = rs.getInt(7);
                if (!gradeName.equals("")) {
                    positionName += " - " + gradeName;
                }
                list.add(new ManagewellnessInfo(crewrotationId, name, positionName, questCount, candidateId, positionId));
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
    
     public static String currDateNew() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        java.util.Date dt = cal.getTime();
        return sdf.format(dt);
    }
    public String getPositionIds(int subcategoryId, int assetIdIndex)
    {
        String positionIds = "";
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT group_concat(distinct(i_positionid)) ");
            sb.append("from t_wellnessmatrix  ");
            sb.append("WHERE t_wellnessmatrix.i_subcategorywfid = ? and i_clientassetid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, subcategoryId);
            pstmt.setInt(2, assetIdIndex);
            logger.info("getPositionIds :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                positionIds = rs.getString(1) != null ? rs.getString(1) : "";
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
        return positionIds;
    }
    
    public int createCourseMultiple(int clientassetId, int crewrotationId, String courseids, int positionId,
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
                        String cq = "select i_managewellnessid from t_managewellness where i_active = 1 and i_clientassetid = ? and i_crewrotationid = ? and i_positionid = ? and i_courseid = ?";
                        pstmt = conn.prepareStatement(cq);
                        pstmt.setInt(1, clientassetId);
                        pstmt.setInt(2, crewrotationId);
                        pstmt.setInt(3, positionId);
                        pstmt.setInt(4, courseId);
                        logger.info("check course :: " + pstmt.toString());
                        rs = pstmt.executeQuery();
                        int managewellnessId = 0;
                        while (rs.next()) {
                            managewellnessId = rs.getInt(1);
                        }
                        if(managewellnessId > 0)
                        {
                            StringBuilder sb = new StringBuilder();
                            sb.append("UPDATE t_managewellness SET i_status = ?, d_completeby = ?, s_link = ?, ");
                            sb.append("i_userid = ?, ts_moddate = ? where i_managewellnessid = ? ");
                            String query = (sb.toString()).intern();
                            sb.setLength(0);
                            pstmt = conn.prepareStatement(query);
                            pstmt.setInt(1, 2);
                            pstmt.setString(2, changeDate1(completeby));
                            pstmt.setString(3, link);
                            pstmt.setInt(4, userId);
                            pstmt.setString(5, currDate1());
                            pstmt.setInt(6, managewellnessId);
                            pstmt.executeUpdate();
                        }
                        else
                        {
                            StringBuilder sb = new StringBuilder();            
                            sb.append("INSERT INTO t_managewellness ");
                            sb.append("(i_clientassetid, i_crewrotationid, i_courseid, i_positionid, i_status, d_completeby, s_link, ");
                            sb.append("i_userid, ts_regdate, ts_moddate) ");
                            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                            String query = (sb.toString()).intern();
                            sb.setLength(0);
                            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                            pstmt.setInt(1, clientassetId);
                            pstmt.setInt(2, crewrotationId);
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
    
    public ManagewellnessInfo getcourseModalInfo(int courseId)
    {
        ManagewellnessInfo info = null;
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
                info = new ManagewellnessInfo(0, courseId, courseName, categoryName, subcategoryName,"", "", "", "", "", 0,0,0,0);
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
    public int createPersonalMultiple(int clientassetId, String crewrotationIds, int courseId,  
        String completeby, String link, int userId)
    {
        int id = 0;
        try
        {
            conn = getConnection();
            String carr[] = parseCommaDelimString(crewrotationIds);
            if(carr != null)
            {
                int len = carr.length;
                for(int i = 0; i < len; i++)
                {
                    int crewrotationId = Integer.parseInt(carr[i]);
                    if(crewrotationId > 0)
                    {
                        int positionId = 0;
                        String qp = "select c.i_positionid from t_crewrotation as cr left join t_candidate as c on (cr.i_candidateid = c.i_candidateid) where cr.i_crewrotationid = ? ";
                        pstmt = conn.prepareStatement(qp);
                        pstmt.setInt(1, crewrotationId);
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                            positionId = rs.getInt(1);
                        }
                        rs.close();
                       
                        String cq = "select i_managewellnessid from t_managewellness where i_active = 1 and i_clientassetid = ? and i_crewrotationid = ? and i_positionid = ? and i_courseid = ?";
                        pstmt = conn.prepareStatement(cq);
                        pstmt.setInt(1, clientassetId);
                        pstmt.setInt(2, crewrotationId);
                        pstmt.setInt(3, positionId);
                        pstmt.setInt(4, courseId);
                        logger.info("check course :: " + pstmt.toString());
                        rs = pstmt.executeQuery();
                        int managewellnessId = 0;
                        while (rs.next()) {
                            managewellnessId = rs.getInt(1);
                        }
                        if(managewellnessId > 0)
                        {
                            StringBuilder sb = new StringBuilder();
                            sb.append("UPDATE t_managewellness SET i_status = ?, d_completeby = ?, s_link = ?, ");
                            sb.append("i_userid = ?, ts_moddate = ? where i_managewellnessid = ? ");
                            String query = (sb.toString()).intern();
                            sb.setLength(0);
                            pstmt = conn.prepareStatement(query);
                            pstmt.setInt(1, 2);
                            pstmt.setString(2, changeDate1(completeby));
                            pstmt.setString(3, link);
                            pstmt.setInt(4, userId);
                            pstmt.setString(5, currDate1());
                            pstmt.setInt(6, managewellnessId);
                            pstmt.executeUpdate();
                        }
                        else
                        {
                            StringBuilder sb = new StringBuilder();            
                            sb.append("INSERT INTO t_managewellness ");
                            sb.append("(i_clientassetid, i_crewrotationid, i_courseid, i_positionid, i_status, d_completeby, s_link, ");
                            sb.append("i_userid, ts_regdate, ts_moddate) ");
                            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                            String query = (sb.toString()).intern();
                            sb.setLength(0);
                            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                            pstmt.setInt(1, clientassetId);
                            pstmt.setInt(2, crewrotationId);
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
    public ManagewellnessInfo getScheduleInfo( int clientassetId, int assetIdIndex, int subcategorywfId)
    {
        ManagewellnessInfo info = null;
        String query = "SELECT  DATE_FORMAT(t_schedule.d_fromdate, '%d %b %Y'), DATE_FORMAT(t_schedule.d_todate, '%d %b %Y') from t_schedule where i_clientid = ? and i_clientassetid = ? and i_subcategorywfid = ? ";
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, assetIdIndex);
            pstmt.setInt(3, subcategorywfId);
            logger.info(" getScheduleInfo:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String date = "",completedondate = "";
            while (rs.next()) 
            {
                date = rs.getString(1) != null ? rs.getString(1) : "";
                completedondate = rs.getString(2) != null ? rs.getString(2) : "";
            }
                info = new ManagewellnessInfo(date,completedondate);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    } 
    
    public String getqlist(int clientassetId, int subcategoryId)
    {
        StringBuilder sb = new StringBuilder();
        if(clientassetId > 0)
        {  
            try
            {
                conn = getConnection();
                String query = "SELECT t_subcategorywf.s_name, t_categorywf.s_name FROM t_subcategorywf left join t_categorywf on (t_categorywf.i_categorywfid = t_subcategorywf.i_categorywfid) where t_subcategorywf.i_subcategorywfid = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, subcategoryId);
                String subcatName = "", catName = "";
                rs = pstmt.executeQuery();
                while (rs.next())
                { 
                    subcatName = rs.getString(1) != null ? rs.getString(1) : "";
                    catName = rs.getString(2) != null ? rs.getString(2) : "";
                }
                rs.close();
                sb.append("<div class='row flex-center align-items-center mb_10'>");
                sb.append("<div class='col'>");
                sb.append("<h2>"+subcatName+" | "+catName+"</h2>");
                sb.append("</div>");
                sb.append("</div>");
                
                query = "select distinct(t_question.s_question) from t_wellnessmatrix left join t_question on (t_question.i_questionid = t_wellnessmatrix.i_questionid) where t_wellnessmatrix.i_clientassetid = ? and t_wellnessmatrix.i_subcategorywfid = ? ORDER BY t_question.s_question";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                pstmt.setInt(2, subcategoryId);
                logger.info("getqlist :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String name;
                sb.append("<div class='row client_position_table'>");
                sb.append("<div class='col-md-12 table-rep-plugin sort_table'>");
                sb.append("<div class='table-responsive mb-0'>");
                sb.append("<table id='tech-companies-1' class='table table-striped'>");
                sb.append("<tbody>");
                while (rs.next())
                { 
                    name = rs.getString(1) != null ? rs.getString(1) : "";
                    sb.append("<tr>");
                    sb.append("<td class='que_ans'>");
                    sb.append("<span class='que_bg'>Q</span> " + name);
                    sb.append("</td>");
                    sb.append("</tr>");
                }
                rs.close();
                sb.append("</tbody>");
                sb.append("</table>");
                sb.append("</div>");	
                sb.append("</div>");
                sb.append("</div>");
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
        String s = sb.toString();
        sb.setLength(0);
        return s;
    }
}