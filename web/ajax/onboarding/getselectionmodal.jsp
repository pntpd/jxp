<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.onboarding.OnboardingInfo"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="onboarding" class="com.web.jxp.onboarding.Onboarding" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String maillogIds = request.getParameter("maillogId") != null && !request.getParameter("maillogId").equals("") ? vobj.replaceint(request.getParameter("maillogId")) : "0";
            int maillogId = Integer.parseInt(maillogIds);
            
            String view_mailfile_path = onboarding.getMainPath("view_maillog_file");
            OnboardingInfo info = onboarding.getselectionEmailDetailById(maillogId);
            StringBuilder sb = new StringBuilder();

            sb.append("<h2>EMAIL DETAILS</h2>");
            sb.append("<div class='row client_position_table'>");
            if (info != null) 
            {
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>From</label>");
                sb.append("<input name='fromval' class='form-control' placeholder='' value = '" + (info.getMailfrom() != null ? info.getMailfrom() : "") + "' readonly>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>To</label>");
                sb.append("<input name='toval' class='form-control' placeholder='' value = '" + (info.getMailto() != null ? info.getMailto() : "") + "' readonly>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>CC</label>");
                sb.append("<input name='ccval' class='form-control' placeholder='' value = '" + (info.getMailcc() != null ? info.getMailcc() : "") + "' readonly>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>BCC</label>");
                sb.append("<input  name='bccval' class='form-control' placeholder='' value = '" + (info.getMailbcc() != null ? info.getMailbcc() : "") + "' readonly>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Email Subject</label>");
                sb.append("<input name='subject' class='form-control' placeholder='' value='" + (info.getSubject() != null ? info.getSubject() : "") + "' readonly>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Email Body</label>");
                if (info.getFilename() != null && !info.getFilename().equals("")) {
                    sb.append("<iframe src='" + (view_mailfile_path + info.getFilename()) + "' style='height: 400px; width: 100%;'></iframe>");
                }
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>File Details</label>");
                sb.append("<div class='full_width veri_details'>");
                sb.append("<ul>");
                if (info.getAttachmentpath() != null && !info.getAttachmentpath().equals("")) {
                    sb.append("<li><a href='" + info.getAttachmentpath() + "' target='_blank'>View Attachment</a></li><br>");
                }
                if (info.getAttachmentpath1() != null && !info.getAttachmentpath1().equals("")) {
                    sb.append("<li><a href='" + info.getAttachmentpath1() + "' target='_blank'>View Attachment</a></li><br>");
                }
                if (info.getAttachmentpath2() != null && !info.getAttachmentpath2().equals("")) {
                    sb.append("<li><a href='" + info.getAttachmentpath2() + "' target='_blank'>View Attachment</a></li><br>");
                }
                if (info.getAttachmentpath3() != null && !info.getAttachmentpath3().equals("")) {
                    String arrfile[] = onboarding.parseCommaDelimString(info.getAttachmentpath3());
                    if (arrfile.length > 0) {
                        for (int i = 0; i < arrfile.length; i++) {
                            if (arrfile[i] != null && !arrfile[i].equals("")) {
                                sb.append("<li><a href='" + arrfile[i] + "' target='_blank'>View Attachment</a></li><br>");
                            }
                        }
                    }
                }
                sb.append("</ul>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
            } else {
                sb.append("<label class='form_label'>No information available</label>");
            }
            sb.append("</div>");
            
            String st1 = sb.toString();
            sb.setLength(0);
            response.getWriter().write(st1);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>