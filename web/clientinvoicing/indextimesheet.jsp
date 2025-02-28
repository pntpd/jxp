<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.clientinvoicing.ClientinvoicingInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="clientinvoicing" class="com.web.jxp.clientinvoicing.Clientinvoicing" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 7, submtp = 77;
            String per = "N", addper = "N", editper = "N", deleteper = "N",approveper="N";
            if (session.getAttribute("LOGININFO") == null)
            {
    %>
    <jsp:forward page="/index1.jsp"/>
    <%
            }
            else
            {
                UserInfo uinfo = (UserInfo) session.getAttribute("LOGININFO");
                if(uinfo != null)
                {
                    per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                    addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
                    editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                    deleteper = uinfo.getDeleteper() != null ? uinfo.getDeleteper() : "N";
                    approveper = uinfo.getApproveper() != null ? uinfo.getApproveper() : "N";
                }
            }

            ClientinvoicingInfo cinfo = null;
            if(request.getAttribute("CLIENTTIMESHEET_DETAIL") != null)
            {
                cinfo = (ClientinvoicingInfo)request.getAttribute("CLIENTTIMESHEET_DETAIL");
            }

            ArrayList clientinvoicing_list = new ArrayList();
            int count = 0;
            int recordsperpage = clientinvoicing.getCount();
            if(session.getAttribute("COUNT_LISTTS") != null)
                count = Integer.parseInt((String) session.getAttribute("COUNT_LISTTS"));
            if(session.getAttribute("CLIENTTIMESHEET_LIST") != null)
                clientinvoicing_list = (ArrayList) session.getAttribute("CLIENTTIMESHEET_LIST");
            int pageSize = count / recordsperpage;
            if(count % recordsperpage > 0)
                pageSize = pageSize + 1;

            int total = clientinvoicing_list.size();
            int showsizelist = clientinvoicing.getCountList("show_size_list");
            int CurrPageNo = 1;

            String message = "", clsmessage = "deleted-msg";
            if (request.getAttribute("MESSAGE") != null)
            {
                message = (String)request.getAttribute("MESSAGE");
                request.removeAttribute("MESSAGE");
            }
            if(message.toLowerCase().contains("success"))
            {
                    message = "";
            }
            if(message != null && (message.toLowerCase()).indexOf("success") != -1)
                clsmessage = "updated-msg";
            
                String view_path = clientinvoicing.getMainPath("view_timesheet_file");

            String thankyou = "no";
            if(request.getAttribute("CANDSAVEMODEL") != null)
            {
                thankyou = (String)request.getAttribute("CANDSAVEMODEL");
                request.removeAttribute("CANDSAVEMODEL");
            }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= clientinvoicing.getMainPath("title") != null ? clientinvoicing.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <!-- Responsive Table css -->
        <link href="../assets/css/rwd-table.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/clientinvoicing.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/clientinvoicing/ClientinvoicingAction.do" onsubmit="return false;" enctype="multipart/form-data">
        <html:hidden property="doAdd"/>
        <html:hidden property="doModify"/>
        <html:hidden property="doView"/>    
        <html:hidden property="doGenerate"/>
        <html:hidden property="doPayment"/>
        <html:hidden property="doViewPayment"/>
        <html:hidden property="timesheetId"/>
        <html:hidden property="clientassetId"/>
        <html:hidden property="doSavePaySlip"/>
        <html:hidden property="type"/>
        <!-- Begin page -->
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow">
                                <a href="javascript: goback();"><img src="../assets/images/back-arrow.png"/></a>
                                <span>Invoicing</span>
                            </div>

                            <div class="float-end">
                                <div class="toggled-off usefool_tool">
                                    <div class="toggle-title">
                                        <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                        <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>

                                    </div>
                                    <!-- end toggle-title --->
                                    <div class="toggle-content">
                                        <h4>Useful Tools</h4>
                                        <ul>
                                            <li><a href="javascript:openinnewtab();"><img src="../assets/images/open-in-new-tab.png"/> Open in New Tab</a></li>
                                            <li><a href="javascript:printPage('printArea');"><img src="../assets/images/print-screen.png"/> Print Screen</a></li>
                                            <li><a href="javascript: exporttoexcel('2');"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12 metrix_top_right">
                            <div class="row d-flex align-items-center">
                                <div class="col-md-3 com_label_value">
                                    <div class="row mb_0">
                                        <div class="col-md-3"><label>Client</label></div>
                                        <div class="col-md-9"><span><%= cinfo != null && !cinfo.getClientName().equals("") ? cinfo.getClientName() : ""%></span></div>
                                    </div>
                                </div>
                                <div class="col-md-3 com_label_value">
                                    <div class="row mb_0">
                                        <div class="col-md-3 pd_0"><label>Asset</label></div>
                                        <div class="col-md-9 pd_0"><span><%= cinfo != null && !cinfo.getClientassetName().equals("") ? cinfo.getClientassetName() : ""%></span></div>
                                    </div>
                                </div>
                                <div class="col align-self-end text-right">
                                    <a href="javascript:;" class="go_btn" onclick="javascript: getPaySlip();">Upload Pay Slip</a>
                                </div> 
                            </div>
                        </div>
                    </div>

                    <div class="container-fluid pd_0">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 pd_0">
                                <div class="body-background">
                                    <div class="row">
                                        <div class="col-lg-12 pd_left_right_50">
                                            <div class="row client_head_search">
                                                <div class="col-lg-6">
                                                    <div class="row">
                                                        <div class="col-lg-6">                                                            
                                                            <div class="row">
                                                                <label for="example-text-input" class="col-sm-3 col-form-label">Search:</label>
                                                                <div class="col-sm-9 field_ic">
                                                                    <html:text property ="search" styleClass="form-control" styleId="example-text-input" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                                this.removeAttribute('readonly');
                                                                                this.blur();
                                                                                this.focus();
                                                                            }"/>
                                                                    <a href="javascript: searchFormAjaxTS('s','-1');" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                                </div>
                                                            </div>                                                                    
                                                        </div>
                                                    </div>
                                                </div>
                                                                    
                                                <div class="col-lg-6 col-md-6 col-sm-12 col-12 float-end">
                                                    <div class="row justify-content-end">
                                                        <div class="col-lg-4">
                                                            <div class="row">
                                                                <label for="example-text-input" class="col-sm-4 col-form-label ">Status:</label>
                                                                <div class="col-sm-8 field_ic">
                                                                    <html:select property="invoicestatusIndex" styleId="invoicestatusIndex" styleClass="form-select" onchange="javascript: searchFormAjaxTS('s', '-1');">
                                                                        <html:option value="-1">All</html:option>
                                                                        <html:option value="1">Pending</html:option>
                                                                        <html:option value="2">Generate</html:option>
                                                                        <html:option value="3">Sent</html:option>
                                                                        <html:option value="4">Payment received</html:option>
                                                                    </html:select>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-lg-4">
                                                            <div class="row">
                                                                <label for="example-text-input" class="col-sm-4 col-form-label text-right">Month:</label>
                                                                <div class="col-sm-8 field_ic">
                                                                    <html:select property="month" styleId="month" styleClass="form-select" onchange="javascript: searchFormAjaxTS('s', '-1');">
                                                                        <html:option value="-1">All</html:option>
                                                                        <html:option value="1">Jan</html:option>
                                                                        <html:option value="2">Feb</html:option>
                                                                        <html:option value="3">Mar</html:option>
                                                                        <html:option value="4">Apr</html:option>
                                                                        <html:option value="5">May</html:option>
                                                                        <html:option value="6">Jun</html:option>
                                                                        <html:option value="7">Jul</html:option>
                                                                        <html:option value="8">Aug</html:option>
                                                                        <html:option value="9">Sep</html:option>
                                                                        <html:option value="10">Oct</html:option>
                                                                        <html:option value="11">Nov</html:option>
                                                                        <html:option value="12">Dec</html:option>
                                                                    </html:select>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-lg-4">
                                                            <div class="row">
                                                                <label for="example-text-input" class="col-sm-4 col-form-label text-right">Year:</label>
                                                                <div class="col-sm-8 field_ic">
                                                                    <html:select property="yearId" styleId="yearId" styleClass="form-select" onchange="javascript: searchFormAjaxTS('s', '-1');">
                                                                        <html:optionsCollection filter="false" property="years" label="ddlLabel" value="ddlValue">
                                                                        </html:optionsCollection>
                                                                    </html:select>
                                                                </div>
                                                            </div>
                                                        </div>                                                        
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row" id='ajax_cat'>
                                                <input type="hidden" name="nextDel" value="<%= total %>" />
<%
                                        int clientinvoicingCount = count;
                                        int nextCount = 0;
                                        String prev = "";
                                        String prevclose = "";
                                        String next = "";
                                        String nextclose = "";
                                        int last = 0;
                                        if (session.getAttribute("NEXTTS") != null)
                                        {
                                            nextCount = Integer.parseInt((String)session.getAttribute("NEXTTS"));
                                            CurrPageNo = Integer.parseInt((String)session.getAttribute("NEXTVALUETS"));
%>
                                                <input type='hidden' name='totalpage' value='<%= pageSize %>'>
                                                <input type="hidden" name="nextValue" value="<%=CurrPageNo%>">
<%
                                            if (nextCount == 0 && clientinvoicingCount > recordsperpage)
                                            {
                                                next = ("<li class='next'><a href=\"javascript: searchFormAjaxTS('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                                                nextclose = ("</i></a></li>");
                                            }
                                            else if (nextCount > 0 && clientinvoicingCount >
                                                ((nextCount*recordsperpage) + recordsperpage))
                                            {
                                                next = ("<li class='next'><a href=\"javascript: searchFormAjaxTS('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                                                nextclose = ("</i></a></li>");
                                                prev = ("<li class='prev'><a href=\"javascript: searchFormAjaxTS('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                prevclose = ("</i></a></li>");
                                            }
                                            else if (nextCount > 0 && clientinvoicingCount <=
                                                ((nextCount*recordsperpage) + recordsperpage))
                                            {
                                                prev = ("<li class='prev'><a href=\"javascript: searchFormAjaxTS('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                prevclose = ("</i></a></li>");
                                                last = clientinvoicingCount;
                                            }
                                            else
                                            {
                                                recordsperpage = clientinvoicingCount;
                                            }
                                        }
                                        else
                                        {
                                            recordsperpage = clientinvoicingCount;
                                        }
                                        int test = nextCount;
                                        int noOfPages = 1;
                                        if (recordsperpage > 0)
                                        {
                                            noOfPages = clientinvoicingCount / recordsperpage;
                                            if (clientinvoicingCount % recordsperpage > 0)
                                                noOfPages += 1;
                                        }
                                        if(total > 0)
                                        {
%>
                                            <div class="wesl_pagination pagination-mg m_15">
                                                <div class="wesl_pg_rcds">
                                                    Page&nbsp;<%=CurrPageNo%> of <%= noOfPages %>, &nbsp;(<%= nextCount*recordsperpage + 1 %> - <%= last > 0?last:nextCount*recordsperpage + recordsperpage %> record(s) of <%=clientinvoicingCount%>)                                                
                                                </div>
                                                <div class="wesl_No_pages">
                                                    <div class="loanreportTables_paginate paging_bootstrap_full_number">
                                                        <ul class="wesl_pagination">
                                                            <%
                                                                                                                if(noOfPages > 1 && CurrPageNo != 1)
                                                                                                                {
                                                            %>
                                                            <li class="wesl_pg_navi"><a href="javascript: searchFormAjaxTS('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
                                                                    <%
                                                                                                                        }
                                                                    %>
                                                                    <%= prev %><%= prevclose %> &nbsp;
                                                            <%
                                                                                                                int a1 = test - showsizelist;
                                                                                                                int a2 = test + showsizelist;
                                                                                                                for(int ii = a1; ii <= a2; ii++)
                                                                                                                {
                                                                                                                    if(ii >= 0 && ii < noOfPages)
                                                                                                                    {
                                                                                                                        if(ii == test)
                                                                                                                        {
                                                            %>
                                                            <li class="wesl_active"><a href="javascript:;"><%=ii+1 %></a></li>
                                                                <%
                                                                                                                            }
                                                                                                                            else
                                                                                                                            {
                                                                %>
                                                            <li><a href="javascript: searchFormAjaxTS('n', '<%=ii%>');"><%= ii+1 %></a></li>
                                                                <%
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                %>
                                                            &nbsp;<%= next %><%= nextclose %>
                                                            <%
                                                                                                                if (noOfPages > 1 && CurrPageNo != noOfPages)
                                                                                                                {
                                                            %>
                                                            <li class='wesl_pg_navi'><a href="javascript: searchFormAjaxTS('n','<%=(noOfPages  - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
                                                                    <%
                                                                                                                        }
                                                                    %>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>                                        
                                                <div class="col-lg-12" id="printArea">
                                                    <div class="table-rep-plugin sort_table">
                                                        <div class="table-responsive mb-0" data-bs-pattern="priority-columns">        
                                                            <table id="tech-companies-1" class="table table-striped">
                                                                <thead>
                                                                    <tr>
                                                                        <th width="15%">
                                                                            <span><b>Invoice ID</b></span>
                                                                            <a href="javascript: sortFormTS('1', '2');" id="img_1_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                            <a href="javascript: sortFormTS('1', '1');" id="img_1_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                        </th>
                                                                        <th width="14%">
                                                                            <span><b>Status</b></span>
                                                                            <a href="javascript: sortFormTS('2', '2');" id="img_2_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                            <a href="javascript: sortFormTS('2', '1');" id="img_2_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                        </th>
                                                                        <th width="15%">
                                                                            <span><b>Generated on</b></span>
                                                                            <a href="javascript: sortFormTS('3', '2');" id="img_3_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                            <a href="javascript: sortFormTS('3', '1');" id="img_3_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                        </th>
                                                                        <th width="11%">
                                                                            <span><b>Sent on</b></span>
                                                                            <a href="javascript: sortFormTS('4', '2');" id="img_4_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                            <a href="javascript: sortFormTS('4', '1');" id="img_4_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                        </th>
                                                                        <th width="13%">
                                                                            <span><b>From Date</b></span>
                                                                            <a href="javascript: sortFormTS('5', '2');" id="img_5_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                            <a href="javascript: sortFormTS('5', '1');" id="img_5_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                        </th>
                                                                        <th width="10%">
                                                                            <span><b>To Date</b></span>
                                                                            <a href="javascript: sortFormTS('6', '2');" id="img_6_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                            <a href="javascript: sortFormTS('6', '1');" id="img_6_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                        </th>
                                                                        <th width="11%" class="text-right">
                                                                            <span><b>Actions</b></span>
                                                                        </th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody id="sort_id">
<%
                                                                int status;
                                                                ClientinvoicingInfo info;
                                                                for (int i = 0; i < total; i++)
                                                                {
                                                                    info = (ClientinvoicingInfo) clientinvoicing_list.get(i);
                                                                    if (info != null) 
                                                                    {
%>
                                                                    <tr>
                                                                        <td><%= clientinvoicing.changeNum( info.getInvoiceId(),6) %></td>
                                                                        <td><%= clientinvoicing.getInvoicestatusvalue(info.getInvoicestatus())%></td>
                                                                        <td><%= info.getGeneratedate() != null ? info.getGeneratedate() : ""  %></td>
                                                                        <td><%= info.getSentdate() != null ? info.getSentdate() : ""  %></td>
                                                                        <td><%= info.getFromdate() != null ? info.getFromdate() : ""  %></td>
                                                                        <td><%= info.getTodate() != null ? info.getTodate() : ""  %></td>
                                                                        <% if (editper.equals("Y") || addper.equals("Y")){ %> 
                                                                        <td class="action_column text-right">
                                                                            <%if(info.getInvoicestatus() <= 2){%>
                                                                            <a href="javascript:;" onclick=" javascript: doGenerateinvoice('<%= info.getTimesheetId()%>');" class="mr_15 generate_action">Generate </a>
                                                                            <%} else if(info.getInvoicestatus() == 3){%>
                                                                            <a href="javascript:;" onclick=" javascript: doPayment('<%= info.getTimesheetId()%>');" class="mr_15"><img src="../assets/images/view.png"> </a>
                                                                                <%} else if(info.getInvoicestatus() == 4){%>
                                                                            <a href="javascript:;" onclick=" javascript: doPaymentView('<%= info.getTimesheetId()%>');" class="mr_15"><img src="../assets/images/view.png"> </a>
                                                                                <%}%>
                                                                                <% if(!info.getSendappfile().equals("")) {%>                                                                                
                                                                            <a  href="javascript: seturl('<%=view_path+info.getSendappfile()%>');"><img src="../assets/images/timesheet.png"></a>
                                                                                <%}%>
                                                                                <%}%>
                                                                        </td>
                                                                    </tr>
<%
                                                                            }
                                                                        }
%>
                                                                </tbody>
                                                            </table>
                                                        </div>	
                                                    </div>
                                                </div>

                                                <div class="wesl_pagination pagination-mg mt_15">
                                                    <div class="wesl_pg_rcds">
                                                        Page&nbsp;<%=CurrPageNo%> of <%= noOfPages %>, &nbsp;(<%= nextCount*recordsperpage + 1 %> - <%= last > 0?last:nextCount*recordsperpage + recordsperpage %> record(s) of <%=clientinvoicingCount%>)                                                
                                                    </div>
                                                    <div class="wesl_No_pages">
                                                        <div class="loanreportTables_paginate paging_bootstrap_full_number">
                                                            <ul class="wesl_pagination">
                                                                <%
                                                                                                                    if(noOfPages > 1 && CurrPageNo != 1)
                                                                                                                    {
                                                                %>
                                                                <li class="wesl_pg_navi"><a href="javascript: searchFormAjaxTS('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
                                                                        <%
                                                                                                                            }
                                                                        %>
                                                                        <%= prev %><%= prevclose %> &nbsp;
                                                                <%
                                                                                                                    for(int ii = a1; ii <= a2; ii++)
                                                                                                                    {
                                                                                                                        if(ii >= 0 && ii < noOfPages)
                                                                                                                        {
                                                                                                                            if(ii == test)
                                                                                                                            {
                                                                %>
                                                                <li class="wesl_active"><a href="javascript:;"><%=ii+1 %></a></li>
                                                                    <%
                                                                                                                                }
                                                                                                                                else
                                                                                                                                {
                                                                    %>
                                                                <li><a href="javascript: searchFormAjaxTS('n', '<%=ii%>');"><%= ii+1 %></a></li>
                                                                    <%
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                    %>
                                                                &nbsp;<%= next %><%= nextclose %>
                                                                <%
                                                                                                                    if (noOfPages > 1 && CurrPageNo != noOfPages)
                                                                                                                    {
                                                                %>
                                                                <li class='wesl_pg_navi'><a href="javascript: searchFormAjaxTS('n','<%=(noOfPages  - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
<%
                                                                    }
%>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
<%
                                                }
%>                
                                            </div>	
                                        </div>
                                    </div>  
                                </div>    
                            </div>
                        </div>
                    </div> 
                </div>
                <!-- End Page-content -->
            </div>
            <!-- end main content-->
        </div>
        <!-- END layout-wrapper -->
        <div id="pay_slip_modal" class="modal fade parameter_modal pay_slip" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered ">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="dpayslip">
                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <%@include file ="../footer.jsp"%>
        <div id="view_pdf" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        <span class="resume_title"> Timesheet</span>
                        <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                        <a href="javascript:;" class="down_btn" id="diframe"><img src="../assets/images/download.png"/></a>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <iframe class="pdf_mode" src="" id="iframe"></iframe> </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="thank_you_modal" class="modal fade thank_you_modal blur_modal" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <h2>Thank You!</h2>
                                    <center><img src="../assets/images/thank-you.png"></center>
                                    <h3>Pay Slip uploaded successfully.</h3>
                                    <a href="javascript: ;" class="msg_button" data-bs-dismiss="modal" style="text-decoration: underline;">Okay</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        <!-- JAVASCRIPT -->
        <script src="../assets/libs/jquery/jquery.min.js"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/app.js"></script>
        <!-- Responsive Table js -->
        <script src="../assets/js/rwd-table.min.js"></script>
        <script src="../assets/js/table-responsive.init.js"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script>
        <% if(thankyou.equals("yes")){%>
            <script type="text/javascript">
                $(window).on('load', function () {
                $('#thank_you_modal').modal('show');
                });
            </script>
            <%}%>
        <script>
            // toggle class show hide text section
            $(document).on('click', '.toggle-title', function () {
                $(this).parent()
                        .toggleClass('toggled-on')
                        .toggleClass('toggled-off');
            });
        </script>
        <script>
            $(document).ready(function () {
                $('input').attr('autocomplete', 'off');
            });
        </script>
        <script>
            $(function () {
                $("#upload_link_2").on('click', function (e) {
                    e.preventDefault();
                    $("#upload2:hidden").trigger('click');
                });

            });
        </script>
    </html:form>
</body>
<%
}
catch(Exception e)
{
    e.printStackTrace();
}
%>
</html>