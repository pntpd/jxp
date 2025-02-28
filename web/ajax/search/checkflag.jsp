<%@page import="org.json.JSONObject"%>
<%@page contentType="text/html"%>
<jsp:useBean id="obj" class="com.web.jxp.candidate.Autofill" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        if(request.getSession().getAttribute("LOGININFO") != null)
        {
            String candidateId_s = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "0";
            int candidateId = Integer.parseInt(candidateId_s);            
            String s = obj.getFlag(candidateId);
            response.getWriter().write(s);
        }
        else {
            response.getWriter().write("Please check your login session....");
        }
    }catch(Exception e)
    {
        e.printStackTrace();
    }
%>