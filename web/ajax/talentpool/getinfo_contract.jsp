<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<jsp:useBean id="ddl" class="com.web.jxp.talentpool.Ddl" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String per = "N", addper = "N", editper = "N", deleteper = "N", approveper = "N";
            if (uinfo != null) 
            {
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                deleteper = uinfo.getDeleteper() != null ? uinfo.getDeleteper() : "N";
                approveper = uinfo.getApproveper() != null ? uinfo.getApproveper() : "N";
            }
            TalentpoolInfo dinfo = null;
            int dstatus = 0;
            if (session.getAttribute("CANDIDATE_DETAIL") != null) {
                dinfo = (TalentpoolInfo) session.getAttribute("CANDIDATE_DETAIL");
                dstatus = dinfo.getStatus();
            }
            int showsizelist = ddl.getCountList("show_size_list");
            String file_path = ddl.getMainPath("view_candidate_file");
            
            StringBuilder sb = new StringBuilder();
            sb.append("<thead>");
            sb.append("<tr class='first_row'>");
                sb.append("<th colspan='5' width='%'>&nbsp;</th>");
                sb.append("<th colspan='2' width='%' class='text-center'><span><b>Validity</b></span></th>");
                sb.append("<th colspan='2' width='%' class='text-center'><span><b>Approval</th>");
                sb.append("<th width='%'>&nbsp;</th>");
                sb.append("<th width='%'>&nbsp;</th>");
            sb.append("</tr>");    
            sb.append("<tr>");
                sb.append("<th rowspan='2' width='%'><span><b>Position</b></span></th>");
                sb.append("<th rowspan='2' width='%'><span><b>Client</b></span></th>");
                sb.append("<th rowspan='2' width='%'><span><b>Asset</b></span></th>");
                sb.append("<th rowspan='2' width='%'><span><b>Type</b></span></th>");
                sb.append("<th rowspan='2' width='%'><span><b>Status</b></span></th>");
                sb.append("<th width='%' class='text-center'><span><b>From</b></span></th>");                                                                
                sb.append("<th width='%' class='text-center'><span><b>To</b></span></th>");  
                sb.append("<th width='%' class='text-center'><span><b>Company</b></span></th>");
                sb.append("<th width='%' class='text-center'><span><b>Crew</b></span></th>");  
                sb.append("<th width='%' class='text-center'>&nbsp;</th>"); 
                sb.append("<th rowspan='2' width='%' class='text-right'><span><b>Actions</b></span></th>");
            sb.append("</tr>");
            sb.append("</thead>");
            String thead = sb.toString();
            sb.setLength(0);
            
            String nextContractValue = request.getParameter("nextContractValue") != null && !request.getParameter("nextContractValue").equals("") ? vobj.replaceint(request.getParameter("nextContractValue")) : "0";
            String dodirects = request.getParameter("doDirect") != null && !request.getParameter("doDirect").equals("") ? vobj.replaceint(request.getParameter("doDirect")) : "-1";
            String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "0";
            String clientIdContracts = request.getParameter("clientIdContract") != null && !request.getParameter("clientIdContract").equals("") ? vobj.replaceint(request.getParameter("clientIdContract")) : "-1";
            String assetIdContracts = request.getParameter("assetIdContract") != null && !request.getParameter("assetIdContract").equals("") ? vobj.replaceint(request.getParameter("assetIdContract")) : "-1";
            String contractStatuss = request.getParameter("contractStatus") != null && !request.getParameter("contractStatus").equals("") ? vobj.replaceint(request.getParameter("contractStatus")) : "-1";
            String fromdate = request.getParameter("fromdate") != null && !request.getParameter("fromdate").equals("") ? vobj.replacedate(request.getParameter("fromdate")) : "";
            String todate = request.getParameter("todate") != null && !request.getParameter("todate").equals("") ? vobj.replacedate(request.getParameter("todate")) : "";
            int candidateId = Integer.parseInt(candidateIds);
            int clientIdContract = Integer.parseInt(clientIdContracts);
            int assetIdContract = Integer.parseInt(assetIdContracts);
            int contractStatus = Integer.parseInt(contractStatuss);
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
                int recordsperpage = ddl.getCount();
                ArrayList list = new ArrayList();
                int count = 0;
                if (next1.equals("p") || next1.equals("n")) 
                {
                    list = ddl.getContractListing(candidateId, clientIdContract, assetIdContract, contractStatus, fromdate, todate, n, recordsperpage);
                    if (list.size() > 0) {
                        TalentpoolInfo cinfo = (TalentpoolInfo) list.get(list.size() - 1);
                        count = cinfo.getContractdetailId();
                        list.remove(list.size() - 1);
                    }
                } else {
                    list = ddl.getContractListing(candidateId, clientIdContract, assetIdContract, contractStatus, fromdate, todate, n, recordsperpage);
                    if (list.size() > 0) {
                        TalentpoolInfo cinfo = (TalentpoolInfo) list.get(list.size() - 1);
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

                int talentpoolCount = count;
                String prev = "";
                String prevclose = "";
                String nextContract = "";
                String nextclose = "";
                int last = 0;
                int total = list.size();
                str.append("<input type='hidden' name='totalpage' value='" + pageSize + "' />");
                str.append("<input type='hidden' name='nextDel' value='" + total + "'/>");
                str.append("<input type='hidden' name='nextContractValue' value='" + (next_contractvalue) + "'/>");
                if (n == 0 && talentpoolCount > recordsperpage) {
                    nextContract = ("<li class='next'><a href=\"javascript: searchContract('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                } else if (n > 0 && talentpoolCount > ((n * recordsperpage) + recordsperpage)) {
                    nextContract = ("<li class='next'><a href=\"javascript: searchContract('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                    prev = ("<li class='prev'><a href=\"javascript: searchContract('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                } else if (n > 0 && talentpoolCount <= ((n * recordsperpage) + recordsperpage)) {
                    prev = ("<li class='prev'><a href=\"javascript: searchContract('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                    last = talentpoolCount;
                } else {
                    recordsperpage = talentpoolCount;
                }
                int value = n * recordsperpage + 1;
                int value1 = last > 0 ? last : n * recordsperpage + recordsperpage;
                int test = n;
                int noOfPages = 1;
                if (recordsperpage > 0) {
                    noOfPages = talentpoolCount / recordsperpage;
                    if (talentpoolCount % recordsperpage > 0) {
                        noOfPages += 1;
                    }
                }
                if (total > 0) 
                {
                    str.append("<div class='wesl_pagination pagination-mg m_15'>");
                    str.append("<div class='wesl_pg_rcds'>");
                    str.append("Page&nbsp;" + (next_contractvalue) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + talentpoolCount + ")&nbsp;");
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
                    str.append(nextContract + nextclose);
                    if (noOfPages > 1 && next_contractvalue != noOfPages) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchContract('n','" + (noOfPages - 1) + "');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
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
                        info = (TalentpoolInfo) list.get(i);
                        if (info != null) 
                        {
                            str.append("<tr>");
                            str.append("<td>" + (info.getPositionname() != null ? info.getPositionname() : "") + "</td>");
                            str.append("<td>" + (info.getClientName() != null ? info.getClientName() : "") + "</td>");
                            str.append("<td>" + (info.getAssetName() != null ? info.getAssetName() : "") + "</td>");
                            str.append("<td>" + (info.getType() == 1 ? "Main" : "Repeated") + "</td>");
                            str.append("<td>" + (info.getStatusValue() != null ? info.getStatusValue() : "") + "</td>");
                            str.append("<td class='text-center'>" + (info.getFromDate() != null ? info.getFromDate() : "") + "</td>");                            
                            str.append("<td class='text-center'>" + (info.getToDate() != null ? info.getToDate() : "") + "</td>");                            
                            str.append("<td class='text-center sel_uns_check'>");
                                if (approveper.equals("Y") && info.getApproval2() == 0 && info.getStatus() == 1 && dstatus != 4) {
                                str.append("<a href='javascript:;' data-bs-toggle='modal' data-bs-target='#crew_contract_modal' onclick=\"javascript: getCrewDetails('"+ info.getContractdetailId() +"', '2');\">");
                                    str.append("<img src='../assets/images/unselected_check.png'/>");
                                str.append("</a>");
                                } else if (info.getApproval2() == 1) {
                                    str.append("<a><img src='../assets/images/selected_check.png'/></a>");
                                }      
                            str.append("</td>");
                            str.append("<td class='text-center sel_uns_check'>");
                               if (approveper.equals("Y") && info.getApproval1() == 0 && info.getApproval2() == 1 && info.getStatus() == 1 && dstatus != 4) {
                                str.append("<a href='javascript:;' data-bs-toggle='modal' data-bs-target='#crew_contract_modal' onclick=\"javascript: getCrewDetails('"+ info.getContractdetailId()+ "', '1');\"><img src='../assets/images/unselected_check.png'/></a>");
                                } else if (info.getApproval1() == 1) {
                                    str.append("<a><img src='../assets/images/selected_check.png'/></a>");
                                }       
                            str.append("</td>");
                            str.append("<td class='text-center repeat_btn'>");
                            if (addper.equals("Y") && info.getType() == 1 && info.getStatus() == 1 && info.getApproval1() == 1 && info.getApproval2() == 1 && dstatus != 4) {
                                str.append("<a href=\"javascript: repeatContract('2','"+info.getContractId()+"', '-1');\" class=' mr_15'>Repeat</a>");
                                }
                            str.append("</td>");

                            str.append("<td class='action_column'>");
                                if(info.getApproval1() == 1 || info.getApproval2() == 1){
                                    str.append("<a class='mr_15' href=\"javascript:;\" data-bs-toggle='modal' data-bs-target='#crew_contractremarks' onclick=\"javascript: getCrewRemarkDetails('"+ info.getContractdetailId()+ "');\">");
                                        str.append("<img src='../assets/images/info.png'/>");
                                    str.append("</a>");
                                }
                                str.append("<a href='javascript:;' onclick=\"javascript: viewContractDoc('"+ info.getContractdetailId()+"', '"+info.getFile1()+"', '"+info.getFile2()+"', '"+info.getFile3()+"');\" class='mr_15' data-bs-toggle='modal' data-bs-target='#view_resume_list'><img src='../assets/images/attachment.png'/></a>");
                                if (editper.equals("Y") && info.getStatus() == 1 && info.getApproval1() <= 0 && info.getApproval2() <= 0 && dstatus != 4) {
                                    str.append("<a href=\"javascript: modifyContract('"+info.getType() +"', '"+ info.getContractdetailId()+"') ;\" class=' mr_15'><img src='../assets/images/pencil.png'/></a>");
                                }
                                str.append("<span class='switch_bth'>");  
                                   if (deleteper.equals("Y") && dstatus != 4) {
                                    str.append("<div class='form-check form-switch'>");
                                        str.append("<input class='form-check-input' type='checkbox' role='switch' id='flexSwitchCheckDefault_" + (i) + "' ");
                                                if(info.getStatus() == 1 && dstatus != 4){
                                                    str.append("checked onclick=\"javascript: deleteContractForm('"+ info.getContractdetailId() +"', '"+ info.getStatus() +"', '"+i+"');\" ");
                                                }
                                                if(info.getStatus() ==2) {
                                                    str.append("disabled='true' "); 
                                                 }
                                                str.append("/>");
                                    str.append("</div>");
                                    }
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
                    str.append("Page&nbsp;" + (next_contractvalue) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + talentpoolCount + ")&nbsp;");
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
                    str.append(nextContract + nextclose);
                    if (noOfPages > 1 && next_contractvalue != noOfPages) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchContract('n','" + (noOfPages - 1) + "');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
                    }
                    str.append("</ul></div></div></div>");
                } else {
                    str.append("<div class='panel-body'>");
                    str.append("<div class='table-responsive'>");
                    str.append("<table class='table table-bordered table-hover' id='dataTables-example'>");
                    str.append("<tbody>");
                    str.append("<tr class='grid1'>");
                    str.append("<td>" + ddl.getMainPath("record_not_found") + "</td>");
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