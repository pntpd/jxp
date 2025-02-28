package com.web.jxp.clientlogin;

import com.web.jxp.base.Base;
import static com.web.jxp.base.Base.cipher;
import static com.web.jxp.base.Base.parseCommaDelimString;
import com.web.jxp.base.StatsInfo;
import com.web.jxp.base.Template;
import com.web.jxp.common.Common;
import static com.web.jxp.common.Common.*;
import com.web.jxp.documentexpiry.Documentexpiry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TimeZone;
import java.util.logging.Logger;
import javax.mail.MessagingException;

public class Clientlogin extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    Documentexpiry docexp = new Documentexpiry();

    public ArrayList getClientLoginByName(String search, int next, int count, int positionIndex, int clientIdIndex, int assetIdIndex, int mid) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT jp.i_jobpostid, DATE_FORMAT(jp.ts_regdate,'%d-%b-%Y'), t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name, ");
        sb.append("jp.i_noofopening, t1.ct, jp.i_status, jp.i_clientid FROM t_jobpost AS jp ");
        sb.append("LEFT JOIN t_client ON ( jp.i_clientid = t_client.i_clientid ) ");
        sb.append("LEFT JOIN t_clientasset ON ( jp.i_clientassetid = t_clientasset.i_clientassetid ) ");
        sb.append("LEFT JOIN t_position ON ( jp.i_positionid = t_position.i_positionid ) ");
        sb.append("LEFT JOIN t_grade on (jp.i_gradeid = t_grade.i_gradeid) ");
        sb.append("LEFT JOIN (SELECT i_jobpostid, count(1) AS ct FROM t_shortlist WHERE i_sflag= 3 AND i_oflag =1 GROUP BY i_jobpostid) AS t1 ON (t1.i_jobpostid = jp.i_jobpostid) ");
        sb.append("WHERE jp.i_status =1 AND t_client.i_status = 1 AND t_clientasset.i_status = 1 AND FIND_IN_SET(?, t_client.s_mids) ");        
        if (clientIdIndex > 0) {
            sb.append("AND jp.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND jp.i_clientassetid = ? ");
        }
        if (positionIndex > 0) {
            sb.append("AND jp.i_positionid = ? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("AND (jp.i_jobpostid LIKE ? OR DATE_FORMAT(jp.ts_regdate, '%d-%b-%Y') LIKE ? OR t_clientasset.s_name LIKE ? OR t_grade.s_name LIKE ? ");
            sb.append("OR t_position.s_name LIKE ? OR DATE_FORMAT(jp.ts_targetmobdate, '%d-%b-%Y') LIKE ?)  ");
        }
        sb.append("ORDER BY jp.i_status, jp.ts_regdate ");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("SELECT count(1) FROM t_jobpost AS jp ");
        sb.append("LEFT JOIN t_client ON ( jp.i_clientid = t_client.i_clientid ) ");
        sb.append("LEFT JOIN t_clientasset ON ( jp.i_clientassetid = t_clientasset.i_clientassetid ) ");
        sb.append("LEFT JOIN t_position ON ( jp.i_positionid = t_position.i_positionid ) ");
        sb.append("LEFT JOIN t_grade on (jp.i_gradeid = t_grade.i_gradeid) ");
        sb.append("LEFT JOIN (SELECT i_jobpostid, count(1) AS ct FROM t_shortlist WHERE i_sflag= 3 AND i_oflag =1 GROUP BY i_jobpostid) AS t1 ON (t1.i_jobpostid = jp.i_jobpostid) ");
        sb.append("WHERE jp.i_status =1 AND t_client.i_status = 1 AND t_clientasset.i_status = 1 AND FIND_IN_SET(?, t_client.s_mids) ");
        if (clientIdIndex > 0) {
            sb.append("AND jp.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND jp.i_clientassetid = ? ");
        }
        if (positionIndex > 0) {
            sb.append("AND jp.i_positionid = ? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("AND (jp.i_jobpostid LIKE ? OR DATE_FORMAT(jp.ts_regdate, '%d-%b-%Y') LIKE ? OR t_clientasset.s_name LIKE ? OR t_grade.s_name LIKE ? ");
            sb.append("OR t_position.s_name LIKE ? OR DATE_FORMAT(jp.ts_targetmobdate, '%d-%b-%Y') LIKE ?) ");
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;            
            if (mid > 0) {
                pstmt.setInt(++scc, mid);
            }
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            if (positionIndex > 0) {
                pstmt.setInt(++scc, positionIndex);
            }
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getClientLoginByName :: " + pstmt.toString());
            
            rs = pstmt.executeQuery();
            String date, clientName, positionName, grade, clientAsset, ccStatus = "", mobdate = "";
            int jobpostId, opening, selcount, status, clientId;
            while (rs.next()) {
                jobpostId = rs.getInt(1);
                date = rs.getString(2) != null ? rs.getString(2) : "";
                clientName = rs.getString(3) != null ? rs.getString(3) : "";
                clientAsset = rs.getString(4) != null ? rs.getString(4) : "";
                positionName = rs.getString(5) != null ? rs.getString(5) : "";
                grade = rs.getString(6) != null ? rs.getString(6) : "";
                opening = rs.getInt(7);
                selcount = rs.getInt(8);
                status = rs.getInt(9);
                clientId = rs.getInt(10);
                ccStatus = getccStatusbyId(selcount, opening);
                if (!grade.equals("")) {
                    positionName += " - " + grade;
                }
                if (!clientAsset.equals("")) {
                    clientName += " - " + clientAsset;
                }
                list.add(new ClientloginInfo(jobpostId, date, clientName,  positionName, grade, opening, 
                        selcount, ccStatus, status, clientId));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (mid > 0) {
                pstmt.setInt(++scc, mid);
            }
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            if (positionIndex > 0) {
                pstmt.setInt(++scc, positionIndex);
            }
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            print(this, "getClientLoginByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                jobpostId = rs.getInt(1);
                list.add(new ClientloginInfo(jobpostId, "", "", "", "",   0,0,"", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public String getccStatusbyId(int scount, int opening) {
        String stval = "";

        if (scount <= 0) {
            stval = "Pending";
        } else if (scount > 0 && scount < opening) {
            stval = "In-progress";
        } else if (scount == opening) {
            stval = "Completed";
        }
        return stval;
    }
    
    public Collection getClients(int mid) {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_clientid, s_name FROM t_client WHERE i_status = 1 AND s_mids IN("+mid+") ORDER BY s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new ClientloginInfo(-1, " Select Client"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new ClientloginInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getClientAsset(int clientId) {
        Collection coll = new LinkedList();
        if (clientId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_clientid =? AND i_status =1 ORDER BY s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new ClientloginInfo(-1, " Select Client Asset "));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                System.out.println("getClientAsset :: "+pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new ClientloginInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("getClientAsset :: "+pstmt.toString());
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new ClientloginInfo(-1, " Select Client Asset "));
        }
        return coll;
    }

    public Collection getPostions(int clientassetId) {
        Collection coll = new LinkedList();
        if (clientassetId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_position.i_positionid, t_position.s_name, t_grade.s_name FROM t_clientassetposition ");
            sb.append(" LEFT JOIN t_position ON (t_position.i_positionid = t_clientassetposition.i_positionid) ");
            sb.append(" LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append(" WHERE t_clientassetposition.i_status = 1 AND t_clientassetposition. i_clientassetid = ? ORDER BY t_position.s_name, t_grade.s_name");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new ClientloginInfo(-1, "Select Position | Rank"));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                Common.print(this, "getPostions :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int id;
                String ddlLabel = "", grade, position;
                while (rs.next()) {
                    id = rs.getInt(1);
                    position = rs.getString(2) != null ? rs.getString(2) : "";
                    grade = rs.getString(3) != null ? rs.getString(3) : "";
                    ddlLabel = position;
                    if (!grade.equals("")) {
                        ddlLabel += " | " + grade;
                    }
                    coll.add(new ClientloginInfo(id, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new ClientloginInfo(-1, " Select Position | Rank "));
        }
        return coll;
    }
    
    public ClientloginInfo getClientSelectionByIdforDetail(int jobpostId) {
        ClientloginInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name, t_qualificationtype.s_name, ");
        sb.append("DATE_FORMAT(jp.ts_regdate,'%d-%b-%Y'), jp.d_experiencemin, jp.d_experiencemax, jp.i_noofopening, jp.i_status, t2.ct2, ");
        sb.append("jp.i_clientid, t1.ct FROM t_jobpost AS jp ");
        sb.append("LEFT JOIN t_client ON jp.i_clientid = t_client.i_clientid ");
        sb.append("LEFT JOIN t_clientasset ON jp.i_clientassetid = t_clientasset.i_clientassetid ");
        sb.append("LEFT JOIN t_position ON jp.i_positionid = t_position.i_positionid ");
        sb.append("LEFT JOIN t_grade ON jp.i_gradeid = t_grade.i_gradeid ");
        sb.append("LEFT JOIN t_qualificationtype ON jp.i_qualificationtypeid = t_qualificationtype.i_qualificationtypeid ");
        sb.append("LEFT JOIN (SELECT i_jobpostid, count(1) AS ct2 FROM t_shortlist WHERE i_status IN (2,3) AND i_jobpostid =? GROUP BY i_jobpostid) AS t2 ON (t2.i_jobpostid = jp.i_jobpostid) ");
        sb.append("LEFT JOIN (SELECT i_jobpostid, count(1) AS ct FROM t_shortlist WHERE i_sflag= 4 AND i_oflag =4 GROUP BY i_jobpostid) AS t1 ON (t1.i_jobpostid = jp.i_jobpostid) ");
        sb.append("WHERE jp.i_jobpostid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, jobpostId);
            pstmt.setInt(2, jobpostId);
            print(this, "getClientSelectionByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientname, assetname, positionname, grade, education, poston;
            double experiencemin, experiencemax;
            int noofopening, shortlistcount, status, clientId, selcount;
            while (rs.next()) {
                clientname = rs.getString(1) != null ? rs.getString(1) : "";
                assetname = rs.getString(2) != null ? rs.getString(2) : "";
                positionname = rs.getString(3) != null ? rs.getString(3) : "";
                grade = rs.getString(4) != null ? rs.getString(4) : "";
                education = rs.getString(5) != null ? rs.getString(5) : "";
                poston = rs.getString(6) != null ? rs.getString(6) : "";
                experiencemin = rs.getDouble(7);
                experiencemax = rs.getDouble(8);
                noofopening = rs.getInt(9);
                status = rs.getInt(10);
                shortlistcount = rs.getInt(11);
                clientId = rs.getInt(12);
                selcount = rs.getInt(13);
                if (!grade.equals("")) {
                    positionname += " - " + grade;
                }
                if (!assetname.equals("")) {
                    clientname += " - " + assetname;
                }
                info = new ClientloginInfo(jobpostId, clientname, assetname, positionname, grade, education, poston, experiencemin, experiencemax,
                        noofopening, status, selcount, shortlistcount, clientId);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getClientSelectionByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public ArrayList getShortlistedCandidateListByIDs(int jobpostId, String search) {
        ArrayList list = new ArrayList();
        if (jobpostId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name, t_degree.s_name, ");
            sb.append("t_qualificationtype.s_name, tw1.s_companyname, w.exp, sl.i_shortlistid, sl.i_status, sl.i_sflag, c.s_photofilename, t_userlogin.s_name, ");
            sb.append("DATE_FORMAT(t_interview.d_datec, '%d-%b-%Y'), sl.s_srby, DATE_FORMAT(sl.ts_srdate, '%d-%b-%Y'), t_country.s_name, sl.s_pdffilename, sl.i_oflag, ");
            sb.append("sl.s_offerpdffilename, t_interview.i_iflag, t_interview.i_interviewid, CASE WHEN CURRENT_DATE() >= DATE_FORMAT(t_interview.d_datec, '%Y-%m-%d') THEN '2' ELSE '1' END,  ");
            sb.append("CASE WHEN NOW() >= DATE_FORMAT(t_interview.d_datec, '%Y-%m-%d %H:%i') THEN '1' ELSE '2' END, t_interview.i_avflag, t_interview.i_evflag ");
            sb.append("FROM t_shortlist sl ");
            sb.append("LEFT JOIN t_candidate AS c ON (sl.i_candidateid = c.i_candidateid) ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("LEFT JOIN t_eduqual ON (t_eduqual.i_candidateid = c.i_candidateid AND t_eduqual.i_highestqualification = 1) ");
            sb.append("LEFT JOIN (SELECT tw.i_candidateid, tw.s_companyname FROM (SELECT i_candidateid, s_companyname FROM t_workexperience WHERE i_status = 1 ORDER BY d_workstartdate DESC) AS tw GROUP BY i_candidateid) AS tw1 ON (tw1.i_candidateid = c.i_candidateid) ");
            sb.append("LEFT JOIN t_degree ON (t_degree.i_degreeid = t_eduqual.i_degreeid) ");
            sb.append("LEFT JOIN t_country ON (c.i_countryid = t_country.i_countryid) ");
            sb.append("LEFT JOIN t_interview ON (t_interview.i_shortlistid = sl.i_shortlistid) ");
            sb.append("LEFT JOIN t_userlogin ON( t_userlogin.i_userid = t_interview.i_managerid) ");
            sb.append("LEFT JOIN t_qualificationtype ON ( t_qualificationtype.i_qualificationtypeid = t_eduqual.i_qualificationtypeid ) ");
            sb.append("LEFT JOIN (SELECT i_candidateid, ROUND(SUM(DATEDIFF( IF(d_workenddate IS NULL OR d_workenddate = '0000-00-00', current_date(), d_workenddate), ");
            sb.append("d_workstartdate)) / 365, 1) AS exp FROM t_workexperience WHERE i_status = 1 GROUP BY i_candidateid ) AS w ON (c.i_candidateid = w.i_candidateid) ");
            sb.append("WHERE sl.i_jobpostid =? AND sl.i_sflag = 3 AND sl.i_oflag = 1 AND c.i_progressid > 0 AND sl.i_active = 1 ");            
            if (!search.equals("")) {
                sb.append("AND CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? ");
            }
            sb.append("GROUP BY c.i_candidateid ORDER BY sl.i_sflag, sl.i_oflag, c.s_firstname ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                pstmt.setInt(++scc, jobpostId);                
                if (!search.equals("")) {
                    pstmt.setString(++scc, "%" + search + "%");
                }
                logger.info("getShortlistedCandidateListByIDs :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String name, position, grade, degree, qualification, company, photo, interviewer, int_date, 
                srby, sron, country, pdffilename, offerpdffile;
                int candidateId, shortlistId, status, substatus, sflag, iflag, interviewId, type, type2, avflag, evflag;
                double exp;
                while (rs.next()) 
                {
                    candidateId = rs.getInt(1);
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    position = rs.getString(3) != null ? rs.getString(3) : "";
                    grade = rs.getString(4) != null ? rs.getString(4) : "";
                    degree = rs.getString(5) != null ? rs.getString(5) : "";
                    qualification = rs.getString(6) != null ? rs.getString(6) : "";
                    company = rs.getString(7) != null ? rs.getString(7) : "";
                    exp = rs.getDouble(8);
                    shortlistId = rs.getInt(9);
                    status = rs.getInt(10);
                    sflag = rs.getInt(11);
                    photo = rs.getString(12) != null ? rs.getString(12) : "";
                    interviewer = rs.getString(13) != null ? rs.getString(13) : "";
                    int_date = rs.getString(14) != null ? rs.getString(14) : "";
                    srby = rs.getString(15) != null ? rs.getString(15) : "";
                    sron = rs.getString(16) != null ? rs.getString(16) : "";
                    country = rs.getString(17) != null ? rs.getString(17) : "";
                    pdffilename = rs.getString(18) != null ? rs.getString(18) : "";
                    substatus = rs.getInt(19);
                    offerpdffile = rs.getString(20) != null ? rs.getString(20) : "";
                    iflag = rs.getInt(21);
                    interviewId = rs.getInt(22);
                    type = rs.getInt(23);
                    type2 = rs.getInt(24);
                    avflag = rs.getInt(25);
                    evflag = rs.getInt(26);
                    if (!grade.equals("")) {
                        position += " - " + grade;
                    }
                    if (!degree.equals("")) {
                        qualification += " - " + degree;
                    }
                    list.add(new ClientloginInfo(candidateId, name, position, grade, degree, qualification, company,
                            exp, shortlistId, status, sflag, photo, interviewer, int_date, srby, sron, country, 
                            pdffilename, jobpostId, substatus, offerpdffile, iflag, interviewId, type, type2, avflag, evflag));
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
        
    public ClientloginInfo getManagerAccess(String username, String password, String ip) {
        ClientloginInfo info = null;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_userid, s_name, s_email, i_mflag FROM t_userlogin ");
            sb.append("WHERE t_userlogin.i_status = 1 AND t_userlogin.s_username = ? AND t_userlogin.s_password = ? AND i_mflag = 1");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, cipher((password)));
            print(this, "getManagerAccess :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                int userId = rs.getInt(1);
                String name = rs.getString(2) != null ? rs.getString(2) : "";
                String email = rs.getString(3) != null ? rs.getString(3) : "";
                int ismanager = rs.getInt(4);
                
                info = new ClientloginInfo(userId, name, email, ismanager);
            }
        } catch (Exception exception) {
            print(this, "getManagerAccess :: " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public ClientloginInfo getDetails(int shortlistId) {
        ClientloginInfo info = null;
        StringBuilder sb = new StringBuilder();
        try 
        {            
            sb.append("SELECT t_shortlist.i_candidateid, t_client.s_name, t_client.s_email, t4.emails, t_shortlist.s_pdffilename, ");
            sb.append("CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_lastname), t_jobpost.i_jobpostid, t_position.s_name, t_grade.s_name, ");
            sb.append("DATE_FORMAT(t_shortlist.ts_regdate,'%d-%b-%Y'), t_candidate.s_email, t_client.i_clientid, ");
            sb.append("t_jobpost.i_clientassetid, t6.emails, t_position.i_positionid, t8.emails ");
            sb.append("FROM t_shortlist ");
            sb.append("LEFT JOIN t_jobpost on (t_jobpost.i_jobpostid = t_shortlist.i_jobpostid) ");
            sb.append("LEFT JOIN t_position on (t_jobpost.i_positionid = t_position.i_positionid) ");
            sb.append("LEFT JOIN t_grade on (t_jobpost.i_gradeid = t_grade.i_gradeid) ");
            sb.append("LEFT JOIN t_candidate on (t_candidate.i_candidateid = t_shortlist.i_candidateid) ");
            sb.append("LEFT JOIN t_client on (t_client.i_clientid = t_jobpost.i_clientid) ");
            sb.append("LEFT JOIN (SELECT t_client.i_clientid, GROUP_CONCAT(DISTINCT t3.s_email order by t3.s_email SEPARATOR ', ') as emails from t_client LEFT JOIN t_userlogin as t3 on find_in_set(t3.i_userid, t_client.s_ocsuserids) group by t_client.i_clientid) as t4 on (t4.i_clientid = t_client.i_clientid) ");
            sb.append("LEFT JOIN (SELECT t_client.i_clientid, GROUP_CONCAT(DISTINCT t5.s_email order by t5.s_email SEPARATOR ', ') as emails from t_client LEFT JOIN t_userlogin as t5 on find_in_set(t5.i_userid, t_client.s_mids) group by t_client.i_clientid) as t6 on (t6.i_clientid = t_client.i_clientid) "); 
            sb.append("LEFT JOIN (SELECT t_client.i_clientid, GROUP_CONCAT(DISTINCT t7.s_email order by t7.s_email SEPARATOR ', ') as emails from t_client LEFT JOIN t_userlogin as t7 on find_in_set(t7.i_userid, t_client.s_rids) group by t_client.i_clientid) as t8 on (t8.i_clientid = t_client.i_clientid) "); 
            sb.append("LEFT JOIN t_interview on(t_interview.i_shortlistid = t_shortlist.i_shortlistid) ");
            sb.append("WHERE t_shortlist.i_shortlistid = ? ");
            String query = sb.toString().intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, shortlistId);
            print(this, "getDetails :: " + pstmt.toString());
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
                int clientId = rs.getInt(12);
                int assetId = rs.getInt(13);
                String m_mail = rs.getString(14) != null ? rs.getString(14) : "";
                int positionId = rs.getInt(15);
                String rmail = rs.getString(16) != null ? rs.getString(16) : "";
                if (!grade.equals("")) {
                    position += " - " + grade;
                }
                info = new ClientloginInfo(shortlistId, candidateId, clientname, clientmailId, comailId, 
                        pdffileName, candidateName, jobpostId, position, date, candidatemail, 
                        clientId, assetId, m_mail, positionId, rmail);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getDetails :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public ArrayList getTimezone() 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_timezoneid, s_name FROM t_timezone WHERE i_status =1 GROUP BY s_name ORDER BY s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            print(this, "getTimezone :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int timezoneId;
            while (rs.next()) 
            {
                timezoneId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                list.add(new ClientloginInfo(timezoneId, name));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getTimezoneById(int tId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_timezoneid, s_name FROM t_timezone WHERE i_status =1 GROUP BY s_name ORDER BY s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            print(this, "getTimezone :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int timezoneId;
            while (rs.next()) 
            {
                timezoneId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                list.add(new ClientloginInfo(timezoneId, name));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public String getManagerList(int mId) 
    {
        StringBuilder sb = new StringBuilder();        
        sb.append("SELECT DISTINCT t_userlogin.i_userid, t_userlogin.s_name ");
      	sb.append("FROM t_client ");
        sb.append("LEFT JOIN t_userlogin ON FIND_IN_SET(t_userlogin.i_userid, t_client.s_mids) "); 
        sb.append("WHERE t_userlogin.i_status = 1 AND t_userlogin.i_mflag = 1 ORDER BY s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) 
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2): "";
                if (id == mId) {
                    sb.append("<OPTION VALUE='" + id + "' SELECTED>" + name + "</OPTION>");
                } else {
                    sb.append("<OPTION VALUE='" + id + "'>" + name + "</OPTION>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        String str = sb.toString();
        sb.setLength(0);
        return str;
    }
    
    public String getStringFromList(ArrayList mainlist, int timezoneId) 
    {
        StringBuilder sb = new StringBuilder();
        int size = mainlist.size();
        sb.append("<option value='-1'>- Select -</option>");
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                ClientloginInfo info = (ClientloginInfo) mainlist.get(i);
                if (info != null) {
                    if (info.getDdlValue() == timezoneId) {
                        sb.append("<option value='" + info.getDdlValue() + "' selected>" + (info.getDdlLabel()) + "</option>");
                    } else {
                        sb.append("<option value='" + info.getDdlValue() + "'>" + (info.getDdlLabel()) + "</option>");
                    }
                }
            }
        }
        String str = sb.toString();
        sb.setLength(0);
        return str;
    }
    
    public String changedatetz(String inputdate, String intz, String outtz) 
    {
        String fdate = "";
        try 
        {
            DateFormat formatterIST = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
            formatterIST.setTimeZone(TimeZone.getTimeZone(intz));
            Date date = formatterIST.parse(inputdate);

            DateFormat formatterUTC = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
            formatterUTC.setTimeZone(TimeZone.getTimeZone(outtz));
            fdate = formatterUTC.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fdate;
    }
    
    public int createInterview(ClientloginInfo info) 
    {
        int cc = 0;
        conn = getConnection();            
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_interview ");
            sb.append("(i_candidateid, i_clientassetid, i_managerid, s_linkloc, s_mode, i_timezoneid1, i_timezoneid2, d_datec, ");//8
            sb.append("ts_regdate, ts_moddate, i_duration, d_date, i_shortlistid, i_userid, i_iflag, s_username, i_positionid, i_clientid, i_jobpostid) ");//11
            sb.append("VALUES (?, ?, ?, ?, ?,    ?, ?, ?, ?, ?,    ?, ?, ?, ?, ?,    ?, ?, ?, ?)");//18
            String query = (sb.toString()).intern();
            sb.setLength(0);

            pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, (info.getCandidateId()));
            pstmt.setInt(++scc, (info.getAssetId()));
            pstmt.setInt(++scc, (info.getInterviewerId()));
            pstmt.setString(++scc, (info.getLinkloc()));
            pstmt.setString(++scc, (info.getMode()));
            pstmt.setInt(++scc, (info.getTz1()));
            pstmt.setInt(++scc, (info.getTz2()));
            pstmt.setString(++scc, changeDateTime(info.getDatec()));
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, (info.getDuration()));
            pstmt.setString(++scc, changeDateTime(info.getDate()));
            pstmt.setInt(++scc, (info.getShortlistId()));
            pstmt.setInt(++scc, (info.getUserId()));
            pstmt.setInt(++scc, 1);
            pstmt.setString(++scc, (info.getUsername()));
            pstmt.setInt(++scc, info.getPositionId());
            pstmt.setInt(++scc, info.getClientId());
            pstmt.setInt(++scc, info.getJobpostId());
            print(this, "createInterview :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                cc = rs.getInt(1);
            }
            if(cc > 0)
            {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("INSERT INTO t_interviewh ");
                sb2.append("(i_candidateid, i_clientassetid, i_managerid, s_linkloc, s_mode, i_timezoneid1, i_timezoneid2,  d_datec, ");//8
                sb2.append("ts_regdate, i_duration, d_date, i_shortlistid, i_userid, i_iflag, s_username, i_positionid, i_clientid, i_jobpostid) ");//10
                sb2.append("VALUES (?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,   ?, ?, ?)");//18
                String query2 = (sb2.toString()).intern();
                sb2.setLength(0);
                scc = 0;
                pstmt = conn.prepareStatement(query2, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(++scc, (info.getCandidateId()));
                pstmt.setInt(++scc, (info.getAssetId()));
                pstmt.setInt(++scc, (info.getInterviewerId()));
                pstmt.setString(++scc, (info.getLinkloc()));
                pstmt.setString(++scc, (info.getMode()));
                pstmt.setInt(++scc, (info.getTz1()));
                pstmt.setInt(++scc, (info.getTz2()));
                pstmt.setString(++scc, changeDateTime(info.getDatec()));
                pstmt.setString(++scc, currDate1());
                pstmt.setInt(++scc, (info.getDuration()));
                pstmt.setString(++scc, changeDateTime(info.getDate()));
                pstmt.setInt(++scc, (info.getShortlistId()));
                pstmt.setInt(++scc, (info.getUserId()));
                pstmt.setInt(++scc, 1);
                pstmt.setString(++scc, (info.getUsername()));
                pstmt.setInt(++scc, info.getPositionId());
                pstmt.setInt(++scc, info.getClientId());
                pstmt.setInt(++scc, info.getJobpostId());
                print(this,"Insert into t_interviewh  :: " + pstmt.toString());
                pstmt.executeUpdate();                
            }
        } catch (Exception e) {
            print(this, "createInterview :: " + pstmt.toString());
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public String changeDateTime(String date) 
    {
        String str = "0000-00-00 00:00:00";
        try 
        {
            if (date != null && !date.equals("")) 
            {
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                str = sdf2.format(sdf1.parse(date));
            }
        } catch (Exception e) {
            print(this, "Error in changeDateTime :: " + e.getMessage());
        }
        return str;
    }


    public int sendMailToCandidate(String fromval, String toval, String ccval, String bccval, String subject, String description,
                int interviewId, String username, int uid, int jobpostId) throws MessagingException 
    {        
        int maillogId = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_clientid, i_clientassetid FROM t_interview WHERE i_interviewid = ? ");
        String query = sb.toString();
        sb.setLength(0);      
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, interviewId);
            logger.info("get Client Asset :: " + pstmt.toString());
            ResultSet rs = pstmt.executeQuery();
            int clientId = 0, assetId = 0;
            while (rs.next()) 
            {
                clientId = rs.getInt(1);
                assetId = rs.getInt(2);
                
                String toaddress[] = parseCommaDelimString(toval);
                String ccaddress[] = parseCommaDelimString(ccval);
                String bccaddress[] = parseCommaDelimString(bccval);
                String sdescription = description.replaceAll("\n", "<br/>");
                String mailbody = getpatchbody(sdescription, "interview_availability2.html");

                String file_maillog = getMainPath("file_maillog");
                java.util.Date nowmail = new java.util.Date();
                String fn_mail = "Interview_Invitation-" + String.valueOf(nowmail.getTime());
                String filePath_html = createFolder(file_maillog);
                String fname = writeHTMLFile(mailbody, file_maillog + "/" + filePath_html, fn_mail + ".html");

                StatsInfo sinfo = postMailAttach(toaddress, ccaddress, bccaddress, mailbody, subject, "", "" , -1);
                String from = "";
                if (sinfo != null) {
                    from = sinfo.getDdlLabel();
                }
                Documentexpiry doc = new Documentexpiry();
                int notificationId = doc.createNotification(uid, 2, 2, jobpostId, clientId, assetId, "Interview Invitation Sent", username);
                if(notificationId > 0){
                   maillogId = createMailLogForInterview(conn, 21, username, toval, ccval, "", from, subject, 0, username, filePath_html+ "/" +fname, notificationId);
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return maillogId;
    }
    
    public String getpatchbody(String description, String htmlFile) {
        String adminpage = getMainPath("template_path") + htmlFile;
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("MESSAGE", description);
        return template.patch(hashmap);
    }
    
    public int updateStatusByMail(int interviewId, int userId)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_interview SET ");
            sb.append("i_iflag = 2, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_interviewid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, interviewId);
            print(this,"updateStatusByMail :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"updateStatusByMail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public ClientloginInfo editInterview(int interviewId) 
    {
        ClientloginInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_candidateid, i_shortlistid, i_managerid, s_linkloc, s_mode, i_timezoneid1, ");
        sb.append("i_timezoneid2, i_duration, DATE_FORMAT(d_date, '%d-%b-%Y'), ");
        sb.append("DATE_FORMAT(d_date, '%H:%i'), DATE_FORMAT(d_datec, '%d-%b-%Y %H:%i'), i_clientassetid, i_positionid ");
        sb.append("FROM t_interview WHERE i_interviewid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, interviewId);
            print(this, "editInterview :: "+ pstmt.toString());
            rs = pstmt.executeQuery();
            String linkloc, mode, date, time, datec;
            int candidateId, shortlistId, managerId, tz1, tz2, duration, assetId, positionId;
            while (rs.next())
            {
                candidateId = rs.getInt(1);
                shortlistId = rs.getInt(2);
                managerId = rs.getInt(3);
                linkloc = rs.getString(4) != null ? rs.getString(4) : "";
                mode = rs.getString(5) != null ? rs.getString(5) : "";
                tz1 = rs.getInt(6);
                tz2 = rs.getInt(7);
                duration = rs.getInt(8);
                date = rs.getString(9) != null ? rs.getString(9) : "";
                time = rs.getString(10) != null ? rs.getString(10) : "";
                datec = rs.getString(11) != null ? rs.getString(11) : "";
                assetId = rs.getInt(12);
                positionId = rs.getInt(13);
                
                info = new ClientloginInfo(candidateId, shortlistId, managerId, linkloc, mode, tz1, tz2, 
                        duration, date, time, datec, assetId, positionId);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int rescheduleInterview(ClientloginInfo info, int interviewId)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_interview SET ");
            sb.append("i_managerid =?, s_linkloc =?, s_mode =?, ");
            sb.append("i_timezoneid1 =?, i_timezoneid2 =?, d_datec =?, ts_moddate =?, ");
            sb.append("i_duration =?, d_date =?, i_avflag = 0, i_iflag = 1, s_remarks = '', i_userid =?, ");
            sb.append("i_clientassetid= ?, s_username = ?, i_jobpostid = ? WHERE i_interviewid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, (info.getInterviewerId()));
            pstmt.setString(++scc, (info.getLinkloc()));
            pstmt.setString(++scc, (info.getMode()));
            pstmt.setInt(++scc, (info.getTz1()));
            pstmt.setInt(++scc, (info.getTz2()));
            pstmt.setString(++scc, changeDateTime(info.getDatec()));
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, (info.getDuration()));
            pstmt.setString(++scc, changeDateTime(info.getDate()));
            pstmt.setInt(++scc, (info.getUserId()));
            pstmt.setInt(++scc, (info.getAssetId()));
            pstmt.setString(++scc, (info.getUsername()));
            pstmt.setInt(++scc, (info.getJobpostId()));
            pstmt.setInt(++scc, (interviewId));
            print(this,"rescheduleInterview :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            
            if(cc > 0)
            {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("INSERT INTO t_interviewh ");
                sb2.append("(i_candidateid, i_clientassetid, i_managerid, s_linkloc, s_mode, i_timezoneid1, i_timezoneid2,  d_datec, ");//8
                sb2.append("ts_regdate, i_duration, d_date, i_shortlistid, i_userid, i_iflag, s_username, i_positionid, i_clientid, i_jobpostid) ");//10
                sb2.append("VALUES (?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,   ?, ?,?)");//18
                String query2 = (sb2.toString()).intern();
                sb2.setLength(0);
                scc = 0;
                pstmt = conn.prepareStatement(query2, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(++scc, (info.getCandidateId()));
                pstmt.setInt(++scc, (info.getAssetId()));
                pstmt.setInt(++scc, (info.getInterviewerId()));
                pstmt.setString(++scc, (info.getLinkloc()));
                pstmt.setString(++scc, (info.getMode()));
                pstmt.setInt(++scc, (info.getTz1()));
                pstmt.setInt(++scc, (info.getTz2()));
                pstmt.setString(++scc, changeDateTime(info.getDatec()));
                pstmt.setString(++scc, currDate1());
                pstmt.setInt(++scc, (info.getDuration()));
                pstmt.setString(++scc, changeDateTime(info.getDate()));
                pstmt.setInt(++scc, (info.getShortlistId()));
                pstmt.setInt(++scc, (info.getUserId()));
                pstmt.setInt(++scc, 1);
                pstmt.setString(++scc, (info.getUsername()));
                pstmt.setInt(++scc, info.getPositionId());
                pstmt.setInt(++scc, info.getClientId());
                pstmt.setInt(++scc, info.getJobpostId());
                print(this,"rescheduleInterview t_interviewh  :: " + pstmt.toString());
                pstmt.executeUpdate();
            }
        }
        catch (Exception exception)
        {
            print(this,"rescheduleInterview :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public ClientloginInfo getSchedulDetail(int interviewId) 
    {
        ClientloginInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_userlogin.s_name, t_interview.s_linkloc, t_interview.s_mode, ");
        sb.append("DATE_FORMAT(t_interview.d_datec, '%d-%b-%Y %H:%i:%s'), t_interview.i_candidateid, ");
        sb.append("t_interview.s_evremarks, t_interview.s_remarks, DATE_FORMAT(t_interview.ts_moddate, '%d-%b-%Y') ");
        sb.append("FROM t_interview ");
        sb.append("LEFT JOIN t_userlogin ON (t_userlogin.i_userid  = t_interview.i_managerid) ");
        sb.append("WHERE t_interview.i_interviewid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);        
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, interviewId);
            logger.info("getSchedulDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();            
            String username, link, mode, date, remark1, remark2, date2;
            while (rs.next()) 
            {
                username = rs.getString(1) != null ? rs.getString(1) : "";
                link = rs.getString(2) != null ? rs.getString(2) : "";
                mode = rs.getString(3) != null ? rs.getString(3) : "";
                date = rs.getString(4) != null ? rs.getString(4) : "";
                int candidateId = rs.getInt(5);
                remark1 = rs.getString(6) != null ? rs.getString(6) : "";
                remark2 = rs.getString(7) != null ? rs.getString(7) : "";
                date2 = rs.getString(8) != null ? rs.getString(8) : "";
                
                info = new ClientloginInfo(interviewId, username, link, mode, date, candidateId, remark1, remark2, date2);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int saveEvaluation(int interviewId, int score, int evflag, String remarks, String username, int uId, int candidateId)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_interview SET ");
            sb.append("i_score = ?, s_evremarks =?, i_evflag =?, i_userid =?, s_username = ?, i_iflag = ?, ts_moddate =? WHERE i_interviewid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, score);
            pstmt.setString(++scc, remarks);
            pstmt.setInt(++scc, evflag);            
            pstmt.setInt(++scc, uId);
            pstmt.setString(++scc, username);
            if(evflag == 1)
                pstmt.setInt(++scc, 5);
            else
                pstmt.setInt(++scc, 6);
            pstmt.setString(++scc, currDate1());            
            pstmt.setInt(++scc, interviewId);
            print(this,"saveEvaluation :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            if(cc > 0)
            {
                sendmail(interviewId, username, uId, candidateId);
            }
        }
        catch (Exception exception)
        {
            print(this,"saveEvaluation :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int sendmail(int interviewId, String username, int userId, int candidateId) 
    {
        int sent = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_interview.i_score, t_userlogin.s_name, t_userlogin.s_email, t_interview.s_evremarks, ");
        sb.append("t_interview.i_evflag, t_candidate.s_email, t_client.s_name, t_clientasset.s_name, ");
        sb.append("t_position.s_name, t_grade.s_name, ");//10        
        sb.append("t_interview.s_remarks, t_interview.i_avflag, t_interview.i_clientid, t_interview.i_clientassetid, t_interview.s_linkloc, ");
        sb.append("t_interview.s_mode, t_interview.i_timezoneid1, t_interview.i_timezoneid2, t_interview.d_datec, t_interview.ts_moddate, ");
        sb.append("t_interview.i_duration, t_interview.d_date, t_interview.i_shortlistid, t_interview.i_iflag, t_interview.i_positionid, ");
        sb.append("t_interview.i_managerid, t_interview.i_jobpostid, CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_lastname), ");
        sb.append("DATE_FORMAT(t_interview.ts_moddate, '%D-%b-%y'), t2.emails, t4.emails ");//16
        sb.append("FROM t_interview ");
        sb.append("LEFT JOIN t_userlogin ON (t_userlogin.i_userid = t_interview.i_managerid) ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_interview.i_candidateid) ");
        sb.append("LEFT JOIN t_client ON(t_client.i_clientid = t_interview.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON(t_clientasset.i_clientassetid = t_interview.i_clientassetid) ");
        sb.append("LEFT JOIN t_position on (t_interview.i_positionid = t_position.i_positionid) ");
        sb.append("LEFT JOIN t_grade on (t_position.i_gradeid = t_grade.i_gradeid) ");
        sb.append("LEFT JOIN (SELECT t_client.i_clientid, GROUP_CONCAT(DISTINCT t1.s_email ORDER BY t1.s_email SEPARATOR ', ') AS emails FROM t_client "); 
        sb.append("LEFT JOIN t_userlogin AS t1 ON FIND_IN_SET( t1.i_userid, t_client.s_ocsuserids) GROUP BY t_client.i_clientid) AS t2 ON (t2.i_clientid = t_client.i_clientid) "); 
        sb.append("LEFT JOIN (SELECT t_client.i_clientid, GROUP_CONCAT(DISTINCT t3.s_email ORDER BY t3.s_email SEPARATOR ', ') AS emails FROM t_client ");  
        sb.append("LEFT JOIN t_userlogin AS t3 ON FIND_IN_SET( t3.i_userid, t_client.s_rids) GROUP BY t_client.i_clientid) AS t4 ON (t4.i_clientid = t_client.i_clientid) ");
        sb.append("WHERE t_interview.i_interviewid = ? ");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);           
            pstmt.setInt(1, interviewId);
            String name = "", email = "",  ccval = "", cand_email, client = "", asset = "", position= "", grade= "", 
            candidatename ="", linkloc = "", mode = "", date ="", datec = "",avremarks = "", evremarks = "", 
            modDate ="", resDate ="", str= "", co_mail= "", re_mail= "";
            int avflag = 0, managerId = 0, clientId = 0, assetId = 0, tz1= 0, tz2= 0, duration = 0,
            shortlistId= 0, iflag = 0, positionId= 0, jobpostId = 0, evflag = 0, score = 0;
            print(this, "sendmail :: " + pstmt.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                score = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                email = rs.getString(3) != null ? rs.getString(3) : "";
                evremarks = rs.getString(4) != null ? rs.getString(4) : "";
                evflag = rs.getInt(5);              
                cand_email = rs.getString(6) != null ? rs.getString(6) : "";
                client = rs.getString(7) != null ? rs.getString(7) : "";
                asset = rs.getString(8) != null ? rs.getString(8) : "";
                position = rs.getString(9) != null ? rs.getString(9) : "";
                grade = rs.getString(10) != null ? rs.getString(10) : "";                
                avremarks = rs.getString(11) != null ? rs.getString(11) : "";
                avflag = rs.getInt(12);
                clientId = rs.getInt(13);    
                assetId = rs.getInt(14);    
                linkloc = rs.getString(15) != null ? rs.getString(15) : "";
                mode = rs.getString(16) != null ? rs.getString(16) : "";
                tz1 = rs.getInt(17);    
                tz2 = rs.getInt(18);    
                datec = rs.getString(19) != null ? rs.getString(19) : "";
                modDate = rs.getString(20) != null ? rs.getString(20) : "";
                duration = rs.getInt(21);    
                date = rs.getString(22) != null ? rs.getString(22) : "";
                shortlistId = rs.getInt(23);    
                iflag = rs.getInt(24);    
                positionId = rs.getInt(25); 
                managerId = rs.getInt(26); 
                jobpostId = rs.getInt(27); 
                candidatename = rs.getString(28) != null ? rs.getString(28) : "";
                resDate = rs.getString(29) != null ? rs.getString(29) : "";
                co_mail = rs.getString(30) != null ? rs.getString(30) : "";
                re_mail = rs.getString(31) != null ? rs.getString(31) : "";                
                if(!grade.equals(""))
                {
                    position += " | "+grade;
                }
//                email = "sharad.gaikwad@webelementinc.com";               
//                    
//                cand_email = "sharad.gaikwad@webelementinc.com";
//                ccval = cand_email;
                if(!email.equals(""))
                {
                    String message = "", subject = "";
                    if(evflag == 2)
                    {
                        message = "Dear Candidate, "+"\n\nThank you for interviewing for the "+position+"."+"\nAfter careful consideration, we have decided not to proceed with your application at this time. We appreciate your time and interest. ";
                        subject = "Interview Update - "+client+" | "+ asset+" and "+ position;
                    }else
                    {
                        message = "Dear Recruiter,"+
                                
                        "\n\nWe are pleased to inform you that the client has selected a candidate for the position.\n\n"+
                        
                        "Details:\n"+
                        "Client Name: "+client+" - "+asset+"\n"+
                        "Selected Candidate: "+candidatename+"\n"+
                        "Position: "+position+"\n"+
                        "Score: "+score+"\n"+
                        "Selection Date: "+resDate+"\n\n"+

                        "Please proceed with the next steps as per the recruitment process.";
                        subject = "Candidate Evaluation Completed";
                    }
                    
                    String sdescription = message.replaceAll("\n", "<br/>");
                    String mailbody = "";
                    if(evflag == 2){
                        mailbody = getpatchbody(sdescription, "interview_evaluation.html");
                    }else{                        
                        mailbody = getpatchbody(sdescription, "interview_evaluation.html");
                    }
                    String file_maillog = getMainPath("file_maillog");
                    java.util.Date nowmail = new java.util.Date();
                    String fn_mail = "Interview_Evaluation-"+String.valueOf(nowmail.getTime())+".html";
                    String filePath = createFolder(file_maillog);
                    String fname = writeHTMLFile(mailbody, file_maillog+"/"+filePath, fn_mail);
                    String receipent[] = parseCommaDelimString(re_mail);
                    String cc[] = parseCommaDelimString(co_mail);
                    String from = "";
                    String bcc[] = new String[0];
                    try
                    {
                        StatsInfo sinfo = postMailAttach(receipent, cc, bcc, mailbody, subject, "", "", -1);
                        int flag = 0;
                        if(sinfo != null)
                        {
                            flag = sinfo.getDdlValue();
                            from = sinfo.getDdlLabel();                            
                        }                        
                        Documentexpiry doc = new Documentexpiry();
                        int type =0;
                        if(evflag == 2){
                            type = 6;
                        }else{
                            type = 5;
                        }
                        int notificationId = doc.createNotification(userId, 2, type, userId, clientId, assetId, str, username);
                        if(notificationId > 0){
                           sent = createMailLogForInterview(conn, 20, username, re_mail, co_mail, "", from, subject, userId, username, filePath+ "/" +fname, notificationId);
                        }
                        if(sent > 0)
                        {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("INSERT INTO t_interviewh ");
                            sb2.append("(i_candidateid, i_clientassetid, i_managerid, s_linkloc, s_mode, i_timezoneid1, i_timezoneid2,  d_datec, ");//8
                            sb2.append("ts_regdate, i_duration, d_date, i_shortlistid, i_userid, i_iflag, s_username, i_positionid, i_clientid, i_avflag, s_remarks,  ");//11
                            sb2.append("s_evremarks, i_score, i_evflag, i_jobpostid) ");//4
                            sb2.append("VALUES (?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,   ?, ?, ?,?,?,  ?,?,?)");//23
                            String query2 = (sb2.toString()).intern();
                            sb2.setLength(0);
                            int scc = 0;
                            pstmt = conn.prepareStatement(query2, PreparedStatement.RETURN_GENERATED_KEYS);
                            pstmt.setInt(++scc, (userId));
                            pstmt.setInt(++scc, (assetId));
                            pstmt.setInt(++scc, (managerId));
                            pstmt.setString(++scc, (linkloc));
                            pstmt.setString(++scc, (mode));
                            pstmt.setInt(++scc, (tz1));
                            pstmt.setInt(++scc, (tz2));
                            pstmt.setString(++scc, (datec));
                            pstmt.setString(++scc, modDate);
                            pstmt.setInt(++scc, (duration));
                            pstmt.setString(++scc, (date));
                            pstmt.setInt(++scc, (shortlistId));
                            pstmt.setInt(++scc, (userId));
                            pstmt.setInt(++scc, iflag);
                            pstmt.setString(++scc, (username));
                            pstmt.setInt(++scc, positionId);
                            pstmt.setInt(++scc, clientId);
                            pstmt.setInt(++scc, avflag);
                            pstmt.setString(++scc, (avremarks));
                            pstmt.setString(++scc, (evremarks));
                            pstmt.setInt(++scc, score);
                            pstmt.setInt(++scc, evflag);
                            pstmt.setInt(++scc, jobpostId);
                            print(this,"Insert into t_interviewh for Evaluation :: " + pstmt.toString());
                            pstmt.executeUpdate();
                        }
                    }
                    catch(Exception e)
                    {
                       print(this, "sendmail :: " + e.getMessage());
                    }
                }
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return sent;
    }
    
    public int createMailLogForInterview(Connection conn, int type, String name, String to, String cc, 
            String bcc, String from, String subject, int candidateId, String username, String filename, int notificationId) 
    {
        int count = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_maillog ");
            sb.append("(i_type, s_name, s_to, s_cc, s_bcc, s_from, s_subject, s_filename, ts_regdate, ts_moddate, i_candidateid, s_sendby, i_notificationid) ");
            sb.append("VALUES (?, ?, ?, ?, ?,   ?, ?, ?, ?, ?,   ?, ?, ?)");
            String query = sb.toString().intern();
            sb.setLength(0);
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
            pstmt.setInt(11, candidateId);
            pstmt.setString(12, username);
            pstmt.setInt(13, notificationId);
            
            print(this, "createMailLogForInterview :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                count = rs.getInt(1);
            }            
        } 
        catch (Exception exception) 
        {
            print(this, "createMailLogForInterview :: " + exception.getMessage());
        } 
        finally 
        {
            close(null, pstmt, null);
        }
        return count;
    }
    
    
    public int changeStatusByMail(int shortlistId, int userId)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_interview SET ");
            sb.append("i_iflag = 7, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_shortlistid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, shortlistId);
            print(this,"changeStatusByMail :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"changeStatusByMail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public ClientloginInfo getDetailsById(int interviewId) {
        ClientloginInfo info = null;
        StringBuilder sb = new StringBuilder();
        try 
        {
            sb.append("SELECT t_userlogin.s_email, CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_lastname), t_candidate.s_email, ");
            sb.append("t_interview.s_linkloc, DATE_FORMAT( t_interview.d_datec,'%d-%b-%Y'), ");
            sb.append("DATE_FORMAT(t_interview.d_datec,'%H:%i:%s'), t2.emails, t4.emails, t_interview.i_jobpostid, t_position.s_name, t_grade.s_name ");
            sb.append("FROM t_interview ");
            sb.append("LEFT JOIN t_candidate on (t_candidate.i_candidateid = t_interview.i_candidateid) "); 
            sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_interview.i_clientid) "); 
            sb.append("LEFT JOIN t_userlogin ON(t_userlogin.i_userid = t_interview.i_managerid) ");
            sb.append("LEFT JOIN t_position on (t_interview.i_positionid = t_position.i_positionid) ");
            sb.append("LEFT JOIN t_grade on (t_position.i_gradeid = t_grade.i_gradeid) ");
            sb.append("LEFT JOIN (SELECT t_client.i_clientid, GROUP_CONCAT(DISTINCT t1.s_email ORDER BY t1.s_email SEPARATOR ', ') AS emails FROM t_client "); 
            sb.append("LEFT JOIN t_userlogin AS t1 ON FIND_IN_SET( t1.i_userid, t_client.s_ocsuserids) GROUP BY t_client.i_clientid) AS t2 ON (t2.i_clientid = t_client.i_clientid) "); 
            sb.append("LEFT JOIN (SELECT t_client.i_clientid, GROUP_CONCAT(DISTINCT t3.s_email ORDER BY t3.s_email SEPARATOR ', ') AS emails FROM t_client "); 
            sb.append("LEFT JOIN t_userlogin AS t3 ON FIND_IN_SET( t3.i_userid, t_client.s_rids) GROUP BY t_client.i_clientid) AS t4 ON (t4.i_clientid = t_client.i_clientid) "); 
            sb.append("WHERE t_interview.i_interviewid = ? ");
            String query = sb.toString().intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, interviewId);
            print(this, "getDetailsById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String ma_email = rs.getString(1) != null ? rs.getString(1) : "";
                String name = rs.getString(2) != null ? rs.getString(2) : "";
                String candidatemail = rs.getString(3) != null ? rs.getString(3) : "";
                String linkLoc = rs.getString(4) != null ? rs.getString(4) : "";
                String date = rs.getString(5) != null ? rs.getString(5) : "";
                String time = rs.getString(6) != null ? rs.getString(6) : "";
                String co_email = rs.getString(7) != null ? rs.getString(7) : "";
                String re_email = rs.getString(8) != null ? rs.getString(8) : "";
                int jobpostId = rs.getInt(9);
                String position = rs.getString(10) != null ? rs.getString(10) : "";
                String grade = rs.getString(11) != null ? rs.getString(11) : "";
                if(!grade.equals(""))
                    position += " - "+grade;
                
                info = new ClientloginInfo(interviewId, ma_email, name, candidatemail, linkLoc, date, time, co_email, re_email, jobpostId, position);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getDetailsById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int rejectionByClient(int shortlistId, String username, int uId, int maillogId) {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_shortlist SET ");
            sb.append("i_sflag = 5, ");
            sb.append("s_srby = ?, ");
            sb.append("ts_srdate = ? ");            
            sb.append("WHERE i_shortlistid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, username);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, shortlistId);
            print(this, "rejectionByClient :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            if(cc > 0){
                insertclientselectionhistory(shortlistId, 5, 0, maillogId, uId);
            }

        } catch (Exception exception) {
            print(this, "rejectionByClient :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int sendRejectionMail(String fromval, String toval, String ccval, String bccval, String subject, String description,
                String username, int uid, int jobpostId, int shortlistId) throws MessagingException 
    {        
        int maillogId = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_clientid, i_clientassetid FROM t_jobpost WHERE i_jobpostid = ? ");        
        String query = sb.toString();
        sb.setLength(0);      
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, jobpostId);
            logger.info("get Client Asset :: " + pstmt.toString());
            ResultSet rs = pstmt.executeQuery();
            int clientId = 0, assetId = 0;
            while (rs.next()) 
            {
                clientId = rs.getInt(1);
                assetId = rs.getInt(2);
                
                String toaddress[] = parseCommaDelimString(toval);
                String ccaddress[] = parseCommaDelimString(ccval);
                String bccaddress[] = parseCommaDelimString(bccval);
                String sdescription = description.replaceAll("\n", "<br/>");
                String mailbody = getpatchbody(sdescription, "mail_rejection.html");

                String file_maillog = getMainPath("file_maillog");
                java.util.Date nowmail = new java.util.Date();
                String fn_mail = "Client_Rejection-" + String.valueOf(nowmail.getTime());
                String filePath_html = createFolder(file_maillog);
                String fname = writeHTMLFile(mailbody, file_maillog + "/" + filePath_html, fn_mail + ".html");

                StatsInfo sinfo = postMailAttach(toaddress, ccaddress, bccaddress, mailbody, subject, "", "" , -1);
                String from = "";
                if (sinfo != null) {
                    from = sinfo.getDdlLabel();
                }
                Documentexpiry doc = new Documentexpiry();
                int notificationId = doc.createNotification(uid, 2, 14, jobpostId, clientId, assetId, "Rejection Email", username);
                if(notificationId > 0){
                   maillogId = createMailLogForInterview(conn, 21, username, toval, ccval, "", from, subject, 0, username, filePath_html+ "/" +fname, notificationId);
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return maillogId;
    }
    
    public int insertclientselectionhistory(int shortlistId, int sflag, int companytype, int maillogId, int userId) {
        int id = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_clientselectionh  ");
            sb.append("( i_shortlistid, i_sflag,  i_usertype, i_maillogid, i_userid, ts_regdate) ");
            sb.append("values (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, shortlistId);
            pstmt.setInt(++scc, sflag);
            pstmt.setInt(++scc, companytype);
            pstmt.setInt(++scc, maillogId);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "insertclientselectionhistory :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return id;
    }
}