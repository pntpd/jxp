<%@page contentType="text/html"%>
<%@page language="java" %>
<%@page pageEncoding="UTF-8"%>
<jsp:useBean id="base" class="com.web.jxp.base.Base" scope="page"/>
<%
    try {
        String regemailId = (String) request.getSession().getAttribute("REG_EMAILID");
        if (regemailId != null) {
            session.removeAttribute("REG_EMAILID");
            response.getWriter().write("success");
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } catch (Exception e) {

    }
%>
