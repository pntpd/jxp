<%@page contentType="text/html"%>
<%@page language="java" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="user" class="com.web.jxp.user.User" scope="page"/>
<%
    response.setContentType("text/plain");
    response.setHeader("Cache-Control", "no-cache");
    int userId = 0, tp = 0;
    UserInfo uInfo = null;
    if (session.getAttribute("LOGININFO") != null)
    {
        uInfo = (UserInfo) session.getAttribute("LOGININFO");
        if(uInfo != null)
            userId = uInfo.getUserId();
    }
    if(userId > 0)
    {
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String str = "", ipAddrStr = "";
        UserInfo info = user.getPassword(userId);
        String iplocal = user.getLocalIp();
        ipAddrStr = request.getRemoteAddr();
        if(ipAddrStr.equals("0:0:0:0:0:0:0:1"))
            ipAddrStr  = iplocal;
        if(info != null)
        {
            if(oldPassword.equals(info.getPassword()))
            { 
                int cc = user.updatePassword(newPassword,userId);
                if(cc > 0)
                {
                    
                    session.setAttribute("LOGININFO", uInfo);
                    str = "S";
                }
                else
                    str = ("N");
            }
            else
            {
                str = ("N");
            }
        }
        else
        {
            str = ("N");
        }
        response.getWriter().write(str);
   }
   else
   {
        response.getWriter().write("Please check your login session....");
   }
%>