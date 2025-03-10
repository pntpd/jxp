<%@page import="org.json.JSONObject"%>
<%@page contentType="text/html"%>
<%@page language="java" %>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo" %>
<%@page import="com.web.jxp.base.StatsInfo" %>
<jsp:useBean id="crewlogin" class="com.web.jxp.crewlogin.Crewlogin" scope="page"/>
<%
    response.setContentType("text/html");
    response.setHeader("Cache-Control", "no-cache");
    JSONObject jo = new JSONObject();
    if (request.getParameter("emailId") != null) {
        String emailId = request.getParameter("emailId") != null ? request.getParameter("emailId") : "";
        if (emailId != null && !emailId.equals("")) {
            CrewloginInfo info = crewlogin.getCandidateInfo(emailId);
            int candidateId = 0;
            if (info != null) {
                candidateId = info.getCandidateId();
            }
            if (candidateId > 0) {
                String otp = crewlogin.generateotp();
                String date = crewlogin.getDateAfter(crewlogin.currDate1(), 5, 5, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                int otpId = crewlogin.insertOTP(emailId, otp.replaceAll("\\s", ""), date);
                if (otpId > 0) {
                    session.removeAttribute("CREWLOGIN");
                    session.setAttribute("CREWLOGIN", info);
                    session.setAttribute("WELCOMECRL", (info.getName() != null ? info.getName() : ""));
                }
                String messageBody = crewlogin.getOTPMessage(otp, "otpcrewlogin.html");
                String file_maillog = crewlogin.getMainPath("file_maillog");
                java.util.Date nowmail = new java.util.Date();
                String fn_mail = "otp-" + String.valueOf(nowmail.getTime()) + ".html";
                String filePath = crewlogin.createFolder(file_maillog);
                String fname = crewlogin.writeHTMLFile(messageBody, file_maillog + "/" + filePath, fn_mail);
                String receipent[] = new String[1];
                receipent[0] = emailId;
                String from = "";
                String subject = "JourneyXPro Sign In OTP";
                String cc[] = new String[0];
                String bcc[] = new String[0];
                try {
                    StatsInfo sinfo = crewlogin.postMailAttach(receipent, cc, bcc, messageBody, subject, "", "", -1);
                    int flag = 0;
                    if (sinfo != null) {
                        flag = sinfo.getDdlValue();
                        from = sinfo.getDdlLabel();
                    }
                    crewlogin.createMailLog(12, "", emailId, "", "", from, subject, filePath + "/" + fname, "", 0, "", 0);
                    if (flag <= 0) {
                        jo.put("status", "error");
                        jo.put("message", "Something went wrong.");
                    } else {
                        jo.put("status", "success");
                        jo.put("message", "OTP Sent");
                        jo.put("crewName", (info.getName() != null ? info.getName() : ""));
                        jo.put("crId", (info.getCrewrotationId()));
                        jo.put("otpId", otpId);
                    }
                } catch (Exception e) {
                    jo.put("status", "error");
                    jo.put("message", e.getMessage());
                }
            } else {
                jo.put("status", "error");
                jo.put("message", "Email Id not exist in our database.");
            }
        } else {
            jo.put("status", "error");
            jo.put("message", "OTP cannot be sent. Please check your email Id or contact your Administrator.");
        }
    } else {
        jo.put("status", "error");
        jo.put("message", "OTP cannot be sent. Please check your email Id or contact your Administrator.");
    }
    response.getWriter().write(jo.toString());
%>