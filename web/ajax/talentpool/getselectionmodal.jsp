<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String shortlistIds = request.getParameter("shortlistId") != null && !request.getParameter("shortlistId").equals("") ? vobj.replaceint(request.getParameter("shortlistId")) : "0";
            String maillogIds = request.getParameter("maillogId") != null && !request.getParameter("maillogId").equals("") ? vobj.replaceint(request.getParameter("maillogId")) : "0";
            String sflags = request.getParameter("sflag") != null && !request.getParameter("sflag").equals("") ? vobj.replaceint(request.getParameter("sflag")) : "0";
            String oflags = request.getParameter("oflag") != null && !request.getParameter("oflag").equals("") ? vobj.replaceint(request.getParameter("oflag")) : "0";
            String usertypes = request.getParameter("usertype") != null && !request.getParameter("usertype").equals("") ? vobj.replaceint(request.getParameter("usertype")) : "0";
            int shortlistId = Integer.parseInt(shortlistIds);
            int maillogId = Integer.parseInt(maillogIds);
            int sflagp = Integer.parseInt(sflags);
            int oflagp = Integer.parseInt(oflags);
            int usertype = Integer.parseInt(usertypes);

            String file_path = talentpool.getMainPath("view_resumetemplate_pdf");
            String mailfile_path = talentpool.getMainPath("file_maillog");
            TalentpoolInfo info = talentpool.getselectionEmailDetail(shortlistId, maillogId, sflagp, oflagp);
            StringBuilder sb = new StringBuilder();
            if(info != null)
            {
                if (sflagp <= 2 && oflagp == 0)
                {
                    sb.append("<div class='modal-header'>");
                    sb.append("<button type='button' class='close close_modal_btn pull-right' data-bs-dismiss='modal' aria-hidden='true'><i class='ion ion-md-close'></i></button>");
                    sb.append("<span class='resume_title'> File</span>");
                    sb.append("<a href='javascript:;' data-bs-toggle='fullscreen' class='full_screen'>Full Screen</a>");
                    if(info.getAttachmentpath() != null && !info.getAttachmentpath().equals(""))                
                        sb.append("<a id='diframe' href='" + file_path + info.getAttachmentpath() + "' class='down_btn' download=''><img src='../assets/images/download.png'/></a>");
                    sb.append("</div>");

                    sb.append("<div class='modal-body'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-12'>");
                    if(info.getAttachmentpath() != null && !info.getAttachmentpath().equals(""))    
                        sb.append("<iframe id='iframe' class='pdf_mode' src='" + file_path + info.getAttachmentpath() + "'></iframe>");

                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");

                } else if (oflagp == 2 && sflagp == 4) {

                    sb.append("<div class='modal-header'>");
                    sb.append("<button type='button' class='close close_modal_btn pull-right' data-bs-dismiss='modal' aria-hidden='true'><i class='ion ion-md-close'></i></button>");
                    sb.append("<span class='resume_title'> File</span>");
                    sb.append("<a href='javascript:;' data-bs-toggle='fullscreen' class='full_screen'>Full Screen</a>");
                    if(info.getAttachmentpath() != null && !info.getAttachmentpath().equals(""))    
                        sb.append("<a id='diframe' href='" + file_path + info.getAttachmentpath() + "' class='down_btn' download=''><img src='../assets/images/download.png'/></a>");
                    sb.append("</div>");

                    sb.append("<div class='modal-body'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-12'>");
                    if(info.getAttachmentpath() != null && !info.getAttachmentpath().equals(""))    
                        sb.append("<iframe id='iframe' class='pdf_mode' src='" + file_path + info.getAttachmentpath() + "'></iframe>");

                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");

                } else if (sflagp > 2 || oflagp > 2) {
                    String mailbody = "";
                    String sflagvalue = "", oflagvalue = "";
                    if (sflagp == 4) {
                        sflagvalue = "Selected";
                    } else if (sflagp == 5) {
                        sflagvalue = "Rejected";
                    }

                    if (oflagp == 4) {
                        oflagvalue = "Accepted";
                    } else if (oflagp == 5) {
                        oflagvalue = "Declined";
                    }

                    mailbody = talentpool.readHTMLFile(info.getFilename(), mailfile_path);

                    int v1 = 0;
                    int v2 = mailbody.length();
                    if (mailbody.indexOf("<br/><br/>") != -1) {
                        v1 = mailbody.indexOf("<br/><br/>");
                    }
                    if (mailbody.indexOf("<br/><br/><br/></table>") != -1) {
                        v2 = mailbody.indexOf("<br/><br/><br/></table>");
                    }
                    if(mailbody.length() > 10)
                        mailbody = mailbody.substring(v1 + 10, v2).trim();

                    String modalheading = "";
                    if (sflagp == 3) {
                        modalheading = "EMAIL CV TO CLIENT";
                    } else if (oflagp == 3) {
                        modalheading = "EMAIL OFFER LETTER TO CANDIDATE";
                    } else if (sflagp == 4 || sflagp == 5) {
                        modalheading = "SUMMARY";
                    }

                    sb.append("<div class='modal-header'>");
                    sb.append("<button type='button' class='close close_modal_btn pull-right' data-bs-dismiss='modal' aria-hidden='true'><i class='ion ion-md-close'></i></button>");
                    sb.append("</div>");

                    sb.append("<div class='modal-body'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-12'>");

                    sb.append("<h2>" + modalheading + "</h2>");

                    sb.append("<div class='row client_position_table'>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                    sb.append("<label class='form_label'>User Type</label>");
                    sb.append("<span class='form-control'>" + talentpool.getusertype(usertype) + "</span>");
                    sb.append("</div>");

                    if (sflagp == 3 || sflagp == 5 || sflagp == 4) 
                    {
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>Sent On</label>");
                        sb.append("<span class='form-control'>" + (info.getDate() != null ? info.getDate(): "") + "</span>");
                        sb.append("</div>");
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>Sent By</label>");
                        sb.append("<span class='form-control'>" + (info.getSentby() != null ? info.getSentby(): "")+ "</span>");
                        sb.append("</div>");
                    }

                    if (oflagp == 4 || oflagp == 5) 
                    {
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>" + oflagvalue + " On</label>");
                        sb.append("<span class='form-control'>" + info.getOaddate() + "</span>");
                        sb.append("</div>");

                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>" + oflagvalue + " By</label>");
                        sb.append("<span class='form-control'>" + (info.getOadby() != null ? info.getOadby(): "")+ "</span>");
                        sb.append("</div>");
                    } else if ((sflagp == 4 || sflagp == 5) && oflagp == 0) {
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>" + sflagvalue + " On</label>");
                        sb.append("<span class='form-control'>" + (info.getSrdate() != null ? info.getSrdate(): "") + "</span>");
                        sb.append("</div>");

                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>" + sflagvalue + " By</label>");
                        sb.append("<span class='form-control'>" + (info.getSrby() != null ? info.getSrby(): "") + "</span>");
                        sb.append("</div>");
                    }
                    if (sflagp == 4 || sflagp == 3)
                    {
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>From</label>");
                        sb.append("<span class='form-control'>" + (info.getMailfrom() != null ? info.getMailfrom(): "") + "</span>");
                        sb.append("</div>");
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>To</label>");
                        sb.append("<span class='form-control'>" + (info.getMailto() != null ? info.getMailto(): "") + "</span>");
                        sb.append("</div>");
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>CC</label>");
                        sb.append("<span class='form-control'>");
                        if (!info.getMailcc().equals("")) {
                            sb.append(info.getMailcc());
                        } else {
                            sb.append("&nbsp;");
                        }
                        sb.append("</span>");
                        sb.append("</div>");
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>BCC</label>");
                        sb.append("<span class='form-control'>");
                        if (!info.getMailbcc().equals("")) {
                            sb.append(info.getMailbcc());
                        } else {
                            sb.append("&nbsp;");
                        }
                        sb.append("</span>");
                        sb.append("</div>");
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>Email Subject</label>");
                        sb.append("<span class='form-control'>" + (info.getSubject() != null ? info.getSubject(): "") + "</span>");
                        sb.append("</div>");
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>Email Body</label>");
                        sb.append("<span class='form-control'>");
                        sb.append(mailbody != null && !mailbody.equals("") ? mailbody : "&nbsp;" );
                        sb.append("</span>");
                        sb.append("</div>");
                    }
                    if (oflagp == 5) {
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>Reason</label>");
                        sb.append("<span class='form-control'>" + ((talentpool.getReasonbyId(info.getOadreason())).replaceAll(",", "<br/>")) + "</span>");
                        sb.append("</div>");

                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>Remarks</label>");
                        sb.append("<span class='form-control'>" + (info.getOadremarks() != null ? info.getOadremarks(): "") + "</span>");
                        sb.append("</div>");
                    } else if (sflagp == 5 && oflagp == 1) {
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>Reason</label>");
                        sb.append("<span class='form-control'>" + ((talentpool.getReasonbyId(info.getReason())).replaceAll(",", "<br/>")) + "</span>");
                        sb.append("</div>");

                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>Remarks</label>");
                        sb.append("<span class='form-control'>" + (info.getRemarks() != null ? info.getRemarks(): "") + "</span>");
                        sb.append("</div>");
                    }
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                    sb.append("<label class='form_label'>Attachment Details</label>");
                    sb.append("<div class='full_width veri_details'>");
                    sb.append("<ul>");

                    if (!info.getAttachmentpath().equals("")) {
                        sb.append("<li><a href='" + info.getAttachmentpath() + "' target = '_blank'>View Attachment</a></li>");
                    } else {
                        sb.append("<li><a href=\"javascript:;\">No attachments</a></li>");
                    }
                    sb.append("</ul>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                }
                String st1 = sb.toString();
                sb.setLength(0);
                response.getWriter().write(st1);
            } else {
                response.getWriter().write("Please check your login session....");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>