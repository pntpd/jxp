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
        ArrayList assqus_list = new ArrayList();
        if (request.getAttribute("ASSQUSTION_LIST") != null) {
            assqus_list = (ArrayList) request.getAttribute("ASSQUSTION_LIST");
        }
        FeedbackInfo info = null;
        if (session.getAttribute("PCODE_INFO") != null) {
            info = (FeedbackInfo) session.getAttribute("PCODE_INFO");
        }

        int total = assqus_list.size();
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
        <html:hidden property="compsearch"/>
        <html:hidden property="compstatus"/>
        <html:hidden property="doSaveAssessment"/>
        <html:hidden property="doSubmitAssessment"/>
        <html:hidden property="doCancelCompetency"/>
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 heading_title"><h1><a href="javascript:;" onclick="javascript: gobackcomp();"><i class="ion ion-ios-arrow-round-back"></i></a> Competency</h1></div>
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background online_assessment">
                                    <div class="col-md-12 col-xl-12 online_head_section m_30">
                                        <div class="row d-flex align-items-center main-heading ">
                                            <div class="col-xl-8 col-lg-8 col-md-7 col-sm-12 col-12"><div class="add-btn"><h4><%= (info != null && info.getRole() != null) ? info.getRole() : ""%></h4></div></div>
                                            <div class="col-xl-4 col-lg-4 col-md-5 col-sm-12 col-12 float-end">
                                                <div class="row">
                                                    <div class="col-xl-7 col-lg-7 col-md-7 col-sm-6 col-12">
                                                        <label class="form_label">Complete By: <%= (info != null && info.getCompleteByDate() != null) ? info.getCompleteByDate() : ""%></label>
                                                    </div>
                                                    <div class="col-xl-5 col-lg-5 col-md-5 col-sm-6 col-12 cl_mob_hide">
                                                        <a href="javascript:;" onclick="javascript: resetCompetency();" class="clear_respon">Clear&nbsp;Responses</a>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 onlin_ass_ques">
                                        <div class="table-rep-plugin">
                                            <div class="table-responsive mb-0">
                                                <table class="table table-striped1">

                                                    <tbody>
                                                        <%if (total > 0) {
                                                                FeedbackInfo qinfo = null;
                                                                String cate = "";
                                                                for (int i = 0; i < total; i++) {
                                                                    qinfo = (FeedbackInfo) assqus_list.get(i);
                                                                    if (qinfo != null) {
                                                        %>
                                                        <tr>
                                                            <td>
                                                                <%if (cate.equals("") || !cate.equals(qinfo.getCategory())) {%>
                                                                <h4><%=qinfo.getCategory() != null ? qinfo.getCategory() : ""%></h4>
                                                                <%
                                                                    }
                                                                %>
                                                                <h5><%=qinfo.getQuestion() != null ? "Q" + (i + 1) + ") " + qinfo.getQuestion() : ""%></h5>	
                                                                <textarea name="answer" class="form-control" rows="4"><%=qinfo.getAnswer() != null ? qinfo.getAnswer() : ""%></textarea>
                                                                <input type="hidden" name="questionId" value="<%=qinfo.getQuestionId()%>"/>
                                                                <input type="hidden" name="trackerDltsId" value="<%=qinfo.getTrackerdtlsId()%>"/>
                                                            </td>
                                                        </tr>
                                                        <%   cate = qinfo.getCategory() != null ? qinfo.getCategory() : "";
                                                                    }
                                                                }
                                                            }
                                                        %>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <div class="full_width">
                                            <div class="form-check permission-check">
                                                <input class="form-check-input" type="checkbox" id="allwork" name="allwork" value="1">
                                                <span class="ml_10">I confirm that the answers provided in this assessment are genuine and represent my own knowledge and understanding.</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 save_sub_div">
                                        <div class="row justify-content-md-end">
                                            <div class="col-xl-2 col-lg-2 col-md-3 col-sm-4 col-6"><a href="javascript:;" onclick="getSaveAssessment();" class="save_as_draft">Save As Draft</a></div>
                                            <div class="col-xl-2 col-lg-2 col-md-3 col-sm-4 col-6"><a href="javascript:;" onclick="getSubmitAssessment();" class="sub_btn">Submit</a></div>
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
        <script src="../assets/js/sweetalert2.min.js"></script>
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
<%    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
