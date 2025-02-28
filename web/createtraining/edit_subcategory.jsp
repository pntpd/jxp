<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.createtraining.CreatetrainingInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="createtraining" class="com.web.jxp.createtraining.Createtraining" scope="page"/>
<!doctype html>
<html lang="en">
<%
    try
    {
        int mtp = 7, submtp = 60, ctp = 2;
        if (session.getAttribute("LOGININFO") == null)
        {
%>
            <jsp:forward page="/index1.jsp"/>
<%
        }
        String message = "", clsmessage = "red_font";
        if (request.getAttribute("MESSAGE") != null)
        {
            message = (String)request.getAttribute("MESSAGE");
            request.removeAttribute("MESSAGE");
        }        
        if(message != null && (message.toLowerCase()).indexOf("success") != -1)
            clsmessage = "updated-msg";
%>
<head>
    <meta charset="utf-8">
    <title><%= createtraining.getMainPath("title") != null ? createtraining.getMainPath("title") : "" %></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- App favicon -->
    <link rel="shortcut icon" href="../assets/images/favicon.png">
    <!-- Bootstrap Css -->
    <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
    <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
    <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
    <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
    <!-- Icons Css -->
    <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
    <!-- App Css-->
    <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
    <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
    <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
    <script src="../jsnew/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="../jsnew/validation.js"></script>
    <script type="text/javascript" src="../jsnew/createtraining.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
<html:form action="/createtraining/CreatetrainingAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
    <html:hidden property="categoryId"/>
    <html:hidden property="subcategoryId"/>
    <html:hidden property="doSaveSubcategory"/>
    <html:hidden property="doCancel"/>
    <html:hidden property="doView"/>
    <html:hidden property="search"/>   
    <html:hidden property="subcategoryIdIndex"/>   
    <html:hidden property="doIndexCourse"/>   
    <html:hidden property="doIndexSubcategory"/>   
    <!-- Begin page -->
    <div id="layout-wrapper">
        <%@include file ="../header.jsp"%>
        <%@include file ="../sidemenu.jsp"%>
        <!-- Start right Content -->
        <div class="main-content">
            <div class="page-content tab_panel">
                <div class="row head_title_area">
                    <div class="col-md-12 col-xl-12">
                        <div class="float-start">
                            <a href="javascript: viewSubcategory();" class="back_arrow">
                            <img  src="../assets/images/back-arrow.png"/> 
                                <c:choose>
                                    <c:when test="${createtrainingForm.subcategoryId <= 0}">
                                        Add Create Trainings
                                    </c:when>
                                    <c:otherwise>
                                        Edit Create Trainings
                                    </c:otherwise>
                                </c:choose>
                            </a>
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
                                    <%@include file ="../shortcutmenu_edit.jsp"%>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 col-xl-12 tab_head_area">
                        <c:choose>
                            <c:when test="${createtrainingForm.subcategoryId <= 0}">
                                <ul class='nav nav-tabs nav-tabs-custom' id='tab_menu'>
                                    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 1) ? " active" : ""%>'>
                                        <a class='nav-link' href="javascript: showDetailcategory();">
                                            <span class='d-none d-md-block'>Category</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 2) ? " active" : ""%>'>
                                        <a class='nav-link' href="javascript: void(0);">
                                            <span class='d-none d-md-block'>Sub-Category</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 3) ? " active" : ""%>'>
                                        <a class='nav-link' href="javascript: void(0);">
                                            <span class='d-none d-md-block'>Courses</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                </ul>
                            </c:when>
                                <c:otherwise>
                                        <ul class='nav nav-tabs nav-tabs-custom' id='tab_menu'>
                                    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 1) ? " active" : ""%>'>
                                        <a class='nav-link' href="javascript: showDetailcategory();">
                                            <span class='d-none d-md-block'>Category</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 2) ? " active" : ""%>'>
                                        <a class='nav-link' href="javascript: viewSubcategory();">
                                            <span class='d-none d-md-block'>Sub-Category</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 3) ? " active" : ""%>'>
                                        <a class='nav-link' href="javascript: viewCourse('-1');">
                                            <span class='d-none d-md-block'>Courses</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                </ul>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-12 col-xl-12">
                            <div class="body-background">
                                <div class="row d-none1">
                                    <div class="row">
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Category Code</label>
                                            <html:text property="categorycode" styleId="categorycode" styleClass="form-control" maxlength="100" readonly="true" />
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Category Name</label>
                                            <html:text property="categoryname" styleId="categoryname" styleClass="form-control" maxlength="100" readonly="true" />
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Sub-category Name</label>
                                            <html:text property="name" styleId="name" styleClass="form-control" maxlength="100"/>
                                            <script type="text/javascript">
                                                document.getElementById("name").setAttribute('placeholder', '');
                                            </script>
                                        </div>
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                            <label class="form_label">Description</label>
                                            <html:textarea property="cdescription" styleId="cdescription" styleClass="form-control" rows="4"/>
                                            <script type="text/javascript">
                                                document.getElementById("cdescription").setAttribute('placeholder', '');
                                                document.getElementById("cdescription").setAttribute('maxlength', '1000');
                                            </script>
                                        </div>
                                    </div>


                                    <div class="row" id="submitdiv">
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                            <a href="javascript: submitsubcategoryForm();" class="save_btn"><img src="../assets/images/save.png"> Save</a>
                                        <a href="javascript: viewSubcategory();" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
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
    </div>
    <!-- END layout-wrapper -->
    <%@include file ="../footer.jsp"%>
    <!-- JAVASCRIPT -->
    <script src="../assets/libs/jquery/jquery.min.js"></script>		
    <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
    <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
    <script src="../assets/js/app.js"></script>	
    <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>
    <script src="../assets/js/bootstrap-select.min.js"></script>
    <script src="../assets/js/bootstrap-datepicker.min.js"></script>
    <script src="/jxp/assets/js/sweetalert2.min.js"></script>
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
