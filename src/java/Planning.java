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

public class Planning 
{
    Properties database = null; 
    public Planning() 
    {
        database = new Properties();  
        InputStream in = null;
        try 
        {
            in = getClass().getClassLoader().getResourceAsStream("database.properties");
            database.load(in);
            in.close();
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(in != null)
                    in.close();
            }
            catch(Exception e){}
        }
    }
    
    public Connection getConnection()
    {
        Connection conn1 = null;
        String DriverClassName = database.getProperty("DriverClassName");
        String dburl = database.getProperty("dburl");
        String dbusername = database.getProperty("dbusername");
        String dbpassword = database.getProperty("dbpassword");
        try
        {
            Class.forName(DriverClassName);
            conn1 = DriverManager.getConnection(dburl, dbusername, dbpassword);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return conn1;
    }
    
    public void close(Connection conn,PreparedStatement pstmt, ResultSet rs)
    {
        try
        {
            if(conn != null)
                conn.close();
            if(pstmt != null)
                pstmt.close();
            if(rs != null)
                rs.close();
        }
        catch (SQLException e)
        {
             e.printStackTrace();
        }
    }
    public int currYear() 
    {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        return cal.get(Calendar.YEAR);
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
    
    public String currDate1() 
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        java.util.Date dt = cal.getTime();
        return sdf.format(dt);
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
    
    public static void main(String args[])
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Planning obj = new Planning();
        PreparedStatement insert_pstmt = null;
        try
        {
            String currdate = obj.currDate1();
            int curryear = obj.currYear();
            conn = obj.getConnection();
            
            String insertq = "INSERT INTO t_planning (i_crewrotationid, d_fromdate, d_todate, i_type, i_status, ts_regdate, ts_moddate) values (?,?,?,?,?,?,?) ";
            insert_pstmt = conn.prepareStatement(insertq);
            
            String delq = "DELETE FROM t_planning where YEAR(d_fromdate) = ? ";
            PreparedStatement del_pstmt = conn.prepareStatement(delq);
            del_pstmt.setInt(1, curryear);
            del_pstmt.executeUpdate();
            del_pstmt.close();
                    
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_crewrotation.i_crewrotationid, i_status1, i_status2, DATE_FORMAT(ts_expecteddate, '%Y-%m-%d'), ");
            sb.append("t_rotation.i_noofdays ");
            sb.append("FROM t_crewrotation left join t_candidate on (t_candidate.i_candidateid = t_crewrotation.i_candidateid) ");
            sb.append("left join t_position on (t_position.i_positionid = t_candidate.i_positionid) ");
            sb.append("left join t_rotation on (t_rotation.i_rotationid = t_position.i_rotationid) ");
            sb.append("where t_crewrotation.i_active = 1 and ts_expecteddate != '0000-00-00' and t_rotation.i_noofdays > 0 ");
            String query = sb.toString();
            sb.setLength(0);    
            System.out.println("query :: " + query);
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                int crewrotationId = rs.getInt(1);
                int status1 = rs.getInt(2);
                int status2 = rs.getInt(3);
                String date = rs.getString(4) != null ? rs.getString(4) : "";
                String startdate = date;
                int noofdays = rs.getInt(5);                
                if(status2 > 0)
                {
                    date = getDateAfter(date, 1, noofdays, "yyyy-MM-dd", "yyyy-MM-dd");                    
                    insert_pstmt.setInt(1, crewrotationId);
                    insert_pstmt.setString(2, startdate);
                    insert_pstmt.setString(3, date);
                    insert_pstmt.setInt(4, 2);
                    insert_pstmt.setInt(5, 1);
                    insert_pstmt.setString(6, currdate);
                    insert_pstmt.setString(7, currdate);
                    System.out.println("insert_pstmt :: " + insert_pstmt.toString());
                    insert_pstmt.executeUpdate();
                }
                while(obj.getDiffTwoDateInt(date, curryear+"-12-31", "yyyy-MM-dd") > 0)
                {  
                    String todate = getDateAfter(date, 1, noofdays, "yyyy-MM-dd", "yyyy-MM-dd");
                    insert_pstmt.setInt(1, crewrotationId);
                    insert_pstmt.setString(2, date);
                    insert_pstmt.setString(3, todate);
                    insert_pstmt.setInt(4, 1);
                    insert_pstmt.setInt(5, 1);
                    insert_pstmt.setString(6, currdate);
                    insert_pstmt.setString(7, currdate);
                    System.out.println("insert_pstmt :: " + insert_pstmt.toString());
                    insert_pstmt.executeUpdate();
                    
                    
                    date = getDateAfter(todate, 1, noofdays, "yyyy-MM-dd", "yyyy-MM-dd");
                    
                    insert_pstmt.setInt(1, crewrotationId);
                    insert_pstmt.setString(2, todate);
                    insert_pstmt.setString(3, date);
                    insert_pstmt.setInt(4, 2);
                    insert_pstmt.setInt(5, 1);
                    insert_pstmt.setString(6, currdate);
                    insert_pstmt.setString(7, currdate);
                    System.out.println("insert_pstmt :: " + insert_pstmt.toString());
                    insert_pstmt.executeUpdate();
                }       
            }
            rs.close();
            insert_pstmt.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            obj.close(conn, pstmt, null);
        }
    }
}