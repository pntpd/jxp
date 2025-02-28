<%@page contentType="text/html"%>
<%@page language="java" %>
<%@page import="com.web.jxp.home.HomeInfo" %>
<%@page import="com.web.jxp.base.StatsInfo" %>
<jsp:useBean id="home" class="com.web.jxp.home.Home" scope="page"/>
<%
    response.setContentType("text/html");
    response.setHeader("Cache-Control", "no-cache");
    if (request.getParameter("emailId") != null)
    {
        String emailId = request.getParameter("emailId") != null ? request.getParameter("emailId") : "";
        String cap = request.getParameter("cap") != null ? request.getParameter("cap") : "";
        String cval = "";
        if (request.getSession().getAttribute("CVAL2") != null) 
        {
            cval = (String) request.getSession().getAttribute("CVAL2");
            session.removeAttribute("CVAL2");
        }
        if (!cval.equals("") && !cap.equals("") && home.validateCaptcha(cap, cval))  
        {
            String otp = home.generateotp();
            String date = home.getDateAfter(home.currDate1(),5,5,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm:ss");
             
            if(emailId != null && !emailId.equals(""))
            {   
                int otpId =  home.insertOTP(emailId,otp.replaceAll("\\s", ""),date);
                   if(otpId > 0){
                        session.setAttribute("HOME_EMAILID", emailId);
                    }
                String str = "S1";
                String messageBody = home.getOTPMessage( otp, "otp.html");
                String file_maillog = home.getMainPath("file_maillog");
                java.util.Date nowmail = new java.util.Date();
                String fn_mail = "otp-"+String.valueOf(nowmail.getTime())+".html";
                String filePath = home.createFolder(file_maillog);
                String fname = home.writeHTMLFile(messageBody, file_maillog+"/"+filePath, fn_mail);
                String receipent[] = new String[1];
                receipent[0] = emailId;
                String from = "";
                String subject = "OCS Sign In OTP";
                String cc[] = new String[0];
                String bcc[] = new String[0];
                try
                {
                    StatsInfo sinfo = home.postMailAttach(receipent, cc, bcc, messageBody, subject, "", "", -1);
                    int flag = 0;
                    if(sinfo != null)
                    {
                        flag = sinfo.getDdlValue();
                        from = sinfo.getDdlLabel();
                    }
                    home.createMailLog(8, "", emailId, "", "", from, subject, filePath+"/"+fname,"",0,"",0);
                    //int flag = 1;
                    if(flag <= 0)
                    {
                        response.getWriter().write("NOMAIL");
                    }
                    response.getWriter().write(str);
                }
                catch(Exception e)
                {
                    response.getWriter().write("E");
                }
            }
            else
            {
                response.getWriter().write("S2");
            }
        }
        else
        {
            response.getWriter().write("INV");
        }
   }
   else
   {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
   }
%>