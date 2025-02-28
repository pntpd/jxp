package com.web.jxp.cassessment;

import com.web.jxp.base.Base;
import com.web.jxp.base.StatsInfo;
import com.web.jxp.base.Template;
import com.web.jxp.common.Common;
import static com.web.jxp.common.Common.print;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cassessment extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getCassessmentByName(String search, int vstatusIndex, int astatusIndex, int positionIndex, 
        int assettypeIndex, int next, int count) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, DATE_FORMAT(c.d_vdate,'%d-%b-%Y'), c.i_vflag, ");
        sb.append(" c.i_aflag, t1.ct, t_grade.s_name, c.i_pass, t_assettype.s_name FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_assettype on (t_assettype.i_assettypeid = c.i_assettypeid) ");
        sb.append("LEFT JOIN t_position on (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) as ct FROM t_cassessment WHERE i_active =1 GROUP BY i_candidateid) AS t1 ON (c.i_candidateid = t1.i_candidateid) ");
        sb.append("WHERE c.i_status = 1 AND c.i_vflag in (3,4) AND c.i_aflag <= 1  ");
        if (vstatusIndex > 0) {
            sb.append(" AND c.i_vflag = ? ");
        }
        if (astatusIndex > 0 && astatusIndex < 3) {
            sb.append(" AND c.i_aflag = ? AND c.i_pass =1 ");
        }
        if (astatusIndex > 2 && astatusIndex < 5) {
            sb.append(" AND c.i_pass = ? ");
        }
        if (positionIndex > 0) {
            sb.append(" AND c.i_positionid = ? ");
        }
        if(assettypeIndex > 0)
            sb.append(" AND c.i_assettypeid = ? ");
        if (search != null && !search.equals("")) {
            sb.append("AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) like ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) like ? OR c.i_candidateid like ? ");
            sb.append(" OR t_position.s_name like ? OR t_grade.s_name like ? OR t_assettype.s_name like ? OR DATE_FORMAT(c.d_vdate,'%d-%b-%Y') LIKE ?) ");
        }
        sb.append(" ORDER BY c.i_vflag DESC, c.ts_regdate DESC ");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1) FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_assettype on (t_assettype.i_assettypeid = c.i_assettypeid) ");
        sb.append("LEFT JOIN t_position on (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) as ct FROM t_cassessment WHERE i_active = 1 GROUP BY i_candidateid) AS t1 ON (c.i_candidateid = t1.i_candidateid) ");
        sb.append("WHERE c.i_status = 1 AND c.i_vflag in (3,4) AND c.i_aflag <= 1  ");
        if (vstatusIndex > 0) {
            sb.append(" and c.i_vflag = ? ");
        }
        if (astatusIndex > 0 && astatusIndex < 3) {
            sb.append(" AND c.i_aflag = ? AND c.i_pass =1 ");
        }
        if (astatusIndex > 2 && astatusIndex < 5) {
            sb.append(" AND c.i_pass = ? ");
        }
        if (positionIndex > 0) {
            sb.append(" and c.i_positionid = ? ");
        }
        if(assettypeIndex > 0)
            sb.append(" AND c.i_assettypeid = ? ");
        if (search != null && !search.equals("")) {
            sb.append("AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) like ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) like ? OR c.i_candidateid like ? ");
            sb.append(" OR t_position.s_name like ? OR t_grade.s_name like ? OR t_assettype.s_name like ? OR DATE_FORMAT(c.d_vdate,'%d-%b-%Y') LIKE ?) ");
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (vstatusIndex > 0) {
                pstmt.setInt(++scc, vstatusIndex);
            }
            if (astatusIndex > 0 && astatusIndex < 3) {
                pstmt.setInt(++scc, (astatusIndex - 1));
            }
            if (astatusIndex > 2 && astatusIndex < 5) {
                pstmt.setInt(++scc, (astatusIndex - 1));
            }
            if (positionIndex > 0) {
                pstmt.setInt(++scc, positionIndex);
            }
            if(assettypeIndex > 0)
                pstmt.setInt(++scc, assettypeIndex);
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getCassessmentByName :: " + pstmt.toString());

            rs = pstmt.executeQuery();
            String name, position, Ids, vflagstatus, aflagstatus, vdate, scheduled, grade, assettypeName;
            int cassessmentId;
            while (rs.next()) 
            {
                cassessmentId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                vdate = rs.getString(4) != null ? rs.getString(4) : "";
                vflagstatus = getStbyId(rs.getInt(5));
                aflagstatus = getAssbyId(rs.getInt(6), rs.getInt(9));
                scheduled = rs.getInt(7) > 0 ? changeNum(rs.getInt(7), 2) : "";
                grade = rs.getString(8) != null ? rs.getString(8) : "";
                assettypeName = rs.getString(10) != null ? rs.getString(10) : "";
                if (!grade.equals("")) 
                {
                    position += " - " + grade;
                }
                Ids = changeNum(cassessmentId, 6);
                list.add(new CassessmentInfo(cassessmentId, Ids, name, position, vdate, vflagstatus, 
                    aflagstatus, scheduled, 0, assettypeName));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (vstatusIndex > 0) {
                pstmt.setInt(++scc, vstatusIndex);
            }
            if (astatusIndex > 0 && astatusIndex < 3) {
                pstmt.setInt(++scc, (astatusIndex - 1));
            }
            if (astatusIndex > 2 && astatusIndex < 5) {
                pstmt.setInt(++scc, (astatusIndex - 1));
            }
            if (positionIndex > 0) {
                pstmt.setInt(++scc, positionIndex);
            }
            if(assettypeIndex > 0)
                pstmt.setInt(++scc, assettypeIndex);
            if (search != null && !search.equals("")) 
            {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                cassessmentId = rs.getInt(1);
                list.add(new CassessmentInfo(cassessmentId, "", "", "", "", "", "", "", 0, ""));
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
        CassessmentInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (CassessmentInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map;
            if (colId.equals("1")) {
                map = sortById(record, tp);
            } else {
                map = sortByName(record, tp);
            }
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            CassessmentInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (CassessmentInfo) l.get(i);
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

    // to sort search result
    public String getInfoValue(CassessmentInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getCassessmentId() + "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getName() != null ? info.getName() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getPosition() != null ? info.getPosition() : "";
        } else if (i != null && i.equals("4")) {
            infoval = info.getVdate() != null ? info.getVdate() : "";
        } else if (i != null && i.equals("5")) {
            infoval = info.getAssettypeName() != null ? info.getAssettypeName() : "";
        }
        return infoval;
    }

    public int createCassessment(CassessmentInfo info, String loginUsername) 
    {
        int cc = 0, attemptcount = 0;
        try
        {
            conn = getConnection();
            if (info.getAttemptCount() > 0) {
                attemptcount = info.getAttemptCount();
            } else {
                String selqery = "SELECT i_attempt FROM t_cassessmenth WHERE i_candidateid =? AND i_paid =? ORDER BY ts_regdate DESC";
                pstmt = conn.prepareStatement(selqery);
                pstmt.setInt(1, (info.getCandidateId()));
                pstmt.setInt(2, (info.getpAssessmentId()));
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    attemptcount = rs.getInt(1);
                }
                rs.close();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_cassessment ");
            sb.append("(i_candidateid, i_paid, i_assessorid, s_linkloc, s_mode, i_coordinatorid , i_timezoneid1, i_timezoneid2, i_status, d_datec, ");
            sb.append("ts_regdate, ts_moddate, i_duration, d_date, i_attempt) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, (info.getCandidateId()));
            pstmt.setInt(++scc, (info.getpAssessmentId()));
            pstmt.setInt(++scc, (info.getAssessorId()));
            pstmt.setString(++scc, (info.getLinkloc()));
            pstmt.setString(++scc, (info.getMode()));
            pstmt.setInt(++scc, (info.getUserId()));
            pstmt.setInt(++scc, (info.getTz1()));
            pstmt.setInt(++scc, (info.getTz2()));
            pstmt.setInt(++scc, (info.getStatus()));
            pstmt.setString(++scc, changeDateTime(info.getDatec()));
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, (info.getDuration()));
            pstmt.setString(++scc, changeDateTime(info.getDate()));
            pstmt.setInt(++scc, attemptcount);
            print(this, "insert cassessment :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                cc = rs.getInt(1);
            }
            if (cc > 0) {
                query = ("UPDATE t_candidate SET i_aflag =1, ts_moddate =? WHERE i_candidateid =?");
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, currDate1());
                pstmt.setInt(2, info.getCandidateId());
                pstmt.executeUpdate();
            }
            //sendmailcassementdata(cc, loginUsername);
        } catch (Exception exception) {
            print(this, "create cassessment:: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int updateCassessment(CassessmentInfo info, String loginUsername) 
    {
        StringBuilder sb = new StringBuilder();
        int cc = 0;
        try 
        {
            conn = getConnection();
            sb.append("UPDATE t_cassessment SET i_candidateid =?, i_paid =?, i_assessorid =?, s_linkloc =?, s_mode =?, i_coordinatorid =?, ");
            sb.append("i_timezoneid1 =?, i_timezoneid2 =?, i_status =?, d_datec =?, ts_moddate =?, i_duration =?, d_date =? WHERE i_cassessmentid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, (info.getCandidateId()));
            pstmt.setInt(++scc, (info.getpAssessmentId()));
            pstmt.setInt(++scc, (info.getAssessorId()));
            pstmt.setString(++scc, (info.getLinkloc()));
            pstmt.setString(++scc, (info.getMode()));
            pstmt.setInt(++scc, (info.getUserId()));
            pstmt.setInt(++scc, (info.getTz1()));
            pstmt.setInt(++scc, (info.getTz2()));
            pstmt.setInt(++scc, (info.getStatus()));
            pstmt.setString(++scc, changeDateTime(info.getDatec()));
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, (info.getDuration()));
            pstmt.setString(++scc, changeDateTime(info.getDate()));
            pstmt.setInt(++scc, (info.getCassessmentId()));
            cc = pstmt.executeUpdate();
            pstmt.close();
            sendmailcassementdata(info.getCassessmentId(), loginUsername);
        } catch (Exception exception) {
            print(this, "updatecassessmentId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int candidateId, int paId) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_cassessmentid FROM t_cassessment WHERE i_candidateid =? AND i_paid =? AND i_active = 1");
        String query = (sb.toString()).intern();
        sb.setLength(0);

        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, paId);
            //print(this,"checkDuplicacy :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "checkDuplicacy :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public void createCassessmentHistory(int CassessmentId, CassessmentInfo info, String type)
    {
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_cassessmenth ");
            sb.append("(i_cassessmentid, i_candidateid, i_paid, i_assessorid, s_linkloc, s_mode, i_coordinatorid , i_timezoneid1, i_timezoneid2, i_status, ");
            sb.append("d_datec, ts_regdate, ts_moddate, i_duration, d_date, s_desc) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, CassessmentId);
            pstmt.setInt(++scc, (info.getCandidateId()));
            pstmt.setInt(++scc, (info.getpAssessmentId()));
            pstmt.setInt(++scc, (info.getAssessorId()));
            pstmt.setString(++scc, (info.getLinkloc()));
            pstmt.setString(++scc, (info.getMode()));
            pstmt.setInt(++scc, (info.getUserId()));
            pstmt.setInt(++scc, (info.getTz1()));
            pstmt.setInt(++scc, (info.getTz2()));
            pstmt.setInt(++scc, (info.getStatus()));
            pstmt.setString(++scc, changeDateTime(info.getDatec()));
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, (info.getDuration()));
            pstmt.setString(++scc, changeDateTime(info.getDate()));
            pstmt.setString(++scc, type);
            print(this, "create createCassessmentHistory :: " + pstmt.toString());
            pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "create createCassessmentHistory:: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public CassessmentInfo viewCassessment(int cassessmentId) 
    {
        CassessmentInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ca.i_cassessmentid, ca.s_linkloc, ca.s_mode, ca.i_duration, DATE_FORMAT(d_datec, '%d-%b-%Y %H:%i:%s'), t_userlogin.s_name, ");
        sb.append("tz1.s_name, tz2.s_name, DATE_FORMAT(ca.d_date, '%d-%b-%Y'), DATE_FORMAT(ca.d_date, '%H:%i'), NOW() < d_date, ca.i_marks, ca.i_status FROM t_cassessment AS ca ");
        sb.append("LEFT JOIN t_userlogin ON (t_userlogin.i_userid = ca.i_assessorid) ");
        sb.append("LEFT JOIN t_timezone AS tz1 ON (tz1.i_timezoneid = ca.i_timezoneid1) ");
        sb.append("LEFT JOIN t_timezone AS tz2 ON (tz2.i_timezoneid = ca.i_timezoneid2) ");
        sb.append("WHERE ca.i_cassessmentid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, cassessmentId);
            logger.log(Level.INFO, "viewCassessment :: {0}", pstmt.toString());

            rs = pstmt.executeQuery();
            String linkloc, mode, date, time, datec, assesor, tz1, tz2;
            int duration, editflag, status;
            double marks;
            while (rs.next()) 
            {
                cassessmentId = rs.getInt(1);
                linkloc = rs.getString(2) != null ? rs.getString(2) : "";
                mode = rs.getString(3) != null ? rs.getString(3) : "";
                duration = rs.getInt(4);
                datec = rs.getString(5) != null ? rs.getString(5) : "";
                assesor = rs.getString(6) != null ? rs.getString(6) : "";
                tz1 = rs.getString(7) != null ? rs.getString(7) : "";
                tz2 = rs.getString(8) != null ? rs.getString(8) : "";
                date = rs.getString(9) != null ? rs.getString(9) : "";
                time = rs.getString(10) != null ? rs.getString(10) : "";
                editflag = rs.getInt(11);
                marks = rs.getDouble(12);
                status = rs.getInt(13);
                info = new CassessmentInfo(cassessmentId, linkloc, mode, duration, datec, assesor, tz1, tz2, date, time, editflag, marks, status);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public CassessmentInfo editCassessment(int cassessmentId) 
    {
        CassessmentInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_candidateid, i_paid, i_assessorid, s_linkloc, s_mode, i_coordinatorid, i_timezoneid1, i_timezoneid2, i_duration, ");
        sb.append("DATE_FORMAT(d_date, '%d-%b-%Y'), DATE_FORMAT(d_date, '%H:%i'), DATE_FORMAT(d_datec, '%d-%b-%Y %H:%i') ");
        sb.append("FROM t_cassessment WHERE i_cassessmentid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, cassessmentId);
            logger.log(Level.INFO, "editCassessment :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String linkloc, mode, date, time, datec;
            int candidateId, paId, assessorId, coordinatorId, tz1, tz2, duration;
            while (rs.next())
            {
                candidateId = rs.getInt(1);
                paId = rs.getInt(2);
                assessorId = rs.getInt(3);
                linkloc = rs.getString(4) != null ? rs.getString(4) : "";
                mode = rs.getString(5) != null ? rs.getString(5) : "";
                coordinatorId = rs.getInt(6);
                tz1 = rs.getInt(7);
                tz2 = rs.getInt(8);
                duration = rs.getInt(9);
                date = rs.getString(10) != null ? rs.getString(10) : "";
                time = rs.getString(11) != null ? rs.getString(11) : "";
                datec = rs.getString(12) != null ? rs.getString(12) : "";
                info = new CassessmentInfo(candidateId, paId, assessorId, linkloc, mode, coordinatorId, tz1, tz2, duration, date, time, datec);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getListForExcel(String search, int vstatusIndex, int astatusIndex, int positionIndex, int assettypeIndex) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, DATE_FORMAT(c.d_vdate,'%d-%b-%Y'), c.i_vflag, ");
        sb.append(" c.i_aflag, t1.ct, t_grade.s_name, c.i_pass, t_assettype.s_name FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_assettype on (t_assettype.i_assettypeid = c.i_assettypeid) ");
        sb.append("LEFT JOIN t_position on (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) as ct FROM t_cassessment WHERE i_active =1 GROUP BY i_candidateid) AS t1 ON (c.i_candidateid = t1.i_candidateid) ");
        sb.append("WHERE c.i_status = 1 AND c.i_vflag in (3,4) AND c.i_aflag <= 1  ");
        if (vstatusIndex > 0) {
            sb.append(" AND c.i_vflag = ? ");
        }
        if (astatusIndex > 0 && astatusIndex < 3) {
            sb.append(" AND c.i_aflag = ? AND c.i_pass =1 ");
        }
        if (astatusIndex > 2 && astatusIndex < 5) {
            sb.append(" AND c.i_pass = ? ");
        }
        if (positionIndex > 0) {
            sb.append(" AND c.i_positionid = ? ");
        }
        if(assettypeIndex > 0)
            sb.append(" AND c.i_assettypeid = ? ");
        if (search != null && !search.equals("")) {
            sb.append("AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) like ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) like ? OR c.i_candidateid like ? ");
            sb.append(" OR t_position.s_name like ? OR t_grade.s_name like ? OR t_assettype.s_name like ? OR DATE_FORMAT(c.d_vdate,'%d-%b-%Y') LIKE ?) ");
        }
        sb.append(" ORDER BY c.i_vflag DESC, c.ts_regdate DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;

            if (vstatusIndex > 0) {
                pstmt.setInt(++scc, vstatusIndex);
            }
            if (astatusIndex > 0 && astatusIndex < 3) {
                pstmt.setInt(++scc, (astatusIndex - 1));
            }
            if (astatusIndex > 2 && astatusIndex < 5) {
                pstmt.setInt(++scc, (astatusIndex - 1));
            }
            if (positionIndex > 0) {
                pstmt.setInt(++scc, positionIndex);
            }
            if(assettypeIndex > 0)
                pstmt.setInt(++scc, assettypeIndex);
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, position, Ids, vflagstatus, aflagstatus, vdate, scheduled, grade, assettypeName;
            int cassessmentId;
            while (rs.next()) 
            {
                cassessmentId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                vdate = rs.getString(4) != null ? rs.getString(4): "";
                vflagstatus = getStbyId(rs.getInt(5));
                aflagstatus = getAssbyId(rs.getInt(6), rs.getInt(9));
                scheduled = rs.getInt(7) > 0 ? changeNum(rs.getInt(7), 2) : "";
                grade = rs.getString(8) != null ? rs.getString(8) : "";
                assettypeName = rs.getString(10) != null ? rs.getString(10) : "";
                if (!grade.equals("")) {
                    position += " - " + grade;
                }
                Ids = changeNum(cassessmentId, 6);
                list.add(new CassessmentInfo(cassessmentId, Ids, name, position, vdate, vflagstatus, 
                    aflagstatus, scheduled, 0, assettypeName));
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

    public String getStbyId(int status) 
    {
        String stval = "";
        switch (status) {
            case 1:
                stval = "Unverified";
                break;
            case 2:
                stval = "Unverified";
                break;
            case 3:
                stval = "Minimum";
                break;
            case 4:
                stval = "Verified";
                break;
            default:
                break;
        }
        return stval;
    }

    public String getAssbyId(int Schedule, int pass) 
    {
        String stval = "";

        if (pass > 1 && pass < 4) {
            if (pass == 2) {
                stval = "Passed";
            } else if (pass == 3) {
                stval = "Failed";
            }
        } else if (Schedule == 0) {
            stval = "Unscheduled";
        } else if (Schedule == 1) {
            stval = "Scheduled";
        }
        return stval;
    }

    public Collection getPositions() 
    {
        Collection list = new LinkedList();
        list.add(new CassessmentInfo(-1, "Select Position-Rank"));
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_position.i_positionid, t_position.s_name, t_grade.s_name, t_assettype.s_name FROM t_position ");
        sb.append("LEFT JOIN t_grade ON (t_position.i_gradeid = t_grade.i_gradeid) ");
        sb.append("LEFT JOIN t_assettype ON (t_assettype.i_assettypeid = t_position.i_assettypeid) ");
        sb.append("WHERE t_position.i_status = 1 ");
        sb.append("ORDER BY t_assettype.s_name, t_position.s_name, t_grade.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int positionId;
            String pname, gname, aname;
            while (rs.next()) 
            {
                positionId = rs.getInt(1);
                pname = rs.getString(2) != null ? rs.getString(2) : "";
                gname = rs.getString(3) != null ? rs.getString(3) : "";
                aname = rs.getString(4) != null ? rs.getString(4) : "";
                if (gname != null && !gname.equals(""))
                    pname += " - " + gname;
                if (aname != null && !aname.equals(""))
                    pname += " - " + aname;
                list.add(new CassessmentInfo(positionId, pname));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getAssessmentList(int candidateId, int positionId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT pa.i_positionassessmentid, t_assessment.i_assessmentid, t_assessment.s_name, pa.i_minscore, pa.i_passingflag, ");
        sb.append("t_cassessment.i_cassessmentid, t_cassessment.i_status, current_date() > DATE_FORMAT(t_cassessment.d_date, '%Y-%m-%d') FROM t_positionassessment AS pa ");
        sb.append("LEFT JOIN t_assessment ON (t_assessment.i_assessmentid = pa.i_assessmentid) ");
        sb.append("LEFT JOIN t_cassessment ON (pa.i_positionassessmentid = t_cassessment.i_paid AND t_cassessment.i_candidateid = ? AND t_cassessment.i_active =1)  ");
        sb.append("WHERE pa.i_positionid = ? AND pa.i_status = 1 ");
        sb.append("ORDER BY t_assessment.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            pstmt.setInt(2, positionId);
            logger.log(Level.INFO, "getAssessmentList :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String assessmentName, Scheduletype, code;
            int assessmentDetailId, assessmentId, minScore, passingFlag, cassessmentId, status, expireflag;
            while (rs.next()) 
            {
                assessmentDetailId = rs.getInt(1);
                assessmentId = rs.getInt(2);
                assessmentName = rs.getString(3) != null ? rs.getString(3) : "";
                minScore = rs.getInt(4);
                passingFlag = rs.getInt(5);
                cassessmentId = rs.getInt(6);
                status = rs.getInt(7);
                expireflag = rs.getInt(8);
                if (expireflag > 0) {
                    Scheduletype = cassessmentId > 0 ? "Expired" : "Unscheduled";
                } else {
                    Scheduletype = cassessmentId > 0 ? "Scheduled" : "Unscheduled";
                }
                code = changeNum(assessmentId, 3);
                if (status == 2) {
                    Scheduletype = "Passed";
                } else if (status == 3) {
                    Scheduletype = "Failed";
                }
                list.add(new CassessmentInfo(assessmentDetailId, assessmentId, code, assessmentName, minScore, passingFlag, Scheduletype, cassessmentId,
                        status, expireflag));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public CassessmentInfo getAssessmentDetailByIdforDetail(int passId, int candidateId)
    {
        CassessmentInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_assessment.i_assessmentid, pa.i_minscore, pa.i_passingflag, t2.names, t_assessment.s_mode, t_userlogin.s_name, t_assessment.s_name, ");
        sb.append("t_cassessment.i_cassessmentid, t_cassessment.i_status, current_date() > DATE_FORMAT(t_cassessment.d_date, '%Y-%m-%d') FROM t_positionassessment AS pa ");
        sb.append("LEFT JOIN t_assessment ON (t_assessment.i_assessmentid = pa.i_assessmentid) ");
        sb.append("LEFT JOIN (SELECT t_assessment.i_assessmentid, GROUP_CONCAT(DISTINCT t1.s_name order by t1.s_name SEPARATOR ', ') as names from t_assessment ");
        sb.append("LEFT JOIN t_assessmentparameter AS t1 ON find_in_set(t1.i_assessmentparameterid, t_assessment.s_parameterids) GROUP BY t_assessment.i_assessmentid) ");
        sb.append("AS t2 ON (t2.i_assessmentid = t_assessment.i_assessmentid) ");
        sb.append("LEFT JOIN t_cassessment ON (t_cassessment.i_paid = pa.i_positionassessmentid AND t_cassessment.i_candidateid = ? ) ");
        sb.append("LEFT JOIN t_userlogin ON (t_userlogin.i_userid = t_cassessment.i_coordinatorid) ");
        sb.append("WHERE pa.i_positionassessmentid = ? AND pa.i_status = 1 ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            pstmt.setInt(2, passId);
            print(this, "getAssessmentDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String parameters, mode, name, passing, assessmentName, Scheduletype;
            int assessmentid = 0, passingflag = 0, minscore = 0, cassessmentId = 0, status = 0, expireflag = 0;
            while (rs.next()) 
            {
                assessmentid = rs.getInt(1);
                minscore = rs.getInt(2);
                passingflag = rs.getInt(3);
                parameters = rs.getString(4) != null ? rs.getString(4): "";
                mode = rs.getString(5) != null ? rs.getString(5): "";
                name = rs.getString(6) != null ? rs.getString(6): "";
                assessmentName = rs.getString(7) != null ? rs.getString(7) : "";
                cassessmentId = rs.getInt(8);
                status = rs.getInt(9);
                expireflag = rs.getInt(10);
                if (cassessmentId > 0) {
                    if (status == 2) {
                        Scheduletype = "Passed";
                    } else if (status == 3) {
                        Scheduletype = "Failed";
                    } else if (expireflag > 0) {
                        Scheduletype = "Expired";
                    } else {
                        Scheduletype = "Scheduled";
                    }
                } else {
                    Scheduletype = "Unscheduled";
                }
                passing = passingflag > 0 ? "yes" : "No";
                info = new CassessmentInfo(assessmentid, minscore, passingflag, parameters, mode, name, passing, assessmentName, Scheduletype);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getAssessmentDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public CassessmentInfo getCassessmentDetailById(int CassessmentId) 
    {
        CassessmentInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ca.s_mode, ca.s_linkloc, ca.i_duration, DATE_FORMAT(ca.d_date, '%d-%b-%Y'), DATE_FORMAT(ca.d_date, '%H:%i'), t_assessment.s_name, ");
        sb.append("t_userlogin.s_name, ca.s_remarks, ca.i_marks, t_assessment.s_parameterids, pa.i_minscore, pa.i_passingflag, pa.i_assessmentid, ");
        sb.append("ca.i_paid, ca.i_assessorid, ca.i_coordinatorid, ca.i_status FROM t_cassessment AS ca ");
        sb.append("LEFT JOIN t_positionassessment AS pa ON (pa.i_positionassessmentid = ca.i_paid ) ");
        sb.append("LEFT JOIN t_assessment ON (t_assessment.i_assessmentid = pa.i_assessmentid) ");
        sb.append("LEFT JOIN t_userlogin ON (t_userlogin.i_userid = ca.i_coordinatorid) ");
        sb.append("WHERE ca.i_cassessmentid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, CassessmentId);
            print(this, "getCassessmentDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String mode, linkloc, date, time, assessmentName, name, remarks, parameterid, Scheduletype = "";
            int duration = 0, minScore, passFlag, assessmentId, paid, assessorId, coordinatorId, status = 0;
            double marks;
            while (rs.next()) 
            {
                mode = rs.getString(1) != null ? rs.getString(1) : "";
                linkloc = rs.getString(2) != null ? rs.getString(2) : "";
                duration = rs.getInt(3);
                date = rs.getString(4) != null ? rs.getString(4) : "";
                time = rs.getString(5) != null ? rs.getString(5) : "";
                assessmentName = rs.getString(6) != null ? rs.getString(6) : "";
                name = rs.getString(7) != null ? rs.getString(7) : "";
                remarks = rs.getString(8) != null ? rs.getString(8) : "";
                marks = rs.getDouble(9);
                parameterid = rs.getString(10) != null ? rs.getString(10) : "";
                minScore = rs.getInt(11);
                passFlag = rs.getInt(12);
                assessmentId = rs.getInt(13);
                paid = rs.getInt(14);
                assessorId = rs.getInt(15);
                coordinatorId = rs.getInt(16);
                status = rs.getInt(17);
                if (status == 2) {
                    Scheduletype = "Passed";
                } else if (status == 3) {
                    Scheduletype = "Failed";
                }
                info = new CassessmentInfo(mode, linkloc, duration, date, time, assessmentName, name, remarks, marks, parameterid, minScore, passFlag,
                        assessmentId, paid, assessorId, coordinatorId, Scheduletype);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getCassessmentDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getAssessmentquestionlist(int assessmentId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_assessmentdetail.i_assessmentdetailid, t_assessmentquestion.s_name, t_assessmentanswertype.s_name, t_assessmentparameter.s_name from t_assessmentdetail ");
        sb.append("left join t_assessmentquestion on (t_assessmentdetail.i_assessmentquestionid = t_assessmentquestion.i_assessmentquestionid) ");
        sb.append("left join t_assessmentanswertype on (t_assessmentanswertype.i_assessmentanswertypeid = t_assessmentquestion.i_assessmentanswertypeid) ");
        sb.append("left join t_assessmentparameter on t_assessmentquestion.i_assessmentparameterid = t_assessmentparameter.i_assessmentparameterid ");
        sb.append("where t_assessmentdetail.i_assessmentid = ? ");
        sb.append("order by t_assessmentdetail.i_status,t_assessmentquestion.i_assessmentparameterid, t_assessmentquestion.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assessmentId);
            logger.log(Level.INFO, "getAssessmentList :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String assessmentquestion, assessmentanswertype, assparameter;
            int assessmentDetailId;
            SortedSet<String> sortedset = new TreeSet<String>();
            while (rs.next()) 
            {
                sortedset.add(rs.getString(4) != null ? rs.getString(4) : "");
                assessmentDetailId = rs.getInt(1);
                assessmentquestion = rs.getString(2) != null ? rs.getString(2) : "";
                assessmentanswertype = rs.getString(3) != null ? rs.getString(3) : "";
                assparameter = rs.getString(4) != null ? rs.getString(4) : "";
                list.add(new CassessmentInfo(assessmentDetailId, assessmentquestion, assessmentanswertype, assparameter));
            }
            list.add(sortedset);

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getListFromList(ArrayList mainlist, String parameterName) 
    {
        ArrayList list = new ArrayList();
        int size = mainlist.size();
        if (size > 0)
        {
            for (int i = 0; i < size; i++) {
                CassessmentInfo info = (CassessmentInfo) mainlist.get(i);
                if (info != null && info.getAssparameter() != null && info.getAssparameter().equals(parameterName)) {
                    list.add(info);
                }
            }
        }
        return list;
    }

    public CassessmentInfo getCandidatePositionDetails(int candidateId)
    {
        CassessmentInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_lastname), c.i_vflag, t_experiencedept.s_name, c.i_positionid, t_position.s_name, ");
        sb.append("t_grade.s_name, c.i_pass FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_position on (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_experiencedept on (t_experiencedept.i_experiencedeptid = c.i_departmentid) ");
        sb.append("LEFT JOIN t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("WHERE c.i_candidateid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            print(this, "getCandidatePositionDetails :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String candidateName, vflagstatus, deptName, positionName, rankName;
            int ccandidateId = 0, positionId = 0, pass = 0;
            while (rs.next()) 
            {
                ccandidateId = rs.getInt(1);
                candidateName = rs.getString(2) != null ? rs.getString(2): "";
                vflagstatus = getStbyId(rs.getInt(3));
                deptName = rs.getString(4) != null ? rs.getString(4): "";
                positionId = rs.getInt(5);
                positionName = rs.getString(6) != null ? rs.getString(6): "";
                rankName = rs.getString(7) != null ? rs.getString(7): "";
                pass = rs.getInt(8);
                info = new CassessmentInfo(ccandidateId, candidateName, vflagstatus, deptName, positionId, positionName, rankName, pass);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getCandidatePositionDetails :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getListFromList(ArrayList tlist, int type) 
    {
        ArrayList list = new ArrayList();
        int size = tlist.size();
        if (type == 1 || type == 2 || type == 3 || type == 4 || type == 5) 
        {
            if (size > 0) {
                for (int i = 0; i < size; i++) 
                {
                    CassessmentInfo info = (CassessmentInfo) tlist.get(i);
                    if (info != null)
                    {
                        if (type == 3 || type == 4) {
                            if (type == 3) {
                                if (info.getStatus() == 2) {
                                    list.add(info);
                                }
                            } else if (type == 4) {
                                if (info.getStatus() == 3) {
                                    list.add(info);
                                }
                            }
                        } else if (type == 5) {
                            if (info.getCassessmentId() > 0 && info.getExpireflag() == 0 && info.getStatus() == 1) {
                                list.add(info);
                            }
                        } else if (type == 1 || type == 2) {
                            if (type == 1) {
                                if (info.getCassessmentId() > 0 && info.getStatus() == 1) {
                                    list.add(info);
                                }
                            } else if (type == 2) {
                                if (info.getCassessmentId() <= 0) {
                                    list.add(info);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            list = tlist;
        }
        return list;
    }

    public String getAssessorList(int assessorId) 
    {
        StringBuilder sb = new StringBuilder();
        String query = ("SELECT i_userid, s_name FROM t_userlogin WHERE i_status = 1 AND i_aflag > 0 ORDER BY s_name ");
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
                if (id == assessorId) {
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
            logger.log(Level.INFO, "getTimezone :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int timezoneId;
            while (rs.next()) 
            {
                timezoneId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                list.add(new CassessmentInfo(timezoneId, name));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public String getStringFromList(ArrayList mainlist, int timezoneId) 
    {
        StringBuilder sb = new StringBuilder();
        int size = mainlist.size();
        sb.append("<option value='-1'>- Select -</option>");
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                CassessmentInfo info = (CassessmentInfo) mainlist.get(i);
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

    public ArrayList getFinalRecordAss(ArrayList l, String colId, int tp) 
    {
        ArrayList list = null;
        HashMap record = null;
        CassessmentInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try 
        {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (CassessmentInfo) l.get(i);
                    record.put(getInfoValueA(info, colId), info);
                }
            }
            Map map;
            if (colId.equals("1") && colId.equals("2")) {
                //%d-%b-%Y %H:%i
                map = sortByDate(record, tp, "dd-MM-yyyy HH:mm");
            } else {
                map = sortByName(record, tp);
            }
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            CassessmentInfo rInfo;
            while (it.hasNext()) 
            {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (CassessmentInfo) l.get(i);
                    String str = getInfoValueA(rInfo, colId);
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

    // to sort search result
    public String getInfoValueA(CassessmentInfo info, String i) 
    {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getDateTime() != null ? info.getDateTime() : "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getDateTime() != null ? info.getDateTime() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getName() != null ? info.getName() : "";
        } else if (i != null && i.equals("4")) {
            infoval = info.getAssessmentName() != null ? info.getAssessmentName() : "";
        } else if (i != null && i.equals("5")) {
            infoval = info.getPosition() != null ? info.getPosition() : "";
        }else if (i != null && i.equals("6")) {
            infoval = info.getAssettypeName() != null ? info.getAssettypeName() : "";
        }
        return infoval;
    }

    public String getAssstatusbyId(int status) 
    {
        String stval = "";
        switch (status) 
        {
            case 0:
                stval = "Unscheduled";
                break;
            case 1:
                stval = "Scheduled";
                break;
            case 2:
                stval = "Passed";
                break;
            case 3:
                stval = "Failed";
                break;
            default:
                break;
        }
        return stval;
    }

    public Collection getAssessors() 
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_userid, s_name FROM t_userlogin where i_status = 1 and i_aflag = 1 order by s_name").intern();
        coll.add(new CassessmentInfo(-1, " Select Assessor "));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new CassessmentInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getPositionAssessments(int assessorId)
    {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_assessment.i_assessmentid, t_assessment.s_name FROM t_cassessment ");
        sb.append("left join t_positionassessment on (t_cassessment.i_paid = t_positionassessment.i_positionassessmentid) ");
        sb.append(" left join t_assessment on (t_positionassessment.i_assessmentid = t_assessment.i_assessmentid) WHERE t_assessment.i_status = 1 AND i_active =1 ");
        if (assessorId > 0) {
            sb.append(" and t_cassessment.i_assessorid = ?  ");
        }
        sb.append("group by  t_assessment.i_assessmentid order by t_assessment.s_name ");
        String query = (sb.toString()).intern();
        coll.add(new CassessmentInfo(-1, " Select Assessment "));
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            if (assessorId > 0) 
            {
                pstmt.setInt(1, assessorId);
            }
            logger.info("getPositionAssessments :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next())
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new CassessmentInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public ArrayList getCassessmentByNameforAssessor(String search, int positionIndex, int assessmentIndex, int assessorIndex, 
            int assessorId, int assettypeIdIndex, int next, int count) 
    {
        ArrayList list = new ArrayList();
        if (assessorId != 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t1.i_cassessmentid, CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname), ");
            sb.append("t_position.s_name, t_grade.s_name, t1.i_status,t1.i_marks,t_userlogin.s_name, DATE_FORMAT(t1.d_date, '%d-%b-%Y'), ");
            sb.append("DATE_FORMAT(t1.d_date, '%H:%i'), DATE_FORMAT(t1.d_date, '%d-%b-%Y %H:%i'), t_positionassessment.i_minscore, t1.i_candidateid, ");
            sb.append("t_assessment.s_name, t_timezone.s_code, t1.i_paid, t_assettype.s_name FROM t_cassessment as t1  ");
            sb.append("LEFT JOIN t_candidate on (t_candidate.i_candidateid = t1.i_candidateid)  ");
            sb.append("LEFT JOIN t_positionassessment on (t1.i_paid = t_positionassessment.i_positionassessmentid) ");
            sb.append("LEFT JOIN t_assessment on (t_positionassessment.i_assessmentid = t_assessment.i_assessmentid) ");
            sb.append("LEFT JOIN t_position on (t_position.i_positionid = t_candidate.i_positionid) ");
            sb.append("LEFT JOIN t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("LEFT JOIN t_assettype on (t_assettype.i_assettypeid = t_candidate.i_assettypeid) ");
            sb.append("LEFT JOIN t_userlogin on (t1.i_assessorid = t_userlogin.i_userid) ");
            sb.append("LEFT JOIN t_timezone on (t_timezone.i_timezoneid = t1.i_timezoneid1) ");
            sb.append("WHERE 0 = 0 AND ((DATE_FORMAT(t1.d_date, '%Y-%m-%d') >= current_date() AND t1.i_status =1) OR (t1.i_status IN (2,3))) AND t1.i_active =1 ");
            if (assessorId > 0) {
                sb.append(" and t1.i_assessorid = ? ");
            }
            if (assessmentIndex > 0) {
                sb.append(" AND t_positionassessment.i_assessmentid = ? ");
            }
            if (assessorIndex > 0) {
                sb.append(" AND t1.i_assessorid = ? ");
            }
            if (positionIndex > 0) {
                sb.append(" AND t_positionassessment.i_positionid = ? ");
            }
            if(assettypeIdIndex > 0)
                sb.append(" AND t_candidate.i_assettypeid = ? ");
            if (search != null && !search.equals("")) {
                sb.append("AND (CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname) like ? OR CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_lastname) like ? OR t_candidate.i_candidateid like ? ");
                sb.append(" OR t_position.s_name like ? OR t_grade.s_name like ? OR t_assettype.s_name like ? OR DATE_FORMAT(t1.d_date, '%d-%b-%Y %H:%i') LIKE ?) ");
            }
            sb.append(" ORDER BY t1.i_status, t1.d_date ");

            String query = (sb.toString()).intern();
            sb.setLength(0);

            sb.append("SELECT COUNT(1) FROM t_cassessment AS t1 ");
            sb.append(" left join t_candidate on (t_candidate.i_candidateid = t1.i_candidateid)  ");
            sb.append("LEFT JOIN t_positionassessment on (t1.i_paid = t_positionassessment.i_positionassessmentid) ");
            sb.append("LEFT JOIN t_assessment on (t_positionassessment.i_assessmentid = t_assessment.i_assessmentid) ");
            sb.append("LEFT JOIN t_position on (t_position.i_positionid = t_candidate.i_positionid) ");
            sb.append("LEFT JOIN t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("LEFT JOIN t_assettype on (t_assettype.i_assettypeid = t_candidate.i_assettypeid) ");
            sb.append("LEFT JOIN t_userlogin on (t1.i_assessorid = t_userlogin.i_userid) ");
            sb.append("LEFT JOIN t_timezone on (t_timezone.i_timezoneid = t1.i_timezoneid1) ");
            sb.append("WHERE 0 = 0 AND ((DATE_FORMAT(t1.d_date, '%Y-%m-%d') >= current_date() AND t1.i_status =1) OR (t1.i_status IN (2,3))) AND t1.i_active =1 ");
            if (assessorId > 0) {
                sb.append(" and t1.i_assessorid = ? ");
            }
            if (assessmentIndex > 0) {
                sb.append(" AND t_positionassessment.i_assessmentid = ? ");
            }
            if (assessorIndex > 0) {
                sb.append(" AND t1.i_assessorid = ? ");
            }
            if (positionIndex > 0) {
                sb.append(" AND t_positionassessment.i_positionid = ? ");
            }
            if(assettypeIdIndex > 0)
                sb.append(" AND t_candidate.i_assettypeid = ? ");
            if (search != null && !search.equals("")) {
                sb.append("AND (CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname) like ? OR CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_lastname) like ? OR t_candidate.i_candidateid like ? ");
                sb.append(" OR t_position.s_name like ? OR t_grade.s_name like ? OR t_assettype.s_name like ? OR DATE_FORMAT(t1.d_date, '%d-%b-%Y %H:%i') LIKE ?) ");
            }
            sb.append(" ORDER BY t1.i_status, t1.d_date ");
            String countquery = (sb.toString()).intern();
            sb.setLength(0);

            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                int scc = 0;

                if (assessorId > 0) {
                    pstmt.setInt(++scc, assessorId);
                }
                if (assessmentIndex > 0) {
                    pstmt.setInt(++scc, assessmentIndex);
                }
                if (assessorIndex > 0) {
                    pstmt.setInt(++scc, (assessorIndex));
                }
                if (positionIndex > 0) {
                    pstmt.setInt(++scc, positionIndex);
                }
                if(assettypeIdIndex > 0)
                    pstmt.setInt(++scc, assettypeIdIndex);
                if (search != null && !search.equals("")) {
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, search);
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, "%" + (search) + "%");
                }
                logger.info("getCassessmentByNameAssessor :: " + pstmt.toString());

                rs = pstmt.executeQuery();
                String name, position, statusValue, grade, assessorName, date, time, dateTime, assessmentName, tzoneCode, assettypeName;
                int cassessmentId, minScore, candidateId, status, paId;
                double marks;
                while (rs.next()) 
                {
                    cassessmentId = rs.getInt(1);
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    position = rs.getString(3) != null ? rs.getString(3) : "";
                    grade = rs.getString(4) != null ? rs.getString(4) : "";
                    status = rs.getInt(5);
                    marks = rs.getDouble(6);
                    assessorName = rs.getString(7) != null ? rs.getString(7) : "";
                    date = rs.getString(8) != null ? rs.getString(8) : "";
                    time = rs.getString(9) != null ? rs.getString(9) : "";
                    dateTime = rs.getString(10) != null ? rs.getString(10) : "";
                    minScore = rs.getInt(11);
                    candidateId = rs.getInt(12);
                    assessmentName = rs.getString(13) != null ? rs.getString(13) : "";
                    tzoneCode = rs.getString(14) != null ? rs.getString(14) : "";                    
                    paId = rs.getInt(15);
                    assettypeName = rs.getString(16) != null ? rs.getString(16) : "";
                    if (!grade.equals("")) {
                        position += " - " + grade;
                    }
                    if (!tzoneCode.equals("")) {
                        time += " " + tzoneCode;
                    }
                    statusValue = getAssstatusbyId(status);
                    list.add(new CassessmentInfo(cassessmentId, name, position, statusValue, marks, assessorName, date, time, dateTime, minScore,
                            candidateId, assessmentName, status, paId, assettypeName));
                }
                rs.close();

                pstmt = conn.prepareStatement(countquery);
                scc = 0;

                if (assessorId > 0) {
                    pstmt.setInt(++scc, assessorId);
                }
                if (assessmentIndex > 0) {
                    pstmt.setInt(++scc, assessmentIndex);
                }
                if (assessorIndex > 0) {
                    pstmt.setInt(++scc, (assessorIndex));
                }
                if (positionIndex > 0) {
                    pstmt.setInt(++scc, positionIndex);
                }
                if(assettypeIdIndex > 0)
                    pstmt.setInt(++scc, assettypeIdIndex);
                if (search != null && !search.equals("")) 
                {
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, search);
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, "%" + (search) + "%");
                }
                logger.info("getCassessmentByNameAssessor :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    cassessmentId = rs.getInt(1);
                    list.add(new CassessmentInfo(cassessmentId, "", "", "", 0, "", "", "", "", 0, 0, "", 0, 0, ""));
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
            return list;
        }
        return new ArrayList();
    }

    public ArrayList getAssessorListForExcel(String search, int positionIndex, int assessmentIndex, int assessorIndex, 
        int assessorId, int assettypeIdIndex)
    {
        ArrayList list = new ArrayList();
        if (assessorId != 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("select t1.i_cassessmentid, CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname),t_position.s_name, t_grade.s_name,");
            sb.append("t1.i_status,t1.i_marks,t_userlogin.s_name, DATE_FORMAT(t1.d_date, '%d-%b-%Y'), DATE_FORMAT(t1.d_date, '%H:%i'), ");
            sb.append(" DATE_FORMAT(t1.d_date, '%d-%b-%Y %H:%i'), t_positionassessment.i_minscore, t1.i_candidateid,t_assessment.s_name, t_timezone.s_code, t_assettype.s_name from t_cassessment as t1  ");
            sb.append(" left join t_candidate on (t_candidate.i_candidateid = t1.i_candidateid)  ");
            sb.append("LEFT JOIN t_positionassessment on (t1.i_paid = t_positionassessment.i_positionassessmentid) ");
            sb.append("LEFT JOIN t_assessment on (t_positionassessment.i_assessmentid = t_assessment.i_assessmentid) ");
            sb.append("LEFT JOIN t_position on (t_position.i_positionid = t_candidate.i_positionid) ");
            sb.append("LEFT JOIN t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("LEFT JOIN t_assettype on (t_assettype.i_assettypeid = t_candidate.i_assettypeid) ");
            sb.append("LEFT JOIN t_userlogin on (t1.i_assessorid = t_userlogin.i_userid) ");
            sb.append("LEFT JOIN t_timezone on (t_timezone.i_timezoneid = t1.i_timezoneid1) ");
            sb.append("WHERE 0 = 0 AND ((DATE_FORMAT(t1.d_date, '%Y-%m-%d') >= current_date() AND t1.i_status =1) OR (t1.i_status IN (2,3))) AND t1.i_active =1 ");
            if (assessorId > 0) {
                sb.append(" and t1.i_assessorid = ? ");
            }
            if (assessmentIndex > 0) {
                sb.append(" AND t_positionassessment.i_assessmentid = ? ");
            }
            if (assessorIndex > 0) {
                sb.append(" AND t1.i_assessorid = ? ");
            }
            if (positionIndex > 0) {
                sb.append(" AND t_positionassessment.i_positionid = ? ");
            }
            if(assettypeIdIndex > 0)
                sb.append(" AND t_candidate.i_assettypeid = ? ");
            if (search != null && !search.equals("")) {
                sb.append("AND (CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname) like ? OR CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_lastname) like ? OR t_candidate.i_candidateid like ? ");
                sb.append(" OR t_position.s_name like ? OR t_grade.s_name like ? OR t_assettype.s_name like ? OR DATE_FORMAT(t1.d_date, '%d-%b-%Y %H:%i') LIKE ?) ");
            }
            sb.append(" ORDER BY t1.i_status, t1.d_date ");

            String query = (sb.toString()).intern();
            sb.setLength(0);
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                int scc = 0;

                if (assessorId > 0) {
                    pstmt.setInt(++scc, assessorId);
                }
                if (assessmentIndex > 0) {
                    pstmt.setInt(++scc, assessmentIndex);
                }
                if (assessorIndex > 0) {
                    pstmt.setInt(++scc, (assessorIndex));
                }
                if (positionIndex > 0) {
                    pstmt.setInt(++scc, positionIndex);
                }
                if(assettypeIdIndex > 0)
                    pstmt.setInt(++scc, assettypeIdIndex);
                if (search != null && !search.equals(""))
                {
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, search);
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, "%" + (search) + "%");
                }
                logger.info("getCassessmentByNameAssessor :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String name, position, statusValue, grade, assessorName, date, time, dateTime, assessmentName, tzoneCode, assettypeName;
                int cassessmentId, minScore, candidateId, status;
                double marks;
                while (rs.next()) 
                {
                    cassessmentId = rs.getInt(1);
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    position = rs.getString(3) != null ? rs.getString(3) : "";
                    grade = rs.getString(4) != null ? rs.getString(4) : "";
                    status = rs.getInt(5);
                    marks = rs.getDouble(6);
                    assessorName = rs.getString(7) != null ? rs.getString(7) : "";
                    date = rs.getString(8) != null ? rs.getString(8) : "";
                    time = rs.getString(9) != null ? rs.getString(9) : "";
                    dateTime = rs.getString(10) != null ? rs.getString(10) : "";
                    minScore = rs.getInt(11);
                    candidateId = rs.getInt(12);
                    assessmentName = rs.getString(13) != null ? rs.getString(13) : "";
                    tzoneCode = rs.getString(14) != null ? rs.getString(14) : "";
                    assettypeName = rs.getString(15) != null ? rs.getString(15) : "";
                    if (!grade.equals("")) {
                        position += " - " + grade;
                    }
                    if (!tzoneCode.equals("")) {
                        time += " " + tzoneCode;
                    }
                    statusValue = getAssstatusbyId(status);
                    list.add(new CassessmentInfo(cassessmentId, name, position, statusValue, marks, assessorName, 
                        date, time, dateTime, minScore, candidateId, assessmentName, status, 0, assettypeName));
                }
                rs.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
            return list;
        }
        return new ArrayList();
    }

    public Collection getPositionsforAssessor(int assessorId) 
    {
        Collection list = new LinkedList();
        list.add(new CassessmentInfo(-1, "Select Position-Rank"));
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_position.i_positionid, t_position.s_name, t_grade.s_name, t_assettype.s_name FROM t_cassessment ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_cassessment.i_candidateid) ");
        sb.append("LEFT JOIN t_position ON (t_candidate.i_positionid = t_position.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_position.i_gradeid = t_grade.i_gradeid) ");
        sb.append("LEFT JOIN t_assettype ON (t_assettype.i_assettypeid = t_candidate.i_assettypeid) ");
        sb.append("WHERE t_position.i_status = 1 AND i_active =1 ");
        if (assessorId > 0) {
            sb.append("AND t_cassessment.i_assessorid = ?  ");
        }
        sb.append("GROUP BY t_assettype.s_name, t_position.s_name, t_grade.s_name ");
        sb.append("ORDER BY t_position.s_name, t_grade.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            if (assessorId > 0) {
                pstmt.setInt(1, assessorId);
            }
            rs = pstmt.executeQuery();
            int positionId;
            String pname, gname, aname;
            while (rs.next()) 
            {
                positionId = rs.getInt(1);
                pname = rs.getString(2) != null ? rs.getString(2) : "";
                gname = rs.getString(3) != null ? rs.getString(3) : "";
                aname = rs.getString(4) != null ? rs.getString(4) : "";
                if (gname != null && !gname.equals(""))
                    pname += " - " + gname;
                if (aname != null && !aname.equals(""))
                    pname += " - " + aname;
                list.add(new CassessmentInfo(positionId, pname));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getAssessmentAssessorList(int cassessmentid) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT pa.i_positionassessmentid, t_assessment.i_assessmentid, t_assessment.s_name, pa.i_minscore, pa.i_passingflag, ca.i_cassessmentid ");
        sb.append("FROM t_cassessment AS ca ");
        sb.append("LEFT JOIN t_positionassessment AS pa ON (pa.i_positionassessmentid = ca.i_paid)  ");
        sb.append("LEFT JOIN t_assessment ON (t_assessment.i_assessmentid = pa.i_assessmentid) ");
        sb.append("WHERE ca.i_status = 1 AND ca.i_cassessmentid =? AND ca.i_active =1 ");
        sb.append("ORDER BY t_assessment.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, cassessmentid);
            logger.log(Level.INFO, "getAssessmentAssessorList :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String assessmentName, Scheduletype, code;
            int assessmentDetailId, assessmentId, minScore, passingFlag, cassessmentId;
            while (rs.next()) 
            {
                assessmentDetailId = rs.getInt(1);
                assessmentId = rs.getInt(2);
                assessmentName = rs.getString(3) != null ? rs.getString(3) : "";
                minScore = rs.getInt(4);
                passingFlag = rs.getInt(5);
                cassessmentId = rs.getInt(6);
                Scheduletype = cassessmentId > 0 ? "Scheduled" : "Unscheduled";
                code = changeNum(assessmentId, 3);
                list.add(new CassessmentInfo(assessmentDetailId, assessmentId, code, assessmentName, minScore, passingFlag, Scheduletype, cassessmentId, 0, 0));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getAssessmentParameterListByParaId(String parameterIds) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_assessmentparameterid, s_name, s_description FROM t_assessmentparameter ");
        sb.append("WHERE i_status = 1 AND i_assessmentparameterid IN (" + parameterIds + ")");
        sb.append("ORDER BY s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            logger.log(Level.INFO, "getAssessmentParameterListByParaId :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String parameterName, description;
            int parameterid;
            while (rs.next()) 
            {
                parameterid = rs.getInt(1);
                parameterName = rs.getString(2) != null ? rs.getString(2) : "";
                description = rs.getString(3) != null ? rs.getString(3) : "";
                list.add(new CassessmentInfo(parameterid, parameterName, description));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getAssessmentquestionlistbyparameterid(int assessmentId, int parameterId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_assessmentquestion.s_name from t_assessmentdetail ");
        sb.append("left join t_assessmentquestion on (t_assessmentdetail.i_assessmentquestionid = t_assessmentquestion.i_assessmentquestionid) ");
        sb.append("left join t_assessmentparameter on t_assessmentquestion.i_assessmentparameterid = t_assessmentparameter.i_assessmentparameterid ");
        sb.append("where t_assessmentdetail.i_assessmentid = ? and t_assessmentquestion.i_assessmentparameterid = ? and t_assessmentdetail.i_status = 1 ");
        sb.append("order by t_assessmentquestion.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assessmentId);
            pstmt.setInt(2, parameterId);
            logger.log(Level.INFO, "getAssessmentquestionlistbyparameterid :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String assessmentquestion;
            while (rs.next())
            {
                assessmentquestion = rs.getString(1) != null ? rs.getString(1) : "";
                list.add(assessmentquestion);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int createAssessorScore(int candidateId, int CassessmentId, int parameterId[], double marks[], double avgMarks, int minScore, int passflag,
            int userId, String remark, int paId, int assessorId, int coordinateId, String permission)
    {
        int cc = 0, status = 0, reattempt = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_assessmentmarks ");
            sb.append("(i_cassessmentid, i_assessmentparameterid, i_marks, i_userid, ts_regdate , ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            String delqery = "DELETE FROM t_assessmentmarks WHERE i_cassessmentid = ?";
            pstmt = conn.prepareStatement(delqery);
            pstmt.setInt(1, CassessmentId);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement(query);
            int size = parameterId.length;
            int scc = 0;
            for (int i = 0; i < size; i++) 
            {
                if (parameterId[i] > 0) 
                {
                    scc = 0;
                    pstmt.setInt(++scc, CassessmentId);
                    pstmt.setInt(++scc, parameterId[i]);
                    pstmt.setDouble(++scc, marks[i]);
                    pstmt.setInt(++scc, userId);
                    pstmt.setString(++scc, currDate1());
                    pstmt.setString(++scc, currDate1());
                    print(this, "createAssessorScore :: " + pstmt.toString());
                    int inserted = pstmt.executeUpdate();
                    if (inserted > 0) {
                        cc++;
                    }
                }
            }

            if (passflag > 0) {
                if (avgMarks >= minScore) {
                    status = 2;
                } else {
                    status = 3;
                }
            } else {
                status = 2;
            }
            String strTemp = "";
            if (status == 2) {
                strTemp = "Passed";
            } else {
                strTemp = "Failed";
            }
            String selqery = "SELECT i_attempt FROM t_cassessment WHERE i_cassessmentid =?";
            pstmt = conn.prepareStatement(selqery);
            pstmt.setInt(1, CassessmentId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                reattempt = rs.getInt(1);
            }
            rs.close();
            if (!permission.equalsIgnoreCase("Y")) {
                reattempt = reattempt + 1;
            }

            String upquery = "UPDATE t_cassessmenth SET i_showflag =0 WHERE i_cassessmentid =? AND i_candidateid =?";
            pstmt = conn.prepareStatement(upquery);
            pstmt.setInt(1, CassessmentId);
            pstmt.setInt(2, candidateId);
            pstmt.executeUpdate();

            String upquery1 = "UPDATE t_cassessment SET i_marks =?, s_remarks =?, i_minmarks =?, i_passingflag =?, i_status =?, i_attempt =? WHERE i_cassessmentid =? ";
            pstmt = conn.prepareStatement(upquery1);
            pstmt.setDouble(1, avgMarks);
            pstmt.setString(2, remark);
            pstmt.setInt(3, minScore);
            pstmt.setInt(4, passflag);
            pstmt.setInt(5, status);
            pstmt.setInt(6, reattempt);
            pstmt.setInt(7, CassessmentId);
            pstmt.executeUpdate();

            String historyquery = "INSERT INTO t_cassessmenth (i_cassessmentid, i_candidateid, s_remarks, i_marks, i_status, i_minmarks, s_desc, ts_regdate, ts_moddate,"
                    + " i_paid, i_assessorid, i_coordinatorid, i_attempt)"
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
            pstmt = conn.prepareStatement(historyquery);
            pstmt.setInt(1, CassessmentId);
            pstmt.setInt(2, candidateId);
            pstmt.setString(3, remark);
            pstmt.setDouble(4, avgMarks);
            pstmt.setInt(5, status);
            pstmt.setInt(6, minScore);
            pstmt.setString(7, strTemp);
            pstmt.setString(8, currDate1());
            pstmt.setString(9, currDate1());
            pstmt.setInt(10, paId);
            pstmt.setInt(11, userId);
            pstmt.setInt(12, coordinateId);
            pstmt.setInt(13, reattempt);
            pstmt.executeUpdate();

            updateStatus(conn, candidateId);
        } catch (Exception exception) {
            print(this, "create createAssessorScore:: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public void updateStatus(Connection conn, int candidateId) 
    {
        int status = 1, pass = 0;
        String query = "SELECT i_status FROM t_cassessment WHERE i_candidateid = ? AND i_active =1 ";
        try 
        {
            conn = getConnection();
            String selqery = "SELECT i_pass FROM t_candidate WHERE i_candidateid =? ";
            pstmt = conn.prepareStatement(selqery);
            pstmt.setInt(1, candidateId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                pass = rs.getInt(1);
            }
            rs.close();

            if (pass != 2) {
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, candidateId);
                logger.info("updateStatus :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int cc = 0, st3 = 0, st1 = 0;
                while (rs.next()) {
                    cc++;
                    int st = rs.getInt(1);
                    if (st == 1) {
                        st1++;
                    }
                    if (st == 3) {
                        st3++;
                    }
                }
                rs.close();
                if (cc > 0 && st1 == 0) {
                    if (st3 > 0) {
                        status = 3;
                    } else {
                        status = 2;
                    }
                    query = "UPDATE t_candidate SET i_pass = ?, ts_moddate = NOW() WHERE i_candidateid = ?";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, status);
                    pstmt.setInt(2, candidateId);
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, pstmt, rs);
        }
    }

    public ArrayList getScoreByAssessmentId(int CassessmentId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT am.i_assessmentparameterid, am.i_marks, ap.s_name FROM t_assessmentmarks AS am ");
        sb.append("LEFT JOIN t_assessmentparameter AS ap ON (ap.i_assessmentparameterid = am.i_assessmentparameterid) ");
        sb.append("WHERE am.i_cassessmentid =? ");
        sb.append("ORDER BY ap.s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, CassessmentId);
            logger.log(Level.INFO, "getScoreByAssessmentId :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String parameterName;
            int parameterid;
            double marks;
            while (rs.next()) 
            {
                parameterid = rs.getInt(1);
                marks = rs.getDouble(2);
                parameterName = rs.getString(3) != null ? rs.getString(3) : "";
                list.add(new CassessmentInfo(parameterid, parameterName, marks));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int getRetakeAssessmentById(int CassessmentId, int candidateId)
    {
        int iattempt = 0;
        try
        {
            conn = getConnection();
            String selqery = "SELECT i_attempt FROM t_cassessment WHERE i_cassessmentid =?";
            pstmt = conn.prepareStatement(selqery);
            pstmt.setInt(1, CassessmentId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                iattempt = rs.getInt(1);
            }
            rs.close();

            String delqery = "DELETE FROM t_cassessment WHERE i_cassessmentid =?";
            pstmt = conn.prepareStatement(delqery);
            pstmt.setInt(1, CassessmentId);
            pstmt.executeUpdate();

            String upqery = "UPDATE t_cassessmenth SET i_showflag =0 WHERE i_cassessmentid =?";
            pstmt = conn.prepareStatement(upqery);
            pstmt.setInt(1, CassessmentId);
            pstmt.executeUpdate();

            String delmarkqery = "DELETE FROM t_assessmentmarks WHERE i_cassessmentid =?";
            pstmt = conn.prepareStatement(delmarkqery);
            pstmt.setInt(1, CassessmentId);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return iattempt;
    }

    public void sendmailcassementdata(int CassessmentId, String loginUsername)
    {
        CassessmentInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select DATE_FORMAT(ca.d_date, '%d %b %Y %H:%i'), DATE_FORMAT(ca.d_datec, '%d %b %Y %H:%i'), ca.s_mode, ca.s_linkloc, t_userlogin.s_name, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), ");
        sb.append("t1.s_code, t2.s_code, t_assessment.s_name, c.s_email, t_userlogin.s_email ");
        sb.append("from t_cassessment as ca left join t_userlogin on (t_userlogin.i_userid = ca.i_assessorid ) ");
        sb.append("left join t_candidate as c on (c.i_candidateid = ca.i_candidateid) ");
        sb.append("left join t_timezone as t1 on (t1.i_timezoneid = ca.i_timezoneid1) ");
        sb.append("left join t_timezone as t2 on (t2.i_timezoneid = ca.i_timezoneid2) ");
        sb.append("left join t_positionassessment on (t_positionassessment.i_positionassessmentid = ca.i_paid)");
        sb.append("left join t_assessment on (t_assessment.i_assessmentid = t_positionassessment.i_assessmentid)");
        sb.append("where ca.i_cassessmentid = ? ");

        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, CassessmentId);
            logger.info("getcassementdata" + pstmt.toString());
            rs = pstmt.executeQuery();
            String assessorDate = "", candidateDate = "", mode, link, userName = "", candidateName = "", assessorCode = "", candidateCode = "", assessmentName = "", cEmail = "", uEmail = "";
            String linkstr = "";
            while (rs.next())
            {
                assessorDate = rs.getString(1) != null ? rs.getString(1): "";
                candidateDate = rs.getString(2) != null ? rs.getString(2): "";
                mode = rs.getString(3) != null ? rs.getString(3): "";
                link = rs.getString(4) != null ? rs.getString(4): "";
                userName = rs.getString(5) != null ? rs.getString(5): "";
                candidateName = rs.getString(6) != null ? rs.getString(6): "";
                assessorCode = rs.getString(7) != null ? rs.getString(7): "";
                candidateCode = rs.getString(8) != null ? rs.getString(8): "";
                assessmentName = rs.getString(9) != null ? rs.getString(9): "";
                cEmail = rs.getString(10) != null ? rs.getString(10): "";
                uEmail = rs.getString(11) != null ? rs.getString(11): "";

                if (link != null && !link.equals("")) {
                    sb.append("<tr style='height: 30px;'>");
                    sb.append("<td style='font-family: Arial; color: #666; font-size: 12px; font-weight: normal;'>");
                    if (mode.equalsIgnoreCase("Online")) {
                        sb.append("Please use the below link to join:");
                    } else {
                        sb.append("Location:");
                    }
                    sb.append("</td>");
                    sb.append("</tr>");
                    sb.append("<tr><td style='height:5px;'></td></tr>");
                    sb.append("<tr>");
                    sb.append("<td style='font-family: Arial; color: #666; font-size: 12px; font-weight: normal;'>");
                    sb.append("<strong>URL: </strong>");
                    sb.append("<a style='color:#00acc1;padding:4px 5px;border-radius: 2px;text-decoration:none;' title='Click here' href='" + link + "'>" + link + "</a>");
                    sb.append("</td>");
                    sb.append("</tr>");
                    linkstr = sb.toString();
                    sb.setLength(0);
                }
                info = new CassessmentInfo(assessorDate, candidateDate, mode, linkstr, userName, candidateName, assessorCode, candidateCode, assessmentName, cEmail, uEmail);
            }

            if (CassessmentId > 0) {

                if (assessorCode != null && !assessorCode.equals("")) {
                    assessorDate += " (" + assessorCode + ")";
                }
                if (candidateCode != null && !candidateCode.equals("")) {
                    candidateDate += " (" + candidateCode + ")";
                }

                String cc[] = new String[0];
                String bcc[] = new String[0];
                String toa[] = new String[1];
                String toc[] = new String[1];
                toa[0] = uEmail;
                toc[0] = cEmail;

                String assessormailbody = getPatchMail(info, 1, "mail_assessor.html");
                String candidatemailbody = getPatchMail(info, 2, "mail_candidate.html");
                String file_maillog = getMainPath("file_maillog");
                java.util.Date nowmail = new java.util.Date();
                String fn_mail = String.valueOf(nowmail.getTime());
                String fn_maila = "as-" + fn_mail;
                String fn_mailc = "ca-" + fn_mail;
                String filePath = createFolder(file_maillog);
                String fnameassessor = writeHTMLFile(assessormailbody, file_maillog + "/" + filePath, fn_maila);
                String fnamecandidate = writeHTMLFile(candidatemailbody, file_maillog + "/" + filePath, fn_mailc);

                StatsInfo sinfoa = postMailAttach(toa, cc, bcc, assessormailbody, assessmentName + " Scheduled for Date " + assessorDate, "", "", -1);
                StatsInfo sinfoc = postMailAttach(toc, cc, bcc, candidatemailbody, assessmentName + " Scheduled for Date " + candidateDate, "", "", -1);
                String from = "";
                if (sinfoa != null) {
                    from = sinfoa.getDdlLabel();
                    createMailLog(5, userName, uEmail, "", "", from, assessmentName + " Date : " + assessorDate, filePath + "/" + fnameassessor, "", 0, loginUsername, 0);
                }
                if (sinfoc != null) {
                    from = sinfoc.getDdlLabel();
                    createMailLog(4, candidateName, cEmail, "", "", from, assessmentName + " Date : " + candidateDate, filePath + "/" + fnamecandidate, "", 0, loginUsername, 0);
                }
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public String getPatchMail(CassessmentInfo info, int toflag, String htmlFile) 
    {
        String mailassessor = "";
        if (toflag == 1) {
            mailassessor = getMainPath("template_path") + htmlFile;
        } else if (toflag == 2) {
            mailassessor = getMainPath("template_path") + htmlFile;
        }
        HashMap hashmap = new HashMap();
        Template template = new Template(mailassessor);
        hashmap.put("LINK", info.getLink());
        hashmap.put("ASSESSMENTNAME", info.getAssessmentName());
        hashmap.put("CANDIDATENAME", info.getCandidateName());
        if (toflag == 1) {
            String assessorDate = info.getAssessorDate();
            if (info.getAssessorCode() != null && !info.getAssessorCode().equals("")) {
                assessorDate += "(" + info.getAssessorCode() + ")";
            }

            hashmap.put("NAME", info.getUserName());
            hashmap.put("DATETIME", assessorDate);
        }
        if (toflag == 2) {
            String candidateDate = info.getCandidateDate();
            if (info.getCandidateCode() != null && !info.getCandidateCode().equals("")) {
                candidateDate += "(" + info.getCandidateCode() + ")";
            }

            hashmap.put("NAME", info.getCandidateName());
            hashmap.put("DATETIME", candidateDate);
        }
        return template.patch(hashmap);
    }
    
    public int createAssessNow(CassessmentInfo info, int uId) 
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            int assessnowid = 0;
            String selqery = "SELECT i_assessnowid FROM t_assessnow WHERE i_candidateid = ? ";
            pstmt = conn.prepareStatement(selqery);
            pstmt.setInt(1, (info.getCandidateId()));
            logger.info("createAssessNow" + pstmt.toString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                assessnowid = rs.getInt(1);
            }
            rs.close();
            if(assessnowid <= 0)
            {
                StringBuilder sb = new StringBuilder();
                sb.append("INSERT INTO t_assessnow ");
                sb.append("(i_candidateid, i_radio1, i_radio2, i_radio3, i_radio4, i_radio5 , i_radio6, i_radio7, i_radio8, i_radio9, i_radio10, i_radio11, i_radio12, i_radio13, i_radio14, ");
                sb.append(" i_radio15, i_radio16, i_radio17, i_radio18, s_cdescription, i_positionid, i_status, i_userid, ts_regdate, ts_moddate) ");
                sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?)");//23
                String query = (sb.toString()).intern();
                sb.setLength(0);

                pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                int scc = 0;
                pstmt.setInt(++scc, (info.getCandidateId()));
                pstmt.setInt(++scc, (info.getRadio1()));
                pstmt.setInt(++scc, (info.getRadio2()));
                pstmt.setInt(++scc, (info.getRadio3()));
                pstmt.setInt(++scc, (info.getRadio4()));
                pstmt.setInt(++scc, (info.getRadio5()));
                pstmt.setInt(++scc, (info.getRadio6()));
                pstmt.setInt(++scc, (info.getRadio7()));
                pstmt.setInt(++scc, (info.getRadio8()));
                pstmt.setInt(++scc, (info.getRadio9()));
                pstmt.setInt(++scc, (info.getRadio10()));
                pstmt.setInt(++scc, (info.getRadio11()));
                pstmt.setInt(++scc, (info.getRadio12()));
                pstmt.setInt(++scc, (info.getRadio13()));
                pstmt.setInt(++scc, (info.getRadio14()));
                pstmt.setInt(++scc, (info.getRadio15()));
                pstmt.setInt(++scc, (info.getRadio16()));
                pstmt.setInt(++scc, (info.getRadio17()));
                pstmt.setInt(++scc, (info.getRadio18()));
                pstmt.setString(++scc, (info.getCdescription()));
                pstmt.setInt(++scc, (info.getPositionId()));
                pstmt.setInt(++scc, (info.getStatus()));
                pstmt.setInt(++scc, (uId));
                pstmt.setString(++scc, currDate1());
                pstmt.setString(++scc, currDate1());
                logger.info("insert createAssessNow" + pstmt.toString());
                pstmt.executeUpdate();
                rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    cc = rs.getInt(1);
                }
                createAssessNowhistory( info, uId, cc);
            }
            else if(assessnowid >0)
            {
                int scc = 0; 
                String query = ("UPDATE t_assessnow SET i_radio1 = ?, i_radio2 = ?, i_radio3 = ?, i_radio4 = ?, i_radio5 = ?, i_radio6 = ?, i_radio7 = ?, i_radio8 = ?, i_radio9 = ?, i_radio10 = ?, i_radio11 = ?, i_radio12 = ?, i_radio13 = ?, i_radio14 = ?, i_radio15 = ?, i_radio16 = ?, i_radio17 = ?, i_radio18 = ?, s_cdescription = ?, i_positionid = ?,  i_status = ?, i_userid = ?, ts_moddate = ? WHERE i_candidateid = ? ");
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(++scc, (info.getRadio1()));
                pstmt.setInt(++scc, (info.getRadio2()));
                pstmt.setInt(++scc, (info.getRadio3()));
                pstmt.setInt(++scc, (info.getRadio4()));
                pstmt.setInt(++scc, (info.getRadio5()));
                pstmt.setInt(++scc, (info.getRadio6()));
                pstmt.setInt(++scc, (info.getRadio7()));
                pstmt.setInt(++scc, (info.getRadio8()));
                pstmt.setInt(++scc, (info.getRadio9()));
                pstmt.setInt(++scc, (info.getRadio10()));
                pstmt.setInt(++scc, (info.getRadio11()));
                pstmt.setInt(++scc, (info.getRadio12()));
                pstmt.setInt(++scc, (info.getRadio13()));
                pstmt.setInt(++scc, (info.getRadio14()));
                pstmt.setInt(++scc, (info.getRadio15()));
                pstmt.setInt(++scc, (info.getRadio16()));
                pstmt.setInt(++scc, (info.getRadio17()));
                pstmt.setInt(++scc, (info.getRadio18()));
                pstmt.setString(++scc, (info.getCdescription()));
                pstmt.setInt(++scc, (info.getPositionId()));
                pstmt.setInt(++scc, (info.getStatus()));
                pstmt.setInt(++scc, (uId));
                pstmt.setString(++scc, currDate1());
                pstmt.setInt(++scc, info.getCandidateId());
                logger.info("UPDATE createAssessNow" + pstmt.toString());
                pstmt.executeUpdate();
                createAssessNowhistory(info, uId, assessnowid);
            }            
            updatePassflag(uId, info.getCandidateId(),info.getRadio18());            
        } catch (Exception exception) {
            print(this, "UPDATE createAssessNow:: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int createAssessNowhistory(CassessmentInfo info, int uId, int Id) 
    {
        int cc = 0;
        try
        {
            conn = getConnection();                
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_assessnowhistory ");
            sb.append("(i_candidateid, i_radio1, i_radio2, i_radio3, i_radio4, i_radio5 , i_radio6, i_radio7, i_radio8, i_radio9, i_radio10, i_radio11, i_radio12, i_radio13, i_radio14, ");
            sb.append(" i_radio15, i_radio16, i_radio17, i_radio18, s_cdescription, i_positionid, i_assessnowid, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?)");//24
            String query = (sb.toString()).intern();
            sb.setLength(0);

            pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, (info.getCandidateId()));
            pstmt.setInt(++scc, (info.getRadio1()));
            pstmt.setInt(++scc, (info.getRadio2()));
            pstmt.setInt(++scc, (info.getRadio3()));
            pstmt.setInt(++scc, (info.getRadio4()));
            pstmt.setInt(++scc, (info.getRadio5()));
            pstmt.setInt(++scc, (info.getRadio6()));
            pstmt.setInt(++scc, (info.getRadio7()));
            pstmt.setInt(++scc, (info.getRadio8()));
            pstmt.setInt(++scc, (info.getRadio9()));
            pstmt.setInt(++scc, (info.getRadio10()));
            pstmt.setInt(++scc, (info.getRadio11()));
            pstmt.setInt(++scc, (info.getRadio12()));
            pstmt.setInt(++scc, (info.getRadio13()));
            pstmt.setInt(++scc, (info.getRadio14()));
            pstmt.setInt(++scc, (info.getRadio15()));
            pstmt.setInt(++scc, (info.getRadio16()));
            pstmt.setInt(++scc, (info.getRadio17()));
            pstmt.setInt(++scc, (info.getRadio18()));
            pstmt.setString(++scc, (info.getCdescription()));
            pstmt.setInt(++scc, (info.getPositionId()));
            pstmt.setInt(++scc, (Id));
            pstmt.setInt(++scc, (info.getStatus()));
            pstmt.setInt(++scc, (uId));
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            logger.info("insert createAssessNowhistory" + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                cc = rs.getInt(1);
            }           
        } catch (Exception exception) {
            print(this, "insert createAssessNowhistory:: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public CassessmentInfo getAssessnowList(int candidateId) 
    {
        CassessmentInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_radio1, i_radio2, i_radio3, i_radio4, i_radio5 , i_radio6, i_radio7, i_radio8, i_radio9, i_radio10, i_radio11, i_radio12, i_radio13, i_radio14, ");
        sb.append(" i_radio15, i_radio16, i_radio17, i_radio18, s_cdescription, i_positionid, i_status ");
        sb.append(" FROM t_assessnow ");
        sb.append("WHERE i_candidateid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.log(Level.INFO, "getAssessnowList :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                int radio1 = rs.getInt(1);
                int radio2 = rs.getInt(2);
                int radio3 = rs.getInt(3);
                int radio4 = rs.getInt(4);
                int radio5 = rs.getInt(5);
                int radio6 = rs.getInt(6);
                int radio7 = rs.getInt(7);
                int radio8 = rs.getInt(8);
                int radio9 = rs.getInt(9);
                int radio10 = rs.getInt(10);
                int radio11 = rs.getInt(11);
                int radio12 = rs.getInt(12);
                int radio13 = rs.getInt(13);
                int radio14 = rs.getInt(14);
                int radio15 = rs.getInt(15);
                int radio16 = rs.getInt(16);
                int radio17 = rs.getInt(17);
                int radio18 = rs.getInt(18);
                String cdescription = rs.getString(19) != null ? rs.getString(19) : "";
                int positionId = rs.getInt(20);
                int status = rs.getInt(21);
                
                info = new CassessmentInfo(candidateId,radio1,radio2, radio3, radio4, radio5, radio6, radio7, radio8, radio9, radio10, radio11, radio12, radio13, radio14
            , radio15, radio16, radio17, radio18, cdescription,status,positionId);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int updatePassflag(int uId, int candidateId, int radio18)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_candidate set ");
            sb.append("i_pass = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_candidateid = ? and i_status = 1 ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, radio18);
            pstmt.setInt(++scc, uId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, candidateId);
            logger.log(Level.INFO, "updatePassflag :: {0}", pstmt.toString());
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"updatePassflag :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
}
