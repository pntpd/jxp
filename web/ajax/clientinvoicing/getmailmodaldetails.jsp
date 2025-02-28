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
            String timesheetIds = request.getParameter("timesheetId") != null && !request.getParameter("timesheetId").equals("") ? vobj.replaceint(request.getParameter("timesheetId")) : "0";
            int timesheetId = Integer.parseInt(timesheetIds);
            String mailfile_path = clientinvoicing.getMainPath("file_maillog");
            ClientinvoicingInfo info = clientinvoicing.getTimesheetDetailByIdforDetailMail(timesheetId);

            String subject = "", mailbody = "";
                mailbody = "Dear Client \n\nAs per the timesheet ("+clientinvoicing.changeNum( timesheetId,3)+") approved on "+info.getApprovaldate()+", please find the invoice attached. Kindly do the needful.\n\nThank you,\n\nTeam OCS.\n\n  Generated from: JourneyXPro - Crew Management System";
                subject = "Invoice - "+info.getFromdate() +"-"+info.getTodate();
                 ClientinvoicingInfo mailinfo = clientinvoicing.getTimesheetDetailfromMaillogtable(timesheetId);
                if(info.getInvoicestatus() == 3){
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
            
                }
                String cc = "", bcc = "";
                if( mailinfo!= null)
                {
                cc = mailinfo.getCc();
                bcc = mailinfo.getBcc();
                }
            StringBuilder sb = new StringBuilder();
            sb.append("<h2>EMAIL INVOICE TO CLIENT</h2>");
            sb.append("<div class='row client_position_table'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>From</label>");
            sb.append("<input name='fromval' class='form-control' placeholder='' value = 'journeyxpro@journeyxpro.com' readonly>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>To</label>");
            sb.append("<input name='toval' class='form-control' placeholder='' value = '" + info.getClientemail()+ "'>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>CC</label>");
            sb.append("<input name='ccval' class='form-control' placeholder='' value = '" + cc + "'>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>BCC</label>");
            sb.append("<input  name='bccval' class='form-control' placeholder='' value = '" + bcc+ "'>");
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
            sb.append("<label class='form_label'>Invoice Generation Details</label>");
            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-5 col-md-5 col-sm-12 col-12 form_group'>");
            sb.append("<input id='upload2' name='attachfile' type='file' onchange=\"javascript: setClass('2');\">");
            sb.append("<a href=\"javascript:;\" id='upload_link_2' class='attache_btn attache_btn_white uploaded_img1'>");
            sb.append("<i class='fas fa-paperclip'></i> Attach");
            sb.append("</a>");
            sb.append("</div>");
            sb.append("<div class='col-lg-7 col-md-7 col-sm-12 col-12 form_group'>");
            sb.append("<div class='mt-checkbox-inline'>");
            sb.append("<label class='mt-checkbox mt-checkbox-outline'> No Additional Attachment");
            sb.append("<input type='checkbox' value='1' name='checkcb'>");
            sb.append("<span></span>");
            sb.append("</label>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'  id='sendmaildiv'>");
            sb.append("<a href=\"javascript: ;\" onclick=\"javascript: submitmailForm();\" class='save_page'>Send &nbsp;&nbsp;<img src='../assets/images/share-as-email.png'></a>");
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