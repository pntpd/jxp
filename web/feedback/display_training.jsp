<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.feedback.FeedbackInfo"%>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="training" class="com.web.jxp.feedback.Feedback" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            if (session.getAttribute("CREWLOGIN") == null) {
    %>
    <jsp:forward page="/crewloginindex1.jsp"/>
    <%
        }
        int crewrotationId = 0, ctp = 12;
        if (session.getAttribute("CREWLOGIN") != null) {
            CrewloginInfo info = (CrewloginInfo) session.getAttribute("CREWLOGIN");
            if (info != null) {
                crewrotationId = info.getCrewrotationId();
            }
        }

        ArrayList list = new ArrayList();       
        if (session.getAttribute("TRAINING_LIST") != null) 
        {
            list = (ArrayList) session.getAttribute("TRAINING_LIST");
        }
        int total = list.size();
        String file_path = training.getMainPath("view_trainingfiles");
    %>
    <head>
        <meta charset="utf-8">
        <title><%= training.getMainPath("title") != null ? training.getMainPath("title") : ""%></title>
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
        
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>

            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">

                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 heading_title"><h1>Training Course</h1></div>
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background index_list">
                                    <div class="row">
                                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                            <div class="row">
                                                <div class="col-xl-3 col-lg-3 col-md-4 col-sm-4 col-12">
                                                    <div class="row">
                                                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 field_ic">
                                                            <html:text property ="tcsearch" styleClass="form-control" styleId="tcsearch" maxlength="200" onkeypress="javascript: handleKeySearchCompetency(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                        this.removeAttribute('readonly');
                                                                        this.blur();
                                                                        this.focus();
                                                                    }"/>                                                            
                                                            <a href="javascript:;" onclick="javascript: getTraininglist();" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                            <script type="text/javascript">
                                                                document.getElementById("tcsearch").setAttribute('placeholder', 'Search by Course Name');
                                                            </script>
                                                        </div>
                                                    </div>
                                                </div>
                                                
                                            </div>
                                        </div>
                                        <div class="" id='ajax_cat'>
                                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                <ul class="comp_list">
                                                    <li class="documents_name_label">Course Name</li>
                                                    <li class="docu_actions_label">Action</li>
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
                                                                            <li class="documents_name_label ord_list_1">
                                                                                <span class="value_record"><%= info.getCoursename() != null ? info.getCoursename() : ""%></span>
                                                                            </li>
                                                                            <li class="docu_actions_label action_list text-center ord_list_5">              
                                                                                <% if(info.getFilename() != null && !info.getFilename().equals("") ) {%><a href="<%=file_path+info.getFilename()+"story.html"%>" target='_blank'><img src="../assets/images/view.png"></a><%} else {%>&nbsp;<%}%>
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
