package com.web.jxp.experiencewaterdepth;

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

public class ExperienceWaterDepthAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExperienceWaterDepthForm frm = (ExperienceWaterDepthForm) form;
        ExperienceWaterDepth experiencewaterdepth = new ExperienceWaterDepth();
        Validate vobj = new Validate();
        int count = experiencewaterdepth.getCount();
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
        int check_user = experiencewaterdepth.checkUserSession(request, 30, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = experiencewaterdepth.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "ExperienceWaterDepth Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setExperiencewaterdepthId(-1);
            saveToken(request);
            return mapping.findForward("add_experiencewaterdepth");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int experiencewaterdepthId = frm.getExperiencewaterdepthId();
            frm.setExperiencewaterdepthId(experiencewaterdepthId);
            ExperienceWaterDepthInfo info = experiencewaterdepth.getExperienceWaterDepthDetailById(experiencewaterdepthId);
            if(info != null)
            {
                frm.setUnitMeasurement(info.getUnitMeasurement());
                frm.setDepth(info.getDepth());
                
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_experiencewaterdepth");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int experiencewaterdepthId = frm.getExperiencewaterdepthId();
            frm.setExperiencewaterdepthId(experiencewaterdepthId);
            ExperienceWaterDepthInfo info = experiencewaterdepth.getExperienceWaterDepthDetailByIdforDetail(experiencewaterdepthId);
            request.setAttribute("EXPERIENCEWATERDEPTH_DETAIL", info);
            return mapping.findForward("view_experiencewaterdepth");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int experiencewaterdepthId = frm.getExperiencewaterdepthId();
                String unitMeasurement = vobj.replacename(frm.getUnitMeasurement());
                String depth = vobj.replacename(frm.getDepth());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = experiencewaterdepth.getLocalIp();
                int ck = experiencewaterdepth.checkDuplicacy(experiencewaterdepthId, unitMeasurement, depth);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Water Depth already exists");
                    return mapping.findForward("add_experiencewaterdepth");
                }
                ExperienceWaterDepthInfo info = new ExperienceWaterDepthInfo(experiencewaterdepthId, unitMeasurement, depth, status, uId);
                if(experiencewaterdepthId <= 0)
                {
                    int cc = experiencewaterdepth.createExperienceWaterDepth(info);
                    if(cc > 0)
                    {
                       experiencewaterdepth.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 30, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList experiencewaterdepthList = experiencewaterdepth.getExperienceWaterDepthByName(search, 0, count);
                    int cnt = 0;
                    if(experiencewaterdepthList.size() > 0)
                    {
                        ExperienceWaterDepthInfo cinfo = (ExperienceWaterDepthInfo) experiencewaterdepthList.get(experiencewaterdepthList.size() - 1);
                        cnt = cinfo.getExperiencewaterdepthId();
                        experiencewaterdepthList.remove(experiencewaterdepthList.size() - 1);
                    }
                    request.getSession().setAttribute("EXPERIENCEWATERDEPTH_LIST", experiencewaterdepthList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    experiencewaterdepth.updateExperienceWaterDepth(info);
                    experiencewaterdepth.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 30, experiencewaterdepthId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList experiencewaterdepthList = experiencewaterdepth.getExperienceWaterDepthByName(search, next, count);
                    int cnt = 0;
                    if(experiencewaterdepthList.size() > 0)
                    {
                        ExperienceWaterDepthInfo cinfo = (ExperienceWaterDepthInfo) experiencewaterdepthList.get(experiencewaterdepthList.size() - 1);
                        cnt = cinfo.getExperiencewaterdepthId();
                        experiencewaterdepthList.remove(experiencewaterdepthList.size() - 1);
                    }
                    request.getSession().setAttribute("EXPERIENCEWATERDEPTH_LIST", experiencewaterdepthList);
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
            ArrayList experiencewaterdepthList = experiencewaterdepth.getExperienceWaterDepthByName(search, next, count);
            int cnt = 0;
            if(experiencewaterdepthList.size() > 0)
            {
                ExperienceWaterDepthInfo cinfo = (ExperienceWaterDepthInfo) experiencewaterdepthList.get(experiencewaterdepthList.size() - 1);
                cnt = cinfo.getExperiencewaterdepthId();
                experiencewaterdepthList.remove(experiencewaterdepthList.size() - 1);
            }
            request.getSession().setAttribute("EXPERIENCEWATERDEPTH_LIST", experiencewaterdepthList);
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
            ArrayList experiencewaterdepthList = experiencewaterdepth.getExperienceWaterDepthByName(search, 0, count);
            int cnt = 0;
            if(experiencewaterdepthList.size() > 0)
            {
                ExperienceWaterDepthInfo cinfo = (ExperienceWaterDepthInfo) experiencewaterdepthList.get(experiencewaterdepthList.size() - 1);
                cnt = cinfo.getExperiencewaterdepthId();
                experiencewaterdepthList.remove(experiencewaterdepthList.size() - 1);
            }
            request.getSession().setAttribute("EXPERIENCEWATERDEPTH_LIST", experiencewaterdepthList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}