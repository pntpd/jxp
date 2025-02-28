package com.web.jxp.talentpool;

import com.web.jxp.base.Base;
import static com.web.jxp.base.Base.dateFolder;
import static com.web.jxp.base.Base.decipher;
import static com.web.jxp.base.Base.parseCommaDelimString;
import com.web.jxp.base.StatsInfo;
import com.web.jxp.base.Template;
import com.web.jxp.candidate.CandidateInfo;
import static com.web.jxp.clientselection.Clientselection.currDate01;
import static com.web.jxp.clientselection.Clientselection.currDate02;
import com.web.jxp.common.Common;
import static com.web.jxp.common.Common.print;
import com.web.jxp.onboarding.OnboardingInfo;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TimeZone;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.zefer.pd4ml.PD4Constants;

public class Ddl extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String view_path = getMainPath("view_candidate_file");

    public Collection getClients() {
        Collection coll = new LinkedList();
        String query = ("SELECT i_clientid, s_name FROM t_client WHERE i_status = 1 ORDER BY s_name").intern();
        coll.add(new TalentpoolInfo(-1, "Select Client"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new TalentpoolInfo(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getClientAssets(int clientId) {
        Collection coll = new LinkedList();
        String query = ("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_status = 1 AND i_clientid = ? ORDER BY s_name").intern();
        coll.add(new TalentpoolInfo(-1, "Select Asset"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientId);
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new TalentpoolInfo(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
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
            coll.add(new TalentpoolInfo(-1, "Select Position | Rank"));
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
                    coll.add(new TalentpoolInfo(id, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new TalentpoolInfo(-1, " Select Position | Rank "));
        }
        return coll;
    }

    public int addtocrew(int clietId, int clientassetId, int candidateId, int positionId, int userId, String remarks, String date) {
        int cc = 0;
        if (clietId > 0 && clientassetId > 0 && candidateId > 0) {
            try {
                conn = getConnection();
                String query = "SELECT i_crewrotationid FROM t_crewrotation WHERE i_candidateid = ? AND i_active = 1";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, candidateId);
                logger.info("get crewrotationid:: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int crewrotationId = 0;
                while (rs.next()) {
                    crewrotationId = rs.getInt(1);
                }
                rs.close();
                if (crewrotationId <= 0) {
                    query = "SELECT i_assettypeid FROM t_clientasset WHERE i_clientassetid = ?";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, clientassetId);
                    logger.info("get assettypeid:: " + pstmt.toString());
                    rs = pstmt.executeQuery();
                    int assettypeId = 0;
                    while (rs.next()) {
                        assettypeId = rs.getInt(1);
                    }
                    rs.close();

                    query = "UPDATE t_candidate SET i_status = 1, i_clientid = ?, i_clientassetid = ?, i_assettypeid = ?, i_positionid = ?, i_userid = ?, ts_moddate = ?, i_progressid = 0 WHERE i_candidateid = ?";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, clietId);
                    pstmt.setInt(2, clientassetId);
                    pstmt.setInt(3, assettypeId);
                    pstmt.setInt(4, positionId);
                    pstmt.setInt(5, userId);
                    pstmt.setString(6, currDate1());
                    pstmt.setInt(7, candidateId);
                    print(this, "addtocrew update :: " + pstmt.toString());
                    cc = pstmt.executeUpdate();
                    if (cc > 0) {
                        query = "INSERT INTO t_crewrotation (i_candidateid, i_clientid, i_clientassetid, i_docflag, i_status1, ts_regdate, ts_moddate) "
                                + "SELECT i_candidateid, i_clientid, i_clientassetid, 1, 1, ts_moddate, ts_moddate FROM t_candidate WHERE i_clientid > 0 AND i_candidateid = ?";
                        pstmt = conn.prepareStatement(query);
                        pstmt.setInt(1, candidateId);
                        print(this, "addtocrew :: " + pstmt.toString());
                        cc = pstmt.executeUpdate();
                        if (cc > 0) {
                            query = "INSERT INTO t_directhistory (i_candidateid, i_clientid, i_clientassetid, i_positionid, s_remarks, i_userid, ts_regdate, d_date) VALUES (?,?,?,?,?,?, ?,?)";
                            pstmt = conn.prepareStatement(query);
                            pstmt.setInt(1, candidateId);
                            pstmt.setInt(2, clietId);
                            pstmt.setInt(3, clientassetId);
                            pstmt.setInt(4, positionId);
                            pstmt.setString(5, remarks);
                            pstmt.setInt(6, userId);
                            pstmt.setString(7, currDate1());
                            pstmt.setString(8, changeDate1(date));
                            print(this, "insert into history :: " + pstmt.toString());
                            cc = pstmt.executeUpdate();
                        }
                    }
                }
            } catch (Exception exception) {
                print(this, "addtocrew :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return cc;
    }

    //Onboard History
    public ArrayList getDirectOnboardHistory(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name , t_userlogin.s_name, ");
        sb.append("DATE_FORMAT(t_directhistory.d_date, '%d-%b-%Y'), t_directhistory.s_remarks ");
        sb.append("FROM t_directhistory ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_directhistory.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_directhistory.i_clientassetid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_directhistory.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_userlogin ON (t_userlogin.i_userid = t_directhistory. i_userid) ");
        sb.append("WHERE t_directhistory.i_candidateid = ? ");
        sb.append("ORDER BY  t_directhistory.ts_regdate DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getDirectOnboardHistory :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientname, clientAsset, positionName, gradeName, date, username, remarks;
            while (rs.next()) {
                clientname = rs.getString(1) != null ? rs.getString(1) : "";
                clientAsset = rs.getString(2) != null ? rs.getString(2) : "";
                positionName = rs.getString(3) != null ? rs.getString(3) : "";
                gradeName = rs.getString(4) != null ? rs.getString(4) : "";
                username = rs.getString(5) != null ? rs.getString(5) : "";
                date = rs.getString(6) != null ? rs.getString(6) : "";
                remarks = rs.getString(7) != null ? rs.getString(7) : "";
                if (!positionName.equals("")) {
                    if (!gradeName.equals("")) {
                        positionName += " | " + gradeName;
                    }
                }
                if (!clientname.equals("")) {
                    if (!clientAsset.equals("")) {
                        clientname += " | " + clientAsset;
                    }
                }
                list.add(new TalentpoolInfo(candidateId, clientname, positionName, username, date, remarks));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getCompetencyTrackerList(int candidateId, int next, int count, int clientId, int clientassetId, int pdeptId, int positionId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_tracker.i_trackerid, t_pcode.s_role, t_priority.s_name, DATE_FORMAT(t_tracker.d_completedate, '%d %b %Y'), ");
        sb.append("DATE_FORMAT(t_tracker.d_date, '%d %b %Y'), t_tracker.i_status, t_userlogin.s_name, ");
        sb.append("t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name, t_pdept.s_name, DATE_FORMAT(t_tracker.d_feedbackdate, '%d %b %Y') ");
        sb.append("FROM t_tracker LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) ");
        sb.append("LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid)  ");
        sb.append("LEFT JOIN t_priority ON (t_priority.i_priorityid = t_fcrole.i_priorityid) ");
        sb.append("LEFT JOIN t_userlogin ON (t_userlogin.i_userid = t_tracker.i_assessorid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_tracker.i_clientassetid) ");
        sb.append("LEFT JOIN t_client ON ( t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_fcrole.i_positionid ) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid ) ");
        sb.append("LEFT JOIN t_pdept ON (t_pdept.i_pdeptid = t_pcode.i_pdeptid) ");
        sb.append("WHERE t_tracker.i_candidateid = ? ");
        if (clientId > 0) {
            sb.append(" AND t_clientasset.i_clientid = ? ");
        }
        if (clientassetId > 0) {
            sb.append(" AND t_tracker.i_clientassetid = ? ");
        }
        if (pdeptId > 0) {
            sb.append(" AND t_pcode.i_pdeptid = ? ");
        }
        if (positionId > 0) {
            sb.append(" AND t_fcrole.i_positionid = ? ");
        }
        sb.append("ORDER BY t_tracker.d_completebydate1, t_tracker.ts_regdate DESC ");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        sb.append("SELECT COUNT(1) FROM t_tracker ");
        sb.append("JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) ");
        sb.append("LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid)  ");
        sb.append("LEFT JOIN t_priority ON (t_priority.i_priorityid = t_fcrole.i_priorityid) ");
        sb.append("LEFT JOIN t_userlogin ON (t_userlogin.i_userid = t_tracker.i_assessorid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_tracker.i_clientassetid) ");
        sb.append("WHERE t_tracker.i_candidateid = ? ");
        if (clientId > 0) {
            sb.append(" AND t_clientasset.i_clientid = ? ");
        }
        if (clientassetId > 0) {
            sb.append(" AND t_tracker.i_clientassetid = ? ");
        }
        if (pdeptId > 0) {
            sb.append(" AND t_pcode.i_pdeptid = ? ");
        }
        if (positionId > 0) {
            sb.append(" AND t_fcrole.i_positionid = ? ");
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            if (clientId > 0) {
                pstmt.setInt(++scc, clientId);
            }
            if (clientassetId > 0) {
                pstmt.setInt(++scc, clientassetId);
            }
            if (pdeptId > 0) {
                pstmt.setInt(++scc, pdeptId);
            }
            if (positionId > 0) {
                pstmt.setInt(++scc, positionId);
            }
            logger.info("getCompetencyTrackerList :: " + pstmt.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int trackerId = rs.getInt(1);
                String role = rs.getString(2) != null ? rs.getString(2) : "";
                String priority = rs.getString(3) != null ? rs.getString(3) : "";
                String completedon = rs.getString(4) != null ? rs.getString(4) : "";
                String validtill = rs.getString(5) != null ? rs.getString(5) : "";
                int status = rs.getInt(6);
                String loginName = rs.getString(7) != null ? rs.getString(7) : "";
                String clientname = rs.getString(8) != null ? rs.getString(8) : "";
                String clientassetname = rs.getString(9) != null ? rs.getString(9) : "";
                String position = rs.getString(10) != null ? rs.getString(10) : "";
                String grade = rs.getString(11) != null ? rs.getString(11) : "";
                String department = rs.getString(12) != null ? rs.getString(12) : "";
                String feedbackdate = rs.getString(13) != null ? rs.getString(13) : "";
                if (!position.equals("")) {
                    if (!grade.equals("")) {
                        position += " | " + grade;
                    }
                }
                String statusval = getStatus(status);

                list.add(new TalentpoolInfo(candidateId, trackerId, role, priority, completedon, validtill, status, loginName,
                        clientname, clientassetname, position, department, feedbackdate, statusval));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            pstmt.setInt(++scc, candidateId);
            if (clientId > 0) {
                pstmt.setInt(++scc, clientId);
            }
            if (clientassetId > 0) {
                pstmt.setInt(++scc, clientassetId);
            }
            if (pdeptId > 0) {
                pstmt.setInt(++scc, pdeptId);
            }
            if (positionId > 0) {
                pstmt.setInt(++scc, positionId);
            }
            print(this, "getCompetencyTrackerList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                candidateId = rs.getInt(1);
                list.add(new TalentpoolInfo(candidateId, 0, "", "", "", "", 0, "", "", "", "", "", "", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    //Competency position-rank
    public Collection getCopetencyPostions(int clientassetId, int pdeptId) {
        Collection coll = new LinkedList();
        if (clientassetId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_position.i_positionid, t_position.s_name, t_grade.s_name ");
            sb.append("FROM t_clientassetposition ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_clientassetposition.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("WHERE t_clientassetposition.i_clientassetid = ? AND t_clientassetposition.i_pdeptid = ? ORDER BY t_position.s_name, t_grade.s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new TalentpoolInfo(-1, "Select Position | Rank"));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                pstmt.setInt(2, pdeptId);
                Common.print(this, "getCopetencyPostions :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int id;
                String grade, position;
                while (rs.next()) {
                    id = rs.getInt(1);
                    position = rs.getString(2) != null ? rs.getString(2) : "";
                    grade = rs.getString(3) != null ? rs.getString(3) : "";
                    if (!position.equals("")) {
                        if (!grade.equals("")) {
                            position += " | " + grade;
                        }
                    }
                    coll.add(new TalentpoolInfo(id, position));
                }
            } catch (Exception e) {
                print(this, "getCopetencyPostions ::" + e.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new TalentpoolInfo(-1, " Select Position | Rank "));
        }
        return coll;
    }

    //Competency Department
    public Collection getCopetencyDepartment(int clientassetId) {
        Collection coll = new LinkedList();
        if (clientassetId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT DISTINCT(t_clientassetposition.i_pdeptid), t_pdept.s_name ");
            sb.append("FROM t_clientassetposition ");
            sb.append("LEFT JOIN t_pdept ON (t_pdept.i_pdeptid = t_clientassetposition.i_pdeptid) ");
            sb.append("WHERE i_clientassetid = ? AND t_clientassetposition.i_pdeptid > 0 ORDER BY t_pdept.s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new TalentpoolInfo(-1, "Select Department"));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                Common.print(this, "getCopetencyDepartment :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int id;
                String department;
                while (rs.next()) {
                    id = rs.getInt(1);
                    department = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new TalentpoolInfo(id, department));
                }
            } catch (Exception e) {
                print(this, "getCopetencyDepartment :: " + e.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new TalentpoolInfo(-1, " Select Department "));
        }
        return coll;
    }

    public int getPdeptId(int clientassetId, int positionId) {
        int pdeptId = 0;
        String query = ("SELECT i_pdeptid FROM t_clientassetposition WHERE i_clientassetid = ? AND i_positionid = ?");
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, positionId);
            logger.info(" getPdeptId:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                pdeptId = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            print(this, "getPdeptId :: " + e.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return pdeptId;
    }

    public String getStatus(int st) {
        String str = "";
        switch (st) {
            case 1:
            case 0:
                str = "Unassigned";
                break;
            case 2:
                str = "In Progress";
                break;
            case 3:
                str = "Completed";
                break;
            case 4:
                str = "Competent";
                break;
            case 5:
                str = "Not Yet Competent";
                break;
            case 6:
                str = "Appealed";
                break;
            default:
                break;
        }
        return str;
    }

    public Collection getClientAsset(int clientId, String assetids, int allclient, String permission) {
        Collection coll = new LinkedList();
        if (clientId > 0) {
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
            coll.add(new TalentpoolInfo(-1, " Select Asset "));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                // logger.info("getClientAsset :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new TalentpoolInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new TalentpoolInfo(-1, " Select Asset "));
        }
        return coll;
    }

    //Competency position-rank
    public Collection getCopetencyPositions(int clientassetId, int pdeptId) {
        Collection coll = new LinkedList();
        if (clientassetId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_position.i_positionid, t_position.s_name, t_grade.s_name ");
            sb.append("FROM t_clientassetposition ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_clientassetposition.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("WHERE t_clientassetposition.i_clientassetid = ? AND t_clientassetposition.i_pdeptid = ? ORDER BY t_position.s_name, t_grade.s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new TalentpoolInfo(-1, "Select Position | Rank"));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                pstmt.setInt(2, pdeptId);
                Common.print(this, "getCopetencyPostions :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int id;
                String grade, position;
                while (rs.next()) {
                    id = rs.getInt(1);
                    position = rs.getString(2) != null ? rs.getString(2) : "";
                    grade = rs.getString(3) != null ? rs.getString(3) : "";
                    if (!position.equals("")) {
                        if (!grade.equals("")) {
                            position += " | " + grade;
                        }
                    }
                    coll.add(new TalentpoolInfo(id, position));
                }
            } catch (Exception e) {
                print(this, "getCopetencyPostions ::" + e.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new TalentpoolInfo(-1, " Select Position | Rank "));
        }
        return coll;
    }

    //Position base on assettype
    public Collection getAssetypePositions(int assettypeId) {
        Collection coll = new LinkedList();
        if (assettypeId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECt t_position.i_positionid, t_position.s_name , t_grade.s_name  ");
            sb.append("FROM t_position ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("WHERE t_position.i_assettypeid = ? ORDER BY t_position.s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new TalentpoolInfo(-1, " Select Position | Rank "));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assettypeId);
                Common.print(this, "getAssetypePositions :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int id;
                String grade, position;
                while (rs.next()) {
                    id = rs.getInt(1);
                    position = rs.getString(2) != null ? rs.getString(2) : "";
                    grade = rs.getString(3) != null ? rs.getString(3) : "";
                    if (!position.equals("")) {
                        if (!grade.equals("")) {
                            position += " | " + grade;
                        }
                    }
                    coll.add(new TalentpoolInfo(id, position));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new TalentpoolInfo(-1, " Select Position | Rank "));
        }
        return coll;
    }

    //For onboarding formality
    public boolean checkInList(ArrayList list, String name) {
        boolean b = true;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            OnboardingInfo info = (OnboardingInfo) list.get(i);
            if (info != null && info.getPositionValue() != null && info.getPositionValue().equals(name)) {
                b = false;
                break;
            }
        }
        return b;
    }

    //For doctype Excel
    public ArrayList getDoctypeExcel(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_doctype.s_doc, t_govdoc.i_govid, DATE_FORMAT(t_govdoc.d_expirydate, '%d-%b-%Y') ");
        sb.append("FROM t_doctype ");
        sb.append("LEFT JOIN t_govdoc ON (t_doctype.i_doctypeid = t_govdoc.i_doctypeid AND t_govdoc.i_candidateid = ? AND t_govdoc.i_status = 1) ");
        sb.append("WHERE t_doctype.i_status = 1  AND t_doctype.i_mflag = 1 ORDER BY t_govdoc.d_expirydate ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getDoctypeExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String documentname, statusValue, expirydate;
            int govId;
            while (rs.next()) {
                documentname = rs.getString(1) != null ? rs.getString(1) : "";
                govId = rs.getInt(2);
                expirydate = rs.getString(3) != null ? rs.getString(3) : "";
                if (govId > 0) {
                    statusValue = "Yes";
                } else {
                    statusValue = "No";
                }
                list.add(new TalentpoolInfo(candidateId, documentname, govId, expirydate, statusValue));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public TalentpoolInfo getDoctypeInfo(int candidateId) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname), ");
        sb.append("t_clientasset.s_name,  t_client.s_name,  t_position.s_name,  t_grade.s_name ");
        sb.append("FROM t_candidate ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_candidate.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_candidate.i_clientassetid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_candidate.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("WHERE t_candidate.i_candidateid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getDoctypeInfo :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, clientname, assetname, position, grade;
            while (rs.next()) {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                clientname = rs.getString(2) != null ? rs.getString(2) : "";
                assetname = rs.getString(3) != null ? rs.getString(3) : "";
                position = rs.getString(4) != null ? rs.getString(4) : "";
                grade = rs.getString(5) != null ? rs.getString(5) : "";
                if (!position.equals("")) {
                    if (!grade.equals("")) {
                        position += " - " + grade;
                    }
                }
                info = (new TalentpoolInfo(candidateId, name, clientname, assetname, position));
            }
        } catch (Exception exception) {
            print(this, "getDoctypeInfo :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    //For offshore update    
    public ArrayList getOffshoreExcel(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_offshoreupdate.i_offshoreupdateid, t_client.s_name, t_clientasset.s_name, ");
        sb.append("t_remarktype.s_name, t_offshoreupdate.s_remarks, t_offshoreupdate.i_status, t_offshoreupdate.s_filename,  ");
        sb.append(" DATE_FORMAT(t_offshoreupdate.d_date, '%d-%b-%Y') FROM t_offshoreupdate ");
        sb.append("LEFT JOIN t_client ON (t_offshoreupdate.i_clientid = t_client.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_offshoreupdate.i_clientassetid =t_clientasset.i_clientassetid) ");
        sb.append("LEFT JOIN t_remarktype ON (t_remarktype.i_remarktypeid = t_offshoreupdate.i_remarktypeid) ");
        sb.append("WHERE t_offshoreupdate.i_candidateid= ? ORDER BY t_offshoreupdate.i_status, t_offshoreupdate.d_date DESC");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getOffshoreExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clinetname, assetname, filename, remarktype, remark, date;
            int offshoreId, status;
            while (rs.next()) {
                offshoreId = rs.getInt(1);
                clinetname = rs.getString(2) != null ? rs.getString(2) : "";
                assetname = rs.getString(3) != null ? rs.getString(3) : "";
                remarktype = rs.getString(4) != null ? rs.getString(4) : "";
                remark = rs.getString(5) != null ? rs.getString(5) : "";
                status = rs.getInt(6);
                filename = rs.getString(7) != null ? rs.getString(7) : "";
                date = rs.getString(8) != null ? rs.getString(8) : "";
                list.add(new TalentpoolInfo(offshoreId, clinetname, assetname, remarktype, remark, status, filename, date, 0, ""));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    //For Nominee excel    
    public ArrayList getNomineeExcel(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_nominee.i_nomineeid, t_nominee.s_nomineename, t_nominee.s_nomineecontactno, ");
        sb.append("t_relation.s_name, t_nominee.i_status, t_nominee.s_code, t_nominee.s_address, t_nominee.i_age, t_nominee.d_percentage ");
        sb.append("FROM t_nominee ");
        sb.append("LEFT JOIN t_relation ON (t_relation.i_relationid = t_nominee.i_relationid) ");
        sb.append("WHERE t_nominee.i_candidateid = ? ORDER BY t_nominee.i_status, t_nominee.ts_regdate DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getNomineeExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String nomineename, nomineecontactno, address,nomineerelation, code;
            int nomineedetailId, status, age;
            double percentage;
            while (rs.next()) {
                nomineedetailId = rs.getInt(1);
                nomineename = rs.getString(2) != null ? rs.getString(2) : "";
                nomineecontactno = rs.getString(3) != null ? rs.getString(3) : "";
                nomineerelation = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                code = rs.getString(6) != null ? rs.getString(6) : "";
                address = rs.getString(7) != null ? rs.getString(7) : "";
                age = rs.getInt(8);
                percentage = rs.getDouble(9);
                if (code != null && !code.equals("")) {
                    if (nomineecontactno != null && !nomineecontactno.equals("")) {
                        nomineecontactno = "+" + code + " " + nomineecontactno;
                    }
                }
                list.add(new TalentpoolInfo(nomineedetailId, nomineename, nomineecontactno, 
                        nomineerelation, status, code, address, age, percentage, "", candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    //For PPE excel    
    public ArrayList getPpeExcel(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_ppedetail.i_ppedetailid, t_ppedetail.s_remarks, t_ppedetail.i_status, t_ppetype.s_name, ");
        sb.append("DATE_FORMAT(t_ppedetail.ts_moddate, '%d-%b-%Y'), t_userlogin.s_name ");
        sb.append("FROM t_ppedetail ");
        sb.append("LEFT JOIN t_ppetype ON(t_ppetype.i_ppetypeid = t_ppedetail.i_ppetypeid) ");
        sb.append("LEFT JOIN t_userlogin ON(t_userlogin.i_userid = t_ppedetail.i_userid ) ");
        sb.append("WHERE t_ppedetail.i_candidateid = ? ORDER BY t_ppedetail.i_status, t_ppetype.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getPpeExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String ppetype, remark,date, username;
            int ppedetailId, status;
            while (rs.next()) {
                ppedetailId = rs.getInt(1);
                remark = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                ppetype = rs.getString(4) != null ? rs.getString(4) : "";
                date = rs.getString(5) != null ? rs.getString(5) : "";
                username = rs.getString(6) != null ? rs.getString(6) : "";
                list.add(new TalentpoolInfo(ppedetailId, remark, status, ppetype, date, username, "", candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int createContractPdffile(int candidateId, String pdffilename, int uId,
            int contractId, String fromDate, String toDate, int clientassetId, int positionId,
            String username,String cval1, String cval2, String cval3, String cval4,
            String cval5, String cval6, String cval7, String cval8, String cval9, String cval10) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_contractdetail ");
            sb.append("(i_contractid, s_file1, ts_file1ts, i_candidateid, i_status, d_fromdate, ");
            sb.append("d_todate, i_userid, ts_regdate, ts_moddate, i_clientassetid, i_positionid, s_username1, ");
            sb.append("s_val1, s_val2, s_val3, s_val4, s_val5, s_val6, s_val7, s_val8, s_val9,s_val10 ) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,   ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, contractId);
            pstmt.setString(++scc, pdffilename);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, 1);
            pstmt.setString(++scc, changeDate1(fromDate));
            pstmt.setString(++scc, changeDate1(toDate));
            pstmt.setInt(++scc, uId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, clientassetId);
            pstmt.setInt(++scc, positionId);
            pstmt.setString(++scc, username);
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
            print(this, "createContractPdffile :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                cc = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createContractPdffile :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    //For contarct
    public String createContractFile(int candidateId, int contractId, String cval1s, String cval2s, String cval3s,
            String cval4s, String cval5s, String cval6s, String cval7s, String cval8s, String cval9s, String cval10s,
            String validfrom, String validto, String contractor, String edate, String dmname, String designation, String comments,
            int clientId, int jobpostId, String jobasset, double variablepay ) throws SQLException 
    {        
        String pdffilename = "", templatename = "", templatepath = "";
        templatepath = getMainPath("add_contractfile");
        
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), DATE_FORMAT(c.d_dob, '%d-%b-%Y'), ");
        sb.append("c.s_placeofbirth, c.s_email, c.s_code1, c.s_contactno1, c.s_code2, c.s_contactno2, c.s_code3, c.s_contactno3, ");
        sb.append("c.s_gender, t_country.s_name, n.s_nationality, c.s_address1line1, c.s_address1line2, c.s_address2line1, ");
        sb.append("c.s_address2line2, c.s_address2line3, c.s_nextofkin, t_relation.s_name, c.s_ecode1, c.s_econtactno1, c.s_ecode2, ");
        sb.append("c.s_econtactno2, t_maritialstatus.s_name, c.s_photofilename, t_position.s_name , c.s_address1line3, t_city.s_name, ");
        sb.append("t_experiencedept.s_name,c.i_expectedsalary,t_currency.s_name, c.s_firstname, t_grade.s_name,  c.s_lastname, ");
        sb.append("c.s_middlename, t_healthdeclaration.s_bloodgroup, p2.s_name, g2.s_name, t_dayrate.d_rate1, t_dayrate.d_rate2, d2.d_rate1, d2.d_rate2 ");
        sb.append("FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = c.i_countryid ) ");
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
        sb.append("LEFT JOIN t_healthdeclaration ON (t_healthdeclaration.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN t_dayrate ON (t_dayrate.i_candidateid = c.i_candidateid AND t_dayrate.i_positionid = c.i_positionid AND t_dayrate.i_clientassetid = c.i_clientassetid) ");
        sb.append("LEFT JOIN t_dayrate AS d2 ON (d2.i_candidateid = c.i_candidateid AND d2.i_positionid = c.i_positionid2 AND d2.i_clientassetid = c.i_clientassetid) ");
        sb.append(" WHERE c.i_status = 1 AND c.i_candidateid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);

        Connection conn = null;
        ResultSet rs = null;
        String fle_query = ("SELECT s_description, s_wids,s_eids,s_dids, s_tids, s_description2, s_description3, s_filename, s_nids FROM t_contract WHERE i_contractid = ? ");
        
        StringBuilder sbinner = new StringBuilder();
        sbinner.append("SELECT  t_position.s_name,  t_experiencedept.s_name, t_workexperience.s_companyname, t_workexperience.s_assetname,  ");
        sbinner.append("DATE_FORMAT(t_workexperience.d_workstartdate, '%d-%b-%Y'), DATE_FORMAT(t_workexperience.d_workenddate, '%d-%b-%Y'), ");
        sbinner.append("t_workexperience.i_currentworkingstatus  ");
        sbinner.append("FROM t_workexperience ");
        sbinner.append("LEFT JOIN t_position ON (t_position.i_positionid = t_workexperience.i_positionid) ");
        sbinner.append("LEFT JOIN t_experiencedept ON (t_experiencedept.i_experiencedeptid = t_workexperience.i_departmentid) ");
        sbinner.append("WHERE t_workexperience.i_status = 1 AND t_workexperience.i_candidateid = ? ");
        String sbinnerquery = sbinner.toString();
        sbinner.setLength(0);
        PreparedStatement pstmtinner = null;

        StringBuilder certsbinner = new StringBuilder();
        certsbinner.append("SELECT  t_coursename.s_name,  t_coursetype.s_name, t_trainingandcert.s_educinst, t_city.s_name,  t_approvedby.s_name, ");
        certsbinner.append("DATE_FORMAT(t_trainingandcert.d_dateofissue, '%d-%b-%Y'), DATE_FORMAT(t_trainingandcert.d_expirydate, '%d-%b-%Y'), ");
        certsbinner.append("t_country.s_name ");
        certsbinner.append("FROM t_trainingandcert ");
        certsbinner.append("LEFT JOIN t_coursetype ON ( t_coursetype.i_coursetypeid = t_trainingandcert.i_coursetypeid ) ");
        certsbinner.append("LEFT JOIN t_coursename ON (t_coursename.i_coursenameid = t_trainingandcert.i_coursenameid) ");
        certsbinner.append("LEFT JOIN t_city ON ( t_city.i_cityid = t_trainingandcert.i_locationofinstitid ) ");
        certsbinner.append("LEFT JOIN t_country ON ( t_city.i_countryid = t_country. i_countryid ) ");
        certsbinner.append("LEFT JOIN t_approvedby ON ( t_approvedby.i_approvedbyid = t_trainingandcert.i_approvedbyid ) ");
        certsbinner.append("WHERE t_trainingandcert.i_candidateid = ? AND t_trainingandcert.i_status = 1 ");
        String certsbinnerquery = certsbinner.toString();
        certsbinner.setLength(0);
        PreparedStatement pstmtcertinner = null;

        String clientname = "";
        StringBuilder clientinner = new StringBuilder();
        clientinner.append(" SELECT s_name FROM t_client WHERE i_status = 1 AND i_clientid = ? ");
        String clientquery = clientinner.toString();
        clientinner.setLength(0);
        PreparedStatement pstmtclient = null;

        double dayrate = 0;
        String currencyrate = "";
        StringBuilder dayrateinner = new StringBuilder();
        dayrateinner.append("SELECT t_currency.s_name, t_jobpost.d_dayratevalue FROM t_jobpost ");
        dayrateinner.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = t_jobpost.i_currencyid) ");
        dayrateinner.append("WHERE t_jobpost.i_status = 1 AND t_jobpost.i_jobpostid = ? ");
        String dayratequery = dayrateinner.toString();
        dayrateinner.setLength(0);
        PreparedStatement pstmtdayrate = null;
        
        StringBuilder nomineesb = new StringBuilder();
        nomineesb.append("SELECT t_nominee.i_nomineeid, t_nominee.s_nomineename, t_nominee.s_nomineecontactno, t_relation.s_name, ");
        nomineesb.append("t_nominee.s_code, t_nominee.s_address, t_nominee.i_age, t_nominee.d_percentage ");
        nomineesb.append("FROM t_nominee ");
        nomineesb.append("LEFT JOIN t_relation ON (t_relation.i_relationid = t_nominee.i_relationid) ");
        nomineesb.append("WHERE t_nominee.i_candidateid = ? ORDER BY t_nominee.i_status, t_nominee.ts_regdate DESC ");
        String nomineequery = nomineesb.toString();
        nomineesb.setLength(0);
        PreparedStatement pstmtnominee = null;

        StringBuilder edusbinner = new StringBuilder();
        edusbinner.append("SELECT  t_qualificationtype.s_name,  t_degree.s_name, t_eduqual.s_eduinstitute, ");
        edusbinner.append("t_city.s_name,t_eduqual.s_fieldofstudy, DATE_FORMAT(t_eduqual.d_coursestart, '%d-%b-%Y'), ");
        edusbinner.append("DATE_FORMAT(t_eduqual.d_passingyear, '%d-%b-%Y') ,t_country.s_name, t_eduqual.i_highestqualification ");
        edusbinner.append("FROM t_eduqual ");
        edusbinner.append("LEFT JOIN t_qualificationtype ON (t_qualificationtype.i_qualificationtypeid = t_eduqual.i_qualificationtypeid) ");
        edusbinner.append("LEFT JOIN t_city ON (t_city.i_cityid = t_eduqual.i_locationofinstituteid) ");
        edusbinner.append("LEFT JOIN t_degree ON (t_degree.i_degreeid = t_eduqual. i_degreeid) ");
        edusbinner.append("LEFT JOIN t_country ON (t_city.i_countryid = t_country. i_countryid) ");
        edusbinner.append("WHERE t_eduqual.i_status = 1 AND t_eduqual.i_candidateid = ? ");
        String edusbinnerquery = edusbinner.toString();
        edusbinner.setLength(0);
        PreparedStatement pstmteduinner = null;
        sb.setLength(0);

        sb.append("SELECT t_doctype.s_doc, t_govdoc.s_docno, t_city.s_name, t_documentissuedby.s_name, ");
        sb.append("DATE_FORMAT(t_govdoc.d_dateofissue, '%d-%b-%Y'), DATE_FORMAT(t_govdoc.d_expirydate, '%d-%b-%Y'), ");
        sb.append("t_country.s_name, t_govdoc.i_doctypeid ");
        sb.append("FROM t_govdoc ");
        sb.append("LEFT JOIN t_doctype ON (t_govdoc.i_doctypeid = t_doctype.i_doctypeid) ");
        sb.append("LEFT JOIN t_city ON (t_govdoc.i_placeofissueid = t_city.i_cityid) ");
        sb.append("LEFT JOIN t_documentissuedby ON (t_govdoc.i_documentissuedbyid = t_documentissuedby.i_documentissuedbyid) ");
        sb.append("LEFT JOIN t_country ON (t_city.i_countryid = t_country.i_countryid) ");
        sb.append("WHERE t_govdoc.i_status =1 AND t_govdoc.i_candidateid =? ");
        String docsbinnerquery = (sb.toString()).intern();
        sb.setLength(0);
        PreparedStatement pstmtdocinner = null;
        sb.setLength(0);
        
        try {
            conn = getConnection();
            pstmtinner = conn.prepareStatement(sbinnerquery);
            pstmtdocinner = conn.prepareStatement(docsbinnerquery);
            pstmtcertinner = conn.prepareStatement(certsbinnerquery);
            pstmt = conn.prepareStatement(fle_query);
            pstmt.setInt(1, contractId);
            String wids = "", eids = "", dids = "", tids = "", nids = "",headerBody = "", footerBody= "", watermarkurl = "";
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                templatename = rs.getString(1);
                wids = rs.getString(2) != null ? rs.getString(2) : "";
                eids = rs.getString(3) != null ? rs.getString(3) : "";
                dids = rs.getString(4) != null ? rs.getString(4) : "";
                tids = rs.getString(5) != null ? rs.getString(5) : "";
                headerBody = rs.getString(6) != null ? rs.getString(6) : "";
                footerBody = rs.getString(7) != null ? rs.getString(7) : "";
                watermarkurl = rs.getString(8) != null ? rs.getString(8) : "";
                nids = rs.getString(9) != null ? rs.getString(9) : "";
            }
            rs.close();
            
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            print(this, "Personnel_Data :: " + pstmt.toString());
            String name, dob, birthplace, email, contact1_code, contact1, contact2_code, contact2,
            contact3_code, contact3, gender, countryName, nationality, address1line1, address1line2,
            address2line1, address2line2, address2line3, nextofkin, relation, econtact1_code, econtact1,
            econtact2_code, econtact2, maritialstatus, photo, positionName, address1line3, city,experiencedept,
            currency, firstname, gradeName, lastname, middlename, bloodgroup, position2, grade2;
            double rate1, rate2, p2rate1, p2rate2;
            int expectedsalary;
            rs = pstmt.executeQuery();
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
                econtact1 = decipher(rs.getString(22) != null ? rs.getString(22) : "");
                econtact2_code = rs.getString(23) != null ? rs.getString(23) : "";
                econtact2 = decipher(rs.getString(24) != null ? rs.getString(24) : "");
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
                bloodgroup = decipher(rs.getString(37) != null ? rs.getString(37) : "");
                position2 = rs.getString(38) != null ? rs.getString(38) : "";
                grade2 = rs.getString(39) != null ? rs.getString(39) : "";
                rate1 = rs.getDouble(40);
                rate2 = rs.getDouble(41);
                p2rate1 = rs.getDouble(42);
                p2rate2 = rs.getDouble(43);                
                address1line1 += " "+address1line2+ " "+address1line3;
                if (!position2.equals("")) {
                    if (!grade2.equals("")) {
                        position2 += " - " + grade2;
                    }
                }
                if (!positionName.equals("")) {
                    if (!gradeName.equals("")) {
                        positionName += " - " + gradeName;
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
                
                pstmtinner.setInt(1, candidateId);
                print(this, "Experience_Data :: " + pstmtinner.toString());
                ResultSet rsinner = pstmtinner.executeQuery();
                StringBuilder we = new StringBuilder();
                while (rsinner.next()) 
                {
                    String position = rsinner.getString(1) != null ? rsinner.getString(1) : "";
                    String department = rsinner.getString(2) != null ? rsinner.getString(2) : "";
                    String companyname = rsinner.getString(3) != null ? rsinner.getString(3) : "";
                    String assetname = rsinner.getString(4) != null ? rsinner.getString(4) : "";
                    String startdate = rsinner.getString(5) != null ? rsinner.getString(5) : "";
                    String enddate = rsinner.getString(6) != null ? rsinner.getString(6) : "";
                    int currentworkingstatus = rsinner.getInt(7);
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
                    we.append("</tr>");
                }
                rsinner.close();

                String westr = we.toString();
                we.setLength(0);
                String wevalue = "";
                if (!westr.equals("")) {
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
                    wevalue += ("</tr>");
                    wevalue += westr;
                    wevalue += "</table>";
                    wevalue += "</td>";
                    wevalue += "</tr>";
                }

                pstmtclient = conn.prepareStatement(clientquery);
                pstmtclient.setInt(1, clientId);
                print(this, "clientquery :: " + pstmtclient.toString());
                ResultSet clientrs = pstmtclient.executeQuery();
                while (clientrs.next()) {
                    clientname = clientrs.getString(1) != null ? clientrs.getString(1) : "";
                }
                clientrs.close();

                pstmtdayrate = conn.prepareStatement(dayratequery);
                pstmtdayrate.setInt(1, jobpostId);
                print(this, "dayratequery :: " + pstmtdayrate.toString());
                ResultSet dayraters = pstmtdayrate.executeQuery();
                while (dayraters.next()) {
                    currencyrate = dayraters.getString(1) != null ? dayraters.getString(1) : "";
                    dayrate = dayraters.getDouble(2);
                }
                dayraters.close();
                
                pstmtnominee = conn.prepareStatement(nomineequery);
                pstmtnominee.setInt(1, candidateId);
                print(this, "Nominee query :: " + pstmtnominee.toString());
                ResultSet nomineers = pstmtnominee.executeQuery();
                int nomineeId, age;
                double percantage ;
                String nomineename, nomineerelation, code, nomineecontactno, address;
                StringBuilder nom = new StringBuilder();
                while (nomineers.next()) 
                {                    
                    nomineeId = nomineers.getInt(1);
                    nomineename = nomineers.getString(2) != null ? nomineers.getString(2) : "";
                    nomineecontactno = nomineers.getString(3) != null ? nomineers.getString(3) : "";
                    nomineerelation = nomineers.getString(4) != null ? nomineers.getString(4) : "";
                    code = nomineers.getString(5) != null ? nomineers.getString(5) : "";
                    address = nomineers.getString(6) != null ? nomineers.getString(6) : "";
                    age = nomineers.getInt(7);
                    percantage = nomineers.getDouble(8);
                    if (code != null && !code.equals("")) {
                        if (nomineecontactno != null && !nomineecontactno.equals("")) {
                            nomineecontactno = "+" + code + " " + nomineecontactno;
                        }
                    }                
                    nom.append("<tr style='border: 1px solid black;text-align:center;'>");
                    if (nids.contains("1")) {
                        nom.append("<td style='border: 1px solid black;text-align:center;'>" + nomineename + "</td>");
                    }
                    if (nids.contains("2")) {
                        nom.append("<td style='border: 1px solid black;text-align:center;'>" + nomineecontactno + "</td>");
                    }
                    if (nids.contains("3")) {
                        nom.append("<td style='border: 1px solid black;text-align:center;'>" + nomineerelation + "</td>");
                    }
                    if (nids.contains("4")) {
                        nom.append("<td style='border: 1px solid black;text-align:center;'>" + (age > 0 ? age : "&nbsp;") + "</td>");
                    }
                    if (nids.contains("5")) {
                        nom.append("<td style='border: 1px solid black;text-align:center;'>" + (percantage > 0 ? percantage : "&nbsp;") + "</td>");
                    }
                    if (nids.contains("6")) {
                        nom.append("<td style='border: 1px solid black;text-align:center;'>" + address + "</td>");
                    }
                    nom.append("</tr>");
                }
                nomineers.close();                
                
                String nomstr = nom.toString();
                nom.setLength(0);
                String nomvalue = "";
                if (!nomstr.equals("")) 
                {
                    nomvalue += "<tr style='text-align:center;'><td style='height:10px;'></td></tr>";
                    nomvalue += "<tr style='text-align:center;'><td style='text-decoration:underline;text-align:right; font-weight:bold; font-family: georgia, palatino; font-size: 20px;'></td></tr>";
                    nomvalue += "<tr style='text-align:center;'>";
                    nomvalue += "<td style='text-align:center;' colspan='2'>";
                    nomvalue += "<table cellspacing='5' cellpadding='5' align='center' style='width:100%;border-collapse: collapse;'>";
                    nomvalue += ("<tr>");
                    if (nids.contains("1")) {
                        nomvalue += ("<td width='5%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center; text-align:center;'>Name</td>");
                    }
                    if (nids.contains("2")) {
                        nomvalue += ("<td width='5%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center; text-align:center;'>Contact Number</td>");
                    }
                    if (nids.contains("3")) {
                        nomvalue += ("<td width='5%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center; text-align:center;'>Relation</td>");
                    }
                    if (nids.contains("4")) {
                        nomvalue += ("<td width='5%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center; text-align:center;'>Age Of Nominee</td>");
                    }
                    if (nids.contains("5")) {
                        nomvalue += ("<td width='5%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center; text-align:center;'>Percentage Of Distribution</td>");
                    }
                    if (nids.contains("6")) {
                        nomvalue += ("<td width='5%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center; text-align:center;'>Address</td>");
                    }
                    nomvalue += ("</tr>");
                    nomvalue += nomstr;
                    nomvalue += "</table>";
                    nomvalue += "</td>";
                    nomvalue += "</tr>";
                } 
                
                String highestq = "";
                pstmteduinner = conn.prepareStatement(edusbinnerquery);
                pstmteduinner.setInt(1, candidateId);
                print(this, "Education_Detalis :: " + pstmteduinner.toString());
                ResultSet edurs = pstmteduinner.executeQuery();
                StringBuilder ed = new StringBuilder();
                while (edurs.next()) {
                    String kindname = edurs.getString(1) != null ? edurs.getString(1) : "";
                    String degree = edurs.getString(2) != null ? edurs.getString(2) : "";
                    String educinst = decipher(edurs.getString(3) != null ? edurs.getString(3) : "");
                    String locationofinstit = edurs.getString(4) != null ? edurs.getString(4) : "";
                    String filedofstudy = edurs.getString(5) != null ? edurs.getString(5) : "";
                    String startdate = edurs.getString(6) != null ? edurs.getString(6) : "";
                    String enddate = edurs.getString(7) != null ? edurs.getString(7) : "";
                    String countryname = edurs.getString(8) != null ? edurs.getString(8) : "";
                    int hflag = edurs.getInt(9);
                    if (hflag == 1) {
                        highestq = kindname + " - " + degree;
                    }
                    if (!countryname.equals("")){
                        if(!educinst.equals("")){
                            educinst += ", "+locationofinstit + "(" + countryname + ")";
                        }
                    }
                    
                    ed.append("<tr>");
                    if (eids.contains("1")) {
                        ed.append("<td>" + kindname + "</td>");
                    }
                    if (eids.contains("2")) {
                        ed.append("<td>" + degree + "</td>");
                    }
                    if (eids.contains("3")) {
                        ed.append("<td>" + educinst+ "</td>");
                    }
                    if (eids.contains("4")) {
                        ed.append("<td>" + filedofstudy + "</td>");
                    }
                    if (eids.contains("5")) {
                        ed.append("<td>" + startdate + "</td>");
                    }
                    if (eids.contains("6")) {
                        ed.append("<td>" + enddate + "</td>");
                    }
                    ed.append("</tr>");
                }
                edurs.close();

                String edstr = ed.toString();
                ed.setLength(0);
                String eduvalue = "";
                if (!edstr.equals("")) {
                    eduvalue += "<tr><td style='height:10px;'></td></tr>";
                    eduvalue += "<tr><td style='text-decoration:underline;text-align:left;font-weight:bold;'></td></tr>";
                    eduvalue += "<tr>";
                    eduvalue += "<td>";
                    eduvalue += "<table width='100%' border='1' cellspacing='5' cellpadding='5' align='center' style='border-collapse: collapse;'>";
                    eduvalue += ("<tr>");
                    if (eids.contains("1")) {
                        eduvalue += ("<td>Kind</td>");
                    }
                    if (eids.contains("2")) {
                        eduvalue += ("<td>Degree</td>");
                    }
                    if (eids.contains("3")) {
                        eduvalue += ("<td>Institution, Location</td>");
                    }
                    if (eids.contains("4")) {
                        eduvalue += ("<td>Field of Study</td>");
                    }
                    if (eids.contains("5")) {
                        eduvalue += ("<td>Start Date</td>");
                    }
                    if (eids.contains("6")) {
                        eduvalue += ("<td>End Date</td>");
                    }
                    eduvalue += ("</tr>");
                    eduvalue += edstr;
                    eduvalue += "</table>";
                    eduvalue += "</td>";
                    eduvalue += "</tr>";
                }

                pstmtcertinner.setInt(1, candidateId);
                print(this, "Certificate_Deatils :: " + pstmtcertinner.toString());
                ResultSet certrs = pstmtcertinner.executeQuery();
                StringBuilder cert = new StringBuilder();
                while (certrs.next()) {
                    String coursename = certrs.getString(1) != null ? certrs.getString(1) : "";
                    String coursetype = certrs.getString(2) != null ? certrs.getString(2) : "";
                    String educinst = certrs.getString(3) != null ? certrs.getString(3) : "";
                    String locationofinstit = certrs.getString(4) != null ? certrs.getString(4) : "";
                    String approvedby = certrs.getString(5) != null ? certrs.getString(5) : "";
                    String dateofissue = certrs.getString(6) != null ? certrs.getString(6) : "";
                    String expirydate = certrs.getString(7) != null ? certrs.getString(7) : "";
                    String countryname = certrs.getString(8) != null ? certrs.getString(8) : "";
                    if (!countryname.equals("")) {
                        if(!educinst.equals("")){
                            educinst += ", "+locationofinstit+" (" + countryname + ")";
                        }
                    }
                    cert.append("<tr>");
                    if (tids.contains("5")) {
                        cert.append("<td>" + dateofissue + "</td>");
                    }
                    if (tids.contains("6")) {
                        cert.append("<td>" + expirydate + "</td>");
                    }
                    if (tids.contains("1")) {
                        cert.append("<td>" + coursename + "</td>");
                    }
                    if (tids.contains("2")) {
                        cert.append("<td>" + coursetype + "</td>");
                    }
                    if (tids.contains("3")) {
                        cert.append("<td>" + educinst+ "</td>");
                    }
                    if (tids.contains("4")) {
                        cert.append("<td>" + approvedby + "</td>");
                    }
                    cert.append("</tr>");
                }
                certrs.close();

                String certstr = cert.toString();
                cert.setLength(0);
                String certvalue = "";
                if (!certstr.equals("")) {
                    certvalue += "<tr><td style='height:10px;'></td></tr>";
                    certvalue += "<tr><td style='text-decoration:underline;text-align:left;font-weight:bold;'></td></tr>";
                    certvalue += "<tr>";
                    certvalue += "<td>";
                    certvalue += "<table width='100%' border='1' cellspacing='5' cellpadding='5' align='center' style='border-collapse: collapse;'>";
                    certvalue += ("<tr>");
                    if (tids.contains("5")) {
                        certvalue += ("<td>Issue Date</td>");
                    }
                    if (tids.contains("6")) {
                        certvalue += ("<td>Expiry Date</td>");
                    }
                    if (tids.contains("1")) {
                        certvalue += ("<td>Course Name</td>");
                    }
                    if (tids.contains("2")) {
                        certvalue += ("<td>Type</td>");
                    }
                    if (tids.contains("3")) {
                        certvalue += ("<td>Institution, Location</td>");
                    }
                    if (tids.contains("4")) {
                        certvalue += ("<td>Approved By</td>");
                    }
                    certvalue += ("</tr>");
                    certvalue += certstr;
                    certvalue += "</table>";
                    certvalue += "</td>";
                    certvalue += "</tr>";
                }

                HashMap hashmap = new HashMap();
                pstmtdocinner.setInt(1, candidateId);
                print(this, "Document_Details :: " + pstmtdocinner.toString());
                ResultSet rsdoc = pstmtdocinner.executeQuery();
                StringBuilder govdoc = new StringBuilder();
                String documentname, documentno, placeofissue, issuedby, dateofissue, dateofexpiry, countryname;
                int doctype;
                while (rsdoc.next()) {
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
                    govdoc.append("<tr>");
                    if (dids.contains("1")) {
                        govdoc.append("<td>" + documentname + "</td>");
                    }
                    if (dids.contains("2")) {
                        govdoc.append("<td>" + documentno + "</td>");
                    }
                    if (dids.contains("3")) {
                        govdoc.append("<td>" + placeofissue + "</td>");
                    }
                    if (dids.contains("4")) {
                        govdoc.append("<td>" + issuedby + "</td>");
                    }
                    if (dids.contains("5")) {
                        govdoc.append("<td>" + dateofissue + "</td>");
                    }
                    if (dids.contains("6")) {
                        govdoc.append("<td>" + dateofexpiry + "</td>");
                    }
                    govdoc.append("</tr>");

                    if (doctype == 1) {
                        hashmap.put("AADHARNUMBER", documentno);
                        hashmap.put("AADHARISSUEDATE", dateofissue);
                    }
                    if (doctype == 3) {
                        hashmap.put("BIRTHCERTIFICATENUMBER", documentno);
                        hashmap.put("BIRTHCERTIFICATEISSUEDATE", dateofissue);
                    }
                    if (doctype == 4) {
                        hashmap.put("MEDICALCERTIFICATENUMBER", documentno);
                        hashmap.put("MEDICALCERTIFICATEISSUEDATE", dateofissue);
                        hashmap.put("MEDICALCERTIFICATEEXPIRYDATE", dateofexpiry);
                    }
                    if (doctype == 2) {
                        hashmap.put("PASSPORTNUMBER", documentno);
                        hashmap.put("PASSPORTISSUEDATE", dateofissue);
                        hashmap.put("PASSPORTEXPIRYDATE", dateofexpiry);
                    }
                    if (doctype == 5) {
                        hashmap.put("PANCARDNUMBER", documentno);
                        hashmap.put("PANCARDISSUEDATE", dateofissue);
                    }
                    if (doctype == 6) {
                        hashmap.put("PCCNUMBER", documentno);
                        hashmap.put("PCCISSUEDATE", dateofissue);
                        hashmap.put("PCCEXPIRYDATE", dateofexpiry);
                    }
                    if (doctype == 8) {
                        hashmap.put("ONGCNEDPASSNUMBER", documentno);
                        hashmap.put("ONGCNEDPASSNUMBERISSUEDATE", dateofissue);
                        hashmap.put("ONGCNEDPASSNUMBEREXPIRYDATE", dateofexpiry);
                    }
                    if (doctype == 9) {
                        hashmap.put("CDCNUMBER", documentno);
                        hashmap.put("CDCISSUEDATE", dateofissue);
                        hashmap.put("CDCEXPIRYDATE", dateofexpiry);
                    }
                }
                rsdoc.close();

                String docstr = govdoc.toString();
                govdoc.setLength(0);
                String docvalue = "";
                if (!docstr.equals("")) {
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
                double totalrate = dayrate + dayrate * variablepay / 100;
                
                //For serial number
                String countquery = "SELECT i_count FROM t_counter";
                PreparedStatement pstmtcount = conn.prepareStatement(countquery);
                logger.info("countquery :: " + pstmtcount.toString());
                ResultSet countrs = pstmtcount.executeQuery();
                int count = 0;
                while (countrs.next()) {
                    count = countrs.getInt(1);
                }
                countrs.close();
                
                countquery = "UPDATE t_counter SET i_count = (i_count +1) ";
                pstmtcount = conn.prepareStatement(countquery);
                logger.info("countquery2 :: " + pstmtcount.toString());
                count += pstmtcount.executeUpdate();

                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
                int year = cal.get(Calendar.YEAR);
                Template template = new Template(templatepath + templatename);
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
                hashmap.put("PERMANENTADDRESS", address1line1);
                hashmap.put("ADDRESS2LINE1", address2line1);
                hashmap.put("ADDRESS2LINE2", address2line2);
                hashmap.put("ADDRESS2LINE3", address2line3);
                hashmap.put("NEXTOFKIN", nextofkin);
                hashmap.put("RELATION", relation);
                hashmap.put("ECONTACT1", econtact1);
                hashmap.put("ECONTACT2", econtact2);
                hashmap.put("MARITALSTATUS", maritialstatus);
                if (!photo.equals("")) {
                    hashmap.put("PHOTO", "<img src='" + view_path + photo + "' style = 'weight :150px; height : 150px'/>");
                }
                hashmap.put("ADDRESS1LINE3", address1line3);
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
                hashmap.put("BLOODGROUP", bloodgroup);
                hashmap.put("WORKEXPERIENCE", wevalue);
                hashmap.put("EDUCATION", eduvalue);
                hashmap.put("GOVDOC", docvalue);
                hashmap.put("CUSTOMVALUE1", (cval1s != null && !cval1s.equals("") ? cval1s : "Custom Value 1"));
                hashmap.put("CUSTOMVALUE2", (cval2s != null && !cval2s.equals("") ? cval2s : "Custom Value 2"));
                hashmap.put("CUSTOMVALUE3", (cval3s != null && !cval3s.equals("") ? cval3s : "Custom Value 3"));
                hashmap.put("CUSTOMVALUE4", (cval4s != null && !cval4s.equals("") ? cval4s : "Custom Value 4"));
                hashmap.put("CUSTOMVALUE5", (cval5s != null && !cval5s.equals("") ? cval5s : "Custom Value 5"));
                hashmap.put("CUSTOMVALUE6", (cval6s != null && !cval6s.equals("") ? cval6s : "Custom Value 6"));
                hashmap.put("CUSTOMVALUE7", (cval7s != null && !cval7s.equals("") ? cval7s : "Custom Value 7"));
                hashmap.put("CUSTOMVALUE8", (cval8s != null && !cval8s.equals("") ? cval8s : "Custom Value 8"));
                hashmap.put("CUSTOMVALUE9", (cval9s != null && !cval9s.equals("") ? cval9s : "Custom Value 9"));
                hashmap.put("CUSTOMVALUE10", (cval10s != null && !cval10s.equals("") ? cval10s : "Custom Value 10"));
                hashmap.put("CERTIFICATIONS", certvalue);
                hashmap.put("HIGHESTQUALIFICATION", highestq);
                hashmap.put("CONTRACTOR", contractor);
                hashmap.put("CALLOFFORDER", edate);
                hashmap.put("VALIDFROM", validfrom);
                hashmap.put("VALIDTO", validto);
                hashmap.put("MANAGERNAME", dmname);
                hashmap.put("DESIGNATION", designation);
                hashmap.put("COMMENTS", comments);
                hashmap.put("COMPANY", clientname);
                hashmap.put("DAYRATE", dayrate);
                hashmap.put("DAYRATECURRENCY", currencyrate);
                hashmap.put("ASSETNAME", jobasset);
                hashmap.put("TOTALRATE", totalrate);
                hashmap.put("VPE", variablepay);
                hashmap.put("CURRENTDATE", currDate01());
                hashmap.put("CURRENTDATETIME", currDate02());                
                hashmap.put("CURRENTYEAR", year);
                hashmap.put("SERIALNUMBER", count);
                hashmap.put("NOMINEE", nomvalue);
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
                String filePath = getMainPath("add_candidate_file");
                String viewPath = getMainPath("view_contractfile");
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
                if(watermarkurl != null && !watermarkurl.equals(""))
                {
                    watermarkurl = viewPath+watermarkurl;
                }
                try {
                    generatePDFString(st1, pdfFile, PD4Constants.A4, "",  headerBody, footerBody, watermarkurl, pdfwidth);
                    pdfFile = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            rs.close();
            pstmt.close();
            pstmtinner.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return pdffilename;
    }

    //Mail for contract
    public TalentpoolInfo setContractEmailDetail(int candidateId) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("SELECT t_candidate.s_firstname, t_position.s_name, t_grade.s_name, t_candidate.s_email, ");
            sb.append("t_client.s_name, t_clientasset.s_name, t_client.s_ocsuserids ");
            sb.append("FROM t_candidate ");
            sb.append("LEFT JOIN t_position ON (t_candidate.i_positionid = t_position.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_position.i_gradeid = t_grade.i_gradeid) ");
            sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_candidate.i_clientassetid) ");
            sb.append("LEFT JOIN t_client ON ( t_client.i_clientid = t_clientasset.i_clientid) ");
            sb.append("WHERE t_candidate.i_candidateid = ? ");
            String query = sb.toString().intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            print(this, "setContractEmailDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String candidatename = rs.getString(1) != null ? rs.getString(1) : "";
                String position = rs.getString(2) != null ? rs.getString(2) : "";
                String grade = rs.getString(3) != null ? rs.getString(3) : "";
                String email = rs.getString(4) != null ? rs.getString(4) : "";
                String clientname = rs.getString(5) != null ? rs.getString(5) : "";
                String assetname = rs.getString(6) != null ? rs.getString(6) : "";
                String ocsuserids = rs.getString(7) != null ? rs.getString(7) : "";
                if (!grade.equals("")) {
                    position += " - " + grade;
                }
                String ccval = "";
                if (!ocsuserids.equals("")) {
                    String query_cc = "SELECT GROUP_CONCAT(s_email) FROM t_userlogin WHERE i_userid IN (" + ocsuserids + ")";
                    PreparedStatement pstmt_cc = conn.prepareStatement(query_cc);
                    ResultSet rs_cc = pstmt_cc.executeQuery();
                    while (rs_cc.next()) {
                        ccval = rs_cc.getString(1) != null ? rs_cc.getString(1) : "";
                    }
                }
                info = new TalentpoolInfo(candidatename, candidateId, position, email, clientname, assetname, ccval);
            }

        } catch (Exception exception) {
            print(this, "setContractEmailDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    //Contract listing
    public ArrayList getContractListing(int candidateId, int clientId, int assetId, int contractStatus, String fromdate, String todate,
            int next, int count) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_contractdetail.i_contractdetailid, t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name, t_contract.i_type, ");
        sb.append("DATE_FORMAT(t_contractdetail.d_fromdate, '%d-%b-%Y '), DATE_FORMAT(t_contractdetail.d_todate, '%d-%b-%Y'), ");
        sb.append("t_contractdetail.s_file1, t_contractdetail.s_file2, t_contractdetail.s_file3, t_contractdetail.i_status, ");
        sb.append("t_contractdetail.i_approval1, t_contractdetail.i_approval2, ");
        sb.append("CURRENT_DATE() < t_contractdetail.d_fromdate , CURRENT_DATE() <= t_contractdetail.d_todate && CURRENT_DATE() >= t_contractdetail.d_fromdate , ");
        sb.append(" CURRENT_DATE() >t_contractdetail.d_todate, t_contractdetail.i_userid, t_contractdetail.i_contractid  ");
        sb.append("FROM t_contractdetail ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_contractdetail.i_clientassetid) ");
        sb.append("LEFT JOIN t_client ON ( t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_contractdetail.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_contract ON(t_contract.i_contractid = t_contractdetail.i_contractid) ");
        sb.append("WHERE t_contractdetail.i_candidateid = ? ");
        if (clientId > 0) {
            sb.append("AND t_client.i_clientid = ? ");
        }
        if (assetId > 0) {
            sb.append("AND t_contractdetail.i_clientassetid = ? ");
        }
        if (contractStatus > 0) {
            if (contractStatus == 1) {
                sb.append("AND t_contractdetail.i_status = 1 AND CURRENT_DATE() < t_contractdetail.d_fromdate ");
            } else if (contractStatus == 2) {
                sb.append("AND t_contractdetail.i_status = 1 AND CURRENT_DATE() <= t_contractdetail.d_todate && CURRENT_DATE() >= t_contractdetail.d_fromdate ");
            } else if (contractStatus == 3) {
                sb.append("AND t_contractdetail.i_status = 1 AND CURRENT_DATE() >t_contractdetail.d_todate ");
            } else if (contractStatus == 4) {
                sb.append("AND t_contractdetail.i_status = 2 ");
            }
        }
        if (fromdate != null && !fromdate.equals("") && !fromdate.equals("DD-MMM-YYYY") && todate != null && !todate.equals("") && !todate.equals("DD-MMM-YYYY")) {
            sb.append(" AND ((t_contractdetail.d_fromdate >= ? AND t_contractdetail.d_fromdate <= ?) ");
            sb.append("OR (t_contractdetail.d_todate >= ? AND t_contractdetail.d_todate <= ?)) ");
        }
        sb.append("ORDER BY t_contractdetail.i_status, t_contractdetail.d_fromdate DESC ");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);        
        
        sb.append("SELECT COUNT(1) FROM t_contractdetail ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_contractdetail.i_clientassetid) ");
        sb.append("LEFT JOIN t_client ON ( t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_contractdetail.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_contract ON(t_contract.i_contractid = t_contractdetail.i_contractid) ");
        sb.append("WHERE t_contractdetail.i_candidateid = ? ");
        if (clientId > 0) {
            sb.append("AND t_client.i_clientid = ? ");
        }
        if (assetId > 0) {
            sb.append("AND t_contractdetail.i_clientassetid = ? ");
        }
        if (contractStatus > 0) {
            if (contractStatus == 1) {
                sb.append("AND t_contractdetail.i_status = 1 AND CURRENT_DATE() < t_contractdetail.d_fromdate ");
            } else if (contractStatus == 2) {
                sb.append("AND t_contractdetail.i_status = 1 AND CURRENT_DATE() <= t_contractdetail.d_todate && CURRENT_DATE() >= t_contractdetail.d_fromdate ");
            } else if (contractStatus == 3) {
                sb.append("AND t_contractdetail.i_status = 1 AND CURRENT_DATE() >t_contractdetail.d_todate ");
            } else if (contractStatus == 4) {
                sb.append("AND t_contractdetail.i_status = 2 ");
            }
        }
        if (fromdate != null && !fromdate.equals("") && !fromdate.equals("DD-MMM-YYYY") && todate != null && !todate.equals("") && !todate.equals("DD-MMM-YYYY")) {
            sb.append(" AND ((t_contractdetail.d_fromdate >= ? AND t_contractdetail.d_fromdate <= ?) ");
            sb.append("OR (t_contractdetail.d_todate >= ? AND t_contractdetail.d_todate <= ?)) ");
        }
        sb.append("ORDER BY t_contractdetail.i_status, t_contractdetail.d_fromdate DESC ");
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            if (clientId > 0) {
                pstmt.setInt(++scc, clientId);
            }
            if (assetId > 0) {
                pstmt.setInt(++scc, assetId);
            }
            if (fromdate != null && !fromdate.equals("") && !fromdate.equals("DD-MMM-YYYY") && todate != null && !todate.equals("") && !todate.equals("DD-MMM-YYYY")) {
                pstmt.setString(++scc, changeDate1(fromdate));
                pstmt.setString(++scc, changeDate1(todate));
                pstmt.setString(++scc, changeDate1(fromdate));
                pstmt.setString(++scc, changeDate1(todate));
            }
            logger.info("getContractListing :: " + pstmt.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int contractdetailId = rs.getInt(1);
                String clientName = rs.getString(2) != null ? rs.getString(2) : "";
                String assetName = rs.getString(3) != null ? rs.getString(3) : "";
                String positionName = rs.getString(4) != null ? rs.getString(4) : "";
                String gradeName = rs.getString(5) != null ? rs.getString(5) : "";
                int type = rs.getInt(6);
                String fromDate = rs.getString(7) != null ? rs.getString(7) : "";
                String toDate = rs.getString(8) != null ? rs.getString(8) : "";
                String file1 = rs.getString(9) != null ? rs.getString(9) : "";
                String file2 = rs.getString(10) != null ? rs.getString(10) : "";
                String file3 = rs.getString(11) != null ? rs.getString(11) : "";
                int status = rs.getInt(12);
                int approval1 = rs.getInt(13);
                int approval2 = rs.getInt(14);
                int flag1 = rs.getInt(15);
                int flag2 = rs.getInt(16);
                int flag3 = rs.getInt(17);
                int userId = rs.getInt(18);
                int contractId = rs.getInt(19);
                String stval = "";
                if (status == 2) {
                    stval = "In-Active";
                } else {
                    stval = getDateStatusVal(flag1, flag2, flag3);
                }
                list.add(new TalentpoolInfo(contractdetailId, clientName, assetName, positionName,
                        gradeName, type, fromDate, toDate, file1, file2, file3, status, approval1, 
                        approval2, stval, userId, contractId, "", candidateId));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            pstmt.setInt(++scc, candidateId);
            if (clientId > 0) {
                pstmt.setInt(++scc, clientId);
            }
            if (assetId > 0) {
                pstmt.setInt(++scc, assetId);
            }
            if (fromdate != null && !fromdate.equals("") && !fromdate.equals("DD-MMM-YYYY") && todate != null && !todate.equals("") && !todate.equals("DD-MMM-YYYY")) {
                pstmt.setString(++scc, changeDate1(fromdate));
                pstmt.setString(++scc, changeDate1(todate));
                pstmt.setString(++scc, changeDate1(fromdate));
                pstmt.setString(++scc, changeDate1(todate));
            }
            print(this, "getContractListing :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int contractdetailId = rs.getInt(1);
                list.add(new TalentpoolInfo(contractdetailId, "", "", "", "", 0, "", "", "", "", "", 0, 0, 0, "", 0, 0, "", 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public String getDateStatusVal(int flag1, int flag2, int flag3) {
        String str = "";
        if (flag1 == 1) {
            str = "New";
        }
        if (flag2 == 1) {
            str = "Ongoing";
        }
        if (flag3 == 1) {
            str = "Expired";
        }
        return str;
    }

    //Contract generated pdf    
    public String getGeneratedContractpdf(int contractdetailId) {
        String pdffilename = "";
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_file1 FROM t_contractdetail WHERE i_contractdetailid =? AND i_status = 1 ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, contractdetailId);
            print(this, "getGeneratedContractpdf :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                pdffilename = rs.getString(1) != null ? rs.getString(1) : "";
            }
        } catch (Exception exception) {
            print(this, "getGeneratedContractpdf :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return pdffilename;
    }

    //for modify contract
    public TalentpoolInfo getDetailFormodify(int contractDetailId) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("SELECT t_contractdetail.i_contractid, t_contractdetail.s_file1, t_contractdetail.s_file2, t_contractdetail.s_file3, ");
            sb.append("DATE_FORMAT(t_contractdetail.d_fromdate, '%d-%b-%Y '), DATE_FORMAT(t_contractdetail.d_todate, '%d-%b-%Y'), ");
            sb.append("t_contract.i_type, t_contract.i_refid, t_contractdetail.i_status, t_contractdetail.s_remarks1, t_contractdetail.s_remarks2, ");
            sb.append("t_contractdetail.s_val1, t_contractdetail.s_val2, t_contractdetail.s_val3, t_contractdetail.s_val4, t_contractdetail.s_val5, ");
            sb.append("t_contractdetail.s_val6, t_contractdetail.s_val7, t_contractdetail.s_val8, t_contractdetail.s_val9, t_contractdetail.s_val10 ");
            sb.append("FROM t_contractdetail ");
            sb.append("LEFT JOIN t_contract ON (t_contractdetail.i_contractid = t_contract.i_contractid) ");
            sb.append("WHERE t_contractdetail.i_contractdetailid = ? ");
            String query = sb.toString().intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, contractDetailId);
            print(this, "getDetailFormodify :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int contractId = rs.getInt(1);
                String file1 = rs.getString(2) != null ? rs.getString(2) : "";
                String file2 = rs.getString(3) != null ? rs.getString(3) : "";
                String file3 = rs.getString(4) != null ? rs.getString(4) : "";
                String fromdate = rs.getString(5) != null ? rs.getString(5) : "";
                String todate = rs.getString(6) != null ? rs.getString(6) : "";
                int type = rs.getInt(7);
                int refId = rs.getInt(8);
                int status = rs.getInt(9);
                String remark1 = rs.getString(10) != null ? rs.getString(10) : "";
                String remark2 = rs.getString(11) != null ? rs.getString(11) : "";
                String val1 = rs.getString(12) != null ? rs.getString(12) : "";
                String val2 = rs.getString(13) != null ? rs.getString(13) : "";
                String val3 = rs.getString(14) != null ? rs.getString(14) : "";
                String val4 = rs.getString(15) != null ? rs.getString(15) : "";
                String val5 = rs.getString(16) != null ? rs.getString(16) : "";
                String val6 = rs.getString(17) != null ? rs.getString(17) : "";
                String val7 = rs.getString(18) != null ? rs.getString(18) : "";
                String val8 = rs.getString(19) != null ? rs.getString(19) : "";
                String val9 = rs.getString(20) != null ? rs.getString(20) : "";
                String val10 = rs.getString(21) != null ? rs.getString(21) : "";
                info = new TalentpoolInfo(contractId, file1, file2, file3, fromdate, 
                        todate, type, refId, status, remark1, remark2, val1, val2, val3,
                        val4, val5, val6, val7, val8, val9, val10);
            }
        } catch (Exception exception) {
            print(this, "getDetailFormodify :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int updateContractPdffile(int candidateId, String pdffilename, int uId,
            int contractId, String fromDate, String toDate, int clientassetId, int positionId,
            String username, int contractdetailId,String cval1, String cval2, String cval3,
            String cval4, String cval5, String cval6, String cval7, String cval8, String cval9, String cval10) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_contractdetail SET ");
            sb.append("i_contractid = ?, ");
            sb.append("s_file1 = ?, ");
            sb.append(" ts_file1ts = ?, ");
            sb.append(" d_fromdate = ?, ");
            sb.append(" d_todate = ?, ");
            sb.append(" s_username1 = ?, ");
            sb.append("i_userid = ?, ");
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
            sb.append("WHERE i_contractdetailid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, contractId);
            pstmt.setString(++scc, pdffilename);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, changeDate1(fromDate));
            pstmt.setString(++scc, changeDate1(toDate));
            pstmt.setString(++scc, username);
            pstmt.setInt(++scc, uId);
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
            pstmt.setInt(++scc, contractdetailId);
            print(this, "updateContractPdffile :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                cc = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "updateContractPdffile :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int contractApprove(int candidateId, int contractIdetailId, String file2, String remarks,
            String userName, int uId, int type) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            if (type == 2) {
                sb.append("UPDATE t_contractdetail SET ");
                sb.append("s_file2 = ?, ");
                sb.append(" ts_file2ts = ?, ");
                sb.append(" s_username2 = ?, ");
                sb.append("i_userid = ?, ");
                sb.append("i_status = ?, ");
                sb.append("i_approval2 = ?, ");
                sb.append("s_remarks2 = ?, ");
                sb.append(" ts_regdate = ?, ");
                sb.append(" ts_moddate = ? ");
                sb.append("WHERE i_contractdetailid = ? AND i_candidateid = ?");
            } else {
                sb.append("UPDATE t_contractdetail SET ");
                sb.append("s_file3 = ?, ");
                sb.append(" ts_file3ts = ?, ");
                sb.append(" s_username3 = ?, ");
                sb.append("i_userid = ?, ");
                sb.append("i_status = ?, ");
                sb.append("i_approval1 = ?, ");
                sb.append("s_remarks1 = ?, ");
                sb.append(" ts_regdate = ?, ");
                sb.append(" ts_moddate = ? ");
                sb.append("WHERE i_contractdetailid = ? AND i_candidateid = ?");
            }
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, file2);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, userName);
            pstmt.setInt(++scc, uId);
            pstmt.setInt(++scc, 1);
            pstmt.setInt(++scc, 1);
            pstmt.setString(++scc, remarks);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, contractIdetailId);
            pstmt.setInt(++scc, candidateId);
            print(this, "contractApprove1 :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                cc = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "contractApprove1 :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    //For user detail modal
    public TalentpoolInfo getDetailsForModal(int contractDetailId) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        try 
        {            
            sb.append("SELECT t_contractdetail.i_contractdetailid, t_contractdetail.s_file1, t_contractdetail.s_file2, "); 
            sb.append("t_contractdetail.s_file3, DATE_FORMAT(t_contractdetail.ts_file1ts, '%d-%b-%Y %H:%i'), "); 
            sb.append("DATE_FORMAT(t_contractdetail.ts_file2ts, '%d-%b-%Y %H:%i'), ");
            sb.append("DATE_FORMAT(t_contractdetail.ts_file3ts, '%d-%b-%Y %H:%i'), t_contractdetail.s_username1, "); 
            sb.append("t_contractdetail.s_username2, t_contractdetail.s_username3 , t_contract.s_name ");
            sb.append("FROM t_contractdetail ");
            sb.append("LEFT JOIN t_contract ON(t_contract.i_contractid = t_contractdetail.i_contractid) ");
            sb.append("WHERE t_contractdetail.i_contractdetailid = ? ");            
            String query = sb.toString().intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, contractDetailId);
            print(this, "getDetailsForModal :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int contractId = rs.getInt(1);
                String file1 = rs.getString(2) != null ? rs.getString(2) : "";
                String file2 = rs.getString(3) != null ? rs.getString(3) : "";
                String file3 = rs.getString(4) != null ? rs.getString(4) : "";
                String date1 = rs.getString(5) != null ? rs.getString(5) : "";
                String date2 = rs.getString(6) != null ? rs.getString(6) : "";
                String date3 = rs.getString(7) != null ? rs.getString(7) : "";
                String uername1 = rs.getString(8) != null ? rs.getString(8) : "";
                String uername2 = rs.getString(9) != null ? rs.getString(9) : "";
                String uername3 = rs.getString(10) != null ? rs.getString(10) : "";
                String contractName = rs.getString(11) != null ? rs.getString(11) : "";

                info = new TalentpoolInfo(contractId, file1, file2, file3, date1, date2, date3, uername1, uername2, uername3, contractName);
            }

        } catch (Exception exception) {
            print(this, "getDetailsForModal :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    //delete Contract
    public int deleteContract(int candidateId, int contractdetailId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_contractdetail SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_contractdetailid = ? AND i_candidateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (status == 1) {
                pstmt.setInt(++scc, 2);
            } else {
                pstmt.setInt(++scc, 1);
            }
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, contractdetailId);
            pstmt.setInt(++scc, candidateId);
            print(this, "deleteContract :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteContract :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 7, contractdetailId);
        return cc;
    }

    //For delete contract file
    public int deleteContractFile(int contractdetailId, int fileId, int userId, String file1, String file2, String file3) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            String path = getMainPath("add_candidate_file");
            sb.append("UPDATE t_contractdetail SET ");
            if (fileId == 2) 
            {
                String deletePath = path + file2;
                File file = new File(deletePath);
                if (file.exists()) 
                {
                    file.delete();
                }
                
                String deletePath2 = path + file3;
                File file_3 = new File(deletePath2);
                if (file_3.exists()) 
                {
                    file_3.delete();
                }
                sb.append("s_file2 = '', ");
                sb.append("ts_file2ts = '0000-00-00 00:00:00', ");
                sb.append("s_username2 = '', ");
                sb.append("i_approval1 = 0, ");
                sb.append("s_remarks1 = '', ");
                
                sb.append("s_file3 = '', ");
                sb.append("ts_file3ts = '0000-00-00 00:00:00', ");
                sb.append("s_username3 = '', ");
                sb.append("i_approval2 = 0, ");
                sb.append("s_remarks2 = '', ");
            }
            if (fileId == 3) 
            {
                String deletePath = path + file3;
                File file = new File(deletePath);
                if (file.exists()) 
                {
                    file.delete();
                }
                sb.append("s_file3 = '', ");
                sb.append("ts_file3ts = '0000-00-00 00:00:00', ");
                sb.append("s_username3 = '', ");
                sb.append("i_approval2 = 0, ");
                sb.append("s_remarks2 = '', ");
            }
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_contractdetailid = ?");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, contractdetailId);
            print(this, "deleteContractFile :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteContractFile :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    //Contract Duplicacy
    public int checkContractDuplicacy(int contractdetailId, int contractId, String fromDate, String toDate) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_contractdetailid FROM t_contractdetail WHERE i_contractid = ? AND d_fromdate = ? AND d_todate = ? AND i_status IN (1, 2)");
        if (contractdetailId > 0) {
            sb.append(" AND i_contractdetailid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, contractId);
            pstmt.setString(++scc, changeDate1(fromDate));
            pstmt.setString(++scc, changeDate1(toDate));
            if (contractdetailId > 0) {
                pstmt.setInt(++scc, contractdetailId);
            }
            print(this, "checkContractDuplicacy :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkContractDuplicacy :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public String getCurrencyNameAndId(int clientassetId) {
        String cc = "";
        if (clientassetId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_clientasset.i_currencyid, t_currency.s_name FROM t_clientasset ");
            sb.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = t_clientasset.i_currencyid) ");
            sb.append("WHERE i_clientassetid =? AND t_clientasset.i_status =1 ");
            String query = sb.toString();
            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                logger.info("getCurrencyNameAndId :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                    cc = ddlValue + "#@#@#" + ddlLabel;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return cc;
    }

    public int updateTransModal(int candidateId, int uId, TalentpoolInfo info) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_jobpostid FROM t_crewrotation WHERE i_candidateid = ? AND i_active = 1");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("get id :: " + pstmt.toString());

            rs = pstmt.executeQuery();
            int jobpostId = 0;
            while (rs.next()) {
                jobpostId = rs.getInt(1);
            }
            rs.close();
            
            if (info != null) 
            {
                String torate1 = "0", torate2 = "0", torate3 = "0",top2rate1 = "0", top2rate2 = "0", top2rate3 = "0";
                torate1 = info.getTorate1() != null && !info.getTorate1().equals("") ? info.getTorate1() : "0";
                torate2 = info.getTorate2() != null && !info.getTorate2().equals("") ? info.getTorate2() : "0";
                torate3 = info.getTorate3() != null && !info.getTorate3().equals("") ? info.getTorate3() : "0";
                
                top2rate1 = info.getTop2rate1() != null && !info.getTop2rate1().equals("") ? info.getTop2rate1() : "0";
                top2rate2 = info.getTop2rate2() != null && !info.getTop2rate2().equals("") ? info.getTop2rate2() : "0";
                top2rate3 = info.getTop2rate3() != null && !info.getTop2rate3().equals("") ? info.getTop2rate3() : "0";

                sb.append("UPDATE t_candidate SET ");
                sb.append("i_clientid =?, ");
                sb.append("i_clientassetid =?, ");
                sb.append("i_currencyid =?, ");
                sb.append("i_positionid =?, ");
                sb.append("i_positionid2 =?, ");
                sb.append("i_userid =?, ");
                sb.append(" ts_moddate = NOW() ");
                sb.append("WHERE i_candidateid = ? ");
                query = (sb.toString()).intern();
                sb.setLength(0);

                pstmt = conn.prepareStatement(query);
                int scc = 0;
                pstmt.setInt(++scc, info.getToClientId());
                pstmt.setInt(++scc, info.getToAssetId());
                pstmt.setInt(++scc, info.getToCurrencyId());
                pstmt.setInt(++scc, info.getToPositionId());
                pstmt.setInt(++scc, info.getToPositionId2());
                pstmt.setInt(++scc, uId);
                pstmt.setInt(++scc, candidateId);
                print(this, "updateTransModal :: " + pstmt.toString());
                pstmt.executeUpdate();
                
                sb.append("SELECT t_clientasset.i_assettypeid FROM t_candidate LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_candidate.i_clientassetid) WHERE t_candidate.i_clientassetid = ? AND t_candidate.i_candidateid = ? ");
                query = (sb.toString()).intern();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, info.getToAssetId());
                pstmt.setInt(2, candidateId);
                logger.info("get assettypeId :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int assettypeId = 0;
                while (rs.next()) {
                    assettypeId = rs.getInt(1);
                }
                rs.close();
                
                sb.append("UPDATE t_candidate SET ");
                sb.append("i_userid =?, ");
                sb.append("i_assettypeid =?, ");
                sb.append(" ts_moddate = NOW() ");
                sb.append("WHERE i_candidateid = ? ");
                query = (sb.toString()).intern();
                sb.setLength(0);

                pstmt = conn.prepareStatement(query);
                scc = 0;
                pstmt.setInt(++scc, uId);
                pstmt.setInt(++scc, assettypeId);
                pstmt.setInt(++scc, candidateId);
                print(this, "updateAssettypeId :: " + pstmt.toString());
                pstmt.executeUpdate();
                
                sb.append("UPDATE t_crewrotation SET ");
                sb.append("i_active =2, ");
                sb.append("i_userid =?, ");
                sb.append("ts_moddate =NOW() ");
                sb.append("WHERE i_candidateid =? ");
                sb.append("AND i_active =1 ");
                query = (sb.toString()).intern();
                sb.setLength(0);

                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, uId);
                pstmt.setInt(2, candidateId);
                print(this, "updateTransModal :: " + pstmt.toString());
                pstmt.executeUpdate();

                sb.append("INSERT INTO t_crewrotation ");
                sb.append("(i_candidateid, i_clientid, i_clientassetid, i_jobpostid, i_docflag, i_status1, i_userid, ts_onboardeddate, ts_regdate) ");
                sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                query = (sb.toString()).intern();
                sb.setLength(0);

                pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                scc = 0;
                pstmt.setInt(++scc, candidateId);
                pstmt.setInt(++scc, info.getToClientId());
                pstmt.setInt(++scc, info.getToAssetId());
                pstmt.setInt(++scc, jobpostId);
                pstmt.setInt(++scc, 1);
                pstmt.setInt(++scc, 1);
                pstmt.setInt(++scc, uId);
                pstmt.setString(++scc, currDate1());
                pstmt.setString(++scc, currDate1());
                print(this,"updateTransModal :: " + pstmt.toString());
                pstmt.executeUpdate();
                rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    cc = rs.getInt(1);
                }
                rs.close();
                
                if (cc > 0) {
                    int dayratemainid = 0;
                    String selQuery = "SELECT i_dayratemainid FROM t_dayratemain WHERE i_clientassetid =? AND i_status =1";
                    sb.setLength(0);
                    pstmt = conn.prepareStatement(selQuery);
                    pstmt.setInt(1, info.getToAssetId());
                    logger.info("get dayratemainid :: " + pstmt.toString());
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        dayratemainid = rs.getInt(1);
                    }
                    if (dayratemainid <= 0) {
                        query = "INSERT INTO t_dayratemain (i_clientassetid, i_status, i_userid, ts_regdate, ts_moddate) VALUES(?, ?, ?, ?, ?)";
                        pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                        scc = 0;
                        pstmt.setInt(++scc, info.getToAssetId());
                        pstmt.setInt(++scc, 1);
                        pstmt.setInt(++scc, uId);
                        pstmt.setString(++scc, currDate1());
                        pstmt.setString(++scc, currDate1());
                        print(this, "INSERT INTO t_dayratemain :: "+pstmt.toString());
                        pstmt.executeUpdate();
                        rs = pstmt.getGeneratedKeys();
                        while (rs.next()) {
                            dayratemainid = rs.getInt(1);
                        }
                        rs.close();
                    }
                    
                    if (dayratemainid > 0) 
                    {
                        sb.append("INSERT INTO t_dayrate (i_dayratemainid, i_clientassetid, i_candidateid, i_positionid, d_rate1, d_rate2, d_rate3, i_status, ");
                        sb.append("i_userid, ts_regdate, ts_moddate, d_fromdate, d_todate) VALUES(?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?)");
                        query = sb.toString().intern();
                        sb.setLength(0);
                        pstmt = conn.prepareStatement(query);
                        scc = 0;
                        pstmt.setInt(++scc, dayratemainid);
                        pstmt.setInt(++scc, info.getToAssetId());
                        pstmt.setInt(++scc, candidateId);
                        pstmt.setInt(++scc, info.getToPositionId());
                        pstmt.setString(++scc, info.getTorate1());
                        pstmt.setString(++scc, info.getTorate2());
                        pstmt.setString(++scc, info.getTorate3());
                        pstmt.setInt(++scc, 1);
                        pstmt.setInt(++scc, uId);
                        pstmt.setString(++scc, currDate1());
                        pstmt.setString(++scc, currDate1());
                        pstmt.setString(++scc, changeDate1(info.getJoiningDate1()));
                        pstmt.setString(++scc, changeDate1(info.getEndDate1()));
                        print(this, "INSERT INTO position1  :: "+pstmt.toString());
                        pstmt.executeUpdate();                        
                        if(info.getToPositionId2() > 0)
                        {
                            scc = 0;
                            pstmt.setInt(++scc, dayratemainid);
                            pstmt.setInt(++scc, info.getToAssetId());
                            pstmt.setInt(++scc, candidateId);
                            pstmt.setInt(++scc, info.getToPositionId2());
                            pstmt.setString(++scc, info.getTop2rate1());
                            pstmt.setString(++scc, info.getTop2rate2());
                            pstmt.setString(++scc, info.getTop2rate3());
                            pstmt.setInt(++scc, 1);
                            pstmt.setInt(++scc, uId);
                            pstmt.setString(++scc, currDate1());
                            pstmt.setString(++scc, currDate1());
                            pstmt.setString(++scc, changeDate1(info.getJoiningDate2()));
                            pstmt.setString(++scc, changeDate1(info.getEndDate2()));
                            print(this, "INSERT INTO position2  :: "+pstmt.toString());
                            pstmt.executeUpdate();
                        }
                    }
                    
                    query = "SELECT i_dayratehid, DATE_FORMAT(d_todate, '%d-%b-%Y ') FROM t_dayrateh WHERE i_clientassetid = ? AND i_candidateid = ? AND i_positionid = ? ORDER BY d_fromdate DESC LIMIT 0,1 ";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, info.getToAssetId());
                    pstmt.setInt(2, candidateId);
                    pstmt.setInt(3, info.getToPositionId());
                    print(this, " Select dayratehid :: " + pstmt.toString());
                    rs = pstmt.executeQuery();
                    int dayratehId = 0;
                    String lasttodate = "";
                    while (rs.next()) 
                    {
                        dayratehId = rs.getInt(1);
                        lasttodate = rs.getString(2) != null && !rs.getString(2).equals("0000-00-00") ? rs.getString(2) : "";
                    }
                    rs.close();

                    if (dayratehId > 0 && (lasttodate.equals("") || lasttodate.equals("0000-00-00"))) {
                        String newdate = getDateAfter(info.getJoiningDate1(), 1, -1, "dd-MMM-yyyy", "dd-MMM-yyyy");
                        query = "UPDATE t_dayrateh SET d_todate = ? WHERE i_dayratehid = ?";
                        pstmt = conn.prepareStatement(query);
                        pstmt.setString(1, changeDate1(newdate));
                        pstmt.setInt(2, dayratehId);
                        print(this, "UPDATE t_dayrateh :: " + pstmt.toString());
                        cc = pstmt.executeUpdate();
                    }

                    scc = 0;
                    query = "INSERT INTO t_dayrateh (i_clientassetid, i_candidateid, i_positionid, d_rate1, d_rate2, d_rate3, d_fromdate, d_todate, i_status, i_userid, ts_regdate, ts_moddate)  VALUES (?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ? ) ";
                    pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setInt(++scc, info.getToAssetId());
                    pstmt.setInt(++scc, candidateId);
                    pstmt.setInt(++scc, info.getToPositionId());
                    pstmt.setString(++scc, info.getTorate1());
                    pstmt.setString(++scc, info.getTorate2());
                    pstmt.setString(++scc, info.getTorate3());
                    pstmt.setString(++scc, changeDate1(info.getJoiningDate1()));
                    pstmt.setString(++scc, changeDate1(info.getEndDate1()));
                    pstmt.setInt(++scc, 1);
                    pstmt.setInt(++scc, uId);
                    pstmt.setString(++scc, currDate1());
                    pstmt.setString(++scc, currDate1());
                    print(this, " insert dayrateh :: " + pstmt.toString());
                    cc = pstmt.executeUpdate(); 
                    
                    if(info.getToPositionId2() > 0)
                    {
                        query = "SELECT i_dayratehid, DATE_FORMAT(d_todate, '%d-%b-%Y ') FROM t_dayrateh WHERE i_clientassetid = ? AND i_candidateid = ? AND i_positionid = ? ORDER BY d_fromdate DESC LIMIT 0,1 ";
                        pstmt = conn.prepareStatement(query);
                        pstmt.setInt(1, info.getToAssetId());
                        pstmt.setInt(2, candidateId);
                        pstmt.setInt(3, info.getToPositionId2());
                        print(this, " Select dayratehid2 :: " + pstmt.toString());
                        rs = pstmt.executeQuery();
                        while (rs.next()) 
                        {
                            dayratehId = rs.getInt(1);
                            lasttodate = rs.getString(2) != null && !rs.getString(2).equals("0000-00-00") ? rs.getString(2) : "";
                        }
                        rs.close();

                        if (dayratehId > 0 && (lasttodate.equals("") || lasttodate.equals("0000-00-00"))) {
                            String newdate = getDateAfter(info.getJoiningDate2(), 1, -1, "dd-MMM-yyyy", "dd-MMM-yyyy");
                            query = "UPDATE t_dayrateh SET d_todate = ? WHERE i_dayratehid = ?";
                            pstmt = conn.prepareStatement(query);
                            pstmt.setString(1, changeDate1(newdate));
                            pstmt.setInt(2, dayratehId);
                            print(this, "UPDATE t_dayrateh2 :: " + pstmt.toString());
                            cc = pstmt.executeUpdate();
                        }

                        scc = 0;
                        query = "INSERT INTO t_dayrateh (i_clientassetid, i_candidateid, i_positionid, d_rate1, d_rate2, d_rate3, d_fromdate, d_todate, i_status, i_userid, ts_regdate, ts_moddate)  VALUES (?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ? ) ";
                        pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                        pstmt.setInt(++scc, info.getToAssetId());
                        pstmt.setInt(++scc, candidateId);
                        pstmt.setInt(++scc, info.getToPositionId2());
                        pstmt.setString(++scc, info.getTop2rate1());
                        pstmt.setString(++scc, info.getTop2rate2());
                        pstmt.setString(++scc, info.getTop2rate3());
                        pstmt.setString(++scc, changeDate1(info.getJoiningDate2()));
                        pstmt.setString(++scc, changeDate1(info.getEndDate2()));
                        pstmt.setInt(++scc, 1);
                        pstmt.setInt(++scc, uId);
                        pstmt.setString(++scc, currDate1());
                        pstmt.setString(++scc, currDate1());
                        print(this, " insert dayrateh2 :: " + pstmt.toString());
                        cc = pstmt.executeUpdate(); 
                    }
                }
            }
        } catch (Exception exception) {
            print(this, "updateTransModal :: " + exception.getMessage());
            print(this, "updateTransModal :: " + pstmt.toString());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public Collection getClientAssetForTransfer(int clientId, String assetids, int allclient, String permission, int assetid) {
        Collection coll = new LinkedList();
        if (clientId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_clientid = ? AND i_clientassetid !=? AND i_status = 1 ");
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
            coll.add(new TalentpoolInfo(-1, " Select Asset "));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                pstmt.setInt(2, assetid);
                logger.info("getClientAsset :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new TalentpoolInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new TalentpoolInfo(-1, " Select Asset "));
        }
        return coll;
    }    
    
    public TalentpoolInfo getformalityFile(int candidateId, int templateId)
    {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_onboardingfileid, s_file FROM t_onboardingfiles WHERE i_status = 1 AND i_candidateid = ? AND i_templateid= ? ORDER BY ts_moddate DESC LIMIT 1");
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
            String filename;
            int onboardingfileid = 0;
            while (rs.next()) 
            {
                onboardingfileid = rs.getInt(1);
                filename = rs.getString(2) != null ? rs.getString(2) : "";
                info = new TalentpoolInfo(onboardingfileid, filename);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int getAssettypeIdForUpdate(int clientassetId, int candidateId) {
        int cc = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_assettypeid FROM t_candidate WHERE i_clientassetid =? AND i_candidateid = ? AND i_status = 1 ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, candidateId);
            print(this, "getAssettypeIdForUpdate :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                cc = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "getAssettypeIdForUpdate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public String getPpeNameAndId(int ppetypeId)
    {
        String str = "";
        if (ppetypeId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT s_description FROM t_ppetype WHERE i_ppetypeid = ? AND i_type = 2 ");
            String query = sb.toString();
            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, ppetypeId);
                logger.info("getPpeNameAndId :: " + pstmt.toString());
                rs = pstmt.executeQuery();               
                while (rs.next()) 
                {
                    str = rs.getString(1) != null ? rs.getString(1) : "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return str;
    }
    
    public String getPpeNameAndId2(int ppedetailId)
    {
        String str = "";
        if (ppedetailId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT s_remarks FROM t_ppedetail WHERE i_ppedetailid = ? ");
            String query = sb.toString();
            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, ppedetailId);
                logger.info("getPpeNameAndId2 :: " + pstmt.toString());
                rs = pstmt.executeQuery();               
                while (rs.next()) 
                {
                    str = rs.getString(1) != null ? rs.getString(1) : "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return str;
    }
    
    public String getColl(String val, String selectedval)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<select name='remark' class='form-control'>");    
        sb.append("<option value=''>Select</option>");    
        if(val != null && !val.equals(""))
        {
            String arr[] = val.split(",");
            int len = arr.length;
            for(int i = 0; i < len; i++)
            {
                if(arr[i].equalsIgnoreCase(selectedval))
                    sb.append("<option selected value='"+arr[i]+"'>"+arr[i]+"</option>");    
                else
                    sb.append("<option value='"+arr[i]+"'>"+arr[i]+"</option>");
            }
        }
        sb.append("</select>");    
        String s = sb.toString();
        sb.setLength(0);
        return s;
    }
    
    //For contract mail
    public int sendContractMail(String fromval, String toval, String ccval, String bccval, String subject, String description,
            String candidatename, String filename, String clientname, int candidateId, String username) throws MessagingException {
        String toaddress[] = parseCommaDelimString(toval);
        String ccaddress[] = parseCommaDelimString(ccval);
        String bccaddress[] = parseCommaDelimString(bccval);
        String filePath = getMainPath("add_candidate_file");
        String vfilePath = getMainPath("view_candidate_file");
        String attachmentpath = vfilePath + filename;
        String sdescription = description.replaceAll("\n", "<br/>");
        String mailbody = getpatchbody(sdescription, "mail_selection.html");

        String file_maillog = getMainPath("file_maillog");
        java.util.Date nowmail = new java.util.Date();
        String fn_mail = "Employment Contract-" + String.valueOf(nowmail.getTime());
        String filePath_html = createFolder(file_maillog);
        String fname = writeHTMLFile(mailbody, file_maillog + "/" + filePath_html, fn_mail + ".html");

        StatsInfo sinfo = postMailAttach(toaddress, ccaddress, bccaddress, mailbody, subject, filePath + filename, candidatename + "_Contract.pdf", -1);
        String from = "";
        if (sinfo != null) {
            from = sinfo.getDdlLabel();
        }

        int maillogId = createMailLog(18, clientname, toval, ccval, bccval, from, subject, filePath_html + "/" + fname, attachmentpath, candidateId, username, 1);

        return maillogId;
    }
    
    public String getpatchbody(String description, String htmlFile) {
        String adminpage = getMainPath("template_path") + htmlFile;
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("MAILBODY", description);
        return template.patch(hashmap);
    }
    
    public ArrayList getAppraisalListing(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_appraisal.i_appraisalid, t_position.i_positionid, t_position.s_name, t_grade.s_name, p2.i_positionid, ");
        sb.append("p2.s_name, g2.s_name, t_appraisal.s_remarks, DATE_FORMAT(t_appraisal.d_date, '%d-%b-%Y' ), ");
        sb.append("t_appraisal.i_status, t_appraisal.s_filename, t_appraisal.d_rate1, t_appraisal.d_rate2, t_appraisal.d_rate3, ");
        sb.append("DATE_FORMAT(t_appraisal.d_date2, '%d-%b-%Y' ) ");
        sb.append("FROM t_appraisal ");
        sb.append("LEFT JOIN t_position ON(t_position.i_positionid = t_appraisal.i_positionid1) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_position AS p2 ON(p2.i_positionid = t_appraisal.i_positionid2) ");
        sb.append("LEFT JOIN t_grade AS g2 ON (g2.i_gradeid = p2.i_gradeid) ");
        sb.append("WHERE t_appraisal.i_candidateid= ? ORDER BY t_appraisal.i_status, t_appraisal.d_date DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getAppraisalListing :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String position, position2, grade1, grade2,filename, remark, date, date2;
            int appraisalId, positionId, positionId2, status;
            double newRate1,newRate2, newRate3;
            while (rs.next()) 
            {
                appraisalId = rs.getInt(1);
                positionId = rs.getInt(2);
                position = rs.getString(3) != null ? rs.getString(3) : "";
                grade1 = rs.getString(4) != null ? rs.getString(4) : "";
                positionId2 = rs.getInt(5);
                position2 = rs.getString(6) != null ? rs.getString(6) : "";
                grade2 = rs.getString(7) != null ? rs.getString(7) : "";
                remark = rs.getString(8) != null ? rs.getString(8) : "";
                date = rs.getString(9) != null ? rs.getString(9) : "";
                status = rs.getInt(10);
                filename = rs.getString(11) != null ? rs.getString(11) : "";                
                newRate1 = rs.getDouble(12);
                newRate2 = rs.getDouble(13);
                newRate3 = rs.getDouble(14);
                date2 = rs.getString(15) != null ? rs.getString(15) : "";
                if(!grade1.equals(""))
                {
                    position += " | "+grade1;
                }
                if(!grade2.equals(""))
                {
                    position2 += " | "+grade2;
                }
                
                list.add(new TalentpoolInfo(appraisalId, positionId, position, positionId2, position2,
                        remark, date,status, filename, newRate1,newRate2, newRate3, date2,"", candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public TalentpoolInfo getCandidatePositions(int candidateId) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_position.i_positionid, t_position.s_name, t_grade.s_name, p2.i_positionid, ");
        sb.append("p2.s_name, g2.s_name, t_candidate.i_clientassetid ");
        sb.append("FROM t_candidate ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_candidate.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_position AS p2 ON (p2.i_positionid = t_candidate.i_positionid2) ");
        sb.append("LEFT JOIN t_grade AS g2 ON (g2.i_gradeid = p2.i_gradeid) ");
        sb.append("WHERE  t_candidate.i_candidateid= ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getCandidatePositions :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String position, position2, grade1, grade2;
            int positionId, positionId2,assetId;
            while (rs.next()) 
            {
                positionId = rs.getInt(1);
                position = rs.getString(2) != null ? rs.getString(2) : "";
                grade1 = rs.getString(3) != null ? rs.getString(3) : "";
                positionId2 = rs.getInt(4);
                position2 = rs.getString(5) != null ? rs.getString(5) : "";
                grade2 = rs.getString(6) != null ? rs.getString(6) : "";
                assetId = rs.getInt(7);
                if(!grade1.equals(""))
                {
                    position += " | "+grade1;
                }
                if(!grade2.equals(""))
                {
                    position2 += " | "+grade2;
                }
                
                info = new TalentpoolInfo(positionId, position, positionId2, position2, assetId);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }    
    
    public String getCandidatePositionsRate(int candidateId, int positionId, int clientAssetId) {
        String str = "";
        StringBuilder sb = new StringBuilder();
        if(positionId> 0)
        {
            sb.append("SELECT d_rate1, d_rate2, d_rate3 FROM t_dayrateh WHERE i_candidateid = ? AND i_clientassetid = ? AND i_positionid = ? ORDER BY d_fromdate DESC LIMIT 0,1 ");        
            String query = (sb.toString()).intern();
            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, candidateId);
                pstmt.setInt(2, clientAssetId);
                pstmt.setInt(3, positionId);
                logger.info("getCandidatePositionsRate :: " + pstmt.toString());
                rs = pstmt.executeQuery();    
                double rate1, rate2, rate3;
                while (rs.next()) 
                {
                    rate1 = rs.getDouble(1);
                    rate2 = rs.getDouble(2);
                    rate3 = rs.getDouble(3);
                    str = rate1+"@"+rate2+"@"+rate3;
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
            return str;
        }
        return str = ""+"@"+""+"@"+"";
    }
    
    public int checkDuplicacyAppraisal(int candidateId, int appraisalId, int positionId, int positionId2, String date) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_appraisalid FROM t_appraisal WHERE i_candidateid = ? AND i_positionid1 = ? AND i_positionid2 = ? AND d_date = ? AND i_status  IN (1, 2)");
        if (appraisalId > 0) {
            sb.append(" AND i_appraisalid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, positionId);
            pstmt.setInt(++scc, positionId2);
            pstmt.setString(++scc, changeDate1(date));
            if (appraisalId > 0) {
                pstmt.setInt(++scc, appraisalId);
            }
            print(this,"checkDuplicacyAppraisal :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkDuplicacyAppraisal :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }
    
    
    public int insertAppraisal(TalentpoolInfo info, int candidateId, int userId, int positionId2) {
        int id = 0;
        try 
        {
            conn = getConnection();
            String query = "SELECT i_appraisalid FROM t_appraisal WHERE i_candidateid = ? AND i_positionid2 = ? ORDER BY d_date DESC LIMIT 0,1 ";
            pstmt = conn.prepareStatement(query); 
            pstmt.setInt(1, candidateId);
            pstmt.setInt(2, positionId2);
            print(this," get i_appraisalid :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int apprisailId = 0;
            while (rs.next())
            {
                apprisailId = rs.getInt(1);
            }
            rs.close();

            if(apprisailId > 0 )
            {
                String newdate = getDateAfter(info.getFromDate(), 1, -1, "dd-MMM-yyyy", "dd-MMM-yyyy");
                query = "UPDATE t_appraisal SET d_date2 = ? WHERE i_appraisalid = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, changeDate1(newdate));
                pstmt.setInt(2, apprisailId);
                print(this,"UPDATE t_appraisal :: " + pstmt.toString());
                pstmt.executeUpdate();
            }
                
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_appraisal  ");
            sb.append("( i_candidateid, i_positionid1, i_positionid2, d_rate1, d_rate2, d_rate3, i_status, s_filename, d_date, d_date2, s_remarks, i_type, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?,   ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, info.getPositionId());
            if(info.getType() == 1)
                pstmt.setInt(++scc, info.getPositionId());
            else
                pstmt.setInt(++scc, info.getPositionId2());
            pstmt.setDouble(++scc, info.getRate1());
            pstmt.setDouble(++scc, info.getRate2());
            pstmt.setDouble(++scc, info.getRate3());
            pstmt.setInt(++scc, 1);
            pstmt.setString(++scc, info.getFilename());
            pstmt.setString(++scc, changeDate1(info.getFromDate()));
            pstmt.setString(++scc, changeDate1(info.getToDate()));
            pstmt.setString(++scc, (info.getRemarks()));
            pstmt.setInt(++scc, info.getType());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "insertAppraisal :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) 
            {
                id = rs.getInt(1);                
            }
        } catch (Exception exception) {
            print(this, "insertAppraisal :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return id;
    }
    
    public int updateAppraisalPosition(int positionId, int candidateId, int uId, int pid1, int pid2,int positionId2, int type) {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_candidate SET ");
            if(type <= 0)
            {
                if(positionId == pid1)
                {
                    sb.append("i_positionid = ?, ");
                }else if(positionId == pid2)
                {
                    sb.append("i_positionid2 = ?, ");
                }
            }
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ?  ");
            sb.append("WHERE i_candidateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if(type <= 0)
            {
                pstmt.setInt(++scc, positionId2);
            }
            pstmt.setInt(++scc, uId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, candidateId);
            
            print(this, "updateAppraisalPosition :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateAppraisalPosition :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }    
    
    public ArrayList getExcelDetails(String search, int statusIndex, int positionIndex,
            int clientIndex, int locationIndex, int assetIndex, int Verified, int employementIndex, int allclient,
            String permission, String cids, String assetids, int assettypeIdIndex, int positionFilter) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  c.i_candidateid, t_client.s_name, t_clientasset.s_name, c.s_empno, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), ");  
        sb.append("t_position.s_name,  t_grade.s_name , s_code1, c.s_contactno1, s_ecode1,  c.s_econtactno1, c.s_email, DATE_FORMAT(c.d_dob, '%d-%b-%Y'), "); 
        sb.append("c.s_gender, t_maritialstatus.s_name, t_country.s_name, t_city.s_name, c.s_address1line1, ");
        sb.append("c.s_address1line2, c.s_address1line3, t_country.s_nationality, ");
        sb.append("t_currency.s_name, t_dayrate.d_rate1, t_dayrate.d_rate2, t1.ct, t2.ct, t3.ct, t4.ct, t5.ct, t6.ct, t7.ct, c.s_code2, c.s_contactno2, ");
        sb.append("c.s_code3, c.s_contactno3, c.s_placeofbirth, c.s_nextofkin, t_relation.s_name, c.s_address2line1, c.s_address2line2, ");
        sb.append("c.s_address2line3, t_experiencedept.s_name, c.i_expectedsalary ");
        sb.append("FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_clientasset ON ( c.i_clientassetid = t_clientasset.i_clientassetid ) ");
        sb.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = t_clientasset.i_currencyid) ");
        sb.append("LEFT JOIN t_country  ON ( t_country.i_countryid = c.i_countryid ) ");
        sb.append("LEFT JOIN t_city ON ( c.i_cityid = t_city.i_cityid ) ");
        sb.append("LEFT JOIN t_position ON ( c.i_positionid = t_position.i_positionid ) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_client ON ( c.i_clientid = t_client.i_clientid) ");
        sb.append("LEFT JOIN t_maritialstatus ON (t_maritialstatus.i_maritialstatusid = c.i_maritialstatusid) ");
        sb.append("LEFT JOIN t_relation ON (t_relation.i_relationid = c.i_relationid) ");        
        sb.append("LEFT JOIN t_experiencedept ON (t_experiencedept.i_experiencedeptid = c.i_departmentid) ");    
        sb.append("LEFT JOIN t_dayrate ON (t_dayrate.i_candidateid = c.i_candidateid AND t_dayrate.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_candlang GROUP BY i_candidateid) AS t1 ON (t1.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_healthdeclaration GROUP BY i_candidateid) AS t2 ON (t2.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_vbd GROUP BY i_candidateid) AS t3 ON (t3.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_workexperience GROUP BY i_candidateid) AS t4 ON (t4.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_eduqual GROUP BY i_candidateid) AS t5 ON (t5.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_trainingandcert GROUP BY i_candidateid) AS t6 ON (t6.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_govdoc GROUP BY i_candidateid) AS t7 ON (t7.i_candidateid = c.i_candidateid) ");
        sb.append("WHERE 0 = 0  AND c.i_vflag IN  (3,4) AND c.i_pass = 2  ");       

        if (allclient == 1) {
            sb.append(" AND c.i_clientid >= 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND (c.i_clientid IN (" + cids + ") OR (c.i_clientid <= 0 AND c.i_progressid = 0) OR (c.i_progressid = 1 AND c.i_candidateid IN (SELECT i_candidateid FROM t_shortlist WHERE i_jobpostid IN (SELECT i_jobpostid FROM t_jobpost WHERE i_status = 1 AND i_clientid IN (" + cids + ") )))) ");
            } else {
                sb.append("AND t_client.i_clientid < 0 ");
            }
            if (!assetids.equals("")) {
                sb.append("AND (c.i_clientassetid IN (" + assetids + ") OR (c.i_clientassetid <= 0 AND c.i_progressid = 0) OR (c.i_progressid = 1 AND c.i_candidateid IN (SELECT i_candidateid FROM t_shortlist WHERE i_jobpostid IN (SELECT i_jobpostid FROM t_jobpost WHERE i_status = 1 AND i_clientassetid IN (" + assetids + ") )))) ");
            }
        }
        if (Verified > 0) {
            sb.append("AND c.i_vflag =4 ");
        }
        if (positionIndex > 0) {
            sb.append("AND c.i_positionid =? ");
        }
        if (clientIndex > 0) {
            sb.append("AND c.i_clientid = ? ");
        }
        if (locationIndex > 0) {
            sb.append("AND t_clientasset.i_countryid = ? ");
        }
        if (assetIndex > 0) {
            sb.append("AND c.i_clientassetid = ? ");
        }
        if (statusIndex > 0) {
            sb.append("AND c.i_status =? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? OR c.i_candidateid LIKE ? ");
            sb.append("OR t_country.s_name LIKE ? OR t_client.s_name LIKE ? OR t_position.s_name LIKE ? OR t_grade.s_name LIKE ? OR t_clientasset.s_name LIKE ? OR c.s_empno like ?) ");
        }
        if(employementIndex > 0)
        {
            if (employementIndex == 1) {
                sb.append("AND c.i_clientid = 0 AND c.i_status != 4 ");
            }
            if (employementIndex == 2) {
                sb.append("AND c.i_clientid >0 AND c.i_status != 4 ");
            }
            if (employementIndex == 3) {
                sb.append("AND c.i_status = 4 ");
            }
        }else if (employementIndex <= 0)
        {
            sb.append("AND c.i_status != 4 ");
        }
        if (assettypeIdIndex > 0) {
            sb.append("AND t_position.i_assettypeid = ? ");
        }
        if (positionFilter > 0) {
            sb.append("AND c.i_positionid = ? ");
        }
        sb.append("ORDER BY c.i_candidateid DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (positionIndex > 0) {
                pstmt.setInt(++scc, positionIndex);
            }
            if (clientIndex > 0) {
                pstmt.setInt(++scc, clientIndex);
            }
            if (locationIndex > 0) {
                pstmt.setInt(++scc, locationIndex);
            }
            if (assetIndex > 0) {
                pstmt.setInt(++scc, assetIndex);
            }
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (assettypeIdIndex > 0) {
                pstmt.setInt(++scc, assettypeIdIndex);
            }
            if (positionFilter > 0) {
                pstmt.setInt(++scc, positionFilter);
            }
            logger.info("getExcelDetails :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, countryName,positionName = "", gradeName, clientName, clientAsset, position, city,
                    primarycontact, econtact,  email, dob, gender, maritalstatus, address1line1, address1line2, address1line3,
                    employeeId, isdcode1, isdcode2, nationality, currency ;         
            int candidateId, ct1, ct2, ct3, ct4, ct5, ct6, ct7;
            double rate1, rate2;
            while (rs.next()) {
                candidateId = rs.getInt(1);
                clientName = rs.getString(2) != null ? rs.getString(2) : "";
                clientAsset = rs.getString(3) != null ? rs.getString(3) : "";
                employeeId = rs.getString(4) != null ? rs.getString(4) : "";
                name = rs.getString(5) != null ? rs.getString(5) : "";
                position = rs.getString(6) != null ? rs.getString(6) : "";
                gradeName = rs.getString(7) != null ? rs.getString(7) : "";
                isdcode1 = rs.getString(8) != null ? rs.getString(8) : "";
                primarycontact = decipher(rs.getString(9) != null ? rs.getString(9) : "");
                isdcode2 = rs.getString(10) != null ? rs.getString(10) : "";
                econtact = decipher(rs.getString(11) != null ? rs.getString(11) : "");
                email = rs.getString(12) != null ? rs.getString(12) : "";
                dob = rs.getString(13) != null ? rs.getString(13) : "";
                gender = rs.getString(14) != null ? rs.getString(14) : "";
                maritalstatus = rs.getString(15) != null ? rs.getString(15) : "";
                countryName = rs.getString(16) != null ? rs.getString(16) : "";
                city = rs.getString(17) != null ? rs.getString(17) : "";
                address1line1 = rs.getString(18) != null ? rs.getString(18) : "";
                address1line2 = rs.getString(19) != null ? rs.getString(19) : "";
                address1line3 = rs.getString(20) != null ? rs.getString(20) : "";
                nationality = rs.getString(21) != null ? rs.getString(21) : "";
                currency = rs.getString(22) != null ? rs.getString(22) : "";
                rate1 = rs.getDouble(23);
                rate2 = rs.getDouble(24);
                ct1 = rs.getInt(25);
                ct2 = rs.getInt(26);
                ct3 = rs.getInt(27);
                ct4 = rs.getInt(28);
                ct5 = rs.getInt(29);
                ct6 = rs.getInt(30);
                ct7 = rs.getInt(31);
                String contact2_code = rs.getString(32) != null ? rs.getString(32) : "";
                String contact2 = decipher(rs.getString(33) != null ? rs.getString(33) : "");
                String contact3_code = rs.getString(34) != null ? rs.getString(34) : "";
                String contact3 = decipher(rs.getString(35) != null ? rs.getString(35) : "");
                String placeofbirth = rs.getString(36) != null ? rs.getString(36) : "";
                String nextofkin = decipher(rs.getString(37) != null ? rs.getString(37) : "");
                String relation = rs.getString(38) != null ? rs.getString(38) : "";
                String address2line1 = rs.getString(39) != null ? rs.getString(39) : "";
                String address2line2 = rs.getString(40) != null ? rs.getString(40) : "";
                String address2line3 = rs.getString(41) != null ? rs.getString(41) : "";
                String experiencedept = rs.getString(42) != null ? rs.getString(42) : "";
                int expectedsalary = rs.getInt(43);                
                                
                if (gradeName != null && !gradeName.equals("")) {
                    positionName = position + " | " + gradeName;
                }
                if (primarycontact != null && !primarycontact.equals("")) {
                    if (isdcode1 != null && !isdcode1.equals("")) {
                        primarycontact = "+" + isdcode1 + " " + primarycontact;
                    }
                }
                if (econtact != null && !econtact.equals("")) {
                    if (isdcode2 != null && !isdcode2.equals("")) {
                        econtact = "+" + isdcode2 + " " + econtact;
                    }
                }
                if (contact2 != null && !contact2.equals("")) {
                    if (contact2_code != null && !contact2_code.equals("")) {
                        contact2 = "+" + contact2_code + " " + contact2;
                    }
                }
                if (contact3 != null && !contact3.equals("")) {
                    if (contact3_code != null && !contact3_code.equals("")) {
                        contact3 = "+" + contact3_code + " " + contact3;
                    }
                }
                list.add(new TalentpoolInfo(candidateId, clientName, clientAsset, employeeId, name,  positionName, 
                        primarycontact, econtact, email, dob, gender, maritalstatus,countryName, city, 
                        address1line1, address1line2,address1line3, nationality, currency, rate1, rate2,
                        ct1, ct2, ct3, ct4, ct5, ct6, ct7, contact2, contact3, placeofbirth, nextofkin, relation,
                        address2line1, address2line2, address2line3, experiencedept,expectedsalary ));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getAppraisalHistory(int appraisalId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_appraisal.i_appraisalid, t_position.i_positionid, t_position.s_name, t_grade.s_name, p2.i_positionid, ");
        sb.append("p2.s_name, g2.s_name, t_appraisal.s_remarks, DATE_FORMAT(t_appraisal.d_date, '%d-%b-%Y' ), ");
        sb.append("t_appraisal.i_status, t_appraisal.s_filename, t_appraisal.d_rate1, t_appraisal.d_rate2, t_appraisal.d_rate3, ");
        sb.append("DATE_FORMAT(t_appraisal.d_date2, '%d-%b-%Y') ");
        sb.append("FROM t_appraisal ");
        sb.append("LEFT JOIN t_position ON(t_position.i_positionid = t_appraisal.i_positionid1) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_position AS p2 ON(p2.i_positionid = t_appraisal.i_positionid2) ");
        sb.append("LEFT JOIN t_grade AS g2 ON (g2.i_gradeid = p2.i_gradeid) ");
        sb.append("WHERE t_appraisal.i_appraisalid= ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, appraisalId);
            logger.info("getAppraisalHistory :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String position, position2, grade1, grade2,filename, remark, date, date2;
            int positionId, positionId2, status;
            double newRate1,newRate2, newRate3;
            while (rs.next()) 
            {
                appraisalId = rs.getInt(1);
                positionId = rs.getInt(2);
                position = rs.getString(3) != null ? rs.getString(3) : "";
                grade1 = rs.getString(4) != null ? rs.getString(4) : "";
                positionId2 = rs.getInt(5);
                position2 = rs.getString(6) != null ? rs.getString(6) : "";
                grade2 = rs.getString(7) != null ? rs.getString(7) : "";
                remark = rs.getString(8) != null ? rs.getString(8) : "";
                date = rs.getString(9) != null ? rs.getString(9) : "";
                status = rs.getInt(10);
                filename = rs.getString(11) != null ? rs.getString(11) : "";
                newRate1 = rs.getDouble(12);
                newRate2 = rs.getDouble(13);
                newRate3 = rs.getDouble(14);
                date2 = rs.getString(15) != null ? rs.getString(15) : "";
                if(!grade1.equals(""))
                {
                    position += " | "+grade1;
                }
                if(!grade2.equals(""))
                {
                    position2 += " | "+grade2;
                }
                
                list.add(new TalentpoolInfo(appraisalId, positionId, position, positionId2, position2,remark, 
                        date,status, filename, newRate1,newRate2, newRate3, date2, "", 0));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public int createAppraisalExp(TalentpoolInfo info, int candidateId, int uId, TalentpoolInfo info3, int positionId) {
        int experiencedetailId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_workexperience (s_companyname, s_assetname, d_workstartdate, d_workenddate, ");
            sb.append("i_userid, i_candidateid, ts_regdate, ts_moddate, i_positionid,s_filenameexp, i_status, i_assettypeid ) ");
            sb.append("VALUES ( ?,?,?,?,?,  ?,?,?,?,?,  ?,?)");
            String query = sb.toString().intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, info.getClientName());
            pstmt.setString(2, info.getAssetName());
            pstmt.setString(3, changeDate1(info3.getDate()));
            pstmt.setString(4, "0000-00-00");
            pstmt.setInt(5, uId);
            pstmt.setInt(6, candidateId);
            pstmt.setString(7, currDate1());
            pstmt.setString(8, currDate1());            
            pstmt.setInt(9, positionId);
            pstmt.setString(10, info3.getFilename());
            pstmt.setInt(11, 1);
            pstmt.setInt(12, info.getAssettypeId());
            pstmt.executeUpdate();
            logger.info("createAppraisalExp :: " + pstmt.toString());
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                experiencedetailId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createTerminateExp :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return experiencedetailId;
    }
    
    public ArrayList getDayrateListing(int candidateId)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_dayrateh.i_dayratehid, t_position.s_name, DATE_FORMAT(t_dayrateh.d_fromdate, '%d-%b-%Y'), ");
        sb.append("DATE_FORMAT(t_dayrateh.d_todate, '%d-%b-%Y'), t_dayrateh.d_rate1, t_dayrateh.d_rate2, t_dayrateh.d_rate3, ");
        sb.append("t_grade.s_name, t_dayrateh.i_positionid "); 
        sb.append("FROM t_dayrateh ");
        sb.append("LEFT JOIN t_position ON(t_position.i_positionid = t_dayrateh.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("WHERE t_dayrateh.i_candidateid = ? ORDER BY t_dayrateh.d_fromdate DESC");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getDayrateListing :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String fromDate, toDate, position, grade;
            int dayrateId, positionId;
            double rate1, rate2, rate3;
            while (rs.next())
            {
                dayrateId = rs.getInt(1);
                position = rs.getString(2) != null ? rs.getString(2) : "";
                fromDate = rs.getString(3) != null ? rs.getString(3) : "";
                toDate = rs.getString(4) != null ? rs.getString(4) : "";
                rate1 = rs.getDouble(5);
                rate2 = rs.getDouble(6);
                rate3 = rs.getDouble(7);
                grade = rs.getString(8) != null ? rs.getString(8) : "";
                positionId = rs.getInt(9);
                if (!grade.equals("")) {
                    position += " | " + grade;
                }
                list.add(new TalentpoolInfo(dayrateId, position, fromDate, toDate, rate1, rate2, rate3, positionId,"", candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public TalentpoolInfo getDayRateForEdit(int dayratehId)
    {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT d_rate1, d_rate2, d_rate3, DATE_FORMAT(d_fromdate, '%d-%b-%Y'), ");
        sb.append("DATE_FORMAT(d_todate, '%d-%b-%Y'), i_positionid FROM t_dayrateh WHERE i_dayratehid = ?");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, dayratehId);
            print(this, "getDayRateForEdit :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            double rate1, rate2, rate3;
            String fromDate, toDate;
            int positionId;
            while (rs.next()) 
            {
                rate1 = rs.getDouble(1);
                rate2 = rs.getDouble(2);
                rate3 = rs.getDouble(3);
                fromDate = rs.getString(4) != null ? rs.getString(4): "";
                toDate = rs.getString(5) != null ? rs.getString(5): "";
                positionId = rs.getInt(6);
                info = new TalentpoolInfo(dayratehId,rate1, rate2, rate3, fromDate, toDate, positionId);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int checkDayRateId(int candidateId, int positionId, int assetId) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_dayrateid FROM t_dayrate WHERE i_candidateid = ? AND i_positionid = ? AND i_clientassetid = ?");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, positionId);
            pstmt.setInt(++scc, assetId);
            print(this,"checkDayRateId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                ck = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "checkDayRateId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }
    
    public int updateCandidateDayrate(double rate1, double rate2, double rate3, int userId,
            int dayratehId, int dayrateId, String fromdate, String todate, int positionId)
    {
        int cc = 0;
        try
        {
            String currdate = currDate1();
            conn = getConnection();
            String query = "UPDATE t_dayrateh SET d_rate1 = ?, d_rate2 = ?, d_rate3 = ?, d_fromdate = ?, d_todate = ?, i_positionid = ? WHERE i_dayratehid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setDouble(1, rate1);
            pstmt.setDouble(2, rate2);
            pstmt.setDouble(3, rate3);
            pstmt.setString(4, changeDate1(fromdate));
            pstmt.setString(5, changeDate1(todate));
            pstmt.setInt(6, positionId);
            pstmt.setInt(7, dayratehId);
            
            print(this,"UPDATE t_dayrateh :: " + pstmt.toString());
            cc += pstmt.executeUpdate();
            
            if(dayrateId > 0)
            {
                StringBuilder sb = new StringBuilder();
                sb.append("UPDATE t_dayrate SET d_rate1 = ?, d_rate2 = ?, d_rate3 = ?, i_userid = ?, ts_moddate = ?, d_fromdate = ?, d_todate = ?, i_positionid = ? WHERE i_dayrateid = ? ");
                query = sb.toString();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);                    
                pstmt.setDouble(1, rate1);
                pstmt.setDouble(2, rate2);
                pstmt.setDouble(3, rate3);
                pstmt.setInt(4, userId);
                pstmt.setString(5, currdate);
                pstmt.setString(6, changeDate1(fromdate));
                pstmt.setString(7, changeDate1(todate));
                pstmt.setInt(8, positionId);
                pstmt.setInt(9, dayrateId);
                print(this,"updateDayrate :: " + pstmt.toString());
                cc += pstmt.executeUpdate();
            }
        }
        catch (Exception e)
        {
            print(this,"updateCandidateDayrate :: " + e.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }    
    
    public TalentpoolInfo getDayRateForTransfer(int candidateId, int clientassetId, int positionId)
    {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT d_rate1, d_rate2, d_rate3 FROM t_dayrate WHERE i_candidateid = ? AND i_clientassetid = ? AND i_positionid =? ");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            pstmt.setInt(2, clientassetId);
            pstmt.setInt(3, positionId);
            print(this, "getDayRateForTransfer :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            double rate1, rate2, rate3;
            while (rs.next()) 
            {
                rate1 = rs.getDouble(1);
                rate2 = rs.getDouble(2);
                rate3 = rs.getDouble(3);
                info = new TalentpoolInfo(rate1, rate2, rate3);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int checkDuplicacyDayrate(int candidateId, String fromDate, String toDate, int clientassetId, int positionId, int dayaretId)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_dayratehid FROM t_dayrateh WHERE i_clientassetid = ? AND i_candidateid= ? AND i_positionid= ?  ");        
        sb.append("AND (( ? >= d_fromdate AND ? <= d_todate )  ");        
        sb.append("OR ( ? >= d_fromdate AND  ? <= d_todate )  ");    
        sb.append("OR ( ? <= d_fromdate )) ");    
        if(dayaretId > 0)
        {
            sb.append("AND i_dayratehid != ? "); 
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;              
            pstmt.setInt(++scc, clientassetId);
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, positionId);
            pstmt.setString(++scc, changeDate1(fromDate));
            pstmt.setString(++scc, changeDate1(fromDate));
            pstmt.setString(++scc, changeDate1(toDate));
            pstmt.setString(++scc, changeDate1(toDate));
            pstmt.setString(++scc, changeDate1(fromDate));   
            if(dayaretId > 0)
            {
                pstmt.setInt(++scc, dayaretId);
            }
            print(this,"checkDuplicacyDayrate :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                ck = 1;
            }
        }
        catch (Exception exception)
        {
            print(this,"checkDuplicacyDayrate :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return ck;
    }
    
    public int deleteRate(int candidateId, int positionId, int assetId, String fromDate, String toDate) 
    {
        int cc = 0;
        String query = "DELETE FROM t_dayrateh WHERE i_candidateid = ? AND i_positionid = ? AND i_clientassetid = ? AND (d_fromdate > ? OR d_todate > ?)";
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            pstmt.setInt(2, positionId);
            pstmt.setInt(3, assetId);
            pstmt.setString(4, fromDate);
            pstmt.setString(5, toDate);
            logger.info("deleteRate :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteRate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public Collection getDocumentTypesForModule(int mid, int smid)
    {
        Collection coll = new LinkedList();
        coll.add(new CandidateInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("documenttype.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    String mids = jobj.optString("docmodule");
                    String msids = jobj.optString("docsubmodule");
                    if(mids.contains(""+mid) && msids.contains(""+smid)) 
                        coll.add(new CandidateInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
}
