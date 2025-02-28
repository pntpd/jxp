<%@page import="java.util.TreeSet"%>
<%@page import="java.util.SortedSet"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.clientlogin.ClientloginInfo" %>
<jsp:useBean id="clientlogin" class="com.web.jxp.clientlogin.Clientlogin" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        ClientloginInfo uInfo = (ClientloginInfo) request.getSession().getAttribute("MLOGININFO");
        if (uInfo != null) 
        {
                String jobpostIds = request.getParameter("jobpostid") != null && !request.getParameter("jobpostid").equals("") ? vobj.replaceint(request.getParameter("jobpostid")) : "0";
                String search = request.getParameter("search") != null && !request.getParameter("search").equals("") ? vobj.replacename(request.getParameter("search")) : "";
                int jobpostId = Integer.parseInt(jobpostIds);
                
                String file_path = clientlogin.getMainPath("view_candidate_file");
                String cvfile_path = clientlogin.getMainPath("view_resumetemplate_pdf");
                ClientloginInfo info = clientlogin.getClientSelectionByIdforDetail(jobpostId);
                ArrayList list = clientlogin.getShortlistedCandidateListByIDs(jobpostId, search);
                request.getSession().setAttribute("SHORTLIST_LIST", list);
                int total = list.size();
                StringBuffer sb = new StringBuffer();
                if (total > 0) 
                {
                    String tempImg = "";
                    for (int i = 0; i < total; i++) 
                    {
                        ClientloginInfo cinfo = (ClientloginInfo) list.get(i);
                        if (cinfo != null) 
                        {
                            tempImg = "";
                            sb.append("<li class='odd_list_1'>");
                            sb.append("<div class='search_box'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='col-lg-9 col-md-9 col-sm-9 col-9 comp_view'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 client_prof_status'>");
                            sb.append("<div class='row d-flex align-items-start'>");
                            sb.append("<div class='col-lg-3 col-md-3 col-sm-3 col-4 com_view_prof cand_box_img'>");
                            sb.append("<div class='user_photo pic_photo'>");

                            sb.append("<div class='upload_file'>");
                            sb.append("<input id='upload1' type='file'>");
                            if (cinfo.getPhoto().equals("")) {
                                tempImg = "../assets/images/empty_user_100x100.png";
                            } else {
                                tempImg = file_path + cinfo.getPhoto();
                            }
                            sb.append("<img src='" + tempImg + "'>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("<div class='col-lg-9 col-md-9 col-sm-9 col-8'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='portlet box status_on_hold'>");

                            sb.append("<div class='portlet-body'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='col-lg-12 col-md-12 col-md-12 col-md-12 com_label_value'>");
                            sb.append("<div class='row mb_0'>");
                            sb.append("<div class='col-lg-3 col-md-3 col-sm-4 col-4'><label>Interview Date</label></div>");
                            sb.append("<div class='col-lg-9 col-md-9 col-sm-8 col-8'><span>");
                                sb.append(cinfo.getDate() != null ? cinfo.getDate(): "-" );
                            sb.append("</span></div>");
                            sb.append("</div>");
                            sb.append("<div class='row mb_0'>");
                            sb.append("<div class='col-lg-3 col-md-3 col-sm-4 col-4'><label>Recruiter</label></div>");
                            sb.append("<div class='col-lg-9 col-md-9 col-sm-8 col-8'><span>");
                                sb.append(cinfo.getInterviewer()!= null ? cinfo.getInterviewer(): "-" );
                            sb.append("</span></div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("<div class='full_name_ic col-md-12 mb_0'>");
                            sb.append("<a href=\"javascript:;\" class='tooltip_name' data-toggle='tooltip' data-placement='top' title='' data-bs-original-title='Full Name' aria-label='Full Name'><i class='mdi mdi-account'></i></a>");
                            sb.append("<span>" + cinfo.getName() + "</span>");
                            sb.append("</div>");

                            sb.append("<div class='posi_rank_ic col-md-12 mb_0'>");
                            sb.append("<a href=\"javascript:;\" class='tooltip_name' data-toggle='tooltip' data-placement='top' title='' data-bs-original-title='Position-Rank' aria-label='Position-Rank'><i class='mdi mdi-star-circle'></i></a>");
                            sb.append("<span>" + cinfo.getPositionname() + "</span>");
                            sb.append("</div>");

                            sb.append("<div class='expe_ic col-md-12 mb_0'>");
                            sb.append("<a href=\"javascript:;\" class='tooltip_name' data-toggle='tooltip' data-placement='top' title='' data-bs-original-title='Experience' aria-label='Experience'><i class='mdi mdi-lightbulb'></i></a>");
                            sb.append("<span>" + cinfo.getExperience() + " Yrs</span>");
                            sb.append("</div>");

                            sb.append("<div class='gradu_ic col-md-12 mb_0'>");
                            sb.append("<a href=\"javascript:;\" class='tooltip_name' data-toggle='tooltip' data-placement='top' title='' data-bs-original-title='Education' aria-label='Education'><i class='fas fa-graduation-cap'></i></a>");
                            sb.append("<span>" + cinfo.getQualification() + "</span>");
                            sb.append("</div>");

                            sb.append("<div class='brief_ic col-md-12'>");
                            sb.append("<a href=\"javascript:;\" class='tooltip_name' data-toggle='tooltip' data-placement='top' title='' data-bs-original-title='Last Job' aria-label='Last Job'><i class='ion ion-ios-briefcase'></i></a>");
                            sb.append("<span>" + cinfo.getCompany() + "</span>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("<div class='col-lg-3 col-md-3 col-sm-3 col-3 add_view_area client_se_vi_re'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='search_add_btn'>");
                            if (cinfo.getIflag() == 1) {
                                sb.append("<a href=\"javascript:;\" onclick=\" javascript: getEmailModal('" + cinfo.getShortlistId() + "', '" + cinfo.getInterviewId() + "');\" data-bs-toggle='modal' data-bs-target='#mail_modal'>Send Mail To Candidate</a>");
                            } else if (cinfo.getIflag() == 2) {
                                if (cinfo.getType() == 2) {
                                    sb.append("<a href=\"javascript:;\" onclick=\" javascript: getRescheduleModel('" + cinfo.getInterviewId() + "');\" data-bs-toggle='modal' data-bs-target='#interviewre_modal'>Not Responded</a>");
                                } else {
                                    sb.append("<a href=\"javascript:;\" onclick=\" javascript:;\" >Interview Invite Sent</a>");
                                }
                            } else if (cinfo.getIflag() == 3) {
                                sb.append("<a href=\"javascript:;\" onclick=\" javascript: getRescheduleModel('" + cinfo.getInterviewId() + "');\" data-bs-toggle='modal' data-bs-target='#interviewre_modal'>Re-Schedule Interview</a>");
                            } else if (cinfo.getIflag() == 4) {
                                if (cinfo.getType() == 1) {
                                    sb.append("<a href=\"javascript:;\" onclick=\" javascript: getScheduledModal('" + cinfo.getInterviewId() + "', '1');\" data-bs-toggle='modal' data-bs-target='#interviewsc_modal'>Interview Scheduled</a>");
                                } else {
                                    sb.append("<a href=\"javascript:;\" onclick=\" javascript: getEvaluateModal('" + cinfo.getInterviewId() + "', '2');\" data-bs-toggle='modal' data-bs-target='#evaluate_modal'>Evaluate Candidate</a>");
                                }
                            } else if (cinfo.getIflag() == 5) {
                                sb.append("<a href=\"javascript:;\">Selected by Client</a> ");
                            } else if (cinfo.getIflag() == 6) {
                                sb.append("<a href=\"javascript:;\" onclick=\" javascript: getScheduledModal('" + cinfo.getInterviewId() + "', '3');\" data-bs-toggle='modal' data-bs-target='#interviewsc_modal'>Rejected by Client</a>");
                            } else {
                                sb.append("<a href=\"javascript:;\" onclick=\" javascript: getModel('" + cinfo.getShortlistId() + "');\" data-bs-toggle='modal' data-bs-target='#interview_modal'>Schedule Interview</a>");
                            }
                            sb.append("</div>");

                            sb.append("<div class='search_view_prof com_view_job with_reject d-none1'>");
                            sb.append("<a href=\"javascript:;\" data-bs-toggle='modal' data-bs-target='#view_pdf' onclick=\"javascript: setIframe('" + (cvfile_path + cinfo.getPdffileName()) + "');\"><img src='../assets/images/view.png'/><br> View CV</a>");
                            sb.append("</div>");
                            sb.append("<div class='search_view_prof client_reject'>");
                            sb.append("<a href=\"javascript: ;\" onclick=\" javascript: getRejectModal('" + cinfo.getShortlistId() + "');\" data-bs-toggle='modal' data-bs-target='#reject_modal'>Reject</a>");

                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</li>");
                        }
                    }
                }
                
                String st = sb.toString();
                sb.setLength(0);
                response.getWriter().write(st);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>