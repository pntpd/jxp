package com.web.jxp.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LogoutAction extends Action
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
        HttpServletRequest request, HttpServletResponse response) 
        throws Exception
    {        
        User user = new User();
        int userId = 0;
        if (request.getSession().getAttribute("LOGININFO") != null)
        {
            UserInfo uinfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if(uinfo != null)
                userId = uinfo.getUserId();
        }
        String ipAddress = request.getRemoteAddr();
        String iplocal = user.getLocalIp();
        if(userId > 0)
            user.createHistoryAccess(null, userId, ipAddress, iplocal, "Logout", -1, userId);
        request.getSession(false).invalidate();        
        System.gc();
        String message = user.getMainPath("logout.desc");
        request.getSession().setAttribute("LOGOUTMESSAGE", message);
        return mapping.findForward("success");
    }
}
