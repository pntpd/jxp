package com.web.jxp.knowledgebasematrix;

import com.web.jxp.assettype.Assettype;
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

public class KnowledgebasematrixAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KnowledgebasematrixForm frm = (KnowledgebasematrixForm) form;
        Knowledgebasematrix knowledgebasematrix = new Knowledgebasematrix();
        Assettype assettype = new Assettype();
        int count = knowledgebasematrix.getCount();
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
        Collection clients = knowledgebasematrix.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);

        Collection assets = knowledgebasematrix.getClientAsset(clientIdIndex, assetids, allclient, permission);
        frm.setAssets(assets);

        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);
        int check_user = knowledgebasematrix.checkUserSession(request, 93, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = knowledgebasematrix.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Configure Knowledgebasematrix Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setKnowledgebasematrixId(-1);
            Collection assettypes = assettype.getAssettypes();
            frm.setAssettypes(assettypes);
            frm.setMid(knowledgebasematrix.changeNum(knowledgebasematrix.getMaxId(), 3));
            saveToken(request);
            return mapping.findForward("add_wellnessmatrix");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            frm.setDoView("no");
            print(this, " getDoView block ");
            int knowledgebasematixId = frm.getKnowledgebasematrixId();
            frm.setKnowledgebasematrixId(knowledgebasematixId);
            int clientId = frm.getClientId();
            int assetId = frm.getAssetId();
            frm.setClientId(clientId);
            frm.setAssetId(assetId);
            KnowledgebasematrixInfo info = knowledgebasematrix.getKnowledgebasematrixDetailByIdforDetail(clientId, assetId);
            request.getSession().setAttribute("WELLMATRIX_DETAIL", info);
            ArrayList list = knowledgebasematrix.getPositionList(clientId, assetId);
            request.getSession().setAttribute("WELLMATRIX_POS_LIST", list);
            return mapping.findForward("view_knowledgebasematrix");
        } else if (frm.getDoDeleteDetail() != null && frm.getDoDeleteDetail().equals("yes")) {
            frm.setDoDeleteDetail("no");
            int positionId = frm.getPositionId();
            int clientId = frm.getClientId();
            int assetId = frm.getAssetId();
            int status = frm.getStatus();
            knowledgebasematrix.deleteKnowledgebasematrixDetail(clientId, assetId, positionId, uId, status);

            KnowledgebasematrixInfo info = knowledgebasematrix.getKnowledgebasematrixDetailByIdforDetail(clientId, assetId);
            request.getSession().setAttribute("WELLMATRIX_DETAIL", info);
            ArrayList list = knowledgebasematrix.getPositionList(clientId, assetId);
            request.getSession().setAttribute("WELLMATRIX_POS_LIST", list);
            return mapping.findForward("view_knowledgebasematrix");
        } else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
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
            ArrayList wellnessmatrixList = knowledgebasematrix.getKnowledgebasematrixByName(search, 0, count, allclient, permission, cids, assetids, clientIdIndex, assetIdIndex);
            int cnt = 0;
            if (wellnessmatrixList.size() > 0) {
                KnowledgebasematrixInfo cinfo = (KnowledgebasematrixInfo) wellnessmatrixList.get(wellnessmatrixList.size() - 1);
                cnt = cinfo.getClientId();
                wellnessmatrixList.remove(wellnessmatrixList.size() - 1);
            }
            request.getSession().setAttribute("WELLMATRIX_LIST", wellnessmatrixList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display");
        } else if (frm.getDoAssign() != null && frm.getDoAssign().equals("yes")) {
            frm.setDoAssign("no");
            int positionId = frm.getPositionId();
            frm.setPositionId(positionId);
            int knowledgebasematixId = frm.getKnowledgebasematrixId();
            frm.setKnowledgebasematrixId(knowledgebasematixId);
            frm.setPositionId(positionId);
            KnowledgebasematrixInfo info = knowledgebasematrix.getPositionInfo(positionId);
            request.getSession().setAttribute("WELLPINFO", info);
            KnowledgebasematrixInfo wdinfo = null;
            if (request.getSession().getAttribute("WELLMATRIX_DETAIL") != null) {
                wdinfo = (KnowledgebasematrixInfo) request.getSession().getAttribute("WELLMATRIX_DETAIL");
            }

            int assettypeids = 0;
            if (wdinfo != null) {
                assettypeids = wdinfo.getAssettypeId();
            }
            Collection categories = knowledgebasematrix.getCategories(assettypeids);
            frm.setCategories(categories);
            request.removeAttribute("WELLCATNAME");
            frm.setCategoryIdHidden(-1);
            request.removeAttribute("SAVED");
            return mapping.findForward("assign");
        } else if (frm.getDoCategory() != null && frm.getDoCategory().equals("yes")) {
            frm.setDoCategory("no");
            int positionId = frm.getPositionId();
            int clientId = frm.getClientId();
            int assetId = frm.getAssetId();
            int assettypeId = frm.getAssettypeId();
            frm.setClientId(clientId);
            frm.setAssetId(assetId);
            int knowledgebasematixId = frm.getKnowledgebasematrixId();
            frm.setKnowledgebasematrixId(knowledgebasematixId);
            KnowledgebasematrixInfo wdinfo = null;
            if (request.getSession().getAttribute("WELLMATRIX_DETAIL") != null) {
                wdinfo = (KnowledgebasematrixInfo) request.getSession().getAttribute("WELLMATRIX_DETAIL");
            }

            int assettypeids = 0;
            if (wdinfo != null) {
                assettypeids = wdinfo.getAssettypeId();
            }
            Collection categories = knowledgebasematrix.getCategories(assettypeids);
            frm.setCategories(categories);
            int categoryId = frm.getCategoryId();
            frm.setCategoryIdHidden(categoryId);
            String categoryName = frm.getCategoryName() != null ? frm.getCategoryName() : "";
            frm.setCategoryId(categoryId);
            KnowledgebasematrixInfo cidinfo = new KnowledgebasematrixInfo(categoryId, categoryName);
            ArrayList subcat_list = knowledgebasematrix.getSubCategoryList(categoryId);
            ArrayList question_list = knowledgebasematrix.getQuestList(clientId, assetId, positionId, categoryId, assettypeId);
            request.setAttribute("WELLCATNAME", cidinfo);
            request.setAttribute("WELLSUBCATLIST", subcat_list);
            request.setAttribute("WELLQUEST_LIST", question_list);
            request.removeAttribute("SAVED");
            return mapping.findForward("assign");
        } else if (frm.getDoSaveCourse() != null && frm.getDoSaveCourse().equals("yes")) {
            frm.setDoSaveCourse("no");
            int positionId = frm.getPositionId();
            int clientId = frm.getClientId();
            int assetId = frm.getAssetId();
            frm.setClientId(clientId);
            frm.setAssetId(assetId);
            int knowledgebasematixId = frm.getKnowledgebasematrixId();
            frm.setKnowledgebasematrixId(knowledgebasematixId);
            KnowledgebasematrixInfo wdinfo = null;
            if (request.getSession().getAttribute("WELLMATRIX_DETAIL") != null) {
                wdinfo = (KnowledgebasematrixInfo) request.getSession().getAttribute("WELLMATRIX_DETAIL");
            }
            int assettypeids = 0;
            if (wdinfo != null) {
                assettypeids = wdinfo.getAssettypeId();
            }
            Collection categories = knowledgebasematrix.getCategories(assettypeids);
            frm.setCategories(categories);
            int categoryId = frm.getCategoryId();
            frm.setCategoryIdHidden(categoryId);

            int subcategoryId[] = frm.getSubcategoryId();
            int questionId[] = frm.getQuestionId();
            knowledgebasematrix.createKnowledgebasematrixDetail(categoryId, positionId, subcategoryId, questionId, uId, clientId, assetId);

            request.removeAttribute("WELLCATNAME");
            request.removeAttribute("WELLSUBCATLIST");
            request.removeAttribute("WELLQUEST_LIST");
            request.setAttribute("SAVED", "yes");
            frm.setCategoryId(-1);
            frm.setCategoryIdHidden(-1);
            return mapping.findForward("assign");
        } else {
            print(this, "else block.");
            if (frm.getCtp() == 0) {
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }

            ArrayList wellnessmatrixList = knowledgebasematrix.getKnowledgebasematrixByName(search, 0, count, allclient, permission, cids, assetids, clientIdIndex, assetIdIndex);
            int cnt = 0;
            if (wellnessmatrixList.size() > 0) {
                KnowledgebasematrixInfo cinfo = (KnowledgebasematrixInfo) wellnessmatrixList.get(wellnessmatrixList.size() - 1);
                cnt = cinfo.getClientId();
                wellnessmatrixList.remove(wellnessmatrixList.size() - 1);
            }
            request.getSession().setAttribute("WELLMATRIX_LIST", wellnessmatrixList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
