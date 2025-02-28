package com.web.jxp.rotation;

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

public class RotationAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RotationForm frm = (RotationForm) form;
        Rotation rotation = new Rotation();
        Validate vobj = new Validate();
        int count = rotation.getCount();
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
        int check_user = rotation.checkUserSession(request, 21, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = rotation.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Rotation Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setRotationId(-1);
            saveToken(request);
            return mapping.findForward("add_rotation");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int rotationId = frm.getRotationId();
            frm.setRotationId(rotationId);
            RotationInfo info = rotation.getRotationDetailById(rotationId);
            if(info != null)
            {
                frm.setNoOfDays(info.getNoOfDays());
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_rotation");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int rotationId = frm.getRotationId();
            frm.setRotationId(rotationId);
            RotationInfo info = rotation.getRotationDetailByIdforDetail(rotationId);
            request.setAttribute("ROTATION_DETAIL", info);
            return mapping.findForward("view_rotation");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int rotationId = frm.getRotationId();
                int noOfDays = frm.getNoOfDays();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = rotation.getLocalIp();
                int ck = rotation.checkDuplicacy(rotationId, name, noOfDays);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Rotation already exists");
                    return mapping.findForward("add_rotation");
                }
                RotationInfo info = new RotationInfo(rotationId, noOfDays, name, status, uId);
                if(rotationId <= 0)
                {
                    int cc = rotation.createRotation(info);
                    if(cc > 0)
                    {
                       rotation.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 21, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList rotationList = rotation.getRotationByName(search, 0, count);
                    int cnt = 0;
                    if(rotationList.size() > 0)
                    {
                        RotationInfo cinfo = (RotationInfo) rotationList.get(rotationList.size() - 1);
                        cnt = cinfo.getRotationId();
                        rotationList.remove(rotationList.size() - 1);
                    }
                    request.getSession().setAttribute("ROTATION_LIST", rotationList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    rotation.updateRotation(info);
                    rotation.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 21, rotationId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList rotationList = rotation.getRotationByName(search, next, count);
                    int cnt = 0;
                    if(rotationList.size() > 0)
                    {
                        RotationInfo cinfo = (RotationInfo) rotationList.get(rotationList.size() - 1);
                        cnt = cinfo.getRotationId();
                        rotationList.remove(rotationList.size() - 1);
                    }
                    request.getSession().setAttribute("ROTATION_LIST", rotationList);
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
            ArrayList rotationList = rotation.getRotationByName(search, next, count);
            int cnt = 0;
            if(rotationList.size() > 0)
            {
                RotationInfo cinfo = (RotationInfo) rotationList.get(rotationList.size() - 1);
                cnt = cinfo.getRotationId();
                rotationList.remove(rotationList.size() - 1);
            }
            request.getSession().setAttribute("ROTATION_LIST", rotationList);
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
            ArrayList rotationList = rotation.getRotationByName(search, 0, count);
            int cnt = 0;
            if(rotationList.size() > 0)
            {
                RotationInfo cinfo = (RotationInfo) rotationList.get(rotationList.size() - 1);
                cnt = cinfo.getRotationId();
                rotationList.remove(rotationList.size() - 1);
            }
            request.getSession().setAttribute("ROTATION_LIST", rotationList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}