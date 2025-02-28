package com.web.jxp.enginetype;

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

public class EnginetypeAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EnginetypeForm frm = (EnginetypeForm) form;
        Enginetype enginetype = new Enginetype();
        Validate vobj = new Validate();
        int count = enginetype.getCount();
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
        int check_user = enginetype.checkUserSession(request, 38, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = enginetype.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Enginetype Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setEnginetypeId(-1);
            saveToken(request);
            return mapping.findForward("add_enginetype");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int enginetypeId = frm.getEnginetypeId();
            frm.setEnginetypeId(enginetypeId);
            EnginetypeInfo info = enginetype.getEnginetypeDetailById(enginetypeId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_enginetype");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int enginetypeId = frm.getEnginetypeId();
            frm.setEnginetypeId(enginetypeId);
            EnginetypeInfo info = enginetype.getEnginetypeDetailByIdforDetail(enginetypeId);
            request.setAttribute("ENGINETYPE_DETAIL", info);
            return mapping.findForward("view_enginetype");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int enginetypeId = frm.getEnginetypeId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = enginetype.getLocalIp();
                int ck = enginetype.checkDuplicacy(enginetypeId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Engine Type already exists");
                    return mapping.findForward("add_enginetype");
                }
                EnginetypeInfo info = new EnginetypeInfo(enginetypeId, name, status, uId);
                if(enginetypeId <= 0)
                {
                    int cc = enginetype.createEnginetype(info);
                    if(cc > 0)
                    {
                       enginetype.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 38, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                      
                    }
                    ArrayList enginetypeList = enginetype.getEnginetypeByName(search, 0, count);
                    int cnt = 0;
                    if(enginetypeList.size() > 0)
                    {
                        EnginetypeInfo cinfo = (EnginetypeInfo) enginetypeList.get(enginetypeList.size() - 1);
                        cnt = cinfo.getEnginetypeId();
                        enginetypeList.remove(enginetypeList.size() - 1);
                    }
                    request.getSession().setAttribute("ENGINETYPE_LIST", enginetypeList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    enginetype.updateEnginetype(info);
                    enginetype.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 38, enginetypeId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList enginetypeList = enginetype.getEnginetypeByName(search, next, count);
                    int cnt = 0;
                    if(enginetypeList.size() > 0)
                    {
                        EnginetypeInfo cinfo = (EnginetypeInfo) enginetypeList.get(enginetypeList.size() - 1);
                        cnt = cinfo.getEnginetypeId();
                        enginetypeList.remove(enginetypeList.size() - 1);
                    }
                    request.getSession().setAttribute("ENGINETYPE_LIST", enginetypeList);
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
            ArrayList enginetypeList = enginetype.getEnginetypeByName(search, next, count);
            int cnt = 0;
            if(enginetypeList.size() > 0)
            {
                EnginetypeInfo cinfo = (EnginetypeInfo) enginetypeList.get(enginetypeList.size() - 1);
                cnt = cinfo.getEnginetypeId();
                enginetypeList.remove(enginetypeList.size() - 1);
            }
            request.getSession().setAttribute("ENGINETYPE_LIST", enginetypeList);
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
            
            ArrayList enginetypeList = enginetype.getEnginetypeByName(search, 0, count);
            int cnt = 0;
            if(enginetypeList.size() > 0)
            {
                EnginetypeInfo cinfo = (EnginetypeInfo) enginetypeList.get(enginetypeList.size() - 1);
                cnt = cinfo.getEnginetypeId();
                enginetypeList.remove(enginetypeList.size() - 1);
            }
            request.getSession().setAttribute("ENGINETYPE_LIST", enginetypeList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}