<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="crewlogin" class="com.web.jxp.crewlogin.Crewlogin" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            if (session.getAttribute("CREWLOGIN") == null) {
    %>
    <jsp:forward page="/crewloginindex1.jsp"/>
    <%
        }
        String emailId = "";
        if (session.getAttribute("CREWLOGIN") != null) {
            CrewloginInfo info = (CrewloginInfo) session.getAttribute("CREWLOGIN");
            if (info != null) {
                emailId = info.getEmailId() != null ? info.getEmailId() : "";
            }
        }
    %>  
    <head>
        <meta charset="utf-8">
        <title><%= crewlogin.getMainPath("title") != null ? crewlogin.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">	
        <link href="../assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/crewlogin.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="ocs_feedback vertical-collpsed user_main_login user_login_bg" onload="javascript: calltimer();">
    <html:form action="/crewlogin/CrewloginAction.do" onsubmit="return false;" styleClass="form-horizontal">
        <!-- Begin page -->
        <div id="layout-wrapper">
            <%@include file="beforloginheader.jsp" %>   
            <div class="main-content">
                <div class="page-content tab_panel no_tab1">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background">
                                    <div class="row justify-content-center">
                                        <div class="col-xl-4 col-lg-5 col-md-7 col-sm-8 col-12">
                                            <div class="card card-login">
                                                <div class="card-body">
                                                    <div class="login-pdg">
                                                        <div class="form-horizontal">
                                                            <div class="wrapper">
                                                                <div class="login-title">Enter OTP</div>

                                                                <div class="form-group field-pdg">
                                                                    <label>Email</label>
                                                                    <div class="input-field">
                                                                        <input type="text" class="form-control" placeholder="Enter Email" value="<%= emailId%>" required  readonly /> 
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-12 form-group otp_field m_30">
                                                                    <label>Enter OTP</label>
                                                                    <div class="row flex-center align-items-center">
                                                                        <div class="col-xl-10 col-lg-9 col-md-9 col-sm-9 col-9 pd_right_0">
                                                                            <input type="text" id="otp1" name="otp1" maxlength="1" class="form-control text-center" placeholder="" autocomplete='off' onkeyup="javascript: handleKeySearch(event, '1');">
                                                                            <input type="text" id="otp2" name="otp2" maxlength="1" class="form-control text-center" placeholder="" autocomplete='off' onkeyup="javascript: handleKeySearch(event, '2');">
                                                                            <input type="text" id="otp3" name="otp3" maxlength="1" class="form-control text-center" placeholder="" autocomplete='off' onkeyup="javascript: handleKeySearch(event, '3');">
                                                                            <input type="text" id="otp4" name="otp4" maxlength="1" class="form-control text-center" placeholder="" autocomplete='off' onkeyup="javascript: handleKeySearch(event, '4');">
                                                                            <input type="text" id="otp5" name="otp5" maxlength="1" class="form-control text-center" placeholder="" autocomplete='off' onkeyup="javascript: handleKeySearch(event, '5');">
                                                                            <input type="text" id="otp6" name="otp6" maxlength="1" class="form-control text-center" placeholder="" autocomplete='off'>
                                                                        </div>
                                                                        <div class="col-xl-2 col-lg-3 col-md-3 col-sm-3 col-3 pd_0">
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
                                                                        <a class="btn btn-block text-center my-3 re_send" onclick="javascript: resendOTP();">Resend OTP</a>
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
            </div>
        </div>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/js/sweetalert2.min.js"></script> 
    </html:form>
</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

</html>
