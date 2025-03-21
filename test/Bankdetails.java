import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import jxl.*;
import com.web.jxp.base.Base;
import static com.web.jxp.base.Base.cipher;

public class Bankdetails
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
                    s = workbook.getSheet(12);
                    rowCount = s.getRows();
                    columnCount = s.getColumns();
                  
                    for(int i = 3; i < rowCount; i++)
                    {
                        rowData = s.getRow(i);
                        if(rowData != null)
                        {
                            for(int j = 1; j < columnCount; j++)
                            {

                                 String name="",bankname="", savingaccno="", branch="", ifsccode="",nreacc = "";
                                              
                                int candidateid = 0 , id = 0;
  
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
                                    bankname = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                     savingaccno = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    branch = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    ifsccode = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    nreacc = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                
                    if(bankname != "" && !bankname.equals(""))
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
                                    sb.append("insert into t_bankdetail ");
                                    sb.append("(s_bankname,s_savingaccno, s_branch,s_ifsccode,s_nreacc,"
                                            + "  i_candidateid,i_status, ts_regdate,ts_moddate ) ");
                                    sb.append("values (?,?,?,?,?,?,?,?,?)");
                                    String candidatequery = sb.toString().intern();
                                    sb.setLength(0);

                                            pstmt = conn.prepareStatement(candidatequery, PreparedStatement.RETURN_GENERATED_KEYS);
                                            pstmt.setString(1,bankname);
                                            pstmt.setString(2, cipher(savingaccno));
                                            pstmt.setString(3, branch);
                                            pstmt.setString(4, cipher(ifsccode));
                                            pstmt.setString(5, nreacc);
                                            pstmt.setInt(6, candidateid);
                                            pstmt.setInt(7, 1);
                                            pstmt.setString(8, base.currDate1());
                                            pstmt.setString(9, base.currDate1());
                                            
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

    public static void main(String args[])
    {
        System.gc();
        new Bankdetails().createSheet();
       
    }
}
