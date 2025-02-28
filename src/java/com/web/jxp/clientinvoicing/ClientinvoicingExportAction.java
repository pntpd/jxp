package com.web.jxp.clientinvoicing;

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

public class ClientinvoicingExportAction extends Action
{        
    @Override
    @SuppressWarnings("static-access")
    public ActionForward execute (ActionMapping mapping, ActionForm form,  HttpServletRequest request, HttpServletResponse response)   throws Exception 
    { 
       try
        { 
            Clientinvoicing clientinvoicing = new Clientinvoicing();
            ClientinvoicingForm frm = (ClientinvoicingForm)form;         
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
            int assetIdIndex = frm.getAssetIdIndex();
            frm.setAssetIdIndex(assetIdIndex);

            int typeIndex = frm.getTypeIndex();
            frm.setTypeIndex(typeIndex);
            int clientIdIndex = frm.getClientIdIndex();
            frm.setClientIdIndex(clientIdIndex);
            String search = frm.getSearch() != null ? frm.getSearch() : "";
            int type = frm.getType();
            if(type == 1)
            {
                ArrayList list = clientinvoicing.getListForExcel(search,  allclient, permission, cids, assetids,  clientIdIndex,  assetIdIndex,  typeIndex);
                int total = list.size();  
                String fileName = "Clientinvoicing_Report";
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
                cell.setCellValue("Client");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Asset");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Schedule");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Invoices");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                int col = 0;
                int rownum = 1;

                for(int i = 0; i < total;i++)
                {
                    ClientinvoicingInfo info = (ClientinvoicingInfo) list.get(i);
                    if(info != null)
                    {                    
                        col = 0 ;
                        row = sheet.createRow(rownum++); 

                        cell = row.createCell(col++);
                        cell.setCellValue((i+1));
                        cell.setCellStyle(contentstyle);   

                        cell = row.createCell(col++);
                        if(info.getClientName()!= null && !info.getClientName().equals(""))
                            cell.setCellValue(info.getClientName());
                        else
                            cell.setCellType(cell.CELL_TYPE_BLANK);
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        if(info.getClientassetName()!= null && !info.getClientassetName().equals(""))
                            cell.setCellValue(info.getClientassetName());
                        else
                            cell.setCellType(cell.CELL_TYPE_BLANK);
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        if(clientinvoicing.getschedulevalue(info.getType()) != null && !clientinvoicing.getschedulevalue(info.getType()).equals(""))
                            cell.setCellValue(clientinvoicing.getschedulevalue(info.getType()));
                        else
                            cell.setCellType(cell.CELL_TYPE_BLANK);
                        cell.setCellStyle(contentstyle);                

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getInvoiceCount());
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
            if(type == 2)
            {
                ArrayList list = new ArrayList();
                if(request.getAttribute("CLIENTTIMESHEET_LIST") != null)
                {
                    list = (ArrayList) request.getSession().getAttribute("CLIENTTIMESHEET_LIST");
                }
                int total = list.size();  
                String fileName = "Clientinvoicing_Details_Report";
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
                cell.setCellValue("Invoice Id");
                sheet.setColumnWidth(c, 256*10);
                cell.setCellStyle(headstyle);
                c++;
                
                cell = row.createCell(c);
                cell.setCellValue("Status");
                sheet.setColumnWidth(c, 256*10);
                cell.setCellStyle(headstyle);
                c++;
                
                cell = row.createCell(c);
                cell.setCellValue("Generated On");
                sheet.setColumnWidth(c, 256*10);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Sent on");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("From Date");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("To Date");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                int col = 0;
                int rownum = 1;

                for(int i = 0; i < total;i++)
                {
                    ClientinvoicingInfo info = (ClientinvoicingInfo) list.get(i);
                    if(info != null)
                    {                    
                        col = 0 ;
                        row = sheet.createRow(rownum++); 

                        cell = row.createCell(col++);
                        cell.setCellValue((clientinvoicing.changeNum( info.getInvoiceId(),6)));
                        cell.setCellStyle(contentstyle);   
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(clientinvoicing.getInvoicestatusvalue(info.getInvoicestatus()));
                        cell.setCellStyle(contentstyle);   

                        cell = row.createCell(col++);
                        if(info.getGeneratedate()!= null && !info.getGeneratedate().equals(""))
                            cell.setCellValue(info.getGeneratedate());
                        else
                            cell.setCellType(cell.CELL_TYPE_BLANK);
                        cell.setCellStyle(contentstyle);       
                        
                        cell = row.createCell(col++);
                        if(info.getSentdate()!= null && !info.getSentdate().equals(""))
                            cell.setCellValue(info.getSentdate());
                        else
                            cell.setCellType(cell.CELL_TYPE_BLANK);
                        cell.setCellStyle(contentstyle);                                 
                        
                        cell = row.createCell(col++);
                        if(info.getFromdate()!= null && !info.getFromdate().equals(""))
                            cell.setCellValue(info.getFromdate());
                        else
                            cell.setCellType(cell.CELL_TYPE_BLANK);
                        cell.setCellStyle(contentstyle);                                 
                        
                        cell = row.createCell(col++);
                        if(info.getTodate()!= null && !info.getTodate().equals(""))
                            cell.setCellValue(info.getTodate());
                        else
                            cell.setCellType(cell.CELL_TYPE_BLANK);
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
