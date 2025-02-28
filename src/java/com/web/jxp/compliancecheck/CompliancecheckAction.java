package com.web.jxp.compliancecheck;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import static com.web.jxp.common.Common.*;
import com.web.jxp.user.UserInfo;
import java.util.Collection;
import java.util.Stack;

public class CompliancecheckAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CompliancecheckForm frm = (CompliancecheckForm) form;
        Compliancecheck compliancecheck = new Compliancecheck();
        int count = compliancecheck.getCount();
        String search = frm.getSearch() != null ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0, allclient = 0;
        String permission = "N", cids = "", assetids = "";
        if (request.getSession().getAttribute("LOGININFO") != null)
        {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) 
            {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
                 permission = uInfo.getPermission();
                uId = uInfo.getUserId();
                cids = uInfo.getCids();
                allclient = uInfo.getAllclient();
                assetids = uInfo.getAssetids();
            }
        }
        int check_user = compliancecheck.checkUserSession(request, 47, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = compliancecheck.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Compliancecheck Enrollment Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        
        Collection clients = compliancecheck.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);
        
        Collection assets = compliancecheck.getClientAsset(clientIdIndex, assetids, allclient, permission);
        frm.setAssets(assets);
        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);
        
        Collection positions = compliancecheck.getPostions(assetIdIndex);
        frm.setPositions(positions);
        String pgvalue = frm.getPgvalue();
        frm.setPgvalue(pgvalue);
        
        if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            frm.setDoView("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            
            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            
            int shortlistId = frm.getShortlistId();
            frm.setShortlistId(shortlistId);
            
            int shortlistccId = frm.getShortlistccId();
            frm.setShortlistccId(shortlistccId);
            
            int status = frm.getStatus();
            frm.setStatus(status);
            
            CompliancecheckInfo cinfo = compliancecheck.getCandidateDetail(candidateId);
            request.getSession().setAttribute("CANDIDATEC_DETAIL", cinfo);
            CompliancecheckInfo jpinfo = compliancecheck.getJobpostDetail(jobpostId);
            request.getSession().setAttribute("JOBPOST_DETAIL", jpinfo);
            
            ArrayList compliancecheckList = compliancecheck.getcompliancechecklist(  shortlistId, jpinfo.getGradeId(),jpinfo.getClientId(), jpinfo.getClientassetId(), jpinfo.getPositionName(), status, shortlistccId);
            int listsize = compliancecheckList.size();
            frm.setListsize(listsize);
            request.getSession().setAttribute("CC_LIST", compliancecheckList);
            return mapping.findForward("view");
            
        }  else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            frm.setDoSave("no");
            
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int shortlistId = frm.getShortlistId();
            int shortlistccId = frm.getShortlistccId();
            int checkpointId = frm.getCheckpointId();
            int cbstatus = frm.getRbchecked();
            String remarks = frm.getRemarks();
            int listsize = frm.getListsize();

            CompliancecheckInfo info = new CompliancecheckInfo(shortlistId,shortlistccId,checkpointId,cbstatus,remarks,0);
            int cc = 0;
            cc = compliancecheck.saveShortlistccdata(info, candidateId, uId,listsize);
            
            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            int status = frm.getStatus();
            frm.setStatus(status);
            
            CompliancecheckInfo cinfo = compliancecheck.getCandidateDetail(candidateId);
            request.getSession().setAttribute("CANDIDATEC_DETAIL", cinfo);
            CompliancecheckInfo jpinfo = compliancecheck.getJobpostDetail(jobpostId);
            request.getSession().setAttribute("JOBPOST_DETAIL", jpinfo);
            
            ArrayList compliancecheckList = compliancecheck.getcompliancechecklist(  shortlistId, jpinfo.getGradeId(),jpinfo.getClientId(), jpinfo.getClientassetId(), jpinfo.getPositionName(), status, shortlistccId);
            listsize = compliancecheckList.size();
            frm.setListsize(listsize);
            request.getSession().setAttribute("CC_LIST", compliancecheckList);
            return mapping.findForward("view");
            
        } else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            frm.setDoCancel("no");
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList jobpostList = compliancecheck.getJobpostByName(search, next, count, clientIdIndex, assetIdIndex,pgvalue,allclient,  permission,  cids,  assetids);
            int cnt = 0;
            if (jobpostList.size() > 0) {
                CompliancecheckInfo cinfo = (CompliancecheckInfo) jobpostList.get(jobpostList.size() - 1);
                cnt = cinfo.getJobpostId();
                jobpostList.remove(jobpostList.size() - 1);
            }
            request.getSession().setAttribute("COMPLANCE_LIST", jobpostList);
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
            ArrayList jobpostList = compliancecheck.getJobpostByName(search, 0, count, clientIdIndex,assetIdIndex, pgvalue,allclient,  permission,  cids,  assetids);
            int cnt = 0;
            if (jobpostList.size() > 0) {
                CompliancecheckInfo cinfo = (CompliancecheckInfo) jobpostList.get(jobpostList.size() - 1);
                cnt = cinfo.getJobpostId();
                jobpostList.remove(jobpostList.size() - 1);
            }
            request.getSession().setAttribute("COMPLANCE_LIST", jobpostList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
