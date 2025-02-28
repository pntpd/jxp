<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.checkpoint.CheckPointInfo" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="checkpoint" class="com.web.jxp.checkpoint.CheckPoint" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 7, submtp = 48, ctp = 1;
            String per = "N", addper = "N", editper = "N", deleteper = "N";
            if (session.getAttribute("LOGININFO") == null) {
    %>
    <jsp:forward page="/index1.jsp"/>
    <%
        } else {
            UserInfo uinfo = (UserInfo) session.getAttribute("LOGININFO");
            if (uinfo != null) {
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                deleteper = uinfo.getDeleteper() != null ? uinfo.getDeleteper() : "N";
            }
        }
        CheckPointInfo info = null;
        if (request.getAttribute("CHECKPOINT_DETAIL") != null) {
            info = (CheckPointInfo) request.getAttribute("CHECKPOINT_DETAIL");
        }
    %> 
    <head>
        <meta charset="utf-8">
        <title><%= checkpoint.getMainPath("title") != null ? checkpoint.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="cache-control" content="no-cache"/>
        <meta http-equiv="pragma" content="no-cache"/>
        <meta http-equiv="expires" content="0"/>
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">

        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/checkpoint.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/checkpoint/CheckPointAction.do" method="POST" onsubmit="return false;" styleClass="form-horizontal">
        <html:hidden property="checkpointId"/>
        <html:hidden property="doSave"/>
        <html:hidden property="doAddMore"/>
        <html:hidden property="doModify"/>
        <html:hidden property="doCancel"/>
        <html:hidden property="doView"/>
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
                            <div class="float-start back_arrow">
                                <a href="javascript: goback();"><img  src="../assets/images/back-arrow.png"/>
                                </a>Compliance Checkpoint
                            </div>
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
                                        <div class="col-lg-12">
                                            <div class="main-heading m_30">
                                                <div class="add-btn">
                                                    <h4>VIEW COMPLIANCE CHECKPOINT </h4>
                                                </div>
                                                <div class="edit_btn pull_right float-end">
                                                    <% if (editper.equals("Y") && info.getStatus() == 1) {%><a href="javascript: modifyForm('<%= info.getCheckpointId()%>');"><img src="../assets/images/edit.png"></a><%}%>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Compliance Checkpoint Code</label>
                                                    <span class="form-control" style="background-color: #F1F1F1"><%= info.getCode() != null || info.getCode().equals("") ? info.getCode() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Checkpoint Name</label>
                                                    <span class="form-control"><%= info.getName() != null || info.getName().equals("") ? info.getName() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Client</label>
                                                    <span class="form-control"><%= info.getClientname() != null || info.getClientname().equals("") ? info.getClientname() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Asset</label>
                                                    <span class="form-control"><%= info.getClientassetname() != null || !info.getClientassetname().equals("") ? info.getClientassetname() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Position</label>
                                                    <span class="form-control"><%= info.getPositionname() != null || !info.getPositionname().equals("") ? info.getPositionname() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Rank</label>
                                                    <span class="form-control"><%= info.getGrade() != null || !info.getGrade().equals("") ? info.getGrade() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Display Note</label>
                                                    <span class="form-control" style="height: 70px"><%= info.getDesc() != null || !info.getDesc().equals("") ? info.getDesc() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <% if( info.getFlag() == 1) {%><span class="check_mark"><i class="ion ion-md-checkmark" aria-hidden="true"></i></span><% } else {%> <span class="check_mark empty_box"> <i class="ion ion-md-square-outline" aria-hidden="true"></i></span><% } %>
                                                    <span class="ml_10"><b>This is a minimum check-point requirement</b></span>
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
            <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
            <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
            <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
            <script src="../assets/js/app.js"></script>	

            <script src="../assets/js/bootstrap-select.min.js"></script>
            <script src="../assets/js/bootstrap-datepicker.min.js"></script>
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
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
