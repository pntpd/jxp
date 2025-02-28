<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.home.HomeInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="home" class="com.web.jxp.home.Home" scope="page"/>
<!doctype html>
<html lang="en">
<%
    try
    {
        int mtp = 7, submtp = 66;
        String per = "N", addper = "N", editper = "N", deleteper = "N";
        if (session.getAttribute("HOME_EMAILID") == null)
        {
%>
            <jsp:forward page="/homeindex1.jsp"/>
<%
        }
        
        String emailId = "";
        if(session.getAttribute("HOME_EMAILID") != null)
            emailId = (String)session.getAttribute("HOME_EMAILID");
%>  
<head>
    <meta charset="utf-8">
    <title><%= home.getMainPath("title") != null ? home.getMainPath("title") : "" %></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- App favicon -->
    <link rel="shortcut icon" href="../assets/images/favicon.png">
    <!-- Bootstrap Css -->
    <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
    <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
    <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
    <!-- Icons Css -->
    <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
    <!-- App Css-->
    <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
    <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
    <link href="../assets/css/style.css" rel="stylesheet" type="text/css"/>
    <script src="../jsnew/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="../jsnew/home.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed registration_page" onload="javascript: calltimer();">
<html:form action="/home/HomeAction.do" onsubmit="return false;" styleClass="form-horizontal">
<html:hidden property="doCancel"/>
<html:hidden property="doAdd"/>
<html:hidden property="doView"/>
    <!-- Begin page -->
    <div id="layout-wrapper">
        <!-- Start right Content -->
        <div class="main-content">
            <div class="page-content tab_panel">

                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-12 col-xl-12">
                            <div class="body-background">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="tab-content">
                                            <div class="tab-pane active">
                                                <div class="">

                                                    <div class="row row justify-content-md-center ">
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 bg-white box_shadow login_div_area">
                                                            <div class="mt-4">
                                                                <div class="full_width">
                                                                    <center><img class="otp_logo" src="/jxp/assets/images/ocs-logo.jpg" alt="logo"></center>
                                                                </div>
                                                                <div class="form-group field-pdg m_30">
                                                                    <label>Email</label>
                                                                    <div class="input-field"><input type="text" value="<%= emailId%>"  class="form-control" placeholder="Enter Email" required  readonly /></div>
                                                                </div>

                                                                <div class="col-md-12 form-group otp_field full_width m_30">                                                                    
                                                                    <label>Enter OTP</label>
                                                                    <div class="row flex-center align-items-center">
                                                                        <div class="col-md-9">
                                                                            <input type="text" name="otp1" id="otp1" maxlength="1" class="form-control text-center" placeholder=""  autocomplete='off' onkeyup="javascript: handleKeySearch(event, '1');"/>
                                                                            <input type="text" name="otp2" id="otp2" maxlength="1" class="form-control text-center" placeholder=""  autocomplete='off' onkeyup="javascript: handleKeySearch(event, '2');"/>
                                                                            <input type="text" name="otp3" id="otp3" maxlength="1" class="form-control text-center" placeholder=""  autocomplete='off' onkeyup="javascript: handleKeySearch(event, '3');"/>
                                                                            <input type="text" name="otp4" id="otp4" maxlength="1" class="form-control text-center" placeholder=""  autocomplete='off' onkeyup="javascript: handleKeySearch(event, '4');"/>
                                                                            <input type="text" name="otp5" id="otp5" maxlength="1" class="form-control text-center" placeholder=""  autocomplete='off' onkeyup="javascript: handleKeySearch(event, '5');"/>
                                                                            <input type="text" name="otp6" id="otp6" maxlength="1" class="form-control text-center" placeholder=""  autocomplete='off' />
                                                                        </div>                                                                        
                                                                        <div class="col-md-3">
                                                                            <span id="timer" class="time_value"></span>
                                                                        </div>                                                                        
                                                                    </div>
                                                                </div>
                                                                <div class="full_width">
                                                                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                                                                        <strong>OTP</strong> has been sent on <b><%= emailId%></b>. In case OTP mail is not there in your inbox please check spam/junk folder.
                                                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>    
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-md-6">
                                                                        <a class="btn btn-block text-center my-3" onclick="javascript: submitotpForm();">Verify OTP</a>
                                                                    </div>
                                                                    <div class="col-md-6">
                                                                        <a class="btn btn-block text-center my-3" onclick="javascript: resendOTP();">Resend OTP</a>
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
    </div>
    <!-- END layout-wrapper -->
    <%@include file="../footer.jsp" %>
    <!-- JAVASCRIPT -->
    <script src="../assets/libs/jquery/jquery.min.js"></script>		
    <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
    <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
    <script src="../assets/js/app.js"></script>	
    <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>
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
