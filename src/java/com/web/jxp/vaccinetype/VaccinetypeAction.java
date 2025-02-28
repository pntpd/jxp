package com.web.jxp.vaccinetype;

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

public class VaccinetypeAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        VaccinetypeForm frm = (VaccinetypeForm) form;
        Vaccinetype vaccinetype = new Vaccinetype();
        Validate vobj = new Validate();
        int count = vaccinetype.getCount();
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
        int check_user = vaccinetype.checkUserSession(request, 26, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = vaccinetype.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Vaccine Type Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setVaccinetypeId(-1);
            saveToken(request);
            return mapping.findForward("add_vaccinetype");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int vaccinetypeId = frm.getVaccinetypeId();
            frm.setVaccinetypeId(vaccinetypeId);
            VaccinetypeInfo info = vaccinetype.getVaccinetypeDetailById(vaccinetypeId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_vaccinetype");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int vaccinetypeId = frm.getVaccinetypeId();
            frm.setVaccinetypeId(vaccinetypeId);
            VaccinetypeInfo info = vaccinetype.getVaccinetypeDetailByIdforDetail(vaccinetypeId);
            request.setAttribute("VACCINETYPE_DETAIL", info);
            return mapping.findForward("view_vaccinetype");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int vaccinetypeId = frm.getVaccinetypeId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = vaccinetype.getLocalIp();
                int ck = vaccinetype.checkDuplicacy(vaccinetypeId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Vaccination Type already exists");
                    return mapping.findForward("add_vaccinetype");
                }
                VaccinetypeInfo info = new VaccinetypeInfo(vaccinetypeId, name, status, uId);
                if(vaccinetypeId <= 0)
                {
                    int cc = vaccinetype.createVaccinetype(info);
                    if(cc > 0)
                    {
                       vaccinetype.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 26, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                      
                    }
                    ArrayList vaccinetypeList = vaccinetype.getVaccinetypeByName(search,  0, count);
                    int cnt = 0;
                    if(vaccinetypeList.size() > 0)
                    {
                        VaccinetypeInfo cinfo = (VaccinetypeInfo) vaccinetypeList.get(vaccinetypeList.size() - 1);
                        cnt = cinfo.getVaccinetypeId();
                        vaccinetypeList.remove(vaccinetypeList.size() - 1);
                    }
                    request.getSession().setAttribute("VACCINETYPE_LIST", vaccinetypeList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    vaccinetype.updateVaccinetype(info);
                    vaccinetype.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 26, vaccinetypeId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList vaccinetypeList = vaccinetype.getVaccinetypeByName(search, next, count);
                    int cnt = 0;
                    if(vaccinetypeList.size() > 0)
                    {
                        VaccinetypeInfo cinfo = (VaccinetypeInfo) vaccinetypeList.get(vaccinetypeList.size() - 1);
                        cnt = cinfo.getVaccinetypeId();
                        vaccinetypeList.remove(vaccinetypeList.size() - 1);
                    }
                    request.getSession().setAttribute("VACCINETYPE_LIST", vaccinetypeList);
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
            ArrayList vaccinetypeList = vaccinetype.getVaccinetypeByName(search, next, count);
            int cnt = 0;
            if(vaccinetypeList.size() > 0)
            {
                VaccinetypeInfo cinfo = (VaccinetypeInfo) vaccinetypeList.get(vaccinetypeList.size() - 1);
                cnt = cinfo.getVaccinetypeId();
                vaccinetypeList.remove(vaccinetypeList.size() - 1);
            }
            request.getSession().setAttribute("VACCINETYPE_LIST", vaccinetypeList);
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
            ArrayList vaccinetypeList = vaccinetype.getVaccinetypeByName(search, 0, count);
            int cnt = 0;
            if(vaccinetypeList.size() > 0)
            {
                VaccinetypeInfo cinfo = (VaccinetypeInfo) vaccinetypeList.get(vaccinetypeList.size() - 1);
                cnt = cinfo.getVaccinetypeId();
                vaccinetypeList.remove(vaccinetypeList.size() - 1);
            }
            request.getSession().setAttribute("VACCINETYPE_LIST", vaccinetypeList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}