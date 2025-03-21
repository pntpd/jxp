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
            int uid = 0, companytype = 0;
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            if (uinfo != null) 
            {
                uid = uinfo.getUserId();               
                companytype = uinfo.getCompanytype();
            }
            String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "-1";
            String resumetempIds = request.getParameter("resumetempId") != null && !request.getParameter("resumetempId").equals("") ? vobj.replaceint(request.getParameter("resumetempId")) : "0";
            String shortlistIds = request.getParameter("shortlistId") != null && !request.getParameter("shortlistId").equals("") ? vobj.replaceint(request.getParameter("shortlistId")) : "0";
            String clientIds = request.getParameter("clientId") != null && !request.getParameter("clientId").equals("") ? vobj.replaceint(request.getParameter("clientId")) : "0";
            String cval1s = request.getParameter("cval1") != null && !request.getParameter("cval1").equals("") ? vobj.replacedesc(request.getParameter("cval1")) : "";
            String cval2s = request.getParameter("cval2") != null && !request.getParameter("cval2").equals("") ? vobj.replacedesc(request.getParameter("cval2")) : "";
            String cval3s = request.getParameter("cval3") != null && !request.getParameter("cval3").equals("") ? vobj.replacedesc(request.getParameter("cval3")) : "";
            String cval4s = request.getParameter("cval4") != null && !request.getParameter("cval4").equals("") ? vobj.replacedesc(request.getParameter("cval4")) : "";
            String cval5s = request.getParameter("cval5") != null && !request.getParameter("cval5").equals("") ? vobj.replacedesc(request.getParameter("cval5")) : "";
            String cval6s = request.getParameter("cval6") != null && !request.getParameter("cval6").equals("") ? vobj.replacedesc(request.getParameter("cval6")) : "";
            String cval7s = request.getParameter("cval7") != null && !request.getParameter("cval7").equals("") ? vobj.replacedesc(request.getParameter("cval7")) : "";
            String cval8s = request.getParameter("cval8") != null && !request.getParameter("cval8").equals("") ? vobj.replacedesc(request.getParameter("cval8")) : "";
            String cval9s = request.getParameter("cval9") != null && !request.getParameter("cval9").equals("") ? vobj.replacedesc(request.getParameter("cval9")) : "";
            String cval10s = request.getParameter("cval10") != null && !request.getParameter("cval10").equals("") ? vobj.replacedesc(request.getParameter("cval10")) : "";
            String validfrom = request.getParameter("validfrom") != null && !request.getParameter("validfrom").equals("") ? vobj.replacedesc(request.getParameter("validfrom")) : "";
            String validto = request.getParameter("validto") != null && !request.getParameter("validto").equals("") ? vobj.replacedesc(request.getParameter("validto")) : "";
            String contractor = request.getParameter("contractor") != null && !request.getParameter("contractor").equals("") ? vobj.replacedesc(request.getParameter("contractor")) : "";
            String edate = request.getParameter("edate") != null && !request.getParameter("edate").equals("") ? vobj.replacedesc(request.getParameter("edate")) : "";
            String dmname = request.getParameter("dmname") != null && !request.getParameter("dmname").equals("") ? vobj.replacedesc(request.getParameter("dmname")) : "";
            String designation = request.getParameter("designation") != null && !request.getParameter("designation").equals("") ? vobj.replacedesc(request.getParameter("designation")) : "";
            String comments = request.getParameter("comments") != null && !request.getParameter("comments").equals("") ? vobj.replacedesc(request.getParameter("comments")) : "";
            String jobpostIds = request.getParameter("jobpostId") != null && !request.getParameter("jobpostId").equals("") ? vobj.replaceint(request.getParameter("jobpostId")) : "0";
            String clientasset = request.getParameter("clientasset") != null && !request.getParameter("clientasset").equals("") ? vobj.replacename(request.getParameter("clientasset")) : "";
            String variablepays = request.getParameter("variablepay") != null && !request.getParameter("variablepay").equals("") ? vobj.replacedouble(request.getParameter("variablepay")) : "";
            
            int candidateId = Integer.parseInt(candidateIds);
            int jobpostId = Integer.parseInt(jobpostIds);
            int resumetempId = Integer.parseInt(resumetempIds);
            int shortlistId = Integer.parseInt(shortlistIds);
            int clientId = Integer.parseInt(clientIds);
            double variablepay = Double.parseDouble(variablepays);
            
            String pdffilename = clientselection.createResumeTeamplateFile(candidateId, resumetempId,cval1s,cval2s,cval3s,
                    cval4s, cval5s, cval6s, cval7s, cval8s, cval9s, cval10s, validfrom, validto, contractor,
                    edate,dmname,designation,comments,clientId,jobpostId, clientasset,variablepay);
            clientselection.updateShortlistofferPdffile(shortlistId,pdffilename,uid, validfrom, validto);
            clientselection.insertclientselectionhistory(  shortlistId, 4, companytype,0,  uid, 2);
            String filepath = clientselection.getMainPath("view_resumetemplate_pdf");           

            response.getWriter().write(filepath+pdffilename);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>