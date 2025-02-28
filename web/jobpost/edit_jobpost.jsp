<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.jobpost.JobPostInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="jobpost" class="com.web.jxp.jobpost.JobPost" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 2, submtp = 45, ctp = 1;
            if (session.getAttribute("LOGININFO") == null) {
    %>
    <jsp:forward page="/index1.jsp"/>
    <%
        }
        String message = "", clsmessage = "red_font", nationalityIds = "", languageIds = "", genderIds = "";
        if (request.getAttribute("MESSAGE") != null) {
            message = (String) request.getAttribute("MESSAGE");
            request.removeAttribute("MESSAGE");
        }
        if (message != null && (message.toLowerCase()).indexOf("success") != -1) {
            clsmessage = "updated-msg";
        }
        if (session.getAttribute("NATIONALITY_IDs") != null) {
            nationalityIds = (String) session.getAttribute("NATIONALITY_IDs");
        }
        if (session.getAttribute("LANGUAGE_IDs") != null) {
            languageIds = (String) session.getAttribute("LANGUAGE_IDs");
        }
        if (session.getAttribute("GENDER_IDs") != null) {
            genderIds = (String) session.getAttribute("GENDER_IDs");
        }

        ArrayList nationalist = jobpost.getNationalityList();
        ArrayList laguagelist = jobpost.getLanguageList();
    %>
    <head>
        <meta charset="utf-8">
        <title><%= jobpost.getMainPath("title") != null ? jobpost.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="cache-control" content="no-cache"/>
        <meta http-equiv="pragma" content="no-cache"/>
        <meta http-equiv="expires" content="0"/>
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

        <link rel="stylesheet" type="text/css" href="../autofill/jquery-ui.min.css" />  <!-- Autofill-->

        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/jobpost.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed" onload="javascript: loadfun();">
    <html:form action="/jobpost/JobPostAction.do" onsubmit="return false;" styleClass="form-horizontal">
        <html:hidden property="jobpostId"/>
        <html:hidden property="doSave"/>
        <html:hidden property="doCancel"/>
        <html:hidden property="search"/>   
        <html:hidden property="doModify"/>
        <html:hidden property="doView"/>
        <html:hidden property="doBenefitsList"/>
        <html:hidden property="doViewAssessmentList"/>
        <html:hidden property="statusIndex"/>
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
                                <a href="javascript: goback();"><img src="../assets/images/back-arrow.png"/></a>
                                <span>Job Posting</span>
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
                                <c:when test="${jobpostForm.jobpostId <= 0}">
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
                                                    <c:choose>
                                                        <c:when test="${jobpostForm.jobpostId <= 0}">
                                                            <h4>Create Job Post</h4>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <h4>Job Post</h4>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Client Name<span class="required">*</span></label>
                                                    <html:select property="clientId" styleId="clientId" styleClass="form-select" onchange="javascript: setAssetDDL();">
                                                        <html:optionsCollection filter="false" property="client" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Asset Name<span class="required">*</span></label>
                                                    <html:select property="clientassetId" styleId="clientassetddl" styleClass="form-select" onchange="javascript: setPositionDDL();">
                                                        <html:optionsCollection filter="false" property="clientasset" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Position<span class="required">*</span></label>
                                                    <html:hidden property="positionname"/>
                                                    <html:select property="positionId" styleId="positionddl" styleClass="form-select" onchange="javascript: setGradeDDL();" >
                                                        <html:optionsCollection filter="false" property="position" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Rank<span class="required">*</span></label>
                                                    <html:select property="gradeId" styleId="Gradeddl" styleClass="form-select">
                                                        <html:optionsCollection filter="false" property="grade" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Country<span class="required">*</span></label>
                                                    <html:select property="countryId" styleId="countryddl" styleClass="form-select" onchange="javascript: clearcity();">
                                                        <html:optionsCollection filter="false" property="country" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <div class="row flex-end align-items-end">
                                                    <div class="col-lg-10 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">City</label>
                                                    <html:hidden property="cityId" />
                                                    <html:text property="cityname" styleId="cityname" styleClass="form-control" maxlength="100" onblur="if (this.value == '') {
                                                                document.forms[0].cityId.value = '0';
                                                            }"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cityname").setAttribute('placeholder', '');
                                                        document.getElementById("cityname").setAttribute('autocomplete', 'off');
                                                    </script>
                                                </div>
                                                    <div class="col-lg-2 col-md-12 col-sm-12 col-12 form_group pd_left_null">
                                                            <a href="javascript:;" onclick="javascript: addtomaster('3')" class="add_btn"><i class="mdi mdi-plus"></i></a>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Experience (min, max in years)<span class="required">*</span></label>
                                                    <div class="row">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                            <div class="input-group">
                                                                <html:text property="experiencemin" styleId="experiencemin" styleClass="form-control text-center" maxlength="10" onkeypress="return allowPositiveNumber(event);"/>
                                                                <script type="text/javascript">
                                                                    document.getElementById("experiencemin").setAttribute('placeholder', 'Min');
                                                                </script>
                                                                <span class="input-group-addon">:</span>
                                                                <html:text property="experiencemax" styleId="experiencemax" styleClass="form-control text-center" maxlength="10" onkeypress="return allowPositiveNumber(event);"/>
                                                                <script type="text/javascript">
                                                                    document.getElementById("experiencemax").setAttribute('placeholder', 'Max');
                                                                </script>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <%
                                                    int nsize = nationalist.size();
                                                %>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Nationality</label>

                                                    <select name="nationality" id="nationalitymultiselect_dd" multiple="multiple" class="form-select">
                                                        <%
                                                            if (nsize > 0) {
                                                                for (int i = 0; i < nsize; i++) {
                                                                    JobPostInfo info = (JobPostInfo) nationalist.get(i);
                                                        %>
                                                        <option value="<%=info.getDdlValue()%>" <% if (jobpost.checkToStr(nationalityIds, info.getDdlValue() + "")) {%>selected<%}%>><%= (info.getDdlLabel())%> </option>
                                                        <%
                                                                }
                                                            }
                                                        %>
                                                    </Select>
                                                </div>
                                                <%
                                                    nsize = 0;
                                                    nsize = laguagelist.size();
                                                %>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Language</label>
                                                    <select name="language" id="languagemultiselect_dd" multiple="multiple" class="form-select">
                                                        <%
                                                            if (nsize > 0) {
                                                                for (int i = 0; i < nsize; i++) {
                                                                    JobPostInfo info = (JobPostInfo) laguagelist.get(i);
                                                        %>
                                                        <option value="<%=info.getDdlValue()%>" <% if (jobpost.checkToStr(languageIds, info.getDdlValue() + "")) {%>selected<%}%>><%= (info.getDdlLabel())%> </option>
                                                        <%
                                                                }
                                                            }
                                                        %>
                                                    </Select>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Gender</label>
                                                    <select name="gender" id="gendermultiselect_dd" multiple="multiple" class="form-select">
                                                        <option value="Male" <% if (jobpost.checkToStr(genderIds, "Male")) {%>selected<%}%>>Male</option>
                                                        <option value="Female" <% if (jobpost.checkToStr(genderIds, "Female")) {%>selected<%}%>>Female</option>
                                                    </Select>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Education Level<span class="required">*</span></label>
                                                    <html:select property="educationtypeId" styleId="educationtypeId" styleClass="form-select">
                                                        <html:optionsCollection filter="false" property="education" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-8 col-md-8 col-sm-8 col-4 form_group">
                                                    <label class="form_label">Description</label>
                                                    <html:text property="description" styleId="description" styleClass="form-control" maxlength="1000"/>                              
                                                    <script type="text/javascript">
                                                        document.getElementById("description").setAttribute('placeholder', '');
                                                    </script>
                                                </div>


                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">No. Of Openings<span class="required">*</span></label>
                                                    <html:text property="noofopening" styleId="noofopening" styleClass="form-control"  maxlength="10" onkeypress="return allowPositiveNumber1(event);"/>                              
                                                    <script type="text/javascript">
                                                        document.getElementById("noofopening").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Target Mobilization Date<span class="required">*</span></label>
                                                    <div class="input-daterange input-group">
                                                        <html:text property="targetmobdate" styleId="targetmobdate" styleClass="form-control add-style wesl_dt date-add" maxlength="11"/>                              
                                                        <script type="text/javascript">
                                                            document.getElementById("targetmobdate").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                        </script>
                                                    </div>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Tenure (in days)</label>
                                                    <html:text property="tenure" styleId="tenure" styleClass="form-control" maxlength="10" onkeypress="return allowPositiveNumber1(event);"/>                              
                                                    <script type="text/javascript">
                                                        document.getElementById("tenure").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Day Rate Currency<span class="required">*</span></label>
                                                    <div class="row">
                                                        <div class="col-lg-4">
                                                            <html:select property="currencyId" styleId="currencyId" styleClass="form-select">
                                                                <html:optionsCollection filter="false" property="currency" label="ddlLabel" value="ddlValue">
                                                                </html:optionsCollection>
                                                            </html:select>
                                                        </div>
                                                        <div class="col-lg-8">
                                                            <html:text property="dayratevalue" styleId="dayratevalue" styleClass="form-control text-center" maxlength="10" onkeypress="return allowPositiveNumber(event);"/>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Remuneration (in USD)</label>
                                                    <div class="row">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                            <div class="input-group">
                                                                <html:text property="remunerationmin" styleId="remunerationmin" styleClass="form-control text-center" maxlength="10" onkeypress="return allowPositiveNumber(event);"/>
                                                                <script type="text/javascript">
                                                                    document.getElementById("remunerationmin").setAttribute('placeholder', 'Min');
                                                                </script>
                                                                <span class="input-group-addon">:</span>
                                                                <html:text property="remunerationmax" styleId="remunerationmax" styleClass="form-control text-center" maxlength="10" onkeypress="return allowPositiveNumber(event);"/>
                                                                <script type="text/javascript">
                                                                    document.getElementById("remunerationmax").setAttribute('placeholder', 'Max');
                                                                </script>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Work Hours (per day)</label>
                                                    <html:text property="workhour" styleId="workhour" styleClass="form-control" maxlength="7" onkeypress="return allowPositiveNumber(event);"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("workhour").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Vacancy Posted By </label>
                                                    <html:text property="vacancypostedby" styleId="vacancypostedby" styleClass="form-control" maxlength="100"/>  
                                                    <script type="text/javascript">
                                                        document.getElementById("vacancypostedby").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-8 col-md-8 col-sm-8 col-12 form_group">
                                                    <label class="form_label">Additional Note</label>
                                                    <html:text property="additionalnote" styleId="additionalnote" styleClass="form-control"  maxlength="200"/>  
                                                    <script type="text/javascript">
                                                        document.getElementById("additionalnote").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                            </div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="submitdiv">
                                                <a href="javascript:submitForm();" class="save_btn"><img src="../assets/images/save.png"> Save
                                                </a>
                                                <c:choose>
                                                    <c:when test="${jobpostForm.jobpostId <= 0}">
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
                    </div>
                    <!-- End Page-content -->
                </div>
                <!-- end main content-->
            </div>
            <!-- END layout-wrapper -->

            <%@include file ="../footer.jsp"%>
            <div id="relation_modal" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <input type='hidden' name='mtype' />
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id='maddid'>Relation</h4> 
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="row">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                        <label class="form_label">Name</label>
                                        <input class="form-control" placeholder="" name='mname' maxlength="100"/>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 text-center"><a href="javascript:addtomasterajax();" class="save_button mt_15"><img src="../assets/images/save.png"> Save</a></div>
                                </div>
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
            <script src="../autofill/jquery-ui.min.js" type="text/javascript"></script> <!-- autofill-->
            <script src="../assets/js/app.js"></script>	
            <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>

            <script src="../assets/js/bootstrap-select.min.js"></script>
            <script src="../assets/js/bootstrap-datepicker.min.js"></script>
            <script src="/jxp/assets/js/sweetalert2.min.js"></script>
            <script>

                                                        $(function ()
                                                        {
                                                            $("#cityname").autocomplete({
                                                                source: function (request, response) {
                                                                    $.ajax({
                                                                        url: "/jxp/ajax/client/autofillcity.jsp",
                                                                        type: 'post',
                                                                        dataType: "json",
                                                                        data: JSON.stringify({"search": request.term, "countryId": $("#countryddl").val()}),
                                                                        success: function (data) {
                                                                            response(data);
                                                                        }
                                                                    });
                                                                },
                                                                select: function (event, ui) {
                                                                    //console.log('onHover :: '+JSON.stringify(ui,null,2));					
                                                                    $('#cityname').val(ui.item.label); // display the selected text
                                                                    $('input[name="cityId"]').val(ui.item.value);
                                                                    return false;
                                                                },
                                                                focus: function (event, ui)
                                                                {
                                                                    //console.log('onFocus :: '+JSON.stringify(ui,null,2));					
                                                                    $('#cityname').val(ui.item.label); // display the selected text
                                                                    return false;
                                                                }
                                                            });
                                                        });
            </script>
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
            <script type="text/javascript">
                $(document).ready(function () {
                    $('#nationalitymultiselect_dd').multiselect({
                        includeSelectAllOption: true,
                        //maxHeight: 400,
                        dropUp: true,
                        nonSelectedText: '- Select -',
                        maxHeight: 200,
                        enableFiltering: false,
                        enableCaseInsensitiveFiltering: false,
                        buttonWidth: '100%'
                    });
                    $('#languagemultiselect_dd').multiselect({
                        includeSelectAllOption: true,
                        //maxHeight: 400,
                        dropUp: true,
                        nonSelectedText: '- Select -',
                        maxHeight: 200,
                        enableFiltering: false,
                        enableCaseInsensitiveFiltering: false,
                        buttonWidth: '100%'
                    });
                    $('#gendermultiselect_dd').multiselect({
                        includeSelectAllOption: true,
                        //maxHeight: 400,
                        dropUp: true,
                        nonSelectedText: '- Select -',
                        maxHeight: 200,
                        enableFiltering: false,
                        enableCaseInsensitiveFiltering: false,
                        buttonWidth: '100%'
                    });
                });
            </script>
            <script>
                function loadfun()
                {
                    if (Number(document.jobpostForm.jobpostId.value) <= 0) {
                        if (Number(document.getElementById("experiencemin").value) <= 0) {
                            document.getElementById("experiencemin").value = "";
                        }

                        if (Number(document.getElementById("experiencemax").value) <= 0) {
                            document.getElementById("experiencemax").value = "";
                        }
                    }
                    if (Number(document.getElementById("noofopening").value) <= 0) {
                        document.getElementById("noofopening").value = "";
                    }
                    if (Number(document.getElementById("tenure").value) <= 0) {
                        document.getElementById("tenure").value = "";
                    }
                    if (Number(document.getElementById("remunerationmin").value) <= 0) {
                        document.getElementById("remunerationmin").value = "";
                    }
                    if (Number(document.getElementById("remunerationmax").value) <= 0) {
                        document.getElementById("remunerationmax").value = "";
                    }
                    if (Number(document.getElementById("dayratetext").value) <= 0) {
                        document.getElementById("dayratetext").value = "";
                    }
                    if (Number(document.getElementById("dayratevalue").value) <= 0) {
                        document.getElementById("dayratevalue").value = "";
                    }
                    if (Number(document.getElementById("workhour").value) <= 0) {
                        document.getElementById("workhour").value = "";
                    }
                }
            </script>
    </html:form>
</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
