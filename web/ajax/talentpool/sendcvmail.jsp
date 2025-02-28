<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="mail" class="com.web.jxp.talentpool.Mail" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) {
            int companytype = 0;
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String username = "";
            if (uinfo != null)
            {
                username = uinfo.getName() != null ? uinfo.getName() : "";
                companytype = uinfo.getCompanytype();
            }
            String fromval = request.getParameter("fromval") != null && !request.getParameter("fromval").equals("") ? vobj.replacedesc(request.getParameter("fromval")) : "";
            String toval = request.getParameter("toval") != null && !request.getParameter("toval").equals("") ? vobj.replacedesc(request.getParameter("toval")) : "";
            String ccval = request.getParameter("ccval") != null && !request.getParameter("ccval").equals("") ? vobj.replacedesc(request.getParameter("ccval")) : "";
            String bccval = request.getParameter("bccval") != null && !request.getParameter("bccval").equals("") ? vobj.replacedesc(request.getParameter("bccval")) : "";
            String subject = request.getParameter("subject") != null && !request.getParameter("subject").equals("") ? vobj.replacedesc(request.getParameter("subject")) : "";
            String description = request.getParameter("description") != null && !request.getParameter("description").equals("") ? vobj.replacedesc(request.getParameter("description")) : "";
            String candidatename = request.getParameter("candidatename") != null && !request.getParameter("candidatename").equals("") ? vobj.replacedesc(request.getParameter("candidatename")) : "";
            String filename = request.getParameter("filename") != null && !request.getParameter("filename").equals("") ? vobj.replacedesc(request.getParameter("filename")) : "";
            String clientname = request.getParameter("clientname") != null && !request.getParameter("clientname").equals("") ? vobj.replacedesc(request.getParameter("clientname")) : "";
            String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "0";
            int candidateId = Integer.parseInt(candidateIds);

            int maillogId = mail.sendCVMail(fromval, toval, ccval, bccval, subject, description, candidatename, filename, clientname, candidateId, username);
            String usertype = mail.getusertype(companytype);
            response.getWriter().write(usertype);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>