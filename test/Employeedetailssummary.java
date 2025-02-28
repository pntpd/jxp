import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import jxl.*;
import com.web.jxp.base.Base;

public class Employeedetailssummary
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
                    s = workbook.getSheet(5);
                    rowCount = s.getRows();
                    columnCount = s.getColumns();
                    
                    for(int i = 3; i < rowCount; i++)
                    {
                        rowData = s.getRow(i);
                        if(rowData != null)
                        {
                            for(int j = 1; j < columnCount; j++)
                            {
                                String name = "", company="", client = "",rigname="", typeofrigvel="",waterdepth="",jobtitle="",departmentfunction= "",country="", city = "",workstartdate = "",
                                             workenddate = "",icurrworkinghere = "", verificationcheck = "";
                                int candidateid = 0 , id = 0 , countryid = 0, lastdrawnsalary = 0 , positionid = 0, assettypeid =0 ;
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
                                    company = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                
                                try
                                {
                                    rigname = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                
                                try
                                {
                                    client = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                
                                try
                                {
                                    typeofrigvel = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    waterdepth = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    jobtitle = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    departmentfunction = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    country = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    city = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    workstartdate = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    workenddate = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    String lastdrawnsalarys = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    lastdrawnsalary = Integer.parseInt(lastdrawnsalarys);
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    icurrworkinghere = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    verificationcheck = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                
           
                    
                        try
                        {
                            
                        if(company != "" &&!company.equals(""))
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
                                    
                                if(country != "")
                                {
                                    String countryquery = "select i_countryid from t_country where s_name = ? ";
                                     
                                     pstmt = conn.prepareStatement(countryquery);
                                     pstmt.setString(1, country);                 
                                    
                                     rs = pstmt.executeQuery();
                                    while(rs.next())
                                    {
                                            countryid = rs.getInt(1);                            
                                    }
                                    rs.close();
                                    pstmt.close();
                                }
                                
                                if(typeofrigvel != "")
                                {
                                    String countryquery = "select i_assettypeid from t_assettype where s_name = ? ";
                                     
                                     pstmt = conn.prepareStatement(countryquery);
                                     pstmt.setString(1, typeofrigvel);                 
                                     rs = pstmt.executeQuery();
                                    while(rs.next())
                                    {
                                            assettypeid = rs.getInt(1);                            
                                    }
                                    rs.close();
                                    pstmt.close();
                                }
                                
                                if(jobtitle != "")
                                {
                                    String countryquery = "select i_positionid from t_position where s_name = ? ";
                                     
                                     pstmt = conn.prepareStatement(countryquery);
                                     pstmt.setString(1, jobtitle);                 
                                   
                                     rs = pstmt.executeQuery();
                                    while(rs.next())
                                    {
                                            positionid = rs.getInt(1);                            
                                    }
                                    rs.close();
                                    pstmt.close();
                                }

                            if(candidateid != 0)
                            {
                             
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("insert into t_eds ");
                                    sb.append("(s_company,s_rigname,s_client, i_assettypeid,s_waterdepth,i_positionid,i_countryid, s_city,d_workstartdate,"
                                            + "d_workenddate,i_lastdrawnsalary,s_icurrworkinghere,s_verificationcheck,i_candidateid,i_status,ts_regdate,ts_moddate ) ");
                                    sb.append("values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                    String candidatequery = sb.toString().intern();
                                    sb.setLength(0);

                                            pstmt = conn.prepareStatement(candidatequery, PreparedStatement.RETURN_GENERATED_KEYS);
                                            pstmt.setString(1,company);
                                            pstmt.setString(2, rigname);
                                            pstmt.setString(3, client);
                                            pstmt.setInt(4, assettypeid);
                                            pstmt.setString(5, waterdepth);
                                            pstmt.setInt(6, positionid);
                                            pstmt.setInt(7,countryid);
                                            pstmt.setString(8, city);
                                            pstmt.setString(9, base.changeDate5(workstartdate));
                                            pstmt.setString(10, base.changeDate5(workenddate));
                                            pstmt.setInt(11, lastdrawnsalary);
                                            pstmt.setString(12, icurrworkinghere);
                                            pstmt.setString(13, verificationcheck);
                                            pstmt.setInt(14, candidateid);
                                            pstmt.setInt(15, 1);
                                            pstmt.setString(16, base.currDate1());
                                            pstmt.setString(17, base.currDate1());
                                           
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
        new Employeedetailssummary().createSheet();
      
    }
}
