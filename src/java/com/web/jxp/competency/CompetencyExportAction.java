package com.web.jxp.competency;

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

public class CompetencyExportAction extends Action
{        
    @Override
    @SuppressWarnings("static-access")
    public ActionForward execute (ActionMapping mapping, ActionForm form,  HttpServletRequest request, HttpServletResponse response)   throws Exception 
    { 
       try
        { 
                Competency competency = new Competency();
                
                ArrayList list = new ArrayList();
                if(request.getSession().getAttribute("DEPT_TABLELIST") != null)
                    list = (ArrayList) request.getSession().getAttribute("DEPT_TABLELIST"); 
                int total = list.size();

                String fileName = "Competency_Report";
                HSSFWorkbook wb = new HSSFWorkbook();
                HSSFSheet sheet = wb.createSheet("Competency Department");
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
                
                HSSFCellStyle contentstyle_orange = wb.createCellStyle();
                contentstyle_orange.setFont(contentFont);
                contentstyle_orange.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
                contentstyle_orange.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                contentstyle_orange.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                
                HSSFCellStyle contentstyle_green = wb.createCellStyle();
                contentstyle_green.setFont(contentFont);
                contentstyle_green.setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);
                contentstyle_green.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                contentstyle_green.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                
                HSSFCellStyle contentstyle_blue= wb.createCellStyle();
                contentstyle_blue.setFont(contentFont);
                contentstyle_blue.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
                contentstyle_blue.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                contentstyle_blue.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                
                HSSFCellStyle contentstyle_red = wb.createCellStyle();
                contentstyle_red.setFont(contentFont);
                contentstyle_red.setFillForegroundColor(HSSFColor.RED.index);
                contentstyle_red.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                contentstyle_red.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                
                HSSFCellStyle contentstyle_darkorange = wb.createCellStyle();
                contentstyle_darkorange.setFont(contentFont);
                contentstyle_darkorange.setFillForegroundColor(HSSFColor.ORANGE.index);
                contentstyle_darkorange.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                contentstyle_darkorange.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                
                HSSFCellStyle contentstyle_grey = wb.createCellStyle();
               contentstyle_grey.setFont(contentFont);
               contentstyle_grey.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
               contentstyle_grey.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
               contentstyle_grey.setAlignment(HSSFCellStyle.ALIGN_CENTER);

                int rowval = 0;
                int c = 0;

                row = sheet.createRow(rowval); 

                cell = row.createCell(c);
                cell.setCellValue("#");
                sheet.setColumnWidth(c, 256*10);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Department");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Positions");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Personnel");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Role Competencies");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Role Competencies Assigned");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Role Competencies Submitted");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Role Competencies checked by Assessor");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Role Competencies with satisfactory result");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Total Role Competencies needs further action");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("% Completion of Role Competency");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                int col = 0;
                int rownum = 1;

                for(int i = 0; i < total;i++)
                {
                    CompetencyInfo info = (CompetencyInfo) list.get(i);
                    if(info != null)
                    {                    
                        col = 0 ;
                        row = sheet.createRow(rownum++); 
                        cell = row.createCell(col++);
                        cell.setCellValue((i+1));
                        cell.setCellStyle(contentstyle);  

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getDepartment() != null ? info.getDepartment() : "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getCount1());
                        cell.setCellStyle(contentstyle); 

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getCount2());
                        cell.setCellStyle(contentstyle);      

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getCount3());
                        cell.setCellStyle(contentstyle);      

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getCount4());
                        cell.setCellStyle(contentstyle);      

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getCount5());
                        cell.setCellStyle(contentstyle);      

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getCount6());
                        cell.setCellStyle(contentstyle);      

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getCount7());
                        cell.setCellStyle(contentstyle);      

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getCount8());
                        cell.setCellStyle(contentstyle);      

                        cell = row.createCell(col++);
                        cell.setCellValue(competency.getDecimal((info.getCount5() /(double)info.getCount4()) *100.00));
                        cell.setCellStyle(contentstyle);      
                    }
                    info = null;
                }
                list.clear();
                
               //Personal-position            
                ArrayList perposlist = new ArrayList();
                if (request.getSession().getAttribute("PER_POSITION") != null) {
                    perposlist = (ArrayList) request.getSession().getAttribute("PER_POSITION");
                }
                int perposlisttotal = perposlist.size();
                
                //Trackerlist
                ArrayList trackerlist = new ArrayList();
                if (request.getSession().getAttribute("TRACKER_LIST") != null) {
                    trackerlist = (ArrayList) request.getSession().getAttribute("TRACKER_LIST");
                }
                 ArrayList alist = new ArrayList();
                if (request.getSession().getAttribute("ALIST") != null) {
                    alist = (ArrayList) request.getSession().getAttribute("ALIST");
                }
                
                // Comp role-table 
                ArrayList rolelist = new ArrayList();
                if (request.getSession().getAttribute("ROLE_TABLE") != null) {
                    rolelist = (ArrayList) request.getSession().getAttribute("ROLE_TABLE");
                }
                int rolelist_size = rolelist.size();
                
                HSSFSheet sheet1 = wb.createSheet("Competency Tracker");             
                
                 rowval = 0;
                 c = 0;

                row = sheet1.createRow(rowval); 
                
                cell = row.createCell(c);
                cell.setCellValue("Positions");
                sheet1.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Personnel");
                sheet1.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;
                
                 col = 0;
                 rownum = 1;
                
                for(int i = 0 ; i< rolelist_size; i++)
                {
                    CompetencyInfo info = (CompetencyInfo) rolelist.get(i);
                    if (info != null) 
                    { 
                        col = 3 ;
                        cell = row.createCell(c);
                        cell.setCellValue(info.getDdlLabel());
                        sheet1.setColumnWidth(c, 256*30);
                        cell.setCellStyle(headstyle);
                        c++;
                    }
                }
               
                for(int i = 0; i < perposlisttotal; i++)
                {
                    CompetencyInfo info = (CompetencyInfo) perposlist.get(i);
                    if(info != null)
                    {    
                        int crewrotationId = info.getCrewrotationId();
                        int positionId = info.getPositionId();
                        col = 0 ;
                        row = sheet1.createRow(rownum++);                         
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getPosition() != null ? info.getPosition() : "");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getName() != null ? info.getName() : "");
                        cell.setCellStyle(contentstyle);
                        
                        String stname = "" ;
                        for(int j = 0; j < rolelist_size; j++)
                        {
                            CompetencyInfo rinfo = (CompetencyInfo)  rolelist.get(j);
                            if (rinfo != null) 
                            { 
                                int pcodeId = rinfo.getDdlValue();  
                                CompetencyInfo innerinfo = competency.getfromList(trackerlist, alist, positionId, crewrotationId, pcodeId);
                                if(innerinfo != null)
                                {
                                    stname = innerinfo.getName() != null ? innerinfo.getName() : "";
                                }
                                cell = row.createCell(col++);
                                if(stname.equals("UA"))
                                {
                                    cell.setCellValue(stname);
                                    cell.setCellStyle(contentstyle_orange);
                                }
                                else if(!stname.equals(""))
                                {
                                    cell.setCellValue(stname);
                                    cell.setCellStyle(contentstyle);
                                }
                                else
                                {
                                    cell.setCellValue("-");
                                    cell.setCellStyle(contentstyle);
                                }
                                if(stname.equals("P"))
                                {
                                    cell.setCellValue(stname);
                                    cell.setCellStyle(contentstyle_blue);
                                }
                                if(stname.equals("Y"))
                                {
                                    cell.setCellValue(stname);
                                    cell.setCellStyle(contentstyle_green);
                                }
                                if(stname.equals("N"))
                                {
                                    cell.setCellValue(stname);
                                    cell.setCellStyle(contentstyle_red);
                                }
                                if(stname.equals("A"))
                                {
                                    cell.setCellValue(stname);
                                    cell.setCellStyle(contentstyle_darkorange);
                                }
                                else if(stname.equals("E"))
                                {
                                    cell.setCellValue(stname);
                                    cell.setCellStyle(contentstyle_grey);
                                }
                            }
                            rinfo = null;
                        }
                    }
                    info = null;
                }
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
