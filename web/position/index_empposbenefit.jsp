<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.position.PositionInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="position" class="com.web.jxp.position.Position" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 7, submtp = 12, ctp = 2;
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

        ArrayList positionbenefit_list = new ArrayList();
        if (request.getAttribute("BENEFIT_LIST") != null) {
            positionbenefit_list = (ArrayList) request.getAttribute("BENEFIT_LIST");
        }
        int total = positionbenefit_list.size();
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
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/position.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/position/PositionAction.do" onsubmit="return false;" styleClass="form-horizontal">
            <html:hidden property="positionId"/>
            <html:hidden property="doSave"/>
            <html:hidden property="doCancel"/>
            <html:hidden property="search"/>   
            <html:hidden property="doModify"/>
            <html:hidden property="doView"/>
            <html:hidden property="benefitId"/>
            <html:hidden property="doBenefitsList"/>
            <html:hidden property="doDeleteBenefit"/>
            <html:hidden property="doAddBenefit"/>
            <html:hidden property="doModifyBenefit"/>
            <html:hidden property="doViewAssessmentList"/>
            <html:hidden property="positionBenefitId"/>
            <html:hidden property="statusBenifit"/>
            <!-- Begin page -->
            <div id="layout-wrapper">
                <%@include file ="../header.jsp"%>
                <%@include file ="../sidemenu.jsp"%>
                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content tab_panel">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12">
                                <div class="float-start back_arrow">
                                    <a href="javascript: goback();">
                                        <img src="../assets/images/back-arrow.png"/>
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
                                                <a class='nav-link' href="javascript:openTab('1');">
                                                    <span class='d-none d-md-block'>General</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                                </a>
                                            </li>
                                            <li id='list_menu2' class='list_menu2 nav-item <%=(ctp == 2) ? " active" : ""%>'>
                                                <a class='nav-link' href="javascript:openTab('1');">
                                                    <span class='d-none d-md-block'>Benefits</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
                                                </a>
                                            </li>
                                            <li id='list_menu3' class='list_menu3 nav-item <%=(ctp == 3) ? " active" : ""%>'>
                                                <a class='nav-link' href="javascript:openTab('1');">
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
                                    <div class="bg_top_space body-background">
                                        <div class="row d-none1">
                                            <div class="col-lg-12">
                                                <div class="main-heading m_30">
                                                    <div class="add-btn">
                                                        <h4>EMPLOYEE POSITION BENEFITS</h4>
                                                    </div>
                                                    <div class="edit_btn pull_right float-end">
                                                        <% if (editper.equals("Y")) {%>
                                                        <a href="javascript:modifybenefitForm();" class="mr_15"><img src="../assets/images/edit.png"></a>
                                                            <%}
                                                                if (addper.equals("Y")) {
                                                            %>
                                                        <a href="javascript:addbenefitForm();" class="add_btn"><i class="mdi mdi-plus"></i></a>
                                                            <%
                                                            }
                                                            %>
                                                    </div>
                                                </div>
                                                <div class="col-lg-12 all_client_sec" id="all_client">

                                                    <div class="table-responsive table-detail">
                                                        <table class="table table-striped mb-0">
                                                            <thead>
                                                                <tr>
                                                                    <th width="6%">Code</th>
                                                                    <th width="42%">Title</th>
                                                                    <th width="42%">Information</th>
                                                                    <th width="10%" class="text-right">Actions</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%
                                                                      if(total > 0)
                                                                                                                                                                     {
                                                                    PositionInfo info;
                                                                    for (int i = 0; i < total; i++) {
                                                                        info = (PositionInfo) positionbenefit_list.get(i);
                                                                        if (info != null) {
                                                                %>
                                                                <tr>
                                                                    <td><%= info.getCodenum()%></td>
                                                                    <td><%= info.getBenifitname() != null ? info.getBenifitname() : ""%></td>
                                                                    <td><%= info.getPositionbenefitinfo() != null ? info.getPositionbenefitinfo() : ""%></td>
                                                                    <td class="action_column">
                                                                        <span class="switch_bth">                                           
                                                                            <div class="form-check form-switch">
                                                                                <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault_<%=info.getPositionbenefitId()%>" <% if( info.getStatus() == 1){%>checked<% } if(!editper.equals("Y")) {%> disabled="true"<% }%> onclick="javascript: deleteBenefitForm('<%= info.getPositionbenefitId()%>', '<%= info.getStatus()%>');"/>
                                                                            </div>                                                                        
                                                                        </span>
                                                                    </td>
                                                                </tr>
                                                                <%
                                                                        }
                                                                    }
}else{
                                                                %>
                                                                <tr><td colspan='6'>No Employee Position Benefit available.</td></tr>
                                                                <%
}
                                                                %>
                                                            </tbody>
                                                        </table>		
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
                <script src="../assets/js/sweetalert2.min.js"></script>
                <script>
                // toggle class show hide text section
                $(document).on('click', '.toggle-title', function () {
                    $(this).parent()
                            .toggleClass('toggled-on')
                            .toggleClass('toggled-off');
                });
                </script>
                <script>
                    $(function () {
                        $("#upload_link_1").on('click', function (e) {
                            e.preventDefault();
                            $("#upload1:hidden").trigger('click');
                        });

                    });

                </script>


                <script type="text/javascript">
                    jQuery(document).ready(function () {
                        $(".kt-selectpicker").selectpicker();
                        $(".wesl_dt").datepicker({
                            todayHighlight: !0,
                            format: "dd-M-yyyy",
                            autoclose: "true",
                            orientation: "bottom"
                        });
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
