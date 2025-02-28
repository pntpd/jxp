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
    if(session.getAttribute("LOGININFO") != null)
    {  
        if(request.getParameter("fileId") != null)
        {
            String fileIds = request.getParameter("fileId") != null ? vobj.replaceint(request.getParameter("fileId")) : "0";
            int fileId = Integer.parseInt(fileIds); 
            if(fileId > 0)
            {
                int cc = candidate.deldocumentfiles(fileId);
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