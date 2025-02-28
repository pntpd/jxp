<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
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
        int status = 0, expired = 0, ctp = 9;
        String feedbackval = "", submissionDate = "", filleddate = "", date = "";
        if (request.getAttribute("SURVEYINFO") != null) {
            FeedbackInfo finfo = (FeedbackInfo) request.getAttribute("SURVEYINFO");
            if (finfo != null) {
                submissionDate = finfo.getSubmissionDate() != null && !finfo.getSubmissionDate().equals("") ? finfo.getSubmissionDate() : "&nbsp;";
                status = finfo.getStatus();
                expired = finfo.getExpired();
                feedbackval = finfo.getFeedback() != null ? finfo.getFeedback() : "";
                filleddate = finfo.getFilleddate() != null && !finfo.getFilleddate().equals("") ? finfo.getFilleddate() : "&nbsp;";
                date = submissionDate;
                if (status == 2) {
                    date = filleddate;
                }
            }
        }
        ArrayList queans_list = new ArrayList();
        if (session.getAttribute("QUEANS_LIST") != null) {
            queans_list = (ArrayList) session.getAttribute("QUEANS_LIST");
        }
        int total = queans_list.size();
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
        <link href="../assets/css/minimal.css" rel="stylesheet">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/feedback.js"></script>

    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="ocs_feedback vertical-collpsed crew_user bg_image">
    <html:form action="/feedback/FeedbackAction.do" onsubmit="return false;" styleClass="form-horizontal">
        <html:hidden property="search"/>
        <html:hidden property="surveyId"/>
        <html:hidden property="doSave"/>
        <html:hidden property="fromDate"/>
        <html:hidden property="toDate"/>
        <html:hidden property="fromDate1"/>
        <html:hidden property="toDate1"/>
        <html:hidden property="status"/>
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 heading_title">
                                <h1>
                                    <%if (status == 1) {%>
                                    <a href="javascript: goback();"><i class="ion ion-ios-arrow-round-back"></i></a> Wellness Feedback
                                        <%} else {%>
                                    <a href="javascript: goback();"><i class="ion ion-ios-arrow-round-back"></i></a> Wellness Feedback
                                        <%}%>
                                </h1>
                            </div>

                            <div class="col-md-12 col-xl-12">
                                <div class="body-background online_assessment">
                                    <div class="col-md-12 col-xl-12 online_head_section m_30">
                                        <div class="row d-flex align-items-center main-heading ">
                                            <div class="col-xl-9 col-lg-8 col-md-7 col-sm-12 col-12"><div class="add-btn"><h4><%=feedbackval%></h4></div></div>
                                            <div class="col-xl-3 col-lg-4 col-md-5 col-sm-12 col-12 text-right">
                                                <div class="row">
                                                    <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                        <label class="form_label">Complete By: <%=date%></label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>



                                    <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 onlin_ass_ques">
                                        <ul>
                                            <%
                                                FeedbackInfo info;
                                                String answer = "", question = "";
                                                int questionId = 0;
                                                for (int i = 0; i < total; i++) {
                                                    info = (FeedbackInfo) queans_list.get(i);
                                                    if (info != null) {
                                                        question = info.getQuestion() != null ? info.getQuestion(): "";
                                                        answer = info.getAnswer() != null ? info.getAnswer(): "";
                                                        questionId = info.getQuestionId();
                                            %>
                                            <li>
                                                <h5>Q<%=i + 1%>) <%=question%></h5>	
                                                <%if (status == 1) {%>                                                                            
                                                <%=feedback.getAnswer((i + 1), info.getType(), answer, questionId)%>
                                                <%} else if (status == 2) {%><%=answer%><%}%>
                                            </li>
                                            <%
                                                    }
                                                }
                                            %>
                                        </ul>
                                    </div>
                                    <%if (status == 1) {%>
                                    <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 save_sub_div" id="submitdiv">
                                        <div class="row justify-content-md-end">
                                            <div class="col-xl-2 col-lg-2 col-md-3 col-sm-4 col-6">
                                                <a href="javascript: submitForm();" class="sub_btn"> Submit</a>
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
        <script src="../assets/js/sweetalert2.min.js"></script>
    </html:form>
</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
