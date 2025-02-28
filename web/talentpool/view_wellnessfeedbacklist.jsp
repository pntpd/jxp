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
            int mtp = 2, submtp = 4, ctp = 21;
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
            ArrayList wellnessFeedback_list = new ArrayList();
            int count = 0;
            int recordsperpage = talentpool.getCount();
            if(session.getAttribute("COUNT_FEEDBACKLIST") != null)
                count = Integer.parseInt((String) session.getAttribute("COUNT_FEEDBACKLIST"));
            if(session.getAttribute("WELLNESSFEEDBACKHISTLIST") != null)
                wellnessFeedback_list = (ArrayList) session.getAttribute("WELLNESSFEEDBACKHISTLIST");
            int pageSize = count / recordsperpage;
            if(count % recordsperpage > 0)
                pageSize = pageSize + 1;

            int total = wellnessFeedback_list.size();
            int showsizelist = talentpool.getCountList("show_size_list");
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
            <html:hidden property="surveyId"/>
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
                                                <li><a href="javascript: printPage('printArea');"><img src="../assets/images/print-screen.png"/> Print Screen</a></li>
                                                <li><a href="javascript: exporttoexcelnew('21');"><img src="../assets/images/export-data.png"/> Export Data</a></li>
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
                                                                                    <div class="main-heading just_cont_inherit list_heading">
                                                                                        <div class="add-btn float-start mr_15 mt_5">
                                                                                            <h4>WELLNESS FEEDBACK HISTORY</h4>
                                                                                        </div>
                                                                                        <div class="col-lg-2 col-md-2 col-sm-3 col-12 float-start">
                                                                                            <html:select property="statusIndex" styleId="statusIndex" styleClass="form-select" onchange="javascript: searchFeedback('s', '-1');" >
                                                                                                <html:option value="-1">Select Filled On Status</html:option>
                                                                                                <html:option value="1">Pending</html:option>
                                                                                                <html:option value="2">Completed</html:option>
                                                                                                <html:option value="3">Expired</html:option>
                                                                                            </html:select> 
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class='row' id='ajax_cat'>
                                                                                    <%
                                                                                        int talentpoolCount = count;
                                                                                        int nextCount = 0;
                                                                                        String prev = "";
                                                                                        String prevclose = "";
                                                                                        String next = "";
                                                                                        String nextclose = "";
                                                                                        int last = 0;
                                                                                        if (session.getAttribute("NEXTFEEDBACK") != null)
                                                                                        {
                                                                                            nextCount = Integer.parseInt((String)session.getAttribute("NEXTFEEDBACK"));
                                                                                            CurrPageNo = Integer.parseInt((String)session.getAttribute("NEXTFEEDBACKVALUE"));
                                                                                    %>
                                                                                    <input type='hidden' name='totalpage' value='<%= pageSize %>'>
                                                                                    <input type="hidden" name="nextFeedbackValue" value="<%=CurrPageNo%>">
                                                                                    <%
                                                                                                if (nextCount == 0 && talentpoolCount > recordsperpage)
                                                                                                {
                                                                                                    next = ("<li class='next'><a href=\"javascript: searchFeedback('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                                                                                                    nextclose = ("</i></a></li>");
                                                                                                }
                                                                                                else if (nextCount > 0 && talentpoolCount >
                                                                                                    ((nextCount*recordsperpage) + recordsperpage))
                                                                                                {
                                                                                                    next = ("<li class='next'><a href=\"javascript: searchFeedback('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                                                                                                    nextclose = ("</i></a></li>");
                                                                                                    prev = ("<li class='prev'><a href=\"javascript: searchFeedback('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                                                                    prevclose = ("</i></a></li>");
                                                                                                }
                                                                                                else if (nextCount > 0 && talentpoolCount <=
                                                                                                    ((nextCount*recordsperpage) + recordsperpage))
                                                                                                {
                                                                                                    prev = ("<li class='prev'><a href=\"javascript: searchFeedback('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
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
                                                                                                    <li class="wesl_pg_navi"><a href="javascript: searchFeedback('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
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
                                                                                                    <li><a href="javascript: searchFeedback('n', '<%=ii%>');"><%= ii+1 %></a></li>
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
                                                                                                    <li class='wesl_pg_navi'><a href="javascript: searchFeedback('n','<%=(noOfPages  - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
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
                                                                                                                <th width="%"><b>Client - Asset</b></th>
                                                                                                                <th width="%"><b>Position - Rank</b></th>
                                                                                                                <th width="%"><b>Feedback Name</b></th>
                                                                                                                <th width="%"><b>Sent On</b></th>
                                                                                                                <th width="%"><b>Filled On</b></th>
                                                                                                                <th width="%" class="text-center"><b>Actions</b></th>
                                                                                                            </tr>
                                                                                                        </thead>
                                                                                                        <tbody>
                                                                                                            <%
                                                                                                            if(wellnessFeedback_list.size() > 0)
                                                                                                            {
                                                                                                                TalentpoolInfo ainfo;
                                                                                                                for(int i = 0; i < wellnessFeedback_list.size(); i++)
                                                                                                                {
                                                                                                                    ainfo = (TalentpoolInfo) wellnessFeedback_list.get(i);
                                                                                                                    if(ainfo != null)
                                                                                                                    {
                                                                                                            %>
                                                                                                            <tr>
                                                                                                                <td><%= ainfo.getClientName() != null && !ainfo.getClientName().equals("") ? ainfo.getClientName() : "" %></td>
                                                                                                                <td><%= ainfo.getPosition() != null && !ainfo.getPosition().equals("") ? ainfo.getPosition() : "" %>  </td>
                                                                                                                <td><%= ainfo.getFeedback() != null && !ainfo.getFeedback().equals("") ? ainfo.getFeedback() : "" %></td>
                                                                                                                <td><%= ainfo.getSenton() != null && !ainfo.getSenton().equals("") ? ainfo.getSenton() : "" %> 
                                                                                                                <td><%= ainfo.getStatusValue() != null && !ainfo.getStatusValue().equals("") ? ainfo.getStatusValue() : "" %>
                                                                                                                <td class="action_column text-center"><a data-bs-toggle="modal" data-bs-target="#medical_emer_details_modal_01" onclick="javascript: setQuestionModal('<%= ainfo.getSurveyId()%>');"><img src="../assets/images/view.png"><br></a></td>
                                                                                                            </tr> 
                                                                                                            <%
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                            else
                                                                                                            {
                                                                                                            %>
                                                                                                            <tr><td colspan='7'>No Wellness Feedback details available.</td></tr>
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
                                                                                                    <li class="wesl_pg_navi"><a href="javascript: searchFeedback('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
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
                                                                                                    <li><a href="javascript: searchFeedback('n', '<%=ii%>');"><%= ii+1 %></a></li>
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
                                                                                                    <li class='wesl_pg_navi'><a href="javascript: searchFeedback('n','<%=(noOfPages  - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
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
            <div id="medical_emer_details_modal_01" class="modal fade parameter_modal define_modal large_modal">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row" id ='questionmodal'>
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