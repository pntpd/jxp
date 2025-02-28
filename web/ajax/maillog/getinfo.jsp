<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.maillog.MaillogInfo" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="maillog" class="com.web.jxp.maillog.Maillog" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
try
{
    if(session.getAttribute("LOGININFO") != null)
    {
        String viewpath = maillog.getMainPath("view_maillog_file");
        int showsizelist = maillog.getCountList("show_size_list");
        
        StringBuilder sb = new StringBuilder();
        sb.append("<thead>");
        sb.append("<tr>");
        sb.append("<th><span><b>Name</b> </span></th>");
        sb.append("<th><span><b>To Address</b> </span></th>");
        sb.append("<th><span><b>CC</b> </span></th>");
        sb.append("<th><span><b>Subject</b> </span></th>");
        sb.append("<th><span><b>Date</b> </span></th>");
        sb.append("<th><span><b>View</b> </span></th>");
        sb.append("</tr>");
        sb.append("</thead>");
        String thead = sb.toString();
        sb.setLength(0);
        
        String search = request.getParameter("search") != null ? vobj.replacedesc(request.getParameter("search")) : "";
        String nextValue = request.getParameter("nextValue") != null && !request.getParameter("nextValue").equals("") ? vobj.replaceint(request.getParameter("nextValue")) : "0";            
        String dodirects = request.getParameter("doDirect") != null && !request.getParameter("doDirect").equals("") ? vobj.replaceint(request.getParameter("doDirect")) : "-1";
        String fromDate = request.getParameter("fromDate") != null ? request.getParameter("fromDate") : "";
        String toDate = request.getParameter("toDate") != null ? request.getParameter("toDate") : "";
        int n = Integer.parseInt(nextValue);
        int dodirect = Integer.parseInt(dodirects);
        if(request.getParameter("deleteVal") == null)
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
            int recordsperpage = maillog.getCount();
            ArrayList maillog_list = new ArrayList();
            int count = 0;
            if (next1.equals("p") || next1.equals("n")) 
            {
                maillog_list = maillog.getMaillogListByName(search, fromDate, toDate, n, recordsperpage);
                if(maillog_list.size() > 0)
                {
                    MaillogInfo cinfo = (MaillogInfo) maillog_list.get(maillog_list.size() - 1);
                    count = cinfo.getMaillogId();
                    maillog_list.remove(maillog_list.size() - 1);
                }
            }
            else
            {
                maillog_list = maillog.getMaillogListByName(search, fromDate, toDate, n, recordsperpage);
                if(maillog_list.size() > 0)
                {
                    MaillogInfo cinfo = (MaillogInfo) maillog_list.get(maillog_list.size() - 1);
                    count = cinfo.getMaillogId();
                    maillog_list.remove(maillog_list.size() - 1);
                }
            }
            session.setAttribute("MAILLOGCOUNT_LIST", count+"");
            session.setAttribute("MAILLOG_LIST", maillog_list);

            int pageSize = count / recordsperpage;
            if(count % recordsperpage > 0)
                pageSize = pageSize + 1;

            int maillogCount = count;
            String prev = "";
            String prevclose = "";
            String next = "";
            String nextclose = "";
            int last = 0;    
            int total = maillog_list.size();
            str.append("<input type='hidden' name='totalpage' value='"+ pageSize +"' />");
            str.append("<input type='hidden' name='nextDel' value='"+ total +"'/>");
            str.append("<input type='hidden' name='nextValue' value='"+ (next_value) +"'/>");
			if (n == 0 && maillogCount > recordsperpage)
            {
                next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                nextclose = ("</i></a></li>");
            }
            else if (n > 0 && maillogCount > ((n*recordsperpage) + recordsperpage))
            {
                next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                nextclose = ("</i></a></li>");
                prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                prevclose = ("</i></a></li>");
            }
            else if (n > 0 && maillogCount <= ((n*recordsperpage) + recordsperpage))
			{
                prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                prevclose = ("</i></a></li>");
                last = maillogCount;
            }
            else
            {
               recordsperpage = maillogCount;
            }
            int value =  n*recordsperpage + 1;
            int value1 =  last > 0 ? last : n*recordsperpage + recordsperpage;
            int test = n;
            int noOfPages = 1;
            if (recordsperpage > 0)
            {
                noOfPages = maillogCount / recordsperpage;
                if (maillogCount % recordsperpage > 0)
                    noOfPages += 1;
            }            
            if(total > 0)
            {
                str.append("<div class='wesl_pagination pagination-mg m_15'>");
                str.append("<div class='wesl_pg_rcds'>");
                str.append("Page&nbsp;"+(next_value)+" of "+( noOfPages )+", &nbsp;");
                str.append ("(" + value +" - " + value1 + " record(s) of "+maillogCount+")&nbsp;");
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
                
                MaillogInfo info;
                for (int i = 0; i < total; i++)
                {
                    info = (MaillogInfo) maillog_list.get(i);
                    if (info != null)
                    {
                        str.append("<tr class='hand_cursor' href='javascript: void(0);'>");
                        str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+ info.getMaillogId()+"');\">" + (info.getName() != null ? info.getName() : "") + "</td>");
                        str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+ info.getMaillogId()+"');\">" + (info.getEmail() != null ? info.getEmail() : "") + "</td>");
                        str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+ info.getMaillogId()+"');\">" + (info.getCc() != null ? info.getCc() : "") + "</td>");
                        str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+ info.getMaillogId()+"');\">" + (info.getSubject() != null ? info.getSubject() : "") + "</td>");
                        str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+ info.getMaillogId()+"');\">" + (info.getRegDate() != null ? info.getRegDate() : "") + "</td>");
                        str.append("<td class='text-align'>");
                        if(info.getFilename() != null && !info.getFilename().equals(""))
                            str.append("<a href='"+viewpath+info.getFilename()+"' class='view_mode float-end mr_15' target='_blank'><i class='ion ion-ios-search'></i></a>");
                        else 
                            str.append("&nbsp;");
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
                str.append("Page&nbsp;"+(next_value)+" of "+( noOfPages )+", &nbsp;");
                str.append ("(" + value +" - " + value1 + " record(s) of "+maillogCount+")&nbsp;");
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
                str.append("<div class='panel-body'>");
                str.append("<div class='table-responsive'>");
                str.append("<table class='table table-bordered table-hover' id='dataTables-example'>");
                str.append("<tbody>");
                str.append("<tr class='grid1'>");
                str.append("<td>"+maillog.getMainPath("record_not_found")+"</td>");
                str.append("</tr>");
                str.append("</tbody>");
                str.append("</table>");
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