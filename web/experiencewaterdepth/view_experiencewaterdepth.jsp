<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.experiencewaterdepth.ExperienceWaterDepthInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="experiencewaterdepth" class="com.web.jxp.experiencewaterdepth.ExperienceWaterDepth" scope="page"/>
<!doctype html>
<html lang="en">
<%
    try
    {
        int mtp = 7, submtp = 30;
        String per = "N", addper = "N", editper = "N", deleteper = "N";
        if (session.getAttribute("LOGININFO") == null)
        {
%>
            <jsp:forward page="/index1.jsp"/>
<%
        }
        else
        {
            UserInfo uinfo = (UserInfo) session.getAttribute("LOGININFO");
            if(uinfo != null)
            {
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                deleteper = uinfo.getDeleteper() != null ? uinfo.getDeleteper() : "N";
            }
        }
        ExperienceWaterDepthInfo info = null;
        if(request.getAttribute("EXPERIENCEWATERDEPTH_DETAIL") != null)
            info = (ExperienceWaterDepthInfo)request.getAttribute("EXPERIENCEWATERDEPTH_DETAIL");
%>  
<head>
    <meta charset="utf-8">
    <title><%= experiencewaterdepth.getMainPath("title") != null ? experiencewaterdepth.getMainPath("title") : "" %></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- App favicon -->
    <link rel="shortcut icon" href="../assets/images/favicon.png">
    <!-- Bootstrap Css -->
    <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
    <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
    <!-- Icons Css -->
    <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
    <!-- App Css-->
    <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
    <link href="../assets/css/style.css" rel="stylesheet" type="text/css"/>
    <script src="../jsnew/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="../jsnew/experiencewaterdepth.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
<html:form action="/experiencewaterdepth/ExperienceWaterDepthAction.do" onsubmit="return false;" styleClass="form-horizontal">
<html:hidden property="doCancel"/>
<html:hidden property="search"/>
    <!-- Begin page -->
    <div id="layout-wrapper">
        <%@include file="../header.jsp" %>
        <%@include file="../sidemenu.jsp" %>
        <!-- Start right Content -->
        <div class="main-content">
            <div class="page-content">
                <div class="row head_title_area">
                    <div class="col-md-12 col-xl-12">
                        <div class="float-start"><a href="javascript: goback();" class="back_arrow"><img  src="../assets/images/back-arrow.png"/> View Water Depth</a></div>
                        <div class="float-end">                            
                            <div class="toggled-off usefool_tool">
                                <div class="toggle-title">
                                    <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                    <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>

                                </div>
                                <!-- end toggle-title --->
                                <div class="toggle-content">
                                    <h4>Useful Tools</h4>
                                    <%@include file ="../shortcutmenu.jsp"%>
                                </div>
                            </div>
                        </div>
                    </div>	
                </div>
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-12 col-xl-12">
                            <div class="body-background">
                                <div class="row d-none1">  
<%
                                    if(info != null)
                                    {
%>
                                    <div class="row col-lg-12">
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                            <label class="form_label">Unit Measurement</label>
                                            <span class="form-control"><%= (info.getUnitMeasurement() != null && !info.getUnitMeasurement().equals("")) ? info.getUnitMeasurement() : "&nbsp;" %></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                            <label class="form_label">No. of Days</label>
                                            <span class="form-control"><%= (info.getDepth() != null && !info.getDepth().equals("")) ? info.getDepth() : "&nbsp;" %></span>
                                        </div>
                                        
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                            <label class="form_label">Status</label>
                                            <span class="form-control"><%= experiencewaterdepth.getStatusById(info.getStatus()) %></span>
                                        </div>
                                    </div>                                    
<%
                                    }
%>
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
