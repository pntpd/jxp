package com.web.jxp.degree;

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

public class DegreeAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DegreeForm frm = (DegreeForm) form;
        Degree degree = new Degree();
        Validate vobj = new Validate();
        int count = degree.getCount();
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
        int check_user = degree.checkUserSession(request, 34, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = degree.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Degree Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setDegreeId(-1);
            saveToken(request);
            return mapping.findForward("add_degree");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int degreeId = frm.getDegreeId();
            frm.setDegreeId(degreeId);
            DegreeInfo info = degree.getDegreeDetailById(degreeId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_degree");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int degreeId = frm.getDegreeId();
            frm.setDegreeId(degreeId);
            DegreeInfo info = degree.getDegreeDetailByIdforDetail(degreeId);
            request.setAttribute("DEGREE_DETAIL", info);
            return mapping.findForward("view_degree");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int degreeId = frm.getDegreeId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = degree.getLocalIp();
                int ck = degree.checkDuplicacy(degreeId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Degree already exists");
                    return mapping.findForward("add_degree");
                }
                DegreeInfo info = new DegreeInfo(degreeId, name, status, uId);
                if(degreeId <= 0)
                {
                    int cc = degree.createDegree(info);
                    if(cc > 0)
                    {
                       degree.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 34, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList degreeList = degree.getDegreeByName(search, 0, count);
                    int cnt = 0;
                    if(degreeList.size() > 0)
                    {
                        DegreeInfo cinfo = (DegreeInfo) degreeList.get(degreeList.size() - 1);
                        cnt = cinfo.getDegreeId();
                        degreeList.remove(degreeList.size() - 1);
                    }
                    request.getSession().setAttribute("DEGREE_LIST", degreeList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    degree.updateDegree(info);
                    degree.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 34, degreeId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList degreeList = degree.getDegreeByName(search, next, count);
                    int cnt = 0;
                    if(degreeList.size() > 0)
                    {
                        DegreeInfo cinfo = (DegreeInfo) degreeList.get(degreeList.size() - 1);
                        cnt = cinfo.getDegreeId();
                        degreeList.remove(degreeList.size() - 1);
                    }
                    request.getSession().setAttribute("DEGREE_LIST", degreeList);
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
            ArrayList degreeList = degree.getDegreeByName(search, next, count);
            int cnt = 0;
            if(degreeList.size() > 0)
            {
                DegreeInfo cinfo = (DegreeInfo) degreeList.get(degreeList.size() - 1);
                cnt = cinfo.getDegreeId();
                degreeList.remove(degreeList.size() - 1);
            }
            request.getSession().setAttribute("DEGREE_LIST", degreeList);
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
            
            ArrayList degreeList = degree.getDegreeByName(search, 0, count);
            int cnt = 0;
            if(degreeList.size() > 0)
            {
                DegreeInfo cinfo = (DegreeInfo) degreeList.get(degreeList.size() - 1);
                cnt = cinfo.getDegreeId();
                degreeList.remove(degreeList.size() - 1);
            }
            request.getSession().setAttribute("DEGREE_LIST", degreeList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}