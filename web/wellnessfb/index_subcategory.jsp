<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.wellnessfb.WellnessfbInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="wellnessfb" class="com.web.jxp.wellnessfb.Wellnessfb" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 7, submtp = 69, ctp = 2;
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
            ArrayList wellnessfb_list = new ArrayList();

            if(request.getAttribute("SUBCATEGORYWF_LIST") != null)
                wellnessfb_list = (ArrayList) request.getAttribute("SUBCATEGORYWF_LIST");

            int total = wellnessfb_list.size();
            int showsizelist = wellnessfb.getCountList("show_size_list");
       
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
            if(request.getAttribute("SUBCATEGORYWFSAVEMODEL") != null)
            {
                thankyou = (String)request.getAttribute("SUBCATEGORYWFSAVEMODEL");
                request.removeAttribute("SUBCATEGORYWFSAVEMODEL");
            }
            WellnessfbInfo csinfo = null;
            if(request.getAttribute("CATEGORY_STATUS") != null)
            {
                csinfo = (WellnessfbInfo)request.getAttribute("CATEGORY_STATUS");
                request.removeAttribute("CATEGORY_STATUS");
            }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= wellnessfb.getMainPath("title") != null ? wellnessfb.getMainPath("title") : "" %></title>
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
        <script type="text/javascript" src="../jsnew/wellnessfb.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/wellnessfb/WellnessfbAction.do" onsubmit="return false;">
            <html:hidden property="doAdd"/>
            <html:hidden property="doModify"/>
            <html:hidden property="doView"/>    
            <html:hidden property="categoryId"/>
            <html:hidden property="subcategoryId"/>
            <html:hidden property="doIndexSubcategory"/>    
            <html:hidden property="doAddSubcategory"/>
            <html:hidden property="doModifysubcategory"/>
            <html:hidden property="doIndexQuestion"/>
            <html:hidden property="subcategoryIdIndex"/>
            <html:hidden property="doAddQuestion"/>
            <html:hidden property="dodeletesubcategory"/>
            <html:hidden property="substatus"/>
            <html:hidden property="esubcategoryId"/>
            <html:hidden property="search"/>
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
                                    <a href="javascript: showDetailcategory();"><img src="../assets/images/back-arrow.png"/></a>
                                    <span>Wellness Feedback</span>
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

                            <div class="col-md-12 col-xl-12 tab_head_area">
                                <ul class='nav nav-tabs nav-tabs-custom' id='tab_menu'>
                                    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 1) ? " active" : ""%>'>
                                        <a class='nav-link' href="javascript: showDetailcategory();">
                                            <span class='d-none d-md-block'>Category</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 2) ? " active" : ""%>'>
                                        <a class='nav-link' href="javascript: viewSubcategory('-1');">
                                            <span class='d-none d-md-block'>Sub-Category</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 3) ? " active" : ""%>'>
                                        <a class='nav-link' href="javascript: viewQuestion('-1');">
                                            <span class='d-none d-md-block'>Questions</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
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

                                                    <div class="main-heading m_30">
                                                        <div class="add-btn">
                                                            <h4>SUB-CATEGORY LIST</h4>
                                                        </div>
                                                        <div class="pull_right float-end">
                                                            <% if (addper.equals("Y") && csinfo.getDdlValue() == 1) {%><a href="javascript: addsubcategoryForm('-1');" class="add_btn"><i class="mdi mdi-plus"></i></a> <%}%>
                                                        </div>
                                                    </div>

                                                    <div class="row">	

                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                            <div class="table-rep-plugin sort_table">
                                                <div class="table-responsive mb-0" data-bs-pattern="priority-columns">        
                                                    <table id="tech-companies-1" class="table table-striped">
                                                        <thead>
                                                            <tr>
                                                                <th width="40%">
                                                                    <span><b>Sub-category</b></span>
                                                                </th>
                                                                <th  width="16%"><span><b>Schedule</b></span></th>
                                                                <th  width="21%"><span><b>Send notification before (days)</b></span></th>
                                                                <th width="10%" class="text-center"><span><b>Questions</b></span></th>
                                                                <th width="13%" class="text-right"><span><b>Actions</b></span></th>
                                                            </tr>
                                                        </thead>
                                                        <tbody id="sort_id">
<%
                                                        int status;
                                                        WellnessfbInfo info;
                                                        for (int i = 0; i < total; i++)
                                                        {
                                                            info = (WellnessfbInfo) wellnessfb_list.get(i);
                                                            if (info != null) 
                                                            {
                                                                status = info.getStatus();
%>
                                                            <tr>
                                                                <td><%= info.getName() != null ? info.getName() : "" %></td>
                                                                <td><%= info.getRepeatvalue() != null ? info.getRepeatvalue() : "" %></td>
                                                                <td><%= info.getNotification() != null ? info.getNotification() : "" %></td>
                                                                <td class="assets_list text-center"><a href="javascript: viewQuestioncatsub('<%= info.getCategoryId()%>','<%= info.getSubcategoryId()%>');"><%=  wellnessfb.changeNum( info.getQuestioncount(),2) %></a></td>
                                                                <td class="action_column">
                                                                    <% if (addper.equals("Y")  && info.getStatus() == 1 && info.getCategorystatus() == 1) {%><a class="mr_15" href=" javascript: addquestionsubForm('<%= info.getSubcategoryId()%>');"><img src="../assets/images/plus.png"></a><% } %>
                                                                    <span class="switch_bth float-end">
                                                                        <div class="form-check form-switch">
                                                                            <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault_<%=(i)%>" <% if(status == 1){%>checked<% }%> <% if(!editper.equals("Y") || info.getCategorystatus() != 1) {%>disabled="true"<% } %> onclick="javascript: deletesubcategoryForm('<%= info.getSubcategoryId()%>', '<%=status%>', '<%=i%>');"/>
                                                                        </div>
                                                                    </span>
                                                                    <% if (editper.equals("Y") && info.getStatus() == 1 && info.getCategorystatus() == 1) {%><a href="javascript: modifySubcategoryForm('<%= info.getSubcategoryId()%>');" class="edit_mode float-end mr_15"><img src="../assets/images/pencil.png"/></a><% } %>
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
                                <h3>Feedback Sub-category Created</h3>
                                <p>Go ahead and list questions under the same!</p>
                                <a href="javascript: addquestionsubForm('-1');" class="msg_button" style="text-decoration: underline;">Add Questions</a>
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