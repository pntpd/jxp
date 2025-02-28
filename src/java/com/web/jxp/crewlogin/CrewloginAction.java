package com.web.jxp.crewlogin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CrewloginAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
        if (request.getSession().getAttribute("CREWLOGIN") != null) 
        {
             if (request.getSession().getAttribute("CREWLOGIN") != null) 
             {
                 String email = (String)request.getSession().getAttribute("CREWLOGIN");
             }
        }
        return mapping.findForward("homedefault");
    }
}