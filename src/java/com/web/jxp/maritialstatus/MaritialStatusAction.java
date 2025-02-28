package com.web.jxp.maritialstatus;

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

public class MaritialStatusAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MaritialStatusForm frm = (MaritialStatusForm) form;
        MaritialStatus maritialstatus = new MaritialStatus();
        Validate vobj = new Validate();
        int count = maritialstatus.getCount();
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
        int check_user = maritialstatus.checkUserSession(request, 19, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = maritialstatus.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Marritial Status Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setMaritialStatusId(-1);
            saveToken(request);
            return mapping.findForward("add_maritialstatus");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int maritialStatusId = frm.getMaritialStatusId();
            frm.setMaritialStatusId(maritialStatusId);
            MaritialStatusInfo info = maritialstatus.getMaritialStatusDetailById(maritialStatusId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_maritialstatus");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int maritialStatusId = frm.getMaritialStatusId();
            frm.setMaritialStatusId(maritialStatusId);
            MaritialStatusInfo info = maritialstatus.getMaritialStatusDetailByIdforDetail(maritialStatusId);
            request.setAttribute("MARITIALSTATUS_DETAIL", info);
            return mapping.findForward("view_maritialstatus");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int maritialStatusId = frm.getMaritialStatusId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = maritialstatus.getLocalIp();
                int ck = maritialstatus.checkDuplicacy(maritialStatusId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Marital status already exists");
                    return mapping.findForward("add_maritialstatus");
                }
                MaritialStatusInfo info = new MaritialStatusInfo(maritialStatusId, name, status, uId);
                if(maritialStatusId <= 0)
                {
                    int cc = maritialstatus.createMaritialStatus(info);
                    if(cc > 0)
                    {
                       maritialstatus.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 19, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");                       
                    }
                    ArrayList maritialstatusList = maritialstatus.getMaritialStatusByName(search, 0, count);
                    int cnt = 0;
                    if(maritialstatusList.size() > 0)
                    {
                        MaritialStatusInfo cinfo = (MaritialStatusInfo) maritialstatusList.get(maritialstatusList.size() - 1);
                        cnt = cinfo.getMaritialStatusId();
                        maritialstatusList.remove(maritialstatusList.size() - 1);
                    }
                    request.getSession().setAttribute("MARITIALSTATUS_LIST", maritialstatusList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    maritialstatus.updateMaritialStatus(info);
                    maritialstatus.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 19, maritialStatusId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList maritialstatusList = maritialstatus.getMaritialStatusByName(search, next, count);
                    int cnt = 0;
                    if(maritialstatusList.size() > 0)
                    {
                        MaritialStatusInfo cinfo = (MaritialStatusInfo) maritialstatusList.get(maritialstatusList.size() - 1);
                        cnt = cinfo.getMaritialStatusId();
                        maritialstatusList.remove(maritialstatusList.size() - 1);
                    }
                    request.getSession().setAttribute("MARITIALSTATUS_LIST", maritialstatusList);
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
            ArrayList maritialstatusList = maritialstatus.getMaritialStatusByName(search, next, count);
            int cnt = 0;
            if(maritialstatusList.size() > 0)
            {
                MaritialStatusInfo cinfo = (MaritialStatusInfo) maritialstatusList.get(maritialstatusList.size() - 1);
                cnt = cinfo.getMaritialStatusId();
                maritialstatusList.remove(maritialstatusList.size() - 1);
            }
            request.getSession().setAttribute("MARITIALSTATUS_LIST", maritialstatusList);
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
            ArrayList maritialstatusList = maritialstatus.getMaritialStatusByName(search, 0, count);
            int cnt = 0;
            if(maritialstatusList.size() > 0)
            {
                MaritialStatusInfo cinfo = (MaritialStatusInfo) maritialstatusList.get(maritialstatusList.size() - 1);
                cnt = cinfo.getMaritialStatusId();
                maritialstatusList.remove(maritialstatusList.size() - 1);
            }
            request.getSession().setAttribute("MARITIALSTATUS_LIST", maritialstatusList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}