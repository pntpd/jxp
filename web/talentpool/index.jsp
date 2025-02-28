<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 2, submtp = 4;
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
                ArrayList candidate_list = new ArrayList();
                int count = 0;
                int recordsperpage = talentpool.getCount();
                if(session.getAttribute("COUNT_LIST") != null)
                    count = Integer.parseInt((String) session.getAttribute("COUNT_LIST"));
                if(session.getAttribute("CANDIDATE_LIST") != null)
                    candidate_list = (ArrayList) session.getAttribute("CANDIDATE_LIST");
                String file_path = talentpool.getMainPath("view_candidate_file");
                int pageSize = count / recordsperpage;
                if(count % recordsperpage > 0)
                    pageSize = pageSize + 1;
                int total = candidate_list.size();
                int showsizelist = talentpool.getCountList("show_size_list");
                int CurrPageNo = 1;
                String message = "", clsmessage = "deleted-msg";
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
        <title><%= talentpool.getMainPath("title") != null ? talentpool.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <link href="../assets/css/rwd-table.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/talentpool.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/talentpool/TalentpoolAction.do" onsubmit="return false;">
        <html:hidden property="doAdd"/>
        <html:hidden property="doModify"/>
        <html:hidden property="doView"/>
        <html:hidden property="doCancel"/>
        <html:hidden property="candidateId"/>

        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <div class="main-content">
                <div class="page-content">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow">
                                <a href="javascript: backtoold();"><img src="../assets/images/back-arrow.png"/></a>
                                <span>Talent Pool</span>
                            </div>
                            <div class="float-end">
                                <div class="toggled-off usefool_tool">
                                    <div class="toggle-title">
                                        <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                        <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>
                                    </div>
                                    <div class="toggle-content">
                                        <h4>Useful Tools</h4>
                                        <%--<%@include file ="../shortcutmenu.jsp"%>--%>
                                        <ul>
                                        <li><a href="javascript:openinnewtab();"><img src="../assets/images/open-in-new-tab.png"/> Open in New Tab</a></li>
                                        <li><a href="javascript:printPage('printArea');"><img src="../assets/images/print-screen.png"/> Print Screen</a></li>
                                        <li><a href="javascript: exporttoexcel();"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                        <li><a href="javascript: exporttoexcelfordatareport();"><img src="../assets/images/export-data.png"/> Data Report</a></li>
                                        <li><a href="javascript: exporttoexcelforanaliticsreport();"><img src="../assets/images/export-data.png"/> Analytics Report</a></li>
                                       </ul>
                                    </div>
                                </div>
                            </div>
                        </div>	
                    </div>
                    <div class="row">
                        <div class="col-md-12 col-xl-12">
                            <div class="search_export_area">
                                <div class="row">
                                    <div class="col-lg-10">
                                        <div class="row mb-3">
                                            <div class="col-lg-3 pd_0">
                                                <div class="row">
                                                    <label for="example-text-input" class="col-sm-3 col-form-label">Search:</label>
                                                    <div class="col-sm-9 field_ic">
                                                        <html:text property ="search" styleId="example-text-input" styleClass="form-control" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                    this.removeAttribute('readonly');
                                                                    this.blur();
                                                                    this.focus();
                                                                }"/>
                                                        <a href="javascript: searchFormAjax('s','-1');" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-2">
                                                <div class="row">
                                                    <div class="col-sm-12 field_ic">
                                                        <html:select property="clientIndex" styleId="clientIndex" styleClass="form-select" onchange="javascript: setAssetDDL();" >
                                                            <html:optionsCollection filter="false" property="clients" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-2">
                                                <div class="row">
                                                    <div class="col-sm-12 field_ic">
                                                        <html:select property="assetIndex" styleId="assetIndex" styleClass="form-select" onchange="javascript: setPositionDDL();" >
                                                            <html:optionsCollection filter="false" property="clientassets" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-3">
                                                <div class="row">
                                                    <div class="col-sm-12 field_ic">
                                                        <html:select property="positionIndexId" styleId="positionIndexId" styleClass="form-select" onchange="javascript: searchFormAjax('s', '-1');" >
                                                            <html:optionsCollection filter="false" property="ipositions" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                </div>                                                
                                            </div>
                                            <div class="col-lg-2">
                                                <a href="javascript:;" class="add_user" data-bs-toggle="modal" data-bs-target="#transfer_modal">Advance&nbsp;Filter</a>
                                            </div>                                                                                           
                                        </div> 
                                        </div>
                                            <div class="col-lg-2 text-right float-end">
                                                <a href="javascript: resetFilter();" class="reset_export"><img src="../assets/images/refresh.png"/>Reset Filters</a>                                                 
                                        </div>
                                    </div>                                    
                                </div>
                            </div>
                        </div>
                   
                    <div class="container-fluid">
                        <div class="row">
                            <% if(!message.equals("")) {%><div class="alert <%=clsmessage%> alert-dismissible fade show">
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button><%=message%>
                            </div><% } %>
                        </div>
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background mt_0">
                                    <div class="row" id='ajax_cat'>
                                        <input type="hidden" name="nextDel" value="<%= total %>" />
                                        <%
                                                                                int candidateCount = count;
                                                                                int nextCount = 0;
                                                                                String prev = "";
                                                                                String prevclose = "";
                                                                                String next = "";
                                                                                String nextclose = "";
                                                                                int last = 0;
                                                                                if (session.getAttribute("NEXT") != null)
                                                                                {
                                                                                    nextCount = Integer.parseInt((String)session.getAttribute("NEXT"));
                                                                                    CurrPageNo = Integer.parseInt((String)session.getAttribute("NEXTVALUE"));
                                        %>
                                        <input type='hidden' name='totalpage' value='<%= pageSize %>'>
                                        <input type="hidden" name="nextValue" value="<%=CurrPageNo%>">
                                        <%
                                                                                    if (nextCount == 0 && candidateCount > recordsperpage)
                                                                                    {
                                                                                        next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                                                                                        nextclose = ("</i></a></li>");
                                                                                    }
                                                                                    else if (nextCount > 0 && candidateCount >
                                                                                        ((nextCount*recordsperpage) + recordsperpage))
                                                                                    {
                                                                                        next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                                                                                        nextclose = ("</i></a></li>");
                                                                                        prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                                                        prevclose = ("</i></a></li>");
                                                                                    }
                                                                                    else if (nextCount > 0 && candidateCount <=
                                                                                        ((nextCount*recordsperpage) + recordsperpage))
                                                                                    {
                                                                                        prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                                                        prevclose = ("</i></a></li>");
                                                                                        last = candidateCount;
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        recordsperpage = candidateCount;
                                                                                    }
                                                                                }
                                                                                else
                                                                                {
                                                                                    recordsperpage = candidateCount;
                                                                                }
                                                                                int test = nextCount;
                                                                                int noOfPages = 1;
                                                                                if (recordsperpage > 0)
                                                                                {
                                                                                    noOfPages = candidateCount / recordsperpage;
                                                                                    if (candidateCount % recordsperpage > 0)
                                                                                        noOfPages += 1;
                                                                                }
                                                                                if(total > 0)
                                                                                {
                                        %>
                                        <div class="wesl_pagination pagination-mg m_15">
                                            <div class="wesl_pg_rcds">
                                                Page&nbsp;<%=CurrPageNo%> of <%= noOfPages %>, &nbsp;(<%= nextCount*recordsperpage + 1 %> - <%= last > 0?last:nextCount*recordsperpage + recordsperpage %> record(s) of <%=candidateCount%>)                                                
                                            </div>
                                            <div class="wesl_No_pages">
                                                <div class="loanreportTables_paginate paging_bootstrap_full_number">
                                                    <ul class="wesl_pagination">
                                                        <%
                                                                                                            if(noOfPages > 1 && CurrPageNo != 1)
                                                                                                            {
                                                        %>
                                                        <li class="wesl_pg_navi"><a href="javascript: searchFormAjax('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
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
                                                        <li><a href="javascript: searchFormAjax('n', '<%=ii%>');"><%= ii+1 %></a></li>
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
                                                        <li class='wesl_pg_navi'><a href="javascript: searchFormAjax('n','<%=(noOfPages  - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
                                                                <%
                                                                                                                    }
                                                                %>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>                                        
                                        <div class="col-lg-12"  id="printArea">
                                            <div class="table-rep-plugin sort_table">
                                                <div class="table-responsive mb-0" data-bs-pattern="priority-columns">        
                                                    <table id="tech-companies-1" class="table table-striped">
                                                        <thead>
                                                            <tr>
                                                                <th width="7%" class="text-center select_option">
                                                                    <span><b>Certified</b> </span>
                                                                    <div class="form-check permission-check">
                                                                        <html:checkbox property="verified" value = "1" styleClass="form-check-input"  styleId="switch1" onclick="javascript: searchFormAjax('s', '-1');"/>
                                                                    </div>
                                                                </th>
                                                                <th width="13%">
                                                                    <span><b>Employee Id</b></span>
                                                                    <a href="javascript: sortForm('6', '2');" id="img_6_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortForm('6', '1');" id="img_6_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th width="17%">
                                                                    <span><b>Name</b></span>
                                                                    <a href="javascript: sortForm('1', '2');" id="img_1_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortForm('1', '1');" id="img_1_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th width="16%">
                                                                    <span><b>Position - Rank</b></span>
                                                                    <a href="javascript: sortForm('2', '2');" id="img_2_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortForm('2', '1');" id="img_2_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th width="17%">
                                                                    <span><b>Client</b></span>
                                                                    <a href="javascript: sortForm('3', '2');" id="img_3_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortForm('3', '1');" id="img_3_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th width="12%">
                                                                    <span><b>Location</b></span>
                                                                    <a href="javascript: sortForm('4', '2');" id="img_4_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortForm('4', '1');" id="img_4_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th width="12%">
                                                                    <span><b>Asset</b></span>
                                                                    <a href="javascript: sortForm('5', '2');" id="img_5_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                    <a href="javascript: sortForm('5', '1');" id="img_5_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                </th>
                                                                <th width="11%">
                                                                    <span><b>Alerts</b></span>
                                                                </th>
                                                                <th width="5%" class="text-center">
                                                                    <span><b>Employment <br/>Status</b></span>
                                                                </th>
                                                            </tr>
                                                        </thead>
                                                        <tbody id="sort_id">
<%
                                                        int status;
                                                        TalentpoolInfo info;
                                                        for (int i = 0; i < total; i++)
                                                        {
                                                            info = (TalentpoolInfo) candidate_list.get(i);
                                                            int vflag = 0, progress = 0, clientId = 0;
                                                            if (info != null) 
                                                            {
                                                                status = info.getStatus();
                                                                vflag = info.getVflag();
                                                                progress = info.getProgressId();
                                                                clientId = info.getClientId();
%>
                                                            <tr class='hand_cursor'>
                                                                <td class="ocs_cer_index text-center" data-org-colspan="1" data-columns="tech-companies-1-col-0"><% if(vflag == 4) {%><img src="../assets/images/ocs_certified_index.png"><%} else {%><a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a><%}%></td>
                                                                <td class="hand_cursor" onclick="javascript: view('<%= info.getCandidateId()%>');"><%= info.getEmployeeId() != null ? info.getEmployeeId() : "" %></td>
                                                                <td class="hand_cursor" onclick="javascript: view('<%= info.getCandidateId()%>');"><%= info.getName() != null ? info.getName() : "" %></td>                                                                
                                                                <td class="hand_cursor" onclick="javascript: view('<%= info.getCandidateId()%>');"><%= info.getPosition() != null ? info.getPosition() : ""%></td>
                                                                <td class="hand_cursor" onclick="javascript: view('<%= info.getCandidateId()%>');"><%= info.getClientName() != null ? info.getClientName() : ""%></td>
                                                                <td class="hand_cursor" onclick="javascript: view('<%= info.getCandidateId()%>');"><%= info.getCountryName() != null ? info.getCountryName() : ""%></td>
                                                                <td class="hand_cursor" onclick="javascript: view('<%= info.getCandidateId()%>');"><%= info.getClientAsset() != null ? info.getClientAsset() : ""%></td>
                                                                <td><a href="javascript:;" data-bs-toggle="modal" data-bs-target="#client_position" onclick="javascript: viewAlertmodal('<%= info.getCandidateId()%>', '<%= info.getName()%>', '<%= info.getPositionname()%>', '<%= info.getGradename()%>');"><span class="alert_icon"><i class="ion ion-md-information-circle-outline"></i></span></a> <%= talentpool.changeNum( info.getAlertCount(),2) %></td>
                                                                <td class="hand_cursor text-center" data-org-colspan="1" data-columns="tech-companies-1-col-7">
                                                                    <% if(clientId > 0) {%>
                                                                        <img class="cer_img" src="../assets/images/active_status.png" />
                                                                    <%} else if(clientId <= 0 && status == 4) {%>
                                                                        <img class="cer_img" src="../assets/images/deceased_status.png" />                                                                    
                                                                    <%} else {%>
                                                                        <img class="cer_img" src="../assets/images/inactive_status.png" />
                                                                    <%}%>
                                                                    &nbsp;
                                                                    <% if(progress == 1) {%><img class="cer_img lock_icon" src="../assets/images/lock.png"><%}%>
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
                                                Page&nbsp;<%=CurrPageNo%> of <%= noOfPages %>, &nbsp;(<%= nextCount*recordsperpage + 1 %> - <%= last > 0?last:nextCount*recordsperpage + recordsperpage %> record(s) of <%=candidateCount%>)                                                
                                            </div>
                                            <div class="wesl_No_pages">
                                                <div class="loanreportTables_paginate paging_bootstrap_full_number">
                                                    <ul class="wesl_pagination">
                                                        <%
                                                                                                            if(noOfPages > 1 && CurrPageNo != 1)
                                                                                                            {
                                                        %>
                                                        <li class="wesl_pg_navi"><a href="javascript: searchFormAjax('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
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
                                                        <li><a href="javascript: searchFormAjax('n', '<%=ii%>');"><%= ii+1 %></a></li>
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
                                                        <li class='wesl_pg_navi'><a href="javascript: searchFormAjax('n','<%=(noOfPages  - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
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
        <%@include file ="../footer.jsp"%>

        <div id="client_position" class="modal fade client_position" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" onclick="javascript: searchFormAjax('s', '-1');" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="alertmodal">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="view_resume_list" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div><a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen r_f_screen">Full Screen</a></div>
                        <div class="row" id="viewfilesdiv">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div id="transfer_modal" class="modal fade parameter_modal right_side_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <h2>ADVANCE FILTER</h2>
                                <div class="row client_position_table">
                                    <div class="col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                        <label class="form_label">Location</label>
                                        <html:select property="locationIndex" styleId="locationIndex" styleClass="form-select" onchange="javascript: setPositionFilter();" >
                                            <html:optionsCollection filter="false" property="locations" label="ddlLabel" value="ddlValue">
                                            </html:optionsCollection>
                                        </html:select>
                                    </div>
                                    <div class="col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                        <label class="form_label">Status</label>
                                        <html:select property="employementstatus" styleId="employementstatus" styleClass="form-select" onchange="javascript: setPositionFilter();">
                                            <html:option value="-1">Select Status</html:option>
                                            <html:option value="1">Available</html:option>
                                            <html:option value="2">Employed</html:option>
                                            <html:option value="3">Deceased</html:option>
                                        </html:select>
                                    </div>
                                    <div class="col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                        <label class="form_label">Asset Type</label>
                                        <html:select property="assettypeIdIndex" styleId="assettypeIdIndex" styleClass="form-select" onchange="javascript: setPositionFilter();">
                                                <html:optionsCollection filter="false" property="assettypes" label="ddlLabel" value="ddlValue">
                                                </html:optionsCollection>
                                            </html:select>
                                    </div>
                                    <div class="col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                        <label class="form_label">Position|Rank</label>
                                        <html:select property="positionFilterId" styleId="positionFilterId" styleClass="form-select"  onchange="javascript: searchFormAjax('s', '-1');">
                                            <html:optionsCollection filter="false" property="positions" label="ddlLabel" value="ddlValue">
                                            </html:optionsCollection>
                                        </html:select>
                                    </div>                       
                                </div>	
                                <div class="row">	
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 text-center"><a href="javascript: ;" class="go_btn" data-bs-dismiss="modal" >Close</a></div>
                                 </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="../assets/libs/jquery/jquery.min.js"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/app.js"></script>
        <!-- Responsive Table js -->
        <script src="../assets/js/rwd-table.min.js"></script>
        <script src="../assets/js/table-responsive.init.js"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script> 
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
    <script type="text/javascript">
    function addLoadEvent(func) {
          var oldonload = window.onload;
          if (typeof window.onload != 'function') {
            window.onload = func;
          } else {
            window.onload = function() {
              if (oldonload) {
                oldonload();
              }
            }
          }
        }
        addLoadEvent(function() {
        })
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