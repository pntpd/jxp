package com.web.jxp.onboarding;

import com.web.jxp.base.Base;
import static com.web.jxp.base.Base.dateFolder;
import static com.web.jxp.base.Base.decipher;
import static com.web.jxp.base.Base.parseCommaDelimString;
import com.web.jxp.base.StatsInfo;
import com.web.jxp.base.Template;
import com.web.jxp.common.Common;
import static com.web.jxp.common.Common.*;
import com.web.jxp.mobilization.Mobilization;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.mail.MessagingException;
import org.zefer.pd4ml.PD4Constants;

public class Onboarding extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Mobilization mobilization = new Mobilization();
    String view_path = getMainPath("view_candidate_file");
    String resumepdffile_path = getMainPath("add_resumetemplate_pdf");

    public ArrayList getClientSelectByName(String search, int statusIndex, int next, int count,
            int clientIdIndex, int assetIdIndex, int allclient, String permission, String cids, String assetids) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_client.i_clientid, t_client.s_name,t_clientasset.i_clientassetid, t_clientasset.s_name, t_country.s_name, jp.su, t2.ct, t_clientasset.i_countryid FROM t_jobpost ");
        sb.append("LEFT JOIN t_client on (t_client.i_clientid = t_jobpost.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset on (t_clientasset.i_clientassetid = t_jobpost.i_clientassetid) ");
        sb.append("LEFT JOIN t_country on (t_country.i_countryid = t_clientasset.i_countryid) ");
        sb.append("LEFT JOIN (select cp.i_clientid, cp.i_clientassetid, cp.i_jobpostid, sum( i_noofopening) as su FROM t_jobpost as cp group by cp.i_clientassetid, cp.i_clientid ) as jp on (jp.i_clientassetid = t_jobpost.i_clientassetid and jp.i_clientid = t_jobpost.i_clientid)");
        sb.append("LEFT JOIN (select t_jobpost.i_clientid, t_jobpost.i_clientassetid, count(1) as ct FROM t_shortlist ");
        sb.append("LEFT JOIN t_jobpost on (t_jobpost.i_jobpostid = t_shortlist.i_jobpostid) WHERE t_jobpost.i_status IN (1,3) and t_shortlist.i_onboard = 9 ");
        sb.append("group by t_jobpost.i_clientid, t_jobpost.i_clientassetid) as t2 on (t2.i_clientid = t_client.i_clientid and t2.i_clientassetid = t_clientasset.i_clientassetid) ");
        sb.append("WHERE t_jobpost.i_status IN (1,3) AND t_client.i_status = 1 AND t_clientasset.i_status = 1 ");
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
            sb.append("AND t_jobpost.i_status = ?  ");
        }
        if (clientIdIndex > 0) {
            sb.append(" AND t_client.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND t_clientasset.i_clientassetid = ? ");
        }

        if (search != null && !search.equals("")) {
            sb.append("AND (t_clientasset.s_name LIKE ? OR t_client.s_name LIKE ?  OR t_country.s_name LIKE ? ) ");
        }

        sb.append(" group by t_jobpost.i_clientid, t_jobpost.i_clientassetid ");

        sb.append("ORDER BY t_jobpost.i_status, t_jobpost.ts_regdate ");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("select count(1) FROM ( select count(1) FROM t_jobpost  LEFT JOIN t_client on (t_client.i_clientid = t_jobpost.i_clientid)");
        sb.append("LEFT JOIN t_clientasset on (t_clientasset.i_clientassetid = t_jobpost.i_clientassetid)");
        sb.append("LEFT JOIN t_country on (t_country.i_countryid = t_clientasset.i_countryid)");
        sb.append("LEFT JOIN (select cp.i_clientid, cp.i_clientassetid,  cp.i_jobpostid, sum( i_noofopening) as su  FROM t_jobpost as cp group by cp.i_clientassetid ) as jp on (jp.i_clientassetid = t_jobpost.i_clientassetid and jp.i_clientid = t_jobpost.i_clientid)");
        sb.append("LEFT JOIN (select t_jobpost.i_clientid, t_jobpost.i_clientassetid, count(1) as ct FROM t_shortlist ");
        sb.append("LEFT JOIN t_jobpost on (t_jobpost.i_jobpostid = t_shortlist.i_jobpostid) WHERE t_jobpost.i_status IN (1,3) and t_shortlist.i_onboard = 1 ");
        sb.append("group by t_jobpost.i_clientid, t_jobpost.i_clientassetid) as t2 on (t2.i_clientid = t_client.i_clientid and t2.i_clientassetid = t_clientasset.i_clientassetid) ");
        sb.append("WHERE t_jobpost.i_status IN (1,3) AND t_client.i_status = 1 AND t_clientasset.i_status = 1 ");
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
            sb.append("AND t_jobpost.i_status = ? ");
        }
        if (clientIdIndex > 0) {
            sb.append(" AND t_client.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append(" AND t_clientasset.i_clientassetid = ? ");
        }

        if (search != null && !search.equals("")) {
            sb.append("AND (t_clientasset.s_name LIKE ? OR t_client.s_name LIKE ?  OR t_country.s_name LIKE ? ) ");
        }
        sb.append(" group by t_jobpost.i_clientid, t_jobpost.i_clientassetid ");

        sb.append(" ) as t45  ");

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

            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getClientSelectByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAsset, country = "";
            int clientId, clientassetId, totalnoofopenings, onboardCount, countryId;
            while (rs.next()) 
            {
                clientId = rs.getInt(1);
                clientName = rs.getString(2) != null ? rs.getString(2) : "";
                clientassetId = rs.getInt(3);
                clientAsset = rs.getString(4) != null ? rs.getString(4) : "";
                country = rs.getString(5) != null ? rs.getString(5) : "";
                totalnoofopenings = rs.getInt(6);
                onboardCount = rs.getInt(7);
                countryId = rs.getInt(8);
                if (!clientAsset.equals("")) 
                {
                    clientName += " - " + clientAsset;
                }
                
                list.add(new OnboardingInfo(clientId, clientName, clientassetId, clientAsset, country, totalnoofopenings, onboardCount, countryId));
            }
            rs.close();
            
            clientId = 0;
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

            if (search != null && !search.equals("")) 
            {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            print(this, "getClientSelectByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                clientId = rs.getInt(1);
                list.add(new OnboardingInfo(clientId, "", 0, "", "", 0, 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public String getccStatusbyId(int onboardflag) 
    {
        String stval = "";
        if (onboardflag != 9) {
            stval = "Pending";
        } else if (onboardflag == 9) {
            stval = "Completed";
        }
        return stval;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) 
    {
        ArrayList list = null;
        HashMap record = null;
        OnboardingInfo info;
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
                    info = (OnboardingInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }

            Map map;
            map = sortByName(record, tp);

            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            OnboardingInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (OnboardingInfo) l.get(i);
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

    // to company sort search result
    public String getInfoValue(OnboardingInfo info, String i) 
    {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getClientName() != null ? info.getClientName() : "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getCountry() != null ? info.getCountry() : "";
        }
        return infoval;
    }

    public Collection getClients() 
    {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_clientid, s_name FROM t_client WHERE i_status = 1 ");
        sb.append("order by s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new OnboardingInfo(-1, " Select Client"));
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
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new OnboardingInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getClientAsset(int clientId) 
    {
        Collection coll = new LinkedList();
        if (clientId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_clientid = ? AND i_status =1 ");
            sb.append("ORDER BY s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new OnboardingInfo(-1, " Select Asset "));
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
                    ddlLabel = rs.getString(2) != null ? rs.getString(2): "";
                    coll.add(new OnboardingInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new OnboardingInfo(-1, " Select Asset "));
        }
        return coll;
    }

    public Collection getAsset(String assetids) 
    {
        Collection coll = new LinkedList();
        if (!assetids.equals("")) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_status = 1 ");
            if (!assetids.equals("")) {
                sb.append("AND i_clientassetid IN (" + assetids + ") ");
            }
            sb.append("ORDER BY s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new OnboardingInfo(-1, " Select Asset "));
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) 
                {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2): "";
                    coll.add(new OnboardingInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new OnboardingInfo(-1, " Select Asset "));
        }
        return coll;
    }

    public Collection getPostions(int clientassetId) 
    {
        Collection coll = new LinkedList();
        if (clientassetId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_clientassetposition.i_positionid, t_position.s_name, t_grade.s_name, t_position.i_gradeid FROM t_clientassetposition ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_clientassetposition.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("WHERE t_clientassetposition.i_clientassetid = ? ORDER BY t_position.s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new OnboardingInfo("", " Select Position - Rank "));
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                Common.print(this, "getPostions :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue, gradeId;
                String ddlLabel = "", grade, position, positionValue = "";
                while (rs.next())
                {
                    ddlValue = rs.getInt(1);
                    position = rs.getString(2) != null ? rs.getString(2): "";
                    grade = rs.getString(3) != null ? rs.getString(3) : "";
                    gradeId = rs.getInt(4);
                    ddlLabel = position;
                    if (!grade.equals("")) {
                        ddlLabel += " - " + grade;
                    }
                    positionValue = position;
                    if (!position.equals("")) {
                        positionValue += " # " + gradeId;
                    }
                    coll.add(new OnboardingInfo(positionValue, ddlLabel));
                    positionValue = "";
                    ddlLabel = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new OnboardingInfo("", " Select Position - Rank "));
        }
        return coll;
    }

    public OnboardingInfo getonboardingByIdforDetail(int clientId, int clientassetId)
    {
        OnboardingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_name, t_clientasset.s_name, t_country.s_name, jp1.su, t2.ct ,p.ct1 FROM t_client ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientid = t_client.i_clientid) ");
        sb.append("LEFT JOIN t_country ON ( t_country.i_countryid = t_clientasset.i_countryid) ");
        sb.append("LEFT JOIN (SELECT cp.i_clientid, cp.i_clientassetid, cp.i_jobpostid, SUM(i_noofopening) AS su FROM t_jobpost AS cp GROUP BY cp.i_clientassetid, ");
        sb.append("cp.i_clientid) AS jp1 ON (jp1.i_clientassetid = t_clientasset.i_clientassetid AND jp1.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN (SELECT t_jobpost.i_clientid, t_jobpost.i_clientassetid, COUNT(1) AS ct FROM t_shortlist  ");
        sb.append("LEFT JOIN t_jobpost ON (t_jobpost.i_jobpostid = t_shortlist.i_jobpostid) WHERE t_jobpost.i_status IN (1,3) and t_shortlist.i_onboard = 9 GROUP BY ");
        sb.append("t_jobpost.i_clientid, t_jobpost.i_clientassetid) AS t2 ON (t2.i_clientid = t_client.i_clientid AND t2.i_clientassetid = t_clientasset.i_clientassetid) ");
        sb.append("LEFT JOIN (SELECT  t_jobpost.i_positionid, t_jobpost.i_clientid, t_jobpost.i_clientassetid ,COUNT(distinct i_positionid) AS ct1 FROM t_jobpost WHERE ");
        sb.append("t_jobpost.i_status IN (1,3) GROUP BY t_jobpost.i_clientid, t_jobpost.i_clientassetid ) AS p ON (p.i_clientid = t_clientasset.i_clientid AND p.i_clientassetid = t_clientasset.i_clientassetid) ");
        sb.append("WHERE t_client.i_clientid =? AND t_clientasset.i_clientassetid =? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientId);
            pstmt.setInt(2, clientassetId);
            print(this, "getClientSelectionByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAsset, country;
            int onboardCount, totalnoofopenings, positionCount;
            while (rs.next()) 
            {
                clientName = rs.getString(1) != null ? rs.getString(1) : "";
                clientAsset = rs.getString(2) != null ? rs.getString(2) : "";
                country = rs.getString(3) != null ? rs.getString(3) : "";
                totalnoofopenings = rs.getInt(4);
                onboardCount = rs.getInt(5);
                positionCount = rs.getInt(6);
                
                info = new OnboardingInfo(clientId, clientassetId, clientName, positionCount, clientAsset, onboardCount, totalnoofopenings, country);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getClientSelectionByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getSelectedCandidateListByIDs(int clientId, int clientassetId, String search, int onstatus) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();        
        sb.append("SELECT c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name, c.s_photofilename, ");
        sb.append("DATE_FORMAT(t_jobpost.ts_targetmobdate, '%d-%b-%Y'), t_shortlist.i_shortlistid, t_shortlist.i_onboard, ");
        sb.append("t_shortlist.i_onflag, c.i_countryid, t_shortlistmob.i_notreqtravel, ");
        sb.append("t_shortlistmob.i_notreqaccom FROM t_shortlist ");
        sb.append("LEFT JOIN t_jobpost ON (t_shortlist.i_jobpostid = t_jobpost.i_jobpostid) ");
        sb.append("LEFT JOIN t_candidate AS c  ON (c.i_candidateid = t_shortlist.i_candidateid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_shortlistmob ON (t_shortlistmob.i_shortlistid = t_shortlist.i_shortlistid) ");
        sb.append("WHERE t_shortlist.i_sflag =4 AND t_shortlist.i_oflag =4 AND t_jobpost.i_clientid =? AND t_jobpost.i_clientassetid =? ");
        if (onstatus > 0) {
            if (onstatus == 1) {
                sb.append("AND i_onboard !=9 ");
            } else if (onstatus == 2) {
                sb.append("AND i_onboard =2 ");
            } else if (onstatus == 3) {
                sb.append("AND i_onboard =3 ");
            } else if (onstatus == 4) {
                sb.append("AND i_onboard =3 ");
            } else if (onstatus == 5) {
                sb.append("AND i_onboard =3 ");
            } else if (onstatus == 6) {
                sb.append("AND i_onboard =4 ");
            } else if (onstatus == 7) {
                sb.append("AND i_onboard IN (5,6,7) ");
            } else if (onstatus == 8) {
                sb.append("AND i_onboard =9 ");
            }
        }
        if (!search.equals("")) {
            sb.append("AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? OR  t_position.s_name LIKE ? ");
            sb.append("OR t_grade.s_name LIKE ?) ");
        }
        sb.append("ORDER BY c.s_firstname ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientId);
            pstmt.setInt(++scc, clientassetId);
            if (!search.equals(""))
            {
                pstmt.setString(++scc, "%" + search + "%");
                pstmt.setString(++scc, "%" + search + "%");
                pstmt.setString(++scc, "%" + search + "%");
                pstmt.setString(++scc, "%" + search + "%");
            }

            logger.info("getSelectedCandidateListByIDs onboarding:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, position, grade, photo, mobilizedate;
            int candidateId, shortlistId, onboardflag, onflag, countryId, notereqtravel, notereqtaccomm;
            while (rs.next())
            {
                candidateId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                grade = rs.getString(4) != null ? rs.getString(4) : "";
                photo = rs.getString(5) != null ? rs.getString(5) : "";
                mobilizedate = rs.getString(6) != null ? rs.getString(6) : "";
                shortlistId = rs.getInt(7);
                onboardflag = rs.getInt(8);
                onflag = rs.getInt(9);
                countryId = rs.getInt(10);
                notereqtravel = rs.getInt(11);
                notereqtaccomm = rs.getInt(12);
                if (!grade.equals("")) {
                    position += " - " + grade;
                }

                list.add(new OnboardingInfo(candidateId, name, position, photo, mobilizedate, shortlistId, onboardflag, onflag,
                        countryId, notereqtravel, notereqtaccomm, 0, ""));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }

        return list;
    }

    public void unlockCandidate(int candidateId)
    {
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_candidate SET ");
            sb.append("i_progressid = 0 ");
            sb.append("WHERE i_candidateid = ? ");
            String candidatequery = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(candidatequery);
            pstmt.setInt(1, candidateId);
            pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "unlockCandidate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public OnboardingInfo getInfoFromList(ArrayList list, int shortlistId) 
    {
        print(this, "getInfoFromList");
        OnboardingInfo info = null;
        int size = list.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                OnboardingInfo info1 = (OnboardingInfo) list.get(i);
                if (info1 != null && info1.getShortlistId() == shortlistId) {
                    info = info1;
                    break;
                }
            }
        }
        return info;
    }

    public String getSeparatedString(String val) {
        String str = "";
        if (val != null && !val.equals("")) {
            String[] arr = val.split(",");
            for (int i = 1; i <= arr.length; i++) {
                str += "<span>" + i + "." + arr[i - 1] + "</span>";
            }
        }
        return str;
    }

    public static String currDate01() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        java.util.Date dt = cal.getTime();
        return sdf.format(dt);
    }

    public Collection getformalitiesById(int clientId)
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_formalityid, s_name FROM t_formality WHERE i_status = 1 and i_clientid = ? ORDER BY s_name").intern();
        coll.add(new OnboardingInfo(-1, "- Select -"));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientId);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new OnboardingInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public String createOnboardingFormFile(int shortlistId, int formalityId, int clientId, int assetId, int candidateId,
            String cval1s, String cval2s, String cval3s, String cval4s, String cval5s, String cval6s, String cval7s, String cval8s,
            String cval9s, String cval10s) 
    {
        String pdffilename = "";
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), DATE_FORMAT(c.d_dob, '%d-%b-%Y'), ");        
        sb.append("c.s_placeofbirth, c.s_email, c.s_code1, c.s_contactno1, c.s_code2, c.s_contactno2, c.s_code3, c.s_contactno3, c.s_gender, ");
        sb.append("t_country.s_name, n.s_nationality, c.s_address1line1, c.s_address1line2,  c.s_address2line1, c.s_address2line2, ");
        sb.append("c.s_address2line3, c.s_nextofkin, t_relation.s_name, c.s_ecode1, c.s_econtactno1, c.s_ecode2, c.s_econtactno2, ");
        sb.append("t_maritialstatus.s_name, c.s_photofilename, t_position.s_name , c.s_address1line3, t_city.s_name,t_experiencedept.s_name, ");
        sb.append("c.i_expectedsalary,t_currency.s_name, c.s_firstname, t_grade.s_name,  c.s_lastname, c.s_middlename, ");
        sb.append("ROUND(DATEDIFF(CASE WHEN c.d_dob = '0000-00-00' THEN c.d_dob ELSE CURRENT_DATE() END, c.d_dob) / 365, 1 ) AS age, c.i_candidateid, ");
        sb.append("p2.s_name, g2.s_name, t_dayrate.d_rate1, t_dayrate.d_rate2, d2.d_rate1, d2.d_rate2 ");
        sb.append("FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = c.i_countryid) ");
        sb.append("LEFT JOIN t_country AS n ON (n.i_countryid = c.i_nationalityid) ");
        sb.append("LEFT JOIN t_city  ON (t_city.i_cityid = c.i_cityid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_position AS p2 ON (p2.i_positionid = c.i_positionid2) ");
        sb.append("LEFT JOIN t_grade AS g2 ON (g2.i_gradeid = p2.i_gradeid) ");
        sb.append("LEFT JOIN t_relation ON (t_relation.i_relationid = c.i_relationid) ");
        sb.append("LEFT JOIN t_maritialstatus ON (t_maritialstatus.i_maritialstatusid = c.i_maritialstatusid) ");
        sb.append("LEFT JOIN t_experiencedept ON (t_experiencedept.i_experiencedeptid = c.i_departmentid) ");
        sb.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = c.i_currencyid) ");
        sb.append("LEFT JOIN t_dayrate ON (t_dayrate.i_candidateid = c.i_candidateid AND t_dayrate.i_positionid = c.i_positionid AND t_dayrate.i_clientassetid = c.i_clientassetid) ");
        sb.append("LEFT JOIN t_dayrate AS d2 ON (d2.i_candidateid = c.i_candidateid AND d2.i_positionid = c.i_positionid2 AND d2.i_clientassetid = c.i_clientassetid) ");
        if(shortlistId > 0)
        {
            sb.append("LEFT JOIN t_shortlist ON (t_shortlist.i_candidateid = c.i_candidateid) "); 
        }
        sb.append("WHERE c.i_status = 1 ");
        if(shortlistId > 0){
            sb.append("AND t_shortlist.i_shortlistid = ? ");
        }else{
            sb.append("AND c.i_candidateid = ? ");            
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        Connection conn = null;
        ResultSet rs = null;       
        
        StringBuilder sbhealth = new StringBuilder();
        sbhealth.append("SELECT s_ssmf, s_ogukmedicalftw, DATE_FORMAT(d_ogukexp, '%d-%b-%Y') , s_medifitcert, ");
        sbhealth.append("DATE_FORMAT(d_medifitcertexp, '%d-%b-%Y'), s_bloodgroup ");
        sbhealth.append("FROM t_healthdeclaration ");
        sbhealth.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_healthdeclaration.i_candidateid) ");
        sbhealth.append("LEFT JOIN t_bloodpressure ON (t_bloodpressure.i_bloodpressureid = t_healthdeclaration.i_bloodpressureid) ");
        sbhealth.append("WHERE t_healthdeclaration.i_candidateid =? ");
        String healthquery = sbhealth.toString();
        sbhealth.setLength(0);
        PreparedStatement pstmthealth = null ;
        ResultSet hrs = null;       
        
        String clientname = "";
        StringBuilder clientinner = new StringBuilder();
        clientinner.append(" SELECT s_name FROM t_client WHERE i_status = 1 AND i_clientid = ? ");
        String clientquery = clientinner.toString();
        clientinner.setLength(0);
        PreparedStatement pstmtclient = null;

        String clientasset = "";
        StringBuilder assetinner = new StringBuilder();
        assetinner.append(" SELECT s_name FROM t_clientasset WHERE i_status = 1 AND i_clientassetid = ? ");
        String assetquery = assetinner.toString();
        assetinner.setLength(0);
        PreparedStatement pstmtasset = null;
        
        StringBuilder sbdoc = new StringBuilder();
        sbdoc.append("SELECT t_doctype.s_doc, t_govdoc.s_docno, t_city.s_name, t_documentissuedby.s_name, DATE_FORMAT(t_govdoc.d_dateofissue, '%d-%b-%Y'), ");
        sbdoc.append("DATE_FORMAT(t_govdoc.d_expirydate, '%d-%b-%Y'), t_country.s_name, t_govdoc.i_doctypeid ");
        sbdoc.append("FROM t_govdoc ");
        sbdoc.append("LEFT JOIN t_doctype ON (t_govdoc.i_doctypeid = t_doctype.i_doctypeid) ");
        sbdoc.append("LEFT JOIN t_city ON (t_govdoc.i_placeofissueid = t_city.i_cityid) ");
        sbdoc.append("LEFT JOIN t_documentissuedby ON (t_govdoc.i_documentissuedbyid = t_documentissuedby.i_documentissuedbyid) ");
        sbdoc.append("LEFT JOIN t_country ON (t_city.i_countryid = t_country.i_countryid) ");
        sbdoc.append("WHERE t_govdoc.i_status =1 AND t_govdoc.i_doctypeid IN (2,5,9) AND t_govdoc.i_candidateid =?  ");
        String docsbinnnerquery = (sbdoc.toString()).intern();
        sbdoc.setLength(0);
        PreparedStatement pstmtdocinner = null;
        
        StringBuilder sbinnner = new StringBuilder();
        sbinnner.append("SELECT  t_position.s_name,  t_experiencedept.s_name, t_workexperience.s_companyname, t_workexperience.s_assetname,  ");
        sbinnner.append("DATE_FORMAT(t_workexperience.d_workstartdate, '%d-%b-%Y'), DATE_FORMAT(t_workexperience.d_workenddate, '%d-%b-%Y'), ");
        sbinnner.append("t_workexperience.i_currentworkingstatus, ");
        sbinnner.append("ROUND(DATEDIFF(IF(d_workenddate IS NULL OR d_workenddate = '0000-00-00', current_date(), d_workenddate), d_workstartdate) / 365, 1) AS exp ");
        sbinnner.append("FROM t_workexperience ");
        sbinnner.append("LEFT JOIN t_position ON (t_position.i_positionid = t_workexperience.i_positionid) ");
        sbinnner.append("LEFT JOIN t_experiencedept ON (t_experiencedept.i_experiencedeptid = t_workexperience.i_departmentid) ");
        sbinnner.append("WHERE t_workexperience.i_status = 1 AND t_workexperience.i_candidateid = ? ");
        String sbinnnerquery = sbinnner.toString();
        sbinnner.setLength(0);
        PreparedStatement pstmtinner = null;

        StringBuilder certsbinnner = new StringBuilder();
        certsbinnner.append("SELECT  t_coursename.s_name,  t_coursetype.s_name, t_trainingandcert.s_educinst, t_city.s_name,  t_approvedby.s_name, ");
        certsbinnner.append("DATE_FORMAT(t_trainingandcert.d_dateofissue, '%d-%b-%Y'), DATE_FORMAT(t_trainingandcert.d_expirydate, '%d-%b-%Y'), ");
        certsbinnner.append("t_country.s_name, t_trainingandcert.s_certificateno ");
        certsbinnner.append("FROM t_trainingandcert ");
        certsbinnner.append("LEFT JOIN t_coursetype ON ( t_coursetype.i_coursetypeid = t_trainingandcert.i_coursetypeid ) ");
        certsbinnner.append("LEFT JOIN t_coursename ON (t_coursename.i_coursenameid = t_trainingandcert.i_coursenameid) ");
        certsbinnner.append("LEFT JOIN t_city ON ( t_city.i_cityid = t_trainingandcert.i_locationofinstitid ) ");
        certsbinnner.append("LEFT JOIN t_country ON ( t_city.i_countryid = t_country. i_countryid ) ");
        certsbinnner.append("LEFT JOIN t_approvedby ON ( t_approvedby.i_approvedbyid = t_trainingandcert.i_approvedbyid ) WHERE t_trainingandcert.i_candidateid = ? AND t_trainingandcert.i_status = 1 ");
        String certsbinnnerquery = certsbinnner.toString();
        certsbinnner.setLength(0);
        PreparedStatement pstmtcertinner = null;

        StringBuilder edusbinnner = new StringBuilder();
        edusbinnner.append("SELECT  t_qualificationtype.s_name,  t_degree.s_name, t_eduqual.s_eduinstitute, t_city.s_name,t_eduqual.s_fieldofstudy,  ");
        edusbinnner.append("DATE_FORMAT(t_eduqual.d_coursestart, '%d-%b-%Y'), DATE_FORMAT(t_eduqual.d_passingyear, '%d-%b-%Y') , ");
        edusbinnner.append("t_country.s_name, t_eduqual.i_highestqualification ");
        edusbinnner.append("FROM t_eduqual ");
        edusbinnner.append("LEFT JOIN t_qualificationtype ON (t_qualificationtype.i_qualificationtypeid = t_eduqual.i_qualificationtypeid) ");
        edusbinnner.append("LEFT JOIN t_city ON (t_city.i_cityid = t_eduqual.i_locationofinstituteid) ");
        edusbinnner.append("LEFT JOIN t_degree ON (t_degree.i_degreeid = t_eduqual. i_degreeid) ");
        edusbinnner.append("LEFT JOIN t_country ON (t_city.i_countryid = t_country. i_countryid) ");
        edusbinnner.append("WHERE t_eduqual.i_status = 1 AND t_eduqual.i_candidateid = ? ");
        String edusbinnnerquery = edusbinnner.toString();
        edusbinnner.setLength(0);
        PreparedStatement pstmteduinner = null;
        sb.setLength(0);
        
        StringBuilder sbdoctype = new StringBuilder();
        sbdoctype.append("SELECT i_doctypeid, s_docno, DATE_FORMAT(d_dateofissue, '%d %b %Y'), DATE_FORMAT(d_expirydate,'%d %b %Y'), t_city.s_name ");
        sbdoctype.append("FROM t_govdoc ");
        sbdoctype.append("LEFT JOIN t_city ON (t_city.i_cityid = t_govdoc.i_placeofissueid) ");
        sbdoctype.append("WHERE i_doctypeid IN (1,2,5,9) AND i_candidateid = ? AND t_govdoc.i_status = 1 ");
        String docquery = sbdoctype.toString();
        sbdoctype.setLength(0);
        PreparedStatement pstmtdoc = null;
        
        StringBuilder sbvac = new StringBuilder();
        sbvac.append("SELECT t_vaccine.s_name, t_city.s_name,DATE_FORMAT(t_vbd.d_dateofapplication, '%d-%b-%Y'), ");
        sbvac.append("DATE_FORMAT(t_vbd.d_dateofexpiry, '%d-%b-%Y') , t_country.s_name ");
        sbvac.append("FROM t_vbd ");
        sbvac.append("LEFT JOIN t_vaccine ON (t_vbd.i_vaccinenameid = t_vaccine.i_vaccineid) ");
        sbvac.append("LEFT JOIN t_city ON (t_vbd.i_placeofapplicationid = t_city.i_cityid) ");
        sbvac.append("LEFT JOIN t_country ON (t_country.i_countryid = t_city.i_countryid) ");
        sbvac.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_vbd.i_candidateid) ");
        sbvac.append("WHERE t_vbd.i_candidateid = ? AND  t_vbd.i_status = 1 ");
        sbvac.append("ORDER BY t_vbd.i_status, t_vaccine.s_name ");
        String vacquery = sbvac.toString();
        sbvac.setLength(0);
        PreparedStatement pstmtvac = null;
        
        StringBuilder sblang = new StringBuilder();
        sblang.append("SELECT t_language.s_name, t_proficiency.s_name FROM t_candlang ");
        sblang.append("LEFT JOIN t_language ON (t_candlang.i_languageid = t_language.i_languageid) ");
        sblang.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_candlang.i_candidateid) ");
        sblang.append("LEFT JOIN t_proficiency ON (t_candlang.i_proficiencyid = t_proficiency.i_proficiencyid) ");
        sblang.append("WHERE t_candlang.i_candidateid = ? ");
        String langquery = sblang.toString();
        sblang.setLength(0);
        PreparedStatement pstmtlang = null;
        
        StringBuilder banksb = new StringBuilder();
        banksb.append("SELECT s_savingaccno, s_ifsccode ");
        banksb.append("FROM t_bankdetail ");
        banksb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_bankdetail.i_candidateid) ");
        banksb.append("WHERE t_candidate.i_candidateid = ? AND t_bankdetail.i_primarybankid = 1 LIMIT 0,1 ");
        String bankquery = banksb.toString();
        banksb.setLength(0);
        PreparedStatement pstmtbank = null;
        
        String fle_query = ("SELECT s_description, s_wids, s_eids, s_dids, s_tids, s_lids, s_vids, s_description2, s_description3, s_filename FROM t_formality WHERE i_formalityid = ? ");
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(fle_query);
            pstmtdocinner = conn.prepareStatement(docsbinnnerquery);
            pstmtcertinner = conn.prepareStatement(certsbinnnerquery);
            pstmtinner = conn.prepareStatement(sbinnnerquery);
            pstmtlang = conn.prepareStatement(langquery);
            pstmtvac = conn.prepareStatement(vacquery);
            pstmtbank = conn.prepareStatement(bankquery);
            pstmt.setInt(1, formalityId);
            //print(this, "fle_query data ::" + pstmt.toString());
            String templatename = "" , wids = "", eids = "", dids = "", tids = "", lids ="", vids = "",
                    headerBody = "", footerBody= "", watermarkurl = "";
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                templatename = rs.getString(1) != null ? rs.getString(1): "";
                wids = rs.getString(2) != null ? rs.getString(2) : "";
                eids = rs.getString(3) != null ? rs.getString(3) : "";
                dids = rs.getString(4) != null ? rs.getString(4) : "";
                tids = rs.getString(5) != null ? rs.getString(5) : "";
                lids = rs.getString(6) != null ? rs.getString(6) : "";
                vids = rs.getString(7) != null ? rs.getString(7) : "";
                headerBody = rs.getString(8) != null ? rs.getString(8) : "";
                footerBody = rs.getString(9) != null ? rs.getString(9) : "";
                watermarkurl = rs.getString(10) != null ? rs.getString(10) : "";
            }
            rs.close();
            
            pstmt = conn.prepareStatement(query);
            if(shortlistId > 0){
                pstmt.setInt(1, shortlistId);
            }
            else{
                pstmt.setInt(1, candidateId);
            }
            print(this, "Personal data ::" + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, dob, birthplace, email, contact1_code, contact1, contact2_code, contact2,
            contact3_code, contact3, gender, countryName, nationality, address1line1, address1line2,
            address2line1,address2line2, address2line3, nextofkin, relation, econtact1_code, econtact1,
            econtact2_code, econtact2, maritialstatus, photo, positionName, address1line3, city, experiencedept,
            currency, firstname, gradeName, lastname, middlename, position2, grade2; 
            double rate1, rate2, p2rate1, p2rate2, age;
            int empno, expectedsalary;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                dob = rs.getString(2) != null ? rs.getString(2) : "";
                birthplace = rs.getString(3) != null ? rs.getString(3) : "";
                email = rs.getString(4) != null ? rs.getString(4) : "";
                contact1_code = rs.getString(5) != null ? rs.getString(5) : "";
                contact1 = decipher(rs.getString(6) != null ? rs.getString(6) : "");                
                contact2_code = rs.getString(7) != null ? rs.getString(7) : "";
                contact2 = decipher(rs.getString(8) != null ? rs.getString(8) : "");                
                contact3_code = rs.getString(9) != null ? rs.getString(9) : "";
                contact3 = decipher(rs.getString(10) != null ? rs.getString(10) : "");                
                gender = rs.getString(11) != null ? rs.getString(11) : "";
                countryName = rs.getString(12) != null ? rs.getString(12) : "";
                nationality = rs.getString(13) != null ? rs.getString(13) : "";
                address1line1 = rs.getString(14) != null ? rs.getString(14) : "";
                address1line2 = rs.getString(15) != null ? rs.getString(15) : "";
                address2line1 = rs.getString(16) != null ? rs.getString(16) : "";
                address2line2 = rs.getString(17) != null ? rs.getString(17) : "";
                address2line3 = rs.getString(18) != null ? rs.getString(18) : "";
                nextofkin = decipher(rs.getString(19) != null ? rs.getString(19) : "");
                relation = rs.getString(20) != null ? rs.getString(20) : "";
                econtact1_code = rs.getString(21) != null ? rs.getString(21) : "";
                econtact1 = rs.getString(22) != null && !rs.getString(22).equals("") ? decipher(rs.getString(22)) : "";                
                econtact2_code = rs.getString(23) != null ? rs.getString(23) : "";
                econtact2 = rs.getString(24) != null ? decipher(rs.getString(24)) : "";                
                maritialstatus = rs.getString(25) != null ? rs.getString(25) : "";
                photo = rs.getString(26) != null ? rs.getString(26) : "";
                positionName = rs.getString(27) != null ? rs.getString(27) : "";
                address1line3 = rs.getString(28) != null ? rs.getString(28) : "";
                city = rs.getString(29) != null ? rs.getString(29) : "";
                experiencedept = rs.getString(30) != null ? rs.getString(30) : "";
                expectedsalary = rs.getInt(31);
                currency = rs.getString(32) != null ? rs.getString(32) : "";
                firstname = rs.getString(33) != null ? rs.getString(33) : "";
                gradeName = rs.getString(34) != null ? rs.getString(34) : "";
                lastname = rs.getString(35) != null ? rs.getString(35) : "";
                middlename = rs.getString(36) != null ? rs.getString(36) : "";
                age = rs.getDouble(37); 
                empno = rs.getInt(38);
                position2 = rs.getString(39) != null ? rs.getString(39) : "";
                grade2 = rs.getString(40) != null ? rs.getString(40) : "";
                rate1 = rs.getDouble(41);
                rate2 = rs.getDouble(42);
                p2rate1 = rs.getDouble(43);
                p2rate2 = rs.getDouble(44);
                if (!position2.equals("")) {
                    if (!grade2.equals("")) {
                        position2 += " - " + grade2;
                    }
                }                
                String add = "", cadd = "";
                if(!address1line1.equals(""))
                {
                    if(add.equals(""))
                        add = address1line1;
                    else
                        add += "," + address1line1;
                }
                if(!address1line2.equals(""))
                {
                    if(add.equals(""))
                        add = address1line2;
                    else
                        add += ", " + address1line2;
                }
                if(!address1line3.equals(""))
                {
                    if(add.equals(""))
                        add = address1line3;
                    else
                        add += ", " + address1line3;
                }                
                if(!address2line1.equals(""))
                {
                    if(cadd.equals(""))
                        cadd = address2line1;
                    else
                        cadd += "," + address2line1;
                }
                if(!address2line2.equals(""))
                {
                    if(cadd.equals(""))
                        cadd = address2line2;
                    else
                        cadd += ", " + address2line2;
                }
                if(!address2line3.equals(""))
                {
                    if(cadd.equals(""))
                        add = address2line3;
                    else
                        cadd += ", " + address2line3;
                }                
                if (!positionName.equals("")) 
                {
                    if (!gradeName.equals(""))
                    {
                        positionName += " | " + gradeName;
                    }
                }
                if (contact1_code != null && !contact1_code.equals("")) {
                    contact1 = "+" + contact1_code + " " + contact1;
                }
                if (contact2_code != null && !contact2_code.equals("")) {
                    contact2 = "+" + contact2_code + " " + contact2;
                }
                if (contact3_code != null && !contact3_code.equals("")) {
                    contact3 = "+" + contact3_code + " " + contact3;
                }
                if (econtact1_code != null && !econtact1_code.equals("")) {
                    econtact1 = "+" + econtact1_code + " " + econtact1;
                }
                if (econtact2_code != null && !econtact2_code.equals("")) {
                    econtact2 = "+" + econtact2_code + " " + econtact2;
                }

                pstmthealth = conn.prepareStatement(healthquery);
                pstmthealth.setInt(1, candidateId);
                //print(this, "Health data ::" + pstmthealth.toString());
                hrs = pstmthealth.executeQuery();
                String ssmf = "", ogukmedicalftw = "", ogukexp= "", medifitcert = "", medifitcertexp= "", bloodgroup = "";
                while (hrs.next()) 
                {
                    ssmf = decipher(hrs.getString(1) != null ? hrs.getString(1): "") ;
                    ogukmedicalftw = decipher(hrs.getString(2) != null ? hrs.getString(2): "") ;
                    ogukexp = hrs.getString(3) != null ? hrs.getString(3): "";
                    medifitcert = decipher(hrs.getString(4) != null ? hrs.getString(4): "") ;
                    medifitcertexp = hrs.getString(5) != null ? hrs.getString(5): "";
                    bloodgroup = decipher(hrs.getString(6) != null ? hrs.getString(6): "") ;
                }              
                
                pstmtclient = conn.prepareStatement(clientquery);
                pstmtclient.setInt(1, clientId);
                //print(this, "clientquery :: " + pstmtclient.toString());
                ResultSet clientrs = pstmtclient.executeQuery();
                clientrs = pstmtclient.executeQuery();
                while (clientrs.next()) 
                {
                    clientname = clientrs.getString(1) != null ? clientrs.getString(1) : "";
                }

                pstmtasset = conn.prepareStatement(assetquery);
                pstmtasset.setInt(1, assetId);
                //print(this, "assetquery :: " + pstmtasset.toString());
                ResultSet assetrs = pstmtasset.executeQuery();
                assetrs = pstmtasset.executeQuery();
                while (assetrs.next()) 
                {
                    clientasset = assetrs.getString(1) != null ? assetrs.getString(1) : "";
                }
                
                pstmtdoc = conn.prepareStatement(docquery);
                pstmtdoc.setInt(1, candidateId);
                //print(this, "Doctype :: " + pstmtdoc.toString());
                String passportno = "", panno = "", cdcno = "", issuedate= "", passportexpiry = "", place= "", adhaarno ="";
                int doctypeId = 0;
                ResultSet docrs = pstmtdoc.executeQuery();
                while(docrs.next())
                {
                    doctypeId = docrs.getInt(1);
                    if(doctypeId == 2)
                    {
                        passportno = decipher(docrs.getString(2) != null ? docrs.getString(2): "");
                        issuedate = docrs.getString(3) != null ? docrs.getString(3): "";
                        passportexpiry = docrs.getString(4) != null ? docrs.getString(4): "";
                        place = docrs.getString(5) != null ? docrs.getString(5): "";
                    }
                    else if(doctypeId == 5)
                    {
                        panno = decipher(docrs.getString(2) != null ? docrs.getString(2): "");
                    }
                    else if(doctypeId == 9)
                    {
                        cdcno = decipher(docrs.getString(2) != null ? docrs.getString(2): "");                        
                    }
                    else if(doctypeId == 1)
                    {
                        adhaarno = decipher(docrs.getString(2) != null ? docrs.getString(2): "");                        
                    }
                }
                docrs.close();
                
                pstmtcertinner.setInt(1, candidateId);
                //print(this, "certificates :: " + pstmtcertinner.toString());
                ResultSet certrs = pstmtcertinner.executeQuery();
                StringBuilder cert = new StringBuilder();
                String coursename, coursetype, educinst, locationofinstit, approvedby, dateofissue,
                expirydate, countryname, certificateno;
                while (certrs.next()) 
                {
                    coursename = certrs.getString(1) != null ? certrs.getString(1) : "";
                    coursetype = certrs.getString(2) != null ? certrs.getString(2) : "";
                    educinst = certrs.getString(3) != null ? certrs.getString(3) : "";
                    locationofinstit = certrs.getString(4) != null ? certrs.getString(4) : "";
                    approvedby = certrs.getString(5) != null ? certrs.getString(5) : "";
                    dateofissue = certrs.getString(6) != null ? certrs.getString(6) : "";
                    expirydate = certrs.getString(7) != null ? certrs.getString(7) : "";
                    countryname = certrs.getString(8) != null ? certrs.getString(8) : "";
                    certificateno = decipher(certrs.getString(9) != null ? certrs.getString(9) : "");
                    if (!countryname.equals("")) {
                        locationofinstit += " (" + countryname + ")";
                    }
                    cert.append("<tr>");
                     if (tids.contains("2")) {
                        cert.append("<td>" + coursetype + "</td>");
                    }
                    if (tids.contains("1")) {
                        cert.append("<td>" + coursename + "</td>");
                    }
                    if (tids.contains("3")) {
                        cert.append("<td>" + educinst + "," + locationofinstit + "</td>");
                    }
                     if (tids.contains("4")) {
                        cert.append("<td>" + approvedby + "</td>");
                    }
                    if (tids.contains("5")) {
                        cert.append("<td>" + dateofissue + "</td>");
                    }
                    if (tids.contains("6")) {
                        cert.append("<td>" + expirydate + "</td>");
                    }
                    if (tids.contains("7")) {
                        cert.append("<td>" + certificateno + "</td>");
                    }                   
                    cert.append("</tr>");
                }
                certrs.close();
                
                String certstr = cert.toString();
                cert.setLength(0);
                String certvalue = "";
                if (!certstr.equals("")) 
                {
                    certvalue += "<tr><td style='height:10px;'></td></tr>";
                    certvalue += "<tr><td style='text-decoration:underline;text-align:left;font-weight:bold;'></td></tr>";
                    certvalue += "<tr>";
                    certvalue += "<td>";
                    certvalue += "<table width='100%' border='1' cellspacing='5' cellpadding='5' align='center' style='border-collapse: collapse;'>";
                    certvalue += ("<tr>");
                    if (tids.contains("2")) {
                        certvalue += ("<td>Type</td>");
                    }
                    if (tids.contains("1")) {
                        certvalue += ("<td>Course Name</td>");
                    }
                    if (tids.contains("3")) {
                        certvalue += ("<td>Institution, Location</td>");
                    }
                    if (tids.contains("4")) {
                        certvalue += ("<td>Approved By</td>");
                    }                    
                    if (tids.contains("5")) {
                        certvalue += ("<td>Issue Date</td>");
                    }
                    if (tids.contains("6")) {
                        certvalue += ("<td>Expiry Date</td>");
                    }
                    if (tids.contains("7")) {
                        certvalue += ("<td>Certificate No</td>");
                    }                    
                    certvalue += ("</tr>");
                    certvalue += certstr;
                    certvalue += "</table>";
                    certvalue += "</td>";
                    certvalue += "</tr>";
                }
                
                
                pstmtdocinner.setInt(1, candidateId);
                //print(this, "GovDoc ::: " + pstmtdocinner.toString());
                ResultSet rsdoc = pstmtdocinner.executeQuery();
                rsdoc = pstmtdocinner.executeQuery();
                String documentname, documentno, placeofissue, issuedby,  dateofexpiry;
                int doctype = 0;
                while (rsdoc.next()) 
                {
                    documentname = rsdoc.getString(1) != null ? rsdoc.getString(1) : "";
                    documentno = decipher(rsdoc.getString(2) != null ? rsdoc.getString(2) : "");
                    placeofissue = rsdoc.getString(3) != null ? rsdoc.getString(3) : "";
                    issuedby = rsdoc.getString(4) != null ? rsdoc.getString(4) : "";
                    dateofissue = rsdoc.getString(5) != null ? rsdoc.getString(5) : "";
                    dateofexpiry = rsdoc.getString(6) != null ? rsdoc.getString(6) : "";
                    countryname = rsdoc.getString(7) != null ? rsdoc.getString(7) : "";
                    doctype = rsdoc.getInt(8);
                    if (!countryname.equals("")) {
                        placeofissue += "(" + countryname + ")";
                    }

                    sbdoc.append("<tr>");
                    if (dids.contains("1")) {
                        sbdoc.append("<td>" + documentname + "</td>");
                    }
                    if (dids.contains("2")) {
                        sbdoc.append("<td>" + documentno + "</td>");
                    }
                    if (dids.contains("3")) {
                        sbdoc.append("<td>" + placeofissue + "</td>");
                    }
                    if (dids.contains("4")) {
                        sbdoc.append("<td>" + issuedby + "</td>");
                    }
                    if (dids.contains("5")) {
                        sbdoc.append("<td>" + dateofissue + "</td>");
                    }
                    if (dids.contains("6")) {
                        sbdoc.append("<td>" + dateofexpiry + "</td>");
                    }
                    sbdoc.append("</tr>");
                }     
                
                String docstr = sbdoc.toString();
                sbdoc.setLength(0);
                String docvalue = "";
                if (!docstr.equals("")) 
                {
                    docvalue += "<tr><td style='height:10px;'></td></tr>";
                    docvalue += "<tr><td style='text-decoration:underline;text-align:left;font-weight:bold;'></td></tr>";
                    docvalue += "<tr>";
                    docvalue += "<td>";
                    docvalue += "<table width='100%' border='1' cellspacing='5' cellpadding='5' align='center' style='border-collapse: collapse;'>";
                    docvalue += ("<tr>");
                    if (dids.contains("1")) {
                        docvalue += ("<td>Document Name</td>");
                    }
                    if (dids.contains("2")) {
                        docvalue += ("<td>Number</td>");
                    }
                    if (dids.contains("3")) {
                        docvalue += ("<td>Place of Issue</td>");
                    }
                    if (dids.contains("4")) {
                        docvalue += ("<td>Issued By</td>");
                    }
                    if (dids.contains("5")) {
                        docvalue += ("<td>Issue Date</td>");
                    }
                    if (dids.contains("6")) {
                        docvalue += ("<td>Expiry Date</td>");
                    }
                    docvalue += ("</tr>");
                    docvalue += docstr;
                    docvalue += "</table>";
                    docvalue += "</td>";
                    docvalue += "</tr>";
                }
                
                String highestq = "";
                pstmteduinner = conn.prepareStatement(edusbinnnerquery);
                pstmteduinner.setInt(1, candidateId);
                ResultSet edurs = pstmteduinner.executeQuery();
                StringBuilder ed = new StringBuilder();
                edurs = pstmteduinner.executeQuery();
                String kindname, degree, filedofstudy, startdate, enddate;
                while (edurs.next()) 
                {
                    kindname = edurs.getString(1) != null ? edurs.getString(1) : "";
                    degree = edurs.getString(2) != null ? edurs.getString(2) : "";
                    educinst = decipher(edurs.getString(3) != null ? edurs.getString(3) : "");
                    locationofinstit = edurs.getString(4) != null ? edurs.getString(4) : "";
                    filedofstudy = edurs.getString(5) != null ? edurs.getString(5) : "";
                    startdate = edurs.getString(6) != null ? edurs.getString(6) : "";
                    enddate = edurs.getString(7) != null ? edurs.getString(7) : "";
                    countryname = edurs.getString(8) != null ? edurs.getString(8) : "";
                    int hflag = edurs.getInt(9);
                    if (hflag == 1) {
                        highestq = kindname + " - " + degree;
                    }
                    if (!countryname.equals("")) {
                        locationofinstit += "(" + countryname + ")";
                    }
                    ed.append("<tr>");
                    if (eids.contains("1")) {
                        ed.append("<td>" + degree + "</td>");
                    }
                    if (eids.contains("2")) {
                        ed.append("<td>" + enddate + "</td>");
                    }
                    if (eids.contains("3")) {
                        ed.append("<td>" + educinst + "," + locationofinstit + "</td>");
                    }    
                    ed.append("</tr>");
                }
                edurs.close();
                
                String edstr = ed.toString();
                ed.setLength(0);
                String eduvalue = "";
                if (!edstr.equals("")) 
                {
                    eduvalue += "<tr><td style='height:10px;'></td></tr>";
                    eduvalue += "<tr><td style='text-decoration:underline;text-align:left;font-weight:bold;'></td></tr>";
                    eduvalue += "<tr>";
                    eduvalue += "<td>";
                    eduvalue += "<table width='100%' border='1' cellspacing='5' cellpadding='5' align='center' style='border-collapse: collapse;'>";
                    eduvalue += ("<tr>");                    
                    if (eids.contains("1")) {
                        eduvalue += ("<td>Degree</td>");
                    }
                    if (eids.contains("2")) {
                        eduvalue += ("<td>End date</td>");
                    }
                    if (eids.contains("3")) {
                        eduvalue += ("<td>Institution, Location</td>");
                    }                    
                    eduvalue += ("</tr>");
                    eduvalue += edstr;
                    eduvalue += "</table>";
                    eduvalue += "</td>";
                    eduvalue += "</tr>";
                }
                
                pstmtvac.setInt(1, candidateId);
                //print(this, "Vaccine Details :: " + pstmtvac.toString());
                ResultSet vacrs = pstmtvac.executeQuery();
                StringBuilder vacsb = new StringBuilder();
                while (vacrs.next()) 
                {
                    name = vacrs.getString(1) != null ? vacrs.getString(1) : "";
                    city = vacrs.getString(2) != null ? vacrs.getString(2) : "";
                    issuedate = vacrs.getString(3) != null ? vacrs.getString(3) : "";
                    String expiry = vacrs.getString(4) != null ? vacrs.getString(4) : "";
                    String country = vacrs.getString(5) != null ? vacrs.getString(5) : "";            
                    if (!city.equals("")) 
                    {
                        if (!country.equals("")) 
                        {
                            city += " (" + country + ")";
                        }
                    }
                    vacsb.append("<tr>");
                    if (vids.contains("1")) {
                        vacsb.append("<td>" + name + "</td>");
                    }                    
                    if (vids.contains("2")) {
                        vacsb.append("<td>" + issuedate +"</td>");
                    }    
                    if (vids.contains("3")) {
                        vacsb.append("<td>" + expiry +"</td>");
                    }    
                   if (vids.contains("4")) {
                        vacsb.append("<td>" + city + "</td>");
                    }   
                    vacsb.append("</tr>");
                }
                vacrs.close();
                
                String vacstr = vacsb.toString();
                vacsb.setLength(0);
                String vacvalue = "";
                if (!vacstr.equals("")) 
                {
                    vacvalue += "<tr><td style='height:10px;'></td></tr>";
                    vacvalue += "<tr><td style='text-decoration:underline;text-align:left;font-weight:bold;'></td></tr>";
                    vacvalue += "<tr>";
                    vacvalue += "<td>";
                    vacvalue += "<table width='100%' border='1' cellspacing='5' cellpadding='5' align='center' style='border-collapse: collapse;'>";
                    vacvalue += ("<tr>");                    
                    if (vids.contains("1")) {
                        vacvalue += ("<td>Name</td>");
                    }
                    if (vids.contains("2")) {
                        vacvalue += ("<td>Issue date</td>");
                    }
                    if (vids.contains("3")) {
                        vacvalue += ("<td>Expiry Date</td>");
                    }
                    if (vids.contains("4")) {
                        vacvalue += ("<td>Place of Issue</td>");
                    }                    
                    vacvalue += ("</tr>");
                    vacvalue += vacstr;
                    vacvalue += "</table>";
                    vacvalue += "</td>";
                    vacvalue += "</tr>";
                }
                
                pstmtinner.setInt(1, candidateId);
                //print(this, "Work Exp :: "+ pstmtinner.toString());
                ResultSet rsinner = pstmtinner.executeQuery();
                StringBuilder we = new StringBuilder();
                String position, department, companyname, assetname;
                int currentworkingstatus;
                double experience;
                while (rsinner.next()) 
                {
                    position = rsinner.getString(1) != null ? rsinner.getString(1) : "";
                    department = rsinner.getString(2) != null ? rsinner.getString(2) : "";
                    companyname = rsinner.getString(3) != null ? rsinner.getString(3) : "";
                    assetname = rsinner.getString(4) != null ? rsinner.getString(4) : "";
                    startdate = rsinner.getString(5) != null ? rsinner.getString(5) : "";
                    enddate = rsinner.getString(6) != null ? rsinner.getString(6) : "";
                    currentworkingstatus = rsinner.getInt(7);
                    experience = rsinner.getDouble(8);
                    if (currentworkingstatus == 1) {
                        enddate = "Present";
                    }
                    we.append("<tr>");
                    if (wids.contains("1")) {
                        we.append("<td>" + position + "</td>");
                    }
                    if (wids.contains("2")) {
                        we.append("<td>" + department + "</td>");
                    }
                    if (wids.contains("3")) {
                        we.append("<td>" + companyname + "</td>");
                    }
                    if (wids.contains("4")) {
                        we.append("<td>" + assetname + "</td>");
                    }
                    if (wids.contains("5")) {
                        we.append("<td>" + startdate + "</td>");
                    }
                    if (wids.contains("6")) {
                        we.append("<td>" + enddate + "</td>");
                    }
                    if (wids.contains("7")) {
                        we.append("<td>" + experience + " Years" + "</td>");
                    }
                    we.append("</tr>");
                }
                rsinner.close();
                
                String westr = we.toString();
                we.setLength(0);
                String wevalue = "";
                if (!westr.equals("")) 
                {
                    wevalue += "<tr><td style='height:10px;'></td></tr>";
                    wevalue += "<tr><td style='text-decoration:underline;text-align:left;font-weight:bold;'></td></tr>";
                    wevalue += "<tr>";
                    wevalue += "<td>";
                    wevalue += "<table width='100%' border='1' cellspacing='5' cellpadding='5' align='center' style='border-collapse: collapse;'>";
                    wevalue += ("<tr>");
                    if (wids.contains("1")) {
                        wevalue += ("<td>Position</td>");
                    }
                    if (wids.contains("2")) {
                        wevalue += ("<td>Department</td>");
                    }
                    if (wids.contains("3")) {
                        wevalue += ("<td>Company Name</td>");
                    }
                    if (wids.contains("4")) {
                        wevalue += ("<td>Asset Name</td>");
                    }
                    if (wids.contains("5")) {
                        wevalue += ("<td>Start Date</td>");
                    }
                    if (wids.contains("6")) {
                        wevalue += ("<td>End Date</td>");
                    }
                    if (wids.contains("7")) {
                        wevalue += ("<td>Experience</td>");
                    }
                    wevalue += ("</tr>");
                    wevalue += westr;
                    wevalue += "</table>";
                    wevalue += "</td>";
                    wevalue += "</tr>";
                }
                
                pstmtlang.setInt(1, candidateId);
                //print(this, "Language :: "+ pstmtlang.toString());
                ResultSet rslang = pstmtlang.executeQuery();
                StringBuilder lang = new StringBuilder();
                while (rslang.next()) 
                {
                    String language = rslang.getString(1) != null ? rslang.getString(1) : "";
                    String proficiency = rslang.getString(2) != null ? rslang.getString(2) : "";
                    lang.append("<tr>");
                    if (lids.contains("1")) {
                        lang.append("<td>" + language + "</td>");
                    }
                    if (lids.contains("2")) {
                        lang.append("<td>" + proficiency + "</td>");
                    }
                    lang.append("</tr>");
                }
                rslang.close();
                
                String langstr = lang.toString();
                lang.setLength(0);
                String langvalue = "";
                if (!langstr.equals("")) 
                {
                    langvalue += "<tr><td style='height:10px;'></td></tr>";
                    langvalue += "<tr><td style='text-decoration:underline;text-align:left;font-weight:bold;'></td></tr>";
                    langvalue += "<tr>";
                    langvalue += "<td>";
                    langvalue += "<table width='100%' border='1' cellspacing='5' cellpadding='5' align='center' style='border-collapse: collapse;'>";
                    langvalue += ("<tr>");
                    if (lids.contains("1")) {
                        langvalue += ("<td>Name</td>");
                    }
                    if (lids.contains("2")) {
                        langvalue += ("<td>Proficiency</td>");
                    }
                    langvalue += ("</tr>");
                    langvalue += langstr;
                    langvalue += "</table>";
                    langvalue += "</td>";
                    langvalue += "</tr>";
                }
                
                pstmtbank.setInt(1, candidateId);
                //print(this, "Bank Details :: "+ pstmtbank.toString());
                ResultSet bankrs = pstmtbank.executeQuery();
                String accountno = "", ifsccode = "";
                while (bankrs.next()) 
                {
                    accountno = decipher(bankrs.getString(1) != null ? bankrs.getString(1) : "");
                    ifsccode = decipher(bankrs.getString(2) != null ? bankrs.getString(2) : "");                    
                }
                bankrs.close();
                
                HashMap hashmap = new HashMap();
                Template template  = new Template(getMainPath("add_formality_file") + templatename);
                
                hashmap.put("NAME", name);
                hashmap.put("DOB", dob);
                hashmap.put("EMAIL", email);
                hashmap.put("PLACEOFBIRTH", birthplace);
                hashmap.put("CONTACT1", contact1);
                hashmap.put("CONTACT2", contact2);
                hashmap.put("CONATCT3", contact3);                
                hashmap.put("GENDER", gender);
                hashmap.put("CITY", city);
                hashmap.put("COUNTRYNAME", countryName);
                hashmap.put("NATIONALITY", nationality);
                hashmap.put("PERMANENTADDRESS", add);
                hashmap.put("COMMUNICATIONADDRESS", cadd);
                hashmap.put("NEXTOFKIN", nextofkin);
                hashmap.put("RELATION", relation);
                hashmap.put("ECONTACT1", econtact1);
                hashmap.put("ECONTACT2", econtact2);
                hashmap.put("MARITALSTATUS", maritialstatus);
                if (!photo.equals("")) {
                    hashmap.put("PHOTO", "<img src='" + view_path + photo + "' style = 'weight :150px; height : 150px'/>");
                }
                hashmap.put("EXPERIENCEDEPT", experiencedept);
                hashmap.put("EXPECTEDSALARY", expectedsalary);
                hashmap.put("CURRENCY", currency);
                hashmap.put("PRIMARYPOSITIONNAME", positionName);
                hashmap.put("PRIMARYPOSITIONRATE", rate1);
                hashmap.put("PRIMARYPOSITIONOVERTIMERATE", rate2);
                hashmap.put("SECONDARYPOSITIONNAME", position2);
                hashmap.put("SECONDARYPOSITIONRATE", p2rate1);
                hashmap.put("SECONDARYPOSITIONOVERTIMERATE", p2rate2);
                hashmap.put("FIRSTNAME", firstname);
                hashmap.put("MIDDLENAME", middlename);
                hashmap.put("LASTNAME", lastname);
                hashmap.put("COMPANY", clientname);
                hashmap.put("ASSETNAME", clientasset);
                hashmap.put("EMPLOYEENUMBER", changeNum(empno, 6));
                hashmap.put("GOVDOC", docvalue);
                hashmap.put("WORKEXPERIENCE", wevalue);
                hashmap.put("EDUCATION", eduvalue);
                hashmap.put("CERTIFICATIONS", certvalue);
                hashmap.put("HIGHESTQUALIFICATION", highestq);       
                hashmap.put("PASSPORTNUMBER", passportno);
                hashmap.put("PASSPORTISSUEPLACE", place);
                hashmap.put("PASSPORTISSUEDATE", issuedate);
                hashmap.put("PASSPORTEXPIRYDATE", passportexpiry);
                hashmap.put("PANCARDNUMBER", panno);                        
                hashmap.put("AADHARNUMBER", adhaarno);                   
                hashmap.put("CDCNUMBER", cdcno);                
                hashmap.put("LANGUAGEDETAILS", langvalue);
                hashmap.put("VACCINEDEATILS", vacvalue);
                hashmap.put("SSMF", ssmf);
                hashmap.put("OGUKMEDICAL", ogukmedicalftw);
                hashmap.put("OGUKMEDICALEXPIRY", ogukexp);
                hashmap.put("MEDICALCERTIFICATE", medifitcert);
                hashmap.put("MEDICALCERTIFICATEEXPIRY", medifitcertexp);
                hashmap.put("BLOODGROUP", bloodgroup);
                hashmap.put("CURRENTDATE", currDate01());
                hashmap.put("ACCOUNTNUMBER", accountno);
                hashmap.put("IFSCCODE", ifsccode);
                hashmap.put("CUSTOMVALUE1", (cval1s != null && !cval1s.equals("") ? cval1s  : "<b style='color:red'>Custom Value 1</b>"));
                hashmap.put("CUSTOMVALUE2", (cval2s != null && !cval2s.equals("") ? cval2s  : "<b style='color:red'>Custom Value 2</b>"));
                hashmap.put("CUSTOMVALUE3", (cval3s != null && !cval3s.equals("") ? cval3s  : "<b style='color:red'>Custom Value 3</b>"));
                hashmap.put("CUSTOMVALUE4", (cval4s != null && !cval4s.equals("") ? cval4s  : "<b style='color:red'>Custom Value 4</b>"));
                hashmap.put("CUSTOMVALUE5", (cval5s != null && !cval5s.equals("") ? cval5s  : "<b style='color:red'>Custom Value 5</b>"));
                hashmap.put("CUSTOMVALUE6", (cval6s != null && !cval6s.equals("") ? cval6s  : "<b style='color:red'>Custom Value 6</b>"));
                hashmap.put("CUSTOMVALUE7", (cval7s != null && !cval7s.equals("") ? cval7s  : "<b style='color:red'>Custom Value 7</b>"));
                hashmap.put("CUSTOMVALUE8", (cval8s != null && !cval8s.equals("") ? cval8s  : "<b style='color:red'>Custom Value 8</b>"));
                hashmap.put("CUSTOMVALUE9", (cval9s != null && !cval9s.equals("") ? cval9s  : "<b style='color:red'>Custom Value 9</b>"));
                hashmap.put("CUSTOMVALUE10", (cval10s != null && !cval10s.equals("") ? cval10s : "<b style='color:red'>Custom Value 9</b>"));                
                if(age > 0)
                {
                    hashmap.put("AGE", age + " Years");
                }
                
                String content = template.patch(hashmap);
                StringBuilder sb2 = new StringBuilder();
                sb2.append("<html>");     
                    sb2.append("<head>");
                        sb2.append("<style type='text/css'>");
                            sb2.append("table {page-break-inside: avoid}");
                            sb2.append("tr {page-break-inside: avoid;}");
                            sb2.append("td {font-size:14px;line-height:28px}");
                            sb2.append("img {page-break-inside: avoid}");
                        sb2.append("</style>");
                    sb2.append("</head>");
                    sb2.append("<body>"+content+"</body>");
                sb2.append("</html>");
                String st1 = sb2.toString();
                sb2.setLength(0);
                
                st1 = content.replaceAll("<strong", "<b style='text-weight: bold' ").replaceAll("</strong>", "</b>");
                hashmap.clear();
                
                String filePath = "";
                String viewPath = getMainPath("view_onboarding");
                if(shortlistId > 0){                    
                    filePath = getMainPath("add_onboarding");
                }else{
                    filePath = getMainPath("add_onboardingtp");
                }
                if(watermarkurl != null && !watermarkurl.equals(""))
                {
                    watermarkurl = viewPath+watermarkurl;
                }                
                java.util.Date now = new java.util.Date();
                String fname = String.valueOf(now.getTime()) + ".pdf";
                String htmlFolderName = dateFolder();
                File dir = new File(filePath + htmlFolderName);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                pdffilename = htmlFolderName + "/" + fname;
                File pdfFile = new File(filePath + htmlFolderName + "/" + fname);
                int pdfwidth = 1050;
                try {
                    generatePDFString(st1, pdfFile, PD4Constants.A4, "", headerBody, footerBody, watermarkurl, pdfwidth);
                    pdfFile = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return pdffilename;
    }

    public String getAirportList(int airportId, int countryId) 
    {
        String s = "";
        StringBuilder sb = new StringBuilder();
        sb.append("<option value='-1'>- Select -</option>");
        if (countryId > 0) 
        {
            String query = ("SELECT i_airportid, s_name FROM t_airport WHERE i_status = 1 AND i_countryid = ? ORDER BY s_name").intern();
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, countryId);
                print(this, "getAirportList :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    sb.append("<option value='" + rs.getInt(1) + "' " + (rs.getInt(1) == airportId ? "selected" : "") + " >" + (rs.getString(2)) + "</option>");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        s = sb.toString();
        sb.setLength(0);
        return s;
    }

    public int createDocumentList(String doclistname, int shortlistId) 
    {
        shortlistId = 1;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_shortlistmob SET ");
            sb.append("s_reqdoconboarding =? ");
            sb.append("ts_moddate =? ");
            sb.append("WHERE i_shortlistid =? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, doclistname);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, shortlistId);
            print(this, "createDocumentList :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                shortlistId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createDocumentList :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return shortlistId;
    }

    public ArrayList getDocumentList() 
    {
        ArrayList docList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_doctypeid, s_doc  FROM t_doctype WHERE i_status = 1 AND FIND_IN_SET(1, s_docmoduleids) AND FIND_IN_SET(4, s_docsubmoduleids) ORDER BY s_doc ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            logger.info(" getDocumentList:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String docname;
            int docId;
            while (rs.next()) 
            {
                docId = rs.getInt(1);
                docname = rs.getString(2) != null ? rs.getString(2) : "";
                docList.add(new OnboardingInfo(docId, docname));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return docList;
    }

    public OnboardingInfo getReqDocListId(int shortlistId)
    {
        OnboardingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_notreqtravel, i_notreqaccom, s_reqdoconboarding, s_reqdocchecklist, s_checklistby, DATE_FORMAT(ts_checkliston, '%d-%b-%Y'), DATE_FORMAT(ts_checkliston, '%H:%i'), ");
        sb.append("s_mailextfile1, s_onboardingkits, DATE_FORMAT(ts_moddate, '%d-%b-%Y'), DATE_FORMAT(ts_moddate, '%H:%i') FROM t_shortlistmob ");
        sb.append("WHERE i_shortlistid =? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, shortlistId);
            print(this, " getReqDocListId:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int notreqtravel = 0, notreqaccom = 0;
            String reqdocId = "", reqchecklistId = "", depairportname = "", arrairportname = "", checklistBy = "", checklistdate = "", checklisttime = "", mailextfile = "",
                    onboardingkits = "", ondate = "", ontime = "";
            while (rs.next())
            {
                notreqtravel = rs.getInt(1);
                notreqaccom = rs.getInt(2);
                reqdocId = rs.getString(3) != null ? rs.getString(3) : "";
                reqchecklistId = rs.getString(4) != null ? rs.getString(4) : "";
                checklistBy = rs.getString(5) != null ? rs.getString(5) : "";
                checklistdate = rs.getString(6) != null ? rs.getString(6) : "";
                checklisttime = rs.getString(7) != null ? rs.getString(7) : "";
                mailextfile = rs.getString(8) != null ? rs.getString(8) : "";
                onboardingkits = rs.getString(9) != null ? rs.getString(9) : "";
                ondate = rs.getString(10) != null ? rs.getString(10) : "";
                ontime = rs.getString(11) != null ? rs.getString(11) : "";
            }
            info = new OnboardingInfo(notreqtravel, notreqaccom, reqdocId, reqchecklistId, checklistBy, checklistdate, checklisttime, shortlistId, mailextfile, onboardingkits, ondate, ontime);
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getDocumentCheckList(String doctypeIds)
    {
        ArrayList doccheckList = new ArrayList();
        if (!doctypeIds.equals("")) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_doctypeid, s_doc  FROM t_doctype WHERE i_doctypeid IN (" + doctypeIds + ") AND i_status = 1 ORDER BY  s_doc ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                logger.info(" getDocumentCheckList:: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String docname;
                int docId;
                while (rs.next()) 
                {
                    docId = rs.getInt(1);
                    docname = rs.getString(2) != null ? rs.getString(2) : "";
                    doccheckList.add(new OnboardingInfo(docId, docname));
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return doccheckList;
    }

    public int insertReqDoclist(String reqdocIds, int shortlistId, int userId) 
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            String selquery = "SELECT i_shortlistid FROM t_shortlistmob WHERE i_shortlistid =?";
            pstmt = conn.prepareStatement(selquery);
            pstmt.setInt(1, shortlistId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                cc = rs.getInt(1);
            }

            if (!reqdocIds.equals("")) 
            {
                if (cc > 0) 
                {
                    String query = "UPDATE t_shortlistmob SET s_reqdoconboarding =?, i_userid =?, ts_moddate =? WHERE i_shortlistid =?";
                    pstmt = conn.prepareStatement(query);
                    int scc = 0;
                    pstmt.setString(++scc, reqdocIds);
                    pstmt.setInt(++scc, userId);
                    pstmt.setString(++scc, currDate1());
                    pstmt.setInt(++scc, shortlistId);
                    print(this, "UpdateReqDoclist :: " + pstmt.toString());
                    pstmt.executeUpdate();
                } else {
                    String query = "INSERT INTO t_shortlistmob (i_shortlistid, s_reqdoconboarding, i_userid, ts_regdate, ts_moddate) VALUES(?, ?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(query);
                    int scc = 0;
                    pstmt.setInt(++scc, shortlistId);
                    pstmt.setString(++scc, reqdocIds);
                    pstmt.setInt(++scc, userId);
                    pstmt.setString(++scc, currDate1());
                    pstmt.setString(++scc, currDate1());
                    print(this, "InsertReqDoclist :: " + pstmt.toString());
                    pstmt.executeUpdate();
                }
            }
            pstmt.close();
            updateShortlistFlag(shortlistId, userId, 2, 0);
        } catch (Exception exception) {
            print(this, "insertReqDoclist :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return shortlistId;
    }

    public int insertDocChecklist(String docchecklistIds, int shortlistId, int userId, int reqcheckstatus, String username) 
    {
        try 
        {
            conn = getConnection();
            if (!docchecklistIds.equals(""))
            {
                String query = "UPDATE t_shortlistmob SET s_reqdocchecklist =?, s_checklistby =?, ts_checkliston =?, i_userid =?, ts_moddate =? WHERE i_shortlistid =?";
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                pstmt.setString(++scc, docchecklistIds);
                pstmt.setString(++scc, username);
                pstmt.setString(++scc, currDate1());
                pstmt.setInt(++scc, userId);
                pstmt.setString(++scc, currDate1());
                pstmt.setInt(++scc, shortlistId);
                print(this, "insertDocChecklist :: " + pstmt.toString());
                pstmt.executeUpdate();
            }
            pstmt.close();
            if (reqcheckstatus > 0) {
                updateShortlistFlag(shortlistId, userId, 4, 0);
            }
        } catch (Exception exception) {
            print(this, "insertReqDoclist :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return shortlistId;
    }

    public int updateShortlistFlag(int shortlistId, int uId, int onboardflag, int onflag) 
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_shortlist SET i_onboard =?, i_userid =?, ts_moddate =? WHERE i_shortlistid =? AND i_onboard <= " + onboardflag);
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, onboardflag);
            pstmt.setInt(++scc, uId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, shortlistId);
            print(this, "updateShortlistFlag :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            if (onflag > 0)
            {
                updateShortlistOnFlag(shortlistId);
            }
        } catch (Exception exception) {
            print(this, "updateShortlistFlag :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int updateShortlistOnFlag(int shortlistId) 
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_shortlist SET i_onflag =1 WHERE i_shortlistid =? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, shortlistId);
            print(this, "updateShortlistOnFlag :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateShortlistOnFlag :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public OnboardingInfo getSelectedCandidatedetailsByid(int shortlistId)
    {
        OnboardingInfo info = null;
        if (shortlistId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name, c.s_photofilename, ");
            sb.append("DATE_FORMAT( t_jobpost.ts_targetmobdate, '%d-%b-%Y'), t_onboardingdtls.s_val1, t_shortlist.i_shortlistid, t_shortlist.i_onboard, ");
            sb.append("DATE_FORMAT(t_onboardingdtls.s_val10, '%d-%b-%Y'), t_shortlist.i_onflag, t_shortlist.i_upflag, t_shortlist.s_uploadexternal, ");
            sb.append("t_client.s_name, t_clientasset.s_name, t_country.s_name, t_jobpost.i_clientid, DATE_FORMAT(CURRENT_DATE(),'%d-%b-%Y') FROM t_shortlist ");
            sb.append("LEFT JOIN t_jobpost on (t_shortlist.i_jobpostid = t_jobpost.i_jobpostid) ");
            sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_jobpost.i_clientid) ");
            sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_jobpost.i_clientassetid) ");
            sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_jobpost.i_countryid) ");
            sb.append("LEFT JOIN t_candidate as c  on (c.i_candidateid = t_shortlist.i_candidateid ) ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("LEFT JOIN t_onboardingdtls ON (t_onboardingdtls.i_shortlistid = t_shortlist.i_shortlistid) ");
            sb.append("WHERE t_shortlist.i_sflag = 4 and t_shortlist.i_oflag =4 and t_shortlist.i_shortlistid =? ");
            sb.append(" GROUP BY c.i_candidateid ORDER BY  c.s_firstname ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                pstmt.setInt(++scc, shortlistId);
                logger.info("getSelectedCandidatedetailsByid onboarding:: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String name, position, grade, photo, mobilizedate, hotelname, arrivaldate, 
                        uploadexternal, client, asset, country,date2;
                int candidateId, onboardflag, onflag, upflag, clientId;
                while (rs.next())
                {
                    candidateId = rs.getInt(1);
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    position = rs.getString(3) != null ? rs.getString(3) : "";
                    grade = rs.getString(4) != null ? rs.getString(4) : "";
                    photo = rs.getString(5) != null ? rs.getString(5) : "";
                    mobilizedate = rs.getString(6) != null ? rs.getString(6) : "";
                    hotelname = rs.getString(7) != null ? rs.getString(7) : "";
                    shortlistId = rs.getInt(8);
                    onboardflag = rs.getInt(9);
                    arrivaldate = rs.getString(10) != null ? rs.getString(10) : "";
                    onflag = rs.getInt(11);
                    upflag = rs.getInt(12);
                    uploadexternal = rs.getString(13) != null ? rs.getString(13) : "";
                    client = rs.getString(14) != null ? rs.getString(14) : "";
                    asset = rs.getString(15) != null ? rs.getString(15) : "";
                    country = rs.getString(16) != null ? rs.getString(16) : "";
                    clientId = rs.getInt(17);
                    date2 = rs.getString(18) != null ? rs.getString(18) : "";
                    if (!grade.equals("")) {
                        position += " - " + grade;
                    }
                    if (!asset.equals("")) {
                        client += " - " + asset;
                    }
                    info = new OnboardingInfo(candidateId, name, position, photo, mobilizedate, hotelname, shortlistId, onboardflag, arrivaldate, onflag, upflag,
                            uploadexternal, client, country, clientId, date2);
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return info;
    }

    public void updateexternalpdfmobfile(int shortlistId, String filename, int uId)
    {
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_shortlistmob SET ");
            sb.append("s_mailextfile1 = ?, ");
            sb.append("i_userid = ? ,");
            sb.append("ts_moddate = ?  ");
            sb.append("WHERE i_shortlistid = ? ");
            String candidatequery = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(candidatequery);
            pstmt.setString(1, filename);
            pstmt.setInt(2, uId);
            pstmt.setString(3, currDate1());
            pstmt.setInt(4, shortlistId);
            print(this, "updateexternalpdfmobfile :: " + pstmt.toString());
            pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "unlockCandidate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public OnboardingInfo gettraveldetailsByshortlist(int shortlistId)
    {
        OnboardingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_reqdoconboarding, s_mailextfile1 FROM t_shortlistmob ");
        sb.append("WHERE i_shortlistid = ? ");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, shortlistId);
            print(this, "gettraveldetailsByshortlist :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String reqdoconboarding, extfilename;
            while (rs.next()) 
            {
                reqdoconboarding = rs.getString(1) != null ? rs.getString(1) : "";
                extfilename = rs.getString(2) != null ? rs.getString(2) : "";
                info = new OnboardingInfo(shortlistId, reqdoconboarding, extfilename);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public OnboardingInfo settravelEmailDetail(int shortlistId, int onboardflag, int maillogId) 
    {
        OnboardingInfo info = null;
        StringBuilder sb = new StringBuilder();
        try 
        {
            if (onboardflag == 2) 
            {
                sb.append("SELECT t_shortlist.i_candidateid, t_client.s_name, t_client.s_email, t4.emails, t_shortlist.s_pdffilename, ");
                sb.append("t_candidate.s_firstname, t_jobpost.i_jobpostid, t_position.s_name, t_grade.s_name, DATE_FORMAT(t_shortlist.ts_regdate,'%d-%b-%Y'), ");
                sb.append("t_candidate.s_email, t_clientasset.s_name FROM t_shortlist ");
                sb.append("LEFT JOIN t_jobpost on (t_jobpost.i_jobpostid = t_shortlist.i_jobpostid) ");
                sb.append("LEFT JOIN t_position on (t_jobpost.i_positionid = t_position.i_positionid) ");
                sb.append("LEFT JOIN t_grade on (t_jobpost.i_gradeid = t_grade.i_gradeid) ");
                sb.append("LEFT JOIN t_candidate on (t_candidate.i_candidateid = t_shortlist.i_candidateid) ");
                sb.append("LEFT JOIN t_client on (t_client.i_clientid = t_jobpost.i_clientid) ");
                sb.append("LEFT JOIN t_clientasset on (t_clientasset.i_clientassetid = t_jobpost.i_clientassetid)");
                sb.append("LEFT JOIN (select t_client.i_clientid, GROUP_CONCAT(DISTINCT t3.s_email ORDER BY t3.s_email SEPARATOR ', ') as emails FROM t_client LEFT JOIN t_userlogin as t3 on find_in_set(t3.i_userid, t_client.s_ocsuserids) group by t_client.i_clientid) as t4 on (t4.i_clientid = t_client.i_clientid) ");
                sb.append("WHERE t_shortlist.i_shortlistid = ? ");
                String query = sb.toString().intern();
                sb.setLength(0);
                
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, shortlistId);
                print(this, "settravelEmailDetail onboardflag == 2 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    int candidateId = rs.getInt(1);
                    String clientname = rs.getString(2) != null ? rs.getString(2) : "";
                    String clientmailId = rs.getString(3) != null ? rs.getString(3) : "";
                    String comailId = rs.getString(4) != null ? rs.getString(4) : "";
                    String pdffileName = rs.getString(5) != null ? rs.getString(5) : "";
                    String candidateName = rs.getString(6) != null ? rs.getString(6) : "";
                    int jobpostId = rs.getInt(7);
                    String position = rs.getString(8) != null ? rs.getString(8) : "";
                    String grade = rs.getString(9) != null ? rs.getString(9) : "";
                    String date = rs.getString(10) != null ? rs.getString(10) : "";
                    String candidatemail = rs.getString(11) != null ? rs.getString(11) : "";
                    String clientAsset = rs.getString(12) != null ? rs.getString(12) : "";
                    String casset = "";
                    if (!clientAsset.equals("")) {
                        casset = clientname + " - " + clientAsset;
                    }
                    if (!grade.equals("")) {
                        position += " - " + grade;
                    }
                    info = new OnboardingInfo(shortlistId, candidateId, clientname, clientmailId, "", pdffileName, candidateName.trim(),
                            jobpostId, position, "", "", "", "", date, candidatemail, casset);
                }

                } else if (onboardflag == 3) 
                {
                sb.append("SELECT s.i_candidateid, m.s_name, m.s_to, m.s_cc, m.s_bcc, m.s_subject, m.s_filename, s.s_pdffilename, t_candidate.s_firstname, ");
                sb.append("t_jobpost.i_jobpostid, t_position.s_name, t_grade.s_name, m.s_sendby, DATE_FORMAT(m.ts_regdate,'%d-%b-%Y'), t_candidate.s_email ");
                sb.append("FROM t_maillog m ");
                sb.append("LEFT JOIN t_shortlist as s on (s.i_shortlistid = m.i_shortlistid) ");
                sb.append("LEFT JOIN t_candidate on (t_candidate.i_candidateid = s.i_candidateid) ");
                sb.append("LEFT JOIN t_jobpost on (t_jobpost.i_jobpostid = s.i_jobpostid) ");
                sb.append("LEFT JOIN t_position on (t_jobpost.i_positionid = t_position.i_positionid) ");
                sb.append("LEFT JOIN t_grade on (t_jobpost.i_gradeid = t_grade.i_gradeid) ");
                sb.append("WHERE m.i_shortlistid =? ");
                if (maillogId > 0) {
                    sb.append("AND m.i_maillogid =? ");
                }
                sb.append("order by m.ts_regdate desc limit 0,1");
                String query = (sb.toString()).intern();
                sb.setLength(0);
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, shortlistId);
                if (maillogId > 0) {
                    pstmt.setInt(2, maillogId);
                }
                logger.info("settravelEmailDetail  onboardflag == 3:: " + pstmt.toString());

                rs = pstmt.executeQuery();

                while (rs.next())
                {
                    int candidateId = rs.getInt(1);
                    String clientname = rs.getString(2) != null ? rs.getString(2) : "";
                    String clientmailId = rs.getString(3) != null ? rs.getString(3) : "";
                    String comailId = rs.getString(4) != null ? rs.getString(4) : "";
                    String bccmailId = rs.getString(5) != null ? rs.getString(5) : "";
                    String subject = rs.getString(6) != null ? rs.getString(6) : "";
                    String mailfileName = rs.getString(7) != null ? rs.getString(7) : "";
                    String pdffileName = rs.getString(8) != null ? rs.getString(8) : "";
                    String candidateName = rs.getString(9) != null ? rs.getString(9) : "";
                    int jobpostId = rs.getInt(10);
                    String position = rs.getString(8) != null ? rs.getString(11) : "";
                    String grade = rs.getString(12) != null ? rs.getString(12) : "";
                    String sendby = rs.getString(13) != null ? rs.getString(13) : "";
                    String date = rs.getString(14) != null ? rs.getString(14) : "";
                    String candidatemail = rs.getString(15) != null ? rs.getString(15) : "";
                    if (!grade.equals("")) {
                        position += " - " + grade;
                    }

                    info = new OnboardingInfo(shortlistId, candidateId, clientname, clientmailId, comailId, pdffileName, candidateName, jobpostId,
                            position, bccmailId, subject, mailfileName, sendby, date, clientmailId, "");
                }
                rs.close();
            }

        } catch (Exception exception) {
            print(this, "getJobpostDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public OnboardingInfo setEmailDetail(int shortlistId, int onboardflag, int maillogId) 
    {
        OnboardingInfo info = null;
        StringBuilder sb = new StringBuilder();
        try
        {
            if (onboardflag <= 5) 
            {
                sb.append("SELECT t_shortlist.i_candidateid, t_client.s_name, t_client.s_email, t4.emails, t_shortlist.s_pdffilename, ");
                sb.append("t_candidate.s_firstname, t_jobpost.i_jobpostid, t_position.s_name, t_grade.s_name, DATE_FORMAT(t_shortlist.ts_regdate,'%d-%b-%Y'), ");
                sb.append("t_candidate.s_email,t_clientasset.s_name FROM t_shortlist ");
                sb.append("LEFT JOIN t_jobpost on (t_jobpost.i_jobpostid = t_shortlist.i_jobpostid) ");
                sb.append("LEFT JOIN t_position on (t_jobpost.i_positionid = t_position.i_positionid) ");
                sb.append("LEFT JOIN t_grade on (t_jobpost.i_gradeid = t_grade.i_gradeid) ");
                sb.append("LEFT JOIN t_candidate on (t_candidate.i_candidateid = t_shortlist.i_candidateid) ");
                sb.append("LEFT JOIN t_client on (t_client.i_clientid = t_jobpost.i_clientid) ");
                sb.append("LEFT JOIN t_clientasset on (t_clientasset.i_clientassetid = t_jobpost.i_clientassetid)");
                sb.append("LEFT JOIN (select t_client.i_clientid, GROUP_CONCAT(DISTINCT t3.s_email ORDER BY  t3.s_email SEPARATOR ', ') as emails FROM t_client LEFT JOIN t_userlogin as t3 on find_in_set(t3.i_userid, t_client.s_ocsuserids) group by t_client.i_clientid) as t4 on (t4.i_clientid = t_client.i_clientid) ");
                sb.append("WHERE t_shortlist.i_shortlistid = ? ");
                String query = sb.toString().intern();
                sb.setLength(0);
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, shortlistId);
                print(this, "setEmailDetail onboardflag <= 5 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    int candidateId = rs.getInt(1);
                    String clientname = rs.getString(2) != null ? rs.getString(2) : "";
                    String clientmailId = rs.getString(3) != null ? rs.getString(3) : "";
                    String comailId = rs.getString(4) != null ? rs.getString(4) : "";
                    String pdffileName = rs.getString(5) != null ? rs.getString(5) : "";
                    String candidateName = rs.getString(6) != null ? rs.getString(6) : "";
                    int jobpostId = rs.getInt(7);
                    String position = rs.getString(8) != null ? rs.getString(8) : "";
                    String grade = rs.getString(9) != null ? rs.getString(9) : "";
                    String date = rs.getString(10) != null ? rs.getString(10) : "";
                    String candidatemail = rs.getString(11) != null ? rs.getString(11) : "";
                    String clientAsset = rs.getString(12) != null ? rs.getString(12) : "";
                    String casset = "";
                    if (!clientAsset.equals("")) {
                        casset = clientname + " - " + clientAsset;
                    }
                    if (!grade.equals("")) {
                        position += " - " + grade;
                    }
                    info = new OnboardingInfo(shortlistId, candidateId, clientname, clientmailId, "", pdffileName, candidateName.trim(),
                            jobpostId, position, "", "", "", "", date, candidatemail, casset);
                }

                } else if (onboardflag >= 6) 
                {
                    sb.append("SELECT s.i_candidateid, m.s_name, m.s_to, m.s_cc, m.s_bcc, m.s_subject, m.s_filename, s.s_pdffilename, t_candidate.s_firstname, ");
                    sb.append("t_jobpost.i_jobpostid, t_position.s_name, t_grade.s_name, m.s_sendby, DATE_FORMAT(m.ts_regdate,'%d-%b-%Y'), t_candidate.s_email ");
                    sb.append("FROM t_maillog m ");
                    sb.append("LEFT JOIN t_shortlist as s on (s.i_shortlistid = m.i_shortlistid) ");
                    sb.append("LEFT JOIN t_candidate on (t_candidate.i_candidateid = s.i_candidateid) ");
                    sb.append("LEFT JOIN t_jobpost on (t_jobpost.i_jobpostid = s.i_jobpostid) ");
                    sb.append("LEFT JOIN t_position on (t_jobpost.i_positionid = t_position.i_positionid) ");
                    sb.append("LEFT JOIN t_grade on (t_jobpost.i_gradeid = t_grade.i_gradeid) ");
                    sb.append("WHERE m.i_shortlistid =? ");
                    if (maillogId > 0) {
                        sb.append("AND m.i_maillogid =? ");
                    }
                    sb.append("order by m.ts_regdate desc limit 0,1");
                    String query = (sb.toString()).intern();
                    sb.setLength(0);
                    conn = getConnection();
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, shortlistId);
                    if (maillogId > 0) {
                        pstmt.setInt(2, maillogId);
                    }
                    logger.info("setEmailDetail  onboardflag >= 6 :: " + pstmt.toString());

                    rs = pstmt.executeQuery();

                    while (rs.next()) 
                    {
                        int candidateId = rs.getInt(1);
                        String clientname = rs.getString(2) != null ? rs.getString(2) : "";
                        String clientmailId = rs.getString(3) != null ? rs.getString(3) : "";
                        String comailId = rs.getString(4) != null ? rs.getString(4) : "";
                        String bccmailId = rs.getString(5) != null ? rs.getString(5) : "";
                        String subject = rs.getString(6) != null ? rs.getString(6) : "";
                        String mailfileName = rs.getString(7) != null ? rs.getString(7) : "";
                        String pdffileName = rs.getString(8) != null ? rs.getString(8) : "";
                        String candidateName = rs.getString(9) != null ? rs.getString(9) : "";
                        int jobpostId = rs.getInt(10);
                        String position = rs.getString(8) != null ? rs.getString(11) : "";
                        String grade = rs.getString(12) != null ? rs.getString(12) : "";
                        String sendby = rs.getString(13) != null ? rs.getString(13) : "";
                        String date = rs.getString(14) != null ? rs.getString(14) : "";
                        String candidatemail = rs.getString(15) != null ? rs.getString(15) : "";
                        if (!grade.equals("")) {
                            position += " - " + grade;
                        }

                        info = new OnboardingInfo(shortlistId, candidateId, clientname, clientmailId, comailId, pdffileName, candidateName, jobpostId,
                                position, bccmailId, subject, mailfileName, sendby, date, clientmailId, "");
                    }
                    rs.close();
                }
            } catch (Exception exception) {
                print(this, "getJobpostDetail :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
            return info;
    }

    public void savetozip(String zipFile, ArrayList list) {
        try
        {
            byte[] buffer = new byte[1024];
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);

            int size = list.size();
            for (int i = 0; i < size; i++) {
                OnboardingInfo info = (OnboardingInfo) list.get(i);
                File srcFile = new File(getMainPath("add_onboarding") + info.getPdffilename());
                FileInputStream fis = new FileInputStream(srcFile);
                // begin writing a new ZIP entry, positions the stream to the start of the entry data
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                // close the InputStream
                fis.close();
            }
            zos.close();
        } catch (IOException ioe) {
            print(this, "Error creating zip file: " + ioe);
        }
    }

    public int createMailLog1(int type, String name, String to, String cc, String bcc, String from, String subject, String filename, String attachmentpath, int shortlistId, String username, int oflag) {
        int id = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_maillog ");
            sb.append("(i_type, s_name, s_to, s_cc, s_bcc, s_from, s_subject, s_filename, ts_regdate, ts_moddate, s_attachmentpath, i_shortlistid, s_sendby, i_oflag) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            String query = sb.toString().intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
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
            print(this, "createMailLog :: " + query);
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

    public int sendtravelMail(String fromval, String toval, String ccval, String bccval, String subject, String description, String candidatename,
            String filename2, String clientname, int shortlistId, String username) throws MessagingException {
        String toaddress[] = parseCommaDelimString(toval);
        String ccaddress[] = parseCommaDelimString(ccval);
        String bccaddress[] = parseCommaDelimString(bccval);
        String filePath = getMainPath("add_onboarding");
        String vfilePath = getMainPath("view_onboarding");
        String attachmentpath2 = "";
        String attachfilepath2 = "";
        String attachfilename2 = "";

        if (filename2 != null && !filename2.equals("")) {
            attachmentpath2 = vfilePath + filename2;
            attachfilepath2 = filePath + filename2;
            attachfilename2 = candidatename + ".pdf";
        }
        String sdescription = description.replaceAll("\n", "<br/>");
        String mailbody = getpatchbody(sdescription, "mail_selection.html");

        String file_maillog = getMainPath("file_maillog");
        java.util.Date nowmail = new java.util.Date();
        String fn_mail = "cvma-" + String.valueOf(nowmail.getTime());
        String filePath_html = createFolder(file_maillog);
        String fname = writeHTMLFile(mailbody, file_maillog + "/" + filePath_html, fn_mail + ".html");

        StatsInfo sinfo = postMailAttach1(toaddress, ccaddress, bccaddress, mailbody, subject, attachfilepath2, attachfilename2, -1);
        String from = "";
        if (sinfo != null) {
            from = sinfo.getDdlLabel();
        }
        int maillogId = createMailLog1(15, clientname, toval, ccval, bccval, from, subject, "/" + filePath_html + "/" + fname, attachmentpath2, shortlistId, username, 3);

        return maillogId;
    }

    public int sendformalityMail(String fromval, String toval, String ccval, String bccval, String subject, String description,
            String candidatename, String filename, String clientname, int shortlistId, String username) throws MessagingException {
        String toaddress[] = parseCommaDelimString(toval);
        String ccaddress[] = parseCommaDelimString(ccval);
        String bccaddress[] = parseCommaDelimString(bccval);
        String filePath = getMainPath("add_archivefiles");
        String vfilePath = getMainPath("view_archivefiles");
        String attachmentpath = vfilePath + filename;
        String sdescription = description.replaceAll("\n", "<br/>");
        String mailbody = getpatchbody(sdescription, "mail_selection.html");

        String file_maillog = getMainPath("file_maillog");
        java.util.Date nowmail = new java.util.Date();
        String fn_mail = "cvma-" + String.valueOf(nowmail.getTime());
        String filePath_html = createFolder(file_maillog);
        String fname = writeHTMLFile(mailbody, file_maillog + "/" + filePath_html, fn_mail + ".html");

        StatsInfo sinfo = postMailAttach(toaddress, ccaddress, bccaddress, mailbody, subject, filePath + filename, candidatename + ".zip", -1);
        String from = "";
        if (sinfo != null) {
            from = sinfo.getDdlLabel();
        }

        int maillogId = createMailLog(16, clientname, toval, ccval, bccval, from, subject, "/" + filePath_html + "/" + fname, attachmentpath, shortlistId, username, 4);

        return maillogId;
    }

    public int sendMobilizationMail(String mailfrom, String mailto, String mailcc, String mailbcc, String subject, String description,
            String[] candidatename, String[] filename, String clientname, String vfilename, String username, int shortlistId) throws MessagingException {
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
            int maillogId = mobilization.createSpacificMailLog(17, clientname, mailto, mailcc, mailbcc, from, subject, "/" + filePath_html + "/" + fname, vfilename, shortlistId, username, 4);
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

    public int updateonboardflagformail(int shortlistId, int uId, String mailby, int onboardflag, int upflag, String date) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_shortlist SET ");
            sb.append("i_onboard = ?, ");
            if (!mailby.equals("")) {
                sb.append("s_formalitymailby = ?, ");
            }
            // upflag active when external zip file uploaded
            if (upflag > 0) {
                sb.append("i_upflag = 1, ");
            }
            if (onboardflag == 9) {
                sb.append("ts_onboardingon = ?, ");
            }
            sb.append("d_date = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_formalitymaildate = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_shortlistid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, onboardflag);
            if (!mailby.equals("")) {
                pstmt.setString(++scc, mailby);
            }
            if (onboardflag == 9) {
                pstmt.setString(++scc, currDate1());
            }
            pstmt.setString(++scc, changeDate1(date));
            pstmt.setInt(++scc, uId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, shortlistId);
            print(this, "updateonboardflagformail :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updatesflagformail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int updateonboardflagfortravelmail(int shortlistId, int uId, String mailby, int onboardflag) {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_shortlist SET  ");
            sb.append(" i_onboard = ?, ");
            if (!mailby.equals("")) {
                sb.append(" s_traveldetailsmailby = ?, ");
            }
            sb.append(" i_userid = ?, ");
            sb.append(" ts_travelmaildate = ?,  ");
            sb.append(" ts_moddate = ?  ");
            sb.append("WHERE i_shortlistid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, onboardflag);
            if (!mailby.equals("")) {
                pstmt.setString(++scc, mailby);
            }
            pstmt.setInt(++scc, uId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, shortlistId);
            print(this, "updateonboardflagfortravelmail :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateonboardflagfortravelmail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public Collection getformalitiesById(int clientId, int shortlistId)
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_formalityid, s_name FROM t_formality WHERE i_status = 1 and i_clientid = ? and i_formalityid NOT IN (select i_formalityid FROM t_onboardingdoc WHERE i_shortlistid = ?) ORDER BY  s_name").intern();
        coll.add(new OnboardingInfo(-1, "- Select -"));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientId);
            pstmt.setInt(2, shortlistId);
            print(this, "getformalitiesById :: " +pstmt.toString());
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new OnboardingInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public int createformalitypdf(int shortlistId, String pdffilename, int formalityId, int userId) {
        int onboarddocId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_onboardingdoc ");
            sb.append("(s_generateddoc, i_shortlistid , i_formalityid, i_userid,i_status, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, pdffilename);
            pstmt.setInt(++scc, shortlistId);
            pstmt.setInt(++scc, formalityId);
            pstmt.setInt(++scc, userId);
            pstmt.setInt(++scc, 1);
            pstmt.setString(++scc, currDate1());
            //print(this,"createformalitypdf :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                onboarddocId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createformalitypdf :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return onboarddocId;
    }

    public int deleteformalitypdf(int shortlistId, int onboarddocId, int userId) 
    {
        int portId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM t_onboardingdoc ");
            sb.append("WHERE i_onboardingdocid = ? and i_shortlistid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, onboarddocId);
            pstmt.setInt(++scc, shortlistId);
            print(this, "deleteformalitypdf :: " + pstmt.toString());
            portId = pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "deleteformalitypdf :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return portId;
    }

    public ArrayList getformlistByshortlist(int shortlistId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_onboardingdocid, t_onboardingdoc.i_formalityid, t_onboardingdoc.s_generateddoc, t_formality.s_name ");
        sb.append("FROM t_onboardingdoc ");
        sb.append("LEFT JOIN t_formality ON (t_formality.i_formalityid = t_onboardingdoc.i_formalityid) ");
        sb.append("WHERE t_onboardingdoc.i_status = 1 AND t_onboardingdoc.i_shortlistid = ? ");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, shortlistId);
            print(this, "getformlistByshortlist :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String pdffilename, formalityname;
            int onboarddocId, formalityId;
            while (rs.next()) 
            {
                onboarddocId = rs.getInt(1);
                formalityId = rs.getInt(2);
                pdffilename = rs.getString(3) != null ? rs.getString(3) : "";
                formalityname = rs.getString(4) != null ? rs.getString(4) : "";
                list.add(new OnboardingInfo(shortlistId, onboarddocId, formalityId, pdffilename, formalityname));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getuploadformlistByshortlist(int shortlistId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_onboardingdocid, t_onboardingdoc.i_formalityid, t_onboardingdoc.s_uploadeddoc, t_formality.s_name ");
        sb.append("FROM t_onboardingdoc ");
        sb.append("LEFT JOIN t_formality ON (t_formality.i_formalityid = t_onboardingdoc.i_formalityid) ");
        sb.append("WHERE t_onboardingdoc.i_status = 1 AND t_onboardingdoc.i_shortlistid = ? ");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, shortlistId);
            print(this, "getuploadformlistByshortlist :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String pdffilename, formalityname;
            int onboarddocId, formalityId;
            while (rs.next())
            {
                onboarddocId = rs.getInt(1);
                formalityId = rs.getInt(2);
                pdffilename = rs.getString(3) != null ? rs.getString(3) : "";
                formalityname = rs.getString(4) != null ? rs.getString(4) : "";
                list.add(new OnboardingInfo(shortlistId, onboarddocId, formalityId, pdffilename, formalityname));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getOnboardingkitByName()
    {
        ArrayList onboardingkits = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_onboardingkitid, s_name ");
        sb.append("FROM t_onboardingkit WHERE i_status = 1 ");
        sb.append(" ORDER BY i_status, s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            logger.info("getOnboardingkitByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int onboardingkitId;
            while (rs.next()) 
            {
                onboardingkitId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                onboardingkits.add(new OnboardingInfo(onboardingkitId, name));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return onboardingkits;
    }

    public ArrayList getOnboardingkitById(String Ids) 
    {
        ArrayList onboardingkits = new ArrayList();
        if (!Ids.equals("")) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_onboardingkitid, s_name FROM t_onboardingkit WHERE i_status = 1 AND i_onboardingkitid IN (" + Ids + ") ");
            sb.append("ORDER BY s_name ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                logger.info("getOnboardingkitById :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String name;
                int onboardingkitId;
                while (rs.next()) 
                {
                    onboardingkitId = rs.getInt(1);
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    onboardingkits.add(new OnboardingInfo(onboardingkitId, name));
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return onboardingkits;
    }

    public int uploadformalitypdf(int shortlistId, String pdffilename, int onboarddocId, int userId) {
        int status = 0;
        try
        {
            StringBuilder sb = new StringBuilder();

            sb.append("UPDATE t_onboardingdoc SET  ");
            sb.append(" s_uploadeddoc = ?, ");
            sb.append(" i_userid = ?, ");
            sb.append(" ts_moddate = ?  ");
            sb.append("WHERE i_shortlistid = ? and i_onboardingdocid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, pdffilename);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, shortlistId);
            pstmt.setInt(++scc, onboarddocId);
            status = pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "uploadformalitypdf :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return status;
    }

    public int updateshortlistforkit(int shortlistId, String onboardingkitids, int userId) 
    {
        int status = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();

            sb.append("UPDATE t_shortlistmob SET ");
            sb.append("s_onboardingkits =?, ");
            sb.append("ts_moddate = ?  ");
            sb.append("WHERE i_shortlistid =? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, onboardingkitids);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, shortlistId);
            logger.info("updateshortlistforkit :: " + pstmt.toString());
            status = pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updateshortlistforkit :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return status;
    }

    public int getJobpostIdByShortlistId(int shortlistId, int uId, int candidateId, int clientId, int clientassetId) 
    {
        int status = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select t_shortlist.i_jobpostid, t_jobpost.i_noofopening , jp1.ct ");
        sb.append("FROM t_shortlist LEFT JOIN t_jobpost on (t_shortlist.i_jobpostid = t_jobpost.i_jobpostid) ");
        sb.append(" LEFT JOIN (select i_jobpostid , count(1) as ct  FROM t_shortlist  WHERE   i_onboard = 9   group by i_jobpostid ) as jp1 on ( jp1.i_jobpostid =  t_shortlist.i_jobpostid ) ");
        sb.append(" WHERE  t_shortlist.i_onboard = 9  and t_shortlist.i_shortlistid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        int jobpostId = 0, noofopenings = 0, onboardedcount = 0;
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, shortlistId);
            logger.info("getJobpostIdByShortlistId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                jobpostId = rs.getInt(1);
                noofopenings = rs.getInt(2);
                onboardedcount = rs.getInt(3);
            }
            if (onboardedcount >= noofopenings) {
                status = 1;
            }
            updatecandidateafteronboarded(candidateId, clientId, clientassetId, uId);
            if (status > 0) {
                updatejobpostflagbystatus(jobpostId, uId);
                updatecandidateflagonboarded(uId);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return jobpostId;
    }

    public int updatecandidateafteronboarded(int candidateId, int clientId, int clientassetId, int userId) 
    {
        int status = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();

            sb.append("UPDATE t_candidate SET ");
            sb.append(" i_progressid = 0 , ");
            sb.append(" i_clientid = ? , ");
            sb.append(" i_clientassetid = ? , ");
            sb.append(" i_userid = ? , ");
            sb.append(" ts_moddate = ?  ");
            sb.append(" WHERE i_candidateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientId);
            pstmt.setInt(++scc, clientassetId);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, candidateId);
            logger.info("updatecandidateafteronboarded :: " + pstmt.toString());
            status = pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updatecandidateafteronboarded :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return status;
    }

    public int updatejobpostflagbystatus(int jobpostId, int userId)
    {
        int status = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_jobpost SET ");
            sb.append(" i_status = 3 , ");
            sb.append(" i_userid = ? , ");
            sb.append(" ts_moddate = ?  ");
            sb.append(" WHERE i_jobpostid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, jobpostId);
            logger.info("updatejobpostflagbystatus :: " + pstmt.toString());
            status = pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updatejobpostflagbystatus :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return status;
    }

    public int updatecandidateflagonboarded(int userId) 
    {
        int status = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_candidate SET ");
            sb.append(" i_progressid = 0 , ");
            sb.append(" i_userid = ? , ");
            sb.append(" ts_moddate = ?  ");
            sb.append(" WHERE i_candidateid IN (select i_candidateid FROM t_shortlist WHERE i_onboard != 9) ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            logger.info("updatecandidateflagonboarded :: " + pstmt.toString());
            status = pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updatecandidateflagonboarded :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return status;
    }

    public int getfileexitsByIds(int shortlistId)
    {
        int count = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select count(1) FROM t_onboardingdoc WHERE (s_uploadeddoc is NULL or s_uploadeddoc = '') and i_shortlistid = ? ");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, shortlistId);
            print(this, "getfileByIds :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                count = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return count;
    }

    public OnboardingInfo getformalityflagByformality(int formalityId)
    {
        OnboardingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_temptype, s_formalityfile FROM t_formality WHERE i_formalityid = ? ");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, formalityId);
            print(this, "getformalityflagByformality :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename;
            int temptype = 0;
            while (rs.next()) 
            {
                temptype = rs.getInt(1);
                filename = rs.getString(2) != null ? rs.getString(2) : "";
                info = new OnboardingInfo(temptype, filename);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    

    public void copyfile(String source, String target) 
    {
        try 
        {
            File from_file = new File(source);
            File to_file = new File(target);

            FileInputStream from = null;
            FileOutputStream to = null;
            try {
                from = new FileInputStream(from_file);
                to = new FileOutputStream(to_file);
                byte[] buffer = new byte[4096];
                int bytes_read;
                while ((bytes_read = from.read(buffer)) != -1) {
                    to.write(buffer, 0, bytes_read);
                }
            } finally {
                if (from != null) {
                    try {
                        from.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (to != null) {
                    try {
                        to.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            print(this, "Error :: " + e.getMessage());
        }
    }

    public ArrayList getdocumentdetailsByIds(String doctypeIds)
    {
        ArrayList list = new ArrayList();
        if (!doctypeIds.equals("")) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_doctypeid, s_doc FROM t_doctype WHERE i_status =1 AND i_doctypeid IN ( " + doctypeIds + ") ");
            String query = sb.toString();
            sb.setLength(0);
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                print(this, "getdocumentdetailsByIds :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int doctypeId;
                String docname;
                while (rs.next()) {

                    doctypeId = rs.getInt(1);
                    docname = rs.getString(2) != null ? rs.getString(2) : "";
                    list.add(new OnboardingInfo(doctypeId, docname));
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

    public void insertCrewRotation(int clientId, int clientassetId, int candidateId, int jobpostId, int userId) 
    {
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_crewrotation ");
            sb.append("(i_candidateid, i_clientid , i_clientassetid, i_jobpostid, i_docflag, i_status1, i_userid,ts_onboardeddate,ts_regdate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, clientId);
            pstmt.setInt(++scc, clientassetId);
            pstmt.setInt(++scc, jobpostId);
            pstmt.setInt(++scc, 1);
            pstmt.setInt(++scc, 1);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            //print(this,"insertCrewRotaion :: " + pstmt.toString());
            pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "insertCrewRotaion :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public void createOnboardingFormFile(int candiadteId, int formalityId,
            int clientId, int positionId, int jobpostId, int shortlistId) 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname, t_country.s_nationality FROM t_candidate ");
        sb.append(" LEFT JOIN t_country on ( t_country.i_countryid = t_candidate.i_candidateid ) ");
        sb.append(" WHERE t_candidate.i_status = 1 and t_candidate.i_candidateid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        Connection conn = null;
        ResultSet rs = null;

        String clientname = "";
        String assettname = "";
        StringBuilder clientinner = new StringBuilder();
        clientinner.append(" SELECT t_client.s_name, t_clientasset.s_name FROM t_client ");
        clientinner.append(" LEFT JOIN t_clientasset on  ( t_clientasset.i_clientassetid = t_client.i_clientid ) ");
        clientinner.append("WHERE t_client.i_status = 1 and t_client.i_clientid = ? ");
        String clientquery = clientinner.toString();
        clientinner.setLength(0);
        PreparedStatement pstmtclient = null;

        String positionName = "";
        String rank = "";
        StringBuilder jobpositioninner = new StringBuilder();
        jobpositioninner.append("SELECT t_position.s_name, t_grade.s_name FROM t_position ");
        jobpositioninner.append("LEFT JOIN t_grade ON  t_grade.i_gradeid = t_position.i_positionid ");
        jobpositioninner.append("WHERE t_position.i_status = 1 and t_position.i_positionid = ? ");
        String jobpositionquery = jobpositioninner.toString();
        jobpositioninner.setLength(0);
        PreparedStatement pstmtjobposition = null;

        String targetmobdate = "";
        StringBuilder mobidateinner = new StringBuilder();
        mobidateinner.append(" SELECT ts_targetmobdate FROM t_jobpost WHERE i_status IN (1,3) and i_jobpostid = ? ");
        String mobidatequery = mobidateinner.toString();
        mobidateinner.setLength(0);
        PreparedStatement pstmtmobidate = null;

        String arrivaldatetime = "";
        StringBuilder arrivaldateinner = new StringBuilder();
        arrivaldateinner.append(" SELECT s_val10 FROM t_onboardingdtls WHERE i_shortlistid = ? ");
        String arrivaldatequery = arrivaldateinner.toString();
        arrivaldateinner.setLength(0);
        PreparedStatement pstmtarrivaldate = null;

        String fle_query = ("SELECT s_description FROM t_formality WHERE i_formalityid = ? ");

        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(fle_query);
            pstmt.setInt(1, formalityId);
            print(this, "formalityId: " + formalityId);
            String templatename = "";
            rs = pstmt.executeQuery();
            while (rs.next()) {
                templatename = rs.getString(1) != null ? rs.getString(1): "";
            }
            rs.close();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candiadteId);
            print(this, "createOnboardingFormFile :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                String firstname = rs.getString(1);
                String middlename = rs.getString(2);
                String lastname = rs.getString(3);
                String nationality = rs.getString(4) != null ? rs.getString(4) : "";

                pstmtclient = conn.prepareStatement(clientquery);
                pstmtclient.setInt(1, clientId);
                ResultSet clientrs = pstmtclient.executeQuery();
                clientrs = pstmtclient.executeQuery();
                while (clientrs.next()) {
                    clientname = clientrs.getString(1) != null ? clientrs.getString(1) : "";
                    assettname = clientrs.getString(2) != null ? clientrs.getString(2) : "";
                    if (!clientname.equals("")) {
                        clientname += " - " + assettname;
                    }
                }

                pstmtjobposition = conn.prepareStatement(jobpositionquery);
                pstmtjobposition.setInt(1, positionId);
                ResultSet jobpositionrs = pstmtjobposition.executeQuery();
                jobpositionrs = pstmtjobposition.executeQuery();
                while (jobpositionrs.next()) {
                    positionName = jobpositionrs.getString(1) != null ? jobpositionrs.getString(1) : "";
                    rank = jobpositionrs.getString(2) != null ? jobpositionrs.getString(2) : "";
                    if (!positionName.equals("")) {
                        positionName += " - " + rank;
                    }
                }

                pstmtmobidate = conn.prepareStatement(mobidatequery);
                pstmtmobidate.setInt(1, jobpostId);
                ResultSet mobidaters = pstmtmobidate.executeQuery();
                mobidaters = pstmtmobidate.executeQuery();
                while (mobidaters.next()) {
                    targetmobdate = mobidaters.getString(1) != null ? mobidaters.getString(1) : "";
                }

                pstmtarrivaldate = conn.prepareStatement(arrivaldatequery);
                pstmtarrivaldate.setInt(1, shortlistId);
                ResultSet arrivaldaters = pstmtarrivaldate.executeQuery();
                arrivaldaters = pstmtarrivaldate.executeQuery();
                while (arrivaldaters.next()) {
                    arrivaldatetime = arrivaldaters.getString(1) != null ? arrivaldaters.getString(1) : "";
                }

                HashMap hashmap = new HashMap();
                Template template = new Template(getMainPath("add_formality_file") + templatename);

                hashmap.put("NAME", firstname);
                hashmap.put("MIDDLENAME", middlename);
                hashmap.put("LASTNAME", lastname);
                hashmap.put("POSITIONNAME", positionName);
                hashmap.put("ASSETNAME", assettname);
                hashmap.put("NATIONALITY", nationality);
                hashmap.put("RANK", rank);
                hashmap.put("TARGETMOBDATE", targetmobdate);
                hashmap.put("ARRIVALDATE", arrivaldatetime);
                hashmap.put("COMPANY", clientname);

                String content = template.patch(hashmap);
                //content = content.replaceAll("strong>", "b>");
                hashmap.clear();
                String filePath = getMainPath("add_onboarding");
                java.util.Date now = new java.util.Date();
                String fname = String.valueOf(now.getTime()) + ".pdf";
                String htmlFolderName = dateFolder();
                File dir = new File(filePath + htmlFolderName);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                File pdfFile = new File(filePath + htmlFolderName + "/" + fname);
                int pdfwidth = 1050;
                try {
                    generatePDFString(content, pdfFile, PD4Constants.A4, "", "", "", "", pdfwidth);
                    pdfFile = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public int updateshortlistforextupload(int shortlistId, String fileName1, int userId) 
    {
        int status = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();

            sb.append("UPDATE t_shortlist SET ");
            sb.append("s_uploadexternal =? ");
            sb.append("WHERE i_shortlistid = ?");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, fileName1);
            pstmt.setInt(++scc, shortlistId);
            logger.info("updateshortlistforextupload :: " + pstmt.toString());
            status = pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updateshortlistforextupload :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return status;
    }

    public String deleteFiles(String filename, String path) 
    {
        String str = "";
        try 
        {
            if ((filename != null && !filename.equals(""))) 
            {
                String deletePath = path + filename;
                File file = new File(deletePath);
                if (file.exists()) 
                {
                    file.delete();
                    str = "true";
                }
            }
        } catch (Exception e) {
            print(this, "catch block of file::" + e);
        }
        return str;
    }

    public OnboardingInfo getSelectedCandidateByIDs(int shortlistId) 
    {
        OnboardingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name, c.s_photofilename, ");
        sb.append("DATE_FORMAT(t_jobpost.ts_targetmobdate, '%d-%b-%Y'), t_shortlist.i_onboard, ");
        sb.append("t_shortlist.i_onflag, c.i_countryid, t_shortlistmob.i_notreqtravel, t_shortlistmob.i_notreqaccom, t_shortlist.i_upflag, t_shortlist.s_uploadexternal FROM t_shortlist ");
        sb.append("LEFT JOIN t_jobpost ON (t_shortlist.i_jobpostid = t_jobpost.i_jobpostid) ");
        sb.append("LEFT JOIN t_candidate AS c  on (c.i_candidateid = t_shortlist.i_candidateid ) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_shortlistmob ON (t_shortlistmob.i_shortlistid = t_shortlist.i_shortlistid) ");
        sb.append("WHERE t_shortlist.i_shortlistid =? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, shortlistId);
            logger.info("getSelectedCandidateByIDs onboarding:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, position, grade, photo, mobilizedate, extzipfile;
            int candidateId, onboardflag, onflag, countryId, notereqtravel, notereqtaccomm, upflag;
            while (rs.next()) 
            {
                candidateId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                grade = rs.getString(4) != null ? rs.getString(4) : "";
                photo = rs.getString(5) != null ? rs.getString(5) : "";
                mobilizedate = rs.getString(6) != null ? rs.getString(6) : "";
                onboardflag = rs.getInt(7);
                onflag = rs.getInt(8);
                countryId = rs.getInt(9);
                notereqtravel = rs.getInt(10);
                notereqtaccomm = rs.getInt(11);
                upflag = rs.getInt(12);
                extzipfile = rs.getString(13) != null ? rs.getString(13) : "";
                if (!grade.equals("")) {
                    position += " - " + grade;
                }

                info = new OnboardingInfo(candidateId, name, position, photo, mobilizedate, shortlistId, onboardflag, onflag,
                        countryId, notereqtravel, notereqtaccomm, upflag, extzipfile);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }

        return info;
    }

    public ArrayList getSummaryEmaillog(int shortlistId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        if (shortlistId > 0)
        {
            try 
            {
                sb.append("SELECT s_filename, s_sendby, DATE_FORMAT(ts_regdate,'%d-%b-%Y'), i_maillogid FROM t_maillog ");
                sb.append("WHERE i_oflag IN (3, 4) AND i_shortlistid =? ORDER BY ts_regdate DESC LIMIT 0,4");
                String query = (sb.toString()).intern();
                sb.setLength(0);
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, shortlistId);
                logger.info("getSummaryEmaillog :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int maillogId;
                String mailfileName, sendby, date;
                while (rs.next()) 
                {
                    mailfileName = rs.getString(1) != null ? rs.getString(1) : "";
                    sendby = rs.getString(2) != null ? rs.getString(2) : "";
                    date = rs.getString(3) != null ? rs.getString(3) : "";
                    maillogId = rs.getInt(4);
                    list.add(new OnboardingInfo(shortlistId, mailfileName, sendby, date, maillogId));
                }
                rs.close();
            } catch (Exception exception) {
                exception.printStackTrace();
                print(this, "getSummaryEmaillog :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return list;
    }

    public ArrayList getFormalityForm(int shortlistId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        if (shortlistId > 0) 
        {
            try
            {
                sb.append("SELECT t_onboardingdoc.i_onboardingdocid, t_formality.s_name, t_onboardingdoc.s_generateddoc, t_onboardingdoc.s_uploadeddoc FROM t_onboardingdoc ");
                sb.append("LEFT JOIN t_formality ON (t_formality.i_formalityid = t_onboardingdoc.i_formalityid) ");
                sb.append("WHERE i_shortlistid =?");
                String query = (sb.toString()).intern();
                sb.setLength(0);
                
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, shortlistId);
                logger.info("getFormalityForm :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int onboardingdocid;
                String formalityname, generateddoc, uploadeddoc;
                while (rs.next()) 
                {
                    onboardingdocid = rs.getInt(1);
                    formalityname = rs.getString(2) != null ? rs.getString(2) : "";
                    generateddoc = rs.getString(3) != null ? rs.getString(3) : "";
                    uploadeddoc = rs.getString(4) != null ? rs.getString(4) : "";
                    list.add(new OnboardingInfo(shortlistId, onboardingdocid, formalityname, generateddoc, uploadeddoc));
                }
                rs.close();
            } catch (Exception exception) {
                exception.printStackTrace();
                print(this, "getFormalityForm :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return list;
    }

    public String getFormalityuploadFormfile(int onboarddocId) 
    {
        String uploadeddoc = "";
        StringBuilder sb = new StringBuilder();
        try 
        {
            sb.append("SELECT t_onboardingdoc.s_uploadeddoc FROM t_onboardingdoc ");
            sb.append("WHERE i_onboardingdocid =?");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, onboarddocId);
            logger.info("getFormalityuploadFormfile :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                uploadeddoc = rs.getString(1) != null ? rs.getString(1) : "";
            }
            rs.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            print(this, "getFormalityuploadFormfile :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }

        return uploadeddoc;
    }

    public String getFormalitygeneratedFormfile(int onboarddocId) 
    {
        String generateddoc = "";
        StringBuilder sb = new StringBuilder();
        try 
        {
            sb.append("SELECT t_onboardingdoc.s_generateddoc FROM t_onboardingdoc ");
            sb.append("WHERE i_onboardingdocid =?");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, onboarddocId);
            logger.info("getFormalitygeneratedFormfile :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                generateddoc = rs.getString(1) != null ? rs.getString(1) : "";
            }
            rs.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            print(this, "getFormalitygeneratedFormfile :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }

        return generateddoc;
    }

    public Collection getClientAsset(int clientId, String assetids, int allclient, String permission) 
    {
        Collection coll = new LinkedList();
        if (clientId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_clientid = ? AND i_status = 1 ");
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
            coll.add(new OnboardingInfo(-1, " Select Asset "));
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
                    coll.add(new OnboardingInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new OnboardingInfo(-1, " Select Asset "));
        }
        return coll;
    }

    public Collection getClients(String cids, int allclient, String permission)
    {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_clientid, s_name FROM t_client WHERE i_status = 1 ");
        if (allclient == 1) 
        {
            sb.append("AND i_clientid > 0 ");
        } else if (!cids.equals("")) {
            sb.append("AND i_clientid IN (" + cids + ") ");
        } else {
            sb.append("AND i_clientid < 0 ");
        }

        sb.append("order by s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new OnboardingInfo(-1, " Select Client"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new OnboardingInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    //Excel
    public ArrayList getListForExcel(String search, int statusIndex, int clientIdIndex, int assetIdIndex, int allclient, String permission, String cids, String assetids) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_client.i_clientid, t_client.s_name,t_clientasset.i_clientassetid, t_clientasset.s_name, t_country.s_name, jp.su, t2.ct, t_clientasset.i_countryid FROM t_jobpost ");
        sb.append("LEFT JOIN t_client on (t_client.i_clientid = t_jobpost.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset on (t_clientasset.i_clientassetid = t_jobpost.i_clientassetid) ");
        sb.append("LEFT JOIN t_country on (t_country.i_countryid = t_clientasset.i_countryid) ");
        sb.append("LEFT JOIN (select cp.i_clientid, cp.i_clientassetid, cp.i_jobpostid, sum( i_noofopening) as su FROM t_jobpost as cp group by cp.i_clientassetid, cp.i_clientid ) as jp on (jp.i_clientassetid = t_jobpost.i_clientassetid and jp.i_clientid = t_jobpost.i_clientid)");
        sb.append("LEFT JOIN (select t_jobpost.i_clientid, t_jobpost.i_clientassetid, count(1) as ct FROM t_shortlist ");
        sb.append("LEFT JOIN t_jobpost on (t_jobpost.i_jobpostid = t_shortlist.i_jobpostid) WHERE t_jobpost.i_status IN (1,3) and t_shortlist.i_onboard = 9 ");
        sb.append("group by t_jobpost.i_clientid, t_jobpost.i_clientassetid) as t2 on (t2.i_clientid = t_client.i_clientid and t2.i_clientassetid = t_clientasset.i_clientassetid) ");
        sb.append("WHERE t_jobpost.i_status IN (1,3)  ");
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
            sb.append("AND t_jobpost.i_status = ?  ");
        }
        if (clientIdIndex > 0) {
            sb.append(" AND t_client.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND t_clientasset.i_clientassetid = ? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("AND (t_clientasset.s_name LIKE ? OR t_client.s_name LIKE ?  OR t_country.s_name LIKE ? ) ");
        }
        sb.append(" group by t_jobpost.i_clientid, t_jobpost.i_clientassetid ");

        sb.append("ORDER BY t_jobpost.i_status, t_jobpost.ts_regdate ");

        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
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
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAsset, country = "";
            int clientId, clientassetId, totalnoofopenings, onboardCount, countryId;
            while (rs.next()) {
                clientId = rs.getInt(1);
                clientName = rs.getString(2) != null ? rs.getString(2) : "";
                clientassetId = rs.getInt(3);
                clientAsset = rs.getString(4) != null ? rs.getString(4) : "";
                country = rs.getString(5) != null ? rs.getString(5) : "";
                totalnoofopenings = rs.getInt(6);
                onboardCount = rs.getInt(7);
                countryId = rs.getInt(8);

                if (!clientAsset.equals("")) {
                    clientName += " - " + clientAsset;
                }
                list.add(new OnboardingInfo(clientId, clientName, clientassetId, clientAsset, country, totalnoofopenings, onboardCount, countryId));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int insertOnboardingDtls(int shortlistId, int type, String val1, String val2, 
            String val3, String val4, String val5, String val6, String val7, String val8, String vald9,
            String valt9, String vald10, String valt10, String filename, int userId, int status) 
    {
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            if (shortlistId > 0) 
            {
                sb.append("INSERT INTO t_onboardingdtls (i_shortlistid, i_type, s_val1, s_val2, s_val3, s_val4, s_val5, s_val6, s_val7, s_val8, s_val9, s_val10, s_filename, ");
                sb.append("i_status, i_userid, ts_regdate, ts_moddate) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                String onquery = sb.toString().intern();
                sb.setLength(0);
                int scc = 0;
                pstmt = conn.prepareStatement(onquery);
                pstmt.setInt(++scc, shortlistId);
                pstmt.setInt(++scc, type);
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
                print(this, "insertOnboardingDtls :: " + pstmt.toString());
                pstmt.executeUpdate();

                updateShortlistFlag(shortlistId, userId, 1, 1);
            }
            pstmt.close();
        } catch (Exception exception) {
            print(this, "insertOnboardingDtls catch :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return shortlistId;
    }

    public ArrayList getOnboadingListByshortlistId(int shortlist, int type) 
    {
        ArrayList list = new ArrayList();
        if (shortlist > 0)
        {
            try 
            {
                conn = getConnection();
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT i_onboardingdtlsid, s_val1, s_val2, s_val3, s_val4, s_val5, s_val6, s_val7, s_val8, DATE_FORMAT(s_val9, '%d-%b-%Y %H:%i'), ");
                sb.append("DATE_FORMAT(s_val10, '%d-%b-%Y %H:%i'), s_filename, i_status, i_userid, DATE_FORMAT( ts_regdate, '%d-%b-%Y'), DATE_FORMAT(ts_moddate, '%d-%b-%Y'), ");
                sb.append("DATE_FORMAT(s_val9, '%d-%b-%Y'), DATE_FORMAT(s_val9, '%H:%i'), DATE_FORMAT(s_val10, '%d-%b-%Y'), DATE_FORMAT(s_val10, '%H:%i'), i_type FROM t_onboardingdtls ");
                sb.append("WHERE i_shortlistid =? ");
                if (type > 0) {
                    sb.append("AND i_type =? ");
                }
                String query = (sb.toString()).intern();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                pstmt.setInt(++scc, shortlist);
                if (type > 0) {
                    pstmt.setInt(++scc, type);
                }
                print(this, "getOnboadingListByshortlistId :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String val1, val2, val3, val4, val5, val6, val7, val8, val9, val10, filename, regdate, moddate, vald9, valt9, vald10, valt10;
                int onboardingdtlsid, userId, status, Type;
                while (rs.next()) 
                {
                    onboardingdtlsid = rs.getInt(1);
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
                    vald9 = rs.getString(17) != null ? rs.getString(17) : "";
                    valt9 = rs.getString(18) != null ? rs.getString(18) : "";
                    vald10 = rs.getString(19) != null ? rs.getString(19) : "";
                    valt10 = rs.getString(20) != null ? rs.getString(20) : "";
                    Type = rs.getInt(21);
                    list.add(new OnboardingInfo(shortlist, Type, onboardingdtlsid, val1, val2, val3, val4, val5, val6, val7, val8, val9, val10, filename, status, userId, regdate,
                            moddate, vald9, valt9, vald10, valt10));
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

    public int insertTravelDtls(int shortlistId, int travelnotreq, int userId, int status) 
    {
        int cc = 0;
        if (shortlistId > 0) 
        {
            try
            {
                conn = getConnection();
                String query = "SELECT i_shortlistid FROM t_shortlistmob WHERE i_shortlistid =?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, shortlistId);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    cc = rs.getInt(1);
                }

                if (cc > 0) {
                    String notreqquery = "UPDATE t_shortlistmob SET i_notreqtravel =?, ts_moddate =? WHERE i_shortlistid =?";
                    pstmt = conn.prepareStatement(notreqquery);
                    pstmt.setInt(1, travelnotreq);
                    pstmt.setString(2, currDate1());
                    pstmt.setInt(3, shortlistId);
                    print(this, "insertTravelDtls notreq:: " + pstmt.toString());
                    pstmt.executeUpdate();
                } else {
                    String notreqquery = "INSERT INTO t_shortlistmob (i_shortlistid, i_notreqtravel, ts_regdate, ts_moddate) VALUES(?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(notreqquery, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setInt(1, shortlistId);
                    pstmt.setInt(2, travelnotreq);
                    pstmt.setString(3, currDate1());
                    pstmt.setString(4, currDate1());
                    print(this, "insertTravelDtls notreq:: " + pstmt.toString());
                    pstmt.executeUpdate();
                    rs = pstmt.getGeneratedKeys();
                    while (rs.next()) {
                        cc = rs.getInt(1);
                    }
                    shortlistId = cc;
                    updateShortlistFlag(shortlistId, userId, 1, 1);
                }
                pstmt.close();
            } catch (Exception exception) {
                print(this, "insertTravelDtls catch:: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return shortlistId;
    }

    public int insertAccomodationDtls(int shortlistId, int travelnotreq, int userId, int status) {
        int cc = 0;
        if (shortlistId > 0) {
            try {
                conn = getConnection();
                String query = "SELECT i_shortlistid FROM t_shortlistmob WHERE i_shortlistid =?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, shortlistId);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    cc = rs.getInt(1);
                }

                if (cc > 0) {
                    String notreqquery = "UPDATE t_shortlistmob SET i_notreqaccom =?, ts_moddate =? WHERE i_shortlistid =?";
                    pstmt = conn.prepareStatement(notreqquery);
                    pstmt.setInt(1, travelnotreq);
                    pstmt.setString(2, currDate1());
                    pstmt.setInt(3, shortlistId);
                    print(this, "insertAccomodationDtls notreq:: " + pstmt.toString());
                    pstmt.executeUpdate();
                } else {
                    String notreqquery = "INSERT INTO t_shortlistmob (i_shortlistid, i_notreqaccom, ts_regdate, ts_moddate) VALUES(?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(notreqquery, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setInt(1, shortlistId);
                    pstmt.setInt(2, travelnotreq);
                    pstmt.setString(3, currDate1());
                    pstmt.setString(4, currDate1());
                    print(this, "insertAccomodationDtls notreq:: " + pstmt.toString());
                    pstmt.executeUpdate();
                    rs = pstmt.getGeneratedKeys();
                    while (rs.next()) {
                        cc = rs.getInt(1);
                    }
                    shortlistId = cc;
                    updateShortlistFlag(shortlistId, userId, 1, 1);
                }
                pstmt.close();
            } catch (Exception exception) {
                print(this, "insertAccomodationDtls catch:: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return shortlistId;
    }

    public int deleteOnboardingDtls(int onId)
    {
        int temp = 0;
        try 
        {
            conn = getConnection();
            if (onId > 0) 
            {
                String query = "DELETE FROM t_onboardingdtls WHERE i_onboardingdtlsid =? ";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, onId);
                print(this, "deleteOnboardingDtls :: " + pstmt.toString());
                pstmt.executeUpdate();
                temp = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return temp;
    }

    public OnboardingInfo getDetailsByOnId(int onId) 
    {
        OnboardingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_onboardingdtlsid, s_val1, s_val2, s_val3, s_val4, s_val5, s_val6, s_val7, s_val8, DATE_FORMAT(s_val9, '%d-%b-%Y'), DATE_FORMAT(s_val9, '%H:%i'), ");
        sb.append("DATE_FORMAT(s_val10, '%d-%b-%Y'), DATE_FORMAT(s_val10, '%H:%i'), s_filename, i_status, i_shortlistid FROM t_onboardingdtls ");
        sb.append("WHERE i_onboardingdtlsid =? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        if (onId > 0) 
        {
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, onId);
                print(this, "getDetailsByMobId :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String val1, val2, val3, val4, val5, val6, val7, val8, vald9, valt9, vald10, valt10, filename;
                int onboardingId, status, shortlistId;
                while (rs.next()) 
                {
                    onboardingId = rs.getInt(1);
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
                    shortlistId = rs.getInt(16);
                    info = new OnboardingInfo(shortlistId, onboardingId, val1, val2, val3, val4, val5, val6, val7, val8, vald9, valt9, vald10, valt10, filename, status);
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

    public OnboardingInfo getselectionEmailDetailById(int maillogId) 
    {
        OnboardingInfo info = null;
        StringBuilder sb = new StringBuilder();
        try
        {
            if (maillogId > 0) {
                sb.append("SELECT DATE_FORMAT(m.ts_regdate, '%d-%b-%Y %H:%i'), m.s_to, m.s_from, m.s_cc, m.s_bcc, m.s_subject, m.s_filename, m.s_attachmentpath, m.s_sendby, ");
                sb.append("s_attachmentpath1, s_attachmentpath2, s_attachmentpath3 FROM t_maillog m WHERE m.i_maillogid = ? ");

                String query = (sb.toString()).intern();
                sb.setLength(0);
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, maillogId);
                logger.info("getselectionEmailDetail :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String date, mailto, mailfrom, mailcc, mailbcc, subject, filename, attachmentpath, sentby, attachmentpath1, attachmentpath2, attachmentpath3;
                while (rs.next())
                {
                    date = rs.getString(1) != null ? rs.getString(1) : "";
                    mailto = rs.getString(2) != null ? rs.getString(2) : "";
                    mailfrom = rs.getString(3) != null ? rs.getString(3) : "";
                    mailcc = rs.getString(4) != null ? rs.getString(4) : "";
                    mailbcc = rs.getString(5) != null ? rs.getString(5) : "";
                    subject = rs.getString(6) != null ? rs.getString(6) : "";
                    filename = rs.getString(7) != null ? rs.getString(7) : "";
                    attachmentpath = rs.getString(8) != null ? rs.getString(8) : "";
                    sentby = rs.getString(9) != null ? rs.getString(9) : "";
                    attachmentpath1 = rs.getString(10) != null ? rs.getString(10) : "";
                    attachmentpath2 = rs.getString(11) != null ? rs.getString(11) : "";
                    attachmentpath3 = rs.getString(12) != null ? rs.getString(12) : "";
                    info = new OnboardingInfo(mailto, mailfrom, mailcc, mailbcc, subject, filename, attachmentpath, sentby, maillogId, date, attachmentpath1, attachmentpath2,
                            attachmentpath3);
                }
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public Collection getOboardingFormalityClientIndex(int clientId) 
    {
        Collection coll = new LinkedList();
        if (clientId > 0) 
        {
            String query = ("SELECT i_formalityid , s_name FROM t_formality WHERE i_clientid =?  AND i_status =1 ORDER BY s_name").intern();
            coll.add(new OnboardingInfo(-1, "Select Template"));
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                logger.info("getOboardingFormalityClientIndex :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int refId;
                String refName;
                while (rs.next()) 
                {
                    refId = rs.getInt(1);
                    refName = rs.getString(2) != null && !rs.getString(2).equals("") ? rs.getString(2) : "";
                    coll.add(new OnboardingInfo(refId, refName));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new OnboardingInfo(-1, "Select Template"));
        }
        return coll;
    }
    
    public OnboardingInfo getformalityFile(int candidateId, int templateId)
    {
        OnboardingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_onboardingfileid, s_file, i_templateid, i_clientid, i_clientassetid, ");
        sb.append("s_val1, s_val2, s_val3, s_val4, s_val5,  s_val6, s_val7, s_val8, s_val9, s_val10 ");
        sb.append("FROM t_onboardingfiles ");
        sb.append("WHERE i_status = 1 AND i_candidateid = ? AND i_templateid= ? ORDER BY ts_moddate");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            pstmt.setInt(2, templateId);
            print(this, "getformalityFile :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename,  cval1,  cval2,  cval3, cval4,  cval5,  cval6,  cval7,  cval8,  cval9,  cval10;
            int onboardingfileid, template, clientId, assetId;
            while (rs.next()) 
            {
                onboardingfileid = rs.getInt(1);
                filename = rs.getString(2) != null ? rs.getString(2) : "";
                template = rs.getInt(3);
                clientId = rs.getInt(4);
                assetId = rs.getInt(5);
                cval1 = rs.getString(6) != null ? rs.getString(6) : "";
                cval2 = rs.getString(7) != null ? rs.getString(7) : "";
                cval3 = rs.getString(8) != null ? rs.getString(8) : "";
                cval4 = rs.getString(9) != null ? rs.getString(9) : "";
                cval5 = rs.getString(10) != null ? rs.getString(10) : "";
                cval6 = rs.getString(11) != null ? rs.getString(11) : "";
                cval7 = rs.getString(12) != null ? rs.getString(12) : "";
                cval8 = rs.getString(13) != null ? rs.getString(13) : "";
                cval9 = rs.getString(14) != null ? rs.getString(14) : "";
                cval10 = rs.getString(15) != null ? rs.getString(15) : "";
                
                info = new OnboardingInfo(onboardingfileid, filename, template, clientId, assetId, cval1, cval2, cval3, cval4, cval5, cval6, cval7, cval8, cval9, cval10);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public OnboardingInfo getformalityFileById(int onboardingfileId)
    {
        OnboardingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_onboardingfileid, s_file, i_templateid, i_clientid, i_clientassetid, ");
        sb.append("s_val1, s_val2, s_val3, s_val4, s_val5,  s_val6, s_val7, s_val8, s_val9, s_val10 ");
        sb.append("FROM t_onboardingfiles ");
        sb.append("WHERE i_status = 1 AND i_onboardingfileid = ?");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, onboardingfileId);
            print(this, "getformalityFileById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename,  cval1,  cval2,  cval3, cval4,  cval5,  cval6,  cval7,  cval8,  cval9,  cval10;
            int onboardingfileid, template, clientId, assetId;
            while (rs.next()) 
            {
                onboardingfileid = rs.getInt(1);
                filename = rs.getString(2) != null ? rs.getString(2) : "";
                template = rs.getInt(3);
                clientId = rs.getInt(4);
                assetId = rs.getInt(5);
                cval1 = rs.getString(6) != null ? rs.getString(6) : "";
                cval2 = rs.getString(7) != null ? rs.getString(7) : "";
                cval3 = rs.getString(8) != null ? rs.getString(8) : "";
                cval4 = rs.getString(9) != null ? rs.getString(9) : "";
                cval5 = rs.getString(10) != null ? rs.getString(10) : "";
                cval6 = rs.getString(11) != null ? rs.getString(11) : "";
                cval7 = rs.getString(12) != null ? rs.getString(12) : "";
                cval8 = rs.getString(13) != null ? rs.getString(13) : "";
                cval9 = rs.getString(14) != null ? rs.getString(14) : "";
                cval10 = rs.getString(15) != null ? rs.getString(15) : "";
                
                info = new OnboardingInfo(onboardingfileid, filename, template, clientId, assetId, cval1, cval2, cval3, cval4, cval5, cval6, cval7, cval8, cval9, cval10);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    //common method
    public OnboardingInfo getformalityDetail(int candidateId, String pdffilename, int uId,
            int templateId, int clientId, int clientassetId, String cval1,String cval2, String cval3, 
            String cval4, String cval5, String cval6, String cval7,String cval8, String cval9, String cval10)
    {
        OnboardingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_onboardingfileid, s_file ");
        sb.append("FROM t_onboardingfiles ");
        sb.append("WHERE i_status = 1 AND i_candidateid = ? AND i_templateid= ? ");
        String query = sb.toString();
        sb.setLength(0);
        int cc =0;
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            pstmt.setInt(2, templateId);
            print(this, "getformalityDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename = "";
            int onboardingfileId=0;
            while (rs.next()) 
            {
                onboardingfileId = rs.getInt(1);
                filename = rs.getString(2) != null ? rs.getString(2) : "";                
                info = new OnboardingInfo(onboardingfileId, filename);
            }
            rs.close();
            
            if(onboardingfileId > 0)
            {
                if (!filename.equals("")) {
                    File f = new File(getMainPath("add_onboardingtp") + filename);
                    if (f.exists()) {
                        f.delete();
                    }
                }
                sb.append("UPDATE t_onboardingfiles SET ");
                sb.append("i_templateid = ?, ");
                sb.append("s_file = ?, ");
                sb.append("i_userid = ?, ");
                sb.append("i_clientid = ?, ");
                sb.append("i_clientassetid = ?, ");
                sb.append("s_val1 = ?, ");
                sb.append("s_val2 = ?, ");
                sb.append("s_val3 = ?, ");
                sb.append("s_val4 = ?, ");
                sb.append("s_val5 = ?, ");
                sb.append("s_val6 = ?, ");
                sb.append("s_val7 = ?, ");
                sb.append("s_val8 = ?, ");
                sb.append("s_val9 = ?, ");
                sb.append("s_val10 = ?, ");
                sb.append(" ts_regdate = ?, ");
                sb.append(" ts_moddate = ? ");
                sb.append("WHERE i_onboardingfileid = ? ");
                query = (sb.toString()).intern();
                sb.setLength(0);

                conn = getConnection();
                pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, onboardingfileId);
                int scc = 0;
                pstmt.setInt(++scc, templateId);
                pstmt.setString(++scc, pdffilename);
                pstmt.setInt(++scc, uId);
                pstmt.setInt(++scc, clientId);
                pstmt.setInt(++scc, clientassetId);
                pstmt.setString(++scc, cval1);
                pstmt.setString(++scc, cval2);
                pstmt.setString(++scc, cval3);
                pstmt.setString(++scc, cval4);
                pstmt.setString(++scc, cval5);
                pstmt.setString(++scc, cval6);
                pstmt.setString(++scc, cval7);
                pstmt.setString(++scc, cval8);
                pstmt.setString(++scc, cval9);
                pstmt.setString(++scc, cval10);
                pstmt.setString(++scc, currDate1());
                pstmt.setString(++scc, currDate1());
                pstmt.setInt(++scc, onboardingfileId);
                print(this, "updatePdffile :: " + pstmt.toString());
                cc =pstmt.executeUpdate();
                rs = pstmt.getGeneratedKeys();
            }else
            {
                sb.append("INSERT INTO t_onboardingfiles ");
                sb.append("(i_templateid, s_file, i_candidateid, i_status, i_clientid, ");
                sb.append("i_clientassetid, s_val1, s_val2, s_val3, s_val4, s_val5, ");
                sb.append(" s_val6, s_val7, s_val8, s_val9, s_val10, i_userid, ts_regdate, ts_moddate ) ");
                sb.append("VALUES (?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?)");//19
                query = (sb.toString()).intern();
                sb.setLength(0);

                conn = getConnection();
                pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int scc = 0;
                pstmt.setInt(++scc, templateId);
                pstmt.setString(++scc, pdffilename);
                pstmt.setInt(++scc, candidateId);
                pstmt.setInt(++scc, 1);
                pstmt.setInt(++scc, clientId);
                pstmt.setInt(++scc, clientassetId);    
                pstmt.setString(++scc, cval1);
                pstmt.setString(++scc, cval2);
                pstmt.setString(++scc, cval3);
                pstmt.setString(++scc, cval4);
                pstmt.setString(++scc, cval5);
                pstmt.setString(++scc, cval6);
                pstmt.setString(++scc, cval7);
                pstmt.setString(++scc, cval8);
                pstmt.setString(++scc, cval9);
                pstmt.setString(++scc, cval10);
                pstmt.setInt(++scc, uId);
                pstmt.setString(++scc, currDate1());
                pstmt.setString(++scc, currDate1());
                print(this, "createPdffile :: " + pstmt.toString());
                cc = pstmt.executeUpdate();
                rs = pstmt.getGeneratedKeys();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public ArrayList getPdfIdList(int candidateId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_onboardingfiles.i_onboardingfileid, t_onboardingfiles.s_file, t_formality.s_name, t_formality.i_temptype ");
        sb.append("FROM t_onboardingfiles ");
        sb.append("LEFT JOIN t_formality ON (t_formality.i_formalityid = t_onboardingfiles.i_templateid) ");
        sb.append("WHERE t_onboardingfiles.i_status = 1  AND i_candidateid = ? ORDER BY t_onboardingfiles.ts_regdate ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info(" getPdfIdList:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String file, name;
            int Id, temptype;
            while (rs.next()) 
            {
                Id = rs.getInt(1);
                file = rs.getString(2) != null ? rs.getString(2) : "";
                name = rs.getString(3) != null ? rs.getString(3) : "";
                temptype = rs.getInt(4);
                list.add(new OnboardingInfo(Id, file, name,temptype));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public int deletePdfFile(int onboardingfileId) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM t_onboardingfiles WHERE i_onboardingfileid = ?");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, onboardingfileId);
            print(this, "deletePdfFile :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deletePdfFile :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public OnboardingInfo getformalitypdfFile(int onboardingfileId)
    {
        OnboardingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_file, i_templateid FROM t_onboardingfiles WHERE i_onboardingfileid = ?");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, onboardingfileId);
            print(this, "getformalitypdfFile :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename;
            int template;
            while (rs.next()) 
            {
                filename = rs.getString(1) != null ? rs.getString(1) : "";
                template = rs.getInt(2);                
                info = new OnboardingInfo( filename, template);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
}
