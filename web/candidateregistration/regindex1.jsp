<%@page language="java" %>
<%@page import="com.web.jxp.base.*" %>
<jsp:useBean id="base" class="com.web.jxp.base.Base" scope="page"/>
<html>
    <head>
        <title>OCS - Candidate Registration Form</title>
    </head>
    <body>
        <%
            try {
                String sessionExp = base.getMainPath("session_expire");
                session.setAttribute("SESSIONEXP", sessionExp);
                response.sendRedirect("/jxp/candidateregistration/level1.jsp");
            } catch (Exception e) {
                out.println("error : " + e);
            }
        %>
    </body>
</html>
