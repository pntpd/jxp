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
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String editper = "N", deleteper = "N", addper = "N", approveper = "N";
            if (uinfo != null) 
            {
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                deleteper = uinfo.getDeleteper() != null ? uinfo.getDeleteper() : "N";
                addper = uinfo.getAddper()!= null ? uinfo.getAddper() : "N";
                approveper = uinfo.getApproveper() != null ? uinfo.getApproveper() : "N";
            }
            String view_path = timesheet.getMainPath("view_timesheet_file");
            int showsizelist = timesheet.getCountList("show_size_list");
            
            StringBuilder sb = new StringBuilder();
            sb.append("<thead>");
            sb.append("<tr>");
            sb.append("<th width='15%' >");
            sb.append("<span><b>Timesheet ID</b> </span>");
            sb.append("</th>");            
            sb.append("<th width='14%' ><span><b>Status</b> </span>");
            sb.append("</th>");            
            sb.append("<th width='11%' >");
            sb.append("<span><b>Schedule</b> </span>");
            sb.append("</th>");            
            sb.append("<th width='11%'>");
            sb.append("<span><b>Currency </b> </span>");
            sb.append("</th>");            
            sb.append("<th width='15%'>");
            sb.append("<span><b>Generated On </b> </span>");
            sb.append("</th>");            
            sb.append("<th width='13%'>");
            sb.append("<span><b>From Date </b> </span>");
            sb.append("</th>");            
            sb.append("<th width='12%'>");
            sb.append("<span><b>To Date </b> </span>");
            sb.append("</th>");            
            sb.append("<th width='9%' class='text-right'>");
            sb.append("<span><b>Actions </b> </span>");
            sb.append("</th>");            
            sb.append("</tr>");
            sb.append("</thead>");
            String thead = sb.toString();
            sb.setLength(0);
            
            String search = request.getParameter("search") != null ? vobj.replacedesc(request.getParameter("search")) : "";
            String nextTsValue = request.getParameter("nextTsValue") != null && !request.getParameter("nextTsValue").equals("") ? vobj.replaceint(request.getParameter("nextTsValue")) : "0";
            String dodirects = request.getParameter("doDirect") != null && !request.getParameter("doDirect").equals("") ? vobj.replaceint(request.getParameter("doDirect")) : "-1";
            String clientassetIds = request.getParameter("clientassetId") != null && !request.getParameter("clientassetId").equals("") ? vobj.replaceint(request.getParameter("clientassetId")) : "0";
            String statusIndexs = request.getParameter("statusIndex") != null && !request.getParameter("statusIndex").equals("") ? vobj.replaceint(request.getParameter("statusIndex")) : "-1";
            String month_s = request.getParameter("month") != null && !request.getParameter("month").equals("") ? vobj.replaceint(request.getParameter("month")) : "-1";
            String yearIds = request.getParameter("yearId") != null && !request.getParameter("yearId").equals("") ? vobj.replaceint(request.getParameter("yearId")) : "0";
            String fromDateIndex = request.getParameter("fromDateIndex") != null ? request.getParameter("fromDateIndex") : "";
            String toDateIndex = request.getParameter("toDateIndex") != null ? request.getParameter("toDateIndex") : "";
            int n = Integer.parseInt(nextTsValue);
            int dodirect = Integer.parseInt(dodirects);
            int statusIndex = Integer.parseInt(statusIndexs);
            int month = Integer.parseInt(month_s);
            int yearId = Integer.parseInt(yearIds);
            int clientassetId = Integer.parseInt(clientassetIds);
            if (true) 
            {
                StringBuilder str = new StringBuilder();
                int m = n;
                int next_tsvalue = 0;
                String next1 = request.getParameter("next");
                if (next1 != null && next1.equals("n")) {
                    if (dodirect >= 0) {
                        n = dodirect;
                        next_tsvalue = dodirect + 1;
                    } else {
                        n = n;
                        next_tsvalue = m + 1;
                    }
                } else if (next1 != null && next1.equals("p")) {
                    n = n - 2;
                    next_tsvalue = m - 1;
                } else if (next1 != null && next1.equals("s")) {
                    n = 0;
                    next_tsvalue = 1;
                }
                session.setAttribute("NEXTTSVALUE", next_tsvalue + "");
                int recordsperpage = timesheet.getCount();
                ArrayList timesheet_list = new ArrayList();
                int count = 0;
                if (next1.equals("p") || next1.equals("n")) 
                {
                    timesheet_list = timesheet.getTimesheetList(clientassetId, search, statusIndex, month, yearId, n, recordsperpage, fromDateIndex, toDateIndex);                    
                    if (timesheet_list.size() > 0) 
                    {
                        TimesheetInfo cinfo = (TimesheetInfo) timesheet_list.get(timesheet_list.size() - 1);
                        count = cinfo.getTimesheetId();
                        timesheet_list.remove(timesheet_list.size() - 1);
                    }
                } else 
                {
                    timesheet_list = timesheet.getTimesheetList(clientassetId, search, statusIndex, month, yearId, n, recordsperpage, fromDateIndex, toDateIndex);
                    if (timesheet_list.size() > 0) 
                    {
                        TimesheetInfo cinfo = (TimesheetInfo) timesheet_list.get(timesheet_list.size() - 1);
                        count = cinfo.getTimesheetId();
                        timesheet_list.remove(timesheet_list.size() - 1);
                    }
                }
                session.setAttribute("COUNT_TSLIST", count + "");
                session.setAttribute("T_LIST", timesheet_list);

                int pageSize = count / recordsperpage;
                if (count % recordsperpage > 0) {
                    pageSize = pageSize + 1;
                }

                int tsCount = count;
                String prev = "";
                String prevclose = "";
                String next = "";
                String nextclose = "";
                int last = 0;
                int total = timesheet_list.size();
                str.append("<input type='hidden' name='totalpage' value='" + pageSize + "' />");
                str.append("<input type='hidden' name='nextDel' value='" + total + "'/>");
                str.append("<input type='hidden' name='nextTsValue' value='" + (next_tsvalue) + "'/>");
                if (n == 0 && tsCount > recordsperpage) {
                    next = ("<li class='next'><a href=\"javascript: searchTimesheet('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                } else if (n > 0 && tsCount > ((n * recordsperpage) + recordsperpage)) {
                    next = ("<li class='next'><a href=\"javascript: searchTimesheet('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                    prev = ("<li class='prev'><a href=\"javascript: searchTimesheet('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                } else if (n > 0 && tsCount <= ((n * recordsperpage) + recordsperpage)) {
                    prev = ("<li class='prev'><a href=\"javascript: searchTimesheet('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                    last = tsCount;
                } else {
                    recordsperpage = tsCount;
                }
                int value = n * recordsperpage + 1;
                int value1 = last > 0 ? last : n * recordsperpage + recordsperpage;
                int test = n;
                int noOfPages = 1;
                if (recordsperpage > 0) {
                    noOfPages = tsCount / recordsperpage;
                    if (tsCount % recordsperpage > 0) {
                        noOfPages += 1;
                    }
                }
                if (total > 0) 
                {
                    str.append("<div class='wesl_pagination pagination-mg m_15'>");
                    str.append("<div class='wesl_pg_rcds'>");
                    str.append("Page&nbsp;" + (next_tsvalue) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + tsCount + ")&nbsp;");
                    str.append("</div>");
                    str.append("<div class='wesl_No_pages'>");
                    str.append("<div class='loanreportTables_paginate paging_bootstrap_full_number'>");
                    str.append("<ul class='wesl_pagination'>");
                    if (noOfPages > 1 && next_tsvalue != 1) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchTimesheet('n', '0');\" title='First' style='height: 33px;'><i class='fa fa-angle-double-left'></i></a></li>");
                    }
                    str.append(prev + prevclose + "&nbsp;");
                    int a1 = test - showsizelist;
                    int a2 = test + showsizelist;
                    for (int ii = a1; ii <= a2; ii++) {
                        if (ii >= 0 && ii < noOfPages) {
                            if (ii == test) {
                                str.append("<li class='wesl_active'><a href='#'> " + (ii + 1) + " </a></li>");
                            } else {
                                str.append("<li><a href=\"javascript: searchTimesheet('n', '" + ii + "');\" title=''> " + (ii + 1) + " </a></li>");
                            }
                        }
                    }
                    str.append(next + nextclose);
                    if (noOfPages > 1 && next_tsvalue != noOfPages) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchTimesheet('n','" + (noOfPages - 1) + "');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
                    }
                    str.append("</ul></div></div></div>");
                    str.append("<div class='col-lg-12' id='printArea'>");
                    str.append("<div class='table-rep-plugin sort_table'>");
                    str.append("<div class='table-responsive mb-0' data-bs-pattern='priority-columns'>");
                    str.append("<table id='tech-companies-1' class='table table-striped'>");
                    str.append(thead);
                    str.append("<tbody id = 'sort_id'>");
                    
                    int status;
                    TimesheetInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (TimesheetInfo) timesheet_list.get(i);
                        if (info != null) 
                        {
                            status = info.getStatus();
                            str.append("<tr class='hand_cursor'>");
                            str.append("<td>" + timesheet.changeNum(info.getTimesheetId(),3) + "</td>");
                            str.append("<td>" + (info.getStatusValue() != null ? info.getStatusValue() : "") + "</td>");
                            str.append("<td>" + (info.getTypevalue() != null ? info.getTypevalue() : "") + "</td>");
                            str.append("<td>" + (info.getCurrencyName() != null ? info.getCurrencyName() : "") + "</td>");
                            str.append("<td>" + (info.getGeneratedOn() != null ? info.getGeneratedOn() : "") + "</td>");
                            str.append("<td>" + (info.getFromDate() != null ? info.getFromDate() : "") + "</td>");
                            str.append("<td>" + (info.getToDate()!= null ? info.getToDate(): "") + "</td>");
                            str.append("<td class='action_column text-right'>");
                            if ((deleteper.equals("Y")) && (status == 1 || status == 2))
                            {
                                str.append("<a href=\"javascript: deleteForm('" +info.getTimesheetId()+ "');\" class='mr_15' ><img src='../assets/images/trash.png'/> </a>");
                            }
                            if ((addper.equals("Y") || editper.equals("Y")) && (status == 1 || status == 2 || status == 4 ))
                            {
                                str.append("<a href=\"javascript: showTimesheetDetail('" +info.getTimesheetId()+ "');\"><img src='../assets/images/pencil.png'/> </a>");
                            }
                            if(status == 5)
                            {
                                if(!info.getFilename2().equals("")) 
                                {                                                                               
                                     str.append("<a  href=\"javascript: seturl('"+view_path + info.getFilename2()+"');\"><img src='../assets/images/attachment.png'/></a>");
                                }
                            }
                            if(status == 3)
                            {
                                str.append("<a class='' href=\"javascript: showTimesheetDetail('"+info.getTimesheetId()+"');\"><img src='../assets/images/view.png'/></a>");                                                                                
                            }
                            str.append("</td>");
                            str.append("<td class='action_column text-right'>");                                                                               
                            str.append("<div class='main-nav ass_list float-end'>");
                            str.append("<ul>");
                            str.append("<li class='drop_down'>");
                            if(status == 3 || status == 2 || status == 4 ) 
                            {
                                str.append("<a href=\"javascript:;\" class='toggle'><i class='fas fa-ellipsis-v'></i></a>");
                            }
                            str.append("<div class='dropdown_menu'>");
                            str.append("<div class='dropdown-wrapper'>");
                            str.append("<div class='category-menu'>");
                            if(status == 2 && (addper.equals("Y") || editper.equals("Y")))
                            {
                                str.append("<a onclick=\"javascript: SentforApproval('" +info.getTimesheetId()+ "');\" data-bs-toggle='modal' data-bs-target='#email_timesheet_modal'>Send for Approval</a>");
                            }
                            if(status == 3)
                            {
                                if(addper.equals("Y") || editper.equals("Y"))
                                    str.append("<a onclick=\"javascript: addReason('" +info.getTimesheetId()+ "');\" data-bs-toggle='modal' data-bs-target='#request_timesheet_revision_modal'>Request Revision</a>");
                                if((approveper.equals("Y")))
                                    str.append("<a onclick=\"javascript: appoveInvoice('" +info.getTimesheetId()+ "');)\" data-bs-toggle='modal' data-bs-target='#approve_timesheet_invoicing_modal'>Approve for Invoicing</a>");
                            }
                            if(status == 4)
                            {                                                                                                        
                                str.append("<a onclick=\"javascript: viewReason('" +info.getTimesheetId()+ "');\" data-bs-toggle='modal' data-bs-target='#view_reason_modal'>View Reason</a>");
                            }  
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
                    str.append("Page&nbsp;" + (next_tsvalue) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + tsCount + ")&nbsp;");
                    str.append("</div>");
                    str.append("<div class='wesl_No_pages'>");
                    str.append("<div class='loanreportTables_paginate paging_bootstrap_full_number'>");
                    str.append("<ul class='wesl_pagination'>");
                    if (noOfPages > 1 && next_tsvalue != 1) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchTimesheet('n', '0');\" title='First' style='height: 33px;'><i class='fa fa-angle-double-left'></i></a></li>");
                    }
                    str.append(prev + prevclose + "&nbsp;");
                    for (int ii = a1; ii <= a2; ii++) {
                        if (ii >= 0 && ii < noOfPages) {
                            if (ii == test) {
                                str.append("<li class='wesl_active'><a href='#'> " + (ii + 1) + " </a></li>");
                            } else {
                                str.append("<li><a href=\"javascript: searchTimesheet('n', '" + ii + "');\" title=''> " + (ii + 1) + " </a></li>");
                            }
                        }
                    }
                    str.append(next + nextclose);
                    if (noOfPages > 1 && next_tsvalue != noOfPages) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchTimesheet('n','" + (noOfPages - 1) + "');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
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