import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Competency 
{
    Properties database = null; 
    public Competency() 
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
    
    public static void main(String args[])
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        Competency obj = new Competency();
        try
        {
            conn = obj.getConnection();
            String query = "update t_tracker set i_active = 2, ts_moddate = NOW() where i_status = 4 and d_date < CURRENT_DATE() and i_active = 1";
            System.out.println("query :: " + query);
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate(); 
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