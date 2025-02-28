package com.web.jxp.matrix;

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

public class MatrixAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MatrixForm frm = (MatrixForm) form;
        Matrix matrix = new Matrix();
        Assettype assettype = new Assettype();
        Validate vobj = new Validate();
        int count = matrix.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0;
        String permission = "N";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
            }
        }
        int check_user = matrix.checkUserSession(request, 64, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = matrix.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Configure Matrix Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setMatrixId(-1);
            Collection assettypes = assettype.getAssettypes();
            frm.setAssettypes(assettypes);
            frm.setMid(matrix.changeNum(matrix.getMaxId(), 3));
            saveToken(request);
            return mapping.findForward("add_matrix");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            frm.setDoModify("no");
            int matrixId = frm.getMatrixId();
            frm.setMatrixId(matrixId);
            MatrixInfo info = matrix.getMatrixDetailById(matrixId);
            if (info != null) {
                Collection assettypes = assettype.getAssettypes();
                frm.setAssettypes(assettypes);
                frm.setMid(matrix.changeNum(matrixId, 3));
                frm.setName(info.getName());
                if (info.getDescription() != null) {
                    frm.setDescription(info.getDescription());
                }
                frm.setAssettypeId(info.getAssettypeId());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_matrix");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            frm.setDoView("no");
            int matrixId = frm.getMatrixId();
            frm.setMatrixId(matrixId);
            MatrixInfo info = matrix.getMatrixDetailByIdforDetail(matrixId);
            request.getSession().setAttribute("MATRIX_DETAIL", info);
            ArrayList list = matrix.getPositionList(matrixId, info.getAssettypeId());
            request.getSession().setAttribute("MATRIX_POS_LIST", list);
            return mapping.findForward("view_matrix");
        } else if (frm.getDoDeleteDetail() != null && frm.getDoDeleteDetail().equals("yes")) {
            frm.setDoDeleteDetail("no");
            int matrixId = frm.getMatrixId();
            frm.setMatrixId(matrixId);
            int matrixpositionId = frm.getMatrixpositionId();
            int status = frm.getStatus();
            matrix.deleteMatrixDetail(matrixpositionId, uId, status);

            MatrixInfo info = matrix.getMatrixDetailByIdforDetail(matrixId);
            request.getSession().setAttribute("MATRIX_DETAIL", info);
            ArrayList list = matrix.getPositionList(matrixId, info.getAssettypeId());
            request.getSession().setAttribute("MATRIX_POS_LIST", list);
            return mapping.findForward("view_matrix");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            frm.setDoSave("no");
            if (isTokenValid(request)) {
                resetToken(request);
                int matrixId = frm.getMatrixId();
                String name = vobj.replacename(frm.getName());
                int assettypeId = frm.getAssettypeId();
                String description = frm.getDescription() != null ? frm.getDescription() : "";
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = matrix.getLocalIp();
                int ck = matrix.checkDuplicacy(matrixId, name, assettypeId);
                if (ck == 1) {
                    saveToken(request);
                    Collection assettypes = assettype.getAssettypes();
                    frm.setAssettypes(assettypes);
                    frm.setAssettypeId(assettypeId);
                    request.setAttribute("MESSAGE", "Matrix name already exists.");
                    return mapping.findForward("add_matrix");
                }
                MatrixInfo info = new MatrixInfo(matrixId, name, assettypeId, description, status, uId);
                if (matrixId <= 0) {
                    int cc = matrix.createMatrix(info);
                    if (cc > 0) {
                        matrixId = cc;
                        matrix.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 64, cc);
                    }
                } else {
                    matrix.updateMatrix(info);
                    matrix.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 64, matrixId);
                }
                frm.setMatrixId(matrixId);
                MatrixInfo minfo = matrix.getMatrixDetailByIdforDetail(matrixId);
                request.getSession().setAttribute("MATRIX_DETAIL", minfo);
                ArrayList list = matrix.getPositionList(matrixId, minfo.getAssettypeId());
                request.getSession().setAttribute("MATRIX_POS_LIST", list);
                return mapping.findForward("view_matrix");
            }
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
            ArrayList matrixList = matrix.getMatrixByName(search, next, count);
            int cnt = 0;
            if (matrixList.size() > 0) {
                MatrixInfo cinfo = (MatrixInfo) matrixList.get(matrixList.size() - 1);
                cnt = cinfo.getMatrixId();
                matrixList.remove(matrixList.size() - 1);
            }
            request.getSession().setAttribute("MATRIX_LIST", matrixList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display");
        } else if (frm.getDoAssign() != null && frm.getDoAssign().equals("yes")) {
            frm.setDoAssign("no");
            int matrixpositionId = frm.getMatrixpositionId();
            int positionId = frm.getPositionId();
            int matrixId = frm.getMatrixId();
            frm.setMatrixId(matrixId);
            if (matrixpositionId <= 0) {
                matrixpositionId = matrix.createMatrixPosition(matrixId, positionId, uId);
            }
            frm.setMatrixpositionId(matrixpositionId);
            frm.setPositionId(positionId);

            MatrixInfo info = matrix.getPositionInfo(matrixpositionId);
            request.getSession().setAttribute("PINFO", info);
            Collection categories = matrix.getCategories();
            frm.setCategories(categories);
            request.removeAttribute("CATNAME");
            frm.setCategoryIdHidden(-1);
            request.removeAttribute("SAVED");
            return mapping.findForward("assign");
        } else if (frm.getDoCategory() != null && frm.getDoCategory().equals("yes")) {
            frm.setDoCategory("no");
            int matrixpositionId = frm.getMatrixpositionId();
            int matrixId = frm.getMatrixId();
            frm.setMatrixId(matrixId);
            frm.setMatrixpositionId(matrixpositionId);
            Collection categories = matrix.getCategories();
            frm.setCategories(categories);
            int categoryId = frm.getCategoryId();
            frm.setCategoryIdHidden(categoryId);
            String categoryName = frm.getCategoryName() != null ? frm.getCategoryName() : "";
            frm.setCategoryId(categoryId);
            MatrixInfo cidinfo = new MatrixInfo(categoryId, categoryName);
            ArrayList subcat_list = matrix.getSubCategoryList(categoryId);
            ArrayList course_list = matrix.getCourseList(matrixpositionId, categoryId);
            request.setAttribute("CATNAME", cidinfo);
            request.setAttribute("SUBCATLIST", subcat_list);
            request.setAttribute("COURSE_LIST", course_list);
            request.removeAttribute("SAVED");
            return mapping.findForward("assign");
        } else if (frm.getDoSaveCourse() != null && frm.getDoSaveCourse().equals("yes")) {
            frm.setDoSaveCourse("no");
            int matrixpositionId = frm.getMatrixpositionId();
            int matrixId = frm.getMatrixId();
            frm.setMatrixId(matrixId);
            frm.setMatrixpositionId(matrixpositionId);
            Collection categories = matrix.getCategories();
            frm.setCategories(categories);
            int categoryId = frm.getCategoryId();
            frm.setCategoryIdHidden(categoryId);

            int subcategoryId[] = frm.getSubcategoryId();
            int courseId[] = frm.getCourseId();
            matrix.createMatrixDetail(categoryId, matrixpositionId, subcategoryId, courseId, uId);

            request.removeAttribute("CATNAME");
            request.removeAttribute("SUBCATLIST");
            request.removeAttribute("COURSE_LIST");
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

            ArrayList matrixList = matrix.getMatrixByName(search, 0, count);
            int cnt = 0;
            if (matrixList.size() > 0) {
                MatrixInfo cinfo = (MatrixInfo) matrixList.get(matrixList.size() - 1);
                cnt = cinfo.getMatrixId();
                matrixList.remove(matrixList.size() - 1);
            }
            request.getSession().setAttribute("MATRIX_LIST", matrixList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
