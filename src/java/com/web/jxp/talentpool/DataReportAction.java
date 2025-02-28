/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.jxp.talentpool;

import com.web.jxp.user.UserInfo;
import org.apache.struts.action.Action;
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Suraj
 */
public class DataReportAction extends Action {
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
            String fileName = "Data Report";
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
            
            
            float overallavarage =0;
            //for reducing line of code using arraylist 
            ArrayList<String> headerName = new ArrayList<String>();
            String[] strs = {
                       "Client",
                       "Asset",
                       "Employee Id",
                       "Name",
                       "Position-Rank",
                       "Primary Contact Number",
                       "Emergency Contact Number",
                       "Secondary Contact Number",
                       "Additional Contact",
                       "Email Address",
                       "Date Of Birth",
                       "Place of Birth",
                       "Gender",
                       "Marital Status",
                       "Next Of Kin",
                       "Relation",
                       "Candidate Country",
                       "Candidate City",
                       "Permanent Address Line 1",
                       "Permanent Address Line 2",
                       "Permanent Address Line 3",
                       "Communication Address Line 1",
                       "Communication Address Line 2",
                       "Communication Address Line 3",
                       "Nationality",
                       "Preferred Department",
                       "Currency",
                       "Expected Salary",
                       "Crew Rate ",
                        "Overtime",
                       "Language",
                       "Health",
                       "Vaccination",
                       "Experience",
                       "Education",
                       "Certifications",
                       "Bank Details",
                       "Documents",
                       "PersonalDataAdded",
                       "OtherDataAdded",	
                       "TotalData"};
               //adding col name in list
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
           
            
                int col = 0;
                int rownum = 1;         
                for(int i = 0; i < total;i++)
                {
                TalentpoolInfo info = (TalentpoolInfo) list.get(i); 
                if(info != null)
                {      
                    float personalDataAvr=0;
                    float totalDataArv=0;
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
                    
                    //For Client Imp : we can also add blank data inside alse block( cell.setCellType(cell.CELL_TYPE_BLANK);)
                    cell = row.createCell(col++);
                    if(info.getClientAsset() != null && !info.getClientAsset().equals("")){
                    cell.setCellValue(info.getClientAsset());
                    ++personalDataAvr;
                    totalDataArv++;
                    }
                    else
                    {
                    cell.setCellValue(info.getClientAsset());
                    cell.setCellStyle(contentstyle);
                    }
                    
                    //for Asset
                     cell = row.createCell(col++);
                     if(info.getAssettype() != null && !info.getAssettype().equals(""))
                     {
                     totalDataArv++;
                     ++personalDataAvr;
                     cell.setCellValue(info.getAssettype());
                     }
                     else
                     {
                     cell.setCellValue(info.getAssettype());
                     cell.setCellStyle(contentstyle);
                     }
                    
                    // for Employee Id
                    cell = row.createCell(col++);
                    if(info.getEmployeeId() != null && !info.getEmployeeId().equals(""))
                    {
                    ++personalDataAvr;
                    totalDataArv++;
                    cell.setCellValue(info.getEmployeeId());
                    }
                    else
                    {
                    cell.setCellValue(info.getEmployeeId());
                    cell.setCellStyle(contentstyle);
                    }
                    
                    //  for Name
                    
                    cell = row.createCell(col++);
                    if(info.getName()!= null && !info.getName().equals(""))
                    {
                    ++personalDataAvr;
                    totalDataArv++;
                    cell.setCellValue(info.getName());
                    }
                    else
                    {
                    cell.setCellValue(info.getName());
                    cell.setCellStyle(contentstyle);
                    }
                   
                    
                   //  for Position-Rank,
                    cell = row.createCell(col++);
                    if(info.getPosition()!= null && ! info.getPosition().equals(""))
                    {
                     ++personalDataAvr;
                     totalDataArv++;
                     cell.setCellValue(info.getPosition());
                    }
                    else
                    {
                    cell.setCellValue(info.getPosition());
                    cell.setCellStyle(contentstyle);
                    }
                    
                    //for Primary Contact Number
                    cell = row.createCell(col++);
                     if(info.getContactno1() != null && !info.getContactno1().equals(""))
                     {
                        ++personalDataAvr;
                        totalDataArv++;
                        cell.setCellValue(info.getContactno1());
                     }
                     else
                     {
                        cell.setCellValue(info.getContactno1());
                        cell.setCellStyle(contentstyle);
                     }

                    //for Emergency Contact Number
                    
                     cell = row.createCell(col++);
                     if(info.getContactno1() != null && !info.getContactno1().equals(""))
                     {
                        ++personalDataAvr;
                        totalDataArv++;
                        cell.setCellValue(info.getContactno1());
                     }
                     else
                     {
                       cell.setCellValue(info.getContactno1());
                       cell.setCellStyle(contentstyle);
                     }
                    
                    //For Contact Number 2
                    cell = row.createCell(col++);
                    if(info.getContactno2() != null && !info.getContactno2().equals(""))
                    {
                        ++personalDataAvr;
                        totalDataArv++;
                        cell.setCellValue(info.getContactno2());
                    }
                    else
                    {
                        cell.setCellValue(info.getContactno2());
                        cell.setCellStyle(contentstyle);
                    }
                    
                  //  For Additional contact number
                    cell = row.createCell(col++);
                    if(info.getContactno3() != null && !info.getContactno3().equals(""))
                    {
                        ++personalDataAvr;
                        totalDataArv++;
                        cell.setCellValue(info.getContactno3());
                    }
                    else
                    {
                        cell.setCellValue(info.getContactno2());
                        cell.setCellStyle(contentstyle);
                    }
                    
                    //For Email address
                    cell = row.createCell(col++);
                    if(info.getEmailId() != null && !info.getEmailId().equals(""))
                    {
                        ++personalDataAvr;
                        totalDataArv++;
                        cell.setCellValue(info.getEmailId());
                    }
                    else
                    {
                        cell.setCellValue(info.getEmailId());
                        cell.setCellStyle(contentstyle);
                    }
                    
                    //For DOB
                    cell = row.createCell(col++);
                    if(info.getDob() != null && !info.getDob().equals(""))
                    {
                        ++personalDataAvr;
                        totalDataArv++;
                        cell.setCellValue(info.getDob());
                    }
                    else
                    {
                       cell.setCellValue(info.getDob());
                        cell.setCellStyle(contentstyle);
                    }
                    
                    //For Place of birth
                    cell = row.createCell(col++);
                    if(info.getPlaceofbirth() != null && !info.getPlaceofbirth().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                         cell.setCellValue(info.getPlaceofbirth());
                    }
                    else
                    {
                       cell.setCellValue(info.getPlaceofbirth());
                        cell.setCellStyle(contentstyle);
                    }
                    
                    
                    //For Gender
                    cell = row.createCell(col++);
                    if(info.getGender() != null && !info.getGender().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                         cell.setCellValue(info.getGender());
                    }
                    else
                    {
                        cell.setCellValue(info.getGender());
                        cell.setCellStyle(contentstyle);
                    }
                    
                    
                   //for Marital Status
                    cell = row.createCell(col++);
                    if(info.getMaritialstatus() != null && !info.getMaritialstatus().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                         cell.setCellValue(info.getMaritialstatus());
                    }
                    else
                    {
                         cell.setCellValue(info.getMaritialstatus());
                         cell.setCellStyle(contentstyle);
                    }
                    
                   // For Nextofkin()
                    cell = row.createCell(col++);
                    if(info.getNextofkin() != null && !info.getNextofkin().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                         cell.setCellValue(info.getNextofkin());
                    }
                    else
                    {
                        cell.setCellValue(info.getNextofkin());
                        cell.setCellStyle(contentstyle);
                    }
                    
                    
                    //For getting relation
                    cell = row.createCell(col++);
                    if(info.getRelation() != null && !info.getRelation().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                         cell.setCellValue(info.getRelation());
                    }
                    else
                    {
                        cell.setCellValue(info.getRelation());
                        cell.setCellStyle(contentstyle);
                    }
                    
                   // For getting cuontryName
                    cell = row.createCell(col++);
                    if(info.getCountryName() != null && !info.getCountryName().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                         cell.setCellValue(info.getCountryName());
                    }
                    else
                    {
                        cell.setCellValue(info.getCountryName());
                        cell.setCellStyle(contentstyle);
                    }
                    
                    //for getting cityname
                    cell = row.createCell(col++);
                    if(info.getCity() != null && !info.getCity().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                         cell.setCellValue(info.getCity());
                    }
                    else
                    {
                        cell.setCellValue(info.getCity());
                        cell.setCellStyle(contentstyle);
                    }
                    
                    //for addressline1
                    cell = row.createCell(col++);
                    if(info.getAddress1line1() != null && !info.getAddress1line1().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                         cell.setCellValue(info.getAddress1line1());
                    }
                    else
                    {
                       cell.setCellValue(info.getAddress1line1());
                        cell.setCellStyle(contentstyle);
                    }
                    
                     //for addressline2
                    cell = row.createCell(col++);
                    if(info.getAddress1line2() != null && !info.getAddress1line2().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                         cell.setCellValue(info.getAddress1line2());
                    }
                    else
                    {
                        cell.setCellValue(info.getAddress1line2());
                        cell.setCellStyle(contentstyle);
                    }
                    
                     //for addressline3
                    cell = row.createCell(col++);
                    if(info.getAddress1line3() != null && !info.getAddress1line3().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                        cell.setCellValue(info.getAddress1line3());
                    }
                    else
                    {
                       cell.setCellValue(info.getAddress1line3());
                        cell.setCellStyle(contentstyle);
                    }
                    
                    
                    //for communication addressline1
                    cell = row.createCell(col++);
                    if(info.getAddress2line1() != null && !info.getAddress2line1().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                        cell.setCellValue(info.getAddress2line1());
                    }
                    else
                    {
                        cell.setCellValue(info.getAddress2line1());
                        cell.setCellStyle(contentstyle);}
                    
                    //for communication addressline2
                    cell = row.createCell(col++);
                    if(info.getAddress2line2() != null && !info.getAddress2line2().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                        cell.setCellValue(info.getAddress2line2());
                    }
                    else
                    {
                        cell.setCellValue(info.getAddress2line2());
                        cell.setCellStyle(contentstyle);
                    }
                    
                    
                    //for communication addressline3
                    cell = row.createCell(col++);
                    if(info.getAddress2line3() != null && !info.getAddress2line3().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                         cell.setCellValue(info.getAddress2line3());
                    }
                    else
                    {
                        cell.setCellValue(info.getAddress2line3());
                        cell.setCellStyle(contentstyle);
                    }
                    
                    //for getting nationality
                    cell = row.createCell(col++);
                    if(info.getNationality() != null && !info.getNationality().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                         cell.setCellValue(info.getNationality());
                    }
                    else
                    {
                        cell.setCellValue(info.getNationality());
                        cell.setCellStyle(contentstyle);
                    }
                    
                    //for getting department
                    cell = row.createCell(col++);
                    if(info.getDepartment() != null && !info.getDepartment().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                         cell.setCellValue(info.getDepartment());
                    }
                    else
                    {
                        cell.setCellValue(info.getDepartment());
                        cell.setCellStyle(contentstyle);
                    }
                    
                    //for getting currency details
                    cell = row.createCell(col++);
                    if(info.getCurrency() != null && !info.getCurrency().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                         cell.setCellValue(info.getCurrency());
                    }
                    else
                    {
                        cell.setCellValue(info.getCurrency());
                        cell.setCellStyle(contentstyle);
                    }
                    
                    //for getting expacted salary
                    cell = row.createCell(col++);
                    if(info.getExpectedsalary() != 0)
                    {
                        ++personalDataAvr; 
                        totalDataArv++;
                        cell.setCellValue(info.getExpectedsalary());
                    }
                    else
                    {
                        cell.setCellValue(info.getExpectedsalary());
                        cell.setCellStyle(contentstyle);  
                    }                  
               
                    //for Crew Rate 
                    cell = row.createCell(col++);
                    if(info.getRate1()!=0)
                    {
                        ++personalDataAvr;
                        totalDataArv++;
                        cell.setCellValue(info.getRate1()); 
                    }   
                    else
                    {
                       cell.setCellValue(info.getRate1());
                       cell.setCellStyle(contentstyle); 
                    }
                    //Overtime
                    cell = row.createCell(col++);
                    if(info.getRate2()!=0)
                    {
                        ++personalDataAvr;
                        totalDataArv++;
                        cell.setCellValue(info.getRate2()); 
                    }
                    else
                    {
                        cell.setCellValue(info.getRate2()); 
                        cell.setCellStyle(contentstyle);
                    }   

                    float otherDataFlg =0;//for otherdata average 
                    
                     //for "Language"
                        cell = row.createCell(col++);
                    if(info.getCt1()!=0)
                    {
                        otherDataFlg++;
                        totalDataArv++;
                        cell.setCellValue(info.getCt1());
                    }
                    else
                    {
                        cell.setCellValue(info.getCt1());
                        cell.setCellStyle(contentstyle);
                    }
                    
                   // for health
                    cell = row.createCell(col++);
                    if(info.getCt2()!=0)
                        {
                        otherDataFlg++;
                        totalDataArv++;
                        cell.setCellValue(info.getCt2());
                        }
                    else{
                        cell.setCellValue(info.getCt2());
                        cell.setCellStyle(contentstyle);
                        }
                    
                    //for Vaccination
                    cell = row.createCell(col++);
                    if(info.getCt3()!=0)
                    {
                            otherDataFlg++;
                            totalDataArv++;
                            cell.setCellValue(info.getCt3());
                    }
                    else 
                    {    
                            cell.setCellValue(info.getCt3());
                            cell.setCellStyle(contentstyle);
                    }
                    
                    // for experiance
                    cell = row.createCell(col++);
                    if(info.getCt4()!=0)
                    {
                        otherDataFlg++;
                        totalDataArv++;
                        cell.setCellValue(info.getCt4());
                    }
                    else
                    {  
                        cell.setCellValue(info.getCt4());
                        cell.setCellStyle(contentstyle);
                    }
                    
                    //for Education
                    cell = row.createCell(col++);
                    if(info.getCt5()!=0)
                    {                  
                        cell.setCellValue(info.getCt5());
                        otherDataFlg++;   
                        totalDataArv++;}
                    else
                    {
                         cell.setCellValue(info.getCt5());
                         cell.setCellStyle(contentstyle);
                    }
                    
                    //for certification
                    cell = row.createCell(col++);
                    if(info.getCt6()!=0)  
                    {                  
                        cell.setCellValue(info.getCt6());
                        otherDataFlg++; 
                        totalDataArv++;
                    }
                    else
                    {
                        cell.setCellValue(info.getCt6());
                        cell.setCellStyle(contentstyle);
                    }
                    
                    //for bank details
                    cell = row.createCell(col++);
                    if(info.getCt8()!=0) 
                    {                  
                        cell.setCellValue(info.getCt8());
                        otherDataFlg++; 
                        totalDataArv++;
                    }
                    else
                    {
                        cell.setCellValue(info.getCt8());
                        cell.setCellStyle(contentstyle);
                    }
                    
                    
                    //for Documents
                    cell = row.createCell(col++);
                    if(info.getCt9()!=0)  
                    {                  
                        cell.setCellValue(info.getCt9());
                        otherDataFlg++; 
                        totalDataArv++;
                    }
                    else
                    {
                       cell.setCellValue(info.getCt9());
                       cell.setCellStyle(contentstyle);
                    } 
                    //for otherdataavarage
                    otherDataFlg = (otherDataFlg / 8);
                    otherDataFlg =otherDataFlg * 100;
                    
                   double otherroundoffvalue = Math.ceil(otherDataFlg);
                   int otherroundoffvalueint = (int)otherroundoffvalue;
                   
                    //for personal data
                    personalDataAvr = (personalDataAvr / 30);
                    personalDataAvr = personalDataAvr * 100;
                    double personalDataAvrroundoff = Math.ceil(personalDataAvr);
                   int personalDataAvrroundoffint = (int)personalDataAvrroundoff;
                    
                    //for totaldata avarage
                    totalDataArv = (totalDataArv/38);
                    totalDataArv =(totalDataArv * 100);
                    
                     double totalDataArvroundof = Math.ceil(totalDataArv);
                     int totalDataArvroundofint = (int)totalDataArvroundof;
                   
                    overallavarage =overallavarage + totalDataArv;
                     
                    //for Personal data added
                    cell = row.createCell(col++);
                    cell.setCellValue(personalDataAvrroundoffint+"%");                    
                    cell.setCellStyle(contentstyle); 
                    
                    //for Otherdata data added
                    cell = row.createCell(col++);
                    cell.setCellValue(otherroundoffvalueint+"%");                    
                    cell.setCellStyle(contentstyle); 
                    
                    //for Total data added
                    cell = row.createCell(col++);
                    cell.setCellValue(totalDataArvroundofint+"%");                    
                    cell.setCellStyle(contentstyle);
                    
                    
                }
                info = null;
            }
               float overallavaragepercatage = (overallavarage/total);
                double overallavaragepercatageint = Math.ceil(overallavaragepercatage);
                int overallavaragepercatageintafter = (int)overallavaragepercatageint;
                
                row  = sheet.createRow(total+3);
                row.createCell(0).setCellValue("Total Crew");
                row.createCell(1).setCellValue(total);
                
                row  = sheet.createRow(total+4);
                row.createCell(0).setCellValue("Total Data Added");
                row.createCell(1).setCellValue(overallavaragepercatageintafter+"%");
    

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
