package com.web.jxp.checkpoint;

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

public class CheckPointExportAction extends Action {
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
        try 
        {
            CheckPoint checkpoint = new CheckPoint();
            CheckPointForm frm = (CheckPointForm) form;
            String search = frm.getSearch() != null ? frm.getSearch() : "";
            ArrayList list = checkpoint.getListForExcel(search);
            int total = list.size();
            String fileName = "Checkpoint_Report";
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Data");
            HSSFRow row = null;
            HSSFCell cell = null;

            HSSFFont titleFont = wb.createFont();
            titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            titleFont.setFontHeightInPoints((short) 12);

            HSSFFont mergeFont = wb.createFont();
            mergeFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            mergeFont.setFontHeightInPoints((short) 10);

            HSSFFont headFont = wb.createFont();
            headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            headFont.setFontHeightInPoints((short) 10);

            HSSFFont contentFont = wb.createFont();
            contentFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            contentFont.setFontHeightInPoints((short) 10);

            HSSFFont contentFontBold = wb.createFont();
            contentFontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            contentFontBold.setFontHeightInPoints((short) 10);

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
            cell.setCellValue("Ref. No");
            sheet.setColumnWidth(c, 256 * 10);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Checkpoint Name");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Client");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Asset");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Position-Rank");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Minimum Requirement");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;

            int col = 0;
            int rownum = 1;

            for (int i = 0; i < total; i++)
            {
                CheckPointInfo info = (CheckPointInfo) list.get(i);
                if (info != null) 
                {
                    col = 0;
                    row = sheet.createRow(rownum++);
                    cell = row.createCell(col++);
                    cell.setCellValue((i+1));
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getName()!= null ? info.getName() : "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getClientname() != null ? info.getClientname() : "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getClientassetname() != null ? info.getClientassetname() : "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPositionname() != null ? info.getPositionname() + " / "+ info.getGrade() : "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getFlag() ==1 ? "Yes" : "No");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getStatus() != 0 ?  checkpoint.getStatusById(info.getStatus()) : "");
                    cell.setCellStyle(contentstyle);
                }
                info = null;
            }
            list.clear();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename = \"" + fileName + ".xls\"");
            final ServletOutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
