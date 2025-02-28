<%@page contentType="text/html"%>
<%@page language="java" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.io.File"%>
<%@page import="java.net.InetAddress" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.home.HomeInfo" %>
<jsp:useBean id="home" class="com.web.jxp.home.Home" scope="page"/>
<%
    session.removeAttribute("BACKURL");
    response.setContentType("text/plain");
    response.setHeader("Cache-Control", "no-cache");    
    if (request.getParameter("otp") != null)
    {
        String otp = request.getParameter("otp"); 
        String emailId = "";
        if(session.getAttribute("HOME_EMAILID") != null)
            emailId = (String)session.getAttribute("HOME_EMAILID");
        String str = "";
        if (!otp.equals("") && !emailId.equals(""))  
        {
            int otpId = home.checkOTP(emailId, otp);
            if(otpId > 0)
            {
                str = "";
                HomeInfo info = home.getCandidateId(emailId);
                if(info != null)
                {
                    if(info.getCandidateId() > 0)
                    {
                        if(info.getOnlineflag() == 2)
                        {
                            str = "EXISTING";
                            session.setAttribute("HOME_CANDIDATEID", info.getCandidateId()+"");
                        }
                        else if(info.getOnlineflag() <= 1)
                        {
                            str = "CTA";
                            session.removeAttribute("HOME_EMAILID");
                        }
                    }
                }
                else
                {
                    //new candidate
                    str = "NEW";
                }
            }
            else
            {
                str = "INVALIDOTP";
            }
        }
        else
        {
            str = "INVALIDOTP";
            session.removeAttribute("HOME_EMAILID");

        }
        response.getWriter().write(str);
   }
   else
   {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
   }
%>