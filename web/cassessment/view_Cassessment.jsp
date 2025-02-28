<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.cassessment.CassessmentInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="cassessment" class="com.web.jxp.cassessment.Cassessment" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 2, submtp = 14;
            String per = "N", addper = "N", editper = "N", deleteper = "N", approveper = "N";
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
            }
        }

            CassessmentInfo info = null;
            if(session.getAttribute("CANDIDATEPOSITION_INFO") != null)
                info = (CassessmentInfo)session.getAttribute("CANDIDATEPOSITION_INFO");

            ArrayList list = new ArrayList();
            if(session.getAttribute("ASSESSMENT_LIST") != null)
                list = (ArrayList)session.getAttribute("ASSESSMENT_LIST");
            int list_size = list.size();

            int paId = 0;
            if(session.getAttribute("PAID") != null){
                paId = Integer.parseInt((String) session.getAttribute("PAID"));
                session.removeAttribute("PAID");
            }
            String strView="";
            if (request.getAttribute("VIEW") != null)
            {
                strView = (String)request.getAttribute("VIEW");
                request.removeAttribute("VIEW");
            }    
    %>
    <head>
        <meta charset="utf-8">
        <title><%= cassessment.getMainPath("title") != null ? cassessment.getMainPath("title") : ""%></title>
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
        <script type="text/javascript" src="../jsnew/cassessment.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>

    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed" <% if("onview".equalsIgnoreCase(strView)){%>onload="getViewEditData('<%=paId%>', '', '<%=strView%>');" <% }%>>
    <html:form action="/cassessment/CassessmentAction.do" onsubmit="return false;">
        <html:hidden property="doView"/>
        <html:hidden property="doSave"/>
        <html:hidden property="doSaveScore"/>
        <html:hidden property="doCancel"/>
        <html:hidden property="doRetake"/>
        <html:hidden property="cassessmentId"/>
        <html:hidden property="pAssessmentId"/>
        <html:hidden property="attemptCount"/>
        <html:hidden property="candidateId"/>
         <html:hidden property="search"/>
        <html:hidden property="positionIndex"/>
        <html:hidden property="astatusIndex"/>
        <html:hidden property="vstatusIndex"/>
        <html:hidden property="assettypeIndex"/>
        <!-- Begin page -->
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content tab_panel1 no_tab1">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start back_arrow">
                                <a href="javascript: goback();"><img  src="../assets/images/back-arrow.png"/></a> 
                                <span>Assessments</span>
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
                                <div class="body-background cre_assement">
                                    <div class="row">
                                        <div class="col-lg-4 col-md-3 left_area">
                                            <div class="row ass_sort_area">
                                                <div class="col-lg-9">
                                                    <html:select property="scheduleIndex" styleId="scheduleddl" styleClass="form-select">
                                                        <html:option value="-1">Sort By Status</html:option>
                                                        <html:option value="1">Scheduled</html:option>
                                                        <html:option value="2">Unscheduled</html:option>
                                                        <html:option value="3">Passed</html:option>
                                                        <html:option value="4">Failed</html:option>
                                                        <html:option value="5">Expired</html:option>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-3"><a href="javascript: void(0);" onclick="searchFormViewAjax();" class="go_btn go_width">Go</a></div>
                                            </div>
                                            <div class="row">
                                                <div class="sort_status" id="dassessments">
                                                    <%
                                                    if(list_size > 0)
                                                        {                  
                                                    %>
                                                    <ul>
                                                        <%
                                                            CassessmentInfo cinfo;
                                                        for(int i = 0; i < list_size; i++)
                                                            {
                                                                 cinfo = (CassessmentInfo) list.get(i);
                                                                    if(cinfo != null)
                                                                    {        
                                                        %>
                                                        <li <% if(cinfo.getAssessmentDetailId() == paId){%>class="active_box"<%}%> id="leftli<%=i+1%>">
                                                            <a href="javascript:void(0);" onclick="getViewEditData('<%=cinfo.getAssessmentDetailId()%>', '<%=cinfo.getCassessmentId()%>', 'view'); setDivClass('<%=list_size%>', '<%=i+1%>');">
                                                                <div class="col-lg-12 status_div <%if(cinfo.getStatus() == 2){%>passed_div<%} else if(cinfo.getStatus() == 3) {%>failed_div<%} else if(cinfo.getCassessmentId() > 0 && cinfo.getStatus() == 1){%>scheduled_div<%}else if(cinfo.getCassessmentId() <= 0){%>unscheduled_div<%}%>">
                                                                    <div class="row flex-end align-items-center">
                                                                        <div class="col-lg-3"><label class="mr_8">Code</label><span class="status_value"><%= cinfo.getCode() != null && !"".equals(cinfo.getCode()) ? cinfo.getCode() : "" %></span></div>
                                                                        <div class="col-lg-6"><label class="mr_8">Minimum Score</label><span class="status_value"><%= cinfo.getMinScore()%></span></div>
                                                                            <%
                                                                            if(cinfo.getPassingFlag() == 1){
                                                                            %>
                                                                        <div class="col-lg-3 text-center"><span class="passing_req">Passing <br/> Required</span></div>
                                                                                <%
                                                                                }
                                                                                %>
                                                                    </div>
                                                                    <div class="row">
                                                                        <div class="col-lg-12 mb_10">
                                                                            <label class="full_width">Name</label>
                                                                            <span class="status_value"><%= cinfo.getAssessmentName() != null && !"".equals(cinfo.getAssessmentName()) ? cinfo.getAssessmentName() : "" %></span>
                                                                        </div>
                                                                        <div class="col-lg-12">
                                                                            <label class="full_width">Status</label>
                                                                            <span class="status_value"><%= cinfo.getScheduletype() != null && !"".equals(cinfo.getScheduletype()) ? cinfo.getScheduletype() : "" %></span>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </a>
                                                        </li>
                                                        <%
                                                            }
                                                        }
                                                        %>
                                                    </ul>
                                                    <%
                                                    }
                                                    %>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-8 col-md-9 right_area">
                                            <div class="row asses_candidate">
                                                <div class="col-lg-10">
                                                    <div class="row">
                                                        <div class="col-lg-6">
                                                            <label class="col-lg-5">Candidate Name</label>
                                                            <span class="col-lg-7 status_value"><%= info.getName() != null && !info.getName().equals("") ? info.getName() : "" %></span>
                                                        </div>
                                                        <div class="col-lg-6">
                                                            <label class="col-lg-6">Verification Status</label>
                                                            <span class="col-lg-6 status_value"><%= info.getVflagstatus() != null && !info.getVflagstatus().equals("") ? info.getVflagstatus() : "" %></span>
                                                        </div>
                                                        <div class="col-lg-6">
                                                            <label class="col-lg-5">Position - Rank</label>
                                                            <span class="col-lg-7 status_value"><%= info.getPosition() != null && !info.getPosition().equals("") ? info.getPosition() : "" %> <%= info.getRankName() != null && !info.getRankName().equals("") ? " - " + info.getRankName() : "" %></span>
                                                        </div>
                                                        <div class="col-lg-6">
                                                            <label class="col-lg-6">Preferred Department</label>
                                                            <span class="col-lg-6 status_value"><%= info.getDeptName() != null && !info.getDeptName().equals("") ? info.getDeptName() : "" %></span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-2 candi_prof_div">
                                                    <a href="javascript: viewCandidate('<%= info.getCandidateId()%>', '<%=info.getPassingFlag()%>');">
                                                        <img src="../assets/images/view.png"/><br/>
                                                        Candidate Profile
                                                    </a>
                                                </div>
                                            </div>  
                                            <div class="full_width" id="ajax_div">
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

        <div id="client_position" class="modal fade client_position" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true" ><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body" id="camodel">
                    </div>
                </div>
            </div>
        </div>


        <!-- JAVASCRIPT -->
        <script src="../assets/libs/jquery/jquery.min.js"></script>		
        <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>

        <script src="../assets/js/sweetalert2.min.js"></script>
        <script src="../assets/time/bootstrap-timepicker.min.js" type="text/javascript"></script>
        <script src="../assets/js/app.js"></script>

        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
        <script src="../assets/time/components-date-time-pickers.min.js" type="text/javascript"></script>

        <script>
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
    </html:form>
</body>
<%
} catch (Exception e) {
e.printStackTrace();
}
%>
</html>
