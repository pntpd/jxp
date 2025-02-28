package com.web.jxp.clientselection;

import com.web.jxp.base.Validate;
import static com.web.jxp.common.Common.*;
import com.web.jxp.user.UserInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ClientselectionAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ClientselectionForm frm = (ClientselectionForm) form;
        Clientselection clientselection = new Clientselection();
        Validate vobj = new Validate();
        int count = clientselection.getCount();
        String search = frm.getSearch() != null ? frm.getSearch() : "";
        frm.setSearch(search);
        int statusIndex = frm.getStatusIndex();
        frm.setStatusIndex(statusIndex);
        int uId = 0, allclient = 0, companytype = 0;
        String permission = "N", cids = "", assetids = "", username = "";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
                cids = uInfo.getCids();
                allclient = uInfo.getAllclient();
                assetids = uInfo.getAssetids();
                companytype = uInfo.getCompanytype();
                username = uInfo.getName();
            }
        }
        int check_user = clientselection.checkUserSession(request, 51, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = clientselection.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Compliancecheck Enrollment Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }

        Collection clients = clientselection.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);

        if (companytype == 2) {
            Collection assets = clientselection.getAsset(assetids);
            frm.setAssets(assets);

        } else {
            Collection assets = clientselection.getClientAsset(-1, assetids, allclient, permission);
            frm.setAssets(assets);
        }
        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);

        Collection positions = clientselection.getPostions(-1);
        frm.setPositions(positions);
        String pgvalue = frm.getPgvalue();
        frm.setPgvalue(pgvalue);

        if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            frm.setDoView("no");
            print(this, " getDoView block :: ");

            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            String strfrom = frm.getFrom();
            frm.setFrom(strfrom);
            int status = frm.getStatus();
            frm.setStatus(status);
            frm.setShortlistId(0);
            frm.setCandidateId(0);

            ClientselectionInfo info = clientselection.getClientSelectionByIdforDetail(jobpostId);
            int clientId = info.getClientId();
            frm.setClientId(clientId);
            ArrayList shortcandList = clientselection.getShortlistedCandidateListByIDs(jobpostId, companytype, "", 0, 0);
            request.getSession().setAttribute("CLIENTSELECTIONJOBPOST_DETAIL", info);
            request.getSession().setAttribute("SHORTLISTEDCANDIDATE_LIST", shortcandList);

            return mapping.findForward("selection_details");

        } else if (frm.getDoGenerate() != null && frm.getDoGenerate().equals("yes")) {
            frm.setDoGenerate("no");
            print(this, " getDoGenerate block :: ");

            int shortlistId = frm.getShortlistId();
            int sflag = frm.getSflag();
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);

            Collection resumetemplates = clientselection.getResumetemplatesforclientindex(clientId, 1);
            frm.setResumetemplates(resumetemplates);

            String pdffilename = "";
            pdffilename = clientselection.getshortlistpdf(shortlistId);
            request.setAttribute("PDFFILENAME", pdffilename);
            request.setAttribute("SFLAG", sflag);
            request.setAttribute("SHORTLISTID", shortlistId);
            request.setAttribute("JOBPOSTID", jobpostId);
            return mapping.findForward("generate_cv");
        } else if (frm.getDoSummary() != null && frm.getDoSummary().equals("yes")) {
            frm.setDoSummary("no");
            print(this, " getDoSummary block :: ");

            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int shortlistId = frm.getShortlistId();

            ArrayList EmaillogList = clientselection.getSelectionSummaryEmaillog(shortlistId);
            ArrayList OfferlogList = clientselection.getSelectionSummaryOfferlog(shortlistId);
            ArrayList shortcand_list = new ArrayList();
            if (request.getSession().getAttribute("SHORTLISTEDCANDIDATE_LIST") != null) {
                shortcand_list = (ArrayList) request.getSession().getAttribute("SHORTLISTEDCANDIDATE_LIST");
            }
            ClientselectionInfo cinfo = clientselection.getInfoFromList(shortcand_list, shortlistId);
            ClientselectionInfo einfo = clientselection.setEmailDetail(shortlistId, cinfo.getSflag(), 0);
            request.setAttribute("CANDIDATESUMMARY", cinfo);
            request.setAttribute("CANDIDATEEMAILSUMMARY", einfo);
            request.setAttribute("SUMMARYEMAILLOG", EmaillogList);
            request.setAttribute("SUMMARYOFFERLOG", OfferlogList);

            return mapping.findForward("selection_summary");

        } else if (frm.getDoGenerateoffer() != null && frm.getDoGenerateoffer().equals("yes")) {
            frm.setDoGenerateoffer("no");
            print(this, " getDoGenerateoffer block :: ");

            int shortlistId = frm.getShortlistId();
            int sflag = frm.getSflag();
            int oflag = frm.getOflag();
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            int candidateId = frm.getCandidateId();
            int type = frm.getType();
            frm.setCandidateId(candidateId);
            frm.setClientId(clientId);
            String clientasset = frm.getHdnclientasset();

            Collection resumetemplates = clientselection.getResumetemplatesforclientindex(clientId, 2);
            frm.setResumetemplates(resumetemplates);
            frm.setResumetempId(-1);
            String pdffilename = "";
            pdffilename = clientselection.getshortlistofferpdf(shortlistId);
            request.setAttribute("PDFFILENAME", pdffilename);
            request.setAttribute("SFLAG", sflag);
            request.setAttribute("OFLAG", oflag);
            request.setAttribute("SHORTLISTID", shortlistId);
            request.setAttribute("JOBPOSTID", jobpostId);
            request.setAttribute("CLIENTASSET", clientasset);
            request.setAttribute("OFF_TP", type);
            return mapping.findForward("generate_offerlett");
        } else if (frm.getDoSelect() != null && frm.getDoSelect().equals("yes")) {
            frm.setDoSelect("no");
            print(this, " getDoSelect block :: ");

            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int shortlistId = frm.getShortlistId();
            int cc = clientselection.getClientCandidateSelect(shortlistId, username, companytype, uId);
            print(this, " getDoSelect Query :: " + cc);
            ClientselectionInfo info = clientselection.getClientSelectionByIdforDetail(jobpostId);
            ArrayList shortcandList = clientselection.getShortlistedCandidateListByIDs(jobpostId, companytype, "", 0, 0);
            request.setAttribute("CANDIDATESELECTEDMODAL", "yes");
            request.getSession().setAttribute("CLIENTSELECTIONJOBPOST_DETAIL", info);
            request.getSession().setAttribute("SHORTLISTEDCANDIDATE_LIST", shortcandList);

            return mapping.findForward("selection_details");
        } else if (frm.getDoReject() != null && frm.getDoReject().equals("yes")) {
            frm.setDoReject("no");
            print(this, " getDoReject block :: ");

            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int shortlistId = frm.getShortlistId();
            int candidateId = frm.getCandidateId();
            String[] strReason = frm.getRejectReason();
            String Reasons = vobj.replacealphacomma(makeCommaDelimString(strReason));
            String strRemark = vobj.replacedesc(frm.getRejectRemark());
            int cc = clientselection.getClientCandidateReject(shortlistId, username, Reasons, strRemark, companytype, uId, candidateId);

            ClientselectionInfo info = clientselection.getClientSelectionByIdforDetail(jobpostId);
            ArrayList shortcandList = clientselection.getShortlistedCandidateListByIDs(jobpostId, companytype, "", 0, 0);
            request.getSession().setAttribute("CLIENTSELECTIONJOBPOST_DETAIL", info);
            request.getSession().setAttribute("SHORTLISTEDCANDIDATE_LIST", shortcandList);
            return mapping.findForward("selection_details");
        } else if (frm.getDoAccept() != null && frm.getDoAccept().equals("yes")) {
            frm.setDoAccept("no");
            print(this, " getDoAccept block :: ");

            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int shortlistId = frm.getShortlistId();
            int candidateId = frm.getCandidateId();
            String strAcceptRemark = vobj.replacedesc(frm.getTxtAcceptremark());
            int cc = clientselection.getClientCandidateAccept(shortlistId, username, strAcceptRemark, companytype, uId, candidateId);

            ClientselectionInfo info = clientselection.getClientSelectionByIdforDetail(jobpostId);
            ArrayList shortcandList = clientselection.getShortlistedCandidateListByIDs(jobpostId, companytype, "", 0, 0);
            request.getSession().setAttribute("CLIENTSELECTIONJOBPOST_DETAIL", info);
            request.getSession().setAttribute("SHORTLISTEDCANDIDATE_LIST", shortcandList);
            return mapping.findForward("selection_details");
        } 
        else if (frm.getDoDecline() != null && frm.getDoDecline().equals("yes")) {
            frm.setDoDecline("no");
            print(this, " getDoDecline block :: ");

            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int shortlistId = frm.getShortlistId();
            int candidateId = frm.getCandidateId();
            String[] strReason = frm.getDeclineReason();
            String declineReasons = vobj.replacealphacomma(makeCommaDelimString(strReason));
            String strDeclineRemark = vobj.replacedesc(frm.getTxtDeclineremark());
            int cc = clientselection.getClientCandidateDecline(shortlistId, username, declineReasons, strDeclineRemark, companytype, uId, candidateId);

            ClientselectionInfo info = clientselection.getClientSelectionByIdforDetail(jobpostId);
            ArrayList shortcandList = clientselection.getShortlistedCandidateListByIDs(jobpostId, companytype, "", 0, 0);
            request.getSession().setAttribute("CLIENTSELECTIONJOBPOST_DETAIL", info);
            request.getSession().setAttribute("SHORTLISTEDCANDIDATE_LIST", shortcandList);
            return mapping.findForward("selection_details");
        } else if (frm.getDoRelease()!= null && frm.getDoRelease().equals("yes")) {
            frm.setDoRelease("no");
            print(this, " getDoRelease block :: ");
            
            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int shortlistId = frm.getShortlistId();
            frm.setShortlistId(shortlistId);
            int id = clientselection.updateStatus(shortlistId);
            if(id > 0)
            {
                clientselection.unlockCandidate(candidateId);
                clientselection.insertclientselectionhistory(shortlistId, 4, companytype, 0, uId, 5);
            }            
            ClientselectionInfo info = clientselection.getClientSelectionByIdforDetail(jobpostId);
            ArrayList shortcandList = clientselection.getShortlistedCandidateListByIDs(jobpostId, companytype, "", 0, 0);
            request.getSession().setAttribute("CLIENTSELECTIONJOBPOST_DETAIL", info);
            request.getSession().setAttribute("SHORTLISTEDCANDIDATE_LIST", shortcandList);
            return mapping.findForward("selection_details");
        }
        else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            frm.setDoCancel("no");
            print(this, " getDoCancel block :: ");

            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList jobpostList = clientselection.getClientSelectByName(search, statusIndex, next, count, clientIdIndex, assetIdIndex, pgvalue, allclient, permission, cids, assetids);
            int cnt = 0;
            if (jobpostList.size() > 0) {
                ClientselectionInfo cinfo = (ClientselectionInfo) jobpostList.get(jobpostList.size() - 1);
                cnt = cinfo.getJobpostId();
                jobpostList.remove(jobpostList.size() - 1);
            }
            request.getSession().setAttribute("CCOMPLANCE_LIST", jobpostList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display");
        } else {
            print(this, "else block.");

            if (frm.getCtp() == 0) {
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }
            if (companytype == 2) {
                ArrayList jobpostList = clientselection.getClientSelectByName(search, statusIndex, 0, count, clientIdIndex, assetIdIndex, pgvalue, allclient, permission, cids, assetids);
                int cnt = 0;
                if (jobpostList.size() > 0) {
                    ClientselectionInfo cinfo = (ClientselectionInfo) jobpostList.get(jobpostList.size() - 1);
                    cnt = cinfo.getJobpostId();
                    jobpostList.remove(jobpostList.size() - 1);
                }
                request.getSession().setAttribute("CLIENTSELECTION_LIST", jobpostList);
                request.getSession().setAttribute("COUNT_LIST", cnt + "");
                request.getSession().setAttribute("NEXT", "0");
                request.getSession().setAttribute("NEXTVALUE", "1");
                return mapping.findForward("client_display");
            } else {
                ArrayList jobpostList = clientselection.getClientSelectByName(search, statusIndex, 0, count, clientIdIndex, assetIdIndex, pgvalue, allclient, permission, cids, assetids);
                int cnt = 0;
                if (jobpostList.size() > 0) {
                    ClientselectionInfo cinfo = (ClientselectionInfo) jobpostList.get(jobpostList.size() - 1);
                    cnt = cinfo.getJobpostId();
                    jobpostList.remove(jobpostList.size() - 1);
                }
                request.getSession().setAttribute("CCOMPLANCE_LIST", jobpostList);
                request.getSession().setAttribute("COUNT_LIST", cnt + "");
                request.getSession().setAttribute("NEXT", "0");
                request.getSession().setAttribute("NEXTVALUE", "1");
                return mapping.findForward("display");
            }
        }
    }
}
