package com.web.jxp.mobilization;

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

public class MobilizationExportTravelAction extends Action 
{

    @Override
    @SuppressWarnings("static-access")
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try 
        {
            Mobilization mobilization = new Mobilization();
            MobilizationForm frm = (MobilizationForm) form;
            
            int crewrotationId = frm.getCrewrotationId();
            frm.setCrewrotationId(crewrotationId);
            int type = frm.getType();
            frm.setType(type);
            String view_mobilization = mobilization.getMainPath("view_mobilization");
            ArrayList list = mobilization.getMobDataByCrewId(crewrotationId, type);
            int total = list.size();
            String fileName = "Mobilization_Travel_Report";
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
            
            HSSFCellStyle headstyle_centre = wb.createCellStyle();
            headstyle_centre.setFont(headFont);
            headstyle_centre.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            HSSFCellStyle contentstyle = wb.createCellStyle();
            contentstyle.setFont(contentFont);
            contentstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

            HSSFCellStyle contentstyle_right = wb.createCellStyle();
            contentstyle_right.setFont(contentFont);
            contentstyle_right.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

            HSSFCellStyle contentstylebold = wb.createCellStyle();
            contentstylebold.setFont(contentFontBold);
            contentstylebold.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

            int rowvalh = 0;
            int c1 = 0;

            row = sheet.createRow(rowvalh);

            cell = row.createCell(c1);
            cell.setCellValue("MODE");
            sheet.setColumnWidth(c1, 256 * 10);
            cell.setCellStyle(headstyle_centre);
            c1++;

            cell = row.createCell(c1);
            cell.setCellValue("DEPARTURE");
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 1,4 ));
            sheet.setColumnWidth(c1, 256 * 30);
            cell.setCellStyle(headstyle_centre);
            c1++;

            cell = row.createCell(5);
            cell.setCellValue("ARRIVAL");
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 5,8 ));
            sheet.setColumnWidth(c1, 256 * 30);
            cell.setCellStyle(headstyle_centre);
            c1++;
            
            int rowval = 1;
            int c = 0;
            
            row = sheet.createRow(rowval);

            cell = row.createCell(c);
            cell.setCellValue("Flight/train /Bus/Car No.");
            sheet.setColumnWidth(c, 256 * 10);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Location");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Airport/Train/Bus");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Terminal/Platform No.");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("ETD");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Location");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Airport/Train/Bus");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Terminal/Platform No.");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("ETA");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Filepath");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            int col = 0;
            int rownum = 2;

            for (int i = 0; i < total; i++) 
            {
                MobilizationInfo info = (MobilizationInfo) list.get(i);
                if (info != null) 
                {
                    col = 0;
                    row = sheet.createRow(rownum++);
                    
                    String modedetail = info.getVal1() != null ? info.getClientName() : "";
                    if(info.getVal5() != null && !info.getVal5().equals(""))
                        modedetail += "-"+info.getVal5();
                    cell = row.createCell(col++);
                    cell.setCellValue(modedetail);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getVal2() != null ? info.getVal2() : "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getVal3() != null ? info.getVal3() : "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getVal4() != null ? info.getVal4() : "");
                    cell.setCellStyle(contentstyle);
                    
                    String datetime = info.getVald9() != null ? info.getVald9() : "";
                    if(info.getValt9() != null && !info.getValt9().equals(""))
                        datetime += " | "+info.getValt9();
                    cell = row.createCell(col++);
                    cell.setCellValue(datetime);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getVal6() != null ? info.getVal6() : "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getVal7() != null ? info.getVal7() : "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getVal8() != null ? info.getVal8() : "");
                    cell.setCellStyle(contentstyle);
                    
                    String datetime1 = info.getVald10() != null ? info.getVald10() : "";
                    if(info.getValt10() != null && !info.getValt10().equals(""))
                        datetime1 += " | "+info.getValt10();
                    cell = row.createCell(col++);
                    cell.setCellValue(datetime1);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getFilename() != null && !info.getFilename().equals("") ? view_mobilization+info.getFilename() : "");
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
