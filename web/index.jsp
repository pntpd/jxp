<%@page language="java" %>
<%@page import="java.util.ArrayList" %>
<%@page import="com.web.jxp.base.*" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<jsp:useBean id="base" class="com.web.jxp.base.Base" scope="page"/>
<jsp:useBean id="user" class="com.web.jxp.user.User" scope="page"/>
<!DOCTYPE html>
<html lang="en">
<%
    String message = "";
    if(session.getAttribute("SESSIONEXP") != null)
    {
        message = (String) session.getAttribute("SESSIONEXP");
        session.removeAttribute("SESSIONEXP");
    }
    if(session.getAttribute("LOGOUTMESSAGE") != null)
    {
        message = (String) session.getAttribute("LOGOUTMESSAGE");
        session.removeAttribute("LOGOUTMESSAGE");
    }
%>
<head>
    <meta charset="utf-8" />
    <title><%= base.getMainPath("title") != null ? base.getMainPath("title") : "" %></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <!-- App favicon -->
    <link rel="shortcut icon" href="/jxp/assets/images/favicon.png" />
    <!-- Bootstrap Css -->
    <link href="/jxp/assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css" />
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;0,800;1,300;1,400;1,500;1,600;1,700;1,800&display=swap" rel="stylesheet">
    <!-- Icons Css -->
    <link href="/jxp/assets/css/icons.min.css" rel="stylesheet" type="text/css" />
    <!-- App Css-->
    <link href="/jxp/assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css" />
    <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
    <link href="/jxp/assets/css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="jsnew/common.js"></script>
    <script type="text/javascript" src="jsnew/login.js"></script>
</head>
<body class="login_modal">
    <form name="loginForm" method="post" onsubmit="return false;">
        <!-- Begin page -->
        <div class="accountbg" style="background-color: #2478a6; background-size: cover; background-position: center;"></div>
        <div class="login_logo"> 
            <img src="/jxp/assets/images/login-bg.png" alt="logo" class="logo-class" />
        </div>
        <div class="wrapper-page account-page-full  d-flex aligns-items-center justify-content-center">
            <div class="card shadow-none d-flex1 aligns-items-center justify-content-center">
                <div class="card-block">
                    <div class="account-box">
                        <div class="card-box shadow-none login_box">
                            <div class="wrapper bg-white">
                                <center><img src="/jxp/assets/images/login-logo.png"/></center>                                
                                <div class="mt-4">
                                    <div class="form-group field-pdg">
                                        <label>User ID</label>
                                        <div class="input-field"><input type="text" name="username" autocomplete="off" class="form-control" placeholder="Enter User ID" required maxlength="100" onfocus="if (this.hasAttribute('readonly')) {this.removeAttribute('readonly'); this.blur(); this.focus();}" readonly="readonly"/></div>
                                    </div>
                                    <div class="form-group field-pdg">
                                        <label>Password</label>
                                        <div class="input-field">
                                            <input type="password" class="form-control" placeholder="Enter Password" required maxlength="50" name="password" onkeypress="javascript: handleKeyLogin(event);" onfocus="if (this.hasAttribute('readonly')) {this.removeAttribute('readonly'); this.blur(); this.focus();}" readonly="readonly" />
                                        </div>
                                    </div>
                                    <div class="form-group field-pdg full_width m_30">
                                        <div class="d-flex1 captcha_par captcha_label full_width">
                                            <div class="input-group input-xlarge">
                                                <div class="input-group-btn">
                                                    <label>Captcha</label>
                                                </div>
                                                <input type="text" id="txtCaptcha" name="txtCaptcha" readonly="true" class="captcha_text form-control" maxlength="20" placeholder="Captcha code" />
                                                <div class="input-group-btn">
                                                    <a class="refreshcaptcha" id="btnrefresh" href="javascript:;" onclick="javascript: DrawCaptcha();"><img src="/jxp/assets/images/refresh.png"/></a>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="captcha_par full_width enter_cap_code">
                                            <div class="form-group">
                                                <input type="text" class="captcha_input1 form-control" name="cap" autocomplete="off" onkeypress="javascript: handleKeyLogin(event);" id="txtInput" placeholder="Enter captcha" maxlength="6" />
                                            </div>
                                        </div>	
                                    </div>     
                                    <button class="btn btn-block text-center my-3" onclick="javascript: submitForm();">Sign In</button>
<!--                                    <div id="submitdiv">                                    
                                        <button class="btn btn-block text-center my-3" onclick="javascript: submitForm();">Sign In</button>									
                                    </div>                                    -->
                                    <div class="checkbox-check text-center">
                                        <span class="text-center" id="submitdiv">
                                            <a href="javascript: forgotForm();" class="forgot_pd"> Forgot Password?</a>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                           <div class="copy_right_privacy">
                               <p><span class="copy_icon"><img src="assets/images/copy_right.png"></span> HubC3</p>
                               <a href="<%=base.getMainPath("web_path")%>/DataSecurityPolicy.html" target='_blank'>Privacy Policy</a>
                           </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade welcome_page show blur_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" id="logout_modal">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <h3><%=message%></h3>
                                <a href="javascript: closelogout();" class="log_in">Log In</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>     
        <script src="/jxp/assets/libs/jquery/jquery.min.js"></script>
        <script src="/jxp/assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script>
    </form>
</body>
<script type="text/javascript">
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
    window.onload = function() 
    {
        for(var i = 0, l = document.getElementsByTagName('input').length; i < l; i++) 
        {
            if(document.getElementsByTagName('input').item(i).type == 'text') {
                document.getElementsByTagName('input').item(i).setAttribute('autocomplete', 'off');
            };
        };
    };
    <% if(!message.equals("")){%>
        $(window).on('load', function() {
            $('#logout_modal').modal('show');
        });
    <%}%>
    function closelogout()
    {
        $('#logout_modal').modal('hide');
    }
</script>
</html>	