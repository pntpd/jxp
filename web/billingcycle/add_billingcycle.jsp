<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.billingcycle.BillingcycleInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="billingcycle" class="com.web.jxp.billingcycle.Billingcycle" scope="page"/>
<!doctype html>
<html lang="en">
<%
    try
    {
        int mtp = 7, submtp = 72;
        String per = "N", addper = "N", editper = "N", deleteper = "N";
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
            }
        }
        BillingcycleInfo info = null;
        int assettypeId = 0;
        if(session.getAttribute("BILLINGCYCLE_DETAIL") != null)
            info = (BillingcycleInfo) session.getAttribute("BILLINGCYCLE_DETAIL");

        String show_modal = "no";
        if(request.getAttribute("SAVED") != null)
        {
            show_modal = (String) request.getAttribute("SAVED");
            request.removeAttribute("SAVED");
        }
%>  
<head>
    <meta charset="utf-8">
    <title><%= billingcycle.getMainPath("title") != null ? billingcycle.getMainPath("title") : "" %></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="shortcut icon" href="../assets/images/favicon.png" />
    <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css" />
    <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
    <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css" />
    <link href="/jxp/assets/css/minimal.css" rel="stylesheet" />
    <link href="../assets/css/style.css" rel="stylesheet" type="text/css"/>
    <script src="../jsnew/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="../jsnew/billingcycle.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
<html:form action="/billingcycle/BillingcycleAction.do" onsubmit="return false;" styleClass="form-horizontal">
<html:hidden property="doCancel"/>
<html:hidden property="search"/>
<html:hidden property="doModify" />
<html:hidden property="doView"/>
<html:hidden property="clientIdIndex" />
<html:hidden property="assetIdIndex" />
<html:hidden property="clientId" />
<html:hidden property="assetId" />
<html:hidden property="schedulevalue" />
<html:hidden property="doSaveCourse" />
    <!-- Begin page -->
    <div id="layout-wrapper">
        <%@include file="../header.jsp" %>
        <%@include file="../sidemenu.jsp" %>
        <!-- Start right Content -->
        <div class="main-content">
            <div class="page-content tab_panel">
                <div class="row head_title_area">
                    <div class="col-md-12 col-xl-12">
                        <div class="float-start"><a href="javascript: goback();" class="back_arrow"><img  src="../assets/images/back-arrow.png"/> Billing Cycle </a></div>
                        <div class="float-end">                            
                            <div class="toggled-off usefool_tool">
                                <div class="toggle-title">
                                    <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                    <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>
                                </div>
                                <!-- end toggle-title --->
                                <div class="toggle-content">
                                    <h4>Useful Tools</h4>
                                    <%@include file ="../shortcutmenu_edit.jsp"%>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 metrix_top_right">
                        <div class="row d-flex align-items-center">
                            <% if(info != null) {%>
                            
                            <div class="col-md-2 com_label_value">
                                <div class="row mb_0">
                                    <div class="col-md-4"><label>Client</label></div>
                                    <div class="col-md-8 text-left"><span><%= info.getClientName() != null ? info.getClientName() : "" %></span></div>
                                </div>
                            </div>
                            <div class="col-md-2 com_label_value">
                                <div class="row mb_0">
                                    <div class="col-md-4"><label>Asset</label></div>
                                    <div class="col-md-8 text-left"><span><%= info.getClientAssetName() != null ? info.getClientAssetName() : "" %></span></div>
                                </div>
                            </div>
                            <div class="col-md-2 com_label_value">
                                <div class="row mb_0">
                                    <div class="col-md-5"><label>Positions</label></div>
                                    <div class="col-md-7 text-left"><span><%= info.getPcount()%></span></div>
                                </div>
                            </div>
                            <div class="col-md-2 com_label_value">
                                <div class="row mb_0">
                                    <div class="col-md-5"><label>Personnel</label></div>
                                    <div class="col-md-7 text-left"><span><%= info.getBillingstatus()%></span></div>
                                </div>
                            </div>
                            <div class="col-md-3 com_label_value">
                                <div class="row mb_0">
                                    <div class="col-md-12 single_line"><span>&nbsp;</span></div>
                                </div>
                            </div>                            
                            <% } %>
                        </div>
                    </div>
                </div>
                <div class="container-fluid pd_0">
                    <div class="row">
                        <div class="col-md-12 col-xl-12 pd_0">
                            <div class="body-background">
                                <div class="row d-none1">
                                    <div class="col-lg-12 pd_left_right_50">
                                        <div class="main-heading m_30">
                                            <div class="add-btn ">
                                                <h4>DEFINE CYCLE SCHEDULE</h4>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Repeats</label>
                                                <html:select property="repeatId" styleId="repeatId" styleClass="form-select" onchange="javascript: showhideschedule();">
                                                    <html:option value="-1"> - Select - </html:option>
                                                    <html:option value="1"> Weekly </html:option>
                                                    <html:option value="2"> Monthly </html:option>
                                                </html:select>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group field_box weekly" id="weekdaysId">
                                                <label class="form_label">Select</label>
                                                    <div class="full_width weekly_div">
                                                        <ul id="weekviewId">
                                                            <li><a href="javascript: checkWeek('1');">S</a></li>
                                                            <li><a href="javascript: checkWeek('2');">M</a></li>
                                                            <li><a href="javascript: checkWeek('3');">T</a></li>
                                                            <li><a href="javascript: checkWeek('4');">W</a></li>
                                                            <li><a href="javascript: checkWeek('5');">T</a></li>
                                                            <li><a href="javascript: checkWeek('6');">F</a></li>
                                                            <li><a href="javascript: checkWeek('7');">S</a></li>
                                                        </ul>
                                                    </div>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group field_box monthly"  id="monthdaysId">
                                                <label class="form_label">Select Days</label>
                                                    <div class="full_width weekly_div">
                                                        <ul>
                                                            <li><a href="javascript: checkmonth('1');">1</a></li>
                                                            <li><a href="javascript: checkmonth('2');">2</a></li>
                                                            <li><a href="javascript: checkmonth('3');">3</a></li>
                                                            <li><a href="javascript: checkmonth('4');">4</a></li>
                                                            <li><a href="javascript: checkmonth('5');">5</a></li>
                                                            <li><a href="javascript: checkmonth('6');">6</a></li>
                                                            <li><a href="javascript: checkmonth('7');">7</a></li>
                                                            <li><a href="javascript: checkmonth('8');">8</a></li>
                                                            <li><a href="javascript: checkmonth('9');">9</a></li>
                                                            <li><a href="javascript: checkmonth('10');">10</a></li>
                                                            <li><a href="javascript: checkmonth('11');">11</a></li>
                                                            <li><a href="javascript: checkmonth('12');">12</a></li>
                                                            <li><a href="javascript: checkmonth('13');">13</a></li>
                                                            <li><a href="javascript: checkmonth('14');">14</a></li>
                                                            <li><a href="javascript: checkmonth('15');">15</a></li>
                                                            <li><a href="javascript: checkmonth('16');">16</a></li>
                                                            <li><a href="javascript: checkmonth('17');">17</a></li>
                                                            <li><a href="javascript: checkmonth('18');">18</a></li>
                                                            <li><a href="javascript: checkmonth('19');">19</a></li>
                                                            <li><a href="javascript: checkmonth('20');">20</a></li>
                                                            <li><a href="javascript: checkmonth('21');">21</a></li>
                                                            <li><a href="javascript: checkmonth('22');">22</a></li>
                                                            <li><a href="javascript: checkmonth('23');">23</a></li>
                                                            <li><a href="javascript: checkmonth('24');">24</a></li>
                                                            <li><a href="javascript: checkmonth('25');">25</a></li>
                                                            <li><a href="javascript: checkmonth('26');">26</a></li>
                                                            <li><a href="javascript: checkmonth('27');">27</a></li>
                                                            <li><a href="javascript: checkmonth('28');">28</a></li>
                                                        </ul>
                                                    </div>
                                                <div class="full_width">
                                                    <div class="form-check permission-check">
                                                        <input class="form-check-input" type="checkbox" name="" value="-2" onchange="javascript: checkmonth('-2');">
                                                            <span class="last_day">Last Day</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <% if(addper.equals("Y") || editper.equals("Y")) {%>
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="submitdiv">
                                            <a href="javascript: save();" class="save_btn"><img src="../assets/images/save.png"> Save</a>
                                            <%}%>
                                            <a href="javascript: goback();" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
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
    <%@include file="../footer.jsp" %>
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
                            <h3>Wellness Matrix Configured</h3>
                            <p>Selected Question(s) have been assigned to the position.</p>
                            <a href="javascript: showDetailBack();" class="msg_button" style="text-decoration: underline;">Wellness Matrix</a>
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
    <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>   
    <script src="/jxp/assets/js/sweetalert2.min.js"></script>
    <script>
        showhidescheduleonload();
       // setweekdays('0');
       // setmonthdays('0');
    </script>
    <script>
        // toggle class show hide text section
        $(document).on('click', '.toggle-title', function () {
            $(this).parent()
            .toggleClass('toggled-on')
            .toggleClass('toggled-off');
        });
    </script>
    <script>
        $(window).on('scroll', function() {
            if ($(this).scrollTop() >150){  
                $('.head_fixed').addClass("is-sticky");
            }
            else{
                $('.head_fixed').removeClass("is-sticky");
            }
        });
    </script>
    <% if(show_modal.equals("yes")){%>
        <script type="text/javascript">
            $(window).on('load', function () {
                $('#thank_you_modal').modal('show');
            });
        </script>
    <%}%>
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
