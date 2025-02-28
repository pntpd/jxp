<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
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
            int showsizelist = talentpool.getCountList("show_size_list");
            String search = request.getParameter("search") != null ? vobj.replacedesc(request.getParameter("search")) : "";
            String statusIndexs = request.getParameter("statusIndex") != null && !request.getParameter("statusIndex").equals("") ? vobj.replaceint(request.getParameter("statusIndex")) : "0";
            String nextValue = request.getParameter("nextValue") != null && !request.getParameter("nextValue").equals("") ? vobj.replaceint(request.getParameter("nextValue")) : "0";
            String dodirects = request.getParameter("doDirect") != null && !request.getParameter("doDirect").equals("") ? vobj.replaceint(request.getParameter("doDirect")) : "-1";
            String positionIndexIds = request.getParameter("positionIndexId") != null && !request.getParameter("positionIndexId").equals("") ? vobj.replaceint(request.getParameter("positionIndexId")) : "-1";
            String clientIndexs = request.getParameter("clientIndex") != null && !request.getParameter("clientIndex").equals("") ? vobj.replaceint(request.getParameter("clientIndex")) : "-1";
            String locationIndexs = request.getParameter("locationIndex") != null && !request.getParameter("locationIndex").equals("") ? vobj.replaceint(request.getParameter("locationIndex")) : "-1";
            String assetIndexs = request.getParameter("assetIndex") != null && !request.getParameter("assetIndex").equals("") ? vobj.replaceint(request.getParameter("assetIndex")) : "-1";
            String employementIndexs = request.getParameter("employementIndex") != null && !request.getParameter("employementIndex").equals("") ? vobj.replaceint(request.getParameter("employementIndex")) : "-1";
            String verifieds = request.getParameter("verified") != null && !request.getParameter("verified").equals("") ? vobj.replaceint(request.getParameter("verified")) : "-1";
            String assettypeIdIndexs = request.getParameter("assettypeIdIndex") != null && !request.getParameter("assettypeIdIndex").equals("") ? vobj.replaceint(request.getParameter("assettypeIdIndex")) : "-1";
            String positionFilters = request.getParameter("positionFilter") != null && !request.getParameter("positionFilter").equals("") ? vobj.replaceint(request.getParameter("positionFilter")) : "-1";

            int n = Integer.parseInt(nextValue);
            int dodirect = Integer.parseInt(dodirects);
            int statusIndex = Integer.parseInt(statusIndexs);
            int positionIndexId = Integer.parseInt(positionIndexIds);
            int clientIndex = Integer.parseInt(clientIndexs);
            int locationIndex = Integer.parseInt(locationIndexs);
            int assetIndex = Integer.parseInt(assetIndexs);
            int verified = Integer.parseInt(verifieds);
            int assettypeIdIndex = Integer.parseInt(assettypeIdIndexs);
            int positionFilter = Integer.parseInt(positionFilters);
            int employementIndex = Integer.parseInt(employementIndexs);
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = talentpool.getLocalIp();

            if (request.getParameter("deleteVal") == null) 
            {
                StringBuilder sb = new StringBuilder();
                sb.append("<thead>");
                sb.append("<tr>");
                sb.append("<th width='7%' class='text-center select_option'>");
                sb.append("<span><b>Certified</b> </span>");
                sb.append("<div class='form-check permission-check'>");
                sb.append("<input class='form-check-input' type='checkbox' name='verified' id = 'switch1' onclick=\"javascript: searchFormAjax('s','-1');\" value='1' ");
                if (verified == 1) {
                    sb.append("checked");
                }
                sb.append(" >");
                sb.append("</div>");
                sb.append("</th>");
                sb.append("<th width='13%'><span><b>Employee Id</b> </span>");
                sb.append("<a href=\"javascript: sortForm('6', '2');\" id='img_6_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
                sb.append("<a href=\"javascript: sortForm('6', '1');\" id='img_6_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
                sb.append("</th>");
                sb.append("<th width='17%'><span><b>Name</b> </span>");
                sb.append("<a href=\"javascript: sortForm('1', '2');\" id='img_1_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
                sb.append("<a href=\"javascript: sortForm('1', '1');\" id='img_1_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
                sb.append("</th>");
                sb.append("<th width='16%'><span><b>Position-Rank</b> </span>");
                sb.append("<a href=\"javascript: sortForm('2', '2');\" id='img_2_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
                sb.append("<a href=\"javascript: sortForm('2', '1');\" id='img_2_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
                sb.append("</th>");
                sb.append("<th  width='17%'><span><b>Client</b> </span>");
                sb.append("<a href=\"javascript: sortForm('3', '2');\" id='img_3_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
                sb.append("<a href=\"javascript: sortForm('3', '1');\" id='img_3_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
                sb.append("</th>");
                sb.append("<th width='12%'><span><b>Location</b> </span>");
                sb.append("<a href=\"javascript: sortForm('4', '2');\" id='img_4_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
                sb.append("<a href=\"javascript: sortForm('4', '1');\" id='img_4_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
                sb.append("</th>");
                sb.append("<th width='12%'><span><b>Asset</b> </span>");
                sb.append("<a href=\"javascript: sortForm('5', '2');\" id='img_5_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
                sb.append("<a href=\"javascript: sortForm('5', '1');\" id='img_5_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
                sb.append("</th>");
                sb.append("<th width='11%'><span><b>Alerts</b> </span>");
                sb.append("</th>");
                sb.append("<th width='5%' class='text-center'><span><b>Employment <br/>Status</b> </span>");
                sb.append("</th>");
                sb.append("</tr>");
                sb.append("</thead>");
                String thead = sb.toString();
                sb.setLength(0);
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
                int recordsperpage = talentpool.getCount();
                ArrayList candidate_list = new ArrayList();
                int count = 0;
                if (next1.equals("p") || next1.equals("n")) {
                    candidate_list = talentpool.getCandidateByName(search, statusIndex, n, recordsperpage, positionIndexId, 
                            clientIndex, locationIndex, assetIndex, verified, employementIndex, allclient,  per,  cids,  assetids, assettypeIdIndex, positionFilter);
                        if (candidate_list.size() > 0) {
                        TalentpoolInfo cinfo = (TalentpoolInfo) candidate_list.get(candidate_list.size() - 1);
                        count = cinfo.getCandidateId();
                        candidate_list.remove(candidate_list.size() - 1);
                    }
                } else {
                    candidate_list = talentpool.getCandidateByName(search, statusIndex, n, recordsperpage,
                            positionIndexId, clientIndex, locationIndex, assetIndex, verified, employementIndex, 
                            allclient,  per,  cids,  assetids, assettypeIdIndex, positionFilter);
                    if (candidate_list.size() > 0) {
                        TalentpoolInfo cinfo = (TalentpoolInfo) candidate_list.get(candidate_list.size() - 1);
                        count = cinfo.getCandidateId();
                        candidate_list.remove(candidate_list.size() - 1);
                    }
                }
                session.setAttribute("COUNT_LIST", count + "");
                session.setAttribute("CANDIDATE_LIST", candidate_list);

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
                   
                    TalentpoolInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (TalentpoolInfo) candidate_list.get(i);
                        int vflag = 0, progress = 0, clientId = 0;
                        if (info != null) 
                        {
                            vflag = info.getVflag();
                            progress = info.getProgressId();
                            clientId = info.getClientId();
                            str.append("<tr class='hand_cursor' href='javascript: void(0);' \">");
                            str.append("<td class='ocs_cer_index text-center' data-org-colspan='1' data-columns='tech-companies-1-col-0'>");
                            if (vflag == 4) {
                                str.append("<img src='../assets/images/ocs_certified_index.png'>");
                            } else {
                                str.append("<a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a>");
                            }
                            str.append("</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() + "');\">" + (info.getEmployeeId() != null ? info.getEmployeeId() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() + "');\">" + (info.getName() != null ? info.getName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() + "');\">" + (info.getPosition() != null ? info.getPosition() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() + "');\">" + (info.getClientName() != null ? info.getClientName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() + "');\">" + (info.getCountryName() != null ? info.getCountryName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() + "');\">" + (info.getClientAsset() != null ? info.getClientAsset() : "") + "</td>");
                            str.append("<td><a href='javascript:;' data-bs-toggle='modal' data-bs-target='#client_position' onclick=\"javascript: viewAlertmodal('" + info.getCandidateId() + "','" + info.getName() + "','" + info.getPositionname() + "','" + info.getGradename() + "');\"><span class='alert_icon'><i class='ion ion-md-information-circle-outline'></i></span></a> " + talentpool.changeNum(info.getAlertCount(), 2) + "</td>");
                            str.append("<td class='hand_cursor text-center' data-org-colspan='1' data-columns='tech-companies-1-col-7'>");
                            if (clientId > 0) 
                            {
                                str.append("<img class='cer_img' src='../assets/images/active_status.png'>");
                            }else if(clientId <= 0 && info.getStatus() == 4) 
                            {
                                str.append("<img class='cer_img' src='../assets/images/deceased_status.png'>");                            
                            } else {
                                str.append("<img class='cer_img' src='../assets/images/inactive_status.png'>");
                            }
                            str.append("&nbsp;");
                            if (progress == 1) {
                                str.append("<img class='cer_img lock_icon' src='../assets/images/lock.png'>");
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
                    str.append("<td><b>" + talentpool.getMainPath("record_not_found") + "</b></td>");
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
            } 
            else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>