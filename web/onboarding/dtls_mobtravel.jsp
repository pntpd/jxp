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
            int mtp = 2, submtp = 59, allclient = 0, companytype = 0, ctp = 1;
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
        if (session.getAttribute("CANDIDATEINFO") != null) {
            info = (OnboardingInfo) session.getAttribute("CANDIDATEINFO");
        }

        ArrayList mob_list = new ArrayList();
        if (session.getAttribute("MOBILIZATIONLIST") != null) {
            mob_list = (ArrayList) session.getAttribute("MOBILIZATIONLIST");
        }
        int total = mob_list.size();

        String file_path = onboarding.getMainPath("view_candidate_file");
        String view_onboarding = onboarding.getMainPath("view_onboarding");
        String cphoto = "../assets/images/empty_user.png";
        if (!info.getPhoto().equals("")) {
            cphoto = file_path + info.getPhoto();
        }

    %>
    <head>
        <meta charset="utf-8">
        <title><%= onboarding.getMainPath("title") != null ? onboarding.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/rwd-table.min.css" rel="stylesheet" type="text/css">
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
        <script type="text/javascript" src="../jsnew/onboarding.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/onboarding/OnboardingAction.do" onsubmit="return false;" enctype="multipart/form-data">
        <html:hidden property="doView"/>
        <html:hidden property="doViewCancel"/>
        <html:hidden property="doMobAccomm"/>
        <html:hidden property="clientId"/>
        <html:hidden property="clientassetId"/>
        <html:hidden property="shortlistId"/>
        <html:hidden property="candidateId"/>
        <html:hidden property="onboardingId"/>
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
                                <a href="javascript:void(0);" onclick="viewcancel();">
                                    <img  src="../assets/images/back-arrow.png"/>
                                </a>
                                <span>Onboarding</span>
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
                                                <div class="col-md-1 personal_photo com_view_prof cand_box_img">
                                                    <div class="user_photo pic_photo">
                                                        <div class="upload_file">
                                                            <img src="<%=cphoto%>">

                                                        </div>
                                                    </div>	
                                                </div>
                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="com_view_job"><a href="javascript:;" onclick="viewCandidate('<%=info.getCandidateId()%>')"><img src="../assets/images/view.png"/><br/> View Profile</a></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="col-md-5 com_label_value">
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Personnel Name</label></div>
                                                        <div class="col-md-9"><span><%=info.getName()%></span></div>
                                                    </div>
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Position - Rank</label></div>
                                                        <div class="col-md-9"><span><%=info.getPosition()%></span></div>
                                                    </div>
                                                </div>
                                                <div class="col-md-4 com_label_value">
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Client - Asset</label></div>
                                                        <div class="col-md-9"><span><%=info.getClientname()%></span></div>
                                                    </div>
                                                    <div class="row mb_0">
                                                        <div class="col-md-3"><label>Location</label></div>
                                                        <div class="col-md-9"><span><%=info.getCountry()%></span></div>
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="com_view_job"><a href="javascript:;" onclick="ViewAsset('<%=info.getClientId()%>')"><img src="../assets/images/view.png"/><br/> View Asset</a></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">

                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                            <div class="row">
                                                <div class="col-md-12 col-xl-12 tab_head_area">
                                                    <%@include file ="../onboardingtab.jsp"%>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 short_list_area">
                                                    <div class="table-rep-plugin sort_table">
                                                        <div class="table-responsive mb-0">
                                                            <table id="tech-companies-1" class="table table-striped table-bordered">
                                                                <thead>
                                                                    <tr class="blue_bg">
                                                                        <th width="%" class="text-center" colspan="1"><span><b>MODE</b> </span></th>
                                                                        <th width="%" class="text-center" colspan="4"><span><b>DEPARTURE</b></span></th>
                                                                        <th width="%" class="text-center" colspan="4"><span><b>ARRIVAL</b></span></th>
                                                                        <th width="%" class="text-center" colspan="2"><span><b>Action</b></span></th>
                                                                    </tr>
                                                                    <tr>
                                                                        <th width="10%" class="text-center"><span><b>Flight/train /Bus/Car No.</b> </span></th>
                                                                        <th width="12%"><span><b>Location</b></span></th>
                                                                        <th width="9%" class="text-center"><span><b>Airport/Train/Bus</b></span></th>
                                                                        <th width="7%" class="text-center"><span><b>Terminal/Platform No.</b></span></th>
                                                                        <th width="9%"><span><b>ETD</b></span></th>
                                                                        <th width="12%"><span><b>Location</b></span></th>
                                                                        <th width="9%" class="text-center"><span><b>Airport/Train/Bus</b></span></th>
                                                                        <th width="7%" class="text-center"><span><b>Terminal/Platform No.</b></span></th>
                                                                        <th width="9%"><span><b>ETA</b></span></th>
                                                                        <th width="8%" class="text-center"><span><b>View</b></span></th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <%
                                                                        if (total > 0) {
                                                                            OnboardingInfo minfo = null;
                                                                            for (int i = 0; i < total; i++) {
                                                                                minfo = (OnboardingInfo) mob_list.get(i);
                                                                                if (minfo != null) {
                                                                    %>
                                                                    <tr>
                                                                        <td class="text-center"><b><%=minfo.getVal1()%>-<%=minfo.getVal5()%></b></td>
                                                                        <td><%=minfo.getVal2()%></td>
                                                                        <td class="text-center"><%=minfo.getVal3()%></td>
                                                                        <td class="text-center"><%=minfo.getVal4()%></td>
                                                                        <td><%=minfo.getVal9()%></td>
                                                                        <td><%=minfo.getVal6()%></td>
                                                                        <td class="text-center"><%=minfo.getVal7()%></td>
                                                                        <td class="text-center"><%=minfo.getVal8()%></td>
                                                                        <td><%=minfo.getVal10()%></td>
                                                                        <td class="action_column text-center">
                                                                            <%if (!minfo.getFilename().equals("")) {%>
                                                                            <a data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%=view_onboarding + minfo.getFilename()%>');" href="javascript:;"><img src="../assets/images/view.png"></a>
                                                                                <%}%>
                                                                        </td>
                                                                    </tr>
                                                                    <%
                                                                            }
                                                                        }
                                                                    } else {
                                                                    %>
                                                                    <tr>
                                                                        <td class="text-center"colspan="12"><b>No data found!</b></td>
                                                                    </tr>
                                                                    <%}
                                                                    %>
                                                                </tbody>

                                                            </table>	
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <a href="javascript:void(0);" onclick="viewcancel();" class="save_btn">Go to Onboarding</a>
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

        <div id="edit_travel_details_modal" class="modal fade parameter_modal large_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="dedittravel">


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
                                <center> <iframe id='iframe' class="doc" src=""></iframe></center>
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
        <script src="../assets/js/app.js"></script>

        <script src="../assets/js/rwd-table.min.js"></script>
        <script src="../assets/js/table-responsive.init.js"></script>

        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
        <script src="../assets/time/components-date-time-pickers.min.js" type="text/javascript"></script>
        <script src="../assets/js/sweetalert2.min.js"></script>
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
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
                });

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
            //Timepicker
            $(".timepicker-24").timepicker({
                format: 'mm:mm',
                autoclose: !0,
                minuteStep: 5,
                showSeconds: !1,
                showMeridian: !1
            });
        </script>
        <script>
            $(function () {
                $('[data-toggle="tooltip"]').tooltip()
            })
        </script>
        <script type="text/javascript">
            $(document).ready(function () {
                $('#select_langauge').multiselect({
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
            $(function () {
                $(".wrapper1").scroll(function () {
                    $(".wrapper2")
                            .scrollLeft($(".wrapper1").scrollLeft());
                });
                $(".wrapper2").scroll(function () {
                    $(".wrapper1")
                            .scrollLeft($(".wrapper2").scrollLeft());
                });
            });
        </script>
    </html:form>
</body>
<%        } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
