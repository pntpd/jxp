package com.web.jxp.pdept;

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

public class PdeptAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PdeptForm frm = (PdeptForm) form;
        Pdept pdept = new Pdept();
        Validate vobj = new Validate();
        int count = pdept.getCount();
        
        Collection assettypes = pdept.getAssettypes();
        frm.setAssettypes(assettypes);   
        int assettypeIdIndex = frm.getAssettypeIdIndex();
        frm.setAssettypeIdIndex(assettypeIdIndex);
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        Collection statuses = pdept.getStatusNew();
        frm.setStatuses(statuses);
        int statusIndex = frm.getStatusIndex(); 
        frm.setStatusIndex(statusIndex);
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
        int check_user = pdept.checkUserSession(request, 82, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = pdept.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Configure Department Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setPdeptId(-1);
            assettypes = pdept.getAssettypesForEdit();
            frm.setAssettypes(assettypes);   
            saveToken(request);
            return mapping.findForward("add_pdept");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int pdeptId = frm.getPdeptId();
            frm.setPdeptId(pdeptId);
            assettypes = pdept.getAssettypesForEdit();
            frm.setAssettypes(assettypes);   
            PdeptInfo info = pdept.getPdeptDetailById(pdeptId);
            if(info != null)
            {   
                if(info.getName() != null)
                    frm.setName(info.getName());
                if(info.getDescription() != null)
                    frm.setDescription(info.getDescription());
                frm.setAssettypeId(info.getAssettypeId());
                frm.setStatus(info.getStatus());                
            }
            saveToken(request);
            return mapping.findForward("add_pdept");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int pdeptId = frm.getPdeptId();
            frm.setPdeptId(pdeptId);
            PdeptInfo info = pdept.getPdeptDetailByIdforDetail(pdeptId);
            request.setAttribute("PDEPT_DETAIL", info);
            return mapping.findForward("view_pdept");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int pdeptId = frm.getPdeptId();
                String name = vobj.replacedesc(frm.getName());
                String description = vobj.replacedesc(frm.getDescription());
                int status = 1;
                int assettypeId = frm.getAssettypeId();                
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = pdept.getLocalIp();
                int ck = pdept.checkDuplicacy(pdeptId, name, assettypeId);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Data already exists");
                    return mapping.findForward("add_pdept");
                }
                PdeptInfo info = new PdeptInfo(pdeptId, name, assettypeId, description, status, uId);
                if(pdeptId <= 0)
                {
                    int cc = pdept.createPdept(info);
                    if(cc > 0)
                    {
                       pdept.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 82, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList pdeptList = pdept.getPdeptByName(search, statusIndex, assettypeIdIndex, 0, count);
                    int cnt = 0;
                    if(pdeptList.size() > 0)
                    {
                        PdeptInfo cinfo = (PdeptInfo) pdeptList.get(pdeptList.size() - 1);
                        cnt = cinfo.getPdeptId();
                        pdeptList.remove(pdeptList.size() - 1);
                    }
                    request.getSession().setAttribute("PDEPT_LIST", pdeptList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    pdept.updatePdept(info);
                    pdept.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 82, pdeptId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList pdeptList = pdept.getPdeptByName(search, statusIndex, assettypeIdIndex, next, count);
                    int cnt = 0;
                    if(pdeptList.size() > 0)
                    {
                        PdeptInfo cinfo = (PdeptInfo) pdeptList.get(pdeptList.size() - 1);
                        cnt = cinfo.getPdeptId();
                        pdeptList.remove(pdeptList.size() - 1);
                    }
                    request.getSession().setAttribute("PDEPT_LIST", pdeptList);
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
            ArrayList pdeptList = pdept.getPdeptByName(search, statusIndex, assettypeIdIndex, next, count);
            int cnt = 0;
            if(pdeptList.size() > 0)
            {
                PdeptInfo cinfo = (PdeptInfo) pdeptList.get(pdeptList.size() - 1);
                cnt = cinfo.getPdeptId();
                pdeptList.remove(pdeptList.size() - 1);
            }
            request.getSession().setAttribute("PDEPT_LIST", pdeptList);
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
            ArrayList pdeptList = pdept.getPdeptByName(search, statusIndex, assettypeIdIndex, 0, count);
            int cnt = 0;
            if(pdeptList.size() > 0)
            {
                PdeptInfo cinfo = (PdeptInfo) pdeptList.get(pdeptList.size() - 1);
                cnt = cinfo.getPdeptId();
                pdeptList.remove(pdeptList.size() - 1);
            }
            request.getSession().setAttribute("PDEPT_LIST", pdeptList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}