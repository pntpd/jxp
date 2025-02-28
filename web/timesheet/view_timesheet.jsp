<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.timesheet.TimesheetInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="timesheet" class="com.web.jxp.timesheet.Timesheet" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 9, submtp = 76;
            String per = "N", addper = "N", editper = "N", deleteper = "N", approveper = "N";
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
            int currencyId = 0;
            TimesheetInfo cinfo = null;
            if(session.getAttribute("BASICINFO") != null)
            {
                cinfo = (TimesheetInfo) session.getAttribute("BASICINFO");
                currencyId = cinfo.getCurrencyId();                
            }

            TimesheetInfo info = null;
            ArrayList t_list = new ArrayList();
            int count = 0;
            int recordsperpage = timesheet.getCount();
            if(session.getAttribute("COUNT_TSLIST") != null)
                count = Integer.parseInt((String) session.getAttribute("COUNT_TSLIST"));
            if(session.getAttribute("T_LIST") != null)
                t_list = (ArrayList) session.getAttribute("T_LIST");        
            int total = t_list.size();
            int pageSize = count / recordsperpage;
            if(count % recordsperpage > 0)
            pageSize = pageSize + 1;
            int CurrTsPageNo = 1;
            int showsizelist = timesheet.getCountList("show_size_list");
            
            int timesheet_list = 0;
            if(session.getAttribute("TIMESHEET") != null)
            {
                timesheet_list = Integer.parseInt((String) session.getAttribute("TIMESHEET"));
            }

            int tid = 0;
            if(request.getAttribute("SAVE_TID") != null)
            {
                tid = Integer.parseInt((String) request.getAttribute("SAVE_TID"));
                request.removeAttribute("SAVE_TID");
            }      

            String view_path = timesheet.getMainPath("view_timesheet_file");
            
            String message = "", clsmessage = "red_font";
            if (request.getAttribute("MESSAGE") != null)
            {
                message = (String)request.getAttribute("MESSAGE");
                request.removeAttribute("MESSAGE");
            }        
            if(message != null && (message.toLowerCase()).indexOf("success") != -1)
                clsmessage = "updated-msg";
                
             
    %>  
    <head>
        <meta charset="utf-8">
        <title><%= timesheet.getMainPath("title") != null ? timesheet.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="shortcut icon" href="../assets/images/favicon.png" />
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css" />
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css" />
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet" />
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css"/>
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/timesheet.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/timesheet/TimesheetAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
            <html:hidden property="doCancel"/>
            <html:hidden property="clientIdIndex"/>
            <html:hidden property="assetIdIndex"/>
            <html:hidden property="assetId" />
            <html:hidden property="typevalue" />
            <html:hidden property="doView" />
            <html:hidden property="doDelete" />
            <html:hidden property="doSearch" />
            <html:hidden property="doSave" />
            <html:hidden property="timesheetId" />
            <html:hidden property="doGetDetails" />
            <html:hidden property="doCreateSheet" />
            <html:hidden property="currentDate" />
            <html:hidden property="doApproveInvoice" />
            <html:hidden property="doSentApproval" />
            <html:hidden property="repeatId" />
            <input type="hidden" name="currencyId" value='<%=currencyId%>'/>
            <!-- Begin page -->
            <div id="layout-wrapper">
                <%@include file="../header.jsp" %>
                <%@include file="../sidemenu.jsp" %>
                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content tab_panel no_tab1">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12">
                                <div class="float-start"><span class="back_arrow"><a href="javascript: goback();"><img src="../assets/images/back-arrow.png"></a> Timesheet Management</span></div>
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
                                                <li><a href="javascript: exportDetails();"><img src="../assets/images/export-data.png"/> Export Data</a></li>
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
                                            <div class="col-md-9"><span><%= cinfo != null && cinfo.getClientName() != null ? cinfo.getClientName() : "" %></span></div>
                                        </div>
                                    </div>
                                    <div class="col-md-3 com_label_value">
                                        <div class="row mb_0">
                                            <div class="col-md-3 pd_0"><label>Asset</label></div>
                                            <div class="col-md-9 pd_0"><span><%= cinfo != null && cinfo.getClientAssetName() != null ? cinfo.getClientAssetName() : "" %></span></div>
                                        </div>
                                    </div>

                                    <div class="col-lg-6">
                                        <div class="row d-flex align-items-center">
                                            <div class="col-md-2 com_label_value">
                                                <div class="row mb_0">
                                                    <div class="col-md-9 pd_0"><label>Timesheets</label></div>
                                                    <div class="col-md-3 pd_0"><span><%=timesheet_list%></span></div>
                                                </div>
                                            </div>
                                            <div class="col-lg-8">
                                                <div class="row d-flex align-items-center">
                                                    <label for="example-text-input" class="col-sm-5 com_label_value col-form-label text-right pd_0"><span>Generate New Timesheet:</span></label>
                                                    <div class="col-sm-7">
                                                        <div class="row d-flex align-items-center">
                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                                <div class="input-daterange input-group input-addon">
                                                                    <input type="text" name="fromDate" value="" id="wesl_from_dt" class="form-control wesl_from_dt" placeholder="DD-MM-YYYY" maxlength="11"/>
                                                                    <div class="input-group-add">
                                                                        <span class="input-group-text">To</span>
                                                                    </div>
                                                                    <input type="text" name="toDate" value="" id="wesl_to_dt" class="form-control wesl_to_dt" autocomplete="off" placeholder="DD-MM-YYYY" maxlength="11"/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                                <div class="col-lg-2 col-md-2 col-sm-12 col-12" id="submitdiv"><a href="javascript: createSheet();" class="go_btn">Go</a></div>
                                        </div>
                                                 
                                    </div>
                                                
                                </div>
                            </div>
                        </div>
                                                
                        <div class="container-fluid pd_0">
                                                
                            <div class="row">
                                <div class="col-md-12 col-xl-12 pd_0">
                                    
                                    <div class="body-background">
                                        <div class="row d-none1">
                                            <div class="col-lg-12 pd_left_right_50">
                                                <% if(!message.equals("")) {%>
                                                <div class="sbold <%=clsmessage%>">
                                                    <%=message%>
                                                </div>
                                                <% } %>
                                                <div class="row client_head_search">
                                                    
                                                    <div class="col-lg-3">
                                                        <div class="row">
                                                            <div class="col-lg-12">
                                                                <div class="row">
                                                                    <label for="example-text-input" class="col-sm-3 col-form-label">Search:</label>
                                                                    <div class="col-sm-9 field_ic">
                                                                        <html:text property ="search" styleId="example-text-input" styleClass="form-control" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {this.removeAttribute('readonly'); this.blur(); this.focus();}"/>
                                                                        <a href="javascript: searchTimesheet('s', '-1');" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-3">
                                                        <div class="input-daterange input-group input-addon display_flex">
                                                            <html:text property="fromDateIndex" styleId="wesl_from_dt2" styleClass="form-control wesl_from_dt"/>
                                                            <script type="text/javascript">
                                                                document.getElementById("wesl_from_dt2").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                            </script>
                                                            <div class="input-group-add">
                                                                <span class="input-group-text">To</span>
                                                            </div>
                                                            <html:text property="toDateIndex" styleId="wesl_to_dt2" styleClass="form-control wesl_from_dt"/>
                                                            <script type="text/javascript">
                                                                document.getElementById("wesl_to_dt2").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                            </script>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-1">
                                                        <a href="javascript: searchTimesheet('s','-1');" class="go_btn">Go</a>
                                                    </div>
                                                                        
                                                    <div class="col-lg-5 col-md-5 col-sm-12 col-12 float-end">
                                                        <div class="row justify-content-end">
                                                            <div class="col-lg-4">
                                                                <div class="row">
                                                                    <label for="example-text-input" class="col-sm-4 col-form-label text-right">Status:</label>
                                                                    <div class="col-sm-8 field_ic">
                                                                        <html:select property="statusIndex" styleId="statusIndex" styleClass="form-select" onchange="javascript: searchTimesheet('s', '-1');">
                                                                            <html:option value="-1">All</html:option>
                                                                            <html:option value="1">Pending</html:option>
                                                                            <html:option value="2">Submitted</html:option>
                                                                            <html:option value="3">Sent for approval</html:option>
                                                                            <html:option value="4">Revision Required</html:option>
                                                                            <html:option value="5">Approved</html:option>
                                                                        </html:select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            
                                                            <div class="col-lg-4">
                                                                <div class="row">
                                                                    <label for="example-text-input" class="col-sm-4 col-form-label text-right">Month:</label>
                                                                    <div class="col-sm-8 field_ic">
                                                                        <html:select property="month" styleId="month" styleClass="form-select" onchange="javascript: searchTimesheet('s', '-1');">
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
                                                                        <html:select property="yearId" styleId="yearId" styleClass="form-select" onchange="javascript: searchTimesheet('s', '-1');">
                                                                            <html:optionsCollection filter="false" property="years" label="ddlLabel" value="ddlValue">
                                                                            </html:optionsCollection>
                                                                        </html:select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row" id='ajax_ts'>
<%
                                                    int tsCount = count;
                                                    int nextCount = 0;
                                                    String prev = "";
                                                    String prevclose = "";
                                                    String next = "";
                                                    String nextclose = "";
                                                    int last = 0;
                                                    if (session.getAttribute("NEXTTS") != null)
                                                    {
                                                        nextCount = Integer.parseInt((String)session.getAttribute("NEXTTS"));
                                                        CurrTsPageNo = Integer.parseInt((String)session.getAttribute("NEXTTSVALUE"));
%>
                                                <input type='hidden' name='totalpage' value='<%= pageSize %>'>
                                                <input type="hidden" name="nextTsValue" value="<%=CurrTsPageNo%>">
                                                <%
                                                            if (nextCount == 0 && tsCount > recordsperpage)
                                                            {
                                                                next = ("<li class='next'><a href=\"javascript: searchTimesheet('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                                                                nextclose = ("</i></a></li>");
                                                            }
                                                            else if (nextCount > 0 && tsCount >
                                                                ((nextCount*recordsperpage) + recordsperpage))
                                                            {
                                                                next = ("<li class='next'><a href=\"javascript: searchTimesheet('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                                                                nextclose = ("</i></a></li>");
                                                                prev = ("<li class='prev'><a href=\"javascript: searchTimesheet('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                                prevclose = ("</i></a></li>");
                                                            }
                                                            else if (nextCount > 0 && tsCount <=
                                                                ((nextCount*recordsperpage) + recordsperpage))
                                                            {
                                                                prev = ("<li class='prev'><a href=\"javascript: searchTimesheet('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                                prevclose = ("</i></a></li>");
                                                                last = tsCount;
                                                            }
                                                            else
                                                            {
                                                                recordsperpage = tsCount;
                                                            }
                                                        }
                                                        else
                                                        {
                                                            recordsperpage = tsCount;
                                                        }
                                                        int test = nextCount;
                                                        int noOfPages = 1;
                                                        if (recordsperpage > 0)
                                                        {
                                                            noOfPages = tsCount / recordsperpage;
                                                            if (tsCount % recordsperpage > 0)
                                                                noOfPages += 1;
                                                        }
                                                        if(total > 0)
                                                        {
                                                %>
                                                <div class="wesl_pagination pagination-mg m_15">
                                                    <div class="wesl_pg_rcds">
                                                        Page&nbsp;<%=CurrTsPageNo%> of <%= noOfPages %>, &nbsp;(<%= nextCount*recordsperpage + 1 %> - <%= last > 0?last:nextCount*recordsperpage + recordsperpage %> record(s) of <%=tsCount%>)                                                
                                                    </div>
                                                    <div class="wesl_No_pages">
                                                        <div class="loanreportTables_paginate paging_bootstrap_full_number">
                                                            <ul class="wesl_pagination">
                                                                <%
                                                                                                                    if(noOfPages > 1 && CurrTsPageNo != 1)
                                                                                                                    {
                                                                %>
                                                                <li class="wesl_pg_navi"><a href="javascript: searchTimesheet('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
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
                                                                <li><a href="javascript: searchTimesheet('n', '<%=ii%>');"><%= ii+1 %></a></li>
                                                                    <%
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                    %>
                                                                &nbsp;<%= next %><%= nextclose %>
                                                                <%
                                                                                                                    if (noOfPages > 1 && CurrTsPageNo != noOfPages)
                                                                                                                    {
                                                                %>
                                                                <li class='wesl_pg_navi'><a href="javascript: searchTimesheet('n','<%=(noOfPages  - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
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
                                                                            <span><b>Timesheet ID</b> </span>
                                                                        </th>
                                                                        <th width="14%">
                                                                            <span><b>Status</b></span>
                                                                        </th>
                                                                        <th width="11%">
                                                                            <span><b>Schedule</b></span>
                                                                        </th>
                                                                        <th width="11%">
                                                                            <span><b>Currency</b></span>
                                                                        </th>
                                                                        <th width="15%">
                                                                            <span><b>Generated on</b></span>
                                                                        </th>
                                                                        <th width="13%">
                                                                            <span><b>From Date</b></span>
                                                                        </th>
                                                                        <th width="12%">
                                                                            <span><b>To Date</b></span>
                                                                        </th>
                                                                        <th width="9%" class="text-right">
                                                                            <span><b>Action</b></span>
                                                                        </th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody id="detaildiv">
<%                                                                        
                                                                TimesheetInfo tinfo;
                                                                int status;
                                                                for (int i = 0; i < total; i++)
                                                                {
                                                                    tinfo = (TimesheetInfo) t_list.get(i);
                                                                    if(tinfo != null) 
                                                                    {
                                                                        status = tinfo.getStatus();                                                                        
%>                                                                  
                                                                    <tr class='hand_cursor'>
                                                                        <td><%= timesheet.changeNum(tinfo.getTimesheetId(),3)%></td>
                                                                        <td><%= tinfo.getStatusValue() != null ? tinfo.getStatusValue(): "" %></td>
                                                                        <td><%= tinfo.getTypevalue() != null ? tinfo.getTypevalue(): "" %></td>
                                                                        <td><%= tinfo.getCurrencyName() != null ? tinfo.getCurrencyName(): "" %></td>
                                                                        <td><%= tinfo.getGeneratedOn() != null ? tinfo.getGeneratedOn() : "" %></td>                                                                                                                                           
                                                                        <td><%= tinfo.getFromDate() != null ? tinfo.getFromDate() : "" %></td>                                                                                                                                           
                                                                        <td><%= tinfo.getToDate() != null ? tinfo.getToDate() : "" %></td>
                                                                        <td class="action_column text-right">
                                                                            <%if ((deleteper.equals("Y")) && (status == 1 || status == 2)){%>
                                                                                <a href="javascript: deleteForm('<%=tinfo.getTimesheetId()%>', '<%=tinfo.getRepeatId()%>');" class="mr_15"><img src="../assets/images/trash.png"> </a>
                                                                            <%}%>                                                                                
                                                                            <%if ((addper.equals("Y") || editper.equals("Y")) && (status == 1 || status == 2 || status == 4 )){%>
                                                                                <a class="" href="javascript: showTimesheetDetail('<%=tinfo.getTimesheetId()%>', '<%=tinfo.getRepeatId()%>');"><img src="../assets/images/pencil.png"></a>
                                                                            <%}%>
                                                                            <%if(status == 5){%>
                                                                                <% if(!tinfo.getFilename2().equals("")) {%>                                                                                
                                                                                 <a  href="javascript: seturl('<%=view_path+tinfo.getFilename2()%>');"><img src="../assets/images/attachment.png"></a>
                                                                                 <%}%>
                                                                            <%}%>
                                                                            <%if(status == 3){%>
                                                                                <a class="" href="javascript: showTimesheetDetail('<%=tinfo.getTimesheetId()%>', '<%=tinfo.getRepeatId()%>');"><img src="../assets/images/view.png"></a>                                                                                
                                                                            <%}%>
                                                                        </td>
                                                                        <td class="action_column text-right">                                                                                
                                                                                <div class="main-nav ass_list float-end">
                                                                                    <ul>
                                                                                        <li class="drop_down">
                                                                                            <%if(status == 3 || status == 2 || status == 4 ) {%>
                                                                                            <a href="javascript:;" class="toggle"><i class="fas fa-ellipsis-v"></i></a>
                                                                                            <%}%>
                                                                                            <div class="dropdown_menu">
                                                                                                <div class="dropdown-wrapper">
                                                                                                    <div class="category-menu">
                                                                                                        <%if(status == 2 && (addper.equals("Y") || editper.equals("Y"))){%>
                                                                                                            <a onclick="javascript: SentforApproval('<%= tinfo.getTimesheetId()%>','1', '<%=tinfo.getRepeatId()%>');" data-bs-toggle="modal" data-bs-target="#email_timesheet_modal">Send for Approval</a>
                                                                                                        <%}%>
                                                                                                        <%if(status == 3){%>
                                                                                                            <%if(addper.equals("Y") || editper.equals("Y")){%><a onclick="javascript: addReason('<%= tinfo.getTimesheetId()%>');" data-bs-toggle="modal" data-bs-target="#request_timesheet_revision_modal">Request Revision</a><% } %>
                                                                                                            <%if((approveper.equals("Y"))) {%><a onclick="javascript: appoveInvoice('<%= tinfo.getTimesheetId()%>');" data-bs-toggle="modal" data-bs-target="#approve_timesheet_invoicing_modal">Approve for Invoicing</a><% } %>
                                                                                                        <%}%>
                                                                                                        <%if(status == 4){%>                                                                                                        
                                                                                                            <a onclick="javascript: viewReason('<%=tinfo.getTimesheetId()%>');" data-bs-toggle="modal" data-bs-target="#view_reason_modal">View Reason</a>
                                                                                                        <%}%>    
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                        </li>
                                                                                    </ul>
                                                                                </div>
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
                                                            Page&nbsp;<%=CurrTsPageNo%> of <%= noOfPages %>, &nbsp;(<%= nextCount*recordsperpage + 1 %> - <%= last > 0?last:nextCount*recordsperpage + recordsperpage %> record(s) of <%=tsCount%>)                                                
                                                        </div>
                                                        <div class="wesl_No_pages">
                                                            <div class="loanreportTables_paginate paging_bootstrap_full_number">
                                                                <ul class="wesl_pagination">
                                                                    <%
                                                                                                            if(noOfPages > 1 && CurrTsPageNo != 1)
                                                                                                            {
                                                                    %>
                                                                    <li class="wesl_pg_navi"><a href="javascript: searchTimesheet('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
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
                                                                    <li><a href="javascript: searchTimesheet('n', '<%=ii%>');"><%= ii+1 %></a></li>
                                                                        <%
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                        %>
                                                                    &nbsp;<%= next %><%= nextclose %>
                                                                    <%
                                                                                                                        if (noOfPages > 1 && CurrTsPageNo != noOfPages)
                                                                                                                        {
                                                                    %>
                                                                    <li class='wesl_pg_navi'><a href="javascript: searchTimesheet('n','<%=(noOfPages  - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
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
                        <!-- End Page-content -->
                    </div>
                    <!-- end main content-->
                </div>
                <!-- END layout-wrapper -->
                <%@include file="../footer.jsp" %>
                <div id="request_timesheet_revision_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <h2>REQUEST TIMESHEET REVISION</h2>
                                        <div class="row client_position_table" id="addReasonDiv">                                           
                                        </div>                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="approve_timesheet_invoicing_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <h2>APPROVE TIMESHEET FOR INVOICING</h2>
                                        <div class="row client_position_table" id="invoiceDiv">
                                            
                                        </div>
                                        <div class="row">
                                            
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="view_reason_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <h2>VIEW REASON</h2>
                                        <div class="row client_position_table" id="viewReasonDiv">                                            
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="view_pdf" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                    <div class="modal-dialog modal-dialog-scrollable">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                                <span class="resume_title">Approved Timesheet</span>
                                <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                                <a href="" class="down_btn" id="diframe"><img src="../assets/images/download.png"/></a>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <iframe class="pdf_mode" src="" id="iframe"></iframe>                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="email_timesheet_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="row client_position_table" id="approveDiv">                                            
                                        </div>                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- JAVASCRIPT -->
                <script src="../assets/libs/jquery/jquery.min.js"></script>		
                <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
                <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
                <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
                <script src="../assets/js/bootstrap-datepicker.min.js"></script>
                <script src="../assets/js/app.js"></script>	
                <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>   
                <script src="/jxp/assets/js/sweetalert2.min.js"></script>
                <%--<% if(tid > 0){%>
                    <script type="text/javascript">
                        $(window).on('load', function () {
                            SentforApproval('<%=tid%>');
                        });
                    </script>
                    <%}%> 
                --%>
                <script>
                        $(window).on('scroll', function () {
                            if ($(this).scrollTop() > 150) {
                                $('.head_fixed').addClass("is-sticky");
                            } else {
                                $('.head_fixed').removeClass("is-sticky");
                            }
                        });
                </script>
                <script>
                    // toggle class show hide text section
                    $(document).on('click', '.toggle-title', function () {
                        $(this).parent()
                                .toggleClass('toggled-on')
                                .toggleClass('toggled-off');
                    });
                </script>                
                <script type="text/javascript">
                        jQuery(document).ready(function () {
                                var date_pick = ".wesl_from_dt, .wesl_to_dt";
                                $(date_pick).datepicker({
                                        todayHighlight: !0,
                                        format: "dd-M-yyyy",
                                        autoclose: "true",
                                });
                        });
                </script>
                <script type="text/javascript">
                        jQuery(document).ready(function () {
                                var date_pick = ".wesl_from_dt2, .wesl_to_dt2";
                                $(date_pick).datepicker({
                                        todayHighlight: !0,
                                        format: "dd-M-yyyy",
                                        autoclose: "true",
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
