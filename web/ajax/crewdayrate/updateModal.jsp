<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.crewdayrate.CrewdayrateInfo" %>
<jsp:useBean id="crewdayrate" class="com.web.jxp.crewdayrate.Crewdayrate" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            int uId = 0 ;
            if (uinfo != null) 
            {
                uId = uinfo.getUserId();
            }            
            if (request.getParameter("positionId") != null) 
            {
                StringBuilder sb = new StringBuilder();
                String positionIds = request.getParameter("positionId") != null ? vobj.replaceint(request.getParameter("positionId")) : "0";
                String clientassetIds = request.getParameter("clientassetId") != null ? vobj.replaceint(request.getParameter("clientassetId")) : "0";
                String prate_s = request.getParameter("prate") != null ? vobj.replacedouble(request.getParameter("prate")) : "0.0";
                int positionId = Integer.parseInt(positionIds);
                int clientassetId = Integer.parseInt(clientassetIds);
                double prate = Double.parseDouble(prate_s);
                if (positionId > 0)
                {
                    int cc = crewdayrate.createPositionRate(clientassetId, uId, positionId, prate);
                    String st1 = sb.toString()+"#@#"+cc;
                    sb.setLength(0);
                    response.getWriter().write(st1);
                } else {
                    response.getWriter().write("Something went wrong.");
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>