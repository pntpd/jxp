package com.web.jxp.verification;

import com.web.jxp.base.Validate;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import static com.web.jxp.common.Common.*;
import com.web.jxp.crewdb.Crewdb;
import com.web.jxp.crewdb.CrewdbInfo;
import com.web.jxp.user.UserInfo;
import java.util.Collection;
import java.util.Date;
import java.util.Stack;
import org.apache.struts.upload.FormFile;

public class VerificationAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        VerificationForm frm = (VerificationForm) form;
        Verification verification = new Verification();
        Crewdb crewdb = new Crewdb();
        Validate vobj = new Validate();

        int count = verification.getCount();
        String search = frm.getSearch() != null ? vobj.replacedesc(frm.getSearch()) : "";
        frm.setSearch(search);
        int statusIndex = frm.getStatusIndex();
        frm.setStatusIndex(statusIndex);
        int tabIndex = frm.getTabIndex();
        frm.setTabIndex(tabIndex);
        int uId = 0;
        String permission = "N", username= "";
        if (request.getSession().getAttribute("LOGININFO") != null) 
        {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) 
            {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
                username = uInfo.getName();
            }
        }
        int check_user = verification.checkUserSession(request, 46, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = verification.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Verification Database Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        Collection positions = verification.getPositions();
        frm.setPosition(positions);
        int positionIndex = frm.getPositionIndex();
        frm.setPositionIndex(positionIndex);
        if (frm.getDoView() != null && frm.getDoView().equals("yes")) {

            frm.setDoView("no");
            int verificationId = frm.getVerificationId();
            frm.setVerificationId(verificationId);
            frm.setCandidateId(verificationId);
            int alertId = frm.getAlertId();
            frm.setAlertId(alertId);
                    
            CrewdbInfo passinfo = crewdb.getPassDetail(verificationId);
            request.getSession().setAttribute("PASSINFO", passinfo);

            CrewdbInfo info = crewdb.getCrewdbDetail(verificationId);
            request.getSession().setAttribute("CREWDB_DETAIL", info);

            ArrayList list = crewdb.getList1(verificationId);
            request.getSession().setAttribute("LIST1", list);

            CrewdbInfo healthinfo = crewdb.gethealthdetail(verificationId);
            request.getSession().setAttribute("HEALTH_DETAIL", healthinfo);

            ArrayList vaccinationlist = crewdb.getvaccinationList(verificationId);
            request.getSession().setAttribute("VACCINATIONLIST", vaccinationlist);

            ArrayList workexplist = crewdb.getworkexpList(verificationId);
            request.getSession().setAttribute("WORKEXPLIST", workexplist);

            ArrayList educlist = crewdb.getListeduc(verificationId);
            request.getSession().setAttribute("LISTEDUC", educlist);

            ArrayList certilist = crewdb.getListcerti(verificationId);
            request.getSession().setAttribute("LISTCERTI", certilist);

            ArrayList banklist = crewdb.getListbank(verificationId);
            request.getSession().setAttribute("LISTBANK", banklist);

            ArrayList doclist = crewdb.getListGovdoc(verificationId);
            request.getSession().setAttribute("LISTGOVDOC", doclist);
            
            ArrayList nomineelist = crewdb.getNomineeList(verificationId);   
            request.getSession().setAttribute("NOMINEELIST", nomineelist);

            int tabno = 0;
            if (request.getParameter("tb") != null) {
                tabno = Integer.parseInt(request.getParameter("tb"));
                if (tabno > 0) {
                    request.getSession().setAttribute("TABNO", "" + tabno);
                }
            } else {
                request.getSession().setAttribute("TABNO", "" + 1);
            }
            return mapping.findForward("view_verification");

        } else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            print(this, "doCancel block");
            frm.setDoCancel("no");
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList verificationList = verification.getVerificationByName(search, statusIndex, positionIndex, tabIndex, 0, count);
            int cnt = 0;
            if (verificationList.size() > 0) {
                VerificationInfo cinfo = (VerificationInfo) verificationList.get(verificationList.size() - 1);
                cnt = cinfo.getVerificationId();
                verificationList.remove(verificationList.size() - 1);
            }
            request.getSession().setAttribute("VERIFICATION_LIST", verificationList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display");

        } else if (frm.getDoVerify() != null && frm.getDoVerify().equals("yes")) {

            frm.setDoVerify("no");
            print(this, "getDoVerify block.");
            int verificationId = frm.getVerificationId();
            int tabno = frm.getTabId();
            int childId = frm.getChildId();
            int alertId = frm.getAlertId();
            String documentName = frm.getDocumentName();
            String coursename = frm.getCoursename();            
            String verificationauth = vobj.replacedesc(frm.getVerificatonauthority());
            FormFile file1 = frm.getVerifiedfile();
            int chkVal = frm.getRbverification();

            String add_candidate_file = verification.getMainPath("add_candidate_file");
            String foldername = verification.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "";
            if (file1 != null && file1.getFileSize() > 0) {
                fileName1 = verification.uploadFile(verificationId, "", file1, fn + "_1", add_candidate_file, foldername);
            }
            int cc = verification.updateverification(tabno, uId, verificationId, childId, chkVal, fileName1, verificationauth);
            if(alertId > 0)
            {
                verification.deleteAlert(alertId);
            }
            frm.setVerificationId(verificationId);
            CrewdbInfo info1 = crewdb.getCrewdbDetail(verificationId); 
            
            String name = info1.getName();
            String email = info1.getEmail();
            String ccval = info1.getCcval();
                
            String messageBody = "";
            if (tabno == 7) {
                String message = "Your "+ coursename + " has been verified.";            
                messageBody = crewdb.getVerifyMessage(name, coursename, message, "verify.html");
            }
            else if (tabno == 10) {
                String message = "Your "+ documentName + " has been verified.";            
                messageBody = crewdb.getVerifyMessage(name, documentName, message, "verify.html");
            }
            String file_maillog = crewdb.getMainPath("file_maillog");
            java.util.Date nowmail = new java.util.Date();
            String fn_mail = "verify-"+String.valueOf(nowmail.getTime())+".html";
            String filePath = crewdb.createFolder(file_maillog);
            String fname = crewdb.writeHTMLFile(messageBody, file_maillog+"/"+filePath, fn_mail);
            
            String from = "journeyxpro@journeyxpro.com";
            String toaddress[] = new String[1];
            toaddress[0] = email;
            String ccva[] = parseCommaDelimString(ccval); 
            String subject = "JourneyXPro Verification";
            String ccaddress[] = parseCommaDelimString(ccval);
            String bccaddress[] = new String[0];
            if(tabno == 7 || tabno == 10)
            {
                verification.postMailAttach(toaddress, ccaddress, bccaddress, messageBody, subject, "", "", cc);   
                verification.createMailLog(20, name, email, ccval, "", from, subject, filePath+"/"+fname,  file_maillog +filePath+"/"+fname, 0, username, 0);
            }
            
            if (tabno == 1) {
                CrewdbInfo info = crewdb.getCrewdbDetail(verificationId);
                request.getSession().setAttribute("CREWDB_DETAIL", info);
            } else if (tabno == 2) {
                ArrayList list = crewdb.getList1(verificationId);
                request.getSession().setAttribute("LIST1", list);

            } else if (tabno == 3) {
                CrewdbInfo healthinfo = crewdb.gethealthdetail(verificationId);
                request.getSession().setAttribute("HEALTH_DETAIL", healthinfo);
                if (request.getSession().getAttribute("CREWDB_DETAIL") != null) {
                    CrewdbInfo info = (CrewdbInfo) request.getSession().getAttribute("CREWDB_DETAIL");
                    info.setCflag3(chkVal);
                    request.getSession().setAttribute("CREWDB_DETAIL", info);
                }
            } else if (tabno == 4) {
                ArrayList vaccinationlist = crewdb.getvaccinationList(verificationId);
                request.getSession().setAttribute("VACCINATIONLIST", vaccinationlist);

            } else if (tabno == 5) {
                ArrayList workexplist = crewdb.getworkexpList(verificationId);
                request.getSession().setAttribute("WORKEXPLIST", workexplist);

            } else if (tabno == 6) {
                ArrayList educlist = crewdb.getListeduc(verificationId);
                request.getSession().setAttribute("LISTEDUC", educlist);

            } else if (tabno == 7) {
                ArrayList certilist = crewdb.getListcerti(verificationId);
                request.getSession().setAttribute("LISTCERTI", certilist);

            } else if (tabno == 9) {
                ArrayList banklist = crewdb.getListbank(verificationId);
                request.getSession().setAttribute("LISTBANK", banklist);

            } else if (tabno == 10) {
                ArrayList doclist = crewdb.getListGovdoc(verificationId);
                request.getSession().setAttribute("LISTGOVDOC", doclist);

            } else if (tabno == 11) {
                ArrayList nomineelist = crewdb.getNomineeList(verificationId);
                request.getSession().setAttribute("NOMINEELIST", nomineelist);
            }
            request.getSession().setAttribute("TABNO", "" + tabno);
            return mapping.findForward("view_verification");
        } else if (frm.getDoVerifyOther() != null && frm.getDoVerifyOther().equals("yes")){
            frm.setDoVerifyOther("no");
            print(this, "getDoVerifyOther block.");
            int verificationId = frm.getVerificationId();
            int tabno = frm.getTabIdOther();
            int cc = verification.updateverification8910(tabno, uId, verificationId);
            frm.setVerificationId(verificationId);
            if (tabno == 4 || tabno == 9 || tabno == 10 || tabno == 11) 
            {
                CrewdbInfo info = crewdb.getCrewdbDetail(verificationId);
                request.getSession().setAttribute("CREWDB_DETAIL", info);
            }
            request.getSession().setAttribute("TABNO", "" + tabno);
            return mapping.findForward("view_verification");
        }else {
            print(this, "else block.");
            if (frm.getCtp() == 0) {
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }
            ArrayList verificationList = verification.getVerificationByName(search, statusIndex, positionIndex, tabIndex, 0, count);
            int cnt = 0;
            if (verificationList.size() > 0) {
                VerificationInfo cdinfo = (VerificationInfo) verificationList.get(verificationList.size() - 1);
                cnt = cdinfo.getVerificationId();
                verificationList.remove(verificationList.size() - 1);
            }
            request.getSession().setAttribute("VERIFICATION_LIST", verificationList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
            request.getSession().removeAttribute("TABNO");
        }
        return mapping.findForward("display");
    }
}
