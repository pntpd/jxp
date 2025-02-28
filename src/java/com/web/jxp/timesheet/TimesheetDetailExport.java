package com.web.jxp.timesheet;

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

public class TimesheetDetailExport extends Action
{
    @Override
    @SuppressWarnings("static-access")
    public ActionForward execute (ActionMapping mapping, ActionForm form,  HttpServletRequest request, HttpServletResponse response)   throws Exception 
    { 
        try
        {             
            Timesheet timesheet = new Timesheet(); 
            TimesheetForm frm = (TimesheetForm) form;
            int timesheetId = frm.getTimesheetId();
            frm.setTimesheetId(timesheetId);
            int typeId = frm.getTypeId();
            TimesheetInfo info = null;
            String client = "", asset = "", fromDate= "", toDate= "", currency = "";
            if (request.getSession().getAttribute("BASICINFO") != null){
                info =   (TimesheetInfo) request.getSession().getAttribute("BASICINFO");     
                if(info != null)
                {
                    client = info.getClientName() != null ? info.getClientName(): "";
                    asset = info.getClientAssetName() != null ? info.getClientAssetName(): "";
                    currency = info.getCurrencyName() != null ? info.getCurrencyName(): "";
                }
            }
            
            TimesheetInfo tinfo = timesheet.getBasicTimesheet(timesheetId);
            if(tinfo != null)
            {
                timesheetId = tinfo.getTimesheetId();
                fromDate = tinfo.getFromDate() != null ? tinfo.getFromDate(): "";
                toDate = tinfo.getToDate() != null ? tinfo.getToDate(): "";
            }
            String fileName = "Timesheet_Details_Report";
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

            HSSFCellStyle headstyle_centre = wb.createCellStyle();
            headstyle_centre.setFont(headFont);
            headstyle_centre.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            ArrayList list = new ArrayList();
            if(typeId == 2)
            {
                list = timesheet.getExcelList(timesheetId);
                int total = list.size();
                

                int rowvalh = 0;
                int c1 = 0;

                row = sheet.createRow(rowvalh);

                cell = row.createCell(c1);
                cell.setCellValue("TIMESHEET");
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 1, 0,13));
                sheet.setColumnWidth(c1, 256 * 30);
                cell.setCellStyle(headstyle_centre);

                int rowval = 2;
                int c = 0;

                row = sheet.createRow(rowval); 

                cell = row.createCell(c);
                cell.setCellValue("Client");
                sheet.setColumnWidth(c, 256*20);
                c++;

                cell = row.createCell(c);
                cell.setCellValue(client);
                sheet.setColumnWidth(c, 256*20);
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(2, 2, 1,2 ));

                cell = row.createCell(3);
                cell.setCellValue("Asset");
                sheet.setColumnWidth(c, 256*20);
                c++;

                cell = row.createCell(4);
                cell.setCellValue(asset);
                sheet.setColumnWidth(c, 256*20);
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(2, 2, 4,5));
                c++;

                cell = row.createCell(6);
                cell.setCellValue("From Date");
                sheet.setColumnWidth(c, 256*20);
                c++;

                cell = row.createCell(7);
                cell.setCellValue(fromDate);
                sheet.setColumnWidth(c, 256*20);
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(2, 2, 7,8));

                cell = row.createCell(9);
                cell.setCellValue("To Date");
                sheet.setColumnWidth(c, 256*20);
                c++;

                cell = row.createCell(10);
                cell.setCellValue(toDate);
                sheet.setColumnWidth(c, 256*20);
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(2, 2, 10,11));

                cell = row.createCell(12);
                cell.setCellValue("Currency");
                sheet.setColumnWidth(c, 256*20);
                //sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(2, 2, 12,13));

                cell = row.createCell(13);
                cell.setCellValue(currency);
                sheet.setColumnWidth(c, 256*20);

                rowval = 3;
                c = 0;
                row = sheet.createRow(rowval); 

                cell = row.createCell(c);
                cell.setCellValue("JXP System Data");
                sheet.setColumnWidth(c, 256*20);
                cell.setCellStyle(headstyle_centre);
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(3, 3, 0,6)); 

                cell = row.createCell(7);
                cell.setCellValue("On Site Data");
                sheet.setColumnWidth(c, 256*20);
                cell.setCellStyle(headstyle_centre);
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(3, 3, 7,13));

                rowval = 4;
                c = 0;
                row = sheet.createRow(rowval); 

                cell = row.createCell(c);
                cell.setCellValue("Candidate ID");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Employment Code");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Candidate Name");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Position-Rank");
                sheet.setColumnWidth(c, 256*40);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Rotation Days");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Overtime Hours");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Travel Days");
                sheet.setColumnWidth(c, 256*20);        
                c++; 

                cell = row.createCell(c);
                cell.setCellValue("Candidate ID");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Employment Code");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Candidate Name");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Position-Rank");
                sheet.setColumnWidth(c, 256*40);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Rotation Days");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Overtime Hours");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Travel Days");
                sheet.setColumnWidth(c, 256*20);        

                rowval = 5;
                int col = 0;
                for(int i = 0; i <total; i++)
                {
                    TimesheetInfo cwinfo = (TimesheetInfo) list.get(i);
                    if(cwinfo != null)
                    {
                        col = 0 ;                  
                        row = sheet.createRow(rowval++);

                        cell = row.createCell(col++);
                        cell.setCellValue(timesheet.changeNum( cwinfo.getCandidateId(),6));
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(cwinfo.getEmployeeNo()!= null ? cwinfo.getEmployeeNo(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(cwinfo.getName()!= null ? cwinfo.getName(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(cwinfo.getPosition()!= null ? cwinfo.getPosition(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(cwinfo.getDays1());
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(cwinfo.getDays2());
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(cwinfo.getDays5());
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(timesheet.changeNum( cwinfo.getCandidateId(),6));
                        cell.setCellStyle(contentstyle);   

                        cell = row.createCell(col++);
                        cell.setCellValue(cwinfo.getEmployeeNo()!= null ? cwinfo.getEmployeeNo(): "");
                        cell.setCellStyle(contentstyle);   

                        cell = row.createCell(col++);
                        cell.setCellValue(cwinfo.getName()!= null ? cwinfo.getName(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(cwinfo.getPosition()!= null ? cwinfo.getPosition(): "");
                        cell.setCellStyle(contentstyle);
                    }
                    cwinfo = null;
                }
                list.clear();

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename = \""+fileName+".xls\"");
               try (ServletOutputStream os = response.getOutputStream()) {
                   wb.write(os);
                   os.flush();
               }
            
            }
            if(typeId == 1)
            {
                if (request.getSession().getAttribute("ACTIVITY_LIST") != null)
                {
                    list =   (ArrayList) request.getSession().getAttribute("ACTIVITY_LIST");
                }
                int total = list.size();
                int rowvalh = 0;
                int c1 = 0;

                row = sheet.createRow(rowvalh);

                cell = row.createCell(c1);
                cell.setCellValue("TIMESHEET");
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 1, 0,13));
                sheet.setColumnWidth(c1, 256 * 30);
                cell.setCellStyle(headstyle_centre);

                int rowval = 2;
                int c = 0;

                row = sheet.createRow(rowval); 

                cell = row.createCell(c);
                cell.setCellValue("Client");
                sheet.setColumnWidth(c, 256*20);
                c++;

                cell = row.createCell(c);
                cell.setCellValue(client);
                sheet.setColumnWidth(c, 256*20);
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(2, 2, 1,2 ));

                cell = row.createCell(3);
                cell.setCellValue("Asset");
                sheet.setColumnWidth(c, 256*20);
                c++;

                cell = row.createCell(4);
                cell.setCellValue(asset);
                sheet.setColumnWidth(c, 256*20);
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(2, 2, 4,5));
                c++;

                cell = row.createCell(6);
                cell.setCellValue("From Date");
                sheet.setColumnWidth(c, 256*20);
                c++;

                cell = row.createCell(7);
                cell.setCellValue(fromDate);
                sheet.setColumnWidth(c, 256*20);
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(2, 2, 7,8));

                cell = row.createCell(9);
                cell.setCellValue("To Date");
                sheet.setColumnWidth(c, 256*20);
                c++;

                cell = row.createCell(10);
                cell.setCellValue(toDate);
                sheet.setColumnWidth(c, 256*20);
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(2, 2, 10,11));

                cell = row.createCell(12);
                cell.setCellValue("Currency");
                sheet.setColumnWidth(c, 256*20);
                //sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(2, 2, 12,13));

                cell = row.createCell(13);
                cell.setCellValue(currency);
                sheet.setColumnWidth(c, 256*20);

                rowval = 3;
                c = 0;
                row = sheet.createRow(rowval); 

                cell = row.createCell(c);
                cell.setCellValue("JXP System Data");
                sheet.setColumnWidth(c, 256*20);
                cell.setCellStyle(headstyle_centre);
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(3, 3, 0,6)); 

                cell = row.createCell(7);
                cell.setCellValue("On Site Data");
                sheet.setColumnWidth(c, 256*20);
                cell.setCellStyle(headstyle_centre);
                sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(3, 3, 7,13));

                rowval = 4;
                c = 0;
                row = sheet.createRow(rowval); 

                cell = row.createCell(c);
                cell.setCellValue("Candidate ID");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Employment Code");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Candidate Name");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Position-Rank");
                sheet.setColumnWidth(c, 256*40);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Rotation Days");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Overtime Hours");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Travel Days");
                sheet.setColumnWidth(c, 256*20);        
                c++; 

                cell = row.createCell(c);
                cell.setCellValue("Candidate ID");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Employment Code");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Candidate Name");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Position-Rank");
                sheet.setColumnWidth(c, 256*40);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Rotation Days");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Overtime Hours");
                sheet.setColumnWidth(c, 256*20);        
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Travel Days");
                sheet.setColumnWidth(c, 256*20);        

                rowval = 5;
                int col = 0;
                for(int i = 0; i <total; i++)
                {
                    TimesheetInfo cwinfo = (TimesheetInfo) list.get(i);
                    if(cwinfo != null)
                    {
                        col = 0 ;                  
                        row = sheet.createRow(rowval++);

                        cell = row.createCell(col++);
                        cell.setCellValue(timesheet.changeNum( cwinfo.getCandidateId(),6));
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(cwinfo.getEmployeeNo()!= null ? cwinfo.getEmployeeNo(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(cwinfo.getName()!= null ? cwinfo.getName(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(cwinfo.getPosition()!= null ? cwinfo.getPosition(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(cwinfo.getDateDiff());
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(0);
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(cwinfo.getTravelDays());
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(timesheet.changeNum( cwinfo.getCandidateId(),6));
                        cell.setCellStyle(contentstyle);   

                        cell = row.createCell(col++);
                        cell.setCellValue(cwinfo.getEmployeeNo()!= null ? cwinfo.getEmployeeNo(): "");
                        cell.setCellStyle(contentstyle);   

                        cell = row.createCell(col++);
                        cell.setCellValue(cwinfo.getName()!= null ? cwinfo.getName(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(cwinfo.getPosition()!= null ? cwinfo.getPosition(): "");
                        cell.setCellStyle(contentstyle);
                    }
                    cwinfo = null;
                }
                list.clear();

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename = \""+fileName+".xls\"");
               try (ServletOutputStream os = response.getOutputStream()) {
                   wb.write(os);
                   os.flush();
               }
            
        }
            
            
        }
        catch (Exception e)
        {   
            e.printStackTrace();
        }
          return null;
    }
    
}
