import static com.web.jxp.base.Base.cipher;
import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import jxl.*;

public class ImportOcsNew
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
        String dburl = "jdbc:mysql://localhost/cms_jxprodb2?characterSetResults=UTF-8&characterEncoding=UTF-8&useUnicode=yes&autoReconnect=true";
        String dbusername = "root";
        String dbpassword = "root";   
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
        String addExcelPath = "C:\\Users\\wesld\\OneDrive\\Documents\\OCS\\05-11-2024.xls";
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
                    String e1 = "", e2 = "", e3 = "", e4 = "", e5 = "", e6 = "", e7 = "", e8 = "", e9 = "", e10 = "", 
                        e11 = "", e12 = "", e13 = "", e14 = "", e15 = "", e16 = "", e17 = "", e18 = "", e19 = "", e20 = "",
                        e21 = "", e22 = "", e23 = "", e24 = "", e25 = "", e26 = "", e27 = "", e28 = "", e29 = "", e30 = "",
                        e31 = "", e32 = "", e33 = "", e34 = "", e35 = "", e36 = "", e37 = "", e38 = "", e39 = "", e40 = "",
                        e41 = "", e42 = "", e43 = "", e44 = "", e45 = "", e46 = "", e47 = "";
                    for(int i = 1; i < rowCount; i++)
                    {
                        rowData = s.getRow(i);
                        if(rowData != null)
                        {
                            for(int j = 0; j < columnCount; j++)
                            {
                                String empno = "", fname = "", mname = "", lname = "", email = "", religion = "", bloodgroup = "", nearestAirport = "", age = "", placeofbirth = "",
                                    state = "", city = "", pincode = "", gender = "", maritalstatus = "", ecode1 = "", econtact1 = "", 
                                    bankname = "", accounttype = "", accountno = "", accholdername = "", branch = "", ifsc = "", 
                                    remarkdate = "", remarktype = "", remarkdesc = "",
                                    language1 = "", languagep1 = "", language2 = "", languagep2 = "", language3 = "", languagep3 = "", language4 = "", languagep4 = "", language5 = "", languagep5 = "",
                                    language6 = "", languagep6 = "", language7 = "", languagep7 = "", language8 = "", languagep8 = "", language9 = "", languagep9 = "", language10 = "", languagep10 = "",
                                    language11 = "", languagep11 = "", language12 = "", languagep12 = "", language13 = "", languagep13 = "", language14 = "", languagep14 = "", language15 = "", languagep15 = "",
                                    language16 = "", languagep16 = "", nominee = "", nomineerelation = "", nomineecode = "", nomineecontact = ""; 
                                String Seaman_Medical_Fitness = "", OGUK_Medical_Fitness = "", OGUK_Medical_Expiry = "", Blood_Pressure = "", Smoking = "", Hypertension = "", Diabetes = "";
                                String Vaccinationname1 = "", VaccinationType1 = "", DateofApplication1 = "", DateofExpiry1 = "", Vaccinationname2 = "", VaccinationType2 = "", DateofApplication2 = "", DateofExpiry2 = "",
                                    Vaccinationname3 = "", VaccinationType3 = "", DateofApplication3 = "", DateofExpiry3 = "";
                                String Kind1 = "", Degree1 = "", Education_Institute1 = "", Location_Institute1 = "", CourseStarted1 = "", PassingDate1 = "", 
                                    Kind2 = "", Degree2 = "", Education_Institute2 = "", Location_Institute2 = "", CourseStarted2 = "", PassingDate2 = "";
                                String Company_Name1 = "", Company_Industry1 = "", Asset_Type1 = "", Asset_Name1 = "", Position1 = "", Department_Function1 = "", Country_Operations1 = "", City_Operations1 = "", Work_Start_Date1 = "", Work_End_Date1 = "", Currently_working_here1 = "", 
                                    Company_Name2 = "", Company_Industry2 = "", Asset_Type2 = "", Asset_Name2 = "", Position2 = "", Department_Function2 = "", Country_Operations2 = "", City_Operations2 = "", Work_Start_Date2 = "", Work_End_Date2 = "", Currently_working_here2 = "",
                                    Company_Name3 = "", Company_Industry3 = "", Asset_Type3 = "", Asset_Name3 = "", Position3 = "", Department_Function3 = "", Country_Operations3 = "", City_Operations3 = "", Work_Start_Date3 = "", Work_End_Date3 = "", Currently_working_here3 = "",
                                    Company_Name4 = "", Company_Industry4 = "", Asset_Type4 = "", Asset_Name4 = "", Position4 = "", Department_Function4 = "", Country_Operations4 = "", City_Operations4 = "", Work_Start_Date4 = "", Work_End_Date4 = "", Currently_working_here4 = "",
                                    Company_Name5 = "", Company_Industry5 = "", Asset_Type5 = "", Asset_Name5 = "", Position5 = "", Department_Function5 = "", Country_Operations5 = "", City_Operations5 = "", Work_Start_Date5 = "", Work_End_Date5 = "", Currently_working_here5 = "";
                                String PPEItemName1 = "", PPEItemSize1 = "", PPEItemName2 = "", PPEItemSize2 = "";
                                
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
                                    email = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    religion = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    bloodgroup = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    nearestAirport = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                    nearestAirport = nearestAirport.replaceAll("�", " ");
                                }
                                catch(Exception e){j++;}
                                
                                try
                                {
                                    age = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
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
                                    state = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
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
                                    pincode = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
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
                                    maritalstatus = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
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
                                    econtact1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
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
                                    accounttype = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    accountno = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    accholdername = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
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
                                    ifsc = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} //30
                                try
                                {
                                    remarkdate = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}   
                                
                                try
                                {
                                    remarktype = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    remarkdesc = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                    remarkdesc = remarkdesc.replaceAll("�", " ");
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    language1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                 
                                try
                                {
                                    languagep1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    language2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                 
                                try
                                {
                                    languagep2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    language3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                 
                                try
                                {
                                    languagep3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    language4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                 
                                try
                                {
                                    languagep4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    language5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                 
                                try
                                {
                                    languagep5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    language6 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                 
                                try
                                {
                                    languagep6 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    language7 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                 
                                try
                                {
                                    languagep7 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    language8 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                 
                                try
                                {
                                    languagep8 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    language9 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                 
                                try
                                {
                                    languagep9 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    language10 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                 
                                try
                                {
                                    languagep10 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    language11 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                 
                                try
                                {
                                    languagep11 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    language12 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                 
                                try
                                {
                                    languagep12 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    language13 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                 
                                try
                                {
                                    languagep13 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    language14 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                 
                                try
                                {
                                    languagep14 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    language15 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                 
                                try
                                {
                                    languagep15 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    language16 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                 
                                try
                                {
                                    languagep16 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;} 
                                try
                                {
                                    nominee = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    nomineerelation = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    nomineecode = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                    nomineecode = nomineecode.replace("0091", "91").replace("+", "");
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    nomineecontact = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}                                
                                try
                                {
                                    Seaman_Medical_Fitness = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
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
                                    Blood_Pressure = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Smoking = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Hypertension = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Diabetes = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Vaccinationname1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
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
                                    DateofApplication1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    DateofExpiry1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Vaccinationname2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    VaccinationType2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    DateofApplication2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    DateofExpiry2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Vaccinationname3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    VaccinationType3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    DateofApplication3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    DateofExpiry3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Kind1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Degree1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Education_Institute1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Location_Institute1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseStarted1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    PassingDate1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Kind2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Degree2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Education_Institute2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Location_Institute2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    CourseStarted2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    PassingDate2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Company_Name1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Company_Industry1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Asset_Type1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Asset_Name1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Position1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Department_Function1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Country_Operations1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    City_Operations1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Work_Start_Date1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Work_End_Date1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Currently_working_here1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Company_Name2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Company_Industry2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Asset_Type2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Asset_Name2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Position2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Department_Function2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Country_Operations2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    City_Operations2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Work_Start_Date2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Work_End_Date2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Currently_working_here2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Company_Name3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Company_Industry3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Asset_Type3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Asset_Name3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Position3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Department_Function3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Country_Operations3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    City_Operations3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Work_Start_Date3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Work_End_Date3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Currently_working_here3 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Company_Name4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Company_Industry4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Asset_Type4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Asset_Name4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Position4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Department_Function4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Country_Operations4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    City_Operations4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Work_Start_Date4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Work_End_Date4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Currently_working_here4 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Company_Name5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Company_Industry5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Asset_Type5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Asset_Name5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Position5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Department_Function5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Country_Operations5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    City_Operations5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Work_Start_Date5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Work_End_Date5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    Currently_working_here5 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    PPEItemName1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    PPEItemSize1 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    PPEItemName2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    PPEItemSize2 = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                
                                if(!empno.equals("")) 
                                {
                                    int clientId = 0, clientassetId = 0, candidateId = 0; 
                                    int countryId = 0, stateId = 0, cityId = 0, maritalstatusId = 0, bankaccounttypeId = 0, remarktypeId = 0;
                                    int languageId1 = 0, languageId2 = 0, languageId3 = 0, languageId4 = 0, languageId5 = 0, languageId6 = 0,
                                        languageId7 = 0, languageId8 = 0, languageId9 = 0, languageId10 = 0, languageId11 = 0, languageId12 = 0,
                                        languageId13 = 0, languageId14 = 0, languageId15 = 0, languageId16 = 0;
                                    int languagepId1 = 0, languagepId2 = 0, languagepId3 = 0, languagepId4 = 0, languagepId5 = 0, languagepId6 = 0,
                                        languagepId7 = 0, languagepId8 = 0, languagepId9 = 0, languagepId10 = 0, languagepId11 = 0, languagepId12 = 0,
                                        languagepId13 = 0, languagepId14 = 0, languagepId15 = 0, languagepId16 = 0;
                                    int relationId = 0, vaccineId1 = 0, vaccinetype1 = 0, vaccineId2 = 0, vaccinetype2 = 0, vaccineId3 = 0, vaccinetype3 = 0;        
                                    int qualificationtypeId1 = 0, degreeId1 = 0, locationId1 = 0;
                                    int qualificationtypeId2 = 0, degreeId2 = 0, locationId2 = 0;
                                    int industryId1 = 0, assettypeId1 = 0, positionId1 = 0, countryId_op_1 = 0, cityId_op1 = 0, departmentId1 = 0;
                                    int industryId2 = 0, assettypeId2 = 0, positionId2 = 0, countryId_op_2 = 0, cityId_op2 = 0, departmentId2 = 0;
                                    int industryId3 = 0, assettypeId3 = 0, positionId3 = 0, countryId_op_3 = 0, cityId_op3 = 0, departmentId3 = 0;
                                    int industryId4 = 0, assettypeId4 = 0, positionId4 = 0, countryId_op_4 = 0, cityId_op4 = 0, departmentId4 = 0;
                                    int industryId5 = 0, assettypeId5 = 0, positionId5 = 0, countryId_op_5 = 0, cityId_op5 = 0, departmentId5 = 0;
                                    int ppetypeId1 = 0, ppetypeId2 = 0;
                                    try
                                    {  
                                        String query = "select i_countryid, i_stateid from t_state where i_status = 1 and s_name = '"+escapeApostrophes(state)+"' ";
                                        Statement stmt = conn.createStatement();
                                        ResultSet rs = stmt.executeQuery(query);
                                        while(rs.next())
                                        {
                                            countryId = rs.getInt(1);
                                            stateId = rs.getInt(2);
                                        }
                                        rs.close();
                                        stmt.close();
                                        if(stateId <= 0)
                                        {
                                            if(e1.equals(""))
                                                e1 = empno;
                                            else
                                                e1 += ", "+empno;
                                        }
                                        
                                        query = "select i_countryid, i_cityid from t_city where i_status = 1 and s_name = '"+escapeApostrophes(city)+"' ";
                                        stmt = conn.createStatement();
                                        rs = stmt.executeQuery(query);
                                        while(rs.next())
                                        {
                                            if(countryId <= 0)
                                                countryId = rs.getInt(1);                                            
                                            cityId = rs.getInt(2);
                                        }
                                        rs.close();
                                        stmt.close(); 
                                        if(cityId <= 0)
                                        {
                                            if(e2.equals(""))
                                                e2 = empno;
                                            else
                                                e2 += ", "+empno;
                                        }
                                        
                                        query = "select i_candidateid, i_clientid, i_clientassetid from t_candidate where s_empno = '"+escapeApostrophes(empno)+"' and i_clientassetid > 0 ";
                                        stmt = conn.createStatement();
                                        rs = stmt.executeQuery(query);
                                        while(rs.next())
                                        {
                                            candidateId = rs.getInt(1);
                                            clientId = rs.getInt(2);
                                            clientassetId = rs.getInt(3);
                                        }
                                        rs.close();
                                        stmt.close();
                                        if(candidateId <= 0)
                                        {
                                            if(e3.equals(""))
                                                e3 = empno;
                                            else
                                                e3 += ", "+empno;
                                        }
                                        
                                        query = "select i_maritialstatusid from t_maritialstatus where i_status = 1 and s_name = '"+escapeApostrophes(maritalstatus)+"' ";
                                        stmt = conn.createStatement();
                                        rs = stmt.executeQuery(query);
                                        while(rs.next())
                                        {
                                            maritalstatusId = rs.getInt(1);
                                        }
                                        rs.close();
                                        stmt.close();
                                        
                                        query = "select i_bankaccounttypeid from t_bankaccounttype where i_status = 1 and s_name = '"+escapeApostrophes(accounttype)+"' ";
                                        stmt = conn.createStatement();
                                        rs = stmt.executeQuery(query);
                                        while(rs.next())
                                        {
                                            bankaccounttypeId = rs.getInt(1);
                                        }
                                        rs.close();
                                        stmt.close();
                                        query = "select i_remarktypeid from t_remarktype where i_status = 1 and s_name = '"+escapeApostrophes(remarktype)+"' ";
                                        stmt = conn.createStatement();
                                        rs = stmt.executeQuery(query);
                                        while(rs.next())
                                        {
                                            remarktypeId = rs.getInt(1);
                                        }
                                        rs.close();
                                        stmt.close();
                                        
                                        if(!language1.equals(""))
                                        {
                                            query = "select i_languageid from t_language where i_status = 1 and s_name = '"+escapeApostrophes(language1)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languageId1 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!languagep1.equals(""))
                                        {
                                            query = "select i_proficiencyid from t_proficiency where i_status = 1 and s_name = '"+escapeApostrophes(languagep1)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languagepId1 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!language2.equals(""))
                                        {
                                            query = "select i_languageid from t_language where i_status = 1 and s_name = '"+escapeApostrophes(language2)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languageId2 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!languagep2.equals(""))
                                        {
                                            query = "select i_proficiencyid from t_proficiency where i_status = 1 and s_name = '"+escapeApostrophes(languagep2)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languagepId2 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!language3.equals(""))
                                        {
                                            query = "select i_languageid from t_language where i_status = 1 and s_name = '"+escapeApostrophes(language3)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languageId3 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!languagep3.equals(""))
                                        {
                                            query = "select i_proficiencyid from t_proficiency where i_status = 1 and s_name = '"+escapeApostrophes(languagep3)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languagepId3 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!language4.equals(""))
                                        {
                                            query = "select i_languageid from t_language where i_status = 1 and s_name = '"+escapeApostrophes(language4)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languageId4 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!languagep4.equals(""))
                                        {
                                            query = "select i_proficiencyid from t_proficiency where i_status = 1 and s_name = '"+escapeApostrophes(languagep4)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languagepId4 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!language5.equals(""))
                                        {
                                            query = "select i_languageid from t_language where i_status = 1 and s_name = '"+escapeApostrophes(language5)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languageId5 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!languagep5.equals(""))
                                        {
                                            query = "select i_proficiencyid from t_proficiency where i_status = 1 and s_name = '"+escapeApostrophes(languagep5)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languagepId5 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!language6.equals(""))
                                        {
                                            query = "select i_languageid from t_language where i_status = 1 and s_name = '"+escapeApostrophes(language6)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languageId6 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!languagep6.equals(""))
                                        {
                                            query = "select i_proficiencyid from t_proficiency where i_status = 1 and s_name = '"+escapeApostrophes(languagep6)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languagepId6 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!language7.equals(""))
                                        {
                                            query = "select i_languageid from t_language where i_status = 1 and s_name = '"+escapeApostrophes(language7)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languageId7 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!languagep7.equals(""))
                                        {
                                            query = "select i_proficiencyid from t_proficiency where i_status = 1 and s_name = '"+escapeApostrophes(languagep7)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languagepId7 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!language8.equals(""))
                                        {
                                            query = "select i_languageid from t_language where i_status = 1 and s_name = '"+escapeApostrophes(language8)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languageId8 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!languagep8.equals(""))
                                        {
                                            query = "select i_proficiencyid from t_proficiency where i_status = 1 and s_name = '"+escapeApostrophes(languagep8)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languagepId8 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!language9.equals(""))
                                        {
                                            query = "select i_languageid from t_language where i_status = 1 and s_name = '"+escapeApostrophes(language9)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languageId9 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!languagep9.equals(""))
                                        {
                                            query = "select i_proficiencyid from t_proficiency where i_status = 1 and s_name = '"+escapeApostrophes(languagep9)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languagepId9 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!language10.equals(""))
                                        {
                                            query = "select i_languageid from t_language where i_status = 1 and s_name = '"+escapeApostrophes(language10)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languageId10 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!languagep10.equals(""))
                                        {
                                            query = "select i_proficiencyid from t_proficiency where i_status = 1 and s_name = '"+escapeApostrophes(languagep10)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languagepId10 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!language11.equals(""))
                                        {
                                            query = "select i_languageid from t_language where i_status = 1 and s_name = '"+escapeApostrophes(language11)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languageId11 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!languagep11.equals(""))
                                        {
                                            query = "select i_proficiencyid from t_proficiency where i_status = 1 and s_name = '"+escapeApostrophes(languagep11)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languagepId11 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!language12.equals(""))
                                        {
                                            query = "select i_languageid from t_language where i_status = 1 and s_name = '"+escapeApostrophes(language12)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languageId12 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!languagep12.equals(""))
                                        {
                                            query = "select i_proficiencyid from t_proficiency where i_status = 1 and s_name = '"+escapeApostrophes(languagep12)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languagepId12 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!language13.equals(""))
                                        {
                                            query = "select i_languageid from t_language where i_status = 1 and s_name = '"+escapeApostrophes(language13)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languageId13 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!languagep13.equals(""))
                                        {
                                            query = "select i_proficiencyid from t_proficiency where i_status = 1 and s_name = '"+escapeApostrophes(languagep13)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languagepId13 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!language14.equals(""))
                                        {
                                            query = "select i_languageid from t_language where i_status = 1 and s_name = '"+escapeApostrophes(language14)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languageId14 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!languagep14.equals(""))
                                        {
                                            query = "select i_proficiencyid from t_proficiency where i_status = 1 and s_name = '"+escapeApostrophes(languagep14)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languagepId14 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!language15.equals(""))
                                        {
                                            query = "select i_languageid from t_language where i_status = 1 and s_name = '"+escapeApostrophes(language15)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languageId15 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!languagep15.equals(""))
                                        {
                                            query = "select i_proficiencyid from t_proficiency where i_status = 1 and s_name = '"+escapeApostrophes(languagep15)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languagepId15 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!language16.equals(""))
                                        {
                                            query = "select i_languageid from t_language where i_status = 1 and s_name = '"+escapeApostrophes(language16)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languageId16 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!languagep16.equals(""))
                                        {
                                            query = "select i_proficiencyid from t_proficiency where i_status = 1 and s_name = '"+escapeApostrophes(languagep16)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                languagepId16 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(!nomineerelation.equals(""))
                                        {
                                            query = "select i_relationid from t_relation where i_status = 1 and s_name = '"+escapeApostrophes(nomineerelation)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                relationId = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                        }
                                        if(Vaccinationname1 != null && !Vaccinationname1.equals(""))
                                        {
                                            query = "select i_vaccineid from t_vaccine where i_status = 1 and s_name = '"+escapeApostrophes(Vaccinationname1)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                vaccineId1 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                            if(vaccineId1 <= 0)
                                            {
                                                if(e4.equals(""))
                                                    e4 = empno;
                                                else
                                                    e4 += ", " + empno;
                                            }
                                        }
                                        if(VaccinationType1 != null && !VaccinationType1.equals(""))
                                        {
                                            query = "select i_vaccinetypeid from t_vaccinetype where i_status = 1 and s_name = '"+escapeApostrophes(VaccinationType1)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                vaccinetype1 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(vaccinetype1 <= 0)
                                            {
                                                if(e5.equals(""))
                                                    e5 = empno;
                                                else
                                                    e5 += ", " + empno;
                                            }
                                        }
                                        if(Vaccinationname2 != null && !Vaccinationname2.equals(""))
                                        {
                                            query = "select i_vaccineid from t_vaccine where i_status = 1 and s_name = '"+escapeApostrophes(Vaccinationname2)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                vaccineId2 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(vaccineId2 <= 0)
                                            {
                                                if(e6.equals(""))
                                                    e6 = empno;
                                                else
                                                    e6 += ", " + empno;
                                            }
                                        }
                                        if(VaccinationType2 != null && !VaccinationType2.equals(""))
                                        {
                                            query = "select i_vaccinetypeid from t_vaccinetype where i_status = 1 and s_name = '"+escapeApostrophes(VaccinationType2)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                vaccinetype2 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            
                                            if(vaccinetype2 <= 0)
                                            {
                                                if(e7.equals(""))
                                                    e7 = empno;
                                                else
                                                    e7 += ", " + empno;
                                            }
                                        }
                                        if(Vaccinationname3 != null && !Vaccinationname3.equals(""))
                                        {
                                            query = "select i_vaccineid from t_vaccine where i_status = 1 and s_name = '"+escapeApostrophes(Vaccinationname3)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                vaccineId3 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            
                                            if(vaccineId3 <= 0)
                                            {
                                                if(e8.equals(""))
                                                    e8 = empno;
                                                else
                                                    e8 += ", " + empno;
                                            }
                                        }
                                        if(VaccinationType3 != null && !VaccinationType3.equals(""))
                                        {
                                            query = "select i_vaccinetypeid from t_vaccinetype where i_status = 1 and s_name = '"+escapeApostrophes(VaccinationType3)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                vaccinetype3 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close();
                                            
                                            if(vaccinetype3 <= 0)
                                            {
                                                if(e9.equals(""))
                                                    e9 = empno;
                                                else
                                                    e9 += ", " + empno;
                                            }
                                        }                                       
                                        
                                        if(Kind1 != null && !Kind1.equals(""))
                                        {
                                            query = "select i_qualificationtypeid from t_qualificationtype where i_status = 1 and s_name = '"+escapeApostrophes(Kind1)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                qualificationtypeId1 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(qualificationtypeId1 <= 0)
                                            {
                                                if(e10.equals(""))
                                                    e10 = empno;
                                                else
                                                    e10 += ", " + empno;
                                            }
                                        }
                                        if(Degree1 != null && !Degree1.equals(""))
                                        {
                                            query = "select i_degreeid from t_degree where i_status = 1 and s_name = '"+escapeApostrophes(Degree1)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                degreeId1 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(degreeId1 <= 0)
                                            {
                                                if(e11.equals(""))
                                                    e11 = empno;
                                                else
                                                    e11 += ", " + empno;
                                            }
                                        }
                                        if(Location_Institute1 != null && !Location_Institute1.equals(""))
                                        {
                                            query = "select i_cityid from t_city where i_status = 1 and s_name = '"+escapeApostrophes(Location_Institute1)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                locationId1 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(locationId1 <= 0)
                                            {
                                                if(e12.equals(""))
                                                    e12 = empno;
                                                else
                                                    e12 += ", " + empno;
                                            }
                                        }
                                        if(Kind2 != null && !Kind2.equals(""))
                                        {
                                            query = "select i_qualificationtypeid from t_qualificationtype where i_status = 1 and s_name = '"+escapeApostrophes(Kind2)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                qualificationtypeId2 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(qualificationtypeId2 <= 0)
                                            {
                                                if(e13.equals(""))
                                                    e13 = empno;
                                                else
                                                    e13 += ", " + empno;
                                            }
                                        }
                                        if(Degree2 != null && !Degree2.equals(""))
                                        {
                                            query = "select i_degreeid from t_degree where i_status = 1 and s_name = '"+escapeApostrophes(Degree2)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                degreeId2 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(degreeId2 <= 0)
                                            {
                                                if(e14.equals(""))
                                                    e14 = empno;
                                                else
                                                    e14 += ", " + empno;
                                            }
                                        }
                                        if(Location_Institute2 != null && !Location_Institute2.equals(""))
                                        {
                                            query = "select i_cityid from t_city where i_status = 1 and s_name = '"+escapeApostrophes(Location_Institute2)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                locationId2 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(locationId2 <= 0)
                                            {
                                                if(e15.equals(""))
                                                    e15 = empno;
                                                else
                                                    e15 += ", " + empno;
                                            }
                                        }
                                        
                                        if(Company_Industry1 != null && !Company_Industry1.equals(""))
                                        {
                                            query = "select i_companyindustryid from t_companyindustry where i_status = 1 and s_name = '"+escapeApostrophes(Company_Industry1)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                industryId1 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(industryId1 <= 0)
                                            {
                                                if(e16.equals(""))
                                                    e16 = empno;
                                                else
                                                    e16 += ", " + empno;
                                            }
                                        }
                                        if(Asset_Type1 != null && !Asset_Type1.equals(""))
                                        {
                                            query = "select i_assettypeid from t_assettype where i_status = 1 and s_name = '"+escapeApostrophes(Asset_Type1)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                assettypeId1 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(assettypeId1 <= 0)
                                            {
                                                if(e17.equals(""))
                                                    e17 = empno;
                                                else
                                                    e17 += ", " + empno;
                                            }
                                        }
                                        if(Position1 != null && !Position1.equals(""))
                                        {
                                            query = "select i_positionid from t_position where i_status = 1 and i_assettypeid = "+assettypeId1+" and s_name = '"+escapeApostrophes(Position1)+"' limit 1 ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                positionId1 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(positionId1 <= 0)
                                            {
                                                if(e18.equals(""))
                                                    e18 = empno;
                                                else
                                                    e18 += ", " + empno;
                                            }
                                        }
                                        if(Country_Operations1 != null && !Country_Operations1.equals(""))
                                        {
                                            query = "select i_countryid from t_country where i_status = 1 and s_name = '"+escapeApostrophes(Country_Operations1)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                countryId_op_1 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(countryId_op_1 <= 0)
                                            {
                                                if(e19.equals(""))
                                                    e19 = empno;
                                                else
                                                    e19 += ", " + empno;
                                            }
                                            
                                            if(!City_Operations1.equals("") && countryId_op_1 > 0)
                                            {
                                                query = "select i_cityid from t_city where i_status = 1 and i_countryid = "+countryId_op_1+" and s_name = '"+escapeApostrophes(City_Operations1)+"' ";
                                                stmt = conn.createStatement();
                                                rs = stmt.executeQuery(query);
                                                while(rs.next())
                                                {
                                                    cityId_op1 = rs.getInt(1);
                                                }
                                                rs.close();
                                                stmt.close(); 
                                                if(cityId_op1 <= 0)
                                                {
                                                    if(e20.equals(""))
                                                        e20 = empno;
                                                    else
                                                        e20 += ", " + empno;
                                                }
                                            }
                                        }
                                        if(!Department_Function1.equals(""))
                                        {
                                            query = "select i_experiencedeptid from t_experiencedept where i_status = 1 and s_name = '"+escapeApostrophes(Department_Function1)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                departmentId1 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(departmentId1 <= 0)
                                            {
                                                if(e21.equals(""))
                                                    e21 = empno;
                                                else
                                                    e21 += ", " + empno;
                                            }
                                        }
                                        
                                        if(Company_Industry2 != null && !Company_Industry2.equals(""))
                                        {
                                            query = "select i_companyindustryid from t_companyindustry where i_status = 1 and s_name = '"+escapeApostrophes(Company_Industry2)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                industryId2 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(industryId2 <= 0)
                                            {
                                                if(e22.equals(""))
                                                    e22 = empno;
                                                else
                                                    e22 += ", " + empno;
                                            }
                                        }
                                        if(Asset_Type2 != null && !Asset_Type2.equals(""))
                                        {
                                            query = "select i_assettypeid from t_assettype where i_status = 1 and s_name = '"+escapeApostrophes(Asset_Type2)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                assettypeId2 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(assettypeId2 <= 0)
                                            {
                                                if(e23.equals(""))
                                                    e23 = empno;
                                                else
                                                    e23 += ", " + empno;
                                            }
                                        }
                                        if(Position2 != null && !Position2.equals(""))
                                        {
                                            query = "select i_positionid from t_position where i_status = 1 and i_assettypeid = "+assettypeId2+" and s_name = '"+escapeApostrophes(Position2)+"' limit 1 ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                positionId2 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(positionId2 <= 0)
                                            {
                                                if(e24.equals(""))
                                                    e24 = empno;
                                                else
                                                    e24 += ", " + empno;
                                            }
                                        }
                                        if(Country_Operations2 != null && !Country_Operations2.equals(""))
                                        {
                                            query = "select i_countryid from t_country where i_status = 1 and s_name = '"+escapeApostrophes(Country_Operations2)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                countryId_op_2 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(countryId_op_2 <= 0)
                                            {
                                                if(e25.equals(""))
                                                    e25 = empno;
                                                else
                                                    e25 += ", " + empno;
                                            }
                                            
                                            if(!City_Operations2.equals("") && countryId_op_2 > 0)
                                            {
                                                query = "select i_cityid from t_city where i_status = 1 and i_countryid = "+countryId_op_2+" and s_name = '"+escapeApostrophes(City_Operations2)+"' ";
                                                stmt = conn.createStatement();
                                                rs = stmt.executeQuery(query);
                                                while(rs.next())
                                                {
                                                    cityId_op2 = rs.getInt(1);
                                                }
                                                rs.close();
                                                stmt.close(); 
                                                if(cityId_op2 <= 0)
                                                {
                                                    if(e26.equals(""))
                                                        e26 = empno;
                                                    else
                                                        e26 += ", " + empno;
                                                }
                                            }
                                        }
                                        if(!Department_Function2.equals(""))
                                        {
                                            query = "select i_experiencedeptid from t_experiencedept where i_status = 1 and s_name = '"+escapeApostrophes(Department_Function2)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                departmentId2 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(departmentId2 <= 0)
                                            {
                                                if(e27.equals(""))
                                                    e27 = empno;
                                                else
                                                    e27 += ", " + empno;
                                            }
                                        }
                                        
                                        if(Company_Industry3 != null && !Company_Industry3.equals(""))
                                        {
                                            query = "select i_companyindustryid from t_companyindustry where i_status = 1 and s_name = '"+escapeApostrophes(Company_Industry3)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                industryId3 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(industryId3 <= 0)
                                            {
                                                if(e28.equals(""))
                                                    e28 = empno;
                                                else
                                                    e28 += ", " + empno;
                                            }
                                        }
                                        if(Asset_Type3 != null && !Asset_Type3.equals(""))
                                        {
                                            query = "select i_assettypeid from t_assettype where i_status = 1 and s_name = '"+escapeApostrophes(Asset_Type3)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                assettypeId3 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(assettypeId3 <= 0)
                                            {
                                                if(e29.equals(""))
                                                    e29 = empno;
                                                else
                                                    e29 += ", " + empno;
                                            }
                                        }
                                        if(Position3 != null && !Position3.equals(""))
                                        {
                                            query = "select i_positionid from t_position where i_status = 1 and i_assettypeid = "+assettypeId3+" and s_name = '"+escapeApostrophes(Position3)+"' limit 1 ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                positionId3 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(positionId3 <= 0)
                                            {
                                                if(e30.equals(""))
                                                    e30 = empno;
                                                else
                                                    e30 += ", " + empno;
                                            }
                                        }
                                        if(Country_Operations3 != null && !Country_Operations3.equals(""))
                                        {
                                            query = "select i_countryid from t_country where i_status = 1 and s_name = '"+escapeApostrophes(Country_Operations3)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                countryId_op_3 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(countryId_op_3 <= 0)
                                            {
                                                if(e31.equals(""))
                                                    e31 = empno;
                                                else
                                                    e31 += ", " + empno;
                                            }
                                            
                                            if(!City_Operations3.equals("") && countryId_op_3 > 0)
                                            {
                                                query = "select i_cityid from t_city where i_status = 1 and i_countryid = "+countryId_op_3+" and s_name = '"+escapeApostrophes(City_Operations3)+"' ";
                                                stmt = conn.createStatement();
                                                rs = stmt.executeQuery(query);
                                                while(rs.next())
                                                {
                                                    cityId_op3 = rs.getInt(1);
                                                }
                                                rs.close();
                                                stmt.close(); 
                                                if(cityId_op3 <= 0)
                                                {
                                                    if(e32.equals(""))
                                                        e32 = empno;
                                                    else
                                                        e32 += ", " + empno;
                                                }
                                            }
                                        }
                                        if(!Department_Function3.equals(""))
                                        {
                                            query = "select i_experiencedeptid from t_experiencedept where i_status = 1 and s_name = '"+escapeApostrophes(Department_Function3)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                departmentId3 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(departmentId3 <= 0)
                                            {
                                                if(e33.equals(""))
                                                    e33 = empno;
                                                else
                                                    e33 += ", " + empno;
                                            }
                                        }
                                        
                                        if(Company_Industry4 != null && !Company_Industry4.equals(""))
                                        {
                                            query = "select i_companyindustryid from t_companyindustry where i_status = 1 and s_name = '"+escapeApostrophes(Company_Industry4)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                industryId4 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(industryId4 <= 0)
                                            {
                                                if(e34.equals(""))
                                                    e34 = empno;
                                                else
                                                    e34 += ", " + empno;
                                            }
                                        }
                                        if(Asset_Type4 != null && !Asset_Type4.equals(""))
                                        {
                                            query = "select i_assettypeid from t_assettype where i_status = 1 and s_name = '"+escapeApostrophes(Asset_Type4)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                assettypeId4 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(assettypeId4 <= 0)
                                            {
                                                if(e35.equals(""))
                                                    e35 = empno;
                                                else
                                                    e35 += ", " + empno;
                                            }
                                        }
                                        if(Position4!= null && !Position4.equals(""))
                                        {
                                            query = "select i_positionid from t_position where i_status = 1 and i_assettypeid = "+assettypeId4+" and s_name = '"+escapeApostrophes(Position4)+"' limit 1 ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                positionId4 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(positionId4 <= 0)
                                            {
                                                if(e36.equals(""))
                                                    e36 = empno;
                                                else
                                                    e36 += ", " + empno;
                                            }
                                        }
                                        if(Country_Operations4 != null && !Country_Operations4.equals(""))
                                        {
                                            query = "select i_countryid from t_country where i_status = 1 and s_name = '"+escapeApostrophes(Country_Operations4)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                countryId_op_4 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(countryId_op_4 <= 0)
                                            {
                                                if(e37.equals(""))
                                                    e37 = empno;
                                                else
                                                    e37 += ", " + empno;
                                            }
                                            
                                            if(!City_Operations4.equals("") && countryId_op_4 > 0)
                                            {
                                                query = "select i_cityid from t_city where i_status = 1 and i_countryid = "+countryId_op_4+" and s_name = '"+escapeApostrophes(City_Operations4)+"' ";
                                                stmt = conn.createStatement();
                                                rs = stmt.executeQuery(query);
                                                while(rs.next())
                                                {
                                                    cityId_op4 = rs.getInt(1);
                                                }
                                                rs.close();
                                                stmt.close(); 
                                                if(cityId_op4 <= 0)
                                                {
                                                    if(e38.equals(""))
                                                        e38 = empno;
                                                    else
                                                        e38 += ", " + empno;
                                                }
                                            }
                                        }
                                        if(!Department_Function4.equals(""))
                                        {
                                            query = "select i_experiencedeptid from t_experiencedept where i_status = 1 and s_name = '"+escapeApostrophes(Department_Function4)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                departmentId4 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(departmentId4 <= 0)
                                            {
                                                if(e39.equals(""))
                                                    e39 = empno;
                                                else
                                                    e39 += ", " + empno;
                                            }
                                        }
                                        //
                                        if(Company_Industry5 != null && !Company_Industry5.equals(""))
                                        {
                                            query = "select i_companyindustryid from t_companyindustry where i_status = 1 and s_name = '"+escapeApostrophes(Company_Industry5)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                industryId5 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(industryId5 <= 0)
                                            {
                                                if(e40.equals(""))
                                                    e40 = empno;
                                                else
                                                    e40 += ", " + empno;
                                            }
                                        }
                                        if(Asset_Type5 != null && !Asset_Type5.equals(""))
                                        {
                                            query = "select i_assettypeid from t_assettype where i_status = 1 and s_name = '"+escapeApostrophes(Asset_Type5)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                assettypeId5 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(assettypeId5 <= 0)
                                            {
                                                if(e41.equals(""))
                                                    e41 = empno;
                                                else
                                                    e41 += ", " + empno;
                                            }
                                        }
                                        if(Position5!= null && !Position5.equals(""))
                                        {
                                            query = "select i_positionid from t_position where i_status = 1 and i_assettypeid = "+assettypeId5+" and s_name = '"+escapeApostrophes(Position5)+"' limit 1";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                positionId5 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(positionId5 <= 0)
                                            {
                                                if(e42.equals(""))
                                                    e42 = empno;
                                                else
                                                    e42 += ", " + empno;
                                            }
                                        }
                                        if(Country_Operations5 != null && !Country_Operations5.equals(""))
                                        {
                                            query = "select i_countryid from t_country where i_status = 1 and s_name = '"+escapeApostrophes(Country_Operations5)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                countryId_op_5 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(countryId_op_5 <= 0)
                                            {
                                                if(e43.equals(""))
                                                    e43 = empno;
                                                else
                                                    e43 += ", " + empno;
                                            }
                                            
                                            if(!City_Operations5.equals("") && countryId_op_5 > 0)
                                            {
                                                query = "select i_cityid from t_city where i_status = 1 and i_countryid = "+countryId_op_5+" and s_name = '"+escapeApostrophes(City_Operations5)+"' ";
                                                stmt = conn.createStatement();
                                                rs = stmt.executeQuery(query);
                                                while(rs.next())
                                                {
                                                    cityId_op5 = rs.getInt(1);
                                                }
                                                rs.close();
                                                stmt.close(); 
                                                if(cityId_op5 <= 0)
                                                {
                                                    if(e44.equals(""))
                                                        e44 = empno;
                                                    else
                                                        e44 += ", " + empno;
                                                }
                                            }
                                        }
                                        if(!Department_Function5.equals(""))
                                        {
                                            query = "select i_experiencedeptid from t_experiencedept where i_status = 1 and s_name = '"+escapeApostrophes(Department_Function5)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                departmentId5 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            if(departmentId5 <= 0)
                                            {
                                                if(e45.equals(""))
                                                    e45 = empno;
                                                else
                                                    e45 += ", " + empno;
                                            }
                                        }                                         
                                        if(PPEItemName1 != null && !PPEItemName1.equals(""))
                                        {
                                            query = "select i_ppetypeid from t_ppetype where i_status = 1 and s_name = '"+escapeApostrophes(PPEItemName1)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                ppetypeId1 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            
                                            if(ppetypeId1 <= 0)
                                            {
                                                if(e46.equals(""))
                                                    e46 = empno;
                                                else
                                                    e46 += ", " + empno;
                                            }
                                        }
                                        if(PPEItemName2 != null && !PPEItemName2.equals(""))
                                        {
                                            query = "select i_ppetypeid from t_ppetype where i_status = 1 and s_name = '"+escapeApostrophes(PPEItemName2)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                ppetypeId2 = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                            
                                            if(ppetypeId2 <= 0)
                                            {
                                                if(e47.equals(""))
                                                    e47 = empno;
                                                else
                                                    e47 += ", " + empno;
                                            }
                                        }
                                        int bloodpressureId = 0;
                                        if(Blood_Pressure != null && !Blood_Pressure.equals(""))
                                        {
                                            query = "select i_bloodpressureid from t_bloodpressure where i_status = 1 and s_name = '"+escapeApostrophes(Blood_Pressure)+"' ";
                                            stmt = conn.createStatement();
                                            rs = stmt.executeQuery(query);
                                            while(rs.next())
                                            {
                                                bloodpressureId = rs.getInt(1);
                                            }
                                            rs.close();
                                            stmt.close(); 
                                        }
                                        if(candidateId > 0 && countryId > 0 || stateId > 0 || cityId > 0)
                                        {
                                            int age_int = 0;
                                            if(!age.equals(""))
                                                age_int = Integer.parseInt(age);
                                            String updatestr = "update t_candidate set s_firstname = ?, s_middlename = ?, s_lastname= ?, s_email = ?, ";                                            
                                            updatestr += "s_religion = ?, s_airport1 = ?, i_age = ?, s_placeofbirth = ?, i_countryid = ?, i_stateid = ?, ";
                                            updatestr += "i_cityid = ?, s_pincode = ?, s_gender = ?, i_maritialstatusid = ?, s_ecode1 = ?, s_econtactno1 = ?, ";
                                            updatestr += "ts_moddate = ? where i_candidateid = ?";
                                            PreparedStatement pstmt = conn.prepareStatement(updatestr);
                                            pstmt.setString(1, fname);
                                            pstmt.setString(2, mname);
                                            pstmt.setString(3, lname);
                                            pstmt.setString(4, email);
                                            pstmt.setString(5, religion);
                                            pstmt.setString(6, nearestAirport);
                                            pstmt.setInt(7, age_int); 
                                            pstmt.setString(8, placeofbirth);
                                            pstmt.setInt(9, countryId);
                                            pstmt.setInt(10, stateId);
                                            pstmt.setInt(11, cityId);
                                            pstmt.setString(12, pincode);
                                            pstmt.setString(13, gender);
                                            pstmt.setInt(14, maritalstatusId);
                                            pstmt.setString(15, ecode1);
                                            if(!econtact1.equals(""))
                                                pstmt.setString(16, cipher(econtact1));
                                            else
                                                pstmt.setString(16, "");
                                            pstmt.setString(17, currdate);
                                            pstmt.setInt(18, candidateId);
                                            System.out.println("pstmt :: " + pstmt.toString());
                                            pstmt.executeUpdate();
                                            pstmt.close();
                                            if(bankname != null && !bankname.equals(""))
                                            {
                                                String q_bank = "select i_bankdetid from t_bankdetail where i_candidateid = ? and s_bankname = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setString(2, bankname);
                                                rs = pstmt.executeQuery();
                                                int bankdetId = 0;
                                                while(rs.next())
                                                {
                                                    bankdetId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(bankdetId > 0)
                                                {
                                                    q_bank = "UPDATE t_bankdetail set i_bankaccounttypeid = ?, s_savingaccno = ?, s_accountholder = ?, s_branch = ?, s_ifsccode = ?, ts_moddate = ? where i_bankdetid = ?";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, bankaccounttypeId);
                                                    if(!accountno.equals(""))
                                                        pstmt.setString(2, cipher(accountno));
                                                    else
                                                        pstmt.setString(2, "");
                                                    pstmt.setString(3, accholdername);
                                                    pstmt.setString(4, branch);
                                                    if(!ifsc.equals("")) 
                                                        pstmt.setString(5, cipher(ifsc));
                                                    else
                                                        pstmt.setString(5, ifsc);
                                                    pstmt.setString(6, currdate);
                                                    pstmt.setInt(7, bankdetId);
                                                    pstmt.executeUpdate();
                                                }
                                                else
                                                {
                                                    q_bank = "insert into t_bankdetail (i_candidateid, s_bankname, i_bankaccounttypeid, s_savingaccno, s_accountholder, s_branch, s_ifsccode, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?,?,?,?,?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setString(2, bankname);
                                                    pstmt.setInt(3, bankaccounttypeId);
                                                    if(!accountno.equals(""))
                                                        pstmt.setString(4, cipher(accountno));
                                                    else
                                                        pstmt.setString(4, "");
                                                    pstmt.setString(5, accholdername);
                                                    pstmt.setString(6, branch);
                                                    if(!ifsc.equals("")) 
                                                        pstmt.setString(7, cipher(ifsc));
                                                    else
                                                        pstmt.setString(7, ifsc);
                                                    pstmt.setInt(8, 1);
                                                    pstmt.setString(9, currdate);
                                                    pstmt.setString(10, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }  
                                            //health
                                            if(Seaman_Medical_Fitness != null && !Seaman_Medical_Fitness.equals(""))
                                            {
                                                String q_bank = "select i_healthid from t_healthdeclaration where i_candidateid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                rs = pstmt.executeQuery();
                                                int healthId = 0;
                                                while(rs.next())
                                                {
                                                    healthId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(healthId > 0)
                                                {
                                                    q_bank = "UPDATE t_healthdeclaration set s_ssmf = ?, s_ogukmedicalftw = ?, d_ogukexp = ?, s_bloodgroup = ?, i_bloodpressureid = ?, s_smoking = ?, s_hypertension = ?, s_diabetes = ?, ts_moddate = ? where i_healthid = ?";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    if(!Seaman_Medical_Fitness.equals(""))
                                                        pstmt.setString(1, cipher(Seaman_Medical_Fitness));
                                                    else
                                                        pstmt.setString(1, "");
                                                    if(!OGUK_Medical_Fitness.equals(""))
                                                        pstmt.setString(2, cipher(OGUK_Medical_Fitness));
                                                    else
                                                        pstmt.setString(2, "");
                                                    pstmt.setString(3, changeDate2(OGUK_Medical_Expiry));
                                                    if(!bloodgroup.equals(""))
                                                        pstmt.setString(4, cipher(bloodgroup));
                                                    else
                                                        pstmt.setString(4, "");
                                                    pstmt.setInt(5, bloodpressureId);
                                                    if(!Smoking.equals(""))
                                                        pstmt.setString(6, cipher(Smoking));
                                                    else
                                                        pstmt.setString(6, "");
                                                    if(!Hypertension.equals("")) 
                                                        pstmt.setString(7, cipher(Hypertension));
                                                    else
                                                        pstmt.setString(7, "");
                                                    if(!Diabetes.equals(""))
                                                        pstmt.setString(8, cipher(Diabetes));
                                                    else
                                                        pstmt.setString(8, "");
                                                    pstmt.setString(9, currdate);
                                                    pstmt.setInt(10, healthId);
                                                    pstmt.executeUpdate();
                                                }
                                                else
                                                {
                                                    q_bank = "insert into t_healthdeclaration (i_candidateid, s_ssmf, s_ogukmedicalftw, d_ogukexp, s_bloodgroup, i_bloodpressureid, s_smoking, s_hypertension, s_diabetes, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?,?,?,?,?, ?,?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    if(!Seaman_Medical_Fitness.equals(""))
                                                        pstmt.setString(2, cipher(Seaman_Medical_Fitness));
                                                    else
                                                        pstmt.setString(2, "");
                                                    if(!OGUK_Medical_Fitness.equals(""))
                                                        pstmt.setString(3, cipher(OGUK_Medical_Fitness));
                                                    else
                                                        pstmt.setString(3, "");
                                                    pstmt.setString(4, changeDate2(OGUK_Medical_Expiry));
                                                    if(!bloodgroup.equals(""))
                                                        pstmt.setString(5, cipher(bloodgroup));
                                                    else
                                                        pstmt.setString(5, "");
                                                    pstmt.setInt(6, bloodpressureId);
                                                    if(!Smoking.equals(""))
                                                        pstmt.setString(7, cipher(Smoking));
                                                    else
                                                        pstmt.setString(7, "");
                                                    if(!Hypertension.equals("")) 
                                                        pstmt.setString(8, cipher(Hypertension));
                                                    else
                                                        pstmt.setString(8, "");
                                                    if(!Diabetes.equals(""))
                                                        pstmt.setString(9, cipher(Diabetes));
                                                    else
                                                        pstmt.setString(9, "");
                                                    pstmt.setInt(10, 1);
                                                    pstmt.setString(11, currdate);
                                                    pstmt.setString(12, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //remarks
                                            if(remarktypeId > 0)
                                            {
                                                String q_bank = "select i_offshoreupdateid from t_offshoreupdate where i_clientassetid = ? and i_candidateid = ? and i_remarktypeid = ? and d_date = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, clientassetId);
                                                pstmt.setInt(2, candidateId);
                                                pstmt.setInt(3, remarktypeId);
                                                pstmt.setString(4, changeDate2(remarkdate));
                                                rs = pstmt.executeQuery();
                                                int offshoreupdateId = 0;
                                                while(rs.next())
                                                {
                                                    offshoreupdateId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(offshoreupdateId > 0)
                                                {
                                                    q_bank = "UPDATE t_offshoreupdate set i_remarktypeid = ?, s_remarks = ?, d_date = ?, ts_moddate = ? where i_offshoreupdateid = ?";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, remarktypeId);
                                                    pstmt.setString(2, remarkdesc);
                                                    pstmt.setString(3, changeDate2(remarkdate));
                                                    pstmt.setString(4, currdate);
                                                    pstmt.setInt(5, offshoreupdateId);
                                                    pstmt.executeUpdate();
                                                }
                                                else
                                                {
                                                    q_bank = "insert into t_offshoreupdate (i_candidateid, i_clientid, i_clientassetid, i_remarktypeid, s_remarks, d_date, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?,?,?,?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, clientId);
                                                    pstmt.setInt(3, clientassetId);
                                                    pstmt.setInt(4, remarktypeId);
                                                    pstmt.setString(5, remarkdesc);
                                                    pstmt.setString(6, changeDate2(remarkdate));
                                                    pstmt.setInt(7, 1);
                                                    pstmt.setString(8, currdate);
                                                    pstmt.setString(9, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            
                                            //languange1
                                            if(languageId1 > 0 && languagepId1 > 0)
                                            {
                                                String q_bank = "select i_candlangid from t_candlang where i_candidateid = ? and i_languageid = ? and i_proficiencyid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, languageId1);
                                                pstmt.setInt(3, languagepId1);
                                                rs = pstmt.executeQuery();
                                                int candlangId = 0;
                                                while(rs.next())
                                                {
                                                    candlangId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(candlangId <= 0)
                                                {
                                                    q_bank = "insert into t_candlang (i_candidateid, i_languageid, i_proficiencyid, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, languageId1);
                                                    pstmt.setInt(3, languagepId1);
                                                    pstmt.setInt(4, 1);
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setString(6, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //language2
                                            if(languageId2 > 0 && languagepId2 > 0)
                                            {
                                                String q_bank = "select i_candlangid from t_candlang where i_candidateid = ? and i_languageid = ? and i_proficiencyid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, languageId2);
                                                pstmt.setInt(3, languagepId2);
                                                rs = pstmt.executeQuery();
                                                int candlangId = 0;
                                                while(rs.next())
                                                {
                                                    candlangId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(candlangId <= 0)
                                                {
                                                    q_bank = "insert into t_candlang (i_candidateid, i_languageid, i_proficiencyid, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, languageId2);
                                                    pstmt.setInt(3, languagepId2);
                                                    pstmt.setInt(4, 1);
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setString(6, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //language3
                                            if(languageId3 > 0 && languagepId3 > 0)
                                            {
                                                String q_bank = "select i_candlangid from t_candlang where i_candidateid = ? and i_languageid = ? and i_proficiencyid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, languageId3);
                                                pstmt.setInt(3, languagepId3);
                                                rs = pstmt.executeQuery();
                                                int candlangId = 0;
                                                while(rs.next())
                                                {
                                                    candlangId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(candlangId <= 0)
                                                {
                                                    q_bank = "insert into t_candlang (i_candidateid, i_languageid, i_proficiencyid, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, languageId3);
                                                    pstmt.setInt(3, languagepId3);
                                                    pstmt.setInt(4, 1);
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setString(6, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //language4
                                            if(languageId4 > 0 && languagepId4 > 0)
                                            {
                                                String q_bank = "select i_candlangid from t_candlang where i_candidateid = ? and i_languageid = ? and i_proficiencyid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, languageId4);
                                                pstmt.setInt(3, languagepId4);
                                                rs = pstmt.executeQuery();
                                                int candlangId = 0;
                                                while(rs.next())
                                                {
                                                    candlangId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(candlangId <= 0)
                                                {
                                                    q_bank = "insert into t_candlang (i_candidateid, i_languageid, i_proficiencyid, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, languageId4);
                                                    pstmt.setInt(3, languagepId4);
                                                    pstmt.setInt(4, 1);
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setString(6, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //language5
                                            if(languageId5 > 0 && languagepId5 > 0)
                                            {
                                                String q_bank = "select i_candlangid from t_candlang where i_candidateid = ? and i_languageid = ? and i_proficiencyid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, languageId5);
                                                pstmt.setInt(3, languagepId5);
                                                rs = pstmt.executeQuery();
                                                int candlangId = 0;
                                                while(rs.next())
                                                {
                                                    candlangId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(candlangId <= 0)
                                                {
                                                    q_bank = "insert into t_candlang (i_candidateid, i_languageid, i_proficiencyid, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, languageId5);
                                                    pstmt.setInt(3, languagepId5);
                                                    pstmt.setInt(4, 1);
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setString(6, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //language6
                                            if(languageId6 > 0 && languagepId6 > 0)
                                            {
                                                String q_bank = "select i_candlangid from t_candlang where i_candidateid = ? and i_languageid = ? and i_proficiencyid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, languageId6);
                                                pstmt.setInt(3, languagepId6);
                                                rs = pstmt.executeQuery();
                                                int candlangId = 0;
                                                while(rs.next())
                                                {
                                                    candlangId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(candlangId <= 0)
                                                {
                                                    q_bank = "insert into t_candlang (i_candidateid, i_languageid, i_proficiencyid, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, languageId6);
                                                    pstmt.setInt(3, languagepId6);
                                                    pstmt.setInt(4, 1);
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setString(6, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //language7
                                            if(languageId7 > 0 && languagepId7 > 0)
                                            {
                                                String q_bank = "select i_candlangid from t_candlang where i_candidateid = ? and i_languageid = ? and i_proficiencyid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, languageId7);
                                                pstmt.setInt(3, languagepId7);
                                                rs = pstmt.executeQuery();
                                                int candlangId = 0;
                                                while(rs.next())
                                                {
                                                    candlangId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(candlangId <= 0)
                                                {
                                                    q_bank = "insert into t_candlang (i_candidateid, i_languageid, i_proficiencyid, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, languageId7);
                                                    pstmt.setInt(3, languagepId7);
                                                    pstmt.setInt(4, 1);
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setString(6, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //language8
                                            if(languageId8 > 0 && languagepId8 > 0)
                                            {
                                                String q_bank = "select i_candlangid from t_candlang where i_candidateid = ? and i_languageid = ? and i_proficiencyid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, languageId8);
                                                pstmt.setInt(3, languagepId8);
                                                rs = pstmt.executeQuery();
                                                int candlangId = 0;
                                                while(rs.next())
                                                {
                                                    candlangId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(candlangId <= 0)
                                                {
                                                    q_bank = "insert into t_candlang (i_candidateid, i_languageid, i_proficiencyid, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, languageId8);
                                                    pstmt.setInt(3, languagepId8);
                                                    pstmt.setInt(4, 1);
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setString(6, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //language9
                                            if(languageId9 > 0 && languagepId9 > 0)
                                            {
                                                String q_bank = "select i_candlangid from t_candlang where i_candidateid = ? and i_languageid = ? and i_proficiencyid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, languageId9);
                                                pstmt.setInt(3, languagepId9);
                                                rs = pstmt.executeQuery();
                                                int candlangId = 0;
                                                while(rs.next())
                                                {
                                                    candlangId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(candlangId <= 0)
                                                {
                                                    q_bank = "insert into t_candlang (i_candidateid, i_languageid, i_proficiencyid, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, languageId9);
                                                    pstmt.setInt(3, languagepId9);
                                                    pstmt.setInt(4, 1);
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setString(6, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //language10
                                            if(languageId10 > 0 && languagepId10 > 0)
                                            {
                                                String q_bank = "select i_candlangid from t_candlang where i_candidateid = ? and i_languageid = ? and i_proficiencyid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, languageId10);
                                                pstmt.setInt(3, languagepId10);
                                                rs = pstmt.executeQuery();
                                                int candlangId = 0;
                                                while(rs.next())
                                                {
                                                    candlangId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(candlangId <= 0)
                                                {
                                                    q_bank = "insert into t_candlang (i_candidateid, i_languageid, i_proficiencyid, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, languageId10);
                                                    pstmt.setInt(3, languagepId10);
                                                    pstmt.setInt(4, 1);
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setString(6, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //language11
                                            if(languageId11 > 0 && languagepId11 > 0)
                                            {
                                                String q_bank = "select i_candlangid from t_candlang where i_candidateid = ? and i_languageid = ? and i_proficiencyid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, languageId11);
                                                pstmt.setInt(3, languagepId11);
                                                rs = pstmt.executeQuery();
                                                int candlangId = 0;
                                                while(rs.next())
                                                {
                                                    candlangId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(candlangId <= 0)
                                                {
                                                    q_bank = "insert into t_candlang (i_candidateid, i_languageid, i_proficiencyid, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, languageId11);
                                                    pstmt.setInt(3, languagepId11);
                                                    pstmt.setInt(4, 1);
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setString(6, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //language12
                                            if(languageId12 > 0 && languagepId12 > 0)
                                            {
                                                String q_bank = "select i_candlangid from t_candlang where i_candidateid = ? and i_languageid = ? and i_proficiencyid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, languageId12);
                                                pstmt.setInt(3, languagepId12);
                                                rs = pstmt.executeQuery();
                                                int candlangId = 0;
                                                while(rs.next())
                                                {
                                                    candlangId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(candlangId <= 0)
                                                {
                                                    q_bank = "insert into t_candlang (i_candidateid, i_languageid, i_proficiencyid, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, languageId12);
                                                    pstmt.setInt(3, languagepId12);
                                                    pstmt.setInt(4, 1);
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setString(6, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //language13
                                            if(languageId13 > 0 && languagepId13 > 0)
                                            {
                                                String q_bank = "select i_candlangid from t_candlang where i_candidateid = ? and i_languageid = ? and i_proficiencyid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, languageId13);
                                                pstmt.setInt(3, languagepId13);
                                                rs = pstmt.executeQuery();
                                                int candlangId = 0;
                                                while(rs.next())
                                                {
                                                    candlangId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(candlangId <= 0)
                                                {
                                                    q_bank = "insert into t_candlang (i_candidateid, i_languageid, i_proficiencyid, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, languageId13);
                                                    pstmt.setInt(3, languagepId13);
                                                    pstmt.setInt(4, 1);
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setString(6, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //language14
                                            if(languageId14 > 0 && languagepId14 > 0)
                                            {
                                                String q_bank = "select i_candlangid from t_candlang where i_candidateid = ? and i_languageid = ? and i_proficiencyid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, languageId14);
                                                pstmt.setInt(3, languagepId14);
                                                rs = pstmt.executeQuery();
                                                int candlangId = 0;
                                                while(rs.next())
                                                {
                                                    candlangId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(candlangId <= 0)
                                                {
                                                    q_bank = "insert into t_candlang (i_candidateid, i_languageid, i_proficiencyid, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, languageId14);
                                                    pstmt.setInt(3, languagepId14);
                                                    pstmt.setInt(4, 1);
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setString(6, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //language15
                                            if(languageId15 > 0 && languagepId15 > 0)
                                            {
                                                String q_bank = "select i_candlangid from t_candlang where i_candidateid = ? and i_languageid = ? and i_proficiencyid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, languageId15);
                                                pstmt.setInt(3, languagepId15);
                                                rs = pstmt.executeQuery();
                                                int candlangId = 0;
                                                while(rs.next())
                                                {
                                                    candlangId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(candlangId <= 0)
                                                {
                                                    q_bank = "insert into t_candlang (i_candidateid, i_languageid, i_proficiencyid, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, languageId15);
                                                    pstmt.setInt(3, languagepId15);
                                                    pstmt.setInt(4, 1);
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setString(6, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //language16
                                            if(languageId16 > 0 && languagepId16 > 0)
                                            {
                                                String q_bank = "select i_candlangid from t_candlang where i_candidateid = ? and i_languageid = ? and i_proficiencyid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, languageId16);
                                                pstmt.setInt(3, languagepId16);
                                                rs = pstmt.executeQuery();
                                                int candlangId = 0;
                                                while(rs.next())
                                                {
                                                    candlangId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(candlangId <= 0)
                                                {
                                                    q_bank = "insert into t_candlang (i_candidateid, i_languageid, i_proficiencyid, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, languageId16);
                                                    pstmt.setInt(3, languagepId16);
                                                    pstmt.setInt(4, 1);
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setString(6, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //vaccine 1
                                            if(vaccineId1 > 0 && vaccinetype1 > 0)
                                            {
                                                String q_bank = "select i_vbdid from t_vbd where i_candidateid = ? and i_vaccinetypeid = ? and i_vaccinenameid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, vaccinetype1);
                                                pstmt.setInt(3, vaccineId1);
                                                rs = pstmt.executeQuery();
                                                int vbdId = 0;
                                                while(rs.next())
                                                {
                                                    vbdId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(vbdId > 0)
                                                {
                                                    System.out.println("DateofApplication1 :: "+DateofApplication1+" , DateofExpiry1 :: "+DateofExpiry1);
                                                    q_bank = "UPDATE t_vbd set d_dateofapplication = ?, d_dateofexpiry = ?, ts_moddate = ? where i_vbdid = ?";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setString(1, changeDate2(DateofApplication1));
                                                    pstmt.setString(2, changeDate2(DateofExpiry1));
                                                    pstmt.setString(3, currdate);
                                                    pstmt.setInt(4, vbdId);
                                                    System.out.println("UPDATE t_vbd ::: "+pstmt.toString());
                                                    pstmt.executeUpdate();
                                                }
                                                else
                                                {
                                                    System.out.println("DateofApplication_1 :: "+DateofApplication1+" , DateofExpiry_1 :: "+DateofExpiry1);
                                                    q_bank = "insert into t_vbd (i_candidateid, i_vaccinetypeid, i_vaccinenameid, d_dateofapplication, d_dateofexpiry, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?,?,?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, vaccinetype1);
                                                    pstmt.setInt(3, vaccineId1);
                                                    pstmt.setString(4, changeDate2(DateofApplication1));
                                                    pstmt.setString(5, changeDate2(DateofExpiry1));
                                                    pstmt.setInt(6, 1);
                                                    pstmt.setString(7, currdate);
                                                    pstmt.setString(8, currdate);
                                                    System.out.println("Insert t_vbd ::: "+pstmt.toString());
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //vaccine 2
                                            if(vaccineId2 > 0 && vaccinetype2 > 0)
                                            {
                                                String q_bank = "select i_vbdid from t_vbd where i_candidateid = ? and i_vaccinetypeid = ? and i_vaccinenameid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, vaccinetype2);
                                                pstmt.setInt(3, vaccineId2);
                                                rs = pstmt.executeQuery();
                                                int vbdId = 0;
                                                while(rs.next())
                                                {
                                                    vbdId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(vbdId > 0)
                                                {
                                                    System.out.println("DateofApplication2 :: "+DateofApplication2+" , DateofExpiry2 :: "+DateofExpiry2);
                                                    q_bank = "UPDATE t_vbd set d_dateofapplication = ?, d_dateofexpiry = ?, ts_moddate = ? where i_vbdid = ?";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setString(1, changeDate2(DateofApplication2));
                                                    pstmt.setString(2, changeDate2(DateofExpiry2));
                                                    pstmt.setString(3, currdate);
                                                    pstmt.setInt(4, vbdId);
                                                    pstmt.executeUpdate();
                                                }
                                                else
                                                {
                                                    System.out.println("DateofApplication_2 :: "+DateofApplication2+" , DateofExpiry_2 :: "+DateofExpiry2);
                                                    q_bank = "insert into t_vbd (i_candidateid, i_vaccinetypeid, i_vaccinenameid, d_dateofapplication, d_dateofexpiry, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?,?,?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, vaccinetype2);
                                                    pstmt.setInt(3, vaccineId2);
                                                    pstmt.setString(4, changeDate2(DateofApplication2));
                                                    pstmt.setString(5, changeDate2(DateofExpiry2));
                                                    pstmt.setInt(6, 1);
                                                    pstmt.setString(7, currdate);
                                                    pstmt.setString(8, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //vaccine 3
                                            if(vaccineId3 > 0 && vaccinetype3 > 0)
                                            {
                                                String q_bank = "select i_vbdid from t_vbd where i_candidateid = ? and i_vaccinetypeid = ? and i_vaccinenameid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, vaccinetype3);
                                                pstmt.setInt(3, vaccineId3);
                                                rs = pstmt.executeQuery();
                                                int vbdId = 0;
                                                while(rs.next())
                                                {
                                                    vbdId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(vbdId > 0)
                                                {
                                                    System.out.println("DateofApplication3 :: "+DateofApplication3+" , DateofExpiry3 :: "+DateofExpiry3);
                                                    q_bank = "UPDATE t_vbd set d_dateofapplication = ?, d_dateofexpiry = ?, ts_moddate = ? where i_vbdid = ?";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setString(1, changeDate2(DateofApplication3));
                                                    pstmt.setString(2, changeDate2(DateofExpiry3));
                                                    pstmt.setString(3, currdate);
                                                    pstmt.setInt(4, vbdId);
                                                    pstmt.executeUpdate();
                                                }
                                                else
                                                {
                                                    System.out.println("DateofApplication_3 :: "+DateofApplication3+" , DateofExpiry_3 :: "+DateofExpiry3);
                                                    q_bank = "insert into t_vbd (i_candidateid, i_vaccinetypeid, i_vaccinenameid, d_dateofapplication, d_dateofexpiry, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?,?,?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, vaccinetype3);
                                                    pstmt.setInt(3, vaccineId3);
                                                    pstmt.setString(4, changeDate2(DateofApplication3));
                                                    pstmt.setString(5, changeDate2(DateofExpiry3));
                                                    pstmt.setInt(6, 1);
                                                    pstmt.setString(7, currdate);
                                                    pstmt.setString(8, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //qualification 1
                                            if(qualificationtypeId1 > 0 && degreeId1 > 0 && locationId1 > 0)
                                            {
                                                String q_bank = "select i_eduqulid from t_eduqual where i_candidateid = ? and i_qualificationtypeid = ? and i_degreeid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, qualificationtypeId1);
                                                pstmt.setInt(3, degreeId1);
                                                rs = pstmt.executeQuery();
                                                int eduqulId = 0;
                                                while(rs.next())
                                                {
                                                    eduqulId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(eduqulId > 0)
                                                {
                                                    q_bank = "UPDATE t_eduqual set s_eduinstitute = ?, i_locationofinstituteid = ?, d_coursestart = ?, d_passingyear = ?, ts_moddate = ? where i_eduqulid = ?";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setString(1, Education_Institute1);
                                                    pstmt.setInt(2, locationId1);
                                                    pstmt.setString(3, changeDate2(CourseStarted1));
                                                    pstmt.setString(4, changeDate2(PassingDate1));
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setInt(6, eduqulId);
                                                    pstmt.executeUpdate();
                                                }
                                                else
                                                {
                                                    q_bank = "insert into t_eduqual (i_candidateid, s_eduinstitute, i_locationofinstituteid, d_coursestart, d_passingyear, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?,?,?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setString(2, Education_Institute1);
                                                    pstmt.setInt(3, locationId1);
                                                    pstmt.setString(4, changeDate2(CourseStarted1));
                                                    pstmt.setString(5, changeDate2(PassingDate1));
                                                    pstmt.setInt(6, 1);
                                                    pstmt.setString(7, currdate);
                                                    pstmt.setString(8, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }  
                                            //qualification 2
                                            if(qualificationtypeId2 > 0 && degreeId2 > 0 && locationId2 > 0)
                                            {
                                                String q_bank = "select i_eduqulid from t_eduqual where i_candidateid = ? and i_qualificationtypeid = ? and i_degreeid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, qualificationtypeId2);
                                                pstmt.setInt(3, degreeId2);
                                                rs = pstmt.executeQuery();
                                                int eduqulId = 0;
                                                while(rs.next())
                                                {
                                                    eduqulId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(eduqulId > 0)
                                                {
                                                    q_bank = "UPDATE t_eduqual set s_eduinstitute = ?, i_locationofinstituteid = ?, d_coursestart = ?, d_passingyear = ?, ts_moddate = ? where i_eduqulid = ?";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setString(1, Education_Institute2);
                                                    pstmt.setInt(2, locationId2);
                                                    pstmt.setString(3, changeDate2(CourseStarted2));
                                                    pstmt.setString(4, changeDate2(PassingDate2));
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setInt(6, eduqulId);
                                                    pstmt.executeUpdate();
                                                }
                                                else
                                                {
                                                    q_bank = "insert into t_eduqual (i_candidateid, s_eduinstitute, i_locationofinstituteid, d_coursestart, d_passingyear, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?,?,?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setString(2, Education_Institute2);
                                                    pstmt.setInt(3, locationId2);
                                                    pstmt.setString(4, changeDate2(CourseStarted2));
                                                    pstmt.setString(5, changeDate2(PassingDate2));
                                                    pstmt.setInt(6, 1);
                                                    pstmt.setString(7, currdate);
                                                    pstmt.setString(8, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //work exp 1
                                            if(industryId1 > 0 && assettypeId1 > 0 && positionId1 > 0 && cityId_op1 > 0)
                                            {
                                                int currentworkingstatus = 0;
                                                if(Currently_working_here1.equalsIgnoreCase("yes"))
                                                    currentworkingstatus = 1;
                                                String q_bank = "select i_experienceid from t_workexperience where i_candidateid = ? and s_companyname = ? and d_workstartdate = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setString(2, Company_Name1);
                                                pstmt.setString(3, changeDate2(Work_Start_Date1)); 
                                                rs = pstmt.executeQuery();
                                                int experienceId = 0;
                                                while(rs.next())
                                                {
                                                    experienceId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(experienceId > 0)
                                                {
                                                    q_bank = "UPDATE t_workexperience set s_companyname = ?, i_companyindustryid = ?, i_assettypeid = ?, s_assetname = ?, i_positionid = ?, i_departmentid = ?, i_cityid = ?, d_workstartdate = ?, d_workenddate = ?, i_currentworkingstatus = ?, ts_moddate = ? where i_experienceid = ?";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setString(1, Company_Name1);
                                                    pstmt.setInt(2, industryId1);
                                                    pstmt.setInt(3, assettypeId1);
                                                    pstmt.setString(4, Asset_Name1); 
                                                    pstmt.setInt(5, positionId1);
                                                    pstmt.setInt(6, departmentId1);
                                                    pstmt.setInt(7, cityId_op1);
                                                    pstmt.setString(8, changeDate2(Work_Start_Date1));
                                                    pstmt.setString(9, changeDate2(Work_End_Date1));
                                                    pstmt.setInt(10, currentworkingstatus);
                                                    pstmt.setString(11, currdate);
                                                    pstmt.setInt(12, experienceId);
                                                    pstmt.executeUpdate();
                                                }
                                                else
                                                {
                                                    q_bank = "insert into t_workexperience (i_candidateid, s_companyname, i_companyindustryid, i_assettypeid, s_assetname, i_positionid, i_departmentid, i_cityid, d_workstartdate, d_workenddate, i_currentworkingstatus, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?,?,?,?,?, ?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setString(2, Company_Name1);
                                                    pstmt.setInt(3, industryId1);
                                                    pstmt.setInt(4, assettypeId1);
                                                    pstmt.setString(5, Asset_Name1); 
                                                    pstmt.setInt(6, positionId1);
                                                    pstmt.setInt(7, departmentId1);
                                                    pstmt.setInt(8, cityId_op1);
                                                    pstmt.setString(9, changeDate2(Work_Start_Date1));
                                                    pstmt.setString(10, changeDate2(Work_End_Date1));
                                                    pstmt.setInt(11, currentworkingstatus);
                                                    pstmt.setInt(12, 1);
                                                    pstmt.setString(13, currdate);
                                                    pstmt.setString(14, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //work exp 2
                                            if(industryId2 > 0 && assettypeId2 > 0 && positionId2 > 0 && cityId_op2 > 0)
                                            {
                                                int currentworkingstatus = 0;
                                                if(Currently_working_here2.equalsIgnoreCase("yes"))
                                                    currentworkingstatus = 1;
                                                String q_bank = "select i_experienceid from t_workexperience where i_candidateid = ? and s_companyname = ? and d_workstartdate = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setString(2, Company_Name2);
                                                pstmt.setString(3, changeDate2(Work_Start_Date2)); 
                                                rs = pstmt.executeQuery();
                                                int experienceId = 0;
                                                while(rs.next())
                                                {
                                                    experienceId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(experienceId > 0)
                                                {
                                                    q_bank = "UPDATE t_workexperience set s_companyname = ?, i_companyindustryid = ?, i_assettypeid = ?, s_assetname = ?, i_positionid = ?, i_departmentid = ?, i_cityid = ?, d_workstartdate = ?, d_workenddate = ?, i_currentworkingstatus = ?, ts_moddate = ? where i_experienceid = ?";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setString(1, Company_Name2);
                                                    pstmt.setInt(2, industryId2);
                                                    pstmt.setInt(3, assettypeId2);
                                                    pstmt.setString(4, Asset_Name2); 
                                                    pstmt.setInt(5, positionId2);
                                                    pstmt.setInt(6, departmentId2);
                                                    pstmt.setInt(7, cityId_op2);
                                                    pstmt.setString(8, changeDate2(Work_Start_Date2));
                                                    pstmt.setString(9, changeDate2(Work_End_Date2));
                                                    pstmt.setInt(10, currentworkingstatus);
                                                    pstmt.setString(11, currdate);
                                                    pstmt.setInt(12, experienceId);
                                                    pstmt.executeUpdate();
                                                }
                                                else
                                                {
                                                    q_bank = "insert into t_workexperience (i_candidateid, s_companyname, i_companyindustryid, i_assettypeid, s_assetname, i_positionid, i_departmentid, i_cityid, d_workstartdate, d_workenddate, i_currentworkingstatus, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?,?,?,?,?, ?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setString(2, Company_Name2);
                                                    pstmt.setInt(3, industryId2);
                                                    pstmt.setInt(4, assettypeId2);
                                                    pstmt.setString(5, Asset_Name2); 
                                                    pstmt.setInt(6, positionId2);
                                                    pstmt.setInt(7, departmentId2);
                                                    pstmt.setInt(8, cityId_op2);
                                                    pstmt.setString(9, changeDate2(Work_Start_Date2));
                                                    pstmt.setString(10, changeDate2(Work_End_Date2));
                                                    pstmt.setInt(11, currentworkingstatus);
                                                    pstmt.setInt(12, 1);
                                                    pstmt.setString(13, currdate);
                                                    pstmt.setString(14, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //work exp 3
                                            if(industryId3 > 0 && assettypeId3 > 0 && positionId3 > 0 && cityId_op3 > 0)
                                            {
                                                int currentworkingstatus = 0;
                                                if(Currently_working_here3.equalsIgnoreCase("yes"))
                                                    currentworkingstatus = 1;
                                                String q_bank = "select i_experienceid from t_workexperience where i_candidateid = ? and s_companyname = ? and d_workstartdate = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setString(2, Company_Name3);
                                                pstmt.setString(3, changeDate2(Work_Start_Date3)); 
                                                rs = pstmt.executeQuery();
                                                int experienceId = 0;
                                                while(rs.next())
                                                {
                                                    experienceId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(experienceId > 0)
                                                {
                                                    q_bank = "UPDATE t_workexperience set s_companyname = ?, i_companyindustryid = ?, i_assettypeid = ?, s_assetname = ?, i_positionid = ?, i_departmentid = ?, i_cityid = ?, d_workstartdate = ?, d_workenddate = ?, i_currentworkingstatus = ?, ts_moddate = ? where i_experienceid = ?";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setString(1, Company_Name3);
                                                    pstmt.setInt(2, industryId3);
                                                    pstmt.setInt(3, assettypeId3);
                                                    pstmt.setString(4, Asset_Name3); 
                                                    pstmt.setInt(5, positionId3);
                                                    pstmt.setInt(6, departmentId3);
                                                    pstmt.setInt(7, cityId_op3);
                                                    pstmt.setString(8, changeDate2(Work_Start_Date3));
                                                    pstmt.setString(9, changeDate2(Work_End_Date3));
                                                    pstmt.setInt(10, currentworkingstatus);
                                                    pstmt.setString(11, currdate);
                                                    pstmt.setInt(12, experienceId);
                                                    pstmt.executeUpdate();
                                                }
                                                else
                                                {
                                                    q_bank = "insert into t_workexperience (i_candidateid, s_companyname, i_companyindustryid, i_assettypeid, s_assetname, i_positionid, i_departmentid, i_cityid, d_workstartdate, d_workenddate, i_currentworkingstatus, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?,?,?,?,?, ?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setString(2, Company_Name3);
                                                    pstmt.setInt(3, industryId3);
                                                    pstmt.setInt(4, assettypeId3);
                                                    pstmt.setString(5, Asset_Name3); 
                                                    pstmt.setInt(6, positionId3);
                                                    pstmt.setInt(7, departmentId3);
                                                    pstmt.setInt(8, cityId_op3);
                                                    pstmt.setString(9, changeDate2(Work_Start_Date3));
                                                    pstmt.setString(10, changeDate2(Work_End_Date3));
                                                    pstmt.setInt(11, currentworkingstatus);
                                                    pstmt.setInt(12, 1);
                                                    pstmt.setString(13, currdate);
                                                    pstmt.setString(14, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //work exp 4
                                            if(industryId4 > 0 && assettypeId4 > 0 && positionId4 > 0 && cityId_op4 > 0)
                                            {
                                                int currentworkingstatus = 0;
                                                if(Currently_working_here4.equalsIgnoreCase("yes"))
                                                    currentworkingstatus = 1;
                                                String q_bank = "select i_experienceid from t_workexperience where i_candidateid = ? and s_companyname = ? and d_workstartdate = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setString(2, Company_Name4);
                                                pstmt.setString(3, changeDate2(Work_Start_Date4)); 
                                                rs = pstmt.executeQuery();
                                                int experienceId = 0;
                                                while(rs.next())
                                                {
                                                    experienceId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(experienceId > 0)
                                                {
                                                    q_bank = "UPDATE t_workexperience set s_companyname = ?, i_companyindustryid = ?, i_assettypeid = ?, s_assetname = ?, i_positionid = ?, i_departmentid = ?, i_cityid = ?, d_workstartdate = ?, d_workenddate = ?, i_currentworkingstatus = ?, ts_moddate = ? where i_experienceid = ?";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setString(1, Company_Name4);
                                                    pstmt.setInt(2, industryId4);
                                                    pstmt.setInt(3, assettypeId4);
                                                    pstmt.setString(4, Asset_Name4); 
                                                    pstmt.setInt(5, positionId4);
                                                    pstmt.setInt(6, departmentId4);
                                                    pstmt.setInt(7, cityId_op4);
                                                    pstmt.setString(8, changeDate2(Work_Start_Date4));
                                                    pstmt.setString(9, changeDate2(Work_End_Date4));
                                                    pstmt.setInt(10, currentworkingstatus);
                                                    pstmt.setString(11, currdate);
                                                    pstmt.setInt(12, experienceId);
                                                    pstmt.executeUpdate();
                                                }
                                                else
                                                {
                                                    q_bank = "insert into t_workexperience (i_candidateid, s_companyname, i_companyindustryid, i_assettypeid, s_assetname, i_positionid, i_departmentid, i_cityid, d_workstartdate, d_workenddate, i_currentworkingstatus, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?,?,?,?,?, ?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setString(2, Company_Name4);
                                                    pstmt.setInt(3, industryId4);
                                                    pstmt.setInt(4, assettypeId4);
                                                    pstmt.setString(5, Asset_Name4); 
                                                    pstmt.setInt(6, positionId4);
                                                    pstmt.setInt(7, departmentId4);
                                                    pstmt.setInt(8, cityId_op4);
                                                    pstmt.setString(9, changeDate2(Work_Start_Date4));
                                                    pstmt.setString(10, changeDate2(Work_End_Date4));
                                                    pstmt.setInt(11, currentworkingstatus);
                                                    pstmt.setInt(12, 1);
                                                    pstmt.setString(13, currdate);
                                                    pstmt.setString(14, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //work exp 5
                                            if(industryId5 > 0 && assettypeId5 > 0 && positionId5 > 0 && cityId_op5 > 0)
                                            {
                                                int currentworkingstatus = 0;
                                                if(Currently_working_here5.equalsIgnoreCase("yes"))
                                                    currentworkingstatus = 1;
                                                String q_bank = "select i_experienceid from t_workexperience where i_candidateid = ? and s_companyname = ? and d_workstartdate = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setString(2, Company_Name5);
                                                pstmt.setString(3, changeDate2(Work_Start_Date5)); 
                                                rs = pstmt.executeQuery();
                                                int experienceId = 0;
                                                while(rs.next())
                                                {
                                                    experienceId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(experienceId > 0)
                                                {
                                                    q_bank = "UPDATE t_workexperience set s_companyname = ?, i_companyindustryid = ?, i_assettypeid = ?, s_assetname = ?, i_positionid = ?, i_departmentid = ?, i_cityid = ?, d_workstartdate = ?, d_workenddate = ?, i_currentworkingstatus = ?, ts_moddate = ? where i_experienceid = ?";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setString(1, Company_Name5);
                                                    pstmt.setInt(2, industryId5);
                                                    pstmt.setInt(3, assettypeId5);
                                                    pstmt.setString(4, Asset_Name5); 
                                                    pstmt.setInt(5, positionId5);
                                                    pstmt.setInt(6, departmentId5);
                                                    pstmt.setInt(7, cityId_op5);
                                                    pstmt.setString(8, changeDate2(Work_Start_Date5));
                                                    pstmt.setString(9, changeDate2(Work_End_Date5));
                                                    pstmt.setInt(10, currentworkingstatus);
                                                    pstmt.setString(11, currdate);
                                                    pstmt.setInt(12, experienceId);
                                                    pstmt.executeUpdate();
                                                }
                                                else
                                                {
                                                    q_bank = "insert into t_workexperience (i_candidateid, s_companyname, i_companyindustryid, i_assettypeid, s_assetname, i_positionid, i_departmentid, i_cityid, d_workstartdate, d_workenddate, i_currentworkingstatus, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?,?,?,?,?, ?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setString(2, Company_Name5);
                                                    pstmt.setInt(3, industryId5);
                                                    pstmt.setInt(4, assettypeId5);
                                                    pstmt.setString(5, Asset_Name5); 
                                                    pstmt.setInt(6, positionId5);
                                                    pstmt.setInt(7, departmentId5);
                                                    pstmt.setInt(8, cityId_op5);
                                                    pstmt.setString(9, changeDate2(Work_Start_Date5));
                                                    pstmt.setString(10, changeDate2(Work_End_Date5));
                                                    pstmt.setInt(11, currentworkingstatus);
                                                    pstmt.setInt(12, 1);
                                                    pstmt.setString(13, currdate);
                                                    pstmt.setString(14, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            
                                            //ppe 1
                                            if(ppetypeId1 > 0)
                                            {
                                                String q_bank = "select i_ppedetailid from t_ppedetail where i_candidateid = ? and i_ppetypeid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, ppetypeId1);
                                                rs = pstmt.executeQuery();
                                                int ppedetailId = 0;
                                                while(rs.next())
                                                {
                                                    ppedetailId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(ppedetailId > 0)
                                                {
                                                    q_bank = "UPDATE t_ppedetail set s_remarks = ?, ts_moddate = ? where i_ppedetailid = ?";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setString(1, PPEItemSize1);
                                                    pstmt.setString(2, currdate);
                                                    pstmt.setInt(3, ppedetailId);
                                                    pstmt.executeUpdate();
                                                }
                                                else
                                                {
                                                    q_bank = "insert into t_ppedetail (i_candidateid, i_ppetypeid, s_remarks, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, ppetypeId1);
                                                    pstmt.setString(3, PPEItemSize1);
                                                    pstmt.setInt(4, 1);
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setString(6, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
                                            //ppe 2
                                            if(ppetypeId2 > 0)
                                            {
                                                String q_bank = "select i_ppedetailid from t_ppedetail where i_candidateid = ? and i_ppetypeid = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setInt(2, ppetypeId2);
                                                rs = pstmt.executeQuery();
                                                int ppedetailId = 0;
                                                while(rs.next())
                                                {
                                                    ppedetailId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(ppedetailId > 0)
                                                {
                                                    q_bank = "UPDATE t_ppedetail set s_remarks = ?, ts_moddate = ? where i_ppedetailid = ?";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setString(1, PPEItemSize2);
                                                    pstmt.setString(2, currdate);
                                                    pstmt.setInt(3, ppedetailId);
                                                    pstmt.executeUpdate();
                                                }
                                                else
                                                {
                                                    q_bank = "insert into t_ppedetail (i_candidateid, i_ppetypeid, s_remarks, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setInt(2, ppetypeId2);
                                                    pstmt.setString(3, PPEItemSize2);
                                                    pstmt.setInt(4, 1);
                                                    pstmt.setString(5, currdate);
                                                    pstmt.setString(6, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            } 
                                            
                                            //nominee 1
                                            if(relationId > 0)
                                            {
                                                String q_bank = "select i_nomineeid from t_nominee where i_candidateid = ? and s_nomineename = ?";
                                                pstmt = conn.prepareStatement(q_bank);
                                                pstmt.setInt(1, candidateId);
                                                pstmt.setString(2, nominee);
                                                rs = pstmt.executeQuery();
                                                int nomineeId = 0;
                                                while(rs.next())
                                                {
                                                    nomineeId = rs.getInt(1);
                                                }
                                                rs.close();
                                                pstmt.close(); 
                                                if(nomineeId > 0)
                                                {
                                                    q_bank = "UPDATE t_nominee set i_relationid = ?, s_code = ?, s_nomineecontactno = ?, ts_moddate = ? where i_nomineeid = ?";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, relationId);
                                                    pstmt.setString(2, nomineecode);
                                                    pstmt.setString(3, nomineecontact);
                                                    pstmt.setString(4, currdate);
                                                    pstmt.setInt(5, nomineeId);
                                                    pstmt.executeUpdate();
                                                }
                                                else
                                                {
                                                    q_bank = "insert into t_nominee (i_candidateid, s_nomineename, i_relationid, s_code, s_nomineecontactno, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?, ?,?,?) ";
                                                    pstmt = conn.prepareStatement(q_bank);
                                                    pstmt.setInt(1, candidateId);
                                                    pstmt.setString(2, nominee);
                                                    pstmt.setInt(3, relationId);
                                                    pstmt.setString(4, nomineecode);
                                                    pstmt.setString(5, nomineecontact);
                                                    pstmt.setInt(6, 1);
                                                    pstmt.setString(7, currdate);
                                                    pstmt.setString(8, currdate);
                                                    pstmt.executeUpdate();
                                                }
                                            }
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
                    System.out.println("Error due to Country Id :: " + e1);
                    System.out.println("Error due to City Id :: " + e2);
                    System.out.println("Error due to Candidate Id :: " + e3);
                    System.out.println("Error due to Vaccination Name 1 :: " + e4);
                    System.out.println("Error due to Vaccination Type 1 :: " + e5);
                    System.out.println("Error due to Vaccination Name 2 :: " + e6);
                    System.out.println("Error due to Vaccination Type 2 :: " + e7);
                    System.out.println("Error due to Vaccination Name 3 :: " + e8);
                    System.out.println("Error due to Vaccination Type 3 :: " + e9);
                    
                    System.out.println("Error due to Kind 1 :: " + e10);
                    System.out.println("Error due to Degree 1 :: " + e11);
                    System.out.println("Error due to Location 1 :: " + e12);
                    System.out.println("Error due to Kind 2 :: " + e13);
                    System.out.println("Error due to Degree 2 :: " + e14);
                    System.out.println("Error due to Location 2 :: " + e15);
                    
                    System.out.println("Error due to Company Industry 1 :: " + e16);
                    System.out.println("Error due to Asset Type 1 :: " + e17);
                    System.out.println("Error due to Position 1 :: " + e18);
                    System.out.println("Error due to Country of Operation 1 :: " + e19);
                    System.out.println("Error due to City of Operation 1 :: " + e20);
                    System.out.println("Error due to Department Function 1 :: " + e21);
                    
                    System.out.println("Error due to Company Industry 2 :: " + e22);
                    System.out.println("Error due to Asset Type 2 :: " + e23);
                    System.out.println("Error due to Position 2 :: " + e24);
                    System.out.println("Error due to Country of Operation 2 :: " + e25);
                    System.out.println("Error due to City of Operation 2 :: " + e26);
                    System.out.println("Error due to Department Function 2 :: " + e27);
                    
                    System.out.println("Error due to Company Industry 3 :: " + e28);
                    System.out.println("Error due to Asset Type 3 :: " + e29);
                    System.out.println("Error due to Position 3 :: " + e30);
                    System.out.println("Error due to Country of Operation 3 :: " + e31);
                    System.out.println("Error due to City of Operation 3 :: " + e32);
                    System.out.println("Error due to Department Function 3 :: " + e33);
                    
                    System.out.println("Error due to Company Industry 4 :: " + e34);
                    System.out.println("Error due to Asset Type 4 :: " + e35);
                    System.out.println("Error due to Position 4 :: " + e36);
                    System.out.println("Error due to Country of Operation 4 :: " + e37);
                    System.out.println("Error due to City of Operation 4 :: " + e38);
                    System.out.println("Error due to Department Function 4 :: " + e39);
                    
                    System.out.println("Error due to Company Industry 5 :: " + e40);
                    System.out.println("Error due to Asset Type 5 :: " + e41);
                    System.out.println("Error due to Position 5 :: " + e42);
                    System.out.println("Error due to Country of Operation 5 :: " + e43);
                    System.out.println("Error due to City of Operation 5 :: " + e44);
                    System.out.println("Error due to Department Function 5 :: " + e45);
                    
                    System.out.println("Error due to PPE Item Name 1 :: " + e46);
                    System.out.println("Error due to PPE Item Name 2 :: " + e47);
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
        new ImportOcsNew().insertdata();
        System.out.println("Import Complete.");
    }
}
