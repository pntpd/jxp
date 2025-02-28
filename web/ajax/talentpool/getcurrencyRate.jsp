<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<jsp:useBean id="ddl" class="com.web.jxp.talentpool.Ddl" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            if (request.getParameter("candidateId") != null) 
            {
                String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "0";
                String positionIds = request.getParameter("positionId") != null && !request.getParameter("positionId").equals("") ? vobj.replaceint(request.getParameter("positionId")) : "0";
                String clientAssetIds = request.getParameter("clientAssetId") != null && !request.getParameter("clientAssetId").equals("") ? vobj.replaceint(request.getParameter("clientAssetId")) : "0";
                int candidateId = Integer.parseInt(candidateIds);
                int positionId = Integer.parseInt(positionIds);
                int clientAssetId = Integer.parseInt(clientAssetIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                String data = ddl.getCandidatePositionsRate(candidateId, positionId, clientAssetId);                
                response.getWriter().write(data);
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } catch (Exception e) {
        e.printStackTrace();
        response.getWriter().write("Something went wrong.");
    }
%>