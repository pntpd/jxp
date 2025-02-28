package com.web.jxp.invoicetemplate;

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

public class InvoicetemplateAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
        InvoicetemplateForm frm = (InvoicetemplateForm) form;
        Invoicetemplate invoicetemplate = new Invoicetemplate();
        Validate vobj = new Validate();
        int count = invoicetemplate.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0, allclient = 0;
        String permission = "N", cids = "", assetids = "";
        if (request.getSession().getAttribute("LOGININFO") != null) 
        {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null)
            {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
                cids = uInfo.getCids();
                allclient = uInfo.getAllclient();
                assetids = uInfo.getAssetids();
            }
        }
        int check_user = invoicetemplate.checkUserSession(request, 80, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = invoicetemplate.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Invoice Template Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        } 
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) 
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setInvoicetemplateId(-1);
            Collection clients = invoicetemplate.getClients(cids, allclient, permission);
            frm.setClients(clients);
            saveToken(request);
            return mapping.findForward("add_invoicetemplate");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            int invoicetemplateId = frm.getInvoicetemplateId();
            frm.setInvoicetemplateId(invoicetemplateId);
            InvoicetemplateInfo info = invoicetemplate.getInvoicetemplateDetailById(invoicetemplateId);
            if (info != null) {
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
                
                Collection clients = invoicetemplate.getClients(cids, allclient, permission);
                frm.setClients(clients);
                
                frm.setClientId(info.getClientId());
                frm.setDescription(info.getDescription() != null ? info.getDescription() : "");
            }
            saveToken(request);
            return mapping.findForward("add_invoicetemplate");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            int invoicetemplateId = frm.getInvoicetemplateId();
            frm.setInvoicetemplateId(invoicetemplateId);
            InvoicetemplateInfo info = invoicetemplate.getInvoicetemplateDetailByIdforDetail(invoicetemplateId);
            request.setAttribute("INVOICETEMPLATE_DETAIL", info);
            return mapping.findForward("view_invoicetemplate");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int invoicetemplateId = frm.getInvoicetemplateId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                int clientId = frm.getClientId();
                String description = frm.getDescription();                
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = invoicetemplate.getLocalIp();
                int ck = invoicetemplate.checkDuplicacy(invoicetemplateId, name, clientId);
                if (ck == 1) 
                {
                    saveToken(request);
                    Collection clients = invoicetemplate.getClients(cids, allclient, permission);
                    frm.setClients(clients);
                    request.setAttribute("MESSAGE", "Resume template already exists");
                    return mapping.findForward("add_invoicetemplate");
                }
                InvoicetemplateInfo info = new InvoicetemplateInfo(invoicetemplateId, name, clientId, "", description, status, uId); 
                if (invoicetemplateId <= 0) {
                    int cc = invoicetemplate.createInvoicetemplate(info);
                    if (cc > 0) {
                        invoicetemplate.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 80, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    ArrayList invoicetemplateList = invoicetemplate.getInvoicetemplateByName(search, 0, count,allclient, permission, cids, assetids);
                    int cnt = 0;
                    if (invoicetemplateList.size() > 0) {
                        InvoicetemplateInfo cinfo = (InvoicetemplateInfo) invoicetemplateList.get(invoicetemplateList.size() - 1);
                        cnt = cinfo.getInvoicetemplateId();
                        invoicetemplateList.remove(invoicetemplateList.size() - 1);
                    }
                    request.getSession().setAttribute("INVOICETEMPLATE_LIST", invoicetemplateList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    invoicetemplate.updateInvoicetemplate(info);
                    invoicetemplate.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 80, invoicetemplateId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList invoicetemplateList = invoicetemplate.getInvoicetemplateByName(search, next, count,allclient, permission, cids, assetids);
                    int cnt = 0;
                    if (invoicetemplateList.size() > 0) {
                        InvoicetemplateInfo cinfo = (InvoicetemplateInfo) invoicetemplateList.get(invoicetemplateList.size() - 1);
                        cnt = cinfo.getInvoicetemplateId();
                        invoicetemplateList.remove(invoicetemplateList.size() - 1);
                    }
                    request.getSession().setAttribute("INVOICETEMPLATE_LIST", invoicetemplateList);
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
            ArrayList invoicetemplateList = invoicetemplate.getInvoicetemplateByName(search, next, count,allclient, permission, cids, assetids);
            int cnt = 0;
            if (invoicetemplateList.size() > 0) {
                InvoicetemplateInfo cinfo = (InvoicetemplateInfo) invoicetemplateList.get(invoicetemplateList.size() - 1);
                cnt = cinfo.getInvoicetemplateId();
                invoicetemplateList.remove(invoicetemplateList.size() - 1);
            }
            request.getSession().setAttribute("INVOICETEMPLATE_LIST", invoicetemplateList);
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

            ArrayList invoicetemplateList = invoicetemplate.getInvoicetemplateByName(search, 0, count,allclient, permission, cids, assetids);
            int cnt = 0;
            if (invoicetemplateList.size() > 0) {
                InvoicetemplateInfo cinfo = (InvoicetemplateInfo) invoicetemplateList.get(invoicetemplateList.size() - 1);
                cnt = cinfo.getInvoicetemplateId();
                invoicetemplateList.remove(invoicetemplateList.size() - 1);
            }
            request.getSession().setAttribute("INVOICETEMPLATE_LIST", invoicetemplateList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
