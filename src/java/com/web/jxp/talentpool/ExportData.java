
package com.web.jxp.talentpool;

import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExportData extends Action
{        
    @Override
    @SuppressWarnings("static-access")
    public ActionForward execute (ActionMapping mapping, ActionForm form,  HttpServletRequest request, HttpServletResponse response)   throws Exception 
    { 
        Connection conn = null;
       try
        {
            Talentpool talentpool = new Talentpool();
            Ddl ddl = new Ddl();
            Export export = new Export();            
            String empno = "";
              
            String fileName = "Candidate_Data_Report";
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Personal");
            ArrayList per_list = export.getPersonalDataDump(conn, empno);
            int total = per_list.size();
            
            HSSFSheet sheet1 = wb.createSheet("Education");
            ArrayList edu_list = export.getEducationDump(conn, empno);
            int total_edu = edu_list.size();
            
            HSSFSheet sheet2 = wb.createSheet("Experience");
            ArrayList exp_list = export.getExperienceDataDump(conn, empno);
            int total_exp = exp_list.size(); 
            
            HSSFSheet sheet3 = wb.createSheet("Vaccination");
            ArrayList vac_list = export.getVaccinationDump(conn,empno);
            int total_vac = vac_list.size();            
            
            HSSFSheet sheet4 = wb.createSheet("Documents");
            ArrayList doc_list = export.getDocumentDump(conn,empno);
            int total_doc = doc_list.size();
            
            HSSFSheet sheet5 = wb.createSheet("Certifications");
            ArrayList cert_list = export.getCertificationDump(conn,empno);
            int total_cert = cert_list.size();
            
            HSSFSheet sheet6 = wb.createSheet("Health");
            ArrayList health_list = export.getHealthDetailDump(conn,empno);
            int total_health = health_list.size();
            
            HSSFSheet sheet7 = wb.createSheet("Language");
            ArrayList lang_list = export.getLanguageDump(conn,empno);
            int total_lang = lang_list.size();
            
            HSSFSheet sheet8 = wb.createSheet("Bank Details");
            ArrayList bank_list = export.getBankdetailsDump(conn, empno);
            int total_bank = bank_list.size();
            
            HSSFSheet sheet9 = wb.createSheet("Remarks");
            ArrayList rem_list = export.getRemarksDump(conn, empno);
            int total_rem = rem_list.size();
            
            HSSFSheet sheet10 = wb.createSheet("Nominee");
            ArrayList nom_list = export.getNomineeDetailDump(conn, empno);
            int total_nom = nom_list.size();
           
            HSSFSheet sheet11 = wb.createSheet("Contract");
            ArrayList con_list = export.getContractDump(conn, empno);
            int total_con = con_list.size();
            
            HSSFSheet sheet12 = wb.createSheet("PPE");
            ArrayList ppe_list = export.getPpeDump(conn, empno);
            int total_ppe = ppe_list.size();
            
            HSSFSheet sheet13 = wb.createSheet("Appraisal");
            ArrayList app_list = export.getAppraisalDump(conn, empno);
            int total_app = app_list.size();
            
            HSSFSheet sheet14 = wb.createSheet("Day Rate");
            ArrayList dayrate_list = export.getDayrateDump(conn, empno);
            int total_dayrate = dayrate_list.size();
            
            HSSFRow row = null;
            HSSFCell cell = null;
            
            HSSFFont titleFont = wb.createFont();
            titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            titleFont.setFontHeightInPoints((short)12);

            HSSFFont mergeFont = wb.createFont();
            mergeFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            mergeFont.setFontHeightInPoints((short)10);

            HSSFFont headFont = wb.createFont();
            headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            headFont.setFontHeightInPoints((short)10);
            
            HSSFFont contentFont = wb.createFont();
            contentFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            contentFont.setFontHeightInPoints((short)10);
            
            HSSFFont contentFontBold = wb.createFont();
            contentFontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            contentFontBold.setFontHeightInPoints((short)10);

            HSSFCellStyle titlestyle = wb.createCellStyle();
            titlestyle.setFont(titleFont);
            titlestyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
            titlestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            HSSFCellStyle mergestyle = wb.createCellStyle();
            mergestyle.setFont(mergeFont);
            mergestyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

            HSSFCellStyle headstyle = wb.createCellStyle();
            headstyle.setFont(headFont);
            headstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            
            HSSFCellStyle headstyle_right = wb.createCellStyle();
            headstyle_right.setFont(headFont);
            headstyle_right.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

            HSSFCellStyle contentstyle = wb.createCellStyle();
            contentstyle.setFont(contentFont);
            contentstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            
            HSSFCellStyle contentstyle_right = wb.createCellStyle();
            contentstyle_right.setFont(contentFont);
            contentstyle_right.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            
            HSSFCellStyle contentstylebold = wb.createCellStyle();
            contentstylebold.setFont(contentFontBold);
            contentstylebold.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            
            int rowval = 0;
            int c = 0;            
            row = sheet.createRow(rowval); 
            
            cell = row.createCell(c);
            cell.setCellValue("EmployeeId");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Name");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;  
            
            cell = row.createCell(c);
            cell.setCellValue("Client");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Asset");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++; 
            
            cell = row.createCell(c);
            cell.setCellValue("Position-Rank");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Primary Contact Number");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Emergency Contact Number");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Secondary Contact Number");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Additional Contact Number");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Email Address");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Date Of Birth");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Place of Birth");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Gender");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Marital Status");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Next Of Kin");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Relation");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Candidate Country");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Candidate City");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Permanent Address Line 1");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Permanent Address Line 2");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Permanent Address Line 3");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Communication Address Line 1");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Communication Address Line 2");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Communication Address Line 3");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Nationality");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Preferred Department");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Currency");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Expected Salary");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Crew Day Rate");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Overtime");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
         
            int col = 0;
            int rownum = 1;         
            for(int i = 0; i < total;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) per_list.get(i);
                if(info != null)
                {
                    col = 0 ;
                    row = sheet.createRow(rownum++);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEmployeeId()!= null ? info.getEmployeeId() : "");
                    cell.setCellStyle(contentstyle);
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getName()!= null ? info.getName() : "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getClientName()!= null ? info.getClientName() : "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getClientAsset()!= null ? info.getClientAsset() : "");
                    cell.setCellStyle(contentstyle);
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPosition()!= null ? info.getPosition() : "");
                    cell.setCellStyle(contentstyle);
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getContactno1()!= null ? info.getContactno1() : "");
                    cell.setCellStyle(contentstyle);
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEcontactno1()!= null ? info.getEcontactno1() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getContactno2()!= null ? info.getContactno2() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getContactno3()!= null ? info.getContactno3() : "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEmailId()!= null ? info.getEmailId() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDob()!= null ? info.getDob() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPlaceofbirth()!= null ? info.getPlaceofbirth() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getGender()!= null ? info.getGender() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getMaritialstatus()!= null ? info.getMaritialstatus() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getNextofkin()!= null ? info.getNextofkin() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getRelation()!= null ? info.getRelation() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCountryName()!= null ? info.getCountryName() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCity()!= null ? info.getCity() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAddress1line1()!= null ? info.getAddress1line1() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAddress1line2()!= null ? info.getAddress1line2() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAddress1line3()!= null ? info.getAddress1line3() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAddress1line3()!= null ? info.getAddress2line1() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAddress1line3()!= null ? info.getAddress2line2() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAddress1line3()!= null ? info.getAddress2line3() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getNationality()!= null ? info.getNationality() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDepartment()!= null ? info.getDepartment() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCurrency()!= null ? info.getCurrency() : "");
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getExpectedsalary());
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getRate1());
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getRate2());
                    cell.setCellStyle(contentstyle);                                         
                }
                info = null;
            }
            per_list.clear();
            
            row = null;
            cell = null;
            rowval = 0;
            c = 0;
            row = sheet1.createRow(rowval); 
            
            cell = row.createCell(c);
            cell.setCellValue("EmployeeId");
            sheet1.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Kind");
            sheet1.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Degree");
            sheet1.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Institution");
            sheet1.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Location");
            sheet1.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Start Date");
            sheet1.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("End Date");
            sheet1.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Highest Qualification");
            sheet1.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet1.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;            
            
            col = 0;
            rownum = 1;
         
            for(int i = 0; i < total_edu;i++)
            {
                ExportInfo info = (ExportInfo) edu_list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet1.createRow(rownum++); 
                  
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEmpno()!= null ? info.getEmpno(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getKindname()!= null ? info.getKindname(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDegree()!= null ? info.getDegree(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEduinstitute()!= null ? info.getEduinstitute(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getLocationofInstitute()!= null ? info.getLocationofInstitute(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCoursestart()!= null ? info.getCoursestart(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPassingyear()!= null ? info.getPassingyear(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getHighestqualification()== 1 ? "yes" : "no");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(export.getStatusById(info.getStatus()));
                    cell.setCellStyle(contentstyle);                 
                }
                info = null;
            }
            edu_list.clear();            
            //Experience
            row = null;
            cell = null;
            rowval = 0;
            c = 0;            
            row = sheet2.createRow(rowval); 
            
            cell = row.createCell(c);
            cell.setCellValue("EmployeeId");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Company Name");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Company Industry");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Asset Type");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Asset Name");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Position");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Department/Function");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Country");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("City");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Operater");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Water Depth");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Last Drawn Salary(currency)");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;            
            
            cell = row.createCell(c);
            cell.setCellValue("Last Drawn Salary");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Work Start date");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Work End date");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Currently Working");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Skills");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Ranks");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("OwnerPool");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("CrewType");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("OCS Employed");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Legal Rights");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Day Rate(currency)");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Day Rate");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Monthly Salary(currency)");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Monthly Salary");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet2.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;            
            
            col = 0;
            rownum = 1;         
            for(int i = 0; i < total_exp;i++)
            {
                ExportInfo info = (ExportInfo) exp_list.get(i);
                if(info != null)
                {
                    col = 0 ;
                    row = sheet2.createRow(rownum++); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEmpno()!= null ? info.getEmpno(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCompanyname()!= null ? info.getCompanyname(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCompayIndname()!= null ? info.getCompayIndname(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAssettypename()!= null ? info.getAssettypename(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAssetName()!= null ? info.getAssetName(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPosition()!= null ? info.getPosition(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDepartment()!= null ? info.getDepartment(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCountryname()!= null ? info.getCountryname(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCityname()!= null ? info.getCityname(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getOperator()!= null ? info.getOperator(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getWaterdepth()!= null ? info.getWaterdepth(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getLastdrawncurrrency()!= null ? info.getLastdrawncurrrency(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getLastdrawncurrrencyvalue());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getWorkstartdate()!= null ? info.getWorkstartdate(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCurrentworkingstatus()!= 1 && info.getWorkenddate()!= null ? info.getWorkenddate() : "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCurrentworkingstatus()== 1 ? "Yes" : "No");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getSkills()!= null ? info.getSkills(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getRank()!= null ? info.getRank(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getOwnerpool()!= null ? info.getOwnerpool(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCrewtypename()!= null ? info.getCrewtypename(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getOcsemployed()!= null ? info.getOcsemployed(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getLegalrights()!= null ? info.getLegalrights(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDaterate()!= null ? info.getDaterate(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDayvalue());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getMonthrate()!= null ? info.getMonthrate(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getMonthvalue());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(talentpool.getStatusById(info.getStatus()));
                    cell.setCellStyle(contentstyle);                 
                }
                info = null;
            }
            exp_list.clear();
           //Vaccination 
            row = null;
            cell = null;
            rowval = 0;
            c = 0;
            
            row = sheet3.createRow(rowval);
            
            cell = row.createCell(c);
            cell.setCellValue("EmployeeId");
            sheet3.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Name");
            sheet3.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Type");
            sheet3.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Place");
            sheet3.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Application Date");
            sheet3.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Expiration Date");
            sheet3.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet3.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            col = 0;
            rownum = 1;         
            for(int i = 0; i < total_vac;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) vac_list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet3.createRow(rownum++); 
                  
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEmployeeId()!= null ? info.getEmployeeId(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getVaccinationName()!= null ? info.getVaccinationName(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getVacinationType()!= null ? info.getVacinationType(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPlaceofapplication()!= null ? info.getPlaceofapplication(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDateofapplication()!= null ? info.getDateofapplication(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDateofexpiry()!= null ? info.getDateofexpiry(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(talentpool.getStatusById(info.getStatus()));
                    cell.setCellStyle(contentstyle);                    
                }
                info = null;
            }
            vac_list.clear();
            //Document
            row = null;
            cell = null;
            rowval = 0;
            c = 0;            
            row = sheet4.createRow(rowval);
            
            cell = row.createCell(c);
            cell.setCellValue("EmployeeId");
            sheet4.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Document Name");
            sheet4.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Document Number");
            sheet4.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Place of Issue");
            sheet4.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Date of Issue");
            sheet4.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Date of Expiry");
            sheet4.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Document Issued By");
            sheet4.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
                      
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet4.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            col = 0;
            rownum = 1;
         
            for(int i = 0; i < total_doc;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) doc_list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet4.createRow(rownum++); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEmployeeId()!= null ? info.getEmployeeId(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDocumentname()!= null ? info.getDocumentname(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDocumentno()!= null ? info.getDocumentno(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPlaceofissue()!= null ? info.getPlaceofissue(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getIssuedby()!= null ? info.getIssuedby(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDateofissue()!= null ? info.getDateofissue(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDateofexpiry()!= null ? info.getDateofexpiry(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(talentpool.getStatusById(info.getStatus()));
                    cell.setCellStyle(contentstyle);
                }
                info = null;
            }
            doc_list.clear();            
            
            //Certifications
            row = null;
            cell = null;
            rowval = 0;
            c = 0;
            
            row = sheet5.createRow(rowval);
            
            cell = row.createCell(c);
            cell.setCellValue("EmployeeId");
            sheet5.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Course Type");
            sheet5.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Course Name");
            sheet5.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Educational Institute");
            sheet5.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Location of Institute(city)");
            sheet5.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Field of study");
            sheet5.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Course Start Date");
            sheet5.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Passing Date");
            sheet5.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Date of Issue");
            sheet5.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Certification No");
            sheet5.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Date of Expiry");
            sheet5.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Approved By");
            sheet5.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet5.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            col = 0;
            rownum = 1;
         
            for(int i = 0; i < total_cert;i++)
            {
                ExportInfo info = (ExportInfo) cert_list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet5.createRow(rownum++); 

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEmpno()!= null ? info.getEmpno(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCoursetype()!= null ? info.getCoursetype(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCoursename()!= null ? info.getCoursename(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEduinstitute()!= null ? info.getEduinstitute(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getLocationofInstitute()!= null ? info.getLocationofInstitute(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getFieldofstudy()!= null ? info.getFieldofstudy(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCoursestart()!= null ? info.getCoursestart(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPassingyear()!= null ? info.getPassingyear(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDateofissue()!= null ? info.getDateofissue(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getExpirydate()!= null ? info.getExpirydate(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCertificateno()!= null ? info.getCertificateno(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getApprovedby()!= null ? info.getApprovedby(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(talentpool.getStatusById(info.getStatus()));
                    cell.setCellStyle(contentstyle);                    
                }
                info = null;
            }
            cert_list.clear();
            
            //Health
            row = null;
            cell = null;
            rowval = 0;
            c = 0;            
            row = sheet6.createRow(rowval);
            
            cell = row.createCell(c);
            cell.setCellValue("EmployeeId");
            sheet6.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Seaman Specific Medical Fitness");
            sheet6.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("OGUK Medical FTW");
            sheet6.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("OGUK Expiry");
            sheet6.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Medical Fitness Certificate");
            sheet6.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Medical Fitness Certificate Expiry");
            sheet6.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Blood Group");
            sheet6.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Blood Pressure");
            sheet6.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Hypertension");
            sheet6.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Diabetes");
            sheet6.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Smoking");
            sheet6.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Covid-19 2 Doses");
            sheet6.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            col = 0;
            rownum = 1;
            for(int i = 0; i < total_health;i++)
            {
                TalentpoolInfo hinfo = (TalentpoolInfo) health_list.get(i);
                if(hinfo != null)
                {                    
                    col = 0 ;
                    row = sheet6.createRow(rownum++); 

                    cell = row.createCell(col++);
                    cell.setCellValue(hinfo.getEmployeeId()!= null ? hinfo.getEmployeeId(): "");
                    cell.setCellStyle(contentstyle); 

                    cell = row.createCell(col++);
                    cell.setCellValue(hinfo.getSsmf()!= null ? hinfo.getSsmf(): "");
                    cell.setCellStyle(contentstyle); 

                    cell = row.createCell(col++);
                    cell.setCellValue(hinfo.getOgukmedicalftw()!= null ? hinfo.getOgukmedicalftw(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(hinfo.getOgukexp()!= null ? hinfo.getOgukexp(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(hinfo.getMedifitcert()!= null ? hinfo.getMedifitcert(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(hinfo.getMedifitcertexp()!= null ? hinfo.getMedifitcertexp(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(hinfo.getBloodgroup()!= null ? hinfo.getBloodgroup(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(hinfo.getBloodpressure()!= null ? hinfo.getBloodpressure(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(hinfo.getHypertension()!= null ? hinfo.getHypertension(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(hinfo.getDiabetes()!= null ? hinfo.getDiabetes(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(hinfo.getSmoking()!= null ? hinfo.getSmoking(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(hinfo.getCov192doses()!= null ? hinfo.getCov192doses(): "");
                    cell.setCellStyle(contentstyle);                
                }
                hinfo = null;
            }
            health_list.clear();
            
            //Language
            row = null;
            cell = null;
            rowval = 0;
            c = 0;            
            row = sheet7.createRow(rowval); 
            
            cell = row.createCell(c);
            cell.setCellValue("EmployeeId");
            sheet7.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Language");
            sheet7.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Proficiency");
            sheet7.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet7.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
                        
            col = 0;
            rownum = 1;         
            for(int i = 0; i < total_lang;i++)
            {
                ExportInfo info = (ExportInfo) lang_list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet7.createRow(rownum++);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEmpno()!= null ? info.getEmpno(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getLanguageName()!= null ? info.getLanguageName(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getProficiency()!= null ? info.getProficiency(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(export.getStatusById(info.getStatus()));
                    cell.setCellStyle(contentstyle);                                        
                }
                info = null;
            }
            lang_list.clear();
            
            //Bank
            row = null;
            cell = null;
            rowval = 0;
            c = 0;            
            row = sheet8.createRow(rowval);  
            
            cell = row.createCell(c);
            cell.setCellValue("EmployeeId");
            sheet8.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Bank Name");
            sheet8.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Type of Account");
            sheet8.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Account No");
            sheet8.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Branch");
            sheet8.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("IFSC");
            sheet8.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Primary Account");
            sheet8.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
                      
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet8.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;            
                        
            col = 0;
            rownum = 1;         
            for(int i = 0; i < total_bank;i++)
            {
                ExportInfo info = (ExportInfo) bank_list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet8.createRow(rownum++); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEmpno()!= null ? info.getEmpno(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getBankName()!= null ? info.getBankName(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAccountTypename()!= null ? info.getAccountTypename(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAccountno()!= null ? info.getAccountno(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getBranch()!= null ? info.getBranch(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getIFSCCode()!= null ? info.getIFSCCode(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPrimarybankId() == 1 ? "Primary" : "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(talentpool.getStatusById(info.getStatus()));
                    cell.setCellStyle(contentstyle);
                }
                info = null;
            }
            bank_list.clear();
            
            //Remarks tab
            row = null;
            cell = null;
            rowval = 0;
            c = 0;            
            row = sheet9.createRow(rowval);

            cell = row.createCell(c);
            cell.setCellValue("EmployeeId");
            sheet9.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Date");
            sheet9.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Client");
            sheet9.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Asset");
            sheet9.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Remark Type");
            sheet9.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Remarks");
            sheet9.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet9.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            col = 0;
            rownum = 1;
            for(int i = 0; i < total_rem;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) rem_list.get(i);
                if(info != null)
                {
                    col = 0 ;
                    row = sheet9.createRow(rownum++);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEmployeeId()!= null ? info.getEmployeeId(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDate()!= null ? info.getDate(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getClientName()!= null ? info.getClientName(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAssetName()!= null ? info.getAssetName(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getTypeName()!= null ? info.getTypeName(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getRemarks()!= null ? info.getRemarks(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(ddl.getStatusById(info.getStatus()));
                    cell.setCellStyle(contentstyle);
                }
                info = null;
            }
            rem_list.clear();
            
            //Nominee tab
            row = null;
            cell = null;
            rowval = 0;
            c = 0;            
            row = sheet10.createRow(rowval);

            cell = row.createCell(c);
            cell.setCellValue("EmployeeId");
            sheet10.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Nominee Name");
            sheet10.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Nominee Contact Number");
            sheet10.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Nominee Relation");
            sheet10.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Nominee Address");
            sheet10.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Nominee Age");
            sheet10.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Percentage Of Distribution");
            sheet10.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet10.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            col = 0;
            rownum = 1;
            for(int i = 0; i < total_nom;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) nom_list.get(i);
                if(info != null)
                {
                    col = 0 ;
                    row = sheet10.createRow(rownum++);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEmployeeId()!= null ? info.getEmployeeId(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getNomineeName()!= null ? info.getNomineeName(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getNomineeContactno()!= null ? info.getNomineeContactno(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getNomineeRelation()!= null ? info.getNomineeRelation(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAddress()!= null ? info.getAddress(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAge());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPercentage());
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(ddl.getStatusById(info.getStatus()));
                    cell.setCellStyle(contentstyle);
                }
                info = null;
            }
            nom_list.clear();
            
            //Contract tab
            row = null;
            cell = null;
            rowval = 0;
            c = 0;            
            row = sheet11.createRow(rowval);

            cell = row.createCell(c);
            cell.setCellValue("EmployeeId");
            sheet11.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Position");
            sheet11.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Client");
            sheet11.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Asset");
            sheet11.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Type");
            sheet11.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Contract Status");
            sheet11.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("From");
            sheet11.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("To");
            sheet11.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Crew Approval");
            sheet11.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Company Approval");
            sheet11.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet11.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            col = 0;
            rownum = 1;
            for(int i = 0; i < total_con;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) con_list.get(i);
                if(info != null)
                {
                    col = 0 ;
                    row = sheet11.createRow(rownum++);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEmployeeId()!= null ? info.getEmployeeId(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPositionname()!= null ? info.getPositionname(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getClientName()!= null ? info.getClientName(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAssetName()!= null ? info.getAssetName(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getType() == 1 ? "Main" : "Repeated");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getStatusValue() != null ? info.getStatusValue() : "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getFromDate() != null ? info.getFromDate() : "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getToDate() != null ? info.getToDate() : "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getApproval1() == 1 ? "Yes" : "No");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getApproval2() == 1 ? "Yes" : "No");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(ddl.getStatusById(info.getStatus()));
                    cell.setCellStyle(contentstyle);
                }
                info = null;
            }
            con_list.clear();
            
            //ppe tab
            row = null;
            cell = null;
            rowval = 0;
            c = 0;            
            row = sheet12.createRow(rowval);

            cell = row.createCell(c);
            cell.setCellValue("EmployeeId");
            sheet12.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Item Name");
            sheet12.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Size");
            sheet12.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Date Of Entry");
            sheet12.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Entered By");
            sheet12.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet12.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            col = 0;
            rownum = 1;
            for(int i = 0; i < total_ppe;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) ppe_list.get(i);
                if(info != null)
                {
                    col = 0 ;
                    row = sheet12.createRow(rownum++);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEmployeeId()!= null ? info.getEmployeeId(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPpetype()!= null ? info.getPpetype(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getRemarks()!= null ? info.getRemarks(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDate()!= null ? info.getDate(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getUsername()!= null ? info.getUsername(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(ddl.getStatusById(info.getStatus()));
                    cell.setCellStyle(contentstyle);
                }
                info = null;
            }
            ppe_list.clear();
            
            //Appraisal
            row = null;
            cell = null;
            rowval = 0;
            c = 0;
            row = sheet13.createRow(rowval);

            cell = row.createCell(c);
            cell.setCellValue("EmployeeId");
            sheet13.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("New Position");
            sheet13.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Position Earlier");
            sheet13.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Salary Effective from Date");
            sheet13.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Remarks");
            sheet13.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet13.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            col = 0;
            rownum = 1;
            for(int i = 0; i < total_app;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) app_list.get(i);
                if(info != null)
                {
                    col = 0 ;
                    row = sheet13.createRow(rownum++);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEmployeeId()!= null ? info.getEmployeeId(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPosition2()!= null ? info.getPosition2(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPosition()!= null ? info.getPosition(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDate()!= null ? info.getDate(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getRemarks()!= null ? info.getRemarks() : "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(ddl.getStatusById(info.getStatus()));
                    cell.setCellStyle(contentstyle);
                }
                info = null;
            }
            app_list.clear();
            
            //day rate
            row = null;
            cell = null;
            rowval = 0;
            c = 0;
            row = sheet14.createRow(rowval);

            cell = row.createCell(c);
            cell.setCellValue("EmployeeId");
            sheet14.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Position | Rank");
            sheet14.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("From Date");
            sheet14.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("To Date");
            sheet14.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Day Rate");
            sheet14.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Overtime");
            sheet14.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Allowance");
            sheet14.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            col = 0;
            rownum = 1;
            for(int i = 0; i < total_dayrate;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) dayrate_list.get(i);
                if(info != null)
                {
                    col = 0 ;
                    row = sheet14.createRow(rownum++);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEmployeeId()!= null ? info.getEmployeeId(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPosition()!= null ? info.getPosition(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getFromDate()!= null ? info.getFromDate(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getToDate()!= null ? info.getToDate(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getRate1());
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getRate2());
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getRate3());
                    cell.setCellStyle(contentstyle);
                }
                info = null;
            }
            dayrate_list.clear();
            
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename = \""+fileName+".xls\"");
           try (ServletOutputStream os = response.getOutputStream()) {
               wb.write(os);
               os.flush();
           }
        }
        catch (Exception e)
        {   
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
          return null;
    }
}
