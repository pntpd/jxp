<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.billingcycle.BillingcycleInfo" %>
<jsp:useBean id="wellnessfb" class="com.web.jxp.billingcycle.Billingcycle" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null)
        {
            if (request.getParameter("schedulevalue") != null) 
            {
                StringBuilder str = new StringBuilder();
                String schedulevalues = request.getParameter("schedulevalue") != null && !request.getParameter("schedulevalue").equals("") ? vobj.replacenamenum(request.getParameter("schedulevalue")) : "0";
                
                str.append("<li><a href=\"javascript: checkWeek('1');\"");
                str.append("class='");
                if(schedulevalues.contains("1"))
                {
                    str.append("week_selected ");
                }
                str.append("'>S</a></li>");
                str.append("<li><a href=\"javascript: checkWeek('2');\"");
                str.append("class='");
                if(schedulevalues.contains("2"))
                {
                    str.append("week_selected ");
                }
                str.append("'>M</a></li>");
                str.append("<li><a href=\"javascript: checkWeek('3');\"");
                str.append("class='");
                if(schedulevalues.contains("3"))
                {
                    str.append("week_selected ");
                }
                str.append("'>T</a></li>");
                str.append("<li><a href=\"javascript: checkWeek('4');\"");
                str.append("class='");
                if(schedulevalues.contains("4"))
                {
                    str.append("week_selected ");
                }
                str.append("'>W</a></li>");
                str.append("<li><a href=\"javascript: checkWeek('5');\"");
                str.append("class='");
                if(schedulevalues.contains("5"))
                {
                    str.append("week_selected ");
                }
                str.append("'>T</a></li>");
                str.append("<li><a href=\"javascript: checkWeek('6');\" ");
                str.append("class='");
                if(schedulevalues.contains("6"))
                {
                    str.append("week_selected ");
                }
                str.append("'>F</a></li>");
                str.append("<li><a href=\"javascript: checkWeek('7');\"");
                str.append("class='");
                if(schedulevalues.contains("7"))
                {
                    str.append("week_selected ");
                }
                str.append("'>S</a></li>");
                
                String st = str.toString();
                str.setLength(0);
                response.getWriter().write(st);
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