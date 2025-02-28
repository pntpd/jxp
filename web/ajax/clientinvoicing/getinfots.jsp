<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.clientinvoicing.ClientinvoicingInfo" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="clientinvoicing" class="com.web.jxp.clientinvoicing.Clientinvoicing" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String  editper = "N", addper = "N";
            if (uinfo != null) 
            {
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                addper = uinfo.getAddper()!= null ? uinfo.getAddper() : "N";
            }
            String view_path = clientinvoicing.getMainPath("view_timesheet_file");
            int showsizelist = clientinvoicing.getCountList("show_size_list");
            
            StringBuilder sb = new StringBuilder();
            
            sb.append("<thead>");
            sb.append("<tr>");
            sb.append("<th width='15%' ><span><b>Invoice ID</b> </span>");
            sb.append("<a href=\"javascript: sortFormTS('1', '2');\" id='img_1_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortFormTS('1', '1');\" id='img_1_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='14%' ><span><b>Status</b> </span>");
            sb.append("<a href=\"javascript: sortFormTS('2', '2');\" id='img_2_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortFormTS('2', '1');\" id='img_2_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='15%' ><span><b>Generated on</b> </span>");
            sb.append("<a href=\"javascript: sortFormTS('3', '2');\" id='img_3_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortFormTS('3', '1');\" id='img_3_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='11%'><span><b>Sent on</b> </span>");
            sb.append("<a href=\"javascript: sortFormTS('4', '2');\" id='img_4_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortFormTS('4', '1');\" id='img_4_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='13%'><span><b>From Date</b> </span>");
            sb.append("<a href=\"javascript: sortFormTS('5', '2');\" id='img_5_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortFormTS('5', '1');\" id='img_5_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='10%'><span><b>To Date</b> </span>");
            sb.append("<a href=\"javascript: sortFormTS('6', '2');\" id='img_6_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortFormTS('6', '1');\" id='img_6_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("</tr>");
            sb.append("</thead>");
            
            String thead = sb.toString();
            sb.setLength(0);
            
            String search = request.getParameter("search") != null ? vobj.replacedesc(request.getParameter("search")) : "";
            String invoicestatusIndexs = request.getParameter("invoicestatusIndex") != null ? vobj.replacedesc(request.getParameter("invoicestatusIndex")) : "";
            String months = request.getParameter("month") != null ? vobj.replacedesc(request.getParameter("month")) : "";
            String yearIds = request.getParameter("yearId") != null ? vobj.replacedesc(request.getParameter("yearId")) : "";
            String clientassetIds = request.getParameter("clientassetId") != null ? vobj.replacedesc(request.getParameter("clientassetId")) : "";
            String nextValue = request.getParameter("nextValue") != null && !request.getParameter("nextValue").equals("") ? vobj.replaceint(request.getParameter("nextValue")) : "0";
            String dodirects = request.getParameter("doDirect") != null && !request.getParameter("doDirect").equals("") ? vobj.replaceint(request.getParameter("doDirect")) : "-1";
            int n = Integer.parseInt(nextValue);
            int invoicestatusIndex = Integer.parseInt(invoicestatusIndexs);
            int month = Integer.parseInt(months);
            int yearId = Integer.parseInt(yearIds);
            int clientassetId = Integer.parseInt(clientassetIds);
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
                session.setAttribute("NEXTVALUETS", next_value + "");
                int recordsperpage = clientinvoicing.getCount();
                ArrayList clientinvoicing_list = new ArrayList();
                int count = 0;
                if (next1.equals("p") || next1.equals("n")) {
                    clientinvoicing_list = clientinvoicing.getClientassetdetailByName(search,invoicestatusIndex, n, recordsperpage,month, yearId, clientassetId);
                    if (clientinvoicing_list.size() > 0) {
                        ClientinvoicingInfo cinfo = (ClientinvoicingInfo) clientinvoicing_list.get(clientinvoicing_list.size() - 1);
                        count = cinfo.getTimesheetId();
                        clientinvoicing_list.remove(clientinvoicing_list.size() - 1);
                    }
                } else {
                    clientinvoicing_list = clientinvoicing.getClientassetdetailByName(search, invoicestatusIndex, n, recordsperpage,month, yearId, clientassetId);
                    if (clientinvoicing_list.size() > 0) {
                        ClientinvoicingInfo cinfo = (ClientinvoicingInfo) clientinvoicing_list.get(clientinvoicing_list.size() - 1);
                        count = cinfo.getTimesheetId();
                        clientinvoicing_list.remove(clientinvoicing_list.size() - 1);
                    }
                }
                session.setAttribute("COUNT_LISTTS", count + "");
                session.setAttribute("CLIENTTIMESHEET_LIST", clientinvoicing_list);

                int pageSize = count / recordsperpage;
                if (count % recordsperpage > 0) {
                    pageSize = pageSize + 1;
                }

                int clientinvoicingCount = count;
                String prev = "";
                String prevclose = "";
                String next = "";
                String nextclose = "";
                int last = 0;
                int total = clientinvoicing_list.size();
                str.append("<input type='hidden' name='totalpage' value='" + pageSize + "' />");
                str.append("<input type='hidden' name='nextDel' value='" + total + "'/>");
                str.append("<input type='hidden' name='nextValue' value='" + (next_value) + "'/>");
                if (n == 0 && clientinvoicingCount > recordsperpage) {
                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                } else if (n > 0 && clientinvoicingCount > ((n * recordsperpage) + recordsperpage)) {
                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                } else if (n > 0 && clientinvoicingCount <= ((n * recordsperpage) + recordsperpage)) {
                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                    last = clientinvoicingCount;
                } else {
                    recordsperpage = clientinvoicingCount;
                }
                int value = n * recordsperpage + 1;
                int value1 = last > 0 ? last : n * recordsperpage + recordsperpage;
                int test = n;
                int noOfPages = 1;
                if (recordsperpage > 0) {
                    noOfPages = clientinvoicingCount / recordsperpage;
                    if (clientinvoicingCount % recordsperpage > 0) {
                        noOfPages += 1;
                    }
                }
                if (total > 0) 
                {
                    str.append("<div class='wesl_pagination pagination-mg m_15'>");
                    str.append("<div class='wesl_pg_rcds'>");
                    str.append("Page&nbsp;" + (next_value) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + clientinvoicingCount + ")&nbsp;");
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
                    
                    ClientinvoicingInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (ClientinvoicingInfo) clientinvoicing_list.get(i);
                        if (info != null) 
                        {
                            str.append("<tr>");
                             str.append("<td>" + (clientinvoicing.changeNum( info.getInvoiceId(),6)) + "</td>");
                             str.append("<td>" + (clientinvoicing.getInvoicestatusvalue(info.getInvoicestatus())) + "</td>");
                            str.append("<td>" + (info.getGeneratedate() != null ? info.getGeneratedate() : "") + "</td>");
                            str.append("<td>" + (info.getSentdate() != null ? info.getSentdate() : "") + "</td>");
                            str.append("<td>" + (info.getFromdate() != null ? info.getFromdate() : "") + "</td>");
                            str.append("<td>" + (info.getTodate() != null ? info.getTodate() : "") + "</td>");
                            if (editper.equals("Y") || addper.equals("Y")){ 
                            str.append("<td class='action_column text-right'>");
                                if(info.getInvoicestatus() <= 2){
                            str.append("<a href=\"javascript:;\" onclick=\" javascript: doGenerateinvoice('"+ info.getTimesheetId()+"');\" class='mr_15 generate_action'>Generate </a>");
                                } else if(info.getInvoicestatus() == 3){
                            str.append("<a href=\"javascript:;\" onclick=\" javascript: doPayment('"+ info.getTimesheetId()+"');\" class='mr_15'><img src='../assets/images/view.png'> </a>");
                                } else if(info.getInvoicestatus() == 4){
                            str.append("<a href=\"javascript:;\" onclick=\" javascript: doPaymentView('"+ info.getTimesheetId()+"');\" class='mr_15'><img src='../assets/images/view.png'> </a>");
                                }
                                if(!info.getSendappfile().equals("")) {                                                                                
                            str.append("<a  href=\"javascript: seturl('"+(view_path+info.getSendappfile())+"');\"><img src='../assets/images/attachment.png'></a>");
                                }
                            str.append("</td>");
                            }
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
                    str.append("(" + value + " - " + value1 + " record(s) of " + clientinvoicingCount + ")&nbsp;");
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
                    str.append("<td>" + clientinvoicing.getMainPath("record_not_found") + "</td>");
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