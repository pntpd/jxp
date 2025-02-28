import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import jxl.*;
import com.web.jxp.base.Base;

public class Hitchdetails
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
                    s = workbook.getSheet(10);
                    rowCount = s.getRows();
                    columnCount = s.getColumns();
                   
                    for(int i = 3; i < rowCount; i++)
                    {
                        rowData = s.getRow(i);
                        if(rowData != null)
                        {
                            for(int j = 1; j < columnCount; j++)
                            {

                                 String companyshipname="",region="", countryworked="",enginetype="",signindate="",signoffdate = "";
                                              
                                int candidateid = 0 , id = 0 , countryid = 0, position = 0;
                                double tonnage =0;
                                
                                try
                                {
                                    String ids = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "0";
                                    id = Integer.parseInt(ids);
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    String positions = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    position = Integer.parseInt(positions);
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    companyshipname = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                     region = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    countryworked = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    enginetype = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    String tonnages = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    tonnage = Double.parseDouble(tonnages);
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    signindate = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    signoffdate = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                
                    
                        try
                        {
                            if(!companyshipname.equals("") && companyshipname != "")
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
                                    
                                    
                                    if(countryworked != "")
                                {
                                    String countryquery = "select i_countryid from t_country where s_name = ? ";
                                     
                                     pstmt = conn.prepareStatement(countryquery);
                                     pstmt.setString(1, countryworked);                 
                                     rs = pstmt.executeQuery();
                                    while(rs.next())
                                    {
                                            countryid = rs.getInt(1);                            
                                    }
                                    rs.close();
                                    pstmt.close();
                                }
                                   

                            if(candidateid != 0)
                            {
                             
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("insert into t_hitch ");
                                    sb.append("(i_position,s_companyshipname, s_region,i_countryworked,s_enginetype,d_tonnage, d_signin,d_signoff,"
                                            + " i_candidateid,i_status, ts_regdate,ts_moddate ) ");
                                    sb.append("values (?,?,?,?,?,?,?,?,?,?,?)");
                                    String candidatequery = sb.toString().intern();
                                    sb.setLength(0);

                                            pstmt = conn.prepareStatement(candidatequery, PreparedStatement.RETURN_GENERATED_KEYS);
                                            pstmt.setInt(1,position);
                                            pstmt.setString(2, companyshipname);
                                            pstmt.setString(3, region);
                                            pstmt.setInt(4, countryid);
                                            pstmt.setString(5, enginetype);
                                            pstmt.setDouble(6,tonnage);
                                            pstmt.setString(7,  base.changeDate5(signindate));
                                            pstmt.setString(8,  base.changeDate5(signoffdate));
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
                                            
                                    }
                                    catch (Exception exception)
                                    {
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
        new Hitchdetails().createSheet();
    }
}
