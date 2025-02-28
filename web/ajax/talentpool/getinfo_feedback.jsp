<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null)
        {
            int showsizelist = talentpool.getCountList("show_size_list");
            StringBuilder sb = new StringBuilder();
            sb.append("<thead>");
            sb.append("<tr>");           
            sb.append("<th width='%'><span><b>Client - Asset</b> </span></th>");
            sb.append("<th width='%'><span><b>Position - Rank</b> </span></th>");
            sb.append("<th width='%'><span><b>Feedback Name</b> </span></th>");
            sb.append("<th width='%'><span><b>Sent On</b> </span></th>");
            sb.append("<th width='%'><span><b>Filled On</b> </span></th>");
            sb.append("<th width='%' class='text-center'><span><b>Actions</b></span></th>");
            sb.append("</tr>");
            sb.append("</thead>");
            String thead = sb.toString();
            sb.setLength(0);
            
            String nextFeedbackValue = request.getParameter("nextFeedbackValue") != null && !request.getParameter("nextFeedbackValue").equals("") ? vobj.replaceint(request.getParameter("nextFeedbackValue")) : "0";
            String dodirects = request.getParameter("doDirect") != null && !request.getParameter("doDirect").equals("") ? vobj.replaceint(request.getParameter("doDirect")) : "-1";
            String statusIds = request.getParameter("statusId") != null && !request.getParameter("statusId").equals("") ? vobj.replaceint(request.getParameter("statusId")) : "0";
            String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "0";
            int candidateId = Integer.parseInt(candidateIds);
            int statusId = Integer.parseInt(statusIds);
            int n = Integer.parseInt(nextFeedbackValue);
            int dodirect = Integer.parseInt(dodirects);
            if (true) 
            {
                StringBuilder str = new StringBuilder();
                int m = n;
                int next_feedbackvalue = 0;
                String next1 = request.getParameter("next");
                if (next1 != null && next1.equals("n")) {
                    if (dodirect >= 0) {
                        n = dodirect;
                        next_feedbackvalue = dodirect + 1;
                    } else {
                        n = n;
                        next_feedbackvalue = m + 1;
                    }
                } else if (next1 != null && next1.equals("p")) {
                    n = n - 2;
                    next_feedbackvalue = m - 1;
                } else if (next1 != null && next1.equals("s")) {
                    n = 0;
                    next_feedbackvalue = 1;
                }
                session.setAttribute("NEXTFEEDBACKVALUE", next_feedbackvalue + "");
                int recordsperpage = talentpool.getCount();
                ArrayList talentpool_list = new ArrayList();
                int count = 0;
                if (next1.equals("p") || next1.equals("n")) {
                    talentpool_list = talentpool.getWellnessFeedbackhistory(candidateId, statusId, n, recordsperpage);
                    if (talentpool_list.size() > 0) {
                        TalentpoolInfo cinfo = (TalentpoolInfo) talentpool_list.get(talentpool_list.size() - 1);
                        count = cinfo.getCandidateId();
                        talentpool_list.remove(talentpool_list.size() - 1);
                    }
                } else {
                    talentpool_list = talentpool.getWellnessFeedbackhistory(candidateId, statusId, n, recordsperpage);
                    if (talentpool_list.size() > 0) {
                        TalentpoolInfo cinfo = (TalentpoolInfo) talentpool_list.get(talentpool_list.size() - 1);
                        count = cinfo.getCandidateId();
                        talentpool_list.remove(talentpool_list.size() - 1);
                    }
                }
                session.setAttribute("COUNT_FEEDBACKLIST", count + "");
                session.setAttribute("WELLNESSFEEDBACKHISTLIST", talentpool_list);

                int pageSize = count / recordsperpage;
                if (count % recordsperpage > 0) {
                    pageSize = pageSize + 1;
                }

                int talentpoolCount = count;
                String prev = "";
                String prevclose = "";
                String nextFeedback = "";
                String nextclose = "";
                int last = 0;
                int total = talentpool_list.size();
                str.append("<input type='hidden' name='totalpage' value='" + pageSize + "' />");
                str.append("<input type='hidden' name='nextDel' value='" + total + "'/>");
                str.append("<input type='hidden' name='nextFeedbackValue' value='" + (next_feedbackvalue) + "'/>");
                if (n == 0 && talentpoolCount > recordsperpage) {
                    nextFeedback = ("<li class='next'><a href=\"javascript: searchFeedback('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                } else if (n > 0 && talentpoolCount > ((n * recordsperpage) + recordsperpage)) {
                    nextFeedback = ("<li class='next'><a href=\"javascript: searchFeedback('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                    prev = ("<li class='prev'><a href=\"javascript: searchFeedback('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                } else if (n > 0 && talentpoolCount <= ((n * recordsperpage) + recordsperpage)) {
                    prev = ("<li class='prev'><a href=\"javascript: searchFeedback('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                    last = talentpoolCount;
                } else {
                    recordsperpage = talentpoolCount;
                }
                int value = n * recordsperpage + 1;
                int value1 = last > 0 ? last : n * recordsperpage + recordsperpage;
                int test = n;
                int noOfPages = 1;
                if (recordsperpage > 0) {
                    noOfPages = talentpoolCount / recordsperpage;
                    if (talentpoolCount % recordsperpage > 0) {
                        noOfPages += 1;
                    }
                }
                if (total > 0) 
                {
                    str.append("<div class='wesl_pagination pagination-mg m_15'>");
                    str.append("<div class='wesl_pg_rcds'>");
                    str.append("Page&nbsp;" + (next_feedbackvalue) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + talentpoolCount + ")&nbsp;");
                    str.append("</div>");
                    str.append("<div class='wesl_No_pages'>");
                    str.append("<div class='loanreportTables_paginate paging_bootstrap_full_number'>");
                    str.append("<ul class='wesl_pagination'>");
                    if (noOfPages > 1 && next_feedbackvalue != 1) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFeedback('n', '0');\" title='First' style='height: 33px;'><i class='fa fa-angle-double-left'></i></a></li>");
                    }
                    str.append(prev + prevclose + "&nbsp;");
                    int a1 = test - showsizelist;
                    int a2 = test + showsizelist;
                    for (int ii = a1; ii <= a2; ii++) {
                        if (ii >= 0 && ii < noOfPages) {
                            if (ii == test) {
                                str.append("<li class='wesl_active'><a href='#'> " + (ii + 1) + " </a></li>");
                            } else {
                                str.append("<li><a href=\"javascript: searchFeedback('n', '" + ii + "');\" title=''> " + (ii + 1) + " </a></li>");
                            }
                        }
                    }
                    str.append(nextFeedback + nextclose);
                    if (noOfPages > 1 && next_feedbackvalue != noOfPages) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFeedback('n','" + (noOfPages - 1) + "');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
                    }
                    str.append("</ul></div></div></div>");
                    str.append("<div class='col-lg-12' id='printArea'>");
                    str.append("<div class='table-rep-plugin sort_table'>");
                    str.append("<div class='table-responsive mb-0' data-bs-pattern='priority-columns'>");
                    str.append("<table id='tech-companies-1' class='table table-striped'>");
                    str.append(thead);
                    str.append("<tbody id = 'sort_id'>");
                    
                    TalentpoolInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (TalentpoolInfo) talentpool_list.get(i);
                        if (info != null) 
                        {
                            str.append("<tr>");
                            str.append("<td>" + (info.getClientName() != null ? info.getClientName() : "") + "</td>");
                            str.append("<td>" + (info.getPosition() != null ? info.getPosition() : "") + "</td>");
                            str.append("<td>" + (info.getFeedback() != null ? info.getFeedback() : "") + "</td>");
                            str.append("<td>" + (info.getSenton() != null ? info.getSenton() : "") + "</td>");
                            str.append("<td>" + (info.getStatusValue() != null ? info.getStatusValue() : "") + "</td>");
                            str.append("<td class='action_column text-center'>");
                            str.append("<a data-bs-toggle='modal' data-bs-target='#medical_emer_details_modal_01' onclick=\"javascript: setQuestionModal('" + info.getSurveyId()+ "');\"><img src='../assets/images/view.png'/></a>");
                            str.append("</td>");
                            str.append("</tr>");
                        }
                    }
                    str.append("</tbody>");
                    str.append("</table>");
                    str.append("</div>");
                    str.append("</div>");
                    str.append("</div>");

                    str.append("<div class='wesl_pagination pagination-mg mt_15'>");
                    str.append("<div class='wesl_pg_rcds'>");
                    str.append("Page&nbsp;" + (next_feedbackvalue) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + talentpoolCount + ")&nbsp;");
                    str.append("</div>");
                    str.append("<div class='wesl_No_pages'>");
                    str.append("<div class='loanreportTables_paginate paging_bootstrap_full_number'>");
                    str.append("<ul class='wesl_pagination'>");
                    if (noOfPages > 1 && next_feedbackvalue != 1) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFeedback('n', '0');\" title='First' style='height: 33px;'><i class='fa fa-angle-double-left'></i></a></li>");
                    }
                    str.append(prev + prevclose + "&nbsp;");
                    for (int ii = a1; ii <= a2; ii++) {
                        if (ii >= 0 && ii < noOfPages) {
                            if (ii == test) {
                                str.append("<li class='wesl_active'><a href='#'> " + (ii + 1) + " </a></li>");
                            } else {
                                str.append("<li><a href=\"javascript: searchFeedback('n', '" + ii + "');\" title=''> " + (ii + 1) + " </a></li>");
                            }
                        }
                    }
                    str.append(nextFeedback + nextclose);
                    if (noOfPages > 1 && next_feedbackvalue != noOfPages) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFeedback('n','" + (noOfPages - 1) + "');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
                    }
                    str.append("</ul></div></div></div>");
                } else {
                    str.append("<div class='panel-body'>");
                    str.append("<div class='table-responsive'>");
                    str.append("<table class='table table-bordered table-hover' id='dataTables-example'>");
                    str.append("<tbody>");
                    str.append("<tr class='grid1'>");
                    str.append("<td>" + talentpool.getMainPath("record_not_found") + "</td>");
                    str.append("</tr>");
                    str.append("</tbody>");
                    str.append("</table>");
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
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>