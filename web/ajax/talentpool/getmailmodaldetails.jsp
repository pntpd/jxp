<%@page import="com.web.jxp.clientselection.ClientselectionInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.compliancecheck.CompliancecheckInfo"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="clientselection" class="com.web.jxp.clientselection.Clientselection" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "0";
            String clientNames = request.getParameter("clientName") != null && !request.getParameter("clientName").equals("") ? vobj.replacename(request.getParameter("clientName")) : "";
            int candidateId = Integer.parseInt(candidateIds);

            String file_path = clientselection.getMainPath("view_resumetemplate_pdf");
            ClientselectionInfo info = clientselection.setTalentpoolEmailDetail(candidateId);

            String subject = "", mailbody = "";

            mailbody = "Greetings! \n \n As per the Client Template for " + info.getPosition() + " and other requirements, we have generated the this CV."
                    + " \n \n Thank you, \n Team OCS. \n Generated from: JourneyxPro-Crew Management System";
            subject = "Candidate CV for " + clientNames;

            StringBuilder sb = new StringBuilder();
            sb.append("<h2>EMAIL CV TO CLIENT</h2>");
            sb.append("<div class='row client_position_table'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>From</label>");
            sb.append("<input name='fromval' class='form-control' placeholder='' value = 'journeyxpro@journeyxpro.com' readonly>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>To</label>");
            sb.append("<input name='toval' class='form-control' placeholder='' value = '' maxlength='200'/>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>CC</label>");
            sb.append("<input name='ccval' class='form-control' placeholder='' value = '' maxlength='200'/>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>BCC</label>");
            sb.append("<input  name='bccval' class='form-control' placeholder='' value = '' maxlength='200'/>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>Email Subject</label>");
            sb.append("<input name='subject' class='form-control' placeholder='' value='" + subject + "' readonly>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>Email Body</label>");
            sb.append("<textarea name='description'  class='form-control' maxlength='2000' style = 'height : 180px'/>");
            sb.append(mailbody);
            sb.append("</textarea>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>CV Generation Details</label>");
            sb.append("<div class='full_width veri_details'>");
            sb.append("<ul>");
            sb.append("<li><a href=\"" + file_path + info.getPdffileName() + "\" target = '_blank'>View Attachment</a></li>");
            sb.append("</ul>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'  id='sendmaildiv'>");
            sb.append("<a href=\"javascript: ;\" onclick=\"javascript: submitmailForm('" + info.getCandidateName().replaceAll("'", "") + "','" + info.getPdffileName() + "','" + clientNames + "','" + info.getCandidateId() + "');\" class='save_page'>Send &nbsp;&nbsp;<img src='../assets/images/share-as-email.png'></a>");
            sb.append("</div>");
            sb.append("</div>");
            String st1 = sb.toString() + "#@#" + "";
            sb.setLength(0);
            response.getWriter().write(st1);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>