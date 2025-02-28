<%@page contentType="text/html"%>
<%@page language="java" %>
<jsp:useBean id="base" class="com.web.jxp.base.Base" scope="page"/>
<%
    response.setContentType("text/plain");
    response.setHeader("Cache-Control", "no-cache");
    if (request.getParameter("type") != null) {
        String cval = base.generateCaptcha();
        session.setAttribute("CVAL", cval);
        response.getWriter().write(cval);
    } else {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
%>
