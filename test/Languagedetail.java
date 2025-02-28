import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import jxl.*;
import com.web.jxp.base.Base;

public class Languagedetail
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
                    s = workbook.getSheet(1);
                    rowCount = s.getRows();
                    columnCount = s.getColumns();
                    
                    for(int i = 3; i < rowCount; i++)
                    {
                        rowData = s.getRow(i);
                        if(rowData != null)
                        {
                            for(int j = 1; j < columnCount; j++)
                            {
                                String lang = "", proficiency = "";
            
                                int candidateid = 0 , id = 0, langid = 0,proficiencyid = 0 ;
                                
                                
                                try
                                {
                                    String ids = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "0";
                                    id = Integer.parseInt(ids);
                                  
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                   String  f = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                     lang = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    proficiency = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                   
                                    j++;
                                }
                                catch(Exception e){j++;}
                                
                    if(lang != "" && !lang.equals(""))
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
                                    
                                    
                                if(lang != "" && !lang.equals(""))
                                {
                                    String langquery = "select i_languageid from t_language where s_name = ? ";
                                     
                                     pstmt = conn.prepareStatement(langquery);
                                     pstmt.setString(1, lang);                 
                                  
                                     rs = pstmt.executeQuery();
                                    while(rs.next())
                                    {
                                            langid = rs.getInt(1);                            
                                    }
                                    rs.close();
                                    pstmt.close();
                                }
                                
                                if(langid <= 0)
                                 {
                                 //insert and get id
                                     StringBuilder sb = new StringBuilder();
                                    sb.append("insert into t_language (s_name,i_status,ts_regdate,ts_moddate )");
                                    sb.append("values (?,?,?,?)");
                                    
                                    String query1 = sb.toString().intern();
                                    sb.setLength(0);
                                    pstmt = conn.prepareStatement(query1, PreparedStatement.RETURN_GENERATED_KEYS);
                                    pstmt.setString(1,lang);
                                    pstmt.setInt(2, 1);            
                                    pstmt.setString(3, base.currDate1());
                                    pstmt.setString(4, base.currDate1());
                                   
                                    pstmt.executeUpdate();
                                    rs = pstmt.getGeneratedKeys();

                                    while(rs.next())
                                    {
                                            langid = rs.getInt(1);
                                    }
                                    rs.close();
                                    pstmt.close();
                                 
                                 }

                                    
                                if(proficiency != "")
                                {
                                    String countryquery = "select i_proficiencyid from t_proficiency where s_name = ? ";
                                     
                                     pstmt = conn.prepareStatement(countryquery);
                                     pstmt.setString(1, proficiency);                 
                                   
                                     rs = pstmt.executeQuery();
                                    while(rs.next())
                                    {
                                            proficiencyid = rs.getInt(1);                            
                                    }
                                    rs.close();
                                    pstmt.close();
                                }
                                
                                if(proficiencyid <= 0)
                                 {
                                 //insert and get id
                                     StringBuilder sb = new StringBuilder();
                                    sb.append("insert into t_proficiency (s_name,i_status,ts_regdate,ts_moddate )");
                                    sb.append("values (?,?,?,?)");
                                    
                                    String query1 = sb.toString().intern();
                                    sb.setLength(0);
                                    pstmt = conn.prepareStatement(query1, PreparedStatement.RETURN_GENERATED_KEYS);
                                    pstmt.setString(1,proficiency);
                                    pstmt.setInt(2, 1);            
                                    pstmt.setString(3, base.currDate1());
                                    pstmt.setString(4, base.currDate1());
                                 
                                    pstmt.executeUpdate();
                                    rs = pstmt.getGeneratedKeys();

                                    while(rs.next())
                                    {
                                            proficiencyid = rs.getInt(1);
                                    }
                                    rs.close();
                                    pstmt.close();
                                 
                                 }
                            if(candidateid != 0)
                            {
                            
                             
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("insert into t_candlang ");
                                    sb.append("(i_languageid,i_proficiencyid ,i_candidateid,i_status, ts_regdate,ts_moddate ) ");
                                    sb.append("values (?,?,?,?,?,?)");
                                    String candidatequery = sb.toString().intern();
                                    sb.setLength(0);

                                            pstmt = conn.prepareStatement(candidatequery, PreparedStatement.RETURN_GENERATED_KEYS);
                                            pstmt.setInt(1,langid);
                                            pstmt.setInt(2, proficiencyid);
                                            pstmt.setInt(3, candidateid);
                                            pstmt.setInt(4, 1);
                                            pstmt.setString(5, base.currDate1());
                                            pstmt.setString(6, base.currDate1());
                                          
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
        new Languagedetail().createSheet();
        
    }
}
