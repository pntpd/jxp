<%@page contentType="text/html"%>
<%@page language="java" import="java.util.*"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="user" class="com.web.jxp.user.User" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String str = "";
            if (request.getParameter("clientId") != null) 
            {
                String clientIds = request.getParameter("clientId") != null && !request.getParameter("clientId").equals("") ? vobj.replaceint(request.getParameter("clientId")) : "0";
                int clientId = Integer.parseInt(clientIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                StringBuffer sb = new StringBuffer();
                if (clientId > 0) 
                {
                    Collection coll = user.getClientLocations(clientId);
                    if (coll.size() > 0)
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext()) 
                        {
                            UserInfo info = (UserInfo) iter.next();
                            int val = info.getUserId();
                            String name = info.getName() != null ? info.getName() : "";
                            sb.append("<option value='" + val + "'>" + name + "</option>");
                        }
                    }
                    coll.clear();
                } else {
                    sb.append("<option value='-1'>- Select -</option>");
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
        e.printStackTrace();
        response.getWriter().write("Something went wrong.");
    }
%>