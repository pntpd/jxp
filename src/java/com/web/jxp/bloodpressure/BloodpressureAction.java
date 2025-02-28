package com.web.jxp.bloodpressure;

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

public class BloodpressureAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BloodpressureForm frm = (BloodpressureForm) form;
        Bloodpressure bloodpressure = new Bloodpressure();
        Validate vobj = new Validate();
        int count = bloodpressure.getCount();
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
        int check_user = bloodpressure.checkUserSession(request, 31, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = bloodpressure.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Bloodpressures Name Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setBloodpressureId(-1);
            saveToken(request);
            return mapping.findForward("add_bloodpressure");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int bloodpressureId = frm.getBloodpressureId();
            frm.setBloodpressureId(bloodpressureId);
            BloodpressureInfo info = bloodpressure.getBloodpressureDetailById(bloodpressureId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_bloodpressure");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int bloodpressureId = frm.getBloodpressureId();
            frm.setBloodpressureId(bloodpressureId);
            BloodpressureInfo info = bloodpressure.getBloodpressureDetailByIdforDetail(bloodpressureId);
            request.setAttribute("BLOODPRESSURE_DETAIL", info);
            return mapping.findForward("view_bloodpressure");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int bloodpressureId = frm.getBloodpressureId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = bloodpressure.getLocalIp();
                int ck = bloodpressure.checkDuplicacy(bloodpressureId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Bloodpressure already exists");
                    return mapping.findForward("add_bloodpressure");
                }
                BloodpressureInfo info = new BloodpressureInfo(bloodpressureId, name, status, uId);
                if(bloodpressureId <= 0)
                {
                    int cc = bloodpressure.createBloodpressure(info);
                    if(cc > 0)
                    {
                       bloodpressure.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 31, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                      
                    }
                    ArrayList bloodpressureList = bloodpressure.getBloodpressureByName(search, 0, count);
                    int cnt = 0;
                    if(bloodpressureList.size() > 0)
                    {
                        BloodpressureInfo cinfo = (BloodpressureInfo) bloodpressureList.get(bloodpressureList.size() - 1);
                        cnt = cinfo.getBloodpressureId();
                        bloodpressureList.remove(bloodpressureList.size() - 1);
                    }
                    request.getSession().setAttribute("BLOODPRESSURE_LIST", bloodpressureList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    bloodpressure.updateBloodpressure(info);
                    bloodpressure.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 31, bloodpressureId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList bloodpressureList = bloodpressure.getBloodpressureByName(search, next, count);
                    int cnt = 0;
                    if(bloodpressureList.size() > 0)
                    {
                        BloodpressureInfo cinfo = (BloodpressureInfo) bloodpressureList.get(bloodpressureList.size() - 1);
                        cnt = cinfo.getBloodpressureId();
                        bloodpressureList.remove(bloodpressureList.size() - 1);
                    }
                    request.getSession().setAttribute("BLOODPRESSURE_LIST", bloodpressureList);
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
            ArrayList bloodpressureList = bloodpressure.getBloodpressureByName(search, next, count);
            int cnt = 0;
            if(bloodpressureList.size() > 0)
            {
                BloodpressureInfo cinfo = (BloodpressureInfo) bloodpressureList.get(bloodpressureList.size() - 1);
                cnt = cinfo.getBloodpressureId();
                bloodpressureList.remove(bloodpressureList.size() - 1);
            }
            request.getSession().setAttribute("BLOODPRESSURE_LIST", bloodpressureList);
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
            
            ArrayList bloodpressureList = bloodpressure.getBloodpressureByName(search, 0, count);
            int cnt = 0;
            if(bloodpressureList.size() > 0)
            {
                BloodpressureInfo cinfo = (BloodpressureInfo) bloodpressureList.get(bloodpressureList.size() - 1);
                cnt = cinfo.getBloodpressureId();
                bloodpressureList.remove(bloodpressureList.size() - 1);
            }
            request.getSession().setAttribute("BLOODPRESSURE_LIST", bloodpressureList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}