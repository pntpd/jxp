<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.policy.PolicyInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="policy" class="com.web.jxp.policy.Policy" scope="page"/>
<!doctype html>
<html lang="en">
<%
    try
    {
        int mtp = 7, submtp = 95;
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
    <title><%= policy.getMainPath("title") != null ? policy.getMainPath("title") : "" %></title>
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
    <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
    <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
    <script src="../jsnew/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="../jsnew/validation.js"></script>
    <script type="text/javascript" src="../jsnew/policy.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
<html:form action="/policy/PolicyAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
    <html:hidden property="policyId"/>
    <html:hidden property="doSave"/>
    <html:hidden property="doCancel"/>
    <html:hidden property="search"/>   
    <html:hidden property="clientIndex"/>
    <html:hidden property="assetIndex"/>
    <html:hidden property="filehidden"/>
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
                                    <c:when test="${policyForm.policyId <= 0}">
                                        Add Documents
                                    </c:when>
                                    <c:otherwise>
                                        Edit Documents
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
                                        <div class="main-heading">
                                            <div class="add-btn">
                                                <h4>&nbsp;</h4>
                                            </div>
                                            
                                        </div>
                                    </div>	
                                    <div class="row col-lg-12">	
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                            <label class="form_label">Client </label>
                                            <html:select styleClass="form-select" property="clientId" onchange="getassetDDL();">
                                            <html:optionsCollection property="clients" value="ddlValue" label="ddlLabel"></html:optionsCollection>
                                        </html:select>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                            <label class="form_label">Asset</label>
                                        <html:select styleClass="form-select" property="assetId" styleId="assetdiv">
                                            <html:optionsCollection property="assets" value="ddlValue" label="ddlLabel"></html:optionsCollection>
                                        </html:select>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                            <label class="form_label">Document Name</label>
                                            <html:text property="policyName" styleId="policyName" styleClass="form-control" maxlength="100"/>
                                            <script type="text/javascript">
                                                document.getElementById("policyName").setAttribute('placeholder', '');
                                            </script>
                                        </div>     
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                            <label class="form_label">Upload File (if any) (5Mb)(.pdf/.doc/.docx)</label>                                                   
                                                <html:file property="policyfile" styleId="upload1" onchange="javascript: setClass('1');"/>
                                                <a href="javascript:;" id="upload_link_1" class="attache_btn uploaded_img1"><i class="fas fa-paperclip"></i> Attach</a>
                                        </div>
                                            
                                    </div> 
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group" id="submitdiv">
                                        <a href="javascript:submitForm();" class="save_btn"><img src="../assets/images/save.png"> Save</a>
                                        <a href="javascript:goback();" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
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
    <script src="/jxp/assets/js/sweetalert2.min.js"></script>
    <script>
        // toggle class show hide text section
        $(document).on('click', '.toggle-title', function () {
            $(this).parent()
            .toggleClass('toggled-on')
            .toggleClass('toggled-off');
        });
    </script>
    <script>
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
            //getassetDDL();
        </script>
        <script>
            $(function () {
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
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
