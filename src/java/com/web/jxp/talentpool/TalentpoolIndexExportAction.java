package com.web.jxp.talentpool;
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
public class TalentpoolIndexExportAction extends Action
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
            Talentpool talentpool = new Talentpool();
            TalentpoolForm frm = (TalentpoolForm)form;         
            String search = frm.getSearch() != null ? frm.getSearch() : "";
            int statusIndex = frm.getStatusIndex(); 
            int positionIndexId = frm.getPositionIndexId();
            int clientIndex = frm.getClientIndex();
            int locationIndex = frm.getLocationIndex();
            int assetIndex = frm.getAssetIndex();

            int verified = 0;
            verified = frm.getVerified();
           
            ArrayList list = talentpool.getCandidateListForExcel( search,statusIndex,positionIndexId,
                clientIndex, locationIndex, assetIndex, verified, allclient, permission, cids, assetids);
            int total = list.size();  
            String fileName = "TalentPool_Index_Report";
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
            cell.setCellValue("Employee Id");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Certified");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
          
            cell = row.createCell(c);
            cell.setCellValue("Name");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
                    
            cell = row.createCell(c);
            cell.setCellValue("Asset Type");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Position-Rank");
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
            cell.setCellValue("Asset Location");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Primary Contact Number");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Emergency Contact Number");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Secondary Contact Number");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Additional Contact");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Email Address");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Date Of Birth");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Place of Birth");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Gender");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Marital Status");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Next Of Kin");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Relation");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Candidate Country");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Candidate City");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Permanent Address Line 1");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Permanent Address Line 2");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            cell = row.createCell(c);
            cell.setCellValue("Permanent Address Line 3");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Communication Address Line 1");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Communication Address Line 2");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Communication Address Line 3");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Nationality");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Preferred Department");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Currency");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Expected Salary");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;                        
            
            cell = row.createCell(c);
            cell.setCellValue("Employee Id");
            sheet.setColumnWidth(c, 256*30);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Crew Day Rate");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Overtime");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;            
            
            cell = row.createCell(c);
            cell.setCellValue("Alerts");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Status");
            sheet.setColumnWidth(c, 256*15);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Lock");
            sheet.setColumnWidth(c, 256*15);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Language");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;       
            
            cell = row.createCell(c);
            cell.setCellValue("Health");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Vaccination");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Experience");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Education");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Certifications");
            sheet.setColumnWidth(c, 256*15);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Bank Details");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;
            
            cell = row.createCell(c);
            cell.setCellValue("Documents");
            sheet.setColumnWidth(c, 256*10);
            cell.setCellStyle(headstyle);
            c++;            
            
            cell = row.createCell(c);
            cell.setCellValue("Sign-On Date");
            sheet.setColumnWidth(c, 256*15);
            cell.setCellStyle(headstyle);
            c++;            
            
            cell = row.createCell(c);
            cell.setCellValue("Exit Date");
            sheet.setColumnWidth(c, 256*15);
            cell.setCellStyle(headstyle);
            c++;            
            
            cell = row.createCell(c);
            cell.setCellValue("Remark");
            sheet.setColumnWidth(c, 256*15);
            cell.setCellStyle(headstyle);
            c++;            
            
            cell = row.createCell(c);
            cell.setCellValue("Reason");
            sheet.setColumnWidth(c, 256*15);
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
                    String  certified = "", statusvalue = "", progress ="";
                    if(info.getVflag() == 4)
                        certified = "Certified";
                    else
                       certified = "Uncertified";
                    if(info.getClientId() > 0)
                        statusvalue = "Not Available";
                    else 
                        statusvalue = "Available";                    
                    if(info.getProgressId() > 0)
                        progress = "Locked";
                    else 
                        progress = "Unlocked";                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getEmployeeId() != null ? info.getEmployeeId() : "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue((certified));
                    cell.setCellStyle(contentstyle); 
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getName()!= null ? info.getName(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getAssettype()!= null ? info.getAssettype(): "");
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getPosition()!= null ? info.getPosition(): "");
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
                     if(info.getClientCountry() != null && !info.getClientCountry().equals(""))
                        cell.setCellValue(info.getClientCountry());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                     if(info.getContactno1() != null && !info.getContactno1().equals(""))
                        cell.setCellValue(info.getContactno1());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getEcontactno1() != null && !info.getEcontactno1().equals(""))
                        cell.setCellValue(info.getEcontactno1());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getContactno2() != null && !info.getContactno2().equals(""))
                        cell.setCellValue(info.getContactno2());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getContactno3() != null && !info.getContactno3().equals(""))
                        cell.setCellValue(info.getContactno3());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getEmailId() != null && !info.getEmailId().equals(""))
                        cell.setCellValue(info.getEmailId());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getDob() != null && !info.getDob().equals(""))
                        cell.setCellValue(info.getDob());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getPlaceofbirth() != null && !info.getPlaceofbirth().equals(""))
                        cell.setCellValue(info.getPlaceofbirth());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getGender() != null && !info.getGender().equals(""))
                        cell.setCellValue(info.getGender());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getMaritialstatus() != null && !info.getMaritialstatus().equals(""))
                        cell.setCellValue(info.getMaritialstatus());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getNextofkin() != null && !info.getNextofkin().equals(""))
                        cell.setCellValue(info.getNextofkin());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getRelation() != null && !info.getRelation().equals(""))
                        cell.setCellValue(info.getRelation());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getCountryName() != null && !info.getCountryName().equals(""))
                        cell.setCellValue(info.getCountryName());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getCity() != null && !info.getCity().equals(""))
                        cell.setCellValue(info.getCity());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getAddress1line1() != null && !info.getAddress1line1().equals(""))
                        cell.setCellValue(info.getAddress1line1());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getAddress1line2() != null && !info.getAddress1line2().equals(""))
                        cell.setCellValue(info.getAddress1line2());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getAddress1line3() != null && !info.getAddress1line3().equals(""))
                        cell.setCellValue(info.getAddress1line3());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getAddress2line1() != null && !info.getAddress2line1().equals(""))
                        cell.setCellValue(info.getAddress2line1());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getAddress2line2() != null && !info.getAddress2line2().equals(""))
                        cell.setCellValue(info.getAddress2line2());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getAddress2line3() != null && !info.getAddress2line3().equals(""))
                        cell.setCellValue(info.getAddress2line3());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getNationality() != null && !info.getNationality().equals(""))
                        cell.setCellValue(info.getNationality());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getDepartment() != null && !info.getDepartment().equals(""))
                        cell.setCellValue(info.getDepartment());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    if(info.getCurrency() != null && !info.getCurrency().equals(""))
                        cell.setCellValue(info.getCurrency());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getExpectedsalary());
                    cell.setCellStyle(contentstyle);                    
                    
                    cell = row.createCell(col++);
                    if(info.getEmployeeId() != null && !info.getEmployeeId().equals(""))
                        cell.setCellValue(info.getEmployeeId());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getRate1());                    
                    cell.setCellStyle(contentstyle);                    
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getRate2());                    
                    cell.setCellStyle(contentstyle);    
                    
                    cell = row.createCell(col++);
                    cell.setCellValue((talentpool.changeNum(info.getAlertCount(),2)));                    
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(statusvalue);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(progress);
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCt1());                    
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCt2());                    
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCt3());                    
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCt4());                    
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);                    
                    cell.setCellValue(info.getCt5());                    
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCt6());                    
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCt8());                    
                    cell.setCellStyle(contentstyle);
                    
                    cell = row.createCell(col++);
                    cell.setCellValue(info.getCt9());                    
                    cell.setCellStyle(contentstyle);                   
                                   
                    cell = row.createCell(col++);
                    if(info.getJoiningDate() != null && !info.getJoiningDate().equals(""))
                        cell.setCellValue(info.getJoiningDate());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);               
                    cell.setCellStyle(contentstyle);                   
                                   
                    cell = row.createCell(col++);
                    if(info.getEndDate() != null && !info.getEndDate().equals(""))
                        cell.setCellValue(info.getEndDate());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);                   
                    cell.setCellStyle(contentstyle);                   
                                   
                    cell = row.createCell(col++);
                    if(info.getRemarks() != null && !info.getRemarks().equals(""))
                        cell.setCellValue(info.getRemarks());
                    else
                        cell.setCellType(cell.CELL_TYPE_BLANK);                    
                    cell.setCellStyle(contentstyle);                   
                                   
                    cell = row.createCell(col++);
                    if(info.getReason() != null && !info.getReason().equals(""))
                        cell.setCellValue(info.getReason());
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
        catch (Exception e)
        {   
            e.printStackTrace();
        }
        return null;
    }  
}