package com.web.jxp.coursename;

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

public class CoursenameAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CoursenameForm frm = (CoursenameForm) form;
        Coursename coursename = new Coursename();
        Validate vobj = new Validate();
        int count = coursename.getCount();
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
        int check_user = coursename.checkUserSession(request, 65, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = coursename.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Coursename Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setCoursenameId(-1);
            saveToken(request);
            return mapping.findForward("add_coursename");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int coursenameId = frm.getCoursenameId();
            frm.setCoursenameId(coursenameId);
            CoursenameInfo info = coursename.getCoursenameDetailById(coursenameId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_coursename");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int coursenameId = frm.getCoursenameId();
            frm.setCoursenameId(coursenameId);
            CoursenameInfo info = coursename.getCoursenameDetailByIdforDetail(coursenameId);
            request.setAttribute("COURSENAME_DETAIL", info);
            return mapping.findForward("view_coursename");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int coursenameId = frm.getCoursenameId();
                String name = vobj.replacedesc(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = coursename.getLocalIp();
                int ck = coursename.checkDuplicacy(coursenameId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Course Type already exists");
                    return mapping.findForward("add_coursename");
                }
                CoursenameInfo info = new CoursenameInfo(coursenameId, name, status, uId);
                if(coursenameId <= 0)
                {
                    int cc = coursename.createCoursename(info);
                    if(cc > 0)
                    {
                       coursename.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 65, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList coursenameList = coursename.getCoursenameByName(search, 0, count);
                    int cnt = 0;
                    if(coursenameList.size() > 0)
                    {
                        CoursenameInfo cinfo = (CoursenameInfo) coursenameList.get(coursenameList.size() - 1);
                        cnt = cinfo.getCoursenameId();
                        coursenameList.remove(coursenameList.size() - 1);
                    }
                    request.getSession().setAttribute("COURSENAME_LIST", coursenameList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    coursename.updateCoursename(info);
                    coursename.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 65, coursenameId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList coursenameList = coursename.getCoursenameByName(search, next, count);
                    int cnt = 0;
                    if(coursenameList.size() > 0)
                    {
                        CoursenameInfo cinfo = (CoursenameInfo) coursenameList.get(coursenameList.size() - 1);
                        cnt = cinfo.getCoursenameId();
                        coursenameList.remove(coursenameList.size() - 1);
                    }
                    request.getSession().setAttribute("COURSENAME_LIST", coursenameList);
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
            ArrayList coursenameList = coursename.getCoursenameByName(search, next, count);
            int cnt = 0;
            if(coursenameList.size() > 0)
            {
                CoursenameInfo cinfo = (CoursenameInfo) coursenameList.get(coursenameList.size() - 1);
                cnt = cinfo.getCoursenameId();
                coursenameList.remove(coursenameList.size() - 1);
            }
            request.getSession().setAttribute("COURSENAME_LIST", coursenameList);
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
            
            ArrayList coursenameList = coursename.getCoursenameByName(search, 0, count);
            int cnt = 0;
            if(coursenameList.size() > 0)
            {
                CoursenameInfo cinfo = (CoursenameInfo) coursenameList.get(coursenameList.size() - 1);
                cnt = cinfo.getCoursenameId();
                coursenameList.remove(coursenameList.size() - 1);
            }
            request.getSession().setAttribute("COURSENAME_LIST", coursenameList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}