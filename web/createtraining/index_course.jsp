<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.createtraining.CreatetrainingInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="createtraining" class="com.web.jxp.createtraining.Createtraining" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 7, submtp = 60, ctp = 3;
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
            ArrayList createtraining_list = new ArrayList();

            if(session.getAttribute("COURSE_LIST") != null)
                createtraining_list = (ArrayList) session.getAttribute("COURSE_LIST");

            int total = createtraining_list.size();
            int showsizelist = createtraining.getCountList("show_size_list");
       
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
            String thankyou = "no";
            if(request.getAttribute("COURSESAVEMODEL") != null)
            {
                thankyou = (String)request.getAttribute("COURSESAVEMODEL");
                request.removeAttribute("COURSESAVEMODEL");
            }
            
            String file_path =  createtraining.getMainPath("view_trainingfiles");
    %>
    <head>
        <meta charset="utf-8">
        <title><%= createtraining.getMainPath("title") != null ? createtraining.getMainPath("title") : "" %></title>
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
        <script type="text/javascript" src="../jsnew/createtraining.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/createtraining/CreatetrainingAction.do" onsubmit="return false;">
            <html:hidden property="doAdd"/>
            <html:hidden property="doModify"/>
            <html:hidden property="doView"/>    
            <html:hidden property="categoryId"/>
            <html:hidden property="subcategoryId"/>
            <html:hidden property="courseId"/>
            <html:hidden property="doIndexSubcategory"/>
            <html:hidden property="doIndexCourse"/>
            <html:hidden property="doModifycourse"/>
            <html:hidden property="doAddCourse"/>    
            <html:hidden property="dodeleteCourse"/>    
            <html:hidden property="substatus"/>
            <html:hidden property="search"/>
            <html:hidden property="type"/>
            <!-- Begin page -->
            <div id="layout-wrapper">
                <%@include file="../header.jsp" %>
                <%@include file="../sidemenu.jsp" %>
                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content tab_panel">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12">

                                <div class="float-start back_arrow">
                                    <a href="javascript: viewSubcategory();"><img src="../assets/images/back-arrow.png"/></a>
                                    <span>Create Trainings</span>
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
                                                <li><a href="javascript: exporttoexcel('3');"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-12 col-xl-12 tab_head_area">
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
                                        <a class='nav-link' href="javascript: viewCourse('-1');">
                                            <span class='d-none d-md-block'>Courses</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                </ul> 
                            </div>    
                        </div>

                        <div class="container-fluid">
                            <div class="row">
                                <% if(!message.equals("")) {%><div class="alert <%=clsmessage%> alert-dismissible fade show">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button><%=message%>
                                </div><% } %>
                            </div>
                            <div class="row">
                                <div class="col-md-12 col-xl-12">
                                    <div class="body-background">
                                        <div class="row d-none1" id='ajax_cat'>
                                            <div class="col-lg-12" id="printArea">
                                                <div class="tab-content">
                                                <div class="tab-pane active" id="tab2" role="tabpanel">

                                                <div class="main-heading m_30 just_cont_inherit list_heading">
                                                    <div class="add-btn float-start mr_15 mt_5">
                                                        <h4>COURSES LIST</h4>
                                                    </div>
                                                    <div class="col-lg-2 col-md-2 col-sm-3 col-12 float-start">
                                                        <html:select property="subcategoryIdIndex" styleId="subcategoryIdIndex" styleClass="form-select" onchange = " javascript: viewCourse(-1); ">
                                                            <html:optionsCollection filter="false" property="subcategorys" label="ddlLabel" value="ddlValue">
                                                            </html:optionsCollection>
                                                        </html:select>
                                                    </div>
                                                    <div class="pull_right float-end">
                                                        <% if (addper.equals("Y")) {%><a href="javascript: addcourseForm();" class="add_btn"><i class="mdi mdi-plus"></i></a> <%}%>
                                                    </div>
                                                </div>

                                            <div class="row">
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                <div class="table-rep-plugin sort_table">
                                                <div class="table-responsive mb-0" data-bs-pattern="priority-columns">        
                                                    <table class="table table-striped">
                                                        <thead>
                                                            <tr>
                                                                <th width="25%"><span><b>Sub-Category</b></span></th>
                                                                <th width="25%"><span><b>Courses</b></span></th>
                                                                <th width="25%" class="text-right"><span><b>Actions</b></span></th>
                                                            </tr>
                                                        </thead>
                                                        <tbody id="sort_id">
<%
                                                        int status;
                                                        CreatetrainingInfo info;
                                                        for (int i = 0; i < total; i++)
                                                        {
                                                            info = (CreatetrainingInfo) createtraining_list.get(i);
                                                            if (info != null) 
                                                            {
                                                                status = info.getStatus();
%>
                                                            <tr>
                                                                <td><%= info.getSubcategoryname() != null ? info.getSubcategoryname() : "" %></td>
                                                                <td><%= info.getName() != null ? info.getName() : "" %></td>
                                                                <td class="action_column">
                                                                    <% if(info.getElearningfile() != null && !info.getElearningfile().equals("") ) {%><a href="<%=file_path+info.getElearningfile()+"story.html"%>" target='_blank'><img src="../assets/images/view.png"></a><%} else {%>&nbsp;<%}%>
                                                                    &nbsp;
                                                                    <% if (info.getCount() > 0) {%><a href="javascript:;" data-bs-toggle="modal" data-bs-target="#view_resume_list" class="mr_15" onclick=" javascript : viewimg('<%= info.getCourseId()%>')"><img src="../assets/images/attachment.png"></a><%} else {%>&nbsp;<%}%>                                                                    
                                                                    <% if (editper.equals("Y") && info.getStatus() == 1) {%><a href="javascript: modifycourseForm('<%= info.getCourseId()%>','<%= info.getSubcategoryId()%>');" class="mr_15"><img src="../assets/images/pencil.png"/></a><% } %>
                                                                   
                                                                    <span class="switch_bth float-end">
                                                                        <div class="form-check form-switch">
                                                                            <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault_<%=(i)%>" <% if(status == 1){%>checked<% }%> <% if(!editper.equals("Y")) {%>disabled="true"<% } %> onclick="javascript: deletecourseForm('<%= info.getCourseId()%>', '<%=status%>', '<%=i%>');"/>
                                                                        </div>
                                                                    </span>
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
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                <a href="javascript:;" class="filter_btn"><i class="mdi mdi-filter-outline"></i> Filter</a>
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
        <!-- END layout-wrapper -->
        <%@include file ="../footer.jsp"%>
        <div id="view_resume_list" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div><a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a></div>
                            <div class="row" id="viewfilesdiv">
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
                                <h3>Training Course Created</h3>
                                <p>Go ahead and Assign Trainings to a Matrix!</p>
                                <a href="javascript: goback();" class="msg_button" style="text-decoration: underline;">Create Training</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
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
        <% if(thankyou.equals("yes")){%>
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