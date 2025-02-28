package com.web.jxp.city;

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
import java.util.Collection;
import java.util.Stack;

public class CityAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CityForm frm = (CityForm) form;
        City city = new City();
        Validate vobj = new Validate();
        Collection countrys = city.getCountry();
        frm.setCountrys(countrys);
        
        int count = city.getCount();
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
        int check_user = city.checkUserSession(request, 24, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = city.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "City Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setCityId(-1);
            saveToken(request);
            return mapping.findForward("add_city");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int cityId = frm.getCityId();
            frm.setCityId(cityId);
            CityInfo info = city.getCityDetailById(cityId);
            if(info != null)
            {    
                if(info.getCityName() != null)
                    frm.setCityName(info.getCityName());
                frm.setStatus(info.getStatus());
                frm.setCountryId(info.getCountryId());
            }
            saveToken(request);
            return mapping.findForward("add_city");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int cityId = frm.getCityId();
            frm.setCityId(cityId);
            CityInfo info = city.getCityDetailByIdforDetail(cityId);
            request.setAttribute("CITY_DETAIL", info);
            return mapping.findForward("view_city");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int cityId = frm.getCityId();
                String name = vobj.replacename(frm.getCityName());
                int status = 1;
                int countryId = frm.getCountryId();
                
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = city.getLocalIp();
                int ck = city.checkDuplicacy(cityId, name, countryId);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "City already exists");
                    return mapping.findForward("add_city");
                }
                CityInfo info = new CityInfo(cityId, name, countryId, status, uId);
                if(cityId <= 0)
                {
                    int cc = city.createCity(info);
                    if(cc > 0)
                    {
                       city.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 24, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList cityList = city.getCityByName(search, 0, count);
                    int cnt = 0;
                    if(cityList.size() > 0)
                    {
                        CityInfo cinfo = (CityInfo) cityList.get(cityList.size() - 1);
                        cnt = cinfo.getCityId();
                        cityList.remove(cityList.size() - 1);
                    }
                    request.getSession().setAttribute("CITY_LIST", cityList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    city.updateCity(info);
                    city.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 24, cityId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList cityList = city.getCityByName(search, next, count);
                    int cnt = 0;
                    if(cityList.size() > 0)
                    {
                        CityInfo cinfo = (CityInfo) cityList.get(cityList.size() - 1);
                        cnt = cinfo.getCityId();
                        cityList.remove(cityList.size() - 1);
                    }
                    request.getSession().setAttribute("CITY_LIST", cityList);
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
            ArrayList cityList = city.getCityByName(search, next, count);
            int cnt = 0;
            if(cityList.size() > 0)
            {
                CityInfo cinfo = (CityInfo) cityList.get(cityList.size() - 1);
                cnt = cinfo.getCityId();
                cityList.remove(cityList.size() - 1);
            }
            request.getSession().setAttribute("CITY_LIST", cityList);
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
            ArrayList cityList = city.getCityByName(search, 0, count);
            int cnt = 0;
            if(cityList.size() > 0)
            {
                CityInfo cinfo = (CityInfo) cityList.get(cityList.size() - 1);
                cnt = cinfo.getCityId();
                cityList.remove(cityList.size() - 1);
            }
            request.getSession().setAttribute("CITY_LIST", cityList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}