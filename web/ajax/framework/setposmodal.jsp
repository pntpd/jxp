<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.framework.FrameworkInfo" %>
<jsp:useBean id="framework" class="com.web.jxp.framework.Framework" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        if (uInfo != null) 
        {
            int userId = uInfo.getUserId();
            if (userId > 0) 
            {
                String pids = request.getParameter("pids") != null ? vobj.replacedesc(request.getParameter("pids")) : "";
                String s = "";                
                StringBuilder sb = new StringBuilder();
                if (!pids.equals("")) 
                {
                    ArrayList list = framework.getPositionlistfordept(pids); 
                    int size = list.size();
                    for (int i = 0; i < size; i++) 
                    {
                        FrameworkInfo info = (FrameworkInfo) list.get(i);
                        if (info != null) 
                        {
                            sb.append("<tr>");
                            sb.append("<td>" + (info.getPositionName() != null ? info.getPositionName() : "") + "</td>");
                            sb.append("<td>" + (info.getGradeName() != null ? info.getGradeName() : "") + "</td>");
                            sb.append("</tr>");
                        }
                    }
                    list.clear();
                }
                s = sb.toString();
                sb.setLength(0);                
                response.getWriter().write(s);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>