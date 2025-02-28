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
            String shortlistIds = request.getParameter("shortlistId") != null && !request.getParameter("shortlistId").equals("") ? vobj.replaceint(request.getParameter("shortlistId")) : "0";
            String sflags = request.getParameter("sflag") != null && !request.getParameter("sflag").equals("") ? vobj.replaceint(request.getParameter("sflag")) : "0";
            int shortlistId = Integer.parseInt(shortlistIds);
            int sflag = Integer.parseInt(sflags);

            String file_path = clientselection.getMainPath("view_resumetemplate_pdf");
            String mailfile_path = clientselection.getMainPath("file_maillog");
            String weburl = clientselection.getMainPath("web_path");
            ClientselectionInfo info = clientselection.setEmailDetail(shortlistId, sflag, 0);
            int clientId = 0, assetId = 0;
            if(info != null)
            {
                clientId = info.getClientId();
                assetId = info.getClientassetId();
            }

            String subject = "", mailbody = "", ext = "/jxp/clientlogin/";
            if (sflag < 3) {
                mailbody = "Dear Client, \n \n As per the Job Posting for (" + info.getPosition() + ") and other requirements,\n\n we have shortlisted this candidate for you. Kindly go through their CV and assess them as per requirements.\n\nLog in with your registered email address to complete the process: "+weburl+ext+"\n\nThank you,\n Team OCS. \n Generated from: JouneryXPro - Crew Management System";
                subject = "Candidate CV against Job Posting #" + clientselection.changeNum(info.getJobpostId(), 6);
            }
            if (sflag >= 3)
            {
                subject = info.getSubject();
                String mbody = clientselection.readHTMLFile(info.getMailfileName(), mailfile_path);

                int v1 = 0;
                int v2 = mbody.length();
                if (mbody.indexOf("<br/><br/>") != -1) {
                    v1 = mbody.indexOf("<br/><br/>");
                }
                if (mbody.indexOf("<br/><br/><br/></table>") != -1) {
                    v2 = mbody.indexOf("<br/><br/><br/></table>");
                }
                mbody = mbody.substring(v1 + 10, v2).trim();
                mailbody = mbody.replaceAll("<br/>", "\n");
            }
            String toval = "";
            if(!info.getMa_email().equals(""))
            {
                toval = info.getMa_email() != null ? info.getMa_email(): "";
            }
//            if(!info.getClientmailId().equals(""))
//            {
//                toval += ","+info.getClientmailId() != null ? info.getClientmailId(): "";
//            }
            StringBuilder sb = new StringBuilder();
            sb.append("<h2>EMAIL CV TO CLIENT</h2>");
            sb.append("<input type='hidden' name='assetId' value='"+assetId+"'>");
            sb.append("<div class='row client_position_table'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>From</label>");
            sb.append("<input name='fromval' class='form-control' placeholder='' value = 'journeyxpro@journeyxpro.com' readonly>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>To</label>");
            sb.append("<input name='toval' class='form-control' placeholder='' value = '" + toval + "'>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>CC</label>");
            sb.append("<input name='ccval' class='form-control' placeholder='' value = '" + info.getComailId() + "'>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>BCC</label>");
            sb.append("<input  name='bccval' class='form-control' placeholder='' value = '" + info.getBccmailId() + "'>");
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
            sb.append("<a href=\"javascript: ;\" onclick=\"javascript: submitmailForm('" + info.getCandidateName().replaceAll("'", "") + "','" + info.getPdffileName() + "','" + info.getClientname() + "','" + shortlistId + "');\" class='save_page'>Send &nbsp;&nbsp;<img src='../assets/images/share-as-email.png'></a>");
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