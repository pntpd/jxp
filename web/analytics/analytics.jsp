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
        int mtp = 8, submtp = -1;
        if (session.getAttribute("LOGININFO") == null) {
%>
    <jsp:forward page="/index1.jsp"/>
<%
    }
    String url = "";
    if(request.getAttribute("DURL") != null)
    {
        url = (String) request.getAttribute("DURL");
        request.removeAttribute("DURL");
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
                                <span class="back_arrow">Analytics</span>
                            </div>
                            <div class="float-end">
                                <div class="toggled-off usefool_tool">                                    
                                </div>
                            </div>
                        </div>	
                    </div>
                    <div class="row">
                        <div class="col-md-12 col-xl-12">
                            <div class="search_export_area">
                                <div class="row">
                                    <div class="col-lg-8">
                                        <div class="row mb-3">
                                            <div class="col-lg-3">
                                                <div class="row">
                                                    <div class="col-sm-12 field_ic">
                                                        <html:select property="clientIdIndex" styleId="clientIdIndex" styleClass="form-select" onchange="javascript: setAssetDDL();" >
                                                            <html:optionsCollection filter="false" property="clients" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-3">
                                                <div class="row">
                                                    <div class="col-sm-12 field_ic">
                                                        <html:select property="assetIdIndex" styleId="assetIdIndex" styleClass="form-select">
                                                            <html:optionsCollection filter="false" property="assets" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-1">
                                                <a href="javascript: searchform();" class="go_btn">Go</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background mt_0">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <%if(!url.equals("")) {%><iframe src="<%=url%>" class="dashboard_iframe"/></iframe><% } %>
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
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script>
        <script src="../assets/js/echarts.js" type="text/javascript"></script>
        <script src="../assets/js/app.js"></script>  
        <script>			
            $(document).on('click', '.toggle-title', function() {
                $(this).parent()
                .toggleClass('toggled-on')
                .toggleClass('toggled-off');
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