package com.web.jxp.dashboard;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import javax.servlet.ServletOutputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;

public class ExportdetailAction extends Action {
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DashboardForm frm = (DashboardForm) form;
        Dashboard dashboard = new Dashboard();
        String exptype = frm.getExptype();
        if (exptype!= null && exptype.equals("1")) 
        {
            exptype = "";
            ArrayList dlist = new ArrayList();
            if ( request.getSession().getAttribute("DASH_CREWROTATIONDLIST") != null)
            {
                dlist = (ArrayList)  request.getSession().getAttribute("DASH_CREWROTATIONDLIST");
            }
            int dtotal = dlist.size();
        try
        { 
            String fileName = "Dashboard_Summary_Report";
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
            cell.setCellValue("Position");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Personnel");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Rotations");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Rotations Days");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("<Rotations Days");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Normal");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue(">Rotations Days Extended");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Over Stay");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Total Offshore Days");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;            
            
            cell = row.createCell(c);
            cell.setCellValue("Total Onshore Days");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;  
            
            cell = row.createCell(c);
            cell.setCellValue("Office Work");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Training");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;    
                        
            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < dtotal;i++)
            {
               DashboardInfo info = (DashboardInfo) dlist.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++); 
                  
                    cell = row.createCell(col++);
                    cell.setCellValue((i+1));
                    cell.setCellStyle(contentstyle);  
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPosition() != null && !info.getPosition().equals("") ? info.getPosition() : "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCandidateName()!= null && !info.getCandidateName().equals("") ? info.getCandidateName(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getRotations());
                    cell.setCellStyle(contentstyle);                    
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getNoofdays());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEarly());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getNormal());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getExtended());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getOverstay());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getNormal()+info.getExtended()+info.getOverstay());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getOfficework()+info.getTraining()+info.getAvailable());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getOfficework());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getTraining());
                    cell.setCellStyle(contentstyle);
                }
                info = null;
        }
            dlist.clear();
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
        else if(exptype!= null && exptype.equals("2")) 
        {
            int type = frm.getTtype();
            exptype = "";
            ArrayList candlist = new ArrayList();
            if (request.getSession().getAttribute("DASH_CREWROTATIONCLIST") != null) {
                candlist = (ArrayList)  request.getSession().getAttribute("DASH_CREWROTATIONCLIST");                
            }
            int dtotal = candlist.size();
            int month = frm.getMonth();
            int year = frm.getYear();
            String []datearr = dashboard.findFromToDate(month, year);            
            String fromdateIndex = datearr[0];
            String todateIndex = datearr[1];
            DashboardInfo listinfo = null;
            if (request.getSession().getAttribute("DASH_CREWROTATIONULIST") != null) {
                listinfo = (DashboardInfo) request.getSession().getAttribute("DASH_CREWROTATIONULIST");
            }
            String monthlist = (String)request.getSession().getAttribute("MONTH"); 
            ArrayList datelist  = dashboard.datelist(fromdateIndex,todateIndex);
            int datetotal = datelist.size();            
            
            ArrayList datelistday  = dashboard.datelistday(fromdateIndex,todateIndex);
            int daylist = datelistday.size();           
                     
        try
        { 
            String fileName = "Dashboard_Excel_Report";
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
            
            HSSFFont headFontblack = wb.createFont();
            headFontblack.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            headFontblack.setColor(IndexedColors.WHITE.getIndex());
            headFontblack.setFontHeightInPoints((short)10);
            
            HSSFFont contentFont = wb.createFont();
            contentFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            contentFont.setFontHeightInPoints((short)10);
            
            HSSFFont contentFontBold = wb.createFont();
            contentFontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            contentFontBold.setFontHeightInPoints((short)10);

            HSSFCellStyle titlestyle = wb.createCellStyle();
            titlestyle.setFont(titleFont);
            titlestyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
            titlestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            titlestyle.setBorderTop((short) 1);
            titlestyle.setBorderBottom((short) 1);
            titlestyle.setBorderLeft((short) 1);
            titlestyle.setBorderRight((short) 1);
            
            HSSFCellStyle titlestyle_center = wb.createCellStyle();
            titlestyle_center.setFont(titleFont);
            titlestyle_center.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
            titlestyle_center.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            titlestyle_center.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            titlestyle_center.setBorderTop((short) 1);
            titlestyle_center.setBorderBottom((short) 1);
            titlestyle_center.setBorderLeft((short) 1);
            titlestyle_center.setBorderRight((short) 1);

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
            
            HSSFCellStyle contentstyle_green = wb.createCellStyle();
            contentstyle_green.setFont(contentFont);
            contentstyle_green.setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);
            contentstyle_green.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            contentstyle_green.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            
            HSSFCellStyle contentstyle_green1 = wb.createCellStyle();
            contentstyle_green1.setFont(contentFont);
            contentstyle_green1.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
            contentstyle_green1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            contentstyle_green1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            
            HSSFCellStyle contentstyle_yellow = wb.createCellStyle();
            contentstyle_yellow.setFont(contentFont);
            contentstyle_yellow.setFillForegroundColor(HSSFColor.YELLOW.index);
            contentstyle_yellow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            contentstyle_yellow.setAlignment(HSSFCellStyle.ALIGN_CENTER);                        
            
            HSSFCellStyle contentstyle_orange = wb.createCellStyle();
            contentstyle_orange.setFont(contentFont);
            contentstyle_orange.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
            contentstyle_orange.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            contentstyle_orange.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            
            HSSFCellStyle contentstyle_black = wb.createCellStyle();
            contentstyle_black.setFont(headFontblack);
            contentstyle_black.setFillForegroundColor(HSSFColor.BLACK.index);
            contentstyle_black.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            contentstyle_black.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            
            HSSFCellStyle contentstyle_blue = wb.createCellStyle();
            contentstyle_blue.setFont(contentFont);
            contentstyle_blue.setFillForegroundColor(HSSFColor.CORNFLOWER_BLUE.index);
            contentstyle_blue.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            contentstyle_blue.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            
            HSSFCellStyle contentstyle_grey = wb.createCellStyle();
            contentstyle_grey.setFont(contentFont);
            contentstyle_grey.setFillForegroundColor(HSSFColor.AQUA.index);
            contentstyle_grey.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            contentstyle_grey.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            
            HSSFCellStyle contentstyle_tr = wb.createCellStyle();
           contentstyle_tr.setFont(contentFont);
           contentstyle_tr.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
           contentstyle_tr.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
           contentstyle_tr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            
            HSSFCellStyle contentstyle_lime = wb.createCellStyle();
            contentstyle_lime.setFont(contentFont);
            contentstyle_lime.setFillForegroundColor(HSSFColor.LIME.index);
            contentstyle_lime.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            contentstyle_lime.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            
            HSSFCellStyle coral = wb.createCellStyle();
            coral.setFont(contentFont);
            coral.setFillForegroundColor(HSSFColor.CORAL.index);
            coral.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            coral.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            
            HSSFCellStyle contentstyle_right = wb.createCellStyle();
            contentstyle_right.setFont(contentFont);
            contentstyle_right.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            
            HSSFCellStyle contentstylebold = wb.createCellStyle();
            contentstylebold.setFont(contentFontBold);
            contentstylebold.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            
            HSSFCellStyle normalstyle = wb.createCellStyle();
            normalstyle.setFont(headFont);
            HSSFPalette palette = wb.getCustomPalette();
            palette.setColorAtIndex (HSSFColor.LIME.index, (byte) 180, (byte) 142, (byte) 173);
            
            int rowvalh = 0;
            int c1 = 0;

            row = sheet.createRow(rowvalh);
            
            cell = row.createCell(c1);
            cell.setCellValue("");
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 4));
            sheet.setColumnWidth(c1, 256 * 10);
            cell.setCellStyle(titlestyle);
            c1++;            
            
            cell = row.createCell(5);
            cell.setCellValue(monthlist);
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 5, 4+daylist));
            sheet.setColumnWidth(c1, 256 * 30);
            cell.setCellStyle(titlestyle_center);
            c1++;
            
            int rowval = 1;
            int c = 1;
            
            row = sheet.createRow(rowval);
            
            cell = row.createCell(0);
            cell.setCellValue("#");
            sheet.setColumnWidth(0, 256*10);
            cell.setCellStyle(headstyle);
            cell.setCellStyle(titlestyle);
          
            cell = row.createCell(c);
            cell.setCellValue("Position");
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
            cell.setCellValue("Rotations");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            cell.setCellStyle(titlestyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            cell.setCellStyle(titlestyle);
            c++;
            
            String date = "";
            if(daylist>0){
            for(int i = 0 ;i < daylist; i++)
            {
                date = (String)datelistday.get(i);
                if (date != null) 
                {
                    cell = row.createCell(c);
                    cell.setCellValue(date);
                    sheet.setColumnWidth(c, 256*10);
                    cell.setCellStyle(headstyle);
                    cell.setCellStyle(titlestyle);
                    c++;
                }
            }
            }
            
            int col = 0;
            int rownum = 2;
            String datestatus = "";
            if(datetotal > 0)
            {
                String lastdate = (String) datelist.get(datetotal - 1);
                String currentdate = dashboard.currDate();
                if(dashboard.getDiffTwoDateInt(currentdate, lastdate, "yyyy-MM-dd") > 0)
                    datestatus = currentdate;
                else
                    datestatus = lastdate;
            }
            for(int i = 0; i < dtotal;i++)
            {
                DashboardInfo caninfo = (DashboardInfo) candlist.get(i);
                if(caninfo != null)
                {
                    int countstatus  = dashboard.checkwork((ArrayList)listinfo.getList4(), (ArrayList)listinfo.getList3(),(ArrayList)listinfo.getList2(), (ArrayList)listinfo.getList1(), datestatus, (ArrayList)listinfo.getList5(), (ArrayList)listinfo.getList6(), (ArrayList)listinfo.getList7(), (ArrayList)listinfo.getList8(), (ArrayList)listinfo.getList9(),caninfo.getCandidateId(), caninfo.getPositionId()) ;
                    if((type == 4 && (countstatus == 0 || countstatus == 4)) ||(type == 3 && countstatus == 3) || (type == 2 && countstatus == 2) || (type == 1 && countstatus == 1) || (type == -1))
                    {
                        col = 0 ;
                        row = sheet.createRow(rownum++); 

                        cell = row.createCell(col++);
                        cell.setCellValue((i+1));
                        cell.setCellStyle(contentstyle);  

                        cell = row.createCell(col++);
                        cell.setCellValue(caninfo.getPosition() != null && !caninfo.getPosition().equals("") ? caninfo.getPosition() : "");
                        cell.setCellStyle(contentstyle); 

                        cell = row.createCell(col++);
                        cell.setCellValue(caninfo.getCandidateName()!= null && !caninfo.getCandidateName().equals("") ? caninfo.getCandidateName(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(caninfo.getRotations());
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        if(caninfo.getStatus() == 1)
                        {
                            cell.setCellValue("Normal");
                            cell.setCellStyle(contentstyle);
                        }
                        else if(caninfo.getStatus() == 2)
                        {
                            cell.setCellValue("Extended");
                            cell.setCellStyle(contentstyle);
                        }
                        else if(caninfo.getStatus() == 3)
                        {
                            cell.setCellValue("OverStay");
                            cell.setCellStyle(contentstyle);
                        }
                        else if(caninfo.getStatus() == 4)
                        {
                            cell.setCellValue("Sign Off");
                            cell.setCellStyle(contentstyle);
                        }
                        
                        if(datetotal > 0)
                        {
                            for(int j = 0 ;j < datetotal;j++)
                            {
                                date = (String)datelist.get(j);
                                if (date != null && listinfo != null) 
                                {                                                                        
                                    int daystatus = dashboard.checkwork((ArrayList)listinfo.getList4(), (ArrayList)listinfo.getList3(),(ArrayList)listinfo.getList2(), (ArrayList)listinfo.getList1(),  date, (ArrayList)listinfo.getList5(), (ArrayList)listinfo.getList6(), (ArrayList)listinfo.getList7(), (ArrayList)listinfo.getList8(), (ArrayList)listinfo.getList9(), caninfo.getCandidateId(), caninfo.getPositionId());

                                    cell = row.createCell(col++);
                                    if(daystatus == 1)
                                    {
                                        cell.setCellValue("W");
                                        cell.setCellStyle(contentstyle);
                                        cell.setCellStyle(contentstyle_green);
                                    }
                                    else if(daystatus == 2)
                                    {
                                        cell.setCellValue("W");
                                        cell.setCellStyle(contentstyle);
                                        cell.setCellStyle(contentstyle_yellow);
                                    }
                                    else if(daystatus == 3)
                                    {
                                        cell.setCellValue("W");
                                        cell.setCellStyle(contentstyle);
                                        cell.setCellStyle(contentstyle_orange);
                                    }
                                    else if(daystatus == 4)
                                    {
                                        cell.setCellValue("SO");
                                        cell.setCellStyle(contentstyle);
                                        cell.setCellStyle(contentstyle_black);
                                    }                            
                                    else if(daystatus == 5)
                                    {
                                        cell.setCellValue("R");
                                        cell.setCellStyle(contentstyle);
                                        cell.setCellStyle(contentstyle_green1);
                                    }
                                    else if(daystatus == 6)
                                    {
                                        cell.setCellValue("P");
                                        cell.setCellStyle(contentstyle);
                                        cell.setCellStyle(contentstyle_blue);
                                    }
                                    else if(daystatus == 7)
                                    {
                                        cell.setCellValue("D");
                                        cell.setCellStyle(contentstyle);
                                        cell.setCellStyle(contentstyle_grey);
                                    }
                                    else if(daystatus == 8)
                                    {
                                        cell.setCellValue("TR");
                                        cell.setCellStyle(contentstyle);
                                        cell.setCellStyle(contentstyle_tr);
                                    }
                                    else if(daystatus == 9)
                                    {
                                        cell.setCellValue("TP");
                                        cell.setCellStyle(contentstyle);
                                        cell.setCellStyle(contentstyle_lime);
                                    }
                                    else if(daystatus == 10)
                                    {
                                        cell.setCellValue("SB");
                                        cell.setCellStyle(contentstyle);
                                        cell.setCellStyle(coral);
                                    }
                                }
                            }   
                        }
                    }                    
                }
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
    return null;
    }
}
