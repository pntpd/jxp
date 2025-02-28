<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.crewinsurance.CrewinsuranceInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="crewinsurance" class="com.web.jxp.crewinsurance.Crewinsurance" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 6, submtp = 71;
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
            ArrayList crewinsurance_list = new ArrayList();
            int count = 0;
            int recordsperpage = crewinsurance.getCount();
            if(session.getAttribute("COUNT_LIST") != null)
                count = Integer.parseInt((String) session.getAttribute("COUNT_LIST"));
            if(session.getAttribute("CREWINSURANCE_LIST") != null)
                crewinsurance_list = (ArrayList) session.getAttribute("CREWINSURANCE_LIST");
            int pageSize = count / recordsperpage;
            if(count % recordsperpage > 0)
                pageSize = pageSize + 1;

            int total = crewinsurance_list.size();
            int showsizelist = crewinsurance.getCountList("show_size_list");
            int CurrPageNo = 1;

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

            String filename = "";
            if (session.getAttribute("CERTFILENAME") != null)
            {
                filename = (String)session.getAttribute("CERTFILENAME");
                if(filename != null && !filename.equals(""))
                    filename = crewinsurance.getMainPath("view_candidate_file")+filename;
            }
            
            String thankyou = "no";
            if(request.getAttribute("SAVECERTMODEL") != null)
            {
                thankyou = (String)request.getAttribute("SAVECERTMODEL");
                request.removeAttribute("SAVECERTMODEL");
            }
            
            int arr[] = new int[7];
            int total_candidate = 0, pending_count = 0, uploaded_count = 0, expired1 = 0, expired2 = 0, expired3 = 0, expired = 0, extend_days = 0;
            if(session.getAttribute("ARR_COUNT") != null)
            {
                arr = (int[]) session.getAttribute("ARR_COUNT");
                total_candidate = arr[0];
                pending_count = arr[1];
                uploaded_count = arr[2];
                expired1 = arr[3];
                expired2 = arr[4];
                expired3 = arr[5];
                expired = arr[6];
            }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= crewinsurance.getMainPath("title") != null ? crewinsurance.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <!-- Responsive Table css -->
        <link href="../assets/css/rwd-table.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">

        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/crewinsurance.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/crewinsurance/CrewinsuranceAction.do" onsubmit="return false;" enctype="multipart/form-data">
            <html:hidden property="doAdd"/>
            <html:hidden property="doSave"/>   
            <html:hidden property="doSearch"/>
            <html:hidden property="doModify"/>
            <html:hidden property="crewinsuranceId"/>
            <html:hidden property="crewrotationId"/>
            <html:hidden property="certificatefilehidden"/>
            <html:hidden property="currentDate"/>
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
                                    <a href="javascript: backtoold();"><img src="../assets/images/back-arrow.png"/></a>
                                    <span>Insurance</span>
                                </div>

                                <div class="float-end">
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
                                                        <div class="row d-flex align-items-center mb_0">
                                                            <div class="col-md-4">
                                                                <div class="row">	
                                                                    <div class="col-md-12 mb_10">
                                                                        <html:select property="clientIdIndex" styleId="clientIdIndex" styleClass="form-select" onchange="javascript: setAssetDDL();">
                                                                            <html:optionsCollection filter="false" property="clients" label="ddlLabel" value="ddlValue">
                                                                            </html:optionsCollection>
                                                                        </html:select>	
                                                                    </div>
                                                                    <div class="col-md-12">
                                                                        <html:select property="assetIdIndex" styleId="assetIdIndex" styleClass="form-select" onchange="javascript: searchFormAsset();">
                                                                            <html:optionsCollection filter="false" property="assets" label="ddlLabel" value="ddlValue">
                                                                            </html:optionsCollection>
                                                                        </html:select> 	
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-4 offset-md-4">
                                                                <div class="row d-flex align-items-center per_com">	
                                                                    <div class="col-md-6  text-right"><label>Percentage Completion</label></div>
                                                                    <div class="col-md-6 pd_0"><span><% if(uploaded_count > 0) {%><%=crewinsurance.getDecimal(((double)uploaded_count / (double)total_candidate) * 100.0)%><% } else { %>0.00<% } %> %</span></div>
                                                                </div>	
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <div class="ref_vie_ope onshore exp_pos_per">
                                                            <ul>
                                                                <li class="unassigned_bg mob_value">Personnel <span><%=crewinsurance.changeNum(total_candidate, 2)%></span></li>
                                                                <li class="pending_bg mob_value">Pending <span><%=crewinsurance.changeNum(pending_count, 2)%></span></li>
                                                                <li class="complete_bg mob_value">Uploaded <span><%=crewinsurance.changeNum(uploaded_count, 2)%></span></li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <div class="ref_vie_ope offshore expiry_days">
                                                            <ul>
                                                                <li class="mob_value offshore_border"><label>Expiry in</label> <div class="full_width"><span>(Days)</span></div></li>
                                                                <li class="mob_value"><label>45</label> <div class="full_width"><span class="expiry_days_value "><%=crewinsurance.changeNum(expired1, 2)%></span></div></li>
                                                                <li class="mob_value"><label>45 to 65</label> <div class="full_width"><span class="expiry_days_value"><%=crewinsurance.changeNum(expired2, 2)%></span></div></li>
                                                                <li class="mob_value"><label>65 to 90</label> <div class="full_width"><span class="expiry_days_value"><%=crewinsurance.changeNum(expired3, 2)%></span></div></li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <div class="ref_vie_ope">
                                                            <ul>
                                                                <li class="mob_value expired_bg">Expired <span><%=crewinsurance.changeNum(expired, 2)%></span></li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12 col-xl-12">
                                                <div class="search_export_area">
                                                    <div class="row">
                                                        <div class="col-lg-9">
                                                            <div class="row mb-3">
                                                                <div class="col-lg-3">
                                                                    <div class="row">
                                                                        <label for="example-text-input" class="col-sm-3 col-form-label">Search:</label>
                                                                        <div class="col-sm-9 field_ic">
                                                                            <html:text property ="search" styleClass="form-control" styleId="example-text-input" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                                       this.removeAttribute('readonly');
                                                                                       this.blur();
                                                                                       this.focus();
                                                                                       }"/>
                                                                            <a href="javascript: searchFormAjax('s','-1');" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                            
                                                                <div class="col-lg-4">
                                                                    <div class="row">
                                                                        <label class="col-sm-3 col-form-label text-right">Position:</label>
                                                                        <div class="col-sm-9 field_ic">
                                                                            <html:select property="positionIdIndex" styleId="positiondIndex" styleClass="form-select" onchange="javascript: searchFormAjax('s','-1');">
                                                                                <html:optionsCollection filter="false" property="positions" label="ddlLabel" value="ddlValue">
                                                                                </html:optionsCollection>
                                                                            </html:select> 
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div class="col-lg-4">
                                                                    <div class="row">
                                                                        <label class="col-sm-3 col-form-label text-right">Status:</label>
                                                                        <div class="col-sm-9 field_ic">
                                                                            <html:select property="statusIndex" styleId="statusIndex" styleClass="form-select" onchange="javascript: searchFormAjax('s','-1');">
                                                                                <html:option value="">Select Status</html:option>
                                                                                <html:option value="1">Pending</html:option>
                                                                                <html:option value="2">Uploaded</html:option>
                                                                                <html:option value="3">Expiry in 45 days</html:option>
                                                                                <html:option value="4">Expiry in 45 to 65 days</html:option>
                                                                                <html:option value="5">Expiry in 90 days</html:option>
                                                                                <html:option value="6">Expired</html:option>
                                                                            </html:select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                            
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-3 text-right">
                                                            <a href="javascript:resetFilter();" class="reset_export mr_8"><img src="../assets/images/refresh.png"/> Reset Filters</a>
                                                            <a href="javascript:;" class="add_user">Advance Filter</a>
                                                        </div>                   
                                                    </div>    
                                                </div>
                                            </div>
                                        </div>	
                                        <div class="container-fluid pd_0">
                                            <div class="row">
                                                <% if(!message.equals("")) {%><div class="alert <%=clsmessage%> alert-dismissible fade show">
                                                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button><%=message%>
                                                </div><% } %>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12 col-xl-12">
                                                    <div class="body-background mt_0">
                                                        <div class="row" id='ajax_cat'>       
                                                            <div class="col-lg-12" id="printArea">
                                                                <div class="table-rep-plugin sort_table">
                                                                    <div class="table-responsive mb-0" data-bs-pattern="priority-columns">        
                                                                        <table id="tech-companies-1" class="table table-striped">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th width="40%">
                                                                                        <span><b>Personnel Name</b></span>
                                                                                        <a href="javascript: sortForm('1', '2');" id="img_1_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                                        <a href="javascript: sortForm('1', '1');" id="img_1_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                                    </th>
                                                                                    <th width="36%">
                                                                                        <span><b>Position-Rank</b></span>
                                                                                        <a href="javascript: sortForm('2', '2');" id="img_2_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                                        <a href="javascript: sortForm('2', '1');" id="img_2_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                                    </th>
                                                                                    <th width="14%">
                                                                                        <span><b>Status</b></span>
                                                                                        <a href="javascript: sortForm('3', '2');" id="img_3_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                                        <a href="javascript: sortForm('3', '1');" id="img_3_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                                    </th>
                                                                                    <th width="10%" class="text-right"><span><b>Action</b></span></th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody id="sort_id">
                                                                            <input type="hidden" id="fileprefixpath" value="<%=crewinsurance.getMainPath("view_candidate_file")%>" />
<%
                                                                        if(total > 0)
                                                                        {
                                                                            CrewinsuranceInfo info;
                                                                            for (int i = 0; i < total; i++)
                                                                            {
                                                                                info = (CrewinsuranceInfo) crewinsurance_list.get(i);
                                                                                if (info != null) 
                                                                                {
%>
                                                                                    <tr>
                                                                                        <td><%= info.getName() != null ? info.getName() : ""%></td>
                                                                                        <td><%= info.getPosition() != null ? info.getPosition() : ""%></td>
                                                                                        <td><%= info.getStval() != null ? info.getStval() : "" %></td>                                                                                        
                                                                                        <td class="text-right action_column">
                                                                                            <%if(info.getStatus() > 0){%>
                                                                                                <a href="javascript: ; " data-bs-toggle="modal" data-bs-target="#upload_insurance_certificate" onclick="javascript: getcertdetails('<%= info.getCrewrotationId()%>', '<%= info.getCrewinsuranceId()%>', '2');" class="edit_mode mr_15"><img src="../assets/images/view.png"></a>
                                                                                            <%}else{%>
                                                                                            <%}%>
                                                                                            <a href="javascript: ; " data-bs-toggle="modal" data-bs-target="#upload_insurance_certificate" onclick="javascript: getcertdetails('<%= info.getCrewrotationId()%>', '<%= info.getCrewinsuranceId()%>', '1');" class="edit_mode mr_15"><img src="../assets/images/pencil.png"></a>
                                                                                        </td>
                                                                                    </tr>
<%
                                                                                }
                                                                            }
                                                                        }else{
%>
                                                                                     <tr><td colspan='5'>No information available.</td></tr>
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
                                    <!-- End Page-content -->
                                </div>
                                <!-- end main content-->
                            </div>
                        </div>
                    </div>
                </div>                        
                <!-- END layout-wrapper -->
                <%@include file ="../footer.jsp"%>

                <div id="upload_insurance_certificate" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-lg-12 upload_insurance" id="modaldata">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="thank_you_modal" class="modal fade thank_you_modal blur_modal" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
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
                                        <h3>Insurance Certificate Details Saved</h3>
                                        <p>Crew Insurance Certificate Details have been attached and updated</p>
                                        <a href="javascript: goback();" class="msg_button" style="text-decoration: underline;">Crew Insurance</a>
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
                <!-- Responsive Table js -->
                <script src="../assets/js/rwd-table.min.js"></script>
                <script src="../assets/js/table-responsive.init.js"></script>
                <script src="../assets/js/bootstrap-select.min.js"></script> 
                <script src="../assets/js/bootstrap-datepicker.min.js"></script>
                <script src="/jxp/assets/js/sweetalert2.min.js"></script>

                <script>
                    // toggle class show hide text section
                    $(document).on('click', '.toggle-title', function () {
                    $(this).parent()
                            .toggleClass('toggled-on')
                            .toggleClass('toggled-off');
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
                    $(document).ready(function () {
                    $('input').attr('autocomplete', 'off');
                    });
                </script>
                <script>
                    function setIframeedit(uval)
                    {
                    var url_v = "", classname = "";
                    if (uval.includes(".doc") || uval.includes(".docx") || uval.includes("wordprocessingml.document"))
                    {
                    url_v = "https://docs.google.com/gview?url=" + uval + "&embedded=true";
                    classname = "doc_mode";
                    }
                    else if (uval.includes(".pdf"))
                    {
                    url_v = uval+"#toolbar=0&page=1&view=fitH,100";
                    classname = "pdf_mode";
                    }
                    else
                    {
                    url_v = uval;
                    classname = "img_mode";
                    }
                    window.top.$('#iframe').attr('src', 'about:blank');
                    setTimeout(function () {
                    window.top.$('#iframe').attr('src', url_v);
                    document.getElementById("iframe").className = classname;
                    document.getElementById("diframe").href = uval;
                    }, 1000);
                    $("#iframe").on("load", function() {
                    let head = $("#iframe").contents().find("head");
                    let css = '<style>img{margin: 0px auto;}</style>';
                    $(head).append(css);
                    });
                    }
                </script>                
                <%if(thankyou.equals("yes")){%>
                <script type="text/javascript">
                    $(window).on('load', function () {
                    $('#thank_you_modal').modal('show');
                    });
                </script>
                <%}%>
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
            addLoadEvent(setval());
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