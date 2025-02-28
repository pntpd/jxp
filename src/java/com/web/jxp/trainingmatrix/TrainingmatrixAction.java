package com.web.jxp.trainingmatrix;

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

public class TrainingmatrixAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TrainingmatrixForm frm = (TrainingmatrixForm) form;
        Trainingmatrix trainingmatrix = new Trainingmatrix();
        int count = trainingmatrix.getCount();
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
        Collection clients = trainingmatrix.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);

        Collection assets = trainingmatrix.getClientAsset(clientIdIndex, assetids, allclient, permission);
        frm.setAssets(assets);

        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);
        int check_user = trainingmatrix.checkUserSession(request, 66, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = trainingmatrix.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Training Matrix Module");
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
            
            TrainingmatrixInfo info = trainingmatrix.getBasicInfo(assetId);
            request.getSession().setAttribute("BASICINFO", info);
            Collection categories = trainingmatrix.getCategories();
            frm.setCategories(categories);
            Collection positions = trainingmatrix.getPositions(assetId);
            frm.setPositions(positions);
            frm.setCategoryId(-1);
            frm.setPositionId(-1);
            frm.setCategoryIdHidden(-1);
            frm.setPositionIdHidden(-1);
            request.removeAttribute("SAVED");
            return mapping.findForward("assign");
        }
        else if(frm.getDoCategory() != null && frm.getDoCategory().equals("yes"))
        {
            frm.setDoCategory("no");
            int clientId = frm.getClientId();
            int assetId = frm.getAssetId();
            frm.setClientId(clientId);
            frm.setAssetId(assetId);
            
            Collection categories = trainingmatrix.getCategories();
            frm.setCategories(categories);
            Collection positions = trainingmatrix.getPositions(assetId);
            frm.setPositions(positions);            
            int categoryId = frm.getCategoryId();
            int positionId = frm.getPositionId();
            frm.setCategoryIdHidden(categoryId);
            frm.setCategoryId(categoryId);
            frm.setPositionId(positionId);
            frm.setPositionIdHidden(positionId);
            
            ArrayList subcat_list = trainingmatrix.getSubCategoryList(categoryId);            
            ArrayList course_list = trainingmatrix.getCourseList(clientId, assetId, positionId, categoryId);            
            ArrayList list1 = trainingmatrix.getTraingKindList(clientId, assetId);
            ArrayList list2 = trainingmatrix.getTraingList(clientId, assetId);
            ArrayList list3 = trainingmatrix.getLevelList();
            request.getSession().setAttribute("SUBCATLIST", subcat_list);
            request.setAttribute("COURSE_LIST", course_list);
            request.getSession().setAttribute("LIST1", list1);
            request.getSession().setAttribute("LIST2", list2);
            request.getSession().setAttribute("LIST3", list3);
            request.removeAttribute("SAVED");
            return mapping.findForward("assign");
        }
        
        else if(frm.getDoSaveCourse() != null && frm.getDoSaveCourse().equals("yes"))
        {
            frm.setDoSaveCourse("no");
            int clientId = frm.getClientId();
            int assetId = frm.getAssetId();
            frm.setClientId(clientId);
            frm.setAssetId(assetId);
            Collection categories = trainingmatrix.getCategories();
            frm.setCategories(categories);
            Collection positions = trainingmatrix.getPositions(assetId);
            frm.setPositions(positions);
            int categoryId = frm.getCategoryIdHidden();
            int positionId = frm.getPositionIdHidden();
            frm.setCategoryId(categoryId);
            frm.setPositionId(positionId);
            frm.setCategoryIdHidden(categoryId);
            frm.setPositionIdHidden(positionId);           
            
            int subcategoryId[] = frm.getSubcategoryId();
            int courseId[] = frm.getCourseId();
            int tctypeId[] = frm.getTctypeId();
            int trainingId[] = frm.getTrainingId();
            int levelId[] = frm.getLevelId();
            int relativeId[] = frm.getCourseIdrel();
            if(clientId > 0 && assetId > 0 && categoryId > 0 && positionId > 0)
            {
                trainingmatrix.createMatrixDetail(clientId, assetId, categoryId, positionId, subcategoryId, courseId, 
                    tctypeId, trainingId, levelId, relativeId, uId);            
            }
            frm.setCategoryId(-1);
            frm.setPositionId(-1);
            frm.setCategoryIdHidden(-1);
            frm.setPositionIdHidden(-1); 
            request.setAttribute("SAVED", "yes");
            return mapping.findForward("assign");
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
            ArrayList trainingmatrixList = trainingmatrix.getTrainingmatrixByName(search, next, count,allclient, permission, cids, assetids, clientIdIndex, assetIdIndex);
            int cnt = 0;
            if (trainingmatrixList.size() > 0) {
                TrainingmatrixInfo cinfo = (TrainingmatrixInfo) trainingmatrixList.get(trainingmatrixList.size() - 1);
                cnt = cinfo.getClientId();
                trainingmatrixList.remove(trainingmatrixList.size() - 1);
            }
            request.getSession().setAttribute("TRAININGMATRIX_LIST", trainingmatrixList);
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

            ArrayList trainingmatrixList = trainingmatrix.getTrainingmatrixByName(search, 0, count, allclient, permission, cids, assetids,clientIdIndex, assetIdIndex);
            int cnt = 0;
            if (trainingmatrixList.size() > 0) {
                TrainingmatrixInfo cinfo = (TrainingmatrixInfo) trainingmatrixList.get(trainingmatrixList.size() - 1);
                cnt = cinfo.getClientId();
                trainingmatrixList.remove(trainingmatrixList.size() - 1);
            }
            request.getSession().setAttribute("TRAININGMATRIX_LIST", trainingmatrixList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
