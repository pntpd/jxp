<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.candidate.CandidateInfo" %>
<jsp:useBean id="candidate" class="com.web.jxp.candidate.Candidate" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
try
{
    if(session.getAttribute("HOME_EMAILID") != null)
    {
              
        if(request.getParameter("childId") != null)
        {
            String type_s = request.getParameter("type") != null ? vobj.replaceint(request.getParameter("type")) : "0";
            String childId_s = request.getParameter("childId") != null ? vobj.replaceint(request.getParameter("childId")) : "0";
            int type = Integer.parseInt(type_s);
            int childId = Integer.parseInt(childId_s); 
            if(childId > 0)
            {
                int cc = candidate.updatefile(childId,type);
                if(cc > 0) 
                    response.getWriter().write("Yes");
                else
                    response.getWriter().write("No");
                session.removeAttribute("FILENAME");
                session.removeAttribute("FILENAME1");
            }
            else
            {
                response.getWriter().write("Something went wrong.");
            }
        }
        else
        {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
    else
    {
        response.getWriter().write("Please check your login session....");
    }
}
catch(Exception e)
{    
    e.printStackTrace();
}
%>