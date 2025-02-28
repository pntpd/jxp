package com.web.jxp.approvedby;

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

public class ApprovedbyAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ApprovedbyForm frm = (ApprovedbyForm) form;
        Approvedby approvedby = new Approvedby();
        Validate vobj = new Validate();
        int count = approvedby.getCount();
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
        int check_user = approvedby.checkUserSession(request, 36, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = approvedby.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Course Approved By Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setApprovedbyId(-1);
            saveToken(request);
            return mapping.findForward("add_approvedby");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int approvedbyId = frm.getApprovedbyId();
            frm.setApprovedbyId(approvedbyId);
            ApprovedbyInfo info = approvedby.getApprovedbyDetailById(approvedbyId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_approvedby");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int approvedbyId = frm.getApprovedbyId();
            frm.setApprovedbyId(approvedbyId);
            ApprovedbyInfo info = approvedby.getApprovedbyDetailByIdforDetail(approvedbyId);
            request.setAttribute("APPROVEDBY_DETAIL", info);
            return mapping.findForward("view_approvedby");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int approvedbyId = frm.getApprovedbyId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = approvedby.getLocalIp();
                int ck = approvedby.checkDuplicacy(approvedbyId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Approved By already exists");
                    return mapping.findForward("add_approvedby");
                }
                ApprovedbyInfo info = new ApprovedbyInfo(approvedbyId, name, status, uId);
                if(approvedbyId <= 0)
                {
                    int cc = approvedby.createApprovedby(info);
                    if(cc > 0)
                    {
                       approvedby.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 36, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                      
                    }
                    ArrayList approvedbyList = approvedby.getApprovedbyByName(search,  0, count);
                    int cnt = 0;
                    if(approvedbyList.size() > 0)
                    {
                        ApprovedbyInfo cinfo = (ApprovedbyInfo) approvedbyList.get(approvedbyList.size() - 1);
                        cnt = cinfo.getApprovedbyId();
                        approvedbyList.remove(approvedbyList.size() - 1);
                    }
                    request.getSession().setAttribute("APPROVEDBY_LIST", approvedbyList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    approvedby.updateApprovedby(info);
                    approvedby.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 36, approvedbyId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList approvedbyList = approvedby.getApprovedbyByName(search, next, count);
                    int cnt = 0;
                    if(approvedbyList.size() > 0)
                    {
                        ApprovedbyInfo cinfo = (ApprovedbyInfo) approvedbyList.get(approvedbyList.size() - 1);
                        cnt = cinfo.getApprovedbyId();
                        approvedbyList.remove(approvedbyList.size() - 1);
                    }
                    request.getSession().setAttribute("APPROVEDBY_LIST", approvedbyList);
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
            ArrayList approvedbyList = approvedby.getApprovedbyByName(search, next, count);
            int cnt = 0;
            if(approvedbyList.size() > 0)
            {
                ApprovedbyInfo cinfo = (ApprovedbyInfo) approvedbyList.get(approvedbyList.size() - 1);
                cnt = cinfo.getApprovedbyId();
                approvedbyList.remove(approvedbyList.size() - 1);
            }
            request.getSession().setAttribute("APPROVEDBY_LIST", approvedbyList);
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
            ArrayList approvedbyList = approvedby.getApprovedbyByName(search, 0, count);
            int cnt = 0;
            if(approvedbyList.size() > 0)
            {
                ApprovedbyInfo cinfo = (ApprovedbyInfo) approvedbyList.get(approvedbyList.size() - 1);
                cnt = cinfo.getApprovedbyId();
                approvedbyList.remove(approvedbyList.size() - 1);
            }
            request.getSession().setAttribute("APPROVEDBY_LIST", approvedbyList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}