package com.web.jxp.coursetype;

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

public class CourseTypeAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CourseTypeForm frm = (CourseTypeForm) form;
        CourseType coursetype = new CourseType();
        Validate vobj = new Validate();
        int count = coursetype.getCount();
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
        int check_user = coursetype.checkUserSession(request, 35, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = coursetype.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "CourseType Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setCoursetypeId(-1);
            saveToken(request);
            return mapping.findForward("add_coursetype");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int coursetypeId = frm.getCoursetypeId();
            frm.setCoursetypeId(coursetypeId);
            CourseTypeInfo info = coursetype.getCourseTypeDetailById(coursetypeId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_coursetype");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int coursetypeId = frm.getCoursetypeId();
            frm.setCoursetypeId(coursetypeId);
            CourseTypeInfo info = coursetype.getCourseTypeDetailByIdforDetail(coursetypeId);
            request.setAttribute("COURSETYPE_DETAIL", info);
            return mapping.findForward("view_coursetype");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int coursetypeId = frm.getCoursetypeId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = coursetype.getLocalIp();
                int ck = coursetype.checkDuplicacy(coursetypeId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Course Type already exists");
                    return mapping.findForward("add_coursetype");
                }
                CourseTypeInfo info = new CourseTypeInfo(coursetypeId, name, status, uId);
                if(coursetypeId <= 0)
                {
                    int cc = coursetype.createCourseType(info);
                    if(cc > 0)
                    {
                       coursetype.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 35, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList coursetypeList = coursetype.getCourseTypeByName(search, 0, count);
                    int cnt = 0;
                    if(coursetypeList.size() > 0)
                    {
                        CourseTypeInfo cinfo = (CourseTypeInfo) coursetypeList.get(coursetypeList.size() - 1);
                        cnt = cinfo.getCoursetypeId();
                        coursetypeList.remove(coursetypeList.size() - 1);
                    }
                    request.getSession().setAttribute("COURSETYPE_LIST", coursetypeList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    coursetype.updateCourseType(info);
                    coursetype.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 35, coursetypeId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList coursetypeList = coursetype.getCourseTypeByName(search, next, count);
                    int cnt = 0;
                    if(coursetypeList.size() > 0)
                    {
                        CourseTypeInfo cinfo = (CourseTypeInfo) coursetypeList.get(coursetypeList.size() - 1);
                        cnt = cinfo.getCoursetypeId();
                        coursetypeList.remove(coursetypeList.size() - 1);
                    }
                    request.getSession().setAttribute("COURSETYPE_LIST", coursetypeList);
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
            ArrayList coursetypeList = coursetype.getCourseTypeByName(search, next, count);
            int cnt = 0;
            if(coursetypeList.size() > 0)
            {
                CourseTypeInfo cinfo = (CourseTypeInfo) coursetypeList.get(coursetypeList.size() - 1);
                cnt = cinfo.getCoursetypeId();
                coursetypeList.remove(coursetypeList.size() - 1);
            }
            request.getSession().setAttribute("COURSETYPE_LIST", coursetypeList);
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
            
            ArrayList coursetypeList = coursetype.getCourseTypeByName(search, 0, count);
            int cnt = 0;
            if(coursetypeList.size() > 0)
            {
                CourseTypeInfo cinfo = (CourseTypeInfo) coursetypeList.get(coursetypeList.size() - 1);
                cnt = cinfo.getCoursetypeId();
                coursetypeList.remove(coursetypeList.size() - 1);
            }
            request.getSession().setAttribute("COURSETYPE_LIST", coursetypeList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}