package com.web.jxp.assessment;

import com.web.jxp.assessmentanswertype.AssessmentAnswerType;
import com.web.jxp.assessmentparameter.AssessmentParameter;
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
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

public class AssessmentAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AssessmentForm frm = (AssessmentForm) form;
        Assessment assessment = new Assessment();
        Validate vobj = new Validate();
        AssessmentParameter assessmentparameter = new AssessmentParameter();
        AssessmentAnswerType assessmentanswertype = new AssessmentAnswerType();
        int count = assessment.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);

        int assessmentparameterIndex = frm.getAssessmentparameterIndex();
        frm.setAssessmentparameterIndex(assessmentparameterIndex);
        Collection assessmentparameters = assessment.getAssessmentParametersindex();
        frm.setAssessmentparameters(assessmentparameters);

        int questiontypeIndex = frm.getQuestiontypeIndex();
        frm.setQuestiontypeIndex(questiontypeIndex);
        Collection questiontypes = assessment.getAssessmentQuestionsIndex();
        frm.setQuestiontypes(questiontypes);

        int uId = 0;
        String permission = "N";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
            }
        }
        int check_user = assessment.checkUserSession(request, 13, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = assessment.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Assessment Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            frm.setDoAdd("no");
            print(this, " doAdd block :: ");
            request.removeAttribute("PIDS");
            if (request.getSession().getAttribute("QIDLIST") != null) {
                request.getSession().removeAttribute("QIDLIST");
            }
            frm.setStatus(1);
            frm.setAssessmentId(-1);
            frm.setAssessmentcode((assessment.changeNum(assessment.getMaxId(), 3)));
            ArrayList aparameterlist = assessmentparameter.getAssessmentParameterArraylist();
            request.setAttribute("APARAMETERLIST", aparameterlist);
            Collection checkedassessmentparameters = assessmentparameter.getAssessmentParametersChecked("");
            frm.setCheckedassessmentparameters(checkedassessmentparameters);
            Collection assessmentanswertypes = assessmentanswertype.getAssessmentAnswerTypes();
            frm.setAssessmentanswers(assessmentanswertypes);
            saveToken(request);
            return mapping.findForward("add_assessment");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            frm.setDoModify("no");
            int assessmentId = frm.getAssessmentId();
            frm.setAssessmentId(assessmentId);
            SortedSet<Integer> ss = assessment.getAssessmentDetailId(assessmentId);
            request.getSession().setAttribute("QIDLIST", ss);
            AssessmentInfo info = assessment.getAssessmentDetailById(assessmentId);
            if (info != null)
            {
                frm.setName(info.getName());
                frm.setMode(info.getMode());
                request.setAttribute("PIDS", (info.getParameterids() != null ? info.getParameterids() : ""));
                frm.setAssessmentcode((assessment.changeNum(assessmentId, 3)));
                frm.setStatus(info.getStatus());
                ArrayList aparameterlist = assessmentparameter.getAssessmentParameterArraylist();
                request.setAttribute("APARAMETERLIST", aparameterlist);
                Collection checkedassessmentparameters = assessmentparameter.getAssessmentParametersChecked(info.getParameterids());
                frm.setCheckedassessmentparameters(checkedassessmentparameters);
                Collection assessmentanswertypes = assessmentanswertype.getAssessmentAnswerTypes();
                frm.setAssessmentanswers(assessmentanswertypes);
            }
            saveToken(request);
            return mapping.findForward("add_assessment");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            frm.setDoView("no");
            int assessmentId = frm.getAssessmentId();
            frm.setAssessmentId(assessmentId);
            AssessmentInfo info = assessment.getAssessmentDetailByIdforDetail(assessmentId);
            Collection selectedassessmentparameters = assessmentparameter.getAssessmentParametersCheckedview(info.getParameterids());
            request.setAttribute("APLIST", selectedassessmentparameters);
            Collection checkedassessmentparameters = assessmentparameter.getAssessmentParametersChecked(info.getParameterids());

            request.setAttribute("ASSESSMENT_DETAIL", info);
            frm.setCheckedassessmentparameters(checkedassessmentparameters);

            return mapping.findForward("view_assessment");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            frm.setDoSave("no");
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int assessmentId = frm.getAssessmentId();
                String name = vobj.replacename(frm.getName());
                String mode = vobj.replacedesc(frm.getMode());
                String assessmentparameteyar[] = frm.getAssparaameter();
                String assparameter = makeCommaDelimString(assessmentparameteyar);
                SortedSet<Integer> ss = new TreeSet<Integer>();
                if (request.getSession().getAttribute("QIDLIST") != null) {
                    ss = (SortedSet<Integer>) request.getSession().getAttribute("QIDLIST");
                }
                ArrayList list = new ArrayList<Integer>(ss);
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = assessment.getLocalIp();
                int ck = assessment.checkDuplicacy(assessmentId, name);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Assessment already exists");
                    return mapping.findForward("add_assessment");
                }
                AssessmentInfo info = new AssessmentInfo(assessmentId, name, status, uId, mode, assparameter, list);

                if (assessmentId <= 0) {
                    int cc = assessment.createAssessment(info);
                    if (cc > 0) {
                        assessment.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 13, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");
                        if (request.getSession().getAttribute("QIDLIST") != null) {
                            request.getSession().removeAttribute("QIDLIST");
                        }
                    }
                    ArrayList assessmentList = assessment.getAssessmentByName(search, assessmentparameterIndex, questiontypeIndex, 0, count);
                    int cnt = 0;
                    if (assessmentList.size() > 0) {
                        AssessmentInfo cinfo = (AssessmentInfo) assessmentList.get(assessmentList.size() - 1);
                        cnt = cinfo.getAssessmentId();
                        assessmentList.remove(assessmentList.size() - 1);
                    }
                    request.getSession().setAttribute("ASSESSMENT_LIST", assessmentList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    assessment.updateAssessment(info);
                    if (request.getSession().getAttribute("QIDLIST") != null) {
                        request.getSession().removeAttribute("QIDLIST");
                    }
                    assessment.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 13, assessmentId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList assessmentList = assessment.getAssessmentByName(search, assessmentparameterIndex, questiontypeIndex, next, count);
                    int cnt = 0;
                    if (assessmentList.size() > 0) {
                        AssessmentInfo cinfo = (AssessmentInfo) assessmentList.get(assessmentList.size() - 1);
                        cnt = cinfo.getAssessmentId();
                        assessmentList.remove(assessmentList.size() - 1);
                    }
                    request.getSession().setAttribute("ASSESSMENT_LIST", assessmentList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", next + "");
                    request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");

                    return mapping.findForward("display");
                }
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
            ArrayList assessmentList = assessment.getAssessmentByName(search, assessmentparameterIndex, questiontypeIndex, next, count);
            int cnt = 0;
            if (assessmentList.size() > 0) {
                AssessmentInfo cinfo = (AssessmentInfo) assessmentList.get(assessmentList.size() - 1);
                cnt = cinfo.getAssessmentId();
                assessmentList.remove(assessmentList.size() - 1);
            }
            request.getSession().setAttribute("ASSESSMENT_LIST", assessmentList);
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
            ArrayList assessmentList = assessment.getAssessmentByName(search, assessmentparameterIndex, questiontypeIndex, 0, count);
            int cnt = 0;
            if (assessmentList.size() > 0) {
                AssessmentInfo cinfo = (AssessmentInfo) assessmentList.get(assessmentList.size() - 1);
                cnt = cinfo.getAssessmentId();
                assessmentList.remove(assessmentList.size() - 1);
            }
            request.getSession().setAttribute("ASSESSMENT_LIST", assessmentList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
