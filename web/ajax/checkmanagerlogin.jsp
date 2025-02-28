<%@page import="com.web.jxp.clientlogin.ClientloginInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.io.File"%>
<%@page import="java.net.InetAddress" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="user" class="com.web.jxp.user.User" scope="page"/>
<jsp:useBean id="clientlogin" class="com.web.jxp.clientlogin.Clientlogin" scope="page"/>
<%
    session.removeAttribute("BACKURL");
    response.setContentType("text/plain");
    response.setHeader("Cache-Control", "no-cache");    
    if (request.getParameter("Username") != null)
    {
        String loginId = request.getParameter("Username"); 
        String password = request.getParameter("Password");
        String cap = request.getParameter("cap") != null ? request.getParameter("cap") : "";
        String cval = "";
        if (request.getSession().getAttribute("CVAL1") != null) 
        {
            cval = (String) request.getSession().getAttribute("CVAL1");
            session.removeAttribute("CVAL1");
        }
        String str = "";
        if (!cval.equals("") && !cap.equals("") && user.validateCaptcha(cap, cval))  
        {
            String ipAddrStr = "";
            String iplocal = user.getLocalIp();
            ipAddrStr = request.getRemoteAddr();
            if(ipAddrStr.equals("0:0:0:0:0:0:0:1"))
                ipAddrStr  = iplocal;
            int cc = 1;
            if(cc > 0)
            {
                ClientloginInfo info = clientlogin.getManagerAccess(loginId, password, ipAddrStr);
                if(info != null && info.getUserId() > 0)
                {                         
                    str = "S";
                    int userId = info.getUserId();
                    if(userId > 0)
                    {
                        user.createHistoryAccess(null, userId, ipAddrStr, iplocal, "Login", -1, userId);                        
                        session.setAttribute("MLOGININFO", info);
                    }
                }
                else
                {
                    str = "N";
                    session.removeAttribute("MLOGININFO");
                }
            }
        }
        else
        {
            str = "INV";
            session.removeAttribute("MLOGININFO");
        }
        response.getWriter().write(str);
   }
   else
   {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
   }
%>