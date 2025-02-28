<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.managetraining.ManagetrainingInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="managetraining" class="com.web.jxp.managetraining.Managetraining" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 4, submtp = 67;
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
            ManagetrainingInfo info = null;
            if(request.getAttribute("MANAGETRAINING_INFO2") != null)
            {
                info = (ManagetrainingInfo) request.getAttribute("MANAGETRAINING_INFO2");
                request.removeAttribute("MANAGETRAINING_INFO2");
            }
            ArrayList list = new ArrayList();
            if(request.getAttribute("ASSIGNLIST1") != null)
            {
                list = (ArrayList) request.getAttribute("ASSIGNLIST1");
                request.removeAttribute("ASSIGNLIST1");
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
            int arr[] = new int[11];
            double perval = 0;
            int completed_count = 0, pending_count = 0, unassigned = 0, expired1 = 0, expired2 = 0, expired3 = 0, expired = 0, total_count = 0, total_position = 0, total_candidate = 0, total_courses = 0;
            if(session.getAttribute("ARR_COUNT") != null)
            {
                arr = (int[]) session.getAttribute("ARR_COUNT");
                completed_count = arr[0];
                pending_count = arr[1];
                unassigned = arr[2];
                expired1 = arr[3];
                expired2 = arr[4];
                expired3 = arr[5];
                expired = arr[6];
                total_count = arr[7];
                total_position = arr[8];
                total_candidate = arr[9];
                total_courses = arr[10];
                perval = ((double)completed_count / (double)total_count) * 100.0;
                if(perval > 100)
                    perval = 100.0;
            }

            String sendcourseemail = "no";
            if(request.getAttribute("SENDCOURSEEMAIL") != null)
            {
                sendcourseemail = (String)request.getAttribute("SENDCOURSEEMAIL");
                request.removeAttribute("SENDCOURSEEMAIL");
            }
            String sendcourseemailassign = "no";
            if(request.getAttribute("SENDCOURSEEMAILASSIGN") != null)
            {
                sendcourseemailassign = (String)request.getAttribute("SENDCOURSEEMAILASSIGN");
                request.removeAttribute("SENDCOURSEEMAILASSIGN");
            }
            String file_path = managetraining.getMainPath("view_candidate_file");
%>
<head>
    <meta charset="utf-8">
    <title><%= managetraining.getMainPath("title") != null ? managetraining.getMainPath("title") : "" %></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- App favicon -->
    <link rel="shortcut icon" href="../assets/images/favicon.png">
    <!-- Bootstrap Css -->
    <!-- Responsive Table css -->
    <link href="../assets/css/rwd-table.min.css" rel="stylesheet" type="text/css">
    <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
    <!-- Icons Css -->
    <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
    <!-- App Css-->
    <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
    <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
    <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="../jsnew/common.js"></script>
    <script type="text/javascript" src="../jsnew/managetraining.js"></script>
    <script type="text/javascript" src="../jsnew/validation.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/managetraining/ManagetrainingAction.do" onsubmit="return false;"  enctype="multipart/form-data">
        <html:hidden property="clientIdIndex"/> 
        <html:hidden property="assetIdIndex"/> 
        <html:hidden property="candidateId" />
        <html:hidden property="doAssignSave1" />
        <html:hidden property="mode" />
        <html:hidden property="positionIdIndex" />
        <html:hidden property="categoryIdIndex" />
        <html:hidden property="subcategoryIdIndex" />
        <html:hidden property="doCancel"/>
        <html:hidden property="ftype" />
        <html:hidden property="courseId" />        
        <html:hidden property="search" />      
        <html:hidden property="doSearch" />
        <html:hidden property="doSavepersonal" />
        <html:hidden property="doSavecourseassign" />
        <html:hidden property="doMail" />
        <html:hidden property="doIndexCourse" />
        <html:hidden property="categoryId" />
        <html:hidden property="subcategoryId" />
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
                                    <span>Manage Training</span>
                                </div>                                
                                <div class="float-end">   
                                    <div class="role mr_25">
                                        <label>Select Mode:</label>
                                        <select class="form-select select_role" name='mode_top' id="mode_top" onchange="javascript: gotomode();">
                                            <option value="1">Personnel List</option>
                                            <option value="2" selected>Course List</option>
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
                                                <li><a href="javascript: exporttoexcel('3');"><img src="../assets/images/export-data.png"/> Export Data</a></li>
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
                                                <div class="col-md-4 com_label_value">
                                                    <div class="row d-flex align-items-center mb_0">
                                                        <div class="col-md-8 com_label_value">
                                                            <div class="row mb_0">
                                                                <div class="col-md-5"><label>Course Name</label></div>
                                                                <div class="col-md-7 pd_0"><span><%= info != null && info.getCourseName() != null ? info.getCourseName() : "" %></span></div>
                                                            </div>
                                                            <div class="row mb_0">
                                                                <div class="col-md-5"><label>Category Details</label></div>
                                                                <div class="col-md-7 pd_0"><span><%= info != null && info.getCategoryName() != null ? info.getCategoryName() + " - " + (info.getSubcategoryName() != null ? info.getSubcategoryName() : "") : "" %></span></div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-4 pd_0">
                                                            <div class="row d-flex align-items-center per_com">	
                                                                <div class="col-md-7 text-right"><label>Percentage Completion</label></div>
                                                                <div class="col-md-5 pd_0"><span><% if(perval > 0) {%><%=managetraining.getDecimal(perval)%><% } else { %>0.00<% } %> %</span></div>
                                                            </div>	
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-3 pd_0">
                                                    <div class="ref_vie_ope onshore cou_una_pen_com">
                                                        <ul>
                                                            <li class="courses_bg mob_value">Courses <span><%=managetraining.changeNum(total_count, 2)%></span></li>
                                                            <li class="unassigned_bg mob_value">Unassigned <span><%=managetraining.changeNum(unassigned, 2)%></span></li>
                                                            <li class="pending_bg mob_value">Pending <span><%=managetraining.changeNum(pending_count, 2)%></span></li>
                                                            <li class="complete_bg mob_value">Complete <span><%=managetraining.changeNum(completed_count, 2)%></span></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="ref_vie_ope offshore expiry_days">
                                                        <ul>
                                                            <li class="mob_value offshore_border"><label>Expiry in</label> <div class="full_width"><span>(Days)</span></div></li>
                                                            <li class="mob_value"><label>45</label> <div class="full_width"><span class="expiry_days_value "><%=managetraining.changeNum(expired1, 2)%></span></div></li>
                                                            <li class="mob_value"><label>45 to 65</label> <div class="full_width"><span class="expiry_days_value"><%=managetraining.changeNum(expired2, 2)%></span></div></li>
                                                            <li class="mob_value"><label>65 to 90</label> <div class="full_width"><span class="expiry_days_value"><%=managetraining.changeNum(expired3, 2)%></span></div></li>
                                                        </ul>
                                                    </div>
                                                </div>

                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="mob_value expired_bg">Expired <span><%=managetraining.changeNum(expired, 2)%></span></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="com_view_job"><a href="javascript: viewCoursecat('<%=info.getCategoryId()%>','<%=info.getSubcategoryId()%>');"><img src="../assets/images/view.png"/><br/> View Course</a></li>
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
                                                                    <div class="col-lg-3 col-md-3 col-sm-12 col-12">
                                                                        <div class="input-group bootstrap-touchspin bootstrap-touchspin-injected short_list_search">
                                                                            <span class="input-group-btn input-group-prepend search_label">Search:</span>
                                                                            <html:text property ="searchdetail" styleId="example-text-input" styleClass="form-control" maxlength="200" readonly="true" onfocus="if (this.hasAttribute('readonly')) {this.removeAttribute('readonly'); this.blur(); this.focus();}"/>
                                                                            <a class="search_view index_search_view" href="javascript: searchFormDetail2();"><img src="../assets/images/view_icon.png"></a>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-lg-2">
                                                                        <div class="row">
                                                                            <div class="col-sm-12 field_ic">
                                                                                <input class="form-control" type="text" value="<%= (info != null && info.getClientName() != null ? info.getClientName() : "") %>" readonly />
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-lg-2">
                                                                        <div class="row">
                                                                            <div class="col-sm-12 field_ic">
                                                                                <input class="form-control" type="text" value="<%= (info != null && info.getAssetName() != null ? info.getAssetName() : "") %>" readonly />
                                                                            </div>
                                                                        </div>
                                                                    </div>

                                                                    <div class="col-lg-2">
                                                                        <div class="row">
                                                                            <div class="col-sm-12 field_ic">
                                                                                <html:select styleClass="form-select" property="positionId2Index" styleId="positionId2Index" onchange = " javascript: searchFormDetail2();">
                                                                                    <html:optionsCollection property="positions" value="ddlValue" label="ddlLabel"></html:optionsCollection>
                                                                                </html:select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                            
                                                                    <div class="col-lg-1 pd_0">
                                                                        <div class="row">
                                                                            <div class="col-sm-12 field_ic">
                                                                                <html:select property="statusIndex" styleId="statusIndex" styleClass="form-select" onchange="javascript: searchFormDetail2();">
                                                                                    <html:option value="-1">- Status -</html:option>
                                                                                    <html:option value="1">Unassigned</html:option>
                                                                                    <html:option value="2">Assigned</html:option>
                                                                                    <html:option value="3">Completed</html:option>
                                                                                </html:select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                
                                                                    <%if(addper.equals("Y") || editper.equals("Y")){%>
                                                                    <div class="col-lg-2 text-right"><a id="assignhref" href="javascript:;" class="assign_training inactive_btn" data-bs-toggle="modal" data-bs-target="#manage_selected_personal_details_modal" onclick="javascript: setCourseModalassign();">Assign&nbsp;Training</a> </div>
                                                                    <%}%>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <div class="table-rep-plugin sort_table">
                                                        <div class="table-responsive mb-0 ellipse_code">
                                                            <table id="tech-companies-1" class="table table-striped">
                                                                <thead>
                                                                    <tr>
                                                                        <th width="3%" class="select_check_box">
                                                                            <label class="mt-checkbox mt-checkbox-outline"> 
                                                                                <input type="checkbox" name="coursecball" id="coursecball" onchange="javascript: setallcb2();"/>
                                                                                <span></span>
                                                                            </label>	
                                                                        </th>
                                                                        <th width="4%" class="text-center">
                                                                            <div class="toggled-off_02 usefool_tool" id='togid'>
                                                                                <div class="toggle-title_02">
                                                                                    <img src="../assets/images/filter.png" class="fa-angle-left"/>
                                                                                    <img src="../assets/images/filter_up.png" class="fa-angle-right"/>
                                                                                </div>
                                                                                <div class="toggle-content">
                                                                                    <ul>                                                                                        
                                                                                        <li class=""><a href="javascript: setfilter2('-1');"><span id='spansel-1' class="round_circle circle_courses selected_circle"></span> Total</a></li>
                                                                                        <li><a href="javascript:setfilter2('1');"><span id='spansel1' class="round_circle circle_unassigned"></span> Unassigned</a></li>
                                                                                        <li><a href="javascript:setfilter2('2');"><span id='spansel2' class="round_circle circle_pending "></span> Pending</a></li>
                                                                                        <li><a href="javascript:setfilter2('3');"><span id='spansel3' class="round_circle circle_complete"></span> Complete</a></li>
                                                                                        <li><a href="javascript:setfilter2('4');"><span id='spansel4' class="round_circle circle_exipry"></span> Expiry in 45 days</a></li>
                                                                                        <li><a href="javascript:setfilter2('5');"><span id='spansel5' class="round_circle circle_exipry"></span> Expiry in 45-65 days </a></li>
                                                                                        <li><a href="javascript:setfilter2('6');"><span id='spansel6' class="round_circle circle_exipry"></span> Expiry in 90 days </a></li>
                                                                                        <li><a href="javascript:setfilter2('7');"><span id='spansel7' class="round_circle circle_exipred"></span> Expired</a></li>
                                                                                    </ul>
                                                                                </div>
                                                                            </div>	
                                                                        </th>
                                                                        <th width="32%"><span><b>Personnel Names</b></span></th>
                                                                        <th width="20%"><span><b>Position - Rank</b></span></th>
                                                                        <th width="10%"><span><b>Course Type</b></span></th>
                                                                        <th width="8%"><span><b>Priority</b></span></th>
                                                                        <th width="8%"><span><b>Status</b></span></th>
                                                                        <th width="8%"><span><b>Date</b></span></th>
                                                                        <th width="7%" class="text-center"><span><b>Action</b></span></th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody id="detaildiv">
    <%
                                                                    if(total > 0)
                                                                    {
                                                                        String filep, url;
                                                                        for(int  i = 0; i < total; i++)
                                                                        {
                                                                            ManagetrainingInfo minfo = (ManagetrainingInfo) list.get(i);
                                                                            if(minfo != null)
                                                                            {
                                                                                filep = ""; url = "";
                                                                                if(minfo.getFilename() != null && !minfo.getFilename().equals(""))
                                                                                    filep = file_path+minfo.getFilename();
                                                                                else if(minfo.getUrl() != null && !minfo.getUrl().equals(""))
                                                                                    url = minfo.getUrl();
%>
                                                                                <tr>
                                                                                    <td class="select_check_box">
                                                                                    <%if(minfo.getStatus() != 2){%>
                                                                                        <label class="mt-checkbox mt-checkbox-outline"> 
                                                                                            <input type="checkbox" value="<%=minfo.getCandidateId()+"@"+minfo.getPositionId()%>" name="coursecb" class="singlechkbox" onchange="javascript: setcb2();" />
                                                                                            <span></span>
                                                                                        </label>	
                                                                                            <%} else {%>&nbsp;<%}%>
                                                                                    </td>
                                                                                    <td class="text-center"><%= managetraining.getStColour(minfo.getStatus())%></td>
                                                                                    <td><%= (minfo.getName() != null ? minfo.getName() : "") %></td>
                                                                                    <td><%= (minfo.getPositionName() != null ? minfo.getPositionName() : "") %></td>
                                                                                    <td><%= (minfo.getCoursetype() != null ? minfo.getCoursetype() : "") %></td>
                                                                                    <td><%= (minfo.getLevel() != null ? minfo.getLevel() : "") %></td>
                                                                                    <td><%= (minfo.getStatusval() != null ? minfo.getStatusval() : "") %></td>
                                                                                    <td><%= (minfo.getDate() != null ? minfo.getDate() : "") %></td>
                                                                                    <td class="action_column text-center">
                                                                                        <% if(!filep.equals("")) {%><a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%=filep %>');"><img src="../assets/images/view.png"/> </a><% } else if(!url.equals("")) {%><a href="<%=url%>" class="mr_15" target="_blank"><img src="../assets/images/view.png"/></a><% } else {%><a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a><%}%>
                                                                                        <%if(addper.equals("Y") || editper.equals("Y")){%><a class="" data-bs-toggle="modal" data-bs-target="#personal_course_details_modal_01" href="javascript:;" onclick="javascript: setPersonalModal('<%=minfo.getCandidateId()%>','<%=minfo.getClientmatrixdetailid()%>','2');"><img src="../assets/images/pencil.png"></a><% } else {%>&nbsp;<% } %>
                                                                                    </td>
                                                                                </tr>
<%
                                                                            }
                                                                        }
                                                                   }else{
%>
                                                                            <tr><td colspan='9'>No information available.</td></tr>
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
                        <!-- End Page-content -->
                    </div>
                    <!-- end main content-->
            </div>
        </div>
        <%@include file ="../footer.jsp"%>
        <div id="personal_course_details_modal_01" class="modal fade parameter_modal large_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row" id="personalmodal">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        
        <div id="view_resume_list" class="modal fade" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" onclick="closeViewModel();" data-bs-dismiss="modal"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row" id="viewfilesdiv">
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <div id="manage_selected_personal_details_modal" class="modal fade parameter_modal large_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row" id="coursemodalassign">
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
                        <div class="row" id="coursemailmodal">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="thank_you_modal" class="modal fade thank_you_modal blur_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <h2>Thank You!</h2>
                                <center><img src="../assets/images/thank-you.png"></center>
                                <%if(sendcourseemailassign.equals("yes")){%>
                                <h3>Course Scheduled</h3>
                                <p>Course has been successfully scheduled.</p>
                                <a href="javascript:;" onclick="javascript: setCoursemail('2');" class="msg_button" data-bs-toggle="modal" data-bs-target="#email_det_candidate_modal">Email Course Details</a>
                                <%} else {%>
                                <h3>Certificate Uploaded</h3>
                                <p>Certificate has been uploaded successfully.</p>
                                    <a href="javascript:;" onclick="javascript: closeModal();" class="msg_button">Manage Trainings</a>
                                <%}%>
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
                        <span class="resume_title">File</span>
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
        <!-- JAVASCRIPT -->
        <script src="../assets/libs/jquery/jquery.min.js"></script>        
        <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/app.js"></script>
        <script src="../assets/js/rwd-table.min.js"></script>
        <script src="../assets/js/table-responsive.init.js"></script>
        <script src="../assets/js/bootstrap-select.min.js"></script> 
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
        <script src="/jxp/assets/js/sweetalert2.min.js"></script>
        <% if(sendcourseemail.equals("yes")){%>
        <script type="text/javascript">
            $(window).on('load', function () {
                $('#thank_you_modal').modal('show');
            });
        </script>
        <%}%>
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