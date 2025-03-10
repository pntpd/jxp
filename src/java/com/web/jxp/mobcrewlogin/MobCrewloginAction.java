package com.web.jxp.mobcrewlogin;

import com.web.jxp.base.StatsInfo;
import com.web.jxp.crewlogin.Crewlogin;
import com.web.jxp.crewlogin.CrewloginInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class MobCrewloginAction extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Crewlogin crewlogin = new Crewlogin();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        String requestBody = stringBuilder.toString();
        JSONObject jsonRequest = new JSONObject(requestBody);
        String action = jsonRequest.optString("action", "");
        String email = jsonRequest.optString("email", "");
        JSONObject jo = new JSONObject();

        if (action != null && !action.equals("")) {
            if (action.equals("login") || action.equals("resentotp")) {
                if (email != null && !email.equals("")) {
                    CrewloginInfo info = crewlogin.getCandidateInfo(email);
                    int candidateId = 0;
                    if (info != null) {
                        candidateId = info.getCandidateId();
                    }
                    if (candidateId > 0) {
                        String otp = crewlogin.generateotp();
                        String date = crewlogin.getDateAfter(crewlogin.currDate1(), 5, 5, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                        int otpId = crewlogin.insertOTP(email, otp.replaceAll("\\s", ""), date);
                        if (otpId > 0) {
                            request.getSession().removeAttribute("CREWLOGIN");
                            request.getSession().setAttribute("CREWLOGIN", info);
                            request.getSession().setAttribute("WELCOMECRL", (info.getName() != null ? info.getName() : ""));
                        }
                        String messageBody = crewlogin.getOTPMessage(otp, "otpcrewlogin.html");
                        String file_maillog = crewlogin.getMainPath("file_maillog");
                        java.util.Date nowmail = new java.util.Date();
                        String fn_mail = "otp-" + String.valueOf(nowmail.getTime()) + ".html";
                        String filePath = crewlogin.createFolder(file_maillog);
                        String fname = crewlogin.writeHTMLFile(messageBody, file_maillog + "/" + filePath, fn_mail);
                        String receipent[] = new String[1];
                        receipent[0] = email;
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
                            crewlogin.createMailLog(12, "", email, "", "", from, subject, filePath + "/" + fname, "", 0, "", 0);
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
            } else if (action.equals("verification")) {
                String otpIds = jsonRequest.optString("otpId", "");
                String otpCode = jsonRequest.optString("otp", "");
                if (otpIds != null && otpCode != null && email != null) {
                    int otpId = Integer.parseInt(otpIds);
                    if (!otpCode.equals("") && !email.equals("")) {
                        boolean bval = crewlogin.checkOTPByAPI(email, otpCode, otpId);
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
                        request.getSession().removeAttribute("CREWLOGIN");
                    }
                } else {
                    jo.put("status", "error");
                    jo.put("message", "Please check your email Id and OTP.");
                }
            } else {
                jo.put("status", "error");
                jo.put("message", "Something went wrong.");
            }
        } else {
            jo.put("status", "error");
            jo.put("message", "Something went wrong.");
        }
        PrintWriter out = response.getWriter();
        out.print(jo.toString());
        out.flush();
    }
}
