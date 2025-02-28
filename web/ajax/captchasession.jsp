<%@page contentType="text/html"%>
<%@page language="java" %>
<jsp:useBean id="base" class="com.web.jxp.base.Base" scope="page"/>
<%
    response.setContentType("text/plain");
    response.setHeader("Cache-Control", "no-cache");
    if (request.getParameter("type") != null)
    {
        String type = base.replacefordir(request.getParameter("type"));
        String cval = base.generateCaptcha();
        if(type.equals("1"))
        {
            session.setAttribute("CVAL1", cval);        
            response.getWriter().write(cval);
        }
        else if(type.equals("2"))
        {
            session.setAttribute("CVAL2", cval);        
            response.getWriter().write(cval);
        }
        else if(type.equals("3"))
        {
            session.setAttribute("CVAL3", cval);        
            response.getWriter().write(cval);
        }
    }
    else
    {
         response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
%>
