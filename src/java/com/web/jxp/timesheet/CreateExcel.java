package com.web.jxp.timesheet;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class CreateExcel
{
    public void createExcel(ArrayList list, String addpath, int timesheetId)
    {
        Timesheet timesheet = new Timesheet();
        try
        { 
            int total = list.size(); 
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
            
            HSSFCellStyle headstyle_center = wb.createCellStyle();
            headstyle_center.setFont(headFont);
            headstyle_center.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            
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
            row = sheet.createRow(rowval);
            int c = 0;
            
            cell = row.createCell(c);
            cell.setCellValue("Personnel Name");
            sheet.setColumnWidth(c, 256*20);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Position");
            sheet.setColumnWidth(c, 256*20);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Type of Expense");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Sign On");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Sign Off");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Days / Hours");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Rate");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Percent(%)");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Amount");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Chargeable");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            int col = 0;         
            for(int i = 0; i < total;i++)
            {
                TimesheetInfo cwinfo = (TimesheetInfo) list.get(i);
                if(cwinfo != null)
                {
                    col = 0 ;
                    row = sheet.createRow(++rowval);                     

                    cell = row.createCell(col++);
                    cell.setCellValue(cwinfo.getName() != null ? cwinfo.getName() : "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(cwinfo.getPosition()!= null ? cwinfo.getPosition(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(cwinfo.getStatusValue() != null ? cwinfo.getStatusValue(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(cwinfo.getFromDate()!= null ? cwinfo.getFromDate(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(cwinfo.getToDate()!= null ? cwinfo.getToDate(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(cwinfo.getNoofdays());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(timesheet.getDecimal(cwinfo.getRate()));
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(timesheet.getDecimal(cwinfo.getPercent()));
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(timesheet.getDecimal(cwinfo.getAmount()));
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(timesheet.getDecimal(cwinfo.getGrandtotal()));
                    cell.setCellStyle(contentstyle);                    
                }
                cwinfo = null;
            }
            list.clear();
            FileOutputStream out = new FileOutputStream(new File(addpath));  
            wb.write(out);
            out.flush();
            out.close();
        }
        catch (Exception e)
        {   
            e.printStackTrace();
        }
    }
}