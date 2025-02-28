package com.web.jxp.remark;

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

public class RemarkAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
        RemarkForm frm = (RemarkForm) form;
        Remark remark = new Remark();
        Validate vobj = new Validate();
        int count = remark.getCount();
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
        int check_user = remark.checkUserSession(request, 96, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = remark.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Remark Type Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setRemarkId(-1);
            saveToken(request);
            return mapping.findForward("add_remark");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            int remarkId = frm.getRemarkId();
            frm.setRemarkId(remarkId);
            RemarkInfo info = remark.getRemarkDetailById(remarkId);
            if (info != null) {
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_remark");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            int remarkId = frm.getRemarkId();
            frm.setRemarkId(remarkId);
            RemarkInfo info = remark.getRemarkDetailByIdforDetail(remarkId);
            request.setAttribute("REMARK_DETAIL", info);
            return mapping.findForward("view_remark");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int remarkId = frm.getRemarkId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = remark.getLocalIp();
                int ck = remark.checkDuplicacy(remarkId, name);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Remark Type already exists");
                    return mapping.findForward("add_remark");
                }
                RemarkInfo info = new RemarkInfo(remarkId, name, status, uId);
                if (remarkId <= 0) {
                    int cc = remark.createRemark(info);
                    if (cc > 0) {
                        remark.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 96, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    ArrayList remarkList = remark.getRemarkByName(search, 0, count);
                    int cnt = 0;
                    if (remarkList.size() > 0) {
                        RemarkInfo cinfo = (RemarkInfo) remarkList.get(remarkList.size() - 1);
                        cnt = cinfo.getRemarkId();
                        remarkList.remove(remarkList.size() - 1);
                    }
                    request.getSession().setAttribute("REMARK_LIST", remarkList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    remark.updateRemark(info);
                    remark.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 96, remarkId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList remarkList = remark.getRemarkByName(search, next, count);
                    int cnt = 0;
                    if (remarkList.size() > 0) {
                        RemarkInfo cinfo = (RemarkInfo) remarkList.get(remarkList.size() - 1);
                        cnt = cinfo.getRemarkId();
                        remarkList.remove(remarkList.size() - 1);
                    }
                    request.getSession().setAttribute("REMARK_LIST", remarkList);
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
            ArrayList remarkList = remark.getRemarkByName(search, next, count);
            int cnt = 0;
            if (remarkList.size() > 0) {
                RemarkInfo cinfo = (RemarkInfo) remarkList.get(remarkList.size() - 1);
                cnt = cinfo.getRemarkId();
                remarkList.remove(remarkList.size() - 1);
            }
            request.getSession().setAttribute("REMARK_LIST", remarkList);
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

            ArrayList remarkList = remark.getRemarkByName(search, 0, count);
            int cnt = 0;
            if (remarkList.size() > 0) {
                RemarkInfo cinfo = (RemarkInfo) remarkList.get(remarkList.size() - 1);
                cnt = cinfo.getRemarkId();
                remarkList.remove(remarkList.size() - 1);
            }
            request.getSession().setAttribute("REMARK_LIST", remarkList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
