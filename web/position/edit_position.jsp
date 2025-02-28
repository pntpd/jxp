<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.position.PositionInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="position" class="com.web.jxp.position.Position" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 7, submtp = 12, ctp = 1;
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
        <title><%= position.getMainPath("title") != null ? position.getMainPath("title") : "" %></title>
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
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed" onload="javascript: loadfun();">
        <html:form action="/position/PositionAction.do" onsubmit="return false;" styleClass="form-horizontal">
            <html:hidden property="positionId"/>
            <html:hidden property="doSave"/>
            <html:hidden property="doCancel"/>
            <html:hidden property="search"/>   
            <html:hidden property="doModify"/>
            <html:hidden property="doView"/>
            <html:hidden property="doBenefitsList"/>
            <html:hidden property="doViewAssessmentList"/>
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
                                    <c:choose>
                                        <c:when test="${positionForm.positionId <= 0}">
                                            <a href="javascript: goback();"><img  src="../assets/images/back-arrow.png"/>
                                            </a>
                                            <span>Add Position</span>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="javascript: goback();"><img  src="../assets/images/back-arrow.png"/>
                                            </a>
                                            <span>Edit Position</span>
                                        </c:otherwise>
                                    </c:choose>

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
                                    <c:when test="${positionForm.positionId <= 0}">
                                        <ul class='nav nav-tabs nav-tabs-custom disable_menu' id='tab_menu'>
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
                                        </ul>
                                    </c:when>
                                    <c:otherwise>
                                        <ul class='nav nav-tabs nav-tabs-custom' id='tab_menu'>
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
                                            <div class="col-lg-12">
                                                <% if(!message.equals("")) {%><div class="sbold <%=clsmessage%>">
                                                    <%=message%>
                                                </div><% } %>
                                                <div class="main-heading m_30">
                                                    <div class="add-btn">
                                                        <h4>BASIC DETAILS </h4>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">Position Title</label>
                                                        <html:text property="positiontitle" styleId="positiontitle" styleClass="form-control" maxlength="100"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("positiontitle").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">Asset Type</label>
                                                        <html:select property="assettypeId" styleId="assettypeId" styleClass="form-select">
                                                            <html:optionsCollection filter="false" property="assettype" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">Rank</label>
                                                        <html:select property="gradeId" styleId="gradeId" styleClass="form-select">
                                                            <html:optionsCollection filter="false" property="grades" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">Crew Rotation Duration(in days)</label>
                                                        <html:select property="rotationId" styleId="rotationId" styleClass="form-select">
                                                            <html:optionsCollection filter="false" property="crewrotation" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">Rotation Over Stay Limit(in days)</label>
                                                        <html:text property="overstaystart" styleId="overstaystart" styleClass="form-control" maxlength="3" onkeypress="return allowPositiveNumber1(event);"/>
                                                        <script type="text/javascript">
                                                            document.getElementById("overstaystart").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">Hours of Work</label>
                                                        <html:select property="hoursofworkId" styleId="hoursofworkId" styleClass="form-select">
                                                            <html:optionsCollection filter="false" property="hoursofwork" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">Languages (optional)</label>
                                                        <html:select property="languageId" styleId="languageId" styleClass="form-select">
                                                            <html:optionsCollection filter="false" property="language" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">Gender (optional)</label>
                                                        <html:select styleClass="form-select" property="gender">
                                                            <html:option value="">- Select -</html:option>
                                                            <html:option value="Male">Male</html:option>
                                                            <html:option value="Female">Female</html:option>
                                                        </html:select>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">Nationality (optional)</label>
                                                        <html:select property="nationalityId" styleId="nationalityId" styleClass="form-select">
                                                            <html:optionsCollection filter="false" property="nationality" label="ddlLabel1" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">Currency</label>
                                                        <html:select property="currencyId" styleId="currencyId" styleClass="form-select">
                                                            <html:optionsCollection filter="false" property="currency" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">Remuneration(USD)</label>
                                                        <div class="row">
                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                                <div class="input-group">
                                                                    <html:text property="renumerationmin" styleId="renumerationmin" styleClass="form-control text-center" maxlength="10" onkeypress="return allowPositiveNumber(event);"/>
                                                                    <script type="text/javascript">
                                                                        document.getElementById("renumerationmin").setAttribute('placeholder', 'Min');
                                                                    </script>
                                                                    <span class="input-group-addon">:</span>
                                                                    <html:text property="renumerationmax" styleId="renumerationmax" styleClass="form-control text-center" maxlength="10" onkeypress="return allowPositiveNumber(event);"/>
                                                                    <script type="text/javascript">
                                                                        document.getElementById("renumerationmax").setAttribute('placeholder', 'Max');
                                                                    </script>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">Minimum Qualification</label>
                                                        <html:select property="minqualificationId" styleId="minqualificationId" styleClass="form-select">
                                                            <html:optionsCollection filter="false" property="minqualification" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">Qualification Type</label>
                                                        <html:select property="qualificationtypeId" styleId="qualificationtypeId" styleClass="form-select">
                                                            <html:optionsCollection filter="false" property="qualificationtype" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">General Work Experience Range</label>
                                                        <div class="row">
                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                                <div class="input-group">
                                                                    <html:text property="genworkexprangemin" styleId="genworkexprangemin" styleClass="form-control text-center" maxlength="10" onkeypress="return allowPositiveNumber(event);"/>
                                                                    <script type="text/javascript">
                                                                        document.getElementById("genworkexprangemin").setAttribute('placeholder', 'Min');
                                                                    </script>
                                                                    <span class="input-group-addon">:</span>
                                                                    <html:text property="genworkexprangemax" styleId="genworkexprangemax" styleClass="form-control text-center" maxlength="10" onkeypress="return allowPositiveNumber(event);"/>
                                                                    <script type="text/javascript">
                                                                        document.getElementById("genworkexprangemax").setAttribute('placeholder', 'Max');
                                                                    </script>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">Position Work Experience Range</label>
                                                        <div class="row">
                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                                <div class="input-group">
                                                                    <html:text property="posworkexprangemin" styleId="posworkexprangemin" styleClass="form-control text-center" maxlength="10" onkeypress="return allowPositiveNumber(event);"/>
                                                                    <script type="text/javascript">
                                                                        document.getElementById("posworkexprangemin").setAttribute('placeholder', 'Min');
                                                                    </script>
                                                                    <span class="input-group-addon">:</span>
                                                                    <html:text property="posworkexprangemax" styleId="posworkexprangemax" styleClass="form-control text-center" maxlength="10" onkeypress="return allowPositiveNumber(event);"/>
                                                                    <script type="text/javascript">
                                                                        document.getElementById("posworkexprangemax").setAttribute('placeholder', 'Max');
                                                                    </script>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-8 col-md-8 col-sm-8 col-4 form_group">
                                                        <label class="form_label">Description</label>
                                                        <html:text property="description" styleId="description" styleClass="form-control"  maxlength="1000"/>                              
                                                        <script type="text/javascript">
                                                            document.getElementById("description").setAttribute('placeholder', '');
                                                        </script>
                                                    </div>
                                                    <div class="col-lg-8 col-md-8 col-sm-8 col-4 form_group add_more_list">
                                                        <label class="form_label">Add Similar/Alternative Position Names (Apply "Enter" to separate tags)</label>
                                                        <div class="full_width add_similar">
                                                            <ul>
                                                                <li id='addli0'>
                                                                    <a id='adda0' href="javascript: addfield('0');" class="add_field_button"><img src="../assets/images/add_more.png"/></a>																
                                                                </li>
                                                                <li id='addli1' style='display: none;'>
                                                                    <a id='adda1' href="javascript: addfield('1');" class="add_field_button"><img src="../assets/images/add_more.png"/></a>
                                                                    <div>
                                                                        <html:text property="position1" styleId="position1" styleClass="form-control" maxlength="100" onkeyup="javascript : handleKeyPosition(event, '1');"/>                              
                                                                        <script type="text/javascript">
                                                                            document.getElementById("position1").setAttribute('placeholder', '');
                                                                        </script>
                                                                        <a id='dela1' href="javascript: delfield('1');" class="remove_field"><img src="../assets/images/remove.png"></a>
                                                                    </div>
                                                                </li>
                                                                <li id='addli2' style='display: none;'>
                                                                    <a id='adda2' name='position2' href="javascript: addfield('2');" class="add_field_button"><img src="../assets/images/add_more.png"/></a>
                                                                    <div>
                                                                        <html:text property="position2" styleId="position2" styleClass="form-control" maxlength="100" onkeyup="javascript : handleKeyPosition(event, '2');"/>                              
                                                                        <script type="text/javascript">
                                                                            document.getElementById("position2").setAttribute('placeholder', '');
                                                                        </script>
                                                                        <a id='dela2' href="javascript: delfield('2');" class="remove_field"><img src="../assets/images/remove.png"></a>
                                                                    </div>
                                                                </li>
                                                                <li id='addli3' style='display: none;'>																
                                                                    <div>
                                                                        <html:text property="position3" styleId="position3" styleClass="form-control" maxlength="100"/>                              
                                                                        <script type="text/javascript">
                                                                            document.getElementById("position3").setAttribute('placeholder', '');
                                                                        </script>
                                                                        <a id='dela3' href="javascript: delfield('3');" class="remove_field"><img src="../assets/images/remove.png"></a>
                                                                    </div>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="submitdiv">
                                                <a href="javascript:submitForm();" class="save_btn"><img src="../assets/images/save.png"> Save
                                                </a>
                                                <c:choose>
                                                    <c:when test="${positionForm.positionId <= 0}">
                                                        <a href="javascript: goback();" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="javascript: openTab('1');" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
                                                    </c:otherwise>
                                                </c:choose>
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

            <script src="../assets/js/bootstrap-select.min.js"></script>
            <script src="../assets/js/bootstrap-datepicker.min.js"></script>
            <script src="../assets/js/sweetalert2.min.js"></script>
            <script>
                                                                            $(document).on('click', '.toggle-title', function () {
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
                        format: "dd-M-yyyy",
                        autoclose: "true",
                        orientation: "bottom"
                    });
                });
            </script>
            <script>
                function handleKeyPosition(e, no)
                {
                    var key = e.keyCode || e.which;
                    if (key === 13)
                    {
                        e.preventDefault();
                        addfield(no);
                    }
                }

                function addfield(srno)
                {
                    document.getElementById("addli" + (Number(srno) + 1)).style.display = "";
                    document.getElementById("adda" + srno).style.display = "none";
                    if (document.getElementById("dela" + srno))
                        document.getElementById("dela" + srno).style.display = "none";
                    document.getElementById("position" + (Number(srno) + 1)).focus();
                }

                function delfield(srno)
                {
                    document.getElementById("addli" + (eval(srno) - 1)).style.display = "";
                    document.getElementById("adda" + (eval(srno) - 1)).style.display = "";
                    if (document.getElementById("dela" + (eval(srno) - 1)))
                        document.getElementById("dela" + (eval(srno) - 1)).style.display = "";
                    document.getElementById("addli" + srno).style.display = "none";
                    if (document.getElementById("position" + srno))
                        document.getElementById("position" + srno).value = "";

                }
            </script>
            <script>
                function loadfun()
                {
                    if (document.getElementById("position3").value != "")
                    {
                        document.getElementById("addli3").style.display = '';
                        document.getElementById("addli2").style.display = '';
                        document.getElementById("addli1").style.display = '';
                        document.getElementById("dela2").style.display = 'none';
                        document.getElementById("dela1").style.display = 'none';
                        document.getElementById("addli0").style.display = 'none';
                        document.getElementById("adda0").style.display = 'none';
                        document.getElementById("adda1").style.display = 'none';
                        document.getElementById("adda2").style.display = 'none';
                    } else if (document.getElementById("position2").value != "")
                    {
                        document.getElementById("addli3").style.display = 'none';
                        document.getElementById("addli2").style.display = '';
                        document.getElementById("addli1").style.display = '';

                        document.getElementById("dela3").style.display = '';
                        document.getElementById("dela2").style.display = '';
                        document.getElementById("dela1").style.display = 'none';
                        document.getElementById("addli0").style.display = 'none';
                        document.getElementById("adda0").style.display = 'none';
                        document.getElementById("adda1").style.display = 'none';
                        document.getElementById("adda2").style.display = '';
                    } else if (document.getElementById("position1").value != "")
                    {
                        document.getElementById("addli3").style.display = 'none';
                        document.getElementById("addli2").style.display = 'none';
                        document.getElementById("addli1").style.display = '';

                        document.getElementById("dela1").style.display = '';
                        document.getElementById("addli0").style.display = 'none';
                        document.getElementById("adda0").style.display = 'none';
                        document.getElementById("adda1").style.display = '';
                    } else
                    {
                        document.getElementById("addli3").style.display = 'none';
                        document.getElementById("addli2").style.display = 'none';
                        document.getElementById("addli1").style.display = 'none';
                        document.getElementById("adda0").style.display = '';
                    }

                    if (Number(document.getElementById("renumerationmin").value) <= 0) {
                        document.getElementById("renumerationmin").value = "";
                    }
                    if (Number(document.getElementById("renumerationmax").value) <= 0) {
                        document.getElementById("renumerationmax").value = "";
                    }

                    if (Number(document.positionForm.positionId.value) <= 0) {
                        if (Number(document.getElementById("genworkexprangemin").value) <= 0) {
                            document.getElementById("genworkexprangemin").value = "";
                        }
                        if (Number(document.getElementById("genworkexprangemax").value) <= 0) {
                            document.getElementById("genworkexprangemax").value = "";
                        }
                        if (Number(document.getElementById("posworkexprangemin").value) <= 0) {
                            document.getElementById("posworkexprangemin").value = "";
                        }
                        if (Number(document.getElementById("posworkexprangemax").value) <= 0) {
                            document.getElementById("posworkexprangemax").value = "";
                        }
                    }
                }
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
