<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.tracker.TrackerInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="tracker" class="com.web.jxp.tracker.Tracker" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 5, submtp = 90;
            String per = "N", addper = "N", editper = "N", deleteper = "N",approveper="N";
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
                    approveper = uinfo.getApproveper() != null ? uinfo.getApproveper() : "N";
                }
            }
            TrackerInfo info = null;
            if(request.getAttribute("TRACKER_INFO1") != null)
            {
                info = (TrackerInfo) request.getAttribute("TRACKER_INFO1");                
                request.removeAttribute("TRACKER_INFO1");
            }

            String position = "", poistionId = "";
            int pid =0;
            if (request.getAttribute("TRACKER_PID") != null) 
            {
                poistionId = (String) request.getAttribute("TRACKER_PID");
                pid = Integer.parseInt(poistionId);
                if(info != null)
                {
                    if(pid == info.getPositionId())
                        position = info.getPositionName();
                    else if(pid == info.getPositionId2())
                        position = info.getPosition2();
                }
                request.removeAttribute("TRACKER_PID");
            }            
            
            ArrayList list = new ArrayList();
            if(session.getAttribute("ASSIGNLIST1") != null)
            {
                list = (ArrayList) session.getAttribute("ASSIGNLIST1");
            }
            int total = list.size();
            String message = "", clsmessage = "deleted-msg";
            if (request.getAttribute("MESSAGE") != null)
            {
                message = (String)request.getAttribute("MESSAGE");
                request.removeAttribute("MESSAGE");
            }
            if(message.toLowerCase().contains("success"))
            {
                    message = "";
            }
            if(message != null && (message.toLowerCase()).indexOf("success") != -1)
                clsmessage = "updated-msg";
            int arr[] = new int[8];
            int total_positions = 0, total_candidate = 0, total_competencies = 0, unassigned = 0, inprogress = 0, 
                pass = 0, fail = 0, appeals = 0;
            if(session.getAttribute("ARR_COUNT") != null)
            {
                arr = (int[]) session.getAttribute("ARR_COUNT");
                total_positions = arr[0];
                total_candidate = arr[1];
                total_competencies = arr[2];
                unassigned = arr[3];
                inprogress = arr[4];
                pass = arr[5];
                fail = arr[6];
                appeals = arr[7];
            }
%>
<head>
    <meta charset="utf-8">
    <title><%= tracker.getMainPath("title") != null ? tracker.getMainPath("title") : "" %></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- App favicon -->
    <link rel="shortcut icon" href="../assets/images/favicon.png">
    <!-- Bootstrap Css -->
    <!-- Responsive Table css -->
    <link href="../assets/css/rwd-table.min.css" rel="stylesheet" type="text/css">
    <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
    <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
    <link href="../assets/time/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css" />
    <!-- Icons Css -->
    <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
    <!-- App Css-->
    <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
    <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
    <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="../jsnew/common.js"></script>
    <script type="text/javascript" src="../jsnew/tracker.js"></script>
    <script type="text/javascript" src="../jsnew/validation.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
<html:form action="/tracker/TrackerAction.do" onsubmit="return false;" enctype="multipart/form-data">
    <html:hidden property="clientIdIndex"/> 
    <html:hidden property="assetIdIndex"/> 
    <html:hidden property="crewrotationId" />
    <html:hidden property="doAssignSave1" />
    <html:hidden property="mode" />
    <html:hidden property="positionIdIndex" />
    <html:hidden property="pcodeIdIndex" />
    <html:hidden property="candidateId" />
    <html:hidden property="doCancel"/>
    <html:hidden property="ftype" />
    <html:hidden property="search" />
    <html:hidden property="doSearch" />
    <html:hidden property="doSavepersonalassign" />
    <html:hidden property="doView" />
    <html:hidden property="doUpdateTrack" />        
    <html:hidden property="trackerFilehidden2" />        
    <html:hidden property="trackerFilehidden" />
    <input type='hidden' name="positionId" value='<%=pid%>'/>
    <!-- Begin page -->
    <div id="layout-wrapper">
        <%@include file="../header.jsp" %>
        <%@include file="../sidemenu.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow">
                                <a href="javascript: goback();"><img src="../assets/images/back-arrow.png"/></a>
                                <span>Competency Tracker</span>
                            </div>                                
                            <div class="float-end"> 
                                <div class="role mr_25">
                                    <label>Select Mode:</label>
                                    <select class="form-select select_role" name='mode_top' id="mode_top" onchange="javascript: gotomode();">
                                        <option value="1" selected>Personnel List</option>
                                        <option value="2">Role Competency List</option>
                                    </select>
                                </div>
                                <div class="toggled-off usefool_tool">
                                    <div class="toggle-title">
                                        <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                        <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>

                                    </div>
                                    <!-- end toggle-title --->
                                    <div class="toggle-content">
                                        <h4>Useful Tools</h4>
                                        <ul>
                                            <li><a href="javascript:openinnewtab();"><img src="../assets/images/open-in-new-tab.png"/> Open in New Tab</a></li>
                                            <li><a href="javascript:printPage('printArea');"><img src="../assets/images/print-screen.png"/> Print Screen</a></li>
                                            <li><a href="javascript: exporttoexcel('2');"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                        </ul>
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
                                                    <div class="row d-flex align-items-center mb_0">
                                                        <div class="col-md-5">
                                                            <div class="row">	
                                                                <div class="col-md-12 mb_10">
                                                                    <span class="form-control"><%= info != null && info.getClientName() != null ? info.getClientName() : "" %></span>
                                                                </div>
                                                                <div class="col-md-12">
                                                                    <span class="form-control"><%= info != null && info.getAssetName() != null ? info.getAssetName() : "" %></span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-7 com_label_value">
                                                            <div class="row mb_0">
                                                                <div class="col-md-4"><label>Personnel</label></div>
                                                                <div class="col-md-8"><span><%= info != null && info.getName() != null ? info.getName() : "" %></span></div>
                                                            </div>
                                                            <div class="row mb_0">
                                                                <div class="col-md-4"><label>Position-Rank</label></div>
                                                                <div class="col-md-8"><span><%= info != null && position != null ? position : "" %></span></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-6 pd_0">
                                                    <div class="ref_vie_ope onshore cou_una_pen_com track_total_comp">
                                                        <ul class=" d-flex align-items-center">
                                                            <li class="tot_comp_width">
                                                                <!--<div class="full_width tot_com"><span>Total</span></div>-->
                                                                <div class="full_width tot_com"><span>Competencies</span></div>
                                                                <div class="full_width tot_com_value"><span><%=tracker.changeNum(total_competencies, 2)%></span></div>
                                                            </li>
                                                            <li class="unassigned_bg mob_value">Unassigned <span><%=tracker.changeNum(unassigned, 2)%></span></li>
                                                            <li class="pending_bg mob_value">In Progress  <span><%=tracker.changeNum(inprogress, 2)%></span></li>
                                                            <li class="complete_bg mob_value">Competent <span><%=tracker.changeNum(pass, 2)%></span></li>
                                                            <li class="fail_bg mob_value">Not Yet Competent <span><%=tracker.changeNum(fail, 2)%></span></li>
                                                            <li class="appeals_bg mob_value">Appeals <span><%=tracker.changeNum(appeals, 2)%></span></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="com_view_job"><a href="javascript: viewprofile();"><img src="../assets/images/view.png"><br> View Profile</a></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">

                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                            <div class="row">
                                                <div class="col-md-12 col-xl-12">
                                                    <div class="search_export_area">
                                                        <div class="row">
                                                            <div class="col-lg-12">
                                                                <div class="row mb-3">
                                                                    <div class="col-lg-10">
                                                                        <div class="row">
                                                                            <div class="col-lg-3 col-md-3 col-sm-12 col-12">
                                                                                <div class="input-group bootstrap-touchspin bootstrap-touchspin-injected short_list_search">
                                                                                    <span class="input-group-btn input-group-prepend search_label">Search:</span>
                                                                                    <html:text property ="searchdetail" styleId="example-text-input" styleClass="form-control" maxlength="200" readonly="true" onfocus="if (this.hasAttribute('readonly')) {this.removeAttribute('readonly'); this.blur(); this.focus();}"/>
                                                                                    <a class="search_view index_search_view" href="javascript: searchFormDetail();"><img src="../assets/images/view_icon.png"></a>
                                                                                </div>
                                                                            </div>
                                                                                    
                                                                              <div class="col-lg-5">
                                                                                    <div class="row">
                                                                                        <label class="col-sm-5 col-form-label text-right">Assessment Type:</label>
                                                                                        <div class="col-sm-7 field_ic">
                                                                                            <html:select property="passessmenttypeId" styleId="passessmenttypeId" styleClass="form-select" onchange="javascript: searchFormDetail();">
                                                                                                <html:optionsCollection filter="false" property="passessmenttypes" label="ddlLabel" value="ddlValue">
                                                                                                </html:optionsCollection>
                                                                                            </html:select>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>       
                                                                                    
                                                                                <div class="col-lg-4">
                                                                                <div class="row">
                                                                                    <label class="col-sm-3 col-form-label text-right">Priority:</label>
                                                                                    <div class="col-sm-9 field_ic">
                                                                                        <html:select property="priorityId" styleId="priorityId" styleClass="form-select" onchange="javascript: searchFormDetail();">
                                                                                            <html:optionsCollection filter="false" property="priorities" label="ddlLabel" value="ddlValue">
                                                                                            </html:optionsCollection>
                                                                                        </html:select>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                                    
                                                                        </div>
                                                                    </div>	
                                                                    <div class="col-lg-2 float-end text-right">
                                                                        <a id="assignhref" href="javascript:;" class="assign_training inactive_btn">Assign&nbsp;Assessment</a></div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="printArea">
                                                    <div class="table-rep-plugin sort_table">
                                                        <div class="table-responsive mb-0 ellipse_code">
                                                            <table id="tech-companies-1" class="table table-striped">
                                                                <thead>
                                                                    <tr>
                                                                        <th width="3%" class="select_check_box">
                                                                            <label class="mt-checkbox mt-checkbox-outline"> 
                                                                                <input type="checkbox" name="fcrolecball" id="fcrolecball" onchange="javascript: setallcb();">
                                                                                <span></span>
                                                                            </label>	
                                                                        </th>

                                                                        <th width="37%"><span><b>Role Competencies</b></span></th>
                                                                        <th width="15%"><span><b>Assessment Type</b></span></th>
                                                                        <th width="6%"><span><b>Priority</b></span></th>
                                                                        <th width="14%">
                                                                            <span><b>Status</b></span>
                                                                        </th>
                                                                        <th width="4%" class="text-center">
                                                                            <div class="toggled-off_02 usefool_tool" id='togid'>
                                                                                <div class="toggle-title_02">
                                                                                    <img src="../assets/images/filter.png" class="fa-angle-left"/>
                                                                                    <img src="../assets/images/filter_up.png" class="fa-angle-right"/>
                                                                                </div>
                                                                                <div class="toggle-content">
                                                                                    <ul>
                                                                                            <li class=""><a href="javascript: setfilter('-1');"><span id="spansel-1" class="round_circle circle_courses selected_circle"></span> All Competencies</a></li>
                                                                                            <li><a href="javascript: setfilter('1');"><span id="spansel1" class="round_circle circle_unassigned"></span> Unassigned</a></li>
                                                                                            <li><a href="javascript: setfilter('2');"><span id="spansel2" class="round_circle circle_pending "></span> In Progress</a></li>
                                                                                            <li><a href="javascript: setfilter('3');"><span id="spansel3" class="round_circle circle_pending"></span> Completed</a></li>
                                                                                            <li><a href="javascript: setfilter('4');"><span id="spansel4" class="round_circle circle_complete"></span> Competent</a></li>
                                                                                            <li><a href="javascript: setfilter('5');"><span id="spansel5" class="round_circle circle_exipred"></span> Not Yet Competent</a></li>
                                                                                            <li><a href="javascript: setfilter('6');"><span id="spansel6" class="round_circle circle_exipry"></span> Appealed</a></li>
                                                                                    </ul>
                                                                                </div>
                                                                            </div>	
                                                                        </th>
                                                                        <th width="10%"><span><b>Valid Till</b></span></th>
                                                                        <th width="5%"  class="text-center"><span><b>Score</b></span></th>
                                                                        <th width="6%" class="text-center"><span><b>Action</b></span></th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody id="detaildiv">
<%
                                                                    if(total > 0)
                                                                    {
                                                                        for(int  i = 0; i < total; i++)
                                                                        {
                                                                            TrackerInfo minfo = (TrackerInfo) list.get(i);
                                                                            if(minfo != null)
                                                                            {
%>
                                                                                <tr>
                                                                                    <td class="select_check_box">
                                                                                    <%if(minfo.getStatus() <= 1){%>
                                                                                        <label class="mt-checkbox mt-checkbox-outline"> 
                                                                                            <input type="checkbox" value="<%=minfo.getFcroleId()%>" name="fcrolecb" class="singlechkbox" onchange="javascript: setcb();" />
                                                                                            <span></span>
                                                                                        </label>
                                                                                    <%} else {%>&nbsp;<%}%>
                                                                                    </td>
                                                                                    <td><%= (minfo.getRole() != null ? minfo.getRole() : "") %></td>
                                                                                    <td><%= (minfo.getPassessmenttypeName() != null ? minfo.getPassessmenttypeName() : "") %></td>
                                                                                    <td><%= (minfo.getPriorityName() != null ? minfo.getPriorityName() : "") %></td>
                                                                                    <td><%= tracker.getStColour(minfo.getStatus())%> <%= (minfo.getStatusval() != null ? minfo.getStatusval() : "") %></td>
                                                                                    <td class="text-center">&nbsp;</td>
                                                                                    <td><%= (minfo.getDate() != null && !minfo.getDate().equals("") ? minfo.getDate() : "-") %></td>
                                                                                     <td class="text-center"><%if(minfo.getAverage() > 0) {%><%= minfo.getAverage() %><% } else { %>-<% } %></td>
                                                                                    <td class="action_column text-center"><%if(addper.equals("Y") || editper.equals("Y")){%><%if(minfo.getTrackerId() > 0) {%><a class="" data-bs-toggle="modal" data-bs-target="#unassigned_details_modal_01" href="javascript:;" onclick="javascript: updatetracker('<%=minfo.getTrackerId()%>','1', '<%=poistionId%>');"><img src="../assets/images/view.png"/></a><% } else {%><a class="" data-bs-toggle="modal" data-bs-target="#unassigned_details_modal_01" href="javascript:;" onclick="javascript: setPersonalModal('-1','<%=minfo.getTrackerId()%>','<%=minfo.getFcroleId()%>','1','<%=poistionId%>');"><img src="../assets/images/view.png"/></a><% } %><% } else {%>&nbsp;<% } %></td>
                                                                                </tr>
<%
                                                                            }
                                                                        }
                                                                   }else{
%>
                                                                            <tr><td colspan='10'>No information available.</td></tr>
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
                </div>
            </div>
        </div>
        <%@include file ="../footer.jsp"%>
        <div id="unassigned_details_modal_01" class="modal fade parameter_modal large_modal">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="personalmodal">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="selected_role_comp_modal" class="modal fade multiple_position parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" data-keyboard="false" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button data-bs-toggle="modal" data-bs-target="#unassigned_details_modal_01" type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <h2>SELECTED ROLE COMPETENCIES</h2>
                                <div class="full_width client_position_table">
                                    <div class="table-rep-plugin sort_table">
                                        <div class="table-responsive mb-0">
                                            <table id="pcodemultiple" class="table table-striped">
                                                
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
        <div id="view_pdf" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button data-bs-toggle="modal" data-bs-target="#unassigned_details_modal_01" type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        <span class="resume_title">View Attachment</span>
                        <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                        <a href="" class="down_btn" id="diframe" download target="_blank"><img src="../assets/images/download.png"/></a>
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
        <div id="history_modal" class="modal fade multiple_position parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" data-keyboard="false" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button data-bs-toggle="modal" data-bs-target="#unassigned_details_modal_01" type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <h2>Tracker History</h2>
                                <div class="full_width client_position_table">
                                    <div class="table-rep-plugin sort_table">
                                        <div class="table-responsive mb-0">
                                            <table id="tech-companies-1" class="table table-striped">
                                                <thead>
                                                    <tr>
                                                        <th width="%"><span><b>Date / Time</b> </span></th>
                                                        <th width="%"><span><b>User Name</b></span></th>
                                                        <th width="%"><span><b>Action</b></span></th>
                                                    </tr>
                                                </thead>
                                                <tbody id="historydiv">
                                                    
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
        <div id="online_submission_modal" class="modal fade parameter_modal define_modal large_modal">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header"> 
                        <button type="button" data-bs-toggle="modal" data-bs-target="#know_ass_details_modal_02" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="row flex-center align-items-center mb_10" id="questiondiv">
                                    
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
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/app.js"></script>
        <script src="../assets/js/table-responsive.init.js"></script>
        <script src="../assets/js/bootstrap-select.min.js"></script> 
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script>
        
        <script>
            $(document).on('click', '.toggle-title', function() {
                $(this).parent()
                .toggleClass('toggled-on')
                .toggleClass('toggled-off');
            });

            $(document).on('click', '.toggle-title_02', function() {
                $(this).parent()
                .toggleClass('toggled-on_02')
                .toggleClass('toggled-off_02');
            });
        </script>
        <script>
            $(document).ready(function () {
                $('input').attr('autocomplete', 'off');
            });
        </script>
        <script>
        $(function(){
                $("#upload_link_1").on('click', function(e){
                        e.preventDefault();
                        $("#upload1:hidden").trigger('click');
                });
                $("#upload_link_2").on('click', function(e){
                        e.preventDefault();
                        $("#upload2:hidden").trigger('click');
                });
                $("#upload_link_3").on('click', function(e){
                        e.preventDefault();
                        $("#upload3:hidden").trigger('click');
                });

        });			
        </script>
        <script type="text/javascript">
            function addLoadEvent(func) {
                  var oldonload = window.onload;
                  if (typeof window.onload != 'function') {
                    window.onload = func;
                  } else {
                    window.onload = function() {
                      if (oldonload) {
                        oldonload();
                      }
                    }
                  }
                }
                addLoadEvent(showhide());
                addLoadEvent(function() {
                })
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