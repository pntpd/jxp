<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="user" class="com.web.jxp.user.User" scope="page"/>
<jsp:useBean id="dashboard" class="com.web.jxp.dashboard.Ticket" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = -2, submtp = -2;
            if (session.getAttribute("LOGININFO") == null) {
    %>
    <jsp:forward page="/index1.jsp"/>
    <%
        }
        String mess = "Not authorized.";
        if (request.getAttribute("AUTHMESSAGE") != null) {
            mess = (String) request.getAttribute("AUTHMESSAGE");
            request.removeAttribute("AUTHMESSAGE");
        }

    %>
    <head>
        <meta charset="utf-8">
        <title><%= user.getMainPath("title") != null ? user.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <!-- Responsive Table css -->
        <link href="../assets/css/rwd-table.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/login.js"></script>
        <script type="text/javascript">
                    window.history.forward();
                    function noBack() {
                            window.history.forward();
                    }
        </script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <form action="" onsubmit="return false;" class="form-horizontal" method="POST" name="homeForm">
            <!-- Begin page -->
            <div id="layout-wrapper">
                <%@include file ="../header.jsp"%>
                <%@include file ="../sidemenu.jsp"%>
                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12">
                                <div class="float-start">
                                    <a href="javascript:;" class="back_arrow"> Not Authorized</a>
                                </div>
                                <div class="float-end">                                
                                    <div class="toggled-off usefool_tool">
                                        <div class="toggle-title">
                                        </div>
                                    </div>
                                </div>
                            </div>	
                        </div>
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-md-12 col-xl-12">
                                    <div class="body-background">
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <div class="alert deleted-msg alert-dismissible fade show">
                                                    <%= mess%>
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
        </form>  
    </body>
    <%
        } catch (Exception e) {
            e.printStackTrace();
        }
    %>
</html>