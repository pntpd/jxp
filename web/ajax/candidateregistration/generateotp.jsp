<%@page import="com.web.jxp.candidateregistration.CandidateRegistrationInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" %>
<%@page import="com.web.jxp.home.HomeInfo" %>
<%@page import="com.web.jxp.base.StatsInfo" %>
<jsp:useBean id="cr" class="com.web.jxp.candidateregistration.CandidateRegistration" scope="page"/>
<%
    response.setContentType("text/html");
    response.setHeader("Cache-Control", "no-cache");
    if (request.getParameter("emailId") != null) {
        String emailId = request.getParameter("emailId") != null ? request.getParameter("emailId") : "";
        String cap = request.getParameter("cap") != null ? request.getParameter("cap") : "";
        String cval = "";
        if (request.getSession().getAttribute("CVAL") != null) {
            cval = (String) request.getSession().getAttribute("CVAL");
            session.removeAttribute("CVAL");
        }
        if (!cval.equals("") && !cap.equals("") && cr.validateCaptcha(cap, cval)) {
            String otp = cr.generateotp();
            String date = cr.getDateAfter(cr.currDate1(), 5, 5, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
            if (emailId != null && !emailId.equals("")) {
                String str = "";
                CandidateRegistrationInfo info = cr.getCandidateId(emailId);
                if (info != null) {
                    if (info.getCandidateId() > 0 && info.getDraftflag() <= 0) {
                        str = "CTA";
                    }
                }
                if (!str.equals("CTA")) {
                    int otpId = cr.insertOTP(emailId, otp.replaceAll("\\s", ""), date);
                    if (otpId > 0) {
                        session.setAttribute("REG_EMAILID", emailId);
                    }
                    str = "S1";
                    String messageBody = cr.getOTPMessage(otp, "otp.html");
                    String file_maillog = cr.getMainPath("file_maillog");
                    java.util.Date nowmail = new java.util.Date();
                    String fn_mail = "otp-" + String.valueOf(nowmail.getTime()) + ".html";
                    String filePath = cr.createFolder(file_maillog);
                    String fname = cr.writeHTMLFile(messageBody, file_maillog + "/" + filePath, fn_mail);
                    String receipent[] = new String[1];
                    receipent[0] = emailId;
                    String from = "";
                    String subject = "OCS Sign In OTP";
                    String cc[] = new String[0];
                    String bcc[] = new String[0];
                    try {
                        StatsInfo sinfo = cr.postMailAttach(receipent, cc, bcc, messageBody, subject, "", "", -1);
                        int flag = 0;
                        if (sinfo != null) {
                            flag = sinfo.getDdlValue();
                            from = sinfo.getDdlLabel();
                        }
                        cr.createMailLog(8, "", emailId, "", "", from, subject, filePath + "/" + fname, "", 0, "", 0);
                        //int flag = 1;
                        if (flag <= 0) {
                            response.getWriter().write("NOMAIL");
                        }
                    } catch (Exception e) {
                        response.getWriter().write("E");
                    }
                }
                response.getWriter().write(str);
            } else {
                response.getWriter().write("S2");
            }
        } else {
            response.getWriter().write("INV");
        }
    } else {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
%>