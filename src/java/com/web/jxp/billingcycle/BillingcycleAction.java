package com.web.jxp.billingcycle;

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

public class BillingcycleAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BillingcycleForm frm = (BillingcycleForm) form;
        Billingcycle billingcycle = new Billingcycle();
        int count = billingcycle.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0, allclient = 0;
        String permission = "N", cids = "", assetids = "";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
                cids = uInfo.getCids();
                allclient = uInfo.getAllclient();
                assetids = uInfo.getAssetids();
            }
        }
        Collection clients = billingcycle.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);

        Collection assets = billingcycle.getClientAsset(clientIdIndex, assetids, allclient, permission);
        frm.setAssets(assets);

        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);
        int check_user = billingcycle.checkUserSession(request, 75, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = billingcycle.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Configure Billingcycle Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int assetId = frm.getAssetId();
            frm.setAssetId(assetId);
            BillingcycleInfo info = billingcycle.getBillingcycleDetailByIdforDetail(clientId,assetId);
            if(info.getType()>0)
            {
                frm.setRepeatId(info.getType());
                frm.setSchedulevalue(info.getTypevalue());
            }
            request.getSession().setAttribute("BILLINGCYCLE_DETAIL", info);
            saveToken(request);
            return mapping.findForward("add_billingcycle");
        }  else if (frm.getDoDeleteDetail() != null && frm.getDoDeleteDetail().equals("yes")) {
            frm.setDoDeleteDetail("no");
            int clientId = frm.getClientId();
            int assetId = frm.getAssetId();
            int status = frm.getStatus();
            billingcycle.deleteBillingcycleDetail(clientId, assetId, uId, status);
            
            ArrayList billingcycleList = billingcycle.getBillingcycleByName(search, 0, count, allclient, permission, cids, assetids,clientIdIndex, assetIdIndex);
            int cnt = 0;
            if (billingcycleList.size() > 0) {
                BillingcycleInfo cinfo = (BillingcycleInfo) billingcycleList.get(billingcycleList.size() - 1);
                cnt = cinfo.getClientId();
                billingcycleList.remove(billingcycleList.size() - 1);
            }
            request.getSession().setAttribute("BILLINGCYCLE_LIST", billingcycleList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
            return mapping.findForward("display");
            
        } else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            frm.setDoCancel("no");
            print(this, "doCancel block");
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList billingcycleList = billingcycle.getBillingcycleByName(search, 0, count, allclient, permission, cids, assetids,clientIdIndex, assetIdIndex);
            int cnt = 0;
            if (billingcycleList.size() > 0) {
                BillingcycleInfo cinfo = (BillingcycleInfo) billingcycleList.get(billingcycleList.size() - 1);
                cnt = cinfo.getClientId();
                billingcycleList.remove(billingcycleList.size() - 1);
            }
            request.getSession().setAttribute("BILLINGCYCLE_LIST", billingcycleList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display");
        }  else if (frm.getDoSaveCourse() != null && frm.getDoSaveCourse().equals("yes")) {
            frm.setDoSaveCourse("no");
            int clientId = frm.getClientId();
            int assetId = frm.getAssetId();
            int type = frm.getRepeatId();
            String repeatValue = frm.getSchedulevalue();
            int billingcycleId =  billingcycle.getBillingcycleId(assetId);
            if(billingcycleId <= 0)
            {
                billingcycle.createBillingcycle(type,repeatValue, uId, clientId, assetId);
            }
            else
            {
                billingcycle.updateBillingcycleDetail(type,repeatValue, uId, clientId, assetId);
            }
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }            
            ArrayList billingcycleList = billingcycle.getBillingcycleByName(search, next, count, allclient, permission, cids, assetids,clientIdIndex, assetIdIndex);
            int cnt = 0;
            if (billingcycleList.size() > 0) {
                BillingcycleInfo cinfo = (BillingcycleInfo) billingcycleList.get(billingcycleList.size() - 1);
                cnt = cinfo.getClientId();
                billingcycleList.remove(billingcycleList.size() - 1);
            }
            request.getSession().setAttribute("BILLINGCYCLE_LIST", billingcycleList);
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

            ArrayList billingcycleList = billingcycle.getBillingcycleByName(search, 0, count, allclient, permission, cids, assetids,clientIdIndex, assetIdIndex);
            int cnt = 0;
            if (billingcycleList.size() > 0) {
                BillingcycleInfo cinfo = (BillingcycleInfo) billingcycleList.get(billingcycleList.size() - 1);
                cnt = cinfo.getClientId();
                billingcycleList.remove(billingcycleList.size() - 1);
            }
            request.getSession().setAttribute("BILLINGCYCLE_LIST", billingcycleList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
