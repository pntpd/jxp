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
            int mtp = 2, submtp = 7, ctp = 5;

            if (session.getAttribute("CREWLOGIN") == null) {
    %>
    <jsp:forward page="/crewloginindex1.jsp"/>
    <%
        }
        String file_path = candidate.getMainPath("view_candidate_file");
        ArrayList exp_list = new ArrayList();
        if (request.getAttribute("CANDEXPERIENCELIST") != null) {
            exp_list = (ArrayList) request.getAttribute("CANDEXPERIENCELIST");
        }
        int exp_list_size = exp_list.size();

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
    <html:form action="/feedback/FeedbackAction.do" onsubmit="return false;" styleClass="form-horizontal">
        <html:hidden property="status" />
        <html:hidden property="doViewexperience" />
        <html:hidden property="experiencedetailId" />
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 heading_title"><h1>Profile > Work Experience Details</h1></div>
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                <div class="body-background">
                                    <div class="row">
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group mt_30">
                                            <div class="table-responsive table-detail">
                                                <table class="table table-striped mb-0">
                                                    <thead>
                                                        <tr>
                                                            <th width="12%">Position</th>
                                                            <th width="15%">Department</th>
                                                            <th width="23%">Company Name</th>
                                                            <th width="20%">Asset Name</th>
                                                            <th width="10%" class="text-center">Start Date</th>
                                                            <th width="10%" class="text-center">End Date</th>																			
                                                            <th width="10%" class="text-center">Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%
                                                            if (exp_list_size > 0) {
                                                                CandidateInfo ainfo;
                                                                for (int i = 0; i < exp_list_size; i++) {
                                                                    ainfo = (CandidateInfo) exp_list.get(i);
                                                                    if (ainfo != null) {
                                                        %>
                                                        <tr>
                                                            <td class="hand_cursor" onclick="javascript: viewexperiendetail('<%= ainfo.getExperiencedetailId()%>');"><%= ainfo.getPosition() != null ? ainfo.getPosition() : "&nbsp;"%></td>
                                                            <td class="hand_cursor" onclick="javascript: viewexperiendetail('<%= ainfo.getExperiencedetailId()%>');"><%= ainfo.getDepartment() != null ? ainfo.getDepartment() : "&nbsp;"%></td>
                                                            <td class="hand_cursor" onclick="javascript: viewexperiendetail('<%= ainfo.getExperiencedetailId()%>');"><%= ainfo.getCompanyname() != null ? ainfo.getCompanyname() : "&nbsp;"%></td>
                                                            <td class="hand_cursor" onclick="javascript: viewexperiendetail('<%= ainfo.getExperiencedetailId()%>');"><%= ainfo.getAssetName() != null ? ainfo.getAssetName() : "&nbsp;"%></td>
                                                            <td class="text-center hand_cursor" onclick="javascript: viewexperiendetail('<%= ainfo.getExperiencedetailId()%>');"><%= ainfo.getWorkstartdate() != null ? ainfo.getWorkstartdate() : "&nbsp;"%></td>
                                                            <td class="text-center hand_cursor" onclick="javascript: viewexperiendetail('<%= ainfo.getExperiencedetailId()%>');"><%= ainfo.getWorkenddate() != null && !ainfo.getWorkenddate().equals("") ? ainfo.getWorkenddate() : "&nbsp;"%></td>
                                                            <td class="action_column">                                                                                    
                                                                <% if (!ainfo.getExperiencefilename().equals("") || !ainfo.getWorkfilename().equals("")) {%><a href="javascript:;" onclick="javascript: viewworkexpfiles('<%=ainfo.getExperiencefilename()%>', '<%=ainfo.getWorkfilename()%>');" class="mr_15" data-bs-toggle="modal" data-bs-target="#view_resume_list"><img src="../assets/images/attachment.png"/></a><% } else {%><a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a><%}%>
                                                                <span class="switch_bth">
                                                                    <div class="form-check form-switch">
                                                                        <input class="form-check-input" type="checkbox" role="switch" <% if (ainfo.getPassflag() == 2) {%>disabled<%}%> id="flexSwitchCheckDefault_<%=(i)%>" <% if (ainfo.getStatus() == 1) {%> checked <% }%>  onclick="javascript: deleteexperienceForm('<%= ainfo.getExperiencedetailId()%>', '<%= ainfo.getStatus()%>');"/>
                                                                    </div>
                                                                </span>
                                                            </td>
                                                        </tr>    
                                                        <%
                                                                }
                                                            }
                                                        } else {
                                                        %>
                                                        <tr><td colspan='7'>No experience details available.</td></tr>
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
        <div id="view_resume_list" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div><a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen r_f_screen">Full Screen</a></div>
                        <div class="row" id="viewfilesdiv">
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
