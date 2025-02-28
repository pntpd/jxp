package com.web.jxp.crewrotation;

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

public class CrewrotationDetailsExport extends Action
{        
    @Override
    @SuppressWarnings("static-access")
    public ActionForward execute (ActionMapping mapping, ActionForm form,  HttpServletRequest request, HttpServletResponse response)   throws Exception 
    { 
       try
        {
            Crewrotation crewrotation = new Crewrotation();
            CrewrotationForm frm = (CrewrotationForm)form;         
            String search = frm.getSearchcr() !=null ? frm.getSearchcr(): "";
            int statusIndex = frm.getStatusIndex();
            String crdate = frm.getCrdate();
            int positionIndex = frm.getPositionIndex();
            int activityDropdown = frm.getActivityIdindex();
            int dynamicId = frm.getDynamicId();
            int countryId = frm.getCountryId();
            int clientId = frm.getClientId();
            int assetId = frm.getClientassetId();
            ArrayList list = crewrotation.getDetailForExcel(search, clientId, assetId, countryId, crdate, 
                    statusIndex, positionIndex, activityDropdown, dynamicId);            
            int total = list.size();  
            String fileName = "Crewrotation_Details_Report";
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
            titlestyle.setFillForegroundColor(HSSFColor.YELLOW.index);
            titlestyle.setFillBackgroundColor(HSSFColor.YELLOW.index);
            titlestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            titlestyle.setBorderTop((short) 1);
            titlestyle.setBorderBottom((short) 1);
            titlestyle.setBorderLeft((short) 1);
            titlestyle.setBorderRight((short) 1);

            HSSFCellStyle mergestyle = wb.createCellStyle();
            mergestyle.setFont(mergeFont);
            mergestyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

            HSSFCellStyle headstyle = wb.createCellStyle();
            headstyle.setFont(headFont);
            headstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            headstyle.setBorderTop((short) 1);
            headstyle.setBorderBottom((short) 1);
            headstyle.setBorderLeft((short) 1);
            headstyle.setBorderRight((short) 1);
            
            HSSFCellStyle headstyle_right = wb.createCellStyle();
            headstyle_right.setFont(headFont);
            headstyle_right.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

            HSSFCellStyle contentstyle = wb.createCellStyle();
            contentstyle.setFont(contentFont);
            contentstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            contentstyle.setBorderTop((short) 1);
            contentstyle.setBorderBottom((short) 1);
            contentstyle.setBorderLeft((short) 1);
            contentstyle.setBorderRight((short) 1);
            
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
            cell.setCellValue("#");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            cell.setCellStyle(titlestyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Client");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            cell.setCellStyle(titlestyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Asset");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            cell.setCellStyle(titlestyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Rank/Position");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            cell.setCellStyle(titlestyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Personnel");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            cell.setCellStyle(titlestyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Activity");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            cell.setCellStyle(titlestyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Current Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            cell.setCellStyle(titlestyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Sign On/From");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            cell.setCellStyle(titlestyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Sign Off(expected)/To");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            cell.setCellStyle(titlestyle);
            c++;            
            
            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < total;i++)
            {
                CrewrotationInfo info = (CrewrotationInfo) list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue((i+1));
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getClientName() != null && !info.getClientName().equals(""))
                        cell.setCellValue(info.getClientName());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getClientAsset() != null && !info.getClientAsset().equals(""))
                        cell.setCellValue(info.getClientAsset());
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
                    if(info.getName() != null && !info.getName().equals(""))
                        cell.setCellValue(info.getName());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getActivitystatus() != null && !info.getActivitystatus().equals(""))
                        cell.setCellValue(info.getActivitystatus());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getOffshorestatus() != null && !info.getOffshorestatus().equals(""))
                        cell.setCellValue(info.getOffshorestatus());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    if(info.getStatus1() ==1 && info.getStatus2() == 0)
                    {
                        cell = row.createCell(col++);
                        if(info.getFromdate() != null && !info.getFromdate().equals(""))
                            cell.setCellValue(info.getFromdate());
                        else
                            cell.setCellType(cell.CELL_TYPE_BLANK);
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        if(info.getTodate() != null && !info.getTodate().equals(""))
                            cell.setCellValue(info.getTodate());
                        else
                            cell.setCellType(cell.CELL_TYPE_BLANK);
                        cell.setCellStyle(contentstyle);  
                    }
                    else
                    {
                        cell = row.createCell(col++);
                        if(info.getSignon() != null && !info.getSignon().equals(""))
                            cell.setCellValue(info.getSignon());
                        else
                            cell.setCellType(cell.CELL_TYPE_BLANK);
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        if(info.getSignoff() != null && !info.getSignoff().equals(""))
                            cell.setCellValue(info.getSignoff());
                        else
                            cell.setCellType(cell.CELL_TYPE_BLANK);
                        cell.setCellStyle(contentstyle); 
                    }                                                          
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
