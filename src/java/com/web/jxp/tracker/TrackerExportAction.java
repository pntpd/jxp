package com.web.jxp.tracker;

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

public class TrackerExportAction extends Action
{        
    @Override
    @SuppressWarnings("static-access")
    public ActionForward execute (ActionMapping mapping, ActionForm form,  HttpServletRequest request, HttpServletResponse response) throws Exception 
    { 
       try
        {
            Tracker tracker = new Tracker();
            TrackerForm frm = (TrackerForm)form;
            String exptype = frm.getExptype();
            if (exptype!= null && exptype.equals("1")) 
            {
                exptype = "";
                int assetIdIndex = frm.getAssetIdIndex();
                String search = frm.getSearch() != null ? frm.getSearch() : "";
                int positionIdIndex = frm.getPositionIdIndex();
                int mode = frm.getMode();
                int pcodeIdIndex = frm.getPcodeIdIndex();

                ArrayList trackerList = tracker.getTrackerByName(assetIdIndex, positionIdIndex, mode, search, pcodeIdIndex);   
                int total = trackerList.size(); 
                if(mode == 1)
                {
                    String fileName = "Competency_Tracker_Personel_Report";
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
                    cell.setCellValue("#");
                    sheet.setColumnWidth(c, 256*10);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Personnel Name");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Position-Rank");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Role Competencies");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;
                    
                    int col = 0;
                    int rownum = 1;

                    for(int i = 0; i < total;i++)
                    {
                        TrackerInfo info = (TrackerInfo) trackerList.get(i);
                        if(info != null)
                        {                    
                            col = 0 ;
                            row = sheet.createRow(rownum++); 
                            cell = row.createCell(col++);
                            cell.setCellValue((i+1));
                            cell.setCellStyle(contentstyle);   

                            cell = row.createCell(col++);
                            cell.setCellValue(info.getName()!= null ? info.getName() : "");
                            cell.setCellStyle(contentstyle);

                            cell = row.createCell(col++);
                            cell.setCellValue(info.getPositionName());
                            cell.setCellStyle(contentstyle); 

                            cell = row.createCell(col++);
                            cell.setCellValue(tracker.changeNum(info.getPcount(),2)+"/"+tracker.changeNum(info.getTotal(),2));
                            cell.setCellStyle(contentstyle);                    
                        }
                        info = null;
                    }
                    trackerList.clear();
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment; filename = \""+fileName+".xls\"");
                   try (ServletOutputStream os = response.getOutputStream()) {
                       wb.write(os);
                       os.flush();
                   }        
                }
                else if(mode == 2)
                {
                    String fileName = "Competency_Tracker_Role_Competency_Report";
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
                    cell.setCellValue("#");
                    sheet.setColumnWidth(c, 256*10);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Role Competencies");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Assessment Type");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Priority");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Positions");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;
                    
                    int col = 0;
                    int rownum = 1;

                    for(int i = 0; i < total;i++)
                    {
                        TrackerInfo info = (TrackerInfo) trackerList.get(i);
                        if(info != null)
                        {                    
                            col = 0 ;
                            row = sheet.createRow(rownum++); 
                            cell = row.createCell(col++);
                            cell.setCellValue((i+1));
                            cell.setCellStyle(contentstyle);   

                            cell = row.createCell(col++);
                            cell.setCellValue(info.getRole() != null ? info.getRole() : "");
                            cell.setCellStyle(contentstyle);

                            cell = row.createCell(col++);
                            cell.setCellValue(info.getPassessmenttypeName() != null ? info.getPassessmenttypeName() : "");
                            cell.setCellStyle(contentstyle); 

                            cell = row.createCell(col++);
                            cell.setCellValue(info.getPriorityName() != null ? info.getPriorityName() : ""); 
                            cell.setCellStyle(contentstyle);

                            cell = row.createCell(col++);
                            cell.setCellValue(tracker.changeNum(info.getTotal(),2));
                            cell.setCellStyle(contentstyle);
                        }
                        info = null;
                    }
                    trackerList.clear();
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment; filename = \""+fileName+".xls\"");
                    try (ServletOutputStream os = response.getOutputStream()) {
                       wb.write(os);
                       os.flush();
                    }
                }
            }
            else if(exptype!= null && exptype.equals("2")) 
            {           
                ArrayList list = new ArrayList();                
                if(request.getSession().getAttribute("ASSIGNLIST1") != null)
                {
                    list = (ArrayList) request.getSession().getAttribute("ASSIGNLIST1");
                }                
                int total = list.size();
                int mode = frm.getMode();
                if(mode == 1)
                {
                    String fileName = "Competency_Tracker_Personel_Assign_Report";
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
                    cell.setCellValue("#");
                    sheet.setColumnWidth(c, 256*10);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Role Competencies");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Assessment Type");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Priority");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;
                    
                    cell = row.createCell(c);
                    cell.setCellValue("Status");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;
                    
                    cell = row.createCell(c);
                    cell.setCellValue("Valid Till");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;
                    
                    cell = row.createCell(c);
                    cell.setCellValue("Score");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;
                    
                    int col = 0;
                    int rownum = 1;

                    for(int i = 0; i < total;i++)
                    {
                        TrackerInfo info = (TrackerInfo) list.get(i);
                        if(info != null)
                        {                    
                            col = 0 ;
                            row = sheet.createRow(rownum++); 
                            cell = row.createCell(col++);
                            cell.setCellValue((i+1));
                            cell.setCellStyle(contentstyle);   

                            cell = row.createCell(col++);
                            cell.setCellValue(info.getRole()!= null ? info.getRole() : "");
                            cell.setCellStyle(contentstyle);
                            
                            cell = row.createCell(col++);
                            cell.setCellValue(info.getPassessmenttypeName() != null ? info.getPassessmenttypeName(): "");
                            cell.setCellStyle(contentstyle); 
        
                            cell = row.createCell(col++);
                            cell.setCellValue(info.getPriorityName() != null ? info.getPriorityName(): "");
                            cell.setCellStyle(contentstyle); 
        
                            cell = row.createCell(col++);
                            cell.setCellValue(info.getStatusval() != null ? info.getStatusval(): "");
                            cell.setCellStyle(contentstyle); 
                            
                            cell = row.createCell(col++);
                            cell.setCellValue(info.getDate() != null ? info.getDate() : ""); 
                            cell.setCellStyle(contentstyle);
                            
                            cell = row.createCell(col++);
                            cell.setCellValue(info.getAverage()); 
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
            }
            else if(exptype!= null && exptype.equals("3")) 
            {           
                ArrayList list = new ArrayList();                
                if(request.getSession().getAttribute("ASSIGNLIST2") != null)
                {
                    list = (ArrayList) request.getSession().getAttribute("ASSIGNLIST2");
                }
                int total = list.size();

                String fileName = "Competency_Tracker_Competency_Role_Report";
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
                cell.setCellValue("#");
                sheet.setColumnWidth(c, 256*10);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Personnel Names");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Position - Rank");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Status");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Valid Till");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Score");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                int col = 0;
                int rownum = 1;

                for(int i = 0; i < total;i++)
                {
                    TrackerInfo info = (TrackerInfo) list.get(i);
                    if(info != null)
                    {                    
                        col = 0 ;
                        row = sheet.createRow(rownum++); 
                        cell = row.createCell(col++);
                        cell.setCellValue((i+1));
                        cell.setCellStyle(contentstyle);   

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getName()!= null ? info.getName() : "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getPositionName() != null ? info.getPositionName(): "");
                        cell.setCellStyle(contentstyle); 

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getStatusval() != null ? info.getStatusval(): "");
                        cell.setCellStyle(contentstyle); 

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getDate() != null ? info.getDate() : ""); 
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getAverage()); 
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
        }
        catch (Exception e)
        {   
            e.printStackTrace();
        }
    return null;
    }
}