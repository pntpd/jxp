<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.managetraining.ManagetrainingInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="managetraining" class="com.web.jxp.managetraining.Managetraining" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 4, submtp = 67;
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
            ArrayList managetraining_list = new ArrayList();
            if(session.getAttribute("MANAGETRAINING_LIST") != null)
                managetraining_list = (ArrayList) session.getAttribute("MANAGETRAINING_LIST");
            int total = managetraining_list.size();
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
            int arr[] = new int[11];
            double perval = 0;
            int completed_count = 0, pending_count = 0, unassigned = 0, expired1 = 0, expired2 = 0, expired3 = 0, expired = 0, total_count = 0, total_position = 0, total_candidate = 0, total_courses = 0;
            if(session.getAttribute("ARR_COUNT") != null)
            {
                arr = (int[]) session.getAttribute("ARR_COUNT");
                completed_count = arr[0];
                pending_count = arr[1];
                unassigned = arr[2];
                expired1 = arr[3];
                expired2 = arr[4];
                expired3 = arr[5];
                expired = arr[6];
                total_count = arr[7];
                total_position = arr[8];
                total_candidate = arr[9];
                total_courses = arr[10];
                perval = ((double)completed_count / (double)total_count) * 100.0;
                if(perval > 100)
                    perval = 100.0;
            }
%>
<head>
    <meta charset="utf-8">
    <title><%= managetraining.getMainPath("title") != null ? managetraining.getMainPath("title") : "" %></title>
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
    <script type="text/javascript" src="../jsnew/managetraining.js"></script>
    <script type="text/javascript" src="../jsnew/validation.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/managetraining/ManagetrainingAction.do" onsubmit="return false;">
        <html:hidden property="doSearch"/> 
        <html:hidden property="doSearchAsset"/> 
        <html:hidden property="doAssign2" />
        <html:hidden property="courseId" />
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
                                    <span>Manage Training</span>
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
                                                <li><a href="javascript:printPage('printArea');"><img src="../assets/images/print-screen.png"/> Print Screen</a></li>
                                                <li><a href="javascript: exporttoexcel('1');"><img src="../assets/images/export-data.png"/> Export Data</a></li>
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
                                                <div class="col-md-3 com_label_value">
                                                    <div class="row d-flex align-items-center mb_0">
                                                        <div class="col-md-6">
                                                            <div class="row">	
                                                                <div class="col-md-12 mb_10">
                                                                    <html:select property="clientIdIndex" styleId="clientIdIndex" styleClass="form-select" onchange="javascript: setAssetDDL();" >
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
                                                        <div class="col-md-6">
                                                            <div class="row d-flex align-items-center per_com">	
                                                                <div class="col-md-7 text-left pd_0"><label>Percentage Completion</label></div>
                                                                <div class="col-md-5 pd_0"><span><% if(perval > 0) {%><%=managetraining.getDecimal(perval)%><% } else { %>0.00<% } %> %</span></div>
                                                            </div>	
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-3 pd_0">
                                                    <div class="ref_vie_ope onshore cou_una_pen_com">
                                                        <ul>
                                                            <li class="courses_bg mob_value">Courses <span><%=managetraining.changeNum(total_count, 2)%></span></li>
                                                            <li class="unassigned_bg mob_value">Unassigned <span><%=managetraining.changeNum(unassigned, 2)%></span></li>
                                                            <li class="pending_bg mob_value">Pending <span><%=managetraining.changeNum(pending_count, 2)%></span></li>
                                                            <li class="complete_bg mob_value">Complete <span><%=managetraining.changeNum(completed_count, 2)%></span></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="ref_vie_ope offshore expiry_days">
                                                        <ul>
                                                            <li class="mob_value offshore_border"><label>Expiry in</label> <div class="full_width"><span>(Days)</span></div></li>
                                                            <li class="mob_value"><label>45</label> <div class="full_width"><span class="expiry_days_value "><%=managetraining.changeNum(expired1, 2)%></span></div></li>
                                                            <li class="mob_value"><label>45 to 65</label> <div class="full_width"><span class="expiry_days_value"><%=managetraining.changeNum(expired2, 2)%></span></div></li>
                                                            <li class="mob_value"><label>65 to 90</label> <div class="full_width"><span class="expiry_days_value"><%=managetraining.changeNum(expired3, 2)%></span></div></li>
                                                        </ul>
                                                    </div>
                                                </div>

                                                <div class="col-md-2 pd_0">
                                                    <div class="ref_vie_ope onshore exp_pos_per">
                                                        <ul>
                                                            <li class="mob_value expired_bg">Expired <span><%=managetraining.changeNum(expired, 2)%></span></li>
                                                            <li class="positions_bg mob_value">Positions <span><%=managetraining.changeNum(total_position, 2)%></span></li>
                                                            <li class="personnel_bg mob_value">Courses <span><%=managetraining.changeNum(total_courses, 2)%></span></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <div class="ref_vie_ope">
                                                        <ul>
                                                            <li class="com_view_job"><a href="../trainingmatrix/TrainingmatrixAction.do" target="_blank"><img src="../assets/images/view.png"/><br/> View Matrix</a></li>
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
                                                            <div class="col-lg-9">
                                                                <div class="row mb-3">
                                                                    <div class="col-lg-3">
                                                                        <div class="row">
                                                                            <label for="example-text-input" class="col-sm-3 col-form-label">Search:</label>
                                                                            <div class="col-sm-9 field_ic">
                                                                                <html:text property ="search" styleId="example-text-input" styleClass="form-control" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {this.removeAttribute('readonly'); this.blur(); this.focus();}"/>
                                                                                <a href="javascript: searchForm();" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-lg-2">
                                                                        <div class="row">
                                                                            <div class="col-sm-12 field_ic">
                                                                                <html:select property="categoryIdIndex" styleId="categoryIdIndex" styleClass="form-select" onchange="javascript: setSubcategory();">
                                                                                    <html:optionsCollection filter="false" property="categories" label="ddlLabel" value="ddlValue">
                                                                                    </html:optionsCollection>
                                                                                </html:select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-lg-3">
                                                                        <div class="row">
                                                                            <div class="col-sm-12 field_ic">
                                                                                <html:select property="subcategoryIdIndex" styleId="subcategoryIdIndex" styleClass="form-select" onchange="javascript: searchForm();">
                                                                                    <html:optionsCollection filter="false" property="subcategories" label="ddlLabel" value="ddlValue">
                                                                                    </html:optionsCollection>
                                                                                </html:select>
                                                                            </div>
                                                                        </div>
                                                                    </div>                                                                    
                                                                    <div class="col-lg-4">
                                                                        <div class="row">
                                                                            <label for="example-text-input" class="col-sm-5 col-form-label text-right">Mode:</label>
                                                                            <div class="col-sm-7 field_ic">
                                                                                <html:select styleClass="form-select" property='mode' onchange="javascript: searchForm();">
                                                                                    <html:option value="1">Personnel List</html:option> 
                                                                                    <html:option value="2">Course List</html:option>
                                                                                </html:select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-3 text-right">
                                                                <a href="javascript:resetFilter2();" class="reset_export mr_8"><img src="../assets/images/refresh.png"> Reset Filters</a>
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
                                                                        <th width="27%">
                                                                            <span><b>Courses</b></span>
                                                                            <a href="javascript: sortForm2('1', '2');" id="img_1_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                            <a href="javascript: sortForm2('1', '1');" id="img_1_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                        </th>
                                                                        <th width="27%">
                                                                            <span><b>Category</b></span>
                                                                            <a href="javascript: sortForm2('2', '2');" id="img_2_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                            <a href="javascript: sortForm2('2', '1');" id="img_2_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                        </th>
                                                                        <th width="27%">
                                                                            <span><b>Sub-category</b></span>
                                                                            <a href="javascript: sortForm2('3', '2');" id="img_3_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                            <a href="javascript: sortForm2('3', '1');" id="img_3_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                        </th>
                                                                        <th width="10%">
                                                                            <span><b>Positions</b></span>
                                                                            <a href="javascript: sortForm2('4', '2');" id="img_4_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                            <a href="javascript: sortForm2('4', '1');" id="img_4_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                        </th>                                                                        
                                                                    </tr>
                                                                </thead>
                                                                <tbody id='sort_id'>
<%
                                                                    if(total > 0)
                                                                    {
                                                                        for(int i = 0; i < total; i++)
                                                                        {
                                                                            ManagetrainingInfo info = (ManagetrainingInfo) managetraining_list.get(i);
                                                                            if(info != null)
                                                                            {
%>
                                                                                <tr>
                                                                                    <td class="hand_cursor" href='javascript: void(0);' onclick="javascript: assign2('<%=info.getCourseId()%>');"><%=info.getCourseName() != null ? info.getCourseName() : ""%></td>
                                                                                    <td class="hand_cursor" href='javascript: void(0);' onclick="javascript: assign2('<%=info.getCourseId()%>');"><%=info.getCategoryName() != null ? info.getCategoryName() : ""%></td>
                                                                                    <td class="hand_cursor" href='javascript: void(0);' onclick="javascript: assign2('<%=info.getCourseId()%>');"><%=info.getSubcategoryName() != null ? info.getSubcategoryName() : ""%></td>
                                                                                    <td class="assets_list text-center"><a href="javascript: assign2('<%=info.getCourseId()%>');"><%=managetraining.changeNum(info.getTotal(),2)%></a></td>                                                                                    
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
                        </div>
                        <!-- End Page-content -->
                    </div>
                    <!-- end main content-->
                </div>
        </div>
            <%@include file ="../footer.jsp"%>
            <!-- JAVASCRIPT -->
            <script src="../assets/libs/jquery/jquery.min.js"></script>
            <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
            <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
            <script src="../assets/js/app.js"></script>
            <!-- Responsive Table js -->
            <script src="../assets/js/rwd-table.min.js"></script>
            <script src="../assets/js/table-responsive.init.js"></script>
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