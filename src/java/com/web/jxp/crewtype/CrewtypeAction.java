package com.web.jxp.crewtype;

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

public class CrewtypeAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CrewtypeForm frm = (CrewtypeForm) form;
        Crewtype crewtype = new Crewtype();
        Validate vobj = new Validate();
        int count = crewtype.getCount();
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
        int check_user = crewtype.checkUserSession(request, 28, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = crewtype.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Crew Type Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setCrewtypeId(-1);
            saveToken(request);
            return mapping.findForward("add_crewtype");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int crewtypeId = frm.getCrewtypeId();
            frm.setCrewtypeId(crewtypeId);
            CrewtypeInfo info = crewtype.getCrewtypeDetailById(crewtypeId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_crewtype");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int crewtypeId = frm.getCrewtypeId();
            frm.setCrewtypeId(crewtypeId);
            CrewtypeInfo info = crewtype.getCrewtypeDetailByIdforDetail(crewtypeId);
            request.setAttribute("CREWTYPE_DETAIL", info);
            return mapping.findForward("view_crewtype");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int crewtypeId = frm.getCrewtypeId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = crewtype.getLocalIp();
                int ck = crewtype.checkDuplicacy(crewtypeId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Crew Type already exists");
                    return mapping.findForward("add_crewtype");
                }
                CrewtypeInfo info = new CrewtypeInfo(crewtypeId, name, status, uId);
                if(crewtypeId <= 0)
                {
                    int cc = crewtype.createCrewtype(info);
                    if(cc > 0)
                    {
                       crewtype.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 28, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                      
                    }
                    ArrayList crewtypeList = crewtype.getCrewtypeByName(search, 0, count);
                    int cnt = 0;
                    if(crewtypeList.size() > 0)
                    {
                        CrewtypeInfo cinfo = (CrewtypeInfo) crewtypeList.get(crewtypeList.size() - 1);
                        cnt = cinfo.getCrewtypeId();
                        crewtypeList.remove(crewtypeList.size() - 1);
                    }
                    request.getSession().setAttribute("CREWTYPE_LIST", crewtypeList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    crewtype.updateCrewtype(info);
                    crewtype.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 28, crewtypeId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList crewtypeList = crewtype.getCrewtypeByName(search, next, count);
                    int cnt = 0;
                    if(crewtypeList.size() > 0)
                    {
                        CrewtypeInfo cinfo = (CrewtypeInfo) crewtypeList.get(crewtypeList.size() - 1);
                        cnt = cinfo.getCrewtypeId();
                        crewtypeList.remove(crewtypeList.size() - 1);
                    }
                    request.getSession().setAttribute("CREWTYPE_LIST", crewtypeList);
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
            ArrayList crewtypeList = crewtype.getCrewtypeByName(search, next, count);
            int cnt = 0;
            if(crewtypeList.size() > 0)
            {
                CrewtypeInfo cinfo = (CrewtypeInfo) crewtypeList.get(crewtypeList.size() - 1);
                cnt = cinfo.getCrewtypeId();
                crewtypeList.remove(crewtypeList.size() - 1);
            }
            request.getSession().setAttribute("CREWTYPE_LIST", crewtypeList);
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
            
            ArrayList crewtypeList = crewtype.getCrewtypeByName(search, 0, count);
            int cnt = 0;
            if(crewtypeList.size() > 0)
            {
                CrewtypeInfo cinfo = (CrewtypeInfo) crewtypeList.get(crewtypeList.size() - 1);
                cnt = cinfo.getCrewtypeId();
                crewtypeList.remove(crewtypeList.size() - 1);
            }
            request.getSession().setAttribute("CREWTYPE_LIST", crewtypeList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}