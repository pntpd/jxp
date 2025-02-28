<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.wellnessfb.WellnessfbInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="wellnessfb" class="com.web.jxp.wellnessfb.Wellnessfb" scope="page"/>
<!doctype html>
<html lang="en">
<%
    try
    {
        int mtp = 7, submtp = 69, ctp = 2;
        if (session.getAttribute("LOGININFO") == null)
        {
%>
            <jsp:forward page="/index1.jsp"/>
<%
        }
        String message = "", clsmessage = "red_font";
        if (request.getAttribute("MESSAGE") != null)
        {
            message = (String)request.getAttribute("MESSAGE");
            request.removeAttribute("MESSAGE");
        }        
        if(message != null && (message.toLowerCase()).indexOf("success") != -1)
            clsmessage = "updated-msg";

        ArrayList list = new ArrayList();
        if(session.getAttribute("MDLIST") != null)
                list = (ArrayList) session.getAttribute("MDLIST");
            int list_size = list.size();
        String dids = "";
        if (session.getAttribute("NOTIFICATIONDETAIL_IDs") != null) {
                dids = (String) session.getAttribute("NOTIFICATIONDETAIL_IDs");
                request.getSession().removeAttribute("NOTIFICATIONDETAIL_IDs");
            } 
%>
<head>
    <meta charset="utf-8">
    <title><%= wellnessfb.getMainPath("title") != null ? wellnessfb.getMainPath("title") : "" %></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- App favicon -->
    <link rel="shortcut icon" href="../assets/images/favicon.png">
    <!-- Bootstrap Css -->
    <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
    <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
    <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
    <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
    <!-- Icons Css -->
    <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
    <!-- App Css-->
    <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
    <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
    <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
    <script src="../jsnew/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="../jsnew/validation.js"></script>
    <script type="text/javascript" src="../jsnew/wellnessfb.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
<html:form action="/wellnessfb/WellnessfbAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
    <html:hidden property="categoryId"/>
    <html:hidden property="subcategoryId"/>
    <html:hidden property="doSaveSubcategory"/>
    <html:hidden property="doCancel"/>
    <html:hidden property="doView"/>
    <html:hidden property="search"/>   
    <html:hidden property="subcategoryIdIndex"/>   
    <html:hidden property="doIndexQuestion"/>   
    <html:hidden property="doIndexSubcategory"/>
    <html:hidden property="schedulevalue"/>  
    <!-- Begin page -->
    <div id="layout-wrapper">
        <%@include file ="../header.jsp"%>
        <%@include file ="../sidemenu.jsp"%>
        <!-- Start right Content -->
        <div class="main-content">
            <div class="page-content tab_panel">
                <div class="row head_title_area">
                    <div class="col-md-12 col-xl-12">
                        <div class="float-start">
                            <a href="javascript: viewSubcategory();" class="back_arrow">
                            <img  src="../assets/images/back-arrow.png"/> 
                                <c:choose>
                                    <c:when test="${wellnessfbForm.subcategoryId <= 0}">
                                        Add Sub-Category
                                    </c:when>
                                    <c:otherwise>
                                        Edit Sub-Category
                                    </c:otherwise>
                                </c:choose>
                            </a>
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
                                    <%@include file ="../shortcutmenu_edit.jsp"%>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 col-xl-12 tab_head_area">
                        <c:choose>
                            <c:when test="${wellnessfbForm.subcategoryId <= 0}">
                                <ul class='nav nav-tabs nav-tabs-custom' id='tab_menu'>
                                    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 1) ? " active" : ""%>'>
                                        <a class='nav-link' href="javascript: showDetailcategory();">
                                            <span class='d-none d-md-block'>Category</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 2) ? " active" : ""%>'>
                                        <a class='nav-link' href="javascript: void(0);">
                                            <span class='d-none d-md-block'>Sub-Category</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 3) ? " active" : ""%>'>
                                        <a class='nav-link' href="javascript: void(0);">
                                            <span class='d-none d-md-block'>Questions</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                </ul>
                            </c:when>
                                <c:otherwise>
                                        <ul class='nav nav-tabs nav-tabs-custom' id='tab_menu'>
                                    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 1) ? " active" : ""%>'>
                                        <a class='nav-link' href="javascript: showDetailcategory();">
                                            <span class='d-none d-md-block'>Category</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 2) ? " active" : ""%>'>
                                        <a class='nav-link' href="javascript: viewSubcategory();">
                                            <span class='d-none d-md-block'>Sub-Category</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 3) ? " active" : ""%>'>
                                        <a class='nav-link' href="javascript: viewQuestion('-1');">
                                            <span class='d-none d-md-block'>Questions</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                </ul>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-12 col-xl-12">
                            <div class="body-background">
                                <div class="row d-none1">
                                    <div class="main-heading m_15">
                                        <div class="add-btn">
                                            <h4>SUB-CATEGORY DETAILS</h4>
                                        </div>
                                    </div>
                                    <div class="row m_30">
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Category Name</label>
                                            <html:text property="categoryname" styleId="categoryname" styleClass="form-control" maxlength="100" readonly="true" />
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Sub-category Name</label>
                                            <html:text property="name" styleId="name" styleClass="form-control" maxlength="100"/>
                                            <script type="text/javascript">
                                                document.getElementById("name").setAttribute('placeholder', '');
                                            </script>
                                        </div>
                                            
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Schedule</label>
                                                <div class="full_width">
                                                    <div class="form-check permission-check">
                                                        <html:checkbox property="schedulecb" value = "1" styleClass="form-check-input"  styleId="switch1" onchange="javascript: showhideschedule();"/>
                                                        <span class="ml_10">Repeats</span>
                                                    </div>
                                                </div>
                                            </div>
                                            
                                                                        
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                            <label class="form_label">Description</label>
                                            <html:textarea property="cdescription" styleId="cdescription" styleClass="form-control" rows="4"/>
                                            <script type="text/javascript">
                                                document.getElementById("cdescription").setAttribute('placeholder', '');
                                                document.getElementById("cdescription").setAttribute('maxlength', '1000');
                                            </script>
                                        </div>
                                      </div>
                                            
                                    <div class="row" id="ShowMeDIV" style="display: none;">
                                            <div class="main-heading m_15">
                                                <div class="add-btn">
                                                    <h4>SCHEDULE DETAILS</h4>
                                                </div>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group" id="all_scheduledetails">
                                                <label class="form_label">Repeat Every</label>
                                                <html:select styleClass="form-select" property="repeatdp" onchange=" javascript: showhideschedule();">
                                                    <html:option value="-1">- Select -</html:option>
                                                    <html:option value="1">Daily</html:option>
                                                    <html:option value="2">Weekly</html:option>
                                                    <html:option value="3">Monthly</html:option>
                                                    <html:option value="4">Yearly</html:option>
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
                                                        <input class="form-check-input" type="checkbox" name="" id="" value="-2" onchange="javascript: checkmonth('-2');">
                                                            <span class="last_day">Last Day</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group field_box yearly"  id="yeardaysId">
                                                <div class="row" id="yearviewId">
                                                    <div class="col-md-4">
                                                            <label class="form_label">Select Month</label>
                                                            <select name = 'monthdp' class="form-select" onchange=" javascript: setyeardays('-1','a')">
                                                                <option value="-1">Select here</option>
                                                                <option value="01">Jan</option>
                                                                <option value="02">Feb</option>
                                                                <option value='03'>Mar</option>
                                                                <option value='04'>Apr</option>
                                                                <option value='05'>May</option>
                                                                <option value='06'>Jun</option>
                                                                <option value='07'>Jul</option>
                                                                <option value='08'>Aug</option>
                                                                <option value='09'>Sep</option>
                                                                <option value='10'>Oct</option>
                                                                <option value='11'>Nov</option>
                                                                <option value='12'>Dec</option>
                                                            </select>
                                                        </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group daily_box daily noti_div" id="all_notificationdetails">
                                                <label class="form_label">Send notification before(days)</label>
                                                    <select name="notificationdp" id="notificationdpmultiselect_dd" multiple="multiple" class="form-select">
                                                        <option value="1" <% if (wellnessfb.checkToStr(dids, "1")) {%>selected<%}%>>1 Day(s)</option>
                                                        <option value="2" <% if (wellnessfb.checkToStr(dids, "2")) {%>selected<%}%>>2 Day(s)</option>
                                                        <option value="3" <% if (wellnessfb.checkToStr(dids, "3")) {%>selected<%}%>>3 Day(s)</option>
                                                        <option value="4" <% if (wellnessfb.checkToStr(dids, "4")) {%>selected<%}%>>4 Day(s)</option>
                                                        <option value="5" <% if (wellnessfb.checkToStr(dids, "5")) {%>selected<%}%>>5 Day(s)</option>
                                                    </Select>
                                            </div>
                                </div>
                                 

                                    <div class="row" id="submitdiv">
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                            <a href="javascript: submitsubcategoryForm();" class="save_btn"><img src="../assets/images/save.png"> Save</a>
                                        <a href="javascript: viewSubcategory();" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
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
    <!-- JAVASCRIPT -->
    <script src="../assets/libs/jquery/jquery.min.js"></script>		
    <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
    <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
    <script src="../assets/js/app.js"></script>	
    <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>
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
    <script>
        $('#notificationdpmultiselect_dd').multiselect({
                    includeSelectAllOption: true,
                    //maxHeight: 400,
                    position: 'top',
                    autoPosition: true,
                    dropUp: true,
                    nonSelectedText: '- Select -',
                    maxHeight: 200,
                    enableFiltering: false,
                    enableCaseInsensitiveFiltering: false,
                    buttonWidth: '100%'
                });
        setSCedit();
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
