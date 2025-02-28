<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.candidate.CandidateInfo" %>
<jsp:useBean id="client" class="com.web.jxp.candidate.Candidate" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
try
{
    if(session.getAttribute("HOME_EMAILID") != null)
    {
        if(request.getParameter("clientassetpicId") != null)
        {
            String clientassetpicId_s = request.getParameter("clientassetpicId") != null ? vobj.replaceint(request.getParameter("clientassetpicId")) : "0";
            int clientassetpicId = Integer.parseInt(clientassetpicId_s); 
            if(clientassetpicId > 0)
            {
                int cc = client.delpic(clientassetpicId);
                if(cc > 0) 
                    response.getWriter().write("Yes");
                else
                    response.getWriter().write("No");
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