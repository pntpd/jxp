package com.web.jxp.candidate;

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

public class CandidateIndexExportAction extends Action
{        
    @Override
    @SuppressWarnings("static-access")
    public ActionForward execute (ActionMapping mapping, ActionForm form,  HttpServletRequest request, HttpServletResponse response)   throws Exception 
    { 
       try
        { 
            Candidate candidate = new Candidate();
            CandidateForm frm = (CandidateForm)form;         
            String search = frm.getSearch() != null ? frm.getSearch() : "";
            int statusIndex = frm.getStatusIndex(); 
           int onlineflag = frm.getOnlineFlag();
           int statustype = frm.getStatustype();
           int assettypeIdIndex = frm.getAssettypeIdIndex();
           int positionIdIndex = frm.getPositionIdIndex();
            ArrayList list = candidate.getCandidateListForExcel( search,statusIndex, onlineflag, statustype, assettypeIdIndex, positionIdIndex);
            int total = list.size();  
            String fileName = "Candidate_Index_Report";
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
            cell.setCellValue("ID");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Enrolled On");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Name");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Country");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("City");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Asset Type");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Position | Rank");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Contact No.");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Emergency Contact No. 1");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Additional Contact");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Email Address");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Date of Birth");
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
            cell.setCellValue("Epmployee ID");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
         
            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < total;i++)
            {
                CandidateInfo info = (CandidateInfo) list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++); 
                   
                    cell = row.createCell(col++);
                    cell.setCellValue((info.getCandidateId()));
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getDate() != null && !info.getDate().equals(""))
                        cell.setCellValue(info.getDate());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getName() != null && !info.getName().equals(""))
                        cell.setCellValue(info.getName());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getCountryName() != null && !info.getCountryName().equals(""))
                        cell.setCellValue(info.getCountryName());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getCity() != null && !info.getCity().equals(""))
                        cell.setCellValue(info.getCity());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getAssettype() != null && !info.getAssettype().equals(""))
                        cell.setCellValue(info.getAssettype());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getPosition() != null && !info.getPosition().equals(""))
                        cell.setCellValue(info.getPosition());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getContactno1() != null && !info.getContactno1().equals(""))
                        cell.setCellValue(info.getContactno1());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getEcontactno1() != null && !info.getEcontactno1().equals(""))
                        cell.setCellValue(info.getEcontactno1());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getContactno3() != null && !info.getContactno3().equals(""))
                        cell.setCellValue(info.getContactno3());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getEmailId() != null && !info.getEmailId().equals(""))
                        cell.setCellValue(info.getEmailId());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getDob() != null && !info.getDob().equals(""))
                        cell.setCellValue(info.getDob());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getPlaceofbirth() != null && !info.getPlaceofbirth().equals(""))
                        cell.setCellValue(info.getPlaceofbirth());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getGender() != null && !info.getGender().equals(""))
                        cell.setCellValue(info.getGender());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getMaritialstatus() != null && !info.getMaritialstatus().equals(""))
                        cell.setCellValue(info.getMaritialstatus());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getNextofkin() != null && !info.getNextofkin().equals(""))
                        cell.setCellValue(info.getNextofkin());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getRelation() != null && !info.getRelation().equals(""))
                        cell.setCellValue(info.getRelation());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getAddress1line1() != null && !info.getAddress1line1().equals(""))
                        cell.setCellValue(info.getAddress1line1());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getAddress1line2() != null && !info.getAddress1line2().equals(""))
                        cell.setCellValue(info.getAddress1line2());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getAddress1line3() != null && !info.getAddress1line3().equals(""))
                        cell.setCellValue(info.getAddress1line3());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getAddress2line1() != null && !info.getAddress2line1().equals(""))
                        cell.setCellValue(info.getAddress2line1());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getAddress2line2() != null && !info.getAddress2line2().equals(""))
                        cell.setCellValue(info.getAddress2line2());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getAddress2line3() != null && !info.getAddress2line3().equals(""))
                        cell.setCellValue(info.getAddress2line3());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getNationality() != null && !info.getNationality().equals(""))
                        cell.setCellValue(info.getNationality());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getDepartment() != null && !info.getDepartment().equals(""))
                        cell.setCellValue(info.getDepartment());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getCurrency() != null && !info.getCurrency().equals(""))
                        cell.setCellValue(info.getCurrency());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);                    
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getExpectedsalary());
                    cell.setCellStyle(contentstyle);
                       
                    cell = row.createCell(col++);
                    if(info.getEmployeeId() != null && !info.getEmployeeId().equals(""))
                        cell.setCellValue(info.getEmployeeId());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(candidate.getStatusById(info.getStatus()));
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