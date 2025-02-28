package com.web.jxp.doccheck;

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

public class DoccheckAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DoccheckForm frm = (DoccheckForm) form;
        Doccheck doccheck = new Doccheck();
        Validate vobj = new Validate();
        int count = doccheck.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        Collection statuses = doccheck.getStatuses();
        frm.setStatuses(statuses);
        int statusIndex = frm.getStatusIndex();
        frm.setStatusIndex(statusIndex);

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
        int check_user = doccheck.checkUserSession(request, 58, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = doccheck.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Doccheck Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        Collection clients = doccheck.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIndex = frm.getClientIndex();
        frm.setClientIndex(clientIndex);

        Collection assets = doccheck.getClientAsset(clientIndex, assetids, allclient, permission);
        frm.setAssets(assets);
        int assetIndex = frm.getAssetIndex();
        frm.setAssetIndex(assetIndex);
        
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setDoccheckId(-1);
            
            clients = doccheck.getClientsForAddEdit(cids, allclient, permission);
            frm.setClients(clients);
            int clientId = frm.getClientId();                
            assets = doccheck.getClientAssetForAddEdit(clientId, assetids, allclient, permission);
            frm.setAssets(assets);                
            
            saveToken(request);
            return mapping.findForward("add_doccheck");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            int doccheckId = frm.getDoccheckId();
            frm.setDoccheckId(doccheckId);
            DoccheckInfo info = doccheck.getDoccheckDetailById(doccheckId);
            if (info != null) 
            {
                frm.setDoccheckName(info.getDoccheckName());
                frm.setStatus(info.getStatus());
                frm.setClientId(info.getClientId());
                frm.setAssetId(info.getAssetId());                
                clients = doccheck.getClientsForAddEdit(cids, allclient, permission);
                frm.setClients(clients);
                clientIndex = frm.getClientIndex();
                frm.setClientIndex(clientIndex);
                
                assets = doccheck.getClientAssetForAddEdit(info.getClientId(), assetids, allclient, permission);
                frm.setAssets(assets);                
            }
            saveToken(request);
            return mapping.findForward("add_doccheck");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            int doccheckId = frm.getDoccheckId();
            frm.setDoccheckId(doccheckId);
            DoccheckInfo info = doccheck.getDoccheckDetailByIdforDetail(doccheckId);
            request.setAttribute("DOCCHECK_DETAIL", info);
            return mapping.findForward("view_doccheck");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int doccheckId = frm.getDoccheckId();
                String name = vobj.replacename(frm.getDoccheckName());
                int status = 1;
                int clientId = frm.getClientId();
                int assetId = frm.getAssetId();

                String ipAddrStr = request.getRemoteAddr();
                String iplocal = doccheck.getLocalIp();
                int ck = doccheck.checkDuplicacy(doccheckId, name, clientId, assetId);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Doc check already exists");
                    return mapping.findForward("add_doccheck");
                }
                DoccheckInfo info = new DoccheckInfo(doccheckId, name, clientId, assetId, status, uId);
                if (doccheckId <= 0) {
                    int cc = doccheck.createDoccheck(info);
                    if (cc > 0) {
                        doccheck.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 58, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    ArrayList doccheckList = doccheck.getDoccheckByName(search, 0, count, clientIndex, assetIndex,allclient,  permission,  cids,  assetids);
                    int cnt = 0;
                    if (doccheckList.size() > 0) {
                        DoccheckInfo cinfo = (DoccheckInfo) doccheckList.get(doccheckList.size() - 1);
                        cnt = cinfo.getDoccheckId();
                        doccheckList.remove(doccheckList.size() - 1);
                    }
                    request.getSession().setAttribute("DOCCHECK_LIST", doccheckList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    doccheck.updateDoccheck(info);
                    doccheck.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 58, doccheckId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList doccheckList = doccheck.getDoccheckByName(search, next, count, clientIndex, assetIndex,allclient,  permission,  cids,  assetids);
                    int cnt = 0;
                    if (doccheckList.size() > 0) {
                        DoccheckInfo cinfo = (DoccheckInfo) doccheckList.get(doccheckList.size() - 1);
                        cnt = cinfo.getDoccheckId();
                        doccheckList.remove(doccheckList.size() - 1);
                    }
                    request.getSession().setAttribute("DOCCHECK_LIST", doccheckList);
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
            ArrayList doccheckList = doccheck.getDoccheckByName(search, next, count, clientIndex, assetIndex,allclient,  permission,  cids,  assetids);
            int cnt = 0;
            if (doccheckList.size() > 0) {
                DoccheckInfo cinfo = (DoccheckInfo) doccheckList.get(doccheckList.size() - 1);
                cnt = cinfo.getDoccheckId();
                doccheckList.remove(doccheckList.size() - 1);
            }
            request.getSession().setAttribute("DOCCHECK_LIST", doccheckList);
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

            ArrayList doccheckList = doccheck.getDoccheckByName(search, 0, count, clientIndex, assetIndex,allclient,  permission,  cids,  assetids);
            int cnt = 0;
            if (doccheckList.size() > 0) {
                DoccheckInfo cinfo = (DoccheckInfo) doccheckList.get(doccheckList.size() - 1);
                cnt = cinfo.getDoccheckId();
                doccheckList.remove(doccheckList.size() - 1);
            }
            request.getSession().setAttribute("DOCCHECK_LIST", doccheckList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
