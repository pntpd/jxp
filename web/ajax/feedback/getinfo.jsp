<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.feedback.FeedbackInfo" %>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="feedback" class="com.web.jxp.feedback.Feedback" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("CREWLOGIN") != null) 
        {
            int crewrotationId = 0, positionId = 0, clientassetId = 0;
            if (request.getSession().getAttribute("CREWLOGIN") != null) 
            {
                CrewloginInfo info = (CrewloginInfo) request.getSession().getAttribute("CREWLOGIN");
                if (info != null)
                {
                    crewrotationId = info.getCrewrotationId();
                    positionId = info.getPositionId();
                    clientassetId = info.getClientassetId();
                }
            }
            if (crewrotationId > 0)
            {
                int showsizelist = feedback.getCountList("show_size_list");
                StringBuilder sb = new StringBuilder();
                sb.append("<ul class='comp_list' id='printArea'>");
                sb.append("<li class='feedback_name_label'>Feedback Name</li>");
                sb.append("<li class='feedback_subm_on_label'>Submitted On</li>");
                sb.append("<li class='feedback_status_label'>Status</li>");
                sb.append("<li class='feedback_filter_label'>");
                sb.append("<div class='toggled-off_02 usefool_tool'>");
                sb.append("<div class='toggle-title_02'>");
                sb.append("<img src='../assets/images/filter.png' class='fa-angle-left'>");
                sb.append("<img src='../assets/images/filter_up.png' class='fa-angle-right'>");
                sb.append("</div>");
                sb.append("<div class='toggle-content'>");
                sb.append("<ul>");
                sb.append("<li><a href=\"javascript: resetStatus();\"><span class='round_circle circle_courses selected_circle'></span>All</a></li>");
                sb.append("<li><a href=\"javascript: setStatusearch('1');\"><span class='round_circle circle_exipry'></span> Pending</a></li>");
                sb.append("<li><a href=\"javascript: setStatusearch('2');\"><span class='round_circle circle_complete'></span> Complete</a></li>");
                sb.append("</ul>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</li>");
                sb.append("<li class='feedback_ques_label text-center'>Questions</li>");
                sb.append("<li class='feedback_actions_label text-center'>Actions</li>");
                sb.append("</ul>");
                String thead = sb.toString();
                sb.setLength(0);
                
                String search = request.getParameter("search") != null ? vobj.replacedesc(request.getParameter("search")) : "";
                String nextValue = request.getParameter("nextValue") != null && !request.getParameter("nextValue").equals("") ? vobj.replaceint(request.getParameter("nextValue")) : "0";
                String dodirects = request.getParameter("doDirect") != null && !request.getParameter("doDirect").equals("") ? vobj.replaceint(request.getParameter("doDirect")) : "-1";
                String status_s = request.getParameter("status") != null && !request.getParameter("status").equals("") ? vobj.replaceint(request.getParameter("status")) : "-1";
                String fromDate = request.getParameter("fromDate") != null ? request.getParameter("fromDate") : "";
                String toDate = request.getParameter("toDate") != null ? request.getParameter("toDate") : "";
                int n = Integer.parseInt(nextValue);
                int dodirect = Integer.parseInt(dodirects);
                int status = Integer.parseInt(status_s);
                if (true)
                {
                    StringBuilder str = new StringBuilder();
                    int m = n;
                    int next_value = 0;
                    String next1 = request.getParameter("next");
                    if (next1 != null && next1.equals("n")) {
                        if (dodirect >= 0) {
                            n = dodirect;
                            next_value = dodirect + 1;
                        } else {
                            n = n;
                            next_value = m + 1;
                        }
                    } else if (next1 != null && next1.equals("p")) {
                        n = n - 2;
                        next_value = m - 1;
                    } else if (next1 != null && next1.equals("s")) {
                        n = 0;
                        next_value = 1;
                    }
                    session.setAttribute("NEXTVALUE", next_value + "");
                    int recordsperpage = feedback.getCount();
                    ArrayList feedback_list = new ArrayList();
                    int count = 0;
                    if (next1.equals("p") || next1.equals("n")) {
                        feedback_list = feedback.getFeedbackByName(crewrotationId, search, status, n, recordsperpage, fromDate, toDate, positionId, clientassetId);
                        if (feedback_list.size() > 0) {
                            FeedbackInfo cinfo = (FeedbackInfo) feedback_list.get(feedback_list.size() - 1);
                            count = cinfo.getSurveyId();
                            feedback_list.remove(feedback_list.size() - 1);
                        }
                    } else {
                        feedback_list = feedback.getFeedbackByName(crewrotationId, search, status, n, recordsperpage, fromDate, toDate, positionId, clientassetId);
                        if (feedback_list.size() > 0) {
                            FeedbackInfo cinfo = (FeedbackInfo) feedback_list.get(feedback_list.size() - 1);
                            count = cinfo.getSurveyId();
                            feedback_list.remove(feedback_list.size() - 1);
                        }
                    }
                    session.setAttribute("COUNT_LIST", count + "");
                    session.setAttribute("FEEDBACK_LIST", feedback_list);

                    int pageSize = count / recordsperpage;
                    if (count % recordsperpage > 0) {
                        pageSize = pageSize + 1;
                    }

                    int feedbackCount = count;
                    String prev = "";
                    String prevclose = "";
                    String next = "";
                    String nextclose = "";
                    int last = 0;
                    int total = feedback_list.size();
                    str.append("<input type='hidden' name='totalpage' value='" + pageSize + "' />");
                    str.append("<input type='hidden' name='nextValue' value='" + (next_value) + "'/>");
                    if (n == 0 && feedbackCount > recordsperpage) {
                        next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                        nextclose = ("</i></a></li>");
                    } else if (n > 0 && feedbackCount > ((n * recordsperpage) + recordsperpage)) {
                        next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                        nextclose = ("</i></a></li>");
                        prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                        prevclose = ("</i></a></li>");
                    } else if (n > 0 && feedbackCount <= ((n * recordsperpage) + recordsperpage)) {
                        prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                        prevclose = ("</i></a></li>");
                        last = feedbackCount;
                    } else {
                        recordsperpage = feedbackCount;
                    }
                    int value = n * recordsperpage + 1;
                    int value1 = last > 0 ? last : n * recordsperpage + recordsperpage;
                    int test = n;
                    int noOfPages = 1;
                    if (recordsperpage > 0) {
                        noOfPages = feedbackCount / recordsperpage;
                        if (feedbackCount % recordsperpage > 0) {
                            noOfPages += 1;
                        }
                    }
                    str.append("<div class='col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12'>");
                    str.append("<div class='wesl_pagination pagination-mg m_15'>");
                    str.append("<div class='wesl_pg_rcds'>");
                    str.append("Page&nbsp;" + (next_value) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + feedbackCount + ")&nbsp;");
                    str.append("</div>");
                    str.append("<div class='wesl_No_pages'>");
                    str.append("<div class='loanreportTables_paginate paging_bootstrap_full_number'>");
                    str.append("<ul class='wesl_pagination'>");
                    if (noOfPages > 1 && next_value != 1) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFormAjax('n', '0');\" title='First' style='height: 33px;'><i class='fa fa-angle-double-left'></i></a></li>");
                    }
                    str.append(prev + prevclose + "&nbsp;");
                    int a1 = test - showsizelist;
                    int a2 = test + showsizelist;
                    for (int ii = a1; ii <= a2; ii++) {
                        if (ii >= 0 && ii < noOfPages) {
                            if (ii == test) {
                                str.append("<li class='wesl_active'><a href='#'> " + (ii + 1) + " </a></li>");
                            } else {
                                str.append("<li><a href=\"javascript: searchFormAjax('n', '" + ii + "');\" title=''> " + (ii + 1) + " </a></li>");
                            }
                        }
                    }
                    str.append(next + nextclose);
                    if (noOfPages > 1 && next_value != noOfPages) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFormAjax('n','" + (noOfPages - 1) + "');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
                    }
                    str.append("</ul></div></div></div>");

                    str.append("<div id='printArea'>");
                    str.append(thead);
                    if (total > 0) 
                    {
                        str.append("<ul class='comp_list_detail' id='sort_id'>");

                        FeedbackInfo info;
                        int sub_status = 0;
                        String stclass = "", stvalue = "";
                        for (int i = 0; i < total; i++) 
                        {
                            info = (FeedbackInfo) feedback_list.get(i);
                            if (info != null) 
                            {
                                sub_status = info.getStatus();
                                if (sub_status == 1) {
                                    stclass = "circle_exipry";
                                    stvalue = "Pending";
                                } else if (sub_status == 2) {
                                    stclass = "circle_complete";
                                    stvalue = "Completed";
                                }

                                str.append("<li>");
                                str.append("<div class='comp_list_main'>");
                                str.append("<ul>");
                                str.append("<li class='feedback_name_label feedback_ord_list_1'>");
                                str.append("<span class='value_record'>");
                                str.append(info.getFeedback() != null ? info.getFeedback() : "");
                                str.append("</span>");
                                str.append("</li>");
                                str.append("<li class='feedback_subm_on_label feedback_ord_list_2'>");
                                str.append("<span class='mob_show label_title'>Submitted On</span> <span class='value_record'>");
                                str.append(info.getSubmissionDate() != null ? info.getSubmissionDate() : "");
                                str.append("</span>");
                                str.append("</li>");
                                str.append("<li class='feedback_status_label feedback_ord_list_3'>");
                                str.append("<span class='mob_show label_title'>Status</span> <span class='round_circle " + stclass + "'></span> <span class='value_record'>" + stvalue + "</span>");
                                str.append("</li>");
                                str.append("<li class='feedback_filter_label feedback_ord_list_4'>&nbsp;</li>");
                                str.append("<li class='feedback_ques_label text-center feedback_ord_list_5'>");
                                str.append("<span class='mob_show label_title'>Questions</span> <span class='value_record que_number'>" + feedback.changeNum(info.getQuestioncount(), 2) + "</span>");
                                str.append("</li>");
                                str.append("<li class='feedback_actions_label action_list text-center feedback_ord_list_6'>");
                                str.append("<a href=\"javascript:;\" onclick=\"javascript: viewSurvey('" + info.getSurveyId() + "');\"> <img src='../assets/images/view.png' /> <span class='mob_show view_appeal'>View</span></a>");
                                str.append("</li>");
                                str.append("</ul>");
                                str.append("</div>");
                                str.append("</li>");
                            }
                        }
                        str.append("</u1l>");
                    } else {
                        str.append("<ul class='comp_list_detail' id='sort_id'>");
                        str.append("<li class='empty_record'>");
                        str.append("<div class='comp_list_main'>");
                        str.append("<ul>");
                        str.append("<span class='value_record'>");
                        str.append(feedback.getMainPath("record_not_found"));
                        str.append("</span>");
                        str.append("</ul>");
                        str.append("</div>");
                        str.append("</li>");
                        str.append("</u1l>");
                    }
                    str.append("</div>");

                    str.append("<div class='wesl_pagination pagination-mg mt_15'>");
                    str.append("<div class='wesl_pg_rcds'>");
                    str.append("Page&nbsp;" + (next_value) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + feedbackCount + ")&nbsp;");
                    str.append("</div>");
                    str.append("<div class='wesl_No_pages'>");
                    str.append("<div class='loanreportTables_paginate paging_bootstrap_full_number'>");
                    str.append("<ul class='wesl_pagination'>");
                    if (noOfPages > 1 && next_value != 1) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFormAjax('n', '0');\" title='First' style='height: 33px;'><i class='fa fa-angle-double-left'></i></a></li>");
                    }
                    str.append(prev + prevclose + "&nbsp;");
                    for (int ii = a1; ii <= a2; ii++) {
                        if (ii >= 0 && ii < noOfPages) {
                            if (ii == test) {
                                str.append("<li class='wesl_active'><a href='#'> " + (ii + 1) + " </a></li>");
                            } else {
                                str.append("<li><a href=\"javascript: searchFormAjax('n', '" + ii + "');\" title=''> " + (ii + 1) + " </a></li>");
                            }
                        }
                    }
                    str.append(next + nextclose);
                    if (noOfPages > 1 && next_value != noOfPages) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFormAjax('n','" + (noOfPages - 1) + "');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
                    }
                    str.append("</ul></div></div></div></div>");
                    
                    String s1 = str.toString();
                    str.setLength(0);
                    response.getWriter().write(s1);
                } else {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            } else {
                response.getWriter().write("Something went wrong");
            }
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>