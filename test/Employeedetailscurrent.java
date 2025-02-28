import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import jxl.*;
import com.web.jxp.base.Base;

public class Employeedetailscurrent
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
                    s = workbook.getSheet(6);
                    rowCount = s.getRows();
                    columnCount = s.getColumns();
                   
                    for(int i = 3; i < rowCount; i++)
                    {
                        rowData = s.getRow(i);
                        if(rowData != null)
                        {
                            for(int j = 1; j < columnCount; j++)
                            {
                                 
                                String name = "", workstartdate="",skills="", compindustry="",functionalarea="",position="", preferreddept = "",
                                              employedocs = "", legalrights = "";
                                int candidateid = 0 , id = 0 , countryid = 0, lastdrawnsalary = 0, rank=0, expectedsalary=0, currentsalary = 0;
                                double totalworkexp=0;
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
                                    workstartdate = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    String totalworkexps = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    totalworkexp = Double.parseDouble(totalworkexps);
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    skills = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    compindustry = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    functionalarea = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    position = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    preferreddept = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    String currentsalarys = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    currentsalary = Integer.parseInt(currentsalarys);
                                    j++;
                                }
                                catch(Exception e){j++;}
                                 try
                                {
                                    String expectedsalarys = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    expectedsalary = Integer.parseInt(expectedsalarys);
                                    j++;
                                }
                                catch(Exception e){j++;}
                                  try
                                {
                                    String ranks = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    rank = Integer.parseInt(ranks);
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    employedocs = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                
                                try
                                {
                                    legalrights = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                
                                
           
                   
                        try
                        {
                             if(workstartdate != "" && !workstartdate.equals(""))
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
                                    sb.append("insert into t_edc ");
                                    sb.append("(d_workstartdate,i_totalworkexp, s_skills,s_compindustry,s_functionalarea,s_position, s_preferreddept,i_currentsalary,"
                                            + "i_expectedsalary ,i_rank,s_employedocs,s_legalrights,i_candidateid,i_status, ts_regdate,ts_moddate ) ");
                                    sb.append("values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                    String candidatequery = sb.toString().intern();
                                    sb.setLength(0);

                                            pstmt = conn.prepareStatement(candidatequery, PreparedStatement.RETURN_GENERATED_KEYS);
                                            pstmt.setString(1,base.changeDate5(workstartdate));
                                            pstmt.setDouble(2, totalworkexp);
                                            pstmt.setString(3, skills);
                                            pstmt.setString(4, compindustry);
                                            pstmt.setString(5, functionalarea);
                                            pstmt.setString(6,position);
                                            pstmt.setString(7, preferreddept);
                                            pstmt.setInt(8, currentsalary);
                                            pstmt.setInt(9, expectedsalary);
                                            pstmt.setInt(10,rank);
                                            pstmt.setString(11, employedocs);
                                            pstmt.setString(12, legalrights);
                                            pstmt.setInt(13, candidateid);
                                            pstmt.setInt(14, 1);
                                            pstmt.setString(15, base.currDate1());
                                            pstmt.setString(16, base.currDate1());
                                            
                                            pstmt.executeUpdate();
                                            rs = pstmt.getGeneratedKeys();             
                                            while (rs.next())
                                            {
                                               int  cc = rs.getInt(1);
                                                    
                                            }
                                            rs.close();
                                            pstmt.close();
                            }
                            
                            }     }
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
                        System.out.println("Something went wrong.3"+e.getMessage());
                    }
                }
                workbook.close();
                fs.close();
            }
            catch(Exception e)
            {
                System.out.println("Error in read xls :: " + e.getMessage());
                System.out.println("Something went wrong.2");
            }
        }
        catch(Exception e)
        {
            System.out.println("Something went wrong.:1"+e.getMessage());
        }
    }

    public static void main(String ar[])
    {
        System.gc();
        new Employeedetailscurrent().createSheet();
        System.out.println("Import Complete.");
    }
}
