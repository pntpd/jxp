<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.position.PositionInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="position" class="com.web.jxp.position.Position" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 7, submtp = 12, ctp = 1;
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
        PositionInfo info = null;
        if (request.getAttribute("POSITION_DETAIL") != null) {
            info = (PositionInfo) request.getAttribute("POSITION_DETAIL");
        }

            String thankyou = "no";
            if(request.getAttribute("POSITIONSAVEMODEL") != null)
            {
                thankyou = (String)request.getAttribute("POSITIONSAVEMODEL");
                request.removeAttribute("POSITIONSAVEMODEL");
            }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= position.getMainPath("title") != null ? position.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        <link href="../assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/position.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/position/PositionAction.do" method="POST" onsubmit="return false;" styleClass="form-horizontal">
        <html:hidden property="positionId"/>
        <html:hidden property="doSave"/>
        <html:hidden property="doModify"/>
        <html:hidden property="doCancel"/>
        <html:hidden property="doView"/>
        <html:hidden property="search"/>
        <html:hidden property="doBenefitsList"/>
        <html:hidden property="doViewAssessmentList"/>
        <!-- Begin page -->
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content tab_panel">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow">
                                <a href="javascript: goback();"><img  src="../assets/images/back-arrow.png"/>
                                </a>
                                <span>View Position</span>
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

                        <div class="col-md-12 col-xl-12 tab_head_area">
                            <ul class='nav nav-tabs nav-tabs-custom' id='tab_menu'>
                                <c:choose>
                                    <c:when test="${positionForm.positionId <= 0}">
                                        <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 1) ? " active" : ""%>'>
                                            <a class='nav-link' href="javascript:addForm();">
                                                <span class='d-none d-md-block'>General</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                            </a>
                                        </li>
                                        <li id='list_menu2' class='list_menu2 nav-item <%=(ctp == 2) ? " active" : ""%>'>
                                            <a class='nav-link' href="javascript:void(0);">
                                                <span class='d-none d-md-block'>Benefits</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
                                            </a>
                                        </li>
                                        <li id='list_menu3' class='list_menu3 nav-item <%=(ctp == 3) ? " active" : ""%>'>
                                            <a class='nav-link' href="javascript:void(0);">
                                                <span class='d-none d-md-block'>Assessment</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
                                            </a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 1) ? " active" : ""%>'>
                                            <a class='nav-link' href="javascript:openTab('1');">
                                                <span class='d-none d-md-block'>General</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                            </a>
                                        </li>
                                        <li id='list_menu2' class='list_menu2 nav-item <%=(ctp == 2) ? " active" : ""%>'>
                                            <a class='nav-link' href="javascript:openTab('2');">
                                                <span class='d-none d-md-block'>Benefits</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
                                            </a>
                                        </li>
                                        <li id='list_menu3' class='list_menu3 nav-item <%=(ctp == 3) ? " active" : ""%>'>
                                            <a class='nav-link' href="javascript:openTab('3');">
                                                <span class='d-none d-md-block'>Assessment</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
                                            </a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </ul>
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
                                                    <h4>BASIC DETAILS </h4>
                                                </div>
                                                <div class="edit_btn pull_right float-end">
                                                    <% if (editper.equals("Y") && info.getStatus() == 1) {%><a href="javascript: modifyForm('<%= info.getPositionId()%>');"><img src="../assets/images/edit.png"></a><%}%>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Position Title</label>
                                                    <span class="form-control"><%= info.getPositiontitle() != null ? info.getPositiontitle() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Asset Type</label>
                                                    <span class="form-control"><%= info.getAsset() != null ? info.getAsset() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Rank</label>
                                                    <span class="form-control"><%= info.getGrade() != null ? info.getGrade() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Crew Rotation Duration(in days)</label>
                                                    <span class="form-control"><%= info.getRotation() != null ? info.getRotation() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Rotation Over Stay Limit(in days)</label>
                                                    <span class="form-control"><%= info.getOverstaystart() %></span>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Hours of Work</label>
                                                    <span class="form-control"><%= info.getHoursofwork() != null ? info.getHoursofwork() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Languages</label>
                                                    <span class="form-control"><%= info.getLanguage() != null && !info.getLanguage().equals("") ?  info.getLanguage() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Gender</label>
                                                    <span class="form-control"><%= info.getGender() != null  && !info.getGender().equals("") ? info.getGender() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Nationality</label>
                                                    <span class="form-control"><%= info.getNationality() != null && !info.getNationality().equals("") ? info.getNationality() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Currency</label>
                                                    <span class="form-control"><%= info.getCurrency() != null && !info.getCurrency().equals("") ? info.getCurrency() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Remuneration(USD)</label>
                                                    <div class="row">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                            <div class="input-group">
                                                                <span class="form-control text-center"><%=info.getRenumerationmin()%></span>
                                                                <span class="input-group-addon">:</span>
                                                                <span class="form-control text-center"><%=info.getRenumerationmax()%></span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Minimum Qualification</label>
                                                    <span class="form-control"><%= info.getMinqualification() != null && !info.getMinqualification().equals("")? info.getMinqualification() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Qualification Type</label>
                                                    <span class="form-control"><%= info.getQualificationtype() != null && !info.getQualificationtype().equals("") ? info.getQualificationtype() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">General Work Experience Range</label>
                                                    <div class="row">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                            <div class="input-group">
                                                                <span class="form-control text-center"><%=info.getGenworkexprangemin()%></span>
                                                                <span class="input-group-addon">:</span>
                                                                <span class="form-control text-center"><%=info.getGenworkexprangemax()%></span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Position Work Experience Range</label>
                                                    <div class="row">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                            <div class="input-group">
                                                                <span class="form-control text-center"><%=info.getPosworkexprangemin()%></span>
                                                                <span class="input-group-addon">:</span>
                                                                <span class="form-control text-center"><%=info.getPosworkexprangemax()%></span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-8 col-md-8 col-sm-8 col-4 form_group">
                                                    <label class="form_label">Description</label>
                                                    <span class="form-control"><%= info.getDescription() != null && !info.getDescription().equals("") ? info.getDescription() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-lg-8 col-md-8 col-sm-8 col-4 form_group add_more_list">
                                                    <label class="form_label">Similar/Alternative Position Names</label>
                                                    <%
                                                        if (info.getPosition1() == null || "".equals(info.getPosition1())) {
                                                        } else {
                                                    %>
                                                    <span class="form-control m_15"><%=info.getPosition1()%></span>
                                                    <%
                                                        }
                                                        if (info.getPosition2() == null || "".equals(info.getPosition2())) {
                                                        } else {
                                                    %>
                                                    <span class="form-control m_15"><%=info.getPosition2()%></span>
                                                    <%
                                                        }
                                                        if (info.getPosition3() == null || "".equals(info.getPosition3())) {
                                                        } else {
                                                    %>
                                                    <span class="form-control"><%=info.getPosition3()%></span>
                                                    <%
                                                        }
                                                    %>
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

            <%@include file ="../footer.jsp"%><div id="thank_you_modal" class="modal fade thank_you_modal" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <h2>Thank You!</h2>
                                    <center><img src="../assets/images/thank-you.png"></center>
                                    <h3>Position Added</h3>
                                    <p>Go ahead and link benefits to this position!</p>
                                    <a href="javascript:openTab('2');" class="msg_button" style="text-decoration: underline;">Benefits</a>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <!-- JAVASCRIPT -->
            <script src="../assets/libs/jquery/jquery.min.js"></script>		
            <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
            <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
            <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
            <script src="../assets/js/app.js"></script>	
            <script src="../assets/js/bootstrap-select.min.js"></script>
            <script src="../assets/js/bootstrap-datepicker.min.js"></script>
            <script src="../assets/js/sweetalert2.min.js"></script>
            <% if(thankyou.equals("yes")){%>
            <script type="text/javascript">
        $(window).on('load', function () {
            $('#thank_you_modal').modal('show');
        });
            </script>
            <%}%>
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
