package com.web.jxp.ppetype;

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

public class PpetypeAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PpetypeForm frm = (PpetypeForm) form;
        Ppetype ppetype = new Ppetype();
        Validate vobj = new Validate();
        int count = ppetype.getCount();
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
        int check_user = ppetype.checkUserSession(request, 97, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = ppetype.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "PPE Master");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setPpetypeId(-1);
            saveToken(request);
            return mapping.findForward("add_ppetype");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            int ppetypeId = frm.getPpetypeId();
            frm.setPpetypeId(ppetypeId);
            PpetypeInfo info = ppetype.getPpetypeDetailById(ppetypeId);
            if (info != null) {
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
                frm.setTypeId(info.getTypeId());
                frm.setAddvalue(info.getAddvalue());
            }
            saveToken(request);
            return mapping.findForward("add_ppetype");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            int ppetypeId = frm.getPpetypeId();
            frm.setPpetypeId(ppetypeId);
            PpetypeInfo info = ppetype.getPpetypeDetailByIdforDetail(ppetypeId);
            request.setAttribute("PPETYPE_DETAIL", info);
            return mapping.findForward("view_ppetype");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int ppetypeId = frm.getPpetypeId();
                String name = vobj.replacename(frm.getName());
                int typeId = frm.getTypeId();
                String addvalue = vobj.replacedesc(frm.getAddvalue());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = ppetype.getLocalIp();
                int ck = ppetype.checkDuplicacy(ppetypeId, name);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "PPE already exists");
                    return mapping.findForward("add_ppetype");
                }
                PpetypeInfo info = new PpetypeInfo(ppetypeId, name, status, uId, typeId, addvalue);
                if (ppetypeId <= 0) {
                    int cc = ppetype.createPpetype(info);
                    if (cc > 0) {
                        ppetype.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 97, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    ArrayList ppetypeList = ppetype.getPpetypeByName(search, 0, count);
                    int cnt = 0;
                    if (ppetypeList.size() > 0) {
                        PpetypeInfo cinfo = (PpetypeInfo) ppetypeList.get(ppetypeList.size() - 1);
                        cnt = cinfo.getPpetypeId();
                        ppetypeList.remove(ppetypeList.size() - 1);
                    }
                    request.getSession().setAttribute("PPETYPE_LIST", ppetypeList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    ppetype.updatePpetype(info);
                    ppetype.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 97, ppetypeId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList ppetypeList = ppetype.getPpetypeByName(search, next, count);
                    int cnt = 0;
                    if (ppetypeList.size() > 0) {
                        PpetypeInfo cinfo = (PpetypeInfo) ppetypeList.get(ppetypeList.size() - 1);
                        cnt = cinfo.getPpetypeId();
                        ppetypeList.remove(ppetypeList.size() - 1);
                    }
                    request.getSession().setAttribute("PPETYPE_LIST", ppetypeList);
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
            ArrayList ppetypeList = ppetype.getPpetypeByName(search, next, count);
            int cnt = 0;
            if (ppetypeList.size() > 0) {
                PpetypeInfo cinfo = (PpetypeInfo) ppetypeList.get(ppetypeList.size() - 1);
                cnt = cinfo.getPpetypeId();
                ppetypeList.remove(ppetypeList.size() - 1);
            }
            request.getSession().setAttribute("PPETYPE_LIST", ppetypeList);
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

            ArrayList ppetypeList = ppetype.getPpetypeByName(search, 0, count);
            int cnt = 0;
            if (ppetypeList.size() > 0) {
                PpetypeInfo cinfo = (PpetypeInfo) ppetypeList.get(ppetypeList.size() - 1);
                cnt = cinfo.getPpetypeId();
                ppetypeList.remove(ppetypeList.size() - 1);
            }
            request.getSession().setAttribute("PPETYPE_LIST", ppetypeList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
