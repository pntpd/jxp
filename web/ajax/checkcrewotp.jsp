<%@page contentType="text/html"%>
<%@page language="java" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.io.File"%>
<%@page import="java.net.InetAddress" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo" %>
<jsp:useBean id="crewlogin" class="com.web.jxp.crewlogin.Crewlogin" scope="page"/>
<%
    session.removeAttribute("BACKURL");
    response.setContentType("text/plain");
    response.setHeader("Cache-Control", "no-cache");
    if (request.getParameter("otp") != null)
    {
        String otp = request.getParameter("otp"); 
        String emailId = "";
        int crewrotationId = 0;
        if(session.getAttribute("CREWLOGIN") != null)
        {
            CrewloginInfo info = (CrewloginInfo)session.getAttribute("CREWLOGIN");
            if(info != null)
            {
                emailId = info.getEmailId() != null ? info.getEmailId() : ""; 
                crewrotationId = info.getCrewrotationId();
            }
        }
        String str = "";
        if (!otp.equals("") && !emailId.equals(""))  
        {
            int otpId = crewlogin.checkOTP(emailId, otp);
            if(otpId > 0)
            {
                str = "Yes";
                if(session.getAttribute("SURVEYID") != null)
                {
                    int crId = Integer.parseInt((String) session.getAttribute("CRID"));
                    if(crId == crewrotationId)
                        str = "DIRECTFEEDBACK";
                    else
                        str = "Yes";
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
            session.removeAttribute("CREWLOGIN");

        }
        response.getWriter().write(str);
   }
   else
   {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
   }
%>