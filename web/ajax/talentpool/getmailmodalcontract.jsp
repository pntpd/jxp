<%@page import="com.web.jxp.talentpool.TalentpoolInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="ddl" class="com.web.jxp.talentpool.Ddl" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            
            String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "0";
            String filename = request.getParameter("filename") != null && !request.getParameter("filename").equals("") ? vobj.replacedesc(request.getParameter("filename")) : "0";
            int candidateId = Integer.parseInt(candidateIds);
            
            String file_path = ddl.getMainPath("view_candidate_file");  
            TalentpoolInfo info = ddl.setContractEmailDetail(candidateId);

            String subject = "", mailbody = "";
            mailbody = "Dear Candidate, \n \n Please find your employment contract for the " + info.getPosition() + " position at " + info.getClientName() +"-"+ info.getAssetName()+". \n Please review the document carefully, sign where indicated, and return a scanned copy to us at your earliest convenience. \n Alternatively, you can also upload the signed copy directly through your Crew application.";
            subject = "Employment Contract - Action Required";

            StringBuilder sb = new StringBuilder();
            sb.append("<h2>EMAIL TO CREW</h2>");
            sb.append("<div class='row client_position_table'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>From</label>");
            sb.append("<input name='fromval' class='form-control' placeholder='' value = 'journeyxpro@journeyxpro.com' readonly>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>To</label>");
            sb.append("<input name='toval' class='form-control' placeholder='' value = '"+info.getEmailId()+"' maxlength='200'/>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>CC</label>");
            sb.append("<input name='ccval' class='form-control' placeholder='' value = '"+info.getCcaddress()+"' maxlength='200'/>");
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
            sb.append("<label class='form_label'>Attchment Details</label>");
            sb.append("<div class='full_width veri_details'>");
            sb.append("<ul>");
            sb.append("<li><a href=\"" + file_path + filename+ "\" target = '_blank'>View Attachment</a></li>");
            sb.append("</ul>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            
            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'  id='sendmaildiv'>");
            sb.append("<a href=\"javascript: ;\" onclick=\"javascript: sendContractMail('" + info.getName().replaceAll("'", "") +"','" + info.getCandidateId() + "');\" class='save_page'>Send &nbsp;&nbsp;<img src='../assets/images/share-as-email.png'></a>");
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