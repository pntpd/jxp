package com.web.jxp.companyindustry;

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

public class CompanyindustryAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CompanyindustryForm frm = (CompanyindustryForm) form;
        Companyindustry companyindustry = new Companyindustry();
        Validate vobj = new Validate();
        int count = companyindustry.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0;
        String permission = "N";
        if (request.getSession().getAttribute("LOGININFO") != null) 
        {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) 
            {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
            }
        }
        int check_user = companyindustry.checkUserSession(request, 27, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = companyindustry.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Verification Type Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setCompanyindustryId(-1);
            saveToken(request);
            return mapping.findForward("add_companyindustry");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int companyindustryId = frm.getCompanyindustryId();
            frm.setCompanyindustryId(companyindustryId);
            CompanyindustryInfo info = companyindustry.getCompanyindustryDetailById(companyindustryId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_companyindustry");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int companyindustryId = frm.getCompanyindustryId();
            frm.setCompanyindustryId(companyindustryId);
            CompanyindustryInfo info = companyindustry.getCompanyindustryDetailByIdforDetail(companyindustryId);
            request.setAttribute("COMPANYINDUSTRY_DETAIL", info);
            return mapping.findForward("view_companyindustry");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int companyindustryId = frm.getCompanyindustryId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = companyindustry.getLocalIp();
                int ck = companyindustry.checkDuplicacy(companyindustryId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Company Industry already exists");
                    return mapping.findForward("add_companyindustry");
                }
                CompanyindustryInfo info = new CompanyindustryInfo(companyindustryId, name, status, uId);
                if(companyindustryId <= 0)
                {
                    int cc = companyindustry.createCompanyindustry(info);
                    if(cc > 0)
                    {
                       companyindustry.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 27, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList companyindustryList = companyindustry.getCompanyindustryByName(search, 0, count);
                    int cnt = 0;
                    if(companyindustryList.size() > 0)
                    {
                        CompanyindustryInfo cinfo = (CompanyindustryInfo) companyindustryList.get(companyindustryList.size() - 1);
                        cnt = cinfo.getCompanyindustryId();
                        companyindustryList.remove(companyindustryList.size() - 1);
                    }
                    request.getSession().setAttribute("COMPANYINDUSTRY_LIST", companyindustryList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    companyindustry.updateCompanyindustry(info);
                    companyindustry.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 27, companyindustryId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList companyindustryList = companyindustry.getCompanyindustryByName(search, next, count);
                    int cnt = 0;
                    if(companyindustryList.size() > 0)
                    {
                        CompanyindustryInfo cinfo = (CompanyindustryInfo) companyindustryList.get(companyindustryList.size() - 1);
                        cnt = cinfo.getCompanyindustryId();
                        companyindustryList.remove(companyindustryList.size() - 1);
                    }
                    request.getSession().setAttribute("COMPANYINDUSTRY_LIST", companyindustryList);
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
            ArrayList companyindustryList = companyindustry.getCompanyindustryByName(search, next, count);
            int cnt = 0;
            if(companyindustryList.size() > 0)
            {
                CompanyindustryInfo cinfo = (CompanyindustryInfo) companyindustryList.get(companyindustryList.size() - 1);
                cnt = cinfo.getCompanyindustryId();
                companyindustryList.remove(companyindustryList.size() - 1);
            }
            request.getSession().setAttribute("COMPANYINDUSTRY_LIST", companyindustryList);
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
            
            ArrayList companyindustryList = companyindustry.getCompanyindustryByName(search, 0, count);
            int cnt = 0;
            if(companyindustryList.size() > 0)
            {
                CompanyindustryInfo cinfo = (CompanyindustryInfo) companyindustryList.get(companyindustryList.size() - 1);
                cnt = cinfo.getCompanyindustryId();
                companyindustryList.remove(companyindustryList.size() - 1);
            }
            request.getSession().setAttribute("COMPANYINDUSTRY_LIST", companyindustryList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}