import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class UpdatePosition 
{
    Properties database = null; 
    public UpdatePosition() 
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
        Connection conn1;
        String DriverClassName = database.getProperty("DriverClassName");
        String dburl = database.getProperty("dburl");
        String dbusername = database.getProperty("dbusername");
        String dbpassword = database.getProperty("dbpassword");
        try
        {
            Class.forName(DriverClassName);
            conn1 = DriverManager.getConnection(dburl, dbusername, dbpassword);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            e.printStackTrace(System.out);
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
             e.printStackTrace(System.out);
        }
    }
    
    public static void main(String args[])
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UpdatePosition obj = new UpdatePosition();
        PreparedStatement update_pstmt;
        try
        {
            conn = obj.getConnection();            
            String updateq = "UPDATE t_cractivity set i_positionid = ? where i_cractivityid = ?";
            update_pstmt = conn.prepareStatement(updateq);
                    
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_cractivity.i_cractivityid, t_candidate.i_positionid from t_cractivity ");
            sb.append("LEFT JOIN t_crewrotation on (t_crewrotation.i_crewrotationid = t_cractivity.i_crewrotationid) ");
            sb.append("left join t_candidate on (t_candidate.i_candidateid = t_crewrotation.i_candidateid) ");
            sb.append("where t_cractivity.i_positionid <= 0 AND t_cractivity.i_activityid  IN (1,2,4,6,8) ");
            String query = sb.toString();
            sb.setLength(0);    
            //System.out.println("query :: " + query);
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                int cractivityId = rs.getInt(1);
                int positionId = rs.getInt(2);          
                if(positionId > 0)
                {   
                    update_pstmt.setInt(1, positionId);
                    update_pstmt.setInt(2, cractivityId);
                    System.out.println("update_pstmt :: " + update_pstmt.toString());
                    update_pstmt.executeUpdate();
                } 
            }
            rs.close();
            update_pstmt.close();
        }
        catch(Exception e)
        {
            e.printStackTrace(System.out);
        }
        finally
        {
            obj.close(conn, pstmt, rs);
        }
    }
}