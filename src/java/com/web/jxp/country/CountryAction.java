package com.web.jxp.country;

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

public class CountryAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CountryForm frm = (CountryForm) form;
        Country country = new Country();
        Validate vobj = new Validate();
        int count = country.getCount();
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
        int check_user = country.checkUserSession(request, 5, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = country.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Country Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setCountryId(-1);
            saveToken(request);
            return mapping.findForward("add_country");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int countryId = frm.getCountryId();
            frm.setCountryId(countryId);
            CountryInfo info = country.getCountryDetailById(countryId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setNationality(info.getNationality());
                frm.setCode(info.getCode());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_country");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int countryId = frm.getCountryId();
            frm.setCountryId(countryId);
            CountryInfo info = country.getCountryDetailByIdforDetail(countryId);
            request.setAttribute("COUNTRY_DETAIL", info);
            return mapping.findForward("view_country");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int countryId = frm.getCountryId();
                String name = vobj.replacename(frm.getName());
                String nationality = frm.getNationality();
                String code = frm.getCode();
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = country.getLocalIp();
                int ck = country.checkDuplicacy(countryId, name, nationality);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Country already exists");
                    return mapping.findForward("add_country");
                }
                CountryInfo info = new CountryInfo(countryId, name, nationality, code, status, uId);
                if(countryId <= 0)
                {
                    int cc = country.createCountry(info);
                    if(cc > 0)
                    {
                       country.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 5, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList countryList = country.getCountryByName(search, 0, count);
                    int cnt = 0;
                    if(countryList.size() > 0)
                    {
                        CountryInfo cinfo = (CountryInfo) countryList.get(countryList.size() - 1);
                        cnt = cinfo.getCountryId();
                        countryList.remove(countryList.size() - 1);
                    }
                    request.getSession().setAttribute("COUNTRY_LIST", countryList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    country.updateCountry(info);
                    country.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 5, countryId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList countryList = country.getCountryByName(search, next, count);
                    int cnt = 0;
                    if(countryList.size() > 0)
                    {
                        CountryInfo cinfo = (CountryInfo) countryList.get(countryList.size() - 1);
                        cnt = cinfo.getCountryId();
                        countryList.remove(countryList.size() - 1);
                    }
                    request.getSession().setAttribute("COUNTRY_LIST", countryList);
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
            ArrayList countryList = country.getCountryByName(search, next, count);
            int cnt = 0;
            if(countryList.size() > 0)
            {
                CountryInfo cinfo = (CountryInfo) countryList.get(countryList.size() - 1);
                cnt = cinfo.getCountryId();
                countryList.remove(countryList.size() - 1);
            }
            request.getSession().setAttribute("COUNTRY_LIST", countryList);
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
            
            ArrayList countryList = country.getCountryByName(search, 0, count);
            int cnt = 0;
            if(countryList.size() > 0)
            {
                CountryInfo cinfo = (CountryInfo) countryList.get(countryList.size() - 1);
                cnt = cinfo.getCountryId();
                countryList.remove(countryList.size() - 1);
            }
            request.getSession().setAttribute("COUNTRY_LIST", countryList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}