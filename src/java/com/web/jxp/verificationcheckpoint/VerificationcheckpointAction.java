package com.web.jxp.verificationcheckpoint;

import com.web.jxp.base.Validate;
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

public class VerificationcheckpointAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        VerificationcheckpointForm frm = (VerificationcheckpointForm) form;
        Verificationcheckpoint verificationcheckpoint = new Verificationcheckpoint();
        Validate vobj = new Validate();
        int count = verificationcheckpoint.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        Collection statuses = verificationcheckpoint.getStatuses();
        frm.setStatuses(statuses);
        int statusIndex = frm.getStatusIndex();
        frm.setStatusIndex(statusIndex);
        int uId = 0;
        String permission = "N";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
            }
        }
        int check_user = verificationcheckpoint.checkUserSession(request, 15, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = verificationcheckpoint.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Verification Type Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            frm.setDoAdd("no");
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setVerificationcheckpointId(-1);
            int verificationcheckpointcode = verificationcheckpoint.getMaxIdverificationcode();
            frm.setVerificationcheckpointcode(verificationcheckpoint.changeNum(verificationcheckpointcode, 3));
            saveToken(request);
            return mapping.findForward("add_verificationcheckpoint");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            frm.setDoModify("no");
            int verificationcheckpointId = frm.getVerificationcheckpointId();
            frm.setVerificationcheckpointId(verificationcheckpointId);
            frm.setVerificationcheckpointcode(verificationcheckpoint.changeNum(verificationcheckpointId, 3));
            VerificationcheckpointInfo info = verificationcheckpoint.getVerificationcheckpointDetailById(verificationcheckpointId);
            if (info != null) {
                frm.setVerificationcheckpointName(info.getVerificationcheckpointName());
                frm.setStatus(info.getStatus());
                frm.setDisplaynote(info.getDisplaynote());
                frm.setMinverification(info.getMinverification());
                frm.setTabid(info.getTabid());

            }
            if (request.getAttribute("VCSAVEMODEL") != null) {

                request.removeAttribute("VCSAVEMODEL");
            }
            saveToken(request);
            return mapping.findForward("add_verificationcheckpoint");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            frm.setDoView("no");
            int verificationcheckpointId = frm.getVerificationcheckpointId();
            frm.setVerificationcheckpointId(verificationcheckpointId);
            VerificationcheckpointInfo info = verificationcheckpoint.getVerificationcheckpointDetailByIdforDetail(verificationcheckpointId);
            request.setAttribute("VERIFICATIONSUBTYPE_DETAIL", info);
            return mapping.findForward("view_verificationcheckpoint");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            frm.setDoSave("no");
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int verificationcheckpointId = frm.getVerificationcheckpointId();
                String name = vobj.replacename(frm.getVerificationcheckpointName());
                int tabid = frm.getTabid();
                int minverification = frm.getMinverification();
                String displaynote = vobj.replacedesc(frm.getDisplaynote());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = verificationcheckpoint.getLocalIp();
                int ck = verificationcheckpoint.checkDuplicacy(verificationcheckpointId, name, tabid);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Verification Checkpoint already exists");
                    return mapping.findForward("add_verificationcheckpoint");
                }
                VerificationcheckpointInfo info = new VerificationcheckpointInfo(verificationcheckpointId, name, tabid, minverification, displaynote, status, uId);
                if (verificationcheckpointId <= 0) {
                    int cc = verificationcheckpoint.createVerificationcheckpoint(info);
                    if (cc > 0) {
                        verificationcheckpoint.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 15, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    request.setAttribute("VCSAVEMODEL", "Created");
                    VerificationcheckpointInfo vinfo = verificationcheckpoint.getVerificationcheckpointDetailByIdforDetail(cc);
                    request.setAttribute("VERIFICATIONSUBTYPE_DETAIL", vinfo);
                    return mapping.findForward("view_verificationcheckpoint");
                } else {
                    verificationcheckpoint.updateVerificationcheckpoint(info);
                    verificationcheckpoint.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 15, verificationcheckpointId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    request.setAttribute("VCSAVEMODEL", "Saved");
                    VerificationcheckpointInfo vinfo = verificationcheckpoint.getVerificationcheckpointDetailByIdforDetail(verificationcheckpointId);
                    request.setAttribute("VERIFICATIONSUBTYPE_DETAIL", vinfo);
                    return mapping.findForward("view_verificationcheckpoint");
                }
            }
        } else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
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
            ArrayList verificationcheckpointList = verificationcheckpoint.getVerificationcheckpointByName(search, statusIndex, next, count);
            int cnt = 0;
            if (verificationcheckpointList.size() > 0) {
                VerificationcheckpointInfo cinfo = (VerificationcheckpointInfo) verificationcheckpointList.get(verificationcheckpointList.size() - 1);
                cnt = cinfo.getVerificationcheckpointId();
                verificationcheckpointList.remove(verificationcheckpointList.size() - 1);
            }
            request.getSession().setAttribute("VERIFICATIONSUBTYPE_LIST", verificationcheckpointList);
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
            ArrayList verificationcheckpointList = verificationcheckpoint.getVerificationcheckpointByName(search, statusIndex, 0, count);
            int cnt = 0;
            if (verificationcheckpointList.size() > 0) {
                VerificationcheckpointInfo cinfo = (VerificationcheckpointInfo) verificationcheckpointList.get(verificationcheckpointList.size() - 1);
                cnt = cinfo.getVerificationcheckpointId();
                verificationcheckpointList.remove(verificationcheckpointList.size() - 1);
            }
            request.getSession().setAttribute("VERIFICATIONSUBTYPE_LIST", verificationcheckpointList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
