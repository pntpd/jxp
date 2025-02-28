package com.web.jxp.candidate;

import com.web.jxp.talentpool.Ddl;
import com.web.jxp.talentpool.TalentpoolInfo;
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
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExportExcelAction extends Action
{        
    @Override
    @SuppressWarnings("static-access")
    public ActionForward execute (ActionMapping mapping, ActionForm form,  HttpServletRequest request, HttpServletResponse response)   throws Exception 
    { 
       try
        { 
            CandidateForm frm = (CandidateForm)form;     
            Ddl ddl = new Ddl();
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = ddl.getDoctypeExcel(candidateId);
            TalentpoolInfo tinfo = ddl.getDoctypeInfo(candidateId);
            String clientName = "", assetName = "", name = "", position = "";
            if(tinfo != null)
            {
                clientName = tinfo.getClientName() != null ? tinfo.getClientName(): "";
                assetName = tinfo.getAssetName() != null ? tinfo.getAssetName(): "";
                name = tinfo.getName() != null ? tinfo.getName(): "";
                position = tinfo.getPosition()!= null ? tinfo.getPosition(): "";
            }
            int total = list.size();  
            String fileName = "Doctype_Report";
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Data");
            HSSFRow row = null;
            HSSFCell cell = null;
            
            HSSFFont headFont = wb.createFont();
            headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            headFont.setFontHeightInPoints((short)10);
            
            HSSFFont contentFont = wb.createFont();
            contentFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            contentFont.setFontHeightInPoints((short)10);
           
            HSSFCellStyle headstyle = wb.createCellStyle();
            headstyle.setFont(headFont);
            headstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

            HSSFCellStyle contentstyle = wb.createCellStyle();
            contentstyle.setFont(contentFont);
            contentstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            
            int rowval = 0;
            int c = 0;
            row = sheet.createRow(rowval);

            cell = row.createCell(0);
            cell.setCellValue("Client");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);

            cell = row.createCell(1);
            cell.setCellValue(clientName);
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(contentstyle);

            row = sheet.createRow(++rowval);
            cell = row.createCell(0);
            cell.setCellValue("Asset");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);

            cell = row.createCell(1);
            cell.setCellValue(assetName);
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(contentstyle);

            row = sheet.createRow(++rowval);
            cell = row.createCell(0);
            cell.setCellValue("Name");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);

            cell = row.createCell(1);
            cell.setCellValue(name);
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(contentstyle);

            row = sheet.createRow(++rowval);
            cell = row.createCell(0);
            cell.setCellValue("Position | Rank");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);         

            cell = row.createCell(1);
            cell.setCellValue(position);
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(contentstyle);   
            
            cell = row.createCell(2);
            cell.setCellValue("Expiry date");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);

            for(int i = 0; i < total;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                if(info != null)
                {   
                    row = sheet.createRow(++rowval);

                    cell = row.createCell(0);                        
                    cell.setCellValue(info.getDocumentname() != null ? info.getDocumentname() : "");
                    cell.setCellStyle(headstyle);

                    cell = row.createCell(1);
                    cell.setCellValue(info.getStatusValue() != null ? info.getStatusValue() : "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(2);
                    cell.setCellValue(info.getExpirydate()!= null ? info.getExpirydate() : "");
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
