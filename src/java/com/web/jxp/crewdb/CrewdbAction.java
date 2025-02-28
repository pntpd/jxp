package com.web.jxp.crewdb;

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

public class CrewdbAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CrewdbForm frm = (CrewdbForm) form;
        Crewdb crewdb = new Crewdb();
        int count = crewdb.getCount();
        String search = frm.getSearch() != null ? frm.getSearch() : "";
        frm.setSearch(search);     
        int statusIndex = frm.getStatusIndex();
        frm.setStatusIndex(statusIndex);
        String permission = "N";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) 
            {
                permission = uInfo.getPermission();
            }
        }        
        int check_user = crewdb.checkUserSession(request, 4, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = crewdb.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Talent Pool Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        Collection positions = crewdb.getPositions();
        frm.setPositions(positions);
        int positionIndex = frm.getPositionIndex();
        frm.setPositionIndex(positionIndex);
        Collection clients = crewdb.getClients();
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);
        Collection countries = crewdb.getClientLocations(clientIdIndex);
        frm.setCountries(countries);
        int countryIdIndex = frm.getCountryIdIndex();
        frm.setCountryIdIndex(countryIdIndex);
        Collection assests = crewdb.getClientAssetsList(clientIdIndex, countryIdIndex);
        frm.setAssets(assests);
        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);
        if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int crewdbId = frm.getCrewdbId();
            frm.setCrewdbId(crewdbId);
            CrewdbInfo info = crewdb.getCrewdbDetail(crewdbId);            
            request.getSession().setAttribute("CREWDB_DETAIL", info);
            
            ArrayList list = crewdb.getList1(crewdbId);
            request.setAttribute("LIST1", list);
            
            CrewdbInfo healthinfo = crewdb.gethealthdetail(crewdbId);   
            request.getSession().setAttribute("HEALTH_DETAIL", healthinfo);
            ArrayList vaccinationlist = crewdb.getvaccinationList(crewdbId);
            request.setAttribute("VACCINATIONLIST", vaccinationlist);
            
            ArrayList workexplist = crewdb.getworkexpList(crewdbId);
            request.setAttribute("WORKEXPLIST", workexplist);
            
            ArrayList educlist = crewdb.getListeduc(crewdbId);
            request.setAttribute("LISTEDUC", educlist);
            
            ArrayList certilist = crewdb.getListcerti(crewdbId);
            request.setAttribute("LISTCERTI", certilist);
            
            ArrayList banklist = crewdb.getListbank(crewdbId);   
            request.setAttribute("LISTBANK", banklist);            
            
            ArrayList doclist = crewdb.getListGovdoc(crewdbId);   
            request.setAttribute("LISTGOVDOC", doclist);
            
            ArrayList cassessmenthistorylist = crewdb.getCassessmenthistoryByCandidateId(crewdbId);   
            request.setAttribute("LISTCASSHIST", cassessmenthistorylist);
            
            ArrayList nomineelist = crewdb.getNomineeList(crewdbId);   
            request.setAttribute("NOMINEELIST", nomineelist);

            return mapping.findForward("view_crewdb");
            
        }         
        else if(frm.getDoCancel() != null && frm.getDoCancel().equals("yes"))
        {
            print(this,"doCancel block");
            int next = 0;
            if(request.getSession().getAttribute("NEXTVALUE") != null)
            {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if(next < 0)
                next = 0;
            ArrayList crewdbList = crewdb.getCrewdbByName(search, statusIndex, positionIndex, clientIdIndex, countryIdIndex, assetIdIndex, next, count);
            int cnt = 0;
            if(crewdbList.size() > 0)
            {
                CrewdbInfo cinfo = (CrewdbInfo) crewdbList.get(crewdbList.size() - 1);
                cnt = cinfo.getCrewdbId();
                crewdbList.remove(crewdbList.size() - 1);
            }
            request.getSession().setAttribute("CREWDB_LIST", crewdbList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next+1) +"");
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
            
            ArrayList crewdbList = crewdb.getCrewdbByName(search, statusIndex, positionIndex, clientIdIndex, countryIdIndex, assetIdIndex, 0, count);
            int cnt = 0;
            if(crewdbList.size() > 0)
            {
                CrewdbInfo cinfo = (CrewdbInfo) crewdbList.get(crewdbList.size() - 1);
                cnt = cinfo.getCrewdbId();
                crewdbList.remove(crewdbList.size() - 1);
            }
            request.getSession().setAttribute("CREWDB_LIST", crewdbList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}