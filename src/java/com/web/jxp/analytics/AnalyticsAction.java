package com.web.jxp.analytics;

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

public class AnalyticsAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
        AnalyticsForm frm = (AnalyticsForm) form;
        Analytics analytics = new Analytics();
        
        int allclient = 0;
        String permission = "N", cids = "", assetids = "";
        if (request.getSession().getAttribute("LOGININFO") != null) 
        {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) 
            {
                permission = uInfo.getPermission();
                cids = uInfo.getCids();
                allclient = uInfo.getAllclient();
                assetids = uInfo.getAssetids();
            }
        }        
        if(frm.getDoCrew() != null && frm.getDoCrew().equals("yes"))
        {
            int check_user = analytics.checkUserSession(request, 68, permission);
            if (check_user == -1) 
            {
                return mapping.findForward("default");
            } 
            else if (check_user == -2) 
            {
                String authmess = analytics.getMainPath("user_auth");
                authmess = authmess.replaceAll("__MODULE__", "Analytics Module");
                request.setAttribute("AUTHMESSAGE", authmess);
                return mapping.findForward("auth");
            }
            frm.setDoCrew("no");
            Collection clients = analytics.getClients(cids, allclient, permission);
            frm.setClients(clients);
            int clientIdIndex = frm.getClientIdIndex();
            frm.setClientIdIndex(clientIdIndex);

            Collection assets = analytics.getClientAsset(clientIdIndex, assetids, allclient, permission);
            frm.setAssets(assets);
            int assetIdIndex = frm.getAssetIdIndex();
            frm.setAssetIdIndex(assetIdIndex);
            String url = analytics.geturl(assetIdIndex, 1);
            request.setAttribute("DURL", url);
            return mapping.findForward("crew");
        }
        else if(frm.getDoTraining() != null && frm.getDoTraining().equals("yes")) 
        {
            print(this, "doTRaining block");
            int check_user = analytics.checkUserSession(request, 68, permission);
            if (check_user == -1) 
            {
                return mapping.findForward("default");
            } 
            else if (check_user == -2) 
            {
                String authmess = analytics.getMainPath("user_auth");
                authmess = authmess.replaceAll("__MODULE__", "Analytics Module");
                request.setAttribute("AUTHMESSAGE", authmess);
                return mapping.findForward("auth");
            }
            frm.setDoTraining("no");
            Collection clients = analytics.getClients(cids, allclient, permission);
            frm.setClients(clients);
            int clientIdIndex = frm.getClientIdIndex();
            frm.setClientIdIndex(clientIdIndex);

            Collection assets = analytics.getClientAsset(clientIdIndex, assetids, allclient, permission);
            frm.setAssets(assets);
            int assetIdIndex = frm.getAssetIdIndex();
            frm.setAssetIdIndex(assetIdIndex);
            String url = analytics.geturl(assetIdIndex, 2);
            request.setAttribute("TURL", url);
            return mapping.findForward("training");
        }
        else if(frm.getDoAttendance() != null && frm.getDoAttendance().equals("yes")) 
        {
            print(this, "getDoAttendance block");
            int check_user = analytics.checkUserSession(request, 100, permission);
            Collection clients = analytics.getClients(cids, allclient, permission);
            frm.setClients(clients);
            int clientId = frm.getClientId();
            frm.setClientId(clientId);

            Collection assets = analytics.getClientAsset(clientId, assetids, allclient, permission);
            frm.setAssets(assets);
            int assetId = frm.getAssetId();
            frm.setAssetId(assetId);
            if (check_user == -1) 
            {
                return mapping.findForward("default");
            } 
            else if (check_user == -2) 
            {
                String authmess = analytics.getMainPath("user_auth");
                authmess = authmess.replaceAll("__MODULE__", "Attendance Report");
                request.setAttribute("AUTHMESSAGE", authmess);
                return mapping.findForward("auth");
            }
            frm.setDoAttendance("no");
            return mapping.findForward("attendance");
        }
        else 
        {
            print(this, "else block.");
            int check_user = analytics.checkUserSession(request, 68, permission);
            if (check_user == -1) 
            {
                return mapping.findForward("default");
            } 
            else if (check_user == -2) 
            {
                String authmess = analytics.getMainPath("user_auth");
                authmess = authmess.replaceAll("__MODULE__", "Analytics Module");
                request.setAttribute("AUTHMESSAGE", authmess);
                return mapping.findForward("auth");
            }
            Collection clients = analytics.getClients(cids, allclient, permission);
            frm.setClients(clients);
            frm.setClientIdIndex(-1);
            Collection assets = analytics.getClientAsset(-1, assetids, allclient, permission);
            frm.setAssets(assets);
            frm.setAssetIdIndex(-1);
            if (frm.getCtp() == 0) 
            {
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) 
                {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }
            return mapping.findForward("analytics");
        }
    }
}
