package com.web.jxp.access;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import java.util.*;
import com.web.jxp.user.*;

public class AccessAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AccessForm frm = (AccessForm) form;
        Access access = new Access();
        
        int count = access.getCount();
        String search = frm.getSearch() != null ? frm.getSearch() : "";
        frm.setSearch(search);
        String fromDate = frm.getFromDate() != null ? frm.getFromDate() : "";
        String toDate = frm.getToDate() != null ? frm.getToDate() : "";
        if (fromDate.equals("")) {
            fromDate = "DD-MM-YYYY";
        }
        if (toDate.equals("")) {
            toDate = "DD-MM-YYYY";
        }
        frm.setFromDate(fromDate);
        frm.setToDate(toDate);
        int userId = 0;
        String permission = "N";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) {
                permission = uInfo.getPermission();
                userId = uInfo.getUserId();
            }
        }
        int check_user = access.checkUserSession(request, 3, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = access.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Access Report");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        } else {
            userId = check_user;
        }
        if (permission.equals("Y")) {
            userId = -1;
        }
        Collection moduleSegment = access.getModulesById();
        frm.setModuleCat(moduleSegment);

        if (frm.getCtp() == 0) {
            Stack<String> blist = new Stack<String>();
            if (request.getSession().getAttribute("BACKURL") != null) {
                blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
            }
            blist.push(request.getRequestURI());
            request.getSession().setAttribute("BACKURL", blist);
        }

        ArrayList accessList = access.getAccessListByName(search, userId, fromDate, toDate, 0, count, 0);
        int access_count_list = 0;
        if (accessList.size() > 0) {
            AccessInfo dinfo = (AccessInfo) accessList.get(accessList.size() - 1);
            access_count_list = dinfo.getAccessId();
            accessList.remove(accessList.size() - 1);
        }
        request.getSession().setAttribute("ACCESS_LIST", accessList);
        request.getSession().setAttribute("ACCESS_COUNT_LIST", access_count_list + "");
        request.getSession().setAttribute("NEXT", "0");
        request.getSession().setAttribute("NEXTVALUE", "1");
        return mapping.findForward("display");
    }
}
