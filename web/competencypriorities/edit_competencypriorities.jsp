<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.competencypriorities.CompetencyprioritiesInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="competencypriorities" class="com.web.jxp.competencypriorities.Competencypriorities" scope="page"/>
<!doctype html>
<html lang="en">
<%
    try
    {
        int mtp = 7, submtp = 85;
        if (session.getAttribute("LOGININFO") == null)
        {
%>
            <jsp:forward page="/index1.jsp"/>
<%
        }
        String message = "", clsmessage = "red_font", deptIds = "";
        if (request.getAttribute("MESSAGE") != null)
        {
            message = (String)request.getAttribute("MESSAGE");
            request.removeAttribute("MESSAGE");
        }        
        if(message != null && (message.toLowerCase()).indexOf("success") != -1)
            clsmessage = "updated-msg";
        if (session.getAttribute("DEPT_IDs") != null) 
        {
            deptIds = (String) session.getAttribute("DEPT_IDs");
        }
        request.getSession().removeAttribute("DEPT_IDs");
%>
<head>
    <meta charset="utf-8">
    <title><%= competencypriorities.getMainPath("title") != null ? competencypriorities.getMainPath("title") : "" %></title>
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
    <script type="text/javascript" src="../jsnew/competencypriorities.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
<html:form action="/competencypriorities/CompetencyprioritiesAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
    <html:hidden property="competencyprioritiesId"/>
    <html:hidden property="doSave"/>
    <html:hidden property="doCancel"/>
    <html:hidden property="search"/>   
    <html:hidden property="assetIdIndex"/> 
    <html:hidden property="departmentIdIndex"/> 
    <html:hidden property="clientIdIndex"/> 
    <input type="hidden" name="ids" value="<%=deptIds%>" />
    <!-- Begin page -->
    <div id="layout-wrapper">
        <%@include file ="../header.jsp"%>
        <%@include file ="../sidemenu.jsp"%>
        <!-- Start right Content -->
        <div class="main-content">
            <div class="page-content">
                <div class="row head_title_area">
                    <div class="col-md-12 col-xl-12">
                        <div class="float-start">
                            <a href="javascript:goback();" class="back_arrow">
                            <img  src="../assets/images/back-arrow.png"/> 
                                <c:choose>
                                    <c:when test="${competencyprioritiesForm.competencyprioritiesId <= 0}">
                                        Competency Priorities
                                    </c:when>
                                    <c:otherwise>
                                        Competency Priorities
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
                                                <h4>ADD / EDIT COMPETENCY PRIORITY</h4>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Client</label>
                                                <html:select styleClass="form-select" property="clientId" styleId="clientId" onchange="javascript: setAssetDDL('2','2');">
                                                    <html:optionsCollection property="clients" value="ddlValue" label="ddlLabel"></html:optionsCollection>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Asset</label>
                                                <html:select styleClass="form-select" property="assetId" styleId="assetId" onchange="javascript: setdept();">
                                                    <html:optionsCollection property="assets" value="ddlValue" label="ddlLabel"></html:optionsCollection>
                                                </html:select>
                                            </div>  
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Department</label>
                                                <div id="deptdiv">
                                                    <select name="deptColum" id="deptmultiselect_dd" class="form-select form-control btn btn-default mt-multiselect" multiple="multiple" data-select-all="true" data-label="left" data-width="100%" data-filter="false">
                                                    </select>
                                                </div>
                                            </div> 
                                            <div class="col-lg-8 col-md-8 col-sm-8 col-8 form_group">
                                                <label class="form_label">Competency Priority Title</label>
                                                <html:text property="name" styleId="name" styleClass="form-control" maxlength="100"/>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Priority Degree</label>
                                                <html:select styleClass="form-select" property="degreeId">
                                                    <html:option value="-1">- Select -</html:option>
                                                    <html:option value="1">High</html:option>
                                                    <html:option value="2">Medium</html:option>
                                                    <html:option value="3">Low</html:option>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                <label class="form_label">Description</label>
                                                <html:textarea property="description" rows="5" styleId="description" styleClass="form-control"></html:textarea>
                                                <script type="text/javascript">
                                                    document.getElementById("description").setAttribute('maxlength', '1000');
                                                </script>
                                            </div>
                                        </div>
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="submitdiv">
                                            <a href="javascript:submitForm();" class="save_btn"><img src="../assets/images/save.png"> Save</a>
                                            <a href="javascript:goback();" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
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
    <script src="/jxp/assets/js/sweetalert2.min.js"></script>
    <script type="text/javascript">
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
        if(document.forms[0].assetId.value > 0)
            addLoadEvent(setdept()); 
    </script>
    
    <script>
        // toggle class show hide text section
        $(document).on('click', '.toggle-title', function () {
            $(this).parent()
            .toggleClass('toggled-on')
            .toggleClass('toggled-off');
        });
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#deptmultiselect_dd').multiselect({
                includeSelectAllOption: true,
                //maxHeight: 400,
                dropUp: true,
                nonSelectedText: 'Select Department',
                maxHeight: 200,
                enableFiltering: false,
                enableCaseInsensitiveFiltering: false,
                buttonWidth: '100%'
            });
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
