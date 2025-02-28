package com.web.jxp.proficiency;

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

public class ProficiencyAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProficiencyForm frm = (ProficiencyForm) form;
        Proficiency proficiency = new Proficiency();
        Validate vobj = new Validate();
        int count = proficiency.getCount();
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
        int check_user = proficiency.checkUserSession(request, 9, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = proficiency.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Proficiency Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setProficiencyId(-1);
            saveToken(request);
            return mapping.findForward("add_proficiency");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int proficiencyId = frm.getProficiencyId();
            frm.setProficiencyId(proficiencyId);
            ProficiencyInfo info = proficiency.getProficiencyDetailById(proficiencyId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_proficiency");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int proficiencyId = frm.getProficiencyId();
            frm.setProficiencyId(proficiencyId);
            ProficiencyInfo info = proficiency.getProficiencyDetailByIdforDetail(proficiencyId);
            request.setAttribute("PROFICIENCY_DETAIL", info);
            return mapping.findForward("view_proficiency");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int proficiencyId = frm.getProficiencyId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = proficiency.getLocalIp();
                int ck = proficiency.checkDuplicacy(proficiencyId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Proficiency already exists");
                    return mapping.findForward("add_proficiency");
                }
                ProficiencyInfo info = new ProficiencyInfo(proficiencyId, name, status, uId);
                if(proficiencyId <= 0)
                {
                    int cc = proficiency.createProficiency(info);
                    if(cc > 0)
                    {
                       proficiency.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 9, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList proficiencyList = proficiency.getProficiencyByName(search, 0, count);
                    int cnt = 0;
                    if(proficiencyList.size() > 0)
                    {
                        ProficiencyInfo cinfo = (ProficiencyInfo) proficiencyList.get(proficiencyList.size() - 1);
                        cnt = cinfo.getProficiencyId();
                        proficiencyList.remove(proficiencyList.size() - 1);
                    }
                    request.getSession().setAttribute("PROFICIENCY_LIST", proficiencyList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    proficiency.updateProficiency(info);
                    proficiency.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 9, proficiencyId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList proficiencyList = proficiency.getProficiencyByName(search, next, count);
                    int cnt = 0;
                    if(proficiencyList.size() > 0)
                    {
                        ProficiencyInfo cinfo = (ProficiencyInfo) proficiencyList.get(proficiencyList.size() - 1);
                        cnt = cinfo.getProficiencyId();
                        proficiencyList.remove(proficiencyList.size() - 1);
                    }
                    request.getSession().setAttribute("PROFICIENCY_LIST", proficiencyList);
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
            ArrayList proficiencyList = proficiency.getProficiencyByName(search, next, count);
            int cnt = 0;
            if(proficiencyList.size() > 0)
            {
                ProficiencyInfo cinfo = (ProficiencyInfo) proficiencyList.get(proficiencyList.size() - 1);
                cnt = cinfo.getProficiencyId();
                proficiencyList.remove(proficiencyList.size() - 1);
            }
            request.getSession().setAttribute("PROFICIENCY_LIST", proficiencyList);
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
            ArrayList proficiencyList = proficiency.getProficiencyByName(search, 0, count);
            int cnt = 0;
            if(proficiencyList.size() > 0)
            {
                ProficiencyInfo cinfo = (ProficiencyInfo) proficiencyList.get(proficiencyList.size() - 1);
                cnt = cinfo.getProficiencyId();
                proficiencyList.remove(proficiencyList.size() - 1);
            }
            request.getSession().setAttribute("PROFICIENCY_LIST", proficiencyList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}