<%@page language="java"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="candidate" class="com.web.jxp.candidate.Autofill" scope="page"/>
<!doctype html>
<html lang="en">
<%
        try
        {
            int mtp = 0, submtp = 0;
            String message = "";
            if (session.getAttribute("LOGININFO") == null)
            {                
%>
                <jsp:forward page="/index1.jsp"/>
<%
            }            
            String permission = "N";
            if (session.getAttribute("LOGININFO") != null) 
            {
                UserInfo uInfo = (UserInfo) session.getAttribute("LOGININFO");
                if (uInfo != null) 
                {
                    permission = uInfo.getPermission();
                }
            }
            int check_user = candidate.checkUserSession(request, 95, permission);                
            if (check_user == -1)
            {
                message = "You are not authorised for this module.";
            }
            else if (check_user == -2) 
            {
                message = "You are not authorised for this module.";
            }
%>
    <head>
        <meta charset="utf-8">
        <title>JourneyXpro Find your Candidate</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <link href="../assets/css/rwd-table.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="../autofill/jquery-ui.min.css" /> 
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/autofill.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <form name="homeForm" method="POST" action="" onsubmit="return false;">
            <input type="hidden" name="doView" />
            <input type="hidden" name="candidateId" />
            <div id="layout-wrapper">
                <%@include file="../header.jsp" %>
                <%@include file="../sidemenu.jsp" %>
                <div class="main-content">
                    <div class="page-content">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12">
                                <div class="float-start back_arrow">                                    
                                    <span>Find your Candidate </span>
                                </div>
                                <div class="float-end">
                                    <div class="toggled-off usefool_tool">
                                        <div class="toggle-title">
                                            <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                            <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>
                                        </div>
                                    </div>
                                </div>
                            </div>	
                        </div>
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="search_export_area">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="row mb-4">
                                                <div class="col-lg-8">
                                                    <div class="row">
                                                        <% if(!message.equals("")) {%> 
                                                            <b><%=message%></b>
                                                        <% } else {%>
                                                        <label for="example-text-input" class="col-sm-3 col-form-label">Find your Candidate :</label>
                                                        <div class="col-sm-9 field_ic">
                                                            <input type="hidden" name="candidateIdHidden" value="0" />
                                                            <input type="text" name ="searchheader" id="searchheader" class="form-control" maxlength="200" readonly onfocus="if (this.hasAttribute('readonly')) {this.removeAttribute('readonly');  this.blur();  this.focus(); }" 
                                                                   placeholder="Find your candidate" />
                                                        </div>
                                                        <% } %>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>                        
                    </div>
                </div>
            </div>
            <%@include file ="../footer.jsp"%>            
            <script src="../assets/libs/jquery/jquery.min.js"></script>
            <script src="../autofill/jquery-ui.min.js" type="text/javascript"></script>
            <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
            <script src="../assets/libs/metismenu/metisMenu.min.js"></script>            
            <script src="../assets/js/app.js"></script>
            <!-- Responsive Table js -->
            <script src="../assets/js/rwd-table.min.js"></script>
            <script src="../assets/js/table-responsive.init.js"></script>
            <script src="/jxp/assets/js/sweetalert2.min.js"></script> 
            <script>
                $(document).ready(function () {
                    $('input').attr('autocomplete', 'off');
                });
            </script>
            <script>
                    $(function ()
                    {
                        $("#searchheader").autocomplete({
                            source: function (request, response) {
                                $.ajax({
                                    url: "/jxp/ajax/search/autofill_search.jsp",
                                    type: 'post',
                                    dataType: "json",
                                    data: JSON.stringify({"search": request.term}),
                                    success: function (data) {
                                        response(data);
                                    }
                                });
                            },
                            select: function (event, ui) {					
                                $('#searchheader').val(ui.item.label); // display the selected text
                                $('input[name="candidateIdHidden"]').val(ui.item.value);
                                gotoaction(ui.item.value);
                                return false;
                            },
                            focus: function (event, ui)
                            {				
                                $('#searchheader').val(ui.item.label); // display the selected text
                                $('input[name="candidateIdHidden"]').val(ui.item.value);
                                return false;
                            }
                        });
                    });
            </script>
        </form>
    </body>
    <%
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
    %>
</html>