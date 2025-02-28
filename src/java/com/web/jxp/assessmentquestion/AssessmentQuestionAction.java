package com.web.jxp.assessmentquestion;

import com.web.jxp.assessmentanswertype.AssessmentAnswerType;
import com.web.jxp.assessmentparameter.AssessmentParameter;
import com.web.jxp.base.Validate;
import com.web.jxp.common.Common;
import static com.web.jxp.common.Common.print;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.web.jxp.user.UserInfo;
import java.util.Collection;
import java.util.Stack;

public class AssessmentQuestionAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AssessmentQuestionForm frm = (AssessmentQuestionForm) form;
        AssessmentQuestion assessmentquestion = new AssessmentQuestion();
        AssessmentAnswerType assessmentAnswerType = new AssessmentAnswerType();
        AssessmentParameter assessmentParameter = new AssessmentParameter();
        Validate vobj = new Validate();
        Collection assessmentAnswerTypes = assessmentAnswerType.getAssessmentAnswerTypes();
        frm.setAssessmentAnswerTypes(assessmentAnswerTypes);
        int assessmentAnswerTypeIdIndex = frm.getAssessmentAnswerTypeIdIndex();
        frm.setAssessmentAnswerTypeIdIndex(assessmentAnswerTypeIdIndex);

        Collection assessmentParameters = assessmentParameter.getAssessmentParameters();
        frm.setAssessmentParameters(assessmentParameters);
        int assessmentParameterIdIndex = frm.getAssessmentParameterIdIndex();
        frm.setAssessmentParameterIdIndex(assessmentParameterIdIndex);

        int count = assessmentquestion.getCount();
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
        int check_user = assessmentquestion.checkUserSession(request, 15, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = assessmentquestion.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Assessment Question Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }

        String methodpostmessage = assessmentquestion.getMainPath("METHOD_POST_NOT_FOUND");

        if ((frm.getDoAdd() != null) && (frm.getDoAdd().equals("yes"))) {
            Common.print(this, "doAdd block.");
            if (request.getMethod().equalsIgnoreCase("POST")) {
                saveToken(request);
                frm.setAssessmentAnswerTypes(assessmentAnswerTypes);
                frm.setAssessmentAnswerTypeId(-1);

                frm.setAssessmentParameters(assessmentParameters);
                frm.setAssessmentParameterId(-1);

                frm.setStatus(1);
                frm.setAssessmentquestionId(-1);
                saveToken(request);
                return mapping.findForward("add_assessmentquestion");
            }
            request.setAttribute("COUNTRYMESSAGE", methodpostmessage);
        } 
        else if ((frm.getDoModify() != null) && (frm.getDoModify().equals("yes"))) {
            int assessmentquestionId = frm.getAssessmentquestionId();
            frm.setAssessmentquestionId(assessmentquestionId);
            AssessmentQuestionInfo info = assessmentquestion.getAssessmentQuestionDetailById(assessmentquestionId);
            if (info != null) {
                if (info.getName() != null) {
                    frm.setName(info.getName());
                }
                frm.setAssessmentAnswerTypeId(info.getAssessmentAnswerTypeId());
                frm.setAssessmentParameterId(info.getAssessmentParameterId());               
                if (info.getLink() != null) {
                    frm.setLink(info.getLink());
                }
                 frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_assessmentquestion");
        } 
        else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);

                int assessmentquestionId = frm.getAssessmentquestionId();
                String name = vobj.replacename(frm.getName());
                int assessmentAnswerTypeId = frm.getAssessmentAnswerTypeId();
                int assessmentParameterId = frm.getAssessmentParameterId();
                String link = vobj.replacedesc(frm.getLink());
                int status = 1;

                String ipAddrStr = request.getRemoteAddr();
                String iplocal = assessmentquestion.getLocalIp();
                int ck = assessmentquestion.checkDuplicacy(assessmentquestionId, assessmentParameterId, name);
                if (ck == 1) {
                    frm.setAssessmentAnswerTypes(assessmentAnswerTypes);
                    frm.setAssessmentParameterId(assessmentParameterId);
                    frm.setName(name);
                    frm.setLink(link);
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Assessment Question already exists");
                    return mapping.findForward("add_assessmentquestion");
                }
                
                AssessmentQuestionInfo info = new AssessmentQuestionInfo(assessmentquestionId, name, assessmentAnswerTypeId, assessmentParameterId, link, status, uId);
                if (assessmentquestionId <= 0) {
                    int cc = assessmentquestion.createAssessmentQuestion(info);
                    if (cc > 0) {
                        assessmentquestion.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 42, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    ArrayList assessmentquestionList = assessmentquestion.getAssessmentQuestionByName(search, 0, count);
                    int cnt = 0;
                    if (assessmentquestionList.size() > 0) {
                        AssessmentQuestionInfo cinfo = (AssessmentQuestionInfo) assessmentquestionList.get(assessmentquestionList.size() - 1);
                        cnt = cinfo.getAssessmentquestionId();
                        assessmentquestionList.remove(assessmentquestionList.size() - 1);
                    }
                    request.getSession().setAttribute("ASSESSMENTQUESTION_LIST", assessmentquestionList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    assessmentquestion.updateAssessmentQuestion(info);
                    assessmentquestion.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 42, assessmentquestionId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList assessmentquestionList = assessmentquestion.getAssessmentQuestionByName(search, next, count);
                    int cnt = 0;
                    if (assessmentquestionList.size() > 0) {
                        AssessmentQuestionInfo cinfo = (AssessmentQuestionInfo) assessmentquestionList.get(assessmentquestionList.size() - 1);
                        cnt = cinfo.getAssessmentquestionId();
                        assessmentquestionList.remove(assessmentquestionList.size() - 1);
                    }
                    request.getSession().setAttribute("ASSESSMENTQUESTION_LIST", assessmentquestionList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", next + "");
                    request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");

                    return mapping.findForward("display");
                }
            }
        } 
        else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            int assessmentquestionId = frm.getAssessmentquestionId();
            frm.setAssessmentquestionId(assessmentquestionId);
            AssessmentQuestionInfo info = assessmentquestion.getAssessmentQuestionDetailByIdforDetail(assessmentquestionId);
            request.setAttribute("ASSESSMENTQUESTION_DETAIL", info);
            return mapping.findForward("view_assessmentquestion");
        } 
        else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            print(this, "doCancel block");
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList assessmentquestionList = assessmentquestion.getAssessmentQuestionByName(search, next, count);
            int cnt = 0;
            if (assessmentquestionList.size() > 0) {
                AssessmentQuestionInfo cinfo = (AssessmentQuestionInfo) assessmentquestionList.get(assessmentquestionList.size() - 1);
                cnt = cinfo.getAssessmentquestionId();
                assessmentquestionList.remove(assessmentquestionList.size() - 1);
            }
            request.getSession().setAttribute("ASSESSMENTQUESTION_LIST", assessmentquestionList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display");
        } 
        else {
            print(this, "else block.");
            
            if (frm.getCtp() == 0) {
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }
            
            ArrayList assessmentquestionList = assessmentquestion.getAssessmentQuestionByName(search, 0, count);
            int cnt = 0;
            if (assessmentquestionList.size() > 0) {
                AssessmentQuestionInfo cinfo = (AssessmentQuestionInfo) assessmentquestionList.get(assessmentquestionList.size() - 1);
                cnt = cinfo.getAssessmentquestionId();
                assessmentquestionList.remove(assessmentquestionList.size() - 1);
            }
            request.getSession().setAttribute("ASSESSMENTQUESTION_LIST", assessmentquestionList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
