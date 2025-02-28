package com.web.jxp.managetraining;

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

public class ManagetrainingExportAction extends Action
{
        
    @Override
    @SuppressWarnings("static-access")
    public ActionForward execute (ActionMapping mapping, ActionForm form,  HttpServletRequest request, HttpServletResponse response)   throws Exception 
    { 
       try
        { 
            Managetraining managetraining = new Managetraining();
            ManagetrainingForm frm = (ManagetrainingForm)form;
            String exptype = frm.getExptype();
            if (exptype!= null && exptype.equals("1")) 
            {
                exptype = "";
                int clientIdIndex = frm.getClientIdIndex();
                int assetIdIndex = frm.getAssetIdIndex();
                String search = frm.getSearch() != null ? frm.getSearch() : "";
                int positionIdIndex = frm.getPositionIdIndex();
                int mode = frm.getMode();
                int categoryIdIndex = frm.getCategoryIdIndex();
                int subcategoryIdIndex = frm.getSubcategoryIdIndex();

                ArrayList managetrainingList = managetraining.getManagetrainingByName(clientIdIndex, assetIdIndex, positionIdIndex, mode, search, categoryIdIndex, subcategoryIdIndex);   
                int total = managetrainingList.size(); 
                if(mode == 1){
                String fileName = "Manage_Training_Report";
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
                cell.setCellValue("Personnel Name");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Position-Rank");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Trainings");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Percentage Completion");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;
                int col = 0;
                int rownum = 1;

                for(int i = 0; i < total;i++)
                {
                    ManagetrainingInfo info = (ManagetrainingInfo) managetrainingList.get(i);
                    if(info != null)
                    {                    
                        col = 0 ;
                        row = sheet.createRow(rownum++); 
                        cell = row.createCell(col++);
                        cell.setCellValue((i+1));
                        cell.setCellStyle(contentstyle);   

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getName()!= null ? info.getName() : "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getPositionName());
                        cell.setCellStyle(contentstyle); 

                        cell = row.createCell(col++);
                        cell.setCellValue(managetraining.changeNum(info.getPcount(),2)+"/"+managetraining.changeNum(info.getTotal(),2));
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(managetraining.getDecimal(info.getPercent()));
                        cell.setCellStyle(contentstyle);

                    }
                    info = null;
            }
                managetrainingList.clear();
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename = \""+fileName+".xls\"");
               try (ServletOutputStream os = response.getOutputStream()) {
                   wb.write(os);
                   os.flush();
               }

                }
                else if(mode == 2){
                String fileName = "Manage_Training_Report";
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
                cell.setCellValue("Courses");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Category");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Sub-Category");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Positions");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;
                int col = 0;
                int rownum = 1;

                for(int i = 0; i < total;i++)
                {
                    ManagetrainingInfo info = (ManagetrainingInfo) managetrainingList.get(i);
                    if(info != null)
                    {                    
                        col = 0 ;
                        row = sheet.createRow(rownum++); 
                        cell = row.createCell(col++);
                        cell.setCellValue((i+1));
                        cell.setCellStyle(contentstyle);   

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getCourseName()!= null ? info.getCourseName() : "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getCategoryName()!= null ? info.getCategoryName() : "");
                        cell.setCellStyle(contentstyle); 

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getSubcategoryName()!= null ? info.getSubcategoryName() : "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(managetraining.changeNum(info.getTotal(),2));
                        cell.setCellStyle(contentstyle);

                    }
                    info = null;
                }
                managetrainingList.clear();
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename = \""+fileName+".xls\"");
               try (ServletOutputStream os = response.getOutputStream()) {
                   wb.write(os);
                   os.flush();
               }
                }
            }
            else if (exptype != null && exptype.equals("2")) 
            {
                exptype = "";
                int crewrotationId = frm.getCrewrotationId();
                ManagetrainingInfo info1 = managetraining.getBasicDetail(crewrotationId);
                int positionId = 0;
                if (info1 != null) {
                    positionId = info1.getPositionId();
                }
                int cid = frm.getCategoryIdDetail();
                int scid = frm.getSubcategoryIdDetail();
                int ftype = frm.getFtype();
                String searchd = frm.getSearchdetail() != null ? frm.getSearchdetail() : "";
                int statusIndex = frm.getStatusIndex();
                int clientIdIndex = frm.getClientIdIndex();
                int assetIdIndex = frm.getAssetIdIndex();
                ArrayList assignlist1 = managetraining.getListAssign1(clientIdIndex, assetIdIndex, positionId, 
                crewrotationId, cid, scid, ftype, searchd, statusIndex);
                int total = assignlist1.size(); 
                String fileName = "Manage_Training_Report";
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
                cell.setCellValue("Course");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Category");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Sub-Category");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Course Type");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Priority");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Status");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;
                
                cell = row.createCell(c);
                cell.setCellValue("Date");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;
                
                int col = 0;
                int rownum = 1;

                for(int i = 0; i < total;i++)
                {
                    ManagetrainingInfo info = (ManagetrainingInfo) assignlist1.get(i);
                    if(info != null)
                    {                    
                        col = 0 ;
                        row = sheet.createRow(rownum++); 
                        cell = row.createCell(col++);
                        cell.setCellValue((i+1));
                        cell.setCellStyle(contentstyle);   

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getCourseName()!= null ? info.getCourseName() : "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getCategoryName()!= null ? info.getCategoryName() : "");
                        cell.setCellStyle(contentstyle); 

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getSubcategoryName()!= null ? info.getSubcategoryName() : "");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getCoursetype()!= null ? info.getCoursetype() : "");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getLevel()!= null ? info.getLevel() : "");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getStatusval()!= null ? info.getStatusval() : "");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getDate()!= null ? info.getDate() : "");
                        cell.setCellStyle(contentstyle);

                    }
                    info = null;
            }
                assignlist1.clear();
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename = \""+fileName+".xls\"");
               try (ServletOutputStream os = response.getOutputStream()) {
                   wb.write(os);
                   os.flush();
               }
            }
            else if (exptype != null && exptype.equals("3")) 
            {
                exptype = "";
                int ftype = frm.getFtype();
                int statusIndex = frm.getStatusIndex();
                int clientIdIndex = frm.getClientIdIndex();
                int courseId = frm.getCourseId();
                int assetIdIndex = frm.getAssetIdIndex();
                int positionId2Index = frm.getPositionId2Index();
                String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
                ArrayList assignlist2 = managetraining.getListAssign2(clientIdIndex, assetIdIndex, courseId, ftype, search, statusIndex, positionId2Index);
                int total = assignlist2.size(); 
                String fileName = "Manage_Training_Report";
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
                cell.setCellValue("Personnel Names");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Position - Rank");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Course Type");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Priority");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Status");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;
                
                cell = row.createCell(c);
                cell.setCellValue("Date");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;
                
                int col = 0;
                int rownum = 1;

                for(int i = 0; i < total;i++)
                {
                    ManagetrainingInfo info = (ManagetrainingInfo) assignlist2.get(i);
                    if(info != null)
                    {                    
                        col = 0 ;
                        row = sheet.createRow(rownum++); 
                        cell = row.createCell(col++);
                        cell.setCellValue((i+1));
                        cell.setCellStyle(contentstyle);   

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getName()!= null ? info.getName() : "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getPositionName()!= null ? info.getPositionName() : "");
                        cell.setCellStyle(contentstyle); 

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getCoursetype()!= null ? info.getCoursetype() : "");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getLevel()!= null ? info.getLevel() : "");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getStatusval()!= null ? info.getStatusval() : "");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getDate()!= null ? info.getDate() : "");
                        cell.setCellStyle(contentstyle);

                    }
                    info = null;
                }
                assignlist2.clear();
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
