<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.clientlogin.ClientloginInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="clientlogin" class="com.web.jxp.clientlogin.Clientlogin" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int ctp = 1;
            if (session.getAttribute("MLOGININFO") == null) {
    %>
    <jsp:forward page="/managerindex.jsp"/>    
    <%
        }
        ClientloginInfo info = null;
        ClientloginInfo cinfo = null;
        if (session.getAttribute("CLIENTSELECTION_DETAIL") != null) {
            info = (ClientloginInfo) session.getAttribute("CLIENTSELECTION_DETAIL");
        }

        ArrayList shortcand_list = new ArrayList();
        if (session.getAttribute("SHORTLIST_LIST") != null) {
            shortcand_list = (ArrayList) session.getAttribute("SHORTLIST_LIST");
        }
        int total = shortcand_list.size();
        String file_path = clientlogin.getMainPath("view_candidate_file");
        String cvfile_path = clientlogin.getMainPath("view_resumetemplate_pdf");
        
        int sId = 0, intv_Id = 0;
        if(session.getAttribute("S_ID") != null)
        {
            sId = Integer.parseInt((String) session.getAttribute("S_ID"));
        }
        session.removeAttribute("S_ID");

        if(session.getAttribute("INTV_ID") != null)
        {
            intv_Id = Integer.parseInt((String) session.getAttribute("INTV_ID"));
        }
        session.removeAttribute("INTV_ID");

        String thankyou1 = "no";
        if (session.getAttribute("THANKYOU_2") != null) {
            thankyou1 = (String) session.getAttribute("THANKYOU_2");
            session.removeAttribute("THANKYOU_2");
        }

        String thankyou3 = "no";
        if (session.getAttribute("THANKYOU_3") != null) {
            thankyou3 = (String) session.getAttribute("THANKYOU_3");
            session.removeAttribute("THANKYOU_3");
        }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= clientlogin.getMainPath("title") != null ? clientlogin.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/time/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">	
        <link href="../assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">

        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/clientlogin.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet">

    <body data-sidebar="dark" data-keep-enlarged="true" class="ocs_feedback vertical-collpsed crew_user bg_image">
        <html:form action="/clientlogin/ClientloginAction.do" onsubmit="return false;">
            <html:hidden property="doSaveInterview"/>
            <html:hidden property="doEvaluation"/>
            <html:hidden property="type"/>
            <html:hidden property="clientId"/>
            <html:hidden property="jobpostId"/>
            <html:hidden property="search"/>
            <html:hidden property="assetIdIndex"/>
            <html:hidden property="positionIndex"/>
            <html:hidden property="clientIdIndex"/>
            <!-- Begin page -->
            <div id="layout-wrapper">
                <%@include file="../header_clientlogin.jsp" %>
                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-md-12 col-xl-12 heading_title"><h1><a href="javascript: goback();"><i class="ion ion-ios-arrow-round-back"></i></a> Selection</h1></div>

                                <div class="col-md-12 col-xl-12">
                                    <div class="body-background online_assessment">
                                        <div class="col-md-12 col-xl-12 m_30">
                                            <div class="row com_checks_main">
                                                <div class="col-md-12 com_top_right our_client_mode">
                                                    <div class="row d-flex align-items-center">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 com_label_value">
                                                            <div class="row mb_0">
                                                                <div class="col-lg-3 col-md-3 col-sm-3 col-12">
                                                                    <div class="row">
                                                                        <div class="col-lg-6 col-md-6 col-sm-12 col-12"><label>Position - Rank</label></div>
                                                                        <div class="col-lg-6 col-md-6 col-sm-12 col-12"><span><%=info.getPositionname()%></span></div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-5 col-md-5 col-sm-6 col-12">
                                                                    <div class="row">
                                                                        <div class="col-lg-3 col-md-3 col-sm-12 col-12"><label>Client - Asset</label></div>
                                                                        <div class="col-lg-9 col-md-9 col-sm-12 col-12"><span><%=info.getClientName()%></span></div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-3 col-12">
                                                                    <div class="row">
                                                                        <div class="col-lg-4 col-md-4 col-sm-12 col-12"><label>Education</label></div>
                                                                        <div class="col-lg-8 col-md-8 col-sm-12 col-12"><span><%=info.getEducation()%></span></div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                                    
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 com_label_value">
                                                            <div class="row mb_0">
                                                                <div class="col-lg-3 col-md-3 col-sm-3 col-4">
                                                                    <div class="row">
                                                                        <div class="col-lg-6 col-md-6 col-sm-12 col-12"><label>Reference No.</label></div>
                                                                        <div class="col-lg-6 col-md-6 col-sm-12 col-12"><span><%=clientlogin.changeNum(info.getJobpostId(), 6)%></span></div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-5 col-md-5 col-sm-6 col-4">
                                                                    <div class="row">
                                                                        <div class="col-lg-3 col-md-3 col-sm-12 col-12"><label>Posting Date</label></div>
                                                                        <div class="col-lg-9 col-md-9 col-sm-12 col-12"><span><%=info.getDate()%></span></div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-3 col-4">
                                                                    <div class="row">
                                                                        <div class="col-lg-4 col-md-4 col-sm-12 col-12"><label>Experience</label></div>
                                                                        <div class="col-lg-8 col-md-8 col-sm-12 col-12"><span><%=info.getExpMin()%>-<%=info.getExpMax()%> Yrs</span></div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                         <%
                                                    if (total > 0) 
                                                    {
                                                        %>
                                        <div class="col-md-12 col-xl-12 m_30 short_list_area client_search_title">
                                            <div class="row justify-content-between client_head_search">
                                                <div class="col-lg-4 col-md-6 col-sm-4 col-12"><h2>CANDIDATES <span></span></h2></div>
                                                <div class="col-lg-4 col-md-6 col-sm-8 col-12">
                                                    <div class="input-group bootstrap-touchspin bootstrap-touchspin-injected short_list_search">
                                                        <div class="search_mode">
                                                            <span class="input-group-btn input-group-prepend search_label">Search:</span>
                                                            <input id="search2" type="text" value="" name="search2" class="form-control" placeholder=""  readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                        this.removeAttribute('readonly');
                                                                        this.blur();
                                                                        this.focus();
                                                                    }">
                                                            <a href="javascript: getSearchShortCandList();" class="search_view"><img src="../assets/images/view_icon.png"></a>
                                                        </div>

                                                        <a href="javascript:;" class="input-group-btn input-group-append search_filter">Filter</a>
                                                    </div>	
                                                </div>
                                            </div>
                                        </div> 
                                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                            
                                            <div class="row">

                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <div class="row">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 short_list_area">
                                                            <div class="search_short_main client_sel_candidate user_client_box">
                                                                <ul id="dshortlistedcandidate">
<%
                                                    if (total > 0) 
                                                    {

                                                        String tempImg = "";
                                                        for (int i = 0; i < total; i++) 
                                                        {
                                                            cinfo = (ClientloginInfo) shortcand_list.get(i);
                                                            if (cinfo != null) 
                                                            {
                                                                tempImg = "";                                                                   

%>
                                                                    <li class="odd_list_1">
                                                                        <div class="search_box">
                                                                            <div class="row">
                                                                                <div class="col-lg-9 col-md-9 col-sm-9 col-9 comp_view">
                                                                                    <div class="row">
                                                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 client_prof_status">
                                                                                            <div class="row d-flex align-items-start">
                                                                                                <div class="col-lg-3 col-md-3 col-sm-3 col-4 com_view_prof cand_box_img">
                                                                                                    <div class="user_photo pic_photo">

                                                                                                        <div class="upload_file">
                                                                                                            <input id="upload1" type="file">
                                                                                                            <%     if (cinfo.getPhoto().equals("")) {
                                                                                                            tempImg = "../assets/images/empty_user_100x100.png";
                                                                                                        } else {
                                                                                                            tempImg = file_path + cinfo.getPhoto();
                                                                                                        }
                                                                                                            %>
                                                                                                            <img src="<%=tempImg%>">
                                                                                                        </div>
                                                                                                    </div>	
                                                                                                </div>
                                                                                                <div class="col-lg-9 col-md-9 col-sm-9 col-8">
                                                                                                    <div class="row">
                                                                                                        <div class="portlet box status_on_hold">
                                                                                                                
                                                                                                            <div class="portlet-title">
                                                                                                    <div class="caption">Status -
                                                                                                    <%if(cinfo.getIflag() == 1 ){%>
                                                                                                        Interview Details Saved 
                                                                                                    <%} else if (cinfo.getIflag() == 2 ){%>
                                                                                                        <%if(cinfo.getType2() == 1) {%>
                                                                                                            Not Responded 
                                                                                                        <%}else{%>
                                                                                                            Interview Invite Sent
                                                                                                        <%}%>
                                                                                                    <%}else if (cinfo.getIflag() == 3 ){%>
                                                                                                        Unavailable for Interview   
                                                                                                    <%}else if (cinfo.getIflag() == 4){%>                                                                                                        
                                                                                                        Available for Interview
                                                                                                    <%}else if (cinfo.getIflag() == 5){%>
                                                                                                        Selected   
                                                                                                    <%} else if (cinfo.getIflag() == 6){%>
                                                                                                        <a href="javascript:;" onclick=" javascript: getScheduledModal('<%= cinfo.getInterviewId()%>', '3');" data-bs-toggle="modal" data-bs-target="#interviewsc_modal">Rejected by Client</a>   
                                                                                                    <%}else {%>
                                                                                                        Interview pending
                                                                                                    <%}%>
                                                                                                </div>
                                                                                                    <div class="actions">
                                                                                                        <%if (cinfo.getIflag() == 3 ){%>
                                                                                                            <a href="javascript:;" onclick=" javascript: getScheduledModal('<%= cinfo.getInterviewId()%>', '4');" data-bs-toggle="modal" data-bs-target="#interviewsc_modal"><i class="ion ion-md-information-circle-outline"></i> </a>
                                                                                                        <%}else if((cinfo.getIflag() == 1  || cinfo.getIflag() == 2) && cinfo.getType2() == 1){%>
                                                                                                            <a href="javascript:;" onclick=" javascript: getScheduledModal('<%= cinfo.getInterviewId()%>', '1');" data-bs-toggle="modal" data-bs-target="#interviewsc_modal"><i class="ion ion-md-information-circle-outline"></i> </a>
                                                                                                        <%}%>
                                                                                                    </div> 
                                                                                                </div>
                                                                                                            
                                                                                                            
                                                                                                            <div class="portlet-body">
                                                                                                                <div class="row">
                                                                                                                    <div class="col-lg-12 col-md-12 col-md-12 col-md-12 com_label_value">
                                                                                                                        <div class="row mb_0">
                                                                                                                            <div class="col-lg-3 col-md-3 col-sm-4 col-4"><label>Interview Date</label></div>
                                                                                                                            <div class="col-lg-9 col-md-9 col-sm-8 col-8"><span><%=cinfo.getDate() != null ? cinfo.getDate(): "-" %></span></div>
                                                                                                                        </div>
                                                                                                                        <div class="row mb_0">
                                                                                                                            <div class="col-lg-3 col-md-3 col-sm-4 col-4"><label>Recruiter</label></div>
                                                                                                                            <div class="col-lg-9 col-md-9 col-sm-8 col-8"><span><%=cinfo.getInterviewer() != null ? cinfo.getInterviewer(): "-" %></span></div>
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="full_name_ic col-md-12 mb_0">
                                                                                            <a href="javascript:;" class="tooltip_name" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Full Name" aria-label="Full Name"><i class="mdi mdi-account"></i></a>
                                                                                            <span><%=cinfo.getName()%> </span>
                                                                                        </div>

                                                                                        <div class="posi_rank_ic col-md-12 mb_0">
                                                                                            <a href="javascript:;" class="tooltip_name" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Position-Rank" aria-label="Position-Rank"><i class="mdi mdi-star-circle"></i></a>
                                                                                            <span><%=cinfo.getPositionname()%></span>
                                                                                        </div>

                                                                                        <div class="expe_ic col-md-12 mb_0">
                                                                                            <a href="javascript:;" class="tooltip_name" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Experience" aria-label="Experience"><i class="mdi mdi-lightbulb"></i></a>
                                                                                            <span><%=cinfo.getExperience()%> Yrs-<%=cinfo.getType2()%></span>
                                                                                        </div>

                                                                                        <div class="gradu_ic col-md-12 mb_0">
                                                                                            <a href="javascript:;" class="tooltip_name" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Education" aria-label="Education"><i class="fas fa-graduation-cap"></i></a>
                                                                                            <span><%=cinfo.getQualification()%></span>
                                                                                        </div>

                                                                                        <div class="brief_ic col-md-12">
                                                                                            <a href="javascript:;" class="tooltip_name" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Last Job" aria-label="Last Job"><i class="ion ion-ios-briefcase"></i></a>
                                                                                            <span><%=cinfo.getCompany()%></span>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="col-lg-3 col-md-3 col-sm-3 col-3 add_view_area client_se_vi_re">
                                                                                    <div class="row">
                                                                                        <div class="search_add_btn">
                                                                                            <%if(cinfo.getIflag() == 1 ){%>
                                                                                                <a href="javascript:;" onclick=" javascript: getEmailModal('<%= cinfo.getShortlistId()%>', '<%= cinfo.getInterviewId()%>');" data-bs-toggle="modal" data-bs-target="#mail_modal">Send Mail To Candidate</a> 
                                                                                            <%} else if (cinfo.getIflag() == 2 ){%>
                                                                                                <%if(cinfo.getType2() == 1) {%>
                                                                                                    <a href="javascript:;" onclick=" javascript: getRescheduleModel('<%= cinfo.getInterviewId()%>');" data-bs-toggle="modal" data-bs-target="#interviewre_modal">Not Responded</a>
                                                                                                <%}else{%>
                                                                                                    <a href="javascript:;" onclick=" javascript:;" >Interview Invite Sent</a>
                                                                                                <%}%>
                                                                                            <%}else if (cinfo.getIflag() == 3 ){%>
                                                                                                <a href="javascript:;" onclick=" javascript: getRescheduleModel('<%= cinfo.getInterviewId()%>');" data-bs-toggle="modal" data-bs-target="#interviewre_modal">Re-Schedule Interview</a>   
                                                                                            <%}else if (cinfo.getIflag() == 4){%>
                                                                                            <%if(cinfo.getType() == 1) {%>
                                                                                                <a href="javascript:;" onclick=" javascript: getScheduledModal('<%= cinfo.getInterviewId()%>', '1');" data-bs-toggle="modal" data-bs-target="#interviewsc_modal">Interview Scheduled</a>  
                                                                                            <%}else{%>
                                                                                                <a href="javascript:;" onclick=" javascript: getEvaluateModal('<%= cinfo.getInterviewId()%>', '2');" data-bs-toggle="modal" data-bs-target="#evaluate_modal">Evaluate Candidate</a>   
                                                                                            <%}%>
                                                                                            <%}else if (cinfo.getIflag() == 5){%>
                                                                                                
                                                                                            <%} else if (cinfo.getIflag() == 6){%>
                                                                                                <a href="javascript:;" onclick=" javascript: getScheduledModal('<%= cinfo.getInterviewId()%>', '3');" data-bs-toggle="modal" data-bs-target="#interviewsc_modal">Rejected by Client</a>   
                                                                                            <%}else {%>
                                                                                                <a href="javascript:;" onclick=" javascript: getModel('<%= cinfo.getShortlistId()%>');" data-bs-toggle="modal" data-bs-target="#interview_modal">Schedule Interview</a>
                                                                                            <%}%>


                                                                                        </div>

                                                                                        <div class="search_view_prof com_view_job with_reject d-none1">
                                                                                            <a href="javascript:;" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript: setIframe('<%=(cvfile_path+cinfo.getPdffileName())%>');"><img src="../assets/images/view.png"/><br> View CV</a>	
                                                                                        </div>
                                                                                        <div class="search_view_prof client_reject">
                                                                                            <% if (cinfo.getIflag() != 5){%>
                                                                                                <a href="javascript: ;" onclick=" javascript: getRejectModal('<%= cinfo.getShortlistId()%>');" data-bs-toggle="modal" data-bs-target="#reject_modal">Reject</a>
                                                                                            <%}%>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            </div>
                                                                    </li>
                                                                    <%
                                                                        }
    }
    }
                                                                    %>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                    </div>	
                                                </div>
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



            <div id="interview_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12" id='int_div'>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="interviewre_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12" id='intre_div'>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="interviewsc_modal" class="modal fade parameter_modal interview_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12" id='scint_div'>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="mail_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><a href="javascript: view('<%= info.getJobpostId()%>');"><i class="ion ion-md-close"></i></a></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12" id='emailmodal'>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div id="evaluate_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12" id='evalint_div'>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div id="reject_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12" id='rejectModal'>                                    
                                    
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
                                    <iframe id='iframe' class="doc" src=""></iframe>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="thank_you_modal_1" class="modal fade thank_you_modal" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><a href="javascript: view('<%= info.getJobpostId()%>');"><i class="ion ion-md-close"></i></a></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <h2>Thank You!</h2>
                                    <center><img src="../assets/images/thank-you.png"></center>
                                    <h3>Email Sent</h3>
                                    <p>Interview Details has been successfully sent to the Candidate.</p>
                                    <a href="javascript: view('<%= info.getJobpostId()%>');" class="msg_button" style="text-decoration: underline;">Selection</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="thank_you_modal_2" class="modal fade thank_you_modal" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><a href="javascript: view('<%= info.getJobpostId()%>');"><i class="ion ion-md-close"></i></a></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <h2>Thank You!</h2>
                                    <center><img src="../assets/images/thank-you.png"></center>
                                    <h3>Email Sent</h3>
                                    <p>Candidate evaluation has been successfully sent to the coordinator.</p>
                                    <a href="javascript: view('<%= info.getJobpostId()%>');" class="msg_button" style="text-decoration: underline;">Selection</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="thank_you_modal_3" class="modal fade thank_you_modal" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><a href="javascript: view('<%= info.getJobpostId()%>');"><i class="ion ion-md-close"></i></a></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <h2>Thank You!</h2>
                                    <center><img src="../assets/images/thank-you.png"></center>
                                    <h3>Email Sent</h3>
                                    <p>Candidate rejection mail has been successfully sent to the coordinator.</p>
                                    <a href="javascript: view('<%= info.getJobpostId()%>');" class="msg_button" style="text-decoration: underline;">Selection</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="reject_modal" class="modal fade thank_you_modal reject_modal blur_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <p>Rejected candidate will be removed from the <br/> job post selection list for the client.</p>
                                    <h4>Are you sure?</h4>
                                    <a href="javascript:;" class="conf_btn" data-bs-toggle="modal" data-bs-target="#reject_status_modal">Confirm</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <script src="../assets/libs/jquery/jquery.min.js"></script>
            <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
            <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script>
            <script src="../assets/js/bootstrap-multiselect.js"></script>
            <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
            <script src="../assets/js/app.js"></script>
            <!-- Responsive Table js -->
            <script src="../assets/js/bootstrap-select.min.js"></script>
            <script src="../assets/js/table-responsive.init.js"></script>
            <script src="../assets/js/sweetalert2.min.js"></script>
            <script src="../assets/js/bootstrap-datepicker.min.js"></script>
            <script src="../assets/time/components-date-time-pickers.min.js" type="text/javascript"></script>
            <script src="../assets/time/bootstrap-timepicker.min.js" type="text/javascript"></script>

            <% if(sId > 0){%>
            <script type="text/javascript">
                $(window).on('load', function () {
                    getEmailModal('<%=sId%>', <%=intv_Id%>);
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
            <script type="text/javascript">
                $(document).ready(function () {
                    $('#rejectReason').multiselect({
                        nonSelectedText: '- Select -',
                        includeSelectAllOption: true,
                        maxHeight: 200,
                        enableFiltering: false,
                        enableCaseInsensitiveFiltering: false,
                        buttonWidth: '100%'
                    });
                });
            </script>

            <script type="text/javascript">
                jQuery(document).ready(function () {
                    $(".kt-selectpicker").selectpicker();
                    $(".wesl_dt").datepicker({
                        todayHighlight: !0,
                        format: "dd-M-yyyy",
                        autoclose: "true",
                        orientation: "bottom"
                    });
                });
            </script>

            <script>
                //Timepicker
                $(".timepicker-24").timepicker({
                    format: 'mm:mm',
                    autoclose: !0,
                    minuteStep: 5,
                    showSeconds: !1,
                    showMeridian: !1
                });
            </script>
            <script>
                $(document).ready(function () {
                    $('input').attr('autocomplete', 'off');
                });
            </script>
            <%if (thankyou1.equals("yes")) {%>
            <script type="text/javascript">
                $(window).on('load', function () {
                    $('#thank_you_modal_2').modal('show');
                });
            </script>
            <%}%>
            <%if (thankyou3.equals("yes")) {%>
            <script type="text/javascript">
                $(window).on('load', function () {
                    $('#thank_you_modal_3').modal('show');
                });
            </script>
            <%}%>
        </html:form>
    </body>

    <%
        } catch (Exception e) {
            e.printStackTrace();
        }
    %>
</html>
