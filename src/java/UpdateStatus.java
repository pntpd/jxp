import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class UpdateStatus 
{
    Properties database = null; 
    public UpdateStatus() 
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
        UpdateStatus obj = new UpdateStatus();
        try
        {
            conn = obj.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_crewrotation, (select t_crewrotation.i_crewrotationid, t_position.i_rotationid from t_crewrotation left join t_candidate on (t_candidate.i_candidateid = t_crewrotation.i_candidateid) ");
            sb.append("left join t_position on (t_position.i_positionid = t_candidate.i_positionid) ");
            sb.append("left join t_rotation on (t_rotation.i_rotationid = t_position.i_rotationid) ");
            sb.append("where i_status2 = 1 and (DATEDIFF(CURDATE(), DATE_FORMAT(ts_expecteddate, '%Y-%m-%d')) > 0 and DATEDIFF(CURDATE(), DATE_FORMAT(ts_expecteddate, '%Y-%m-%d')) <= i_rotationoverstay - t_rotation.i_noofdays)) as t1 ");
            sb.append("set t_crewrotation.i_status2 = 2, d_rdate1 = CURRENT_DATE() where t_crewrotation.i_crewrotationid = t1.i_crewrotationid and t1.i_crewrotationid > 0 and t1.i_rotationid > 0");
            String query = sb.toString();
            sb.setLength(0);            
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
            
            sb.append("update t_crewrotation, (select t_crewrotation.i_crewrotationid from t_crewrotation left join t_candidate on (t_candidate.i_candidateid = t_crewrotation.i_candidateid) ");
            sb.append("left join t_position on (t_position.i_positionid = t_candidate.i_positionid) ");
            sb.append("left join t_rotation on (t_rotation.i_rotationid = t_position.i_rotationid) ");
            sb.append("where i_status2 IN (1,2) and i_rotationoverstay > 0 and DATEDIFF(CURDATE(), DATE_FORMAT(ts_expecteddate, '%Y-%m-%d')) > i_rotationoverstay - t_rotation.i_noofdays) as t1 ");
            sb.append("set t_crewrotation.i_status2 = 3, d_rdate2 = CURRENT_DATE() where t_crewrotation.i_crewrotationid = t1.i_crewrotationid and t1.i_crewrotationid > 0");
            query = sb.toString();
            sb.setLength(0);            
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