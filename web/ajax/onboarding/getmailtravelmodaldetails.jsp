<%@page import="java.io.File"%>
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
            String file_path = onboarding.getMainPath("view_onboarding");
            String afile_path = onboarding.getMainPath("add_onboarding");

            java.util.Date now = new java.util.Date();
            String fname = String.valueOf(now.getTime());
            ArrayList OnDtlsList = onboarding.getOnboadingListByshortlistId(shortlistId, 0);
            session.setAttribute("MAILDOCLIST", OnDtlsList);
            OnboardingInfo tinfo = onboarding.gettraveldetailsByshortlist(shortlistId);
            String doclist = "";
            ArrayList documentlist = null;
            double size = 0.0, allowedsize = 0.0;
            if (tinfo != null) 
            {
                if (!tinfo.getReqdoconboarding().equals("")) 
                {
                    documentlist = onboarding.getdocumentdetailsByIds(tinfo.getReqdoconboarding());
                    if (documentlist.size() > 0) 
                    {
                        for (int i = 0; i < documentlist.size(); i++) 
                        {
                            OnboardingInfo info = (OnboardingInfo) documentlist.get(i);
                            doclist += info.getDdlLabel() + " \n ";
                        }
                    }
                }
                File ftravel = new File(afile_path + tinfo.getTicketfilename());
                File facc = new File(afile_path + tinfo.getHotdocname());
                if (ftravel.exists()) 
                {
                    size = ftravel.length() / (1024.0 * 1024.0);
                }
                if (facc.exists()) {
                    size += facc.length() / (1024.0 * 1024.0);
                }

            }
            allowedsize = 10 - size;

            String mailfile_path = onboarding.getMainPath("file_maillog");
            OnboardingInfo info = onboarding.settravelEmailDetail(shortlistId, onboardflag, 0);

            String subject = "", mailbody = "";
            if (onboardflag == 2) {
                mailbody = "Dear Candidate, \n \n Please find the Mobilization Details and Documents attached.\n Feel free to connect with us for any concerns.\n \n please carry the documents listed below for successfull onboarding: \n " + doclist + " \n Thank you,\n Team OCS. \n Generated from: JouneryXPro - Crew Management System";
                subject = "Travel & Onboarding Details for " + info.getClientAsset();
            }
            if (onboardflag == 3) 
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
            sb.append("<h2>EMAIL DETAILS TO CANDIDATE</h2>");
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
            sb.append("<textarea name='description' class='form-control' maxlength='2000' style = 'height : 180px'/>");
            sb.append(mailbody);
            sb.append("</textarea>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-7 col-md-7 col-sm-12 col-12 form_group'>");
            sb.append("<label class='form_label'>Documents (pdf file - Max" + Math.ceil(allowedsize) + "MB)</label>");
            sb.append("<input id='upload1' name='attachfile' type='file' onchange=\"javascript: setClass('4');\">");
            sb.append("<a href='javascript:;' id='upload_link_4' class='attache_btn attache_btn_white uploaded_img1'>");
            sb.append("<i class='fas fa-paperclip'></i> Attach</a>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");

            int listsize = OnDtlsList.size();
            if (listsize > 0) 
            {
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Mobilization Details</label>");
                sb.append("<div class='full_width veri_details'>");
                OnboardingInfo oninfo = null;
                for (int i = 0; i < listsize; i++) 
                {
                    oninfo = (OnboardingInfo) OnDtlsList.get(i);
                    if (oninfo != null)
                    {
                        if (!oninfo.getFilename().equals("")) 
                        {
                            sb.append("<ul>");
                            if (oninfo.getType() == 1) {
                                sb.append("<li>" + oninfo.getVal5() + (i + 1) + ".pdf</li>");
                            } else if (oninfo.getType() == 2) {
                                sb.append("<li>" + oninfo.getVal1() + (i + 1) + ".pdf</li>");
                            }
                            sb.append("<li><a href='" + file_path + oninfo.getFilename() + "' target='_blank'>View Attachment</a></li>");
                            sb.append("</ul>");
                        }
                    }
                }
                sb.append("</div>");
                sb.append("</div>");
            }

            sb.append("</div>");
            sb.append("<div class='row'>");
            sb.append("<input type='hidden' name='tafilesize' value='" + Math.ceil(allowedsize) + "'/>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center' id='dsavesendtravelmail'>");
            sb.append("<a href='javascript:;' onclick=\"javascript: submittravelmailForm();\" class='save_page' >Send &nbsp;&nbsp;<img src='../assets/images/share-as-email.png'></a>");
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