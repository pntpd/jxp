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
            int showsizelist = cassessment.getCountList("show_size_list");
            StringBuilder sb = new StringBuilder();
            sb.append("<thead>");
            sb.append("<tr>");
            sb.append("<th width='7%'><span><b>ID</b> </span>");
            sb.append("<a href=\"javascript: sortForm('1', '2');\" id='img_1_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('1', '1');\" id='img_1_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='18%'><span><b>Name</b> </span>");
            sb.append("<a href=\"javascript: sortForm('2', '2');\" id='img_2_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('2', '1');\" id='img_2_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='17%'><span><b>Asset Type</b> </span>");
            sb.append("<a href=\"javascript: sortForm('5', '2');\" id='img_5_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('5', '1');\" id='img_5_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='18%'><span><b>Position-Rank</b> </span>");
            sb.append("<a href=\"javascript: sortForm('3', '2');\" id='img_3_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('3', '1');\" id='img_3_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='10%'><span><b>Verified On</b> </span>");
            sb.append("<a href=\"javascript: sortForm('4', '2');\" id='img_4_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('4', '1');\" id='img_4_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='10%'><span><b>Verified</b> </span></th>");
            sb.append("<th width='10%'><span><b>Assessment Status</b> </span></th>");
            sb.append("<th width='10%'><span><b>Scheduled</b> </span></th>");
            sb.append("<th width='6%' class='text-center' id='tech-companies-1-col-8'><span><b>Action</b></span></th>");
            sb.append("</tr>");
            sb.append("</thead>");
            String thead = sb.toString();
            sb.setLength(0);
            
            String search = request.getParameter("search") != null ? vobj.replacedesc(request.getParameter("search")) : "";
            String astatusIndexs = request.getParameter("astatusIndex") != null && !request.getParameter("astatusIndex").equals("") ? vobj.replaceint(request.getParameter("astatusIndex")) : "0";
            String vstatusIndexs = request.getParameter("vstatusIndex") != null && !request.getParameter("vstatusIndex").equals("") ? vobj.replaceint(request.getParameter("vstatusIndex")) : "0";
            String positionIndexs = request.getParameter("positionIndex") != null && !request.getParameter("positionIndex").equals("") ? vobj.replaceint(request.getParameter("positionIndex")) : "-1";
            String assettypeIndexs = request.getParameter("assettypeIndex") != null && !request.getParameter("assettypeIndex").equals("") ? vobj.replaceint(request.getParameter("assettypeIndex")) : "-1";
            String nextValue = request.getParameter("nextValue") != null && !request.getParameter("nextValue").equals("") ? vobj.replaceint(request.getParameter("nextValue")) : "0";
            String dodirects = request.getParameter("doDirect") != null && !request.getParameter("doDirect").equals("") ? vobj.replaceint(request.getParameter("doDirect")) : "-1";
            
            int n = Integer.parseInt(nextValue);
            int dodirect = Integer.parseInt(dodirects);
            int vstatusIndex = Integer.parseInt(vstatusIndexs);
            int astatusIndex = Integer.parseInt(astatusIndexs);
            int positionIndex = Integer.parseInt(positionIndexs);
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

                session.setAttribute("NEXTVALUE", next_value + "");
                int recordsperpage = cassessment.getCount();
                ArrayList cassessment_list = new ArrayList();
                int count = 0;
                if (next1.equals("p") || next1.equals("n")) {
                    cassessment_list = cassessment.getCassessmentByName(search, vstatusIndex, astatusIndex, positionIndex, assettypeIndex, n, recordsperpage);
                    if (cassessment_list.size() > 0) {
                        CassessmentInfo cinfo = (CassessmentInfo) cassessment_list.get(cassessment_list.size() - 1);
                        count = cinfo.getCassessmentId();
                        cassessment_list.remove(cassessment_list.size() - 1);
                    }
                } else {
                    cassessment_list = cassessment.getCassessmentByName(search, vstatusIndex, astatusIndex, positionIndex, assettypeIndex, n, recordsperpage);
                    if (cassessment_list.size() > 0) {
                        CassessmentInfo cinfo = (CassessmentInfo) cassessment_list.get(cassessment_list.size() - 1);
                        count = cinfo.getCassessmentId();
                        cassessment_list.remove(cassessment_list.size() - 1);
                    }
                }
                session.setAttribute("COUNT_LIST", count + "");
                session.setAttribute("CASSESSMENT_LIST", cassessment_list);

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
                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                } else if (n > 0 && cassessmentCount > ((n * recordsperpage) + recordsperpage)) {
                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                } else if (n > 0 && cassessmentCount <= ((n * recordsperpage) + recordsperpage)) {
                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
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
                    
                    CassessmentInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (CassessmentInfo) cassessment_list.get(i);
                        if (info != null) 
                        {
                            str.append("<tr>");
                            str.append("<td>" + (info.getIds() != null && !"".equals(info.getIds()) ? info.getIds() : "") + "</td>");
                            str.append("<td>" + (info.getName() != null && !"".equals(info.getName()) ? info.getName() : "") + "</td>");
                            str.append("<td>" + (info.getAssettypeName() != null && !"".equals(info.getAssettypeName()) ? info.getAssettypeName() : "") + "</td>");
                            str.append("<td>" + (info.getPosition() != null && !"".equals(info.getPosition()) ? info.getPosition() : "") + "</td>");
                            str.append("<td>" + (info.getVdate() != null && !"".equals(info.getVdate()) ? info.getVdate() : "") + "</td>");
                            str.append("<td>" + (info.getVflagstatus() != null && !"".equals(info.getVflagstatus()) ? info.getVflagstatus() : "") + "</td>");
                            str.append("<td>" + (info.getAflagstatus() != null && !"".equals(info.getAflagstatus()) ? info.getAflagstatus() : "") + "</td>");
                            str.append("<td class='assets_list text-center'>");
                            if (info.getScheduled() != null && !"".equals(info.getScheduled())) {
                                str.append("<a href='javascript: void(0);'>" + info.getScheduled() + "</a>");
                            }
                            str.append("</td>");
                            str.append("<td class='text-center' data-org-colspan='1' data-columns='tech-companies-1-col-8'>");
                            str.append("<div class='main-nav ass_list'>");
                            str.append("<ul>");
                            str.append("<li class='drop_down'>");
                            str.append("<a href='javascript:;' class='toggle'><i class='fas fa-ellipsis-v'></i></a>");
                            str.append("<div class='dropdown_menu'>");
                            str.append("<div class='dropdown-wrapper'>");
                            str.append("<div class='category-menu'>");
                            str.append("<a href=\"javascript: showDetailAssessNow('"+ info.getCassessmentId()+"');\">Assess Now</a>");
                            str.append("<a href=\"javascript: showDetail('"+ info.getCassessmentId()+"');\">Schedule Assessments</a>");
                            str.append("</div>");
                            str.append("</div>");
                            str.append("</div>");
                            str.append("</li>");
                            str.append("</ul>");
                            str.append("</div>");
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