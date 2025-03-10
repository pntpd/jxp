<%@page import="org.json.JSONObject"%>
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
    JSONObject jo = new JSONObject();
    String otpIds = "", otpCode = "", emailId = "";
    if (request.getParameter("otpId") != null && request.getParameter("otpCode") != null && request.getParameter("emailId") != null) {
        otpIds = (String) request.getParameter("otpId");
        otpCode = (String) request.getParameter("otpCode");
        emailId = (String) request.getParameter("emailId");
        int otpId = Integer.parseInt(otpIds);
        if (!otpCode.equals("") && !emailId.equals("")) {
            boolean bval = crewlogin.checkOTPByAPI(emailId, otpCode, otpId);
            if (bval) {
                jo.put("status", "success");
                jo.put("message", "Verified");
                jo.put("url", "/jxp/feedback/feedback_welcome.jsp");
            } else {
                jo.put("status", "error");
                jo.put("message", "Invalid OTP.");
            }
        } else {
            jo.put("status", "error");
            jo.put("message", "Invalid OTP.");
            session.removeAttribute("CREWLOGIN");

        }
    } else {
        jo.put("status", "error");
        jo.put("message", "Please check your email Id and OTP.");
    }
    response.getWriter().write(jo.toString());
%>