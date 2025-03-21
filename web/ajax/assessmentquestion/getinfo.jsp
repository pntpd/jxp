<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.assessmentquestion.AssessmentQuestionInfo" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="assessmentquestion" class="com.web.jxp.assessmentquestion.AssessmentQuestion" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            int uid = 0;
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String editper = "N";
            if (uinfo != null) 
            {
                uid = uinfo.getUserId();
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
            }
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = assessmentquestion.getLocalIp();
            int showsizelist = assessmentquestion.getCountList("show_size_list");
            StringBuilder sb = new StringBuilder();
            sb.append("<thead>");
            sb.append("<tr>");
            sb.append("<th><span><b>Question Text</b> </span>");
            sb.append("<a href=\"javascript: sortForm('1', '2');\" id='img_1_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('1', '1');\" id='img_1_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th><span><b>Assessment Answer Type</b> </span>");
            sb.append("<a href=\"javascript: sortForm('2', '2');\" id='img_2_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('2', '1');\" id='img_2_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
             sb.append("<th><span><b>Assessment Parameter</b> </span>");
            sb.append("<a href=\"javascript: sortForm('3', '2');\" id='img_3_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('3', '1');\" id='img_3_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th><span><b>Status</b> </span> </th>");
            sb.append("<th class='text-right'><span><b>Actions</b></span></th>");
            sb.append("</tr>");
            sb.append("</thead>");
            String thead = sb.toString();
            sb.setLength(0);
            String search = request.getParameter("search") != null ? vobj.replacedesc(request.getParameter("search")) : "";
            String nextValue = request.getParameter("nextValue") != null && !request.getParameter("nextValue").equals("") ? vobj.replaceint(request.getParameter("nextValue")) : "0";
            String dodirects = request.getParameter("doDirect") != null && !request.getParameter("doDirect").equals("") ? vobj.replaceint(request.getParameter("doDirect")) : "-1";
            int n = Integer.parseInt(nextValue);
            int dodirect = Integer.parseInt(dodirects);
            if (request.getParameter("deleteVal") != null) 
            {
                StringBuilder str = new StringBuilder();
                String nextd = request.getParameter("nextDel") != null && !request.getParameter("nextDel").equals("") ? vobj.replaceint(request.getParameter("nextDel")) : "-1";
                String delete_ids = request.getParameter("deleteVal") != null && !request.getParameter("deleteVal").equals("") ? vobj.replaceint(request.getParameter("deleteVal")) : "0";
                String status_s = request.getParameter("status") != null && !request.getParameter("status").equals("") ? vobj.replaceint(request.getParameter("status")) : "1";
                int id = Integer.parseInt(delete_ids);
                int statusval = Integer.parseInt(status_s);
                int result = assessmentquestion.deleteAssessmentQuestion(id, uid, statusval, ipAddrStr, iplocal);
                String message = "Status updated successfully.";
                session.setAttribute("MESSAGE", message);
                int n1 = 0;
                int nextDel = Integer.parseInt(nextd);
                if (nextDel > 0 && nextDel == id) {
                    n = n - 1;
                }
                if (n - 1 <= 0) {
                    n = 1;
                }
                n1 = n - 1;
                session.setAttribute("NEXTVALUE", (n1 + 1) + "");
                int recordsperpage = assessmentquestion.getCount();
                ArrayList assessmentquestion_list = assessmentquestion.getAssessmentQuestionByName(search, n1, recordsperpage);
                int count = 0;
                if (assessmentquestion_list.size() > 0) {
                    AssessmentQuestionInfo cinfo = (AssessmentQuestionInfo) assessmentquestion_list.get(assessmentquestion_list.size() - 1);
                    count = cinfo.getAssessmentquestionId();
                    assessmentquestion_list.remove(assessmentquestion_list.size() - 1);
                }
                request.getSession().setAttribute("COUNT_LIST", count + "");
                session.setAttribute("ASSESSMENTQUESTION_LIST", assessmentquestion_list);
                int pageSize = count / recordsperpage;
                if (count % recordsperpage > 0) {
                    pageSize = pageSize + 1;
                }

                int assessmentquestionCount = count;
                String prev = "";
                String prevclose = "";
                String next = "";
                String nextclose = "";
                int last = 0;
                int total = assessmentquestion_list.size();
                str.append("<input type='hidden' name='totalpage' value='" + pageSize + "' />");
                str.append("<input type='hidden' name='nextValue' value='" + (n1 + 1) + "'/>");
                str.append("<input type='hidden' name='nextDel' value='" + total + "'/>");
                if (n1 == 0 && assessmentquestionCount > recordsperpage) {
                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                } else if (n1 > 0 && assessmentquestionCount > ((n1 * recordsperpage) + recordsperpage)) {
                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                } else if (n1 > 0 && assessmentquestionCount <= ((n1 * recordsperpage) + recordsperpage)) {
                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                    last = assessmentquestionCount;
                } else {
                    recordsperpage = assessmentquestionCount;
                }
                int value = (n1) * recordsperpage + 1;
                int value1 = last > 0 ? last : (n1) * recordsperpage + recordsperpage;
                int test = n1;
                int noOfPages = 1;
                if (recordsperpage > 0) {
                    noOfPages = assessmentquestionCount / recordsperpage;
                    if (assessmentquestionCount % recordsperpage > 0) {
                        noOfPages += 1;
                    }
                }
                if (total > 0) 
                {
                    str.append("<div class='wesl_pagination pagination-mg m_15'>");
                    str.append("<div class='wesl_pg_rcds'>");
                    str.append("Page&nbsp;" + (n1 + 1) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + assessmentquestionCount + ")&nbsp;");
                    str.append("</div>");
                    str.append("<div class='wesl_No_pages'>");
                    str.append("<div class='loanreportTables_paginate paging_bootstrap_full_number'>");
                    str.append("<ul class='wesl_pagination'>");
                    if (noOfPages > 1 && (n1 + 1) != 1) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFormAjax('n', '0');\" title='First' style='height: 33px;'><i class='fa fa-angle-double-left'></i></a></li>");
                    }
                    str.append(prev + prevclose + "&nbsp;");
                    int a1 = test - showsizelist;
                    int a2 = test + showsizelist;
                    for (int ii = a1; ii <= a2; ii++) {
                        if (ii >= 0 && ii < noOfPages) {
                            if (ii == test) {
                                str.append("<li class='wesl_active'><a href='javascript:;'> " + (ii + 1) + " </a></li>");
                            } else {
                                str.append("<li><a href=\"javascript: searchFormAjax('n', '" + ii + "');\" title=''> " + (ii + 1) + " </a></li>");
                            }
                        }
                    }
                    str.append(next + nextclose);
                    if (noOfPages > 1 && (n1 + 1) != noOfPages) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFormAjax('n','" + (noOfPages - 1) + "');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
                    }
                    str.append("</ul></div></div></div>");
                    str.append("<div class='col-lg-12'>");
                    str.append("<div class='table-rep-plugin sort_table'>");
                    str.append("<div class='table-responsive mb-0' data-bs-pattern='priority-columns'>");
                    str.append("<table id='tech-companies-1' class='table table-striped'>");
                    str.append(thead);
                    str.append("<tbody id = 'sort_id'>");
                    int status;
                    AssessmentQuestionInfo info;
                    for (int i = 0; i < total; i++)
                    {
                        info = (AssessmentQuestionInfo) assessmentquestion_list.get(i);
                        if (info != null) 
                        {
                            status = info.getStatus();
                            str.append("<tr class='hand_cursor' href='javascript: void(0);'>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+ info.getAssessmentquestionId()+"');\">" + (info.getName() != null ? info.getName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+ info.getAssessmentquestionId()+"');\">" + (info.getAssessmentAnswerTypeName() != null ? info.getAssessmentAnswerTypeName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+ info.getAssessmentquestionId()+"');\">" + (info.getAssessmentParameterName() != null ? info.getAssessmentParameterName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+ info.getAssessmentquestionId()+"');\">" + (assessmentquestion.getStatusById(info.getStatus())) + "</td>");
                            str.append("<td>");
                            str.append("<span class='switch_bth float-end'>");
                            str.append("<div class='form-check form-switch'>");
                            str.append("<input class='form-check-input' type='checkbox' role='switch' id='flexSwitchCheckDefault_" + (i) + "'");
                            if (!editper.equals("Y")) {
                                str.append("disabled='true'");
                            }
                            if (status == 1) {
                                str.append("checked");
                            }
                            str.append(" onclick=\"javascript: deleteForm('" + info.getAssessmentquestionId() + "', '" + status + "', '" + (i) + "');\" />");
                            str.append("</div>");
                            str.append("</span>");
                            if (editper.equals("Y") && info.getStatus() == 1) {
                                str.append("<a href=\"javascript: modifyForm('" + info.getAssessmentquestionId() + "');\" class='edit_mode float-end mr_15'><img src='../assets/images/pencil.png'/></a>");
                            }
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
                    str.append("Page&nbsp;" + (n1 + 1) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + assessmentquestionCount + ")&nbsp;");
                    str.append("</div>");
                    str.append("<div class='wesl_No_pages'>");
                    str.append("<div class='loanreportTables_paginate paging_bootstrap_full_number'>");
                    str.append("<ul class='wesl_pagination'>");
                    if (noOfPages > 1 && (n1 + 1) != 1) {
                        str.append("<li class='prev'><a href=\"javascript: searchFormAjax('n', '0');\" title='First' style='height: 33px;'><i class='fa fa-angle-double-left'></i></a></li>");
                    }
                    str.append(prev + prevclose + "&nbsp;");
                    for (int ii = a1; ii <= a2; ii++) {
                        if (ii >= 0 && ii < noOfPages) {
                            if (ii == test) {
                                str.append("<li class='wesl_active'><a href='javascript:;'> " + (ii + 1) + " </a></li>");
                            } else {
                                str.append("<li><a href=\"javascript: searchFormAjax('n', '" + ii + "');\" title=''> " + (ii + 1) + " </a></li>");
                            }
                        }
                    }
                    str.append(next + nextclose);
                    if (noOfPages > 1 && (n1 + 1) != noOfPages) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFormAjax('n','" + (noOfPages - 1) + "');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
                    }
                    str.append("</ul></div></div></div>");
                } else {
                    str.append("<div class='panel-body'>");
                    str.append("<div class='table-responsive'>");
                    str.append("<table class='table table-bordered table-hover' id='dataTables-example'>");
                    str.append("<tbody>");
                    str.append("<tr class='grid1'>");
                    str.append("<td>" + assessmentquestion.getMainPath("record_not_found") + "</td>");
                    str.append("</tr>");
                    str.append("</tbody>");
                    str.append("</table>");
                    str.append("</div>");
                    str.append("</div>");
                }
                String s1 = str.toString().intern() + "##" + message;
                response.getWriter().write(s1);
            } 
            else if (request.getParameter("deleteVal") == null) 
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
                int recordsperpage = assessmentquestion.getCount();
                ArrayList assessmentquestion_list = new ArrayList();
                int count = 0;
                if (next1.equals("p") || next1.equals("n")) {
                    assessmentquestion_list = assessmentquestion.getAssessmentQuestionByName(search, n, recordsperpage);
                    if (assessmentquestion_list.size() > 0) {
                        AssessmentQuestionInfo cinfo = (AssessmentQuestionInfo) assessmentquestion_list.get(assessmentquestion_list.size() - 1);
                        count = cinfo.getAssessmentquestionId();
                        assessmentquestion_list.remove(assessmentquestion_list.size() - 1);
                    }
                } else {
                    assessmentquestion_list = assessmentquestion.getAssessmentQuestionByName(search, n, recordsperpage);
                    if (assessmentquestion_list.size() > 0) {
                        AssessmentQuestionInfo cinfo = (AssessmentQuestionInfo) assessmentquestion_list.get(assessmentquestion_list.size() - 1);
                        count = cinfo.getAssessmentquestionId();
                        assessmentquestion_list.remove(assessmentquestion_list.size() - 1);
                    }
                }
                session.setAttribute("COUNT_LIST", count + "");
                session.setAttribute("ASSESSMENTQUESTION_LIST", assessmentquestion_list);

                int pageSize = count / recordsperpage;
                if (count % recordsperpage > 0) {
                    pageSize = pageSize + 1;
                }

                int assessmentquestionCount = count;
                String prev = "";
                String prevclose = "";
                String next = "";
                String nextclose = "";
                int last = 0;
                int total = assessmentquestion_list.size();
                str.append("<input type='hidden' name='totalpage' value='" + pageSize + "' />");
                str.append("<input type='hidden' name='nextDel' value='" + total + "'/>");
                str.append("<input type='hidden' name='nextValue' value='" + (next_value) + "'/>");
                if (n == 0 && assessmentquestionCount > recordsperpage) {
                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                } else if (n > 0 && assessmentquestionCount > ((n * recordsperpage) + recordsperpage)) {
                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                } else if (n > 0 && assessmentquestionCount <= ((n * recordsperpage) + recordsperpage)) {
                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                    last = assessmentquestionCount;
                } else {
                    recordsperpage = assessmentquestionCount;
                }
                int value = n * recordsperpage + 1;
                int value1 = last > 0 ? last : n * recordsperpage + recordsperpage;
                int test = n;
                int noOfPages = 1;
                if (recordsperpage > 0) {
                    noOfPages = assessmentquestionCount / recordsperpage;
                    if (assessmentquestionCount % recordsperpage > 0) {
                        noOfPages += 1;
                    }
                }
                if (total > 0)
                {
                    str.append("<div class='wesl_pagination pagination-mg m_15'>");
                    str.append("<div class='wesl_pg_rcds'>");
                    str.append("Page&nbsp;" + (next_value) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + assessmentquestionCount + ")&nbsp;");
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
                    str.append("<div class='col-lg-12' id='printArea'>");
                    str.append("<div class='table-rep-plugin sort_table'>");
                    str.append("<div class='table-responsive mb-0' data-bs-pattern='priority-columns'>");
                    str.append("<table id='tech-companies-1' class='table table-striped'>");
                    str.append(thead);
                    str.append("<tbody id = 'sort_id'>");
                    int status;
                    AssessmentQuestionInfo info;
                    for (int i = 0; i < total; i++)
                    {
                        info = (AssessmentQuestionInfo) assessmentquestion_list.get(i);
                        if (info != null)
                        {
                            status = info.getStatus();
                            str.append("<tr class='hand_cursor' href='javascript: void(0);'>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+ info.getAssessmentquestionId()+"');\">" + (info.getName() != null ? info.getName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+ info.getAssessmentquestionId()+"');\">" + (info.getAssessmentAnswerTypeName() != null ? info.getAssessmentAnswerTypeName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+ info.getAssessmentquestionId()+"');\">" + (info.getAssessmentParameterName() != null ? info.getAssessmentParameterName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+ info.getAssessmentquestionId()+"');\">" + (assessmentquestion.getStatusById(info.getStatus())) + "</td>");
                            str.append("<td>");
                            str.append("<span class='switch_bth float-end'>");
                            str.append("<div class='form-check form-switch'>");
                            str.append("<input class='form-check-input' type='checkbox' role='switch' id='flexSwitchCheckDefault_" + (i) + "'");
                            if (!editper.equals("Y")) {
                                str.append("disabled='true'");
                            }
                            if (status == 1) {
                                str.append("checked");
                            }
                            str.append(" onclick=\"javascript: deleteForm('" + info.getAssessmentquestionId() + "', '" + status + "', '" + (i) + "');\" />");
                            str.append("</div>");
                            str.append("</span>");
                            if (editper.equals("Y") && info.getStatus() == 1) {
                                str.append("<a href=\"javascript: modifyForm('" + info.getAssessmentquestionId() + "');\" class='edit_mode float-end mr_15'><img src='../assets/images/pencil.png'/></a>");
                            }
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
                    str.append("Page&nbsp;" + (next_value) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + assessmentquestionCount + ")&nbsp;");
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
                    str.append("</ul></div></div></div>");
                } else {
                    str.append("<div class='panel-body'>");
                    str.append("<div class='table-responsive'>");
                    str.append("<table class='table table-bordered table-hover' id='dataTables-example'>");
                    str.append("<tbody>");
                    str.append("<tr class='grid1'>");
                    str.append("<td>" + assessmentquestion.getMainPath("record_not_found") + "</td>");
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