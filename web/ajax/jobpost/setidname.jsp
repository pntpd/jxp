<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.position.PositionInfo" %>  
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<jsp:useBean id="position" class="com.web.jxp.position.Position" scope="page"/>
<%
    try
    {
        if(session.getAttribute("LOGININFO") != null)
        {
            if(request.getParameter("assessmentId") != null )
            {
                String assessmentIds = request.getParameter("assessmentId") != null && !request.getParameter("assessmentId").equals("") ? vobj.replaceint(request.getParameter("assessmentId")) : "";
                
                int assessmentId = Integer.parseInt(assessmentIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");   
                String s = "";
                if(assessmentId > 0 )
                {
                    s = position.getAssessmentById(assessmentId);                     
                }
                response.getWriter().write(s);
            }
            else
            {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
        else
        {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
    catch(Exception e)
    {        
        
    }
%>