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
        try
        {
            int mtp = 2, submtp = 45,ctp = 3;
            String per = "N", addper = "N", editper = "N", deleteper = "N";
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
            ArrayList list = new ArrayList();
       
    %>
    <head>
        <meta charset="utf-8">
        <title><%= jobpost.getMainPath("title") != null ? jobpost.getMainPath("title") : "" %></title>
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
        <link href="../assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="../autofill/jquery-ui.min.css" />  <!-- Autofill-->

    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/jobpost/JobPostAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
            <html:hidden property="jobpostId"/>
            <html:hidden property="doSave"/>
            <html:hidden property="doModify"/>
            <html:hidden property="doCancel"/>
            <html:hidden property="search"/>
            <html:hidden property="doView"/>
            <html:hidden property="doBenefitsList"/>
            <html:hidden property="doViewAssessmentList"/>
            <html:hidden property="doModifyAssessmentDetail"/>
            <html:hidden property="doSaveAssessmentDetail"/>
            <html:hidden property="assessmentDetailId"/>
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
                                    <a href="javascript: goback();">
                                        <img  src="../assets/images/back-arrow.png"/> 
                                    </a>
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
                                <ul class='nav nav-tabs nav-tabs-custom' id='tab_menu'>
                                    <c:choose>
                                        <c:when test="${jobpostForm.assessmentDetailId <= 0}">
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
                                                <% if(!message.equals("")) {%><div class="sbold <%=clsmessage%>">
                                                    <%=message%>
                                                </div><% } %>
                                                <div class="main-heading m_15">
                                                    <div class="add-btn">
                                                        <h4>ASSESSMENT</h4>
                                                    </div>
                                                </div>
                                            </div>	
                                            <div class="row">
                                                <div class="col-lg-8 col-md-8 col-sm-8 col-12 form_group">
                                                    <div class="row">
                                                        <div class="col-sm-1">
                                                            <label class="form_label">&nbsp;</label>
                                                            <div class="input-group flex_div">
                                                                <a href="javascript:setAssessments();" class="input-group-text refresh_btn"><i class="ion ion-md-refresh"></i></a>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-11">
                                                            <label class="form_label">Name</label>
                                                            <div class="input-group flex_div">
                                                                <html:select property="assessmentId" styleId="assessmentId" styleClass="form-select mr_15" onchange="javascript: setidname();" >
                                                                    <html:optionsCollection filter="false" property="assessments" label="ddlLabel" value="ddlValue">
                                                                    </html:optionsCollection>
                                                                </html:select>
                                                                <a href="../assessment/AssessmentAction.do?doAdd=yes" target="_blank" class="add_btn"><i class="mdi mdi-plus"></i></a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Code</label>
                                                    <input class="form-control" value="" id="code" name ="code" readonly/>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Parameters</label>
                                                    <input class="form-control" value="" id="pname" name ="pname" readonly>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Minimum Score</label>
                                                    <html:text property="minScore" styleId="minScore" styleClass="form-control" maxlength="5" onkeypress="return allowPositiveNumber1(event);" onfocus="disablePaste('minScore')"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("minScore").setAttribute('placeholder', '');
                                                    </script>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">&nbsp;</label>
                                                    <div class="full_width">
                                                        <div class="form-check permission-check">
                                                            <html:checkbox property="passingFlag" value = "1" styleClass="form-check-input"  styleId="switch1"  />
                                                            <span class="ml_10"><b>Passing required</b></span>
                                                        </div>                                                        
                                                    </div>
                                                </div>
                                            </div>	
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="submitdiv">
                                                <a href="javascript:submitAssessmentDetailForm();" class="save_btn"><img src="../assets/images/save.png"> Save</a>
                                                <a href="javascript:openTab('3');" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
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
            <script src="../autofill/jquery-ui.min.js" type="text/javascript"></script> <!-- autofill-->
            <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
            <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
            <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
            <script src="../assets/js/app.js"></script>	
            <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>
            <script src="../assets/js/bootstrap-select.min.js"></script>
            <script src="../assets/js/bootstrap-datepicker.min.js"></script>
            <script src="../jsnew/common.js" type="text/javascript"></script>
            <script type="text/javascript" src="../jsnew/validation.js"></script>
            <script type="text/javascript" src="../jsnew/jobpost.js"></script>
            <script src="../assets/js/sweetalert2.min.js"></script>
            <script>
                                                        // toggle class show hide text section
                                                        $(document).on('click', '.toggle-title', function () {
                                                            $(this).parent()
                                                                    .toggleClass('toggled-on')
                                                                    .toggleClass('toggled-off');
                                                        });
            </script>
            <script type="text/javascript">
                function setval()
                {
                    if (eval(document.forms[0].minScore.value) <= 0)
                        document.forms[0].minScore.value = "";
                }
                function addLoadEvent(func)
                {
                    var oldonload = window.onload;
                    if (typeof window.onload != 'function') {
                        window.onload = func;
                    } else {
                        window.onload = function () {
                            if (oldonload) {
                                oldonload();
                            }
                        }
                    }
                }
                addLoadEvent(setval());
                addLoadEvent(setidname());
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
