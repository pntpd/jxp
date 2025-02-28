<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.candidate.CandidateInfo" %>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="candidate" class="com.web.jxp.candidate.Candidate" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 2, submtp = 7, ctp = 7;
            if (session.getAttribute("CREWLOGIN") == null) {
    %>
    <jsp:forward page="/crewloginindex1.jsp"/>
    <%
        }
        String file_path = candidate.getMainPath("view_candidate_file");
        CandidateInfo expinfo = null;
        if (request.getAttribute("CANDTRAININGCERTINFO") != null) {
            expinfo = (CandidateInfo) request.getAttribute("CANDTRAININGCERTINFO");
        }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= candidate.getMainPath("title") != null ? candidate.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/feedback.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="ocs_feedback vertical-collpsed crew_user bg_image">
    <html:form action="/feedback/FeedbackAction.do" onsubmit="return false;" styleClass="form-horizontal">
        <html:hidden property="status" />
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 heading_title"><h1>Profile > Training, Certification & Safety Course Details</h1></div>
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                <div class="body-background">
                                    <div class="row">
                                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 form_group mt_30">
                                            <%
                                                if (expinfo != null) {
                                            %>
                                            <div class="row">
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Course Type</label>
                                                    <span class="form-control"><%= expinfo.getCoursetype() != null && !expinfo.getCoursetype().equals("") ? expinfo.getCoursetype() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Course Name</label>
                                                    <span class="form-control"><%= expinfo.getCoursename() != null && !expinfo.getCoursename().equals("") ? expinfo.getCoursename() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Educational Institute</label>
                                                    <span class="form-control"><%= expinfo.getEduinstitute() != null && !expinfo.getEduinstitute().equals("") ? expinfo.getEduinstitute() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Location of Institute (City)</label>
                                                    <span class="form-control"><%= expinfo.getCity() != null && !expinfo.getCity().equals("") ? expinfo.getCity() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Date of Issue</label>
                                                    <span class="form-control"><%= expinfo.getDateofissue() != null && !expinfo.getDateofissue().equals("") ? expinfo.getDateofissue() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Certification No.</label>
                                                    <span class="form-control"><%= expinfo.getCertificateno() != null && !expinfo.getCertificateno().equals("") ? expinfo.getCertificateno() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Date of Expiry</label>
                                                    <span class="form-control"><%= expinfo.getExpirydate() != null && !expinfo.getExpirydate().equals("") ? expinfo.getExpirydate() : "&nbsp;"%></span>
                                                </div>
                                                <%
                                                if(expinfo.getCertifilename() != null && !expinfo.getCertifilename().equals("")){
                                                %>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Attached Document</label>
                                                    <a href="javascript:;" class="attache_btn" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript: setIframe('<%=file_path + expinfo.getCertifilename()%>');"><i class="fas fa-paperclip"></i> Preview</a>
                                                </div>
                                                <%}%>
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
        <div id="view_pdf" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        <span class="resume_title">Document</span>
                        <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                        <a href="javascript:;" class="down_btn" download=""><img src="../assets/images/download.png"/></a>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <iframe id='iframe' class="doc" src=""></iframe>
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
        <script src="../assets/js/app.js"></script>
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
    </html:form>
</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
