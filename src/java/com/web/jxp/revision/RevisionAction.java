package com.web.jxp.revision;

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
import java.util.Stack;

public class RevisionAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RevisionForm frm = (RevisionForm) form;
        Revision revision = new Revision();
        Validate vobj = new Validate();
        int count = revision.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0;
        String permission = "N";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
            }
        }
        int check_user = revision.checkUserSession(request, 79, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = revision.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Revision Reason Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setRevisionId(-1);
            saveToken(request);
            return mapping.findForward("add_revision");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            int revisionId = frm.getRevisionId();
            frm.setRevisionId(revisionId);
            RevisionInfo info = revision.getRevisionDetailById(revisionId);
            if (info != null) {
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_revision");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            int revisionId = frm.getRevisionId();
            frm.setRevisionId(revisionId);
            RevisionInfo info = revision.getRevisionDetailByIdforDetail(revisionId);
            request.setAttribute("REVISION_DETAIL", info);
            return mapping.findForward("view_revision");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int revisionId = frm.getRevisionId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = revision.getLocalIp();
                int ck = revision.checkDuplicacy(revisionId, name);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Revision Reason already exists");
                    return mapping.findForward("add_revision");
                }
                RevisionInfo info = new RevisionInfo(revisionId, name, status, uId);
                if (revisionId <= 0) {
                    int cc = revision.createRevision(info);
                    if (cc > 0) {
                        revision.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 79, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    ArrayList revisionList = revision.getRevisionByName(search, 0, count);
                    int cnt = 0;
                    if (revisionList.size() > 0) {
                        RevisionInfo cinfo = (RevisionInfo) revisionList.get(revisionList.size() - 1);
                        cnt = cinfo.getRevisionId();
                        revisionList.remove(revisionList.size() - 1);
                    }
                    request.getSession().setAttribute("REVISION_LIST", revisionList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    revision.updateRevision(info);
                    revision.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 79, revisionId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList revisionList = revision.getRevisionByName(search, next, count);
                    int cnt = 0;
                    if (revisionList.size() > 0) {
                        RevisionInfo cinfo = (RevisionInfo) revisionList.get(revisionList.size() - 1);
                        cnt = cinfo.getRevisionId();
                        revisionList.remove(revisionList.size() - 1);
                    }
                    request.getSession().setAttribute("REVISION_LIST", revisionList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", next + "");
                    request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");

                    return mapping.findForward("display");
                }
            }
        } else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            print(this, "doCancel block");
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList revisionList = revision.getRevisionByName(search, next, count);
            int cnt = 0;
            if (revisionList.size() > 0) {
                RevisionInfo cinfo = (RevisionInfo) revisionList.get(revisionList.size() - 1);
                cnt = cinfo.getRevisionId();
                revisionList.remove(revisionList.size() - 1);
            }
            request.getSession().setAttribute("REVISION_LIST", revisionList);
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

            ArrayList revisionList = revision.getRevisionByName(search, 0, count);
            int cnt = 0;
            if (revisionList.size() > 0) {
                RevisionInfo cinfo = (RevisionInfo) revisionList.get(revisionList.size() - 1);
                cnt = cinfo.getRevisionId();
                revisionList.remove(revisionList.size() - 1);
            }
            request.getSession().setAttribute("REVISION_LIST", revisionList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
