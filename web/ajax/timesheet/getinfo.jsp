<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.timesheet.TimesheetInfo" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="timesheet" class="com.web.jxp.timesheet.Timesheet" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            int allclient = 0;
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String per = "", cids = "", assetids = "";
            if (uinfo != null) 
            {
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                cids = uinfo.getCids();
                allclient = uinfo.getAllclient();
                assetids = uinfo.getAssetids();
            }
            int showsizelist = timesheet.getCountList("show_size_list");
            StringBuilder sb = new StringBuilder();
            sb.append("<thead>");
            sb.append("<tr>");
            sb.append("<th width='30%'>");
            sb.append("<span><b>Client</b></span>");
            sb.append("<a href=\"javascript: sortForm('1', '2');\" id='img_1_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('1', '1');\" id='img_1_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='30%'>");
            sb.append("<span><b>Asset</b></span>");
            sb.append("<a href=\"javascript: sortForm('2', '2');\" id='img_2_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('2', '1');\" id='img_2_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='15%' class='text-center'>");
            sb.append("<span><b>Schedule</b></span>");
            sb.append("<a href=\"javascript: sortForm('3', '2');\" id='img_3_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('3', '1');\" id='img_3_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='15%' class='text-center'>");
            sb.append("<span><b>Timesheets</b></span>");
            sb.append("<a href=\"javascript: sortForm('4', '2');\" id='img_4_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('4', '1');\" id='img_4_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("</tr>");
            sb.append("</thead>");
            String thead = sb.toString();
            sb.setLength(0);
            
            String nextValue = request.getParameter("nextValue") != null && !request.getParameter("nextValue").equals("") ? vobj.replaceint(request.getParameter("nextValue")) : "0";
            String dodirects = request.getParameter("doDirect") != null && !request.getParameter("doDirect").equals("") ? vobj.replaceint(request.getParameter("doDirect")) : "-1";
            String clientIdIndexs = request.getParameter("clientIdIndex") != null && !request.getParameter("clientIdIndex").equals("") ? vobj.replaceint(request.getParameter("clientIdIndex")) : "-1";
            String assetIdIndexs = request.getParameter("assetIdIndex") != null && !request.getParameter("assetIdIndex").equals("") ? vobj.replaceint(request.getParameter("assetIdIndex")) : "-1";
            String type_s = request.getParameter("type") != null && !request.getParameter("type").equals("") ? vobj.replaceint(request.getParameter("type")) : "-1";
            int clientIdIndex = Integer.parseInt(clientIdIndexs);
            int type = Integer.parseInt(type_s);
            int assetIdIndex = Integer.parseInt(assetIdIndexs);
            int n = Integer.parseInt(nextValue);
            int dodirect = Integer.parseInt(dodirects); 
            if (request.getParameter("deleteVal") == null) 
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
                int recordsperpage = timesheet.getCount();
                ArrayList timesheet_list = new ArrayList();
                int count = 0;
                if (next1.equals("p") || next1.equals("n")) {
                    timesheet_list = timesheet.getTimesheetByName( n, recordsperpage,allclient, per, cids, assetids,clientIdIndex, assetIdIndex, type);
                    if (timesheet_list.size() > 0) {
                        TimesheetInfo cinfo = (TimesheetInfo) timesheet_list.get(timesheet_list.size() - 1);
                        count = cinfo.getAssetId();
                        timesheet_list.remove(timesheet_list.size() - 1);
                    }
                } else {
                    timesheet_list = timesheet.getTimesheetByName( n, recordsperpage,allclient, per, cids, assetids,clientIdIndex, assetIdIndex, type);
                    if (timesheet_list.size() > 0) {
                        TimesheetInfo cinfo = (TimesheetInfo) timesheet_list.get(timesheet_list.size() - 1);
                        count = cinfo.getAssetId();
                        timesheet_list.remove(timesheet_list.size() - 1);
                    }
                }
                session.setAttribute("COUNT_LIST", count + "");
                session.setAttribute("TIMESHEET_LIST", timesheet_list);

                int pageSize = count / recordsperpage;
                if (count % recordsperpage > 0) {
                    pageSize = pageSize + 1;
                }

                int timesheetCount = count;
                String prev = "";
                String prevclose = "";
                String next = "";
                String nextclose = "";
                int last = 0;
                int total = timesheet_list.size();
                str.append("<input type='hidden' name='totalpage' value='" + pageSize + "' />");
                str.append("<input type='hidden' name='nextDel' value='" + total + "'/>");
                str.append("<input type='hidden' name='nextValue' value='" + (next_value) + "'/>");
                if (n == 0 && timesheetCount > recordsperpage) {
                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                } else if (n > 0 && timesheetCount > ((n * recordsperpage) + recordsperpage)) {
                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                } else if (n > 0 && timesheetCount <= ((n * recordsperpage) + recordsperpage)) {
                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                    last = timesheetCount;
                } else {
                    recordsperpage = timesheetCount;
                }
                int value = n * recordsperpage + 1;
                int value1 = last > 0 ? last : n * recordsperpage + recordsperpage;
                int test = n;
                int noOfPages = 1;
                if (recordsperpage > 0) {
                    noOfPages = timesheetCount / recordsperpage;
                    if (timesheetCount % recordsperpage > 0) {
                        noOfPages += 1;
                    }
                }
                if (total > 0)
                {
                    str.append("<div class='wesl_pagination pagination-mg m_15'>");
                    str.append("<div class='wesl_pg_rcds'>");
                    str.append("Page&nbsp;" + (next_value) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + timesheetCount + ")&nbsp;");
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
                    
                    TimesheetInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (TimesheetInfo) timesheet_list.get(i);
                        if (info != null) 
                        {
                            str.append("<tr class='hand_cursor' href='javascript: void(0);'>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+info.getAssetId()+"');\">" + (info.getClientName()!= null ? info.getClientName(): "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+info.getAssetId()+"');\">" + (info.getClientAssetName()!= null ? info.getClientAssetName(): "") + "</td>");
                            str.append("<td class='hand_cursor text-center' href='javascript: void(0);' onclick=\"javascript: showDetail('"+info.getAssetId()+"');\">" + (info.getTypevalue() != null ? info.getTypevalue(): "") + "</td>");
                            str.append("<td class='hand_cursor assets_list text-center'><a href='javascript: void(0);' onclick=\"javascript: showDetail('"+info.getAssetId()+"');\">" + info.getTcount()+ "</a></td>");                            
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
                    str.append("(" + value + " - " + value1 + " record(s) of " + timesheetCount + ")&nbsp;");
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
                    str.append("<td>" + timesheet.getMainPath("record_not_found") + "</td>");
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