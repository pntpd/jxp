package com.web.jxp.mobilization;

import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
import com.web.jxp.base.StatsInfo;
import com.web.jxp.base.Template;
import static com.web.jxp.common.Common.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;
import javax.mail.MessagingException;

public class Mobilization extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getClientSelectByName(String search, int statusIndex, int next, int count, int clientIdIndex, int assetIdIndex, int countryId, int allclient,
            String permission, String cids, String assetids) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_crewrotation.i_crewrotationid, t_client.s_name, t_clientasset.s_name, t_country.s_name, COUNT(1), t_crewrotation.i_clientid, ");
        sb.append("t_crewrotation.i_clientassetid, t_crewrotation.i_jobpostid FROM t_crewrotation ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_crewrotation.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_crewrotation.i_clientassetid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_clientasset.i_countryid) WHERE t_crewrotation.i_active =1 ");
        sb.append("AND t_client.i_status = 1 AND t_clientasset.i_status = 1 ");
        if (allclient == 1) {
            sb.append("AND t_client.i_clientid > 0 ");
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
        if (statusIndex > 0) {
            sb.append("AND t_client.i_status = ? ");
        }
        if (clientIdIndex > 0) {
            sb.append("AND t_crewrotation.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND t_crewrotation.i_clientassetid = ? ");
        }
        if (countryId > 0) {
            sb.append("AND t_clientasset.i_countryid = ? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("AND (t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ? OR t_country.s_name LIKE ?) ");
        }
        sb.append("GROUP BY t_crewrotation.i_clientid, t_crewrotation.i_clientassetid ");
        if (count > 0) {
            sb.append("LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1) FROM (SELECT COUNT(1) FROM t_crewrotation ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_crewrotation.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_crewrotation.i_clientassetid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_clientasset.i_countryid) WHERE t_crewrotation.i_active =1 ");
        sb.append("AND t_client.i_status = 1 AND t_clientasset.i_status = 1 ");
        if (allclient == 1) {
            sb.append("AND t_client.i_clientid > 0 ");
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
        if (statusIndex > 0) {
            sb.append("AND t_client.i_status = ? ");
        }
        if (clientIdIndex > 0) {
            sb.append("AND t_crewrotation.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND t_crewrotation.i_clientassetid = ? ");
        }
        if (countryId > 0) {
            sb.append("AND t_clientasset.i_countryid = ? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("AND (t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ? OR t_country.s_name LIKE ?) ");
        }
        sb.append("GROUP BY t_crewrotation.i_clientid, t_crewrotation.i_clientassetid) AS t ");
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            if (countryId > 0) {
                pstmt.setInt(++scc, countryId);
            }
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search.replaceFirst("^0+(?!$)", "")) + "%");
                pstmt.setString(++scc, "%" + (search.replaceFirst("^0+(?!$)", "")) + "%");
                pstmt.setString(++scc, "%" + (search.replaceFirst("^0+(?!$)", "")) + "%");
            }
            logger.info("getClientSelectByName:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, Country, clientAsset;
            int crewrotationId, status1, clientId, clientassetId, jobpostid;
            while (rs.next()) 
            {
                crewrotationId = rs.getInt(1);
                clientName = rs.getString(2) != null ? rs.getString(2) : "";
                clientAsset = rs.getString(3) != null ? rs.getString(3) : "";
                Country = rs.getString(4) != null ? rs.getString(4) : "";
                status1 = rs.getInt(5);
                clientId = rs.getInt(6);
                clientassetId = rs.getInt(7);
                jobpostid = rs.getInt(8);
                list.add(new MobilizationInfo(crewrotationId, clientName, clientAsset, Country, status1, clientId, clientassetId, jobpostid));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }

            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            if (countryId > 0) {
                pstmt.setInt(++scc, countryId);
            }
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            print(this, "getClientSelectByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                crewrotationId = rs.getInt(1);
                list.add(new MobilizationInfo(crewrotationId, "", "", "", 0, 0, 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        MobilizationInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try 
        {
            if (total > 0) 
            {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) 
                {
                    info = (MobilizationInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map;
            if (colId.equals("1")) {
                map = sortByName(record, tp);
            } else if (colId.equals("2") || colId.equals("3")) {
                map = sortByName(record, tp);
            } else {
                map = sortByName(record, tp);
            }

            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            MobilizationInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (MobilizationInfo) l.get(i);
                    String str = getInfoValue(rInfo, colId);
                    if (str.equals(key)) {
                        list.add(rInfo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getInfoValue(MobilizationInfo info, String i) 
    {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getClientName() != null ? info.getClientName() : "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getClientAsset() != null ? info.getClientAsset() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getCountry() != null ? info.getCountry() : "";
        }
        return infoval;
    }

    public Collection getClients(String cids, int allclient, String permission) 
    {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_clientid, s_name FROM t_client WHERE i_status = 1 ");
        if (allclient == 1) {
            sb.append("AND i_clientid > 0 ");
        } else if (!cids.equals("")) {
            sb.append("AND i_clientid IN (" + cids + ") ");
        } else {
            sb.append("AND i_clientid < 0 ");
        }
        sb.append("order by s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new MobilizationInfo(-1, " Select Client"));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            print(this, "getClients" + pstmt.toString());
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new MobilizationInfo(refId, refName));
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
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_clientid =? AND i_status =1 ");
        if (allclient == 1) {
            sb.append("AND i_clientassetid > 0 ");
        } else if (!assetids.equals("")) {
            sb.append("AND i_clientassetid IN (" + assetids + ") ");
        } else {
            sb.append("AND i_clientassetid < 0 ");
        }
        sb.append("ORDER BY s_name ");
        String query = sb.toString();
        sb.setLength(0);
        coll.add(new MobilizationInfo(-1, " Select Asset "));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientId);
            rs = pstmt.executeQuery();
            int ddlValue;
            String ddlLabel;
            while (rs.next()) 
            {
                ddlValue = rs.getInt(1);
                ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new MobilizationInfo(ddlValue, ddlLabel));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }

        return coll;
    }

    public Collection getCountryList() 
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_countryid, s_name FROM t_country WHERE i_status = 1 order by s_name").intern();
        coll.add(new MobilizationInfo(-1, " Select Location "));
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
                coll.add(new MobilizationInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public ArrayList getListForExcel(String search, int statusIndex, int clientIdIndex, int assetIdIndex, 
            int countryId, int allclient, String permission, String cids, String assetids)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_crewrotation.i_crewrotationid, t_client.s_name, t_clientasset.s_name, t_country.s_name, COUNT(1), t_crewrotation.i_clientid, ");
        sb.append("t_crewrotation.i_clientassetid, t_crewrotation.i_jobpostid FROM t_crewrotation ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_crewrotation.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_crewrotation.i_clientassetid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_clientasset.i_countryid) WHERE t_crewrotation.i_active =1 ");
        if (allclient == 1) {
            sb.append("AND t_client.i_clientid > 0 ");
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
        if (statusIndex > 0) {
            sb.append("AND t_client.i_status = ? ");
        }
        if (clientIdIndex > 0) {
            sb.append("AND t_crewrotation.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND t_crewrotation.i_clientassetid = ? ");
        }
        if (countryId > 0) {
            sb.append("AND t_clientasset.i_countryid = ? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("AND (t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ? OR t_country.s_name LIKE ?) ");
        }
        sb.append("GROUP BY t_crewrotation.i_clientid, t_crewrotation.i_clientassetid ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            if (countryId > 0) {
                pstmt.setInt(++scc, countryId);
            }
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search.replaceFirst("^0+(?!$)", "")) + "%");
                pstmt.setString(++scc, "%" + (search.replaceFirst("^0+(?!$)", "")) + "%");
                pstmt.setString(++scc, "%" + (search.replaceFirst("^0+(?!$)", "")) + "%");
            }
            logger.info("getListForExcel:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, Country, clientAsset;
            int crewrotationId, status1, clientId, clientassetId, jobpostid;
            while (rs.next()) 
            {
                crewrotationId = rs.getInt(1);
                clientName = rs.getString(2) != null ? rs.getString(2) : "";
                clientAsset = rs.getString(3) != null ? rs.getString(3) : "";
                Country = rs.getString(4) != null ? rs.getString(4) : "";
                status1 = rs.getInt(5);
                clientId = rs.getInt(6);
                clientassetId = rs.getInt(7);
                jobpostid = rs.getInt(8);
                list.add(new MobilizationInfo(crewrotationId, clientName, clientAsset, Country, status1, clientId, clientassetId, jobpostid));
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

    public MobilizationInfo getMobiClientDetails(int clientId, int clientassetId) 
    {
        MobilizationInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_name, t_clientasset.s_name, t_country.s_name, jp1.su, t2.ct, p.ct1, t_clientasset.i_assettypeid FROM t_client ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientid = t_client.i_clientid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_clientasset.i_countryid) ");
        sb.append("LEFT JOIN (SELECT *, COUNT(distinct i_crewrotationid) AS su FROM t_crewrotation AS cr WHERE cr.i_active =1 GROUP BY cr.i_clientassetid, ");
        sb.append("cr.i_clientid) AS jp1 ON (jp1.i_clientassetid = t_clientasset.i_clientassetid AND jp1.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN (SELECT t_jobpost.i_clientid, t_jobpost.i_clientassetid, COUNT(1) AS ct FROM t_shortlist ");
        sb.append("LEFT JOIN t_jobpost ON (t_jobpost.i_jobpostid = t_shortlist.i_jobpostid) WHERE t_jobpost.i_status IN (1,3) AND t_shortlist.i_onboard = 10 GROUP BY ");
        sb.append("t_jobpost.i_clientid, t_jobpost.i_clientassetid) AS t2 ON (t2.i_clientid = t_client.i_clientid AND t2.i_clientassetid = t_clientasset.i_clientassetid) ");
        sb.append("LEFT JOIN (SELECT t_jobpost.i_positionid, t_jobpost.i_clientid, t_jobpost.i_clientassetid, COUNT(distinct i_positionid) AS ct1 FROM t_jobpost WHERE ");
        sb.append("t_jobpost.i_status IN (1,3) GROUP BY t_jobpost.i_clientid, t_jobpost.i_clientassetid ) AS p ON (p.i_clientid = t_clientasset.i_clientid AND p.i_clientassetid = t_clientasset.i_clientassetid ) ");
        sb.append("WHERE t_client.i_clientid =? AND t_clientasset.i_clientassetid =? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientId);
            pstmt.setInt(2, clientassetId);
            print(this, "getMobiClientDetails :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAsset, country;
            int onboardCount, totalnoofongoing, positionCount, assettypeid;
            while (rs.next()) 
            {
                clientName = rs.getString(1) != null ? rs.getString(1) : "";
                clientAsset = rs.getString(2) != null ? rs.getString(2) : "";
                country = rs.getString(3) != null ? rs.getString(3) : "";
                totalnoofongoing = rs.getInt(4);
                onboardCount = rs.getInt(5);
                positionCount = rs.getInt(6);
                assettypeid = rs.getInt(7);
                info = new MobilizationInfo(clientId, clientassetId, clientName, clientAsset, country, positionCount, onboardCount, totalnoofongoing, assettypeid);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getMobiClientDetails :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getCandidatesByclientandAssetName(String search, int clientId, int assetId, int positionId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name, c.s_photofilename, ");
        sb.append("t_client.s_name, t_clientasset.s_name, t_country.s_name, t_crewrotation.i_crewrotationid, t_crewrotation.i_jobpostid, t_crewrotation.i_flag1, ");
        sb.append("t_crewrotation.i_flag2 FROM t_crewrotation ");
        sb.append("LEFT JOIN t_candidate AS c ON (c.i_candidateid = t_crewrotation.i_candidateid) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_crewrotation.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_crewrotation.i_clientassetid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_clientasset.i_countryid) ");
        sb.append("WHERE t_crewrotation.i_active =1 AND t_crewrotation.i_clientid =? AND t_crewrotation.i_clientassetid =? ");
        if (search != null && !search.equals("")) {
            sb.append("AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? OR t_position.s_name LIKE ? OR t_grade.s_name LIKE ? ) ");
        }
        if (positionId > 0) {
            sb.append("AND c.i_positionid =? ");
        }

        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientId);
            pstmt.setInt(++scc, assetId);

            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + search.replaceFirst("^0+(?!$)", "") + "%");
                pstmt.setString(++scc, "%" + search.replaceFirst("^0+(?!$)", "") + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (positionId > 0) {
                pstmt.setInt(++scc, positionId);
            }
            logger.info("getCandidatesByclientandAssetName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, Country, clientAsset, name, position, grade, photo;
            int crewrotationId, candidateId, jobpostId, flag1, flag2;
            while (rs.next()) 
            {
                candidateId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                grade = rs.getString(4) != null ? rs.getString(4) : "";
                photo = rs.getString(5) != null ? rs.getString(5) : "";
                clientName = rs.getString(6) != null ? rs.getString(6) : "";
                clientAsset = rs.getString(7) != null ? rs.getString(7) : "";
                Country = rs.getString(8) != null ? rs.getString(8) : "";
                crewrotationId = rs.getInt(9);
                jobpostId = rs.getInt(10);
                flag1 = rs.getInt(11);
                flag2 = rs.getInt(12);
                if (!grade.equals("")) 
                {
                    position += " - " + grade;
                }
                list.add(new MobilizationInfo(crewrotationId, candidateId, name, position, grade, photo, clientName, clientAsset, Country, jobpostId, flag1, flag2));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getDocListId(int clientId, int clientassetId) 
    {
        ArrayList list = new ArrayList();
        if (clientId > 0 && clientassetId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_doccheckid, s_name FROM t_doccheck WHERE i_clientid =? AND i_clientassetid =? AND i_status = 1 ORDER BY s_name ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                pstmt.setInt(2, clientassetId);
                logger.info(" getDocListId:: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String docname;
                int doccheckId;
                while (rs.next()) 
                {
                    doccheckId = rs.getInt(1);
                    docname = rs.getString(2) != null ? rs.getString(2) : "";
                    list.add(new MobilizationInfo(doccheckId, docname));
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return list;
    }

    public ArrayList getMobilizationListByBatch(int batchId, int type)
    {
        ArrayList list = new ArrayList();
        if (batchId > 0)
        {
            try
            {
                conn = getConnection();
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT i_mobilizationid, s_val1, s_val2, s_val3, s_val4, s_val5, s_val6, s_val7, s_val8, DATE_FORMAT(s_val9, '%d-%b-%Y %H:%i'), DATE_FORMAT(s_val10, '%d-%b-%Y %H:%i'), ");
                sb.append("s_filename, i_status, i_userid, DATE_FORMAT( ts_regdate, '%d-%b-%Y'), DATE_FORMAT(ts_moddate, '%d-%b-%Y') FROM t_mobilizationdtls ");
                sb.append("WHERE i_batchid =? ");
                String query = (sb.toString()).intern();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                pstmt.setInt(++scc, batchId);
                print(this, "getMobilizationListByBatch :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String val1, val2, val3, val4, val5, val6, val7, val8, val9, val10, filename, regdate, moddate;
                int mobilizationId, userId, status;
                while (rs.next()) 
                {
                    mobilizationId = rs.getInt(1);
                    val1 = rs.getString(2) != null ? rs.getString(2) : "";
                    val2 = rs.getString(3) != null ? rs.getString(3) : "";
                    val3 = rs.getString(4) != null ? rs.getString(4) : "";
                    val4 = rs.getString(5) != null ? rs.getString(5) : "";
                    val5 = rs.getString(6) != null ? rs.getString(6) : "";
                    val6 = rs.getString(7) != null ? rs.getString(7) : "";
                    val7 = rs.getString(8) != null ? rs.getString(8) : "";
                    val8 = rs.getString(9) != null ? rs.getString(9) : "";
                    val9 = rs.getString(10) != null ? rs.getString(10) : "";
                    val10 = rs.getString(11) != null ? rs.getString(11) : "";
                    filename = rs.getString(12) != null ? rs.getString(12) : "";
                    status = rs.getInt(13);
                    userId = rs.getInt(14);
                    regdate = rs.getString(15) != null ? rs.getString(15) : "";
                    moddate = rs.getString(16) != null ? rs.getString(16) : "";
                    list.add(new MobilizationInfo(batchId, type, mobilizationId, val1, val2, val3, val4, val5, val6, val7, val8, val9, val10, filename, status, userId, regdate, moddate));
                }
                rs.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return list;
    }

    public int getBatchIdByCrewId(int crewrotationId, int type, Connection conn) 
    {
        int batchId = 0;
        if (crewrotationId > 0) 
        {
            try 
            {
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT i_batchid FROM t_batch WHERE i_crewrotationid =? ");
                if (type > 0) {
                    sb.append("AND i_type =? ");
                }
                sb.append("AND i_draft >0 ORDER BY ts_regdate DESC LIMIT 0,1");
                String query = (sb.toString()).intern();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, crewrotationId);
                if (type > 0) {
                    pstmt.setInt(2, type);
                }
                print(this, "getBatchIdByCrewId :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    batchId = rs.getInt(1);
                }
                rs.close();
            } catch (Exception e) {
                print(this, "getBatchIdByCrewId catch:: " + pstmt.toString());
                e.printStackTrace();
            } finally {
                close(null, pstmt, rs);
            }
        }
        return batchId;
    }

    public ArrayList getMobilizationListById(int crewrotationId, int type)
    {
        int batchId = 0;
        ArrayList list = new ArrayList();
        try
        {
            conn = getConnection();
            batchId = getBatchIdByCrewId(crewrotationId, type, conn);
            if (batchId > 0) {
                list = getMobilizationListByBatch(batchId, type);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int insertMobilizationDtls(int crewrotationId, int type, String val1, String val2, String val3, String val4, String val5, String val6, String val7, String val8,
            String vald9, String valt9, String vald10, String valt10, String filename, int userId, int status)     
    {
        int batchId = 0;
        try 
        {
            conn = getConnection();
            batchId = getBatchIdByCrewId(crewrotationId, type, conn);

            if (batchId <= 0) 
            {
                String insertquery = "INSERT INTO t_batch (i_crewrotationid, i_userid, i_status, ts_regdate, ts_moddate, i_type) VALUES(?, ?, ?, ?, ?, ?) ";
                pstmt = conn.prepareStatement(insertquery, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, crewrotationId);
                pstmt.setInt(2, userId);
                pstmt.setInt(3, status);
                pstmt.setString(4, currDate1());
                pstmt.setString(5, currDate1());
                pstmt.setInt(6, type);
                print(this, "insertMobilizationDtls insert batch :: " + pstmt.toString());
                pstmt.executeUpdate();
                rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    batchId = rs.getInt(1);
                }
            }

            StringBuilder sb = new StringBuilder();
            if (batchId > 0) 
            {
                sb.append("INSERT INTO t_mobilizationdtls (i_batchid, s_val1, s_val2, s_val3, s_val4, s_val5, s_val6, s_val7, s_val8, s_val9, s_val10, s_filename, ");
                sb.append("i_status, i_userid, ts_regdate, ts_moddate) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                String mobquery = sb.toString().intern();
                sb.setLength(0);
                int scc = 0;
                pstmt = conn.prepareStatement(mobquery);
                pstmt.setInt(++scc, batchId);
                pstmt.setString(++scc, val1);
                pstmt.setString(++scc, val2);
                pstmt.setString(++scc, val3);
                pstmt.setString(++scc, val4);
                pstmt.setString(++scc, val5);
                pstmt.setString(++scc, val6);
                pstmt.setString(++scc, val7);
                pstmt.setString(++scc, val8);
                pstmt.setString(++scc, changeDate1(vald9) + " " + valt9 + ":00");
                pstmt.setString(++scc, changeDate1(vald10) + " " + valt10 + ":00");
                pstmt.setString(++scc, filename);
                pstmt.setInt(++scc, status);
                pstmt.setInt(++scc, userId);
                pstmt.setString(++scc, currDate1());
                pstmt.setString(++scc, currDate1());
                print(this, "insertMobilizationDtls :: " + pstmt.toString());
                pstmt.executeUpdate();
                updateCrewFlag(crewrotationId, type, userId);
            }
            pstmt.close();
        } catch (Exception exception) {
            print(this, "insertMobilizationDtls catch :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return batchId;
    }

    public int updateCrewFlag(int crewrotationId, int type, int userId) 
    {
        int crewId = 0;
        StringBuilder sb = new StringBuilder();
        try 
        {
            conn = getConnection();
            sb.append("UPDATE t_crewrotation SET ");
            if (type == 1) {
                sb.append("i_flag1 =1, ");
            }
            if (type == 2) {
                sb.append("i_flag2 =1, ");
            }
            if (type == 3) {
                sb.append("i_flag1 =2, ");
            }
            if (type == 4) {
                sb.append("i_flag2 =2, ");
            }
            sb.append("i_userid=?, ts_moddate =? WHERE i_crewrotationid =? ");
            String updatequery = sb.toString().intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(updatequery);
            pstmt.setInt(1, userId);
            pstmt.setString(2, currDate1());
            pstmt.setInt(3, crewrotationId);
            print(this, "updateCrewFlag :: " + pstmt.toString());
            pstmt.executeUpdate();

            if (type == 3 || type == 4) 
            {
                int batchId = 0;
                if (type == 3) {
                    batchId = getBatchIdByCrewId(crewrotationId, 1, conn);
                } else if (type == 4) {
                    batchId = getBatchIdByCrewId(crewrotationId, 2, conn);
                }
                if (batchId > 0) 
                {
                    String query = "UPDATE t_batch SET i_draft=0 WHERE i_batchid =?";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, batchId);
                    print(this, "updateCrewFlag  update t_batch:: " + pstmt.toString());
                    pstmt.executeUpdate();
                }
            }
            crewId = crewrotationId;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return crewId;
    }

    public int updateCrewFlagAfterMail(int crewrotationId, int userId) 
    {
        StringBuilder sb = new StringBuilder();
        if (crewrotationId > 0)
        {
            try
            {
                conn = getConnection();
                sb.append("UPDATE t_crewrotation SET ");
                sb.append("i_flag1 =0, ");
                sb.append("i_flag2 =0, ");
                sb.append("i_userid=?, ts_moddate =? WHERE i_crewrotationid =? ");
                String updatequery = sb.toString().intern();
                sb.setLength(0);
                pstmt = conn.prepareStatement(updatequery);
                pstmt.setInt(1, userId);
                pstmt.setString(2, currDate1());
                pstmt.setInt(3, crewrotationId);
                print(this, "updateCrewFlag :: " + pstmt.toString());
                pstmt.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return crewrotationId;
    }

    public int deleteMobilizationDtls(int mobId) 
    {
        int temp = 0;
        try 
        {
            conn = getConnection();
            if (mobId > 0)
            {
                String query = "DELETE FROM t_mobilizationdtls WHERE i_mobilizationid =? ";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, mobId);
                print(this, "deleteMobilizationDtls :: " + pstmt.toString());
                pstmt.execute();
                temp = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return temp;
    }

    public ArrayList getListByCrewrotationId(int batchId, int type)
    {
        ArrayList list = new ArrayList();
        if (batchId > 0) 
        {
            try 
            {
                conn = getConnection();
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT i_mobilizationid, s_val1, s_val2, s_val3, s_val4, s_val5, s_val6, s_val7, s_val8, DATE_FORMAT(s_val9, '%d-%b-%Y %H:%i'), DATE_FORMAT(s_val10, '%d-%b-%Y %H:%i'), ");
                sb.append("s_filename, i_status, i_userid, DATE_FORMAT( ts_regdate, '%d-%b-%Y'), DATE_FORMAT(ts_moddate, '%d-%b-%Y') FROM t_mobilizationdtls ");
                sb.append("WHERE i_batchid =? AND i_type =? ");
                String query = (sb.toString()).intern();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                pstmt.setInt(++scc, batchId);
                pstmt.setInt(++scc, type);
                print(this, "getListByCrewrotationId :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String val1, val2, val3, val4, val5, val6, val7, val8, val9, val10, filename, regdate, moddate;
                int mobilizationId, userId, status;
                while (rs.next()) 
                {
                    mobilizationId = rs.getInt(1);
                    val1 = rs.getString(2) != null ? rs.getString(2) : "";
                    val2 = rs.getString(3) != null ? rs.getString(3) : "";
                    val3 = rs.getString(4) != null ? rs.getString(4) : "";
                    val4 = rs.getString(5) != null ? rs.getString(5) : "";
                    val5 = rs.getString(6) != null ? rs.getString(6) : "";
                    val6 = rs.getString(7) != null ? rs.getString(7) : "";
                    val7 = rs.getString(8) != null ? rs.getString(8) : "";
                    val8 = rs.getString(9) != null ? rs.getString(9) : "";
                    val9 = rs.getString(10) != null ? rs.getString(10) : "";
                    val10 = rs.getString(11) != null ? rs.getString(11) : "";
                    filename = rs.getString(12) != null ? rs.getString(12) : "";
                    status = rs.getInt(13);
                    userId = rs.getInt(14);
                    regdate = rs.getString(15) != null ? rs.getString(15) : "";
                    moddate = rs.getString(16) != null ? rs.getString(16) : "";
                    list.add(new MobilizationInfo(batchId, type, mobilizationId, val1, val2, val3, val4, val5, val6, val7, val8, val9, val10, filename, status, userId, regdate, moddate));
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return list;
    }

    public ArrayList getMobDataByCrewId(int CrewId, int type) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT mb.i_mobilizationid, mb.s_val1, mb.s_val2, mb.s_val3, mb.s_val4, mb.s_val5, mb.s_val6, mb.s_val7, mb.s_val8, DATE_FORMAT(mb.s_val9, '%d-%b-%Y'), ");
        sb.append("DATE_FORMAT(mb.s_val9, '%H:%i'), DATE_FORMAT(mb.s_val10, '%d-%b-%Y'), DATE_FORMAT(mb.s_val10, '%H:%i'), mb.s_filename, mb.i_status, mb.i_batchid, t_batch.i_type ");
        sb.append("FROM t_mobilizationdtls AS mb ");
        sb.append("LEFT JOIN t_batch ON (mb.i_batchid = t_batch.i_batchid) ");
        sb.append("LEFT JOIN t_crewrotation ON (t_batch.i_crewrotationid = t_crewrotation.i_crewrotationid ) ");
        sb.append("WHERE t_crewrotation.i_active =1 AND t_crewrotation.i_crewrotationid =? AND t_batch.i_type =? AND t_batch.i_draft =0");

        String query = sb.toString().intern();
        sb.setLength(0);
        if (CrewId > 0) 
        {
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, CrewId);
                pstmt.setInt(2, type);
                print(this, "getMobDataByCrewId :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String val1, val2, val3, val4, val5, val6, val7, val8, vald9, valt9, vald10, valt10, filename;
                int mobilizationId, status, batchId;
                while (rs.next()) 
                {
                    mobilizationId = rs.getInt(1);
                    val1 = rs.getString(2) != null ? rs.getString(2) : "";
                    val2 = rs.getString(3) != null ? rs.getString(3) : "";
                    val3 = rs.getString(4) != null ? rs.getString(4) : "";
                    val4 = rs.getString(5) != null ? rs.getString(5) : "";
                    val5 = rs.getString(6) != null ? rs.getString(6) : "";
                    val6 = rs.getString(7) != null ? rs.getString(7) : "";
                    val7 = rs.getString(8) != null ? rs.getString(8) : "";
                    val8 = rs.getString(9) != null ? rs.getString(9) : "";
                    vald9 = rs.getString(10) != null ? rs.getString(10) : "";
                    valt9 = rs.getString(11) != null ? rs.getString(11) : "";
                    vald10 = rs.getString(12) != null ? rs.getString(12) : "";
                    valt10 = rs.getString(13) != null ? rs.getString(13) : "";
                    filename = rs.getString(14) != null ? rs.getString(14) : "";
                    status = rs.getInt(15);
                    batchId = rs.getInt(16);
                    type = rs.getInt(17);
                    list.add(new MobilizationInfo(batchId, type, mobilizationId, val1, val2, val3, val4, val5, val6, val7, val8, vald9, valt9, vald10, valt10, filename, status));
                }
                rs.close();
            } catch (Exception exception) {
                print(this, "getMobDataByCrewId :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return list;
    }

    public ArrayList getMobEmailFilesByCrewId(int CrewId)
    {
        ArrayList list = new ArrayList();

        int batch1 = 0, batch2 = 0;
        if (CrewId > 0) 
        {
            try 
            {
                conn = getConnection();
                String query1 = "SELECT i_batchid FROM t_batch WHERE i_type =1 AND i_draft =0 AND i_crewrotationid =? ORDER BY ts_regdate DESC LIMIT 0,1";
                String query2 = "SELECT i_batchid FROM t_batch WHERE i_type =2 AND i_draft =0 AND i_crewrotationid =? ORDER BY ts_regdate DESC LIMIT 0,1";

                pstmt = conn.prepareStatement(query1);
                pstmt.setInt(1, CrewId);
                print(this, "getMobEmailFilesByCrewId take batch 1 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    batch1 = rs.getInt(1);
                }
                rs.close();

                pstmt = conn.prepareStatement(query2);
                pstmt.setInt(1, CrewId);
                print(this, "getMobEmailFilesByCrewId take batch 2 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    batch2 = rs.getInt(1);
                }
                rs.close();

                if (batch1 > 0 || batch2 > 0) {
                    String ids = "";
                    if (batch1 > 0 && batch2 > 0) {
                        ids = batch1 + "," + batch2;
                    } else if (batch1 > 0) {
                        ids = "" + batch1;
                    } else if (batch2 > 0) {
                        ids = "" + batch2;
                    }
                    if (!ids.equals("")) 
                    {
                        StringBuilder sb = new StringBuilder();
                        sb.append("SELECT mb.i_mobilizationid, mb.s_val1, mb.s_val5, mb.s_filename, t_batch.i_type FROM t_mobilizationdtls AS mb ");
                        sb.append("LEFT JOIN t_batch ON (t_batch.i_batchid = mb.i_batchid) ");
                        sb.append("WHERE mb.i_batchid IN (" + ids + ") AND s_filename != '' AND s_filename IS NOT NULL ORDER BY mb.i_batchid ");

                        String query = sb.toString().intern();
                        sb.setLength(0);
                        pstmt = conn.prepareStatement(query);
                        print(this, "getMobEmailFilesByCrewId :: " + pstmt.toString());
                        rs = pstmt.executeQuery();
                        String val1, val5, filename;
                        int mobilizationId, type;
                        while (rs.next()) 
                        {
                            mobilizationId = rs.getInt(1);
                            val1 = rs.getString(2) != null ? rs.getString(2) : "";
                            val5 = rs.getString(3) != null ? rs.getString(3) : "";
                            filename = rs.getString(4) != null ? rs.getString(4) : "";
                            type = rs.getInt(5);
                            list.add(new MobilizationInfo(CrewId, mobilizationId, val1, val5, filename, type));
                        }
                        rs.close();
                    }
                }
            } catch (Exception exception) {
                print(this, "getMobDataByCrewId :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return list;
    }

    public ArrayList getMobEmailFilesByMobId(String mobIds, int type)
    {
        ArrayList list = new ArrayList();
        try 
        {
            conn = getConnection();
            if (!mobIds.equals("")) 
            {
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT i_mobilizationid, s_val1, s_val5, s_filename FROM t_mobilizationdtls ");
                sb.append("WHERE i_mobilizationid IN (" + mobIds + ") AND s_filename != '' AND s_filename IS NOT NULL ORDER BY i_mobilizationid ");
                String query = sb.toString().intern();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                print(this, "getMobEmailFilesByMobId :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String val1, val5, filename;
                int mobilizationId;
                while (rs.next())
                {
                    mobilizationId = rs.getInt(1);
                    val1 = rs.getString(2) != null ? rs.getString(2) : "";
                    val5 = rs.getString(3) != null ? rs.getString(3) : "";
                    filename = rs.getString(4) != null ? rs.getString(4) : "";
                    list.add(new MobilizationInfo(mobilizationId, val1, val5, filename, type));
                }
                rs.close();
            }
        } catch (Exception exception) {
            print(this, "getMobEmailFilesByMobId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public MobilizationInfo getCandDtlsByCrewId(int CrewId)
    {
        MobilizationInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT cr.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), c.s_photofilename, cr.i_clientid, t_client.s_name, cr.i_clientassetid, ");
        sb.append("t_clientasset.s_name, t_country.s_name, t_position.s_name, t_grade.s_name FROM t_crewrotation AS cr ");
        sb.append("LEFT JOIN t_candidate AS c ON (c.i_candidateid = cr.i_candidateid) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = cr.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = cr.i_clientassetid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_clientasset.i_countryid) ");
        sb.append("LEFT JOIN t_jobpost ON (t_jobpost.i_jobpostid = cr.i_jobpostid)");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_jobpost.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_jobpost.i_gradeid) ");
        sb.append("WHERE cr.i_active =1 AND cr.i_crewrotationid =? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        if (CrewId > 0) 
        {
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, CrewId);
                print(this, "getCandDtlsByCrewId :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String name, clientname, assetname, country, position, grade, filename;
                int candidateId, clientId, assetId;
                while (rs.next()) 
                {
                    candidateId = rs.getInt(1);
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    filename = rs.getString(3) != null ? rs.getString(3) : "";
                    clientId = rs.getInt(4);
                    clientname = rs.getString(5) != null ? rs.getString(5) : "";
                    assetId = rs.getInt(6);
                    assetname = rs.getString(7) != null ? rs.getString(7) : "";
                    country = rs.getString(8) != null ? rs.getString(8) : "";
                    position = rs.getString(9) != null ? rs.getString(9) : "";
                    grade = rs.getString(10) != null ? rs.getString(10) : "";
                    info = new MobilizationInfo(candidateId, name, filename, clientId, clientname, assetId, assetname, country, position, grade, CrewId);
                }
                rs.close();
            } catch (Exception exception) {
                print(this, "getCandDtlsByCrewId :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return info;
    }

    public MobilizationInfo getDetailsByMobId(int MobId) 
    {
        MobilizationInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_mobilizationid, s_val1, s_val2, s_val3, s_val4, s_val5, s_val6, s_val7, s_val8, DATE_FORMAT(s_val9, '%d-%b-%Y'), DATE_FORMAT(s_val9, '%H:%i'), ");
        sb.append("DATE_FORMAT(s_val10, '%d-%b-%Y'), DATE_FORMAT(s_val10, '%H:%i'), s_filename, i_status, i_batchid FROM t_mobilizationdtls ");
        sb.append("WHERE i_mobilizationid =? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        if (MobId > 0) 
        {
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, MobId);
                print(this, "getDetailsByMobId :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String val1, val2, val3, val4, val5, val6, val7, val8, vald9, valt9, vald10, valt10, filename;
                int mobilizationId, status, batchId;
                while (rs.next()) 
                {
                    mobilizationId = rs.getInt(1);
                    val1 = rs.getString(2) != null ? rs.getString(2) : "";
                    val2 = rs.getString(3) != null ? rs.getString(3) : "";
                    val3 = rs.getString(4) != null ? rs.getString(4) : "";
                    val4 = rs.getString(5) != null ? rs.getString(5) : "";
                    val5 = rs.getString(6) != null ? rs.getString(6) : "";
                    val6 = rs.getString(7) != null ? rs.getString(7) : "";
                    val7 = rs.getString(8) != null ? rs.getString(8) : "";
                    val8 = rs.getString(9) != null ? rs.getString(9) : "";
                    vald9 = rs.getString(10) != null ? rs.getString(10) : "";
                    valt9 = rs.getString(11) != null ? rs.getString(11) : "";
                    vald10 = rs.getString(12) != null ? rs.getString(12) : "";
                    valt10 = rs.getString(13) != null ? rs.getString(13) : "";
                    filename = rs.getString(14) != null ? rs.getString(14) : "";
                    status = rs.getInt(15);
                    batchId = rs.getInt(16);
                    info = new MobilizationInfo(batchId, 0, mobilizationId, val1, val2, val3, val4, val5, val6, val7, val8, vald9, valt9, vald10, valt10, filename, status);
                }
                rs.close();
            } catch (Exception exception) {
                print(this, "getDetailsByMobId :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return info;
    }

    public int updateMobilizationDtls(int mobId, int type, String val1, String val2, String val3, String val4, String val5, String val6, String val7, String val8,
            String vald9, String valt9, String vald10, String valt10, String filename, int userId) {
        try 
        {
            conn = getConnection();
            if (mobId > 0) 
            {
                StringBuilder sb = new StringBuilder();
                sb.append("UPDATE t_mobilizationdtls SET ");
                sb.append("s_val1 =?, ");
                sb.append("s_val2 =?, ");
                sb.append("s_val3 =?, ");
                sb.append("s_val4 =?, ");
                sb.append("s_val5 =?, ");
                sb.append("s_val6 =?, ");
                sb.append("s_val7 =?, ");
                sb.append("s_val8 =?, ");
                sb.append("s_val9 =?, ");
                sb.append("s_val10 =?, ");
                if (filename != null && !filename.equals("")) {
                    sb.append("s_filename =?, ");
                }
                sb.append("i_userid =?, ");
                sb.append("ts_moddate =? ");
                sb.append("WHERE i_mobilizationid =? ");
                String mobquery = sb.toString().intern();
                sb.setLength(0);
                int scc = 0;
                pstmt = conn.prepareStatement(mobquery);
                pstmt.setString(++scc, val1);
                pstmt.setString(++scc, val2);
                pstmt.setString(++scc, val3);
                pstmt.setString(++scc, val4);
                pstmt.setString(++scc, val5);
                pstmt.setString(++scc, val6);
                pstmt.setString(++scc, val7);
                pstmt.setString(++scc, val8);
                pstmt.setString(++scc, changeDate1(vald9) + " " + valt9 + ":00");
                pstmt.setString(++scc, changeDate1(vald10) + " " + valt10 + ":00");
                if (filename != null && !filename.equals("")) {
                    pstmt.setString(++scc, filename);
                }
                pstmt.setInt(++scc, userId);
                pstmt.setString(++scc, currDate1());
                pstmt.setInt(++scc, mobId);
                print(this, "updateMobilizationDtls :: " + pstmt.toString());
                pstmt.executeUpdate();
            }
            pstmt.close();
        } catch (Exception exception) {
            print(this, "updateMobilizationDtls catch :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return mobId;
    }

    public int sendMobilizationMail(String mailfrom, String mailto, String mailcc, String mailbcc, String subject, String description,
            String[] candidatename, String[] filename, String clientname, String vfilename, String username) throws MessagingException {
        int val = 0;
        String to[] = parseCommaDelimString(mailto);
        String cc[] = parseCommaDelimString(mailcc);
        String bcc[] = parseCommaDelimString(mailbcc);
        String sdescription = description.replaceAll("\n", "<br/>");
        String mailbody = getpatchbody(sdescription, "mail_selection.html");

        String file_maillog = getMainPath("file_maillog");
        java.util.Date nowmail = new java.util.Date();
        String fn_mail = "cvma-" + String.valueOf(nowmail.getTime());
        String filePath_html = createFolder(file_maillog);
        String fname = writeHTMLFile(mailbody, file_maillog + "/" + filePath_html, fn_mail + ".html");

        StatsInfo sinfo = postMailAttachMF(to, cc, bcc, mailbody, subject, filename, candidatename, -1);
        String from = "";

        if (sinfo != null) {
            from = sinfo.getDdlLabel();
            val = sinfo.getDdlValue();
        }
        if (val == 1) {
            int maillogId = createSpacificMailLog(17, clientname, mailto, mailcc, mailbcc, from, subject, "/" + filePath_html + "/" + fname, vfilename, 0, username, 4);
        }
        return val;
    }

    public String getpatchbody(String description, String htmlFile) {
        String adminpage = getMainPath("template_path") + htmlFile;
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("MAILBODY", description);
        return template.patch(hashmap);
    }

    public Collection getPostions(int clientassetId) 
    {
        Collection coll = new LinkedList();
        if (clientassetId > 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_position.i_positionid, t_position.s_name, t_grade.s_name FROM t_clientassetposition ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_clientassetposition.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("WHERE t_clientassetposition.i_clientassetid = ? AND t_position.i_status = 1 ORDER BY t_position.s_name, t_grade.s_name");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new MobilizationInfo(-1, " Select Position "));
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                logger.info("getPostions :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) 
                {
                    coll.add(new MobilizationInfo(rs.getInt(1), rs.getString(2) + " - " + rs.getString(3)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new MobilizationInfo(-1, " Select Position "));
        }
        return coll;
    }

    public MobilizationInfo setEmailDetail(int crewId) 
    {
        MobilizationInfo info = null;
        StringBuilder sb = new StringBuilder();
        try
        {
            if (crewId > 0) 
            {
                sb.append("SELECT t_crewrotation.i_candidateid, t_candidate.s_firstname, t_candidate.s_email, t_crewrotation.i_clientid, t_client.s_name, t_client.s_email, ");
                sb.append("t_clientasset.s_name, DATE_FORMAT(t_crewrotation.ts_regdate,'%d-%b-%Y') FROM t_crewrotation ");
                sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_crewrotation.i_candidateid) ");
                sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_crewrotation.i_clientid) ");
                sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_crewrotation.i_clientassetid) ");
                sb.append("WHERE t_crewrotation.i_active =1 AND t_crewrotation.i_crewrotationid = ?");
                String query = sb.toString().intern();
                sb.setLength(0);
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, crewId);
                print(this, "setEmailDetail :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) 
                {
                    int candidateId = rs.getInt(1);
                    String candidateName = rs.getString(2) != null ? rs.getString(2) : "";
                    String candidatemail = rs.getString(3) != null ? rs.getString(3) : "";
                    int clientId = rs.getInt(4);
                    String clientname = rs.getString(5) != null ? rs.getString(5) : "";
                    String clientemail = rs.getString(6) != null ? rs.getString(6) : "";
                    String clientAsset = rs.getString(7) != null ? rs.getString(7) : "";
                    String date = rs.getString(8) != null ? rs.getString(8) : "";
                    info = new MobilizationInfo(candidateId, candidateName, candidatemail, clientId, clientname, clientemail, clientAsset, date);
                }
            }
        } catch (Exception exception) {
            print(this, "setEmailDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getListFromList(ArrayList list, int type)
    {
        ArrayList l = new ArrayList();
        int size = list.size();
        for (int i = 0; i < size; i++) 
        {
            MobilizationInfo info = (MobilizationInfo) list.get(i);
            if (info != null && info.getType() == type) 
            {
                l.add(info);
            }
        }
        return l;
    }

    public int createSpacificMailLog(int type, String name, String to, String cc, String bcc, String from, String subject, String filename, String attachmentpath, int shortlistId,
            String username, int oflag) 
    {
        int id = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_maillog ");
            sb.append("(i_type, s_name, s_to, s_cc, s_bcc, s_from, s_subject, s_filename, ts_regdate, ts_moddate, s_attachmentpath3, i_shortlistid, s_sendby, i_oflag) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            String query = sb.toString().intern();
            sb.setLength(0);
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
            pstmt.setInt(12, shortlistId);
            pstmt.setString(13, username);
            pstmt.setInt(14, oflag);
            print(this, "createSpacificMailLog :: " + pstmt.toString());
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
