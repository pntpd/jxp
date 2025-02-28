package com.web.jxp.pcode;

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

public class PcodeExportAction extends Action
{        
    @Override
    @SuppressWarnings("static-access")
    public ActionForward execute (ActionMapping mapping, ActionForm form,  HttpServletRequest request, HttpServletResponse response)   throws Exception 
    { 
       try
        { 
            Pcode pcode = new Pcode();
            PcodeForm frm = (PcodeForm)form;             
            String search =  frm.getSearch() != null ? frm.getSearch() : "";
            int assettypeIdIndex = frm.getAssettypeIdIndex();
            int pdeptIdIndex = frm.getPdeptIdIndex();
            int type = frm.getType();               
            ArrayList list = new ArrayList();
            String fileName = "";
            if(type == 1)
            {
                fileName = "Competency_Framework_List";
                list = pcode.getExcel(search, assettypeIdIndex, pdeptIdIndex); 
            }
            else if(type == 2)
            {
                int pcodeId = frm.getPcodeId();
                fileName = "Competency_Framework_Question_List";
                list = pcode.getExcelQuestion(pcodeId); 
            }
            int total = list.size();  
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
            
            if(type == 1)
            {
                cell = row.createCell(c);
                cell.setCellValue("Role Competency");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;
                
                cell = row.createCell(c);
                cell.setCellValue("Code");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Asset Type");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Departmet");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;
            }    
            else if(type == 2)
            {
                cell = row.createCell(c);
                cell.setCellValue("Asset Type");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;
                
                cell = row.createCell(c);
                cell.setCellValue("Department");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Competency Code");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Category");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;
                
                cell = row.createCell(c);
                cell.setCellValue("Question");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;
            }  
            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < total;i++)
            {
                PcodeInfo info = (PcodeInfo) list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue((i+1));
                    cell.setCellStyle(contentstyle);   
                    if(type == 1)
                    {
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getCode()!= null ? info.getCode(): "");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getName()!= null ? info.getName(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getAssettypeName()!= null ? info.getAssettypeName(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getDeptName()!= null ? info.getDeptName(): "");
                        cell.setCellStyle(contentstyle);
                    }
                    else if(type == 2)
                    {
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getAssettypeName()!= null ? info.getAssettypeName(): "");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getDeptName()!= null ? info.getDeptName(): "");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getCode()!= null ? info.getCode(): "");
                        cell.setCellStyle(contentstyle); 
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getCategoryName()!= null ? info.getCategoryName(): "");
                        cell.setCellStyle(contentstyle);                        

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getQuestionName()!= null ? info.getQuestionName(): "");
                        cell.setCellStyle(contentstyle);                        
                    }
                }
                info = null;
            }
            list.clear();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename = \""+fileName+".xls\"");
            final ServletOutputStream os =  response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        }
        catch (Exception e)
        {   
            e.printStackTrace();
        }
        return null;
    }
}
