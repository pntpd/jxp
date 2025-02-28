<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!doctype html>
<html lang="en">
    <%
        try {
            String email = "";
            if (session.getAttribute("REG_EMAILID") != null) {
                email = (String) session.getAttribute("REG_EMAILID");
            }
    %>
    <head>
        <meta charset="utf-8">
        <title>OCS - Candidate Registration Form</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/pnt-images/ocsfavicon.png">
        <!-- Bootstrap Css -->
        <!-- Responsive Table css -->
        <link href="../assets/css/rwd-table.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/pnt_style.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/candidateregistration.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" onload="LoginDrawCaptchaReg();" class="vertical-collpsed registration_page">
        <header class="my-4">
            <nav class="mx-5 d-flex align-items-center h-55 ">
                <div>
                    <a href="https://ocs.services/" target="_blank"><img class="h-48" src="../assets/pnt-images/Logo.svg" alt="logo"></a>
                </div>
                <div class="socialIcon">
                    <a href="https://www.linkedin.com/company/ocsservices/" target="_blank"><img class=" h-22" src="../assets/pnt-images/Linkedin.svg" alt="LinkedIn"></a>
                    <a href="https://www.youtube.com/channel/UCisQPdftKELUIvGujVUgKPA" target="_blank"><img class=" h-22" src="../assets/pnt-images/Youtube.svg" alt="Youtube"></a>
                </div>
            </nav>
        </header>
        <section class="headerImg">
            <div class="bgImgwithText">
                <div class="d-flex gap-3 ml-5">
                    <div class="verticalRedLine"></div>
                    <div class="d-flex flex-column ">
                        <div class="overlayText">Start your Application</div>
                        <div class="overlayText">at OCS Services </div>
                    </div>
                </div>
            </div>
        </section>

    <html:form action="/candidateregistration/CandidateRegistrationAction.do" onsubmit="return false;">
        <!-- Begin page -->
        <div id="layout-wrapper">
            <!-- Start right Content -->
            <div class="main-content">
                <div class="login-main-div">

                    <div class="page-content tab_panel" style="padding-top:2.5rem">
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="tab-content">
                                                <div class="tab-pane active">
                                                    <div class="row row justify-content-md-center ">
                                                        <div class="col-lg-4 col-md-6 col-sm-10 col-10 bg-white box_shadow login_div_area">
                                                            <div class="mt-4">
                                                                <div class="full_width" style="margin-bottom:1rem">
                                                                    <center>
                                                                        <strong><span class="required">Create your Account</span></strong>
                                                                    </center>
                                                                </div>
                                                                <div class="form-group field-pdg" style="margin-bottom:0.8rem">
                                                                    <label>Email</label>
                                                                    <div class="input-field">

                                                                        <input style="margin-bottom:0;height:35px;padding-top:0;padding-bottom: 0;" type="text" name="emailId" autocomplete="off" class="form-control" placeholder="Enter Email" required maxlength="100" value="<%=email%>"  onkeypress="javascript: handleKeyGebOTP(event);"/>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group field-pdg full_width" style="margin-bottom:0.5rem;">
                                                                    <div class="d-flex1 captcha_par captcha_label full_width">
                                                                        <div class="input-group input-xlarge"style="margin-bottom:0.5rem">
                                                                            <div class="input-group-btn">
                                                                                <label>Captcha</label>
                                                                            </div>

                                                                            <input type="text" id="txtCaptcha" name="" readonly="true" class="captcha_text form-control" maxlength="20" placeholder="Enter captcha code" />
                                                                            <div class="input-group-btn">
                                                                                <a href="javascript:;" class="refreshcaptcha" id="btnrefresh" onclick="javascript: DrawCaptcha();"><img src="/jxp/assets/images/refresh.png"/></a>
                                                                            </div>

                                                                        </div>

                                                                    </div>
                                                                    <div class="captcha_par full_width enter_cap_code">
                                                                        <div class="form-group">
                                                                            <input type="text" class="captcha_input1 form-control" name="cap" autocomplete="off" onkeypress="javascript: handleKeyGebOTP(event);" id="txtInput" placeholder="Enter captcha" maxlength="6" />
                                                                        </div>
                                                                    </div>                                                                    
                                                                </div>                                                                
                                                                <span class="text-center" id="submitdiv" style="margin-top:0.5rem">
                                                                    <a class="btn loginBtn btn-block text-center my-3 get-otp" onclick="javascript: generateotpForm();">Get OTP</a>
                                                                </span>
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
        <!-- END layout-wrapper -->
        <%@include file ="../footer.jsp"%>
        <!-- JAVASCRIPT -->
        <script src="../assets/libs/jquery/jquery.min.js"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/app.js"></script>
        <!-- Responsive Table js -->
        <script src="../assets/js/rwd-table.min.js"></script>
        <script src="../assets/js/table-responsive.init.js"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script> 
        <script>
                                                                        function addLoadEvent(func)
                                                                        {
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
                                                                        addLoadEvent(DrawCaptcha());
                                                                        window.onload = function ()
                                                                        {
                                                                            for (var i = 0, l = document.getElementsByTagName('input').length; i < l; i++)
                                                                            {
                                                                                if (document.getElementsByTagName('input').item(i).type == 'text') {
                                                                                    document.getElementsByTagName('input').item(i).setAttribute('autocomplete', 'off');
                                                                                }
                                                                                ;
                                                                            }
                                                                            ;
                                                                        };
        </script>
        <script>
            // toggle class show hide text section
            $(document).on('click', '.toggle-title', function () {
                $(this).parent()
                        .toggleClass('toggled-on')
                        .toggleClass('toggled-off');
            });
        </script>
        <script>
            $(document).ready(function () {
                $('input').attr('autocomplete', 'off');
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