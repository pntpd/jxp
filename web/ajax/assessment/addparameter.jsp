<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.assessment.AssessmentInfo" %>
<%@page import="com.web.jxp.assessmentparameter.AssessmentParameterInfo" %>
<%@page import="com.web.jxp.client.ClientInfo" %>  
<jsp:useBean id="assessmentparameter" class="com.web.jxp.assessmentparameter.AssessmentParameter" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        if(uInfo != null)
        {    
            int userId = uInfo.getUserId();
            if(userId > 0)
            {
                String name = request.getParameter("name") != null ? vobj.replacename(request.getParameter("name")) : "";
                String description = request.getParameter("description") != null ?  vobj.replacedesc(request.getParameter("description")) : ""; 
                String s = "";
                AssessmentParameterInfo info = new AssessmentParameterInfo( name,description);
                assessmentparameter.createAssessmentParameter(info);             
                StringBuffer sb = new StringBuffer();
                s = sb.toString();
                sb.setLength(0);
                response.getWriter().write(s);
            }
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
%>