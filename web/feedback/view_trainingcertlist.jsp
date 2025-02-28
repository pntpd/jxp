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
            int mtp = 2, submtp = 7, ctp = 7;
            if (session.getAttribute("CREWLOGIN") == null) {
    %>
    <jsp:forward page="/crewloginindex1.jsp"/>
    <%
        }
        String file_path = candidate.getMainPath("view_candidate_file");
        ArrayList trainingcert_list = new ArrayList();
        if (request.getAttribute("CANDTRAININGCERTLIST") != null) {
            trainingcert_list = (ArrayList) request.getAttribute("CANDTRAININGCERTLIST");
        }
        int trainingcert_list_size = trainingcert_list.size();
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
        <html:hidden property="trainingandcertId"/>
        <html:hidden property="doaddtrainingcertdetail"/>
        <html:hidden property="candidateId"/>
        <html:hidden property="doViewcertification"/>
        <html:hidden property="certificationdetaillId"/>
        
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 heading_title">
                                <h1>Profile > Training, Certification & Safety Course Details</h1>
                            </div>

                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                <div class="body-background">
                                    <div class="row">
                                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 mt_15">
                                            <div class="pull_right float-end">
                                                <a href="javascript: modifytrainingcertdetailForm(-1);" class="add_btn"><i class="mdi mdi-plus"></i></a> 
                                            </div>
                                        </div>
                                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 form_group mt_15">
                                            <div class="table-responsive table-detail table-scrollable">
                                                <table class="table table-striped mb-0">
                                                    <thead>
                                                        <tr>
                                                            <th width="22%">Course Name</th>
                                                            <th width="10%">Type</th>
                                                            <th width="30%">Institution, Location</th>
                                                            <th width="10%">Approved By</th>
                                                            <th width="10%">Issue Date</th>
                                                            <th width="10%">Expiry Date</th>
                                                            <th width="8%" class="text-center">Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%
                                                            if (trainingcert_list_size > 0) {
                                                                CandidateInfo ainfo;
                                                                String filep, url;
                                                                for (int i = 0; i < trainingcert_list_size; i++) {
                                                                    ainfo = (CandidateInfo) trainingcert_list.get(i);
                                                                    if (ainfo != null) 
                                                                    {
                                                                        filep = ""; url = "";
                                                                        if(ainfo.getCertifilename() != null && !ainfo.getCertifilename().equals(""))
                                                                            filep = file_path+ainfo.getCertifilename();
                                                                        else if(ainfo.getUrl() != null && !ainfo.getUrl().equals(""))
                                                                            url = ainfo.getUrl();
                                                        %>
                                                        <tr>
                                                            <td class="hand_cursor" onclick="javascript: viewcertificationdetail('<%= ainfo.getTrainingandcertId()%>');"><%= ainfo.getCoursename() != null ? ainfo.getCoursename() : "&nbsp;"%></td>
                                                            <td class="hand_cursor" onclick="javascript: viewcertificationdetail('<%= ainfo.getTrainingandcertId()%>');"><%= ainfo.getCoursetype() != null ? ainfo.getCoursetype() : "&nbsp;"%></td>
                                                            <td class="hand_cursor" onclick="javascript: viewcertificationdetail('<%= ainfo.getTrainingandcertId()%>');"><%= ainfo.getEduinstitute() != null ? ainfo.getEduinstitute() : "&nbsp;"%></td>
                                                            <td class="hand_cursor" onclick="javascript: viewcertificationdetail('<%= ainfo.getTrainingandcertId()%>');"><%= ainfo.getApprovedby() != null ? ainfo.getApprovedby() : "&nbsp;"%></td>
                                                            <td class="hand_cursor" onclick="javascript: viewcertificationdetail('<%= ainfo.getTrainingandcertId()%>');"><%= ainfo.getDateofissue() != null ? ainfo.getDateofissue() : "&nbsp;"%></td>
                                                            <td class="hand_cursor" onclick="javascript: viewcertificationdetail('<%= ainfo.getTrainingandcertId()%>');"><%= ainfo.getExpirydate() != null ? ainfo.getExpirydate() : "&nbsp;"%></td>
                                                            <td class="action_column">                                                                                    
                                                                <% if(!filep.equals("")) {%><a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%=filep %>');"><img src="../assets/images/attachment.png"/> </a><% } else if(!url.equals("")) {%><a href="<%=url%>" class="mr_15" target="_blank"><img src="../assets/images/attachment.png"/> </a><% } else {%><a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a><%}%>
                                                                <%if (ainfo.getStatus() == 1) {%><a href="javascript: modifytrainingcertdetailForm('<%= ainfo.getTrainingandcertId()%>');" class="edit_mode mr_15"><img src="../assets/images/pencil.png"/></a><% }else {%><a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a><%}%>
                                                                <span class="switch_bth">
                                                                    <div class="form-check form-switch">
                                                                        <input class="form-check-input" type="checkbox" role="switch" <% if (ainfo.getPassflag() == 2) {%>disabled<%}%> id="flexSwitchCheckDefault_<%=(i)%>" <% if (ainfo.getStatus() == 1) {%> checked <% }%> onclick="javascript: deletetrainingcertForm('<%= ainfo.getTrainingandcertId()%>', '<%= ainfo.getStatus()%>');"/>
                                                                    </div>
                                                                </span>
                                                            </td>
                                                        </tr>    
                                                        <%
                                                                }
                                                            }
                                                        } else {
                                                        %>
                                                        <tr><td colspan='7'>No Training and Certification details available.</td></tr>
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
                        <span class="resume_title"> Document</span>
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
        <script>
                                                                            $("#iframe").on("load", function () {
                                                                                let head = $("#iframe").contents().find("head");
                                                                                let css = '<style>img{margin: 0px auto;}</style>';
                                                                                $(head).append(css);
                                                                            });
        </script>
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
