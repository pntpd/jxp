package com.web.jxp.timezone;

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

public class TimezoneAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimezoneForm frm = (TimezoneForm) form;
        Timezone timezone = new Timezone();
        Validate vobj = new Validate();
        Collection countrys = timezone.getCountry();
        frm.setCountrys(countrys);

        int count = timezone.getCount();
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
        int check_user = timezone.checkUserSession(request, 16, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = timezone.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Timezone Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setTimezoneId(-1);
            saveToken(request);
            return mapping.findForward("add_timezone");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            int timezoneId = frm.getTimezoneId();
            frm.setTimezoneId(timezoneId);
            TimezoneInfo info = timezone.getTimezoneDetailById(timezoneId);
            if (info != null) {
                frm.setTimezoneName(info.getTimezoneName());
                frm.setStatus(info.getStatus());

                Collection countryss = timezone.getCountry();
                frm.setCountrys(countryss);
                frm.setCountryId(info.getCountryId());
                frm.setTimezoneCode(info.getTimezoneCode());
            }
            saveToken(request);
            return mapping.findForward("add_timezone");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            int timezoneId = frm.getTimezoneId();
            frm.setTimezoneId(timezoneId);
            TimezoneInfo info = timezone.getTimezoneDetailByIdforDetail(timezoneId);
            request.setAttribute("TIMEZONE_DETAIL", info);
            return mapping.findForward("view_timezone");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int timezoneId = frm.getTimezoneId();
                String name = vobj.replacedesc(frm.getTimezoneName());
                String code = vobj.replacename(frm.getTimezoneCode());
                int status = 1;
                int countryId = frm.getCountryId();

                String ipAddrStr = request.getRemoteAddr();
                String iplocal = timezone.getLocalIp();
                int ck = timezone.checkDuplicacy(timezoneId, name, countryId);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Timezone already exists");
                    return mapping.findForward("add_timezone");
                }
                TimezoneInfo info = new TimezoneInfo(timezoneId, name, code, countryId, status, uId);
                if (timezoneId <= 0) {
                    int cc = timezone.createTimezone(info);
                    if (cc > 0) {
                        timezone.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 16, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    ArrayList timezoneList = timezone.getTimezoneByName(search, 0, count);
                    int cnt = 0;
                    if (timezoneList.size() > 0) {
                        TimezoneInfo cinfo = (TimezoneInfo) timezoneList.get(timezoneList.size() - 1);
                        cnt = cinfo.getTimezoneId();
                        timezoneList.remove(timezoneList.size() - 1);
                    }
                    request.getSession().setAttribute("TIMEZONE_LIST", timezoneList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    timezone.updateTimezone(info);
                    timezone.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 16, timezoneId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList timezoneList = timezone.getTimezoneByName(search, next, count);
                    int cnt = 0;
                    if (timezoneList.size() > 0) {
                        TimezoneInfo cinfo = (TimezoneInfo) timezoneList.get(timezoneList.size() - 1);
                        cnt = cinfo.getTimezoneId();
                        timezoneList.remove(timezoneList.size() - 1);
                    }
                    request.getSession().setAttribute("TIMEZONE_LIST", timezoneList);
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
            ArrayList timezoneList = timezone.getTimezoneByName(search, next, count);
            int cnt = 0;
            if (timezoneList.size() > 0) {
                TimezoneInfo cinfo = (TimezoneInfo) timezoneList.get(timezoneList.size() - 1);
                cnt = cinfo.getTimezoneId();
                timezoneList.remove(timezoneList.size() - 1);
            }
            request.getSession().setAttribute("TIMEZONE_LIST", timezoneList);
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
            ArrayList timezoneList = timezone.getTimezoneByName(search, 0, count);
            int cnt = 0;
            if (timezoneList.size() > 0) {
                TimezoneInfo cinfo = (TimezoneInfo) timezoneList.get(timezoneList.size() - 1);
                cnt = cinfo.getTimezoneId();
                timezoneList.remove(timezoneList.size() - 1);
            }
            request.getSession().setAttribute("TIMEZONE_LIST", timezoneList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
