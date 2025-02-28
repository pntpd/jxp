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

        ArrayList selectedcand_list = new ArrayList();
        if (session.getAttribute("PLCANDIDATESLIST") != null) {
            selectedcand_list = (ArrayList) session.getAttribute("PLCANDIDATESLIST");
        }
        int total = selectedcand_list.size();


        String file_path = crewrotation.getMainPath("view_candidate_file");
        String cphoto = "../assets/images/empty_user.png";

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

            String redirecttosignon = "no";
            if(request.getAttribute("REQUIREDDOCUMENTSAVE") != null)
            {
                redirecttosignon = (String)request.getAttribute("REQUIREDDOCUMENTSAVE");
                request.removeAttribute("REQUIREDDOCUMENTSAVE");
            }
            String crId = "";
            if(request.getAttribute("CRID") != null)
            {
                crId = (String)request.getAttribute("CRID");
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
        <!-- Begin page -->
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content tab_panel1 no_tab1">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow"><a href="javascript: gobackview();"><img  src="../assets/images/back-arrow.png"/></a> <span>Crew Rotation</span></div>
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
                                            <li><a href="javascript: exporttoexcelplanning();"><img src="../assets/images/export-data.png"/> Export Data</a></li>
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
                                                <div class="col-md-4 com_label_value">
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Client - Asset</label></div>
                                                        <div class="col-md-9"><span><%=  info.getClientName()+" "+ info.getClientAsset() %></span></div>
                                                    </div>
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Location</label></div>
                                                        <div class="col-md-9"><span><%= info.getCountry() %></span></div>
                                                    </div>
                                                </div>
                                                    
                                                    <div class="col-md-2 com_label_value">
                                                    <div class="row mb_0">
                                                        <div class="col-md-6"><label>Total Positions</label></div>
                                                        <div class="col-md-6"><span><%= info.getPositionCount() %></span></div>
                                                    </div>
                                                    <div class="row mb_0">&nbsp;</div>
                                                </div>

                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope onshore">
                                                        <ul>
                                                            <li class="mob_value">Onshore <span><%=  crewrotation.changeNum( info.getStatus1(),2) %>&nbsp;</span></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="ref_vie_ope offshore">
                                                        <ul>
                                                            <li class="mob_value offshore_border"><label>Offshore</label> <div class="full_width"><span><%= info.getNormal()+info.getDelayed()+info.getExtended() %></span></div></li>
                                                            <li class="mob_value"><label>Normal</label> <div class="full_width"><span class="off_normal_text"><%= info.getNormal() %></span></div></li>
                                                            <li class="mob_value"><label>Extended</label> <div class="full_width"><span class="off_extended_text"><%= info.getDelayed() %></span></div></li>
                                                            <li class="mob_value"><label>Overstay</label> <div class="full_width"><span class="off_overstay_text"><%= info.getExtended() %></span></div></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="mob_value">Total <span><%= info.getStatus1()+info.getNormal()+ info.getDelayed()+info.getExtended() %></span></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                    
                                                    <div class="col-md-1">
                                                        <div class="ref_vie_ope">
                                                            <ul>
                                                                <li class="com_view_job"><a href="javascript:;"><img src="../assets/images/view.png"/><br/> View Profile</a></li>
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
                                                                    <div class="col-md-12 col-xl-12">
                                                                        <div class="search_export_area"> 
                                                                            <div class="row">
                                                                                <div class="col-lg-12">
                                                                                    <div class="row mb-3">
                                                                                        <div class="col-lg-3">
                                                                                            <div class="row">
                                                                                                <label for="example-text-input" class="col-sm-3 col-form-label">Search:</label>
                                                                                                <div class="col-sm-9 field_ic">
                                                                                                    <html:text property ="searchp" styleId="example-text-input" styleClass="form-control" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                                        this.removeAttribute('readonly');
                                                                                        this.blur();
                                                                                        this.focus();
                                                                                    }"/>
                                                                                                    <a href="javascript: viewplanSearch();" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="col-lg-4">
                                                                                            <div class="row">
                                                                                                <label for="example-text-input" class="col-sm-5 col-form-label text-right">Date Range Search:</label>
                                                                                                <div class="col-sm-7">
                                                                                                    <div class="row">
                                                                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                                                                            <div class="input-daterange input-group input-addon">
                                                                                                                <html:text property="fromDate" styleId="wesl_from_dt" styleClass="form-control wesl_from_dt"/>
                                                                                                                <script type="text/javascript">
                                                                                                                    document.getElementById("wesl_from_dt").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                                                                                </script>
                                                                                                                <div class="input-group-add">
                                                                                                                    <span class="input-group-text">To</span>
                                                                                                                </div>
                                                                                                                <html:text property="toDate" styleId="wesl_to_dt" styleClass="form-control wesl_from_dt"/>
                                                                                                                <script type="text/javascript">
                                                                                                                    document.getElementById("wesl_to_dt").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                                                                                </script>
                                                                                                            </div>
                                                                                                            
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="col-lg-1 col-md-1 col-sm-12 col-12">
                                                                                            <a href="javascript: viewplanSearch();" class="go_btn">Go</a>
                                                                                        </div>
                                                                                        <div class="col-lg-2">
                                                                                            <div class="row">
                                                                                                <div class="col-sm-12 field_ic">
                                                                                                    <html:select styleClass="form-select" property="positionIdPlan" styleId="positionIdPlan" onchange = " javascript: viewplanSearch();">
                                                                                                        <html:optionsCollection property="positions" value="ddlValue" label="ddlLabel"></html:optionsCollection>
                                                                                                    </html:select>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="col-lg-2">
                                                                                            <div class="row">
                                                                                                <div class="col-sm-12 field_ic">
                                                                                                    <html:select property="statusPlan" styleId="statusPlan" styleClass="form-select" onchange = " javascript: viewplanSearch();">
                                                                                                        <html:option value="0"> Select status</html:option>
                                                                                                        <html:option value="1"> Offshore </html:option>
                                                                                                        <html:option value="2"> Onshore </html:option>
                                                                                                    </html:select>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>

                                                                            </div>
                                                                        </div>
                                                                    </div>

                                                                    <div class="table-rep-plugin sort_table">
                                                                        <div class="table-responsive mb-0 ellipse_code">
                                                                            <table id="tech-companies-1" class="table table-striped">
                                                                                <thead>
                                                                                    <tr>
                                                                                        <th width="27%"><span><b>Position</b></span></th>
                                                                                        <th width="27%"><span><b>Personnel</b></span></th>
                                                                                        <th width="13%"><span><b>From Date</b></span></th>
                                                                                        <th width="13%"><span><b>To Date</b></span></th>
                                                                                        <th width="8%"><span><b>Status</b></span></th>
                                                                                        <th width="12%" class="text-center"><span><b>Duration (in days)</b></span></th>
                                                                                    </tr>

                                                                                </thead>
                                                                                <tbody>
                                                                                                                                            <%
                                                                                        for(int i = 0 ;i < total;i++){
                                                                                        CrewrotationInfo oinfo = (CrewrotationInfo)selectedcand_list.get(i);
                                                                                        if (oinfo != null) {
                                                                                        %>
                                                                                            <tr>
                                                                                                <td><%= oinfo.getPosition()%></td>
                                                                                                <td><%= oinfo.getName()%></td>
                                                                                                <td><%= oinfo.getFromdate()%></td>
                                                                                                <td><%= oinfo.getTodate()%></td>
                                                                                                <td><%= oinfo.getTypevalue()%></td>
                                                                                                <td class="assets_list text-center"><span class="off_total_value"><%= oinfo.getDatedifference()%></span></td>
                                                                                            </tr>
                                                                                                                                                                            <%
                                                                                        }
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
                <!-- End Page-content -->
            </div>
            <!-- end main content-->
        </div>
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
				var date_pick = ".wesl_from_dt, .wesl_to_dt";
				$(date_pick).datepicker({
					todayHighlight: !0,
					format: "dd-M-yyyy",
					autoclose: "true",
				});
			});
		</script>


    <% if(redirecttosignon.equals("yes")){%>
    <script type="text/javascript">
        $(window).on('load', function () {
            getsignonModel(0, 0);
            $('#sign_on_details_modal').modal('show');
        });
    </script>
    <%}%>
    <% if(!crId.equals("")){%>
    <script type="text/javascript">
            $(window).on('load', function () {                
                window.location.hash="a_<%=crId%>";
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
