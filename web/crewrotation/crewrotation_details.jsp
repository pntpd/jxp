<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.crewrotation.CrewrotationInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="crewrotation" class="com.web.jxp.crewrotation.Crewrotation" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 2, submtp = 57, allclient = 0, companytype = 0;
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
            }
        }
        CrewrotationInfo info = null;
        if (session.getAttribute("CREWROTATIONINFO") != null) {
            info = (CrewrotationInfo) session.getAttribute("CREWROTATIONINFO");
        }
        int crewrota = 0;
        if (info != null) {
            crewrota = info.getCrewrota();
        }

        ArrayList selectedcand_list = new ArrayList();
        if (session.getAttribute("CRCANDIDATESLIST") != null) {
            selectedcand_list = (ArrayList) session.getAttribute("CRCANDIDATESLIST");
        }
        int total = selectedcand_list.size();

        String file_path = crewrotation.getMainPath("view_candidate_file");
        String cphoto = "../assets/images/empty_user.png";

        String message = "", clsmessage = "deleted-msg";
        if (request.getAttribute("MESSAGE") != null) {
            message = (String) request.getAttribute("MESSAGE");
            request.removeAttribute("MESSAGE");
        }
        if (message.toLowerCase().contains("success")) {
            message = "";
        }

        String redirecttosignon = "no";
        if (request.getAttribute("REQUIREDDOCUMENTSAVE") != null) {
            redirecttosignon = (String) request.getAttribute("REQUIREDDOCUMENTSAVE");
            request.removeAttribute("REQUIREDDOCUMENTSAVE");
        }
        String crId = "";
        if (request.getAttribute("CRID") != null) {
            crId = (String) request.getAttribute("CRID");
            request.removeAttribute("CRID");
        }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= crewrotation.getMainPath("title") != null ? crewrotation.getMainPath("title") : ""%></title>
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
        <script type="text/javascript" src="../jsnew/crewrotation.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet">

    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/crewrotation/CrewrotationAction.do" onsubmit="return false;" enctype="multipart/form-data">
        <html:hidden property="search"/>
        <html:hidden property="clientIdIndex"/>
        <html:hidden property="assetIdIndex"/>
        <html:hidden property="countryId"/>
        <html:hidden property="doView"/>
        <html:hidden property="doSummary"/>
        <html:hidden property="doSaveActivity"/>
        <html:hidden property="doSaveSignoff"/>
        <html:hidden property="doSaveSignon"/>
        <html:hidden property="doSavereqdoc"/>
        <html:hidden property="jobpostId"/>
        <html:hidden property="clientId"/>
        <html:hidden property="clientassetId"/>
        <html:hidden property="shortlistId"/>
        <html:hidden property="candidateId"/>
        <html:hidden property="crewrotationId"/>
        <html:hidden property="noofdays"/>
        <html:hidden property="status"/>
        <html:hidden property="doPlanning"/>
        <html:hidden property="doSearchCR"/>        
        <!-- Begin page -->
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content tab_panel1 no_tab1">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow"><a href="javascript: goback();"><img  src="../assets/images/back-arrow.png"/></a> <span>Crew Rotation</span></div>
                            <div class="float-end">
                                <div class="toggled-off usefool_tool">
                                    <div class="toggle-title">
                                        <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                        <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>
                                    </div>
                                    <div class="toggle-content">
                                        <h4>Useful Tools</h4>
                                        <ul>
                                            <li><a href="javascript:openinnewtab();"><img src="../assets/images/open-in-new-tab.png"/> Open in New Tab</a></li>
                                            <li><a href="javascript:printPage('printArea');"><img src="../assets/images/print-screen.png"/> Print Screen</a></li>
                                            <li><a href="javascript: exporttoexcelDetails();"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>	
                    </div>
                    <div class="container-fluid pd_0" id="printArea">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 pd_0">
                                <div class="body-background com_checks">

                                    <div class="row com_checks_main">

                                        <div class="col-md-12 com_top_right">
                                            <div class="row d-flex align-items-center">
                                                <div class="col-md-3 com_label_value">
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Client - Asset</label></div>
                                                        <div class="col-md-9"><span><%=  info.getClientName() + " " + info.getClientAsset()%></span></div>
                                                    </div>
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Location</label></div>
                                                        <div class="col-md-9"><span><%= info.getCountry()%></span></div>
                                                    </div>
                                                </div>
                                                <div class="col-md-2 com_label_value">
                                                    <div class="row mb_0">
                                                        <div class="col-md-6"><label>Total Positions</label></div>
                                                        <div class="col-md-6"><span><%= info.getPositionCount()%></span></div>
                                                    </div>
                                                    <div class="row mb_0">&nbsp;</div>
                                                </div>

                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope onshore">
                                                        <ul>
                                                            <li class="mob_value">Onshore <span><%=  crewrotation.changeNum(info.getStatus1(), 2)%>&nbsp;</span></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="ref_vie_ope offshore">
                                                        <ul>
                                                            <li class="mob_value offshore_border"><label>Offshore</label> <div class="full_width"><span><%= info.getNormal() + info.getDelayed() + info.getExtended()%></span></div></li>
                                                            <li class="mob_value"><label>Normal</label> <div class="full_width"><span class="off_normal_text"><%= info.getNormal()%></span></div></li>
                                                            <li class="mob_value"><label>Extended</label> <div class="full_width"><span class="off_extended_text"><%= info.getDelayed()%></span></div></li>
                                                            <li class="mob_value"><label>Overstay</label> <div class="full_width"><span class="off_overstay_text"><%= info.getExtended()%></span></div></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="mob_value">Total <span><%= info.getStatus1() + info.getNormal() + info.getDelayed() + info.getExtended()%></span></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="com_view_job"><a href="javascript: gotodashboard();"><img src="../assets/images/view.png"><br> View Details</a></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="com_view_job"><a href="javascript:;" onclick="viewplan('<%=info.getClientId()%>', '<%=info.getClientassetId()%>')"><img src="../assets/images/view.png"/><br/> View Plan</a></li>
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
                                                        <div class="col-lg-4 col-md-6 col-sm-12 col-12"><h2>PERSONNEL LIST </h2></div>
                                                        <div class="col-lg-8 col-md-8 col-sm-12 col-12 float-end">
                                                            <div class="row justify-content-end">
                                                                <div class="col-lg-3 col-md-3 col-sm-12 col-12">
                                                                    <html:select property="dynamicId" styleId="dynamicId" styleClass="form-select" onchange = " javascript: showdynamicId();">
                                                                        <html:option value="0"> Select here </html:option>
                                                                        <html:option value="1"> Sign on </html:option>
                                                                        <html:option value="2"> Sign off </html:option>
                                                                        <html:option value="3"> Activity </html:option>
                                                                        <html:option value="4"> Candidate Name </html:option>
                                                                        <html:option value="5"> Position </html:option>
                                                                    </html:select>
                                                                </div>
                                                                <div class="col-lg-3 col-md-4 col-sm-12 col-12 sign_on_off" id="div1" style="diplay:none">
                                                                    <div class="input-daterange input-group">
                                                                        <html:text property="crdate" styleId="crdate" styleClass="form-control add-style wesl_dt date-add"/>
                                                                        <script type="text/javascript">
                                                                            document.getElementById("crdate").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                                        </script>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-3 col-md-6 col-sm-12 col-12 activity" id="div2" style="diplay:none">
                                                                    <html:select property="activityDropdown" styleId="activityDropdown" styleClass="form-select" onchange = " javascript: searchview();">
                                                                        <html:option value="0">Select Activity</html:option>
                                                                        <html:option value="1">Office Work</html:option>
                                                                        <html:option value="2">Training</html:option>
                                                                    </html:select>
                                                                </div>
                                                                <div class="col-lg-4 col-md-6 col-sm-12 col-12 candidate_name" id="div3" style="diplay:none">
                                                                    <div class="input-group bootstrap-touchspin bootstrap-touchspin-injected short_list_search search_field">
                                                                        <div class="search_mode">
                                                                            <span class="input-group-btn input-group-prepend search_label">Search:</span>
                                                                            <html:text property ="searchcr" styleId="example-text-input" styleClass="form-control" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                                        this.removeAttribute('readonly');
                                                                                        this.blur();
                                                                                        this.focus();
                                                                                    }"/>
                                                                            <a class="search_view" onclick=" javascript: searchview();"><img src="../assets/images/view_icon.png"></a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-4 col-md-6 col-sm-12 col-12 position" id="div4" style="diplay:none">
                                                                    <html:select styleClass="form-select" property="positionIdIndex" styleId="positionIdIndex" onchange = " javascript: searchview();">
                                                                        <html:optionsCollection property="positions" value="ddlValue" label="ddlLabel"></html:optionsCollection>
                                                                    </html:select>
                                                                </div>
                                                                <div class="col-lg-1">
                                                                    <a href="javascript: searchview();" class="go_btn">Go</a>
                                                                </div>
                                                                <div class="col-lg-4 col-md-6 col-sm-12 col-12">
                                                                    <html:select property="statusindex" styleId="statusindex" styleClass="form-select" onchange = " javascript: searchview();">
                                                                        <html:option value="0"> All </html:option>
                                                                        <html:option value="1"> Pending </html:option>
                                                                        <html:option value="2"> SignedOn-Normal </html:option>
                                                                        <html:option value="3"> SignedOn-Early </html:option>
                                                                        <html:option value="4"> SignedOn-Delay </html:option>
                                                                        <html:option value="5"> Onshore </html:option>
                                                                        <html:option value="6"> Offshore </html:option>
                                                                    </html:select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <% if (!message.equals("")) {%><div class="alert <%=clsmessage%> alert-dismissible fade show">
                                                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button><%=message%>
                                                </div><% } %>
                                                <div class="search_short_main rotation_list">
                                                    <ul>

                                                        <%
                                                            String position = "", position2 = "";
                                                            for (int i = 0; i < total; i++) {
                                                                CrewrotationInfo oinfo = (CrewrotationInfo) selectedcand_list.get(i);
                                                                if (oinfo != null) 
                                                                {

                                                                    cphoto = "../assets/images/empty_user.png";
                                                                    if (!oinfo.getPhoto().equals("")) {
                                                                        cphoto = file_path + oinfo.getPhoto();
                                                                    }
                                                                    position = oinfo.getPosition() != null ? oinfo.getPosition(): "";
                                                                    position2 = oinfo.getPosition2() != null ? oinfo.getPosition2(): "";
                                                                    if(!position2.equals(""))
                                                                    {
                                                                        position += " | "+position2;
                                                                    }
                                                        %>
                                                        <li class="odd_list_1 odd_1">
                                                            <div class="search_box" id="a_<%= oinfo.getCrewrotationId()%>">
                                                                <div class="row">
                                                                    <div class="col-md-12 comp_view">
                                                                        <div class="row">
                                                                            <div class="col-md-12 client_prof_status">
                                                                                <div class="row d-flex align-items-start">
                                                                                    <div class="col-md-1 com_view_prof cand_box_img">
                                                                                        <div class="user_photo pic_photo">
                                                                                            <div class="upload_file">
                                                                                                <!-- <img src="../assets/images/empty_user.png"> -->
                                                                                                <img src="<%=cphoto%>">
                                                                                                <a href="javascript:;" onclick="viewCandidate('<%=oinfo.getCandidateId()%>')"><img src="../assets/images/view.png"></a>
                                                                                            </div>
                                                                                        </div>	
                                                                                    </div>
                                                                                    <div class="col-md-11">
                                                                                        <div class="row">
                                                                                            <div class="col-md-4 comp_view ">
                                                                                                <div class="row left_border">
                                                                                                    <div class="full_name_ic col-md-12 mb_0">
                                                                                                        <div class="row">
                                                                                                            <div class="col-md-12">
                                                                                                                <a href="javascript:;" class="tooltip_name" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Full Name" aria-label="Full Name"><i class="mdi mdi-account"></i></a>
                                                                                                                <span><%=  oinfo.getName()%> </span>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                    <div class="posi_rank_ic col-md-12 mb_0">
                                                                                                        <a href="javascript:;" class="tooltip_name" data-toggle="tooltip" data-placement="top" title="" data-bs-original-title="Position-Rank" aria-label="Position-Rank"><i class="mdi mdi-star-circle"></i></a>
                                                                                                        <span><%=position%></span>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="col-md-4 sign_on_off">
                                                                                                <div class="row">
                                                                                                    <div class="col-md-6">
                                                                                                        <%
                                                                                                            String executeondate = "";
                                                                                                            if (oinfo.getStatus() == 2) {
                                                                                                                executeondate = !oinfo.getSignon().equals("") ? oinfo.getSignon() : "";
                                                                                                            } else if (oinfo.getStatus() == 1) {
                                                                                                                executeondate = !oinfo.getSignoff().equals("") ? oinfo.getSignoff() : "";
                                                                                                            }
                                                                                                        %>
                                                                                                        <input type="hidden" name="hdnCrewRota" id="hdnCrewRota" value="<%=crewrota%>" />
                                                                                                        <ul>
                                                                                                            <li><label>Sign On</label></li>
                                                                                                               <%if (oinfo.getDocflagId() == 1 && oinfo.getActiveflag() == 1) {%>
                                                                                                            <li class="sign_on"><a class="disable_text" href="javascript:;" <%if (addper.equals("Y") && oinfo.getActiveflag() == 1) {%> data-bs-toggle="modal" data-bs-target="#add_docs_modal" onclick =" javascript: getadocumentmodal('<%= oinfo.getCrewrotationId()%>', '<%= info.getClientId()%>', '<%= info.getClientassetId()%>', '<%= oinfo.getNoofdays()%>')" <%}%>><%if (oinfo.getStatus() == 1) {%><%= !oinfo.getExpecteddate().equals("") ? oinfo.getExpecteddate() : "Signon"%><%} else if (oinfo.getStatus() == 2) {%><%=!oinfo.getSignon().equals("") ? oinfo.getSignon() : "&nbsp"%><%}%></a></li>
                                                                                                              <%} else if (oinfo.getDocflagId() == 2) {%>
                                                                                                            <li class="sign_on"><a class="<%if (oinfo.getStatus() != 2) {%>disable_text<%}%>" <%if (oinfo.getStatus() != 2) {%>href="javascript:;"<%}%> <%if (oinfo.getStatus() == 1 && addper.equals("Y") && oinfo.getActiveflag() == 1) {%> data-bs-toggle="modal" data-bs-target="#sign_on_details_modal" onclick =" javascript :getsignonModel('<%= oinfo.getCrewrotationId()%>', '<%= oinfo.getNoofdays()%>', '<%=crewrota%>')" <%}%>><%if (oinfo.getStatus() == 1) {%><%=!oinfo.getExpecteddate().equals("") ? oinfo.getExpecteddate() : "Signon"%><%} else if (oinfo.getStatus() == 2) {%><%= !oinfo.getSignon().equals("") ? oinfo.getSignon() : "&nbsp"%><%}%></a></li>
                                                                                                                <%}%>
                                                                                                        </ul>
                                                                                                    </div>
                                                                                                    <div class="col-md-6">
                                                                                                        <input type="hidden" id="executeondate_<%= oinfo.getCrewrotationId()%>" value="<%=!executeondate.equals("") ? executeondate.substring(0, 11) : ""%>" />
                                                                                                        <ul>
                                                                                                            <li><label>Sign Off</label></li>
                                                                                                            <li class="sign_off"><a class="<%if (oinfo.getStatus() != 1) {%>disable_text<%}%>" <%if (oinfo.getStatus() != 1) {%>href="javascript:;"<%}%> <%if (oinfo.getStatus() == 2 && addper.equals("Y") && oinfo.getActiveflag() == 1) {%> data-bs-toggle="modal" data-bs-target="#sign_off_details_modal" onclick ="javascript :getsignoffModel('<%= oinfo.getCrewrotationId()%>', '<%= oinfo.getNoofdays()%>', '<%=crewrota%>')" <%}%>><%if (oinfo.getStatus() == 2) {%><%= crewrota != 2 ? (oinfo.getExpecteddate() != null && !oinfo.getExpecteddate().equals("") ? oinfo.getExpecteddate() : "NA") : "NA"%><%} else if (oinfo.getStatus() == 1) {%><%= !oinfo.getSignoff().equals("") ? oinfo.getSignoff() : "&nbsp;"%><%}%></a></li>
                                                                                                        </ul>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="col-md-4">
                                                                                                <div class="portlet box status_on_hold cre_status_area">
                                                                                                    <div class="portlet-title">
                                                                                                        <div class="caption">Status - <%= oinfo.getOffshorestatus() != null && !oinfo.getOffshorestatus().equals("") ? oinfo.getOffshorestatus() : ""%> </div>
                                                                                                        <div class="actions docs_reload">
                                                                                                            <% if (oinfo.getDocflagId() == 2) {%>
                                                                                                            <a href="javascript:;" data-bs-toggle="modal" data-bs-target="#view_docs_modal" class="mr_8" onclick =" javascript: getvdocumentmodal('<%= oinfo.getCrewrotationId()%>', '<%= info.getClientId()%>', '<%= info.getClientassetId()%>')"><img class="mr_5" src="../assets/images/white_view.png"/> View Docs</a>
                                                                                                                <%} else if (oinfo.getDocflagId() == 1) {%>
                                                                                                            <a href="javascript:;" data-bs-toggle="modal" <%if (addper.equals("Y") && oinfo.getActiveflag() == 1) {%> data-bs-target="#add_docs_modal" class="mr_8" onclick =" javascript: getadocumentmodal('<%= oinfo.getCrewrotationId()%>', '<%= info.getClientId()%>', '<%= info.getClientassetId()%>', '-1')" <%}%>><img class="mr_5" src="../assets/images/white_view.png"/> Check Docs</a>
                                                                                                                <%}%>
                                                                                                            <a href=" javascript: viewActivity('<%= oinfo.getCrewrotationId()%>','<%= info.getClientId()%>','<%= info.getClientassetId()%>');" class="" ><img src="../assets/images/reload-time.png"/></a>
                                                                                                        </div> 
                                                                                                    </div>

                                                                                                </div>

                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="row">
                                                                                            <div class="col-md-4 comp_view ">
                                                                                                <div class="row ass_act_area">
                                                                                                    <div class="col-md-12">
                                                                                                        <div class="input-group input_group">
                                                                                                            <span class="input-group-btn">Assign Next Activity</span>
                                                                                                            <span class="form-control" <%if (addper.equals("Y")) {%>data-bs-toggle="modal" data-bs-target="#valueModal" onclick =" javascript :getActivityModel('<%= oinfo.getCrewrotationId()%>', '<%= oinfo.getStatus()%>', '<%= info.getClientassetId()%>')" <%}%>><%=  !oinfo.getActivitystatus().equals("") ? oinfo.getActivitystatus() : "-- Add Activity --"%></span>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>

                                                                                            <%if (oinfo.getActivityId() > 0) {%>    
                                                                                            <div class="col-md-4 sign_on_off">
                                                                                                <div class="row duration_area">
                                                                                                    <div class="input-group input_group">
                                                                                                        <span class="input-group-btn">Duration</span>
                                                                                                        <div class="form-control">
                                                                                                            <ul>
                                                                                                                <li><label>From</label> <span><%=  oinfo.getFromdate()%></span></li>
                                                                                                                <li><label>to</label> <span><%=  oinfo.getTodate()%></span></li>
                                                                                                            </ul>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>

                                                                                            <div class="col-md-4">
                                                                                                <div class="row ass_act_area">
                                                                                                    <div class="col-md-12">
                                                                                                        <div class="input-group input_group">
                                                                                                            <span class="input-group-btn">Remarks</span>
                                                                                                            <span class="form-control"><%=  oinfo.getRemarks()%></span>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>

                                                                                        <%}%>            

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
        <!-- END layout-wrapper -->

        <%@include file ="../footer.jsp"%>
        <div id="valueModal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="activitymodal">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="sign_off_details_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="signoffmodal">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="sign_on_details_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="signonmodal">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="view_docs_modal" class="modal fade parameter_modal req_checklist" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body d-none1">
                        <div class="row">
                            <div class="col-lg-12" id="requireddocview">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="add_docs_modal" class="modal fade parameter_modal req_checklist" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="requireddocadd">
                                <h2>REQUIRED DOCUMENTS CHECKLIST</h2>
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
        <% if (redirecttosignon.equals("yes")) {%>
        <script type="text/javascript">
            $(window).on('load', function () {
                getsignonModel(0, 0, <%=crewrota%>);
                $('#sign_on_details_modal').modal('show');
            });
        </script>
        <%}%>
        <% if (!crId.equals("")) {%>
        <script type="text/javascript">
            $(window).on('load', function () {
                window.location.hash = "a_<%=crId%>";
            });
        </script>
        <%}%>
        <script>
            $(function () {
                $('[data-toggle="tooltip"]').tooltip()
            })
        </script>
        <script>
            function addLoadEvent(func)
            {
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
            showdynamicId();
        </script>
        <script>
            $(document).ready(function () {
                $('input').attr('autocomplete', 'off');
            });
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
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
