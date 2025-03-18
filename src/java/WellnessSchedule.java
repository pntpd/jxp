
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.TimeZone;

public class WellnessSchedule {

    Properties database = null;

    public WellnessSchedule() {
        database = new Properties();
        InputStream in = null;
        try {
            in = getClass().getClassLoader().getResourceAsStream("database.properties");
            database.load(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public Connection getConnection() {
        Connection conn1 = null;
        String DriverClassName = database.getProperty("DriverClassName");
        String dburl = database.getProperty("dburl");
        String dbusername = database.getProperty("dbusername");
        String dbpassword = database.getProperty("dbpassword");
        try {
            Class.forName(DriverClassName);
            conn1 = DriverManager.getConnection(dburl, dbusername, dbpassword);
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public String currDate1() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        java.util.Date dt = cal.getTime();
        return sdf.format(dt);
    }

    public String currDate2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        java.util.Date dt = cal.getTime();
        return sdf.format(dt);
    }

    public static void main(String args[]) {
        Connection conn = null;
        PreparedStatement pstmt = null, sel_pstmt = null, insert_pstmt = null;
        ResultSet rs = null, sel_rs = null;
        WellnessSchedule obj = new WellnessSchedule();
        try {
            String currdate1 = obj.currDate1();
            String currdate2 = obj.currDate2();
            conn = obj.getConnection();
            StringBuilder sb = new StringBuilder();

            sb.append("INSERT INTO t_mobwellness (ts_sleeptime, ts_wakeuptime, d_sleephours, d_activityhours, ");
            sb.append("d_standhours, i_userid, i_candidateid, ts_regdate, ts_moddate) VALUES(?, ?, ?, ?, ?,  ?, ?, ?, ?)");
            String insertq = sb.toString().intern();
            sb.setLength(0);
            insert_pstmt = conn.prepareStatement(insertq);

            String sel_query = "SELECT i_mobwellnessid FROM t_mobwellness WHERE i_candidateid =? AND i_status =1 AND DATE_FORMAT(ts_regdate,'%Y-%m-%d') = ?";
            sel_pstmt = conn.prepareStatement(sel_query);

            sb.append("SELECT i_candidateid, TIME_FORMAT(ts_sleeptime,'%H:%i'), TIME_FORMAT(ts_wakeuptime,'%H:%i'), d_sleephours, ");
            sb.append("d_activityhours, d_standhours FROM t_mobwellnessschedule ");
            sb.append("WHERE i_status =1");
            String query = sb.toString().intern();
            sb.setLength(0);
            System.out.println("query :: " + query);
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int candidateid = rs.getInt(1);
                String sleeptime = rs.getString(2) != null ? rs.getString(2) : "";
                String wakeuptime = rs.getString(3) != null ? rs.getString(3) : "";
                double sleephours = rs.getDouble(4);
                double activityhours = rs.getDouble(5);
                double standhours = rs.getDouble(6);
                if (candidateid > 0) {
                    sel_pstmt.setInt(1, candidateid);
                    sel_pstmt.setString(2, currdate1);
                    sel_rs = sel_pstmt.executeQuery();
                    if (sel_rs.next()) {
                    } else {

                        insert_pstmt.setString(1, sleeptime);
                        insert_pstmt.setString(2, wakeuptime);
                        insert_pstmt.setDouble(3, sleephours);
                        insert_pstmt.setDouble(4, activityhours);
                        insert_pstmt.setDouble(5, standhours);
                        insert_pstmt.setInt(6, candidateid);
                        insert_pstmt.setInt(7, candidateid);
                        insert_pstmt.setString(8, currdate2);
                        insert_pstmt.setString(9, currdate2);
                        System.out.println("insert_pstmt :: " + insert_pstmt.toString());
                        insert_pstmt.executeUpdate();
                    }
                }
            }
            rs.close();
            insert_pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            obj.close(conn, pstmt, null);
        }
    }
}
