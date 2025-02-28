<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="user" class="com.web.jxp.user.User" scope="page"/>
<!doctype html>
<html lang="en">
<%
    try
    {
        int mtp = 7, submtp = 1;
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
        ArrayList vec_list = new ArrayList();
        if(request.getSession().getAttribute("MODULEPER_LIST") != null)
            vec_list = (ArrayList)request.getSession().getAttribute("MODULEPER_LIST");
        UserInfo info = null;
        if(request.getAttribute("USER_DETAIL") != null)
            info = (UserInfo)request.getAttribute("USER_DETAIL");
        int total = vec_list.size();
        ArrayList clist = new ArrayList();
        if(request.getAttribute("CLIST") != null)
        {
            clist = (ArrayList) request.getAttribute("CLIST");
            request.removeAttribute("CLIST");
        }
        int ctotal = clist.size();

        String view_path = user.getMainPath("view_user_file");
%>  
    <head>
        <meta charset="utf-8">
        <title><%= user.getMainPath("title") != null ? user.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css"/>
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/user.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/user/UserAction.do" onsubmit="return false;" styleClass="form-horizontal">
        <html:hidden property="doCancel"/>
        <html:hidden property="search"/>
        <html:hidden property="clientIndex"/>
        <html:hidden property="permissionIndex"/>
        <!-- Begin page -->
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start"><a href="javascript: goback();" class="back_arrow"><img  src="../assets/images/back-arrow.png"/> View User</a></div>
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
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background">
                                    <div class="row d-none1">
                                        <div class="col-lg-12">
                                            <div class="main-heading">
                                                <div class="add-btn">
                                                    <h4>BASIC DETAILS</h4>
                                                </div>
                                            </div>
<%
                                            if(info != null)
                                            {
                                                String userphoto = info.getPhoto() != null ? info.getPhoto() : "";
                                                if(!userphoto.equals(""))
                                                    userphoto = user.getMainPath("view_user_file")+userphoto;
                                                else
                                                    userphoto = "/jxp/assets/images/empty_profile.png";
    %>
                                            <div class="row m_30">

                                                <div class="col-lg-2 col-md-2">

                                                    <div class="user_photo pic_photo">
                                                        <img src="../assets/images/user.png" />
                                                        <div class="upload_file">
                                                            <input id="upload1" type="file">
                                                            <img src="<%=userphoto%>">                                                                                    
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-10 col-md-10 flex-end1 row">
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">Full Name</label>
                                                        <span class="form-control"><%= (info.getName() != null && !info.getName().equals("")) ? info.getName() : "NA" %></span>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">Contact Number 1</label>
                                                        <span class="form-control"><%= (info.getContact1() != null && !info.getContact1().equals("")) ? info.getContact1() : "NA" %></span>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">Contact Number 2</label>
                                                        <span class="form-control"><%= (info.getContact2() != null && !info.getContact2().equals("")) ? info.getContact2() : "NA" %></span>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">Email ID</label>
                                                        <span class="form-control"><%= (info.getEmail() != null && !info.getEmail().equals("")) ? info.getEmail() : "NA" %></span>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">User ID</label>
                                                        <span class="form-control"><%= (info.getUserName() != null && !info.getUserName().equals("")) ? info.getUserName() : "NA" %></span>
                                                    </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                        <label class="form_label">Employee Code</label>
                                                        <span class="form-control"><%= (info.getCode() != null && !info.getCode().equals("")) ? info.getCode() : "NA" %></span>
                                                    </div>
                                                </div>
                                            </div>
                                            <% } %>
                                        </div>
                                        <div class="row col-lg-12">
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                <label class="form_label">Reporting Manager (optional)</label>
                                                <span class="form-control"><%= (info.getManagerName() != null && !info.getManagerName().equals("")) ? info.getManagerName() : "NA" %></span>
                                            </div>
                                            <div class="col-lg-8 col-md-8 col-sm-8 col-12 form_group">
                                                <label class="form_label">Address</label>
                                                <span class="form-control"><%= (info.getAddress() != null && !info.getAddress().equals("")) ? info.getAddress() : "NA" %></span>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Is Admin </label>
                                                <span class="full_width"><% if(info.getPermission().equals("Y")) {%><i class="fas fa-check fa-green"></i><% } else { %><i class="fas fa-times fa-red"></i><% } %></span> 
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Access All Clients</label>
                                                <span class="full_width"><% if(info.getAllclient() == 1) {%><i class="fas fa-check fa-green"></i><% } else { %><i class="fas fa-times fa-red"></i><% } %></span>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Coordinator</label>
                                                <span class="full_width"><% if(info.getCoordinator() == 1) {%><i class="fas fa-check fa-green"></i><% } else { %><i class="fas fa-times fa-red"></i><% } %></span>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Assessor </label>
                                                <span class="full_width"><% if(info.getAssessor() == 1) {%><i class="fas fa-check fa-green"></i><% } else { %><i class="fas fa-times fa-red"></i><% } %></span>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Competency Assessor </label>
                                                <span class="full_width"><% if(info.getCassessor() == 1) {%><i class="fas fa-check fa-green"></i><% } else { %><i class="fas fa-times fa-red"></i><% } %></span>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Is Manager</label>
                                                <span class="full_width"><% if(info.getIsManager() == 1) {%><i class="fas fa-check fa-green"></i><% } else { %><i class="fas fa-times fa-red"></i><% } %></span>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Recruiter</label>
                                                <span class="full_width"><% if(info.getIsRecruiter() == 1) {%><i class="fas fa-check fa-green"></i><% } else { %><i class="fas fa-times fa-red"></i><% } %></span>
                                            </div>
                                            <% if(!info.getCvfile().equals("")) {%>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">View CV </label>
                                               <div class="text-start down_prev" ><a href="javascript: seturl('<%=view_path+info.getCvfile()%>');">Preview</a></div>
                                            </div>
                                             <%}%>                      
                                        </div>
                                        <% if(info.getAllclient() == 0 && ctotal > 0) {%>
                                        <div class="row col-lg-12 all_client_sec" id="all_client">
                                            <div class="main-heading"><div class="add-btn"><h4>ACCESS</h4></div></div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                <div class="table-responsive table-detail">
                                                    <table class="table table-bordered table-striped mb-0">
                                                        <thead>
                                                            <tr>
                                                                <th width="20%">Client</th>
                                                                <th width="20%">Location</th>
                                                                <th width="30%">Assets</th>
                                                                <% if(info.getCassessor() == 1) {%>
                                                                    <th width="30%">Position-Rank</th>
                                                                <% } %>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
<%
                                                        UserInfo cinfo = null;
                                                        for(int i = 0; i < ctotal; i++)
                                                        {
                                                            cinfo = (UserInfo) clist.get(i);
                                                            if(cinfo != null)
                                                            {
%>
                                                            <tr>
                                                                <td><%=cinfo.getClientName() != null ? cinfo.getClientName() : ""%></td>
                                                                <td><%=cinfo.getCountryName() != null && !cinfo.getCountryName().equals("") ? cinfo.getCountryName() : "All"%></td>
                                                                <td><%=cinfo.getAssettext() != null ? cinfo.getAssettext() : ""%></td>
                                                                <% if(info.getCassessor() == 1) {%>
                                                                    <td><%=cinfo.getPositionRankId() != null ? cinfo.getPositionRankId() : ""%></td>
                                                                <% } %>
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
<%
                                        }
                                        if(!info.getPermission().equals("Y")) 
                                        {
%>
                                        <div class="row col-lg-12 m_15" id="all_permission">
                                            <div class="main-heading">
                                                <div class="add-btn">
                                                    <h4>RIGHTS & PERMISSIONS</h4>
                                                </div>
                                            </div>
                                            <div class="col-lg-12">
                                                <div class="table-responsive table-users">
                                                    <table class="table table-bordered table-striped mb-0">
                                                        <thead>
                                                            <tr>
                                                                <th>Module Name</th>
                                                                <th class="text-center">Add</th>
                                                                <th class="text-center">Edit</th>
                                                                <th class="text-center">Delete</th>
                                                                <th class="text-center">Approve</th>
                                                                <th class="text-center">View</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
<%
                                                    int temptype = 0;
                                                    String mType = "";
                                                    if(total > 0)
                                                    {
                                                        for(int k = 0 ; k < total; k++)
                                                        {
                                                            UserInfo mInfo = (UserInfo) vec_list.get(k);
                                                            if(mInfo != null)
                                                            {
                                                                if(mInfo.getType() > temptype)
                                                                {
                                                                    temptype = mInfo.getType();
                                                                    int subtotal = 0;
                                                                    if(temptype == 1)
                                                                    {
                                                                        ArrayList sub_list1 = user.getSortedSubListByGroup(1, 0, vec_list);
                                                                        ArrayList sub_list2 = user.getSortedSubListByGroup(1, 2, vec_list);
                                                                        ArrayList sub_list3 = user.getSortedSubListByGroup(1, 3, vec_list);
                                                                        ArrayList sub_list4 = user.getSortedSubListByGroup(1, 4, vec_list);
                                                                        ArrayList sub_list5 = user.getSortedSubListByGroup(1, 5, vec_list);
                                                                        ArrayList sub_list6 = user.getSortedSubListByGroup(1, 6, vec_list);
                                                                        int subtotal1 = sub_list1.size();   
                                                                        int subtotal2 = sub_list2.size(); 
                                                                        int subtotal3 = sub_list3.size(); 
                                                                        int subtotal4 = sub_list4.size();
                                                                        int subtotal5 = sub_list5.size();
                                                                        int subtotal6 = sub_list6.size();
                                                                        if (sub_list1 != null && subtotal1 > 0)
                                                                        {
%>
                                                                            <tr>
                                                                                <td colspan="6"><strong>Configuration - General</strong></td>
                                                                            </tr>
<%
                                                                            for (int i = 0; i < subtotal1; i++)
                                                                            {
                                                                                UserInfo subInfo = (UserInfo) sub_list1.get(i);
                                                                                if (subInfo != null)
                                                                                {
%>
                                                                                <tr class="odd gradeX">
                                                                                    <td><%= subInfo.getName() != null ? subInfo.getName() : "" %></td>
                                                                                    <th>
                                                                                        <% if (subInfo.getAddper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getEditper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getDeleteper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getApproveper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>                                                                                            
                                                                                    <th>
                                                                                        <% if (subInfo.getViewper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                </tr>
<%
                                                                                }
                                                                            }
                                                                        }
                                                                        if (sub_list2 != null && subtotal2 > 0)
                                                                        {
%>
                                                                            <tr>
                                                                                <td colspan="6"><strong>Configuration - Crew Management</strong></td>
                                                                            </tr>
<%
                                                                            for (int i = 0; i < subtotal2; i++)
                                                                            {
                                                                                UserInfo subInfo = (UserInfo) sub_list2.get(i);
                                                                                if (subInfo != null)
                                                                                {
%>
                                                                                <tr class="odd gradeX">
                                                                                    <td><%= subInfo.getName() != null ? subInfo.getName() : "" %></td>
                                                                                    <th>
                                                                                        <% if (subInfo.getAddper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getEditper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getDeleteper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getApproveper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>                                                                                            
                                                                                    <th>
                                                                                        <% if (subInfo.getViewper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                </tr>
<%
                                                                                }
                                                                            }
                                                                        }
                                                                        if (sub_list3 != null && subtotal3 > 0)
                                                                        {
%>
                                                                            <tr>
                                                                                <td colspan="6"><strong>Configuration - Training & Development</strong></td>                                                                            
                                                                            </tr>
<%
                                                                            for (int i = 0; i < subtotal3; i++)
                                                                            {
                                                                                UserInfo subInfo = (UserInfo) sub_list3.get(i);
                                                                                if (subInfo != null)
                                                                                {
%>
                                                                                <tr class="odd gradeX">
                                                                                    <td><%= subInfo.getName() != null ? subInfo.getName() : "" %></td>
                                                                                    <th>
                                                                                        <% if (subInfo.getAddper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getEditper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getDeleteper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getApproveper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>                                                                                            
                                                                                    <th>
                                                                                        <% if (subInfo.getViewper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                </tr>
<%
                                                                                }
                                                                            }
                                                                        }
                                                                        if (sub_list4 != null && subtotal4 > 0)
                                                                        {
%>
                                                                            <tr>
                                                                                <td colspan="6"><strong>Configuration - Wellness Management</strong></td>
                                                                            </tr>
<%
                                                                            for (int i = 0; i < subtotal4; i++)
                                                                            {
                                                                                UserInfo subInfo = (UserInfo) sub_list4.get(i);
                                                                                if (subInfo != null)
                                                                                {
%>
                                                                                <tr class="odd gradeX">
                                                                                    <td><%= subInfo.getName() != null ? subInfo.getName() : "" %></td>
                                                                                    <th>
                                                                                        <% if (subInfo.getAddper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getEditper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getDeleteper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getApproveper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>                                                                                            
                                                                                    <th>
                                                                                        <% if (subInfo.getViewper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                </tr>
<%
                                                                                }
                                                                            }
                                                                        }
                                                                        if (sub_list5 != null && subtotal5 > 0)
                                                                        {
%>
                                                                            <tr>
                                                                                <td colspan="6"><strong>Configuration - Billing</strong></td>
                                                                            </tr>
<%
                                                                            for (int i = 0; i < subtotal5; i++)
                                                                            {
                                                                                UserInfo subInfo = (UserInfo) sub_list5.get(i);
                                                                                if (subInfo != null)
                                                                                {
%>
                                                                                <tr class="odd gradeX">
                                                                                    <td><%= subInfo.getName() != null ? subInfo.getName() : "" %></td>
                                                                                    <th>
                                                                                        <% if (subInfo.getAddper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getEditper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getDeleteper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getApproveper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>                                                                                            
                                                                                    <th>
                                                                                        <% if (subInfo.getViewper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                </tr>
<%
                                                                                }
                                                                            }
                                                                        }
                                                                        if (sub_list6 != null && subtotal6 > 0)
                                                                        {
%>
                                                                            <tr>
                                                                                <td colspan="6"><strong>Configuration - Competency</strong></td>
                                                                            </tr>
<%
                                                                            for (int i = 0; i < subtotal6; i++)
                                                                            {
                                                                                UserInfo subInfo = (UserInfo) sub_list6.get(i);
                                                                                if (subInfo != null)
                                                                                {
%>
                                                                                <tr class="odd gradeX">
                                                                                    <td><%= subInfo.getName() != null ? subInfo.getName() : "" %></td>
                                                                                    <th>
                                                                                        <% if (subInfo.getAddper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getEditper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getDeleteper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getApproveper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>                                                                                            
                                                                                    <th>
                                                                                        <% if (subInfo.getViewper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                </tr>
<%
                                                                                }
                                                                            }
                                                                        }


                                                                    }
                                                                    else
                                                                    {
                                                                        if(temptype == 2)
                                                                            mType="Crew Management";
                                                                        else if(temptype == 3)
                                                                            mType="Reports";
                                                                        else if(temptype == 4)
                                                                            mType="Training and Development";
                                                                        else if(temptype == 5)
                                                                            mType="Competency Management";
                                                                        else if(temptype == 6)
                                                                            mType="Wellness Management";
                                                                        else if(temptype == 7)
                                                                            mType="Billing"; 
                                                                        else if(temptype == 9)
                                                                            mType="Analytics"; 
                                                                        else if(temptype == 10)
                                                                            mType="Knowledge Base"; 
                                                                        ArrayList sub_list = user.getSortedListByGroup(temptype, vec_list); 
                                                                        subtotal = sub_list.size();                                                                  
%>
                                                                        <tr>
                                                                            <td><strong><%=mType%></strong></td>
                                                                            <td>&nbsp;</td>
                                                                            <td>&nbsp;</td>
                                                                            <td>&nbsp;</td>
                                                                            <td>&nbsp;</td>
                                                                            <td>&nbsp;</td>
                                                                        </tr>
<%
                                                                        if (sub_list != null && subtotal > 0)
                                                                        {
                                                                            for (int i = 0; i < subtotal; i++)
                                                                            {
                                                                                UserInfo subInfo = (UserInfo) sub_list.get(i);
                                                                                if (subInfo != null)
                                                                                {
%>
                                                                                <tr class="odd gradeX">
                                                                                    <td><%= subInfo.getName() != null ? subInfo.getName() : "" %></td>
                                                                                    <th>
                                                                                        <% if (subInfo.getAddper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getEditper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getDeleteper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                    <th>
                                                                                        <% if (subInfo.getApproveper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>                                                                                            
                                                                                    <th>
                                                                                        <% if (subInfo.getViewper().equals("Y")) {%>
                                                                                        <i class="fa fa-check font-green"></i>
                                                                                        <% } else { %>
                                                                                        <i class="fa fa-close font-red"></i>
                                                                                        <% } %>
                                                                                    </th>
                                                                                </tr>
<%
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
%>                                                        
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>	
<%
                                        }
%>
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
        <%@include file="../footer.jsp" %>
        <div id="view_pdf" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                    <div class="modal-dialog modal-dialog-scrollable">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                                <span class="resume_title">CV</span>
                                <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                                <a href="" class="down_btn" id="diframe"><img src="../assets/images/download.png"/></a>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <iframe class="pdf_mode" src="" id="iframe"></iframe>                                        
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
}
catch(Exception e)
{
    e.printStackTrace();
}
%>
</html>
