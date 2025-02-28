<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.compliancecheck.CompliancecheckInfo" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="compliancecheck" class="com.web.jxp.compliancecheck.Compliancecheck" scope="page"/>
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
            int showsizelist = compliancecheck.getCountList("show_size_list");
            StringBuilder sb = new StringBuilder();
            
            sb.append("<thead>");
            sb.append("<tr>");
            sb.append("<th width='%'><span><b>Ref</b> </span>");
            sb.append("<a href=\"javascript: sortForm('1', '2');\" id='img_1_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('1', '1');\" id='img_1_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='%'><span><b>Posted On</b> </span>");
            sb.append("<a href=\"javascript: sortForm('2', '2');\" id='img_2_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('2', '1');\" id='img_2_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th><span><b>Name</b> </span>");
            sb.append("<a href=\"javascript: sortForm('3', '2');\" id='img_3_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('3', '1');\" id='img_3_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='%'><span><b>Position - Rank</b> </span>");
            sb.append("<a href=\"javascript: sortForm('4', '2');\" id='img_4_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('4', '1');\" id='img_4_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='%'><span><b>Client - Asset</b> </span>");
            sb.append("<a href=\"javascript: sortForm('5', '2');\" id='img_5_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('5', '1');\" id='img_5_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='%' class='text-left'><span><b>Status</b> </span>");
            sb.append("</th>");
            sb.append("</tr>");
            sb.append("</thead>");
            
            String thead = sb.toString();
            sb.setLength(0);
            
            String search = request.getParameter("search") != null ? vobj.replacedesc(request.getParameter("search")) : "";
            String nextValue = request.getParameter("nextValue") != null && !request.getParameter("nextValue").equals("") ? vobj.replaceint(request.getParameter("nextValue")) : "0";
            String dodirects = request.getParameter("doDirect") != null && !request.getParameter("doDirect").equals("") ? vobj.replaceint(request.getParameter("doDirect")) : "-1";
            String clientIdIndexs = request.getParameter("clientIdIndex") != null && !request.getParameter("clientIdIndex").equals("") ? vobj.replaceint(request.getParameter("clientIdIndex")) : "-1";
            String pgvalue = request.getParameter("pgvalue") != null && !request.getParameter("pgvalue").equals("") ? request.getParameter("pgvalue") : "";
            String assetIdIndexs = request.getParameter("assetIdIndex") != null && !request.getParameter("assetIdIndex").equals("") ? vobj.replaceint(request.getParameter("assetIdIndex")) : "-1";
            
            int n = Integer.parseInt(nextValue);
            int dodirect = Integer.parseInt(dodirects);
            int  clientIdIndex = Integer.parseInt(clientIdIndexs);
            int assetIdIndex = Integer.parseInt(assetIdIndexs);
            
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
                int recordsperpage = compliancecheck.getCount();
                ArrayList candidate_list = new ArrayList();
                int count = 0;
                if (next1.equals("p") || next1.equals("n")) {
                    candidate_list = compliancecheck.getJobpostByName(search, n, recordsperpage, clientIdIndex,assetIdIndex, pgvalue,  allclient,  per,  cids,  assetids);
                    if (candidate_list.size() > 0) {
                        CompliancecheckInfo cinfo = (CompliancecheckInfo) candidate_list.get(candidate_list.size() - 1);
                        count = cinfo.getJobpostId();
                        candidate_list.remove(candidate_list.size() - 1);
                    }
                } else {
                    candidate_list = compliancecheck.getJobpostByName(search,  n, recordsperpage, clientIdIndex,assetIdIndex, pgvalue , allclient,  per,  cids,  assetids);
                    if (candidate_list.size() > 0) {
                        CompliancecheckInfo cinfo = (CompliancecheckInfo) candidate_list.get(candidate_list.size() - 1);
                        count = cinfo.getJobpostId();
                        candidate_list.remove(candidate_list.size() - 1);
                    }
                }
                session.setAttribute("COUNT_LIST", count + "");
                session.setAttribute("COMPLANCE_LIST", candidate_list);

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
                int total = candidate_list.size();
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
                    str.append("<div class='table-responsive mb-0' data-bs-pattern='priority-columns'>");
                    str.append("<table id='tech-companies-1' class='table table-striped'>");
                    str.append(thead);
                    str.append("<tbody id = 'sort_id'>");
                    
                    CompliancecheckInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (CompliancecheckInfo) candidate_list.get(i);
                        if (info != null) 
                        {
                            str.append("<tr class='hand_cursor'>");
                            str.append("<td class='hand_cursor' onclick=\"javascript: view('" + info.getCandidateId() +"','"+ info.getJobpostId()+"','"+info.getShortlistId()+"','"+ info.getStatus()+ "');\">" + (compliancecheck.changeNum(info.getJobpostId(), 6)) + "</td>");
                            str.append("<td class='hand_cursor' onclick=\"javascript: view('" + info.getCandidateId() +"','"+ info.getJobpostId()+"','"+info.getShortlistId()+"','"+ info.getStatus()+ "');\">" + (info.getDate() != null ? info.getDate() : "") + "</td>");
                            str.append("<td class='hand_cursor' onclick=\"javascript: view('" + info.getCandidateId() +"','"+ info.getJobpostId()+"','"+info.getShortlistId()+"','"+ info.getStatus()+ "');\">" + (info.getName() != null ? info.getName() : "") + "</td>");
                            str.append("<td class='hand_cursor' onclick=\"javascript: view('" + info.getCandidateId() +"','"+ info.getJobpostId()+"','"+info.getShortlistId()+"','"+ info.getStatus()+ "');\">" + (info.getPosition() != null ? info.getPosition() : "") + "</td>");
                            str.append("<td class='hand_cursor' onclick=\"javascript: view('" + info.getCandidateId() +"','"+ info.getJobpostId()+"','"+info.getShortlistId()+"','"+ info.getStatus()+ "');\">" + (info.getClientName() != null ? info.getClientName() : "") + "</td>");
                            str.append("<td class='hand_cursor' onclick=\"javascript: view('" + info.getCandidateId() +"','"+ info.getJobpostId()+"','"+info.getShortlistId()+"','"+ info.getStatus()+ "');\">" + (info.getCcStatus() != null ? info.getCcStatus() : "") + "</td>");
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
                    str.append("<div class='col-lg-12'>");
                    str.append("<div class='table-rep-plugin sort_table'>");
                    str.append("<div class='table-responsive mb-0' data-bs-pattern='priority-columns'>");
                    str.append("<table id='tech-companies-1' class='table table-striped'>");
                    str.append("<tbody>");
                    str.append("<tr>");
                    str.append("<td><b>" + compliancecheck.getMainPath("record_not_found") + "</b></td>");
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