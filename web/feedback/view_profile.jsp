<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.feedback.FeedbackInfo"%>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@page import="com.web.jxp.candidate.CandidateInfo"%>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="feedback" class="com.web.jxp.feedback.Feedback" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 2, submtp = 7, ctp = 1;
          if (session.getAttribute("CREWLOGIN") == null) {
    %>
    <jsp:forward page="/crewloginindex1.jsp"/>
    <%        }
        CandidateInfo info = null;
        if (request.getAttribute("CANDIDATE_DETAIL_CREW") != null) {
            info = (CandidateInfo) request.getAttribute("CANDIDATE_DETAIL_CREW");
            request.removeAttribute("CANDIDATE_DETAIL_CREW");
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
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/feedback.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="ocs_feedback vertical-collpsed crew_user bg_image">
    <html:form action="/feedback/FeedbackAction.do" onsubmit="return false;" styleClass="form-horizontal">
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">

                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 heading_title"><h1>Profile > Personal</h1></div>
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="tab-content">
                                                <div class="tab-pane active">
                                                    <div class="">
                                                        <%
                                                            if (info != null) {
                                                                String view_path = feedback.getMainPath("view_candidate_file");
                                                                String cphoto = info.getPhoto() != null && !info.getPhoto().equals("") ? view_path + info.getPhoto() : "../assets/images/empty_user.png";
                                                                String resumefilename = info.getResumefilename() != null && !info.getResumefilename().equals("") ? view_path + info.getResumefilename() : "";
                                                                String certificate = "";
                                                        %>
                                                        <div class="row m_30">
                                                            <div class="col-xl-2 col-lg-3 col-md-3 col-sm-4 col-12">
                                                                <div class="row mt_30">
                                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                                        <div class="user_photo pic_photo">
                                                                            <img src="../assets/images/user.png">
                                                                            <div class="upload_file">
                                                                                <input id="upload1" type="file">
                                                                                <img src="<%=cphoto%>">
                                                                                <a href="javascript:;" id="upload_link_edit" class="profile_edit d-none"><i class="ion ion-md-create"></i></a> 
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-3 col-lg-4 col-md-4 col-sm-5 col-12 text-left flex-end align-items-end edit_sec">
                                                                <ul class="resume_attach">
                                                                    <li><label class="form_label">Resume (5MB) (.pdf/.docx)</label></li>
                                                                    <li>
                                                                        <a href="javascript:;" onclick="javascript: viewimg('<%=info.getCandidateId()%>', '<%=info.getFirstname().replaceAll("['\"&\\s]", "")%>');" data-bs-toggle="modal" data-bs-target="#view_resume_list" class="attache_btn">
                                                                            <i class="fas fa-paperclip"></i> Attachment <span class="attach_number">001</span>
                                                                        </a>
                                                                    </li>

                                                                </ul>
                                                            </div>
                                                        </div>

                                                        <div class="row">
                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group sub_head_title"><h2>Personal Information</h2></div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                                <label class="form_label">First Name</label>
                                                                <span class="form-control"><%= (!info.getFirstname().equals("") && info.getFirstname() != null ? info.getFirstname() : "&nbsp;")%></span>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                                <label class="form_label">Middle Name</label>
                                                                <span class="form-control"><%=(!info.getMiddlename().equals("") && info.getMiddlename() != null ? info.getMiddlename() : "&nbsp;")%></span>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                                <label class="form_label">Last Name</label>
                                                                <span class="form-control"><%= (!info.getLastname().equals("") && info.getLastname() != null ? info.getLastname() : "&nbsp;")%></span>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                                <label class="form_label">Date Of Birth</label>
                                                                <span class="form-control"><%= (!info.getDob().equals("") && info.getDob() != null && !info.getDob().equals("") ? info.getDob() : "&nbsp;")%></span>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                                <label class="form_label">Age</label>
                                                                <span class="form-control"><%= (info.getAge() > 0 ? info.getAge() : "&nbsp;")%></span>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                                <label class="form_label">Gender</label>
                                                                <span class="form-control"><%= (!info.getGender().equals("") && info.getGender() != null && !info.getGender().equals("") ? info.getGender() : "&nbsp;")%></span>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                                <label class="form_label">Marital Status</label>
                                                                <span class="form-control"><%= (!info.getMaritialstatus().equals("") && info.getMaritialstatus() != null && !info.getMaritialstatus().equals("") ? info.getMaritialstatus() : "&nbsp;")%></span>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                                <label class="form_label">Nationality</label>
                                                                <span class="form-control"><%= (!info.getNationality().equals("") && info.getNationality() != null && !info.getNationality().equals("") ? info.getNationality() : "&nbsp;")%></span>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                                <label class="form_label">Email ID</label>
                                                                <span class="form-control"><%= (!info.getEmailId().equals("") && info.getEmailId() != null ? info.getEmailId() : "&nbsp;")%></span>
                                                            </div>
                                                        </div>

                                                        <div class="row">
                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group sub_head_title"><h2>Address</h2></div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                                <label class="form_label">Country</label>
                                                                <span class="form-control"><%= (info.getCountryName() != null && !info.getCountryName().equals("") ? info.getCountryName() : "&nbsp;")%></span>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                                <label class="form_label">City</label>
                                                                <span class="form-control"><%= (info.getCity() != null && !info.getCity().equals("") ? info.getCity() : "&nbsp;")%></span>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                                <div class="row">
                                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                                        <label class="form_label">Permanent Address</label>
                                                                        <span class="form-control m_15"><%= (info.getAddress1line1() != null && !info.getAddress1line1().equals("") ? info.getAddress1line1() : "&nbsp;")%></span>
                                                                        <span class="form-control m_15"><%= (info.getAddress1line2() != null && !info.getAddress1line2().equals("") ? info.getAddress1line2() : "&nbsp;")%></span>
                                                                        <span class="form-control"><%= (info.getAddress1line3() != null && !info.getAddress1line3().equals("") ? info.getAddress1line3() : "&nbsp;")%></span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                                <label class="form_label">Primary Contact No.</label>
                                                                <span class="form-control"><%= (info.getContactno1() != null && !info.getContactno1().equals("") ? ((info.getCode1Id() != null ? info.getCode1Id() : "") + info.getContactno1()) : "&nbsp;")%></span>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                                <label class="form_label">Nearest International Airport.</label>
                                                                <span class="form-control"><%= (info.getAirport1() != null && !info.getAirport1().equals("") ?  info.getAirport1() : "&nbsp;")%></span>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                                <label class="form_label">Nearest Domestic Airport.</label>
                                                                <span class="form-control"><%= (info.getAirport2() != null && !info.getAirport2().equals("") ?  info.getAirport2() : "&nbsp;")%></span>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group sub_head_title"><h2>Application Details</h2></div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                                <label class="form_label">Applying Job For</label>
                                                                <span class="form-control"><%= (info.getApplytypevalue() != null && !info.getApplytypevalue().equals("") ? info.getApplytypevalue() : "&nbsp;")%></span>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                                <label class="form_label">Position Applied For</label>
                                                                <span class="form-control"><%= (info.getPosition() != null && !info.getPosition().equals("") ? info.getPosition() : "&nbsp;")%></span>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                                <label class="form_label">Asset Type</label>
                                                                <span class="form-control"><%= (info.getAssettype() != null && !info.getAssettype().equals("") ? info.getAssettype() : "&nbsp;")%></span>
                                                            </div>
                                                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12 form_group">
                                                                <label class="form_label">Expected Salary (Per Annum)</label>
                                                                <div class="row">
                                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4">
                                                                        <span class="form-control"><%= (info.getCurrency() != null && !info.getCurrency().equals("") ? info.getCurrency() : "&nbsp;")%></span>
                                                                    </div>
                                                                    <div class="col-lg-8 col-md-8 col-sm-8 col-8">
                                                                        <span class="form-control"><%= (info.getExpectedsalary() > 0 ? info.getExpectedsalary() : "&nbsp;")%></span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>	
                                                        <%} else {%>&nbsp;<%}%>
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
        <script src="../assets/time/components-date-time-pickers.min.js" type="text/javascript"></script>            `	
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
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
