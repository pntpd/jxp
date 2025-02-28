package com.web.jxp.hoursofwork;

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

public class HoursOfWorkAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HoursOfWorkForm frm = (HoursOfWorkForm) form;
        HoursOfWork hoursofwork = new HoursOfWork();
        Validate vobj = new Validate();
        int count = hoursofwork.getCount();
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
        int check_user = hoursofwork.checkUserSession(request, 22, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = hoursofwork.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "HoursOfWork Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setHoursofworkId(-1);
            saveToken(request);
            return mapping.findForward("add_hoursofwork");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int hoursofworkId = frm.getHoursofworkId();
            frm.setHoursofworkId(hoursofworkId);
            HoursOfWorkInfo info = hoursofwork.getHoursOfWorkDetailById(hoursofworkId);
            if(info != null)
            {
                frm.setNoOfHours(info.getNoOfHours());
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_hoursofwork");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int hoursofworkId = frm.getHoursofworkId();
            frm.setHoursofworkId(hoursofworkId);
            HoursOfWorkInfo info = hoursofwork.getHoursOfWorkDetailByIdforDetail(hoursofworkId);
            request.setAttribute("HOURSOFWORK_DETAIL", info);
            return mapping.findForward("view_hoursofwork");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int hoursofworkId = frm.getHoursofworkId();
                int noOfDays = frm.getNoOfHours();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = hoursofwork.getLocalIp();
                int ck = hoursofwork.checkDuplicacy(hoursofworkId, noOfDays);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Hours already exists");
                    return mapping.findForward("add_hoursofwork");
                }
                HoursOfWorkInfo info = new HoursOfWorkInfo(hoursofworkId, noOfDays, name, status, uId);
                if(hoursofworkId <= 0)
                {
                    int cc = hoursofwork.createHoursOfWork(info);
                    if(cc > 0)
                    {
                       hoursofwork.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 22, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList hoursofworkList = hoursofwork.getHoursOfWorkByName(search, 0, count);
                    int cnt = 0;
                    if(hoursofworkList.size() > 0)
                    {
                        HoursOfWorkInfo cinfo = (HoursOfWorkInfo) hoursofworkList.get(hoursofworkList.size() - 1);
                        cnt = cinfo.getHoursofworkId();
                        hoursofworkList.remove(hoursofworkList.size() - 1);
                    }
                    request.getSession().setAttribute("HOURSOFWORK_LIST", hoursofworkList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    hoursofwork.updateHoursOfWork(info);
                    hoursofwork.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 22, hoursofworkId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList hoursofworkList = hoursofwork.getHoursOfWorkByName(search, next, count);
                    int cnt = 0;
                    if(hoursofworkList.size() > 0)
                    {
                        HoursOfWorkInfo cinfo = (HoursOfWorkInfo) hoursofworkList.get(hoursofworkList.size() - 1);
                        cnt = cinfo.getHoursofworkId();
                        hoursofworkList.remove(hoursofworkList.size() - 1);
                    }
                    request.getSession().setAttribute("HOURSOFWORK_LIST", hoursofworkList);
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
            ArrayList hoursofworkList = hoursofwork.getHoursOfWorkByName(search, next, count);
            int cnt = 0;
            if(hoursofworkList.size() > 0)
            {
                HoursOfWorkInfo cinfo = (HoursOfWorkInfo) hoursofworkList.get(hoursofworkList.size() - 1);
                cnt = cinfo.getHoursofworkId();
                hoursofworkList.remove(hoursofworkList.size() - 1);
            }
            request.getSession().setAttribute("HOURSOFWORK_LIST", hoursofworkList);
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
            ArrayList hoursofworkList = hoursofwork.getHoursOfWorkByName(search, 0, count);
            int cnt = 0;
            if(hoursofworkList.size() > 0)
            {
                HoursOfWorkInfo cinfo = (HoursOfWorkInfo) hoursofworkList.get(hoursofworkList.size() - 1);
                cnt = cinfo.getHoursofworkId();
                hoursofworkList.remove(hoursofworkList.size() - 1);
            }
            request.getSession().setAttribute("HOURSOFWORK_LIST", hoursofworkList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}