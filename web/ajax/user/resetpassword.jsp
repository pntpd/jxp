<%@page contentType="text/html"%>
<%@page language="java" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.net.InetAddress" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.base.StatsInfo" %>
<jsp:useBean id="user" class="com.web.jxp.user.User" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        response.setContentType("text/plain");
        response.setHeader("Cache-Control", "no-cache");
        if (request.getParameter("userId") != null)
        {
            String userIds = request.getParameter("userId") != null && !request.getParameter("userId").equals("") ? vobj.replaceint(request.getParameter("userId")) : "-1";
            String name = request.getParameter("name") != null ? vobj.replacedesc(request.getParameter("name")) : "";
            String email = request.getParameter("email") != null ? vobj.replacedesc(request.getParameter("email")) : "";
            int userId = Integer.parseInt(userIds);
            String cc[] = new String[0];
            String bcc[] = new String[0];
            String to[] = new String[1];
            to[0] = email;
            String password = user.getRandomPassword(8);
            String mailBody = user.getForgotMessage(name, email, password, "newpassword.html");
            String file_maillog = user.getMainPath("file_maillog");
            java.util.Date nowmail = new java.util.Date();
            String fn_mail = "np-"+String.valueOf(nowmail.getTime());
            String filePath = user.createFolder(file_maillog);
            String fname = user.writeHTMLFile(mailBody, file_maillog+"/"+filePath, fn_mail);
            StatsInfo sinfo = user.postMailAttach(to, cc, bcc, mailBody, "JourneyXPro: Login Credentials", "", "", -1);
            int flag = 0;
            String from = "";
            if(sinfo != null)
            {
                flag = sinfo.getDdlValue();
                from = sinfo.getDdlLabel();
            }
            user.createMailLog(3, name, email, "", "", from, "JourneyXPro: Login Credentials", filePath+"/"+fname,"",0,name, 0);
            if(flag > 0)
            {
                flag = 0;
                flag = user.updateNewPassword(userId, password, 1);
            }
            String str = "";
            if(flag > 0)
                str = "Password reset successfully and an email also been sent to the user.";
            else
                str = "Password reset unsuccessfully, please try again later.";
            response.getWriter().write(str);
       }
       else
       {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
       }
    }catch(Exception e)
    {
        e.printStackTrace();
    }
%>