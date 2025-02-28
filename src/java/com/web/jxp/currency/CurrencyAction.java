package com.web.jxp.currency;

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

public class CurrencyAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CurrencyForm frm = (CurrencyForm) form;
        Currency currency = new Currency();
        Validate vobj = new Validate();
        int count = currency.getCount();
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
        int check_user = currency.checkUserSession(request, 10, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = currency.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Currency Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setCurrencyId(-1);
            saveToken(request);
            return mapping.findForward("add_currency");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int currencyId = frm.getCurrencyId();
            frm.setCurrencyId(currencyId);
            CurrencyInfo info = currency.getCurrencyDetailById(currencyId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_currency");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int currencyId = frm.getCurrencyId();
            frm.setCurrencyId(currencyId);
            CurrencyInfo info = currency.getCurrencyDetailByIdforDetail(currencyId);
            request.setAttribute("CURRENCY_DETAIL", info);
            return mapping.findForward("view_currency");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int currencyId = frm.getCurrencyId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = currency.getLocalIp();
                int ck = currency.checkDuplicacy(currencyId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Currency already exists");
                    return mapping.findForward("add_currency");
                }
                CurrencyInfo info = new CurrencyInfo(currencyId, name, status, uId);
                if(currencyId <= 0)
                {
                    int cc = currency.createCurrency(info);
                    if(cc > 0)
                    {
                       currency.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 10, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                      
                    }
                    ArrayList currencyList = currency.getCurrencyByName(search, 0, count);
                    int cnt = 0;
                    if(currencyList.size() > 0)
                    {
                        CurrencyInfo cinfo = (CurrencyInfo) currencyList.get(currencyList.size() - 1);
                        cnt = cinfo.getCurrencyId();
                        currencyList.remove(currencyList.size() - 1);
                    }
                    request.getSession().setAttribute("CURRENCY_LIST", currencyList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    currency.updateCurrency(info);
                    currency.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 10, currencyId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList currencyList = currency.getCurrencyByName(search, next, count);
                    int cnt = 0;
                    if(currencyList.size() > 0)
                    {
                        CurrencyInfo cinfo = (CurrencyInfo) currencyList.get(currencyList.size() - 1);
                        cnt = cinfo.getCurrencyId();
                        currencyList.remove(currencyList.size() - 1);
                    }
                    request.getSession().setAttribute("CURRENCY_LIST", currencyList);
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
            ArrayList currencyList = currency.getCurrencyByName(search, next, count);
            int cnt = 0;
            if(currencyList.size() > 0)
            {
                CurrencyInfo cinfo = (CurrencyInfo) currencyList.get(currencyList.size() - 1);
                cnt = cinfo.getCurrencyId();
                currencyList.remove(currencyList.size() - 1);
            }
            request.getSession().setAttribute("CURRENCY_LIST", currencyList);
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
            
            ArrayList currencyList = currency.getCurrencyByName(search, 0, count);
            int cnt = 0;
            if(currencyList.size() > 0)
            {
                CurrencyInfo cinfo = (CurrencyInfo) currencyList.get(currencyList.size() - 1);
                cnt = cinfo.getCurrencyId();
                currencyList.remove(currencyList.size() - 1);
            }
            request.getSession().setAttribute("CURRENCY_LIST", currencyList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}