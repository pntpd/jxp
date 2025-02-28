<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.feedback.FeedbackInfo" %>
<jsp:useBean id="feedback" class="com.web.jxp.feedback.Feedback" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("CREWLOGIN") != null) 
        {
            if (request.getParameter("Id") != null) 
            {
                StringBuilder sb = new StringBuilder();
                String Ids = request.getParameter("Id") != null && !request.getParameter("Id").equals("") ? vobj.replaceint(request.getParameter("Id")) : "0";
                int trackerId = Integer.parseInt(Ids);
                String viewpath = feedback.getMainPath("view_trackerfiles");
                FeedbackInfo info = null;
                if (trackerId > 0) 
                {
                    info = feedback.getPcodeInfo(trackerId);
                    session.setAttribute("PCODE_INFO", info);
                    if (info != null) 
                    {
                        sb.append("<div class='row'>");
                        sb.append("<div class='col'>");
                        sb.append("<h2>" + (info.getRole() != null ? info.getRole() : "") + "</h2>");
                        sb.append("</div>");
                        sb.append("<div class='col col-xl-3 col-lg-2 col-md-2 col-sm-3 col-3 hist_prog_unass text-right'>");
                        sb.append("<ul>");
                        sb.append("<li ");
                        if (info.getStatus() == 4) {
                            sb.append("class='circle_complete'>");
                            sb.append("Competent");
                        } else if (info.getStatus() == 5) {
                            sb.append("class='circle_exipred'>");
                            sb.append("Not Yet Competent");
                        } else if (info.getStatus() == 6) {
                            sb.append("class='circle_exipry'>");
                            sb.append("Appealed");
                        } else if (info.getOnlineflag() == 2 || info.getStatus() == 3) {
                            sb.append("class='rol_com_inprogress'>");
                            sb.append("Assessment Complete");
                        } else if (info.getOnlineflag() == 1) {
                            sb.append("class='rol_com_inprogress'>");
                            sb.append("Saved as draft");
                        } else if (info.getOnlineflag() == 0) {
                            sb.append("class='rol_com_inprogress'>");
                            sb.append("Pending");
                        }
                        sb.append("</li>");
                        sb.append("</ul>");
                        sb.append("</div>");
                        sb.append("</div>");
                        sb.append("<div class='row client_position_table1 fix_modal_body'>");
                        sb.append("<div class='col-md-12'>");
                        sb.append("<div class='row'>");
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12'>");
                        sb.append("<div class='row'>");
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                        sb.append("<label class='form_label'>" + (info.getOnlineDate() != null && !info.getOnlineDate().equals("") ? "Complete By: " + info.getOnlineDate() : "") + "</label>");
                        sb.append("</div>");
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                        sb.append("<label class='form_label'>Description</label>");
                        sb.append("<span class='full_width des_content'>");
                        sb.append((info.getDesc() != null ? info.getDesc() : ""));
                        sb.append("</span>");
                        sb.append("</div>");
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                        sb.append("<label class='form_label'>Instructions</label>");
                        sb.append("<span class='full_width offline_online_content mb_10'>");
                        sb.append("<h4>Offline Submission</h4>");
                        sb.append("<ol type='1'>");
                        sb.append("<li>Download printed copy of the assessment. </li>");
                        sb.append("<li>Fill and Submit the printed copy of your assessment directly to the Crew Coordinator.</li>");
                        sb.append("</ol>");
                        sb.append("</span>");
                        sb.append("<span class='full_width offline_online_content'>");
                        sb.append("<h4>Online Submission</h4>");
                        sb.append("<ol type='1'>");
                        sb.append("<li>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore </li>");
                        sb.append("<li>Et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.</li>");
                        sb.append("</ol>");
                        sb.append("</span>");
                        sb.append("</div>");
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                        sb.append("<label class='form_label'>Contact</label>");
                        sb.append("<span class='full_width des_content'>");
                        sb.append("If you have any questions or run into any technical issues while taking the assessment, please get in touch with our support staff at " + (info.getHelpemail() != null ? info.getHelpemail() : "") + " or by calling " + (info.getHelpno() != null ? info.getHelpno() : "") + ".");
                        sb.append("</span>");
                        sb.append("</div>");
                        sb.append("</div>");
                        sb.append("</div>");
                        sb.append("</div>");
                        sb.append("</div>");
                        sb.append("</div>");
                        sb.append("<div class='row'>");
                        sb.append("<div class='col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12'>");
                        sb.append("<div class='row justify-content-md-center'>");                        
                        sb.append("<div class='col-xl-3 col-lg-3 col-md-3 col-sm-4 col-4 text-center'><a href=\"javascript: ;\" onclick=\"javascript: getTopic();\"class='view_course'>View Course Material</a></div>");
                        if ((info.getOnlineflag() == 0 || info.getOnlineflag() == 1) && info.getStatus() != 3) {
                            sb.append("<div class='col-xl-3 col-lg-3 col-md-3 col-sm-4 col-4'><a href=\"javascript:;\" id='hrefid' onclick=\"getDownloadToPrint('" + (info.getFilename() != null && !info.getFilename().equals("") ? (viewpath + info.getFilename()) : "") + "','" + trackerId + "')\" class='trans_btn'>Download to Print</a></div>");
                            sb.append("<div class='col-xl-3 col-lg-3 col-md-3 col-sm-4 col-4'><a href=\"javascript:;\" onclick=\"getOnlineAssessment('" + trackerId + "')\" class='termi_btn'>" + (info.getOnlineflag() == 1 ? "Resume" : "Online") + " Assessment </a></div>");
                        } else {
                            sb.append("<div class='col-xl-3 col-lg-3 col-md-3 col-sm-4 col-4'>");
                            sb.append("<a href='javascript:;' onclick=\"javascript: getFeedback('" + trackerId + "');\" class='trans_btn'>Feedback</a>");
                            sb.append("</div>");
                        }
                        sb.append("</div>");
                        sb.append("</div>");
                        sb.append("</div>");
                    }
                    String st1 = sb.toString();
                    sb.setLength(0);
                    response.getWriter().write(st1);
                } else {
                    response.getWriter().write("Something went wrong.");
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>