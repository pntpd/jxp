package com.web.jxp.managewellness;

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

public class ManagewellnessAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ManagewellnessForm frm = (ManagewellnessForm) form;
        Managewellness managewellness = new Managewellness();
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
        Collection clients = managewellness.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);

        Collection assets = managewellness.getClientAsset(clientIdIndex, assetids, allclient, permission);
        frm.setAssets(assets);
        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);
        
        Collection positions = managewellness.getPositions(assetIdIndex);
        frm.setPositions(positions);
        int positionIdIndex = frm.getPositionIdIndex();
        frm.setPositionIdIndex(positionIdIndex);
        
        int mode = frm.getMode() > 0 ? frm.getMode() : 1;
        frm.setMode(mode);
        Collection categories = managewellness.getCategories(assetIdIndex);
        frm.setCategories(categories); 
        int categoryIdIndex = frm.getCategoryIdIndex();
        frm.setCategoryIdIndex(categoryIdIndex);
        Collection subcategories = managewellness.getSubCategories(assetIdIndex, categoryIdIndex);
        frm.setSubcategories(subcategories); 
        int subcategoryIdIndex = frm.getSubcategoryIdIndex();
        frm.setSubcategoryIdIndex(subcategoryIdIndex);
        
        int check_user = managewellness.checkUserSession(request, 73, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = managewellness.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Manage Wellness Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        } 
        if (frm.getDoSearchAsset() != null && frm.getDoSearchAsset().equals("yes")) 
        {
            request.removeAttribute("SENDCOURSEEMAIL");
            frm.setDoSearchAsset("no");
            ArrayList managewellnessList = managewellness.getManagewellnessByName(clientIdIndex, assetIdIndex, positionIdIndex, mode, search, categoryIdIndex, subcategoryIdIndex);            
            request.getSession().setAttribute("MANAGEWELLNESS_LIST", managewellnessList);
            int arr[] = managewellness.getCounts(assetIdIndex,-1,-1); 
            request.getSession().setAttribute("ARR_COUNT", arr);
            if(mode == 1)
                return mapping.findForward("list1");
            else
                return mapping.findForward("list2");
        }
        else if (frm.getDoSearch() != null && frm.getDoSearch().equals("yes")) 
        {
            frm.setDoSearch("no");
            ArrayList managewellnessList = managewellness.getManagewellnessByName(clientIdIndex, assetIdIndex, positionIdIndex, mode, search, categoryIdIndex, subcategoryIdIndex);            
            request.getSession().setAttribute("MANAGEWELLNESS_LIST", managewellnessList);
            int arr[] = managewellness.getCounts(assetIdIndex,-1,-1); 
            request.getSession().setAttribute("ARR_COUNT", arr);
            if(mode == 1)
                return mapping.findForward("list1");
            else
                return mapping.findForward("list2");
        }
        else if(frm.getDoAssign1() != null && frm.getDoAssign1().equals("yes"))
        {
            frm.setDoAssign1("no");
            int crewrotationId = frm.getCrewrotationId();
            frm.setCrewrotationId(crewrotationId);
            ManagewellnessInfo info = managewellness.getBasicDetail(crewrotationId);
            int positionId = 0;
            if(info != null)
                positionId = info.getPositionId();
            
            frm.setPositionId(positionId); 
            int cid = frm.getCategoryIdDetail();
            int scid = frm.getSubcategoryIdDetail(); 
            String searchd = frm.getSearchdetail() != null ? frm.getSearchdetail() : "";
            frm.setCategoryIdDetail(cid);
            frm.setSubcategoryIdDetail(scid);
            frm.setSearchdetail(searchd);
            int statusIndex = frm.getStatusIndex();
            frm.setStatusIndex(statusIndex);
            
            ArrayList assignlist1 = managewellness.getListAssign1( clientIdIndex, assetIdIndex, positionId, crewrotationId, searchd, statusIndex, cid, scid);            
            request.setAttribute("WELLASSIGNLIST1", assignlist1);
            request.setAttribute("MANAGEWELLNESS_INFO1", info);    
            int arr[] = managewellness.getCounts(assetIdIndex,crewrotationId,-1); 
            request.getSession().setAttribute("ARR_COUNT", arr);
            return mapping.findForward("assign1");
        }
        else if(frm.getDoAssign2()!= null && frm.getDoAssign2().equals("yes"))
        {
            frm.setDoAssign2("no");
            int positionId2Index = frm.getPositionId2Index();
            frm.setPositionId2Index(positionId2Index);
            String searchdetail = frm.getSearchdetail() != null ? frm.getSearchdetail() : "";
            frm.setSearchdetail(searchdetail);
            int subcategoryId = frm.getSubcategoryId();
            String positionIds = managewellness.getPositionIds(subcategoryId, assetIdIndex);
            ArrayList assignlist2 = managewellness.getAssign2list(clientIdIndex, assetIdIndex, positionId2Index, searchdetail, positionIds,subcategoryId);  
            request.setAttribute("WELLASSIGNLIST2", assignlist2);
           ManagewellnessInfo info = managewellness.getAssign2Info(clientIdIndex, assetIdIndex, subcategoryId);
           request.setAttribute("MANAGEWELLNESS_INFO2", info); 
            int arr[] = managewellness.getCounts(assetIdIndex,-1,subcategoryId); 
            request.getSession().setAttribute("ARR_COUNT", arr);
            
            return mapping.findForward("assign2");
        }
        else if (frm.getDoDeletedate()!= null && frm.getDoDeletedate().equals("yes")) 
        {
            frm.setDoDeletedate("no");
            print(this, "getDoDeletedate block");
            int subcategoryId = frm.getSubcategoryId();
            managewellness.updateschedule(clientIdIndex, assetIdIndex, subcategoryId, uId);
            frm.setMode(2);
            ArrayList managewellnessList = managewellness.getManagewellnessByName(clientIdIndex, assetIdIndex, positionIdIndex, mode, search, categoryIdIndex, subcategoryIdIndex);            
            request.getSession().setAttribute("MANAGEWELLNESS_LIST", managewellnessList);   
             int arr[] = managewellness.getCounts(assetIdIndex,-1,-1); 
            request.getSession().setAttribute("ARR_COUNT", arr);
            
            return mapping.findForward("list2");
        }
        else if (frm.getDoSaveschedule()!= null && frm.getDoSaveschedule().equals("yes")) 
        {
            request.removeAttribute("SENDCOURSEEMAIL");
            frm.setDoSaveschedule("no");
            print(this, "getDoSaveschedule block");
            String startdate = frm.getStartdate();
            String enddate = frm.getEnddate();
            String mcrids = frm.getMcrids();
            managewellness.createSchedule(clientIdIndex, assetIdIndex, mcrids, uId, startdate, enddate);
            frm.setMode(2);
            ArrayList managewellnessList = managewellness.getManagewellnessByName(clientIdIndex, assetIdIndex, positionIdIndex, mode, search, categoryIdIndex, subcategoryIdIndex);            
            request.getSession().setAttribute("MANAGEWELLNESS_LIST", managewellnessList); 
            int arr[] = managewellness.getCounts(assetIdIndex,-1,-1); 
            request.getSession().setAttribute("ARR_COUNT", arr);
            return mapping.findForward("list2");
        }
        
        else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) 
        {
            request.removeAttribute("SENDCOURSEEMAIL");
            frm.setDoCancel("no");
            print(this, "doCancel block");
            ArrayList managewellnessList = managewellness.getManagewellnessByName(clientIdIndex, assetIdIndex, positionIdIndex, mode, search, categoryIdIndex, subcategoryIdIndex);            
            request.getSession().setAttribute("MANAGEWELLNESS_LIST", managewellnessList);
            int arr[] = managewellness.getCounts(assetIdIndex,-1,-1); 
            request.getSession().setAttribute("ARR_COUNT", arr);
            if(mode == 1)
                return mapping.findForward("list1");
            else
                return mapping.findForward("list2");
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
            request.getSession().removeAttribute("MANAGEWELLNESS_LIST");
            request.getSession().removeAttribute("ARR_COUNT");
            return mapping.findForward("list1");
        }        
    }
}
