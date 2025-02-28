<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.feedback.FeedbackInfo" %>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="feedback" class="com.web.jxp.feedback.Feedback" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("CREWLOGIN") != null) 
        {
            int crewrotationId = 0,candidateId = 0;
            if (request.getSession().getAttribute("CREWLOGIN") != null) 
            {
                CrewloginInfo info = (CrewloginInfo) request.getSession().getAttribute("CREWLOGIN");
                if (info != null)
                {
                    crewrotationId = info.getCrewrotationId();
                    candidateId = info.getCandidateId();
                }
            }
            if (crewrotationId > 0)
            {
                int showsizelist = feedback.getCountList("show_size_list");
                StringBuilder sb = new StringBuilder();
                sb.append("<ul class='comp_list' id='printArea'>");
                
                 sb.append("<li class='contracts_name_label thead_font'>Contract Name</li>");
                 sb.append("<li class='contracts_client_label thead_font'>Client</li>");
                 sb.append("<li class='contracts_asset_label thead_font'>Asset</li>");
                 sb.append("<li class='contracts_val_label text-center thead_font'>Validity<div class='contracts_from_to_label thead_font'>");
                         sb.append("<span>From</span>");
                         sb.append("<span>To</span>");
                     sb.append("</div>");
                 sb.append("</li>");
                 sb.append("<li class='contracts_acc_date_label text-center thead_font'>Acceptance Date</li>");
                 sb.append("<li class='contracts_actions_label text-center thead_font'>Actions</li>");
                
                sb.append("</ul>");
                String thead = sb.toString();
                sb.setLength(0);
                
                String search = request.getParameter("search") != null ? vobj.replacedesc(request.getParameter("search")) : "";
                String nextContractValue = request.getParameter("nextContractValue") != null && !request.getParameter("nextContractValue").equals("") ? vobj.replaceint(request.getParameter("nextContractValue")) : "0";
                String dodirects = request.getParameter("doDirect") != null && !request.getParameter("doDirect").equals("") ? vobj.replaceint(request.getParameter("doDirect")) : "-1";
                String fromDate = request.getParameter("fromDate") != null ? request.getParameter("fromDate") : "";
                String toDate = request.getParameter("toDate") != null ? request.getParameter("toDate") : "";
                String crewClientIds = request.getParameter("crewClientId") != null && !request.getParameter("crewClientId").equals("") ? vobj.replaceint(request.getParameter("crewClientId")) : "-1";
                String crewAssetIds = request.getParameter("crewAssetId") != null && !request.getParameter("crewAssetId").equals("") ? vobj.replaceint(request.getParameter("crewAssetId")) : "-1";
                
                int crewClientId = Integer.parseInt(crewClientIds);
                int crewAssetId = Integer.parseInt(crewAssetIds);
                int n = Integer.parseInt(nextContractValue);
                int dodirect = Integer.parseInt(dodirects);
                if (true)
                {
                    StringBuilder str = new StringBuilder();
                    int m = n;
                    int next_contractvalue = 0;
                    String next1 = request.getParameter("next");
                    if (next1 != null && next1.equals("n")) {
                        if (dodirect >= 0) {
                            n = dodirect;
                            next_contractvalue = dodirect + 1;
                        } else {
                            n = n;
                            next_contractvalue = m + 1;
                        }
                    } else if (next1 != null && next1.equals("p")) {
                        n = n - 2;
                        next_contractvalue = m - 1;
                    } else if (next1 != null && next1.equals("s")) {
                        n = 0;
                        next_contractvalue = 1;
                    }
                    session.setAttribute("NEXTCONTRACTVALUE", next_contractvalue + "");
                    int recordsperpage = feedback.getCount();
                    ArrayList list = new ArrayList();
                    int count = 0;
                    if (next1.equals("p") || next1.equals("n")) {
                        list = feedback.getContractListing(candidateId, search, fromDate, toDate, crewClientId, crewAssetId, n, recordsperpage);
                        if (list.size() > 0) {
                            FeedbackInfo cinfo = (FeedbackInfo) list.get(list.size() - 1);
                            count = cinfo.getContractdetailId();
                            list.remove(list.size() - 1);
                        }
                    } else {
                        list = feedback.getContractListing(candidateId, search, fromDate, toDate, crewClientId, crewAssetId, n, recordsperpage);
                        if (list.size() > 0) {
                            FeedbackInfo cinfo = (FeedbackInfo) list.get(list.size() - 1);
                            count = cinfo.getContractdetailId();
                            list.remove(list.size() - 1);
                        }
                    }
                    session.setAttribute("COUNT_CONTRACT", count + "");
                    session.setAttribute("CONTRACTLIST", list);

                    int pageSize = count / recordsperpage;
                    if (count % recordsperpage > 0) {
                        pageSize = pageSize + 1;
                    }

                    int contractCount = count;
                    String prev = "";
                    String prevclose = "";
                    String next = "";
                    String nextclose = "";
                    int last = 0;
                    int total = list.size();
                    str.append("<input type='hidden' name='totalpage' value='" + pageSize + "' />");
                    str.append("<input type='hidden' name='nextContractValue' value='" + (next_contractvalue) + "'/>");
                    if (n == 0 && contractCount > recordsperpage) {
                        next = ("<li class='next'><a href=\"javascript: searchContract('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                        nextclose = ("</i></a></li>");
                    } else if (n > 0 && contractCount > ((n * recordsperpage) + recordsperpage)) {
                        next = ("<li class='next'><a href=\"javascript: searchContract('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                        nextclose = ("</i></a></li>");
                        prev = ("<li class='prev'><a href=\"javascript: searchContract('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                        prevclose = ("</i></a></li>");
                    } else if (n > 0 && contractCount <= ((n * recordsperpage) + recordsperpage)) {
                        prev = ("<li class='prev'><a href=\"javascript: searchContract('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                        prevclose = ("</i></a></li>");
                        last = contractCount;
                    } else {
                        recordsperpage = contractCount;
                    }
                    int value = n * recordsperpage + 1;
                    int value1 = last > 0 ? last : n * recordsperpage + recordsperpage;
                    int test = n;
                    int noOfPages = 1;
                    if (recordsperpage > 0) {
                        noOfPages = contractCount / recordsperpage;
                        if (contractCount % recordsperpage > 0) {
                            noOfPages += 1;
                        }
                    }
                    str.append("<div class='col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12'>");
                    str.append("<div class='wesl_pagination pagination-mg m_15'>");
                    str.append("<div class='wesl_pg_rcds'>");
                    str.append("Page&nbsp;" + (next_contractvalue) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + contractCount + ")&nbsp;");
                    str.append("</div>");
                    str.append("<div class='wesl_No_pages'>");
                    str.append("<div class='loanreportTables_paginate paging_bootstrap_full_number'>");
                    str.append("<ul class='wesl_pagination'>");
                    if (noOfPages > 1 && next_contractvalue != 1) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchContract('n', '0');\" title='First' style='height: 33px;'><i class='fa fa-angle-double-left'></i></a></li>");
                    }
                    str.append(prev + prevclose + "&nbsp;");
                    int a1 = test - showsizelist;
                    int a2 = test + showsizelist;
                    for (int ii = a1; ii <= a2; ii++) {
                        if (ii >= 0 && ii < noOfPages) {
                            if (ii == test) {
                                str.append("<li class='wesl_active'><a href='#'> " + (ii + 1) + " </a></li>");
                            } else {
                                str.append("<li><a href=\"javascript: searchContract('n', '" + ii + "');\" title=''> " + (ii + 1) + " </a></li>");
                            }
                        }
                    }
                    str.append(next + nextclose);
                    if (noOfPages > 1 && next_contractvalue != noOfPages) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchContract('n','" + (noOfPages - 1) + "');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
                    }
                    str.append("</ul></div></div></div>");

                    str.append("<div id='printArea'>");
                    str.append(thead);
                    if (total > 0) 
                    {
                        str.append("<ul class='comp_list_detail' id='ajax_contract'>");

                        FeedbackInfo ainfo;
                        for (int i = 0; i < total; i++) 
                        {
                            ainfo = (FeedbackInfo) list.get(i);
                            if (ainfo != null) 
                            {
                                
                                str.append("<li>");
                                    str.append("<div class='comp_list_main'>");
                                        str.append("<ul>");
                                            str.append("<li class='contracts_name_label mob_mb_15 contr_ord_list_1'>");
                                                str.append("<span class='value_record'>"+(ainfo.getContract() != null ? ainfo.getContract() : "")+"</span>");
                                            str.append("</li>");
                                            str.append("<li class='contracts_client_label mob_mb_15 contr_ord_list_2'>");
                                                str.append("<span class='mob_show label_title'>Client</span>");
                                               str.append(" <span class='value_record'>"+ (ainfo.getClientName() != null ? ainfo.getClientName() : "")+ "</span>");
                                            str.append("</li>");
                                            str.append("<li class='contracts_client_label mob_mb_15 contr_ord_list_3'>");
                                                str.append("<span class='mob_show label_title'>Asset</span>");
                                                str.append("<span class='value_record'>"+ (ainfo.getAssetName() != null ? ainfo.getAssetName() : "")+ "</span>");
                                            str.append("</li>");
                                            str.append("<li class='contracts_val_label mob_mb_15 val_from_to text-center contr_ord_list_4'>");
                                                str.append("<span class='mob_show label_title'>Validity From</span>");
                                                str.append("<span class='value_record'>"+ (ainfo.getFromDate() != null ? ainfo.getFromDate() : "") +"</span>");
                                            str.append("</li>");
                                            str.append("<li class='contracts_val_label mob_mb_15 val_from_to text-center contr_ord_list_5'>");
                                                str.append("<span class='mob_show label_title'>Validity To</span>");
                                                str.append("<span class='value_record'>"+ (ainfo.getToDate() != null ? ainfo.getToDate() : "")+ "</span>");
                                            str.append("</li>");
                                            str.append("<li class='contracts_acc_date_label mob_mb_15 text-center contr_ord_list_6'>");
                                                str.append("<span class='mob_show label_title'>Acceptance Date</span>");
                                                str.append("<span class='value_record'>"+ (ainfo.getAcceptanceDate() != null ? ainfo.getAcceptanceDate() : "")+ "</span>");
                                            str.append("</li>");

                                            str.append("<li class='contracts_actions_label action_list text-center contr_ord_list_7'>");
                                                str.append("<a href=\"javascript:;\" onclick=\"javascript: getModalOf('"+ainfo.getContractdetailId()+"');\" class='mr_15' data-bs-toggle='modal' data-bs-target='#user_comp_pending_modal'><img src='../assets/images/view.png'/></a>");	
                                            str.append("</li>");
                                        str.append("</ul>");
                                    str.append("</div>");
                                str.append("</li>");
                            }
                        }
                        str.append("</u1l>");
                    } else {
                        str.append("<ul class='comp_list_detail' id='sort_id'>");
                        str.append("<li class='empty_record'>");
                        str.append("<div class='comp_list_main'>");
                        str.append("<ul>");
                        str.append("<span class='value_record'>");
                        str.append(feedback.getMainPath("record_not_found"));
                        str.append("</span>");
                        str.append("</ul>");
                        str.append("</div>");
                        str.append("</li>");
                        str.append("</u1l>");
                    }
                    str.append("</div>");

                    str.append("<div class='wesl_pagination pagination-mg mt_15'>");
                    str.append("<div class='wesl_pg_rcds'>");
                    str.append("Page&nbsp;" + (next_contractvalue) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + contractCount + ")&nbsp;");
                    str.append("</div>");
                    str.append("<div class='wesl_No_pages'>");
                    str.append("<div class='loanreportTables_paginate paging_bootstrap_full_number'>");
                    str.append("<ul class='wesl_pagination'>");
                    if (noOfPages > 1 && next_contractvalue != 1) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchContract('n', '0');\" title='First' style='height: 33px;'><i class='fa fa-angle-double-left'></i></a></li>");
                    }
                    str.append(prev + prevclose + "&nbsp;");
                    for (int ii = a1; ii <= a2; ii++) {
                        if (ii >= 0 && ii < noOfPages) {
                            if (ii == test) {
                                str.append("<li class='wesl_active'><a href='#'> " + (ii + 1) + " </a></li>");
                            } else {
                                str.append("<li><a href=\"javascript: searchContract('n', '" + ii + "');\" title=''> " + (ii + 1) + " </a></li>");
                            }
                        }
                    }
                    str.append(next + nextclose);
                    if (noOfPages > 1 && next_contractvalue != noOfPages) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchContract('n','" + (noOfPages - 1) + "');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
                    }
                    str.append("</ul></div></div></div></div>");
                    
                    String s1 = str.toString();
                    str.setLength(0);
                    response.getWriter().write(s1);
                } else {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            } else {
                response.getWriter().write("Something went wrong");
            }
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>