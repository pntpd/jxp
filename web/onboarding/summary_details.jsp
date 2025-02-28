<%@page import="com.web.jxp.clientselection.ClientselectionInfo"%>
<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.onboarding.OnboardingInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="onboarding" class="com.web.jxp.onboarding.Onboarding" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 2, submtp = 56, allclient = 0, companytype = 0;
            String per = "N", addper = "N", editper = "N", deleteper = "N", approveper = "N", assetids = "";
            if (session.getAttribute("LOGININFO") == null) {
    %>
    <jsp:forward page="/index1.jsp"/>
    <%
        } else {
            UserInfo uinfo = (UserInfo) session.getAttribute("LOGININFO");
            if (uinfo != null) {
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                deleteper = uinfo.getDeleteper() != null ? uinfo.getDeleteper() : "N";
                approveper = uinfo.getApproveper() != null ? uinfo.getApproveper() : "N";
                allclient = uinfo.getAllclient();
                companytype = uinfo.getCompanytype();
            }
        }
        OnboardingInfo info = null, oinfo = null, cinfo = null;
        if (session.getAttribute("ONBOARDING_DETAIL") != null) {
            info = (OnboardingInfo) session.getAttribute("ONBOARDING_DETAIL");
        }

        if (session.getAttribute("SELECTEDCANDIDATE") != null) {
            oinfo = (OnboardingInfo) session.getAttribute("SELECTEDCANDIDATE");
        }
        if (session.getAttribute("CANDIDATEINFO") != null) {
            cinfo = (OnboardingInfo) session.getAttribute("CANDIDATEINFO");
        }

        String file_path = onboarding.getMainPath("view_candidate_file");
        String onboard_file_path = onboarding.getMainPath("view_onboarding");
        String cvfile_path = onboarding.getMainPath("view_resumetemplate_pdf");
        String cphoto = "../assets/images/empty_user.png";
        int onboardflag = 0, onflag = 0;


    %>
    <head>
        <meta charset="utf-8">
        <title><%= onboarding.getMainPath("title") != null ? onboarding.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/time/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css" />

        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">	
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/onboarding.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet">

    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/onboarding/OnboardingAction.do" onsubmit="return false;" >
        <html:hidden property="doView"/>
        <html:hidden property="doMobTravel"/>
        <html:hidden property="doMobAccomm"/>
        <html:hidden property="doViewCancel"/>
        <html:hidden property="type"/>
        <html:hidden property="jobpostId"/>
        <html:hidden property="clientId"/>
        <html:hidden property="clientassetId"/>
        <html:hidden property="shortlistId"/>
        <html:hidden property="candidateId"/>
        <html:hidden property="assetcountryId"/>
        <!-- Begin page -->
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content tab_panel no_tab1">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow"><a href="javascript:;" onclick="gobackview();"><img  src="../assets/images/back-arrow.png"/></a> <span>Onboarding</span></div>
                            <div class="float-end">
                                <!-- <a href="javascript:;" class="add_btn mr_25"><i class="mdi mdi-plus"></i></a> -->
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
                    <div class="container-fluid pd_0">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 pd_0">
                                <div class="body-background com_checks">
                                    <div class="row com_checks_main">

                                        <div class="col-md-12 com_top_right">
                                            <div class="row d-flex align-items-center">
                                                <div class="col-md-5 com_label_value">
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Client Asset</label></div>
                                                        <div class="col-md-9"><span><%=  info.getClientName() + " " + info.getClientAsset()%></span></div>
                                                    </div>
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Location</label></div>
                                                        <div class="col-md-9"><span><%= info.getCountry()%></span></div>
                                                    </div>

                                                </div>
                                                <div class="col-md-5 com_label_value">
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Total Positions</label></div>
                                                        <div class="col-md-9"><span><%= info.getPositionCount()%></span></div>
                                                    </div>
                                                    <div class="row mb_0">&nbsp;</div>
                                                </div>

                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="mob_value">Onboarded <span><%=  onboarding.changeNum(info.getOnboardCount(), 2)%>&nbsp;/&nbsp;<%=  onboarding.changeNum(info.getTotalnoofopenings(), 2)%></span></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="com_view_job"><a href="javascript:;" onclick="ViewClient()"><img src="../assets/images/view.png"/><br/> View Asset</a></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">

                                        <div class="col-lg-6 col-md-6 col-sm-6 col-12">
                                            <div class="row">
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 short_list_area">
                                                    <div class="row client_head_search">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12"><h2>CANDIDATE(S) <span>- Travel, Accommodation & Onboarding Details</span></h2></div>
                                                    </div>

                                                    <div class="row">
                                                        <div class="col-md-12  ">
                                                            <div class="search_short_main gen_prof_list">
                                                                <ul>
                                                                    <%
                                                                        if (oinfo != null) {
                                                                            onboardflag = oinfo.getOnboardflag();
                                                                            onflag = oinfo.getOnflag();

                                                                            String date = oinfo.getMobilizedate();

                                                                            if (!oinfo.getPhoto().equals("")) {
                                                                                cphoto = file_path + oinfo.getPhoto();
                                                                            }
                                                                    %>
                                                                    <li class="odd_list_1">
                                                                        <div class="search_box">
                                                                            <div class="row">
                                                                                <div class="col-md-12 comp_view">
                                                                                    <div class="row">
                                                                                        <div class="col-md-12 client_prof_status">
                                                                                            <div class="row d-flex align-items-center">
                                                                                                <div class="col-md-2 com_view_prof cand_box_img">
                                                                                                    <div class="user_photo pic_photo">
                                                                                                        <div class="upload_file">
                                                                                                            <input id="upload1" type="file">
                                                                                                            <!-- <img src="../assets/images/empty_user.png"> -->
                                                                                                            <img src="<%= cphoto%>">
                                                                                                            <a href="javascript:;" onclick="viewCandidate('<%=oinfo.getCandidateId()%>');"><img src="../assets/images/view.png"></a>
                                                                                                        </div>
                                                                                                    </div>	
                                                                                                </div>

                                                                                                <div class="full_name_ic comp_view col-md-10 mb_0">
                                                                                                    <div class="row">
                                                                                                        <div class="full_name_ic col-md-12 mb_0">
                                                                                                            <div class="row">
                                                                                                                <div class="col-md-12">
                                                                                                                    <a href="javascript:;" class="tooltip_name" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Full Name" aria-label="Full Name"><i class="mdi mdi-account"></i></a>
                                                                                                                    <span><%= oinfo.getName()%> </span>
                                                                                                                </div>
                                                                                                                <div class="col-md-12">
                                                                                                                    <span><%= oinfo.getPosition()%></span>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                        <div class="col-md-12">
                                                                                                            <div class="row">
                                                                                                                <div class="col-md-5 dep_location">
                                                                                                                    <label><i class="ion ion-ios-briefcase"></i></label> 
                                                                                                                    <span>Arrival Date - <a href="javascript:;" onclick="javascript: getViewTravel('<%=oinfo.getShortlistId()%>', '1');" class="trav_his_view_details">View Details</a>
                                                                                                                    </span>
                                                                                                                </div>
                                                                                                                <div class="col-md-7 dep_location">
                                                                                                                    <label><i class="fas fa-bed"></i></label>
                                                                                                                    <span>Accommodation - <a href="javascript:;" onclick="javascript: getViewAccomm('<%=oinfo.getShortlistId()%>', '2');" class="trav_his_view_details">View Details</a>
                                                                                                                    </span>
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
                                                                        <div class="col-md-12 travel_status_value"><span>Status - <%= onboarding.getccStatusbyId(oinfo.getOnboardflag())%></span></div>
                                                                    </li>
                                                                    <%
                                                                        }
                                                                    %>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="row client_head_search">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12"><h2>EMAIL DETAILS</h2></div>
                                                    </div>
                                                    <div class="search_short_main com_label_value single_list">
                                                        <ul>
                                                            <%
                                                                ArrayList maillog_list = onboarding.getSummaryEmaillog(cinfo.getShortlistId());
                                                                if (maillog_list.size() > 0) {
                                                                    OnboardingInfo minfo;
                                                            %>
                                                            <li>
                                                                <div class="row">
                                                                    <div class="col-md-3"><label>Email Log</label></div>
                                                                    <div class="col-md-9">
                                                                        <div class="full_width veri_details">
                                                                            <%
                                                                                for (int i = 0; i < maillog_list.size(); i++) {
                                                                                    minfo = (OnboardingInfo) maillog_list.get(i);
                                                                                    if (minfo != null) {
                                                                            %>
                                                                            <ul>
                                                                                <li><%=minfo.getDate()%></li>
                                                                                <li class="circle_ic"><span>&nbsp;</span></li>
                                                                                <li><%=minfo.getSendby()%></li>
                                                                                <li class="circle_ic"><span>&nbsp;</span></li>
                                                                                <li>
                                                                                    <%
                                                                                        if (minfo.getMaillogId() > 0) {
                                                                                    %>
                                                                                    <a href="javascript:;" onclick="javascript: getselectionmodal('<%= minfo.getMaillogId()%>')" data-bs-toggle="modal" data-bs-target="#view_mail">View Record</a>
                                                                                    <%
                                                                                    } else {
                                                                                    %>
                                                                                    <span>View Record</span>
                                                                                    <%}%>
                                                                                </li>
                                                                            </ul>
                                                                            <%
                                                                                    }
                                                                                }
                                                                            %>
                                                                        </div>
                                                                    </div>
                                                                </div>	
                                                            </li>
                                                            <%
                                                                }
                                                                ArrayList reqdoc_list = onboarding.getdocumentdetailsByIds(cinfo.getReqdocId());
                                                                if (reqdoc_list.size() > 0) {
                                                                    OnboardingInfo rinfo;
                                                            %>
                                                            <li>
                                                                <div class="row">
                                                                    <div class="col-md-3"><label>Required Docs</label></div>
                                                                    <div class="col-md-9">
                                                                        <%                                                                            for (int i = 0; i < reqdoc_list.size(); i++) {
                                                                                rinfo = (OnboardingInfo) reqdoc_list.get(i);
                                                                                if (rinfo != null) {
                                                                        %>
                                                                        <b><%=rinfo.getDdlLabel()%></b></br>
                                                                        <%
                                                                                }
                                                                            }
                                                                        %>
                                                                    </div>
                                                                </div>	
                                                            </li>
                                                            <%}
                                                                if (onboardflag > 4) {
                                                            %>
                                                            <li>
                                                                <div class="row">
                                                                    <div class="col-md-3"><label>Sent Forms</label></div>
                                                                    <div class="col-md-9">
                                                                        <% if (oinfo.getUpflag() == 1) {%>
                                                                        <b>External Joining Formalities (Zip)</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                        <% if (onboardflag > 7) {%>
                                                                        <a href="<%=onboard_file_path + oinfo.getExtzipfile()%>">Download</a>
                                                                        <%}
                                                                        } else {
                                                                            ArrayList generatedFroms = onboarding.getFormalityForm(cinfo.getShortlistId());
                                                                            if (generatedFroms.size() > 0) {
                                                                                OnboardingInfo finfo;
                                                                                for (int i = 0; i < generatedFroms.size(); i++) {
                                                                                    finfo = (OnboardingInfo) generatedFroms.get(i);
                                                                                    if (finfo != null) {
                                                                        %>
                                                                        <b><%=finfo.getFormalityname()%></b>
                                                                        <%
                                                                            if (!finfo.getUploadeddoc().equals("")) {
                                                                        %>
                                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%=onboard_file_path + finfo.getUploadeddoc()%>');">View Record</a>
                                                                        <%}%></br><%
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        %>
                                                                    </div>
                                                                </div>	
                                                            </li>
                                                            <%
                                                                }
                                                            %>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>	
                                        </div>
                                        <div class="col-lg-6 col-md-6 col-sm-6 col-12">
                                            <div class="row">
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 short_list_area">
                                                    <div class="row client_head_search">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12"><h2>SUMMARY</h2></div>
                                                    </div>
                                                    <%
                                                        if (onboardflag > 3) {
                                                    %>    
                                                    <div class="row client_head_search">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12"><h2>JOINING SUMMARY</h2></div>
                                                    </div>
                                                    <div class="search_short_main com_label_value trav_summ_details">
                                                        <ul>
                                                            <li><div class="row"><div class="col-md-12"><label><b>DOCUMENTS CHECKS</b></label></div></div></li>
                                                            <li>
                                                                <div class="row">
                                                                    <div class="col-md-3"><label>Date</label></div>
                                                                    <div class="col-md-9"><span><%=cinfo.getChecklistdate()%></span></div>
                                                                </div>	
                                                            </li>
                                                            <li>
                                                                <div class="row">
                                                                    <div class="col-md-3"><label>Time</label></div>
                                                                    <div class="col-md-9"><span><%=cinfo.getChecklisttime()%></span></div>
                                                                </div>	
                                                            </li>
                                                            <li>
                                                                <div class="row">
                                                                    <div class="col-md-3"><label>Username</label></div>
                                                                    <div class="col-md-9"><span><%=cinfo.getChecklistBy()%></span></div>
                                                                </div>	
                                                            </li>
                                                            <%
                                                                String[] strReqchecklistIds = cinfo.getReqchecklistId().split(",");
                                                            %>
                                                            <li>
                                                                <div class="row">
                                                                    <div class="col-md-3"><label>Total Docs</label></div>
                                                                    <div class="col-md-9"><span><%=strReqchecklistIds.length%></span></div>
                                                                </div>	
                                                            </li>
                                                            <li>
                                                                <div class="row">
                                                                    <div class="col-md-3"><label>Doc Checklist</label></div>
                                                                    <div class="col-md-9">
                                                                        <div class="full_width veri_details">
                                                                            <ul>
                                                                                <li><a href="javascript:;" onclick="getDocCheckList('<%=cinfo.getShortlistId()%>', 'view')">View Record</a></li>
                                                                            </ul>
                                                                        </div>	
                                                                    </div>
                                                                </div>	
                                                            </li>
                                                        </ul>
                                                        <% if (onboardflag > 8) {%>
                                                        <ul>
                                                            <li><div class="row"><div class="col-md-12"><label><b>ONBOARD CANDIDATE</b></label></div></div></li>
                                                            <li>
                                                                <div class="row">
                                                                    <div class="col-md-3"><label>Date</label></div>
                                                                    <div class="col-md-9"><span><%=cinfo.getDate()%></span></div>
                                                                </div>	
                                                            </li>
                                                            <li>
                                                                <div class="row">
                                                                    <div class="col-md-3"><label>Time</label></div>
                                                                    <div class="col-md-9"><span><%=cinfo.getOntime()%></span></div>
                                                                </div>	
                                                            </li>
                                                            <li>
                                                                <div class="row">
                                                                    <div class="col-md-3"><label>Username</label></div>
                                                                    <div class="col-md-9"><span><%=cinfo.getChecklistBy()%></span></div>
                                                                </div>	
                                                            </li>
                                                            <li>
                                                                <div class="row">
                                                                    <div class="col-md-3"><label>Items Checklist</label></div>
                                                                    <div class="col-md-9">
                                                                        <div class="full_width veri_details">
                                                                            <ul>
                                                                                <li><a href="javascript:;" data-bs-toggle="modal" data-bs-target="#onboard_candidate_list_modal" onclick="getOnboardingkits('<%=cinfo.getOnboardingkits()%>');">View Record</a></li>
                                                                            </ul>
                                                                        </div>	
                                                                    </div>
                                                                </div>	
                                                            </li>
                                                        </ul>
                                                        <%}%>
                                                    </div>
                                                    <%}%>
                                                </div>
                                            </div>	
                                        </div>

                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                        <a href="javascript:;" onclick="gobackview();" class="save_btn">Go to Onboarding</a>
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

        <div id="req_documents_checklist_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="dmodaldocchecklist">

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
                        <span class="resume_title"> File</span>
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

        <div id="onboard_candidate_list_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="donboardkitmodal">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="view_mail" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content maildetails">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="donboardmailviewmodal">

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


        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
        <script src="../assets/time/components-date-time-pickers.min.js" type="text/javascript"></script>
        <script src="../assets/js/sweetalert2.min.js"></script>
    </html:form>
</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
