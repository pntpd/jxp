package com.web.jxp.dashboard;

import com.web.jxp.managetraining.Managetraining;
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

public class DashboardAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DashboardForm frm = (DashboardForm) form;
        Dashboard dashboard = new Dashboard();
        
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
        if(frm.getDoDashboard() != null && frm.getDoDashboard().equals("yes"))
        {
            frm.setDoDashboard("no");
            Collection clients = dashboard.getClients(cids, allclient, permission);
            frm.setClients(clients);
            int clientIdIndex = frm.getClientIdIndex();
            frm.setClientIdIndex(clientIdIndex);

            Collection assets = dashboard.getClientAsset(clientIdIndex, assetids, allclient, permission);
            frm.setAssets(assets);
            int assetIdIndex = frm.getAssetIdIndex();
            frm.setAssetIdIndex(assetIdIndex);
            request.removeAttribute("DBINFO");
            request.removeAttribute("DBINFOTRAINING");
            return mapping.findForward("dashboard");
        }
        else if(frm.getDoDashboardSearch() != null && frm.getDoDashboardSearch().equals("yes")) 
        {
            frm.setDoDashboardSearch("no");
            Collection clients = dashboard.getClients(cids, allclient, permission);
            frm.setClients(clients);
            int clientIdIndex = frm.getClientIdIndex();
            frm.setClientIdIndex(clientIdIndex);

            Collection assets = dashboard.getClientAsset(clientIdIndex, assetids, allclient, permission);
            frm.setAssets(assets);
            int assetIdIndex = frm.getAssetIdIndex();
            frm.setAssetIdIndex(assetIdIndex);
            int arr[] = dashboard.getcount(clientIdIndex, assetIdIndex); 
            Managetraining managetraining = new Managetraining(); 
            int tarr[] = managetraining.getCounts(assetIdIndex, 2);
            request.setAttribute("DBINFO", arr);
            request.setAttribute("DBINFOTRAINING", tarr);
            return mapping.findForward("dashboard");
        }
        else if(frm.getDoCrewEnr() != null && frm.getDoCrewEnr().equals("yes"))
        {
            frm.setDoCrewEnr("no");
            int arr1[] = dashboard.getverificationcount(); 
            int arr2[] = dashboard.getassessmentcount(); 
            int arr3[] = dashboard.getGraph4();
            int arr4[] = dashboard.getGraph1();
            int arr5[] = dashboard.getTalentPoolcount();
            ArrayList list = dashboard.getGraph5();
            request.setAttribute("VINFO", arr1);            
            request.setAttribute("AINFO", arr2);
            request.setAttribute("G4INFO", arr3);
            request.setAttribute("G5LIST", list);
            request.setAttribute("G1LIST", arr4);
            request.setAttribute("CINFO", arr5);
            return mapping.findForward("crewenrollment");
        }
        else if (frm.getDoCR()!= null && frm.getDoCR().equals("yes")) 
        {
            frm.setDoCR("no");
            print(this, " getDoCR block :: ");
            frm.setTtype(-1);

            Collection clients = dashboard.getClients(cids, allclient, permission);
            frm.setClients(clients);
            int clientIdIndex = frm.getClientIdIndex();
            frm.setClientIdIndex(clientIdIndex);

            Collection assets = dashboard.getClientAsset(clientIdIndex, assetids, allclient, permission);
            frm.setAssets(assets);
            int assertIdIndex = frm.getAssetIdIndex();
            frm.setAssetIdIndex(assertIdIndex);

            frm.setPositioncb("");
            frm.setCrewrotationcb("");
            frm.setSearchPersonnel("");
            frm.setSearchPosition("");
            frm.setFromdate("");
            frm.setTodate("");

            request.getSession().removeAttribute("DASH_CREWROTATIONCLIST");
            request.getSession().removeAttribute("DASH_CREWROTATIONULIST");
            request.getSession().removeAttribute("DASH_CREWROTATIONDLIST");
            request.getSession().removeAttribute("DASH_CREWROTATIONPLIST");
            request.getSession().removeAttribute("DASH_CREWROTATIONPERSONLIST");
            request.getSession().removeAttribute("DASH_DATELIST");
            request.getSession().removeAttribute("DASH_DATELISTDAY");
            request.getSession().removeAttribute("POSITIONCB");
            request.getSession().removeAttribute("CREWROTATIONCB");

            return mapping.findForward("crewrotation");
        }
        else if (frm.getDoSearchCR()!= null && frm.getDoSearchCR().equals("yes")) 
        {
                frm.setDoSearchCR("no");
                print(this, " getDoSearchCR block :: ");
                frm.setTtype(-1);
                Collection clients = dashboard.getClients(cids, allclient, permission);
                frm.setClients(clients);
                int clientIdIndex = frm.getClientIdIndex();
                frm.setClientIdIndex(clientIdIndex);

                Collection assets = dashboard.getClientAsset(clientIdIndex, assetids, allclient, permission);
                frm.setAssets(assets);
                int assetIdIndex = frm.getAssetIdIndex();
                frm.setAssetIdIndex(assetIdIndex);

                int month = frm.getMonth();
                int year = frm.getYear();
                String []date = dashboard.findFromToDate(month, year);
                String fromdateIndex = date[0];
                String todateIndex = date[1];
                String fromdate = frm.getFromdate();
                String todate = frm.getTodate();
                String positioncb = frm.getPositioncb();
                String crewrotationcb = frm.getCrewrotationcb();
                request.getSession().setAttribute("POSITIONCB", positioncb);
                request.getSession().setAttribute("CREWROTATIONCB", crewrotationcb);
                String searchPersonnel = frm.getSearchPersonnel();
                String searchPosition = frm.getSearchPosition();
                frm.setSearchPosition(searchPosition);
                frm.setSearchPersonnel(searchPersonnel);
               
                if(month > 0 && year > 0)
                {
                    request.getSession().setAttribute("MONTH", dashboard.getMonthName(month)+" - "+year);                
                    ArrayList candlist  = dashboard.getCandidatelistfromcrewrotation(clientIdIndex, assetIdIndex,fromdateIndex,todateIndex,positioncb,crewrotationcb);
                    request.getSession().setAttribute("DASH_CREWROTATIONCLIST", candlist);                      
                }
                ArrayList datelist  = dashboard.datelist(fromdateIndex,todateIndex);
                request.getSession().setAttribute("DASH_DATELIST", datelist);
                ArrayList datelistday  = dashboard.datelistday(fromdateIndex,todateIndex);
                request.getSession().setAttribute("DASH_DATELISTDAY", datelistday);
                DashboardInfo listinfo  = dashboard.getListFromcractivity(clientIdIndex, assetIdIndex,fromdateIndex,todateIndex);
                request.getSession().setAttribute("DASH_CREWROTATIONULIST", listinfo);
                
                ArrayList personnellist  = dashboard.getPersonnellistfromcrewrotation(clientIdIndex, assetIdIndex,"","","","","");
                request.getSession().setAttribute("DASH_CREWROTATIONPERSONLIST", personnellist);
                ArrayList positionlist  = dashboard.getPositionlist( assetIdIndex,"");
                request.getSession().setAttribute("DASH_CREWROTATIONPLIST", positionlist);
                
                if(fromdate != null && !fromdate.equals("") && todate != null && !todate.equals("") )
                {
                    ArrayList dlist  = dashboard.getClientSelectByName(clientIdIndex, assetIdIndex,fromdate,todate,positioncb,crewrotationcb);
                    request.getSession().setAttribute("DASH_CREWROTATIONDLIST", dlist);
                }
                return mapping.findForward("crewrotation");
            }
            else if (frm.getDoSearchCRpp()!= null && frm.getDoSearchCRpp().equals("yes")) {
                frm.setDoSearchCRpp("no");
                print(this, " getDoSearchCRpp block :: ");
                
                Collection clients = dashboard.getClients(cids, allclient, permission);
                frm.setClients(clients);
                int clientIdIndex = frm.getClientIdIndex();
                frm.setClientIdIndex(clientIdIndex);

                Collection assets = dashboard.getClientAsset(clientIdIndex, assetids, allclient, permission);
                frm.setAssets(assets);
                int assetIdIndex = frm.getAssetIdIndex();
                frm.setAssetIdIndex(assetIdIndex);
                
                String positioncb = frm.getPositioncb();
                String crewrotationcb = frm.getCrewrotationcb();
                String searchPersonnel = frm.getSearchPersonnel();
                String searchPosition = frm.getSearchPosition();
                frm.setSearchPosition(searchPosition);
                frm.setSearchPersonnel(searchPersonnel);
                
                request.getSession().setAttribute("POSITIONCB", positioncb);
                request.getSession().setAttribute("CREWROTATIONCB", crewrotationcb);
                
                String fromdate = frm.getFromdate();
                String todate = frm.getTodate();
                
                ArrayList personnellist  = dashboard.getPersonnellistfromcrewrotation(clientIdIndex, assetIdIndex,"","","","",searchPersonnel);
                request.getSession().setAttribute("DASH_CREWROTATIONPERSONLIST", personnellist);
                
                ArrayList positionlist  = dashboard.getPositionlist( assetIdIndex,searchPosition);
                request.getSession().setAttribute("DASH_CREWROTATIONPLIST", positionlist);
                
                if(fromdate != null && !fromdate.equals("") && todate != null && !todate.equals("") )
                {
                    ArrayList dlist  = dashboard.getClientSelectByName(clientIdIndex, assetIdIndex, fromdate,todate,positioncb,crewrotationcb);
                    request.getSession().setAttribute("DASH_CREWROTATIONDLIST", dlist);
                    request.setAttribute("DASHCRID", "counttableid");
                }
                return mapping.findForward("crewrotation");
            }            
            else if(frm.getDoTraining() != null && frm.getDoTraining().equals("yes"))
            {
                frm.setDoTraining("no");
                Collection clients = dashboard.getClients(cids, allclient, permission);
                frm.setClients(clients);
                int clientIdIndex = frm.getClientIdIndex();
                frm.setClientIdIndex(clientIdIndex);

                Collection assets = dashboard.getClientAsset(clientIdIndex, assetids, allclient, permission);
                frm.setAssets(assets);
                int assetIdIndex = frm.getAssetIdIndex();
                frm.setAssetIdIndex(assetIdIndex);
                request.removeAttribute("TRAINGCOUNT");
                return mapping.findForward("training");
            }            
            else if(frm.getDoTrainingSearch() != null && frm.getDoTrainingSearch().equals("yes"))
            {
                frm.setDoTrainingSearch("no");
                Collection clients = dashboard.getClients(cids, allclient, permission);
                frm.setClients(clients);
                int clientIdIndex = frm.getClientIdIndex();
                frm.setClientIdIndex(clientIdIndex);

                Collection assets = dashboard.getClientAsset(clientIdIndex, assetids, allclient, permission);
                frm.setAssets(assets);
                int assetIdIndex = frm.getAssetIdIndex();
                frm.setAssetIdIndex(assetIdIndex);
                if(clientIdIndex > 0)
                {
                    Managetraining managetraining = new Managetraining();
                    int tarr[] = managetraining.getCounts(assetIdIndex, 1);
                    request.setAttribute("TRAINGCOUNT", tarr);
                }
                return mapping.findForward("training");
            }  
            else if(frm.getDoCrecruitment()!= null && frm.getDoCrecruitment().equals("yes"))
            {
                frm.setDoCrecruitment("no");
                Collection clients = dashboard.getClients(cids, allclient, permission);
                frm.setClients(clients);
                int clientIdIndex = frm.getClientIdIndex();
                frm.setClientIdIndex(clientIdIndex);

                Collection assets = dashboard.getClientAsset(clientIdIndex, assetids, allclient, permission);
                frm.setAssets(assets);
                int assetIdIndex = frm.getAssetIdIndex();
                frm.setAssetIdIndex(assetIdIndex);
                request.removeAttribute("CRCOUNT");
                return mapping.findForward("crewrecruitment");
            }
            else if(frm.getDoCrecruitmentSearch()!= null && frm.getDoCrecruitmentSearch().equals("yes"))
            {
                frm.setDoCrecruitmentSearch("no");
                Collection clients = dashboard.getClients(cids, allclient, permission);
                frm.setClients(clients);
                int clientIdIndex = frm.getClientIdIndex();
                frm.setClientIdIndex(clientIdIndex);
                Collection assets = dashboard.getClientAsset(clientIdIndex, assetids, allclient, permission);
                frm.setAssets(assets);
                int assetIdIndex = frm.getAssetIdIndex();
                frm.setAssetIdIndex(assetIdIndex);
                if(clientIdIndex > 0)
                {                    
                    int tarr[] = dashboard.getcount_cm(assetIdIndex);
                    request.setAttribute("CRCOUNT", tarr);
                }
                return mapping.findForward("crewrecruitment");
            }  
            else 
            {
                print(this, "else block.");
                Collection clients = dashboard.getClients(cids, allclient, permission);
                frm.setClients(clients);
                frm.setClientIdIndex(-1);
                Collection assets = dashboard.getClientAsset(-1, assetids, allclient, permission);
                frm.setAssets(assets);
                frm.setAssetIdIndex(-1);
                if (frm.getCtp() == 0) {
                    Stack<String> blist = new Stack<String>();
                    if (request.getSession().getAttribute("BACKURL") != null) {
                        blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }
            return mapping.findForward("dashboard");
        }
    }
}
