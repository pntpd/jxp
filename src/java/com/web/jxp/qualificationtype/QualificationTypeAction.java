package com.web.jxp.qualificationtype;

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

public class QualificationTypeAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        QualificationTypeForm frm = (QualificationTypeForm) form;
        QualificationType qualificationtype = new QualificationType();
        Validate vobj = new Validate();
        int count = qualificationtype.getCount();
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
        int check_user = qualificationtype.checkUserSession(request, 23, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = qualificationtype.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "QualificationType Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setQualificationtypeId(-1);
            saveToken(request);
            return mapping.findForward("add_qualificationtype");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int qualificationtypeId = frm.getQualificationtypeId();
            frm.setQualificationtypeId(qualificationtypeId);
            QualificationTypeInfo info = qualificationtype.getQualificationTypeDetailById(qualificationtypeId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_qualificationtype");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int qualificationtypeId = frm.getQualificationtypeId();
            frm.setQualificationtypeId(qualificationtypeId);
            QualificationTypeInfo info = qualificationtype.getQualificationTypeDetailByIdforDetail(qualificationtypeId);
            request.setAttribute("QUALIFICATIONTYPE_DETAIL", info);
            return mapping.findForward("view_qualificationtype");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int qualificationtypeId = frm.getQualificationtypeId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = qualificationtype.getLocalIp();
                int ck = qualificationtype.checkDuplicacy(qualificationtypeId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Education Kind already exists");
                    return mapping.findForward("add_qualificationtype");
                }
                QualificationTypeInfo info = new QualificationTypeInfo(qualificationtypeId, name, status, uId);
                if(qualificationtypeId <= 0)
                {
                    int cc = qualificationtype.createQualificationType(info);
                    if(cc > 0)
                    {
                       qualificationtype.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 23, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList qualificationtypeList = qualificationtype.getQualificationTypeByName(search, 0, count);
                    int cnt = 0;
                    if(qualificationtypeList.size() > 0)
                    {
                        QualificationTypeInfo cinfo = (QualificationTypeInfo) qualificationtypeList.get(qualificationtypeList.size() - 1);
                        cnt = cinfo.getQualificationtypeId();
                        qualificationtypeList.remove(qualificationtypeList.size() - 1);
                    }
                    request.getSession().setAttribute("QUALIFICATIONTYPE_LIST", qualificationtypeList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    qualificationtype.updateQualificationType(info);
                    qualificationtype.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 23, qualificationtypeId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList qualificationtypeList = qualificationtype.getQualificationTypeByName(search, next, count);
                    int cnt = 0;
                    if(qualificationtypeList.size() > 0)
                    {
                        QualificationTypeInfo cinfo = (QualificationTypeInfo) qualificationtypeList.get(qualificationtypeList.size() - 1);
                        cnt = cinfo.getQualificationtypeId();
                        qualificationtypeList.remove(qualificationtypeList.size() - 1);
                    }
                    request.getSession().setAttribute("QUALIFICATIONTYPE_LIST", qualificationtypeList);
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
            ArrayList qualificationtypeList = qualificationtype.getQualificationTypeByName(search, next, count);
            int cnt = 0;
            if(qualificationtypeList.size() > 0)
            {
                QualificationTypeInfo cinfo = (QualificationTypeInfo) qualificationtypeList.get(qualificationtypeList.size() - 1);
                cnt = cinfo.getQualificationtypeId();
                qualificationtypeList.remove(qualificationtypeList.size() - 1);
            }
            request.getSession().setAttribute("QUALIFICATIONTYPE_LIST", qualificationtypeList);
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
            ArrayList qualificationtypeList = qualificationtype.getQualificationTypeByName(search, 0, count);
            int cnt = 0;
            if(qualificationtypeList.size() > 0)
            {
                QualificationTypeInfo cinfo = (QualificationTypeInfo) qualificationtypeList.get(qualificationtypeList.size() - 1);
                cnt = cinfo.getQualificationtypeId();
                qualificationtypeList.remove(qualificationtypeList.size() - 1);
            }
            request.getSession().setAttribute("QUALIFICATIONTYPE_LIST", qualificationtypeList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}