<%@page import="com.web.jxp.onboarding.OnboardingInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="onboarding" class="com.web.jxp.onboarding.Onboarding" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String shortlistIds = request.getParameter("shortlistId") != null && !request.getParameter("shortlistId").equals("") ? vobj.replaceint(request.getParameter("shortlistId")) : "0";
            String onboardflags = request.getParameter("onboardflag") != null && !request.getParameter("onboardflag").equals("") ? vobj.replaceint(request.getParameter("onboardflag")) : "0";
            int shortlistId = Integer.parseInt(shortlistIds);
            int onboardflag = Integer.parseInt(onboardflags);

            String mailfile_path = onboarding.getMainPath("file_maillog");
            OnboardingInfo info = onboarding.setEmailDetail(shortlistId, onboardflag, 0);

            String subject = "", mailbody = "";
            if (onboardflag < 5) {
                mailbody = "Dear Candidate, \n \n To proceed with your onboarding, kindly fill the following forms completely and reply to this email with the scanned files attached. Feel free to connect with us for any concern.\n \nThank you,\n Team OCS. \n Generated from: JouneryXPro - Crew Management System";
                subject = "Candidate Onboarding on #" + info.getClientAsset();
            }
            if (onboardflag >= 5) 
            {
                subject = info.getSubject();
                String mbody = onboarding.readHTMLFile(info.getMailfileName(), mailfile_path);

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
            sb.append("<h2>EMAIL JOINING FORMS TO CANDIDATE</h2>");
            sb.append("<div class='row client_position_table'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>From</label>");
            sb.append("<input name='fromval' class='form-control' placeholder='' value = 'journeyxpro@journeyxpro.com' readonly>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>To</label>");
            sb.append("<input name='toval' class='form-control' placeholder='' value = '" + info.getCandidatemail() + "'>");
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
            sb.append("<div class='col-lg-6 col-md-6 col-sm-12 col-12 form_group'>");
            sb.append("<label class='form_label'>Documents (Zip file - Max 20MB)</label>");
            sb.append("<input id='upload1' name='attachfile' type='file' onchange=\"javascript: setClass('3');\">");
            sb.append("<a href='javascript:;' id='upload_link_3' class='attache_btn attache_btn_white uploaded_img1'>");
            sb.append("<i class='fas fa-paperclip'></i> Attach</a>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");

            sb.append("</div>");
            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center' id='dexternalmail'>");
            sb.append("<a href='javascript:;' onclick=\"javascript: submitexternalmailForm();\" class='save_page'>Send &nbsp;&nbsp;<img src='../assets/images/share-as-email.png'></a>");
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