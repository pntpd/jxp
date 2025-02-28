package com.web.jxp.assessmentparameter;

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

public class AssessmentParameterAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AssessmentParameterForm frm = (AssessmentParameterForm) form;
        AssessmentParameter assessmentParameter = new AssessmentParameter();
        Validate vobj = new Validate();
        int count = assessmentParameter.getCount();
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
        int check_user = assessmentParameter.checkUserSession(request, 40, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = assessmentParameter.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "AssessmentParameter Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setAssessmentParameterId(-1);
            saveToken(request);
            return mapping.findForward("add_assessmentparameter");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            int assessmentParameterId = frm.getAssessmentParameterId();
            frm.setAssessmentParameterId(assessmentParameterId);
            AssessmentParameterInfo info = assessmentParameter.getAssessmentParameterDetailById(assessmentParameterId);
            if (info != null) {
                frm.setName(info.getName());
                frm.setDescription(info.getDescription());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_assessmentparameter");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            int assessmentParameterId = frm.getAssessmentParameterId();
            frm.setAssessmentParameterId(assessmentParameterId);
            AssessmentParameterInfo info = assessmentParameter.getAssessmentParameterDetailByIdforDetail(assessmentParameterId);
            request.setAttribute("ASSESSMENTPARAMETER_DETAIL", info);
            return mapping.findForward("view_assessmentparameter");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int assessmentParameterId = frm.getAssessmentParameterId();
                String name = vobj.replacename(frm.getName());
                String description = vobj.replacedesc(frm.getDescription());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = assessmentParameter.getLocalIp();
                int ck = assessmentParameter.checkDuplicacy(assessmentParameterId, name);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Assessment Parameter already exists");
                    return mapping.findForward("add_assessmentparameter");
                }
                AssessmentParameterInfo info = new AssessmentParameterInfo(assessmentParameterId, name, description, status, uId);
                if (assessmentParameterId <= 0) {
                    int cc = assessmentParameter.createAssessmentParameter(info);
                    if (cc > 0) {
                        assessmentParameter.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 40, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    ArrayList assessmentParameterList = assessmentParameter.getAssessmentParameterByName(search, 0, count);
                    int cnt = 0;
                    if (assessmentParameterList.size() > 0) {
                        AssessmentParameterInfo cinfo = (AssessmentParameterInfo) assessmentParameterList.get(assessmentParameterList.size() - 1);
                        cnt = cinfo.getAssessmentParameterId();
                        assessmentParameterList.remove(assessmentParameterList.size() - 1);
                    }
                    request.getSession().setAttribute("ASSESSMENTPARAMETER_LIST", assessmentParameterList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    assessmentParameter.updateAssessmentParameter(info);
                    assessmentParameter.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 40, assessmentParameterId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList assessmentParameterList = assessmentParameter.getAssessmentParameterByName(search, next, count);
                    int cnt = 0;
                    if (assessmentParameterList.size() > 0) {
                        AssessmentParameterInfo cinfo = (AssessmentParameterInfo) assessmentParameterList.get(assessmentParameterList.size() - 1);
                        cnt = cinfo.getAssessmentParameterId();
                        assessmentParameterList.remove(assessmentParameterList.size() - 1);
                    }
                    request.getSession().setAttribute("ASSESSMENTPARAMETER_LIST", assessmentParameterList);
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
            ArrayList assessmentParameterList = assessmentParameter.getAssessmentParameterByName(search, next, count);
            int cnt = 0;
            if (assessmentParameterList.size() > 0) {
                AssessmentParameterInfo cinfo = (AssessmentParameterInfo) assessmentParameterList.get(assessmentParameterList.size() - 1);
                cnt = cinfo.getAssessmentParameterId();
                assessmentParameterList.remove(assessmentParameterList.size() - 1);
            }
            request.getSession().setAttribute("ASSESSMENTPARAMETER_LIST", assessmentParameterList);
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

            ArrayList assessmentParameterList = assessmentParameter.getAssessmentParameterByName(search, 0, count);
            int cnt = 0;
            if (assessmentParameterList.size() > 0) {
                AssessmentParameterInfo cinfo = (AssessmentParameterInfo) assessmentParameterList.get(assessmentParameterList.size() - 1);
                cnt = cinfo.getAssessmentParameterId();
                assessmentParameterList.remove(assessmentParameterList.size() - 1);
            }
            request.getSession().setAttribute("ASSESSMENTPARAMETER_LIST", assessmentParameterList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
