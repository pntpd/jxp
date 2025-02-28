package com.web.jxp.assessmentanswertype;

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
import java.util.Stack;

public class AssessmentAnswerTypeAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AssessmentAnswerTypeForm frm = (AssessmentAnswerTypeForm) form;
        AssessmentAnswerType assessmentAnswerType = new AssessmentAnswerType();
        Validate vobj = new Validate();
        int count = assessmentAnswerType.getCount();
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
        int check_user = assessmentAnswerType.checkUserSession(request, 41, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = assessmentAnswerType.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "AssessmentAnswerType Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setAssessmentAnswerTypeId(-1);
            saveToken(request);
            return mapping.findForward("add_assessmentanswertype");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            int assessmentAnswerTypeId = frm.getAssessmentAnswerTypeId();
            frm.setAssessmentAnswerTypeId(assessmentAnswerTypeId);
            AssessmentAnswerTypeInfo info = assessmentAnswerType.getAssessmentAnswerTypeDetailById(assessmentAnswerTypeId);
            if (info != null) {
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_assessmentanswertype");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            int assessmentAnswerTypeId = frm.getAssessmentAnswerTypeId();
            frm.setAssessmentAnswerTypeId(assessmentAnswerTypeId);
            AssessmentAnswerTypeInfo info = assessmentAnswerType.getAssessmentAnswerTypeDetailByIdforDetail(assessmentAnswerTypeId);
            request.setAttribute("ASSESSMENTANSWERTYPE_DETAIL", info);
            return mapping.findForward("view_assessmentanswertype");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int assessmentAnswerTypeId = frm.getAssessmentAnswerTypeId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = assessmentAnswerType.getLocalIp();
                int ck = assessmentAnswerType.checkDuplicacy(assessmentAnswerTypeId, name);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Assessment Answer Type already exists");
                    return mapping.findForward("add_assessmentanswertype");
                }
                AssessmentAnswerTypeInfo info = new AssessmentAnswerTypeInfo(assessmentAnswerTypeId, name, status, uId);
                if (assessmentAnswerTypeId <= 0) {
                    int cc = assessmentAnswerType.createAssessmentAnswerType(info);
                    if (cc > 0) {
                        assessmentAnswerType.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 41, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    ArrayList assessmentAnswerTypeList = assessmentAnswerType.getAssessmentAnswerTypeByName(search, 0, count);
                    int cnt = 0;
                    if (assessmentAnswerTypeList.size() > 0) {
                        AssessmentAnswerTypeInfo cinfo = (AssessmentAnswerTypeInfo) assessmentAnswerTypeList.get(assessmentAnswerTypeList.size() - 1);
                        cnt = cinfo.getAssessmentAnswerTypeId();
                        assessmentAnswerTypeList.remove(assessmentAnswerTypeList.size() - 1);
                    }
                    request.getSession().setAttribute("ASSESSMENTANSWERTYPE_LIST", assessmentAnswerTypeList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    assessmentAnswerType.updateAssessmentAnswerType(info);
                    assessmentAnswerType.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 41, assessmentAnswerTypeId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList assessmentAnswerTypeList = assessmentAnswerType.getAssessmentAnswerTypeByName(search, next, count);
                    int cnt = 0;
                    if (assessmentAnswerTypeList.size() > 0) {
                        AssessmentAnswerTypeInfo cinfo = (AssessmentAnswerTypeInfo) assessmentAnswerTypeList.get(assessmentAnswerTypeList.size() - 1);
                        cnt = cinfo.getAssessmentAnswerTypeId();
                        assessmentAnswerTypeList.remove(assessmentAnswerTypeList.size() - 1);
                    }
                    request.getSession().setAttribute("ASSESSMENTANSWERTYPE_LIST", assessmentAnswerTypeList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", next + "");
                    request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");

                    return mapping.findForward("display");
                }
            }
        } else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            print(this, "doCancel block");
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList assessmentAnswerTypeList = assessmentAnswerType.getAssessmentAnswerTypeByName(search, next, count);
            int cnt = 0;
            if (assessmentAnswerTypeList.size() > 0) {
                AssessmentAnswerTypeInfo cinfo = (AssessmentAnswerTypeInfo) assessmentAnswerTypeList.get(assessmentAnswerTypeList.size() - 1);
                cnt = cinfo.getAssessmentAnswerTypeId();
                assessmentAnswerTypeList.remove(assessmentAnswerTypeList.size() - 1);
            }
            request.getSession().setAttribute("ASSESSMENTANSWERTYPE_LIST", assessmentAnswerTypeList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display");
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

            ArrayList assessmentAnswerTypeList = assessmentAnswerType.getAssessmentAnswerTypeByName(search, 0, count);
            int cnt = 0;
            if (assessmentAnswerTypeList.size() > 0) {
                AssessmentAnswerTypeInfo cinfo = (AssessmentAnswerTypeInfo) assessmentAnswerTypeList.get(assessmentAnswerTypeList.size() - 1);
                cnt = cinfo.getAssessmentAnswerTypeId();
                assessmentAnswerTypeList.remove(assessmentAnswerTypeList.size() - 1);
            }
            request.getSession().setAttribute("ASSESSMENTANSWERTYPE_LIST", assessmentAnswerTypeList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
