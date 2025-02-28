<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.knowledgecontent.KnowledgecontentInfo" %>
<jsp:useBean id="knowledgecontent" class="com.web.jxp.knowledgecontent.Knowledgecontent" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
try
{
    if(session.getAttribute("LOGININFO") != null)
    {         
        if(request.getParameter("courseattachmentId") != null)
        {
            String courseattachmentIds = request.getParameter("courseattachmentId") != null ? vobj.replaceint(request.getParameter("courseattachmentId")) : "0";
            int courseattachmentId = Integer.parseInt(courseattachmentIds); 
            if(courseattachmentId > 0)
            {
                int cc = knowledgecontent.delcourseattachment(courseattachmentId);
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