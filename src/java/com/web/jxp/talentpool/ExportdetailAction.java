package com.web.jxp.talentpool;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.web.jxp.user.UserInfo;
import javax.servlet.ServletOutputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExportdetailAction extends Action {
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TalentpoolForm frm = (TalentpoolForm) form;
        Talentpool talentpool = new Talentpool();
        Ddl ddl = new Ddl();
        Export export = new Export();
        
        int uId = 0;
        String permission = "N";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
            }
        }
        int check_user = talentpool.checkUserSession(request, 4, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = talentpool.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Candidate Enrollment Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        String file_path = talentpool.getMainPath("view_candidate_file");
        String extype = frm.getEXTYPE();
        if (extype!= null && extype.equals("2")) {
            extype = "";
            int candidateId = frm.getCandidateId();
            ArrayList list = talentpool.getCandlanguageList(candidateId);
        try
        { 
            int total = list.size();  
            String fileName = "TalentPool_Language_Report";
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
            cell.setCellValue("Language");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Proficiency");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Filepath");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < total;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++); 
                  
                    cell = row.createCell(col++);
                    cell.setCellValue((i+1));
                    cell.setCellStyle(contentstyle);  
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getLanguageName()!= null ? info.getLanguageName(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getProficiencyName()!= null ? info.getProficiencyName(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(talentpool.getStatusById(info.getStatus()));
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCandlangfilename()!= null && !info.getCandlangfilename().equals("") ? file_path+info.getCandlangfilename(): "");
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
        else if (extype!= null && extype.equals("3"))
        {
            extype = "";
            int candidateId = frm.getCandidateId();
            TalentpoolInfo info = talentpool.getCandidateHealthDetailBycandidateId(candidateId);
            if(info != null )
            {
            try
                { 
                    String fileName = "TalentPool_Health_Report";
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
                    cell.setCellValue("Seaman Specific Medical Fitness");
                    sheet.setColumnWidth(c, 256*10);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("OGUK Medical FTW");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("OGUK Expiry");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Medical Fitness Certificate");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Medical Fitness Certificate Expiry");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Blood Group");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Blood Pressure");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Hypertension");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Diabetes");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Smoking");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Covid-19 2 Doses");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    int col = 0;
                    int rownum = 1;

                    if(info != null)
                    {                    
                        col = 0 ;
                        row = sheet.createRow(rownum++); 

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getSsmf()!= null ? info.getSsmf(): "");
                        cell.setCellStyle(contentstyle); 

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getOgukmedicalftw()!= null ? info.getOgukmedicalftw(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getOgukexp()!= null ? info.getOgukexp(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getMedifitcert()!= null ? info.getMedifitcert(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getMedifitcertexp()!= null ? info.getMedifitcertexp(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getBloodgroup()!= null ? info.getBloodgroup(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getBloodpressure()!= null ? info.getBloodpressure(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getHypertension()!= null ? info.getHypertension(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getDiabetes()!= null ? info.getDiabetes(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getSmoking()!= null ? info.getSmoking(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getCov192doses()!= null ? info.getCov192doses(): "");
                        cell.setCellStyle(contentstyle);

                    }
                    info = null;
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
        
        else if (extype!= null && extype.equals("4")) {
            extype = "";
            int candidateId = frm.getCandidateId();
            ArrayList list = talentpool.getCandVaccList(candidateId);
        try
        { 
            int total = list.size();  
            String fileName = "TalentPool_Vaccination_Report";
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
            cell.setCellValue("Name");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Type");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Place");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Application Date");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Expiration Date");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Filepath");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;

            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < total;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++); 
                  
                    cell = row.createCell(col++);
                    cell.setCellValue((i+1));
                    cell.setCellStyle(contentstyle);  
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getVaccinationName()!= null ? info.getVaccinationName(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getVacinationType()!= null ? info.getVacinationType(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPlaceofapplication()!= null ? info.getPlaceofapplication(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDateofapplication()!= null ? info.getDateofapplication(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDateofexpiry()!= null ? info.getDateofexpiry(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(talentpool.getStatusById(info.getStatus()));
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getVaccinecertfile()!= null  && !info.getVaccinecertfile().equals("") ? file_path+info.getVaccinecertfile(): "");
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
        else if (extype!= null && extype.equals("5")) {
            extype = "";
            int candidateId = frm.getCandidateId();
            ArrayList list = export.getexperiencelistdetails(candidateId);
        try
        { 
            int total = list.size();  
            String fileName = "TalentPool_Experience_Report";
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
            cell.setCellValue("CompanyName");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("CompanyIndustry");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("AssetType");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Asset Name");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Position");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Department/Function");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Country");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("City");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Operater");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("WaterDepth");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("LastDrawnSalary(currency)");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            
            cell = row.createCell(c);
            cell.setCellValue("LastDrawnSalary");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Work Start date");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Work End date");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("CurrentyWorking");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("ExperienceCertFilePath");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Skills");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Ranks");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("OwnerPool");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("CrewType");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("OCSEmployed");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("LegalRights");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("DayRate(currency)");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("DayRate");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("MonthlySalary(currency)");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("MonthlySalary");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("WorkFilePath");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;

            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < total;i++)
            {
                ExportInfo info = (ExportInfo) list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++); 
                  
                    cell = row.createCell(col++);
                    cell.setCellValue((i+1));
                    cell.setCellStyle(contentstyle);  
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCompanyname()!= null ? info.getCompanyname(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCompayIndname()!= null ? info.getCompayIndname(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAssettypename()!= null ? info.getAssettypename(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAssetName()!= null ? info.getAssetName(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPosition()!= null ? info.getPosition(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDepartment()!= null ? info.getDepartment(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCountryname()!= null ? info.getCountryname(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCityname()!= null ? info.getCityname(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getOperator()!= null ? info.getOperator(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getWaterdepth()!= null ? info.getWaterdepth(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getLastdrawncurrrency()!= null ? info.getLastdrawncurrrency(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getLastdrawncurrrencyvalue());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getWorkstartdate()!= null ? info.getWorkstartdate(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCurrentworkingstatus()!= 1 && info.getWorkenddate()!= null ? info.getWorkenddate() : "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCurrentworkingstatus()== 1 ? "Yes" : "No");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getExperiencefilename()!= null && !info.getExperiencefilename().equals("") ? file_path+info.getExperiencefilename(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getSkills()!= null ? info.getSkills(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getRank()!= null ? info.getRank(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getOwnerpool()!= null ? info.getOwnerpool(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCrewtypename()!= null ? info.getCrewtypename(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getOcsemployed()!= null ? info.getOcsemployed(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getLegalrights()!= null ? info.getLegalrights(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDaterate()!= null ? info.getDaterate(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDayvalue());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getMonthrate()!= null ? info.getMonthrate(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getMonthvalue());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getWorkfilename()!= null && !info.getWorkfilename().equals("") ? file_path+info.getWorkfilename(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(talentpool.getStatusById(info.getStatus()));
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
        else if (extype!= null && extype.equals("6")) {
            extype = "";
            int candidateId = frm.getCandidateId();
            ArrayList list = export.geteducationlistdetails(candidateId);
        try
        { 
            int total = list.size();  
            String fileName = "TalentPool_Education_Report";
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
            cell.setCellValue("Kind");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Degree");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Institution");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Location");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Start Date");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("End Date");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Highest Qualification");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Filepath");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;

            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < total;i++)
            {
                ExportInfo info = (ExportInfo) list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++); 
                  
                    cell = row.createCell(col++);
                    cell.setCellValue((i+1));
                    cell.setCellStyle(contentstyle);  
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getKindname()!= null ? info.getKindname(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDegree()!= null ? info.getDegree(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEduinstitute()!= null ? info.getEduinstitute(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getLocationofInstitute()!= null ? info.getLocationofInstitute(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCoursestart()!= null ? info.getCoursestart(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPassingyear()!= null ? info.getPassingyear(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getHighestqualification()== 1 ? "yes" : "no");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(talentpool.getStatusById(info.getStatus()));
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEducationalfilename()!= null  && !info.getEducationalfilename().equals("") ? file_path+info.getEducationalfilename(): "");
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
        else if (extype!= null && extype.equals("7")) {
            extype = "";
            int candidateId = frm.getCandidateId();
            ArrayList list = export.gettrainingCertificatelistdetails(candidateId);
        try
        { 
            int total = list.size();  
            String fileName = "TalentPool_Certification_Report";
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
            cell.setCellValue("CourseType");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("CourseName");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Educational Institute");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Location of Institute(city)");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Field of study");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Course Start Date");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Passing Date");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Date of Issue");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Certification No");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Date of Expiry");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Approved By");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Filepath");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;

            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < total;i++)
            {
                ExportInfo info = (ExportInfo) list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++); 
                  
                    cell = row.createCell(col++);
                    cell.setCellValue((i+1));
                    cell.setCellStyle(contentstyle);  
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCoursetype()!= null ? info.getCoursetype(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCoursename()!= null ? info.getCoursename(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEduinstitute()!= null ? info.getEduinstitute(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getLocationofInstitute()!= null ? info.getLocationofInstitute(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getFieldofstudy()!= null ? info.getFieldofstudy(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCoursestart()!= null ? info.getCoursestart(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPassingyear()!= null ? info.getPassingyear(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDateofissue()!= null ? info.getDateofissue(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getExpirydate()!= null ? info.getExpirydate(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCertificateno()!= null ? info.getCertificateno(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getApprovedby()!= null ? info.getApprovedby(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(talentpool.getStatusById(info.getStatus()));
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCertifilename()!= null  && !info.getCertifilename().equals("") ? file_path+info.getCertifilename(): "");
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
        
        else if (extype!= null && extype.equals("9")) {
            extype = "";
            int candidateId = frm.getCandidateId();
            ArrayList list = export.getCandbankListdetails(candidateId);
        try
        { 
            int total = list.size();  
            String fileName = "TalentPool_Bank_Report";
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
            cell.setCellValue("Bank Name");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Type of Account");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Account No");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Branch");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("IFSC");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Primary Account");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                      
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Filepath");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;

            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < total;i++)
            {
                ExportInfo info = (ExportInfo) list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++); 
                  
                    cell = row.createCell(col++);
                    cell.setCellValue((i+1));
                    cell.setCellStyle(contentstyle);  
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getBankName()!= null ? info.getBankName(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAccountTypename()!= null ? info.getAccountTypename(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAccountno()!= null ? info.getAccountno(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getBranch()!= null ? info.getBranch(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getIFSCCode()!= null ? info.getIFSCCode(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPrimarybankId() == 1 ? "Primary" : "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(talentpool.getStatusById(info.getStatus()));
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getBkFilename()!= null  && !info.getBkFilename().equals("") ? file_path+info.getBkFilename(): "");
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
        else if (extype!= null && extype.equals("10")) {
            extype = "";
            int candidateId = frm.getCandidateId();
            ArrayList list = talentpool.getCandgovdocList(candidateId);
        try
        { 
            int total = list.size();  
            String fileName = "TalentPool_Documents_Report";
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
            cell.setCellValue("Document Name");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Document Number");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Place of Issue");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Date of Issue");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Date of Expiry");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Document Issued By");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                      
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;

            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < total;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++); 
                  
                    cell = row.createCell(col++);
                    cell.setCellValue((i+1));
                    cell.setCellStyle(contentstyle);  
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDocumentname()!= null ? info.getDocumentname(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDocumentno()!= null ? info.getDocumentno(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPlaceofissue()!= null ? info.getPlaceofissue(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getIssuedby()!= null ? info.getIssuedby(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDateofissue()!= null ? info.getDateofissue(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDateofexpiry()!= null ? info.getDateofexpiry(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(talentpool.getStatusById(info.getStatus()));
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
        else if (extype!= null && extype.equals("11")) {
            extype = "";
            int candidateId = frm.getCandidateId();
            ArrayList list = talentpool.getVerificationList(candidateId);
        try
        { 
            int total = list.size();  
            String fileName = "TalentPool_Verification_Report";
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
            cell.setCellValue("Date");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Time");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Username");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Tab - Particulars");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Authority");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
           
            cell = row.createCell(c);
            cell.setCellValue("Filepath");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;

            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < total;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++); 
                  
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDate()!= null ? info.getDate(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getTime()!= null ? info.getTime(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getName()!= null ? info.getName(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getTabName()!= null ? info.getTabName(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAuthority()!= null ? info.getAuthority(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getStatus() == 1 ? "Minimum Verified" : "Verified");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getFilename()!= null  && !info.getFilename().equals("") ? file_path+info.getFilename(): "");
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
        else if (extype!= null && extype.equals("12")) {
            extype = "";
            int candidateId = frm.getCandidateId();
            ArrayList list = talentpool.getCassessmenthistoryByCandidateId(candidateId);
        try
        { 
            int total = list.size();  
            String fileName = "TalentPool_Assessment_Report";
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
            
            HSSFCellStyle contentstylegreen = wb.createCellStyle();
            contentstylegreen.setFont(contentFont);
            contentstylegreen.setFillForegroundColor(HSSFColor.BLUE.index);
            contentstylegreen.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            
            int rowval = 0;
            int c = 0;
            
            row = sheet.createRow(rowval); 
            
            cell = row.createCell(c);
            cell.setCellValue("Date");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Time");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Coordinator");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Assessment");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Assessor");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Score");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
           
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
           
            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < total;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++); 
                  
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDate()!= null ? info.getDate(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getTime()!= null ? info.getTime(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCoordinatorName()!= null ? info.getCoordinatorName(): "");
                    cell.setCellStyle(contentstyle);
                    
                    String assessment= "";
                    assessment = info.getAssessment()!= null ? info.getAssessment(): "";
                    if( info.getAttempt() > 1 )
                        assessment += " attempt "+info.getAttempt();
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(assessment);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAssessorName()!= null ? info.getAssessorName(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getMarks());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                     cell.setCellValue(info.getStatusValue()!= null ? info.getStatusValue(): "");
                    cell.setCellStyle(contentstyle);                
                }
                info = null;
            }
            list.clear();
            
            ArrayList anlist = talentpool.getCassessmentAssessNowhistoryByCandidateId(candidateId);
            HSSFSheet sheet1 = wb.createSheet("AssessNow");
            HSSFRow row1 = null;
            HSSFCell cell1 = null;
            int total1 = anlist.size();  
        
            int rowval1 = 0;
            int c1 = 0;
            
            row1 = sheet1.createRow(rowval1); 
            
            cell1 = row1.createCell(c1);
            cell1.setCellValue("Date");
            sheet1.setColumnWidth(c1, 256*10);
            cell1.setCellStyle(headstyle);
            c1++;
            
            cell1 = row1.createCell(c1);
            cell1.setCellValue("Time");
            sheet1.setColumnWidth(c1, 256*10);
            cell1.setCellStyle(headstyle);
            c1++;
          
            cell1 = row1.createCell(c1);
            cell1.setCellValue("Position");
            sheet1.setColumnWidth(c1, 256*30);
            cell1.setCellStyle(headstyle);
            c1++;
                        
            cell1 = row1.createCell(c1);
            cell1.setCellValue("Assess By");
            sheet1.setColumnWidth(c1, 256*30);
            cell1.setCellStyle(headstyle);
            c1++;
            
            cell1 = row1.createCell(c1);
            cell1.setCellValue("Status");
            sheet1.setColumnWidth(c1, 256*30);
            cell1.setCellStyle(headstyle);
            c1++;
           
            int col1 = 0;
            int rownum1 = 1;
         
            for(int i = 0; i < total1;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) anlist.get(i);
                if(info != null)
                {                    
                    col1 = 0 ;
                    row1 = sheet1.createRow(rownum1++); 
                  
                    cell1 = row1.createCell(col1++);
                    cell1.setCellValue(info.getDate()!= null ? info.getDate(): "");
                    cell1.setCellStyle(contentstyle); 
                    
                    cell1 = row1.createCell(col1++);
                    cell1.setCellValue(info.getTime()!= null ? info.getTime(): "");
                    cell1.setCellStyle(contentstyle);
                    
                    cell1 = row1.createCell(col1++);
                    cell1.setCellValue(info.getPosition()!= null ? info.getPosition(): "");
                    cell1.setCellStyle(contentstyle);
                    
                    cell1 = row1.createCell(col1++);
                    cell1.setCellValue(info.getUsername()!= null ? info.getUsername(): "");
                    cell1.setCellStyle(contentstyle);
                    
                    cell1 = row1.createCell(col1++);
                    cell1.setCellValue(info.getStatusValue()!= null ? info.getStatusValue(): "");
                    cell1.setCellStyle(contentstyle);                
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
        else if (extype!= null && extype.equals("13")) {
            extype = "";
            int candidateId = frm.getCandidateId();
            ArrayList list = talentpool.getShortlistinghistoryByCandidateId(candidateId);
        try
        { 
            int total = list.size();  
            String fileName = "TalentPool_Shortlisting_Report";
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
            
            HSSFCellStyle contentstylegreen = wb.createCellStyle();
            contentstylegreen.setFont(contentFont);
            contentstylegreen.setFillForegroundColor(HSSFColor.BLUE.index);
            contentstylegreen.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            
            int rowval = 0;
            int c = 0;
            
            row = sheet.createRow(rowval); 
            
            cell = row.createCell(c);
            cell.setCellValue("Ref");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Posted On");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Client - Asset");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Position - Rank");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Shortlisted On");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Shortlisted By");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
           
            cell = row.createCell(c);
            cell.setCellValue("Post Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
           
            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < total;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++); 
                  
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCode()!= null ? info.getCode(): "");
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPoston()!= null ? info.getPoston(): "");
                    cell.setCellStyle(contentstyle);
                    
                    String clientasset= "";
                    clientasset = info.getClientName()!= null ? info.getClientName(): "";
                    if( info.getClientAsset()!= null &&  !info.getClientAsset().equals(""))
                        clientasset += " - "+info.getClientAsset();
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(clientasset);
                    cell.setCellStyle(contentstyle);
                    
                    String assessment= "";
                    assessment = info.getPosition()!= null ? info.getPosition(): "";
                    if( info.getGradename() != null &&  !info.getGradename().equals(""))
                        assessment += " - "+info.getGradename();
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(assessment);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getShortliston()!= null ? info.getShortliston(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getName()!= null ? info.getName(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                     cell.setCellValue(info.getStatusvalue()!= null ? info.getStatusvalue(): "");
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
        else if (extype!= null && extype.equals("14")) {
            extype = "";
            int candidateId = frm.getCandidateId();
            ArrayList list = talentpool.getCompliancehistoryByCandidateId(candidateId);
        try
        { 
            int total = list.size();  
            String fileName = "TalentPool_Compliance_Report";
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
            
            HSSFCellStyle contentstylegreen = wb.createCellStyle();
            contentstylegreen.setFont(contentFont);
            contentstylegreen.setFillForegroundColor(HSSFColor.BLUE.index);
            contentstylegreen.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            
            int rowval = 0;
            int c = 0;
            
            row = sheet.createRow(rowval); 
            
            cell = row.createCell(c);
            cell.setCellValue("Ref");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Date");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Name");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("User");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < total;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++); 
                  
                    cell = row.createCell(col++);
                    cell.setCellValue(talentpool.changeNum( info.getJobpostId(),6));
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDate()!= null ? info.getDate(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getName()!= null ? info.getName(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getUserName()!= null ? info.getUserName(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getStatusValue()!= null ? info.getStatusValue(): "");
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
        else if (extype!= null && extype.equals("15")) {
            extype = "";
            int candidateId = frm.getCandidateId();
            ArrayList list = talentpool.getClientselectionhistoryByCandidateId(candidateId);
        try
        { 
            int total = list.size();  
            String fileName = "TalentPool_Selection_Report";
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
            
            HSSFCellStyle contentstylegreen = wb.createCellStyle();
            contentstylegreen.setFont(contentFont);
            contentstylegreen.setFillForegroundColor(HSSFColor.BLUE.index);
            contentstylegreen.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            
            int rowval = 0;
            int c = 0;
            
            row = sheet.createRow(rowval); 
            
            cell = row.createCell(c);
            cell.setCellValue("Ref");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Posted On");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Client - Asset");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Position - Rank");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Selection Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < total;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++); 
                  
                    cell = row.createCell(col++);
                    cell.setCellValue(talentpool.changeNum( info.getJobpostId(),6));
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDate()!= null ? info.getDate(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getClientName()!= null ? info.getClientName(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPositionname()!= null ? info.getPositionname(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getStatusValue()!= null ? info.getStatusValue(): "");
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
        else if (extype!= null && extype.equals("16")) {
            extype = "";
            int candidateId = frm.getCandidateId();
            ArrayList list = talentpool.getOnboardinghistoryByCandidateId(candidateId);
        try
        { 
            int total = list.size();  
            String fileName = "TalentPool_Onboarding_Report";
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
            
            HSSFCellStyle contentstylegreen = wb.createCellStyle();
            contentstylegreen.setFont(contentFont);
            contentstylegreen.setFillForegroundColor(HSSFColor.BLUE.index);
            contentstylegreen.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            
            int rowval = 0;
            int c = 0;
            
            row = sheet.createRow(rowval); 
            

            cell = row.createCell(c);
            cell.setCellValue("Client - Asset");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Position - Rank");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Date");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Onboarding Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < total;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++); 
                  
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getClientName()!= null ? info.getClientName(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPosition()!= null ? info.getPosition(): "");
                    cell.setCellStyle(contentstyle);
                                        
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getArrivalDate()!= null ? info.getArrivalDate(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getOnboardStatus() == 9  ? "Complete": "Pending");
                    cell.setCellStyle(contentstyle);                                    
                }
                info = null;
            }
            list.clear();
            
            ArrayList onboard_hist = ddl.getDirectOnboardHistory(candidateId);
            HSSFSheet sheet1 = wb.createSheet("Direct Onboard");
            HSSFRow row1 = null;
            HSSFCell cell1 = null;
            int total1 = onboard_hist.size();  
        
            int rowval1 = 0;
            int c1 = 0;
            
            row1 = sheet1.createRow(rowval1); 
            
            cell1 = row1.createCell(c1);
            cell1.setCellValue("Client-Asset");
            sheet1.setColumnWidth(c1, 256*10);
            cell1.setCellStyle(headstyle);
            c1++;
            
            cell1 = row1.createCell(c1);
            cell1.setCellValue("Position-Rank");
            sheet1.setColumnWidth(c1, 256*10);
            cell1.setCellStyle(headstyle);
            c1++;
          
            cell1 = row1.createCell(c1);
            cell1.setCellValue("User");
            sheet1.setColumnWidth(c1, 256*30);
            cell1.setCellStyle(headstyle);
            c1++;
                        
            cell1 = row1.createCell(c1);
            cell1.setCellValue("Date");
            sheet1.setColumnWidth(c1, 256*30);
            cell1.setCellStyle(headstyle);
            c1++;
            
            cell1 = row1.createCell(c1);
            cell1.setCellValue("Remark");
            sheet1.setColumnWidth(c1, 256*30);
            cell1.setCellStyle(headstyle);
            c1++;
           
            int col1 = 0;
            int rownum1 = 1;
         
            for(int i = 0; i < total1;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) onboard_hist.get(i);
                if(info != null)
                {                    
                    col1 = 0 ;
                    row1 = sheet1.createRow(rownum1++); 
                  
                    cell1 = row1.createCell(col1++);
                    cell1.setCellValue(info.getClientName()!= null ? info.getClientName(): "");
                    cell1.setCellStyle(contentstyle); 
                    
                    cell1 = row1.createCell(col1++);
                    cell1.setCellValue(info.getPositionname()!= null ? info.getPositionname(): "");
                    cell1.setCellStyle(contentstyle);
                    
                    cell1 = row1.createCell(col1++);
                    cell1.setCellValue(info.getUsername()!= null ? info.getUsername(): "");
                    cell1.setCellStyle(contentstyle);
                    
                    cell1 = row1.createCell(col1++);
                    cell1.setCellValue(info.getDate()!= null ? info.getDate(): "");
                    cell1.setCellStyle(contentstyle);
                    
                    cell1 = row1.createCell(col1++);
                    cell1.setCellValue(info.getRemarks()!= null ? info.getRemarks(): "");
                    cell1.setCellStyle(contentstyle);
                
                }
                info = null;
            }
            onboard_hist.clear();
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
        else if (extype!= null && extype.equals("17")) {
            extype = "";
            int candidateId = frm.getCandidateId();
            ArrayList list = talentpool.getMobilizationhistoryByCandidateId(candidateId);
        try
        { 
            int total = list.size();  
            String fileName = "TalentPool_Mobilization_Report";
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
            
            HSSFCellStyle contentstylegreen = wb.createCellStyle();
            contentstylegreen.setFont(contentFont);
            contentstylegreen.setFillForegroundColor(HSSFColor.BLUE.index);
            contentstylegreen.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            
            int rowval = 0;
            int c = 0;
            
            row = sheet.createRow(rowval); 
            
            cell = row.createCell(c);
            cell.setCellValue("Arrival Date");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Client - Asset");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Position - Rank");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Mobilization Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < total;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++); 
                  
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getArrivalDate()!= null ? info.getArrivalDate(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getClientName()!= null ? info.getClientName(): "");
                    cell.setCellStyle(contentstyle);
                                        
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPosition()!= null ? info.getPosition(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getMobilizeStatus() == 9  ? "Complete": "Pending");
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
        else if (extype!= null && extype.equals("18")) {
            extype = "";
            int candidateId = frm.getCandidateId();
            ArrayList list = talentpool.getRotationhistoryByCandidateId(candidateId);
        try
        { 
            int total = list.size();  
            String fileName = "TalentPool_Rotation_Report";
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
            
            HSSFCellStyle contentstylegreen = wb.createCellStyle();
            contentstylegreen.setFont(contentFont);
            contentstylegreen.setFillForegroundColor(HSSFColor.BLUE.index);
            contentstylegreen.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            
            int rowval = 0;
            int c = 0;
            
            row = sheet.createRow(rowval);             

            cell = row.createCell(c);
            cell.setCellValue("Client - Asset");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                        
            cell = row.createCell(c);
            cell.setCellValue("Position - Rank");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Sign On Date");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Sign Off Date");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Rotation Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            int col = 0;
            int rownum = 1;
         
            for(int i = 0; i < total;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                if(info != null)
                {                    
                    col = 0 ;
                    row = sheet.createRow(rownum++); 
                  
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getClientName()!= null ? info.getClientName(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPosition()!= null ? info.getPosition(): "");
                    cell.setCellStyle(contentstyle);
                                        
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getSignon()!= null ? info.getSignon(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getSignoff()!= null ? info.getSignoff(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getRotationStatusValue()!= null ? info.getRotationStatusValue(): "");
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
        else if (extype!= null && extype.equals("19")) 
        {
            extype = "";
            int candidateId = frm.getCandidateId();
            ArrayList list = talentpool.getNotificationhistory(candidateId);
            try
            { 
                int total = list.size();  
                String fileName = "TalentPool_Notification_Report";
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
                
                HSSFCellStyle contentstyle = wb.createCellStyle();
                contentstyle.setFont(contentFont);
                contentstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                
                int rowval = 0;
                int c = 0;

                row = sheet.createRow(rowval);

                cell = row.createCell(c);
                cell.setCellValue("Date");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Document Type");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("User Name");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Filename");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;

                int col = 0;
                int rownum = 1;

                for(int i = 0; i < total;i++)
                {
                    TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                    if(info != null)
                    {                    
                        col = 0 ;
                        row = sheet.createRow(rownum++); 

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getDate()!= null ? info.getDate(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getDocumentname()!= null ? info.getDocumentname(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getUserName()!= null ? info.getUserName(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getFilename()!= null ? info.getFilename(): "");
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
        else if (extype!= null && extype.equals("20")) 
        {
            extype = "";
            int candidateId = frm.getCandidateId();
            ArrayList list = talentpool.getTransferTerminationhistory(candidateId);
            try
            { 
                int total = list.size();  
                String fileName = "TalentPool_Transfer/Close_Assignment_Report";
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
                
                HSSFCellStyle contentstyle = wb.createCellStyle();
                contentstyle.setFont(contentFont);
                contentstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                
                int rowval = 0;
                int c = 0;

                row = sheet.createRow(rowval);

                cell = row.createCell(c);
                cell.setCellValue("Type");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Date");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("User Name");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Remark");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;
                
                cell = row.createCell(c);
                cell.setCellValue("Asset Name");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;

                int col = 0;
                int rownum = 1;

                for(int i = 0; i < total;i++)
                {
                    TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                    if(info != null)
                    {                    
                        col = 0 ;
                        row = sheet.createRow(rownum++); 

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getType() == 1 ? "Transfer" : "Close Assignment");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getDate()!= null ? info.getDate(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getUserName()!= null ? info.getUserName(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getRemarks()!= null ? info.getRemarks(): "");
                        cell.setCellStyle(contentstyle);
                        
                        if(info.getType() == 1)
                        {
                            cell = row.createCell(col++);
                            cell.setCellValue(info.getAssetName()!= null ? info.getAssetName(): "");
                            cell.setCellStyle(contentstyle);
                        }
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
        else if (extype!= null && extype.equals("21")) 
        {
            extype = "";            
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int statusId = frm.getStatusIndex();
            frm.setStatusIndex(statusId);
            ArrayList list = talentpool.getFeedbackhistoryExcel(candidateId, statusId);
            try
            { 
                int total = list.size();  
                String fileName = "TalentPool_Wellness_Feedbcak_Report";
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
                
                HSSFCellStyle contentstyle = wb.createCellStyle();
                contentstyle.setFont(contentFont);
                contentstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                
                int rowval = 0;
                int c = 0;

                row = sheet.createRow(rowval);

                cell = row.createCell(c);
                cell.setCellValue("Client - Asset");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Position - Rank");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Feedback Name");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Sent On");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;
                
                cell = row.createCell(c);
                cell.setCellValue("Filled On");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;

                int col = 0;
                int rownum = 1;

                for(int i = 0; i < total;i++)
                {
                    TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                    if(info != null)
                    {                    
                        col = 0 ;
                        row = sheet.createRow(rownum++);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getClientName()!= null ? info.getClientName(): "");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getPosition()!= null ? info.getPosition(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getFeedback()!= null ? info.getFeedback(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getSenton()!= null ? info.getSenton(): "");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getStatusValue()!= null ? info.getStatusValue(): "");
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
        else if (extype!= null && extype.equals("22")) 
        {
            extype = "";            
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int clientId = frm.getClientIdcom();
            int clientassetId = frm.getAssetIdcom();
            int pdeptId = frm.getPdeptId();   
            int positionId = frm.getPositionIdcom();
            ArrayList list = ddl.getCompetencyTrackerList(candidateId, 0, 0, clientId, clientassetId, pdeptId, positionId);
            try
            { 
                int total = list.size();  
                String fileName = "TalentPool_Competency_Report";
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
                
                HSSFCellStyle contentstyle = wb.createCellStyle();
                contentstyle.setFont(contentFont);
                contentstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                
                int rowval = 0;
                int c = 0;

                row = sheet.createRow(rowval);

                cell = row.createCell(c);
                cell.setCellValue("Role Competency");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Priority");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Completed On");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Valid Till");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;
                
                cell = row.createCell(c);
                cell.setCellValue("Assessor");
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
                cell.setCellValue("Department");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;
                
                cell = row.createCell(c);
                cell.setCellValue("Position | Rank");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;
                
                cell = row.createCell(c);
                cell.setCellValue("Status");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;

                int col = 0;
                int rownum = 1;

                for(int i = 0; i < total;i++)
                {
                    TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                    if(info != null)
                    {                    
                        col = 0 ;
                        row = sheet.createRow(rownum++);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getRole()!= null ? info.getRole(): "");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getPriority()!= null ? info.getPriority(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getCompletedon()!= null ? info.getCompletedon(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getValidtill()!= null ? info.getValidtill(): "");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getAssessorName()!= null ? info.getAssessorName(): "");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getClientName()!= null ? info.getClientName(): "");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getClientAsset()!= null ? info.getClientAsset(): "");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getDepartment()!= null ? info.getDepartment(): "");
                        cell.setCellStyle(contentstyle);
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getPosition()!= null ? info.getPosition(): "");
                        cell.setCellStyle(contentstyle);                        
                        
                        cell = row.createCell(col++);
                        cell.setCellValue(info.getStatusValue()!= null ? info.getStatusValue(): "");
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
        else if (extype!= null && extype.equals("23")) 
        {
            extype = "";            
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
            try
            { 
                int total = list.size();  
                String fileName = "Doctype_Report";
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
                cell.setCellValue("Expiry Date");
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
    //for offshore update excel
    else if (extype!= null && extype.equals("24")) 
    {
        extype = "";            
        int candidateId = frm.getCandidateId();
        frm.setCandidateId(candidateId);
        ArrayList list = ddl.getOffshoreExcel(candidateId);
        try
        { 
            int total = list.size();  
            String fileName = "Remarks_Report";
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

            HSSFCellStyle contentstyle = wb.createCellStyle();
            contentstyle.setFont(contentFont);
            contentstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

            int rowval = 0;
            int c = 0;
            row = sheet.createRow(rowval);

            cell = row.createCell(c);
            cell.setCellValue("#");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Date");
            sheet.setColumnWidth(c, 256*30);
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
            cell.setCellValue("Remark Type");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Remarks");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            int col = 0;
            int rownum = 1;

            for(int i = 0; i < total;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                if(info != null)
                {
                    col = 0 ;
                    row = sheet.createRow(rownum++);

                    cell = row.createCell(col++);
                    cell.setCellValue((i+1));
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getDate()!= null ? info.getDate(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getClientName()!= null ? info.getClientName(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAssetName()!= null ? info.getAssetName(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getTypeName()!= null ? info.getTypeName(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getRemarks()!= null ? info.getRemarks(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(ddl.getStatusById(info.getStatus()));
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
    //for Nominee excel
    else if (extype!= null && extype.equals("25")) 
    {
        extype = "";            
        int candidateId = frm.getCandidateId();
        frm.setCandidateId(candidateId);
        ArrayList list = ddl.getNomineeExcel(candidateId);
        try
        { 
            int total = list.size();  
            String fileName = "Nominee_Report";
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

            HSSFCellStyle contentstyle = wb.createCellStyle();
            contentstyle.setFont(contentFont);
            contentstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

            int rowval = 0;
            int c = 0;
            row = sheet.createRow(rowval);

            cell = row.createCell(c);
            cell.setCellValue("#");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Nominee Name");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Nominee Contact Number");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Nominee Relation");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Nominee Address");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Nominee Age");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Percentage Of Distribution");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;

            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet.setColumnWidth(c, 256 * 30);
            cell.setCellStyle(headstyle);
            c++;

            int col = 0;
            int rownum = 1;

            for(int i = 0; i < total;i++)
            {
                TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                if(info != null)
                {
                    col = 0 ;
                    row = sheet.createRow(rownum++);

                    cell = row.createCell(col++);
                    cell.setCellValue((i+1));
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getNomineeName()!= null ? info.getNomineeName(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getNomineeContactno()!= null ? info.getNomineeContactno(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getNomineeRelation()!= null ? info.getNomineeRelation(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAddress()!= null ? info.getAddress(): "");
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAge());
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPercentage());
                    cell.setCellStyle(contentstyle);

                    cell = row.createCell(col++);
                    cell.setCellValue(ddl.getStatusById(info.getStatus()));
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
        
        //for PPE excel
        else if (extype!= null && extype.equals("26")) 
        {
            extype = "";            
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = ddl.getPpeExcel(candidateId);
            try
            { 
                int total = list.size();  
                String fileName = "PPE_Details_Report";
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

                HSSFCellStyle contentstyle = wb.createCellStyle();
                contentstyle.setFont(contentFont);
                contentstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

                int rowval = 0;
                int c = 0;
                row = sheet.createRow(rowval);

                cell = row.createCell(c);
                cell.setCellValue("#");
                sheet.setColumnWidth(c, 256*10);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Item Name");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Size");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Date Of Entry");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Entered By");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Status");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;

                int col = 0;
                int rownum = 1;

                for(int i = 0; i < total;i++)
                {
                    TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                    if(info != null)
                    {
                        col = 0 ;
                        row = sheet.createRow(rownum++);

                        cell = row.createCell(col++);
                        cell.setCellValue((i+1));
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getPpetype()!= null ? info.getPpetype(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getRemarks()!= null ? info.getRemarks(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getDate()!= null ? info.getDate(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getUsername()!= null ? info.getUsername(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(ddl.getStatusById(info.getStatus()));
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
            //For Contract tab
        else if (extype!= null && extype.equals("27")) 
        {
            extype = "";
            int candidateId = frm.getCandidateId();
            int contractdetailId = frm.getContractdetailId();
            frm.setContractdetailId(contractdetailId);
            int clientId = frm.getClientIdContract();
            int assetId = frm.getAssetIdContract();
            int status = frm.getContractStatus();
            String fromDate = frm.getFromDate();
            String toDate = frm.getToDate();
            ArrayList clist = ddl.getContractListing(candidateId, clientId, assetId, status, fromDate, toDate, 0, 0);
            int ctotal = clist.size();
            try
            {
                String fileName = "Contract_Report";
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

                HSSFCellStyle contentstyle = wb.createCellStyle();
                contentstyle.setFont(contentFont);
                contentstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

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
                sheet.setColumnWidth(c, 256*30);
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
                cell.setCellValue("Type");
                sheet.setColumnWidth(c, 256*30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Contract Status");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("From");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("To");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Crew Approval");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Company Approval");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;

                cell = row.createCell(c);
                cell.setCellValue("Status");
                sheet.setColumnWidth(c, 256 * 30);
                cell.setCellStyle(headstyle);
                c++;

                int col = 0;
                int rownum = 1;

                for(int i = 0; i < ctotal;i++)
                {
                    TalentpoolInfo info = (TalentpoolInfo) clist.get(i);
                    if(info != null)
                    {
                        col = 0 ;
                        row = sheet.createRow(rownum++);

                        cell = row.createCell(col++);
                        cell.setCellValue((i+1));
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getPositionname()!= null ? info.getPositionname(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getClientName()!= null ? info.getClientName(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getAssetName()!= null ? info.getAssetName(): "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getType() == 1 ? "Main" : "Repeated");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getStatusValue() != null ? info.getStatusValue() : "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getFromDate() != null ? info.getFromDate() : "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getToDate() != null ? info.getToDate() : "");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getApproval1() == 1 ? "Yes" : "No");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(info.getApproval2() == 1 ? "Yes" : "No");
                        cell.setCellStyle(contentstyle);

                        cell = row.createCell(col++);
                        cell.setCellValue(ddl.getStatusById(info.getStatus()));
                        cell.setCellStyle(contentstyle);
                    }
                    info = null;
                }
                clist.clear();
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
            //For Appraisal
            else if (extype!= null && extype.equals("28")) 
            {
                extype = "";
                int candidateId = frm.getCandidateId();
                ArrayList clist = ddl.getAppraisalListing(candidateId);
                int ctotal = clist.size();
                try
                {
                    String fileName = "Appraisal_Report";
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

                    HSSFCellStyle contentstyle = wb.createCellStyle();
                    contentstyle.setFont(contentFont);
                    contentstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

                    int rowval = 0;
                    int c = 0;
                    row = sheet.createRow(rowval);

                    cell = row.createCell(c);
                    cell.setCellValue("#");
                    sheet.setColumnWidth(c, 256*10);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("New Position");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Position Earlier");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;
                    
                    cell = row.createCell(c);
                    cell.setCellValue("Salary Effective from Date");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;
                    
                    cell = row.createCell(c);
                    cell.setCellValue("Remarks");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Status");
                    sheet.setColumnWidth(c, 256 * 30);
                    cell.setCellStyle(headstyle);
                    c++;

                    int col = 0;
                    int rownum = 1;

                    for(int i = 0; i < ctotal;i++)
                    {
                        TalentpoolInfo info = (TalentpoolInfo) clist.get(i);
                        if(info != null)
                        {
                            col = 0 ;
                            row = sheet.createRow(rownum++);

                            cell = row.createCell(col++);
                            cell.setCellValue((i+1));
                            cell.setCellStyle(contentstyle);

                            cell = row.createCell(col++);
                            cell.setCellValue(info.getPosition2()!= null ? info.getPosition2(): "");
                            cell.setCellStyle(contentstyle);

                            cell = row.createCell(col++);
                            cell.setCellValue(info.getPosition()!= null ? info.getPosition(): "");
                            cell.setCellStyle(contentstyle);
                            
                            cell = row.createCell(col++);
                            cell.setCellValue(info.getDate()!= null ? info.getDate(): "");
                            cell.setCellStyle(contentstyle);
                            
                            cell = row.createCell(col++);
                            cell.setCellValue(info.getRemarks()!= null ? info.getRemarks() : "");
                            cell.setCellStyle(contentstyle);
                            
                            cell = row.createCell(col++);
                            cell.setCellValue(ddl.getStatusById(info.getStatus()));
                            cell.setCellStyle(contentstyle);
                        }
                        info = null;
                    }
                    clist.clear();
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
            //For Dayrate 
            else if (extype!= null && extype.equals("29")) 
            {
                extype = "";
                int candidateId = frm.getCandidateId();
                ArrayList clist = ddl.getDayrateListing(candidateId);
                int ctotal = clist.size();
                try
                {
                    String fileName = "Dayrate_Report";
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

                    HSSFCellStyle contentstyle = wb.createCellStyle();
                    contentstyle.setFont(contentFont);
                    contentstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

                    int rowval = 0;
                    int c = 0;
                    row = sheet.createRow(rowval);

                    cell = row.createCell(c);
                    cell.setCellValue("#");
                    sheet.setColumnWidth(c, 256*10);
                    cell.setCellStyle(headstyle);
                    c++;

                    cell = row.createCell(c);
                    cell.setCellValue("Position | Rank");
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
                    
                    cell = row.createCell(c);
                    cell.setCellValue("Day Rate");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;
                    
                    cell = row.createCell(c);
                    cell.setCellValue("Overtime");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;
                    
                    cell = row.createCell(c);
                    cell.setCellValue("Allowance");
                    sheet.setColumnWidth(c, 256*30);
                    cell.setCellStyle(headstyle);
                    c++;

                    int col = 0;
                    int rownum = 1;

                    for(int i = 0; i < ctotal;i++)
                    {
                        TalentpoolInfo info = (TalentpoolInfo) clist.get(i);
                        if(info != null)
                        {
                            col = 0 ;
                            row = sheet.createRow(rownum++);

                            cell = row.createCell(col++);
                            cell.setCellValue((i+1));
                            cell.setCellStyle(contentstyle);

                            cell = row.createCell(col++);
                            cell.setCellValue(info.getPosition()!= null ? info.getPosition(): "");
                            cell.setCellStyle(contentstyle);

                            cell = row.createCell(col++);
                            cell.setCellValue(info.getFromDate()!= null ? info.getFromDate(): "");
                            cell.setCellStyle(contentstyle);
                            
                            cell = row.createCell(col++);
                            cell.setCellValue(info.getToDate()!= null ? info.getToDate(): "");
                            cell.setCellStyle(contentstyle);
                            
                            cell = row.createCell(col++);
                            cell.setCellValue(info.getRate1());
                            cell.setCellStyle(contentstyle);
                            
                            cell = row.createCell(col++);
                            cell.setCellValue(info.getRate2());
                            cell.setCellStyle(contentstyle);
                            
                            cell = row.createCell(col++);
                            cell.setCellValue(info.getRate3());
                            cell.setCellStyle(contentstyle);
                            
                        }
                        info = null;
                    }
                    clist.clear();
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
