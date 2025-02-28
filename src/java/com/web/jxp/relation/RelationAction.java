package com.web.jxp.relation;

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

public class RelationAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RelationForm frm = (RelationForm) form;
        Relation relation = new Relation();
        Validate vobj = new Validate();
        int count = relation.getCount();
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
        int check_user = relation.checkUserSession(request, 18, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = relation.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Relation Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setRelationId(-1);
            saveToken(request);
            return mapping.findForward("add_relation");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int relationId = frm.getRelationId();
            frm.setRelationId(relationId);
            RelationInfo info = relation.getRelationDetailById(relationId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_relation");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int relationId = frm.getRelationId();
            frm.setRelationId(relationId);
            RelationInfo info = relation.getRelationDetailByIdforDetail(relationId);
            request.setAttribute("RELATION_DETAIL", info);
            return mapping.findForward("view_relation");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int relationId = frm.getRelationId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = relation.getLocalIp();
                int ck = relation.checkDuplicacy(relationId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Relation already exists");
                    return mapping.findForward("add_relation");
                }
                RelationInfo info = new RelationInfo(relationId, name, status, uId);
                if(relationId <= 0)
                {
                    int cc = relation.createRelation(info);
                    if(cc > 0)
                    {
                       relation.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 18, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList relationList = relation.getRelationByName(search, 0, count);
                    int cnt = 0;
                    if(relationList.size() > 0)
                    {
                        RelationInfo cinfo = (RelationInfo) relationList.get(relationList.size() - 1);
                        cnt = cinfo.getRelationId();
                        relationList.remove(relationList.size() - 1);
                    }
                    request.getSession().setAttribute("RELATION_LIST", relationList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                   
                    return mapping.findForward("display");
                }
                else
                {
                    relation.updateRelation(info);
                    relation.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 18, relationId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList relationList = relation.getRelationByName(search, next, count);
                    int cnt = 0;
                    if(relationList.size() > 0)
                    {
                        RelationInfo cinfo = (RelationInfo) relationList.get(relationList.size() - 1);
                        cnt = cinfo.getRelationId();
                        relationList.remove(relationList.size() - 1);
                    }
                    request.getSession().setAttribute("RELATION_LIST", relationList);
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
            ArrayList relationList = relation.getRelationByName(search, next, count);
            int cnt = 0;
            if(relationList.size() > 0)
            {
                RelationInfo cinfo = (RelationInfo) relationList.get(relationList.size() - 1);
                cnt = cinfo.getRelationId();
                relationList.remove(relationList.size() - 1);
            }
            request.getSession().setAttribute("RELATION_LIST", relationList);
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
            ArrayList relationList = relation.getRelationByName(search, 0, count);
            int cnt = 0;
            if(relationList.size() > 0)
            {
                RelationInfo cinfo = (RelationInfo) relationList.get(relationList.size() - 1);
                cnt = cinfo.getRelationId();
                relationList.remove(relationList.size() - 1);
            }
            request.getSession().setAttribute("RELATION_LIST", relationList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}