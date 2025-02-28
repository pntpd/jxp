<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.candidate.CandidateInfo" %>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="candidate" class="com.web.jxp.candidate.Candidate" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 2, submtp = 7, ctp = 6;
            if (session.getAttribute("CREWLOGIN") == null) {
    %>
    <jsp:forward page="/crewloginindex1.jsp"/>
    <%
        }

        String message = "", clsmessage = "deleted-msg";
        if (request.getAttribute("MESSAGE") != null) {
            message = (String) request.getAttribute("MESSAGE");
            request.removeAttribute("MESSAGE");
        }
        if (message.toLowerCase().contains("success")) {
            message = "";
        }
        if (message != null && (message.toLowerCase()).indexOf("success") != -1) {
            clsmessage = "updated-msg";
        }
        ArrayList list = new ArrayList();
        if (request.getSession().getAttribute("MODULEPER_LIST") != null) {
            list = (ArrayList) request.getSession().getAttribute("MODULEPER_LIST");
        }
        String file_path = candidate.getMainPath("view_candidate_file");
        ArrayList edu_list = new ArrayList();
        if (request.getAttribute("CANDEDUCATIONLIST") != null) {
            edu_list = (ArrayList) request.getAttribute("CANDEDUCATIONLIST");
        }
        int edu_list_size = edu_list.size();

        int ipass = 0;
        if (session.getAttribute("PASS") != null) {
            ipass = Integer.parseInt((String) session.getAttribute("PASS"));
        }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= candidate.getMainPath("title") != null ? candidate.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/feedback.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="ocs_feedback vertical-collpsed crew_user bg_image">
    <html:form action="/feedback/FeedbackAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="status" />
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 heading_title"><h1>Profile > Educational Details</h1></div>
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                <div class="body-background">
                                    <div class="row">
                                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 form_group mt_30">
                                            <div class="table-responsive table-detail">
                                                <table class="table table-striped mb-0">
                                                    <thead>
                                                        <tr>
                                                            <th width="10%">Kind</th>
                                                            <th width="22%">Degree</th>
                                                            <th width="38%">Institution, Location</th>
                                                            <th width="10%">Start Date</th>
                                                            <th width="10%">End Date</th>
                                                            <th width="10%" class="text-center">Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%
                                                            if (edu_list_size > 0) {
                                                                CandidateInfo ainfo;
                                                                for (int i = 0; i < edu_list_size; i++) {
                                                                    ainfo = (CandidateInfo) edu_list.get(i);
                                                                    if (ainfo != null) {
                                                        %>
                                                        <tr>
                                                            <td><%= ainfo.getKindname() != null ? ainfo.getKindname() : "&nbsp;"%></td>
                                                            <td><%= ainfo.getDegree() != null ? ainfo.getDegree() : "&nbsp;"%></td>
                                                            <td><%= ainfo.getEduinstitute() != null ? ainfo.getEduinstitute() : "&nbsp;"%><%= ainfo.getLocationofInstitute() != null && ainfo.getEduinstitute() != null ? "," : ""%><%= ainfo.getLocationofInstitute() != null ? ainfo.getLocationofInstitute() : "&nbsp;"%></td>
                                                            <td><%= ainfo.getCoursestart() != null ? ainfo.getCoursestart() : "&nbsp;"%></td>
                                                            <td><%= ainfo.getPassingyear() != null ? ainfo.getPassingyear() : "&nbsp;"%></td>
                                                            <td class="action_column">                                                                                        
                                                                <% if (!ainfo.getEducationalfilename().equals("")) {%><a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%=file_path + ainfo.getEducationalfilename()%>');"><img src="../assets/images/attachment.png"/> </a><% } else {%><a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a><%}%>
                                                                    <%
                                                                        if (ainfo.getPassflag() == 2) {
                                                                        } else {

                                                                            if (ainfo.getStatus() == 1) {%><a href="javascript: modifyeducationForm('<%= ainfo.getEducationdetailId()%>');" class="edit_mode mr_15"><img src="../assets/images/pencil.png"/></a><% } else {%><a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a><%}%>
                                                                    <%}%>
                                                                <span class="switch_bth">
                                                                    <div class="form-check form-switch">
                                                                        <input class="form-check-input" type="checkbox" <% if (ainfo.getPassflag() == 2) {%>disabled<%}%> role="switch" id="flexSwitchCheckDefault_<%=(i)%>" <% if (ainfo.getStatus() == 1) {%> checked <% }%> onclick="javascript: deleteeducationForm('<%= ainfo.getEducationdetailId()%>', '<%= ainfo.getStatus()%>');"/>
                                                                    </div>
                                                                </span>

                                                            </td>
                                                        </tr>    
                                                        <%
                                                                }
                                                            }
                                                        } else {
                                                        %>
                                                        <tr><td colspan='7'>No educational details available.</td></tr>
                                                        <%
                                                            }
                                                        %>
                                                    </tbody>
                                                </table>		
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





        <div id="view_pdf" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        <span class="resume_title">Document</span>
                        <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                        <a href="javascript:;" class="down_btn" download=""><img src="../assets/images/download.png"/></a>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <iframe id='iframe' class="doc" src=""></iframe>
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
        <script type="text/javascript">
                                                                            function addLoadEvent(func) {
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
        </script>
    </html:form>
</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
