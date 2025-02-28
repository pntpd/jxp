<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.managewellness.ManagewellnessInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="managewellness" class="com.web.jxp.managewellness.Managewellness" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 6, submtp = 73;
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
            ManagewellnessInfo info = null;
            if(request.getAttribute("MANAGEWELLNESS_INFO1") != null)
            {
                info = (ManagewellnessInfo) request.getAttribute("MANAGEWELLNESS_INFO1");
                request.removeAttribute("MANAGEWELLNESS_INFO1");
            }
            ArrayList list = new ArrayList();
            if(request.getAttribute("WELLASSIGNLIST1") != null)
            {
                list = (ArrayList) request.getAttribute("WELLASSIGNLIST1");
                request.removeAttribute("WELLASSIGNLIST1");
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
            int total_position = 0, total_candidate = 0, filledin1 = 0, filledin3 = 0, filledin5 = 0, total_feedback = 0, filled = 0, sent = 0;
            if(session.getAttribute("ARR_COUNT") != null)
            {
                arr = (int[]) session.getAttribute("ARR_COUNT");
                total_position = arr[0];
                total_candidate = arr[1];
                filledin1 = arr[2];
                filledin3 = arr[3];
                filledin5 = arr[4];
                total_feedback = arr[5];
                filled = arr[6];
                sent = arr[7];
                session.removeAttribute("ARR_COUNT");
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
%>
<head>
    <meta charset="utf-8">
    <title><%= managewellness.getMainPath("title") != null ? managewellness.getMainPath("title") : "" %></title>
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
    <script type="text/javascript" src="../jsnew/managewellness.js"></script>
    <script type="text/javascript" src="../jsnew/validation.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/managewellness/ManagewellnessAction.do" onsubmit="return false;" enctype="multipart/form-data">
        <html:hidden property="clientIdIndex"/> 
        <html:hidden property="assetIdIndex"/> 
        <html:hidden property="crewrotationId" />
        <html:hidden property="mode" />
        <html:hidden property="positionIdIndex" />
        <html:hidden property="categoryIdIndex" />
        <html:hidden property="subcategoryIdIndex" />
        <html:hidden property="doCancel"/>
        <html:hidden property="positionId"/> 
        <html:hidden property="ftype" />
        <html:hidden property="search" />
        <html:hidden property="doSearch" />
        <html:hidden property="doView" />
        <html:hidden property="categoryId" />
        <html:hidden property="subcategoryId" />
        
        <input type='hidden' name="candidateId" value='<%=info.getCandidateId()%>' />
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
                                    <span>Manage Wellness</span>
                                </div>                                
                                <div class="float-end">         
                                    <div class="role mr_25">
                                        <label>Select Mode:</label>
                                        <select class="form-select select_role" name='mode_top' id="mode_top" onchange="javascript: gotomode();">
                                            <option value="1" selected>Personnel List</option>
                                            <option value="2">Category List</option>
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
                                                <div class="col-md-4 com_label_value pd_0">
                                                    <div class="row d-flex align-items-center mb_0">
                                                        <div class="col-md-8 com_label_value">
                                                            <div class="row mb_0">
                                                                <div class="col-md-4 pd_right_0"><label>Personnel</label></div>
                                                                <div class="col-md-8"><span><%= info != null && info.getName() != null ? info.getName() : "" %></span></div>
                                                            </div>
                                                            <div class="row mb_0">
                                                                <div class="col-md-4 pd_right_0"><label>Position-Rank</label></div>
                                                                <div class="col-md-8"><span><%= info != null && info.getPositionName() != null ? info.getPositionName() : "" %></span></div>
                                                            </div>
                                                        </div>
                                                            <div class="col-md-4 pd_0">
                                                                <div class="row d-flex align-items-center per_com">	
                                                                    <div class="col-md-6 pd_0 text-left"><label>Percentage Filled<br/></label></div>
                                                                    <div class="col-md-6 pd_0"><span><% if(total_feedback > 0) {%><%=managewellness.getDecimal(((double)filled / (double)total_feedback) * 100.0)%><% } else { %>0.00<% } %> %</span></div>
                                                                </div>	
                                                            </div>
                                                    </div>
                                                </div>
                                                
                                                <div class="col-md-2 pd_right_0">
                                                <div class="ref_vie_ope onshore cou_una_pen_com tot_sent_filled mw_tot_sent_filled">
                                                    <ul>
                                                        <li class="courses_bg mob_value">Total Feedback<span><%=managewellness.changeNum(total_feedback, 2)%></span></li>
                                                        <li class="pending_bg mob_value">Sent <span><%=managewellness.changeNum(sent, 2)%></span></li>
                                                        <li class="complete_bg mob_value">Filled <span><%=managewellness.changeNum(filled, 2)%></span></li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="col-md-3 pd_right_0">
                                                <div class="ref_vie_ope offshore expiry_days req_filled">
                                                    <ul> 
                                                        <li class="mob_value offshore_border">
                                                            <label>Required to be</label> 
                                                            <div class="full_width"><span>Filled in (Days)</span></div>
                                                        </li>
                                                        <li class="mob_value">
                                                            <label>1</label> 
                                                            <div class="full_width"><span class="expiry_days_value "><%=managewellness.changeNum(filledin1, 2)%></span></div>
                                                        </li>
                                                        <li class="mob_value">
                                                            <label>3</label> 
                                                            <div class="full_width"><span class="expiry_days_value"><%=managewellness.changeNum(filledin3, 2)%></span></div>
                                                        </li>
                                                        <li class="mob_value">
                                                            <label>5</label> 
                                                            <div class="full_width"><span class="expiry_days_value"><%=managewellness.changeNum(filledin5, 2)%></span></div>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>                                                
                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="com_view_job"><a href="javascript: viewprofile();"><img src="../assets/images/view.png"/><br/> View Profile</a></li>
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
                                                                    <div class="col-lg-2 col-md-2 col-sm-12 col-12">
                                                                        <div class="input-group bootstrap-touchspin bootstrap-touchspin-injected short_list_search">
                                                                            <span class="input-group-btn input-group-prepend search_label">Search:</span>
                                                                            <html:text property ="searchdetail" styleId="example-text-input" styleClass="form-control" maxlength="200" readonly="true" onfocus="if (this.hasAttribute('readonly')) {this.removeAttribute('readonly'); this.blur(); this.focus();}"/>
                                                                            <a class="search_view index_search_view" href="javascript: searchFormDetail();"><img src="../assets/images/view_icon.png"></a>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-lg-2">
                                                                        <div class="row">
                                                                            <div class="col-sm-12 field_ic">
                                                                                <input class="form-control" type="text" value="<%= info != null && info.getClientName() != null ? info.getClientName() : "" %>" readonly />
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-lg-2">
                                                                        <div class="row">
                                                                            <div class="col-sm-12 field_ic">
                                                                                <input class="form-control" type="text" value="<%= info != null && info.getAssetName() != null ? info.getAssetName() : "" %>" readonly />
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-lg-2">
                                                                        <div class="row">
                                                                            <div class="col-sm-12 field_ic">
                                                                                <html:select property="categoryIdDetail" styleId="categoryIdDetail" styleClass="form-select" onchange="javascript: setSubcategorydetail();">
                                                                                    <html:optionsCollection filter="false" property="categories" label="ddlLabel" value="ddlValue">
                                                                                    </html:optionsCollection>
                                                                                </html:select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-lg-2">
                                                                        <div class="row">
                                                                            <div class="col-sm-12 field_ic">
                                                                                <html:select property="subcategoryIdDetail" styleId="subcategoryIdDetail" styleClass="form-select" onchange="javascript: searchFormDetail();">
                                                                                    <html:optionsCollection filter="false" property="subcategories" label="ddlLabel" value="ddlValue">
                                                                                    </html:optionsCollection>
                                                                                </html:select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-lg-1 pd_0">
                                                                        <div class="row">
                                                                            <div class="col-sm-12 field_ic">
                                                                                <html:select property="statusIndex" styleId="statusIndex" styleClass="form-select" onchange="javascript: searchFormDetail();">
                                                                                    <html:option value="-1">- Status -</html:option>
                                                                                    <html:option value="1">UnScheduled</html:option>
                                                                                    <html:option value="2">Scheduled</html:option>
                                                                                </html:select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
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
                                                                        <th width="25%"><span><b>Sub-Category</b></span></th>
                                                                        <th width="21%"><span><b>Category</b></span></th>
                                                                        <th width="10%" class="text-center"><span><b>Questions</b></span></th>
                                                                        <th width="10%"><span><b>Schedule</b></span></th>
                                                                        <th width="10%"><span><b>Start Date</b></span></th>
                                                                        <th width="10%"><span><b>End Date</b></span></th>
                                                                        <th width="10%" class="text-center"><span><b>Action</b></span></th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody id="detaildiv">
<%
                                                                    if(total > 0)
                                                                    {
                                                                        for(int  i = 0; i < total; i++)
                                                                        {
                                                                            ManagewellnessInfo minfo = (ManagewellnessInfo) list.get(i);
                                                                            if(minfo != null)
                                                                            {
%>
                                                                                <tr>
                                                                                    <td><%= (minfo.getSubcategoryName() != null ? minfo.getSubcategoryName() : "") %></td>
                                                                                    <td><%= (minfo.getCategoryName() != null ? minfo.getCategoryName() : "") %></td>
                                                                                    <td class="assets_list text-center" data-org-colspan="1" data-columns="tech-companies-1-col-3">
                                                                                        <a href="javascript:;" class="off_total_value"><%= (minfo.getQuestCount()) %></a>
                                                                                    </td>
                                                                                    <td><%= (minfo.getRepeatvalue() != null ? minfo.getRepeatvalue() : "") %></td>
                                                                                    <td><%= (minfo.getFromdate() != null ? minfo.getFromdate() : "") %></td>
                                                                                    <td><%= (minfo.getTodate() != null ? minfo.getTodate() : "") %></td>
                                                                                    <td class="action_column text-center"><%if(addper.equals("Y") || editper.equals("Y")){%><a class="mr_15" data-bs-toggle="modal" data-bs-target="#medical_emer_details_modal_01" href="javascript:;" onclick="javascript: setQuestionModal('<%=minfo.getSubcategoryId()%>','<%=info.getPositionId()%>','-1');"><img src="../assets/images/view.png"></a><% } else {%>&nbsp;<% } %></td>
                                                                                </tr>
<%
                                                                            }
                                                                        }
                                                                     }else{
%>
                                                                                <tr><td colspan="8">No information available.</td></tr>
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

        <div id="medical_emer_details_modal_01" class="modal fade parameter_modal define_modal large_modal">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row" id = 'questionmodal'>
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
                                <a href="javascript:;" onclick="javascript: setCoursemail('1');" class="msg_button" data-bs-toggle="modal" data-bs-target="#email_det_candidate_modal">Email Course Details</a>
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