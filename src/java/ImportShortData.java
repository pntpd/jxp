import static com.web.jxp.base.Base.cipher;
import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import jxl.*;

public class ImportShortData
{    
    public String currDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        java.util.Date dt = cal.getTime();
        return sdf.format(dt);
    }
    public Connection getConnection() 
    {
        Connection conn = null;
        String DriverClassName = "org.gjt.mm.mysql.Driver";
        String dburl = "jdbc:mysql://localhost/ocsdb?characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true";
        String dbusername = "root";
        String dbpassword = "12345";   
        try 
        {
            Class.forName(DriverClassName);
            conn = DriverManager.getConnection(dburl, dbusername, dbpassword);
        }
        catch (Exception e) 
        {
            System.out.println("Something went wrong.");
            return null;
        }
        return conn;
    }
    
    public String replaceWith(String oldString, String pattern, String newPattern) 
    {
        String modString = new String();
        int start = 0;        
        while (true) 
        {
            int locindex = oldString.indexOf(pattern, start);
            if (locindex < 0)
                break;
            String prestring = oldString.substring(start, locindex);
            modString += prestring.concat(newPattern);
            start = locindex + pattern.length();
        }
        modString += oldString.substring(start);
        return modString;
    }

    public String escapeApostrophes(String oldStr)
    {
        String ss = "";
        if(oldStr == null)
        {
            return ss;
        }
        String aposStr = "'";
        String doubleAposStr = "''";
        return replaceWith(oldStr, aposStr, doubleAposStr);
    }
    
    public void insertdata()
    {
        String addExcelPath = "D:\\santosh\\OCS\\data2411.xls";
        try
        {
            FileInputStream fs = null;
            WorkbookSettings ws = null;
            Workbook workbook = null;
            Sheet s = null;
            Cell rowData[] = null;
            int rowCount = 0;
            int columnCount = 0;
            try
            {
                ws = new WorkbookSettings();
                ws.setLocale(new Locale("en", "EN"));
                fs = new FileInputStream(new File(addExcelPath));
                workbook = Workbook.getWorkbook(fs, ws);
                Connection conn = null;
                try
                {
                    conn = getConnection();
                    s = workbook.getSheet(0);
                    rowCount = s.getRows();
                    columnCount = s.getColumns();
                    System.out.println("rowCount :: " + rowCount);
                    System.out.println("columnCount :: " + columnCount);
                    String currdate = currDate();
                    for(int i = 1; i < rowCount; i++)
                    {
                        rowData = s.getRow(i);
                        if(rowData != null)
                        {
                            for(int j = 0; j < 17; j++)
                            {
                                String clientname = "", assetname = "", assettype = "", position = "", rank = "", empno = "", fname = "", 
                                    mname = "", lname = "", gender = "", country = "", city = "", add1 = "", add2 = "", add3 = "", 
                                    email = "", code1 = "", mobile1 = ""; 
                                try
                                {
                                    clientname = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    assetname = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                
                                try
                                {
                                    assettype = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
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
                                    rank = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    empno = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    fname = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    mname = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    lname = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
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
                                    city = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    add1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    add2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    add3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    email = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
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
                                    mobile1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                
                                if(!clientname.equals(""))
                                {
                                    int clientId = 0, clientassetId = 0, assettypeId = 0, gradeId = 0, positionId = 0, countryId = 0, nationalityId = 0,
                                        cityId = 0;
                                    try
                                    {
                                        String query = "select i_clientid from t_client where s_name = '"+clientname+"' ";
                                        Statement stmt = conn.createStatement();
                                        ResultSet rs = stmt.executeQuery(query);
                                        while(rs.next())
                                        {
                                            clientId = rs.getInt(1);
                                        }
                                        rs.close();
                                        stmt.close();                                        
                                        
                                        query = "select i_clientassetid from t_clientasset where i_clientid = " + clientId + " and s_name = '"+assetname+"' ";
                                        stmt = conn.createStatement();
                                        rs = stmt.executeQuery(query);
                                        while(rs.next())
                                        {
                                            clientassetId = rs.getInt(1);
                                        }
                                        rs.close();
                                        stmt.close();
                                        
                                        query = "select i_assettypeid from t_assettype where s_name = '"+assettype+"' ";
                                        stmt = conn.createStatement();
                                        rs = stmt.executeQuery(query);
                                        while(rs.next())
                                        {
                                            assettypeId = rs.getInt(1);
                                        }
                                        rs.close();
                                        stmt.close();
                                        
                                        query = "select i_gradeid from t_grade where s_name = '"+rank+"' ";
                                        stmt = conn.createStatement();
                                        rs = stmt.executeQuery(query);
                                        while(rs.next())
                                        {
                                            gradeId = rs.getInt(1);
                                        }
                                        rs.close();
                                        stmt.close();
                                        
                                        query = "select i_positionid from t_position where i_gradeid = "+gradeId+" and i_assettypeid = "+assettypeId+" and s_name = '"+position+"' ";
                                        stmt = conn.createStatement();
                                        rs = stmt.executeQuery(query);
                                        while(rs.next())
                                        {
                                            positionId = rs.getInt(1);
                                        }
                                        rs.close();
                                        stmt.close();
                                        
                                        query = "select i_countryid from t_country where s_name = '"+country+"' ";
                                        stmt = conn.createStatement();
                                        rs = stmt.executeQuery(query);
                                        while(rs.next())
                                        {
                                            countryId = rs.getInt(1);
                                        }
                                        rs.close();
                                        stmt.close();
                                        if(!city.equals(""))
                                        {
                                            query = "select i_cityid from t_city where i_countryid = " + countryId + " and s_name = '"+city+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                cityId = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(cityId <= 0)
                                            {
                                                String insertstr = "INSERT INTO t_city ";
                                                insertstr += "(i_countryid, s_name, i_status, i_userid, ts_regdate, ts_moddate) ";
                                                insertstr += "VALUES (";
                                                insertstr += countryId + ", ";
                                                insertstr += "'" + escapeApostrophes(city) + "', ";
                                                insertstr += "1, ";
                                                insertstr += "1, ";
                                                insertstr += "'" + currdate + "', ";
                                                insertstr += "'" + currdate + "')"; 
                                                System.out.println("insert :: " + insertstr+";");
                                                PreparedStatement pstmt = conn.prepareStatement(insertstr, Statement.RETURN_GENERATED_KEYS);
                                                pstmt.executeUpdate();
                                                ResultSet rs_city = pstmt.getGeneratedKeys();
                                                while (rs_city.next())
                                                {
                                                    cityId = rs_city.getInt(1);
                                                }
                                                rs_city.close();
                                            }                                            
                                        }
                                        if(true)
                                        {
                                            String insertstr = "INSERT INTO t_candidate ";
                                            insertstr += "(s_firstname, s_middlename, s_lastname, s_empno, i_countryid, i_nationalityid, i_cityid, s_gender, s_address1line1, ";
                                            insertstr += "s_address1line2, s_address1line3, s_email, s_code1, s_contactno1, i_clientid, i_clientassetid, ";
                                            insertstr += "i_assettypeid, i_positionid, d_dob, s_address2line1, s_address2line2, s_address2line3, ";
                                            insertstr += "s_contactno2, s_nextofkin, i_relationid, s_ecode1, s_econtactno1, d_rate1, s_photofilename, i_userid, ts_regdate, ts_moddate) ";
                                            insertstr += "VALUES (";
                                            insertstr += "'" + escapeApostrophes(fname) + "', ";
                                            insertstr += "'" + escapeApostrophes(mname) + "', ";
                                            insertstr += "'" + escapeApostrophes(lname) + "', ";
                                            insertstr += "'" + escapeApostrophes(empno) + "', ";
                                            insertstr += countryId + ", ";
                                            insertstr += nationalityId + ", ";
                                            insertstr += cityId + ", ";
                                            insertstr += "'" + escapeApostrophes(gender) + "', ";
                                            insertstr += "'" + escapeApostrophes(add1) + "', ";
                                            insertstr += "'" + escapeApostrophes(add2) + "', ";
                                            insertstr += "'" + escapeApostrophes(add3) + "', ";
                                            insertstr += "'" + escapeApostrophes(email) + "', ";
                                            insertstr += "'" + (escapeApostrophes(code1)) + "', ";
                                            insertstr += "'" + cipher(escapeApostrophes(mobile1)) + "', ";
                                            insertstr += clientId + ", ";
                                            insertstr += clientassetId + ", ";
                                            insertstr += assettypeId + ", ";
                                            insertstr += positionId + ", ";
                                            insertstr += "'0000-00-00', ";
                                            insertstr += "'', ";
                                            insertstr += "'', ";
                                            insertstr += "'', ";
                                            insertstr += "'', ";
                                            insertstr += "'', ";
                                            insertstr += "0, ";
                                            insertstr += "'', ";
                                            insertstr += "'', ";
                                            insertstr += "0, ";
                                            insertstr += "'', ";
                                            insertstr += "1, ";
                                            insertstr += "'" + currdate + "', ";
                                            insertstr += "'" + currdate + "')";
                                            System.out.println("insert :: " + insertstr+";");
                                            PreparedStatement pstmt = conn.prepareStatement(insertstr);
                                            pstmt.executeUpdate();                                            
                                        }
                                        else
                                        {
                                            System.out.println("Not Inserted :: " + i);
                                        }
                                    }
                                    catch (Exception exception)
                                    {
                                        exception.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
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
                        e.printStackTrace();
                    }
                }
                workbook.close();
                fs.close();
            }
            catch(Exception e)
            {
                System.out.println("Error in read xls :: " + e.getMessage());
                System.out.println("Something went wrong.");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public String changeDate2(String date) // dd-mm-yyyy to yyyy-mm-dd
    {
        String str = "0000-00-00";
        try
        {
            if(date != null && !date.equals("") && !date.equals("00-00-0000"))
            {
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                str = sdf2.format(sdf1.parse(date));
            }
        }
        catch(Exception e)
        {
            System.out.println("Something went wrong.");
        }
        return str;
    }
    
    public String changeDate3(String date) // dd-mm-yyyy to yyyy-mm-dd
    {
        String str = "0000-00-00";
        try
        {
            if(date != null && !date.equals("") && !date.equals("00-00-0000"))
            {
                SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                str = sdf2.format(sdf1.parse(date));
            }
        }
        catch(Exception e)
        {
            System.out.println("Something went wrong.");
        }
        return str;
    }

    public static void main(String ar[])
    {
        System.gc();
        new ImportShortData().insertdata();
        System.out.println("Import Complete.");
    }
}
