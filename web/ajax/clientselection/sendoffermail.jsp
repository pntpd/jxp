<%@page import="org.apache.struts.upload.FormFile"%>
<%@page import="java.util.Date"%>
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
            String attachfile = request.getParameter("attachfile") != null && !request.getParameter("attachfile").equals("") ? request.getParameter("attachfile") : "";
            String isattacheds = request.getParameter("isattached") != null && !request.getParameter("isattached").equals("") ? vobj.replaceint(request.getParameter("isattached")) : "0";
            String fromDate = request.getParameter("fromDate") != null && !request.getParameter("fromDate").equals("") ? vobj.replacedate(request.getParameter("fromDate")) : "";
            String toDate = request.getParameter("toDate") != null && !request.getParameter("toDate").equals("") ? vobj.replacedate(request.getParameter("toDate")) : "";
            String clientId_s = request.getParameter("clientId") != null && !request.getParameter("clientId").equals("") ? vobj.replaceint(request.getParameter("clientId")) : "0";
            String assetId_s = request.getParameter("assetId") != null && !request.getParameter("assetId").equals("") ? vobj.replaceint(request.getParameter("assetId")) : "0";
            String types = request.getParameter("type") != null && !request.getParameter("type").equals("") ? vobj.replaceint(request.getParameter("type")) : "0";
            int shortlistId = Integer.parseInt(shortlistIds);
            int isattached = Integer.parseInt(isattacheds);
            int assetId = Integer.parseInt(assetId_s);
            int clientId = Integer.parseInt(clientId_s);
            int type = Integer.parseInt(types);
            
            String add_resumetemplate_pdf = clientselection.getMainPath("add_resumetemplate_pdf");
            String foldername = clientselection.createFolder(add_resumetemplate_pdf);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            if(isattached <= 0)
            {
                filename = clientselection.saveImage(attachfile, add_resumetemplate_pdf, foldername, fn);
                clientselection.updateShortlistofferPdffile(shortlistId,filename,uid, fromDate, toDate);
            }
            int maillogId =  clientselection.sendofferMail(fromval, toval,ccval,bccval,subject,description,candidatename,filename,clientname,shortlistId,username, uid, clientId, assetId);
            clientselection.insertclientselectionhistory(  shortlistId,4, companytype,maillogId,uid,3);
            clientselection.updateoflagformail(shortlistId, uid, username, type);
            String usertype = clientselection.getusertype(companytype);
            
            response.getWriter().write(usertype);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>