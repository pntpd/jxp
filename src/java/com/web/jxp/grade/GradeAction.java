package com.web.jxp.grade;

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

public class GradeAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GradeForm frm = (GradeForm) form;
        Grade grade = new Grade();
        Validate vobj = new Validate();
        int count = grade.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0;
        String permission = "N";
        if (request.getSession().getAttribute("LOGININFO") != null) 
        {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) 
            {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
            }
        }
        int check_user = grade.checkUserSession(request, 20, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = grade.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Grade Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setGradeId(-1);
            saveToken(request);
            return mapping.findForward("add_grade");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int gradeId = frm.getGradeId();
            frm.setGradeId(gradeId);
            GradeInfo info = grade.getGradeDetailById(gradeId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_grade");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int gradeId = frm.getGradeId();
            frm.setGradeId(gradeId);
            GradeInfo info = grade.getGradeDetailByIdforDetail(gradeId);
            request.setAttribute("GRADE_DETAIL", info);
            return mapping.findForward("view_grade");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int gradeId = frm.getGradeId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = grade.getLocalIp();
                int ck = grade.checkDuplicacy(gradeId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Grade already exists");
                    return mapping.findForward("add_grade");
                }
                GradeInfo info = new GradeInfo(gradeId, name, status, uId);
                if(gradeId <= 0)
                {
                    int cc = grade.createGrade(info);
                    if(cc > 0)
                    {
                       grade.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 20, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList gradeList = grade.getGradeByName(search, 0, count);
                    int cnt = 0;
                    if(gradeList.size() > 0)
                    {
                        GradeInfo cinfo = (GradeInfo) gradeList.get(gradeList.size() - 1);
                        cnt = cinfo.getGradeId();
                        gradeList.remove(gradeList.size() - 1);
                    }
                    request.getSession().setAttribute("GRADE_LIST", gradeList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    grade.updateGrade(info);
                    grade.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 20, gradeId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList gradeList = grade.getGradeByName(search, next, count);
                    int cnt = 0;
                    if(gradeList.size() > 0)
                    {
                        GradeInfo cinfo = (GradeInfo) gradeList.get(gradeList.size() - 1);
                        cnt = cinfo.getGradeId();
                        gradeList.remove(gradeList.size() - 1);
                    }
                    request.getSession().setAttribute("GRADE_LIST", gradeList);
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
            ArrayList gradeList = grade.getGradeByName(search, next, count);
            int cnt = 0;
            if(gradeList.size() > 0)
            {
                GradeInfo cinfo = (GradeInfo) gradeList.get(gradeList.size() - 1);
                cnt = cinfo.getGradeId();
                gradeList.remove(gradeList.size() - 1);
            }
            request.getSession().setAttribute("GRADE_LIST", gradeList);
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
            
            ArrayList gradeList = grade.getGradeByName(search, 0, count);
            int cnt = 0;
            if(gradeList.size() > 0)
            {
                GradeInfo cinfo = (GradeInfo) gradeList.get(gradeList.size() - 1);
                cnt = cinfo.getGradeId();
                gradeList.remove(gradeList.size() - 1);
            }
            request.getSession().setAttribute("GRADE_LIST", gradeList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}