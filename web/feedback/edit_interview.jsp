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
            int ctp = 15;
          if (session.getAttribute("CREWLOGIN") == null) {
    %>
    <jsp:forward page="/crewloginindex1.jsp"/>
    <%        }
        String message = "", clsmessage = "red_font";
        if (request.getAttribute("MESSAGE") != null)
        {
            message = (String)request.getAttribute("MESSAGE");
            request.removeAttribute("MESSAGE");
        }        
        if(message != null && (message.toLowerCase()).indexOf("success") != -1)
            clsmessage = "updated-msg";
        ArrayList list = new ArrayList();
        if(request.getSession().getAttribute("MODULEPER_LIST") != null)
            list = (ArrayList)request.getSession().getAttribute("MODULEPER_LIST");
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
        <link rel="stylesheet" type="text/css" href="../autofill/jquery-ui.min.css" />  <!-- Autofill-->
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/feedback.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="ocs_feedback vertical-collpsed crew_user bg_image">
    <html:form action="/feedback/FeedbackAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="doSaveAvailability"/>
        <html:hidden property="interviewId"/>
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">

                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 heading_title">
                                <h1>Interview Availability</h1>
                            </div>

                            <div class="col-md-12 col-xl-12">
                                <div class="body-background">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="tab-content">
                                                <div class="tab-pane active">
                                                    <div class="interview_sec">
                                                        
                                                        <div class="row">                                                            
                                                                                                                           
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group" >
                                                                <label class="control-label col-md-3">Availability:<span class="required" aria-required="true"></span></label>
                                                                <div class="mt-radio-inline radio_list">
                                                                    <label class="mt-radio">
                                                                        <html:radio property="type" value="1"/> Available
                                                                        <span></span>
                                                                    </label>
                                                                    <label class="mt-radio">
                                                                        <html:radio property="type" value="2"/> Not Available
                                                                        <span></span>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                                        
                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                                <label class="form_label">Remarks<span class="required">*</span></label>
                                                                <html:textarea property="remark" rows="6" styleId="remark" styleClass="form-control"></html:textarea>
                                                                <script type="text/javascript">
                                                                    document.getElementById("remark").setAttribute('placeholder', '');
                                                                </script>
                                                            </div>
                                                                
                                                        </div>
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="submitdiv">
                                                            <a href="javascript: saveAvailability();" class="save_btn"><img src="../assets/images/save.png"> Save</a>
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
        </div>
        
            
        <!-- JAVASCRIPT -->
        <script src="../assets/drop/jquery.min.js"></script>
            <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
            <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
            <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
            <script src="../autofill/jquery-ui.min.js" type="text/javascript"></script> <!-- autofill-->
            <script src="../assets/drop/dropzone.min.js" type="text/javascript"></script>  
            <script src="../assets/drop/app.min.js" type="text/javascript"></script>
            <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>
            <script src="../assets/js/bootstrap-select.min.js"></script>
            <script src="../assets/js/bootstrap-datepicker.min.js"></script>
            <script src="../assets/drop/form-dropzone.min.js" type="text/javascript"></script>
            <script src="/jxp/assets/js/sweetalert2.min.js"></script>           `	
        
    </html:form>
</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
