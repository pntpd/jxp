
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.TimeZone;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author wesld
 */
public class AutoSingonSingoff {

    Properties database = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public AutoSingonSingoff() {
        database = new Properties();
        InputStream in = null;
        try {
            in = getClass().getClassLoader().getResourceAsStream("database.properties");
            database.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public Connection getConnection() {
        Connection conn1;
        String DriverClassName = database.getProperty("DriverClassName");
        String dburl = database.getProperty("dburl");
        String dbusername = database.getProperty("dbusername");
        String dbpassword = database.getProperty("dbpassword");
        try {
            Class.forName(DriverClassName);
            conn1 = DriverManager.getConnection(dburl, dbusername, dbpassword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(System.out);
            return null;
        }
        return conn1;
    }

    public void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public static void main(String args[]) {
        Connection conn;
        PreparedStatement pstmtInner = null;
        ResultSet rsInner = null;
        AutoSingonSingoff obj = new AutoSingonSingoff();
        conn = obj.getConnection();
        try {
            StringBuilder sb = new StringBuilder();
            int scc, crId, status1, status2, noofdays, rotationoverstay, positionId;
            String expdate, newexpdate;
            sb.append("SELECT t_crewrotation.i_crewrotationid, DATE_FORMAT(t_crewrotation.ts_expecteddate,'%Y-%m-%d %H:%i:%s'), t_crewrotation.i_status1, t_crewrotation.i_status2, ");
            sb.append("t_rotation.i_noofdays, t_position.i_rotationoverstay, t_position.i_positionid FROM t_crewrotation ");
            sb.append("LEFT JOIN t_candidate AS c ON (c.i_candidateid = t_crewrotation.i_candidateid)");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid)");
            sb.append("LEFT JOIN t_rotation ON (t_position.i_rotationid = t_rotation.i_rotationid)");
            sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_crewrotation.i_clientassetid)");
            sb.append("WHERE t_clientasset.i_mflag = 2 AND DATE_FORMAT(ts_expecteddate, '%Y-%m-%d') = CURRENT_DATE()");
            String query1 = sb.toString().intern();
            sb.setLength(0);
            pstmtInner = conn.prepareStatement(query1);
            System.out.println(" Main ::" + pstmtInner.toString());
            rsInner = pstmtInner.executeQuery();
            while (rsInner.next()) {
                scc = 0;
                crId = 0;
                crId = rsInner.getInt(++scc);
                expdate = rsInner.getString(++scc) != null ? rsInner.getString(scc) : "";
                status1 = rsInner.getInt(++scc);
                status2 = rsInner.getInt(++scc);
                noofdays = rsInner.getInt(++scc);
                rotationoverstay = rsInner.getInt(++scc);
                positionId = rsInner.getInt(++scc);
//                System.out.println(crId + " # " + expdate + " # " + status1 + " # " + status2 + " # " + noofdays + " # " + rotationoverstay);
//------------------------------------------------------------------------------------------------------
                newexpdate = obj.getDateAfter(expdate, 1, noofdays, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                if (status1 > 0) {
                    int signonId = obj.savesignonoff(crId, expdate, 1, "", "0000-00-00 00:00:00", 1, -1, conn, positionId);
                    obj.updatecrewrotationsignoff(crId, expdate, newexpdate, -1, "System", conn, 0, 1);
                    obj.insertcrewActivitysignon(crId, expdate.substring(0, 10), newexpdate, 6, "", -1, 2, signonId, noofdays, rotationoverstay, conn, positionId);
                } else if (status2 > 0) {
                    int signoffId = obj.savesignonoff(crId, expdate, 1, "", "0000-00-00 00:00:00", 2, -1, conn, positionId);
                    obj.updatecrewrotationsignoff(crId, expdate, newexpdate, -1, "System", conn, 1, 0);
                    obj.updatecractivitysignoff(crId, expdate.substring(0, 10), signoffId, -1, 1, "", "", 0, conn);
                }
            }
            pstmtInner.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            obj.close(conn, pstmtInner, rsInner);
        }
    }

    public int savesignonoff(int crewrotationId, String edate, int rdateId, String remarks, String sdate, int type, int userId, Connection conn, int positionId) {
        int id = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_signonoff ");
            sb.append("(i_crewrotationid, ts_date1, s_remarks, ts_date2, i_type, i_subtype, i_userid, ts_regdate, i_positionid) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setString(++scc, (edate));
            pstmt.setString(++scc, remarks);
            pstmt.setString(++scc, (sdate));
            pstmt.setInt(++scc, type);
            pstmt.setInt(++scc, rdateId);
            pstmt.setInt(++scc, userId);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, positionId);
            System.out.println("savesignonoff :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception exception) {
            System.out.println("savesignonoff :: " + exception.getMessage());
        } finally {
            close(null, pstmt, rs);
        }
        return id;
    }

    public void updatecrewrotationsignoff(int crewrotationId, String date, String expecteddate, int userId, String username, Connection conn, int status1, int status2) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_crewrotation SET ");
            if (status1 > 0) {
                sb.append("ts_signoff = ? ,");
                sb.append("s_signofflby = ? ,");
                sb.append("i_docflag =1 ,");
            } else {
                sb.append("ts_signon = ? ,");
                sb.append("s_signonlby = ? ,");
                sb.append("i_docflag =2 ,");
            }
            sb.append("i_status1 = ? ,");
            sb.append("i_status2 = ? ,");
            sb.append("ts_expecteddate = ? ,");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_crewrotationid = ? ");
            String candidatequery = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(candidatequery);
            int scc = 0;
            pstmt.setString(++scc, date);
            pstmt.setString(++scc, username);
            pstmt.setInt(++scc, status1);
            pstmt.setInt(++scc, status2);
            pstmt.setString(++scc, expecteddate);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, crewrotationId);
            System.out.println("updatecrewrotationsignoff :: " + pstmt.toString());
            pstmt.executeUpdate();

        } catch (Exception exception) {
            System.out.println("updatecrewrotationsignoff :: " + exception.getMessage());
        } finally {
            close(null, pstmt, null);
        }
    }

    public void insertcrewActivitysignon(int crewrotationId, String fromdate, String enddate, int activityId,
            String remarks, int userId, int status, int signonId, int noofdays, int overstaydays, Connection conn, int positionId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_cractivity ");
            sb.append("(i_crewrotationid, ts_fromdate ,ts_todate, s_remarks, i_activityid, i_extendeddays, i_overstaydays, i_status, i_signonid, i_userid, ts_regdate, i_positionid) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setString(++scc, (fromdate));
            pstmt.setString(++scc, (enddate));
            pstmt.setString(++scc, remarks);
            pstmt.setInt(++scc, activityId);
            pstmt.setInt(++scc, noofdays);
            pstmt.setInt(++scc, overstaydays);
            pstmt.setInt(++scc, status);
            pstmt.setInt(++scc, signonId);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, positionId);
            System.out.println("insertcrewActivitysignon :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
        } catch (Exception exception) {
            System.out.println("insertcrewActivitysignon :: " + exception.getMessage());
        } finally {
            close(null, pstmt, rs);
        }
    }

    public void updatecractivitysignoff(int crewrotationId, String date, int signoffId, int userId, int rdateId, String sdate,
            String edate, int overstaydays, Connection conn) {
        try {
            StringBuilder sb1 = new StringBuilder();
            sb1.append("SELECT i_cractivityid, DATE_FORMAT( ts_fromdate, '%d-%b-%Y') FROM t_cractivity WHERE i_activityid =6 AND i_crewrotationid =? ");
            sb1.append("ORDER BY ts_fromdate DESC LIMIT 0,1");
            String cquery = (sb1.toString()).intern();
            sb1.setLength(0);
            pstmt = conn.prepareStatement(cquery);
            pstmt.setInt(1, crewrotationId);

            rs = pstmt.executeQuery();
            int cractivityId = 0;
            while (rs.next()) {
                cractivityId = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_cractivity SET ");
            sb.append("i_signoffid =?, ");
            sb.append("ts_todate =?, ");
            sb.append("ts_moddate =? ,");
            sb.append("i_status =2 ");
            sb.append("WHERE i_cractivityid =? ");
            String candidatequery = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(candidatequery);
            int scc = 0;
            pstmt.setInt(++scc, signoffId);
            pstmt.setString(++scc, date);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, cractivityId);
            System.out.println("updatecractivitysignoff :: " + pstmt.toString());
            pstmt.executeUpdate();

        } catch (Exception exception) {
            System.out.println("updatecractivitysignoff :: " + exception.getMessage());
        } finally {
            close(null, pstmt, rs);
        }
    }

    public String currDate1() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        java.util.Date dt = cal.getTime();
        return sdf.format(dt);
    }

    public String changeDate3(String date) {
        String str = "0000-00-00 00:00";
        try {
            if (date != null && !date.equals("") && !date.equals("00-00-0000")) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                str = sdf2.format(sdf1.parse(date));
            }
        } catch (Exception e) {
            System.out.println("Error in changeDate3 :: " + e.getMessage());
        }
        return str;
    }

    public int getDiffTwoDateInt(String sdate, String edate, String sformatv) {
        int diff = 0;
        try {
            if (sdate != null && !sdate.equals("") && !sdate.equals("00-00-0000") && edate != null && !edate.equals("") && !edate.equals("00-00-0000")) {
                SimpleDateFormat sdf = new SimpleDateFormat(sformatv);
                long l1 = sdf.parse(sdate).getTime();
                long l2 = sdf.parse(edate).getTime();
                long d = l2 - l1;
                long div = (long) 24 * 60 * 60 * 1000;
                long f1 = d / div;
                diff = (int) f1;
                diff = diff + 1;
            }
        } catch (Exception e) {
            diff = 0;
        }
        return diff;
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
                default:
                    break;
            }
            dt = endformat.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public String changeDate1(String date) {
        String str = "0000-00-00";
        try {
            if (date != null && !date.equals("")) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                str = sdf2.format(sdf1.parse(date));
            }
        } catch (Exception e) {
            System.out.println("Error in changeDate1 :: " + e.getMessage());
        }
        return str;
    }
}
