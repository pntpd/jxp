package com.web.jxp.onboardingkit;

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

public class OnboardingkitAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
        OnboardingkitForm frm = (OnboardingkitForm) form;
        Onboardingkit onboardingkit = new Onboardingkit();
        Validate vobj = new Validate();
        int count = onboardingkit.getCount();
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
        int check_user = onboardingkit.checkUserSession(request, 54, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = onboardingkit.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Onboarding Kit Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setOnboardingkitId(-1);
            saveToken(request);
            return mapping.findForward("add_onboardingkit");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            int onboardingkitId = frm.getOnboardingkitId();
            frm.setOnboardingkitId(onboardingkitId);
            OnboardingkitInfo info = onboardingkit.getOnboardingkitDetailById(onboardingkitId);
            if (info != null) {
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_onboardingkit");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            int onboardingkitId = frm.getOnboardingkitId();
            frm.setOnboardingkitId(onboardingkitId);
            OnboardingkitInfo info = onboardingkit.getOnboardingkitDetailByIdforDetail(onboardingkitId);
            request.setAttribute("ONBOARDINGKIT_DETAIL", info);
            return mapping.findForward("view_onboardingkit");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int onboardingkitId = frm.getOnboardingkitId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = onboardingkit.getLocalIp();
                int ck = onboardingkit.checkDuplicacy(onboardingkitId, name);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Onboarding Kit already exists");
                    return mapping.findForward("add_onboardingkit");
                }
                OnboardingkitInfo info = new OnboardingkitInfo(onboardingkitId, name, status, uId);
                if (onboardingkitId <= 0) {
                    int cc = onboardingkit.createOnboardingkit(info);
                    if (cc > 0) {
                        onboardingkit.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 54, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    ArrayList onboardingkitList = onboardingkit.getOnboardingkitByName(search, 0, count);
                    int cnt = 0;
                    if (onboardingkitList.size() > 0) {
                        OnboardingkitInfo cinfo = (OnboardingkitInfo) onboardingkitList.get(onboardingkitList.size() - 1);
                        cnt = cinfo.getOnboardingkitId();
                        onboardingkitList.remove(onboardingkitList.size() - 1);
                    }
                    request.getSession().setAttribute("ONBOARDINGKIT_LIST", onboardingkitList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    onboardingkit.updateOnboardingkit(info);
                    onboardingkit.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 54, onboardingkitId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList onboardingkitList = onboardingkit.getOnboardingkitByName(search, next, count);
                    int cnt = 0;
                    if (onboardingkitList.size() > 0) {
                        OnboardingkitInfo cinfo = (OnboardingkitInfo) onboardingkitList.get(onboardingkitList.size() - 1);
                        cnt = cinfo.getOnboardingkitId();
                        onboardingkitList.remove(onboardingkitList.size() - 1);
                    }
                    request.getSession().setAttribute("ONBOARDINGKIT_LIST", onboardingkitList);
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
            ArrayList onboardingkitList = onboardingkit.getOnboardingkitByName(search, next, count);
            int cnt = 0;
            if (onboardingkitList.size() > 0) {
                OnboardingkitInfo cinfo = (OnboardingkitInfo) onboardingkitList.get(onboardingkitList.size() - 1);
                cnt = cinfo.getOnboardingkitId();
                onboardingkitList.remove(onboardingkitList.size() - 1);
            }
            request.getSession().setAttribute("ONBOARDINGKIT_LIST", onboardingkitList);
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

            ArrayList onboardingkitList = onboardingkit.getOnboardingkitByName(search, 0, count);
            int cnt = 0;
            if (onboardingkitList.size() > 0) {
                OnboardingkitInfo cinfo = (OnboardingkitInfo) onboardingkitList.get(onboardingkitList.size() - 1);
                cnt = cinfo.getOnboardingkitId();
                onboardingkitList.remove(onboardingkitList.size() - 1);
            }
            request.getSession().setAttribute("ONBOARDINGKIT_LIST", onboardingkitList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
