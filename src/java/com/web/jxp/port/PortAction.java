package com.web.jxp.port;

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

public class PortAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PortForm frm = (PortForm) form;
        Port port = new Port();
        Validate vobj = new Validate();
        int count = port.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        Collection countrys = port.getCountrys();
        frm.setCountrys(countrys);
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
        int check_user = port.checkUserSession(request, 44, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = port.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Port Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setPortId(-1);
            saveToken(request);
            return mapping.findForward("add_port");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int portId = frm.getPortId();
            frm.setPortId(portId);
            PortInfo info = port.getPortDetailById(portId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setCountrys(countrys);
                frm.setCountryId(info.getCountryId());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_port");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int portId = frm.getPortId();
            frm.setPortId(portId);
            PortInfo info = port.getPortDetailByIdforDetail(portId);
            request.setAttribute("PORT_DETAIL", info);
            return mapping.findForward("view_port");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int portId = frm.getPortId();
                String name = vobj.replacename(frm.getName());
                int countryId = frm.getCountryId();
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = port.getLocalIp();
                int ck = port.checkDuplicacy(portId, name, countryId);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Port already exists");
                    return mapping.findForward("add_port");
                }
                PortInfo info = new PortInfo(portId, name, countryId, status, uId);
                if(portId <= 0)
                {
                    int cc = port.createPort(info);
                    if(cc > 0)
                    {
                       port.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 44, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList portList = port.getPortByName(search, 0, count);
                    int cnt = 0;
                    if(portList.size() > 0)
                    {
                        PortInfo cinfo = (PortInfo) portList.get(portList.size() - 1);
                        cnt = cinfo.getPortId();
                        portList.remove(portList.size() - 1);
                    }
                    request.getSession().setAttribute("PORT_LIST", portList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    port.updatePort(info);
                    port.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 44, portId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList portList = port.getPortByName(search, next, count);
                    int cnt = 0;
                    if(portList.size() > 0)
                    {
                        PortInfo cinfo = (PortInfo) portList.get(portList.size() - 1);
                        cnt = cinfo.getPortId();
                        portList.remove(portList.size() - 1);
                    }
                    request.getSession().setAttribute("PORT_LIST", portList);
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
            ArrayList portList = port.getPortByName(search, next, count);
            int cnt = 0;
            if(portList.size() > 0)
            {
                PortInfo cinfo = (PortInfo) portList.get(portList.size() - 1);
                cnt = cinfo.getPortId();
                portList.remove(portList.size() - 1);
            }
            request.getSession().setAttribute("PORT_LIST", portList);
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
            ArrayList portList = port.getPortByName(search, 0, count);
            int cnt = 0;
            if(portList.size() > 0)
            {
                PortInfo cinfo = (PortInfo) portList.get(portList.size() - 1);
                cnt = cinfo.getPortId();
                portList.remove(portList.size() - 1);
            }
            request.getSession().setAttribute("PORT_LIST", portList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}