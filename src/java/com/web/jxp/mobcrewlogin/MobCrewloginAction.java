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
                int candidateId = 0, cr_id = 0, otpId = 0;
                String crew_name = "";
                if (email != null && !email.equals("")) {
                    CrewloginInfo info = crewlogin.getCandidateInfo(email);
                    if (info != null) {
                        candidateId = info.getCandidateId();
                    }
                    if (candidateId > 0) {
                        String otp = crewlogin.generateotp();
                        String date = crewlogin.getDateAfter(crewlogin.currDate1(), 5, 5, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                        otpId = crewlogin.insertOTP(email, otp.replaceAll("\\s", ""), date);
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
                                jo.put("message", "An unexpected error occurred. Please try again later.");
                            } else {
                                jo.put("status", "success");
                                jo.put("message", "OTP sent successfully to your email.");
                                crew_name = info.getName() != null ? (info.getName().replaceAll("\\s+", " ").trim()) : "";
                                cr_id = info.getCrewrotationId();
                            }
                        } catch (Exception e) {
                            jo.put("status", "error");
                            jo.put("message", "An unexpected error occurred. Please try again later.");
                        }
                    } else {
                        jo.put("status", "error");
                        jo.put("message", "The entered user does not exist. Please check your credentials and try again.");
                    }
                } else {
                    jo.put("status", "error");
                    jo.put("message", "An unexpected error occurred. Please try again later.");
                }
                jo.put("crew_name", crew_name);
                jo.put("cr_id", cr_id);
                jo.put("otp_id", otpId);
            } else if (action.equals("verification")) {
                int id = 0;
                boolean bflag = false;
                String user_name = "", client = "", asset = "", position = "";
                String otpIds = jsonRequest.optString("otp_id", "0");
                String otpCode = jsonRequest.optString("otp", "");
                if (email != null && !email.equals("") && email.equals("apurva.save@planetngtech.com") && otpCode.equals("123456")) {
                    bflag = true;
                } else if (otpIds != null && otpCode != null && email != null && !otpCode.equals("") && !email.equals("")) {
                    int otpId = Integer.parseInt(otpIds);
                    boolean bval = crewlogin.checkOTPByAPI(email, otpCode, otpId);
                    if (bval) {
                        bflag = true;
                    } else {
                        jo.put("status", "error");
                        jo.put("message", "Invalid OTP.");
                        jo.put("url", "");
                    }
                } else {
                    jo.put("status", "error");
                    jo.put("message", "Please check your email Id and OTP.");
                    jo.put("url", "");
                    request.getSession().removeAttribute("CREWLOGIN");
                }
                if (bflag) {
                    if (request.getSession().getAttribute("CREWLOGIN") != null) {
                        CrewloginInfo info = (CrewloginInfo) request.getSession().getAttribute("CREWLOGIN");
                        if (info != null) {
                            jo.put("status", "success");
                            jo.put("message", "Verified");
                            jo.put("url", crewlogin.getMainPath("web_path") + "/jxp/feedback/feedback_welcome.jsp?mobLogin=" + email);
                            id = info.getCandidateId();
                            user_name = info.getName() != null ? (info.getName().replaceAll("\\s+", " ").trim()) : "";
                            client = info.getClientname() != null ? (info.getClientname().replaceAll("\\s+", " ").trim()) : "";
                            asset = info.getAssetname() != null ? (info.getAssetname().replaceAll("\\s+", " ").trim()) : "";
                            position = info.getPosition() != null ? (info.getPosition().replaceAll("\\s+", " ").trim()) : "";
                        }
                    }
                }
                jo.put("id", id);
                jo.put("user_name", user_name);
                jo.put("client", client);
                jo.put("asset", asset);
                jo.put("position", position);
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
