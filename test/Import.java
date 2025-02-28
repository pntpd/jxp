import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import jxl.*;
import com.web.jxp.base.Base;

public class Import
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
                    s = workbook.getSheet(0);
                    rowCount = s.getRows();
                    columnCount = s.getColumns();
                   
                    for(int i = 1; i < rowCount; i++)
                    {
                        rowData = s.getRow(i);
                        if(rowData != null)
                        {
                            for(int j = 1; j < columnCount; j++)
                            {
                                String firstname = "", middlename = "", lastname = "", dateofbirth = "" , placeofbirth= "", emailId= "",code1= "", contactno1="", code2="", contactno2 = "", code3 = "",
                                        contactno3 = "", gender = "", address1line1= "", address1line2 = "", address2line1 = "", address2line2 = "", address2line3= "", nextofkin= "", relation = "",
                                        ecode1= "", econtactno1 = "", ecode2 = "", econtactno2 = "", maritialstatus = "", country = "",nationality = "" , resumename=" " , photoname = "", position = "";
                                int countryId = 0,nationalityId = 0 , id = 0, positionid = 0 , assetid = 0;
                                
                                try
                                {
                                    resumename = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    String assetids = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    assetid = Integer.parseInt(assetids);
                               
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    photoname = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                  
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    position = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    positionid = Integer.parseInt(position);
                                  
                                    j++;
                                }
                                catch(Exception e){j++;}
                                
                                try
                                {
                                    String ids = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "0";
                                    id = Integer.parseInt(ids);
                                    j++;
                                }
                                catch(Exception e){j++;}
                                
                                
                                try
                                {
                                    firstname = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                 
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    middlename = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    lastname = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    dateofbirth = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    placeofbirth = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    emailId = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    code1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    contactno1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    code2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    contactno2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    code3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    contactno3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    gender = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
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
                                    nationality = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}

                                try
                                {
                                    address1line1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    address1line2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    address2line1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    address2line2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    address2line3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    nextofkin = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    relation = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    ecode1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    econtactno1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    ecode2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    econtactno2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    maritialstatus = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}

                                
                                
                    
                        try
                        {
                            
                            if(assetid > 0)
                            {
                            if(!country.equals(""))
                            {
                                     String query = "select i_countryid from t_country where s_name = ? ".intern();
                                     pstmt = conn.prepareStatement(query);
                                     pstmt.setString(1, country);
                                  
                                     rs = pstmt.executeQuery();
                                    while(rs.next())
                                    {
                                            countryId = rs.getInt(1);                            
                                    }
                                    rs.close();
                                    pstmt.close();
                                    if(countryId <= 0)
                                    {
                                            StringBuilder sb = new StringBuilder();
                                            sb.append("insert into t_country ");
                                            sb.append("(s_name, i_status, ts_regdate, ts_moddate  ) ");
                                            sb.append("values (?, ?, ?, ?)");
                                            String query1 = sb.toString().intern();
                                            sb.setLength(0);
                                            pstmt = conn.prepareStatement(query1, PreparedStatement.RETURN_GENERATED_KEYS);
                                            pstmt.setString(1,country);
                                            pstmt.setInt(2, 1);            
                                            pstmt.setString(3, base.currDate1());
                                            pstmt.setString(4, base.currDate1());
                                        
                                            pstmt.executeUpdate();
                                            rs = pstmt.getGeneratedKeys();

                                            while(rs.next())
                                            {
                                                    countryId = rs.getInt(1);
                                            }
                                            rs.close();
                                            pstmt.close();
                                    }

                            }
                                    if(!nationality.equals(""))
                                    {

                                            String query2 = "select i_countryid from t_country where s_nationality = ? ";                                     
                                             pstmt = conn.prepareStatement(query2);
                                             pstmt.setString(1, nationality);
                                           
                                             rs = pstmt.executeQuery();
                                            while(rs.next())
                                            {
                                                    nationalityId = rs.getInt(1);
                                            }
                                            rs.close();
                                            pstmt.close();


                                    }
                                    
                                    
                                    if(position != "")
                                {
                                    String countryquery = "select i_positionid from t_position where s_name = ? ";
                                     
                                     pstmt = conn.prepareStatement(countryquery);
                                     pstmt.setString(1, position);                 
                                    
                                     rs = pstmt.executeQuery();
                                    while(rs.next())
                                    {
                                            positionid = rs.getInt(1);                            
                                    }
                                    rs.close();
                                    pstmt.close();
                                }
                                 if(positionid <= 0)
                                 {
                                 //insert and get id
                                     StringBuilder sb = new StringBuilder();
                                    sb.append("insert into t_position (s_name,i_status,ts_regdate,ts_moddate )");
                                    sb.append("values (?,?,?,?)");
                                    
                                    String query1 = sb.toString().intern();
                                    sb.setLength(0);
                                    pstmt = conn.prepareStatement(query1, PreparedStatement.RETURN_GENERATED_KEYS);
                                    pstmt.setString(1,position);
                                    pstmt.setInt(2, 1);            
                                    pstmt.setString(3, base.currDate1());
                                    pstmt.setString(4, base.currDate1());
                                   
                                    pstmt.executeUpdate();
                                    rs = pstmt.getGeneratedKeys();

                                    while(rs.next())
                                    {
                                            positionid = rs.getInt(1);
                                    }
                                    rs.close();
                                    pstmt.close();
                                 
                                 }


                                    StringBuilder sb = new StringBuilder();
                                    sb.append("insert into t_candidate ");
                                    sb.append("(s_firstname,s_middlename, s_lastname,d_dob,s_placeofbirth,s_email,s_code1,s_code2,s_contactno1,s_contactno2,s_code3,s_contactno3,"
                                                    + " s_gender,i_countryid,i_nationalityid,s_address1line1,s_address1line2,s_address2line1,s_address2line2,s_address2line3,s_nextofkin,s_relation,s_ecode1,s_econtactno1,"
                                                    + "s_ecode2, s_econtactno2,s_maritialstatus,i_excelid,s_photofilename,s_resumefilename,i_clientid,i_clientassetid,i_positionid, i_status, ts_regdate,ts_moddate ) ");
                                    sb.append("values (?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                    String candidatequery = sb.toString().intern();
                                    sb.setLength(0);

                                            pstmt = conn.prepareStatement(candidatequery, PreparedStatement.RETURN_GENERATED_KEYS);
                                            pstmt.setString(1,firstname);
                                            pstmt.setString(2, middlename);
                                            pstmt.setString(3, lastname);
                                            pstmt.setString(4, base.changeDate5(dateofbirth));
                                            pstmt.setString(5, placeofbirth);
                                            pstmt.setString(6, emailId);
                                            pstmt.setString(7, code1);
                                            pstmt.setString(8, code2);
                                            pstmt.setString(9, contactno1);
                                            pstmt.setString(10, contactno2);
                                            pstmt.setString(11, code3);
                                            pstmt.setString(12, contactno3);
                                            pstmt.setString(13, gender);
                                            pstmt.setInt(14, countryId);
                                            pstmt.setInt(15, nationalityId);
                                            pstmt.setString(16, address1line1);
                                            pstmt.setString(17, address1line2);
                                            pstmt.setString(18, address2line1);
                                            pstmt.setString(19, address2line2);
                                            pstmt.setString(20, address2line3);
                                            pstmt.setString(21, nextofkin);
                                            pstmt.setString(22, relation);
                                            pstmt.setString(23, ecode1);
                                            pstmt.setString(24, econtactno1);
                                            pstmt.setString(25, ecode2);
                                            pstmt.setString(26, econtactno2);
                                            pstmt.setString(27, maritialstatus);
                                            pstmt.setInt(28, id);
                                            pstmt.setString(29, photoname);
                                            pstmt.setString(30, resumename);
                                            pstmt.setInt(31,1);
                                            pstmt.setInt(32,assetid);
                                            pstmt.setInt(33,positionid);
                                            pstmt.setInt(34,2);
                                            pstmt.setString(35, base.currDate1());
                                            pstmt.setString(36, base.currDate1());
                                          
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
        new Import().createSheet();
       
    }
}
