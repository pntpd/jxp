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
        CandidateInfo expinfo = null;
        if (request.getAttribute("CANDEXPERIENCEINFO") != null) {
            expinfo = (CandidateInfo) request.getAttribute("CANDEXPERIENCEINFO");
        }

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
    <html:form action="/feedback/FeedbackAction.do" onsubmit="return false;" styleClass="form-horizontal">
        <html:hidden property="status" />
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 heading_title"><h1>Profile > Experience Details</h1></div>
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                <div class="body-background">
                                    <div class="row">
                                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 form_group mt_30">
                                            <div class="row">
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Company Name</label>
                                                    <span class="form-control"><%= expinfo != null && expinfo.getCompanyname() != null ? expinfo.getCompanyname() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Company Industry</label>
                                                    <span class="form-control"><%= expinfo != null && expinfo.getCompanyname() != null && !expinfo.getCompanyname().equals("") ? expinfo.getCompanyname() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Asset Type</label>
                                                    <span class="form-control"><%= expinfo != null && expinfo.getAssettype() != null && !expinfo.getAssettype().equals("") ? expinfo.getAssettype() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Asset Name</label>
                                                    <span class="form-control"><%= expinfo != null && expinfo.getAssetName() != null && !expinfo.getAssetName().equals("") ? expinfo.getAssetName() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Position</label>
                                                    <span class="form-control"><%= expinfo != null && expinfo.getPosition() != null && expinfo.getPosition().equals("") ? expinfo.getPosition() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Department / Function</label>
                                                    <span class="form-control"><%= expinfo != null && expinfo.getDepartment() != null && !expinfo.getDepartment().equals("") ? expinfo.getDepartment() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Country</label>
                                                    <span class="form-control"><%= expinfo != null && expinfo.getCountry() != null && !expinfo.getCountry().equals("") ? expinfo.getCountry() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">City</label>
                                                    <span class="form-control"><%= expinfo != null && expinfo.getCity() != null && !expinfo.getCity().equals("") ? expinfo.getCity() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Operator</label>
                                                    <span class="form-control"><%= expinfo != null && expinfo.getClientpartyname() != null ? expinfo.getClientpartyname() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Water Depth</label>
                                                    <span class="form-control"> <%= expinfo != null && expinfo.getWaterdepthunit() != null ? expinfo.getWaterdepthunit() : "&nbsp;"%> <%= expinfo != null && expinfo.getWaterdepth() != null && !expinfo.getWaterdepth().equals("") ? expinfo.getWaterdepth() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Last drawn salary / Day rate</label>
                                                    <div class="row">
                                                        <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-4">
                                                            <span class="form-control"><%= expinfo != null && expinfo.getLastdrawnsalarycurrency() != null ? expinfo.getLastdrawnsalarycurrency() : "&nbsp;"%></span>
                                                        </div>
                                                        <div class="col-xl-8 col-lg-8 col-md-8 col-sm-8 col-8">
                                                            <span class="form-control"><%= expinfo != null && expinfo.getLastdrawnsalary() != null ? expinfo.getLastdrawnsalary() : "&nbsp;"%></span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Work Start Date</label>
                                                    <span class="form-control"><%= expinfo != null && expinfo.getWorkstartdate() != null ? expinfo.getWorkstartdate() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <div class="row">
                                                        <div class="col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                            <label class="form_label">&nbsp;</label>
                                                            <div class="full_width">
                                                                <div class="form-check permission-check present_work">
                                                                    <%if (expinfo.getCurrentworkingstatus() == 1) {%>
                                                                    <i class="ion ion-md-checkbox "></i>
                                                                    <%}%>
                                                                    <span class="ml_10"><b>I currently work here.</b></span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <%
                                                            if (expinfo != null && expinfo.getExperiencefilename() != null && !expinfo.getExperiencefilename().equals("")) {
                                                        %>
                                                        <div class="col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                            <label class="form_label">Experience Certificate </label>
                                                            <a href="javascript:;" class="attache_btn" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript: setIframe('<%= expinfo != null ? file_path + expinfo.getExperiencefilename() : ""%>', '2');"><i class="fas fa-paperclip"></i> View</a>
                                                        </div>
                                                        <%}%>
                                                    </div>
                                                </div>
                                            </div>	
                                            <div class="row">	
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Skills</label>
                                                    <span class="form-control"><%= expinfo != null && !expinfo.getSkill().equals("") ? expinfo.getSkill() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Rank</label>
                                                    <span class="form-control"><%= expinfo != null && !expinfo.getGrade().equals("") ? expinfo.getGrade() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Owner Pool</label>
                                                    <span class="form-control"><%= expinfo != null && !expinfo.getOwnerpool().equals("") ? expinfo.getOwnerpool() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Crew Type</label>
                                                    <span class="form-control"><%= expinfo != null && !expinfo.getCrewtype().equals("") ? expinfo.getCrewtype() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">OCS Employed</label>
                                                    <span class="form-control"><%= expinfo != null && !expinfo.getOcsemployed().equals("") ? expinfo.getOcsemployed() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Legal Rights</label>
                                                    <span class="form-control"><%= expinfo != null && !expinfo.getLegalrights().equals("") ? expinfo.getLegalrights() : "&nbsp;"%></span>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Day Rate</label>
                                                    <div class="row">
                                                        <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-4"><span class="form-control"><%= expinfo != null && !expinfo.getDayratecurrency().equals("") ? expinfo.getDayratecurrency() : "&nbsp;"%></span></div>
                                                        <div class="col-xl-8 col-lg-8 col-md-8 col-sm-8 col-8"><span class="form-control"><%= expinfo != null && expinfo.getDayrate() > 0 ? expinfo.getDayrate() : "&nbsp;"%></span></div>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                    <label class="form_label">Monthly Salary</label>
                                                    <div class="row">
                                                        <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-4"><span class="form-control"><%= expinfo != null && !expinfo.getMonthlysalarycurrency().equals("") ? expinfo.getMonthlysalarycurrency() : "&nbsp;"%></span></div>
                                                        <div class="col-xl-8 col-lg-8 col-md-8 col-sm-8 col-8"><span class="form-control"><%= expinfo != null && expinfo.getMonthlysalary() > 0 ? expinfo.getMonthlysalary() : "&nbsp;"%></span></div>
                                                    </div>
                                                </div>
                                                <%
                                                    if (expinfo != null && expinfo.getWorkfilename() != null && !expinfo.getWorkfilename().equals("")) {
                                                %>
                                                <div class="col-xl-3 col-lg-3 col-md-3 col-sm-3 col-12 form_group">
                                                    <label class="form_label">Attach Documents</label>
                                                    <a href="javascript:;" class="attache_btn" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%= expinfo != null ? file_path + expinfo.getWorkfilename() : ""%>', '2');"><i class="fas fa-paperclip"></i> View</a>
                                                </div>
                                                <%}%>
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
                                <iframe class="pdf_mode" src="" id="iframe"></iframe>                                        
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

    </html:form>
</body>
<%    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
