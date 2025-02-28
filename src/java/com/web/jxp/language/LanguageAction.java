package com.web.jxp.language;

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

public class LanguageAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LanguageForm frm = (LanguageForm) form;
        Language language = new Language();
        Validate vobj = new Validate();
        int count = language.getCount();
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
        int check_user = language.checkUserSession(request, 8, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = language.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Language Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setLanguageId(-1);
            saveToken(request);
            return mapping.findForward("add_language");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int languageId = frm.getLanguageId();
            frm.setLanguageId(languageId);
            LanguageInfo info = language.getLanguageDetailById(languageId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_language");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int languageId = frm.getLanguageId();
            frm.setLanguageId(languageId);
            LanguageInfo info = language.getLanguageDetailByIdforDetail(languageId);
            request.setAttribute("LANGUAGE_DETAIL", info);
            return mapping.findForward("view_language");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int languageId = frm.getLanguageId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = language.getLocalIp();
                int ck = language.checkDuplicacy(languageId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Language already exists");
                    return mapping.findForward("add_language");
                }
                LanguageInfo info = new LanguageInfo(languageId, name, status, uId);
                if(languageId <= 0)
                {
                    int cc = language.createLanguage(info);
                    if(cc > 0)
                    {
                       language.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 8, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList languageList = language.getLanguageByName(search, 0, count);
                    int cnt = 0;
                    if(languageList.size() > 0)
                    {
                        LanguageInfo cinfo = (LanguageInfo) languageList.get(languageList.size() - 1);
                        cnt = cinfo.getLanguageId();
                        languageList.remove(languageList.size() - 1);
                    }
                    request.getSession().setAttribute("LANGUAGE_LIST", languageList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    language.updateLanguage(info);
                    language.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 8, languageId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList languageList = language.getLanguageByName(search, next, count);
                    int cnt = 0;
                    if(languageList.size() > 0)
                    {
                        LanguageInfo cinfo = (LanguageInfo) languageList.get(languageList.size() - 1);
                        cnt = cinfo.getLanguageId();
                        languageList.remove(languageList.size() - 1);
                    }
                    request.getSession().setAttribute("LANGUAGE_LIST", languageList);
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
            ArrayList languageList = language.getLanguageByName(search, next, count);
            int cnt = 0;
            if(languageList.size() > 0)
            {
                LanguageInfo cinfo = (LanguageInfo) languageList.get(languageList.size() - 1);
                cnt = cinfo.getLanguageId();
                languageList.remove(languageList.size() - 1);
            }
            request.getSession().setAttribute("LANGUAGE_LIST", languageList);
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
            ArrayList languageList = language.getLanguageByName(search, 0, count);
            int cnt = 0;
            if(languageList.size() > 0)
            {
                LanguageInfo cinfo = (LanguageInfo) languageList.get(languageList.size() - 1);
                cnt = cinfo.getLanguageId();
                languageList.remove(languageList.size() - 1);
            }
            request.getSession().setAttribute("LANGUAGE_LIST", languageList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}