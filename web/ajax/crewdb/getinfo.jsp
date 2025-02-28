<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.crewdb.CrewdbInfo" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="crewdb" class="com.web.jxp.crewdb.Crewdb" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
try
{
    if(session.getAttribute("LOGININFO") != null)
    {
        int showsizelist = crewdb.getCountList("show_size_list");
        
        StringBuilder sb = new StringBuilder();
        sb.append("<thead>");
        sb.append("<tr>");
        sb.append("<th width='11%'><span><b>Ref. No.</b> </span>");
        sb.append("<a href=\"javascript: sortForm('1', '2');\" id='img_1_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
        sb.append("<a href=\"javascript: sortForm('1', '1');\" id='img_1_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
        sb.append("</th>");
        sb.append("<th><span><b>Name</b> </span>");
        sb.append("<a href=\"javascript: sortForm('2', '2');\" id='img_2_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
        sb.append("<a href=\"javascript: sortForm('2', '1');\" id='img_2_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
        sb.append("</th>");
        sb.append("<th width='15%'><span><b>Position</b> </span>");
        sb.append("<a href=\"javascript: sortForm('3', '2');\" id='img_3_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
        sb.append("<a href=\"javascript: sortForm('3', '1');\" id='img_3_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
        sb.append("</th>");
        sb.append("<th width='15%'><span><b>Client</b> </span>");
        sb.append("<a href=\"javascript: sortForm('4', '2');\" id='img_4_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
        sb.append("<a href=\"javascript: sortForm('4', '1');\" id='img_4_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
        sb.append("</th>");
        sb.append("<th width='11%'><span><b>Location</b> </span>");
        sb.append("<a href=\"javascript: sortForm('5', '2');\" id='img_5_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
        sb.append("<a href=\"javascript: sortForm('5', '1');\" id='img_5_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
        sb.append("</th>");
        sb.append("<th width='12%'><span><b>Asset</b> </span>");
        sb.append("<a href=\"javascript: sortForm('6', '2');\" id='img_6_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
        sb.append("<a href=\"javascript: sortForm('6', '1');\" id='img_6_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
        sb.append("</th>");
        sb.append("<th width='8%' class='text-center'><span><b></b></span></th>");
        sb.append("<th width='8%' class='text-center'><span><b>Employment Status</b></span></th>");
        sb.append("</tr>");
        sb.append("</thead>");
        String thead = sb.toString();
        sb.setLength(0);
        
        String search = request.getParameter("search") != null ? vobj.replacedesc(request.getParameter("search")) : "";
        String statusIndexs = request.getParameter("statusIndex") != null && !request.getParameter("statusIndex").equals("") ? vobj.replaceint(request.getParameter("statusIndex")) : "0"; 
        String positionIndexs = request.getParameter("positionIndex") != null && !request.getParameter("positionIndex").equals("") ? vobj.replaceint(request.getParameter("positionIndex")) : "-1"; 
        String clientIdIndexs = request.getParameter("clientIdIndex") != null && !request.getParameter("clientIdIndex").equals("") ? vobj.replaceint(request.getParameter("clientIdIndex")) : "-1"; 
        String countryIdIndexs = request.getParameter("countryIdIndex") != null && !request.getParameter("countryIdIndex").equals("") ? vobj.replaceint(request.getParameter("countryIdIndex")) : "-1"; 
        String assetIdIndexs = request.getParameter("assetIdIndex") != null && !request.getParameter("assetIdIndex").equals("") ? vobj.replaceint(request.getParameter("assetIdIndex")) : "-1"; 
        
        String nextValue = request.getParameter("nextValue") != null && !request.getParameter("nextValue").equals("") ? vobj.replaceint(request.getParameter("nextValue")) : "0";            
        String dodirects = request.getParameter("doDirect") != null && !request.getParameter("doDirect").equals("") ? vobj.replaceint(request.getParameter("doDirect")) : "-1";
        int n = Integer.parseInt(nextValue);
        int dodirect = Integer.parseInt(dodirects);
        int statusIndex = Integer.parseInt(statusIndexs);
        int positionIndex = Integer.parseInt(positionIndexs);  
        int clientIdIndex = Integer.parseInt(clientIdIndexs);
        int countryIdIndex = Integer.parseInt(countryIdIndexs);
        int assetIdIndex = Integer.parseInt(assetIdIndexs); 
        if(request.getParameter("search") != null)
        {
            StringBuilder str = new StringBuilder();
            int m = n;
            int next_value = 0;
            String next1 = request.getParameter("next");
            if(next1 != null && next1.equals("n"))
            {
                if (dodirect >= 0)
                {
                    n = dodirect;
                    next_value = dodirect + 1;
                }
                else
                {
                    n = n;
                    next_value = m+1;
                }
            }
            else if(next1!= null && next1.equals("p"))
            {
                n = n-2;
                next_value = m - 1;
            }
            else if(next1 != null && next1.equals("s"))
            {
                n = 0;
                next_value = 1;
            }            
            session.setAttribute("NEXTVALUE", next_value + "");
            int recordsperpage = crewdb.getCount();
            ArrayList crewdb_list = new ArrayList();
            int count = 0;
            if (next1.equals("p") || next1.equals("n")) 
            {
                crewdb_list = crewdb.getCrewdbByName(search, statusIndex, positionIndex, clientIdIndex, countryIdIndex, assetIdIndex, n, recordsperpage);
                if(crewdb_list.size() > 0)
                {
                    CrewdbInfo cinfo = (CrewdbInfo) crewdb_list.get(crewdb_list.size() - 1);
                    count = cinfo.getCrewdbId();
                    crewdb_list.remove(crewdb_list.size() - 1);
                }
            }
            else
            {
                crewdb_list = crewdb.getCrewdbByName(search, statusIndex, positionIndex, clientIdIndex, countryIdIndex, assetIdIndex, n, recordsperpage); 
                if(crewdb_list.size() > 0)
                {
                    CrewdbInfo cinfo = (CrewdbInfo) crewdb_list.get(crewdb_list.size() - 1);
                    count = cinfo.getCrewdbId();
                    crewdb_list.remove(crewdb_list.size() - 1);
                }
            }
            session.setAttribute("COUNT_LIST", count+"");
            session.setAttribute("CREWDB_LIST", crewdb_list);

            int pageSize = count / recordsperpage;
            if(count % recordsperpage > 0)
                pageSize = pageSize + 1;

            int crewdbCount = count;
            String prev = "";
            String prevclose = "";
            String next = "";
            String nextclose = "";
            int last = 0;    
            int total = crewdb_list.size();
            str.append("<input type='hidden' name='totalpage' value='"+ pageSize +"' />");
            str.append("<input type='hidden' name='nextDel' value='"+ total +"'/>");
            str.append("<input type='hidden' name='nextValue' value='"+ (next_value) +"'/>");
			if (n == 0 && crewdbCount > recordsperpage)
            {
                next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                nextclose = ("</i></a></li>");
            }
            else if (n > 0 && crewdbCount > ((n*recordsperpage) + recordsperpage))
            {
                next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                nextclose = ("</i></a></li>");
                prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                prevclose = ("</i></a></li>");
            }
            else if (n > 0 && crewdbCount <= ((n*recordsperpage) + recordsperpage))
			{
                prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                prevclose = ("</i></a></li>");
                last = crewdbCount;
            }
            else
            {
               recordsperpage = crewdbCount;
            }
            int value =  n*recordsperpage + 1;
            int value1 =  last > 0 ? last : n*recordsperpage + recordsperpage;
            int test = n;
            int noOfPages = 1;
            if (recordsperpage > 0)
            {
                noOfPages = crewdbCount / recordsperpage;
                if (crewdbCount % recordsperpage > 0)
                    noOfPages += 1;
            }            
            if(total > 0)
            {
                str.append("<div class='wesl_pagination pagination-mg m_15'>");
                str.append("<div class='wesl_pg_rcds'>");
                str.append("Page&nbsp;"+(next_value)+" of "+( noOfPages )+", &nbsp;");
                str.append ("(" + value +" - " + value1 + " record(s) of "+crewdbCount+")&nbsp;");
                str.append("</div>");
                str.append("<div class='wesl_No_pages'>");
                str.append("<div class='loanreportTables_paginate paging_bootstrap_full_number'>");
                str.append("<ul class='wesl_pagination'>");
                if(noOfPages > 1 && next_value != 1)
                    str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFormAjax('n', '0');\" title='First' style='height: 33px;'><i class='fa fa-angle-double-left'></i></a></li>");
                str.append(prev+prevclose + "&nbsp;");
                int a1 = test - showsizelist;
                int a2 = test + showsizelist;
                for(int ii = a1; ii <= a2; ii++)
                {
                    if(ii >= 0 && ii < noOfPages)
                    {
                        if(ii == test)
                        {
                            str.append ("<li class='wesl_active'><a href='#'> "+(ii+1)+" </a></li>");
                        }
                        else
                        {
                            str.append ("<li><a href=\"javascript: searchFormAjax('n', '"+ii+"');\" title=''> "+ (ii+1)+" </a></li>");
                        }
                    }
                }
                str.append(next+nextclose);
                if (noOfPages > 1 && next_value != noOfPages)
                {
                     str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFormAjax('n','"+(noOfPages  - 1)+"');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
                }
                str.append("</ul></div></div></div>");
                str.append("<div class='col-lg-12' id='printArea'>");
                str.append("<div class='table-rep-plugin sort_table'>");
                str.append("<div class='table-responsive mb-0' data-bs-pattern='priority-columns'>");       
                str.append("<table id='tech-companies-1' class='table table-striped'>");
                str.append(thead);
                str.append("<tbody id = 'sort_id'>");
                
                int status;
                CrewdbInfo info;
                for (int i = 0; i < total; i++)
                {
                    info = (CrewdbInfo) crewdb_list.get(i);
                    if (info != null)
                    {
                        status = info.getStatus();
                            str.append("<tr class='hand_cursor' href='javascript: void(0);' \">");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCrewdbId() + "');\">" + (crewdb.changeNum(info.getCrewdbId(), 6)) + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCrewdbId() + "');\">" + (info.getName() != null ? info.getName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCrewdbId() + "');\">" + (info.getPosition() != null ? info.getPosition() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCrewdbId() + "');\">" + (info.getClientName() != null ? info.getClientName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCrewdbId() + "');\">" + (info.getCountryName() != null ? info.getCountryName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCrewdbId() + "');\">" + (info.getAssetName() != null ? info.getAssetName() : "") + "</td>");
                            str.append("<td class='action_column'>");
                            str.append("<a href='javascript:;' onclick=\"javascript: viewimg('" + (info.getCrewdbId()) + "');\" class='mr_15' data-bs-toggle='modal' data-bs-target='#view_resume_list'><img src='../assets/images/attachment.png'></a>");
                            if(status == 2)
                            str.append("<td class='text-center'><img class='cer_img ' src='../assets/images/active_status.png' /></td>");
                        else
                            str.append("<td  class='text-center'><img class='cer_img' src='../assets/images/inactive_status.png' /></td>");                        
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
                str.append("Page&nbsp;"+(next_value)+" of "+( noOfPages )+", &nbsp;");
                str.append ("(" + value +" - " + value1 + " record(s) of "+crewdbCount+")&nbsp;");
                str.append("</div>");
                str.append("<div class='wesl_No_pages'>");
                str.append("<div class='loanreportTables_paginate paging_bootstrap_full_number'>");
                str.append("<ul class='wesl_pagination'>");
                if(noOfPages > 1 && next_value != 1)
                    str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFormAjax('n', '0');\" title='First' style='height: 33px;'><i class='fa fa-angle-double-left'></i></a></li>");
                str.append(prev+prevclose + "&nbsp;");
                for(int ii = a1; ii <= a2; ii++)
                {
                    if(ii >= 0 && ii < noOfPages)
                    {
                        if(ii == test)
                        {
                            str.append ("<li class='wesl_active'><a href='#'> "+(ii+1)+" </a></li>");
                        }
                        else
                        {
                            str.append ("<li><a href=\"javascript: searchFormAjax('n', '"+ii+"');\" title=''> "+ (ii+1)+" </a></li>");
                        }
                    }
                }
                str.append(next+nextclose);
                if (noOfPages > 1 && next_value != noOfPages)
                {
                     str.append("<li class='wesl_pg_navi'><a href=\"javascript: searchFormAjax('n','"+(noOfPages  - 1)+"');\" title='Last' style='height: 33px;'><i class='fa fa-angle-double-right'></i></a></li>");
                }
                str.append("</ul></div></div></div>");
            }
            else
            {
                str.append("<div class='col-lg-12' id='printArea'>");
                str.append("<div class='table-rep-plugin sort_table'>");
                str.append("<div class='table-responsive mb-0' data-bs-pattern='priority-columns'>");       
                str.append("<table id='tech-companies-1' class='table table-striped'>");
                str.append("<tbody>");
                str.append("<tr>");
                str.append("<td><b>"+crewdb.getMainPath("record_not_found")+"</b></td>");
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
        else
        {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
    else
    {
        response.getWriter().write("Please check your login session....");
    }
}
catch(Exception e)
{    
    e.printStackTrace();
}
%>