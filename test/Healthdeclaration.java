import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import jxl.*;
import com.web.jxp.base.Base;

public class Healthdeclaration
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
                    s = workbook.getSheet(2);
                    rowCount = s.getRows();
                    columnCount = s.getColumns();
                   
                    for(int i = 3; i < rowCount; i++)
                    {
                        rowData = s.getRow(i);
                        if(rowData != null)
                        {
                            for(int j = 1; j < columnCount; j++)
                            {
                                String name = "", ssmf = "" , ogukmedicalfitness= "", ogukexpiry= "",medicalfitnesscertificate= "", medicalfitnesscertificateexp="", bloodgroup="", bloodpressure = "", hypertension = "",
                                        diabetes = "", smoking = "", covid192doses= "", covid19vaccinestatus = "", vaccine1doses = "", vaccine1date = "", vaccine2dose= "", vaccine2date= "", vaccine3dose = "",
                                        vaccine3date= "" ;
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
                                    ssmf = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    ogukmedicalfitness = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    ogukexpiry = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    medicalfitnesscertificate = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    medicalfitnesscertificateexp = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
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
                                    bloodpressure = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    hypertension = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    diabetes = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                try
                                {
                                    smoking = rowData[j].getContents() != null ? rowData[j].getContents().trim() : "";
                                    j++;
                                }
                                catch(Exception e){j++;}
                                 
                                
                    
                        try
                        {
                            
                            if(ssmf != "" || !ssmf.equals("") || bloodgroup != "" || !bloodgroup.equals("") || !bloodpressure.equals("") || bloodpressure != "" || hypertension != "" || !hypertension.equals("") )
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
                                    sb.append("insert into t_healthdeclaration ");
                                    sb.append("(s_ssmf,s_ogukmedicalftw, d_ogukexp,s_medifitcert,d_medifitcertexp,s_bloodgroup,s_bloodpressure,s_hypertension,s_diabetes,s_smoking,s_covid192doses,s_covid19vaccinestatus,"
                                                    + " s_vaccine1dose,d_vaccine1date,s_vaccine2dose,d_vaccine2date,s_vaccine3dose,d_vaccine3date,i_candidateid, i_status,"
                                                    + "ts_regdate,ts_moddate ) ");
                                    sb.append("values (?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                    String candidatequery = sb.toString().intern();
                                    sb.setLength(0);

                                            pstmt = conn.prepareStatement(candidatequery, PreparedStatement.RETURN_GENERATED_KEYS);
                                            pstmt.setString(1,ssmf);
                                            pstmt.setString(2, ogukmedicalfitness);
                                            pstmt.setString(3,  base.changeDate5(ogukexpiry));
                                            pstmt.setString(4, medicalfitnesscertificate );
                                            pstmt.setString(5,  base.changeDate5(medicalfitnesscertificateexp));
                                            pstmt.setString(6, bloodgroup);
                                            pstmt.setString(7, bloodpressure);
                                            pstmt.setString(8, hypertension);
                                            pstmt.setString(9, diabetes);
                                            pstmt.setString(10, smoking);
                                            pstmt.setString(11, covid192doses);
                                            pstmt.setString(12, covid19vaccinestatus);
                                            pstmt.setString(13, vaccine1doses);
                                            pstmt.setString(14,  base.changeDate5(vaccine1date));
                                            pstmt.setString(15, vaccine2dose);
                                            pstmt.setString(16,  base.changeDate5(vaccine2date));
                                            pstmt.setString(17, vaccine3dose);
                                            pstmt.setString(18, base.changeDate5(vaccine3date));
                                            pstmt.setInt(19, candidateid);
                                            pstmt.setInt(20, 1);
                                            pstmt.setString(21, base.currDate1());
                                            pstmt.setString(22, base.currDate1());
                                            
                                            pstmt.executeUpdate();
                                            rs = pstmt.getGeneratedKeys();             
                                            while (rs.next())
                                            {
                                               int  cc = rs.getInt(1);
                                               
                                            }
                                            rs.close();
                                            pstmt.close();
                            }}
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
        new Healthdeclaration().createSheet();
    }
}
