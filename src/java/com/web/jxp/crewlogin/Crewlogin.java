package com.web.jxp.crewlogin;

import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
import com.web.jxp.base.Template;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import static com.web.jxp.common.Common.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Logger;

public class Crewlogin extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public String generateotp() {
        String chars = "0123456789";
        int numberOfCodes = 0;
        String code = "";
        while (numberOfCodes < 6) {
            char c = chars.charAt((int) (Math.random() * chars.length()));
            code += " " + c;
            numberOfCodes++;
        }
        return code;
    }

    public static String getDateAfter(String date, int type, int no, String sformatv, String endformatv) {
        String dt = "";
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sformat = new SimpleDateFormat(sformatv);
            SimpleDateFormat endformat = new SimpleDateFormat(endformatv);
            long l1 = sformat.parse(date).getTime();
            cal.setTimeInMillis(l1);
            switch (type) {
                case 1:
                    cal.add(Calendar.DATE, no);
                    break;
                case 2:
                    cal.add(Calendar.MONTH, no);
                    break;
                case 3:
                    cal.add(Calendar.YEAR, no);
                    break;
                case 4:
                    cal.add(Calendar.HOUR, no);
                    break;
                case 5:
                    cal.add(Calendar.MINUTE, no);
                    break;
                default:
                    break;
            }
            dt = endformat.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public int insertOTP(String emailId, String otp, String date) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_otp ");
            sb.append("(s_emailid, s_otpvalue, d_expirydate, ts_regdate) ");
            sb.append("values (?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (emailId));
            pstmt.setString(++scc, otp);
            pstmt.setString(++scc, date);
            pstmt.setString(++scc, currDate1());
//            print(this,"insertOTP :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                cc = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createCrewlogin :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public String getOTPMessage(String otp, String htmlFile) {
        String adminpage = getMainPath("template_path") + htmlFile;
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("OTP", otp);
        return template.patch(hashmap);
    }

    public int checkOTP(String emailId, String otp) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_otpid FROM t_otp where s_emailid = ? and s_otpvalue = ? and d_expirydate > now() ");

        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, emailId);
            pstmt.setString(++scc, otp);
            print(this, "checkOTP :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkOTP :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public boolean checkOTPByAPI(String emailId, String otpcode, int otpId) {
        boolean bval = false;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_otpid FROM t_otp WHERE s_emailid =? ");
        if (otpcode != null && !otpcode.equals("")) {
            sb.append("AND s_otpvalue =? ");
        }
        if (otpId > 0) {
            sb.append("AND i_otpid =? ");
        }
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, emailId);
            if (otpcode != null && !otpcode.equals("")) {
                pstmt.setString(++scc, otpcode);
            }
            if (otpId > 0) {
                pstmt.setInt(++scc, otpId);
            }
            print(this, "checkOTPByAPI :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                bval = true;
            }
        } catch (Exception exception) {
            print(this, "checkOTPByAPI :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return bval;
    }

    public CrewloginInfo getCandidateInfo(String emailId) {
        CrewloginInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT cr.i_crewrotationid, c.i_candidateid, c.i_positionid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), ");
        sb.append("cr.i_clientid, cr.i_clientassetid, t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name ");
        sb.append("FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = c.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = c.i_clientassetid) ");
        sb.append("LEFT JOIN t_crewrotation AS cr ON (c.i_candidateid = cr.i_candidateid AND cr.i_active =1)  ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid)  ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid)  ");
        sb.append("WHERE c.s_email = ? AND c.i_status =1 ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, emailId);
            print(this, "getCandidateInfo :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int crewrotationId = 0, candidateId = 0, positionId = 0, clientId = 0, clientassetId = 0;
            String name = "", clientname = "", assetname = "", position = "", grade = "";
            while (rs.next()) {
                crewrotationId = rs.getInt(1);
                candidateId = rs.getInt(2);
                positionId = rs.getInt(3);
                name = rs.getString(4) != null ? rs.getString(4) : "";
                clientId = rs.getInt(5);
                clientassetId = rs.getInt(6);
                clientname = rs.getString(7) != null ? rs.getString(7) : "";
                assetname = rs.getString(8) != null ? rs.getString(8) : "";
                position = rs.getString(9) != null ? rs.getString(9) : "";
                grade = rs.getString(10) != null ? rs.getString(10) : "";
//                if (!position.equals("")) {
//                    if (!grade.equals("")) {
//                        position += " | " + grade;
//                    }
//                }
                info = new CrewloginInfo(crewrotationId, candidateId, positionId, name, emailId, clientId, clientassetId, clientname, assetname, position);
            }
        } catch (Exception exception) {
            print(this, "getCandidateId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public CrewloginInfo getHeightAndWeight(int id) {
        CrewloginInfo info = null;
        if (id > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT d_height, d_weight FROM t_healthdeclaration WHERE i_candidateid = ? AND i_status =1 ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, id);
                print(this, "getHeightAndWeight :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    double height = rs.getDouble(1);
                    double weight = rs.getDouble(2);
                    info = new CrewloginInfo(height, weight);
                }
            } catch (Exception exception) {
                print(this, "getHeightAndWeight :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return info;
    }

    public ArrayList getWellnessDetails(int id, String fromDate, String toDate) {
        ArrayList list = new ArrayList();
        if (id > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_mobwellnessid, i_dailysteps, TIME_FORMAT(ts_sleeptime,'%H:%i'), TIME_FORMAT(ts_wakeuptime,'%H:%i'), ");
            sb.append("d_sleephours, d_activityhours, d_standhours, DATE_FORMAT(ts_regdate,'%d-%m-%Y') FROM t_mobwellness ");
            sb.append("WHERE i_candidateid = ? AND i_status =1 ");
            if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
                sb.append("AND (DATE_FORMAT(ts_regdate,'%Y-%m-%d') >= ? AND DATE_FORMAT(ts_regdate,'%Y-%m-%d') <=?)  ");
            }
            sb.append("ORDER BY i_mobwellnessid ");

            String query = (sb.toString()).intern();
            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, id);
                if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
                    pstmt.setString(2, changeDate6(fromDate));
                    pstmt.setString(3, changeDate6(toDate));
                }
                print(this, "getWellnessDetails :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int mobwellnessid, dailysteps, scc;
                double sleephours, activityhours, standhours;
                String sleeptime, wakeuptime, date;
                while (rs.next()) {
                    scc = 0;
                    mobwellnessid = rs.getInt(++scc);
                    dailysteps = rs.getInt(++scc);
                    sleeptime = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    wakeuptime = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    sleephours = rs.getDouble(++scc);
                    activityhours = rs.getDouble(++scc);
                    standhours = rs.getDouble(++scc);
                    date = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    list.add(new CrewloginInfo(mobwellnessid, dailysteps, sleeptime, wakeuptime, sleephours, activityhours, standhours, date));
                }
            } catch (Exception exception) {
                print(this, "getWellnessDetails :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return list;
    }

    public int doSaveWellnessDetails(int id, int dailysteps, String sleeptime, String wakeuptime, double sleephours,
            double activityhours, double standhours, int userId) {
        int cc = 0;
        if (id > 0 && (dailysteps > 0 || (sleeptime != null && !sleeptime.isEmpty()) || (wakeuptime != null && !wakeuptime.isEmpty())
                || sleephours > 0 || activityhours > 0 || standhours > 0)) {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_mobwellness SET ");
            if (dailysteps > 0) {
                sb.append("i_dailysteps =?, ");
            }
            if (sleeptime != null && !sleeptime.isEmpty()) {
                sb.append("ts_sleeptime =?, ");
            }
            if (wakeuptime != null && !wakeuptime.isEmpty()) {
                sb.append("ts_wakeuptime =?, ");
            }
            if (sleephours > 0) {
                sb.append("d_sleephours =?, ");
            }
            if (activityhours > 0) {
                sb.append("d_activityhours =?, ");
            }
            if (standhours > 0) {
                sb.append("d_standhours =?, ");
            }
            sb.append("i_userid =? ");
            sb.append("WHERE i_mobwellnessid =?");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                if (dailysteps > 0) {
                    pstmt.setInt(++scc, dailysteps);
                }
                if (sleeptime != null && !sleeptime.isEmpty()) {
                    pstmt.setString(++scc, sleeptime);
                }
                if (wakeuptime != null && !wakeuptime.isEmpty()) {
                    pstmt.setString(++scc, wakeuptime);
                }
                if (sleephours > 0) {
                    pstmt.setDouble(++scc, sleephours);
                }
                if (activityhours > 0) {
                    pstmt.setDouble(++scc, activityhours);
                }
                if (standhours > 0) {
                    pstmt.setDouble(++scc, standhours);
                }
                pstmt.setInt(++scc, userId);
                pstmt.setInt(++scc, id);
                print(this, "doSaveWellnessDetails :: " + pstmt.toString());
                cc = pstmt.executeUpdate();
            } catch (Exception exception) {
                print(this, "doSaveWellnessDetails :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return cc;
    }

    public int doSaveScheduleDetails(int userId, String sleeptime, String wakeuptime, double sleephours,
            double activityhours, double standhours) {
        int cc = 0, id = 0;
        if (userId > 0) {
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement("SELECT i_mobwellnessscheduleid FROM t_mobwellnessschedule WHERE i_candidateid =? ");
                pstmt.setInt(1, userId);
                print(this, "doSaveScheduleDetails :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
                pstmt.close();
                rs.close();

                String query = "";
                StringBuilder sb = new StringBuilder();

                if (id > 0 && ((sleeptime != null && !sleeptime.isEmpty()) || (wakeuptime != null && !wakeuptime.isEmpty())
                        || sleephours > 0 || activityhours > 0 || standhours > 0)) {
                    sb.append("UPDATE t_mobwellnessschedule SET ");
                    if (sleeptime != null && !sleeptime.isEmpty()) {
                        sb.append("ts_sleeptime =?, ");
                    }
                    if (wakeuptime != null && !wakeuptime.isEmpty()) {
                        sb.append("ts_wakeuptime =?, ");
                    }
                    if (sleephours > 0) {
                        sb.append("d_sleephours =?, ");
                    }
                    if (activityhours > 0) {
                        sb.append("d_activityhours =?, ");
                    }
                    if (standhours > 0) {
                        sb.append("d_standhours =?, ");
                    }
                    sb.append("i_userid =? ");
                    sb.append("WHERE i_mobwellnessscheduleid =?");
                    query = (sb.toString()).intern();
                    sb.setLength(0);
                    pstmt = conn.prepareStatement(query);
                    int scc = 0;
                    if (sleeptime != null && !sleeptime.isEmpty()) {
                        pstmt.setString(++scc, sleeptime);
                    }
                    if (wakeuptime != null && !wakeuptime.isEmpty()) {
                        pstmt.setString(++scc, wakeuptime);
                    }
                    if (sleephours > 0) {
                        pstmt.setDouble(++scc, sleephours);
                    }
                    if (activityhours > 0) {
                        pstmt.setDouble(++scc, activityhours);
                    }
                    if (standhours > 0) {
                        pstmt.setDouble(++scc, standhours);
                    }
                    pstmt.setInt(++scc, userId);
                    pstmt.setInt(++scc, id);
                    print(this, "doSaveScheduleDetails :: " + pstmt.toString());
                    cc = pstmt.executeUpdate();
                } else if ((sleeptime != null && !sleeptime.isEmpty()) || (wakeuptime != null && !wakeuptime.isEmpty())
                        || sleephours > 0 || activityhours > 0 || standhours > 0) {
                    sb.append("INSERT INTO t_mobwellnessschedule (ts_sleeptime, ts_wakeuptime, d_sleephours, d_activityhours ");
                    sb.append("d_standhours, i_userid, i_candidateid, ts_regdate, ts_moddate) VALUES(?, ?, ?, ?, ?,  ?, ?, ?, ?)");
                    query = (sb.toString()).intern();
                    sb.setLength(0);
                    pstmt = conn.prepareStatement(query);
                    int scc = 0;
                    pstmt.setString(++scc, sleeptime);
                    pstmt.setString(++scc, wakeuptime);
                    pstmt.setDouble(++scc, sleephours);
                    pstmt.setDouble(++scc, activityhours);
                    pstmt.setDouble(++scc, standhours);
                    pstmt.setInt(++scc, userId);
                    pstmt.setInt(++scc, userId);
                    pstmt.setString(++scc, currDate1());
                    pstmt.setString(++scc, currDate1());
                    print(this, "doSaveScheduleDetails :: " + pstmt.toString());
                    cc = pstmt.executeUpdate();
                }
            } catch (Exception exception) {
                print(this, "doSaveScheduleDetails :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return cc;
    }

    public CrewloginInfo getWellnessDetailsById(int id) {
        CrewloginInfo info = null;
        if (id > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT TIME_FORMAT(ts_sleeptime,'%H:%i'), TIME_FORMAT(ts_wakeuptime,'%H:%i'), ");
            sb.append("d_sleephours, d_activityhours, d_standhours FROM t_mobwellnessschedule ");
            sb.append("WHERE i_candidateid =? AND i_status =1 ");

            String query = (sb.toString()).intern();
            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, id);
                print(this, "getWellnessDetailsById :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int scc;
                double sleephours, activityhours, standhours;
                String sleeptime, wakeuptime;
                while (rs.next()) {
                    scc = 0;
                    sleeptime = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    wakeuptime = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    sleephours = rs.getDouble(++scc);
                    activityhours = rs.getDouble(++scc);
                    standhours = rs.getDouble(++scc);
                    info = new CrewloginInfo(0, 0, sleeptime, wakeuptime, sleephours, activityhours, standhours, "");
                }
            } catch (Exception exception) {
                print(this, "getWellnessDetailsById :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return info;
    }
}
