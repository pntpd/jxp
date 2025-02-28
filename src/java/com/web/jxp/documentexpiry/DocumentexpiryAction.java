package com.web.jxp.documentexpiry;
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

public class DocumentexpiryAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
        DocumentexpiryForm frm = (DocumentexpiryForm) form;
        Documentexpiry documentexpiry = new Documentexpiry();
        int documentId = frm.getDocumentId();
        frm.setDocumentId(documentId);
        int type = frm.getType();
        frm.setType(type);
        int exp = frm.getExp() > 0 ? frm.getExp() : 1;
        frm.setExp(exp); 
        Collection documents = documentexpiry.getDocumentList();
        frm.setDocuments(documents);        
        Collection coursename = documentexpiry.getCourseName();
        frm.setCoursenames(coursename);
        int coursenameId = frm.getCoursenameId();
        int dropdownId = 0;
        dropdownId = frm.getDropdownId();
        frm.setDropdownId(dropdownId);
        int healthId = frm.getHealthId();
        String search = frm.getSearch() != null ? frm.getSearch() : "";
        frm.setSearch(search);
        
        String search2 = frm.getSearch2() != null ? frm.getSearch2() : "";
        frm.setSearch2(search2);
        
        String fromDate = frm.getFromDate();
        frm.setFromDate(fromDate);
        String toDate = frm.getToDate();
        frm.setToDate(toDate);
        String time = frm.getToTime();
        frm.setToTime(time);
        int moduleId = frm.getModuleId();
        frm.setModuleId(moduleId);
        
        int uId = 0, allclient = 0;
        String permission = "N", cids = "", assetids = "", username = "";
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
                username = uInfo.getName();
            }
        }       
        int check_user = documentexpiry.checkUserSession(request, 70, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = documentexpiry.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Notifications Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");        
        }
        Collection clients = documentexpiry.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);
        
        int clientIdAlert = frm.getClientIdAlert();
        frm.setClientIdAlert(clientIdAlert);

        Collection assets = documentexpiry.getClientAsset(clientIdIndex, assetids, allclient, permission);
        frm.setAssets(assets);

        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);
        
        int assetIdAlert = frm.getAssetIdAlert();
        frm.setAssetIdAlert(assetIdAlert);
        
        
        if(frm.getDoRemind() != null && frm.getDoRemind().equals("yes"))
        {
            frm.setDoRemind("no");
            int govdocId = frm.getGovdocId();
            if (dropdownId == 3) {
                documentexpiry.sendmail(govdocId, "", username, uId, dropdownId, healthId);
            } else {
                documentexpiry.sendmail(govdocId, "", username, uId, dropdownId, 0);
            }
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList list = documentexpiry.getDocumentexpiryByName( documentId, type, exp, clientIdIndex, assetIdIndex,
                    allclient, permission, cids, assetids, coursenameId, dropdownId, healthId, search);
            int cnt = 0;
            if (list.size() > 0) 
            {
                DocumentexpiryInfo cinfo = (DocumentexpiryInfo) list.get(list.size() - 1);
                cnt = cinfo.getCandidateId();
                list.remove(list.size() - 1);
            }
            cnt = list.size();
            request.getSession().setAttribute("DOCUMENTEXPIRY_LIST", list);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            return mapping.findForward("display");
        }
        else if(frm.getDoRemindAll() != null && frm.getDoRemindAll().equals("yes"))
        {
            frm.setDoRemindAll("no");
            String govdocId[] = frm.getGovdoccb();
            String gids = makeCommaDelimString(govdocId); 
            if (dropdownId == 3) {
                documentexpiry.sendmail(-1, gids, username, uId, dropdownId, healthId);
            } else {
                documentexpiry.sendmail(-1, gids, username, uId, dropdownId, 0);
            }
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }            
            ArrayList list = documentexpiry.getDocumentexpiryByName( documentId, type, exp, clientIdIndex, assetIdIndex, 
                    allclient, permission, cids, assetids, coursenameId, dropdownId, healthId, search);
            int cnt = 0;
            if (list.size() > 0) 
            {
                DocumentexpiryInfo cinfo = (DocumentexpiryInfo) list.get(list.size() - 1);
                cnt = cinfo.getCandidateId();
                list.remove(list.size() - 1);
                cnt = list.size();
            }
            request.getSession().setAttribute("DOCUMENTEXPIRY_LIST", list);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            return mapping.findForward("display");
        }
        else if(frm.getViewExpiry()!= null && frm.getViewExpiry().equals("yes"))
        {
            frm.setViewExpiry("no");                        
            ArrayList list = documentexpiry.getDocumentexpiryByName( documentId, type, exp, clientIdIndex, assetIdIndex,
                    allclient, permission, cids, assetids, coursenameId, dropdownId, healthId, search);
            request.getSession().setAttribute("DOCUMENTEXPIRY_LIST", list);
            return mapping.findForward("display");
        }
        else if(frm.getViewAlerts()!= null && frm.getViewAlerts().equals("yes"))
        {
            frm.setViewAlerts("no");
            ArrayList list = documentexpiry.getAlertByName(search2, clientIdAlert, assetIdAlert, fromDate, toDate, moduleId,  
                    allclient, permission, cids, assetids);
            request.getSession().setAttribute("ALERT_LIST", list);
            return mapping.findForward("display_alert");
        }
        else if(frm.getDeleteAlert()!= null && frm.getDeleteAlert().equals("yes"))
        {
            frm.setDeleteAlert("no");
            int notificationId = frm.getNotificationId();
            frm.setNotificationId(notificationId);
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = documentexpiry.getLocalIp();
            int cc = documentexpiry.deleteAlert(notificationId, uId, ipAddrStr, iplocal);
            if(cc > 0)
            {
                request.setAttribute("MESSAGE", "Notification deleted successfully.");
                ArrayList list = documentexpiry.getAlertByName(search2, clientIdAlert, assetIdAlert, fromDate, toDate, moduleId, 
                        allclient, permission, cids, assetids);
                request.getSession().setAttribute("ALERT_LIST", list);
                frm.setNotificationId(0);
                return mapping.findForward("display_alert");
            }
        }
        else 
        {
            print(this, "else block.");
            if (frm.getCtp() == 0) 
            {
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }
            ArrayList list = documentexpiry.getDocumentexpiryByName( documentId, type, exp, clientIdIndex, assetIdIndex, 
                    allclient, permission, cids, assetids, coursenameId, dropdownId, healthId, search);  
           
            request.getSession().setAttribute("DOCUMENTEXPIRY_LIST", list);
        }
        return mapping.findForward("display");
    }
}
