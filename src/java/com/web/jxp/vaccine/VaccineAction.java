package com.web.jxp.vaccine;

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

public class VaccineAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        VaccineForm frm = (VaccineForm) form;
        Vaccine vaccine = new Vaccine();
        Validate vobj = new Validate();
        int count = vaccine.getCount();
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
        int check_user = vaccine.checkUserSession(request, 25, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = vaccine.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Vaccines Name Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setVaccineId(-1);
            saveToken(request);
            return mapping.findForward("add_vaccine");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int vaccineId = frm.getVaccineId();
            frm.setVaccineId(vaccineId);
            VaccineInfo info = vaccine.getVaccineDetailById(vaccineId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_vaccine");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int vaccineId = frm.getVaccineId();
            frm.setVaccineId(vaccineId);
            VaccineInfo info = vaccine.getVaccineDetailByIdforDetail(vaccineId);
            request.setAttribute("VACCINE_DETAIL", info);
            return mapping.findForward("view_vaccine");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int vaccineId = frm.getVaccineId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = vaccine.getLocalIp();
                int ck = vaccine.checkDuplicacy(vaccineId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Vaccination Name already exists");
                    return mapping.findForward("add_vaccine");
                }
                VaccineInfo info = new VaccineInfo(vaccineId, name, status, uId);
                if(vaccineId <= 0)
                {
                    int cc = vaccine.createVaccine(info);
                    if(cc > 0)
                    {
                       vaccine.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 25, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                      
                    }
                    ArrayList vaccineList = vaccine.getVaccineByName(search, 0, count);
                    int cnt = 0;
                    if(vaccineList.size() > 0)
                    {
                        VaccineInfo cinfo = (VaccineInfo) vaccineList.get(vaccineList.size() - 1);
                        cnt = cinfo.getVaccineId();
                        vaccineList.remove(vaccineList.size() - 1);
                    }
                    request.getSession().setAttribute("VACCINE_LIST", vaccineList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    vaccine.updateVaccine(info);
                    vaccine.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 25, vaccineId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList vaccineList = vaccine.getVaccineByName(search, next, count);
                    int cnt = 0;
                    if(vaccineList.size() > 0)
                    {
                        VaccineInfo cinfo = (VaccineInfo) vaccineList.get(vaccineList.size() - 1);
                        cnt = cinfo.getVaccineId();
                        vaccineList.remove(vaccineList.size() - 1);
                    }
                    request.getSession().setAttribute("VACCINE_LIST", vaccineList);
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
            ArrayList vaccineList = vaccine.getVaccineByName(search, next, count);
            int cnt = 0;
            if(vaccineList.size() > 0)
            {
                VaccineInfo cinfo = (VaccineInfo) vaccineList.get(vaccineList.size() - 1);
                cnt = cinfo.getVaccineId();
                vaccineList.remove(vaccineList.size() - 1);
            }
            request.getSession().setAttribute("VACCINE_LIST", vaccineList);
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
            ArrayList vaccineList = vaccine.getVaccineByName(search, 0, count);
            int cnt = 0;
            if(vaccineList.size() > 0)
            {
                VaccineInfo cinfo = (VaccineInfo) vaccineList.get(vaccineList.size() - 1);
                cnt = cinfo.getVaccineId();
                vaccineList.remove(vaccineList.size() - 1);
            }
            request.getSession().setAttribute("VACCINE_LIST", vaccineList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}