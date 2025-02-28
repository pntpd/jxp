import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import jxl.*;
import com.web.jxp.base.Base;

public class Educationalqual
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
                    s = workbook.getSheet(8);
                    rowCount = s.getRows();
                    columnCount = s.getColumns();
                    
                    for(int i = 3; i < rowCount; i++)
                    {
                        rowData = s.getRow(i);
                        if(rowData != null)
                        {
                            for(int j = 1; j < columnCount; j++)
                            {

                                String name = "", degree="",backgroundofstudy="", eduinstitute="",locationofinstitute="",fieldofstudy="",coursestart = "",
                                              passingyear = "", highestqual = "";
                                int candidateid = 0 , id = 0, rank=0, expectedsalary=0, currentsalary = 0;
                                
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
                                    degree = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                     backgroundofstudy = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    eduinstitute = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    locationofinstitute = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    fieldofstudy = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    coursestart = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    passingyear = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    highestqual = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                
                    if(eduinstitute != "" && eduinstitute.equals(""))
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
                                   

                            
                             if(candidateid != 0)
                            {
                             
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("insert into t_eduqual ");
                                    sb.append("(s_degree,s_backgroundofstudy, s_eduinstitute,s_locationofinstitute,s_fieldofstudy,d_coursestart, d_passingyear,s_highestqual,"
                                            + "  i_candidateid,i_status, ts_regdate,ts_moddate ) ");
                                    sb.append("values (?,?,?,?,?,?,?,?,?,?,?,?)");
                                    String candidatequery = sb.toString().intern();
                                    sb.setLength(0);

                                            pstmt = conn.prepareStatement(candidatequery, PreparedStatement.RETURN_GENERATED_KEYS);
                                            pstmt.setString(1,degree);
                                            pstmt.setString(2, backgroundofstudy);
                                            pstmt.setString(3, eduinstitute);
                                            pstmt.setString(4, locationofinstitute);
                                            pstmt.setString(5, fieldofstudy);
                                            pstmt.setString(6, base.changeDate5(coursestart));
                                            pstmt.setString(7,  base.changeDate5(passingyear));
                                            pstmt.setString(8, highestqual);
                                            pstmt.setInt(9, candidateid);
                                            pstmt.setInt(10, 1);
                                            pstmt.setString(11, base.currDate1());
                                            pstmt.setString(12, base.currDate1());
                                            
                                            pstmt.executeUpdate();
                                            rs = pstmt.getGeneratedKeys();             
                                            while (rs.next())
                                            {
                                               int  cc = rs.getInt(1);
                                                   
                                            }
                                            rs.close();
                                            pstmt.close();
                                            
                            }
                            
                            }                
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
        new Educationalqual().createSheet();
            
    }
}
