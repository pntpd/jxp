package com.web.jxp.experiencedept;

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

public class ExperienceDeptAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExperienceDeptForm frm = (ExperienceDeptForm) form;
        ExperienceDept experiencedept = new ExperienceDept();
        Validate vobj = new Validate();
        int count = experiencedept.getCount();
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
        int check_user = experiencedept.checkUserSession(request, 29, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = experiencedept.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "ExperienceDept Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setExperiencedeptId(-1);
            saveToken(request);
            return mapping.findForward("add_experiencedept");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int experiencedeptId = frm.getExperiencedeptId();
            frm.setExperiencedeptId(experiencedeptId);
            ExperienceDeptInfo info = experiencedept.getExperienceDeptDetailById(experiencedeptId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_experiencedept");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int experiencedeptId = frm.getExperiencedeptId();
            frm.setExperiencedeptId(experiencedeptId);
            ExperienceDeptInfo info = experiencedept.getExperienceDeptDetailByIdforDetail(experiencedeptId);
            request.setAttribute("EXPERIENCEDEPT_DETAIL", info);
            return mapping.findForward("view_experiencedept");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int experiencedeptId = frm.getExperiencedeptId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = experiencedept.getLocalIp();
                int ck = experiencedept.checkDuplicacy(experiencedeptId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Department/Function already exists");
                    return mapping.findForward("add_experiencedept");
                }
                ExperienceDeptInfo info = new ExperienceDeptInfo(experiencedeptId, name, status, uId);
                if(experiencedeptId <= 0)
                {
                    int cc = experiencedept.createExperienceDept(info);
                    if(cc > 0)
                    {
                       experiencedept.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 29, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList experiencedeptList = experiencedept.getExperienceDeptByName(search, 0, count);
                    int cnt = 0;
                    if(experiencedeptList.size() > 0)
                    {
                        ExperienceDeptInfo cinfo = (ExperienceDeptInfo) experiencedeptList.get(experiencedeptList.size() - 1);
                        cnt = cinfo.getExperiencedeptId();
                        experiencedeptList.remove(experiencedeptList.size() - 1);
                    }
                    request.getSession().setAttribute("EXPERIENCEDEPT_LIST", experiencedeptList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    experiencedept.updateExperienceDept(info);
                    experiencedept.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 29, experiencedeptId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList experiencedeptList = experiencedept.getExperienceDeptByName(search, next, count);
                    int cnt = 0;
                    if(experiencedeptList.size() > 0)
                    {
                        ExperienceDeptInfo cinfo = (ExperienceDeptInfo) experiencedeptList.get(experiencedeptList.size() - 1);
                        cnt = cinfo.getExperiencedeptId();
                        experiencedeptList.remove(experiencedeptList.size() - 1);
                    }
                    request.getSession().setAttribute("EXPERIENCEDEPT_LIST", experiencedeptList);
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
            ArrayList experiencedeptList = experiencedept.getExperienceDeptByName(search, next, count);
            int cnt = 0;
            if(experiencedeptList.size() > 0)
            {
                ExperienceDeptInfo cinfo = (ExperienceDeptInfo) experiencedeptList.get(experiencedeptList.size() - 1);
                cnt = cinfo.getExperiencedeptId();
                experiencedeptList.remove(experiencedeptList.size() - 1);
            }
            request.getSession().setAttribute("EXPERIENCEDEPT_LIST", experiencedeptList);
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
            
            ArrayList experiencedeptList = experiencedept.getExperienceDeptByName(search, 0, count);
            int cnt = 0;
            if(experiencedeptList.size() > 0)
            {
                ExperienceDeptInfo cinfo = (ExperienceDeptInfo) experiencedeptList.get(experiencedeptList.size() - 1);
                cnt = cinfo.getExperiencedeptId();
                experiencedeptList.remove(experiencedeptList.size() - 1);
            }
            request.getSession().setAttribute("EXPERIENCEDEPT_LIST", experiencedeptList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}