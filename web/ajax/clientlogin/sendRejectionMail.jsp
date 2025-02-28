<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.clientlogin.ClientloginInfo"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="clientlogin" class="com.web.jxp.clientlogin.Clientlogin" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("MLOGININFO") != null) 
        {
            int uid = 0;
            ClientloginInfo uinfo = ((ClientloginInfo) request.getSession().getAttribute("MLOGININFO"));
            String username = "";
            if (uinfo != null) 
            {
                uid = uinfo.getUserId();
                username = uinfo.getName() != null ? uinfo.getName(): "";
            }
            String fromval = request.getParameter("fromval") != null && !request.getParameter("fromval").equals("") ? vobj.replacedesc(request.getParameter("fromval")) : "";
            String toval = request.getParameter("toval") != null && !request.getParameter("toval").equals("") ? vobj.replacedesc(request.getParameter("toval")) : "";
            String ccval = request.getParameter("ccval") != null && !request.getParameter("ccval").equals("") ? vobj.replacedesc(request.getParameter("ccval")) : "";
            String bccval = request.getParameter("bccval") != null && !request.getParameter("bccval").equals("") ? vobj.replacedesc(request.getParameter("bccval")) : "";
            String subject = request.getParameter("subject") != null && !request.getParameter("subject").equals("") ? vobj.replacedesc(request.getParameter("subject")) : "";
            String description = request.getParameter("description") != null && !request.getParameter("description").equals("") ? vobj.replacedesc(request.getParameter("description")) : "";
            String shortlistIds = request.getParameter("shortlistId") != null && !request.getParameter("shortlistId").equals("") ? vobj.replaceint(request.getParameter("shortlistId")) : "0";
            String jobpostIds = request.getParameter("jobpostId") != null && !request.getParameter("jobpostId").equals("") ? vobj.replaceint(request.getParameter("jobpostId")) : "0";
            int shortlistId = Integer.parseInt(shortlistIds);
            int jobpostId = Integer.parseInt(jobpostIds);
            
            int maillogId =  clientlogin.sendRejectionMail(fromval, toval, ccval, bccval, subject, description, username, uid, jobpostId, shortlistId);
            String str = "no";
            if(maillogId > 0)
            {                
                clientlogin.rejectionByClient(shortlistId, username, uid, maillogId);
                if(request.getSession().getAttribute("THANKYOU_3") != null)
                {
                    str = (String)request.getSession().getAttribute("THANKYOU_3");
                    request.removeAttribute("THANKYOU_3");
                }                    
            }
            response.getWriter().write(str);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>