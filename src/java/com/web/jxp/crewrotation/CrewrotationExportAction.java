package com.web.jxp.crewrotation;

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

public class CrewrotationExportAction extends Action
{        
    @Override
    @SuppressWarnings("static-access")
    public ActionForward execute (ActionMapping mapping, ActionForm form,  HttpServletRequest request, HttpServletResponse response)   throws Exception 
    { 
       try
        { 
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
            Crewrotation crewrotation = new Crewrotation();
            CrewrotationForm frm = (CrewrotationForm)form;         
            String search = frm.getSearch() != null ? frm.getSearch() : "";
            int statusIndex = frm.getStatusIndex();
            int clientIdIndex = frm.getClientIdIndex();
            int assetIdIndex = frm.getAssetIdIndex();
            int countryId = frm.getCountryId();
            ArrayList list = crewrotation.getListForExcel(search, statusIndex, clientIdIndex, assetIdIndex,countryId , allclient,  permission,  cids,  assetids);
            int total = list.size();
            String fileName = "Crewrotation_Report";
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
            titlestyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            titlestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            titlestyle.setBorderTop((short) 1);
            titlestyle.setBorderBottom((short) 1);
            titlestyle.setBorderLeft((short) 1);
            titlestyle.setBorderRight((short) 1);

            HSSFCellStyle mergestyle = wb.createCellStyle();
            mergestyle.setFont(mergeFont);
            mergestyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

            HSSFCellStyle headstyle = wb.createCellStyle();
            headstyle.setFont(headFont);
            headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            headstyle.setBorderTop((short) 1);
            headstyle.setBorderBottom((short) 1);
            headstyle.setBorderLeft((short) 1);
            headstyle.setBorderRight((short) 1);
            
            HSSFCellStyle headstyle_right = wb.createCellStyle();
            headstyle_right.setFont(headFont);
            headstyle_right.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

            HSSFCellStyle contentstyle = wb.createCellStyle();
            contentstyle.setFont(contentFont);
            contentstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
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
            
            HSSFCellStyle headstyle_centre = wb.createCellStyle();
            headstyle_centre.setFont(headFont);
            headstyle_centre.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                        
            int rowvalh = 0;
            int c1 = 0;

            row = sheet.createRow(rowvalh);

            cell = row.createCell(c1);
            cell.setCellValue("#");
            sheet.setColumnWidth(c1, 256 * 10);
            cell.setCellStyle(headstyle_centre);
            cell.setCellStyle(titlestyle);
            c1++;
            
            cell = row.createCell(c1);
            cell.setCellValue("Client");
            sheet.setColumnWidth(c1, 256 * 10);
            cell.setCellStyle(headstyle_centre);
            cell.setCellStyle(titlestyle);
            c1++;

            cell = row.createCell(c1);
            cell.setCellValue("Asset");
            sheet.setColumnWidth(c1, 256 * 30);
            cell.setCellStyle(headstyle_centre);
            cell.setCellStyle(titlestyle);
            c1++;
            
            cell = row.createCell(c1);
            cell.setCellValue("Country");
            sheet.setColumnWidth(c1, 256 * 30);
            cell.setCellStyle(headstyle_centre);
            cell.setCellStyle(titlestyle);
            c1++;
            
            cell = row.createCell(c1);
            cell.setCellValue("Onshore");          
            sheet.setColumnWidth(c1, 256 * 30);
            cell.setCellStyle(headstyle_centre);
            cell.setCellStyle(titlestyle);
            c1++;
            
            cell = row.createCell(5);
            cell.setCellValue("Offshore");
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 5,8 ));
            sheet.setColumnWidth(c1, 256 * 30);
            cell.setCellStyle(headstyle_centre);
            cell.setCellStyle(titlestyle);
            c1++;
            
            cell = row.createCell(9);
            cell.setCellValue("Total");          
            sheet.setColumnWidth(c1, 256 * 30);
            cell.setCellStyle(headstyle_centre);
            cell.setCellStyle(titlestyle);
            c1++;
            
            int rowval = 1;
            int c = 1;
            
            row = sheet.createRow(rowval); 
            
            cell = row.createCell(c);
            cell.setCellValue("");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Total");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Normal");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Extended");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("Overstay");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            cell = row.createCell(c);
            cell.setCellValue("");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
         
            int col = 0;
            int rownum = 2;
         
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
                    if(info.getCountry() != null && !info.getCountry().equals(""))
                        cell.setCellValue(info.getCountry());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getStatus1());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getNormal()+info.getDelayed()+info.getExtended());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getNormal());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDelayed());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getExtended());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getStatus1()+info.getNormal()+info.getDelayed()+info.getExtended());
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
