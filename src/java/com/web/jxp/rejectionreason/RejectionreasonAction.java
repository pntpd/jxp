package com.web.jxp.rejectionreason;

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

public class RejectionreasonAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RejectionreasonForm frm = (RejectionreasonForm) form;
        Rejectionreason rejectionreason = new Rejectionreason();
        Validate vobj = new Validate();
        int count = rejectionreason.getCount();
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
        int check_user = rejectionreason.checkUserSession(request, 52, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = rejectionreason.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Rejection Reason Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setRejectionreasonId(-1);
            saveToken(request);
            return mapping.findForward("add_rejectionreason");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int rejectionreasonId = frm.getRejectionreasonId();
            frm.setRejectionreasonId(rejectionreasonId);
            RejectionreasonInfo info = rejectionreason.getRejectionreasonDetailById(rejectionreasonId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setRejectionType(info.getRejectionType());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_rejectionreason");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int rejectionreasonId = frm.getRejectionreasonId();
            frm.setRejectionreasonId(rejectionreasonId);
            RejectionreasonInfo info = rejectionreason.getRejectionreasonDetailByIdforDetail(rejectionreasonId);
            request.setAttribute("REJECTIONREASON_DETAIL", info);
            return mapping.findForward("view_rejectionreason");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int rejectionreasonId = frm.getRejectionreasonId();
                String name = vobj.replacename(frm.getName());
                int rejectiontype = frm.getRejectionType();
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = rejectionreason.getLocalIp();
                int ck = rejectionreason.checkDuplicacy(rejectionreasonId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Rejectionreason already exists");
                    return mapping.findForward("add_rejectionreason");
                }
                RejectionreasonInfo info = new RejectionreasonInfo(rejectionreasonId, name, status, uId, rejectiontype);
                if(rejectionreasonId <= 0)
                {
                    int cc = rejectionreason.createRejectionreason(info);
                    if(cc > 0)
                    {
                       rejectionreason.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 52, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList rejectionreasonList = rejectionreason.getRejectionreasonByName(search, 0, count);
                    int cnt = 0;
                    if(rejectionreasonList.size() > 0)
                    {
                        RejectionreasonInfo cinfo = (RejectionreasonInfo) rejectionreasonList.get(rejectionreasonList.size() - 1);
                        cnt = cinfo.getRejectionreasonId();
                        rejectionreasonList.remove(rejectionreasonList.size() - 1);
                    }
                    request.getSession().setAttribute("REJECTIONREASON_LIST", rejectionreasonList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    rejectionreason.updateRejectionreason(info);
                    rejectionreason.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 52, rejectionreasonId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList rejectionreasonList = rejectionreason.getRejectionreasonByName(search, next, count);
                    int cnt = 0;
                    if(rejectionreasonList.size() > 0)
                    {
                        RejectionreasonInfo cinfo = (RejectionreasonInfo) rejectionreasonList.get(rejectionreasonList.size() - 1);
                        cnt = cinfo.getRejectionreasonId();
                        rejectionreasonList.remove(rejectionreasonList.size() - 1);
                    }
                    request.getSession().setAttribute("REJECTIONREASON_LIST", rejectionreasonList);
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
            ArrayList rejectionreasonList = rejectionreason.getRejectionreasonByName(search, next, count);
            int cnt = 0;
            if(rejectionreasonList.size() > 0)
            {
                RejectionreasonInfo cinfo = (RejectionreasonInfo) rejectionreasonList.get(rejectionreasonList.size() - 1);
                cnt = cinfo.getRejectionreasonId();
                rejectionreasonList.remove(rejectionreasonList.size() - 1);
            }
            request.getSession().setAttribute("REJECTIONREASON_LIST", rejectionreasonList);
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
            
            ArrayList rejectionreasonList = rejectionreason.getRejectionreasonByName(search, 0, count);
            int cnt = 0;
            if(rejectionreasonList.size() > 0)
            {
                RejectionreasonInfo cinfo = (RejectionreasonInfo) rejectionreasonList.get(rejectionreasonList.size() - 1);
                cnt = cinfo.getRejectionreasonId();
                rejectionreasonList.remove(rejectionreasonList.size() - 1);
            }
            request.getSession().setAttribute("REJECTIONREASON_LIST", rejectionreasonList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}