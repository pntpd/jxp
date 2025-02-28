<%@page language="java" %>
<%@page import="com.web.jxp.base.*" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<jsp:useBean id="user" class="com.web.jxp.user.User" scope="page"/>
<html lang="en">
<%
    String mess = "";
    if (session.getAttribute("30DAYS") != null)
    {
        mess = (String)session.getAttribute("30DAYS");
        session.removeAttribute("30DAYS");
    }
%>
<head>
<meta charset="utf-8" />
<title><%= user.getMainPath("title") != null ? user.getMainPath("title") : "" %></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport" />
    <meta content="" name="description" />
    <meta content="" name="author" />
    <link href="../images/fav_icon.png" rel="shortcut icon" />
    <link href="../icons/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <link href="../simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
    <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="../css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
    <link href="../css/custom.css" rel="stylesheet" type="text/css" />
    <link href="../css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
    <link href="../css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
    <link href="../css/login.min.css" rel="stylesheet" type="text/css" />
    <link href="../css/layout.min.css" rel="stylesheet" type="text/css" />
    <link href="../css/darkblue.min.css" rel="stylesheet" type="text/css" id="style_color" />
    <script type="text/javascript" src="../js/active_date.js"></script>
<script type="text/javascript" src="../jsnew/common.js"></script>
<script type="text/javascript" src="../jsnew/user.js"></script>
</head>
<body class=" login" onload="javascript:DrawCaptcha();">
    <div class="menu-toggler sidebar-toggler"></div>
    <div class="logo"></div>
    <div class="content" style="padding:20px 10px;">
        <html:form action="/user/UserAction.do" onsubmit="return false;">
            <div class="content null_bottom">
                <h4 class="form-title font-red"><b>Personalize Password</b></h4>
                <%if(mess != null && mess.equals("YES")){%>
                <div class="alert alert-danger" id="messDiv">
                    <span>Your password has not been updated since 30 days or more. Please create a new personalized password in order to login.</span>
                </div>
                <%}%>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Current Password</label>
                    <input type="password" name="chpassword" value="" placeholder="Current Password * " class="form-control" maxlength="20"/>
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">New Password</label>
                    <input type="password" name="chnewpassword" value="" placeholder="New Password * " class="form-control" maxlength="20"/>
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">Confirm New Password</label>
                    <input type="password" name="chnewpasswordconfirm" value="" placeholder="Confirm New Password * " class="form-control" maxlength="20"/>
                </div>
                <div class="form-group margin-top-20">
                    <input placeholder="Enter the verification code * " type="text" id="txtInput" onmousedown="DisableRightClick(event)"  onfocus="disablePaste('txtInput')" class="captcha_input form-control ver_code" maxlength="20" name="cap" onkeypress="javascript: genericfunctionOnKeyPress1(event);">
                    <input type="text" onmousedown="return false" onfocus="javascript: disableCopy('txtCaptcha')" readonly id="txtCaptcha" class="form-control ver_inp" name="txtCaptcha">
                    <input type="button" id="btnrefresh" class="refreshcaptcha" onclick="DrawCaptcha();">
                </div>        
                <div class="form-actions center_align" id="suDiv">
                    <a href="javascript: submitpersonaliseForm();" class="btn green uppercase" onkeypress="javascript: genericfunctionOnKeyPress1(event);">Submit</a>
                    <a href="javascript: resetregisterForm();" id="register-back-btn" class="btn default">Reset</a>
                </div>
            </div>
                </div>
                <div class="copyright" style="text-align:center;">
                    2017 &copy; Copyright, All rights reserved. <br/>
                    <a class="foot_anch" target="_blank" title="OCS APP" href="#"> OCS APP</a> 
                </div>
            <script src="../js/jquery.min.js" type="text/javascript"></script>
            <script src="../js/bootstrap.min.js" type="text/javascript"></script>
            <script src="../js/jquery.slimscroll.min.js" type="text/javascript"></script>
            <script src="../js/bootstrap-datepicker.min.js" type="text/javascript"></script>
            <script src="../js/app.min.js" type="text/javascript"></script>
            <script src="../js/components-date-time-pickers.min.js" type="text/javascript"></script>
            <script src="../js/layout.min.js" type="text/javascript"></script>
        </html:form>
    
</body>

</html>