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
            int mtp = 2, submtp = 45, ctp = 3;
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
        String message = "", clsmessage = "deleted-msg";
            if (request.getAttribute("MESSAGE") != null)
            {
                message = (String)request.getAttribute("MESSAGE");
                request.removeAttribute("MESSAGE");
            }        
            if(message != null && (message.toLowerCase()).indexOf("success") != -1)
                clsmessage = "updated-msg";       

                ArrayList assessment_list = new ArrayList();
            if(request.getAttribute("ASSESSMENTLIST") != null)
                assessment_list = (ArrayList) request.getAttribute("ASSESSMENTLIST");
            int assessment_list_size = assessment_list.size();
            
        JobPostInfo jpinfo = null;
        if (session.getAttribute("JOBPOST_DETAIL") != null) {
            jpinfo = (JobPostInfo) session.getAttribute("JOBPOST_DETAIL");
        }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= jobpost.getMainPath("title") != null ? jobpost.getMainPath("title") : "" %></title>
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
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">

        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/jobpost.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/jobpost/JobPostAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="jobpostId"/>
        <html:hidden property="doAdd"/>
        <html:hidden property="doModify"/>
        <html:hidden property="doCancel"/>
        <html:hidden property="doView"/>   
        <html:hidden property="doBenefitsList"/>
        <html:hidden property="doModifyAssessmentDetail"/>
        <html:hidden property="doViewAssessmentList"/>
        <html:hidden property="assessmentDetailId"/>       
        <html:hidden property="doDeleteAssessmentDetail"/>
        <html:hidden property="status" />
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
                                        <%@include file ="../shortcutmenu.jsp"%>
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
                                            <div class="main-heading">
                                                <div class="add-btn">
                                                    <h4>ASSESSMENT LIST</h4>
                                                </div>
                                                <% if (addper.equals("Y") && jpinfo.getStatus() == 1) {%>
                                                <div class="edit_btn pull_right float-end">
                                                    <a href="javascript: addAssessmentForm(-1);" class="add_btn"><i class="mdi mdi-plus"></i></a>
                                                </div>
                                                <%
                                                }
                                                %>
                                            </div>
                                            <div class="col-lg-12 all_client_sec" id="all_client">
                                                <div class="table-responsive table-detail">
                                                    <table class="table table-striped mb-0">
                                                        <thead>
                                                            <tr>
                                                                <th width="6%">Code</th>
                                                                <th width="32%">Name</th>
                                                                <th width="32%">Parameter</th>
                                                                <th width="10%" class="text-center">Minimum Score</th>
                                                                <th width="10%" class="text-center">Passing Required</th>
                                                                <th width="10%" class="text-right">Actions</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <%
                                                                                                                                                                 if(assessment_list_size > 0)
                                                                                                                                                                 {
                                                                                                                                                                     JobPostInfo info;
                                                                                                                                                                     for(int i = 0; i < assessment_list_size; i++)
                                                                                                                                                                     {
                                                                                                                                                                         info = (JobPostInfo) assessment_list.get(i);
                                                                                                                                                                         if(info != null)
                                                                                                                                                                         {
 
                                                            %>
                                                            <tr>
                                                                <td> <%= jobpost.changeNum( info.getAssessmentId(),3) %></td> 
                                                                <td><%= info.getAssessmentName() != null ? info.getAssessmentName() : "" %></td>
                                                                <td><%= info.getParameter() != null ? info.getParameter() : "" %></td>
                                                                <td class="assets_list text-center"><a><%= info.getMinScore() %></a></td>
                                                                <% if(info.getPassingFlag() == 1) {%><td class="text-center"><i class="fa fa-check" aria-hidden="true"></i></td><% } else {%><td></td><% } %>
                                                                <td class="action_column">                                                                    
                                                                    <% if (editper.equals("Y") && jpinfo.getStatus() == 1 && info.getStatus() == 1) {%><a href="javascript: addAssessmentForm('<%= info.getAssessmentDetailId()%>');" class=" mr_15"><img src="../assets/images/pencil.png"/></a><% }%>
                                                                    <span class="switch_bth">                                           
                                                                        <div class="form-check form-switch">
                                                                            <input class="form-check-input" type="checkbox" role="switch" <%=jpinfo.getStatus() == 2 || jpinfo.getStatus() == 3 ? "disabled" : ""%> id="flexSwitchCheckDefault_<%=info.getAssessmentDetailId()%>" <% if( info.getStatus() == 1){%>checked<% } if (!editper.equals("Y")) {%> disabled="true"<% }%> onclick="javascript: deleteAssessmentForm('<%= info.getAssessmentDetailId()%>', '<%= info.getStatus()%>');"/>
                                                                        </div>                                                                        
                                                                    </span>
                                                                </td>
                                                            </tr>    
                                                            <%
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                     }
                                                                                                                                    else
                                                                                                                                    {
                                                            %>
                                                            <tr><td colspan='6'>No Assessment details available.</td></tr>
                                                            <%
                                                                                                                                    }
                                                            %>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                <!-- <a href="javascript:;" class="filter_btn"><i class="mdi mdi-filter-outline"></i> Filter</a> -->
                                                <%if(jpinfo.getStatus() == 1){ %>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <a href="javascript:void(0);" onclick="getjobpost();" class="save_btn"><img src="../assets/images/post.png"/> Post</a>
                                                </div>
                                                <%}%>
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
        <div id="view_pdf" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        <span class="resume_title">File</span>
                        <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                        <a id='diframe' href="" class="down_btn"><img src="../assets/images/download.png"/></a>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <center> <iframe id='iframe' class="doc" src=""></iframe></center>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <div id="thank_you_modal" class="modal fade thank_you_modal" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
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
                                <h3>Job Posted</h3>
                                <p>Find the right match for your requirements</p>
                                <a href="../crewdb/CrewdbAction.do" class="msg_button" style="text-decoration: underline;">Talent Pool</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- JAVASCRIPT -->
        <script src="../assets/libs/jquery/jquery.min.js"></script>		
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/app.js"></script>	
        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script>
        <script>
            function getjobpost() {
                $('#thank_you_modal').modal('show');
            }
        </script>
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