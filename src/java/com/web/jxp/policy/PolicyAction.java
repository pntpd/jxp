package com.web.jxp.policy;

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
import java.util.Date;
import java.util.Stack;
import org.apache.struts.upload.FormFile;

public class PolicyAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PolicyForm frm = (PolicyForm) form;
        Policy policy = new Policy();
        Validate vobj = new Validate();
        int count = policy.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        Collection statuses = policy.getStatuses();
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
        int check_user = policy.checkUserSession(request, 95, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = policy.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Documents");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        Collection clients = policy.getClients(cids, allclient, permission);
        frm.setClients(clients);
        
        int clientIndex = frm.getClientIndex();
        frm.setClientIndex(clientIndex);

        Collection assets = policy.getClientAsset(clientIndex, assetids, allclient, permission);
        frm.setAssets(assets);
        int assetIndex = frm.getAssetIndex();
        frm.setAssetIndex(assetIndex);
        
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setPolicyId(-1);
            int clientId = frm.getClientId();
            clients = policy.getClientsForAddEdit(cids, allclient, permission);
            frm.setClients(clients);      
            assets = policy.getClientAssetForAddEdit(clientId, assetids, allclient, permission);
            frm.setAssets(assets); 
            
            saveToken(request);
            return mapping.findForward("add_policy");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            int policyId = frm.getPolicyId();
            frm.setPolicyId(policyId);
            
            PolicyInfo info = policy.getPolicyDetailById(policyId);
            if (info != null) 
            {
                frm.setPolicyName(info.getPolicyName());
                frm.setStatus(info.getStatus());
                frm.setClientId(info.getClientId());
                frm.setAssetId(info.getAssetId());                
                clients = policy.getClientsForAddEdit(cids, allclient, permission);
                frm.setClients(clients);
                clientIndex = frm.getClientIndex();
                frm.setClientIndex(clientIndex);                
                assets = policy.getClientAssetForAddEdit(info.getClientId(), assetids, allclient, permission);
                frm.setAssets(assets); 
                if (info.getFileName()!= null) {
                    frm.setFilehidden(info.getFileName());
                }
            }
            saveToken(request);
            return mapping.findForward("add_policy");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            int policyId = frm.getPolicyId();
            frm.setPolicyId(policyId);
            PolicyInfo info = policy.getPolicyDetailByIdforDetail(policyId);
            request.setAttribute("POLICY_DETAIL", info);
            return mapping.findForward("view_policy");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) 
            {
                resetToken(request);
                int policyId = frm.getPolicyId();
                String name = vobj.replacename(frm.getPolicyName());
                int status = 1;
                int clientId = frm.getClientId();
                int assetId = frm.getAssetId();

                String ipAddrStr = request.getRemoteAddr();
                String iplocal = policy.getLocalIp();
                int ck = policy.checkDuplicacy(policyId, name, clientId);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Document name already exists");
                    return mapping.findForward("add_policy");
                }
                String add_policy_file = policy.getMainPath("add_policyfile");
                String foldername = policy.createFolder(add_policy_file);
                Date now = new Date();
                String fn = String.valueOf(now.getTime());
                String fileName = "";
                FormFile policyfile = frm.getPolicyfile();
                if (policyfile != null && policyfile.getFileSize() > 0) 
                {
                    fileName = policy.uploadFile(policyId, frm.getFilehidden(), policyfile, fn + "_1", add_policy_file, foldername);
                }
                
                PolicyInfo info = new PolicyInfo(policyId, name, clientId, assetId, status, uId, fileName);
                if (policyId <= 0) {
                    int cc = policy.createPolicy(info);
                    if (cc > 0) {
                        policy.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 95, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    ArrayList policyList = policy.getPolicyByName(search, 0, count, clientIndex, assetIndex,allclient,  permission,  cids,  assetids);
                    int cnt = 0;
                    if (policyList.size() > 0) {
                        PolicyInfo cinfo = (PolicyInfo) policyList.get(policyList.size() - 1);
                        cnt = cinfo.getPolicyId();
                        policyList.remove(policyList.size() - 1);
                    }
                    request.getSession().setAttribute("POLICY_LIST", policyList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    policy.updatePolicy(info);
                    policy.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 95, policyId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList policyList = policy.getPolicyByName(search, next, count, clientIndex, assetIndex,allclient,  permission,  cids,  assetids);
                    int cnt = 0;
                    if (policyList.size() > 0) {
                        PolicyInfo cinfo = (PolicyInfo) policyList.get(policyList.size() - 1);
                        cnt = cinfo.getPolicyId();
                        policyList.remove(policyList.size() - 1);
                    }
                    request.getSession().setAttribute("POLICY_LIST", policyList);
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
            ArrayList policyList = policy.getPolicyByName(search, next, count, clientIndex, assetIndex,allclient,  permission,  cids,  assetids);
            int cnt = 0;
            if (policyList.size() > 0) {
                PolicyInfo cinfo = (PolicyInfo) policyList.get(policyList.size() - 1);
                cnt = cinfo.getPolicyId();
                policyList.remove(policyList.size() - 1);
            }
            request.getSession().setAttribute("POLICY_LIST", policyList);
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

            ArrayList policyList = policy.getPolicyByName(search, 0, count, clientIndex, assetIndex,allclient,  permission,  cids,  assetids);
            int cnt = 0;
            if (policyList.size() > 0) {
                PolicyInfo cinfo = (PolicyInfo) policyList.get(policyList.size() - 1);
                cnt = cinfo.getPolicyId();
                policyList.remove(policyList.size() - 1);
            }
            request.getSession().setAttribute("POLICY_LIST", policyList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
