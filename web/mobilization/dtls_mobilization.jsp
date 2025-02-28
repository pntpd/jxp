<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.mobilization.MobilizationInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="mobilization" class="com.web.jxp.mobilization.Mobilization" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 2, submtp = 59, allclient = 0, companytype = 0;
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
         MobilizationInfo info = null;
        if (session.getAttribute("MOBILIZATIONINFO") != null) {
            info = (MobilizationInfo) session.getAttribute("MOBILIZATIONINFO");
        }

        ArrayList crcand_list = new ArrayList();
        if (session.getAttribute("CRCANDIDATESLIST") != null) {
            crcand_list = (ArrayList) session.getAttribute("CRCANDIDATESLIST");
        }
        int total = crcand_list.size();

        String mailmodal="", modaltype="";
        if (request.getAttribute("MAILMODAL") != null) {
            mailmodal = (String) request.getAttribute("MAILMODAL");
        }
        if (request.getAttribute("MODALTYPE") != null) {
            modaltype = (String) request.getAttribute("MODALTYPE");
        }
        
        String file_path = mobilization.getMainPath("view_candidate_file");
        

    %>
    <head>
        <meta charset="utf-8">
        <title><%= mobilization.getMainPath("title") != null ? mobilization.getMainPath("title") : ""%></title>
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
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/mobilization.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/mobilization/MobilizationAction.do" onsubmit="return false;" enctype="multipart/form-data">
        <html:hidden property="doCancel"/>
        <html:hidden property="doView"/>
        <html:hidden property="doViewCancel"/>
        <html:hidden property="doMobTravel"/>
        <html:hidden property="doMobAccomm"/>
        <html:hidden property="doSaveTravel"/>
        <html:hidden property="doSaveAccomm"/>
        <html:hidden property="doMobMail"/>
        <html:hidden property="search"/>
        <html:hidden property="jobpostId"/>
        <html:hidden property="clientId"/>
        <html:hidden property="clientassetId"/>
        <html:hidden property="countryId"/>
        <html:hidden property="candidateId"/>
        <html:hidden property="crewrotationId"/>
        <html:hidden property="clientIdIndex"/>
        <html:hidden property="assetIdIndex"/>
        <html:hidden property="type"/>
        <div id="layout-wrapper">
            <script src="../assets/js/header.js"></script> 
            <%@include file="../header.jsp"%>
            <%@include file="../sidemenu.jsp"%>
            <div class="main-content">
                <div class="page-content tab_panel no_tab1">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow">
                                <a href="javascript:void(0);" onclick="goback();">
                                    <img  src="../assets/images/back-arrow.png"/>
                                </a>
                                <span>Mobilization</span>
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
                                        <ul>
                                            <li><a href="javascript:openinnewtab();"><img src="../assets/images/open-in-new-tab.png"/> Open in New Tab</a></li>
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
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Client - Asset</label></div>
                                                        <div class="col-md-9"><span><%=info.getClientName()%> - <%=info.getClientAsset()%></span></div>
                                                    </div>
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Location</label></div>
                                                        <div class="col-md-9"><span><%=info.getCountry()%></span></div>
                                                    </div>

                                                </div>
                                                <div class="col-md-4 com_label_value">
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Total Positions</label></div>
                                                        <div class="col-md-9"><span><%=info.getPositionCount()%></span></div>
                                                    </div>
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>&nbsp;</label></div>
                                                        <div class="col-md-9"><span>&nbsp; </span></div>
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="mob_value">Ongoing  <span><%=info.getTotalnoofongoing()%></span></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="com_view_job"><a href="javascript:;" onclick="getReqDocList()" ><img src="../assets/images/view.png"/><br/> View Docs List</a></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="com_view_job"><a href="javascript:;" onclick="ViewClient('<%=info.getClientId()%>')"><img src="../assets/images/view.png"/><br/> View Asset</a></li>
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
                                                        <div class="col-lg-6 col-md-6 col-sm-12 col-12"><h2>PERSONNEL <span>- Travel, Accommodation & Mobilization Details</span></h2></div>
                                                        <div class="col-lg-6 col-md-6 col-sm-12 col-12">
                                                            <div class="row">
                                                                <div class="col-lg-4 col-md-4 col-sm-12 col-12">
                                                                    <html:select property="positionId" styleId="positionId" styleClass="form-select" onchange="getCrewCandList();" >
                                                                        <html:optionsCollection filter="false" property="positions" label="ddlLabel" value="ddlValue">
                                                                        </html:optionsCollection>
                                                                    </html:select>
                                                                </div>
                                                                <div class="col-lg-8 col-md-8 col-sm-12 col-12">
                                                                    <div class="input-group bootstrap-touchspin bootstrap-touchspin-injected short_list_search">
                                                                        <span class="input-group-btn input-group-prepend search_label">Search:</span>
                                                                        <html:text property ="txtsearch" styleId="txtsearch" styleClass="form-control" maxlength="200" onkeypress="javascript: handleKeySearch1(event);"  readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                                    this.removeAttribute('readonly');
                                                                    this.blur();
                                                                    this.focus();
                                                                }"/>
                                                                        <a class="search_view" onclick="getCrewCandList();"><img src="../assets/images/view_icon.png"></a>
                                                                        <a href="javascript:;" class="input-group-btn input-group-append search_filter">Filter</a>
                                                                    </div>
                                                                </div>

                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="search_short_main client_sel_candidate personnel_list">
                                                        <ul id="dCRCandidateList">
<%
                                                            String cphoto = "";
                                                            if(total > 0){
                                                                 MobilizationInfo minfo = null;
                                                                 for(int i = 0 ;i < total;i++){
                                                                    minfo = (MobilizationInfo)crcand_list.get(i);
                                                                   if (minfo != null) {
                                                                       cphoto = "../assets/images/empty_user.png";
                                                                   if(!minfo.getPhoto().equals("")){
                                                                       cphoto = file_path+minfo.getPhoto();
                                                                   }
%>
                                                            <li class="odd_list_1">
                                                                <div class="search_box">
                                                                    <div class="row flex-end align-items-center">
                                                                        <div class="col-md-11 comp_view">
                                                                            <div class="row">
                                                                                <div class="col-md-12 client_prof_status">
                                                                                    <div class="row d-flex align-items-start">
                                                                                        <div class="col-md-2 com_view_prof cand_box_img">
                                                                                            <div class="user_photo pic_photo">
                                                                                                <div class="upload_file">
                                                                                                    <img src="<%=cphoto%>">
                                                                                                    <a href="javascript:;" onclick="viewCandidate('<%=minfo.getCandidateId()%>')"><img src="../assets/images/view.png"></a>
                                                                                                </div>
                                                                                            </div>	
                                                                                        </div>
                                                                                        <div class="col-md-10">
                                                                                            <div class="row">
                                                                                                <div class="portlet box status_on_hold">
                                                                                                    <div class="portlet-title">
                                                                                                        <div class="caption"><%=minfo.getName()%> <%    if(!minfo.getName().equals("") && (!minfo.getPosition().equals("") || !minfo.getGrade().equals(""))){%>|<%}%> <%=minfo.getPosition()%></div>
                                                                                                    </div>
                                                                                                    <div class="portlet-body">
                                                                                                        <div class="row">
                                                                                                            <div class="col-md-12 status_ic_checkbox">
                                                                                                                <ul>
                                                                                                                    <li class="fill_trav_accom">
                                                                                                                        <a href="javascript:;" onclick="getTraveldetails('<%=minfo.getCrewrotationId()%>', '1');">
                                                                                                                            <div class="status_ic <%= minfo.getFlag1()==2 ? "req_information" : ""%>"><span><i class="ion ion-ios-briefcase"></i></span></div>
                                                                                                                            <div class="form-check permission-check">
                                                                                                                                <span>Fill Travel Details</span>
                                                                                                                            </div>
                                                                                                                        </a>
                                                                                                                        <a href="javascript:void(0);" onclick="getmobtraveldtls('<%=minfo.getCrewrotationId()%>', '1');" class="fill_tr_acc_det">View Details</a>
                                                                                                                    </li>
                                                                                                                    <li class="fill_trav_accom">
                                                                                                                        <a href="javascript:;" onclick="getAccommDetails('<%=minfo.getCrewrotationId()%>', '2');">
                                                                                                                            <div class="status_ic <%= minfo.getFlag2()==2 ? "req_information" : ""%>"><span><i class="fas fa-bed"></i></span></div>
                                                                                                                            <div class="form-check permission-check">
                                                                                                                                <span>Fill Accommodation Details</span>
                                                                                                                            </div>
                                                                                                                        </a>
                                                                                                                        <a href="javascript:void(0);" onclick="getmobaccommdtls('<%=minfo.getCrewrotationId()%>', '2');" class="fill_tr_acc_det">View Details</a>
                                                                                                                    </li>
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
                                                                        </div>
                                                                        <%
                                                                            if (editper.equals("Y")) {
                                                                        %>
                                                                        <div class="col-md-1 add_view_area client_se_vi_re">
                                                                            <div class="row">
                                                                                <div class="search_add_btn circle_btn"><a href="javascript:void(0);" onclick="getMailDetails('<%=minfo.getCrewrotationId()%>', '0');" class=""><i class="mdi mdi-email-outline"></i></a></div>
                                                                            </div>
                                                                        </div>
                                                                        <%}%>
                                                                    </div>
                                                                </div>
                                                            </li>
<%                                                          }
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
        </div>

        <div id="view_doc_list_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="dReqDocMob">
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
        <script src="../assets/js/bootstrap-multiselect.js"></script>
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>

        <script src="../assets/time/bootstrap-timepicker.min.js" type="text/javascript"></script>
        <script src="../assets/js/table-responsive.init.js"></script>
        <script src="../assets/js/sweetalert2.min.js"></script> 
        <script src="../assets/js/app.js"></script>
        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
        <script src="../assets/time/components-date-time-pickers.min.js" type="text/javascript"></script>
        <script src="../assets/js/sweetalert2.min.js"></script>
        <% if(mailmodal.equals("yes")){%>
        <script type="text/javascript">
                            $(window).on('load', function () {
                                getMailDetails('', '<%=modaltype%>');
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
        <script>
            $(function () {
                $('[data-toggle="tooltip"]').tooltip()
            })
        </script>
        <script>
            $(function () {
                $(".wrapper1").scroll(function () {
                    $(".wrapper2")
                            .scrollLeft($(".wrapper1").scrollLeft());
                });
                $(".wrapper2").scroll(function () {
                    $(".wrapper1")
                            .scrollLeft($(".wrapper2").scrollLeft());
                });

                $(".wrapper01").scroll(function () {
                    $(".wrapper02")
                            .scrollLeft($(".wrapper01").scrollLeft());
                });
                $(".wrapper02").scroll(function () {
                    $(".wrapper01")
                            .scrollLeft($(".wrapper02").scrollLeft());
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
<%        } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
