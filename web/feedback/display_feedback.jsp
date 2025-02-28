<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.feedback.FeedbackInfo"%>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="feedback" class="com.web.jxp.feedback.Feedback" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            if (session.getAttribute("CREWLOGIN") == null) {
    %>
    <jsp:forward page="/crewloginindex1.jsp"/>
    <%
        }
        int crewrotationId = 0, ctp = 9;
        if (session.getAttribute("CREWLOGIN") != null) {
            CrewloginInfo info = (CrewloginInfo) session.getAttribute("CREWLOGIN");
            if (info != null) {
                crewrotationId = info.getCrewrotationId();
            }
        }
        ArrayList feedback_list = new ArrayList();
        int count = 0;
        int recordsperpage = feedback.getCount();
        if (session.getAttribute("COUNT_LIST") != null) {
            count = Integer.parseInt((String) session.getAttribute("COUNT_LIST"));
        }
        if (session.getAttribute("FEEDBACK_LIST") != null) {
            feedback_list = (ArrayList) session.getAttribute("FEEDBACK_LIST");
        }
        int pageSize = count / recordsperpage;
        if (count % recordsperpage > 0) {
            pageSize = pageSize + 1;
        }

        int total = feedback_list.size();
        int showsizelist = feedback.getCountList("show_size_list");
        int CurrPageNo = 1;

        String message = "", clsmessage = "deleted-msg";
        if (request.getAttribute("MESSAGE") != null) {
            message = (String) request.getAttribute("MESSAGE");
            request.removeAttribute("MESSAGE");
        }
        if (message.toLowerCase().contains("success")) {
            message = "";
        }
        if (message != null && (message.toLowerCase()).indexOf("success") != -1) {
            clsmessage = "updated-msg";
        }

        ArrayList queans_list = new ArrayList();
        if (session.getAttribute("QUEANS_LIST") != null) {
            queans_list = (ArrayList) session.getAttribute("QUEANS_LIST");
        }

        int arr[] = new int[3];
        int pending = 0, completed = 0, total_count = 0;
        if (session.getAttribute("ARR_COUNT") != null) {
            arr = (int[]) session.getAttribute("ARR_COUNT");
            total_count = arr[0];
            pending = arr[1];
            completed = arr[2];

        }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= feedback.getMainPath("title") != null ? feedback.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">

        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/drop/dropzone.min.css" rel="stylesheet" type="text/css" />
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/feedback.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="ocs_feedback vertical-collpsed crew_user bg_image">
    <html:form action="/feedback/FeedbackAction.do" onsubmit="return false;">              
        <html:hidden property="surveyId"/>
        <html:hidden property="status"/>
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">

                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 heading_title"><h1>Wellness Feedback</h1></div>
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background index_list">
                                    <div class="row">
                                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 well_feed_page">
                                            <div class="row">
                                                <div class="col-xl-7 col-lg-7 col-md-6 col-sm-12 col-12 feed_list_1">
                                                    <div class="row">
                                                        <div class="col-xl-6 col-lg-5 col-md-10 col-sm-11 col-10">
                                                            <div class="row">
                                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 field_ic">
                                                                    <html:text property ="search" styleClass="form-control" styleId="example-text-input" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                                this.removeAttribute('readonly');
                                                                                this.blur();
                                                                                this.focus();
                                                                            }"/>
                                                                    <a onclick="javascript: searchFormAjax('s', '-1');" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-5 col-lg-5 col-md-5 col-sm-4 col-2 date_go_sec">
                                                            <div class="row">
                                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                                    <div class="input-daterange input-group input-addon">
                                                                        <html:text property="fromDate" styleId="wesl_from_dt" styleClass="form-control wesl_from_dt" onfocus="if (this.value == 'DD-MMM-YYYY') {
                                                                                    this.value = '';
                                                                                }" onblur="if (this.value == '') {
                                                                                            this.value = 'DD-MMM-YYYY';
                                                                                        }"/>
                                                                        <script>
                                                                            document.getElementById("wesl_from_dt").setAttribute("placeholder", "DD-MMM-YYYY");
                                                                            document.getElementById("wesl_from_dt").setAttribute("autocomplete", "off");
                                                                        </script>
                                                                        <div class="input-group-add">
                                                                            <span class="input-group-text">To</span>
                                                                        </div>
                                                                        <html:text property="toDate" styleId="wesl_to_dt" styleClass="form-control wesl_to_dt" onfocus="if (this.value == 'DD-MMM-YYYY') {
                                                                                    this.value = '';
                                                                                }" onblur="if (this.value == '') {
                                                                                            this.value = 'DD-MMM-YYYY';
                                                                                        }"/>
                                                                        <script>
                                                                            document.getElementById("wesl_to_dt").setAttribute("placeholder", "DD-MMM-YYYY");
                                                                            document.getElementById("wesl_to_dt").setAttribute("autocomplete", "off");
                                                                        </script>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-xl-1 col-lg-2 col-md-2 col-sm-12 col-12 date_go_sec"><a href="javascript:;" onclick="javascript: searchFormAjax('s', '-1');" class="go_btn">Go</a></div>
                                                        <div class="col-xl-1 col-lg-1 col-md-2 col-sm-1 col-2 user_filter">
                                                            <div class="row">
                                                                <div class="col-sm-12 field_ic filter_div">
                                                                    <div class="dropdown">
                                                                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                                                                            <i class="mdi mdi-filter-outline"></i>
                                                                        </button>
                                                                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                                                                            <li>
                                                                            <html:text property="fromDate1" styleId="wesl_from_dt1" styleClass="form-control wesl_from_dt" onfocus="if (this.value == 'DD-MMM-YYYY') {
                                                                                        this.value = '';
                                                                                    }" onblur="if (this.value == '') {
                                                                                                this.value = 'DD-MMM-YYYY';
                                                                                            }"/>
                                                                            <script>
                                                                                document.getElementById("wesl_from_dt1").setAttribute("placeholder", "DD-MMM-YYYY");
                                                                                document.getElementById("wesl_from_dt1").setAttribute("autocomplete", "off");
                                                                            </script>
                                                                            <div class="input-group-add">
                                                                                <span class="input-group-text">To</span>
                                                                            </div>
                                                                            <html:text property="toDate1" styleId="wesl_to_dt1" styleClass="form-control wesl_to_dt" onfocus="if (this.value == 'DD-MMM-YYYY') {
                                                                                        this.value = '';
                                                                                    }" onblur="if (this.value == '') {
                                                                                                this.value = 'DD-MMM-YYYY';
                                                                                            }"/>
                                                                            <script>
                                                                                document.getElementById("wesl_to_dt1").setAttribute("placeholder", "DD-MMM-YYYY");
                                                                                document.getElementById("wesl_to_dt1").setAttribute("autocomplete", "off");
                                                                            </script>
                                                                            </li>
                                                                            <li><a href="javascript: resetStatus();"><span class="round_circle circle_courses selected_circle"></span>All</a></li>
                                                                            <li><a href="javascript: setStatusearch('2');"><span class="round_circle circle_complete"></span> Complete</a></li>
                                                                            <li><a href="javascript: setStatusearch('1');"><span class="round_circle circle_exipry"></span> Pending</a></li>
                                                                        </ul>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-xl-5 col-lg-5 col-md-6 col-sm-12 col-12 perc_value feed_list_2">
                                                    <div class="row">
                                                        <div class="col-xl-5 col-lg-5 col-md-5 col-sm-5 col-5">
                                                            <div class="row d-flex align-items-center per_com">	
                                                                <div class="col-xl-6 col-lg-7 col-md-7 col-sm-7 col-6 text-left"><label>Percentage<br/> Completion</label></div>
                                                                <div class="col-xl-6 col-lg-5 col-md-5 col-sm-5 col-6"><span><% if (total_count > 0) {%><%=feedback.getDecimal(((double) completed / (double) total_count) * 100.0)%><% } else { %>0.00<% }%> %</span></div>
                                                            </div>	
                                                        </div>
                                                        <div class="col-xl-7 col-lg-7 col-md-7 col-sm-7 col-7">
                                                            <div class="ref_vie_ope onshore cou_una_pen_com tot_sent_filled">
                                                                <ul>
                                                                    <li class="courses_bg mob_value">Total <span><%=total_count%></span></li><li class="pending_bg mob_value">Pending <span><%=pending%></span></li><li class="complete_bg mob_value">Complete <span><%=completed%></span></li>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="" id='ajax_cat'>
                                            <input type="hidden" name="nextDel" value="<%= total%>" />
                                            <%
                                                int feedbackCount = count;
                                                int nextCount = 0;
                                                String prev = "";
                                                String prevclose = "";
                                                String next = "";
                                                String nextclose = "";
                                                int last = 0;
                                                if (session.getAttribute("NEXT") != null) {
                                                    nextCount = Integer.parseInt((String) session.getAttribute("NEXT"));
                                                    CurrPageNo = Integer.parseInt((String) session.getAttribute("NEXTVALUE"));
                                            %>
                                            <input type='hidden' name='totalpage' value='<%= pageSize%>'>
                                            <input type="hidden" name="nextValue" value="<%=CurrPageNo%>">
                                            <%
                                                    if (nextCount == 0 && feedbackCount > recordsperpage) {
                                                        next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                                                        nextclose = ("</i></a></li>");
                                                    } else if (nextCount > 0 && feedbackCount
                                                            > ((nextCount * recordsperpage) + recordsperpage)) {
                                                        next = ("<li class='next'><a href=\"javascript: searchFormAjax('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                                                        nextclose = ("</i></a></li>");
                                                        prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                        prevclose = ("</i></a></li>");
                                                    } else if (nextCount > 0 && feedbackCount
                                                            <= ((nextCount * recordsperpage) + recordsperpage)) {
                                                        prev = ("<li class='prev'><a href=\"javascript: searchFormAjax('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                        prevclose = ("</i></a></li>");
                                                        last = feedbackCount;
                                                    } else {
                                                        recordsperpage = feedbackCount;
                                                    }
                                                } else {
                                                    recordsperpage = feedbackCount;
                                                }
                                                int test = nextCount;
                                                int noOfPages = 1;
                                                if (recordsperpage > 0) {
                                                    noOfPages = feedbackCount / recordsperpage;
                                                    if (feedbackCount % recordsperpage > 0) {
                                                        noOfPages += 1;
                                                    }
                                                }
                                                if (total > 0) {
                                            %>
                                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                <div class="wesl_pagination pagination-mg mt_15">
                                                    <div class="wesl_pg_rcds">
                                                        Page&nbsp;<%=CurrPageNo%> of <%= noOfPages%>, &nbsp;(<%= nextCount * recordsperpage + 1%> - <%= last > 0 ? last : nextCount * recordsperpage + recordsperpage%> record(s) of <%=feedbackCount%>)                                                
                                                    </div>
                                                    <div class="wesl_No_pages">
                                                        <div class="loanreportTables_paginate paging_bootstrap_full_number">
                                                            <ul class="wesl_pagination">
                                                                <%
                                                                    if (noOfPages > 1 && CurrPageNo != 1) {
                                                                %>
                                                                <li class="wesl_pg_navi"><a href="javascript: searchFormAjax('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
                                                                        <%
                                                                            }
                                                                        %>
                                                                        <%= prev%><%= prevclose%> &nbsp;
                                                                <%
                                                                    int a1 = test - showsizelist;
                                                                    int a2 = test + showsizelist;
                                                                    for (int ii = a1; ii <= a2; ii++) {
                                                                        if (ii >= 0 && ii < noOfPages) {
                                                                            if (ii == test) {
                                                                %>
                                                                <li class="wesl_active"><a href="javascript:;"><%=ii + 1%></a></li>
                                                                    <%
                                                                    } else {
                                                                    %>
                                                                <li><a href="javascript: searchFormAjax('n', '<%=ii%>');"><%= ii + 1%></a></li>
                                                                    <%
                                                                                }
                                                                            }
                                                                        }
                                                                    %>
                                                                &nbsp;<%= next%><%= nextclose%>
                                                                <%
                                                                    if (noOfPages > 1 && CurrPageNo != noOfPages) {
                                                                %>
                                                                <li class='wesl_pg_navi'><a href="javascript: searchFormAjax('n','<%=(noOfPages - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
                                                                        <%
                                                                            }
                                                                        %>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div id="printArea">
                                                    <ul class="comp_list">
                                                        <li class="feedback_name_label">Feedback Name</li>
                                                        <li class="feedback_subm_on_label">Submitted On</li>
                                                        <li class="feedback_status_label">Status</li>
                                                        <li class="feedback_filter_label">
                                                            <div class="toggled-off_02 usefool_tool">
                                                                <div class="toggle-title_02">
                                                                    <img src="../assets/images/filter.png" class="fa-angle-left">
                                                                    <img src="../assets/images/filter_up.png" class="fa-angle-right">
                                                                </div>
                                                                <div class="toggle-content">
                                                                    <ul>
                                                                        <li><a href="javascript: resetStatus();"><span class="round_circle circle_courses selected_circle"></span>All</a></li>
                                                                        <li><a href="javascript: setStatusearch('1');"><span class="round_circle circle_exipry"></span> Pending</a></li>
                                                                        <li><a href="javascript: setStatusearch('2');"><span class="round_circle circle_complete"></span> Complete</a></li>
                                                                    </ul>
                                                                </div>
                                                            </div>	
                                                        </li>
                                                        <li class="feedback_ques_label text-center">Questions</li>
                                                        <li class="feedback_actions_label text-center">Actions</li>
                                                    </ul>
                                                    <ul class="comp_list_detail" id="sort_id">
                                                        <%
                                                            FeedbackInfo info;
                                                            int status = 0;
                                                            String stclass = "", stvalue = "";
                                                            for (int i = 0; i < total; i++) {
                                                                info = (FeedbackInfo) feedback_list.get(i);
                                                                if (info != null) {
                                                                    status = info.getStatus();
                                                                    if (status == 1) {
                                                                        stclass = "circle_exipry";
                                                                        stvalue = "Pending";
                                                                    } else if (status == 2) {
                                                                        stclass = "circle_complete";
                                                                        stvalue = "Completed";
                                                                    }
                                                        %>
                                                        <li>
                                                            <div class="comp_list_main">
                                                                <ul>
                                                                    <li class="feedback_name_label feedback_ord_list_1">
                                                                        <span class="value_record"><%= info.getFeedback() != null ? info.getFeedback() : ""%></span>
                                                                    </li>
                                                                    <li class="feedback_subm_on_label feedback_ord_list_2">
                                                                        <span class="mob_show label_title">Submitted On</span> <span class="value_record"><%= info.getSubmissionDate() != null ? info.getSubmissionDate() : ""%></span>
                                                                    </li>
                                                                    <li class="feedback_status_label feedback_ord_list_3">
                                                                        <span class="mob_show label_title">Status</span> <span class="round_circle <%=stclass%>"></span> <span class="value_record"><%=stvalue%></span>
                                                                    </li>

                                                                    <li class="feedback_filter_label feedback_ord_list_4">&nbsp;</li>
                                                                    <li class="feedback_ques_label text-center feedback_ord_list_5">
                                                                        <span class="mob_show label_title">Questions</span> <span class="value_record que_number"><%= feedback.changeNum(info.getQuestioncount(), 2)%></span>
                                                                    </li>
                                                                    <li class="feedback_actions_label action_list text-center feedback_ord_list_6">
                                                                        <a href="javascript:;" onclick="javascript: viewSurvey('<%=info.getSurveyId()%>');"><img src="../assets/images/view.png"/> <span class="mob_show view_appeal">View</span></a>	
                                                                    </li>
                                                                </ul>
                                                            </div>
                                                        </li>
                                                        <%
                                                                }
                                                            }
                                                        %>
                                                    </ul>
                                                </div>
                                                <div class="wesl_pagination pagination-mg mt_15">
                                                    <div class="wesl_pg_rcds">
                                                        Page&nbsp;<%=CurrPageNo%> of <%= noOfPages%>, &nbsp;(<%= nextCount * recordsperpage + 1%> - <%= last > 0 ? last : nextCount * recordsperpage + recordsperpage%> record(s) of <%=feedbackCount%>)                                                
                                                    </div>
                                                    <div class="wesl_No_pages">
                                                        <div class="loanreportTables_paginate paging_bootstrap_full_number">
                                                            <ul class="wesl_pagination">
                                                                <%
                                                                    if (noOfPages > 1 && CurrPageNo != 1) {
                                                                %>
                                                                <li class="wesl_pg_navi"><a href="javascript: searchFormAjax('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
                                                                        <%
                                                                            }
                                                                        %>
                                                                        <%= prev%><%= prevclose%> &nbsp;
                                                                <%
                                                                    for (int ii = a1; ii <= a2; ii++) {
                                                                        if (ii >= 0 && ii < noOfPages) {
                                                                            if (ii == test) {
                                                                %>
                                                                <li class="wesl_active"><a href="javascript:;"><%=ii + 1%></a></li>
                                                                    <%
                                                                    } else {
                                                                    %>
                                                                <li><a href="javascript: searchFormAjax('n', '<%=ii%>');"><%= ii + 1%></a></li>
                                                                    <%
                                                                                }
                                                                            }
                                                                        }
                                                                    %>
                                                                &nbsp;<%= next%><%= nextclose%>
                                                                <%
                                                                    if (noOfPages > 1 && CurrPageNo != noOfPages) {
                                                                %>
                                                                <li class='wesl_pg_navi'><a href="javascript: searchFormAjax('n','<%=(noOfPages - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
                                                                        <%
                                                                            }
                                                                        %>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <%}%>
                                        </div>
                                    </div>
                                </div>	
                            </div>
                        </div>
                    </div> 
                </div>
            </div>
        </div>
        <!-- END layout-wrapper -->
        <!-- JAVASCRIPT -->
        <script src="../assets/libs/jquery/jquery.min.js"></script>		
        <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/js/bootstrap-multiselect.js"></script>
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/time/bootstrap-timepicker.min.js" type="text/javascript"></script>
        <script src="../assets/js/app.js"></script>
        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
        <script src="../assets/time/components-date-time-pickers.min.js" type="text/javascript"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script>
        <script type="text/javascript">
                                                                            function addLoadEvent(func) {
                                                                                var oldonload = window.onload;
                                                                                if (typeof window.onload != 'function') {
                                                                                    window.onload = func;
                                                                                } else {
                                                                                    window.onload = function () {
                                                                                        if (oldonload) {
                                                                                            oldonload();
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
        </script>

        <script>
            // toggle class show hide text section
            $(document).on('click', '.toggle-title', function () {
                $(this).parent()
                        .toggleClass('toggled-on')
                        .toggleClass('toggled-off');
            });

            $(document).on('click', '.toggle-title_02', function () {
                $(this).parent()
                        .toggleClass('toggled-on_02')
                        .toggleClass('toggled-off_02');
            });
        </script>
        <script>
            $(function () {
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
                });

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
    </html:form>
</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
