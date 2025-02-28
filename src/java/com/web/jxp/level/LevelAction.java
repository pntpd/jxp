package com.web.jxp.level;

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

public class LevelAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LevelForm frm = (LevelForm) form;
        Level level = new Level();
        Validate vobj = new Validate();
        int count = level.getCount();
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
        int check_user = level.checkUserSession(request, 63, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = level.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Level Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setLevelId(-1);
            saveToken(request);
            return mapping.findForward("add_level");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int levelId = frm.getLevelId();
            frm.setLevelId(levelId);
            LevelInfo info = level.getLevelDetailById(levelId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_level");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int levelId = frm.getLevelId();
            frm.setLevelId(levelId);
            LevelInfo info = level.getLevelDetailByIdforDetail(levelId);
            request.setAttribute("LEVEL_DETAIL", info);
            return mapping.findForward("view_level");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int levelId = frm.getLevelId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = level.getLocalIp();
                int ck = level.checkDuplicacy(levelId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Level already exists");
                    return mapping.findForward("add_level");
                }
                LevelInfo info = new LevelInfo(levelId, name, status, uId);
                if(levelId <= 0)
                {
                    int cc = level.createLevel(info);
                    if(cc > 0)
                    {
                       level.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 63, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                      
                    }
                    ArrayList levelList = level.getLevelByName(search, 0, count);
                    int cnt = 0;
                    if(levelList.size() > 0)
                    {
                        LevelInfo cinfo = (LevelInfo) levelList.get(levelList.size() - 1);
                        cnt = cinfo.getLevelId();
                        levelList.remove(levelList.size() - 1);
                    }
                    request.getSession().setAttribute("LEVEL_LIST", levelList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    level.updateLevel(info);
                    level.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 63, levelId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList levelList = level.getLevelByName(search, next, count);
                    int cnt = 0;
                    if(levelList.size() > 0)
                    {
                        LevelInfo cinfo = (LevelInfo) levelList.get(levelList.size() - 1);
                        cnt = cinfo.getLevelId();
                        levelList.remove(levelList.size() - 1);
                    }
                    request.getSession().setAttribute("LEVEL_LIST", levelList);
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
            ArrayList levelList = level.getLevelByName(search, next, count);
            int cnt = 0;
            if(levelList.size() > 0)
            {
                LevelInfo cinfo = (LevelInfo) levelList.get(levelList.size() - 1);
                cnt = cinfo.getLevelId();
                levelList.remove(levelList.size() - 1);
            }
            request.getSession().setAttribute("LEVEL_LIST", levelList);
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
            
            ArrayList levelList = level.getLevelByName(search, 0, count);
            int cnt = 0;
            if(levelList.size() > 0)
            {
                LevelInfo cinfo = (LevelInfo) levelList.get(levelList.size() - 1);
                cnt = cinfo.getLevelId();
                levelList.remove(levelList.size() - 1);
            }
            request.getSession().setAttribute("LEVEL_LIST", levelList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}