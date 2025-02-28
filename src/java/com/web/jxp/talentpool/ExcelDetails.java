package com.web.jxp.talentpool;

import com.web.jxp.user.UserInfo;
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

public class ExcelDetails extends Action
{        
    @Override
    @SuppressWarnings("static-access")
    public ActionForward execute (ActionMapping mapping, ActionForm form,  HttpServletRequest request, HttpServletResponse response)   throws Exception 
    { 
       try
        {        
            
            Ddl ddl = new Ddl();
            int allclient = 0;
            String permission = "N", cids = "", assetids = "";
            if (request.getSession().getAttribute("LOGININFO") != null) 
            {
                UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
                if (uInfo != null) 
                {
                    permission = uInfo.getPermission();
                    cids = uInfo.getCids();
                    allclient = uInfo.getAllclient();
                    assetids = uInfo.getAssetids();
                }
            }
            TalentpoolForm frm = (TalentpoolForm)form;         
            String search = frm.getSearch() != null ? frm.getSearch() : "";
            int statusIndex = frm.getStatusIndex(); 
            int positionIndexId = frm.getPositionIndexId();
            int clientIndex = frm.getClientIndex();
            int locationIndex = frm.getLocationIndex();
            int assetIndex = frm.getAssetIndex();
            int employementstatus = frm.getEmployementstatus();
            int assettypeIdIndex = frm.getAssettypeIdIndex();
            frm.setAssettypeIdIndex(assettypeIdIndex);
            int positionFilterId = frm.getPositionFilterId();
            frm.setPositionFilterId(positionFilterId);

            int verified = 0;
            verified = frm.getVerified();
            ArrayList list = ddl.getExcelDetails(search, statusIndex, positionIndexId,
            clientIndex, locationIndex, assetIndex, verified,  employementstatus, allclient,
            permission, cids, assetids, assettypeIdIndex,  positionFilterId);
            int total = list.size();  
            String fileName = "Data_Report";
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Data");
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
            cell.setCellValue("Client");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Asset");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Employee Id");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Name");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;         
            
            cell = row.createCell(c);
            cell.setCellValue("Position-Rank");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Primary Contact Number");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Emergency Contact Number");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Secondary Contact Number");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Additional Contact Number");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Email Address");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Date Of Birth");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Place of Birth");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Gender");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Marital Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Next Of Kin");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Relation");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Candidate Country");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Candidate City");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Permanent Address Line 1");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Permanent Address Line 2");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Permanent Address Line 3");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Communication Address Line 1");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Communication Address Line 2");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Communication Address Line 3");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Nationality");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Preferred Department");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Currency");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Expected Salary");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Crew Day Rate");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Overtime");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Language");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Health");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Vaccination");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Experience");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Education");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Certifications");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Documents");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("PersonalDataAdded");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("OtherDataAdded");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("TotalData");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < total;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++);                    
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getClientName()!= null ? info.getClientName() : "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getClientAsset()!= null ? info.getClientAsset() : "");
                    cell.setCellStyle(contentstyle);
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEmployeeId()!= null ? info.getEmployeeId() : "");
                    cell.setCellStyle(contentstyle);
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getName()!= null ? info.getName() : "");
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
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCt1());
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCt2());
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCt3());
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCt4());
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCt5());
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCt6());
                    cell.setCellStyle(contentstyle);                                                         
                                                         
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCt7());
                    cell.setCellStyle(contentstyle);   
                           
                    int cc = 0, st = 0;                    
                    if(info.getName() != null && !info.getName().equals("") )
                    {
                        st +=1;
                    }
                    if(info.getPosition()!= null && !info.getPosition().equals("") )
                    {
                        st +=1;
                    }
                    if(info.getContactno1()!= null && !info.getContactno1().equals("") )
                    {
                        st +=1;
                    }
                    if(info.getEcontactno1()!= null && !info.getEcontactno1().equals("") )
                    {
                        st +=1;
                    }
                    if(info.getEmailId()!= null && !info.getEmailId().equals("") )
                    {
                        st +=1;
                    }
                    if(info.getDob()!= null && !info.getDob().equals("") )
                    {
                        st +=1;
                    }
                    if(info.getGender()!= null && !info.getGender().equals("") )
                    {
                        st +=1;
                    }
                    if(info.getMaritialstatus()!= null && !info.getMaritialstatus().equals("") )
                    {
                        st +=1;
                    }
                    if(info.getCountryName()!= null && !info.getCountryName().equals("") )
                    {
                        st +=1;
                    }
                    if(info.getCity()!= null && !info.getCity().equals("") )
                    {
                        st +=1;
                    }                    
                    if(info.getAddress1line1()!= null && !info.getAddress1line1().equals("") )
                    {
                        st +=1;
                    }
                    if(info.getNationality()!= null && !info.getNationality().equals("") )
                    {
                        st +=1;
                    }
                    if(info.getCurrency()!= null && !info.getCurrency().equals("") )
                    {
                        st +=1;
                    }
                    if(info.getRate1()> 0 )
                    {
                        st +=1;
                    }
                    if(info.getRate2()> 0 )
                    {
                        st +=1;
                    }  
                    double v1 = 0.0, v2 =0.0, v3 = 0.0;
                    
                    v1 = ddl.getDecimalDouble((st/(double)15) *100.00);                    
                    
                    cell = row.createCell(col++);
                    cell.setCellValue( ddl.getDecimal((st /(double)15) *100.00)+"%");
                    cell.setCellStyle(contentstyle);
                    
                    if(info.getCt1()> 0)
                    {
                        cc += 1;
                    }
                    if(info.getCt2()> 0)
                    {
                        cc += 1;
                    }
                    if(info.getCt3()> 0)
                    {
                        cc += 1;
                    }
                    if(info.getCt4()> 0)
                    {
                        cc += 1;
                    }
                    if(info.getCt5()> 0)
                    {
                        cc += 1;
                    }
                    if(info.getCt6()> 0)
                    {
                        cc += 1;
                    }
                    if(info.getCt7()> 0)
                    {
                        cc += 1;
                    }      
                    v2 = ddl.getDecimalDouble((cc/(double)7) *100.00);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue( ddl.getDecimal((cc /(double)7) *100.00)+"%");
                    cell.setCellStyle(contentstyle);
                    
                    v3= ((v1+v2)/100.00) /(double)2;
                    
                    cell = row.createCell(col++);
                    cell.setCellValue( ddl.getDecimalDouble((v3) *100.00)+"%");
                    cell.setCellStyle(contentstyle);                             
                }
                info = null;
            }
            list.clear();
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
        }
          return null;
    }
}
