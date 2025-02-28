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
            if (request.getParameter("pcodeids") != null)
            {
                String pcodeids = request.getParameter("pcodeids") != null && !request.getParameter("pcodeids").equals("") ? vobj.replacedesc(request.getParameter("pcodeids")) : "";
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                StringBuilder sb = new StringBuilder();
                if (!pcodeids.equals("")) 
                {
                    sb.append("<thead>");
                        sb.append("<tr>");
                        sb.append("<th width='%'><span><b>Role Competencies</b> </span></th>");
                        sb.append("<th width='%'><span><b>Assessment Type</b></span></th>");
                        sb.append("<th width='%'><span><b>Priority</b></span></th>");
                        sb.append("</tr>");
                    sb.append("</thead>");
                    sb.append("<tbody>");
                    ArrayList alist = tracker.getMultipleRole(pcodeids);
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
                    sb.append("</tbody>");
                }
                else 
                {
                    sb.append("Something went wrong.");  
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