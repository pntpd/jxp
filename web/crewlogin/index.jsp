<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="crewlogin" class="com.web.jxp.crewlogin.Crewlogin" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            String email = "";
            if (session.getAttribute("CREWLOGIN") != null) {
                CrewloginInfo cinfo = (CrewloginInfo) session.getAttribute("CREWLOGIN");
                if (cinfo != null) {
                    email = cinfo.getEmailId() != null ? cinfo.getEmailId() : "";
                }
            }
            String surveyIds = request.getParameter("surveyId") != null && !request.getParameter("surveyId").equals("") ? request.getParameter("surveyId") : "";
            String crIds = request.getParameter("crId") != null && !request.getParameter("crId").equals("") ? request.getParameter("crId") : "";
            if (!surveyIds.equals("")) {
                surveyIds = crewlogin.decipher(surveyIds);
                crIds = crewlogin.decipher(crIds);
                if (!surveyIds.equals("")) {
                    try {
                        int surveyId = Integer.parseInt(surveyIds);
                        int crId = Integer.parseInt(crIds);
                        if (surveyId > 0) {
                            session.setAttribute("SURVEYID", "" + surveyId);
                            session.setAttribute("CRID", "" + crId);
                        }
                    } catch (Exception e) {
                    }
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
    <body data-sidebar="dark" data-keep-enlarged="true" class="ocs_feedback vertical-collpsed user_main_login user_login_bg">
    <html:form action="/crewlogin/CrewloginAction.do" onsubmit="return false;">
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
                                                                <div class="login-title">Crew Login</div>

                                                                <div class="form-group field-pdg">
                                                                    <label>Email</label>
                                                                    <div class="input-field">  <input type="text" name="emailId" class="form-control" placeholder="Enter Email" autocomplete="off" required maxlength="100" onfocus="if (this.hasAttribute('readonly')) {
                                                                                this.removeAttribute('readonly');
                                                                                this.blur();
                                                                                this.focus();
                                                                            }" readonly="readonly" value="<%=email%>"> </div>
                                                                </div>
                                                                <div class="form-group field-pdg">
                                                                    <label>Captcha</label>
                                                                    <div class="captcha_par user_captcha">
                                                                        <div class="d-flex">
                                                                            <a class="refreshcaptcha" id="btnrefresh" onclick="javascript: DrawCaptcha();"><i class="ion-md-refresh"></i></a>
                                                                            <input type="text" id="txtCaptcha" name="" readonly="true" class="captcha_text form-control" maxlength="20" placeholder="Please enter captch code">
                                                                        </div>	
                                                                    </div>
                                                                    <div class="form-group field-pdg">
                                                                        <div class="input-field">
                                                                            <input type="text" class="captcha_input form-control" name="cap" autocomplete="off" onkeypress="javascript: handleKeyLogin(event);" id="txtInput" placeholder="Enter captcha" maxlength="6" >
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <span class="text-center font_white" id="submitdiv">
                                                                    <a onclick="javascript: generateotpForm();" class="btn btn-block text-center my-3">Get OTP</a>
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
            </div>
        </div>
        <script src="../assets/js/sweetalert2.min.js"></script> 
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
    </html:form>
</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
