package com.web.jxp.crewdayrate;

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

public class CrewdayrateViewExport extends Action
{        
    @Override
    @SuppressWarnings("static-access")
    public ActionForward execute (ActionMapping mapping, ActionForm form,  HttpServletRequest request, HttpServletResponse response)   throws Exception 
    { 
       try
        {
            Crewdayrate crewdayrate = new Crewdayrate();
            ArrayList plist = new ArrayList();
            if(request.getSession().getAttribute("POSITION_LIST") != null)
            {
                plist = (ArrayList) request.getSession().getAttribute("POSITION_LIST");
            }
            int plist_size = plist.size();
            ArrayList clist = new ArrayList();
            if(request.getSession().getAttribute("C_LIST") != null)
            {
                clist = (ArrayList) request.getSession().getAttribute("C_LIST");
            }
            String fileName = "Crewdayrate_Details_Report";
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
            cell.setCellValue("Position Rank");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Personnel");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Position Day Rate");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Day Rate");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Overtime / hr");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Allowances");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < plist_size; i++)
            {
                CrewdayrateInfo cinfo = (CrewdayrateInfo) plist.get(i);
                if(cinfo != null)
                {
                    int positionId = cinfo.getDdlValue();
                    ArrayList list = crewdayrate.getListFromList(clist, positionId);
                    int list_size = list.size();                    
                    if(list_size > 0)
                    {                         
                        col = 0 ;
                        row = sheet.createRow(rownum++); 
                        cell = row.createCell(col++);
                        cell.setCellValue(cinfo.getDdlLabel() != null ? cinfo.getDdlLabel() : "");
                        cell.setCellStyle(contentstyle);   

                        cell = row.createCell(col++);
                        cell.setCellValue("");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(cinfo.getRate1());
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue("");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue("");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue("");
                        cell.setCellStyle(contentstyle);
                        
                        for(int j = 0; j < list_size; j++)
                        {
                            CrewdayrateInfo cwinfo = (CrewdayrateInfo) list.get(j);
                            if(cwinfo != null)
                            {
                                col = 0 ;
                                row = sheet.createRow(rownum++); 
                                cell = row.createCell(col++);
                                cell.setCellValue("");
                                cell.setCellStyle(contentstyle);   

                                cell = row.createCell(col++);
                                cell.setCellValue(cwinfo.getName() != null ? cwinfo.getName() : "");
                                cell.setCellStyle(contentstyle);
                                
                                cell = row.createCell(col++);
                                cell.setCellValue("");
                                cell.setCellStyle(contentstyle);

                                cell = row.createCell(col++);
                                cell.setCellValue(cwinfo.getRate1());
                                cell.setCellStyle(contentstyle);

                                cell = row.createCell(col++);
                                cell.setCellValue(cwinfo.getRate2());
                                cell.setCellStyle(contentstyle);

                                cell = row.createCell(col++);
                                cell.setCellValue(cwinfo.getRate3());
                                cell.setCellStyle(contentstyle);
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
}
