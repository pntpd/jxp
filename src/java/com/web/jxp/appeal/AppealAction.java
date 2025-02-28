package com.web.jxp.appeal;

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

public class AppealAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AppealForm frm = (AppealForm) form;
        Appeal appeal = new Appeal();
        Validate vobj = new Validate();
        int count = appeal.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0;
        String permission = "N";
        if (request.getSession().getAttribute("LOGININFO") != null) 
        {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) 
            {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
            }
        }
        int check_user = appeal.checkUserSession(request, 88, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = appeal.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Appeal Reason Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setAppealId(-1);
            saveToken(request);
            return mapping.findForward("add_appeal");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            int appealId = frm.getAppealId();
            frm.setAppealId(appealId);
            AppealInfo info = appeal.getAppealDetailById(appealId);
            if (info != null) {
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_appeal");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            int appealId = frm.getAppealId();
            frm.setAppealId(appealId);
            AppealInfo info = appeal.getAppealDetailByIdforDetail(appealId);
            request.setAttribute("APPEAL_DETAIL", info);
            return mapping.findForward("view_appeal");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int appealId = frm.getAppealId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = appeal.getLocalIp();
                int ck = appeal.checkDuplicacy(appealId, name);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Appeal Reason already exists");
                    return mapping.findForward("add_appeal");
                }
                AppealInfo info = new AppealInfo(appealId, name, status, uId);
                if (appealId <= 0) {
                    int cc = appeal.createAppeal(info);
                    if (cc > 0) {
                        appeal.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 88, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    ArrayList appealList = appeal.getAppealByName(search, 0, count);
                    int cnt = 0;
                    if (appealList.size() > 0) {
                        AppealInfo cinfo = (AppealInfo) appealList.get(appealList.size() - 1);
                        cnt = cinfo.getAppealId();
                        appealList.remove(appealList.size() - 1);
                    }
                    request.getSession().setAttribute("APPEAL_LIST", appealList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    appeal.updateAppeal(info);
                    appeal.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 88, appealId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList appealList = appeal.getAppealByName(search, next, count);
                    int cnt = 0;
                    if (appealList.size() > 0) {
                        AppealInfo cinfo = (AppealInfo) appealList.get(appealList.size() - 1);
                        cnt = cinfo.getAppealId();
                        appealList.remove(appealList.size() - 1);
                    }
                    request.getSession().setAttribute("APPEAL_LIST", appealList);
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
            ArrayList appealList = appeal.getAppealByName(search, next, count);
            int cnt = 0;
            if (appealList.size() > 0) {
                AppealInfo cinfo = (AppealInfo) appealList.get(appealList.size() - 1);
                cnt = cinfo.getAppealId();
                appealList.remove(appealList.size() - 1);
            }
            request.getSession().setAttribute("APPEAL_LIST", appealList);
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

            ArrayList appealList = appeal.getAppealByName(search, 0, count);
            int cnt = 0;
            if (appealList.size() > 0) {
                AppealInfo cinfo = (AppealInfo) appealList.get(appealList.size() - 1);
                cnt = cinfo.getAppealId();
                appealList.remove(appealList.size() - 1);
            }
            request.getSession().setAttribute("APPEAL_LIST", appealList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
