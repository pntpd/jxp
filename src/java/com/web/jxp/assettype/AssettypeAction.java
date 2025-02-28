package com.web.jxp.assettype;

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

public class AssettypeAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
        AssettypeForm frm = (AssettypeForm) form;
        Assettype assettype = new Assettype();
        Validate vobj = new Validate();
        int count = assettype.getCount();
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
        int check_user = assettype.checkUserSession(request, 6, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = assettype.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Asset Type Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setAssettypeId(-1);
            saveToken(request);
            return mapping.findForward("add_assettype");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            int assettypeId = frm.getAssettypeId();
            frm.setAssettypeId(assettypeId);
            AssettypeInfo info = assettype.getAssettypeDetailById(assettypeId);
            if (info != null) {
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_assettype");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            int assettypeId = frm.getAssettypeId();
            frm.setAssettypeId(assettypeId);
            AssettypeInfo info = assettype.getAssettypeDetailByIdforDetail(assettypeId);
            request.setAttribute("ASSETTYPE_DETAIL", info);
            return mapping.findForward("view_assettype");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int assettypeId = frm.getAssettypeId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = assettype.getLocalIp();
                int ck = assettype.checkDuplicacy(assettypeId, name);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Asset Type already exists");
                    return mapping.findForward("add_assettype");
                }
                AssettypeInfo info = new AssettypeInfo(assettypeId, name, status, uId);
                if (assettypeId <= 0) {
                    int cc = assettype.createAssettype(info);
                    if (cc > 0) {
                        assettype.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 6, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");
                    }
                    ArrayList assettypeList = assettype.getAssettypeByName(search, 0, count);
                    int cnt = 0;
                    if (assettypeList.size() > 0) {
                        AssettypeInfo cinfo = (AssettypeInfo) assettypeList.get(assettypeList.size() - 1);
                        cnt = cinfo.getAssettypeId();
                        assettypeList.remove(assettypeList.size() - 1);
                    }
                    request.getSession().setAttribute("ASSETTYPE_LIST", assettypeList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    assettype.updateAssettype(info);
                    assettype.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 6, assettypeId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList assettypeList = assettype.getAssettypeByName(search, next, count);
                    int cnt = 0;
                    if (assettypeList.size() > 0) {
                        AssettypeInfo cinfo = (AssettypeInfo) assettypeList.get(assettypeList.size() - 1);
                        cnt = cinfo.getAssettypeId();
                        assettypeList.remove(assettypeList.size() - 1);
                    }
                    request.getSession().setAttribute("ASSETTYPE_LIST", assettypeList);
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
            ArrayList assettypeList = assettype.getAssettypeByName(search, next, count);
            int cnt = 0;
            if (assettypeList.size() > 0) {
                AssettypeInfo cinfo = (AssettypeInfo) assettypeList.get(assettypeList.size() - 1);
                cnt = cinfo.getAssettypeId();
                assettypeList.remove(assettypeList.size() - 1);
            }
            request.getSession().setAttribute("ASSETTYPE_LIST", assettypeList);
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

            ArrayList assettypeList = assettype.getAssettypeByName(search, 0, count);
            int cnt = 0;
            if (assettypeList.size() > 0) {
                AssettypeInfo cinfo = (AssettypeInfo) assettypeList.get(assettypeList.size() - 1);
                cnt = cinfo.getAssettypeId();
                assettypeList.remove(assettypeList.size() - 1);
            }
            request.getSession().setAttribute("ASSETTYPE_LIST", assettypeList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
