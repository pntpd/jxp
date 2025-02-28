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
        try
        {
            int mtp = 2, submtp = 57, allclient = 0, companytype = 0;
            String per = "N", addper = "N", editper = "N", deleteper = "N",approveper="N", assetids = "";
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
                    allclient = uinfo.getAllclient();
                    companytype = uinfo.getCompanytype();
                }
            }
        CrewrotationInfo info = null;
        if (session.getAttribute("ACTIVITYCRINFO") != null) {
            info = (CrewrotationInfo) session.getAttribute("ACTIVITYCRINFO");
        }

        ArrayList selectedcand_list = new ArrayList();
        if (session.getAttribute("CRACTIVITYLIST") != null) {
            selectedcand_list = (ArrayList) session.getAttribute("CRACTIVITYLIST");
        }
        int total = selectedcand_list.size();

        CrewrotationInfo cinfo = null;
        if (session.getAttribute("CREWROTATIONINFO") != null) {
            cinfo = (CrewrotationInfo) session.getAttribute("CREWROTATIONINFO");
        }
        int crewrota = 0;
        if (cinfo != null) {
            crewrota = cinfo.getCrewrota();
        }
        System.out.println("crewrota :: "+crewrota);

                      
    %>
    <head>
        <meta charset="utf-8">
        <title><%= crewrotation.getMainPath("title") != null ? crewrotation.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <link href="../assets/css/rwd-table.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/time/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/crewrotation.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/crewrotation/CrewrotationAction.do" onsubmit="return false;">
            <html:hidden property="search"/>
            <html:hidden property="clientIdIndex"/>
            <html:hidden property="assetIdIndex"/>
            <html:hidden property="countryId"/>
            
            <html:hidden property="dynamicId"/>
            <html:hidden property="crdate"/>
            <html:hidden property="activityDropdown"/>
            <html:hidden property="searchcr"/>
            <html:hidden property="positionIdIndex"/>
            <html:hidden property="statusindex"/>
            
            <html:hidden property="doView"/>
            <html:hidden property="doSummary"/>
            <html:hidden property="doSaveActivity"/>
            <html:hidden property="doSaveSignoff"/>
            <html:hidden property="doSaveSignon"/>
            <html:hidden property="doCancel"/>
            <html:hidden property="doDelete"/>
            <html:hidden property="jobpostId"/>
            <html:hidden property="clientId"/>
            <html:hidden property="clientassetId"/>
            <html:hidden property="crewrotationId"/>
            <html:hidden property="cractivityId"/>
            <html:hidden property="noofdays"/>
            <html:hidden property="signonoffId"/>
            <html:hidden property="crewId"/>
            <html:hidden property="rota"/>
            <div id="layout-wrapper">
                <%@include file="../header.jsp" %>
                <%@include file="../sidemenu.jsp" %>
                <div class="main-content">
                    <div class="page-content tab_panel no_tab1">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12">
                                <div class="float-start back_arrow">
                                    <a href="javascript: gobackview();"><img src="../assets/images/back-arrow.png"/></a>
                                    <span>Crew Rotation</span>
                                </div>
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
                                                <li><a href="javascript: exporttoexcelActivity();"><img src="../assets/images/export-data.png"/> Export Data</a></li>
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
                                                        <div class="row mb_0">
                                                            <div class="col-md-3"><label>Client Asset</label></div>
                                                            <div class="col-md-9"><span><%=  info.getClientName() != null ? info.getClientName()+" "+ info.getClientAsset() : "" %></span></div>
                                                        </div>
                                                        <div class="row mb_0">
                                                            <div class="col-md-3"><label>Location</label></div>
                                                            <div class="col-md-9"><span><%=  info.getCountry() != null ? info.getCountry():"" %></span></div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-4 com_label_value">
                                                        <div class="row mb_0">
                                                            <div class="col-md-4"><label>Personnel</label></div>
                                                            <div class="col-md-8"><span><%= info.getName() != null ? info.getName():"" %></span></div>
                                                        </div>
                                                        <div class="row mb_0">
                                                            <div class="col-md-4"><label>Position - Rank</label></div>
                                                            <div class="col-md-8"><span><%=  info.getPosition() != null ? info.getPosition():"" %></span></div>
                                                        </div>

                                                    </div>

                                                    <div class="col-md-1">
                                                        <div class="ref_vie_ope offshore training">
                                                            <ul>
                                                                <li class="mob_value"><label>Training</label> <div class="full_width"><span class=""><%= info.getTrainingCount() %></span></div></li>
                                                            </ul>
                                                        </div>
                                                    </div>

                                                    <div class="col-md-1">
                                                        <div class="ref_vie_ope">
                                                            <ul>
                                                                <li class="mob_value">Rotation <span><%= info.getRotationCount() %></span></li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <div class="ref_vie_ope">
                                                            <ul>
                                                                <li class="com_view_job"><a href="javascript:;"><img src="../assets/images/view.png"/><br/> View Asset</a></li>
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
                                                            <div class="col-lg-4 col-md-4 col-sm-12 col-12"><h2>&nbsp;</h2></div>
                                                            <div class="col-lg-8 col-md-8 col-sm-12 col-12">
                                                                <div class="row">
                                                                    <div class="col-lg-4 col-md-4 col-sm-12 col-12">
                                                                        <div class="input-daterange input-group input-addon">
                                                                            <html:text property="fromdateindex" styleId="wesl_from_dt" styleClass="form-control wesl_from_dt"/>
                                                                            <script type="text/javascript">
                                                                                document.getElementById("wesl_from_dt").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                                            </script>
                                                                            <div class="input-group-add">
                                                                                <span class="input-group-text">To</span>
                                                                            </div>
                                                                            <html:text property="todateindex" styleId="wesl_to_dt" styleClass="form-control wesl_from_dt"/>
                                                                            <script type="text/javascript">
                                                                                document.getElementById("wesl_to_dt").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                                            </script>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-lg-3 col-md-3 col-sm-12 col-12">
                                                                        <html:select property="positionId2" styleId="positionId2" styleClass="form-select">
                                                                            <html:optionsCollection filter="false" property="positions" label="ddlLabel" value="ddlValue">
                                                                            </html:optionsCollection>
                                                                        </html:select>
                                                                    </div>
                                                                    <div class="col-lg-3 col-md-3 col-sm-12 col-12">
                                                                        <html:select property="activityIdindex" styleId="activityIdindex" styleClass="form-select">
                                                                            <html:option value ="-1">Select Activity</html:option>
                                                                            <html:option value ="1">Office work</html:option>
                                                                            <html:option value ="2">Training</html:option>
                                                                            <html:option value ="6">Offshore work</html:option>
                                                                            <html:option value ="7">Offshore Training</html:option>
                                                                            <html:option value ="8">Offshore Temporary Promotion</html:option>
                                                                            <html:option value ="9">Standby</html:option>
                                                                        </html:select>
                                                                    </div>
                                                                    <div class="col-lg-2 col-md-2 col-sm-12 col-12">
                                                                        <a href="javascript: searchActivity();" class="go_btn">Go</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="table-rep-plugin sort_table">
                                                            <div class="table-responsive mb-0 ellipse_code">
                                                                <table id="tech-companies-1" class="table table-striped">
                                                                    <thead>
                                                                        <tr>
                                                                            <th width="%"><span><b>Position | Rank</b></span></th>
                                                                            <th width="%"><span><b>From Date</b></span></th>
                                                                            <th width="%"><span><b>To Date</b></span></th>
                                                                            <th width="%"><span><b>Status</b></span></th>
                                                                            <th width="%"><span><b>Activity</b></span></th>
                                                                            <th class="text-center"><span><b>Duration (in days)</b></span></th>
                                                                            <th width="%" class="text-center"><span><b>Action</b></span></th>
                                                                        </tr>

                                                                    </thead>
                                                                    <tbody>

                                                                        <%
                                          
                                                                                CrewrotationInfo ainfo;
                                                                                for (int i = 0; i < total; i++)
                                                                                {
                                                                                    int status = 1;
                                                                                    ainfo = (CrewrotationInfo) selectedcand_list.get(i);
                                                                                    if (ainfo != null) 
                                                                                    {                                                                                        

                                                                        %>
                                                                        <tr>
                                                                            <td><%= ainfo.getPosition()%></td>
                                                                            <td><%= ainfo.getFromdate()%></td>
                                                                            <td><%= ainfo.getTodate()%></td>
                                                                            <td><%= ainfo.getStatusvalue()%></td>
                                                                            <td><%= ainfo.getActivitystatus()%></td>
                                                                            <td class="assets_list text-center"><a href="javascript:;" class="off_total_value"><%= ainfo.getDatedifference()%></a></td>
                                                                            <td class="action_column text-center">
                                                                                <%if(ainfo.getActivityId() != 6){%>
                                                                                <a href="javascript:;" class="mr_15" <%if(editper.equals("Y") && ainfo.getActiveflag()==1){%>data-bs-toggle="modal" data-bs-target="#valueModalsedit" onclick =" javascript: getActivityModeledit('<%= ainfo.getCrewrotationId()%>', '<%= ainfo.getStatus()%>', '<%= ainfo.getCractivityId()%>')" <%}%>><img src="../assets/images/pencil.png"> </a>	
                                                                                <a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#valueModalview" onclick =" javascript: getActivityModelview('<%= ainfo.getCrewrotationId()%>', '<%= ainfo.getStatus()%>', '<%= ainfo.getCractivityId()%>')"><img src="../assets/images/view.png"> </a>	
                                                                                <span class="switch_bth">
                                                                                    <div class="form-check form-switch">
                                                                                        <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault_<%=(i)%>" <% if(status == 1){%>checked<% }%> <% if(!editper.equals("Y")) {%>disabled="true"<% } %> onclick="javascript: deleteForm('<%= ainfo.getCractivityId()%>', '<%=status%>', '<%=i%>', '<%=crewrota%>');"/>
                                                                                    </div>
                                                                                </span>
                                                                                    <%} else if(ainfo.getActivityId() == 6){%>
                                                                                <a href="javascript:;" class="mr_15" <%if(editper.equals("Y") && ainfo.getActiveflag()==1){%>data-bs-toggle="modal" data-bs-target="#edit_sign_on_off_details_modal" onclick =" javascript: getsignonoffModeledit('<%= ainfo.getCrewrotationId()%>', '<%= ainfo.getCractivityId()%>', '<%= ainfo.getNoofdays()%>')" <%}%>><img src="../assets/images/pencil.png"> </a>	
                                                                                <a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#sign_on_off_details_modalview" onclick =" javascript: getsignonoffModelview('<%= ainfo.getCrewrotationId()%>', '<%= ainfo.getCractivityId()%>', '<%= ainfo.getNoofdays()%>')"><img src="../assets/images/view.png"> </a>	
                                                                                <span class="switch_bth">
                                                                                    <div class="form-check form-switch">
                                                                                        <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault_<%=(i)%>" <% if(status == 1){%>checked<% }%> <% if(!editper.equals("Y")) {%>disabled="true"<% } %> onclick="javascript: deleteForm('<%= ainfo.getCractivityId()%>', '<%=status%>', '<%=i%>','<%=crewrota%>');"/>
                                                                                    </div>
                                                                                </span>
                                                                                    <%}%>
                                                                            </td>
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
                </div>
            </div>
            <%@include file ="../footer.jsp"%>

            <div id="valueModalview" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12" id="activitymodalview">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="valueModalsedit" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12" id="activitymodaledit">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="sign_on_off_details_modalview" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12" id="signonoffmodalview">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="edit_sign_on_off_details_modal" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12" id="signonoffmodaledit">
                                    <!-- <h2>SIGN OFF DETAILS</h2> -->
                                    
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <script src="../assets/libs/jquery/jquery.min.js"></script>
            <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
            <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
            <script src="../assets/time/bootstrap-timepicker.min.js" type="text/javascript"></script>
            <script src="../assets/js/app.js"></script>
            <!-- Responsive Table js -->
            <script src="../assets/js/table-responsive.init.js"></script>
            <script src="../assets/js/bootstrap-select.min.js"></script> 
            <script src="../assets/js/bootstrap-datepicker.min.js"></script>
            <script src="../assets/time/components-date-time-pickers.min.js" type="text/javascript"></script>
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
                        orientation: "auto"
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