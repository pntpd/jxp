package com.web.jxp.framework;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import static com.web.jxp.common.Common.*;
import com.web.jxp.pdept.Pdept;
import com.web.jxp.user.UserInfo;
import java.util.Collection;
import java.util.Stack;

public class FrameworkAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
        FrameworkForm frm = (FrameworkForm) form;
        Framework framework = new Framework();
        Pdept pdept = new Pdept();
        
        int count = framework.getCount();
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
        Collection clients = framework.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);

        Collection assets = framework.getClientAsset(clientIdIndex, assetids, allclient, permission);
        frm.setAssets(assets);

        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);
        int check_user = framework.checkUserSession(request, 87, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = framework.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Framework Management");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            frm.setDoView("no");
            int clientId = frm.getClientId();
            int assetId = frm.getAssetId();
            frm.setClientId(clientId);
            frm.setAssetId(assetId); 
            
            FrameworkInfo info = framework.getBasicInfo(assetId);
            request.getSession().setAttribute("BASICINFO", info);            
            int assettypeId = info.getAssettypeId();
            Collection pdepts = pdept.getPdepts(assettypeId);
            frm.setPdepts(pdepts); 
            frm.setPdeptId(-1);
            ArrayList list = framework.getPositionList(assetId, -1);
            request.setAttribute("PLIST", list);
            request.removeAttribute("SAVED");
            return mapping.findForward("positionlist");
        }
        else if(frm.getDoDeptChange() != null && frm.getDoDeptChange().equals("yes"))
        {
            frm.setDoDeptChange("no");
            int clientId = frm.getClientId();
            int assetId = frm.getAssetId();
            frm.setClientId(clientId);
            frm.setAssetId(assetId);
            int pdeptId = frm.getPdeptId();
            FrameworkInfo info = null;
            if(request.getSession().getAttribute("BASICINFO") != null)
                info = (FrameworkInfo) request.getSession().getAttribute("BASICINFO");
            int assettypeId = 0;
            if(info != null)
                assettypeId = info.getAssettypeId();
            Collection pdepts = pdept.getPdepts(assettypeId);
            frm.setPdepts(pdepts); 
            frm.setPdeptId(pdeptId);
            
            ArrayList list = framework.getPositionList(assetId, pdeptId);
            request.setAttribute("PLIST", list);
            request.removeAttribute("SAVED");
            return mapping.findForward("positionlist");
        }
        else if(frm.getDoDeleteRole() != null && frm.getDoDeleteRole().equals("yes"))
        {
            frm.setDoDeleteRole("no");
            int clientId = frm.getClientId();
            int assetId = frm.getAssetId();
            frm.setClientId(clientId);
            frm.setAssetId(assetId);
            int pdeptId = frm.getPdeptId();
            FrameworkInfo info = null;
            if(request.getSession().getAttribute("BASICINFO") != null)
                info = (FrameworkInfo) request.getSession().getAttribute("BASICINFO");
            int assettypeId = 0;
            if(info != null)
                assettypeId = info.getAssettypeId();
            Collection pdepts = pdept.getPdepts(assettypeId);
            frm.setPdepts(pdepts); 
            frm.setPdeptId(pdeptId);
            
            int positionIdHidden = frm.getPositionIdHidden();
            int status = frm.getStatus();
            framework.deleterole(assetId, positionIdHidden, status, uId);
            frm.setPositionIdHidden(-1);
            frm.setStatus(-1);
            
            ArrayList list = framework.getPositionList(assetId, pdeptId);
            request.setAttribute("PLIST", list);
            request.removeAttribute("SAVED");
            return mapping.findForward("positionlist");
        }        
        else if(frm.getDoAssign() != null && frm.getDoAssign().equals("yes"))
        {
            frm.setDoAssign("no");
            int clientId = frm.getClientId();
            int assetId = frm.getAssetId();
            frm.setClientId(clientId);
            frm.setAssetId(assetId); 
            
            int positionIdHidden = frm.getPositionIdHidden();
            int pdeptIdHidden = frm.getPdeptIdHidden();
            frm.setPositionIdHidden(positionIdHidden);
            frm.setPdeptIdHidden(pdeptIdHidden);
            frm.setPids("");
            FrameworkInfo finfo = framework.getBasicInfoForAssign(assetId, positionIdHidden, pdeptIdHidden, "");
            request.getSession().setAttribute("BASICINFOASSIGN", finfo);  
            Collection pcodes = framework.getRoles(pdeptIdHidden);
            frm.setPcodes(pcodes);
            Collection assessmenttypes = framework.getAssessmenttypes(pdeptIdHidden);
            frm.setAssessmenttypes(assessmenttypes);
            Collection priorities = framework.getPriorities(assetId, pdeptIdHidden);
            frm.setPriorities(priorities);
            frm.setPcodeId(-1);
            frm.setPassessmenttypeId(-1);
            frm.setPriorityId(-1);
            
            request.removeAttribute("QLIST");
            request.removeAttribute("SAVED");
            return mapping.findForward("assign");
        }
        else if(frm.getDoAssignAll() != null && frm.getDoAssignAll().equals("yes")) 
        {
            frm.setDoAssignAll("no");
            int clientId = frm.getClientId();
            int assetId = frm.getAssetId();
            frm.setClientId(clientId);
            frm.setAssetId(assetId); 
            String[] positioncb = frm.getPositioncb();
            String pids = framework.makeCommaDelimString(positioncb);
            frm.setPids(pids);
            
            int pdeptIdHidden = frm.getPdeptIdHidden();
            frm.setPdeptIdHidden(pdeptIdHidden);
            
            frm.setPositionIdHidden(-1);            
            FrameworkInfo finfo = framework.getBasicInfoForAssign(assetId, -1, pdeptIdHidden, pids);
            request.getSession().setAttribute("BASICINFOASSIGN", finfo);  
            Collection pcodes = framework.getRoles(pdeptIdHidden);
            frm.setPcodes(pcodes);
            Collection assessmenttypes = framework.getAssessmenttypes(pdeptIdHidden);
            frm.setAssessmenttypes(assessmenttypes);
            Collection priorities = framework.getPriorities(assetId, pdeptIdHidden);
            frm.setPriorities(priorities);
            frm.setPcodeId(-1);
            frm.setPassessmenttypeId(-1);
            frm.setPriorityId(-1);
            
            request.removeAttribute("QLIST");
            request.removeAttribute("SAVED");
            return mapping.findForward("assign");
        }
        else if(frm.getDoChangePcode() != null && frm.getDoChangePcode().equals("yes"))
        {
            frm.setDoChangePcode("no");
            int clientId = frm.getClientId();
            int assetId = frm.getAssetId();
            frm.setClientId(clientId);
            frm.setAssetId(assetId); 
            
            int positionIdHidden = frm.getPositionIdHidden();
            int pdeptIdHidden = frm.getPdeptIdHidden();
            frm.setPositionIdHidden(positionIdHidden);
            frm.setPdeptIdHidden(pdeptIdHidden);
            int pcodeId = frm.getPcodeId();
            frm.setPcodeIdHidden(pcodeId);
            String pcodeName = frm.getPcodeName();
            
            int idarr[] = framework.getIdList(assetId, positionIdHidden, pcodeId);
            int passessmenttypeId_set = 0, priorityId_set = 0, month_set = 0;
            if(idarr != null && idarr.length == 3)
            {
                passessmenttypeId_set = idarr[0];
                priorityId_set = idarr[1];
                month_set = idarr[2];
            }
            
            Collection pcodes = framework.getRoles(pdeptIdHidden);
            frm.setPcodes(pcodes);
            Collection assessmenttypes = framework.getAssessmenttypes(pdeptIdHidden);
            frm.setAssessmenttypes(assessmenttypes);
            Collection priorities = framework.getPriorities(assetId, pdeptIdHidden);
            frm.setPriorities(priorities);
            frm.setPcodeId(pcodeId);            
            frm.setPassessmenttypeId(passessmenttypeId_set);
            frm.setPriorityId(priorityId_set);
            frm.setMonthId(month_set);
            
            ArrayList list1 = framework.getCategoryList(pcodeId, pdeptIdHidden);
            ArrayList list2 = framework.getQuestionList(pcodeId, assetId, positionIdHidden, pdeptIdHidden);
            request.setAttribute("PCODENAME", pcodeName);
            request.setAttribute("CLIST", list1);
            request.setAttribute("QLIST", list2);
            request.removeAttribute("SAVED");
            return mapping.findForward("assign");
        }
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            frm.setDoSave("no");
            int clientId = frm.getClientId();
            int assetId = frm.getAssetId();
            frm.setClientId(clientId);
            frm.setAssetId(assetId); 
            
            int positionIdHidden = frm.getPositionIdHidden();
            int pdeptIdHidden = frm.getPdeptIdHidden();
            frm.setPositionIdHidden(positionIdHidden);
            frm.setPdeptIdHidden(pdeptIdHidden);
            int pcodeId = frm.getPcodeIdHidden();
            int monthId = frm.getMonthId();
            String pids = frm.getPids();
            frm.setPids(pids);
            Collection pcodes = framework.getRoles(pdeptIdHidden);
            frm.setPcodes(pcodes);
            Collection assessmenttypes = framework.getAssessmenttypes(pdeptIdHidden);
            frm.setAssessmenttypes(assessmenttypes);
            Collection priorities = framework.getPriorities(assetId, pdeptIdHidden);
            frm.setPriorities(priorities);
            frm.setPcodeId(-1);                
            int categoryId[] = frm.getCategoryId();
            int questionId[] = frm.getQuestionId();
            framework.createQuestionDetail(assetId, positionIdHidden, pcodeId, frm.getPriorityId(), 
                frm.getPassessmenttypeId(), categoryId, questionId, uId, pids, monthId);
            frm.setPassessmenttypeId(-1);
            frm.setPriorityId(-1);
            frm.setMonthId(-1);
            if(pids != null && pids.equals(""))
            {
                FrameworkInfo finfo = framework.getBasicInfoForAssign(assetId, positionIdHidden, pdeptIdHidden, "");
                request.getSession().setAttribute("BASICINFOASSIGN", finfo); 
            }
            else
            {
                FrameworkInfo finfo = framework.getBasicInfoForAssign(assetId, -1, pdeptIdHidden, pids);
                request.getSession().setAttribute("BASICINFOASSIGN", finfo); 
            }
            
            request.setAttribute("SAVED", "yes");
            frm.setPcodeIdHidden(-1);
            frm.setPcodeName("");
            request.removeAttribute("PCODENAME");
            request.removeAttribute("CLIST");
            request.removeAttribute("QLIST");            
            return mapping.findForward("assign");
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

            ArrayList frameworkList = framework.getFrameworkByName(search, 0, count, allclient, permission, cids, assetids,clientIdIndex, assetIdIndex);
            int cnt = 0;
            if (frameworkList.size() > 0) {
                FrameworkInfo cinfo = (FrameworkInfo) frameworkList.get(frameworkList.size() - 1);
                cnt = cinfo.getClientId();
                frameworkList.remove(frameworkList.size() - 1);
            }
            request.getSession().setAttribute("FRAMEWORK_LIST", frameworkList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
