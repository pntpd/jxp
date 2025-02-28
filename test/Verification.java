import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import jxl.*;
import com.web.jxp.base.Base;

public class Verification
{
    Base base = new Base();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    public String currDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        java.util.Date dt = cal.getTime();
        return sdf.format(dt);
    }
    public void createSheet()
    {
        String addExcelPath = "C:\\Users\\wesld\\OneDrive\\Documents\\jxp\\web\\jxp_data\\data_2.xls";
        try
        {
            FileInputStream fs = null;
            WorkbookSettings ws = null;
            Workbook workbook = null;
            Sheet s = null;
            Cell rowData[] = null;
            int rowCount = 0;
            int columnCount = 0;
            jxl.DateCell dc = null;
            int totalSheet = 0;
            try
            {
                ws = new WorkbookSettings();
                ws.setLocale(new Locale("en", "EN"));
                fs = new FileInputStream(new File(addExcelPath));
                workbook = Workbook.getWorkbook(fs, ws);
                totalSheet = workbook.getNumberOfSheets();
                Connection conn = null;
                try
                {
                    conn = base.getConnection();
                    s = workbook.getSheet(11);
                    rowCount = s.getRows();
                    columnCount = s.getColumns();
                  
                    for(int i = 3; i < rowCount; i++)
                    {
                        rowData = s.getRow(i);
                        if(rowData != null)
                        {
                            for(int j = 0; j < columnCount; j++)
                            {

                                 String type="",subtype="", verifystatus="",remark="" , name = "";
                                              
                                int candidateid = 0 , id = 0, typeid = 0,subtypeid = 0 , verifystatusid = 0;
                                
                                
                                try
                                {
                                    String ids = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "0";
                                    id = Integer.parseInt(ids);
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                     name = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";   
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                     type = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";   
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    subtype = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                     verifystatus = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    remark = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                
                    if(type != "")
                    {
                        try
                        {
                             if(id != 0)
                            {

                                     String query = "select i_candidateid from t_candidate where i_excelid = ? ";
                                     
                                     pstmt = conn.prepareStatement(query);
                                     pstmt.setInt(1, id);                 
                                    
                                     rs = pstmt.executeQuery();
                                    while(rs.next())
                                    {
                                            candidateid = rs.getInt(1);                            
                                    }
                                    rs.close();
                                    pstmt.close();
                                    
                                    
                                if(type != "")
                                {
                                    String countryquery = "select i_typeid from t_type where s_name = ? ";
                                     
                                     pstmt = conn.prepareStatement(countryquery);
                                     pstmt.setString(1, type);                 
                                   
                                     rs = pstmt.executeQuery();
                                    while(rs.next())
                                    {
                                            typeid = rs.getInt(1);                            
                                    }
                                    rs.close();
                                    pstmt.close();
                                }
                                 if(typeid <= 0)
                                 {
                                 //insert and get id
                                     StringBuilder sb = new StringBuilder();
                                    sb.append("insert into t_type (s_name,i_status,ts_regdate,ts_moddate )");
                                    sb.append("values (?,?,?,?)");
                                    
                                    String query1 = sb.toString().intern();
                                    sb.setLength(0);
                                    pstmt = conn.prepareStatement(query1, PreparedStatement.RETURN_GENERATED_KEYS);
                                    pstmt.setString(1,type);
                                    pstmt.setInt(2, 1);            
                                    pstmt.setString(3, base.currDate1());
                                    pstmt.setString(4, base.currDate1());
                                   
                                    pstmt.executeUpdate();
                                    rs = pstmt.getGeneratedKeys();

                                    while(rs.next())
                                    {
                                            typeid = rs.getInt(1);
                                    }
                                    rs.close();
                                    pstmt.close();
                                 
                                 }
                                if(subtype != "")
                                {
                                    String countryquery = "select i_subtypeid from t_subtype where i_typeid = ? and s_name = ? ";
                                     
                                     pstmt = conn.prepareStatement(countryquery);
                                     pstmt.setInt(1, typeid); 
                                     pstmt.setString(2, subtype);                 
                                  
                                     rs = pstmt.executeQuery();
                                    while(rs.next())
                                    {
                                            subtypeid = rs.getInt(1);                            
                                    }
                                    rs.close();
                                    pstmt.close();
                                }
                                
                                if(subtypeid <= 0)
                                {
                                //insert and get id
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("insert into t_subtype (s_name,i_typeid ,i_status,ts_regdate,ts_moddate )");
                                    sb.append("values (?,?,?,?,?)");
                                    
                                    String query1 = sb.toString().intern();
                                    sb.setLength(0);
                                    pstmt = conn.prepareStatement(query1, PreparedStatement.RETURN_GENERATED_KEYS);
                                    pstmt.setString(1,subtype);
                                    pstmt.setInt(2, typeid);
                                    pstmt.setInt(3, 1);            
                                    pstmt.setString(4, base.currDate1());
                                    pstmt.setString(5, base.currDate1());
                                  
                                    pstmt.executeUpdate();
                                    rs = pstmt.getGeneratedKeys();

                                    while(rs.next())
                                    {
                                            subtypeid = rs.getInt(1);
                                    }
                                    rs.close();
                                    pstmt.close();
                                }
                                
                                if(verifystatus != "")
                                {
                                    String statusquery = "select i_verifstatusid from t_verifstatus where s_name = ? ";
                                     
                                     pstmt = conn.prepareStatement(statusquery);
                                     pstmt.setString(1, verifystatus);                 
                                     
                                     rs = pstmt.executeQuery();
                                    while(rs.next())
                                    {
                                            verifystatusid = rs.getInt(1);                            
                                    }
                                    rs.close();
                                    pstmt.close();
                                }
                                   

                            }
                             
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("insert into t_verification ");
                                    sb.append("(i_typeid,i_subtypeid, i_verifystatus,s_remark ,"
                                            + "  i_candidateid,ts_regdate,ts_moddate ) ");
                                    sb.append("values (?,?,?,?,?,?,?)");
                                    String candidatequery = sb.toString().intern();
                                    sb.setLength(0);

                                            pstmt = conn.prepareStatement(candidatequery, PreparedStatement.RETURN_GENERATED_KEYS);
                                            pstmt.setInt(1,typeid);
                                            pstmt.setInt(2, subtypeid);
                                            pstmt.setInt(3, verifystatusid);
                                            pstmt.setString(4, remark);
                                            pstmt.setInt(5, candidateid);
                                            pstmt.setString(6, base.currDate1());
                                            pstmt.setString(7, base.currDate1());
                                           
                                            pstmt.executeUpdate();
                                            rs = pstmt.getGeneratedKeys();             
                                            while (rs.next())
                                            {
                                               int  cc = rs.getInt(1);
                                                 
                                            }
                                            rs.close();
                                            pstmt.close();
                                            
                                    }
                                    catch (Exception exception)
                                    {
                                      
                                    }
                                }
                            }
                        }
                    }
                }
                catch(Exception e)
                {
                  
                }
                finally
                {
                    try
                    {
                        if(conn != null)
                            conn.close();
                    }
                    catch(Exception e)
                    {
                       
                    }
                }
                workbook.close();
                fs.close();
            }
            catch(Exception e)
            {
            
            }
        }
        catch(Exception e)
        {
          
        }
    }

    public static void main(String ar[])
    {
        System.gc();
        new Verification().createSheet();
       
    }
}
