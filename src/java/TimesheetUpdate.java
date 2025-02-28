import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.io.InputStream;
import java.util.Properties;

public class TimesheetUpdate
{
    Properties database;
    
    public TimesheetUpdate() 
    {
        this.database = null;
        this.database = new Properties();
        InputStream in = null;
        try {
            in = this.getClass().getClassLoader().getResourceAsStream("database.properties");
            this.database.load(in);
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (Exception ex) {}
        }
    }
    
    public Connection getConnection() 
    {
        Connection conn1 = null;
        final String DriverClassName = this.database.getProperty("DriverClassName");
        final String dburl = this.database.getProperty("dburl");
        final String dbusername = this.database.getProperty("dbusername");
        final String dbpassword = this.database.getProperty("dbpassword");
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
    
    public void close(final Connection conn, final PreparedStatement pstmt, final ResultSet rs) {
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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String currDate1() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        final Date dt = cal.getTime();
        return sdf.format(dt);
    }
    
    public String currDate() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        final Date dt = cal.getTime();
        return sdf.format(dt);
    }
    
    public boolean checkinschedule(final String scheduleval, final String checkval, final int lastday, final int currday) {
        boolean b = false;
        if (scheduleval != null && !scheduleval.equals("")) 
        {
            if (scheduleval.equals(checkval)) 
            {
                b = true;
            }
            if (!b && scheduleval.contains("-2") && lastday == currday) {
                b = true;
            }
        }
        return b;
    }
    
    public static String getDateAfter(final String date, final int type, final int no, final String sformatv, 
        final String endformatv, int lastdayofmonth) 
    {
        String dt = "";
        try {
            final Calendar cal = Calendar.getInstance();
            final SimpleDateFormat sformat = new SimpleDateFormat(sformatv);
            final SimpleDateFormat endformat = new SimpleDateFormat(endformatv);            
            final long l1 = sformat.parse(date).getTime();
            cal.setTimeInMillis(l1);            
            switch (type) {
                case 1: {
                    cal.add(5, no);
                    break;
                }
                case 2: {
                    cal.add(2, no);
                    break;
                }
                case 3: {
                    cal.add(1, no);
                    break;
                }
                case 4: {
                    cal.add(10, no);
                    break;
                }
            }
            if(lastdayofmonth > 0)
                cal.set(5, cal.getActualMaximum(5));
            dt = endformat.format(cal.getTime());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }
    
    public static void main(final String[] args) 
    {
        final TimesheetUpdate obj = new TimesheetUpdate();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;        
        PreparedStatement insert_pstmt = null;
        try 
        {
            final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));  
            final int currday = cal.get(5);
            final int dayofweek = cal.get(7);
            final int lastday = cal.get(5);
            final String currdate = obj.currDate();
            final String currdate_time = obj.currDate1();
            conn = obj.getConnection();
            
            final String insertq = "INSERT INTO t_timesheet (i_clientassetid, i_type, i_currencyid, d_date, d_fromdate, d_todate, i_status, ts_regdate, ts_moddate) values (?,?,?,?,?,?,?,?,?) ";
            insert_pstmt = conn.prepareStatement(insertq, 1);
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_billingcycle.i_clientassetid, t_billingcycle.i_type, t_billingcycle.s_val, t_clientasset.i_currencyid ");
            sb.append("FROM t_billingcycle ");
            sb.append("left join t_clientasset on (t_clientasset.i_clientassetid = t_billingcycle.i_clientassetid) ");
            sb.append("where t_billingcycle.i_status = 1 ");
            String query = sb.toString();
            sb.setLength(0);
            System.out.println("schedule query :: " + query);
            pstmt = conn.prepareStatement(query);            
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                final int clientassetId = rs.getInt(1);
                final int type = rs.getInt(2);
                final String scheduleval = (rs.getString(3) != null) ? rs.getString(3) : "";
                int currencyId = rs.getInt(4);
                String checkval = "";
                String todate = "";
                if (type == 1) 
                {
                    checkval = "" + dayofweek;
                    todate = getDateAfter(currdate, 1, 7, "yyyy-MM-dd", "yyyy-MM-dd", 0);
                    todate = getDateAfter(todate, 1, -1, "yyyy-MM-dd", "yyyy-MM-dd", 0);
                }
                else if (type == 2) 
                {
                    checkval = "" + currday;
                    if(scheduleval.equals("-2"))
                        todate = getDateAfter(currdate, 2, 1, "yyyy-MM-dd", "yyyy-MM-dd", 1);
                    else
                    {
                        todate = getDateAfter(currdate, 2, 1, "yyyy-MM-dd", "yyyy-MM-dd", 0);
                        todate = getDateAfter(todate, 1, -1, "yyyy-MM-dd", "yyyy-MM-dd", 0);
                    }
                } 
                if (obj.checkinschedule(scheduleval, checkval, lastday, currday) == true)
                {
                    insert_pstmt.setInt(1, clientassetId);
                    insert_pstmt.setInt(2, type);
                    insert_pstmt.setInt(3, currencyId);
                    insert_pstmt.setString(4, currdate);
                    insert_pstmt.setString(5, currdate);
                    insert_pstmt.setString(6, todate);
                    insert_pstmt.setInt(7, 1);
                    insert_pstmt.setString(8, currdate_time);
                    insert_pstmt.setString(9, currdate_time);
                    insert_pstmt.executeUpdate();
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
            obj.close(conn, pstmt, rs);
            try {
                if (insert_pstmt != null) {
                    insert_pstmt.close();
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}