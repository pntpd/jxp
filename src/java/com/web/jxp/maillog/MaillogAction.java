package com.web.jxp.maillog;

import static com.web.jxp.common.Common.print;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import java.util.*;
import com.web.jxp.user.*;

public class MaillogAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        MaillogForm frm = (MaillogForm) form;
        Maillog maillog = new Maillog();

        int count = maillog.getCount();
        String search = frm.getSearch() != null ? frm.getSearch() : "";
        frm.setSearch(search);
        String fromDate = frm.getFromDate() != null ? frm.getFromDate() : "";
        String toDate = frm.getToDate() != null ? frm.getToDate() : "";
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
        int check_user = maillog.checkUserSession(request, 17, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = maillog.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Mail Log Report");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        } else {
            userId = check_user;
        }

        if (permission.equals("Y")) {
            userId = -1;
        }
        if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            print(this, "getDoView block.");
            
            int maillogId = frm.getMaillogId();
            frm.setMaillogId(maillogId);
            MaillogInfo info = maillog.getMaillogDetailByIdforDetail(maillogId);
            request.setAttribute("MAILLOG_DETAIL", info);
            return mapping.findForward("view_maillog");
        }
        else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            print(this, "doCancel block");
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
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

            ArrayList list = maillog.getMaillogListByName(search, fromDate, toDate, 0, count);
                int count_list = 0;
                if (list.size() > 0) {
                    MaillogInfo dinfo = (MaillogInfo) list.get(list.size() - 1);
                    count_list = dinfo.getMaillogId();
                    list.remove(list.size() - 1);
            }
            request.getSession().setAttribute("MAILLOG_LIST", list);
            request.getSession().setAttribute("MAILLOGCOUNT_LIST", count_list + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
