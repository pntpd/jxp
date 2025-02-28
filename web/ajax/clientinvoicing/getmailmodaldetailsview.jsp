<%@page import="com.web.jxp.clientinvoicing.ClientinvoicingInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.compliancecheck.CompliancecheckInfo"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="clientinvoicing" class="com.web.jxp.clientinvoicing.Clientinvoicing" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String maillogIds = request.getParameter("maillogId") != null && !request.getParameter("maillogId").equals("") ? vobj.replaceint(request.getParameter("maillogId")) : "0";
            int maillogId = Integer.parseInt(maillogIds);
            String mailfile_path = clientinvoicing.getMainPath("file_maillog");

            String subject = "", mailbody = "";
            ClientinvoicingInfo mailinfo = clientinvoicing.getMaillogTimesheetbymaillogId( maillogId);

            subject = mailinfo.getSubject();
            String mbody = clientinvoicing.readHTMLFile(mailinfo.getMailbody(), mailfile_path);

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


            String cc = "&nbsp;", bcc = "&nbsp;";
            if( mailinfo!= null)
            {
            cc = mailinfo.getCc();
            bcc = mailinfo.getBcc();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("<h2>EMAIL INVOICE TO CLIENT</h2>");
            sb.append("<div class='row client_position_table'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>To</label>");
            sb.append("<span class='form-control'>"+ mailinfo.getTo()+"</span>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>CC</label>");
            sb.append("<span class='form-control'>"+ (!cc.equals("") ? cc : "&nbsp;")+"</span>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>BCC</label>");
            sb.append("<span class='form-control'>"+ (!bcc.equals("") ? bcc : "&nbsp;")+"</span>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>Email Subject</label>");
            sb.append("<span class='form-control'>"+ subject+"</span>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>Email Body</label>");
            sb.append("<span class='form-control'>"+ mailbody+"</span>");
            sb.append("</div>");
            
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<div class='row'>");
            if(mailinfo.getMailattachedfile() != null && !mailinfo.getMailattachedfile().equals(""))
            {
                sb.append("<div class='col-lg-6 col-md-3 col-sm-6 col-12 text-left'>");
                sb.append("<ul class='resume_attach'>");
                sb.append("<li><label class='form_label'>Additional Docs</label></li>");
                sb.append("<li>");
                sb.append("<a href=\""+mailinfo.getMailattachedfile()+"\" class='attache_btn' target='_blank'>");
                sb.append("<i class='fas fa-paperclip'></i> Attachment");
                sb.append("</a>");
                sb.append("</li>");
                sb.append("<li>");
                sb.append("<div class='down_prev'>");
                sb.append("<a href=\""+mailinfo.getMailattachedfile()+"\"   target = '_blank'  download = 'attchment'>Download</a><span class='dot_ic'></span>");
                sb.append("<a href=\""+mailinfo.getMailattachedfile()+"\"  target = '_blank'>Preview</a>");
                sb.append("</div>");
                sb.append("</li>");
                sb.append("</ul>");
                sb.append("</div>");
            }
            if(mailinfo.getGeneratedfile() != null && !mailinfo.getGeneratedfile().equals(""))
            {
                sb.append("<div class='col-lg-6 col-md-3 col-sm-6 col-12 text-left'>");
                sb.append("<ul class='resume_attach'>");
                sb.append("<li><label class='form_label'>Invoice Sent</label></li>");
                sb.append("<li>");
                sb.append("<a href=\""+mailinfo.getGeneratedfile()+"\" class='attache_btn' target='_blank'>");
                sb.append("<i class='fas fa-paperclip'></i> Attachment");
                sb.append("</a>");
                sb.append("</li>");
                sb.append("<li>");
                sb.append("<div class='down_prev'>");
                sb.append("<a href=\""+mailinfo.getGeneratedfile()+"\" target='_blank' download = 'attchment'>Download</a><span class='dot_ic'></span>");
                sb.append("<a href=\""+mailinfo.getGeneratedfile()+"\" target = '_blank'>Preview</a>");
                sb.append("</div>");
                sb.append("</li>");
                sb.append("</ul>");
                sb.append("</div>");
            }            
            sb.append("</div>");
            sb.append("</div>");            
            sb.append("</div>");            
            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'>");
            sb.append("<a href=\"javascript:;\" class='save_page' data-bs-dismiss='modal' aria-hidden='true'>Close</a>");
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