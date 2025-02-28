package com.web.jxp.skills;

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

public class SkillsAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SkillsForm frm = (SkillsForm) form;
        Skills skills = new Skills();
        Validate vobj = new Validate();
        int count = skills.getCount();
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
        int check_user = skills.checkUserSession(request, 32, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = skills.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Skills Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setSkillsId(-1);
            saveToken(request);
            return mapping.findForward("add_skills");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int skillsId = frm.getSkillsId();
            frm.setSkillsId(skillsId);
            SkillsInfo info = skills.getSkillsDetailById(skillsId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_skills");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int skillsId = frm.getSkillsId();
            frm.setSkillsId(skillsId);
            SkillsInfo info = skills.getSkillsDetailByIdforDetail(skillsId);
            request.setAttribute("SKILLS_DETAIL", info);
            return mapping.findForward("view_skills");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int skillsId = frm.getSkillsId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = skills.getLocalIp();
                int ck = skills.checkDuplicacy(skillsId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Skill already exists");
                    return mapping.findForward("add_skills");
                }
                SkillsInfo info = new SkillsInfo(skillsId, name, status, uId);
                if(skillsId <= 0)
                {
                    int cc = skills.createSkills(info);
                    if(cc > 0)
                    {
                       skills.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 32, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList skillsList = skills.getSkillsByName(search, 0, count);
                    int cnt = 0;
                    if(skillsList.size() > 0)
                    {
                        SkillsInfo cinfo = (SkillsInfo) skillsList.get(skillsList.size() - 1);
                        cnt = cinfo.getSkillsId();
                        skillsList.remove(skillsList.size() - 1);
                    }
                    request.getSession().setAttribute("SKILLS_LIST", skillsList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    skills.updateSkills(info);
                    skills.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 32, skillsId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList skillsList = skills.getSkillsByName(search, next, count);
                    int cnt = 0;
                    if(skillsList.size() > 0)
                    {
                        SkillsInfo cinfo = (SkillsInfo) skillsList.get(skillsList.size() - 1);
                        cnt = cinfo.getSkillsId();
                        skillsList.remove(skillsList.size() - 1);
                    }
                    request.getSession().setAttribute("SKILLS_LIST", skillsList);
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
            ArrayList skillsList = skills.getSkillsByName(search, next, count);
            int cnt = 0;
            if(skillsList.size() > 0)
            {
                SkillsInfo cinfo = (SkillsInfo) skillsList.get(skillsList.size() - 1);
                cnt = cinfo.getSkillsId();
                skillsList.remove(skillsList.size() - 1);
            }
            request.getSession().setAttribute("SKILLS_LIST", skillsList);
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
            ArrayList skillsList = skills.getSkillsByName(search, 0, count);
            int cnt = 0;
            if(skillsList.size() > 0)
            {
                SkillsInfo cinfo = (SkillsInfo) skillsList.get(skillsList.size() - 1);
                cnt = cinfo.getSkillsId();
                skillsList.remove(skillsList.size() - 1);
            }
            request.getSession().setAttribute("SKILLS_LIST", skillsList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}