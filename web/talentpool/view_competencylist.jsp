<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 2, submtp = 4, ctp = 22;
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
            ArrayList competency_list = new ArrayList();
            int count = 0;
            int recordsperpage = talentpool.getCount();
            if(session.getAttribute("COUNT_COMPETENCYLIST") != null)
                count = Integer.parseInt((String) session.getAttribute("COUNT_COMPETENCYLIST"));
            if(session.getAttribute("COMPETENCYLISTHISTLIST") != null)
                competency_list = (ArrayList) session.getAttribute("COMPETENCYLISTHISTLIST");
            int pageSize = count / recordsperpage;
            if(count % recordsperpage > 0)
                pageSize = pageSize + 1;

            int total = competency_list.size();
            int showsizelist = talentpool.getCountList("show_size_list");
            int CurrPageNo = 1;

    %>
    <head>
        <meta charset="utf-8">
        <title><%= talentpool.getMainPath("title") != null ? talentpool.getMainPath("title") : "" %></title>
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
        <script type="text/javascript" src="../jsnew/talentpool.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/talentpool/TalentpoolAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
            <!-- Begin page -->
            <div id="layout-wrapper">
                <%@include file="../header.jsp" %>
                <%@include file="../sidemenu.jsp" %>
                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content tab_panel">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12">
                                <div class="float-start">
                                    <a href="javascript:goback();" class="back_arrow">
                                        <img  src="../assets/images/back-arrow.png"/> 
                                        <%@include file ="../talentpool_title.jsp"%>
                                    </a>
                                </div>
                                <div class="float-end">
                                    <div class="toggled-off usefool_tool">
                                        <div class="toggle-title">
                                            <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                            <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>
                                        </div>
                                        <div class="toggle-content">
                                            <h4>Useful Tools</h4>
                                            <ul>
                                                <li><a href="javascript: openinnewtab();"><img src="../assets/images/open-in-new-tab.png"/> Open in New Tab</a></li>
                                                <li><a href="javascript: printPage('printArea');;"><img src="../assets/images/print-screen.png"/> Print Screen</a></li>
                                                <li><a href="javascript: exporttoexcelnew('22');"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>	
                            <div class="col-md-12 col-xl-12 tab_head_area">
                                <%@include file ="../talentpooltab.jsp"%>
                            </div>
                        </div>

                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-md-12 col-xl-12">
                                    <div class="body-background tab2_body">
                                        <div class="row">  
                                            <div class="container-fluid">
                                                <div class="row">
                                                    <div class="col-md-12 col-xl-12">
                                                        <div class="">
                                                            <div class="row d-none1">
                                                                <div class="col-lg-12">
                                                                    <div class="tab-content">
                                                                        <div class="tab-pane active">
                                                                            <div class="m_30">
                                                                                <div class="row">
                                                                                    <div class="row main-heading just_cont_inherit list_heading">
                                                                                        <div class="col-lg-4 col-md-4 col-sm-3 col-12 float-start">
                                                                                                <div class="add-btn float-start mt_5">
                                                                                                <h4>COMPETENCY ASSESSMENT HISTORY</h4>
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="col-lg-2 col-md-2 col-sm-3 col-12 float-start">
                                                                                            <html:select property="clientId" styleId="clientId" styleClass="form-select" onchange="javascript: setCompasset();">
                                                                                                <html:optionsCollection filter="false" property="clients" label="ddlLabel" value="ddlValue">
                                                                                                </html:optionsCollection>
                                                                                            </html:select>
                                                                                        </div>
                                                                                        <div class="col-lg-2 col-md-2 col-sm-3 col-12 float-start">
                                                                                            <html:select property="assetIdcom" styleId="assetIdcom" styleClass="form-select" onchange="javascript: setComDept();">
                                                                                                <html:optionsCollection filter="false" property="clientassets" label="ddlLabel" value="ddlValue">
                                                                                                </html:optionsCollection>
                                                                                            </html:select>
                                                                                        </div>
                                                                                        <div class="col-lg-2 col-md-2 col-sm-3 col-12 float-start">
                                                                                            <html:select property="pdeptId" styleId="pdeptId" styleClass="form-select" onchange="javascript: setComPosition(); ">
                                                                                                <html:optionsCollection filter="false" property="depts" label="ddlLabel" value="ddlValue">
                                                                                                </html:optionsCollection>
                                                                                            </html:select>
                                                                                        </div>
                                                                                        <div class="col-lg-2 col-md-2 col-sm-3 col-12 float-start">
                                                                                            <html:select property="positionIdcom" styleId="positionIdcom" styleClass="form-select" onchange="javascript: searchCompetency('s', '-1');">
                                                                                                <html:optionsCollection filter="false" property="positions" label="ddlLabel" value="ddlValue">
                                                                                                </html:optionsCollection>
                                                                                            </html:select>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class='row' id='comp_cat'>
                                                                                    <%
                                                                                        int talentpoolCount = count;
                                                                                        int nextCount = 0;
                                                                                        String prev = "";
                                                                                        String prevclose = "";
                                                                                        String next = "";
                                                                                        String nextclose = "";
                                                                                        int last = 0;
                                                                                        if (session.getAttribute("NEXTCOMPETENCY") != null)
                                                                                        {
                                                                                            nextCount = Integer.parseInt((String)session.getAttribute("NEXTCOMPETENCY"));
                                                                                            CurrPageNo = Integer.parseInt((String)session.getAttribute("NEXTCOMPETENCYVALUE"));
                                                                                    %>
                                                                                    <input type='hidden' name='totalpage' value='<%= pageSize %>'/>
                                                                                    <input type="hidden" name="nextCompetencyValue" value="<%=CurrPageNo%>"/>
                                                                                    <%
                                                                                                if (nextCount == 0 && talentpoolCount > recordsperpage)
                                                                                                {
                                                                                                    next = ("<li class='next'><a href=\"javascript: searchCompetency('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                                                                                                    nextclose = ("</i></a></li>");
                                                                                                }
                                                                                                else if (nextCount > 0 && talentpoolCount >
                                                                                                    ((nextCount*recordsperpage) + recordsperpage))
                                                                                                {
                                                                                                    next = ("<li class='next'><a href=\"javascript: searchCompetency('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                                                                                                    nextclose = ("</i></a></li>");
                                                                                                    prev = ("<li class='prev'><a href=\"javascript: searchCompetency('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                                                                    prevclose = ("</i></a></li>");
                                                                                                }
                                                                                                else if (nextCount > 0 && talentpoolCount <=
                                                                                                    ((nextCount*recordsperpage) + recordsperpage))
                                                                                                {
                                                                                                    prev = ("<li class='prev'><a href=\"javascript: searchCompetency('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                                                                    prevclose = ("</i></a></li>");
                                                                                                    last = talentpoolCount;
                                                                                                }
                                                                                                else
                                                                                                {
                                                                                                    recordsperpage = talentpoolCount;
                                                                                                }
                                                                                            }
                                                                                            else
                                                                                            {
                                                                                                recordsperpage = talentpoolCount;
                                                                                            }
                                                                                            int test = nextCount;
                                                                                            int noOfPages = 1;
                                                                                            if (recordsperpage > 0)
                                                                                            {
                                                                                                noOfPages = talentpoolCount / recordsperpage;
                                                                                                if (talentpoolCount % recordsperpage > 0)
                                                                                                    noOfPages += 1;
                                                                                            }
                                                                                            if(total > 0)
                                                                                            {
                                                                                    %>
                                                                                    <div class="wesl_pagination pagination-mg m_15">
                                                                                        <div class="wesl_pg_rcds">
                                                                                            Page&nbsp;<%=CurrPageNo%> of <%= noOfPages %>, &nbsp;(<%= nextCount*recordsperpage + 1 %> - <%= last > 0?last:nextCount*recordsperpage + recordsperpage %> record(s) of <%=talentpoolCount%>)                                                
                                                                                        </div>
                                                                                        <div class="wesl_No_pages">
                                                                                            <div class="loanreportTables_paginate paging_bootstrap_full_number">
                                                                                                <ul class="wesl_pagination">
                                                                                                    <%
                                                                                                                                                        if(noOfPages > 1 && CurrPageNo != 1)
                                                                                                                                                        {
                                                                                                    %>
                                                                                                    <li class="wesl_pg_navi"><a href="javascript: searchCompetency('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
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
                                                                                                    <li><a href="javascript: searchCompetency('n', '<%=ii%>');"><%= ii+1 %></a></li>
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
                                                                                                    <li class='wesl_pg_navi'><a href="javascript: searchCompetency('n','<%=(noOfPages  - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
                                                                                                            <%
                                                                                                                                                                }
                                                                                                            %>
                                                                                                </ul>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="col-lg-12 all_client_sec" id="all_client">
                                                                                        <div class="table-rep-plugin sort_table" id="printArea">
                                                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                                                                <div class="table-responsive mb-0" data-bs-pattern="priority-columns">
                                                                                                    <table id="tech-companies-1" class="table table-striped">
                                                                                                        <thead>
                                                                                                            <tr>
                                                                                                                <th width="%"><b>Role Competency</b></th>
                                                                                                                <th width="%"><b>Priority</b></th>
                                                                                                                <th width="%"><b>Completed On</b></th>
                                                                                                                <th width="%" ><b>Valid Till</b></th>
                                                                                                                <th width="%" ><b>Assessor</b></th>
                                                                                                                <th width="%" ><b>Status</b></th>
                                                                                                                <th width="%" class="text-right"><b>Actions</b></th>
                                                                                                            </tr>
                                                                                                        </thead>
                                                                                                        <tbody>
                                                                                                            <%
                                                                                                            if(competency_list.size() > 0)
                                                                                                            {
                                                                                                                TalentpoolInfo info;
                                                                                                                for(int i = 0; i < competency_list.size(); i++)
                                                                                                                {
                                                                                                                    info = (TalentpoolInfo) competency_list.get(i);
                                                                                                                    if(info != null)
                                                                                                                    {
                                                                                                            %>
                                                                                                            <tr>
                                                                                                                <td><%= info.getRole() != null && !info.getRole().equals("") ? info.getRole() : "" %></td>
                                                                                                                <td><%= info.getPriority() != null && !info.getPriority().equals("") ? info.getPriority() : "" %></td>
                                                                                                                <td><%= info.getCompletedon() != null && !info.getCompletedon().equals("") ? info.getCompletedon() : "" %></td>
                                                                                                                <td><%= info.getValidtill() != null && !info.getValidtill().equals("") ? info.getValidtill() : "-" %></td>
                                                                                                                <td><%= info.getAssessorName() != null && !info.getAssessorName().equals("") ? info.getAssessorName() : "" %> 
                                                                                                                <td><%= info.getStatusValue() != null && !info.getStatusValue().equals("") ? info.getStatusValue() : "" %> 
                                                                                                                <td class="action_column">
                                                                                                                    <%if(info.getDate() != null && !info.getDate().equals("") ) {%>
                                                                                                                    <a class="mr_15" data-bs-toggle="modal" data-bs-target="#comp_ass_feeback_modal" href="javascript:;" onclick="javascript: getCompetencyFeedback('<%=info.getTrackerId()%>');"><img src="../assets/images/star_grey.png"/></a>
                                                                                                                    <%}%>
                                                                                                                    <a data-bs-toggle="modal" data-bs-target="#unassigned_details_modal_01" href="javascript:;" onclick="javascript: updatetracker('<%=info.getTrackerId()%>');"><img src="../assets/images/view.png"/></a></td>
                                                                                                            </tr>    
                                                                                                            <%
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                            else
                                                                                                            {
                                                                                                            %>
                                                                                                            <tr><td colspan='8'>No competency details available.</td></tr>
                                                                                                            <%
                                                                                                            }
                                                                                                            %>
                                                                                                        </tbody>
                                                                                                    </table>
                                                                                                </div>
                                                                                            </div>	
                                                                                        </div>
                                                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                                                            <a href="javascript:;" class="filter_btn"><i class="mdi mdi-filter-outline"></i> Filter</a>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="wesl_pagination pagination-mg mt_15">
                                                                                        <div class="wesl_pg_rcds">
                                                                                            Page&nbsp;<%=CurrPageNo%> of <%= noOfPages %>, &nbsp;(<%= nextCount*recordsperpage + 1 %> - <%= last > 0?last:nextCount*recordsperpage + recordsperpage %> record(s) of <%=talentpoolCount%>)                                                
                                                                                        </div>
                                                                                        <div class="wesl_No_pages">
                                                                                            <div class="loanreportTables_paginate paging_bootstrap_full_number">
                                                                                                <ul class="wesl_pagination">
                                                                                                    <%
                                                                                                                                            if(noOfPages > 1 && CurrPageNo != 1)
                                                                                                                                            {
                                                                                                    %>
                                                                                                    <li class="wesl_pg_navi"><a href="javascript: searchCompetency('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
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
                                                                                                    <li><a href="javascript: searchCompetency('n', '<%=ii%>');"><%= ii+1 %></a></li>
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
                                                                                                    <li class='wesl_pg_navi'><a href="javascript: searchCompetency('n','<%=(noOfPages  - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
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
            <%@include file ="../footer.jsp"%>
            <div id="unassigned_details_modal_01" class="modal fade parameter_modal large_modal">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="personalmodal">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div id="view_pdf" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button data-bs-toggle="modal" data-bs-target="#unassigned_details_modal_01" type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        <span class="resume_title">View Attachment</span>
                        <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                         <a href="" class="down_btn" id="diframe" download target="_blank"><img src="../assets/images/download.png"/></a>
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
        <div id="history_modal" class="modal fade multiple_position parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" data-keyboard="false" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button data-bs-toggle="modal" data-bs-target="#unassigned_details_modal_01" type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <h2>Tracker History</h2>
                                <div class="full_width client_position_table">
                                    <div class="table-rep-plugin sort_table">
                                        <div class="table-responsive mb-0">
                                            <table id="tech-companies-1" class="table table-striped">
                                                <thead>
                                                    <tr>
                                                        <th width="%"><span><b>Date / Time</b> </span></th>
                                                        <th width="%"><span><b>User Name</b></span></th>
                                                        <th width="%"><span><b>Action</b></span></th>
                                                    </tr>
                                                </thead>
                                                <tbody id="historydiv">                                                    
                                                </tbody>
                                            </table>
                                        </div>		
                                    </div>
                                </div>                            
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>            
        <div id="comp_ass_feeback_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="feedbackdiv">
                                    
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
        <div id="online_submission_modal" class="modal fade parameter_modal define_modal large_modal">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header"> 
                        <button type="button" data-bs-toggle="modal" data-bs-target="#unassigned_details_modal_01" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="row flex-center align-items-center mb_10" id="questiondiv">
                                    
                                </div>
                                <div class="row client_position_table">
                                    <div class="col-md-12 table-rep-plugin sort_table">
                                        <div class="table-responsive mb-0">
                                            <table id="tech-companies-1" class="table table-striped">
                                                <tbody>
                                                </tbody>
                                            </table>
                                        </div>	
                                    </div>
                                </div>

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

        <script>
            // toggle class show hide text section
            $(document).on('click', '.toggle-title', function () {
                $(this).parent()
                        .toggleClass('toggled-on')
                        .toggleClass('toggled-off');
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