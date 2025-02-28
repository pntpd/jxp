package com.web.jxp.timesheet;

import com.web.jxp.base.Base;
import com.web.jxp.base.StatsInfo;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import static com.web.jxp.common.Common.*;
import com.web.jxp.base.Mail;
import com.web.jxp.base.Validate;
import com.web.jxp.user.UserInfo;
import java.util.Collection;
import java.util.Date;
import java.util.Stack;
import org.apache.struts.upload.FormFile;

public class TimesheetAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimesheetForm frm = (TimesheetForm) form;
        Timesheet timesheet = new Timesheet();
        Base base = new Base();
        Validate vobj = new Validate();
        CreateExcel createExcel = new CreateExcel();
        int count = timesheet.getCount();
        int type = frm.getTypevalue();        
        int statusIndex = frm.getStatusIndex();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        String fromDateIndex = frm.getFromDateIndex()!= null ? frm.getFromDateIndex(): "";
        String toDateIndex = frm.getToDateIndex()!= null ? frm.getToDateIndex(): "";
        int uId = 0, allclient = 0;
        String permission = "N", cids = "", assetids = "";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
                cids = uInfo.getCids();
                allclient = uInfo.getAllclient();
                assetids = uInfo.getAssetids();
            }
        }
        Collection clients = timesheet.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);

        Collection assets = timesheet.getClientAsset(clientIdIndex, assetids, allclient, permission);
        frm.setAssets(assets);

        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);        
        
        Collection years = timesheet.getYears();
        frm.setYears(years);
        int yearId = frm.getYearId();
        frm.setYearId(yearId);
        int month = frm.getMonth();
        frm.setMonth(month);
        frm.setCurrentDate(base.currDate3());
        int check_user = timesheet.checkUserSession(request, 76, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = timesheet.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Timesheet Management Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        
        }
        if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            print(this,"DoView block");
            frm.setDoView("no");
            int assetId = frm.getAssetId();
            frm.setAssetId(assetId);
            TimesheetInfo info = timesheet.getBasicDetail(assetId);
            int cc = timesheet.getTimesheetCount(assetId);
            ArrayList tlist = timesheet.getTimesheetList(assetId, search, statusIndex, month, yearId, 0, count, fromDateIndex, toDateIndex);
            
            int cnt = 0;
            if (tlist.size() > 0) 
            {
                TimesheetInfo cinfo = (TimesheetInfo) tlist.get(tlist.size() - 1);
                cnt = cinfo.getTimesheetId();
                tlist.remove(tlist.size() - 1);
            }
            request.getSession().setAttribute("T_LIST", tlist);
            request.getSession().setAttribute("COUNT_TSLIST", cnt + "");
            request.getSession().setAttribute("NEXTTS", "0");
            request.getSession().setAttribute("NEXTTSVALUE", "1");
            request.getSession().setAttribute("BASICINFO", info);
            request.getSession().setAttribute("TIMESHEET", "" + cc);
            return mapping.findForward("view_timesheet");
        }
        else if(frm.getDoCreateSheet()!= null && frm.getDoCreateSheet().equals("yes"))
        {
            print(this, "doCreateSheet block");
            frm.setDoCreateSheet("no");            
            int assetId = frm.getAssetId();
            frm.setAssetId(assetId);
            int timesheetId = frm.getTimesheetId();
            frm.setTimesheetId(timesheetId);
            String fromDate = frm.getFromDate();
            String toDate = frm.getToDate();
            int currencyId = frm.getCurrencyId();
            frm.setCurrencyId(currencyId); 
            int repeatId =0;            
            int tid = timesheet.createSheet(timesheetId, assetId, currencyId, fromDate, toDate, uId);
            repeatId = timesheet.checkPendingData(assetId, fromDate, toDate); 
            if(repeatId> 0)
            {
                timesheet.updateRepeatId(tid, repeatId, uId);
            }                        
            ArrayList tlist = timesheet.getTimesheetList(assetId, search, statusIndex, month, yearId, 0, count, fromDateIndex, toDateIndex);
            int cc = timesheet.getTimesheetCount(assetId);
            int cnt = 0;
            if (tlist.size() > 0) 
            {
                TimesheetInfo cinfo = (TimesheetInfo) tlist.get(tlist.size() - 1);
                cnt = cinfo.getTimesheetId();
                tlist.remove(tlist.size() - 1);
            }
            request.getSession().setAttribute("T_LIST", tlist);
            request.getSession().setAttribute("COUNT_TSLIST", cnt + "");
            request.getSession().setAttribute("NEXTTS", "0");
            request.getSession().setAttribute("NEXTTSVALUE", "1");
            request.getSession().setAttribute("TIMESHEET", "" + cc);
            return mapping.findForward("view_timesheet");
        }        
        else if(frm.getDoSearch()!= null && frm.getDoSearch().equals("yes"))
        {
            print(this, "doSearch block");
            frm.setDoSearch("no");
            int assetId = frm.getAssetId();
            frm.setAssetId(assetId);        
            TimesheetInfo info = timesheet.getBasicDetail(assetId);
            ArrayList tlist = timesheet.getTimesheetList(assetId, search, statusIndex, month, yearId, 0, count, fromDateIndex, toDateIndex);
            int cnt = 0;
            if (tlist.size() > 0) {
                TimesheetInfo cinfo = (TimesheetInfo) tlist.get(tlist.size() - 1);
                cnt = cinfo.getTimesheetId();
                tlist.remove(tlist.size() - 1);
            }            
            request.getSession().setAttribute("T_LIST", tlist);
            request.getSession().setAttribute("COUNT_TSLIST", cnt + "");
            request.getSession().setAttribute("NEXTTS", "0");
            request.getSession().setAttribute("NEXTTSVALUE", "1");
            request.getSession().setAttribute("BASICINFO", info); 
            return mapping.findForward("view_timesheet");
        }      
        else if(frm.getDoSave()!= null && frm.getDoSave().equals("yes"))
        {
            print(this, "DoSave block");
            frm.setDoSave("no");
            int assetId = frm.getAssetId();
            frm.setAssetId(assetId);
            int timesheetId = frm.getTimesheetId();
            frm.setTimesheetId(timesheetId);
            int revisionId = frm.getRevisionId();
            String remarks = vobj.replacedesc(frm.getRemarks());            
            TimesheetInfo rinfo = new TimesheetInfo(timesheetId, revisionId,remarks, uId, 4);
            timesheet.updateRevision(rinfo);
            
            timesheet.getBasicDetail(assetId);            
            TimesheetInfo info = timesheet.getBasicDetail(assetId);
            ArrayList tlist = timesheet.getTimesheetList(assetId, search, statusIndex, month, yearId, 0, count, fromDateIndex, toDateIndex);
            
            int cnt = 0;
            if (tlist.size() > 0) {
                TimesheetInfo cinfo = (TimesheetInfo) tlist.get(tlist.size() - 1);
                cnt = cinfo.getTimesheetId();
                tlist.remove(tlist.size() - 1);
            }            
            request.getSession().setAttribute("T_LIST", tlist);
            request.getSession().setAttribute("COUNT_TSLIST", cnt + "");
            request.getSession().setAttribute("NEXTTS", "0");
            request.getSession().setAttribute("NEXTTSVALUE", "1");
            request.getSession().setAttribute("BASICINFO", info); 
            return mapping.findForward("view_timesheet");
        }      
        else if(frm.getDoApproveInvoice()!= null && frm.getDoApproveInvoice().equals("yes"))
        {
            print(this, "DoApproveInvoice block");
            frm.setDoApproveInvoice("no");
            int assetId = frm.getAssetId();
            frm.setAssetId(assetId);
            int timesheetId = frm.getTimesheetId();
            frm.setTimesheetId(timesheetId);            
            String remarks = vobj.replacedesc(frm.getApproveRemarks());
            int status = frm.getStatus();
            
            String add_timesheet_file = timesheet.getMainPath("add_timesheet_file");
            String foldername = timesheet.createFolder(add_timesheet_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "";
            FormFile filename = frm.getInvoiceFile();
            if (filename != null && filename.getFileSize() > 0) {
                fileName1 = timesheet.uploadFile(timesheetId, frm.getInvoicefilehidden(), filename, fn + "_1", add_timesheet_file, foldername);
            }
            
            TimesheetInfo appinfo = new TimesheetInfo(timesheetId, remarks, uId, status, fileName1);
            timesheet.approveInvoicing(appinfo, assetId);
            timesheet.getBasicDetail(assetId);            
            TimesheetInfo info = timesheet.getBasicDetail(assetId);
            ArrayList tlist = timesheet.getTimesheetList(assetId, search, statusIndex, month, yearId, 0, count, fromDateIndex, toDateIndex);
            int cnt = 0;
            if (tlist.size() > 0) {
                TimesheetInfo cinfo = (TimesheetInfo) tlist.get(tlist.size() - 1);
                cnt = cinfo.getTimesheetId();
                tlist.remove(tlist.size() - 1);
            }            
            request.getSession().setAttribute("T_LIST", tlist);
            request.getSession().setAttribute("COUNT_TSLIST", cnt + "");
            request.getSession().setAttribute("NEXTTS", "0");
            request.getSession().setAttribute("NEXTTSVALUE", "1");
            request.getSession().setAttribute("BASICINFO", info); 
            return mapping.findForward("view_timesheet");
        }      
        else if(frm.getDoSentApproval()!= null && frm.getDoSentApproval().equals("yes"))
        {
            print(this, "DoSentApproval block");
            frm.setDoApproveInvoice("no");
            int assetId = frm.getAssetId();
            frm.setAssetId(assetId);;
            int timesheetId = frm.getTimesheetId();
            frm.setTimesheetId(timesheetId);
            String email = frm.getEmail();
            String ccaddress = frm.getCcaddress();
            String bccaddress = frm.getBccaddress();
            String subject = frm.getSubject();
            String message = frm.getMessage();
            String excelFile = frm.getExcelfile() != null ? frm.getExcelfile() : "";
            int checktype = frm.getChecktype();
            frm.setChecktype(checktype);
            int repeatId = frm.getRepeatId();
            String sentapproval_file = timesheet.getMainPath("add_timesheet_file");
            String foldername = timesheet.createFolder(sentapproval_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String attachment = "";
            FormFile filename = frm.getSendApproveFile();
            String fnsave = "", mail_attachment = "";
            if (filename != null && filename.getFileSize() > 0 && checktype == 0) 
            {                
                attachment = timesheet.uploadFile(timesheetId, "", filename, fn + "_1", sentapproval_file, foldername);
                fnsave = sentapproval_file+attachment;
                mail_attachment = timesheet.getMainPath("view_timesheet_file") + attachment;
            }
            String excelFile_mail = "", localname2 = "", excelFile_maillog = "";
            if(!excelFile.equals(""))
            {
                excelFile_mail = sentapproval_file + excelFile;
                localname2 = "TimesheetData.xls";
                excelFile_maillog = timesheet.getMainPath("view_timesheet_file")+excelFile;
            }
            String to[] = parseCommaDelimString(email);
            String cc[] = parseCommaDelimString(ccaddress);
            String bcc[] = parseCommaDelimString(bccaddress);
            String messagebody = timesheet.getTimesheetMessage(message);
            
            String file_maillog = timesheet.getMainPath("file_maillog");
            String fn_mail = "ts-" + fn;
            String filePath_html = timesheet.createFolder(file_maillog);
            String fname = timesheet.writeHTMLFile(messagebody, file_maillog + "" + filePath_html, fn_mail + ".html"); 
            Mail mail = new Mail();
            StatsInfo sinfo = mail.postMailAttach(to, cc, bcc, messagebody, subject, excelFile_mail, localname2, fnsave, "Timesheet.pdf", -1);
            int sentflag = 0;
            if(sinfo != null)
            {
                sentflag = sinfo.getDdlValue();
            }
            timesheet.sendForApproval(timesheetId, attachment, uId, repeatId);
            if(repeatId > 0)
            {
                timesheet.updateFlag(repeatId, uId);
            }
            if(sentflag > 0)
                timesheet.createAprovalMailLog(13, email, ccaddress, bccaddress, subject, filePath_html + "/" + fname, excelFile_maillog, mail_attachment, timesheetId);
            
            timesheet.getBasicDetail(assetId);            
            TimesheetInfo info = timesheet.getBasicDetail(assetId);
            ArrayList tlist = timesheet.getTimesheetList(assetId, search, statusIndex, month, yearId, 0, count, fromDateIndex, toDateIndex);
            int cnt = 0;
            if (tlist.size() > 0) {
                TimesheetInfo cinfo = (TimesheetInfo) tlist.get(tlist.size() - 1);
                cnt = cinfo.getTimesheetId();
                tlist.remove(tlist.size() - 1);
            }            
            request.getSession().setAttribute("T_LIST", tlist);
            request.getSession().setAttribute("COUNT_TSLIST", cnt + "");
            request.getSession().setAttribute("NEXTTS", "0");
            request.getSession().setAttribute("NEXTTSVALUE", "1");
            request.getSession().setAttribute("BASICINFO", info); 
            request.removeAttribute("SAVE_TID");
            return mapping.findForward("view_timesheet");
        }      
        else if(frm.getDoSaveDraft()!= null && frm.getDoSaveDraft().equals("yes"))
        {
            print(this, "doSaveDraft block");
            frm.setDoSaveDraft("no");
            int timesheetId = frm.getTimesheetId();
            frm.setTimesheetId(timesheetId);
            int assetId = frm.getAssetId();
            frm.setAssetId(assetId);
            double[] amount = frm.getAmount();            
            double[] prate = frm.getPrate();            
            double[] srate = frm.getSrate();            
            double[] gtotal = frm.getGtotal();            
            int[] totalcrew = frm.getTotalcrew();            
            double[] rate = frm.getRate();
            int[] flag = frm.getFlag();            
            int[] positionId = frm.getPositionId();          
            int[] crewrotationId = frm.getCrewrotationId();          
            int[] timesheetdetailId = frm.getTimesheetdetailId();          
            int[] typeId = frm.getType();         
            String[] fromDate2 = frm.getFromDate2();
            String[] toDate2 = frm.getToDate2();
            int days[] = frm.getDays();
            int appPositionId[] = frm.getAppPositionId();
            String add_timesheet_file = timesheet.getMainPath("add_timesheet_file");
            String foldername = timesheet.createFolder(add_timesheet_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());            
            int cc = timesheet.createTimesheet(timesheetId, uId, foldername+"/"+fn+".xls", amount, prate,srate, gtotal, totalcrew, rate, 
                    flag, positionId, crewrotationId, timesheetdetailId, typeId, fromDate2, toDate2, days, appPositionId);        
            ArrayList submitted_list = timesheet.submittedList(timesheetId);
            createExcel.createExcel(submitted_list , add_timesheet_file+"/"+foldername+"/"+fn+".xls", timesheetId);            
            ArrayList tlist = timesheet.getTimesheetList(assetId, search, statusIndex, month, yearId, 0, count, fromDateIndex, toDateIndex);
            if(cc > 0)
            {
                assetId = frm.getAssetId();
                frm.setAssetId(assetId);
                frm.getClientassetId();
                frm.setClientassetId(assetId);                
                timesheetId = frm.getTimesheetId();
                frm.setTimesheetId(timesheetId);
                String fromDate = "", toDate = "",fromdate2 = "", todate2 = "";            
                TimesheetInfo tinfo = timesheet.getBasicTimesheet(timesheetId);
                int tcount = 0;
                if(tinfo != null)
                {
                    fromDate = tinfo.getFromDate() != null ? tinfo.getFromDate() : "";
                    toDate = tinfo.getToDate() != null ? tinfo.getToDate() : "";
                    tcount = tinfo.getTcount();
                }
                if(tcount > 0)
                {
                    ArrayList modifylist =  timesheet.getModifyList(timesheetId);
                    request.getSession().setAttribute("MODIFY_LIST", modifylist);
                }
                else
                {
                    ArrayList rateList = timesheet.getCrewRateList(assetId,fromDate, toDate); 
                    ArrayList activityList = timesheet.getactivityDetails(fromDate, toDate, assetId, rateList);
                    ArrayList sbyList = timesheet.getStanbyPromotionDetails(fromDate, toDate, assetId);
                    int size = activityList.size();
                    ArrayList crewList = timesheet.getCrewList(assetId);
                    ArrayList pendingList = timesheet.getPendingList(assetId, timesheetId);  
                    double allowance = 0;
                    TimesheetInfo info = null;
                    if (request.getSession().getAttribute("BASICINFO") != null){
                        info =   (TimesheetInfo) request.getSession().getAttribute("BASICINFO");     
                        if(info != null){
                            allowance = info.getAllowance();
                        }
                    }
                    if(allowance > 0)
                    {
                        ArrayList allowanceList = new ArrayList();
                        TimesheetInfo ainfo = null;
                        if(size > 0)
                        {
                            for (int i = 0; i < size; i++) 
                            {
                                ainfo = (TimesheetInfo) activityList.get(i);
                                if (ainfo != null) 
                                {
                                    fromdate2 = tinfo.getFromDate() != null ? tinfo.getFromDate() : "";
                                    todate2 = tinfo.getToDate() != null ? tinfo.getToDate() : "";
                                }        
                            }
                        }
                        allowanceList = timesheet.getAllowanceList(assetId);                        
                        request.getSession().setAttribute("ALLOWANCE_LIST", allowanceList);
                    }
                    request.getSession().setAttribute("PENDING_LIST", pendingList);
                    request.getSession().setAttribute("CREW_LIST", crewList);
                    request.getSession().setAttribute("ACTIVITY_LIST", activityList);
                    request.getSession().setAttribute("SBYPRO_LIST", sbyList);
                    request.getSession().setAttribute("CREWRATE_LIST", rateList);
                }
                request.getSession().setAttribute("TSINFO", tinfo); 
                request.setAttribute("THANKYOU", "yes");
                return mapping.findForward("view_timesheetdetails");               
                
            }else{
                int cnt = 0;
                if (tlist.size() > 0) 
                {
                    TimesheetInfo cinfo = (TimesheetInfo) tlist.get(tlist.size() - 1);
                    cnt = cinfo.getTimesheetId();
                    tlist.remove(tlist.size() - 1);
                }            
                request.getSession().setAttribute("T_LIST", tlist);
                request.getSession().setAttribute("COUNT_TSLIST", cnt + "");
                request.getSession().setAttribute("NEXTTS", "0");
                request.getSession().setAttribute("NEXTTSVALUE", "1");
                request.setAttribute("SAVE_TID", ""+timesheetId);            
                return mapping.findForward("view_timesheetdetails");
            }
        }       
        else if(frm.getDoGetDetails()!= null && frm.getDoGetDetails().equals("yes"))
        {
            print(this, "GetDetails block");
            frm.setDoGetDetails("no");
            int assetId = frm.getAssetId();
            frm.setAssetId(assetId);
            int timesheetId = frm.getTimesheetId();
            frm.setTimesheetId(timesheetId);
            String fromDate = "", toDate = "";            
            TimesheetInfo tinfo = timesheet.getBasicTimesheet(timesheetId);
            int tcount = 0;
            ArrayList datalist = new ArrayList();
            if(tinfo != null)
            {
                fromDate = tinfo.getFromDate() != null ? tinfo.getFromDate() : "";
                toDate = tinfo.getToDate() != null ? tinfo.getToDate() : "";
                tcount = tinfo.getTcount();
            }
            int repeatId = frm.getRepeatId(); 
            if(tcount > 0)
            {
                ArrayList modifylist =  timesheet.getModifyList(timesheetId);
                request.getSession().setAttribute("MODIFY_LIST", modifylist);
            } else if(repeatId > 0)
            {
                datalist = timesheet.getPandingData(repeatId);
                request.getSession().setAttribute("DUPT_LIST", datalist);
            }
            else 
            {
                ArrayList rateList = timesheet.getCrewRateList(assetId,fromDate, toDate); 
                ArrayList activityList = timesheet.getactivityDetails(fromDate, toDate, assetId, rateList);
                ArrayList sbyList = timesheet.getStanbyPromotionDetails(fromDate, toDate, assetId);
                ArrayList crewList = timesheet.getCrewList(assetId);
                               
                ArrayList pendingList = timesheet.getPendingList(assetId, timesheetId);  
                double allowance = 0;
                TimesheetInfo info = null;
                if (request.getSession().getAttribute("BASICINFO") != null)
                {
                    info =   (TimesheetInfo) request.getSession().getAttribute("BASICINFO");     
                    if(info != null)
                    {
                        allowance = info.getAllowance();
                    }
                }
                ArrayList allowanceList = new ArrayList();
                if(allowance > 0)
                {                    
                    allowanceList = timesheet.getAllowanceList(assetId);                    
                    request.getSession().setAttribute("ALLOWANCE_LIST", allowanceList);
                }
                request.getSession().setAttribute("PENDING_LIST", pendingList);
                request.getSession().setAttribute("CREW_LIST", crewList);
                request.getSession().setAttribute("CREWRATE_LIST", rateList);
                request.getSession().setAttribute("ACTIVITY_LIST", activityList);
                request.getSession().setAttribute("SBYPRO_LIST", sbyList);
            }
            request.getSession().setAttribute("TSINFO", tinfo); 
            return mapping.findForward("view_timesheetdetails");
        }     
        else if(frm.getRegenerate()!= null && frm.getRegenerate().equals("yes"))
        {
            print(this, "getRegenerate block");
            frm.setRegenerate("no");
            int assetId = frm.getAssetId();
            frm.setAssetId(assetId);
            int timesheetId = frm.getTimesheetId();
            frm.setTimesheetId(timesheetId);
            String fromDate = "", toDate = "", fromDate2 = "", toDate2 = "";            
            TimesheetInfo tinfo = timesheet.getBasicTimesheet(timesheetId);
            int tcount = 0;
            if(tinfo != null)
            {
                fromDate = tinfo.getFromDate() != null ? tinfo.getFromDate() : "";
                toDate = tinfo.getToDate() != null ? tinfo.getToDate() : "";
                tcount = tinfo.getTcount();
            }
            if(tcount > 0)
            {
                ArrayList modifylist =  timesheet.getModifyList(timesheetId);
                request.getSession().setAttribute("MODIFY_LIST", modifylist);
            }
            else
            {
                ArrayList rateList = timesheet.getCrewRateList(assetId,fromDate, toDate); 
                ArrayList activityList = timesheet.getactivityDetails(fromDate, toDate, assetId, rateList);
                ArrayList sbyList = timesheet.getStanbyPromotionDetails(fromDate, toDate, assetId);
                int size = activityList.size();
                ArrayList crewList = timesheet.getCrewList(assetId);
                ArrayList pendingList = timesheet.getPendingList(assetId, timesheetId);  
                double allowance = 0;
                TimesheetInfo info = null;
                if (request.getSession().getAttribute("BASICINFO") != null){
                    info =   (TimesheetInfo) request.getSession().getAttribute("BASICINFO");     
                    if(info != null){
                        allowance = info.getAllowance();
                    }
                }
                if(allowance > 0)
                {
                    ArrayList allowanceList = new ArrayList();
                    TimesheetInfo ainfo = null;
                    if(size > 0)
                    {
                        for (int i = 0; i < size; i++) 
                        {
                            ainfo = (TimesheetInfo) activityList.get(i);
                            if (ainfo != null) 
                            {
                                fromDate2 = ainfo.getFromDate() != null ? ainfo.getFromDate() : "";
                                toDate2 = ainfo.getToDate() != null ? ainfo.getToDate() : "";
                            }        
                        }
                    }
                    allowanceList = timesheet.getAllowanceList(assetId);                        
                    request.getSession().setAttribute("ALLOWANCE_LIST", allowanceList);
                }
                request.getSession().setAttribute("PENDING_LIST", pendingList);
                request.getSession().setAttribute("CREW_LIST", crewList);
                request.getSession().setAttribute("ACTIVITY_LIST", activityList);
                request.getSession().setAttribute("SBYPRO_LIST", sbyList);
                request.getSession().setAttribute("CREWRATE_LIST", rateList);
            }
            request.getSession().setAttribute("TSINFO", tinfo); 
            return mapping.findForward("view_timesheetdetails");
        }     
        else if(frm.getDoDelete()!= null && frm.getDoDelete().equals("yes"))
        {
            print(this, "Delete Block");
            frm.setDoDelete("no");
            
            int assetId = frm.getAssetId();
            frm.setAssetId(assetId);
            int timesheetId = frm.getTimesheetId();
            frm.setTimesheetId(timesheetId);   
            timesheet.deleteTimesheet(timesheetId);            
            int cc = timesheet.getTimesheetCount(assetId);
            ArrayList tlist = timesheet.getTimesheetList(assetId, search, statusIndex, month, yearId, 0, count, fromDateIndex, toDateIndex);
            
            int cnt = 0;
            if (tlist.size() > 0) 
            {
                TimesheetInfo cinfo = (TimesheetInfo) tlist.get(tlist.size() - 1);
                cnt = cinfo.getTimesheetId();
                tlist.remove(tlist.size() - 1);
            }            
            request.getSession().setAttribute("T_LIST", tlist);
            request.getSession().setAttribute("COUNT_TSLIST", cnt + "");
            request.getSession().setAttribute("NEXTTS", "0");
            request.getSession().setAttribute("NEXTTSVALUE", "1");
            request.getSession().setAttribute("TIMESHEET", "" + cc);
            return mapping.findForward("view_timesheet");
        }
        else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) 
        {
            frm.setDoCancel("no");
            print(this, "doCancel block");
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList timesheetList = timesheet.getTimesheetByName(next, count,allclient, permission, cids, assetids, clientIdIndex, assetIdIndex, type);
            int cnt = 0;
            if (timesheetList.size() > 0) 
            {
                TimesheetInfo cinfo = (TimesheetInfo) timesheetList.get(timesheetList.size() - 1);
                cnt = cinfo.getAssetId();
                timesheetList.remove(timesheetList.size() - 1);
            }
            request.getSession().setAttribute("TIMESHEET_LIST", timesheetList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display");
        }
        else 
        {
            print(this, "else block.");
            if (frm.getCtp() == 0) {  
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }

            ArrayList timesheetList = timesheet.getTimesheetByName(0, count, allclient, permission, cids, assetids,clientIdIndex, assetIdIndex, type);
            int cnt = 0;
            if (timesheetList.size() > 0) 
            {
                TimesheetInfo cinfo = (TimesheetInfo) timesheetList.get(timesheetList.size() - 1);
                cnt = cinfo.getAssetId();
                timesheetList.remove(timesheetList.size() - 1);
            }
            request.getSession().setAttribute("TIMESHEET_LIST", timesheetList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
