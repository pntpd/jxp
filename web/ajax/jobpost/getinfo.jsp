<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.jobpost.JobPostInfo" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="jobpost" class="com.web.jxp.jobpost.JobPost" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            int uid = 0, allclient = 0;
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String per = "", editper = "N", cids = "", assetids = "";
            if (uinfo != null) 
            {
                uid = uinfo.getUserId();
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                cids = uinfo.getCids();
                allclient = uinfo.getAllclient();
                assetids = uinfo.getAssetids();
            }
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = jobpost.getLocalIp();
            int showsizelist = jobpost.getCountList("show_size_list");
            
            StringBuilder sb = new StringBuilder();
            sb.append("<thead>");
            sb.append("<tr>");
            sb.append("<th width='10%'><span><b>Ref. No.</b> </span>");
            sb.append("<a href=\"javascript: sortForm('1', '2');\" id='img_1_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('1', '1');\" id='img_1_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='11%'><span><b>Posted On</b> </span>");
            sb.append("<a href=\"javascript: sortForm('2', '2');\" id='img_2_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('2', '1');\" id='img_2_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='17%'><span><b>Client Name</b> </span>");
            sb.append("<a href=\"javascript: sortForm('3', '2');\" id='img_3_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('3', '1');\" id='img_3_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='21%'><span><b>Position</b> </span>");
            sb.append("<a href=\"javascript: sortForm('4', '2');\" id='img_4_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('4', '1');\" id='img_4_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='8%'><span><b>Rank</b> </span></th>");
            sb.append("<th width='12%'><span><b>Mobilize By</b> </span>");
            sb.append("<a href=\"javascript: sortForm('5', '2');\" id='img_5_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('5', '1');\" id='img_5_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='7%' class='text-center'><span><b>Opening</b></span></th>");
            sb.append("<th width='14%' class='text-right'><span><b>Actions</b></span></th>");
            sb.append("</tr>");
            sb.append("</thead>");
            String thead = sb.toString();
            sb.setLength(0);
            
            String search = request.getParameter("search") != null ? vobj.replacedesc(request.getParameter("search")) : "";
            String nextValue = request.getParameter("nextValue") != null && !request.getParameter("nextValue").equals("") ? vobj.replaceint(request.getParameter("nextValue")) : "0";
            String dodirects = request.getParameter("doDirect") != null && !request.getParameter("doDirect").equals("") ? vobj.replaceint(request.getParameter("doDirect")) : "-1";
            String statusIndex_s = request.getParameter("statusIndex") != null && !request.getParameter("statusIndex").equals("") ? vobj.replaceint(request.getParameter("statusIndex")) : "-1";
            int statusIndex = Integer.parseInt(statusIndex_s);
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
                int result = jobpost.deleteJobPost(id, uid, statusval, ipAddrStr, iplocal);
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
                int recordsperpage = jobpost.getCount();
                ArrayList jobpost_list = jobpost.getJobPostByName(search, statusIndex, n1, recordsperpage, allclient,  per,  cids,  assetids);
                int count = 0;
                if (jobpost_list.size() > 0) {
                    JobPostInfo cinfo = (JobPostInfo) jobpost_list.get(jobpost_list.size() - 1);
                    count = cinfo.getJobpostId();
                    jobpost_list.remove(jobpost_list.size() - 1);
                }
                request.getSession().setAttribute("COUNT_LIST", count + "");
                session.setAttribute("JOBPOST_LIST", jobpost_list);
                int pageSize = count / recordsperpage;
                if (count % recordsperpage > 0) {
                    pageSize = pageSize + 1;
                }

                int jobpostCount = count;
                String prev = "";
                String prevclose = "";
                String next = "";
                String nextclose = "";
                int last = 0;
                int total = jobpost_list.size();
                str.append("<input type='hidden' name='totalpage' value='" + pageSize + "' />");
                str.append("<input type='hidden' name='nextValue' value='" + (n1 + 1) + "'/>");
                str.append("<input type='hidden' name='nextDel' value='" + total + "'/>");
                if (n1 == 0 && jobpostCount > recordsperpage) {
                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                } else if (n1 > 0 && jobpostCount > ((n1 * recordsperpage) + recordsperpage)) {
                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                } else if (n1 > 0 && jobpostCount <= ((n1 * recordsperpage) + recordsperpage)) {
                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                    last = jobpostCount;
                } else {
                    recordsperpage = jobpostCount;
                }
                int value = (n1) * recordsperpage + 1;
                int value1 = last > 0 ? last : (n1) * recordsperpage + recordsperpage;
                int test = n1;
                int noOfPages = 1;
                if (recordsperpage > 0) {
                    noOfPages = jobpostCount / recordsperpage;
                    if (jobpostCount % recordsperpage > 0) {
                        noOfPages += 1;
                    }
                }
                if (total > 0) 
                {
                    str.append("<div class='wesl_pagination pagination-mg m_15'>");
                    str.append("<div class='wesl_pg_rcds'>");
                    str.append("Page&nbsp;" + (n1 + 1) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + jobpostCount + ")&nbsp;");
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
                    str.append("<div class='col-lg-12' id='printArea'>");
                    str.append("<div class='table-rep-plugin sort_table'>");
                    str.append("<div class='table-responsive mb-0' data-bs-pattern='priority-columns'>");
                    str.append("<table id='tech-companies-1' class='table table-striped'>");
                    str.append(thead);
                    str.append("<tbody id = 'sort_id'>");
                    
                    int status;
                    JobPostInfo info;
                    for (int i = 0; i < total; i++)
                    {
                        info = (JobPostInfo) jobpost_list.get(i);
                        if (info != null) 
                        {
                            status = info.getStatus();
                            str.append("<tr>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getJobpostId() + ");'>" + (info.getRefno() != null ? info.getRefno() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getJobpostId() + ");'>" + (info.getPoston() != null ? info.getPoston() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getJobpostId() + ");'>" + (info.getClientname() != null ? info.getClientname() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getJobpostId() + ");'>" + (info.getPositionname() != null ? info.getPositionname() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getJobpostId() + ");'>" + (info.getGrade() != null ? info.getGrade() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getJobpostId() + ");'>" + (info.getTargetmobdate() != null ? info.getTargetmobdate() : "") + "</td>");
                            str.append("<td class='assets_list text-center hand_cursor'><a href='javascript:;'>" + (info.getOpening() != null ? info.getOpening() : "&nbsp;") + "</a></td>");
                           str.append("<td class='action_column'>");
                            if (status == 1) {
                            str.append("<a href=\"javascript: searchFormShortlist('" + (info.getJobpostId()) + "');\" class='mr_15'><img src='../assets/images/view_icon.png'></a>");
                            }
                            if (editper.equals("Y") && status == 1) {
                                str.append("<a href=\"javascript: modifyForm('" + (info.getJobpostId()) + "');\" class='mr_15'><img src='../assets/images/pencil.png'></a>");
                            }
                            else
                            { 
                                str.append("<a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a>");
                            }
                            if(status == 3)
                                str.append("<span class='switch_bth close_switch'>");
                            else
                                str.append("<span class='switch_bth'>");
                            str.append("<div class='form-check form-switch'>");
                            str.append("<input class='form-check-input' type='checkbox' role='switch' id='flexSwitchCheckDefault_" + (i) + "' ");
                            if (!editper.equals("Y") || status == 3) {
                                str.append("  disabled = 'true'");
                            }
                            if (status == 1 || status == 3) {
                                str.append("  checked");
                            }
                            
                            str.append(" onclick=\"javascript: deleteForm('" + info.getJobpostId() + "', '" + status + "', '" + (i) + "');\"/>");
                            str.append("</div>");
                            str.append("</span>");
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
                    str.append("(" + value + " - " + value1 + " record(s) of " + jobpostCount + ")&nbsp;");
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
                    str.append("<td>" + jobpost.getMainPath("record_not_found") + "</td>");
                    str.append("</tr>");
                    str.append("</tbody>");
                    str.append("</table>");
                    str.append("</div>");
                    str.append("</div>");
                }
                String s1 = str.toString().intern() + "##" + message;
                response.getWriter().write(s1);
            } else if (request.getParameter("deleteVal") == null) {
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
                int recordsperpage = jobpost.getCount();
                ArrayList jobpost_list = new ArrayList();
                int count = 0;
                if (next1.equals("p") || next1.equals("n")) {
                    jobpost_list = jobpost.getJobPostByName(search, statusIndex, n, recordsperpage, allclient,  per,  cids,  assetids);
                    if (jobpost_list.size() > 0) {
                        JobPostInfo cinfo = (JobPostInfo) jobpost_list.get(jobpost_list.size() - 1);
                        count = cinfo.getJobpostId();
                        jobpost_list.remove(jobpost_list.size() - 1);
                    }
                } else {
                    jobpost_list = jobpost.getJobPostByName(search, statusIndex, n, recordsperpage, allclient,  per,  cids,  assetids);
                    if (jobpost_list.size() > 0) {
                        JobPostInfo cinfo = (JobPostInfo) jobpost_list.get(jobpost_list.size() - 1);
                        count = cinfo.getJobpostId();
                        jobpost_list.remove(jobpost_list.size() - 1);
                    }
                }
                session.setAttribute("COUNT_LIST", count + "");
                session.setAttribute("JOBPOST_LIST", jobpost_list);

                int pageSize = count / recordsperpage;
                if (count % recordsperpage > 0) {
                    pageSize = pageSize + 1;
                }

                int jobpostCount = count;
                String prev = "";
                String prevclose = "";
                String next = "";
                String nextclose = "";
                int last = 0;
                int total = jobpost_list.size();
                str.append("<input type='hidden' name='totalpage' value='" + pageSize + "' />");
                str.append("<input type='hidden' name='nextDel' value='" + total + "'/>");
                str.append("<input type='hidden' name='nextValue' value='" + (next_value) + "'/>");
                if (n == 0 && jobpostCount > recordsperpage) {
                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                } else if (n > 0 && jobpostCount > ((n * recordsperpage) + recordsperpage)) {
                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                } else if (n > 0 && jobpostCount <= ((n * recordsperpage) + recordsperpage)) {
                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                    last = jobpostCount;
                } else {
                    recordsperpage = jobpostCount;
                }
                int value = n * recordsperpage + 1;
                int value1 = last > 0 ? last : n * recordsperpage + recordsperpage;
                int test = n;
                int noOfPages = 1;
                if (recordsperpage > 0) {
                    noOfPages = jobpostCount / recordsperpage;
                    if (jobpostCount % recordsperpage > 0) {
                        noOfPages += 1;
                    }
                }
                if (total > 0) 
                {
                    str.append("<div class='wesl_pagination pagination-mg m_15'>");
                    str.append("<div class='wesl_pg_rcds'>");
                    str.append("Page&nbsp;" + (next_value) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + jobpostCount + ")&nbsp;");
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
                    str.append("<div class='col-lg-12' id='printArea' >");
                    str.append("<div class='table-rep-plugin sort_table'>");
                    str.append("<div class='table-responsive mb-0' data-bs-pattern='priority-columns'>");
                    str.append("<table id='tech-companies-1' class='table table-striped'>");
                    str.append(thead);
                    str.append("<tbody id = 'sort_id'>");
                    
                    int status;
                    JobPostInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (JobPostInfo) jobpost_list.get(i);
                        if (info != null) 
                        {
                            status = info.getStatus();
                            str.append("<tr>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getJobpostId() + ");'>" + (info.getRefno() != null ? info.getRefno() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getJobpostId() + ");'>" + (info.getPoston() != null ? info.getPoston() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getJobpostId() + ");'>" + (info.getClientname() != null ? info.getClientname() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getJobpostId() + ");'>" + (info.getPositionname() != null ? info.getPositionname() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getJobpostId() + ");'>" + (info.getGrade() != null ? info.getGrade() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getJobpostId() + ");'>" + (info.getTargetmobdate() != null ? info.getTargetmobdate() : "") + "</td>");
                            str.append("<td class='assets_list text-center hand_cursor'><a href='javascript:;'>" + (info.getOpening() != null ? info.getOpening() : "&nbsp;") + "</a></td>");
                            str.append("<td class='action_column'>");
                             if (status == 1) {
                            str.append("<a href=\"javascript: searchFormShortlist('" + (info.getJobpostId()) + "');\" class='mr_15'><img src='../assets/images/view_icon.png'></a>");
                             }
                            if (editper.equals("Y") && status == 1) {
                                str.append("<a href=\"javascript: modifyForm('" + (info.getJobpostId()) + "');\" class='mr_15'><img src='../assets/images/pencil.png'></a>");
                            }
                            else
                            { 
                                str.append("<a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a>");
                            }
                            if(status == 3)
                                str.append("<span class='switch_bth close_switch'>");
                            else
                                str.append("<span class='switch_bth'>");
                            str.append("<div class='form-check form-switch'>");
                            str.append("<input class='form-check-input' type='checkbox' role='switch' id='flexSwitchCheckDefault_" + (i) + "' ");
                            if (!editper.equals("Y") || status == 3) {
                                str.append("  disabled = 'true'");
                            }
                            if (status == 1 || status == 3) {
                                str.append("  checked");
                            }
                            str.append(" onclick=\"javascript: deleteForm('" + info.getJobpostId() + "', '" + status + "', '" + (i) + "');\"/>");
                            str.append("</div>");
                            str.append("</span>");
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
                    str.append("(" + value + " - " + value1 + " record(s) of " + jobpostCount + ")&nbsp;");
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
                    str.append("<td>" + jobpost.getMainPath("record_not_found") + "</td>");
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