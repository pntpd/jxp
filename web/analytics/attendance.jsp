<%@page import="java.util.Stack"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="user" class="com.web.jxp.user.User" scope="page"/>
<jsp:useBean id="analytics" class="com.web.jxp.analytics.Analytics" scope="page"/>
<!doctype html>
<html lang="en">
<%
    try 
    {
        int mtp = 8, submtp = 100;
        if (session.getAttribute("LOGININFO") == null) {
%>
    <jsp:forward page="/index1.jsp"/>
<%
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
    <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
    <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
    <!-- Icons Css -->
    <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
    <!-- App Css-->
    <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
    <link href="/jxp/assets/css/minimal.css" rel="stylesheet" />
    <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="../jsnew/common.js"></script>
    <script type="text/javascript" src="../jsnew/analytics.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/analytics/AnalyticsAction.do" onsubmit="return false;" enctype="multipart/form-data">
        <html:hidden property="doCrew" />
        <div id="layout-wrapper">
            <%@include file ="../header.jsp"%>
            <%@include file ="../sidemenu.jsp"%>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start">
                                <span class="back_arrow">Attendance Report</span>
                            </div>                            
                        </div>	
                    </div>
                    <div class="row">
                        <div class="col-md-12 col-xl-12">
                            <div class="search_export_area">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="row mb-3">
                                            <div class="col-lg-12">
                                                <div class="row d-flex align-items-center">
                                                    <div class="col-sm-12">
                                                        <div class="row d-flex align-items-center">
                                                            <div class="col-lg-2 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">From Date</label>
                                                                <div class="input-daterange input-group">
                                                                    <input type="text" name="fromDate" value="" id="fromDate" class="form-control add-style wesl_dt date-add" placeholder="DD-MMM-YYYY">
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-2 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">To Date</label>
                                                                <div class="input-daterange input-group">
                                                                    <input type="text" name="toDate" value="" id="toDate" class="form-control add-style wesl_dt date-add" placeholder="DD-MMM-YYYY">
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-3 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Client</label>
                                                                <html:select property="clientId" styleId="clientIdIndex" styleClass="form-select" onchange="javascript: setAssetDDL2();" >
                                                                    <html:optionsCollection filter="false" property="clients" label="ddlLabel" value="ddlValue">
                                                                    </html:optionsCollection>
                                                                </html:select>
                                                            </div>
                                                            <div class="col-lg-3 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Asset</label>
                                                                <html:select property="assetId" styleId="assetIdIndex" styleClass="form-select">
                                                                    <html:optionsCollection filter="false" property="assets" label="ddlLabel" value="ddlValue">
                                                                    </html:optionsCollection>
                                                                </html:select>
                                                            </div> 
                                                            <div class="col-lg-1">
                                                                <a href="javascript: showattendance();" class="go_btn">Generate</a>
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
        <%@include file ="../footer.jsp"%>
        <script src="../assets/libs/jquery/jquery.min.js"></script>
        <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/app.js"></script> 
        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>   
        <script src="/jxp/assets/js/sweetalert2.min.js"></script>
        <script>			
            $(document).on('click', '.toggle-title', function() {
                $(this).parent()
                .toggleClass('toggled-on')
                .toggleClass('toggled-off');
            });
        </script>
        <script type="text/javascript">
                jQuery(document).ready(function () {
                    $(".kt-selectpicker").selectpicker();
                     $(".wesl_dt").datepicker({
                        todayHighlight: !0,
                        format: "dd/mm/yyyy",
                        autoclose: "true",
                        orientation: "bottom"
                    });
                });
           </script>
    </html:form>
</body>
<%
    } 
    catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>