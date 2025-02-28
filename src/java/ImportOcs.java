import static com.web.jxp.base.Base.cipher;
import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import jxl.*;

public class ImportOcs
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
        Connection conn;
        String DriverClassName = "org.gjt.mm.mysql.Driver";
        String dburl = "jdbc:mysql://localhost/ocsdb?characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true";
        String dbusername = "root";
        String dbpassword = "12345";   
        try 
        {
            Class.forName(DriverClassName);
            conn = DriverManager.getConnection(dburl, dbusername, dbpassword);
        }
        catch (SQLException e) 
        {
            System.out.println("getConnection Something went wrong."+e.getMessage());
            return null;
        }
        catch (Exception e) 
        {
            System.out.println("getConnection Something went wrong."+e.getMessage());
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
        String addExcelPath = "D:\\santosh\\OCS\\1.xls";
        try
        {
            FileInputStream fs;
            WorkbookSettings ws;
            Workbook workbook;
            Sheet s;
            Cell rowData[];
            int rowCount;
            int columnCount;
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
                            for(int j = 0; j < columnCount; j++)
                            {
                                String clientname = "", assetname = "", assettype = "", position = "", rank = "", fname = "", 
                                    mname = "", lname = "", country = "", city = "", add1 = "", add2 = "", add3 = "", 
                                    gender = "", email = "", mobile = "", dob = "", commadd1 = "", commadd2 = "", 
                                    commadd3 = "", mobile2 = "", nextofkeen = "", relation = "", econtactno1 = "", 
                                    doctype = "", docno = "", issuedby = "", todate = "", empno = "", nationality = ""; 
                                String doctype1 = "", docno1 = "", issuedby1 = "", todate1 = "";
                                String doctype2 = "", docno2 = "", issuedby2 = "", todate2 = "";
                                String code1 = "", dayrate_val = "", ecode1 = "";
                                String doctype3 = "", issuedby3 = "", todate3 = "";
                                String seaman_medical_fitness = "", OGUK_Medical_Fitness = "", OGUK_Medical_Expiry = "", BloodGroup = "";
                                String VaccinationName1 = "", VaccinationType1 = "", VaccinationExpiry1 = "", VaccinationName2 = "", VaccinationType2 = "", VaccinationExpiry2 = "";
                                String CourseName1 = "", CourseType1 = "", CourseExpiry1 = "", CourseName2 = "", CourseType2 = "", CourseExpiry2 = "", 
                                    CourseName3 = "", CourseType3 = "", CourseExpiry3 = "", CourseName4 = "", CourseType4 = "", CourseExpiry4 = "";
                                String CourseName5 = "", CourseType5 = "", CourseExpiry5 = "", CourseName6 = "", CourseType6 = "", CertificationNo6 = "",
                                        CourseName7 = "", CourseType7 = "", CourseExpiry7 = "", CourseName8 = "", CourseType8 = "", CourseExpiry8 = "",
                                        CourseName9 = "", CourseType9 = "", CourseExpiry9 = "", CourseName10 = "", CourseType10 = "", CourseExpiry10 = "",
                                        CourseName11 = "", CourseType11 = "", CourseExpiry11 = "";
                                String bankname = "", accounttype = "", accno = "", ifsc = "", primary = "";
                                String docf = "", docf1 = "", docf2 = "", docf3 = "";
                                String vaccinef1 = "", vaccinef2 = "";
                                String photo = "", resume = "", medicalfile = "", coursefile1 = "", coursefile2 = "", bankfile = "";
                                String docno3 = "", doctype4 = "", docf4 = "", issuedby4 = "", docno4 = "";
                                String doctype5 = "", docf5 = "", issuedby5 = "", todate5 = "";
                                String coursefile6 = "", CourseExpiry6 = "", coursefile3 = "";
                                double dayrate = 0;
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
                                    photo = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
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
                                catch(Exception e){j++;} //10
                                try
                                {
                                    code1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    mobile = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    mobile2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
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
                                    dayrate_val = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    if(!dayrate_val.equals(""))
                                    {
                                        dayrate = Double.parseDouble(dayrate_val);
                                    }
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
                                    country = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    dob = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
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
                                catch(Exception e){j++;} //20 
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
                                    nextofkeen = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
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
                                    doctype = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    docf = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    docno = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    issuedby = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} //30
                                try
                                {
                                    todate = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}   
                                
                                try
                                {
                                    doctype1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    docf1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    issuedby1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                 
                                try
                                {
                                    doctype2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    docf2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    issuedby2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                
                                try
                                {
                                    doctype3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    docf3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    docno3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} //40
                                try
                                {
                                    issuedby3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    todate3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                
                                
                                try
                                {
                                    doctype4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    docf4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    issuedby4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    docno4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                
                                try
                                {
                                    doctype5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    docf5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    issuedby5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    todate5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} //50
                                
                                try
                                {
                                    seaman_medical_fitness = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    OGUK_Medical_Fitness = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    OGUK_Medical_Expiry = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    medicalfile = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    BloodGroup = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    VaccinationName1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    vaccinef1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    VaccinationType1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    VaccinationExpiry1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    VaccinationName2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} //60
                                try
                                {
                                    VaccinationType2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    vaccinef2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    VaccinationExpiry2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseName1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    coursefile1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseType1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseExpiry1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseName2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    coursefile2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseType2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} //70
                                try
                                {
                                    CourseExpiry2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseName3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    coursefile3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseType3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseExpiry3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseName4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseType4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseExpiry4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseName5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseType5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} //80
                                try
                                {
                                    CourseExpiry5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseName6 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    coursefile6 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseType6 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CertificationNo6 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseExpiry6 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseName7 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseType7 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseExpiry7 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseName8 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} //90
                                try
                                {
                                    CourseType8 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseExpiry8 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseName9 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseType9 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseExpiry9 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseName10 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseType10 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseExpiry10 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseName11 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseType11 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} //100
                                try
                                {
                                    CourseExpiry11 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
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
                                    bankfile = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    accounttype = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    accno = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    ifsc = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    primary = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} //107
                                
                                if(!clientname.equals(""))
                                {
                                    int clientId = 0, clientassetId = 0, assettypeId = 0, gradeId = 0, positionId = 0, countryId = 0, nationalityId = 0,
                                        cityId = 0, relationId = 0;
                                    int doctypeId = 0, documentissuedbyId = 0, doctypeId1 = 0, documentissuedbyId1 = 0, doctypeId2 = 0, documentissuedbyId2 = 0, doctypeId3 = 0, documentissuedbyId3 = 0;
                                    int vaccineId1 = 0, vaccinetype1 = 0, vaccineId2 = 0, vaccinetype2 = 0;
                                    int coursetypeId1 = 0, coursetypeId2 = 0, coursetypeId3 = 0, coursetypeId4 = 0, coursetypeId5 = 0, coursetypeId6 = 0, coursetypeId7 = 0, coursetypeId8 = 0, coursetypeId9 = 0, coursetypeId10 = 0, coursetypeId11 = 0;
                                    int courseId1 = 0, courseId2 = 0, courseId3 = 0, courseId4 = 0, courseId5 = 0, courseId6 = 0, courseId7 = 0, courseId8 = 0, courseId9 = 0, courseId10 = 0, courseId11 = 0;
                                    int doctypeId4 = 0, documentissuedbyId4 = 0, doctypeId5 = 0, documentissuedbyId5 = 0;
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
                                        
                                        query = "select i_countryid from t_country where s_nationality = '"+nationality+"' ";
                                        stmt = conn.createStatement();
                                        rs = stmt.executeQuery(query);
                                        while(rs.next())
                                        {
                                            nationalityId = rs.getInt(1);
                                        }
                                        rs.close();
                                        stmt.close();
                                        
                                        if(relation != null && !relation.equals(""))
                                        {
                                            query = "select i_relationid from t_relation where s_name = '"+relation+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                relationId = rs.getInt(1); 
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!doctype.equals(""))
                                        {
                                            query = "select i_doctypeid from t_doctype where s_doc = '"+doctype+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                doctypeId = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();    

                                            query = "select i_documentissuedbyid from t_documentissuedby where s_name = '"+issuedby+"' and i_doctypeid = "+doctypeId+" and i_countryid = "+countryId;
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                documentissuedbyId = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!doctype1.equals(""))
                                        {
                                            query = "select i_doctypeid from t_doctype where s_doc = '"+doctype1+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                doctypeId1 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();    

                                            query = "select i_documentissuedbyid from t_documentissuedby where s_name = '"+issuedby1+"' and i_doctypeid = "+doctypeId1+" and i_countryid = "+countryId;
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                documentissuedbyId1 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        
                                        if(!doctype2.equals(""))
                                        {
                                            query = "select i_doctypeid from t_doctype where s_doc = '"+doctype2+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                doctypeId2 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();    

                                            query = "select i_documentissuedbyid from t_documentissuedby where s_name = '"+issuedby2+"' and i_doctypeid = "+doctypeId2+" and i_countryid = "+countryId;
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                documentissuedbyId2 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!doctype3.equals(""))
                                        {
                                            query = "select i_doctypeid from t_doctype where s_doc = '"+doctype3+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                doctypeId3 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();    

                                            query = "select i_documentissuedbyid from t_documentissuedby where s_name = '"+issuedby3+"' and i_doctypeid = "+doctypeId3+" and i_countryid = "+countryId;
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                documentissuedbyId3 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!doctype4.equals(""))
                                        {
                                            query = "select i_doctypeid from t_doctype where s_doc = '"+doctype4+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                doctypeId4 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();    

                                            query = "select i_documentissuedbyid from t_documentissuedby where s_name = '"+issuedby4+"' and i_doctypeid = "+doctypeId4+" and i_countryid = "+countryId;
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                documentissuedbyId4 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!doctype5.equals(""))
                                        {
                                            query = "select i_doctypeid from t_doctype where s_doc = '"+doctype5+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                doctypeId5 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();    

                                            query = "select i_documentissuedbyid from t_documentissuedby where s_name = '"+issuedby5+"' and i_doctypeid = "+doctypeId5+" and i_countryid = "+countryId;
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                documentissuedbyId5 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(VaccinationName1 != null && !VaccinationName1.equals(""))
                                        {
                                            query = "select i_vaccineid from t_vaccine where s_name = '"+VaccinationName1+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                vaccineId1 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(VaccinationType1 != null && !VaccinationType1.equals(""))
                                        {
                                            query = "select i_vaccinetypeid from t_vaccinetype where s_name = '"+VaccinationType1+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                vaccinetype1 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(VaccinationName2 != null && !VaccinationName2.equals(""))
                                        {
                                            query = "select i_vaccineid from t_vaccine where s_name = '"+VaccinationName2+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                vaccineId2 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(VaccinationType2 != null && !VaccinationType2.equals(""))
                                        {
                                            query = "select i_vaccinetypeid from t_vaccinetype where s_name = '"+VaccinationType2+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                vaccinetype2 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }                                        
                                        if(CourseType1 != null && !CourseType1.equals(""))
                                        {
                                            query = "select i_coursetypeid from t_coursetype where s_name = '"+CourseType1+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                coursetypeId1 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseType2 != null && !CourseType2.equals(""))
                                        {
                                            query = "select i_coursetypeid from t_coursetype where s_name = '"+CourseType2+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                coursetypeId2 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseType3 != null && !CourseType3.equals(""))
                                        {
                                            query = "select i_coursetypeid from t_coursetype where s_name = '"+CourseType3+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                coursetypeId3 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseType4 != null && !CourseType4.equals(""))
                                        {
                                            query = "select i_coursetypeid from t_coursetype where s_name = '"+CourseType4+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                coursetypeId4 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseType5 != null && !CourseType5.equals(""))
                                        {
                                            query = "select i_coursetypeid from t_coursetype where s_name = '"+CourseType5+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                coursetypeId5 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseType6 != null && !CourseType6.equals(""))
                                        {
                                            query = "select i_coursetypeid from t_coursetype where s_name = '"+CourseType6+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                coursetypeId6 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseType7 != null && !CourseType7.equals(""))
                                        {
                                            query = "select i_coursetypeid from t_coursetype where s_name = '"+CourseType7+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                coursetypeId7 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseType8 != null && !CourseType8.equals(""))
                                        {
                                            query = "select i_coursetypeid from t_coursetype where s_name = '"+CourseType8+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                coursetypeId8 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseType9 != null && !CourseType9.equals(""))
                                        {
                                            query = "select i_coursetypeid from t_coursetype where s_name = '"+CourseType9+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                coursetypeId9 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseType10 != null && !CourseType10.equals(""))
                                        {
                                            query = "select i_coursetypeid from t_coursetype where s_name = '"+CourseType10+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                coursetypeId10 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseType11 != null && !CourseType11.equals(""))
                                        {
                                            query = "select i_coursetypeid from t_coursetype where s_name = '"+CourseType11+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                coursetypeId11 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseName1 != null && !CourseName1.equals(""))
                                        {
                                            query = "select i_coursenameid from t_coursename where s_name = '"+CourseName1+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                courseId1 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseName2 != null && !CourseName2.equals(""))
                                        {
                                            query = "select i_coursenameid from t_coursename where s_name = '"+CourseName2+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                courseId2 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseName3 != null && !CourseName3.equals(""))
                                        {
                                            query = "select i_coursenameid from t_coursename where s_name = '"+CourseName3+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                courseId3 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseName4 != null && !CourseName4.equals(""))
                                        {
                                            query = "select i_coursenameid from t_coursename where s_name = '"+CourseName4+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                courseId4 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseName5 != null && !CourseName5.equals(""))
                                        {
                                            query = "select i_coursenameid from t_coursename where s_name = '"+CourseName5+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                courseId5 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseName6 != null && !CourseName6.equals(""))
                                        {
                                            query = "select i_coursenameid from t_coursename where s_name = '"+CourseName6+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                courseId6 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseName7 != null && !CourseName7.equals(""))
                                        {
                                            query = "select i_coursenameid from t_coursename where s_name = '"+CourseName7+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                courseId7 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseName8 != null && !CourseName8.equals(""))
                                        {
                                            query = "select i_coursenameid from t_coursename where s_name = '"+CourseName8+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                courseId8 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseName9 != null && !CourseName9.equals(""))
                                        {
                                            query = "select i_coursenameid from t_coursename where s_name = '"+CourseName9+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                courseId9 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseName10 != null && !CourseName10.equals(""))
                                        {
                                            query = "select i_coursenameid from t_coursename where s_name = '"+CourseName10+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                courseId10 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(CourseName11 != null && !CourseName11.equals(""))
                                        {
                                            query = "select i_coursenameid from t_coursename where s_name = '"+CourseName11+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                courseId11 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        int bankaccounttypeId = 0;
                                        if(!accounttype.equals(""))
                                        {
                                            query = "select i_bankaccounttypeid from t_bankaccounttype where s_name = '"+accounttype+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                bankaccounttypeId = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
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
                                            if(!photo.equals(""))
                                                photo = "od/"+photo;
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
                                            insertstr += "'" + cipher(escapeApostrophes(mobile)) + "', ";
                                            insertstr += clientId + ", ";
                                            insertstr += clientassetId + ", ";
                                            insertstr += assettypeId + ", ";
                                            insertstr += positionId + ", ";
                                            insertstr += "'" + changeDate2(dob) + "', ";
                                            insertstr += "'" + escapeApostrophes(commadd1) + "', ";
                                            insertstr += "'" + escapeApostrophes(commadd2) + "', ";
                                            insertstr += "'" + escapeApostrophes(commadd3) + "', ";
                                            insertstr += "'" + cipher(escapeApostrophes(mobile2)) + "', ";
                                            insertstr += "'" + cipher(escapeApostrophes(nextofkeen)) + "', ";
                                            insertstr += relationId + ", ";
                                            insertstr += "'" + escapeApostrophes(ecode1) + "', ";
                                            insertstr += "'" + cipher(escapeApostrophes(econtactno1)) + "', ";
                                            insertstr += dayrate + ", ";
                                            insertstr += "'" + escapeApostrophes(photo) + "', ";
                                            insertstr += "1, ";
                                            insertstr += "'" + currdate + "', ";
                                            insertstr += "'" + currdate + "')";
                                            System.out.println("insert :: " + insertstr+";");
                                            PreparedStatement pstmt = conn.prepareStatement(insertstr, Statement.RETURN_GENERATED_KEYS);
                                            pstmt.executeUpdate();
                                            ResultSet rsinner = pstmt.getGeneratedKeys();
                                            int candiadteId = 0;
                                            while (rsinner.next())
                                            {
                                                candiadteId = rsinner.getInt(1);
                                            }
                                            rsinner.close();
                                            pstmt.close();                                            
                                            if(candiadteId > 0)
                                            {
                                                if(!resume.equals(""))
                                                {
                                                    insertstr = "INSERT INTO t_candidatefiles ";
                                                    insertstr += "(s_name, i_candidateid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += "'" + escapeApostrophes(resume) + "', ";
                                                    insertstr += candiadteId + ", ";
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr);
                                                    pstmt.executeUpdate();
                                                    insertstr = "";
                                                }
                                                if(doctypeId > 0)
                                                {
                                                    insertstr = "INSERT INTO t_govdoc ";
                                                    insertstr += "(i_doctypeid, s_docno, d_expirydate, i_candidateid, i_documentissuedbyid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += doctypeId + ", ";
                                                    insertstr += "'" + cipher(escapeApostrophes(docno)) + "', ";
                                                    insertstr += "'" + changeDate2(todate) + "', ";
                                                    insertstr += candiadteId + ", ";
                                                    insertstr += documentissuedbyId + ", ";
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr, Statement.RETURN_GENERATED_KEYS);
                                                    pstmt.executeUpdate();
                                                    ResultSet rs_doc = pstmt.getGeneratedKeys();
                                                    int govdocumentId = 0;
                                                    while (rs_doc.next()) 
                                                    {
                                                        govdocumentId = rs_doc.getInt(1);
                                                    }
                                                    rs_doc.close();
                                                    pstmt.close();
                                                    if(govdocumentId > 0 && !docf.equals(""))
                                                    {
                                                        docf = "od/"+docf;
                                                        String insertdoc = "INSERT INTO t_documentfiles (s_name, i_govid, i_status, ts_regdate, ts_moddate) values (";
                                                        insertdoc += "'" + (escapeApostrophes(docf)) + "', ";
                                                        insertdoc += govdocumentId + ", ";
                                                        insertdoc += "1, ";
                                                        insertdoc += "'" + currdate + "', ";
                                                        insertdoc += "'" + currdate + "')";
                                                        System.out.println("insert :: " + insertdoc+";");
                                                        pstmt = conn.prepareStatement(insertdoc);
                                                        pstmt.executeUpdate();
                                                    }
                                                }
                                                if(doctypeId1 > 0)
                                                {
                                                    insertstr = "INSERT INTO t_govdoc ";
                                                    insertstr += "(i_doctypeid, s_docno, d_expirydate, i_candidateid, i_documentissuedbyid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += doctypeId1 + ", ";
                                                    insertstr += "'', ";
                                                    insertstr += "'" + changeDate2(todate1) + "', ";
                                                    insertstr += candiadteId + ", ";
                                                    insertstr += documentissuedbyId1 + ", ";
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr, Statement.RETURN_GENERATED_KEYS);
                                                    pstmt.executeUpdate();
                                                    ResultSet rs_doc = pstmt.getGeneratedKeys();
                                                    int govdocumentId = 0;
                                                    while (rs_doc.next()) 
                                                    {
                                                        govdocumentId = rs_doc.getInt(1);
                                                    }
                                                    rs_doc.close();
                                                    pstmt.close();
                                                    if(govdocumentId > 0 && !docf1.equals(""))
                                                    {
                                                        docf1 = "od/"+docf1;
                                                        String insertdoc = "INSERT INTO t_documentfiles (s_name, i_govid, i_status, ts_regdate, ts_moddate) values (";
                                                        insertdoc += "'" + (escapeApostrophes(docf1)) + "', ";
                                                        insertdoc += govdocumentId + ", ";
                                                        insertdoc += "1, ";
                                                        insertdoc += "'" + currdate + "', ";
                                                        insertdoc += "'" + currdate + "')";
                                                        System.out.println("insert :: " + insertdoc+";");
                                                        pstmt = conn.prepareStatement(insertdoc);
                                                        pstmt.executeUpdate();
                                                    }
                                                }
                                                if(doctypeId2 > 0)
                                                {
                                                    insertstr = "INSERT INTO t_govdoc ";
                                                    insertstr += "(i_doctypeid, d_dateofissue, d_expirydate, i_candidateid, i_documentissuedbyid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += doctypeId2 + ", ";
                                                    insertstr += "'0000-00-00', ";
                                                    insertstr += "'0000-00-00', ";
                                                    insertstr += candiadteId + ", ";
                                                    insertstr += documentissuedbyId2 + ", ";
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr, Statement.RETURN_GENERATED_KEYS);
                                                    pstmt.executeUpdate();
                                                    ResultSet rs_doc = pstmt.getGeneratedKeys();
                                                    int govdocumentId = 0;
                                                    while (rs_doc.next()) 
                                                    {
                                                        govdocumentId = rs_doc.getInt(1);
                                                    }
                                                    rs_doc.close();
                                                    pstmt.close();
                                                    if(govdocumentId > 0 && !docf2.equals(""))
                                                    {
                                                        docf2 = "od/"+docf2;
                                                        String insertdoc = "INSERT INTO t_documentfiles (s_name, i_govid, i_status, ts_regdate, ts_moddate) values (";
                                                        insertdoc += "'" + (escapeApostrophes(docf2)) + "', ";
                                                        insertdoc += govdocumentId + ", ";
                                                        insertdoc += "1, ";
                                                        insertdoc += "'" + currdate + "', ";
                                                        insertdoc += "'" + currdate + "')";
                                                        System.out.println("insert :: " + insertdoc+";");
                                                        pstmt = conn.prepareStatement(insertdoc);
                                                        pstmt.executeUpdate();
                                                    }
                                                }
                                                if(doctypeId3 > 0)
                                                {
                                                    insertstr = "INSERT INTO t_govdoc ";
                                                    insertstr += "(i_doctypeid, s_docno, d_expirydate, i_candidateid, i_documentissuedbyid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += doctypeId3 + ", ";
                                                    insertstr += "'" + cipher(escapeApostrophes(docno3)) + "', ";
                                                    insertstr += "'" + changeDate2(todate3) + "', ";
                                                    insertstr += candiadteId + ", ";
                                                    insertstr += documentissuedbyId3 + ", ";
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr, Statement.RETURN_GENERATED_KEYS);
                                                    pstmt.executeUpdate();
                                                    ResultSet rs_doc = pstmt.getGeneratedKeys();
                                                    int govdocumentId = 0;
                                                    while (rs_doc.next()) 
                                                    {
                                                        govdocumentId = rs_doc.getInt(1);
                                                    }
                                                    rs_doc.close();
                                                    pstmt.close();
                                                    if(govdocumentId > 0 && !docf3.equals(""))
                                                    {
                                                        docf3 = "od/"+docf3;
                                                        String insertdoc = "INSERT INTO t_documentfiles (s_name, i_govid, i_status, ts_regdate, ts_moddate) values (";
                                                        insertdoc += "'" + escapeApostrophes(docf3) + "', ";
                                                        insertdoc += govdocumentId + ", ";
                                                        insertdoc += "1, ";
                                                        insertdoc += "'" + currdate + "', ";
                                                        insertdoc += "'" + currdate + "')";
                                                        System.out.println("insert :: " + insertdoc+";");
                                                        pstmt = conn.prepareStatement(insertdoc);
                                                        pstmt.executeUpdate();
                                                    }
                                                }
                                                if(doctypeId4 > 0)
                                                {
                                                    insertstr = "INSERT INTO t_govdoc ";
                                                    insertstr += "(i_doctypeid, s_docno, d_expirydate, i_candidateid, i_documentissuedbyid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += doctypeId4 + ", ";
                                                    insertstr += "'" + cipher(escapeApostrophes(docno4)) + "', ";
                                                    insertstr += "'0000-00-00', ";
                                                    insertstr += candiadteId + ", ";
                                                    insertstr += documentissuedbyId4 + ", ";
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr, Statement.RETURN_GENERATED_KEYS);
                                                    pstmt.executeUpdate();
                                                    ResultSet rs_doc = pstmt.getGeneratedKeys();
                                                    int govdocumentId = 0;
                                                    while (rs_doc.next()) 
                                                    {
                                                        govdocumentId = rs_doc.getInt(1);
                                                    }
                                                    rs_doc.close();
                                                    pstmt.close();
                                                    if(govdocumentId > 0 && !docf4.equals(""))
                                                    {
                                                        docf4 = "od/"+docf4;
                                                        String insertdoc = "INSERT INTO t_documentfiles (s_name, i_govid, i_status, ts_regdate, ts_moddate) values (";
                                                        insertdoc += "'" + escapeApostrophes(docf4) + "', ";
                                                        insertdoc += govdocumentId + ", ";
                                                        insertdoc += "1, ";
                                                        insertdoc += "'" + currdate + "', ";
                                                        insertdoc += "'" + currdate + "')";
                                                        System.out.println("insert :: " + insertdoc+";");
                                                        pstmt = conn.prepareStatement(insertdoc);
                                                        pstmt.executeUpdate();
                                                    }
                                                }
                                                if(doctypeId5 > 0)
                                                {
                                                    insertstr = "INSERT INTO t_govdoc ";
                                                    insertstr += "(i_doctypeid, s_docno, d_expirydate, i_candidateid, i_documentissuedbyid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += doctypeId5 + ", ";
                                                    insertstr += "'', ";
                                                    insertstr += "'"+changeDate2(todate5)+"', "; 
                                                    insertstr += candiadteId + ", ";
                                                    insertstr += documentissuedbyId5 + ", ";
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr, Statement.RETURN_GENERATED_KEYS);
                                                    pstmt.executeUpdate();
                                                    ResultSet rs_doc = pstmt.getGeneratedKeys();
                                                    int govdocumentId = 0;
                                                    while (rs_doc.next()) 
                                                    {
                                                        govdocumentId = rs_doc.getInt(1);
                                                    }
                                                    rs_doc.close();
                                                    pstmt.close();
                                                    if(govdocumentId > 0 && !docf5.equals(""))
                                                    {
                                                        docf5 = "od/"+docf5;
                                                        String insertdoc = "INSERT INTO t_documentfiles (s_name, i_govid, i_status, ts_regdate, ts_moddate) values (";
                                                        insertdoc += "'" + escapeApostrophes(docf5) + "', ";
                                                        insertdoc += govdocumentId + ", ";
                                                        insertdoc += "1, ";
                                                        insertdoc += "'" + currdate + "', ";
                                                        insertdoc += "'" + currdate + "')";
                                                        System.out.println("insert :: " + insertdoc+";");
                                                        pstmt = conn.prepareStatement(insertdoc);
                                                        pstmt.executeUpdate();
                                                    }
                                                }
                                                if(!seaman_medical_fitness.equals("")) 
                                                {
                                                    if(!medicalfile.equals(""))
                                                        medicalfile = "od/"+medicalfile;
                                                    insertstr = "INSERT INTO t_healthdeclaration ";
                                                    insertstr += "(s_ssmf, s_ogukmedicalftw, d_ogukexp, s_bloodgroup, s_filename, i_candidateid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += "'" + cipher(seaman_medical_fitness) + "', ";
                                                    insertstr += "'" + cipher(OGUK_Medical_Fitness) + "', ";
                                                    insertstr += "'" + changeDate2(OGUK_Medical_Expiry) + "', ";
                                                    insertstr += "'" + cipher(BloodGroup) + "', ";
                                                    insertstr += "'" + escapeApostrophes(medicalfile) + "', ";
                                                    insertstr += candiadteId + ", ";                                                    
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr);
                                                    pstmt.executeUpdate();
                                                    pstmt.close();
                                                    
                                                    if(!medicalfile.equals(""))
                                                    {
                                                        insertstr = "INSERT INTO t_healthfile ";
                                                        insertstr += "(i_candidateid, s_name, i_status, ts_regdate, ts_moddate) ";
                                                        insertstr += "VALUES (";
                                                        insertstr += candiadteId + ", ";
                                                        insertstr += "'" + escapeApostrophes(medicalfile) + "', ";                                                                                                     
                                                        insertstr += "1, ";
                                                        insertstr += "'" + currdate + "', ";
                                                        insertstr += "'" + currdate + "')";
                                                        System.out.println("insert :: " + insertstr+";");
                                                        pstmt = conn.prepareStatement(insertstr);
                                                        pstmt.executeUpdate();
                                                        pstmt.close();
                                                    }
                                                }
                                                if(vaccinetype1 > 0)
                                                {
                                                    if(!vaccinef1.equals(""))
                                                        vaccinef1 = "od/"+vaccinef1;
                                                    insertstr = "INSERT INTO t_vbd ";
                                                    insertstr += "(i_vaccinetypeid, i_vaccinenameid, d_dateofexpiry, s_filename, i_candidateid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += vaccinetype1 + ", "; 
                                                    insertstr += vaccineId1 + ", ";
                                                    insertstr += "'" + changeDate2(VaccinationExpiry1) + "', ";
                                                    insertstr += "'" + escapeApostrophes(vaccinef1) + "', ";
                                                    insertstr += candiadteId + ", ";                                                    
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr);
                                                    pstmt.executeUpdate();
                                                    pstmt.close();                                                    
                                                }
                                                if(vaccinetype2 > 0)
                                                {
                                                    if(!vaccinef2.equals(""))
                                                        vaccinef2 = "od/"+vaccinef2;
                                                    insertstr = "INSERT INTO t_vbd ";
                                                    insertstr += "(i_vaccinetypeid, i_vaccinenameid, d_dateofexpiry, s_filename, i_candidateid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += vaccinetype2 + ", "; 
                                                    insertstr += vaccineId2 + ", ";
                                                    insertstr += "'" + changeDate2(VaccinationExpiry2) + "', ";
                                                    insertstr += "'" + escapeApostrophes(vaccinef2) + "', ";
                                                    insertstr += candiadteId + ", ";                                                    
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr);
                                                    pstmt.executeUpdate();
                                                    pstmt.close();                                                    
                                                }
                                                if(courseId1 > 0)
                                                {
                                                    if(!coursefile1.equals(""))
                                                        coursefile1 = "od/"+coursefile1;
                                                    insertstr = "INSERT INTO t_trainingandcert ";
                                                    insertstr += "(i_coursenameid, i_coursetypeid, d_expirydate, s_filename, i_candidateid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += courseId1 + ", "; 
                                                    insertstr += coursetypeId1 + ", ";
                                                    insertstr += "'" + changeDate2(CourseExpiry1) + "', ";
                                                    insertstr += "'" + escapeApostrophes(coursefile1) + "', ";
                                                    insertstr += candiadteId + ", ";                                                    
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr);
                                                    pstmt.executeUpdate();
                                                    pstmt.close();  
                                                }
                                                if(courseId2 > 0)
                                                {
                                                    if(!coursefile2.equals(""))
                                                        coursefile2 = "od/"+coursefile2;
                                                    insertstr = "INSERT INTO t_trainingandcert ";
                                                    insertstr += "(i_coursenameid, i_coursetypeid, d_expirydate, s_filename, i_candidateid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += courseId2 + ", "; 
                                                    insertstr += coursetypeId2 + ", ";
                                                    insertstr += "'" + changeDate2(CourseExpiry2) + "', ";
                                                    insertstr += "'" + escapeApostrophes(coursefile2) + "', ";
                                                    insertstr += candiadteId + ", ";                                                    
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr);
                                                    pstmt.executeUpdate();
                                                    pstmt.close();  
                                                }
                                                if(courseId3 > 0)
                                                {
                                                    if(!coursefile3.equals(""))
                                                        coursefile3 = "od/"+coursefile3;
                                                    insertstr = "INSERT INTO t_trainingandcert ";
                                                    insertstr += "(i_coursenameid, i_coursetypeid, d_expirydate, s_filename, i_candidateid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += courseId3 + ", "; 
                                                    insertstr += coursetypeId3 + ", ";
                                                    insertstr += "'" + changeDate2(CourseExpiry3) + "', ";
                                                    insertstr += "'" + escapeApostrophes(coursefile3) + "', ";
                                                    insertstr += candiadteId + ", ";                                                    
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr);
                                                    pstmt.executeUpdate();
                                                    pstmt.close();  
                                                }
                                                if(courseId4 > 0)
                                                {
                                                    insertstr = "INSERT INTO t_trainingandcert ";
                                                    insertstr += "(i_coursenameid, i_coursetypeid, d_expirydate, s_filename, i_candidateid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += courseId4 + ", "; 
                                                    insertstr += coursetypeId4 + ", ";
                                                    insertstr += "'" + changeDate2(CourseExpiry4) + "', ";
                                                    insertstr += "'', ";
                                                    insertstr += candiadteId + ", ";                                                    
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr);
                                                    pstmt.executeUpdate();
                                                    pstmt.close();  
                                                }
                                                if(courseId5 > 0)
                                                {
                                                    insertstr = "INSERT INTO t_trainingandcert ";
                                                    insertstr += "(i_coursenameid, i_coursetypeid, d_expirydate, s_filename, i_candidateid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += courseId5 + ", "; 
                                                    insertstr += coursetypeId5 + ", ";
                                                    insertstr += "'" + changeDate2(CourseExpiry5) + "', ";
                                                    insertstr += "'', ";
                                                    insertstr += candiadteId + ", ";                                                    
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr);
                                                    pstmt.executeUpdate();
                                                    pstmt.close();  
                                                }
                                                if(courseId6 > 0)
                                                {
                                                    if(!coursefile6.equals(""))
                                                        coursefile6 = "od/"+coursefile6;
                                                    insertstr = "INSERT INTO t_trainingandcert ";
                                                    insertstr += "(i_coursenameid, i_coursetypeid, d_expirydate, s_certificateno, s_filename, i_candidateid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += courseId6 + ", "; 
                                                    insertstr += coursetypeId6 + ", ";
                                                    insertstr += "'"+changeDate2(CourseExpiry6)+"', ";
                                                    insertstr += "'" + cipher(CertificationNo6) + "', ";
                                                    insertstr += "'"+escapeApostrophes(coursefile6)+"', ";
                                                    insertstr += candiadteId + ", ";                                                    
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr);
                                                    pstmt.executeUpdate();
                                                    pstmt.close();  
                                                }
                                                if(courseId7 > 0)
                                                {
                                                    insertstr = "INSERT INTO t_trainingandcert ";
                                                    insertstr += "(i_coursenameid, i_coursetypeid, d_expirydate, s_filename, i_candidateid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += courseId7 + ", "; 
                                                    insertstr += coursetypeId7 + ", ";
                                                    insertstr += "'" + changeDate2(CourseExpiry7) + "', ";
                                                    insertstr += "'', ";
                                                    insertstr += candiadteId + ", ";                                                    
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr);
                                                    pstmt.executeUpdate();
                                                    pstmt.close();  
                                                }
                                                if(courseId8 > 0)
                                                {
                                                    insertstr = "INSERT INTO t_trainingandcert ";
                                                    insertstr += "(i_coursenameid, i_coursetypeid, d_expirydate, s_filename, i_candidateid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += courseId8 + ", "; 
                                                    insertstr += coursetypeId8 + ", ";
                                                    insertstr += "'" + changeDate2(CourseExpiry8) + "', ";
                                                    insertstr += "'', ";
                                                    insertstr += candiadteId + ", ";                                                    
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr);
                                                    pstmt.executeUpdate();
                                                    pstmt.close();  
                                                }
                                                if(courseId9 > 0)
                                                {
                                                    insertstr = "INSERT INTO t_trainingandcert ";
                                                    insertstr += "(i_coursenameid, i_coursetypeid, d_expirydate, s_filename, i_candidateid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += courseId9 + ", "; 
                                                    insertstr += coursetypeId9 + ", ";
                                                    insertstr += "'" + changeDate2(CourseExpiry9) + "', ";
                                                    insertstr += "'', ";
                                                    insertstr += candiadteId + ", ";                                                    
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr);
                                                    pstmt.executeUpdate();
                                                    pstmt.close();  
                                                } 
                                                if(courseId10 > 0)
                                                {
                                                    insertstr = "INSERT INTO t_trainingandcert ";
                                                    insertstr += "(i_coursenameid, i_coursetypeid, d_expirydate, s_filename, i_candidateid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += courseId10 + ", "; 
                                                    insertstr += coursetypeId10 + ", ";
                                                    insertstr += "'" + changeDate2(CourseExpiry10) + "', ";
                                                    insertstr += "'', ";
                                                    insertstr += candiadteId + ", ";                                                    
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr);
                                                    pstmt.executeUpdate();
                                                    pstmt.close();  
                                                } 
                                                if(courseId11 > 0)
                                                {
                                                    insertstr = "INSERT INTO t_trainingandcert ";
                                                    insertstr += "(i_coursenameid, i_coursetypeid, d_expirydate, s_filename, i_candidateid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += courseId11 + ", "; 
                                                    insertstr += coursetypeId11 + ", ";
                                                    insertstr += "'" + changeDate2(CourseExpiry11) + "', ";
                                                    insertstr += "'', ";
                                                    insertstr += candiadteId + ", ";                                                    
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr);
                                                    pstmt.executeUpdate();
                                                    pstmt.close();  
                                                } 
                                                if(!bankname.equals(""))
                                                {
                                                    if(!bankfile.equals(""))
                                                        bankfile = "od/"+bankfile;
                                                    int primaryId = 0;
                                                    if(primary.equalsIgnoreCase("Yes"))
                                                        primaryId = 1;
                                                    insertstr = "INSERT INTO t_bankdetail ";
                                                    insertstr += "(s_bankname, i_bankaccounttypeid, s_savingaccno, s_ifsccode, s_filename, i_primarybankid, i_candidateid, i_status, ts_regdate, ts_moddate) ";
                                                    insertstr += "VALUES (";
                                                    insertstr += "'" + (bankname) + "', ";
                                                    insertstr += bankaccounttypeId + ", ";
                                                    insertstr += "'" + cipher(accno) + "', ";
                                                    insertstr += "'" + cipher(ifsc) + "', ";
                                                    insertstr += "'" + escapeApostrophes(bankfile) + "', ";
                                                    insertstr += primaryId + ", "; 
                                                    insertstr += candiadteId + ", ";                                                    
                                                    insertstr += "1, ";
                                                    insertstr += "'" + currdate + "', ";
                                                    insertstr += "'" + currdate + "')";
                                                    System.out.println("insert :: " + insertstr+";");
                                                    pstmt = conn.prepareStatement(insertstr);
                                                    pstmt.executeUpdate();
                                                    pstmt.close();  
                                                }
                                            }
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
                System.out.println("read excel Something went wrong.");
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
                date = date.replaceAll("/", "-");
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                str = sdf2.format(sdf1.parse(date));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return str;
    }

    public static void main(String ar[])
    {
        System.gc();
        new ImportOcs().insertdata();
        System.out.println("Import Complete.");
    }
}
