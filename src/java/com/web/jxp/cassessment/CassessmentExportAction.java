package com.web.jxp.cassessment;

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

public class CassessmentExportAction extends Action 
{
    @Override
    @SuppressWarnings("static-access")
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
        try 
        {
            Cassessment cassessment = new Cassessment();
            CassessmentForm frm = (CassessmentForm) form;
            String search = frm.getSearch() != null ? frm.getSearch() : "";
            int positionIndex = frm.getPositionIndex();
            int vstatusIndex = frm.getVstatusIndex();
            int astatusIndex = frm.getAstatusIndex();
            int assettypeIndex = frm.getAssettypeIndex();
            ArrayList list = cassessment.getListForExcel(search, vstatusIndex, astatusIndex, positionIndex, assettypeIndex);
            int total = list.size();
            String fileName = "assessment_Report";
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
            cell.setCellValue("ID");
            sheet.setColumnWidth(c, 256 * 10);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Name");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Asset Type");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Position-Rank");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Verified On");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Verified");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Assessment Status");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Scheduled");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            int col = 0;
            int rownum = 1;

            for (int i = 0; i < total; i++) 
            {
                CassessmentInfo info = (CassessmentInfo) list.get(i);
                if (info != null) 
                {
                    col = 0;
                    row = sheet.createRow(rownum++);
                    
                    cell = row.createCell(col++);
                    if(info.getIds() != null && !info.getIds().equals(""))
                        cell.setCellValue(info.getIds());
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
                    if(info.getAssettypeName() != null && !info.getAssettypeName().equals(""))
                        cell.setCellValue(info.getAssettypeName());
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
                    if(info.getVdate() != null && !info.getVdate().equals(""))
                        cell.setCellValue(info.getVdate());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    if(info.getVflagstatus() != null && !info.getVflagstatus().equals(""))
                        cell.setCellValue(info.getVflagstatus());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    if(info.getAflagstatus() != null && !info.getAflagstatus().equals(""))
                        cell.setCellValue(info.getAflagstatus());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    if(info.getScheduled() != null && !info.getScheduled().equals(""))
                        cell.setCellValue(info.getScheduled());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
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
