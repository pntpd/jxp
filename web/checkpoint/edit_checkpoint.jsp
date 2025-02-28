<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.checkpoint.CheckPointInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="checkpoint" class="com.web.jxp.checkpoint.CheckPoint" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 7, submtp = 48, ctp = 1;
            if (session.getAttribute("LOGININFO") == null) {
    %>
    <jsp:forward page="/index1.jsp"/>
    <%
        }
        String message = "", clsmessage = "red_font";
        if (request.getAttribute("MESSAGE") != null) {
            message = (String) request.getAttribute("MESSAGE");
            request.removeAttribute("MESSAGE");
        }
        if (message != null && (message.toLowerCase()).indexOf("success") != -1) {
            clsmessage = "updated-msg";
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
        <script type="text/javascript" src="../jsnew/checkpoint.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/checkpoint/CheckPointAction.do" onsubmit="return false;" styleClass="form-horizontal">
        <html:hidden property="checkpointId"/>
        <html:hidden property="doSave"/>
        <html:hidden property="doAddMore"/>
        <html:hidden property="doCancel"/>
        <html:hidden property="search"/>
        <html:hidden property="doModify"/>
        <html:hidden property="doView"/>
        <!-- Begin page -->
        <div id="layout-wrapper">
            <%@include file ="../header.jsp"%>
            <%@include file ="../sidemenu.jsp"%>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow">
                                <a href="javascript: goback();"><img src="../assets/images/back-arrow.png"/></a>
                                <span>Compliance Checkpoint </span>
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
                                                        <c:when test="${checkpointForm.checkpointId <= 0}">
                                                            <h4>ADD COMPLIANCE CHECKPOINT</h4>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <h4>EDIT COMPLIANCE CHECKPOINT</h4>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Compliance Checkpoint Code</label>
                                                <html:text property="code" styleId="code" styleClass="form-control" />
                                                <script type="text/javascript">
                                                    document.getElementById("code").setAttribute('placeholder', '');
                                                    document.getElementById("code").setAttribute('readonly', true);
                                                </script>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Checkpoint Name</label>
                                                <html:text property="name" styleId="name" styleClass="form-control" maxlength="100"/>
                                                <script type="text/javascript">
                                                    document.getElementById("name").setAttribute('autocomplete', 'off');
                                                    document.getElementById("name").setAttribute('placeholder', '');
                                                </script>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Client</label>
                                                <html:select property="clientId" styleId="clientId" styleClass="form-select" onchange="javascript: setAssetDDL();">
                                                    <html:optionsCollection filter="false" property="client" label="ddlLabel" value="ddlValue">
                                                    </html:optionsCollection>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Asset</label>
                                                <html:select property="clientassetId" styleId="clientassetddl" styleClass="form-select" onchange="javascript: setPositionDDL();">
                                                    <html:optionsCollection filter="false" property="clientasset" label="ddlLabel" value="ddlValue">
                                                    </html:optionsCollection>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Position</label>
                                                <html:hidden property="positionname"/>
                                                <html:select property="positionId" styleId="positionddl" styleClass="form-select" onchange="javascript: setGradeDDL();" >
                                                    <html:optionsCollection filter="false" property="position" label="ddlLabel" value="ddlValue">
                                                    </html:optionsCollection>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Rank</label>
                                                <html:select property="gradeId" styleId="Gradeddl" styleClass="form-select">
                                                    <html:optionsCollection filter="false" property="grade" label="ddlLabel" value="ddlValue">
                                                    </html:optionsCollection>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                <label class="form_label">Display Note</label>
                                                <html:textarea property="displaynote" rows="5" styleId="displaynote" styleClass="form-control"></html:textarea>
                                                <script type="text/javascript">
                                                    document.getElementById("displaynote").setAttribute('placeholder', '');
                                                    document.getElementById("displaynote").setAttribute('maxlength', '1000');
                                                </script>
                                            </div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                <label class="form_label">&nbsp;</label>
                                                <div class="full_width">
                                                    <div class="form-check permission-check">
                                                        <html:checkbox property="checkFlag" value = "1" styleClass="form-check-input"  styleId="switch1"  />
                                                        <span class="ml_10"><b>This is a minimum check-point requirement</b></span>
                                                    </div>                                                        
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="submitdiv">
                                            <c:choose>
                                                <c:when test="${checkpointForm.checkpointId <= 0}">
                                                    <a href="javascript:submitAddMore();" class="save_btn" style="right: 170px"><img src="../assets/images/save.png"> Add More </a>
                                                    <a href="javascript: goback();" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="javascript: getView();" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
                                                </c:otherwise>
                                            </c:choose>

                                            <a href="javascript:submitForm();" class="save_btn"><img src="../assets/images/save.png"> Save </a>

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
        <script src="/jxp/assets/js/sweetalert2.min.js"></script>
        <script>
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
