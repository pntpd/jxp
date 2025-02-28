<%@page contentType="text/html"%>
<%@page language="java" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.base.StatsInfo" %>
<jsp:useBean id="user" class="com.web.jxp.user.User" scope="page"/>
<%
    response.setContentType("text/html");
    response.setHeader("Cache-Control", "no-cache");
    if (request.getParameter("Username") != null)
    {
        String loginId = request.getParameter("Username") != null ? request.getParameter("Username") : "";
        String cap = request.getParameter("cap") != null ? request.getParameter("cap") : "";
        String cval = "";
        if (request.getSession().getAttribute("CVAL1") != null) 
        {
            cval = (String) request.getSession().getAttribute("CVAL1");
            session.removeAttribute("CVAL1");
        }
        if (!cval.equals("") && !cap.equals("") && user.validateCaptcha(cap, cval))  
        {
            UserInfo info = user.getLoginById(loginId);
            if(info != null && info.getEmail() != null && !info.getEmail().equals(""))
            {   
                int userId = info.getUserId();
                String password = user.getRandomPassword(8);
                user.updateNewPassword(userId, password, 1);
                String email = info.getEmail();
                String name = info != null && info.getName() != null ? info.getName() : "";
                String loginid = info != null && info.getUserName() != null ? info.getUserName() : "";
                
                String str = "S1";
                String messageBody = user.getForgotMessage(name, loginid, password, "forgot.html");
                String file_maillog = user.getMainPath("file_maillog");
                java.util.Date nowmail = new java.util.Date();
                String fn_mail = "fp-"+String.valueOf(nowmail.getTime())+".html";
                String filePath = user.createFolder(file_maillog);
                String fname = user.writeHTMLFile(messageBody, file_maillog+"/"+filePath, fn_mail);
                String receipent[] = new String[1];
                receipent[0] = email;
                String from = "";
                String subject = user.getMainPath("subject_forgot");
                String cc[] = new String[0];
                String bcc[] = new String[0];
                try
                {
                    StatsInfo sinfo = user.postMailAttach(receipent, cc, bcc, messageBody, subject, "", "", -1);
                    int flag = 0;
                    if(sinfo != null)
                    {
                        flag = sinfo.getDdlValue();
                        from = sinfo.getDdlLabel();
                    }
                    user.createMailLog(2, name, email, "", "", from, subject, filePath+"/"+fname,"",0,name,0);
                    response.getWriter().write(str);
                }
                catch(Exception e)
                {
                    response.getWriter().write("E");
                }
                session.removeAttribute("LOGININFO");
                session.removeAttribute("WELCOME");
                session.removeAttribute("PERMISSION_LIST");
            }
            else
            {
                response.getWriter().write("S2");
                session.removeAttribute("LOGININFO");
                session.removeAttribute("WELCOME");
                session.removeAttribute("PERMISSION_LIST");
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