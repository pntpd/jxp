<%@page contentType="text/html"%>
<%@page language="java" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.io.File"%>
<%@page import="java.net.InetAddress" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.candidateregistration.CandidateRegistrationInfo" %>
<jsp:useBean id="cr" class="com.web.jxp.candidateregistration.CandidateRegistration" scope="page"/>
<%
    session.removeAttribute("BACKURL");
    response.setContentType("text/plain");
    response.setHeader("Cache-Control", "no-cache");
    if (request.getParameter("otp") != null) {
        String otp = request.getParameter("otp");
        String emailId = "";
        if (session.getAttribute("REG_EMAILID") != null) {
            emailId = (String) session.getAttribute("REG_EMAILID");
        }
        String str = "";
        if (!otp.equals("") && !emailId.equals("")) {
            int otpId = cr.checkOTP(emailId, otp);
            if (otpId > 0) {
                str = "";
                CandidateRegistrationInfo info = cr.getCandidateId(emailId);
                if (info != null) {
                    if (info.getCandidateId() > 0) {
//                        if (info.getOnlineflag() == 2 && info.getDraftflag() == 1) {
                        if (info.getDraftflag() == 1) {
                            str = "EXISTING";
                            session.setAttribute("REG_CANDIDATEID", info.getCandidateId() + "");
                        } else  {
                            str = "CTA";
                            session.removeAttribute("REG_EMAILID");
                        }
                    }
                } else {
                    //new candidate
                    str = "NEW";
                }
            } else {
                str = "INVALIDOTP";
            }
        } else {
            str = "INVALIDOTP";
            session.removeAttribute("REG_EMAILID");

        }
        response.getWriter().write(str);
    } else {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
%>