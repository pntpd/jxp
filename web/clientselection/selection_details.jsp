<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.clientselection.ClientselectionInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="clientselection" class="com.web.jxp.clientselection.Clientselection" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 2, submtp = 51, allclient = 0, companytype = 0;
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
        ClientselectionInfo info = null;
        if (session.getAttribute("CLIENTSELECTIONJOBPOST_DETAIL") != null) {
            info = (ClientselectionInfo) session.getAttribute("CLIENTSELECTIONJOBPOST_DETAIL");
        }

        ArrayList shortcand_list = new ArrayList();
        if (session.getAttribute("SHORTLISTEDCANDIDATE_LIST") != null) {
            shortcand_list = (ArrayList) session.getAttribute("SHORTLISTEDCANDIDATE_LIST");
        }
        int total = shortcand_list.size();
        String file_path = clientselection.getMainPath("view_candidate_file");
        String cvfile_path = clientselection.getMainPath("view_resumetemplate_pdf");

        String thankyou = "no";
        if(request.getAttribute("CANDIDATESELECTEDMODAL") != null)
        {
            thankyou = (String)request.getAttribute("CANDIDATESELECTEDMODAL");
            request.removeAttribute("CANDIDATESELECTEDMODAL");
        }
        ArrayList rejectionreason = clientselection.getRejectReasonList();
    %>
    <head>
        <meta charset="utf-8">
        <title><%= clientselection.getMainPath("title") != null ? clientselection.getMainPath("title") : ""%></title>
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
        <link href="../assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">

        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/clientselection.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet">

    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/clientselection/ClientselectionAction.do" onsubmit="return false;">
        <html:hidden property="doGenerate"/>
        <html:hidden property="doGenerateoffer"/>
        <html:hidden property="doView"/>
        <html:hidden property="doSelect"/>
        <html:hidden property="doReject"/>
        <html:hidden property="doAccept"/>
        <html:hidden property="doDecline"/>
        <html:hidden property="doSummary"/>
        <html:hidden property="jobpostId"/>
        <html:hidden property="clientId"/>
        <html:hidden property="shortlistId"/>
        <html:hidden property="candidateId"/>
        <html:hidden property="sflag"/>
        <html:hidden property="oflag"/>
        <html:hidden property="from"/>
        <html:hidden property="type"/>
        <html:hidden property="doRelease"/>
        <html:hidden property="clientIdIndex"/>
        <html:hidden property="assetIdIndex"/>
        <html:hidden property="pgvalue"/>
        <html:hidden property="search"/>
        <!-- Begin page -->
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content tab_panel1 no_tab1">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow"><a href="javascript: goback();"><img  src="../assets/images/back-arrow.png"/></a> <span>Client Selection</span></div>
                            <div class="float-end">
                                <div class="toggled-off usefool_tool">
                                    <div class="toggle-title">
                                        <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                        <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>
                                    </div>
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
                                <div class="body-background com_checks" id="dCandidateList">
                                    <div class="row com_checks_main">

                                        <div class="col-md-12 com_top_right">
                                            <div class="row d-flex align-items-center">
                                                <div class="col-md-5 com_label_value">
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Position - Rank</label></div>
                                                        <div class="col-md-9"><span><%=info.getPosition()%></span></div>
                                                    </div>
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Client - Asset</label></div>
                                                        <input type="hidden" id="hdnclientasset" name="hdnclientasset" value="<%=info.getClientAsset()%>"/>
                                                        <div class="col-md-9"><span><%=info.getClientName()%></span></div>
                                                    </div>
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Education</label></div>
                                                        <div class="col-md-9"><span><%=info.getEducation()%></span></div>
                                                    </div>
                                                </div>
                                                <div class="col-md-3 com_label_value">
                                                    <div class="row mb_0">
                                                        <div class="col-md-5"><label>Reference No.</label></div>
                                                        <div class="col-md-7"><span><%=clientselection.changeNum(info.getJobpostId(), 6)%></span></div>
                                                    </div>
                                                    <div class="row mb_0">
                                                        <div class="col-md-5"><label>Posting Date</label></div>
                                                        <div class="col-md-7"><span><%=info.getDate()%></span></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-5"><label>Experience</label></div>
                                                        <div class="col-md-7"><span><%=info.getExpMin()%>-<%=info.getExpMax()%> Yrs</span></div>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="accept_value">
                                                        <ul>
                                                            <li><label>Accepted</label> <span><%=clientselection.changeNum(info.getSelcount(),2)%></span> / <span><%=clientselection.changeNum(info.getOpening(),2)%></span></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope status_area client_sec_area">
                                                        <ul>
                                                            <li class="com_ope_job">Shortlisted <span><%=clientselection.changeNum(info.getShortlistcount(),2)%></span></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="com_view_job"><a href="javascript:ViewJobpost('');"><img src="../assets/images/view.png"/><br/> View Job Post</a></li>
                                                            <li class="com_ope_job">Opening <span><%=clientselection.changeNum(info.getOpening(),2)%></span></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                            <div class="row">
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 short_list_area">
                                                    <div class="row client_head_search">
                                                        <div class="col-lg-4 col-md-6 col-sm-12 col-12"><h2>CANDIDATES <span>- Compliance Checks Complete</span></h2></div>
                                                        <div class="col-lg-5 col-md-6 col-sm-12 col-12">
                                                            <div class="row">
                                                                <div class="col-lg-5 col-md-6 col-sm-12 col-12">
                                                                    <select class="form-select" onchange="getSearchShortCandList()" id="selstatus">
                                                                        <option value="0">- Select -</option>
                                                                        <option value="1">On Hold</option>
                                                                        <option value="2">Selected</option>
                                                                        <option value="3">Rejected</option>
                                                                    </select>
                                                                </div>
                                                                <div class="col-lg-6 col-md-6 col-sm-12 col-12" id="dsubstatus" style="display: none">
                                                                    <select class="form-select" onchange="getSearchShortCandList()" id="selsubstatus">
                                                                        <option value="0">- Select -</option>
                                                                        <option value="1">Generate Offer</option>
                                                                        <option value="2">Email Candidate</option>
                                                                        <option value="3">Offer Sent</option>
                                                                        <option value="4">Accepted</option>
                                                                        <option value="5">Declined</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-3 col-md-6 col-sm-12 col-12">
                                                            <div class="input-group bootstrap-touchspin bootstrap-touchspin-injected short_list_search">
                                                                <div class="search_mode">
                                                                    <span class="input-group-btn input-group-prepend search_label">Search:</span>
                                                                    <input id="search" type="text" value="" name="search2" class="form-control" placeholder=""  readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                                this.removeAttribute('readonly');
                                                                                this.blur();
                                                                                this.focus();
                                                                            }">
                                                                    <a class="search_view" onclick="getSearchShortCandList()"><img src="../assets/images/view_icon.png"></a>
                                                                </div>
                                                                <a href="javascript:;" class="input-group-btn input-group-append search_filter">Filter</a>
                                                            </div>


                                                        </div>	
                                                    </div>
                                                </div>

                                                <div class="search_short_main client_sel_candidate">
                                                    <ul id="dshortlistedcandidate">
                                                        <%
                                                            if (total > 0) {
                                                                ClientselectionInfo cinfo;
                                                                String tempImg = "";
                                                                String tempclass = "";
                                                                String classAddone = "";
                                                                String tempclass1 = "";
                                                                for (int i = 0; i < total; i++) {
                                                                    cinfo = (ClientselectionInfo) shortcand_list.get(i);
                                                                    if (cinfo != null) {
                                                                        tempImg = "";
                                                                        tempclass = "status_on_hold";
                                                                        tempclass1 = "client_se_vi_re";
                                                                        if (companytype == 2 && (cinfo.getSflag() ==1 || cinfo.getSflag()==2)) {
                                                                        } else {

                                                        %>
                                                        <li class="odd_list_1">
                                                            <div class="search_box">
                                                                <div class="row">
                                                                    <div class="col-md-9 comp_view">
                                                                        <div class="row">
                                                                            <div class="col-md-12 client_prof_status">
                                                                                <div class="row d-flex align-items-start">
                                                                                    <div class="col-md-3 com_view_prof cand_box_img">
                                                                                        <div class="user_photo pic_photo">
                                                                                            <div class="upload_file">
                                                                                                <%     if (cinfo.getPhoto().equals("")) {
                                                                                                        tempImg = "../assets/images/empty_user_100x100.png";
                                                                                                    } else {
                                                                                                        tempImg = file_path + cinfo.getPhoto();
                                                                                                    }
                                                                                                %>
                                                                                                <img src="<%=tempImg%>">
                                                                                                <a href="javascript:viewCandidate('<%= cinfo.getCandidateId()%>');"><img src="../assets/images/view.png"></a>
                                                                                            </div>
                                                                                        </div>	
                                                                                    </div>
                                                                                    <div class="col-md-9">
                                                                                        <div class="row">
                                                                                            <%
                                                                                             if (cinfo.getSflag() == 4) {
                                                                                                 tempclass = "status_selected";
                                                                                             }else if (cinfo.getSflag() == 5) {
                                                                                                 tempclass = "status_rejected";
                                                                                             }
                                                                                            %>
                                                                                            <div class="portlet box <%=tempclass%>">
                                                                                                <div class="portlet-title">
                                                                                                    <div class="caption">Status -
                                                                                                        <%     if (cinfo.getSflag() == 1 || cinfo.getSflag() == 2 || cinfo.getSflag() == 3) {
                                                                                                        %>On Hold<%
                                                                                                        } else if (cinfo.getSflag() == 4) {
                                                                                                        %>Selected<%
                                                                                                        } else if (cinfo.getSflag() == 5) {
                                                                                                        %>Rejected<%
                                                                                                            }
                                                                                                        %>
                                                                                                    </div>
                                                                                                    <div class="actions">
                                                                                                        <%     if ((cinfo.getSflag() == 3 || cinfo.getSflag() == 4 || cinfo.getSflag() == 5) && companytype == 1) {
                                                                                                        %> <a href="javascript: getCandidateSummary('<%=cinfo.getShortlistId()%>');" class=""><i class="ion ion-md-information-circle-outline"></i> </a><%
                                                                                                        }
                                                                                                        %>
                                                                                                    </div> 
                                                                                                </div>
                                                                                                <div class="portlet-body">
                                                                                                    <div class="row">
                                                                                                        <div class="col-md-12 com_label_value">
                                                                                                            <div class="row mb_0">
                                                                                                                <div class="col-md-2"><label>Date</label></div>
                                                                                                                <div class="col-md-10"><span>
                                                                                                                        <%     if (cinfo.getSflag() == 3) {
                                                                                                                        %> <%=cinfo.getMailon()%><%
                                                                                                                        } else if (cinfo.getSflag() == 4 || cinfo.getSflag() == 5) {
                                                                                                                        %><%=cinfo.getSron()%><%} else {%>
                                                                                                                        -
                                                                                                                        <%}%>
                                                                                                                    </span>
                                                                                                                </div>
                                                                                                            </div>
                                                                                                            <div class="row mb_0">
                                                                                                                <div class="col-md-2"><label>User</label></div>
                                                                                                                <div class="col-md-10"><span>
                                                                                                                        <%     if (cinfo.getSflag() == 3) {
                                                                                                                        %> <%=cinfo.getMailby()%><%
                                                                                                                        } else if (cinfo.getSflag() == 4 || cinfo.getSflag() == 5) {
                                                                                                                        %> <%=cinfo.getSrby()%><%} else {%>
                                                                                                                        -
                                                                                                                        <%}%>
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
                                                                            <div class="full_name_ic col-md-12 mb_0">
                                                                                <a href="javascript:;" class="tooltip_name" data-toggle="tooltip" data-placement="top" title="Full Name"><i class="mdi mdi-account"></i></a>
                                                                                <span><%=cinfo.getName()%></span>
                                                                            </div>

                                                                            <div class="posi_rank_ic col-md-12 mb_0">
                                                                                <a href="javascript:;" class="tooltip_name" data-toggle="tooltip" data-placement="top" title="Position-Rank"><i class="mdi mdi-star-circle"></i></a>
                                                                                <span><%=cinfo.getPosition()%></span>
                                                                            </div>

                                                                            <div class="expe_ic col-md-12 mb_0">
                                                                                <a href="javascript:;" class="tooltip_name" data-toggle="tooltip" data-placement="top" title="Experience"><i class="mdi mdi-lightbulb"></i></a>
                                                                                <span><%=cinfo.getExperience()%> Yrs</span>
                                                                            </div>

                                                                            <div class="gradu_ic col-md-12 mb_0">
                                                                                <a href="javascript:;" class="tooltip_name" data-toggle="tooltip" data-placement="top" title="Education"><i class="fas fa-graduation-cap"></i></a>
                                                                                <span><%=cinfo.getQualification()%></span>
                                                                            </div>

                                                                            <div class="brief_ic col-md-12">
                                                                                <a href="javascript:;" class="tooltip_name" data-toggle="tooltip" data-placement="top" title="Last Job"><i class="ion ion-ios-briefcase"></i></a>
                                                                                <span><%=cinfo.getCompany()%></span>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <%
                                                                     if ((cinfo.getSflag() == 4 && cinfo.getSubstatus() != 3) || cinfo.getSflag() == 5){
                                                                         tempclass1="";
                                                                     }
                                                                    %>
                                                                    <div class="col-md-3 add_view_area client_se_vi_re <%=tempclass1%>">
                                                                        <div class="row">
                                                                            <div class="search_add_btn">
                                                                                <%if (cinfo.getSflag() == 1) {
                                                                                %>  <a href="javascript: getGenerateCV('<%= cinfo.getShortlistId()%>','<%= cinfo.getSflag()%>','<%= cinfo.getCandidateId()%>')">Generate CV</a><%
                                                                                } else if (cinfo.getSflag() == 2) {
                                                                                %> <a href="javascript:;" onclick=" javascript: getModel('<%= cinfo.getShortlistId()%>', '<%= cinfo.getSflag()%>');" data-bs-toggle="modal" data-bs-target="#mail_modal">Email Client</a>
                                                                                <%} else if (cinfo.getSflag() == 3) {
                                                                                %> <a href="javascript: getSelectCandidate('<%=cinfo.getName().replaceAll("'", "")%>','<%=cinfo.getCountry()%>','<%=clientselection.changeNum(info.getJobpostId(), 6)%>','<%=cinfo.getShortlistId()%>');">Selected by Client</a>
                                                                                <%} else if ((cinfo.getSflag() == 4 && cinfo.getSubstatus() == 1) && companytype == 1) {
                                                                                %> 
                                                                                    <a href="javascript: getGenerateoffer('<%= cinfo.getShortlistId()%>','<%= cinfo.getSflag()%>','<%= cinfo.getCandidateId()%>','<%= cinfo.getSubstatus()%>', '1');">Generate Offer</a>
                                                                                <%} else if(cinfo.getSflag() == 5 && cinfo.getProgressId() == 1){%>
                                                                                    <a href="javascript: getGenerateoffer('<%= cinfo.getShortlistId()%>','<%= cinfo.getSflag()%>','<%= cinfo.getCandidateId()%>','<%= cinfo.getSubstatus()%>', '2');">Re-Generate Offer</a>
                                                                                <%} else if ((cinfo.getSflag() == 4 && cinfo.getSubstatus() == 2) && companytype == 1) {
                                                                                %> <a href="javascript:;" onclick=" javascript: getModelforoffer('<%=cinfo.getShortlistId()%>', '<%=cinfo.getSflag()%>', '<%=cinfo.getSubstatus()%>');" data-bs-toggle="modal" data-bs-target="#mail_modal">Email Candidate</a>
                                                                                <%} else if ((cinfo.getSflag() == 4 && cinfo.getSubstatus() == 3) && companytype == 1) {
                                                                                %> <a href="javascript:;" onclick="getsetModal('<%= cinfo.getShortlistId()%>', '<%= cinfo.getCandidateId()%>', '0', '0');" data-bs-toggle="modal" data-bs-target="#acc_offer_letter_modal">Accepted by Candidate</a>
                                                                                <%} else if ((cinfo.getSflag() == 4 && cinfo.getSubstatus() == 4) && companytype == 1) {
                                                                                %> <a class="accepted_label" href="javascript:;" >Accepted</a>
                                                                                <%} else if ((cinfo.getSflag() == 4 && cinfo.getSubstatus() == 5) && companytype == 1) {
                                                                                %> <a class="declined_label" href="javascript:;">Declined</a>
                                                                                <%}%>
                                                                            </div>
                                                                            <% if (cinfo.getSflag() == 5 && cinfo.getProgressId() == 1) {%>
                                                                                <div class="search_view_prof com_view_job with_reject client_reject">
                                                                                    <a href="javascript: releaseCandidate('<%= cinfo.getCandidateId()%>', '<%= cinfo.getShortlistId()%>');">Release</a>	
                                                                                </div>
                                                                            <%}%>
                                                                            <% if ((cinfo.getSflag() == 4 && (cinfo.getSubstatus() == 4 || cinfo.getSubstatus() == 5))&& companytype == 2) {
                                                                            %><div class="com_view_job">
                                                                                <a href="javascript:;" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript: setIframe('<%=(cvfile_path+cinfo.getOfferpdffile())%>');"><img src="../assets/images/view.png"/><br> View Offer Letter</a>
                                                                            </div>
                                                                            <%}  if (cinfo.getSflag() == 2 || cinfo.getSflag() == 3 || cinfo.getSflag() == 4 || cinfo.getSflag() == 5) {
                                                                            classAddone = "";
                                                                                if (cinfo.getSflag() == 2 || (cinfo.getSflag() == 4 && (cinfo.getSubstatus() == 1 ||  cinfo.getSubstatus() == 2 || cinfo.getSubstatus() == 4 || cinfo.getSubstatus() == 5 || companytype == 2)) || cinfo.getSflag() == 5){
                                                                                    classAddone = "1";
                                                                                    }
                                                                            %>
                                                                            <div class="search_view_prof com_view_job with_reject<%=classAddone%> d-none1">
                                                                                <%  if ((cinfo.getSflag() == 2 || cinfo.getSflag() == 3) && companytype == 1) {%>
                                                                                <a href="javascript: getGenerateCV('<%= cinfo.getShortlistId()%>','<%= cinfo.getSflag()%>','<%= cinfo.getCandidateId()%>');"><img src="../assets/images/view.png"><br> View Generated CV</a>	
                                                                                    <% } else if (((cinfo.getSflag() == 4 && cinfo.getSubstatus() == 1) || cinfo.getSflag() == 5) || companytype == 2) {%>
                                                                                <a href="javascript:;" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript: setIframe('<%=(cvfile_path+cinfo.getPdffileName())%>');"><img src="../assets/images/view.png"/><br> <%if(companytype == 1){%>View Generated CV<%} else {%>View CV<%}%></a>
                                                                                    <% } else if ((cinfo.getSflag() == 4 && (cinfo.getSubstatus() == 1 || cinfo.getSubstatus() == 2 || cinfo.getSubstatus() == 3)) && companytype == 1) {%>
                                                                                <a href="javascript: getGenerateoffer('<%= cinfo.getShortlistId()%>','<%= cinfo.getSflag()%>','<%= cinfo.getCandidateId()%>','<%= cinfo.getSubstatus()%>', '3');"><img src="../assets/images/view.png"/><br> View Offer Letter</a>
                                                                                    <%} else if ((cinfo.getSflag() == 4 && (cinfo.getSubstatus() == 4 || cinfo.getSubstatus() == 5)) && companytype == 1) {%>
                                                                                <a href="javascript:;" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript: setIframe('<%=(cvfile_path+cinfo.getOfferpdffile())%>');"><img src="../assets/images/view.png"/><br> View Offer Letter</a>
                                                                                    <%} %>
                                                                            </div>
                                                                            <%} %>
                                                                            <% if (cinfo.getSflag() == 3) {%>
                                                                            <div class="search_view_prof client_reject">
                                                                                <a href="javascript:;" onclick="getsetModal('<%= cinfo.getShortlistId()%>', '<%= cinfo.getCandidateId()%>', '0', '0');" data-bs-toggle="modal" data-bs-target="#reject_modal">Rejected by Client</a>	
                                                                            </div>
                                                                            <%} else if ((cinfo.getSflag() == 4 && cinfo.getSubstatus() == 3) && companytype == 1) {%>
                                                                            <div class="search_view_prof client_reject">
                                                                                <a href="javascript:;" onclick="getsetModal('<%= cinfo.getShortlistId()%>', '<%= cinfo.getCandidateId()%>', '<%= cinfo.getSflag()%>', '<%= cinfo.getSubstatus()%>');" data-bs-toggle="modal" data-bs-target="#dec_offer_letter_modal">Rejected by Candidate</a>
                                                                            </div>
                                                                            <%}%>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </li>
<%
                                                                        }
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
                        </div>
                    </div> 
                </div>
                <!-- End Page-content -->
            </div>
            <!-- end main content-->
        </div>
    <!-- END layout-wrapper -->

    <%@include file ="../footer.jsp"%>

    <div id="thank_you_modal" class="modal fade thank_you_modal blur_modal" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12" id="thankyou">
                            <h2>Congratulations!</h2>
                            <center><img src="../assets/images/thank-you.png"></center>
                            <h3>Candidate Selected</h3>
                            <p>Candidate is successfully selected on behalf of the client.</p>
                            <a href="javascript: view('<%= info.getJobpostId()%>', 'company');" class="msg_button" style="text-decoration: underline;">Client Selection</a>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <div id="thank_you_modalmail" class="modal fade thank_you_modal blur_modal" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12" id="thankyou">
                            <h2>Thank You!</h2>
                            <center><img src="../assets/images/thank-you.png"></center>
                            <h3>Email Sent</h3>
                            <p>Candidate CV has been successfully sent to the client.</p>
                            <a href="javascript: view('<%= info.getJobpostId()%>', '');" class="msg_button" style="text-decoration: underline;">Client Selection</a>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <div id="reject_status_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <h2>REJECTION REASON</h2>
                            <div class="row client_position_table">
                                <div class="col-lg-12 col-md-12 col-sm-12 col-4 mt_30">
                                    <p>Please record the feedback / remarks received from the client for future reference.</p>
                                </div>
                                <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                    <%
                                                   int nsize = rejectionreason.size();
                                    %>
                                    <label class="form_label">Select Reasons</label>
                                    <select class="form-select" id="rejectReason" name="rejectReason" multiple="multiple">
                                        <%
                                                          if (nsize > 0) {
                                                              for (int i = 0; i < nsize; i++) {
                                                                  ClientselectionInfo rpinfo = (ClientselectionInfo) rejectionreason.get(i);
                                                                  if(rpinfo.getDdltype() ==1){
                                        %>
                                        <option value="<%=rpinfo.getDdlValue()%>" ><%=(rpinfo.getDdlLabel())%> </option>
                                        <%
                                            }
                                                }
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                    <label class="form_label">Remarks</label>
                                    <textarea class="form-control" rows="4" id="rejectRemark" name="rejectRemark"></textarea>
                                </div>

                            </div>
                            <div class="row">

                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 text-center">
                                    <a href="javascript:getRejectCandidate();" class="save_page"><img src="../assets/images/save.png"> Save</a>
                                </div>
                            </div>
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

    <div id="mail_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12" id='mailmodal'>
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
    <div id="acc_offer_letter_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <h2>ACCEPT OFFER LETTER</h2>
                            <div class="row client_position_table">
                                <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                    <p>Please note any important points negotiated regarding this offer letter.</p>
                                    <label class="form_label">Remarks</label>
                                    <textarea class="form-control" rows="10" name="txtAcceptremark" id="txtAcceptremark"></textarea>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 text-center">
                                    <a href="javascript: getAcceptCandidate();" class="save_page"><img src="../assets/images/save.png"> Save</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="dec_offer_letter_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <h2>DECLINE OFFER LETTER</h2>
                            <div class="row client_position_table">
                                <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                    <p>
                                        Please help us understand what went wrong,
                                        this shall guide us with either revising the offer or
                                        by closing this client selection query.	
                                    </p>
                                    <label class="form_label">Select Reasons</label>
                                    <select class="form-select" id="declineReason" name="declineReason" multiple="multiple">
<%
                                    if (nsize > 0) 
                                    {
                                        for (int i = 0; i < nsize; i++) 
                                        {
                                            ClientselectionInfo orinfo = (ClientselectionInfo) rejectionreason.get(i);
                                            if(orinfo.getDdltype() ==2)
                                            {
%>
                                                <option value="<%=orinfo.getDdlValue()%>" ><%=(orinfo.getDdlLabel())%> </option>
<%
                                            }
                                        }
                                    }
%>
                                    </select>
                                </div>
                                <div class="col-lg-12 col-md-12 col-sm-12 col-4 form_group">
                                    <label class="form_label">Remarks</label>
                                    <textarea class="form-control" rows="6" name="txtDeclineremark" id="txtDeclineremark"></textarea>
                                </div>
                            </div>
                            <div class="row">

                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 text-center">
                                    <a href="javascript: getDeclineCandidate();" class="close_query mr_15">Close Query</a>
                                    <a href="javascript:;" class="save_page" id="modalwithdecline">Revise Offer</a>
                                </div>
                            </div>
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
    <% if(thankyou.equals("yes")){%>
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
        $(document).ready(function () {
            $('#declineReason').multiselect({
                nonSelectedText: '- Select -',
                includeSelectAllOption: true,
                maxHeight: 200,
                enableFiltering: false,
                enableCaseInsensitiveFiltering: false,
                buttonWidth: '100%'
            });
        });
    </script>
    <script>
        $(document).ready(function () {
            $('input').attr('autocomplete', 'off');
        });
    </script>
</html:form>
</body>

<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
