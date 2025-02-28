<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.clientselection.ClientselectionInfo"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="clientselection" class="com.web.jxp.clientselection.Clientselection" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            int uid = 0,companytype = 0;
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String username = "";
            if (uinfo != null) 
            {
                uid = uinfo.getUserId();
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
            String shortlistIds = request.getParameter("shortlistId") != null && !request.getParameter("shortlistId").equals("") ? vobj.replaceint(request.getParameter("shortlistId")) : "0";
            String clientId_s = request.getParameter("clientId") != null && !request.getParameter("clientId").equals("") ? vobj.replaceint(request.getParameter("clientId")) : "0";
            String assetId_s = request.getParameter("assetId") != null && !request.getParameter("assetId").equals("") ? vobj.replaceint(request.getParameter("assetId")) : "0";
            String jobpostId_s = request.getParameter("jobpostId") != null && !request.getParameter("jobpostId").equals("") ? vobj.replaceint(request.getParameter("jobpostId")) : "0";
            int jobpostId = Integer.parseInt(jobpostId_s);
            int assetId = Integer.parseInt(assetId_s);
            int clientId = Integer.parseInt(clientId_s);
            int shortlistId = Integer.parseInt(shortlistIds);
            
            int maillogId =  clientselection.sendCVMail(fromval, toval,ccval,bccval,subject,description,candidatename,
                    filename,clientname,shortlistId,username, uid, clientId, assetId, jobpostId);
            clientselection.insertclientselectionhistory(  shortlistId, 3, companytype,maillogId,  uid,0);
            clientselection.updatesflagformail(shortlistId, uid, username);
            String usertype = clientselection.getusertype(companytype);
            
            response.getWriter().write(usertype);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>