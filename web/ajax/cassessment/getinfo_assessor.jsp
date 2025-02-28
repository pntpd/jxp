<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.cassessment.CassessmentInfo" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="cassessment" class="com.web.jxp.cassessment.Cassessment" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            int uid = 0, assessor = 0;
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String per = "";
            if (uinfo != null) 
            {
                uid = uinfo.getUserId();
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                assessor = uinfo.getAssessor();
            }            
            int assessorId = 0;
            if (assessor > 0) {
                assessorId = uid;
            } else if (per.equals("Y")) {
                assessorId = -1;
            }
            int showsizelist = cassessment.getCountList("show_size_list");
            StringBuilder sb = new StringBuilder();
            sb.append("<thead>");
            sb.append("<tr>");
            sb.append("<th class='text-left' width='9%'><span><b>Date</b> </span>");
            sb.append("<a href=\"javascript: sortFormAssessor('1', '2');\" id='img_1_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortFormAssessor('1', '1');\" id='img_1_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th class='text-left' width='9%'><span><b>Time</b> </span>");
            sb.append("<a href=\"javascript: sortFormAssessor('2', '2');\" id='img_2_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortFormAssessor('2', '1');\" id='img_2_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th class='text-left' width='13%'><span><b>Name</b> </span>");
            sb.append("<a href=\"javascript: sortFormAssessor('3', '2');\" id='img_3_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortFormAssessor('3', '1');\" id='img_3_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th class='text-left' width='13%'><span><b>Assessment</b> </span>");
            sb.append("<a href=\"javascript: sortFormAssessor('4', '2');\" id='img_4_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortFormAssessor('4', '1');\" id='img_4_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th class='text-left' width='13%'><span><b>Asset Type</b> </span>");
            sb.append("<a href=\"javascript: sortFormAssessor('6', '2');\" id='img_6_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortFormAssessor('6', '1');\" id='img_6_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th class='text-left' width='14%'><span><b>Position-Rank</b> </span>");
            sb.append("<a href=\"javascript: sortFormAssessor('5', '2');\" id='img_5_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortFormAssessor('5', '1');\" id='img_5_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th class='text-left' width='6%'><span><b>Status</b> </span></th>");
            sb.append("<th class='text-center' width='7%'><span><b>Min Score</b> </span></th>");
            sb.append("<th width='8%'><span><b>Assessor</b> </span></th>");
            sb.append("<th class='text-center' width='8%'><span><b>Score Obtained</b> </span></th>");
            sb.append("</tr>");
            sb.append("</thead>");
            
            String thead = sb.toString();
            sb.setLength(0);
            
            String search = request.getParameter("search") != null ? vobj.replacedesc(request.getParameter("search")) : "";
            String passessmentIndexs = request.getParameter("passessmentIndex") != null && !request.getParameter("passessmentIndex").equals("") ? vobj.replaceint(request.getParameter("passessmentIndex")) : "0";
            String assessorIndexs = request.getParameter("assessorIndex") != null && !request.getParameter("assessorIndex").equals("") ? vobj.replaceint(request.getParameter("assessorIndex")) : "0";
            String aPositionIndexs = request.getParameter("aPositionIndex") != null && !request.getParameter("aPositionIndex").equals("") ? vobj.replaceint(request.getParameter("aPositionIndex")) : "-1";
            String assettypeIndexs = request.getParameter("assettypeIndex") != null && !request.getParameter("assettypeIndex").equals("") ? vobj.replaceint(request.getParameter("assettypeIndex")) : "-1";
            String nextValue = request.getParameter("nextValue") != null && !request.getParameter("nextValue").equals("") ? vobj.replaceint(request.getParameter("nextValue")) : "0";
            String dodirects = request.getParameter("doDirect") != null && !request.getParameter("doDirect").equals("") ? vobj.replaceint(request.getParameter("doDirect")) : "-1";
            int n = Integer.parseInt(nextValue);
            int dodirect = Integer.parseInt(dodirects);
            int passessmentIndex = Integer.parseInt(passessmentIndexs);
            int assessorIndex = Integer.parseInt(assessorIndexs);
            int aPositionIndex = Integer.parseInt(aPositionIndexs);
            int assettypeIndex = Integer.parseInt(assettypeIndexs);
            if (request.getParameter("search") != null) 
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

                session.setAttribute("NEXTVALUEA", next_value + "");
                int recordsperpage = cassessment.getCount();
                ArrayList cassessment_list = new ArrayList();

                int count = 0;
                if (next1.equals("p") || next1.equals("n")) {
                    cassessment_list = cassessment.getCassessmentByNameforAssessor(search, aPositionIndex, passessmentIndex, assessorIndex, assessorId, assettypeIndex, n, recordsperpage);
                    if (cassessment_list.size() > 0) {
                        CassessmentInfo cinfo = (CassessmentInfo) cassessment_list.get(cassessment_list.size() - 1);
                        count = cinfo.getCassessmentId();
                        cassessment_list.remove(cassessment_list.size() - 1);
                    }
                } else {
                    cassessment_list = cassessment.getCassessmentByNameforAssessor(search, aPositionIndex, passessmentIndex, assessorIndex, assessorId, assettypeIndex, n, recordsperpage);
                    if (cassessment_list.size() > 0) {
                        CassessmentInfo cinfo = (CassessmentInfo) cassessment_list.get(cassessment_list.size() - 1);
                        count = cinfo.getCassessmentId();
                        cassessment_list.remove(cassessment_list.size() - 1);
                    }
                }
                session.setAttribute("COUNT_LISTA", count + "");
                session.setAttribute("ASSESSOR_LIST", cassessment_list);

                int pageSize = count / recordsperpage;
                if (count % recordsperpage > 0) {
                    pageSize = pageSize + 1;
                }

                int cassessmentCount = count;
                String prev = "";
                String prevclose = "";
                String next = "";
                String nextclose = "";
                int last = 0;
                int total = cassessment_list.size();
                str.append("<input type='hidden' name='totalpage' value='" + pageSize + "' />");
                str.append("<input type='hidden' name='nextDel' value='" + total + "'/>");
                str.append("<input type='hidden' name='nextValue' value='" + (next_value) + "'/>");
                if (n == 0 && cassessmentCount > recordsperpage) {
                    next = ("<li class='next'><a href=\"javascript: searchFormAjaxAssessor('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                } else if (n > 0 && cassessmentCount > ((n * recordsperpage) + recordsperpage)) {
                    next = ("<li class='next'><a href=\"javascript: searchFormAjaxAssessor('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjaxAssessor('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                } else if (n > 0 && cassessmentCount <= ((n * recordsperpage) + recordsperpage)) {
                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjaxAssessor('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                    last = cassessmentCount;
                } else {
                    recordsperpage = cassessmentCount;
                }
                int value = n * recordsperpage + 1;
                int value1 = last > 0 ? last : n * recordsperpage + recordsperpage;
                int test = n;
                int noOfPages = 1;
                if (recordsperpage > 0) {
                    noOfPages = cassessmentCount / recordsperpage;
                    if (cassessmentCount % recordsperpage > 0) {
                        noOfPages += 1;
                    }
                }
                if (total > 0) 
                {
                    str.append("<div class='wesl_pagination pagination-mg m_15'>");
                    str.append("<div class='wesl_pg_rcds'>");
                    str.append("Page&nbsp;" + (next_value) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + cassessmentCount + ")&nbsp;");
                    str.append("</div>");
                    str.append("<div class='wesl_No_pages'>");
                    str.append("<div class='loanreportTables_paginate paging_bootstrap_full_number'>");
                    str.append("<ul class='wesl_pagination'>");
                    if (noOfPages > 1 && next_value != 1) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFormAjaxAssessor('n', '0');\" title='First' style='height: 33px;'><i class='fa fa-angle-double-left'></i></a></li>");
                    }
                    str.append(prev + prevclose + "&nbsp;");
                    int a1 = test - showsizelist;
                    int a2 = test + showsizelist;
                    for (int ii = a1; ii <= a2; ii++) {
                        if (ii >= 0 && ii < noOfPages) {
                            if (ii == test) {
                                str.append("<li class='wesl_active'><a href='#'> " + (ii + 1) + " </a></li>");
                            } else {
                                str.append("<li><a href=\"javascript: searchFormAjaxAssessor('n', '" + ii + "');\" title=''> " + (ii + 1) + " </a></li>");
                            }
                        }
                    }
                    str.append(next + nextclose);
                    if (noOfPages > 1 && next_value != noOfPages) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFormAjaxAssessor('n','" + (noOfPages - 1) + "');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
                    }
                    str.append("</ul></div></div></div>");
                    str.append("<div class='col-lg-12' id='printArea'>");
                    str.append("<div class='table-rep-plugin sort_table'>");
                    str.append("<div class='table-responsive mb-0' data-bs-pattern='priority-columns'>");
                    str.append("<table id='tech-companies-1' class='table table-striped'>");
                    str.append(thead);
                    str.append("<tbody id = 'sort_id'>");
                    
                    CassessmentInfo info;
                    for (int i = 0; i < cassessment_list.size(); i++) 
                    {
                        info = (CassessmentInfo) cassessment_list.get(i);
                        if (info != null) 
                        {
                            int status = info.getStatus();
                            String link = ";";
                            if (status == 1) {
                                link = "showAssessorDetail('" + info.getCandidateId() + "','" + info.getCassessmentId() + "');";
                            }
                            str.append("<tr href='javascript: void(0);' >");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript:" + link + ";\">" + (info.getDate() != null && !"".equals(info.getDate()) ? info.getDate() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: " + link + ";\">" + (info.getTime() != null && !"".equals(info.getTime()) ? info.getTime() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: " + link + ";\">" + (info.getName() != null && !"".equals(info.getName()) ? info.getName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: " + link + ";\">" + (info.getAssessmentName() != null && !"".equals(info.getAssessmentName()) ? info.getAssessmentName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: " + link + ";\">" + (info.getAssettypeName() != null && !"".equals(info.getAssettypeName()) ? info.getAssettypeName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: " + link + ";\">" + (info.getPosition() != null && !"".equals(info.getPosition()) ? info.getPosition() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: " + link + ";\">" + (info.getAssStatus() != null && !"".equals(info.getAssStatus()) ? info.getAssStatus() : "") + "</td>");
                            str.append("<td class='hand_cursor text-center' href='javascript: void(0);' onclick=\"javascript: " + link + ";\">" + (info.getMinScore()) + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: " + link + ";\">" + (info.getAssessorName() != null && !"".equals(info.getAssessorName()) ? info.getAssessorName() : "") + "</td>");
                            str.append("<td class='hand_cursor text-center' href='javascript: void(0);' onclick=\"javascript: " + link + ";\">");
                            if (status == 1) {
                                str.append(" - ");
                            } else {
                                str.append("<span ");
                                if (status == 3) {
                                    str.append("class='red_mark' ");
                                }
                                str.append(">" + info.getMarks() + "</span> ");
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
                    str.append("(" + value + " - " + value1 + " record(s) of " + cassessmentCount + ")&nbsp;");
                    str.append("</div>");
                    str.append("<div class='wesl_No_pages'>");
                    str.append("<div class='loanreportTables_paginate paging_bootstrap_full_number'>");
                    str.append("<ul class='wesl_pagination'>");
                    if (noOfPages > 1 && next_value != 1) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFormAjaxAssessor('n', '0');\" title='First' style='height: 33px;'><i class='fa fa-angle-double-left'></i></a></li>");
                    }
                    str.append(prev + prevclose + "&nbsp;");
                    for (int ii = a1; ii <= a2; ii++) {
                        if (ii >= 0 && ii < noOfPages) {
                            if (ii == test) {
                                str.append("<li class='wesl_active'><a href='#'> " + (ii + 1) + " </a></li>");
                            } else {
                                str.append("<li><a href=\"javascript: searchFormAjaxAssessor('n', '" + ii + "');\" title=''> " + (ii + 1) + " </a></li>");
                            }
                        }
                    }
                    str.append(next + nextclose);
                    if (noOfPages > 1 && next_value != noOfPages) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFormAjaxAssessor('n','" + (noOfPages - 1) + "');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
                    }
                    str.append("</ul></div></div></div>");

                } else {
                    str.append("<div class='col-lg-12' id='printArea'>");
                    str.append("<div class='table-rep-plugin sort_table'>");
                    str.append("<div class='table-responsive mb-0' data-bs-pattern='priority-columns'>");
                    str.append("<table id='tech-companies-1' class='table table-striped'>");
                    str.append("<tbody>");
                    str.append("<tr>");
                    str.append("<td><b>" + cassessment.getMainPath("record_not_found") + "</b></td>");
                    str.append("</tr>");
                    str.append("</tbody>");
                    str.append("</table>");
                    str.append("</div>");
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