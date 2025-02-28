<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.managewellness.ManagewellnessInfo" %>
<jsp:useBean id="managewellness" class="com.web.jxp.managewellness.Managewellness" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String assetIds = request.getParameter("assetId") != null && !request.getParameter("assetId").equals("") ? vobj.replaceint(request.getParameter("assetId")) : "0";
            String subcategoryIds = request.getParameter("subcategoryId") != null && !request.getParameter("subcategoryId").equals("") ? vobj.replaceint(request.getParameter("subcategoryId")) : "0";
            int assetId = Integer.parseInt(assetIds);
            int subcategoryId = Integer.parseInt(subcategoryIds);
            if(assetId > 0 && subcategoryId > 0)
            {
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");

                String str = managewellness.getqlist(assetId, subcategoryId);
                response.getWriter().write(str);
            }
            else 
            {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
    } 
    catch (Exception e)
    {
        e.printStackTrace();
    }
%>