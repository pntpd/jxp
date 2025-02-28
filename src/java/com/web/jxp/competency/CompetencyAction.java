package com.web.jxp.competency;

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

public class CompetencyAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CompetencyForm frm = (CompetencyForm) form;
        Competency competency = new Competency();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        
        String searchDept = frm.getSearchDept()!= null && !frm.getSearchDept().equals("") ? frm.getSearchDept() : "";
        frm.setSearchDept(searchDept);
        
        String searchName = frm.getSearchName()!= null && !frm.getSearchName().equals("") ? frm.getSearchName() : "";
        frm.setSearchName(searchName);
        
        String searchRole = frm.getSearchRole()!= null && !frm.getSearchRole().equals("") ? frm.getSearchRole() : "";
        frm.setSearchRole(searchRole);
        
        int statusIndex = frm.getStatusIndex();
        frm.setStatusIndex(statusIndex);
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
        Collection clients = competency.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);

        Collection assets = competency.getClientAsset(clientIdIndex, assetids, allclient, permission);
        frm.setAssets(assets);
        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);
        
        int check_user = competency.checkUserSession(request, 92, permission);
        if (check_user == -1)
        {
            return mapping.findForward("default");
        } 
        else if (check_user == -2) 
        {
            String authmess = competency.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Competency Assessment Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        
        else if (frm.getDoSearch()!= null && frm.getDoSearch().equals("yes")) 
        {
        frm.setDoSearch("no");
        print(this, " getDoSearch block :: ");
        if(assetIdIndex > 0)
        {
            String departmentcb = frm.getDepartmentcb()!= null && !frm.getDepartmentcb().equals("") ? frm.getDepartmentcb() : "";                    
            String positioncb = frm.getPositioncb()!= null && !frm.getPositioncb().equals("") ? frm.getPositioncb() : "";
            String crewrotationcb = frm.getCrewrotationcb()!= null && !frm.getCrewrotationcb().equals("") ? frm.getCrewrotationcb() : "";
            String comprolecb =  frm.getComprolecb() != null && !frm.getComprolecb().equals("")? frm.getComprolecb(): "";
            int arr[] = competency.getCountsCompetency(assetIdIndex);

            ArrayList personallist  = competency.getCandidateList(assetIdIndex, searchName);
            ArrayList positionlist  = competency.getPositionList(assetIdIndex, search);
            ArrayList positioncoutlist  = competency.getPositionCountList(assetIdIndex);
            ArrayList departmentlist  = competency.getDepartmentList(assetIdIndex, searchDept);
            ArrayList comprolelist  = competency.getCompetencyRoleList(assetIdIndex, searchRole);                
            ArrayList list  = competency.getCompentencyListByName(assetIdIndex, -1, search, 0);                
            ArrayList perpositionlist   = competency.getCandidatePositionList(assetIdIndex, positioncb, crewrotationcb); 
            ArrayList roletablelist   = competency.getRoleListForTable(assetIdIndex, departmentcb, comprolecb); 
            ArrayList trackerlist   = competency.getTrackerList(assetIdIndex); 
            ArrayList detp_tablelist   = competency.getDeptTableCountList(assetIdIndex, departmentcb); 
            ArrayList alist = competency.availableList(assetIdIndex) ;


            request.getSession().setAttribute("DEPT_TABLELIST", detp_tablelist);
            request.getSession().setAttribute("TRACKER_LIST", trackerlist);
            request.getSession().setAttribute("ALIST", alist);
            request.getSession().setAttribute("ROLE_TABLE", roletablelist);
            request.getSession().setAttribute("PER_POSITION", perpositionlist);
            request.getSession().setAttribute("DEPTCB", departmentcb);
            request.getSession().setAttribute("POSITIONCB", positioncb);
            request.getSession().setAttribute("CREWROTATIONCB", crewrotationcb);
            request.getSession().setAttribute("ROLECB", comprolecb);                
            request.getSession().setAttribute("COMP_NAMELIST", list);
            request.getSession().setAttribute("COMP_PERSONALLIST", personallist);
            request.getSession().setAttribute("COMP_POSITIONLIST", positionlist);
            request.getSession().setAttribute("COMP_POSITIONCOUNTLIST", positioncoutlist);
            request.getSession().setAttribute("COMP_DEPTLIST", departmentlist);
            request.getSession().setAttribute("COMP_COMPROLELIST", comprolelist);
            request.getSession().setAttribute("COUNTCOMPETENCY", arr);
        }
        return mapping.findForward("display");
        }
        else if (frm.getSearchDetails()!= null && frm.getSearchDetails().equals("yes")) 
        {
            frm.setSearchDetails("no");
            print(this, " searchDetails block :: ");
            int expType = frm.getExpType();
            if(expType <= 0)
                expType = 2;
            frm.setExpType(expType);
            Collection positions = competency.getPostions(assetIdIndex);
            frm.setPositions(positions);
            ArrayList graph_list = competency.getCompetencyGraph(assetIdIndex, expType);
            ArrayList list  = competency.getCompentencyListByName(assetIdIndex, -1, search, expType); 

            request.getSession().setAttribute("COMP_NAMELIST", list);
            request.setAttribute("GRAPH_COUNT", graph_list);
            request.setAttribute("EXPTYPE", ""+expType);
            return mapping.findForward("view_competency");
        }
        else if (frm.getSearchGraph()!= null && frm.getSearchGraph().equals("yes")) 
        {
            frm.setSearchGraph("no");
            print(this, "Search Graph block :: ");
            int expType = frm.getExpType();
            if(expType <= 0)
                expType = 2;
            frm.setExpType(expType);
            ArrayList graph_list = competency.getCompetencyGraph(assetIdIndex, expType);
             Collection positions = competency.getPostions(assetIdIndex);
            frm.setPositions(positions);
             int positionId = frm.getPositionId();
            ArrayList list  = competency.getCompentencyListByName(assetIdIndex, positionId, search, expType);

            request.getSession().setAttribute("COMP_NAMELIST", list);                
            request.setAttribute("GRAPH_COUNT", graph_list);
            request.setAttribute("EXPTYPE", ""+expType);
            return mapping.findForward("view_competency");
        }
        else if (frm.getDoSearchPosition()!= null && frm.getDoSearchPosition().equals("yes")) 
        {
            frm.setDoSearchPosition("no");
            print(this, "doSearchPosition block :: ");
            int expType = frm.getExpType();
            if(expType <= 0)
                expType = 2;
            frm.setExpType(expType);

            int positionId = frm.getPositionId();
            Collection positions = competency.getPostions(assetIdIndex);
            frm.setPositions(positions);
            ArrayList list  = competency.getCompentencyListByName(assetIdIndex, positionId, search, expType);                
            ArrayList graph_list = competency.getCompetencyGraph(assetIdIndex, expType);               

            request.getSession().setAttribute("COMP_NAMELIST", list);
            request.setAttribute("GRAPH_COUNT", graph_list);
            request.setAttribute("EXPTYPE", ""+expType);
            return mapping.findForward("view_competency");
        }else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            print(this, "doCancel block");
            String departmentcb = frm.getDepartmentcb()!= null && !frm.getDepartmentcb().equals("") ? frm.getDepartmentcb() : "";                    
            String positioncb = frm.getPositioncb()!= null && !frm.getPositioncb().equals("") ? frm.getPositioncb() : "";
            String crewrotationcb = frm.getCrewrotationcb()!= null && !frm.getCrewrotationcb().equals("") ? frm.getCrewrotationcb() : "";
            String comprolecb =  frm.getComprolecb() != null && !frm.getComprolecb().equals("")? frm.getComprolecb(): "";
            int arr[] = competency.getCountsCompetency(assetIdIndex);

            ArrayList personallist  = competency.getCandidateList(assetIdIndex, searchName);
            ArrayList positionlist  = competency.getPositionList(assetIdIndex, search);
            ArrayList positioncoutlist  = competency.getPositionCountList(assetIdIndex);
            ArrayList departmentlist  = competency.getDepartmentList(assetIdIndex, searchDept);
            ArrayList comprolelist  = competency.getCompetencyRoleList(assetIdIndex, searchRole);                
            ArrayList list  = competency.getCompentencyListByName(assetIdIndex, -1, search, 0);                
            ArrayList perpositionlist   = competency.getCandidatePositionList(assetIdIndex, positioncb, crewrotationcb); 
            ArrayList roletablelist   = competency.getRoleListForTable(assetIdIndex, departmentcb, comprolecb); 
            ArrayList trackerlist   = competency.getTrackerList(assetIdIndex); 
            ArrayList detp_tablelist   = competency.getDeptTableCountList(assetIdIndex, ""); 
            ArrayList alist = competency.availableList(assetIdIndex) ;
            
            request.getSession().setAttribute("DEPT_TABLELIST", detp_tablelist);
            request.getSession().setAttribute("TRACKER_LIST", trackerlist);
            request.getSession().setAttribute("ALIST", alist);
            request.getSession().setAttribute("ROLE_TABLE", roletablelist);
            request.getSession().setAttribute("PER_POSITION", perpositionlist);
            request.getSession().setAttribute("DEPTCB", departmentcb);
            request.getSession().setAttribute("POSITIONCB", positioncb);
            request.getSession().setAttribute("CREWROTATIONCB", crewrotationcb);
            request.getSession().setAttribute("ROLECB", comprolecb);                
            request.getSession().setAttribute("COMP_NAMELIST", list);
            request.getSession().setAttribute("COMP_PERSONALLIST", personallist);
            request.getSession().setAttribute("COMP_POSITIONLIST", positionlist);
            request.getSession().setAttribute("COMP_POSITIONCOUNTLIST", positioncoutlist);
            request.getSession().setAttribute("COMP_DEPTLIST", departmentlist);
            request.getSession().setAttribute("COMP_COMPROLELIST", comprolelist);
            request.getSession().setAttribute("COUNTCOMPETENCY", arr);
            return mapping.findForward("display");
        }        
        else
        {        
            request.getSession().removeAttribute("COMP_PERSONALLIST");
            return mapping.findForward("display");            
        }        
    }
}
