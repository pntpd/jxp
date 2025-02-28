package com.web.jxp.benefitinformationtype;

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

public class BenefitinformationtypeAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BenefitinformationtypeForm frm = (BenefitinformationtypeForm) form;
        Benefitinformationtype benefitinformationtype = new Benefitinformationtype();
        Collection benefittypes = benefitinformationtype.getBenefittype();
        frm.setBenefittypes(benefittypes);
        Validate vobj = new Validate();
        
        int count = benefitinformationtype.getCount();
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
        int check_user = benefitinformationtype.checkUserSession(request, 33, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = benefitinformationtype.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Benefitinformationtype Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setBenefitinformationtypeId(-1);
            saveToken(request);
            return mapping.findForward("add_benefitinformationtype");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int benefitinformationtypeId = frm.getBenefitinformationtypeId();
            frm.setBenefitinformationtypeId(benefitinformationtypeId);
            BenefitinformationtypeInfo info = benefitinformationtype.getBenefitinformationtypeDetailById(benefitinformationtypeId);
            if(info != null)
            {                
                frm.setBenefitinformationtypeName(info.getBenefitinformationtypeName());
                frm.setStatus(info.getStatus());
                
                Collection benefittypess = benefitinformationtype.getBenefittype();
                frm.setBenefittypes(benefittypess);
                frm.setBenefittypeId(info.getBenefittypeId());
            }
            saveToken(request);
            return mapping.findForward("add_benefitinformationtype");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int benefitinformationtypeId = frm.getBenefitinformationtypeId();
            frm.setBenefitinformationtypeId(benefitinformationtypeId);
            BenefitinformationtypeInfo info = benefitinformationtype.getBenefitinformationtypeDetailByIdforDetail(benefitinformationtypeId);
            request.setAttribute("BENEFITINFORMATIONTYPE_DETAIL", info);
            return mapping.findForward("view_benefitinformationtype");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int benefitinformationtypeId = frm.getBenefitinformationtypeId();
                String name = vobj.replacename(frm.getBenefitinformationtypeName());
                int status = 1;
                int benefittypeId = frm.getBenefittypeId();
                
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = benefitinformationtype.getLocalIp();
                int ck = benefitinformationtype.checkDuplicacy(benefitinformationtypeId, name, benefittypeId);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Benefit Information Type already exists");
                    return mapping.findForward("add_benefitinformationtype");
                }
                BenefitinformationtypeInfo info = new BenefitinformationtypeInfo(benefitinformationtypeId, name, benefittypeId, status, uId);
                if(benefitinformationtypeId <= 0)
                {
                    int cc = benefitinformationtype.createBenefitinformationtype(info);
                    if(cc > 0)
                    {
                       benefitinformationtype.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 33, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList benefitinformationtypeList = benefitinformationtype.getBenefitinformationtypeByName(search,  0, count);
                    int cnt = 0;
                    if(benefitinformationtypeList.size() > 0)
                    {
                        BenefitinformationtypeInfo cinfo = (BenefitinformationtypeInfo) benefitinformationtypeList.get(benefitinformationtypeList.size() - 1);
                        cnt = cinfo.getBenefitinformationtypeId();
                        benefitinformationtypeList.remove(benefitinformationtypeList.size() - 1);
                    }
                    request.getSession().setAttribute("BENEFITINFORMATIONTYPE_LIST", benefitinformationtypeList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    benefitinformationtype.updateBenefitinformationtype(info);
                    benefitinformationtype.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 33, benefitinformationtypeId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList benefitinformationtypeList = benefitinformationtype.getBenefitinformationtypeByName(search, next, count);
                    int cnt = 0;
                    if(benefitinformationtypeList.size() > 0)
                    {
                        BenefitinformationtypeInfo cinfo = (BenefitinformationtypeInfo) benefitinformationtypeList.get(benefitinformationtypeList.size() - 1);
                        cnt = cinfo.getBenefitinformationtypeId();
                        benefitinformationtypeList.remove(benefitinformationtypeList.size() - 1);
                    }
                    request.getSession().setAttribute("BENEFITINFORMATIONTYPE_LIST", benefitinformationtypeList);
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
            ArrayList benefitinformationtypeList = benefitinformationtype.getBenefitinformationtypeByName(search, next, count);
            int cnt = 0;
            if(benefitinformationtypeList.size() > 0)
            {
                BenefitinformationtypeInfo cinfo = (BenefitinformationtypeInfo) benefitinformationtypeList.get(benefitinformationtypeList.size() - 1);
                cnt = cinfo.getBenefitinformationtypeId();
                benefitinformationtypeList.remove(benefitinformationtypeList.size() - 1);
            }
            request.getSession().setAttribute("BENEFITINFORMATIONTYPE_LIST", benefitinformationtypeList);
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
            
            ArrayList benefitinformationtypeList = benefitinformationtype.getBenefitinformationtypeByName(search, 0, count);
            int cnt = 0;
            if(benefitinformationtypeList.size() > 0)
            {
                BenefitinformationtypeInfo cinfo = (BenefitinformationtypeInfo) benefitinformationtypeList.get(benefitinformationtypeList.size() - 1);
                cnt = cinfo.getBenefitinformationtypeId();
                benefitinformationtypeList.remove(benefitinformationtypeList.size() - 1);
            }
            request.getSession().setAttribute("BENEFITINFORMATIONTYPE_LIST", benefitinformationtypeList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}