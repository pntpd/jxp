package com.web.jxp.appealrejection;

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

public class AppealrejectionAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AppealrejectionForm frm = (AppealrejectionForm) form;
        Appealrejection appealrejection = new Appealrejection();
        Validate vobj = new Validate();
        int count = appealrejection.getCount();
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
        int check_user = appealrejection.checkUserSession(request, 91, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = appealrejection.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Appeal Rejection Reason Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setAppealrejectionId(-1);
            saveToken(request);
            return mapping.findForward("add_appealrejection");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            int appealrejectionId = frm.getAppealrejectionId();
            frm.setAppealrejectionId(appealrejectionId);
            AppealrejectionInfo info = appealrejection.getAppealrejectionDetailById(appealrejectionId);
            if (info != null) {
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_appealrejection");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            int appealrejectionId = frm.getAppealrejectionId();
            frm.setAppealrejectionId(appealrejectionId);
            AppealrejectionInfo info = appealrejection.getAppealrejectionDetailByIdforDetail(appealrejectionId);
            request.setAttribute("APPEALREJECTION_DETAIL", info);
            return mapping.findForward("view_appealrejection");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int appealrejectionId = frm.getAppealrejectionId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = appealrejection.getLocalIp();
                int ck = appealrejection.checkDuplicacy(appealrejectionId, name);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Appeal Rejection Reason already exists");
                    return mapping.findForward("add_appealrejection");
                }
                AppealrejectionInfo info = new AppealrejectionInfo(appealrejectionId, name, status, uId);
                if (appealrejectionId <= 0) {
                    int cc = appealrejection.createAppealrejection(info);
                    if (cc > 0) {
                        appealrejection.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 91, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    ArrayList appealrejectionList = appealrejection.getAppealrejectionByName(search, 0, count);
                    int cnt = 0;
                    if (appealrejectionList.size() > 0) {
                        AppealrejectionInfo cinfo = (AppealrejectionInfo) appealrejectionList.get(appealrejectionList.size() - 1);
                        cnt = cinfo.getAppealrejectionId();
                        appealrejectionList.remove(appealrejectionList.size() - 1);
                    }
                    request.getSession().setAttribute("APPEALREJECTION_LIST", appealrejectionList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    appealrejection.updateAppealrejection(info);
                    appealrejection.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 91, appealrejectionId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList appealrejectionList = appealrejection.getAppealrejectionByName(search, next, count);
                    int cnt = 0;
                    if (appealrejectionList.size() > 0) {
                        AppealrejectionInfo cinfo = (AppealrejectionInfo) appealrejectionList.get(appealrejectionList.size() - 1);
                        cnt = cinfo.getAppealrejectionId();
                        appealrejectionList.remove(appealrejectionList.size() - 1);
                    }
                    request.getSession().setAttribute("APPEALREJECTION_LIST", appealrejectionList);
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
            ArrayList appealrejectionList = appealrejection.getAppealrejectionByName(search, next, count);
            int cnt = 0;
            if (appealrejectionList.size() > 0) {
                AppealrejectionInfo cinfo = (AppealrejectionInfo) appealrejectionList.get(appealrejectionList.size() - 1);
                cnt = cinfo.getAppealrejectionId();
                appealrejectionList.remove(appealrejectionList.size() - 1);
            }
            request.getSession().setAttribute("APPEALREJECTION_LIST", appealrejectionList);
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

            ArrayList appealrejectionList = appealrejection.getAppealrejectionByName(search, 0, count);
            int cnt = 0;
            if (appealrejectionList.size() > 0) {
                AppealrejectionInfo cinfo = (AppealrejectionInfo) appealrejectionList.get(appealrejectionList.size() - 1);
                cnt = cinfo.getAppealrejectionId();
                appealrejectionList.remove(appealrejectionList.size() - 1);
            }
            request.getSession().setAttribute("APPEALREJECTION_LIST", appealrejectionList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
