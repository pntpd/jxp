package com.web.jxp.competencyassessments;

import com.web.jxp.base.Validate;
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

public class CompetencyassessmentsAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CompetencyassessmentsForm frm = (CompetencyassessmentsForm) form;
        Competencyassessments compassess = new Competencyassessments();
        Validate vobj = new Validate();        
        Pdept pdept = new Pdept();
        int count = compassess.getCount();
        
        Collection assettypes = compassess.getAssettypes(1);
        frm.setAssettypes(assettypes);
        int assettypeIdIndex = frm.getAssettypeIdIndex();
        frm.setAssettypeIdIndex(assettypeIdIndex);
        
        Collection depatmentss = compassess.getCompetencyDept(assettypeIdIndex, 1);
        frm.setDepartments(depatmentss);
        int departmentIdIndex = frm.getDepartmentIdIndex();
        frm.setDepartmentIdIndex(departmentIdIndex);
        
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0;
        String permission = "N";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) 
            {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
            }
        }
        int check_user = compassess.checkUserSession(request, 84, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = compassess.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Competency Assessments Type Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setCompetencyassessmentsId(-1);
            assettypes = compassess.getAssettypes(2);
            frm.setAssettypes(assettypes);
            Collection depatments = compassess.getCompetencyDept(frm.getAssettypeId(), 2);
            frm.setDepartments(depatments);
            saveToken(request);
            return mapping.findForward("add_competencyassessments");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int competencyassessmentsId = frm.getCompetencyassessmentsId();
            frm.setCompetencyassessmentsId(competencyassessmentsId);
            
            CompetencyassessmentsInfo info = compassess.getCompetencyassessmentsDetailById(competencyassessmentsId);
            if(info != null)
            {
                assettypes = compassess.getAssettypes(2);
                frm.setAssettypes(assettypes);
                depatmentss = pdept.getPdepts(info.getAssettypeId());
                frm.setDepartments(depatmentss);
                if(info.getName() != null)
                    frm.setName(info.getName());
                if(info.getDescription() != null)
                    frm.setDescription(info.getDescription());
                frm.setAssettypeId(info.getAssettypeId());
                frm.setDepartmentId(info.getDepartmentId());
                frm.setStatus(info.getStatus());
                frm.setSchedule(info.getSchedule());
            }
            saveToken(request);
            return mapping.findForward("add_competencyassessments");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int competencyassessmentsId = frm.getCompetencyassessmentsId();
            frm.setCompetencyassessmentsId(competencyassessmentsId);
            CompetencyassessmentsInfo info = compassess.getCompetencyassessmentsDetailByIdforDetail(competencyassessmentsId);
            request.setAttribute("COMPETENCYASSESSMENTS_DETAIL", info);
            return mapping.findForward("view_competencyassessments");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int competencyassessmentsId = frm.getCompetencyassessmentsId();
                String name = vobj.replacedesc(frm.getName());
                String description = vobj.replacedesc(frm.getDescription());
                int status = 1;
                int assettypeId = frm.getAssettypeId();
                int departmentId = frm.getDepartmentId();
                int schedule = frm.getSchedule();
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = compassess.getLocalIp();
                int ck = compassess.checkDuplicacy(competencyassessmentsId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    depatmentss = pdept.getPdepts(assettypeId);
                    frm.setDepartments(depatmentss);
                    request.setAttribute("MESSAGE", "Data already exists");
                    return mapping.findForward("add_competencyassessments");
                }
                CompetencyassessmentsInfo info = new CompetencyassessmentsInfo(competencyassessmentsId, name, assettypeId, description, status, uId, departmentId, schedule);
                if(competencyassessmentsId <= 0)
                {
                    int cc = compassess.createCompetencyassessments(info);
                    if(cc > 0)
                    {
                       compassess.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 84, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList competencyassessmentsList = compassess.getCompetencyassessmentsByName(search, departmentIdIndex, assettypeIdIndex, 0, count);
                    int cnt = 0;
                    if(competencyassessmentsList.size() > 0)
                    {
                        CompetencyassessmentsInfo cinfo = (CompetencyassessmentsInfo) competencyassessmentsList.get(competencyassessmentsList.size() - 1);
                        cnt = cinfo.getCompetencyassessmentsId();
                        competencyassessmentsList.remove(competencyassessmentsList.size() - 1);
                    }
                    request.getSession().setAttribute("COMPETENCYASSESSMENTS_LIST", competencyassessmentsList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    compassess.updateCompetencyassessments(info);
                    compassess.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 84, competencyassessmentsId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList competencyassessmentsList = compassess.getCompetencyassessmentsByName(search, departmentIdIndex, assettypeIdIndex, next, count);
                    int cnt = 0;
                    if(competencyassessmentsList.size() > 0)
                    {
                        CompetencyassessmentsInfo cinfo = (CompetencyassessmentsInfo) competencyassessmentsList.get(competencyassessmentsList.size() - 1);
                        cnt = cinfo.getCompetencyassessmentsId();
                        competencyassessmentsList.remove(competencyassessmentsList.size() - 1);
                    }
                    request.getSession().setAttribute("COMPETENCYASSESSMENTS_LIST", competencyassessmentsList);
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
            ArrayList competencyassessmentsList = compassess.getCompetencyassessmentsByName(search, departmentIdIndex, assettypeIdIndex, next, count);
            int cnt = 0;
            if(competencyassessmentsList.size() > 0)
            {
                CompetencyassessmentsInfo cinfo = (CompetencyassessmentsInfo) competencyassessmentsList.get(competencyassessmentsList.size() - 1);
                cnt = cinfo.getCompetencyassessmentsId();
                competencyassessmentsList.remove(competencyassessmentsList.size() - 1);
            }
            request.getSession().setAttribute("COMPETENCYASSESSMENTS_LIST", competencyassessmentsList);
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
            ArrayList competencyassessmentsList = compassess.getCompetencyassessmentsByName(search, departmentIdIndex, assettypeIdIndex, 0, count);
            int cnt = 0;
            if(competencyassessmentsList.size() > 0)
            {
                CompetencyassessmentsInfo cinfo = (CompetencyassessmentsInfo) competencyassessmentsList.get(competencyassessmentsList.size() - 1);
                cnt = cinfo.getCompetencyassessmentsId();
                competencyassessmentsList.remove(competencyassessmentsList.size() - 1);
            }
            request.getSession().setAttribute("COMPETENCYASSESSMENTS_LIST", competencyassessmentsList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}