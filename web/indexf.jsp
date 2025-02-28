<%@page import="java.util.Stack"%>
<%@page import="java.util.ArrayList"%>
<%@page language="java" %>
<%@page import="com.web.jxp.base.*" %>
<jsp:useBean id="base" class="com.web.jxp.base.Base" scope="page"/>
<html>
<head>
    <title><%= base.getMainPath("title") != null ? base.getMainPath("title") : "" %></title>
</head>
<body>
<%
    try
    {
        Stack<String> list = new Stack<String>();
        if(session.getAttribute("BACKURL") != null)
            list = (Stack<String>) session.getAttribute("BACKURL");
        String str = ""; 
        try
        {
            list.pop();
            str = (String) list.peek();
        }
        catch(Exception e)
        {
            str = "/jxp/dashboard/DashboardAction.do?doDashboard=yes";
        }
        session.setAttribute("BACKURL", list);        
        if(str != null && str.equals(""))
            str = "/jxp/dashboard/DashboardAction.do?doDashboard=yes";
        response.sendRedirect(str+"?ctp=1");
    }
    catch(Exception e)
    {
        out.println("error : " + e);
    }
%>
</body>
</html>
