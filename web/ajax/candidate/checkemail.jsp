<%@page contentType="text/html"%>
<%@page language="java" import="java.util.Base64"%>
<%@page import="com.web.jxp.candidate.*"%>
<jsp:useBean id="candidate" class="com.web.jxp.candidate.Candidate" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        if(request.getParameter("email") != null && request.getParameter("candidateId") != null)
        {
            String email = (String)request.getParameter("email") != null ? vobj.replacedesc(request.getParameter("email")) : "";
            String candidateId_s = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "0";
            int candidateId = Integer.parseInt(candidateId_s);
            String output = candidate.checkEmail(candidateId, email);
            response.getWriter().write(output);
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