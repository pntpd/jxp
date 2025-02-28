<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.feedback.FeedbackInfo"%>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="competency" class="com.web.jxp.feedback.Feedback" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            if (session.getAttribute("CREWLOGIN") == null) {
    %>
    <jsp:forward page="/crewloginindex1.jsp"/>
    <%
        }
        int crewrotationId = 0, ctp = 10;
        if (session.getAttribute("CREWLOGIN") != null) {
            CrewloginInfo info = (CrewloginInfo) session.getAttribute("CREWLOGIN");
            if (info != null) {
                crewrotationId = info.getCrewrotationId();
            }
        }
        int count = 0;
        int recordsperpage = competency.getCount();
        ArrayList competency_list = new ArrayList();
        if (session.getAttribute("COUNT_LIST") != null) {
            count = Integer.parseInt((String) session.getAttribute("COUNT_LIST"));
        }
        if (session.getAttribute("COMPETENCY_LIST") != null) {
            competency_list = (ArrayList) session.getAttribute("COMPETENCY_LIST");
        }

        int pageSize = count / recordsperpage;
        if (count % recordsperpage > 0) {
            pageSize = pageSize + 1;
        }

        int total = competency_list.size();
        int showsizelist = competency.getCountList("show_size_list");
        int CurrPageNo = 1;

        String message = "";
        if (request.getAttribute("APPEAL_MSG") != null) {
            message = (String) request.getAttribute("APPEAL_MSG");
            request.removeAttribute("APPEAL_MSG");
        }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= competency.getMainPath("title") != null ? competency.getMainPath("title") : ""%></title>
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

        <link href="../assets/filter/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/filter/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/feedback.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="ocs_feedback vertical-collpsed crew_user bg_image">
    <html:form action="/feedback/FeedbackAction.do" onsubmit="return false;">
        <html:hidden property="trackerId"/>
        <html:hidden property="doSendAppeal"/>
        <html:hidden property="doOnlineAssessment"/>
        <html:hidden property="doSaveFeedback"/>
        <html:hidden property="doCancelCompetency"/>
        <html:hidden property="doViewtopic"/>
        
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>

            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">

                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 heading_title"><h1>Competency Assessments</h1></div>
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background index_list">
                                    <div class="row">
                                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                            <div class="row">
                                                <div class="col-xl-3 col-lg-3 col-md-4 col-sm-4 col-12">
                                                    <div class="row">
                                                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 field_ic">
                                                            <html:text property ="compsearch" styleClass="form-control" styleId="example-text-input" maxlength="200" onkeypress="javascript: handleKeySearchCompetency(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                        this.removeAttribute('readonly');
                                                                        this.blur();
                                                                        this.focus();
                                                                    }"/>
                                                            <a href="javascript:;" onclick="searchFormAjaxCompetency('s', '-1');" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-xl-3 col-lg-4 col-md-4 col-sm-4 col-12">
                                                    <div class="row">
                                                        <div class="col-sm-12 field_ic filter_div">
                                                            <div class="input-group select2-bootstrap-prepend">
                                                                <span class="input-group-btn">
                                                                    <button class="btn btn-default" type="button" data-select2-open="single-prepend-text">
                                                                        <span class=""><i class="mdi mdi-filter-outline"></i></span>
                                                                    </button>
                                                                </span>
                                                                <html:select styleClass="form-control select2" property="compstatus" styleId="single-prepend-text" onchange="javascript: searchFormAjaxCompetency('s', '-1');">
                                                                    <html:option value="-1">Filter</html:option>
                                                                    <html:option value="0">Pending</html:option>
                                                                    <html:option value="1">Saved as draft</html:option>
                                                                    <html:option value="2">Assessment Complete</html:option>
                                                                    <html:option value="4">Competent</html:option>
                                                                    <html:option value="5">Not Yet Competent</html:option>
                                                                    <html:option value="6">Appealed</html:option>
                                                                </html:select>
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
                                            <input type="hidden" name="totalpage" value="<%= pageSize%>">
                                            <input type="hidden" name="nextValue" value="<%=CurrPageNo%>">
                                            <%
                                                    if (nextCount == 0 && feedbackCount > recordsperpage) {
                                                        next = ("<li class='next'><a href=\"javascript: searchFormAjaxCompetency('n','-1')\" title='First'><i class='fa fa-angle-right'>");
                                                        nextclose = ("</i></a></li>");
                                                    } else if (nextCount > 0 && feedbackCount
                                                            > ((nextCount * recordsperpage) + recordsperpage)) {
                                                        next = ("<li class='next'><a href=\"javascript: searchFormAjaxCompetency('n','-1')\" title='Next'><i class='fa fa-angle-right'>");
                                                        nextclose = ("</i></a></li>");
                                                        prev = ("<li class='prev'><a href=\"javascript: searchFormAjaxCompetency('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
                                                        prevclose = ("</i></a></li>");
                                                    } else if (nextCount > 0 && feedbackCount
                                                            <= ((nextCount * recordsperpage) + recordsperpage)) {
                                                        prev = ("<li class='prev'><a href=\"javascript: searchFormAjaxCompetency('p','-1')\" title='Prev'><i class='fa fa-angle-left'>");
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
                                                                <li class="wesl_pg_navi"><a href="javascript: searchFormAjaxCompetency('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
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
                                                                <li><a href="javascript: searchFormAjaxCompetency('n', '<%=ii%>');"><%= ii + 1%></a></li>
                                                                    <%
                                                                                }
                                                                            }
                                                                        }
                                                                    %>
                                                                &nbsp;<%= next%><%= nextclose%>
                                                                <%if (noOfPages > 1 && CurrPageNo != noOfPages) {%>
                                                                <li class='wesl_pg_navi'><a href="javascript: searchFormAjaxCompetency('n','<%=(noOfPages - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
                                                                        <%}%>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                                <ul class="comp_list">
                                                    <li class="comp_name_label">Competency Name</li>
                                                    <li class="comp_by_label">Complete By</li>
                                                    <li class="comp_on_label">Completed On</li>
                                                    <li class="comp_status_label">Status</li>
                                                    <li class="comp_actions_label">Actions</li>
                                                </ul>
                                                <ul class="comp_list_detail" id="sort_id">
                                                    <%
                                                        FeedbackInfo info;
                                                        int status = 0;
                                                        String stclass = "";
                                                        for (int i = 0; i < total; i++) {
                                                            info = (FeedbackInfo) competency_list.get(i);
                                                            if (info != null) {
                                                                status = info.getStatus();
                                                                if (status == 1) {
                                                                    stclass = "circle_exipry";
                                                                } else if (status == 2) {
                                                                    stclass = "circle_complete";
                                                                }
                                                    %>
                                                    <li>
                                                        <div class="comp_list_main">
                                                            <ul>
                                                                <li class="comp_name_label ord_list_1" onclick="javascript: viewSurvey('<%=info.getTrackerId()%>');">
                                                                    <span class="value_record"><%= info.getRole() != null ? info.getRole() : ""%></span><span class="know_ass">Knowledge Assessment</span>
                                                                </li>
                                                                <li class="comp_by_label ord_list_2" onclick="javascript: viewSurvey('<%=info.getTrackerId()%>');">
                                                                    <span class="mob_show label_title">Complete By</span> <span class="value_record"><%= info.getCompleteByDate() != null ? info.getCompleteByDate() : ""%></span>
                                                                </li>
                                                                <li class="comp_on_label ord_list_3" onclick="javascript: viewSurvey('<%=info.getTrackerId()%>');">
                                                                    <span class="mob_show label_title">Completed On</span> <span class="value_record"><%= info.getOnlineDate() != null ? info.getOnlineDate() : ""%></span>
                                                                </li>
                                                                <li class="comp_status_label ord_list_4" onclick="javascript: viewSurvey('<%=info.getTrackerId()%>');">
                                                                    <span class="mob_show label_title">Status</span>
                                                                    <span class="value_record"><%
                                                                        if (info.getStatus() == 4) {
                                                                        %>Competent<%
                                                                        } else if (info.getStatus() == 5) {
                                                                        %>Not Yet Competent<%
                                                                        } else if (info.getStatus() == 6) {
                                                                        %>Appealed<%
                                                                        } else if (info.getOnlineflag() == 2 || info.getStatus() == 3) {
                                                                        %>Assessment Complete <%
                                                                        } else if (info.getOnlineflag() == 1) {
                                                                        %>Saved as draft<%
                                                                        } else if (info.getOnlineflag() == 0) {
                                                                        %> Pending<%}%>
                                                                    </span>
                                                                </li>
                                                                <li class="comp_actions_label action_list text-center ord_list_5">
                                                                    <a href="javascript:;" onclick="getCompetencyPendingModal('<%=info.getTrackerId()%>');"><img src="../assets/images/view.png"/> <span class="mob_show view_appeal">View</span></a>	
                                                                    <a href="javascript:;" <%if (info.getStatus() == 5) {%>onclick="getCompetencyAppealModal('<%=info.getTrackerId()%>');" <%}%> class="disable_info<%= info.getStatus() == 5 ? "1" : ""%>"><img src="../assets/images/info.png"/> <span class="mob_show view_appeal">Appeal</span></a>	
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </li>
                                                    <%
                                                            }
                                                        }
                                                    %>
                                                </ul>
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
                                                                <li class="wesl_pg_navi"><a href="javascript: searchFormAjaxCompetency('n', '0');" title="First"><i class='fa fa-angle-double-left'></i></a></li>
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
                                                                <li><a href="javascript: searchFormAjaxCompetency('n', '<%=ii%>');"><%= ii + 1%></a></li>
                                                                    <%
                                                                                }
                                                                            }
                                                                        }
                                                                    %>
                                                                &nbsp;<%= next%><%= nextclose%>
                                                                <%
                                                                    if (noOfPages > 1 && CurrPageNo != noOfPages) {
                                                                %>
                                                                <li class='wesl_pg_navi'><a href="javascript: searchFormAjaxCompetency('n','<%=(noOfPages - 1)%>');" title='Last'><i class='fa fa-angle-double-right'></i></a></li>
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
        <div id="user_comp_pending_modal" class="modal fade parameter_modal large_modal">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body ">
                        <div class="row">
                            <div class="col-lg-12" id="dCompePendingModal">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="user_comp_ass_complete_feedback_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" data-bs-toggle="modal" data-bs-target="#user_comp_ass_complete_modal" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="dfeedback">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="user_comp_ass_app_request_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button"  class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="dUserCompAssAppModal">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="success_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-body">
                        <div class="row">
                            <div class="row client_position_table">
                                <div class="col-lg-12">
                                    <h2><%=message%></h2>
                                    <div class="row">
                                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                            <div class="row justify-content-md-center">
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6"><a href="javascript:;" data-bs-toggle="modal" data-bs-target="#success_modal" class="trans_btn">Okay</a></div>
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

        <!-- JAVASCRIPT -->
        <script src="../assets/filter/jquery.min.js"></script>	
        <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/js/bootstrap-multiselect.js"></script>
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/time/bootstrap-timepicker.min.js" type="text/javascript"></script>
        <script src="../assets/filter/select2.full.min.js" type="text/javascript"></script>
        <script src="../assets/filter/menu-app.js"></script>
        <script src="../assets/filter/app.min.js"></script> 
        <script src="../assets/filter/components-select2.min.js" type="text/javascript"></script>
        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>

        <script src="../assets/js/sweetalert2.min.js"></script>
        <%if (!message.equals("")) {%>
        <script type="text/javascript">
                $(window).on('load', function () {
                    $('#success_modal').modal('show');
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
            $(function () {
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
                });

            });
        </script>
        <script type="text/javascript">
            jQuery(document).ready(function () {
                $(".kt-selectpicker").selectpicker();
                $(".wesl_dt").datepicker({
                    todayHighlight: !0,
                    format: "yyyy",
                    viewMode: "years",
                    minViewMode: "years",
                    autoclose: "true",
                    orientation: "bottom"
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
