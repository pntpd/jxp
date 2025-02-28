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
            int crewrotationId = 0;
            if (request.getSession().getAttribute("CREWLOGIN") != null) 
            {
                CrewloginInfo info = (CrewloginInfo) request.getSession().getAttribute("CREWLOGIN");
                if (info != null) 
                {
                    crewrotationId = info.getCrewrotationId();
                }
            }
            if (crewrotationId > 0) 
            {
                int showsizelist = feedback.getCountList("show_size_list");
                
                StringBuilder sb = new StringBuilder();
                sb.append("<ul class='comp_list'>");
                sb.append("<li class='comp_name_label'>Competency Name</li>");
                sb.append("<li class='comp_by_label'>Complete By</li>");
                sb.append("<li class='comp_on_label'>Completed On</li>");
                sb.append("<li class='comp_status_label'>Status</li>");
                sb.append("<li class='feedback_actions_label'>Actions</li>");
                sb.append("</ul>");
                String thead = sb.toString();
                sb.setLength(0);
                
                String search = request.getParameter("search") != null ? vobj.replacedesc(request.getParameter("search")) : "";
                String nextValue = request.getParameter("nextValue") != null && !request.getParameter("nextValue").equals("") ? vobj.replaceint(request.getParameter("nextValue")) : "0";
                String dodirects = request.getParameter("doDirect") != null && !request.getParameter("doDirect").equals("") ? vobj.replaceint(request.getParameter("doDirect")) : "-1";
                String status_s = request.getParameter("status") != null && !request.getParameter("status").equals("") ? vobj.replaceint(request.getParameter("status")) : "-1";
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
                        feedback_list = feedback.getCompetencyByName(crewrotationId, search, status, n, recordsperpage);
                        if (feedback_list.size() > 0) {
                            FeedbackInfo cinfo = (FeedbackInfo) feedback_list.get(feedback_list.size() - 1);
                            count = cinfo.getTrackerId();
                            feedback_list.remove(feedback_list.size() - 1);
                        }
                    } else {
                        feedback_list = feedback.getCompetencyByName(crewrotationId, search, status, n, recordsperpage);
                        if (feedback_list.size() > 0) {
                            FeedbackInfo cinfo = (FeedbackInfo) feedback_list.get(feedback_list.size() - 1);
                            count = cinfo.getTrackerId();
                            feedback_list.remove(feedback_list.size() - 1);
                        }
                    }
                    session.setAttribute("COUNT_LIST", count + "");
                    session.setAttribute("COMPETENCY_LIST", feedback_list);

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
                        next = ("<li class='next'><a href=\"javascript: searchFormAjaxCompetency('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                        nextclose = ("</i></a></li>");
                    } else if (n > 0 && feedbackCount > ((n * recordsperpage) + recordsperpage)) {
                        next = ("<li class='next'><a href=\"javascript: searchFormAjaxCompetency('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                        nextclose = ("</i></a></li>");
                        prev = ("<li class='prev'><a href=\"javascript: searchFormAjaxCompetency('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                        prevclose = ("</i></a></li>");
                    } else if (n > 0 && feedbackCount <= ((n * recordsperpage) + recordsperpage)) {
                        prev = ("<li class='prev'><a href=\"javascript: searchFormAjaxCompetency('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
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
                    if (total > 0)
                    {
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
                            str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFormAjaxCompetency('n', '0');\" title='First' style='height: 33px;'><i class='fa fa-angle-double-left'></i></a></li>");
                        }
                        str.append(prev + prevclose + "&nbsp;");
                        int a1 = test - showsizelist;
                        int a2 = test + showsizelist;
                        for (int ii = a1; ii <= a2; ii++) {
                            if (ii >= 0 && ii < noOfPages) {
                                if (ii == test) {
                                    str.append("<li class='wesl_active'><a href='#'> " + (ii + 1) + " </a></li>");
                                } else {
                                    str.append("<li><a href=\"javascript: searchFormAjaxCompetency('n', '" + ii + "');\" title=''> " + (ii + 1) + " </a></li>");
                                }
                            }
                        }
                        str.append(next + nextclose);
                        if (noOfPages > 1 && next_value != noOfPages) {
                            str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFormAjaxCompetency('n','" + (noOfPages - 1) + "');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
                        }
                        str.append("</ul></div></div></div>");

                        str.append("<div id='printArea'>");
                        str.append(thead);
                        str.append("<ul class='comp_list_detail' id='sort_id'>");

                        FeedbackInfo info;
                        int sub_status = 0;
                        String stclass = "", stvalue = "";
                        for (int i = 0; i < total; i++) {
                            info = (FeedbackInfo) feedback_list.get(i);
                            if (info != null) {
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
                                str.append("<li class='comp_name_label ord_list_1' onclick=\"javascript: viewSurvey('" + info.getTrackerId() + "');\">");
                                str.append("<span class='value_record'>");
                                str.append(info.getRole() != null ? info.getRole() : "");
                                str.append("</span><span class='know_ass'>Knowledge Assessment</span>");
                                str.append("</li>");
                                str.append("<li class='comp_by_label ord_list_2' onclick=\"javascript: viewSurvey('" + info.getTrackerId() + "');\">");
                                str.append("<span class='mob_show label_title'>Complete By</span> <span class='value_record'>");
                                str.append(info.getCompleteByDate() != null ? info.getCompleteByDate() : "");
                                str.append("</span>");
                                str.append("</li>");
                                str.append("<li class='comp_on_label ord_list_3' onclick=\"javascript: viewSurvey('" + info.getTrackerId() + "');\">");
                                str.append("<span class='mob_show label_title'>Completed On</span><span class='value_record'>" + (info.getOnlineDate() != null ? info.getOnlineDate() : "") + "</span>");
                                str.append("</li>");
                                str.append("<li class='comp_status_label ord_list_4' onclick=\"javascript: viewSurvey('" + info.getTrackerId() + "');\">");
                                str.append("<span class='mob_show label_title'>Status</span>");
                                str.append("<span class='value_record'>");
                                if (info.getStatus() == 4) {
                                    str.append("Competent");
                                } else if (info.getStatus() == 5) {
                                    str.append("Not Yet Competent");
                                } else if (info.getStatus() == 6) {
                                    str.append("Appealed");
                                } else if (info.getOnlineflag() == 2 || info.getStatus() == 3) {
                                    str.append("Assessment Complete");
                                } else if (info.getOnlineflag() == 1) {
                                    str.append("Saved as draft");
                                } else if (info.getOnlineflag() == 0) {
                                    str.append("Pending");
                                }
                                str.append("</span>");
                                str.append("</li>");
                                str.append("<li class='comp_actions_label action_list text-left ord_list_5'>");
                                str.append("<a href=\"javascript:;\" onclick=\"getCompetencyPendingModal('" + info.getTrackerId() + "');\"><img src='../assets/images/view.png'/> <span class='mob_show view_appeal'>View</span></a>");
                                str.append("<a href=\"javascript:;\" ");
                                if (info.getStatus() == 5) {
                                    str.append(" onclick=\"getCompetencyAppealModal('" + info.getTrackerId() + "');\" ");
                                }
                                str.append("class='disable_info" + (info.getStatus() == 5 ? "1" : "") + "'><img src='../assets/images/info.png' /> <span class='mob_show view_appeal'>Appeal</span></a>");
                                str.append("</li>");
                                str.append("</ul>");
                                str.append("</div>");
                                str.append("</li>");
                            }
                        }
                        str.append("</ul>");

                        str.append("<div class='wesl_pagination pagination-mg mt_15'>");
                        str.append("<div class='wesl_pg_rcds'>");
                        str.append("Page&nbsp;" + (next_value) + " of " + (noOfPages) + ", &nbsp;");
                        str.append("(" + value + " - " + value1 + " record(s) of " + feedbackCount + ")&nbsp;");
                        str.append("</div>");
                        str.append("<div class='wesl_No_pages'>");
                        str.append("<div class='loanreportTables_paginate paging_bootstrap_full_number'>");
                        str.append("<ul class='wesl_pagination'>");
                        if (noOfPages > 1 && next_value != 1) {
                            str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFormAjaxCompetency('n', '0');\" title='First' style='height: 33px;'><i class='fa fa-angle-double-left'></i></a></li>");
                        }
                        str.append(prev + prevclose + "&nbsp;");
                        for (int ii = a1; ii <= a2; ii++) {
                            if (ii >= 0 && ii < noOfPages) {
                                if (ii == test) {
                                    str.append("<li class='wesl_active'><a href='#'> " + (ii + 1) + " </a></li>");
                                } else {
                                    str.append("<li><a href=\"javascript: searchFormAjaxCompetency('n', '" + ii + "');\" title=''> " + (ii + 1) + " </a></li>");
                                }
                            }
                        }
                        str.append(next + nextclose);
                        if (noOfPages > 1 && next_value != noOfPages) {
                            str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFormAjaxCompetency('n','" + (noOfPages - 1) + "');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
                        }
                        str.append("</ul></div></div></div></div>");
                    } else {
                        str.append("<div class='col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12'>");
                        str.append("<div class='mt_15'>");
                        str.append("<ul class='comp_list_detail'>");
                        str.append("<li class='empty_record'>");
                        str.append("<div class='comp_list_main'>");
                        str.append("<ul>");
                        str.append("<li class='comp_name_label ord_list_1'>");
                        str.append("<span class='value_record'>");
                        str.append(feedback.getMainPath("record_not_found"));
                        str.append("</span>");
                        str.append("</li>");
                        str.append("</ul>");
                        str.append("</div>");
                        str.append("</li>");
                        str.append("</ul>");
                        str.append("</div>");
                        str.append("</div>");
                    }
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