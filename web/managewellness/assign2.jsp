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
            if(request.getAttribute("MANAGEWELLNESS_INFO2") != null)
            {
                info = (ManagewellnessInfo) request.getAttribute("MANAGEWELLNESS_INFO2");
                request.removeAttribute("MANAGEWELLNESS_INFO2");
            }
            ArrayList list = new ArrayList();
            if(request.getAttribute("WELLASSIGNLIST2") != null)
            {
                list = (ArrayList) request.getAttribute("WELLASSIGNLIST2");
                request.removeAttribute("WELLASSIGNLIST2");
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
    <html:form action="/managewellness/ManagewellnessAction.do" onsubmit="return false;"  enctype="multipart/form-data">
        <html:hidden property="clientIdIndex"/> 
        <html:hidden property="assetIdIndex"/> 
        <html:hidden property="crewrotationId" />
        <html:hidden property="doAssignSave1" />
        <html:hidden property="mode" />
        <html:hidden property="positionIdIndex" />
        <html:hidden property="categoryIdIndex" />
        <html:hidden property="subcategoryIdIndex" />
        <html:hidden property="doCancel"/>
        <html:hidden property="search" />      
        <html:hidden property="doSearch" />
        <html:hidden property="doAssign2" />
        <html:hidden property="categoryId" />
        <html:hidden property="subcategoryId" />
        <html:hidden property="doIndexSubcategory" />
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
                                            <option value="1">Personnel List</option>
                                            <option value="2" selected>Category List</option>
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
                                                
                                                <div class="col-md-4 com_label_value pd_0">
                                                    <div class="row d-flex align-items-center mb_0">
                                                        <div class="col-md-8 com_label_value">
                                                            <div class="row mb_0">
                                                                <div class="col-md-5 pd_right_0"><label>Sub-Category Name</label></div>
                                                                <div class="col-md-7"><span><%= info != null && info.getSubcategoryName() != null ? info.getSubcategoryName() : "" %></span></div>
                                                            </div>
                                                            <div class="row mb_0">
                                                                <div class="col-md-5 pd_right_0"><label>Category Name</label></div>
                                                                <div class="col-md-7"><span><%= info != null && info.getCategoryName() != null ? info.getCategoryName() : ""%></span></div>
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
                                                <div class="col-md-2">
                                                    <div class="ref_vie_ope onshore exp_pos_per pos_per">
                                                        <ul>
                                                            <li class="positions_bg mob_value">Positions <span><%=managewellness.changeNum(total_position, 2)%></span></li>
                                                            <li class="personnel_bg mob_value">Personnel <span><%=managewellness.changeNum(total_candidate, 2)%></span></li>
                                                        </ul>
                                                    </div>
                                                </div>     
                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="com_view_job"><a href="javascript: viewSubcategory('<%=info.getCategoryId()%>');"><img src="../assets/images/view.png"/><br/> View Sub-Category</a></li>
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
                                                                            <a class="search_view index_search_view" href="javascript: searchFormposition();"><img src="../assets/images/view_icon.png"></a>
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

                                                                            <div class="col-lg-4">
                                                                        <div class="row">
                                                                            <label for="example-text-input" class="col-sm-3 col-form-label text-right">Position:</label>
                                                                            <div class="col-sm-7 field_ic">
                                                                               <html:select styleClass="form-select" property="positionId2Index" styleId="positionId2Index" onchange = " javascript: searchFormposition();">
                                                                                    <html:optionsCollection property="positions" value="ddlValue" label="ddlLabel"></html:optionsCollection>
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
                                                                        <th width="29%" id="tech-companies-1-col-1"><span><b>Personnel Names</b></span></th>
                                                                        <th width="44%" id="tech-companies-1-col-2"><span><b>Position - Rank</b></span></th>
                                                                        <th width="10%" class="text-center" id="tech-companies-1-col-3"><span><b>Questions</b></span></th>                                                                     
                                                                        <th width="10%" class="text-center" id="tech-companies-1-col-4"><span><b>Action</b></span></th>
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
                                                                                    <td><%= (minfo.getName() != null ? minfo.getName() : "") %></td>
                                                                                    <td><%= (minfo.getPositionName() != null ? minfo.getPositionName() : "") %></td>
                                                                                    <td class="assets_list text-center">
                                                                                        <a href="javascript:;" class="off_total_value"><%=managewellness.changeNum(minfo.getQuestCount(),2)%></a></a>
                                                                                    </td>
                                                                                    <td class="action_column text-center"><%if(addper.equals("Y") || editper.equals("Y")){%><a class="mr_15" data-bs-toggle="modal" data-bs-target="#medical_emer_details_modal_01" href="javascript:;" onclick="javascript: setQuestionModal('<%=info.getSubcategoryId()%>','<%=minfo.getPositionId()%>','<%=minfo.getCrewrotationId()%>');"><img src="../assets/images/view.png"></a><% } else {%>&nbsp;<% } %></td>
                                                                                </tr>
<%
                                                                            }
                                                                        }
                                                                      }else{
%>
                                                                                <tr><td colspan="5">No information available.</td></tr>
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