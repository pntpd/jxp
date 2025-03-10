<%@page import="com.web.jxp.clientselection.ClientselectionInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.compliancecheck.CompliancecheckInfo"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="clientselection" class="com.web.jxp.clientselection.Clientselection" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try {
        if (session.getAttribute("LOGININFO") != null) {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String username = "";
            if (uinfo != null) {
                username = uinfo.getName() != null ? uinfo.getName() : "";
            }
            String shortlistIds = request.getParameter("shortlistId") != null && !request.getParameter("shortlistId").equals("") ? vobj.replaceint(request.getParameter("shortlistId")) : "0";
            String sflags = request.getParameter("sflag") != null && !request.getParameter("sflag").equals("") ? vobj.replaceint(request.getParameter("sflag")) : "0";
            String oflags = request.getParameter("oflag") != null && !request.getParameter("oflag").equals("") ? vobj.replaceint(request.getParameter("oflag")) : "0";
            int shortlistId = Integer.parseInt(shortlistIds);
            int sflag = Integer.parseInt(sflags);
            int oflag = Integer.parseInt(oflags);

            String subject = "", mailbody = "", name = "", position = "", clientname = "",
                    fromDate = "", toDate = "";
            int clientId = 0, assetId = 0;
            String mailfile_path = clientselection.getMainPath("file_maillog");
            String web_path = clientselection.getMainPath("web_path");
            ClientselectionInfo info = clientselection.setEmailDetailoffer(shortlistId, sflag, oflag);
            if (info != null) {
                name = info.getCandidateName() != null ? info.getCandidateName() : "";
                position = info.getPosition() != null ? info.getPosition() : "";
                clientname = info.getClientname() != null ? info.getClientname() : "";
                fromDate = info.getFromDate() != null ? info.getFromDate() : "";
                toDate = info.getToDate() != null ? info.getToDate() : "";
                assetId = info.getClientassetId();
            }
            if (oflag < 3) {
                mailbody = "Dear " + name + ",\n\n"
                        + "We are pleased to inform you that you have been selected for the position of " + position + " at " + clientname + "." + " Your offer letter is attached to this email.\n\n"
                        + "Details:\n"
                        + "Position: " + position + "\n"
                        + "Start Date: " + fromDate + "\n"
                        + "Please review the offer letter, sign it, and upload the signed document on the JourneyXPro portal using the link below. Log in with your registered email address to complete the process:\n"
                        + web_path + "/jxp/crewlogin/" + "\n\n"
                        + "Congratulations once again, and we look forward to welcoming you to our team!";

                subject = "Congratulations!You Have Been Selected - Offer Letter Attached";
            }
            if (oflag >= 3) {
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

            StringBuilder sb = new StringBuilder();

            sb.append("<h2>Email Offer Letter to Candidate</h2>");
            sb.append("<input type='hidden' name='fromDate' value='" + fromDate + "'>");
            sb.append("<input type='hidden' name='toDate' value='" + toDate + "'>");
            sb.append("<input type='hidden' name='assetId' value='" + assetId + "'>");
            sb.append("<div class='row client_position_table'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>From</label>");
            sb.append("<input name='fromval' class='form-control' placeholder='' value = 'journeyxpro@journeyxpro.com' readonly>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>To</label>");
            if (oflag >= 3) {
                sb.append("<input name='toval' class='form-control' placeholder='' value = '" + info.getClientmailId() + "'>");
            } else if (oflag < 3) {
                sb.append("<input name='toval' class='form-control' placeholder='' value = '" + info.getCandidatemail() + "'>");
            }
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
            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-12 col-md-6 col-sm-12 col-12 form_group'>");
            sb.append("<label class='form_label'>Offer Letter Upload</label>");
            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-6'>");
            sb.append("<input id='upload1' name='attachfile' type='file' onchange=\"javascript: setofferfilebase();\" />");
            sb.append("<a href=\"javascript:;\" id='upload_link_1' class='attache_btn attache_btn_white uploaded_img1'>");
            sb.append("<i class='fas fa-paperclip'></i> Attach</a>");
            sb.append("</div>");
            sb.append("<div class='col-lg-6'>");
            sb.append("<div class='full_width off_letter'>");
            sb.append("<div class='form-check permission-check'>");
            sb.append("<input class='form-check-input' type='checkbox' name='isattached' value='1'>");
            sb.append("<span class=''>No Attachment</span>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");

            sb.append("</div>");
            sb.append("</div>");
            sb.append("<div class='row'>");
            sb.append("<input type='hidden' name='offerpdfId' id='offerpdfId' value=''/>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'  id='sendmaildiv'>");
            sb.append("<a href=\"javascript: ;\" onclick=\"javascript: submitmailofferForm('" + info.getCandidateName().replaceAll("'", "") + "','" + info.getPdffileName() + "','" + info.getClientname() + "','" + shortlistId + "');\" class='save_page'>Send &nbsp;&nbsp;<img src='../assets/images/share-as-email.png'></a>");
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