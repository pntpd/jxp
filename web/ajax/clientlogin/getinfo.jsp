<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.clientlogin.ClientloginInfo" %>
<jsp:useBean id="clientlogin" class="com.web.jxp.clientlogin.Clientlogin" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        if (session.getAttribute("MLOGININFO") != null) 
        {
            int uId = 0;
            ClientloginInfo uinfo = ((ClientloginInfo) request.getSession().getAttribute("MLOGININFO"));
            if (uinfo != null)
            {
                uId = uinfo.getUserId();
            }
            int showsizelist = clientlogin.getCountList("show_size_list");
            StringBuilder sb = new StringBuilder();
                        
            sb.append("<ul class='comp_list'>");
                sb.append("<li class='ref_no_label'>Ref. No.</li>");
                sb.append("<li class='posted_on_label'>Posted On</li>");
                sb.append("<li class='client_asset_label'>Client - Asset</li>");
                sb.append("<li class='position_rank_label'>Position - Rank</li>");
                sb.append("<li class='client_status_label'>Status</li>");
                sb.append("<li class='feedback_filter_label'>");
                    sb.append("<div class='toggled-off_02 usefool_tool'>");
                        sb.append("<div class='toggle-title_02'>");
                            sb.append("<img src='../assets/images/filter.png' class='fa-angle-left'>");
                            sb.append("<img src='../assets/images/filter_up.png' class='fa-angle-right'>");
                        sb.append("</div>");
                        sb.append("<div class='toggle-content'>");
                            sb.append("<ul>");
                                sb.append("<li><a href=\"javascript:;\"><span class='round_circle circle_exipry'></span> Pending</a></li>");
                                sb.append("<li><a href=\"javascript:;\"><span class='round_circle circle_complete'></span> Complete</a></li>");
                            sb.append("</ul>");
                        sb.append("</div>");
                    sb.append("</div>");
                sb.append("</li>");
                sb.append("<li class='accepted_label text-center'>Accepted</li>");
            sb.append("</ul>");
            
            String thead = sb.toString();
            sb.setLength(0);
            
            String search = request.getParameter("search") != null ? vobj.replacedesc(request.getParameter("search")) : "";
            String nextValue = request.getParameter("nextValue") != null && !request.getParameter("nextValue").equals("") ? vobj.replaceint(request.getParameter("nextValue")) : "0";
            String dodirects = request.getParameter("doDirect") != null && !request.getParameter("doDirect").equals("") ? vobj.replaceint(request.getParameter("doDirect")) : "-1";
            String clientIdIndexs = request.getParameter("clientIdIndex") != null && !request.getParameter("clientIdIndex").equals("") ? vobj.replaceint(request.getParameter("clientIdIndex")) : "-1";
            String positionIndexs = request.getParameter("positionIndex") != null && !request.getParameter("positionIndex").equals("") ? request.getParameter("positionIndex") : "";
            String assetIdIndexs = request.getParameter("assetIdIndex") != null && !request.getParameter("assetIdIndex").equals("") ? vobj.replaceint(request.getParameter("assetIdIndex")) : "-1";
            int n = Integer.parseInt(nextValue);
            int dodirect = Integer.parseInt(dodirects);            
            int clientIdIndex = Integer.parseInt(clientIdIndexs);
            int assetIdIndex = Integer.parseInt(assetIdIndexs);
            int positionIndex = Integer.parseInt(positionIndexs);

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
                int recordsperpage = clientlogin.getCount();
                ArrayList list = new ArrayList();
                int count = 0;
                if (next1.equals("p") || next1.equals("n")) 
                {
                    list = clientlogin.getClientLoginByName(search, n, recordsperpage, positionIndex, clientIdIndex, assetIdIndex,  uId);
                    if (list.size() > 0) {
                        ClientloginInfo cinfo = (ClientloginInfo) list.get(list.size() - 1);
                        count = cinfo.getJobpostId();
                        list.remove(list.size() - 1);
                    }
                } else {
                    list = clientlogin.getClientLoginByName(search, n, recordsperpage, positionIndex, clientIdIndex, assetIdIndex,  uId);
                    if (list.size() > 0) {
                        ClientloginInfo cinfo = (ClientloginInfo) list.get(list.size() - 1);
                        count = cinfo.getJobpostId();
                        list.remove(list.size() - 1);
                    }
                }
                session.setAttribute("COUNT_LIST", count + "");
                session.setAttribute("CLIENTLOGIN_LIST", list);

                int pageSize = count / recordsperpage;
                if (count % recordsperpage > 0) {
                    pageSize = pageSize + 1;
                }

                int candidateCount = count;
                String prev = "";
                String prevclose = "";
                String next = "";
                String nextclose = "";
                int last = 0;
                int total = list.size();
                str.append("<input type='hidden' name='totalpage' value='" + pageSize + "' />");
                str.append("<input type='hidden' name='nextDel' value='" + total + "'/>");
                str.append("<input type='hidden' name='nextValue' value='" + (next_value) + "'/>");
                if (n == 0 && candidateCount > recordsperpage) {
                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                } else if (n > 0 && candidateCount > ((n * recordsperpage) + recordsperpage)) {
                    next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                } else if (n > 0 && candidateCount <= ((n * recordsperpage) + recordsperpage)) {
                    prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                    last = candidateCount;
                } else {
                    recordsperpage = candidateCount;
                }
                int value = n * recordsperpage + 1;
                int value1 = last > 0 ? last : n * recordsperpage + recordsperpage;
                int test = n;
                int noOfPages = 1;
                if (recordsperpage > 0) {
                    noOfPages = candidateCount / recordsperpage;
                    if (candidateCount % recordsperpage > 0) {
                        noOfPages += 1;
                    }
                }
                if (total > 0) 
                {
                    str.append("<div class='wesl_pagination pagination-mg m_15'>");
                    str.append("<div class='wesl_pg_rcds'>");
                    str.append("Page&nbsp;" + (next_value) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + candidateCount + ")&nbsp;");
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
                    str.append("<div class='table-responsive mb-0 ellipse_code' data-bs-pattern='priority-columns'>");
                    
                    str.append(thead);
                    str.append("<ul class='comp_list_detail'>");
                    
                    ClientloginInfo info;
                    for (int i = 0; i < total; i++)
                    {
                        info = (ClientloginInfo) list.get(i);
                        if (info != null) 
                        {
                            str.append("<li class='hand_cursor'>");
                                str.append("<div class='comp_list_main'>");
                                    str.append("<ul>");

                                        str.append("<li class='ref_no_label' onclick=\"javascript: view('" + info.getJobpostId() + "');\">");
                                            str.append("<span class='mob_show label_title'>Ref. No.</span>");
                                            str.append("<span class='value_record'>" + (clientlogin.changeNum(info.getJobpostId(), 6)) + "</span>");
                                        str.append("</li>");
                                        
                                        str.append("<li class='posted_on_label' onclick=\"javascript: view('" + info.getJobpostId() + "');\">");
                                            str.append("<span class='mob_show label_title'>Posted On</span>"); 
                                            str.append("<span class='value_record'>" + (info.getDate() != null ? info.getDate() : "") + "</span>");
                                        str.append("</li>");
                                        
                                        str.append("<li class='client_asset_label' onclick=\"javascript: view('" + info.getJobpostId() + "');\">");
                                            str.append("<span class='mob_show label_title'>Client - Asset</span>"); 
                                            str.append("<span class='value_record'>" + (info.getClientName() != null ? info.getClientName() : "") + "</span>");
                                        str.append("</li>");
                                        
                                        str.append("<li class='position_rank_label' onclick=\"javascript: view('" + info.getJobpostId() + "');\">");
                                            str.append("<span class='mob_show label_title'>Position - Rank</span>"); 
                                            str.append("<span class='value_record'>" + (info.getPositionname() != null ? info.getPositionname() : "") + "</span>");
                                        str.append("</li>");
                                        
                                        str.append("<li class='client_status_label' onclick=\"javascript: view('" + info.getJobpostId() + "');\">");
                                            str.append("<span class='mob_show label_title'>Status</span>"); 
                                            str.append("<span class='round_circle circle_exipry'></span>"); 
                                            str.append("<span class='value_record'>" + (info.getStVal() != null ? info.getStVal() : "") + "</span>");
                                        str.append("</li>");

                                        str.append("<li class='feedback_filter_label mob_colum_hide'>&nbsp;</li>");
                                        str.append("<li class='accepted_label text-center' onclick=\"javascript: view('" + info.getJobpostId() + "');\">");
                                            str.append("<span class='mob_show label_title'>Accepted</span>"); 
                                            str.append("<a href=\"javascript:;\" class='value_record que_number'>" + info.getSelcount() + "</a> / <a href=\"javascript:;\" class='value_record que_number'>" + info.getOpening() + "</a>");
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
                    str.append("(" + value + " - " + value1 + " record(s) of " + candidateCount + ")&nbsp;");
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
                    str.append("<div class='col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12'>");
                    str.append("<div class='mt_15'>");
                    str.append("<ul class='comp_list_detail'>");
                    str.append("<li class='empty_record'>");
                    str.append("<div class='comp_list_main'>");
                    str.append("<ul>");
                    str.append("<li class='comp_name_label ord_list_1'>");
                    str.append("<span class='value_record'>");
                    str.append(clientlogin.getMainPath("record_not_found"));
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
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>