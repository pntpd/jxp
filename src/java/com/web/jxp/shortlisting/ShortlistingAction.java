package com.web.jxp.shortlisting;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import static com.web.jxp.common.Common.*;
import com.web.jxp.user.UserInfo;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

public class ShortlistingAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ShortlistingForm frm = (ShortlistingForm) form;
        Shortlisting shortlisting = new Shortlisting();

        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
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
                cids = uInfo.getCids();
                allclient = uInfo.getAllclient();
                assetids = uInfo.getAssetids();
            }
        }
        int check_user = shortlisting.checkUserSession(request, 49, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = shortlisting.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Shortlisting Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }

        if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            frm.setDoSave("no");
            print(this, "getDoSave block." + isTokenValid(request));

            if (isTokenValid(request)) {
                resetToken(request);
                int jobpostId = frm.getJobpostId();
                frm.setJobpostId(jobpostId);
                int clientId = frm.getClientId();
                int clientassetId = frm.getClientAssetId();
                int status = 1;

                SortedSet<String> ss = new TreeSet<String>();
                if (request.getSession().getAttribute("CANDSORTEDLIST") != null) {
                    ss = (SortedSet) request.getSession().getAttribute("CANDSORTEDLIST");
                }
                ShortlistingInfo info = new ShortlistingInfo(jobpostId, clientId, clientassetId, status, uId);
                int cc = shortlisting.createShortlisting(ss, info);

                print(this, "getDoSave block. cc" + cc);
                if (cc > 0) {
                    shortlisting.createShortlistHistory(ss, info, cc);
                    request.setAttribute("MESSAGE", "Data added successfully.");
                    request.getSession().removeAttribute("CANDSORTEDLIST");
                    request.getSession().removeAttribute("SEARCHPARAMETER");
                    request.getSession().removeAttribute("CANDIDATELIST");
                    request.setAttribute("SHORTLISTINGSAVEMODEL", "yes");
                    return mapping.findForward("display");
                }
            }
        } else if (frm.getDoCandSearch() != null && frm.getDoCandSearch().equals("yes")) {
            frm.setDoCandSearch("no");
            print(this, " getDoCandSearch block :: ");
            saveToken(request);
            request.getSession().removeAttribute("CANDSORTEDLIST");
            request.getSession().removeAttribute("CANDIDATELIST");

            if (frm.getFm() != null && frm.getFm().equals("jp")) {
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }
            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            ShortlistingInfo info = shortlisting.getShortlistingDetailById(jobpostId, allclient, permission, cids, assetids);
            frm.setClientId(info.getClientId());
            frm.setClientAssetId(info.getClientassetId());

            ArrayList searchlist = shortlisting.getShortlistingJobpostData(info);
            String querycond = shortlisting.getStrFromStr(searchlist);
            ArrayList candidatelist = shortlisting.getCandidateListByFields(querycond);
            request.setAttribute("SHORTLISTINGINFO", info);
            request.getSession().setAttribute("SEARCHPARAMETER", searchlist);
            request.getSession().setAttribute("CANDIDATELIST", candidatelist);
            return mapping.findForward("display");
        } else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            frm.setDoCancel("no");
            print(this, " getDoCancel block :: ");
            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            return mapping.findForward("display");
        } else {
            print(this, "else block.");
            saveToken(request);
            if (frm.getCtp() == 0) {
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }
            request.getSession().removeAttribute("CANDSORTEDLIST");
            request.getSession().removeAttribute("SEARCHPARAMETER");
            request.getSession().removeAttribute("CANDIDATELIST");
        }
        return mapping.findForward("display");
    }
}
