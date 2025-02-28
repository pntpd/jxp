package com.web.jxp.state;

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

public class StateAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        StateForm frm = (StateForm) form;
        State state = new State();
        Validate vobj = new Validate();
        Collection countrys = state.getCountry();
        frm.setCountrys(countrys);
        
        int count = state.getCount();
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
        int check_user = state.checkUserSession(request, 99, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = state.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "State Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setStateId(-1);
            saveToken(request);
            return mapping.findForward("add_state");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int stateId = frm.getStateId();
            frm.setStateId(stateId);
            StateInfo info = state.getStateDetailById(stateId);
            if(info != null)
            {    
                if(info.getStateName() != null)
                    frm.setStateName(info.getStateName());
                frm.setStatus(info.getStatus());
                frm.setCountryId(info.getCountryId());
            }
            saveToken(request);
            return mapping.findForward("add_state");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int stateId = frm.getStateId();
            frm.setStateId(stateId);
            StateInfo info = state.getStateDetailByIdforDetail(stateId);
            request.setAttribute("STATE_DETAIL", info);
            return mapping.findForward("view_state");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int stateId = frm.getStateId();
                String name = vobj.replacename(frm.getStateName());
                int status = 1;
                int countryId = frm.getCountryId();
                
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = state.getLocalIp();
                int ck = state.checkDuplicacy(stateId, name, countryId);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "State already exists");
                    return mapping.findForward("add_state");
                }
                StateInfo info = new StateInfo(stateId, name, countryId, status, uId);
                if(stateId <= 0)
                {
                    int cc = state.createState(info);
                    if(cc > 0)
                    {
                       state.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 99, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList stateList = state.getStateByName(search, 0, count);
                    int cnt = 0;
                    if(stateList.size() > 0)
                    {
                        StateInfo cinfo = (StateInfo) stateList.get(stateList.size() - 1);
                        cnt = cinfo.getStateId();
                        stateList.remove(stateList.size() - 1);
                    }
                    request.getSession().setAttribute("STATE_LIST", stateList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    state.updateState(info);
                    state.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 99, stateId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList stateList = state.getStateByName(search, next, count);
                    int cnt = 0;
                    if(stateList.size() > 0)
                    {
                        StateInfo cinfo = (StateInfo) stateList.get(stateList.size() - 1);
                        cnt = cinfo.getStateId();
                        stateList.remove(stateList.size() - 1);
                    }
                    request.getSession().setAttribute("STATE_LIST", stateList);
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
            ArrayList stateList = state.getStateByName(search, next, count);
            int cnt = 0;
            if(stateList.size() > 0)
            {
                StateInfo cinfo = (StateInfo) stateList.get(stateList.size() - 1);
                cnt = cinfo.getStateId();
                stateList.remove(stateList.size() - 1);
            }
            request.getSession().setAttribute("STATE_LIST", stateList);
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
            ArrayList stateList = state.getStateByName(search, 0, count);
            int cnt = 0;
            if(stateList.size() > 0)
            {
                StateInfo cinfo = (StateInfo) stateList.get(stateList.size() - 1);
                cnt = cinfo.getStateId();
                stateList.remove(stateList.size() - 1);
            }
            request.getSession().setAttribute("STATE_LIST", stateList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}