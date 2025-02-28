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
        int crewrotationId = 0, ctp = 15;
        if (session.getAttribute("CREWLOGIN") != null) {
            CrewloginInfo info = (CrewloginInfo) session.getAttribute("CREWLOGIN");
            if (info != null) {
                crewrotationId = info.getCrewrotationId();
            }
        }
        ArrayList list = new ArrayList();
        if (session.getAttribute("INTERVIEW_LIST") != null) {
            list = (ArrayList) session.getAttribute("INTERVIEW_LIST");
        }
        int total = list.size();

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
        <html:hidden property="doAddAvailability"/>
        <html:hidden property="interviewId"/>
        
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>

            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">

                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 heading_title"><h1>Interview Details</h1></div>
                           
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background index_list">
                                    <div class="row">
                                        
                                        <div class="" id='ajax_cat'>
                                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                
                                                <ul class="comp_list">
                                                    <li class="comp_name_label">Interviewer Name</li>
                                                    <li class="comp_by_label">Interview Date</li>
                                                    <li class="comp_on_label">Mode</li>
                                                    <li class="comp_by_label">Interview Details</li>
                                                    <li class="comp_actions_label">Status</li>
                                                </ul>
                                                <ul class="comp_list_detail" id="sort_id">
                                                    <%
                                                        FeedbackInfo info;                                                        
                                                        for (int i = 0; i < total; i++) 
                                                        {
                                                            info = (FeedbackInfo) list.get(i);
                                                            if (info != null) 
                                                            {
                                                                
                                                    %>
                                                    <li>
                                                        <div class="comp_list_main">
                                                            <ul>                                                                
                                                                <li class="comp_name_label" onclick="javascript: ;">
                                                                    <span class="value_record"><%= info.getUsername() != null ? info.getUsername() : ""%></span>
                                                                </li>
                                                                <li class="comp_by_label">
                                                                    <span class="value_record"><%= info.getDate() != null ? info.getDate() : ""%></span>
                                                                </li>
                                                                <li class="comp_by_label">
                                                                    <span class="value_record"><%= info.getMode() != null ? info.getMode() : ""%></span>
                                                                </li>
                                                                <li class="comp_by_label">
                                                                    <span class="value_record"><%= info.getRemarks() != null ? info.getRemarks() : ""%></span>
                                                                </li>
                                                                <li class="comp_actions_label">
                                                                    <%if(info.getAvflag() <= 0) {%>
                                                                        <span class="value_record">
                                                                            <%if(info.getType() == 1) {%>
                                                                                <a href="javascript:;" onclick=" javascript: setAvailability('<%=info.getInterviewId()%>') ;" >Pending</a>
                                                                            <%}else{%>
                                                                                <a>Date Expired</a>
                                                                            <%}%>
                                                                        </span>
                                                                    <%}else if(info.getAvflag() == 1 && info.getIflag() != 6){%>
                                                                        <span class="value_record">Available </span>                                                                    
                                                                    <%}else if(info.getAvflag() == 2){%>
                                                                        <span class="value_record">Unavailable</span>
                                                                    <%}else if(info.getAvflag() == 1 && info.getIflag() == 6){%>
                                                                        <span class="value_record">Interview Not Cleared</span>
                                                                    <%}%>
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
        <div id="user_pending_modal" class="modal fade parameter_modal large_modal">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body ">
                        <div class="row">
                            <div class="col-lg-12" id="int_pending_div">

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
                
    </html:form>
</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
