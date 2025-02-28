<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.dashboard.DashboardInfo" %>
<jsp:useBean id="dashboard" class="com.web.jxp.dashboard.Dashboard" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String str = "";
            if (request.getParameter("crewrotationId") != null) 
            {
                String date = request.getParameter("date") != null && !request.getParameter("date").equals("") ? vobj.replacedesc(request.getParameter("date")) : "";
                String crewrotationIds = request.getParameter("crewrotationId") != null && !request.getParameter("crewrotationId").equals("") ? vobj.replaceint(request.getParameter("crewrotationId")) : "";
                int crewrotationId = Integer.parseInt(crewrotationIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                DashboardInfo info = dashboard.getStandBydate(date, crewrotationId);
                StringBuffer sb = new StringBuffer();
                if (!date.equals("0000:00:00"))
                {
                    sb.append("<div class='col-lg-12'>");
                    sb.append("<h2>"+info.getName()+"</h2>");
                    sb.append("<div class='row client_position_table1'>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                    sb.append("<label class='form_label'>Type:</label>");
                    sb.append("<span class='form-control'>"+(!info.getSubtypevalue().equals("") ? info.getSubtypevalue() : "&nbsp;" )+"</span>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                    sb.append("<label class='form_label'>Remarks</label>");
                    sb.append("<span class='form-control'>");
                    sb.append(!info.getRemarks().equals("") ? info.getRemarks() : "&nbsp;" );
                    sb.append("</span>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                } else {
                    sb.append("No date available.");
                }
                str = sb.toString();
                sb.setLength(0);
                response.getWriter().write(str);
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } catch (Exception e) {
        e.getMessage();
    }
%>