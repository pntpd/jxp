<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.candidate.CandidateInfo" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="candidate" class="com.web.jxp.candidate.Candidate" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        if (session.getAttribute("LOGININFO") != null)
        {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String editper = "N";
            if (uinfo != null)
            {
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
            }
            int showsizelist = candidate.getCountList("show_size_list");
            String file_path = candidate.getMainPath("view_candidate_file");
            StringBuilder sb = new StringBuilder();
            sb.append("<thead>");
            sb.append("<tr>");           
            sb.append("<th width='20%'><span><b>Course Name</b> </span></th>");
            sb.append("<th width='15%'><span><b>Type</b> </span></th>");
            sb.append("<th width='20%'><span><b>Institution, Location</b> </span></th>");
            sb.append("<th width='10%'><span><b>Approved By</b> </span></th>");
            sb.append("<th width='10%'><span><b>Issue Date</b> </span></th>");
            sb.append("<th width='10%'><span><b>Expiry Date</b> </span></th>");
            sb.append("<th width='15%' class='text-center'><span><b>Actions</b></span></th>");
            sb.append("</tr>");
            sb.append("</thead>");
            String thead = sb.toString();
            sb.setLength(0);
            
            String nextCertificateValue = request.getParameter("nextCertificateValue") != null && !request.getParameter("nextCertificateValue").equals("") ? vobj.replaceint(request.getParameter("nextCertificateValue")) : "0";
            String dodirects = request.getParameter("doDirect") != null && !request.getParameter("doDirect").equals("") ? vobj.replaceint(request.getParameter("doDirect")) : "-1";
            String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "0";
            String courseIndexs = request.getParameter("courseIndex") != null && !request.getParameter("courseIndex").equals("") ? vobj.replaceint(request.getParameter("courseIndex")) : "-1";
            int candidateId = Integer.parseInt(candidateIds);
            int courseIndex = Integer.parseInt(courseIndexs);
            int n = Integer.parseInt(nextCertificateValue);
            int dodirect = Integer.parseInt(dodirects);
            
             if (true) 
             {
                StringBuilder str = new StringBuilder();
                int m = n;
                int next_certificatevalue = 0;
                String next1 = request.getParameter("next");
                if (next1 != null && next1.equals("n")) {
                    if (dodirect >= 0) {
                        n = dodirect;
                        next_certificatevalue = dodirect + 1;
                    } else {
                        n = n;
                        next_certificatevalue = m + 1;
                    }
                } else if (next1 != null && next1.equals("p")) {
                    n = n - 2;
                    next_certificatevalue = m - 1;
                } else if (next1 != null && next1.equals("s")) {
                    n = 0;
                    next_certificatevalue = 1;
                }
                session.setAttribute("NEXTCERTVALUE", next_certificatevalue + "");
                int recordsperpage = candidate.getCount();
                ArrayList candidate_list = new ArrayList();
                int count = 0;
                if (next1.equals("p") || next1.equals("n")) 
                {
                    candidate_list = candidate.gettrainingCertificatelist(candidateId, n, recordsperpage, courseIndex);
                    if (candidate_list.size() > 0) {
                        CandidateInfo cinfo = (CandidateInfo) candidate_list.get(candidate_list.size() - 1);
                        count = cinfo.getCandidateId();
                        candidate_list.remove(candidate_list.size() - 1);
                    }
                } else {
                    candidate_list = candidate.gettrainingCertificatelist(candidateId, n, recordsperpage, courseIndex);
                    if (candidate_list.size() > 0) {
                        CandidateInfo cinfo = (CandidateInfo) candidate_list.get(candidate_list.size() - 1);
                        count = cinfo.getCandidateId();
                        candidate_list.remove(candidate_list.size() - 1);
                    }
                }
                session.setAttribute("COUNT_CERTLIST", count + "");
                session.setAttribute("CANDTRAININGCERTLIST", candidate_list);

                int pageSize = count / recordsperpage;
                if (count % recordsperpage > 0) {
                    pageSize = pageSize + 1;
                }

                int candidateCount = count;
                String prev = "";
                String prevclose = "";
                String nextCertificate = "";
                String nextclose = "";
                int last = 0;
                int total = candidate_list.size();
                str.append("<input type='hidden' name='totalpage' value='" + pageSize + "' />");
                str.append("<input type='hidden' name='nextDel' value='" + total + "'/>");
                str.append("<input type='hidden' name='nextCertificateValue' value='" + (next_certificatevalue) + "'/>");
                if (n == 0 && candidateCount > recordsperpage) {
                    nextCertificate = ("<li class='next'><a href=\"javascript: searchCertificate('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                } else if (n > 0 && candidateCount > ((n * recordsperpage) + recordsperpage)) {
                    nextCertificate = ("<li class='next'><a href=\"javascript: searchCertificate('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                    nextclose = ("</i></a></li>");
                    prev = ("<li class='prev'><a href=\"javascript: searchCertificate('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                    prevclose = ("</i></a></li>");
                } else if (n > 0 && candidateCount <= ((n * recordsperpage) + recordsperpage)) {
                    prev = ("<li class='prev'><a href=\"javascript: searchCertificate('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
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
                    str.append("Page&nbsp;" + (next_certificatevalue) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + candidateCount + ")&nbsp;");
                    str.append("</div>");
                    str.append("<div class='wesl_No_pages'>");
                    str.append("<div class='loanreportTables_paginate paging_bootstrap_full_number'>");
                    str.append("<ul class='wesl_pagination'>");
                    if (noOfPages > 1 && next_certificatevalue != 1) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchCertificate('n', '0');\" title='First' style='height: 33px;'><i class='fa fa-angle-double-left'></i></a></li>");
                    }
                    str.append(prev + prevclose + "&nbsp;");
                    int a1 = test - showsizelist;
                    int a2 = test + showsizelist;
                    for (int ii = a1; ii <= a2; ii++) {
                        if (ii >= 0 && ii < noOfPages) {
                            if (ii == test) {
                                str.append("<li class='wesl_active'><a href='#'> " + (ii + 1) + " </a></li>");
                            } else {
                                str.append("<li><a href=\"javascript: searchCertificate('n', '" + ii + "');\" title=''> " + (ii + 1) + " </a></li>");
                            }
                        }
                    }
                    str.append(nextCertificate + nextclose);
                    if (noOfPages > 1 && next_certificatevalue != noOfPages) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchCertificate('n','" + (noOfPages - 1) + "');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
                    }
                    str.append("</ul></div></div></div>");
                    str.append("<div class='col-lg-12' id='printArea'>");
                    str.append("<div class='table-rep-plugin sort_table'>");
                    str.append("<div class='table-responsive mb-0' data-bs-pattern='priority-columns'>");
                    str.append("<table id='tech-companies-1' class='table table-striped'>");
                    str.append(thead);
                    str.append("<tbody id = 'sort_id'>");
                    CandidateInfo info;
                    String filep, url;
                    for (int i = 0; i < total; i++)
                    {
                        info = (CandidateInfo) candidate_list.get(i);
                        if (info != null) 
                        {
                            filep = ""; url = "";
                            if(info.getCertifilename() != null && !info.getCertifilename().equals(""))
                                filep = file_path+info.getCertifilename();
                            else if(info.getUrl() != null && !info.getUrl().equals(""))
                                url = info.getUrl();
                            str.append("<tr>");
                            str.append("<td>" + (info.getCoursename() != null ? info.getCoursename() : "") + "</td>");
                            str.append("<td>" + (info.getCoursetype() != null ? info.getCoursetype() : "") + "</td>");
                            str.append("<td>" + (info.getEduinstitute() != null ? info.getEduinstitute() : "") + "</td>");
                            str.append("<td>" + (info.getApprovedby() != null ? info.getApprovedby() : "") + "</td>");
                            str.append("<td>" + (info.getDateofissue() != null ? info.getDateofissue() : "") + "</td>");
                            str.append("<td>" + (info.getExpirydate() != null ? info.getExpirydate() : "") + "</td>");                            
                            str.append("<td class='action_column'>");                                                                  
                            if(!filep.equals("")) 
                            {
                                str.append("<a href=\"javascript:;\" class='mr_15' data-bs-toggle='modal' data-bs-target='#view_pdf' onclick=\"javascript: setIframe("+filep +");\"><img src='../assets/images/attachment.png'/></a>");
                            } else if(!url.equals("")) 
                            {
                                str.append("<a href="+url+" class='mr_15' target='_blank'><img src='../assets/images/attachment.png'/> </a>");
                            } else {
                                str.append("<a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a>");
                            }                                                                                        
                            if(info.getPassflag() ==2){
                            }else
                            {
                                if (editper.equals("Y") && info.getStatus() == 1) 
                                {
                                    str.append("<a href=\"javascript: modifytrainingcertdetailForm('"+ info.getTrainingandcertId()+"');\" class='edit_mode mr_15'><img src='../assets/images/pencil.png'/></a>");
                                }else {
                                    str.append("<a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a>");
                                }
                            }                                
                            str.append("<span class='switch_bth'>");
                                 str.append("<div class='form-check form-switch'>");
                                     str.append("<input class='form-check-input' type='checkbox' role='switch' ");
                                     if(info.getPassflag() ==2){
                                         str.append(" disabled ");
                                                }
                                     str.append(" id='flexSwitchCheckDefault_" + (i) + "'");
                                     if( info.getStatus() == 1){
                                         str.append(" checked ");
                                     }
                                     if(!editper.equals("Y")) {
                                         str.append(" disabled='true' ");
                                     } 
                                     str.append(" onclick=\"javascript: deletetrainingcertForm(' " + info.getTrainingandcertId() + " ', ' "+ info.getStatus() + " ');\"/>");
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
                    str.append("Page&nbsp;" + (next_certificatevalue) + " of " + (noOfPages) + ", &nbsp;");
                    str.append("(" + value + " - " + value1 + " record(s) of " + candidateCount + ")&nbsp;");
                    str.append("</div>");
                    str.append("<div class='wesl_No_pages'>");
                    str.append("<div class='loanreportTables_paginate paging_bootstrap_full_number'>");
                    str.append("<ul class='wesl_pagination'>");
                    if (noOfPages > 1 && next_certificatevalue != 1) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchCertificate('n', '0');\" title='First' style='height: 33px;'><i class='fa fa-angle-double-left'></i></a></li>");
                    }
                    str.append(prev + prevclose + "&nbsp;");
                    for (int ii = a1; ii <= a2; ii++) {
                        if (ii >= 0 && ii < noOfPages) {
                            if (ii == test) {
                                str.append("<li class='wesl_active'><a href='#'> " + (ii + 1) + " </a></li>");
                            } else {
                                str.append("<li><a href=\"javascript: searchCertificate('n', '" + ii + "');\" title=''> " + (ii + 1) + " </a></li>");
                            }
                        }
                    }
                    str.append(nextCertificate + nextclose);
                    if (noOfPages > 1 && next_certificatevalue != noOfPages) {
                        str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchCertificate('n','" + (noOfPages - 1) + "');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
                    }
                    str.append("</ul></div></div></div>");
                } else {
                    str.append("<div class='panel-body'>");
                    str.append("<div class='table-responsive'>");
                    str.append("<table class='table table-bordered table-hover' id='dataTables-example'>");
                    str.append("<tbody>");
                    str.append("<tr class='grid1'>");
                    str.append("<td>" + candidate.getMainPath("record_not_found") + "</td>");
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