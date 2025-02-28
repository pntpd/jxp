package com.web.jxp.crewinsurance;

import com.web.jxp.base.Base;
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

public class CrewinsuranceAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CrewinsuranceForm frm = (CrewinsuranceForm) form;
        Crewinsurance crewinsurance = new Crewinsurance();
        Base base = new Base();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        Collection statuses = crewinsurance.getStatuses();
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
        frm.setCurrentDate(base.currDate3());
        
        Collection clients = crewinsurance.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);
        
        Collection assets = crewinsurance.getClientAsset(clientIdIndex, assetids, allclient, permission);
        frm.setAssets(assets);        
        
        int positionIdIndex = frm.getPositionIdIndex();
        frm.setPositionIdIndex(positionIdIndex);
        
        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);
        
        Collection positions = crewinsurance.getPostions(assetIdIndex);
        frm.setPositions(positions);
        
        int check_user = crewinsurance.checkUserSession(request, 71, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = crewinsurance.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Crew Insurance Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
         else if (frm.getDoSearch() != null && frm.getDoSearch().equals("yes")) 
        {
            frm.setDoSearch("no");
            print(this, "getDoSearch block.");
            ArrayList crewinsuranceList = crewinsurance.getCrewinsuranceByName(search, statusIndex, positionIdIndex, clientIdIndex, assetIdIndex, allclient, permission, cids, assetids);
            int arr[] = crewinsurance.getTopCounts(assetIdIndex);
            request.getSession().setAttribute("CREWINSURANCE_LIST", crewinsuranceList);
            request.getSession().setAttribute("ARR_COUNT", arr);
            return mapping.findForward("display");
        }
        else if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setCrewinsuranceId(-1);
            saveToken(request);
            return mapping.findForward("display");
        }
        
        else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) 
        {
            frm.setDoSave("no");
            print(this, "getDoSave block.");

            int crewinsuranceId = frm.getCrewinsuranceId();
            int crewrotationId = frm.getCrewrotationId();
            String fromDate = frm.getFromDate();
            String toDate = frm.getToDate();
            int dependent = frm.getDependent();
            String[] cb1 = frm.getCb1();
            int status = 1;
            String selectedNominee = makeCommaDelimString(cb1);
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = crewinsurance.getLocalIp();                
            String add_crewinsurance_file = crewinsurance.getMainPath("add_candidate_file");
            String foldername = crewinsurance.createFolder(add_crewinsurance_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "";
            FormFile filename = frm.getCertificatefile();
            if (filename != null && filename.getFileSize() > 0) {
                fileName1 = crewinsurance.uploadFile(crewinsuranceId, frm.getCertificatefilehidden(), filename, fn + "_1", add_crewinsurance_file, foldername);
            }
            CrewinsuranceInfo info = new CrewinsuranceInfo(crewinsuranceId, fromDate, toDate,dependent, status, 
                    uId, fileName1, crewrotationId, selectedNominee);
            if(crewrotationId> 0) 
            {
                crewinsuranceId = crewinsurance.createCrewinsurance(info);
                //crewinsurance.sendmailinsurance(crewinsuranceId, username, uId);
                int arr[] = crewinsurance.getTopCounts(assetIdIndex);
                request.getSession().setAttribute("ARR_COUNT", arr);
                request.setAttribute("SAVECERTMODEL", "yes");
            }            
            else if (crewinsuranceId > 0 && crewrotationId> 0)
            {
                int cc = crewinsurance.createCrewinsurance(info);
                //crewinsurance.sendmailinsurance(crewinsuranceId, username, uId);
                if (cc > 0) {
                    crewinsurance.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 71, cc);
                    int arr[] = crewinsurance.getTopCounts(assetIdIndex);
                    request.setAttribute("MESSAGE", "Data added successfully.");
                    request.getSession().setAttribute("ARR_COUNT", arr);
                }                
                request.setAttribute("SAVECERTMODEL", "yes");                
            }
            ArrayList crewinsuranceList = crewinsurance.getCrewinsuranceByName(search, statusIndex,  positionIdIndex, clientIdIndex, assetIdIndex, allclient, permission, cids, assetids);
            int arr[] = crewinsurance.getTopCounts(assetIdIndex);
            request.getSession().setAttribute("ARR_COUNT", arr);
            request.getSession().setAttribute("CREWINSURANCE_LIST", crewinsuranceList);
            return mapping.findForward("display");
        }
        else {
            print(this, "else block.");
            if (frm.getCtp() == 0) {
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);            
            ArrayList crewinsuranceList = crewinsurance.getCrewinsuranceByName(search, statusIndex,  positionIdIndex, clientIdIndex, assetIdIndex, allclient, permission, cids, assetids);
            int arr[] = crewinsurance.getTopCounts(assetIdIndex);
            request.getSession().setAttribute("ARR_COUNT", arr);
            request.getSession().setAttribute("CREWINSURANCE_LIST", crewinsuranceList);
        }
        }
        return mapping.findForward("display");
    }
}
