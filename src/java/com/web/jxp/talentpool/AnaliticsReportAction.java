/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.jxp.talentpool;

import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
import static com.web.jxp.base.Base.decipher;
import com.web.jxp.user.UserInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

/**
 *
 * @author Suraj
 */
public class AnaliticsReportAction extends Action {
    AnalyticsReportadvFun analytics = new AnalyticsReportadvFun(); 
    Base base = new Base();
    int overallcrew =0;
         Connection conn = null;
         PreparedStatement pstmt = null;
         ResultSet rs = null;
    @Override
    @SuppressWarnings("static-access")
    public ActionForward execute (ActionMapping mapping, ActionForm form,  HttpServletRequest request, HttpServletResponse response)   throws Exception 
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
            
            Talentpool talentpool = new Talentpool();
            TalentpoolForm frm = (TalentpoolForm)form;         
           
     
            String fileName = "Analytics_Report";
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
            
            ArrayList<String> headerName = new ArrayList<String>();
            String[] strs = {"Client","Asset","Total Crew","Total Crew Rotations","Profile Completed"};
            
                for(int i =  0; i < strs.length; i++)
                {
                  headerName.add(strs[i]);      
                }
               
                for (int i = 0; i < headerName.size(); i++)
                {
                cell = row.createCell(c);
                cell.setCellValue(headerName.get(i));
                sheet.setColumnWidth(c, 256*10);
                cell.setCellStyle(headstyle);
                c++;
                }
                
                
//                int rownum = 1;
               int rowsize =1;
               int totalcrewsize=0;
               int totalroationsize=0;
               int overallpfdata = 0;
               ArrayList<String> sname = analytics.gettingclientname();
               for (int i = 0; i < sname.size() ; i++)
               {
                String clientName = sname.get(i);
                int clientId = analytics.getclientid(clientName);
                ArrayList<String> asssetName = analytics.getAssetList(clientId);
                for (int j = 0; j < asssetName.size() ; j++)
                {   
                    String assetName = asssetName.get(j);
                    int assetID = analytics.getassetid(clientId,assetName); 
                    int totalCrew = analytics.gettotalcrewcount(clientId,assetID);
                    int rotations = analytics.getrotations(clientId,assetID);
                    int pfdata = analytics.getCandidateListForExcel(clientId,assetID);
                     row = sheet.createRow(rowsize);
                     cell = row.createCell(0);
                     cell.setCellValue(clientName);
                     cell = row.createCell(1);
                     cell.setCellValue(assetName);
                     cell = row.createCell(2);
                     cell.setCellValue(totalCrew);
                     totalcrewsize=totalcrewsize+totalCrew;
                     cell = row.createCell(3);
                     cell.setCellValue(rotations);
                     totalroationsize=totalroationsize+rotations;
                     cell = row.createCell(4);
                     overallpfdata = overallpfdata+pfdata;
                     cell.setCellValue(pfdata+"%");
                     rowsize++;
                }
                
              }
                     float overallavaragepercatage = (overallpfdata/rowsize);
                     double overallavaragepercatageint = Math. ceil(overallavaragepercatage);
                     int overallavaragepercatageintafter = (int)overallavaragepercatageint;
                   
                    row  = sheet.createRow(rowsize+2);
                    row.createCell(0).setCellValue("Available for Employment in Talent Pool");
                    row.createCell(1).setCellValue("");
                   int availableStatusData = analytics.getStatusAvailbledata();
                    row.createCell(2).setCellValue(availableStatusData);
                    row.createCell(3).setCellValue("-"); 
                    row.createCell(4).setCellValue(overallavaragepercatageintafter+"%");
                    response.setContentType("application/vnd.ms-excel"); 
                    response.setHeader("Content-Disposition", "attachment; filename = \""+fileName+".xls\"");
                    try (ServletOutputStream os = response.getOutputStream()) 
                    {
                    wb.write(os);
                    os.flush();
                    }
           
        return null;
    }
    
}


    
 
