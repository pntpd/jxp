package com.web.jxp.appealreason;

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

public class AppealreasonAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AppealreasonForm frm = (AppealreasonForm) form;
        Appealreason appealreason = new Appealreason();
        Validate vobj = new Validate();
        int count = appealreason.getCount();
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
        int check_user = appealreason.checkUserSession(request, 89, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = appealreason.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Competency Result Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setAppealreasonId(-1);
            saveToken(request);
            return mapping.findForward("add_appealreason");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            int appealreasonId = frm.getAppealreasonId();
            frm.setAppealreasonId(appealreasonId);
            AppealreasonInfo info = appealreason.getAppealreasonDetailById(appealreasonId);
            if (info != null) {
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_appealreason");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            int appealreasonId = frm.getAppealreasonId();
            frm.setAppealreasonId(appealreasonId);
            AppealreasonInfo info = appealreason.getAppealreasonDetailByIdforDetail(appealreasonId);
            request.setAttribute("APPEALREASON_DETAIL", info);
            return mapping.findForward("view_appealreason");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int appealreasonId = frm.getAppealreasonId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = appealreason.getLocalIp();
                int ck = appealreason.checkDuplicacy(appealreasonId, name);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Competency Result already exists");
                    return mapping.findForward("add_appealreason");
                }
                AppealreasonInfo info = new AppealreasonInfo(appealreasonId, name, status, uId);
                if (appealreasonId <= 0) {
                    int cc = appealreason.createAppealreason(info);
                    if (cc > 0) {
                        appealreason.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 89, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    ArrayList appealreasonList = appealreason.getAppealreasonByName(search, 0, count);
                    int cnt = 0;
                    if (appealreasonList.size() > 0) {
                        AppealreasonInfo cinfo = (AppealreasonInfo) appealreasonList.get(appealreasonList.size() - 1);
                        cnt = cinfo.getAppealreasonId();
                        appealreasonList.remove(appealreasonList.size() - 1);
                    }
                    request.getSession().setAttribute("APPEALREASON_LIST", appealreasonList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    appealreason.updateAppealreason(info);
                    appealreason.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 89, appealreasonId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList appealreasonList = appealreason.getAppealreasonByName(search, next, count);
                    int cnt = 0;
                    if (appealreasonList.size() > 0) {
                        AppealreasonInfo cinfo = (AppealreasonInfo) appealreasonList.get(appealreasonList.size() - 1);
                        cnt = cinfo.getAppealreasonId();
                        appealreasonList.remove(appealreasonList.size() - 1);
                    }
                    request.getSession().setAttribute("APPEALREASON_LIST", appealreasonList);
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
            ArrayList appealreasonList = appealreason.getAppealreasonByName(search, next, count);
            int cnt = 0;
            if (appealreasonList.size() > 0) {
                AppealreasonInfo cinfo = (AppealreasonInfo) appealreasonList.get(appealreasonList.size() - 1);
                cnt = cinfo.getAppealreasonId();
                appealreasonList.remove(appealreasonList.size() - 1);
            }
            request.getSession().setAttribute("APPEALREASON_LIST", appealreasonList);
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

            ArrayList appealreasonList = appealreason.getAppealreasonByName(search, 0, count);
            int cnt = 0;
            if (appealreasonList.size() > 0) {
                AppealreasonInfo cinfo = (AppealreasonInfo) appealreasonList.get(appealreasonList.size() - 1);
                cnt = cinfo.getAppealreasonId();
                appealreasonList.remove(appealreasonList.size() - 1);
            }
            request.getSession().setAttribute("APPEALREASON_LIST", appealreasonList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
