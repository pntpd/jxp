package com.web.jxp.competencypriorities;

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

public class CompetencyprioritiesAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CompetencyprioritiesForm frm = (CompetencyprioritiesForm) form;
        Competencypriorities competencypriorities = new Competencypriorities();
        Validate vobj = new Validate();
        
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
        int count = competencypriorities.getCount();        
        
        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);
        
        Collection depatmentss = competencypriorities.getPdepts(assetIdIndex, 1);
        frm.setDepartments(depatmentss);
        
        int departmentIdIndex = frm.getDepartmentIdIndex();
        frm.setDepartmentIdIndex(departmentIdIndex);             
        
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
         
        Collection clients = competencypriorities.getClients(cids, allclient, permission, 1);
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);

        Collection assets = competencypriorities.getClientAsset(clientIdIndex, assetids, allclient, permission, 1);
        frm.setAssets(assets);

        assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);
        
        int check_user = competencypriorities.checkUserSession(request, 85, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = competencypriorities.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Competency Priorities Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setCompetencyprioritiesId(-1);
            frm.setClientId(-1);    
            clients = competencypriorities.getClients(cids, allclient, permission, 2);
            frm.setClients(clients);
            
            assets = competencypriorities.getClientAsset(-1, assetids, allclient, permission, 2);
            frm.setAssets(assets);  
            saveToken(request);
            request.getSession().removeAttribute("DEPT_IDs");
            return mapping.findForward("add_competencypriorities");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int competencyprioritiesId = frm.getCompetencyprioritiesId();
            frm.setCompetencyprioritiesId(competencyprioritiesId);            
            CompetencyprioritiesInfo info = competencypriorities.getCompetencyprioritiesDetailById(competencyprioritiesId);
            if(info != null)
            {                
                frm.setClientId(info.getClientId());      
                clients = competencypriorities.getClients(cids, allclient, permission, 2);
                frm.setClients(clients);
                assets = competencypriorities.getClientAsset(info.getClientId(), assetids, allclient, permission, 2);
                frm.setAssets(assets);                
                if(info.getName() != null)
                    frm.setName(info.getName());
                if(info.getDescription() != null)
                    frm.setDescription(info.getDescription());
                frm.setStatus(info.getStatus());
                frm.setDegreeId(info.getDegreeId());
                frm.setAssetId(info.getAssetId());
                request.getSession().setAttribute("DEPT_IDs", info.getDeptColumn());
            }
            saveToken(request);
            return mapping.findForward("add_competencypriorities");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int competencyprioritiesId = frm.getCompetencyprioritiesId();
            frm.setCompetencyprioritiesId(competencyprioritiesId);
            CompetencyprioritiesInfo info = competencypriorities.getCompetencyprioritiesDetailByIdforDetail(competencyprioritiesId);
            request.setAttribute("COMPETENCYPRIORITIES_DETAIL", info);
            return mapping.findForward("view_competencypriorities");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int competencyprioritiesId = frm.getCompetencyprioritiesId();
                String name = vobj.replacedesc(frm.getName());
                String description = vobj.replacedesc(frm.getDescription());
                int status = 1;
                int clientId = frm.getClientId();
                int degreeId = frm.getDegreeId();
                int assetId = frm.getAssetId();
                String deptColum[] = frm.getDeptColum();
                
                String strdepartment = vobj.replacename(makeCommaDelimString(deptColum));
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = competencypriorities.getLocalIp();
                int ck = competencypriorities.checkDuplicacy(competencyprioritiesId, name, assetId, clientId);
                if(ck == 1)
                {
                    saveToken(request);
                    assets = competencypriorities.getClientAsset(clientId, assetids, allclient, permission, 0);
                    frm.setAssets(assets);
                    frm.setClientId(clientId);
                    frm.setAssetId(assetId);
                    request.getSession().setAttribute("DEPT_IDs", strdepartment);
                    request.setAttribute("MESSAGE", "Data already exists");
                    return mapping.findForward("add_competencypriorities");
                }
                request.getSession().removeAttribute("DEPT_IDs");
                CompetencyprioritiesInfo info = new CompetencyprioritiesInfo(competencyprioritiesId, name, assetId, description, status, uId, strdepartment, clientId, degreeId);
                if(competencyprioritiesId <= 0)
                {
                    int cc = competencypriorities.createCompetencypriorities(info);
                    if(cc > 0)
                    {
                       competencypriorities.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 85, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList competencyprioritiesList = competencypriorities.getCompetencyprioritiesByName(search, departmentIdIndex,clientIdIndex, assetIdIndex, 0, count);
                    int cnt = 0;
                    if(competencyprioritiesList.size() > 0)
                    {
                        CompetencyprioritiesInfo cinfo = (CompetencyprioritiesInfo) competencyprioritiesList.get(competencyprioritiesList.size() - 1);
                        cnt = cinfo.getCompetencyprioritiesId();
                        competencyprioritiesList.remove(competencyprioritiesList.size() - 1);
                    }
                    request.getSession().setAttribute("COMPETENCYPRIORITIES_LIST", competencyprioritiesList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    competencypriorities.updateCompetencypriorities(info);
                    competencypriorities.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 85, competencyprioritiesId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList competencyprioritiesList = competencypriorities.getCompetencyprioritiesByName(search, departmentIdIndex, clientIdIndex, assetIdIndex, next, count);
                    int cnt = 0;
                    if(competencyprioritiesList.size() > 0)
                    {
                        CompetencyprioritiesInfo cinfo = (CompetencyprioritiesInfo) competencyprioritiesList.get(competencyprioritiesList.size() - 1);
                        cnt = cinfo.getCompetencyprioritiesId();
                        competencyprioritiesList.remove(competencyprioritiesList.size() - 1);
                    }
                    request.getSession().setAttribute("COMPETENCYPRIORITIES_LIST", competencyprioritiesList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", next+"");
                    request.getSession().setAttribute("NEXTVALUE", (next+1)+"");
                    
                    return mapping.findForward("display");
                }                
            }
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
            ArrayList competencyprioritiesList = competencypriorities.getCompetencyprioritiesByName(search, departmentIdIndex, clientIdIndex, assetIdIndex, next, count);
            int cnt = 0;
            if(competencyprioritiesList.size() > 0)
            {
                CompetencyprioritiesInfo cinfo = (CompetencyprioritiesInfo) competencyprioritiesList.get(competencyprioritiesList.size() - 1);
                cnt = cinfo.getCompetencyprioritiesId();
                competencyprioritiesList.remove(competencyprioritiesList.size() - 1);
            }
            request.getSession().setAttribute("COMPETENCYPRIORITIES_LIST", competencyprioritiesList);
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
            ArrayList competencyprioritiesList = competencypriorities.getCompetencyprioritiesByName(search, departmentIdIndex, clientIdIndex, assetIdIndex, 0, count);
            int cnt = 0;
            if(competencyprioritiesList.size() > 0)
            {
                CompetencyprioritiesInfo cinfo = (CompetencyprioritiesInfo) competencyprioritiesList.get(competencyprioritiesList.size() - 1);
                cnt = cinfo.getCompetencyprioritiesId();
                competencyprioritiesList.remove(competencyprioritiesList.size() - 1);
            }
            request.getSession().setAttribute("COMPETENCYPRIORITIES_LIST", competencyprioritiesList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}