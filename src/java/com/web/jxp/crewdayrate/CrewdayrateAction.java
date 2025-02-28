package com.web.jxp.crewdayrate;

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

public class CrewdayrateAction extends Action 
{

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        CrewdayrateForm frm = (CrewdayrateForm) form;
        Crewdayrate crewdayrate = new Crewdayrate();
        int count = crewdayrate.getCount();
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
        Collection clients = crewdayrate.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);

        Collection assets = crewdayrate.getClientAsset(clientIdIndex, assetids, allclient, permission);
        frm.setAssets(assets);

        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);
        int check_user = crewdayrate.checkUserSession(request, 74, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = crewdayrate.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Crew Day Rate Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");        
        }
        if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            frm.setDoView("no");
            int assetId = frm.getAssetId();
            int type = frm.getType();
            frm.setAssetId(assetId);
            frm.setClientassetId(assetId);
            String search2 = frm.getSearch2() != null && !frm.getSearch2().equals("") ? frm.getSearch2() : "";
            frm.setSearch2(search2);
            CrewdayrateInfo info = crewdayrate.getBasicDetail(assetId);
            Collection postions = crewdayrate.getPostionsFilter(assetId);
            frm.setPositions(postions);
            int positionId2 = frm.getPositionId2();
            frm.setPositionId2(positionId2);
            
            String ratetype = "";
            if(info != null)
                ratetype = info.getRatetype() != null ? info.getRatetype() : "";
            ArrayList plist = crewdayrate.getPositionList(assetId);
            ArrayList clist = crewdayrate.getCrewList(assetId, ratetype, type,positionId2,search2);
            request.getSession().setAttribute("C_LIST", clist);
            request.getSession().setAttribute("POSITION_LIST", plist);
            request.getSession().setAttribute("BASICINFO", info);  
            request.removeAttribute("SAVERATE"); 
            return mapping.findForward("view_crewdayrate");
        }
        if(frm.getDoView2() != null && frm.getDoView2().equals("yes"))
        {
            frm.setDoView2("no");
            int assetId = frm.getAssetId();
            int positionId = frm.getPositionId2();
            int type = frm.getType();
            frm.setAssetId(assetId);
            frm.setClientassetId(assetId);
            String search2 = frm.getSearch2() != null && !frm.getSearch2().equals("") ? frm.getSearch2() : "";
            frm.setSearch2(search2);
            CrewdayrateInfo info = crewdayrate.getBasicDetail(assetId);
            Collection postions = crewdayrate.getPostionsFilter(assetId);
            frm.setPositions(postions);
            int positionId2 = frm.getPositionId2();
            frm.setPositionId2(positionId2);
            
            String ratetype = "";
            if(info != null)
                ratetype = info.getRatetype() != null ? info.getRatetype() : "";
            ArrayList plist = crewdayrate.getPositionList(assetId);
            ArrayList clist = crewdayrate.getCrewList(assetId, ratetype, type,positionId, search2);
            request.getSession().setAttribute("C_LIST", clist);
            request.getSession().setAttribute("POSITION_LIST", plist);
            request.getSession().setAttribute("BASICINFO", info);  
            request.removeAttribute("SAVERATE"); 
            return mapping.findForward("view_crewdayrate");
        }
        if(frm.getDoChange()!= null && frm.getDoChange().equals("yes"))
        {
            frm.setDoChange("no");
            int assetId = frm.getAssetId();
            frm.setAssetId(assetId);
            int status = frm.getStatus();
            frm.setStatus(status);            
            crewdayrate.deleteDayRateDetail(assetId, status, uId);
            
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }            
            ArrayList crewdayrateList = crewdayrate.getCrewdayrateByName(search, next, count,allclient, permission, cids, assetids, clientIdIndex, assetIdIndex);
            int cnt = 0;
            if (crewdayrateList.size() > 0) {
                CrewdayrateInfo cinfo = (CrewdayrateInfo) crewdayrateList.get(crewdayrateList.size() - 1);
                cnt = cinfo.getAssetId();
                crewdayrateList.remove(crewdayrateList.size() - 1);
            }
            request.getSession().setAttribute("CREWDAYRATE_LIST", crewdayrateList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display");
        }
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            frm.setDoSave("no");
            int assetId = frm.getAssetId();
            frm.setAssetId(assetId);    
            int candidateId = frm.getCandidateId();
            double rate1 = frm.getRate1();
            double rate2 = frm.getRate2();
            double rate3 = frm.getRate3();
            int positionId = frm.getPositionIdCandidate();
            int positionId2 = frm.getPositionId2();
            int type = frm.getType();
            String fromdate = frm.getFromDate();
            String todate= frm.getToDate();
            String search2 = frm.getSearch2() != null && !frm.getSearch2().equals("") ? frm.getSearch2() : "";
            frm.setSearch2(search2);
            CrewdayrateInfo info = crewdayrate.getBasicDetail(assetId);
            Collection postions = crewdayrate.getPostionsFilter(assetId);
            frm.setPositions(postions);
            String ratetype = "";            
            if(info != null){
                ratetype = info.getRatetype() != null ? info.getRatetype() : "";
            }
            int ck = crewdayrate.checkDuplicacy(candidateId, fromdate, todate, assetId, positionId);
            if(ck == 1)
            {
                request.setAttribute("MESSAGE", "Day rate for the selected date already exists, Please select a later date.");
                return mapping.findForward("view_crewdayrate");
            }else
            {
                int cc = crewdayrate.createdatacandidate(assetId, candidateId, positionId, rate1, rate2, rate3, fromdate, todate, uId);
                if(cc > 0)
                    request.setAttribute("SAVERATE", "yes"); 
                ArrayList plist = crewdayrate.getPositionList(assetId);
                ArrayList clist = crewdayrate.getCrewList(assetId, ratetype, type,positionId2, search2); 
                request.getSession().setAttribute("C_LIST", clist);
                request.getSession().setAttribute("POSITION_LIST", plist);
                request.getSession().setAttribute("BASICINFO", info);              
                return mapping.findForward("view_crewdayrate");
            }
        }
        else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) 
        {
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
            ArrayList crewdayrateList = crewdayrate.getCrewdayrateByName(search, next, count,allclient, permission, cids, assetids, clientIdIndex, assetIdIndex);
            int cnt = 0;
            if (crewdayrateList.size() > 0) {
                CrewdayrateInfo cinfo = (CrewdayrateInfo) crewdayrateList.get(crewdayrateList.size() - 1);
                cnt = cinfo.getAssetId();
                crewdayrateList.remove(crewdayrateList.size() - 1);
            }
            request.getSession().setAttribute("CREWDAYRATE_LIST", crewdayrateList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display");
        }
        else 
        {
            print(this, "else block.");
            if (frm.getCtp() == 0) {  
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }

            ArrayList crewdayrateList = crewdayrate.getCrewdayrateByName(search, 0, count, allclient, permission, cids, assetids,clientIdIndex, assetIdIndex);
            int cnt = 0;
            if (crewdayrateList.size() > 0) {
                CrewdayrateInfo cinfo = (CrewdayrateInfo) crewdayrateList.get(crewdayrateList.size() - 1);
                cnt = cinfo.getAssetId();
                crewdayrateList.remove(crewdayrateList.size() - 1);
            }
            request.getSession().setAttribute("CREWDAYRATE_LIST", crewdayrateList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
