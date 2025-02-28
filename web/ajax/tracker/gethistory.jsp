<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.tracker.TrackerInfo" %>
<jsp:useBean id="tracker" class="com.web.jxp.tracker.Tracker" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            if (request.getParameter("trackerId") != null)
            {
                String trackerId_s = request.getParameter("trackerId") != null && !request.getParameter("trackerId").equals("") ? vobj.replaceint(request.getParameter("trackerId")) : "0";
                int trackerId = Integer.parseInt(trackerId_s);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                StringBuilder sb = new StringBuilder();
                if (trackerId > 0) 
                {
                    ArrayList alist = tracker.getHistory(trackerId);
                    int alist_size = alist.size(); 
                    if(alist_size > 0)
                    {
                        for(int i = 0; i < alist_size; i++)
                        {
                            TrackerInfo ainfo = (TrackerInfo) alist.get(i);
                            if(ainfo != null)
                            {
                                sb.append("<tr>");
                                    sb.append("<td>"+(ainfo.getRole() != null ? ainfo.getRole() : "")+"</td>");
                                    sb.append("<td>"+(ainfo.getPassessmenttypeName() != null ? ainfo.getPassessmenttypeName() : "")+"</td>");
                                    sb.append("<td>"+(ainfo.getPriorityName() != null ? ainfo.getPriorityName() : "")+"</td>");
                                sb.append("</tr>"); 
                            }
                        }
                    }
                    else
                    {
                        sb.append("<tr><td colspan='3'><b>No records available.</b></td></tr>");
                    }
                }
                else 
                {
                    sb.append("No information available.");  
                }
                String str = sb.toString();
                sb.setLength(0);
                response.getWriter().write(str);
            } 
            else 
            {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } 
        else 
        {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } 
    catch (Exception e) 
    {
        e.printStackTrace();
    }
%>