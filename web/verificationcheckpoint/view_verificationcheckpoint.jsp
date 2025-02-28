<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.verificationcheckpoint.VerificationcheckpointInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="verificationcheckpoint" class="com.web.jxp.verificationcheckpoint.Verificationcheckpoint" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 7, submtp = 15;
            String per = "N", addper = "N", editper = "N", deleteper = "N";
            if (session.getAttribute("LOGININFO") == null)
            {
    %>
    <jsp:forward page="/index1.jsp"/>
    <%
            }
            else
            {
                UserInfo uinfo = (UserInfo) session.getAttribute("LOGININFO");
                if(uinfo != null)
                {
                    per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                    addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
                    editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                    deleteper = uinfo.getDeleteper() != null ? uinfo.getDeleteper() : "N";
                }
            }
            VerificationcheckpointInfo info = null;
            if(request.getAttribute("VERIFICATIONSUBTYPE_DETAIL") != null)
                info = (VerificationcheckpointInfo)request.getAttribute("VERIFICATIONSUBTYPE_DETAIL");
            
            String thankyou = "";
                if(request.getAttribute("VCSAVEMODEL") != null)
                {
                    thankyou = (String)request.getAttribute("VCSAVEMODEL");
                    request.removeAttribute("VCSAVEMODEL");
                }
    %>  
    <head>
        <meta charset="utf-8">
        <title><%= verificationcheckpoint.getMainPath("title") != null ? verificationcheckpoint.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css"/>
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/verificationcheckpoint.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/verificationcheckpoint/VerificationcheckpointAction.do" onsubmit="return false;" styleClass="form-horizontal">
        <html:hidden property="doCancel"/>
        <html:hidden property="doModify"/>
        <html:hidden property="doView"/>
        <html:hidden property="doAdd"/>
        <html:hidden property="verificationcheckpointId"/>
        <html:hidden property="search"/>
        <!-- Begin page -->
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start"><a href="javascript: goback();" class="back_arrow"><img  src="../assets/images/back-arrow.png"/> View Verification Checkpoint</a></div>
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
                    </div>
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background">
                                    <div class="row d-none1"> 
                                        <div class="main-heading">
                                            <div class="add-btn">
                                                <h4>VIEW VERIFICATION CHECKPOINT</h4>
                                            </div>

                                            <div class="edit_btn pull_right float-end">
                                                <% if (editper.equals("Y")) {%> <a href="javascript: modifyForm('<%= info.getVerificationcheckpointId()%>');"><img src="../assets/images/edit.png"></a> <%}%>
                                            </div>

                                        </div>
                                        <%
                                                                            if(info != null)
                                                                            {
                                        %>
                                        <div class="row col-lg-12"> 

                                            <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                <label>Verification Checkpoint Code</label>
                                                <span class="form-control"><%= verificationcheckpoint.changeNum(info.getVerificationcheckpointId(),3) %></span>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                <label>Verification Checkpoint</label>
                                                <span class="form-control"><%= (info.getVerificationcheckpointName() != null && !info.getVerificationcheckpointName().equals("")) ? info.getVerificationcheckpointName() : "NA" %></span>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                <label>Tab</label>
                                                <span class="form-control"><%= (info.getTabname() != null && !info.getTabname().equals("")) ? info.getTabname() : "NA" %></span>
                                            </div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                <label>Display Note</label>
                                                <span class="form-control"><%= (info.getDisplaynote() != null && !info.getDisplaynote().equals("")) ? info.getDisplaynote() : "NA" %></span>
                                            </div>

                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group ">
                                                <% if( info.getMinverification() == 1) {%><span class="check_mark"><i class="ion ion-md-checkmark" aria-hidden="true"></i></span><% } else {%> <span class="check_mark empty_box"> <i class="ion ion-md-square-outline" aria-hidden="true"></i></span><% } %>
                                                <span class="ml_10"><b>This is a minimum verification requirement</b></span></div>

                                        </div>

                                    </div>                                    
                                    <%
                                                                        }
                                    %>
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
    <%@include file="../footer.jsp" %>

    <div id="thank_you_modal" class="modal fade thank_you_modal blur_modal" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
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
                            <h3>Verification Checkpoint <%=thankyou%></h3>
                            <p><%= "Saved".equals(thankyou) ? "The checkpoint has been saved" : "A new checkpoint has been added"%> in the Verification Module</p>
                            <a href="javascript: goback();" class="msg_button" style="text-decoration: underline;">Verification Checkpoint</a>
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
    <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>
    <% if(!"".equals(thankyou)){%>
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
}
catch(Exception e)
{
    e.printStackTrace();
}
%>
</html>
