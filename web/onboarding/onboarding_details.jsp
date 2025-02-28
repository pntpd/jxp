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
        OnboardingInfo info = null;
        if (session.getAttribute("ONBOARDING_DETAIL") != null) {
            info = (OnboardingInfo) session.getAttribute("ONBOARDING_DETAIL");
        }

        ArrayList selectedcand_list = new ArrayList();
        if (session.getAttribute("SELECTEDCANDIDATE_LIST") != null) {
            selectedcand_list = (ArrayList) session.getAttribute("SELECTEDCANDIDATE_LIST");
        }
        int total = selectedcand_list.size();

        String Accomm = "no";
        if(request.getAttribute("ACCOMMODATIONMODAL") != null)
        {
            Accomm = (String)request.getAttribute("ACCOMMODATIONMODAL");
            request.removeAttribute("ACCOMMODATIONMODAL");
        }
        
        String file_path = onboarding.getMainPath("view_candidate_file");
        String cvfile_path = onboarding.getMainPath("view_resumetemplate_pdf");
        String cphoto = "../assets/images/empty_user.png";

        
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
        <link href="../assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">

        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/onboarding.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet">

    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/onboarding/OnboardingAction.do" onsubmit="return false;" enctype="multipart/form-data">
        <html:hidden property="doView"/>
        <html:hidden property="doAdd"/>
        <html:hidden property="doGenerate"/>
        <html:hidden property="doSaveTravel"/>
        <html:hidden property="doSaveAccomm"/>
        <html:hidden property="doSaveReqDoc"/>
        <html:hidden property="doSaveDocCheck"/>
        <html:hidden property="doMail"/>
        <html:hidden property="doUpload"/>
        <html:hidden property="doMailtravel"/>
        <html:hidden property="doSummary"/>
        <html:hidden property="doMobTravel"/>
        <html:hidden property="doMobAccomm"/>
        <html:hidden property="doViewCancel"/>
        <html:hidden property="search"/>
        <html:hidden property="type"/>
        <html:hidden property="jobpostId"/>
        <html:hidden property="clientId"/>
        <html:hidden property="clientassetId"/>
        <html:hidden property="shortlistId"/>
        <html:hidden property="candidateId"/>
        <html:hidden property="assetcountryId"/>
        <html:hidden property="clientIdIndex"/>
        <html:hidden property="assetIdIndex"/>
        <!-- Begin page -->
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content tab_panel1 no_tab1">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow"><a href="javascript: goback();"><img  src="../assets/images/back-arrow.png"/></a> <span>Onboarding</span></div>
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
                                <div class="body-background com_checks">
                                    <div class="row com_checks_main">

                                        <div class="col-md-12 com_top_right">
                                            <div class="row d-flex align-items-center">
                                                <div class="col-md-5 com_label_value">
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Client - Asset</label></div>
                                                        <div class="col-md-9"><span><%=  info.getClientName()+" "+ info.getClientAsset() %></span></div>
                                                    </div>
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Location</label></div>
                                                        <div class="col-md-9"><span><%= info.getCountry() %></span></div>
                                                    </div>

                                                </div>
                                                <div class="col-md-5 com_label_value">
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Total Positions</label></div>
                                                        <div class="col-md-9"><span><%= info.getPositionCount() %></span></div>
                                                    </div>
                                                    <div class="row mb_0">&nbsp;</div>
                                                </div>

                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="mob_value">Onboarded <span><%=  onboarding.changeNum( info.getOnboardCount(),2)%>&nbsp;/&nbsp;<%=  onboarding.changeNum( info.getTotalnoofopenings(),2)%></span></li>
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
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                            <div class="row">
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 short_list_area">
                                                    <div class="row client_head_search">
                                                        <div class="col-lg-4 col-md-6 col-sm-12 col-12"><h2>CANDIDATES <span>- Travel, Accommodation & Onboarding Details</span></h2></div>

                                                        <div class="col-lg-2 col-md-6 col-sm-12 col-12 ">
                                                            <div class="input-group bootstrap-touchspin bootstrap-touchspin-injected short_list_search">
                                                                <div class="search_mode">
                                                                    <span class="input-group-btn input-group-prepend search_label">Search:</span>
                                                                    <html:text property="txtsearch" maxlength="200" styleId="txtsearch" styleClass="form-control"  readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                    this.removeAttribute('readonly');
                                                                    this.blur();
                                                                    this.focus();
                                                                }"/>
                                                                    <a class="search_view" onclick=" javascript: getSearchSelectedCandList();"><img src="../assets/images/view_icon.png"></a>
                                                                </div>
                                                            </div>
                                                        </div>	
                                                        <div class="col-lg-4 col-md-4 col-sm-12 col-12">
                                                            <div class="row">
                                                                <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                                                    <select class="form-select" onchange=" javascript: getSearchSelectedCandList();" id="onstatus">
                                                                        <option value="0"> All </option>
                                                                        <option value="1">Pending</option>
                                                                        <option value="2">Mobilization pending</option>
                                                                        <option value="3">Mobilization details sent</option>
                                                                        <option value="4">Mobilizing(Travelling)</option>
                                                                        <option value="5">Arrived</option>
                                                                        <option value="6">Documents checked</option>
                                                                        <option value="7">Formalities completed</option>
                                                                        <option value="8">Onboarded</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div>

                                                <div class="search_short_main client_sel_candidate">
                                                    <ul id="dselectedcandidate">

                                                        <%
                                                        for(int i = 0 ;i < total;i++){
                                                        OnboardingInfo oinfo = (OnboardingInfo)selectedcand_list.get(i);
                                                        if (oinfo != null) {
                                                        int onboardflag = 0, onflag = 0;
                                                        onboardflag = oinfo.getOnboardflag();
                                                        onflag = oinfo.getOnflag();
                                                            
                                                        if(!oinfo.getPhoto().equals("")){
                                                            cphoto = file_path+oinfo.getPhoto();
                                                        }
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
                                                                                                <img src="<%= cphoto %>"> 
                                                                                                <a href="javascript:;" onclick="viewCandidate('<%=oinfo.getCandidateId()%>')"><img src="../assets/images/view.png"></a>
                                                                                            </div>
                                                                                        </div>	
                                                                                    </div>
                                                                                    <div class="col-md-6">
                                                                                        <div class="row">
                                                                                            <div class="portlet box status_on_hold">
                                                                                                <div class="portlet-title">
                                                                                                    <div class="caption">Status - <%= onboarding.getccStatusbyId(oinfo.getOnboardflag()) %></div>
                                                                                                    <%if(onboardflag > 2) {%>
                                                                                                    <div class="actions">
                                                                                                        <a href="javascript: getSummaryDtls('<%=oinfo.getShortlistId()%>');" class=""><i class="ion ion-md-information-circle-outline"></i> </a>
                                                                                                    </div> 
                                                                                                    <%}%>
                                                                                                </div>
                                                                                                <div class="portlet-body">
                                                                                                    <div class="row">
                                                                                                        <div class="col-md-12 status_ic_checkbox">
                                                                                                            <ul>
                                                                                                                <li>
                                                                                                                    <div class="status_ic"><span><i class="ion ion-ios-briefcase"></i></span></div>
                                                                                                                    <div class="form-check permission-check">
                                                                                                                        <input class="form-check-input" type="checkbox" id="chkMobandAcc<%=oinfo.getShortlistId()%>" <% if(onflag == 1) {%>checked disabled<%}%> onclick="javascript: getTraveldetails('<%=oinfo.getShortlistId()%>', '1');" />
                                                                                                                        <input type="hidden" id="hdnMobandAcc<%=oinfo.getShortlistId()%>" value="<%=oinfo.getShortlistId()%>"/>
                                                                                                                        <span>Mobilize (Travel & Accommodation)</span>
                                                                                                                    </div>
                                                                                                                </li>
                                                                                                                <li>
                                                                                                                    <div class="status_ic"><span><i class="fas fa-bed"></i></span></div>
                                                                                                                    <div class="form-check permission-check">
                                                                                                                        <input class="form-check-input" type="checkbox" id="chkreqdoc<%=oinfo.getShortlistId()%>" <% if(onboardflag >= 2) {%>checked <%}%> onclick="getReqdoc('<%=oinfo.getShortlistId()%>'); <%= onboardflag >= 2 ? "return false;" : ""%>" />
                                                                                                                        <span>Required Documents</span>
                                                                                                                    </div>
                                                                                                                </li>
                                                                                                            </ul>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="col-md-3 bag_bed">
                                                                                        <ul>
                                                                                            <%
                                                                                                String tempclass1="not_required_btn", tempclass2="not_required_btn";
                                                                                                if(onboardflag == 3) {
                                                                                                tempclass1="";
                                                                                                }
                                                                                                if(onboardflag > 3) {
                                                                                                tempclass1="required_btn";
                                                                                                }
                                                                                                if(onboardflag == 4) {
                                                                                                tempclass2="";
                                                                                                }
                                                                                                if(onboardflag > 4) {
                                                                                                tempclass2="required_btn";
                                                                                                }
                                                                                            %>
                                                                                            <li class="<%=tempclass1%>">
                                                                                                <a href="javascript:;" data-toggle="tooltip" data-placement="top" title="Docs Checks" <% if(onboardflag == 3) {%>onclick="javascript:getDocCheckList('<%=oinfo.getShortlistId()%>', '');" <%}%>/>
                                                                                                <i class="far fa-file"></i>
                                                                                                </a>
                                                                                            </li>
                                                                                            <li class="<%=tempclass2%>">
                                                                                                <a href="javascript:;" data-toggle="tooltip" data-placement="top" title="Joining Formalities" <% if(onboardflag == 4) {%>onclick="javascript : viewgenerateformalities('<%=oinfo.getShortlistId()%>');" <%}%>>
                                                                                                    <i class="far fa-copy"></i>
                                                                                                </a>
                                                                                            </li>
                                                                                        </ul>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3 add_view_area client_se_vi_re">
                                                                        <div class="row">
                                                                            <%if(onboardflag <= 3){%>
                                                                            <div class="search_add_btn"><a <%if(onboardflag > 1){%>data-bs-toggle="modal" data-bs-target="#email_det_candidate_modal"<%}%> href="javascript:;" <%=onboardflag%> class=" <%if (onboardflag < 2){%>inactive_btn<%} else {%>active_btn<%}%>" <%if(onboardflag > 1){%>onclick="javascript : gettravelModel('<%= oinfo.getOnboardflag() %>', '<%= oinfo.getShortlistId() %>')" <%}%>><%if (onboardflag == 3){%>Re-send Email<%}else{%>Send Email<%}%></a></div>
                                                                            <%} else if(onboardflag == 4){%>
                                                                            <div class="search_add_btn"><a href="javascript:;" data-bs-toggle="modal" data-bs-target="#email_candidate_modal" class="active_btn" onclick=" javscript : getModelexternal('<%= oinfo.getOnboardflag() %>', '<%= oinfo.getShortlistId() %>')" >Send Email</a></div>
                                                                            <%} else if(onboardflag >=5 && onboardflag <7 ){%>
                                                                            <div class="search_add_btn"><a href="javascript:;" class="active_btn" onclick=" javscript : viewuploadedformalities('<%= oinfo.getShortlistId() %>')">Onboard</a></div>
                                                                            <%} else if(onboardflag == 7 || onboardflag == 8 ){%>
                                                                            <div class="search_add_btn"><a href="javascript:;" class="active_btn" onclick=" javscript : viewuploadedformalities('<%= oinfo.getShortlistId() %>')">Onboard</a></div>
                                                                            <%} else if(onboardflag == 9 ){%>
                                                                            <div class="search_add_btn"><a class="onboarded_label">Onboarded</a></div>
                                                                            <%}%>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-12 comp_view">
                                                                        <div class="row">

                                                                            <div class="full_name_ic col-md-12 mb_0">
                                                                                <div class="row">
                                                                                    <div class="col-md-5">
                                                                                        <a href="javascript:;" class="tooltip_name" data-toggle="tooltip" data-placement="top" title="Full Name"><i class="mdi mdi-account"></i></a>
                                                                                        <span><%=  oinfo.getName()%> </span>
                                                                                    </div>
                                                                                    <div class="col-md-7 posi_rank">
                                                                                        <span><%= oinfo.getPosition()%> </span>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <div class="col-md-12">
                                                                                <div class="row">
                                                                                    <div class="col-md-5 dep_location">
                                                                                        <label>
                                                                                            <i class="ion ion-ios-briefcase"></i>
                                                                                        </label> 
                                                                                        <span>Travel -
                                                                                            <%if(onflag == 1){
                                                                                            if(onboardflag <4){
                                                                                            %>
                                                                                            <a href="javascript:;" onclick="javascript: getTraveldetails('<%=oinfo.getShortlistId()%>', '1');" class="trav_his_view_details">View Details</a>
                                                                                            <%
                                                                                                }else{
                                                                                            %>
                                                                                            <a href="javascript:;" onclick="javascript: getViewTravel('<%=oinfo.getShortlistId()%>', '1');" class="trav_his_view_details">View Details</a>
                                                                                            <%
                                                                                                }
                                                                                            }
                                                                                            %>
                                                                                        </span>
                                                                                    </div>
                                                                                    <div class="col-md-7 dep_location">
                                                                                        <label>
                                                                                            <i class="fas fa-bed"></i>
                                                                                        </label>
                                                                                        <span>Accommodation -
                                                                                            <%if(onflag == 1){
                                                                                             if(onboardflag <4){
                                                                                            %>
                                                                                            <a href="javascript:;" onclick="javascript: getAccommDetails('<%=oinfo.getShortlistId()%>', '2');" class="trav_his_view_details">View Details</a>
                                                                                            <%
                                                                                                }else{
                                                                                            %>
                                                                                            <a href="javascript:;" onclick="javascript: getViewAccomm('<%=oinfo.getShortlistId()%>', '2');" class="trav_his_view_details">View Details</a>
                                                                                            <%
                                                                                                }
                                                                                           }
                                                                                            %>
                                                                                        </span>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </li>
                                                        <%
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
    </div>
    <!-- END layout-wrapper -->

    <%@include file ="../footer.jsp"%>

    <div id="add_travel_details_modal" class="modal fade parameter_modal large_modal blur_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true" onclick="viewcancel();"><i class="ion ion-md-close"></i></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12" id="dTravelDtls">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="add_accom_details_modal" class="modal fade parameter_modal large_modal blur_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true" onclick="viewcancel();"><i class="ion ion-md-close"></i></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12" id="dAccommDtls">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--
        <div id="email_candidate_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="dmaildetails">
    
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>-->

    <div id="req_documents_onboarding_modal" class="modal fade parameter_modal blur_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12" id="dmodalreqdoc">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

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

    <div id="email_candidate_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12" id="mailmodal">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="email_det_candidate_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12" id="travelmailmodel">
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
    <script src="../assets/time/bootstrap-timepicker.min.js" type="text/javascript"></script>
    <script src="../assets/js/app.js"></script>
    <!-- Responsive Table js -->
    <script src="../assets/js/bootstrap-select.min.js"></script>
    <script src="../assets/js/bootstrap-datepicker.min.js"></script>
    <script src="../assets/time/components-date-time-pickers.min.js" type="text/javascript"></script>
    <script src="../assets/js/table-responsive.init.js"></script>
    <script src="../assets/js/sweetalert2.min.js"></script>
    <% if(Accomm.equals("yes")){%>
    <script type="text/javascript">
                        $(window).on('load', function () {
                            getAccommDetails('', '2');
                        });
    </script>
    <%}%>
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
        $(function () {
            $('[data-toggle="tooltip"]').tooltip()
        })
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
