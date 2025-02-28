<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.*" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="user" class="com.web.jxp.user.User" scope="page"/>
<!doctype html>
<html lang="en">
<%
    try
    {
        int mtp = 7, submtp = 1;
        if (session.getAttribute("LOGININFO") == null)
        {
%>
            <jsp:forward page="/index1.jsp"/>
<%
        }
        String message = "", clsmessage = "red_font";
        if (session.getAttribute("MESSAGE") != null)
        {
            message = (String)session.getAttribute("MESSAGE");
            session.removeAttribute("MESSAGE");
        }        
        if(message != null && (message.toLowerCase()).indexOf("success") != -1)
            clsmessage = "updated-msg";
        ArrayList list = new ArrayList();
        if(request.getSession().getAttribute("MODULEPER_LIST") != null)
            list = (ArrayList)request.getSession().getAttribute("MODULEPER_LIST");

        String photoc = "", cv = "";
        if(request.getAttribute("PHOTO") != null)
        {
            photoc = (String)request.getAttribute("PHOTO"); 
            request.removeAttribute("PHOTO");
        }
        if(photoc != null && !photoc.equals(""))
                photoc = user.getMainPath("view_user_file") + photoc;
        else
            photoc = "../assets/images/empty_user.png";
            
        if(request.getAttribute("CVFILE") != null)
        {
            cv = (String)request.getAttribute("CVFILE"); 
            request.removeAttribute("CVFILE");
        }
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
    <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
    <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
    <script src="../jsnew/common.js" type="text/javascript"></script>
    <script type="text/javascript" src="../jsnew/validation.js"></script>
    <script type="text/javascript" src="../jsnew/user.js"></script>
</head>
<body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
<html:form action="/user/UserAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
    <html:hidden property="userId"/>
    <html:hidden property="doSaveUser"/>
    <html:hidden property="doCancel"/>
    <html:hidden property="search"/>
    <html:hidden property="clientIndex"/>
    <html:hidden property="permissionIndex"/>
    <html:hidden property="userNameOld"/>
    <html:hidden property="photoHidden"/>    
    <html:hidden property="cvHidden"/>   
    <!-- Begin page -->
    <div id="layout-wrapper">
        <%@include file ="../header.jsp"%>
        <%@include file ="../sidemenu.jsp"%>
        <!-- Start right Content -->
        <div class="main-content">
            <div class="page-content">
                <div class="row head_title_area">
                    <div class="col-md-12 col-xl-12">
                        <div class="float-start">
                            <a href="javascript:goback();" class="back_arrow">
                            <img  src="../assets/images/back-arrow.png"/> 
                                <c:choose>
                                    <c:when test="${userForm.userId <= 0}">
                                        Add User
                                    </c:when>
                                    <c:otherwise>
                                        Edit User
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
                </div>
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-12 col-xl-12">
                            <div class="body-background">
                                <div class="row d-none1">
                                    <div class="col-lg-12">
                                        <% if(!message.equals("")) {%><div class="sbold <%=clsmessage%>">
                                            <%=message%>
                                        </div><% } %>
                                        <div class="main-heading">
                                            <div class="add-btn">
                                                <h4>BASIC DETAILS</h4>
                                            </div>
                                            
                                        </div>
                                        <div class="row m_30">
                                            <div class="col-lg-2 col-md-2">
                                                
                                                <div class="user_photo pic_photo">
                                                    <img src="../assets/images/user.png">
                                                    <div class="upload_file">
                                                        <html:file property="photo" styleId="upload1" onchange="javascript: setUserClass('1');"/>
                                                        <img id="photo_profile_id" src="<%=photoc%>" />
                                                        <% if(photoc.contains("empty_user")) {%><span>Display Picture <br/>(optional)</span><% } %>
                                                        <a id="upload_link1" class="uploaded_img1 d-none1" href="javascript:;" >
                                                            <img src="../assets/images/upload.png"> 
                                                        </a>
                                                        <a href="javascript:;" id="upload_link_edit" class="profile_edit d-none"><i class="ion ion-md-create"></i></a>
                                                    </div>
                                                </div>
                                                
                                            </div>
                                            <div class="col-lg-10 col-md-10 flex-end1 row align-items-end">
                                                <div class="col-lg-6 col-md-6 col-sm-12 col-12 max_size">
                                                    <ul>
                                                        <li><p>Maximum file size:</p> <span>5 MB (.jpg, .jpeg & .png)</span></li>
                                                        <li><p>Maximum Resolution Required:</p> <span>500 DPI</span></li>
                                                    </ul>
                                                </div>
                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 max_size not_desc">
                                                    <ul>
                                                        <li><p>Note: It is advised to use photo with complete face and shoulders visible.</p> </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>

                                    </div>

                                    <div class="row col-lg-12">
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                            <label class="form_label">Full Name</label>
                                            <html:text property="name" styleId="name" styleClass="form-control" maxlength="100" />
                                            <script type="text/javascript">
                                                document.getElementById("name").setAttribute('placeholder', '');
                                            </script>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Contact Number 1</label>
                                            <html:text property="contact1" styleId="contact1" styleClass="form-control" maxlength="20" />
                                            <script type="text/javascript">
                                                document.getElementById("contact1").setAttribute('placeholder', '');
                                            </script>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Contact Number 2</label>
                                            <html:text property="contact2" styleId="contact2" styleClass="form-control" maxlength="20" />
                                            <script type="text/javascript">
                                                document.getElementById("contact2").setAttribute('placeholder', '');
                                            </script>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Email ID</label>
                                            <html:text property="email" styleId="email" styleClass="form-control" maxlength="100" />
                                            <script type="text/javascript">
                                                document.getElementById("email").setAttribute('placeholder', '');
                                            </script>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">User ID</label>
                                            <html:text property="userName" styleId="userName" styleClass="form-control" maxlength="100" />
                                            <script type="text/javascript">
                                                document.getElementById("userName").setAttribute('placeholder', '');
                                            </script>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Employee Code</label>
                                            <html:text property="code" styleId="code" styleClass="form-control" maxlength="25" />
                                            <script type="text/javascript">
                                                document.getElementById("code").setAttribute('placeholder', '');
                                            </script>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Reporting Manager (optional)</label>
                                            <html:select property="managerId" styleClass="form-select">
                                                <html:optionsCollection filter="false" property="managers" label="name" value="userId">
                                                </html:optionsCollection>
                                            </html:select>
                                        </div>
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                            <label class="form_label">Address</label>
                                            <html:text property="address" styleId="address" styleClass="form-control" maxlength="100" />
                                            <script type="text/javascript">
                                                document.getElementById("address").setAttribute('placeholder', '');
                                            </script>
                                        </div>
                                        <div class="col-lg-2 col-md-2 col-sm-3 col-6 form_group">
                                            <label class="form_label">Is Admin </label>
                                            <div class="full_width">
                                                <div class="form-check permission-check">
                                                    <html:checkbox property="permission" styleClass="form-check-input" value="Y" onchange="javascript:showhide();" />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-2 col-md-2 col-sm-3 col-6 form_group">
                                            <label class="form_label">Access All Clients</label>
                                            <div class="full_width">
                                                <div class="form-check permission-check">
                                                    <html:checkbox property="allclient" styleClass="form-check-input" value="1" onchange="javascript:showhideclient();" />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-2 col-md-2 col-sm-3 col-6 form_group">
                                            <label class="form_label">Coordinator </label>
                                            <div class="full_width">
                                                <div class="form-check permission-check">
                                                    <html:checkbox property="coordinator" styleClass="form-check-input" value="1" onchange = "javascript: setFlag('1');"/>
                                                </div>
                                            </div>
                                        </div>
                                        
                                                
                                        <div class="col-lg-6 col-md-6 col-sm-3 col-6 form_group">
                                            <div class="row">
                                                <div class="col-lg-3 col-md-3 col-sm-3 col-6 form_group">
                                                    <label class="form_label">Assessor </label>
                                                    <div class="full_width">
                                                        <div class="form-check permission-check">
                                                            <html:checkbox property="assessor" styleClass="form-check-input" value="1" onchange = "javascript: setFlag('2');"/>
                                                        </div>
                                                    </div>
                                                </div>    
                                                <div class="col-lg-3 col-md-3 col-sm-3 col-6 form_group" >
                                                    <label class="form_label">Competency Assessor </label>
                                                    <div class="full_width">
                                                        <div class="form-check permission-check">
                                                            <html:checkbox property="cassessor" styleClass="form-check-input" value="1"  onchange="javascript: showhidecassessor();"/>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-3 col-md-3 col-sm-3 col-6 form_group" >
                                                    <label class="form_label">Client Manager </label>
                                                    <div class="full_width">
                                                        <div class="form-check permission-check">
                                                            <html:checkbox property="isManager" styleClass="form-check-input" value="1" onchange = "javascript: setFlag2('1');"/>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-3 col-md-3 col-sm-3 col-6 form_group" >
                                                    <label class="form_label">Recruiter </label>
                                                    <div class="full_width">
                                                        <div class="form-check permission-check">
                                                            <html:checkbox property="isRecruiter" styleClass="form-check-input" value="1" onchange = "javascript: setFlag2('2');"/>
                                                        </div>
                                                    </div>
                                                </div>
                                                        
                                            </div>
                                        </div>        
                                        
                                    </div>
                                    <div class="row col-lg-12 all_client_sec" id="all_client" style='display: none;'>
                                        <div class="main-heading">
                                            <div class="add-btn">
                                                <h4>ACCESS</h4>
                                            </div>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Client</label>
                                            <html:select property="clientId" styleId="clientId" styleClass="form-select" onchange="javascript: setCountryDDL();">
                                                <html:option value = "-1">- Select -</html:option>
                                                <html:optionsCollection filter="false" property="clients" label="name" value="userId">
                                                </html:optionsCollection>
                                            </html:select>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Location(Optional)</label>
                                            <html:select property="countryId" styleClass="form-select" styleId="countryddl" onchange="javascript: setAssetDDL();">
                                                <html:optionsCollection filter="false" property="countries" label="name" value="userId">
                                                </html:optionsCollection>
                                            </html:select>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group compare_section">
                                            <label class="form_label">Assets</label>
                                            <div id="assetdiv">
                                                <select name="assetId" id="multiselect_dd" class="form-select form-control btn btn-default mt-multiselect" multiple="multiple" data-select-all="true" data-label="left" data-width="100%" data-filter="false">
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group compare_section" id="show_cassessor">
                                            <label class="form_label">Position-Rank</label>
                                                <div id="positionrankdiv">
                                                    <select name="positionRank" id="position_rank" class="form-select form-control btn btn-default mt-multiselect" multiple="multiple" data-select-all="true" data-label="left" data-width="100%" data-filter="false">
                                                    </select>
                                                </div>
                                        </div>                                        
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group" id="ca_cv">
                                            <label class="form_label">Upload CV (5MB)(.pdf Only)</label> 
                                            <html:file property="cvfile" styleId="upload2" onchange="javascript: setClass('2');"/>
                                            <a href="javascript:;" id="upload_link_2" class="attache_btn uploaded_img1"><i class="fas fa-paperclip"></i> Attach </a>
                                        </div>
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group add_more"><a href="javascript:addtolist();"><i class="mdi mdi-plus"></i> Add Client</a></div>
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                            <div class="table-responsive table-detail">
                                                <table class="table table-bordered table-striped mb-0">
                                                    <thead>
                                                        <tr>
                                                            <th width="20%">Client</th>
                                                            <th width="20%">Location</th>
                                                            <th width="30%">Assets</th>
                                                            <th width="30%" id="prid">Position-Rank</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="ajax_cat">
                                                        
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
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
                                                            <th>Add</th>
                                                            <th>Edit</th>
                                                            <th>Delete</th>
                                                            <th>Approve</th>
                                                            <th>View</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
<%
                                                    int totalper = list.size();
                                                    int temptype = 0, tempsubtype = 0;
                                                    String mType = "";
                                                    if(list != null && totalper > 0)
                                                    {
                                                        for(int k = 0 ; k < totalper;k++)
                                                        {
                                                            UserInfo mInfo = (UserInfo) list.get(k);
                                                            if(mInfo != null)
                                                            {
                                                                if(mInfo.getType() > temptype)
                                                                {
                                                                    temptype = mInfo.getType();
                                                                    int subtotal = 0;
                                                                    if(temptype == 1)
                                                                    {
                                                                        ArrayList sub_list1 = user.getSortedSubListByGroup(1, 0, list);
                                                                        ArrayList sub_list2 = user.getSortedSubListByGroup(1, 2, list);
                                                                        ArrayList sub_list3 = user.getSortedSubListByGroup(1, 3, list);
                                                                        ArrayList sub_list4 = user.getSortedSubListByGroup(1, 4, list);
                                                                        ArrayList sub_list5 = user.getSortedSubListByGroup(1, 5, list);
                                                                        ArrayList sub_list6 = user.getSortedSubListByGroup(1, 6, list);
                                                                        int subtotal1 = sub_list1.size();   
                                                                        int subtotal2 = sub_list2.size(); 
                                                                        int subtotal3 = sub_list3.size(); 
                                                                        int subtotal4 = sub_list4.size();
                                                                        int subtotal5 = sub_list5.size();
                                                                        int subtotal6 = sub_list6.size();
                                                                        subtotal = subtotal1 + subtotal2 + subtotal3 + subtotal4 + subtotal5+subtotal6;
                                                                        int mcc = -1;
%>
                                                                        <tr>
                                                                            <td><strong>Configuration - General</strong></td>
                                                                            <td><input class='form-check-input' type="checkbox" id="add_<%=temptype%>" onclick="javascript: selectAllModule('<%=temptype%>', '<%=subtotal%>', '1');"/>
                                                                                <span class='select-pdg'>Select All</span>
                                                                            </td>
                                                                            <td><input class='form-check-input' type="checkbox" id="edit_<%=temptype%>" onclick="javascript: selectAllModule('<%=temptype%>', '<%=subtotal%>', '2');"/>
                                                                                <span class='select-pdg'>Select All</span>
                                                                            </td>
                                                                            <td><input class='form-check-input' type="checkbox" id="delete_<%=temptype%>" onclick="javascript: selectAllModule('<%=temptype%>', '<%=subtotal%>', '3');"/>
                                                                                <span class='select-pdg'>Select All</span>
                                                                            </td>
                                                                            <td><input class='form-check-input' type="checkbox" id="approve_<%=temptype%>" onclick="javascript: selectAllModule('<%=temptype%>', '<%=subtotal%>', '4');"/>
                                                                                <span class='select-pdg'>Select All</span>
                                                                            </td>
                                                                            <td><input class='form-check-input' type="checkbox" id="view_<%=temptype%>" onclick="javascript: selectAllModule('<%=temptype%>', '<%=subtotal%>', '6');"/>
                                                                                <span class='select-pdg'>Select All</span>
                                                                            </td>
                                                                        </tr>
<%
                                                                        if (sub_list1 != null && subtotal1 > 0)
                                                                        {                                                                            
                                                                            for (int i = 0; i < subtotal1; i++)
                                                                            {
                                                                                UserInfo subInfo = (UserInfo) sub_list1.get(i);
                                                                                if (subInfo != null)
                                                                                {
                                                                                    mcc++;
    %>
                                                                                <tr>
                                                                                    <input type="hidden" name="moduleIdArr" value="<%=subInfo.getModuleId() %>" />
                                                                                    <td> <%= subInfo.getName() != null ? subInfo.getName() : "" %> </td>
                                                                                    <% if (subInfo.getAddper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="addRight" value="<%=subInfo.getModuleId()%>" checked="true" id="add_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '1', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="addRight" value="<%=subInfo.getModuleId()%>" id="add_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '1', '<%=mcc%>');"></td>
                                                                                    <%} if (subInfo.getEditper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="editRight" value="<%=subInfo.getModuleId()%>" checked="true" id="edit_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '2', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="editRight" value="<%=subInfo.getModuleId()%>" id="edit_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '2', '<%=mcc%>');"></td>
                                                                                    <%}if (subInfo.getDeleteper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="deleteRight" value="<%=subInfo.getModuleId()%>" checked="true" id="delete_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '3', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="deleteRight" value="<%=subInfo.getModuleId()%>" id="delete_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '3', '<%=mcc%>');"></td>
                                                                                    <%}if (subInfo.getApproveper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="approveRight" value="<%=subInfo.getModuleId()%>" checked="true" id="approve_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '4', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="approveRight" value="<%=subInfo.getModuleId()%>" id="approve_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '4', '<%=mcc%>');"></td>

                                                                                    <% }if (subInfo.getViewper().equals("Y")) { %>
                                                                                    <td class="text-center"><input type="checkbox" class="form-check-input" name="viewRight" value="<%=subInfo.getModuleId()%>" checked="true" id="view_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '6', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                    <td class="text-center"><input type="checkbox" class="form-check-input" name="viewRight" value="<%=subInfo.getModuleId()%>" id="view_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '6', '<%=mcc%>');"></td>
                                                                                    <% } %>
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
                                                                                    mcc++;
    %>
                                                                                <tr>
                                                                                    <input type="hidden" name="moduleIdArr" value="<%=subInfo.getModuleId() %>" />
                                                                                    <td> <%= subInfo.getName() != null ? subInfo.getName() : "" %> </td>
                                                                                    <% if (subInfo.getAddper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="addRight" value="<%=subInfo.getModuleId()%>" checked="true" id="add_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '1', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="addRight" value="<%=subInfo.getModuleId()%>" id="add_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '1', '<%=mcc%>');"></td>
                                                                                    <%} if (subInfo.getEditper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="editRight" value="<%=subInfo.getModuleId()%>" checked="true" id="edit_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '2', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="editRight" value="<%=subInfo.getModuleId()%>" id="edit_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '2', '<%=mcc%>');"></td>
                                                                                    <%}if (subInfo.getDeleteper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="deleteRight" value="<%=subInfo.getModuleId()%>" checked="true" id="delete_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '3', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="deleteRight" value="<%=subInfo.getModuleId()%>" id="delete_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '3', '<%=mcc%>');"></td>
                                                                                    <%}if (subInfo.getApproveper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="approveRight" value="<%=subInfo.getModuleId()%>" checked="true" id="approve_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '4', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="approveRight" value="<%=subInfo.getModuleId()%>" id="approve_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '4', '<%=mcc%>');"></td>

                                                                                    <% }if (subInfo.getViewper().equals("Y")) { %>
                                                                                    <td class="text-center"><input type="checkbox" class="form-check-input" name="viewRight" value="<%=subInfo.getModuleId()%>" checked="true" id="view_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '6', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                    <td class="text-center"><input type="checkbox" class="form-check-input" name="viewRight" value="<%=subInfo.getModuleId()%>" id="view_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '6', '<%=mcc%>');"></td>
                                                                                    <% } %>
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
                                                                                    mcc++;
    %>
                                                                                <tr>
                                                                                    <input type="hidden" name="moduleIdArr" value="<%=subInfo.getModuleId() %>" />
                                                                                    <td> <%= subInfo.getName() != null ? subInfo.getName() : "" %> </td>
                                                                                    <% if (subInfo.getAddper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="addRight" value="<%=subInfo.getModuleId()%>" checked="true" id="add_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '1', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="addRight" value="<%=subInfo.getModuleId()%>" id="add_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '1', '<%=mcc%>');"></td>
                                                                                    <%} if (subInfo.getEditper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="editRight" value="<%=subInfo.getModuleId()%>" checked="true" id="edit_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '2', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="editRight" value="<%=subInfo.getModuleId()%>" id="edit_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '2', '<%=mcc%>');"></td>
                                                                                    <%}if (subInfo.getDeleteper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="deleteRight" value="<%=subInfo.getModuleId()%>" checked="true" id="delete_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '3', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="deleteRight" value="<%=subInfo.getModuleId()%>" id="delete_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '3', '<%=mcc%>');"></td>
                                                                                    <%}if (subInfo.getApproveper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="approveRight" value="<%=subInfo.getModuleId()%>" checked="true" id="approve_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '4', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="approveRight" value="<%=subInfo.getModuleId()%>" id="approve_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '4', '<%=mcc%>');"></td>

                                                                                    <% }if (subInfo.getViewper().equals("Y")) { %>
                                                                                    <td class="text-center"><input type="checkbox" class="form-check-input" name="viewRight" value="<%=subInfo.getModuleId()%>" checked="true" id="view_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '6', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                    <td class="text-center"><input type="checkbox" class="form-check-input" name="viewRight" value="<%=subInfo.getModuleId()%>" id="view_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '6', '<%=mcc%>');"></td>
                                                                                    <% } %>
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
                                                                                    mcc++;
    %>
                                                                                <tr>
                                                                                    <input type="hidden" name="moduleIdArr" value="<%=subInfo.getModuleId() %>" />
                                                                                    <td> <%= subInfo.getName() != null ? subInfo.getName() : "" %> </td>
                                                                                    <% if (subInfo.getAddper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="addRight" value="<%=subInfo.getModuleId()%>" checked="true" id="add_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '1', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="addRight" value="<%=subInfo.getModuleId()%>" id="add_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '1', '<%=mcc%>');"></td>
                                                                                    <%} if (subInfo.getEditper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="editRight" value="<%=subInfo.getModuleId()%>" checked="true" id="edit_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '2', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="editRight" value="<%=subInfo.getModuleId()%>" id="edit_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '2', '<%=mcc%>');"></td>
                                                                                    <%}if (subInfo.getDeleteper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="deleteRight" value="<%=subInfo.getModuleId()%>" checked="true" id="delete_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '3', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="deleteRight" value="<%=subInfo.getModuleId()%>" id="delete_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '3', '<%=mcc%>');"></td>
                                                                                    <%}if (subInfo.getApproveper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="approveRight" value="<%=subInfo.getModuleId()%>" checked="true" id="approve_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '4', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="approveRight" value="<%=subInfo.getModuleId()%>" id="approve_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '4', '<%=mcc%>');"></td>

                                                                                    <% }if (subInfo.getViewper().equals("Y")) { %>
                                                                                    <td class="text-center"><input type="checkbox" class="form-check-input" name="viewRight" value="<%=subInfo.getModuleId()%>" checked="true" id="view_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '6', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                    <td class="text-center"><input type="checkbox" class="form-check-input" name="viewRight" value="<%=subInfo.getModuleId()%>" id="view_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '6', '<%=mcc%>');"></td>
                                                                                    <% } %>
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
                                                                                    mcc++;
    %>
                                                                                <tr>
                                                                                    <input type="hidden" name="moduleIdArr" value="<%=subInfo.getModuleId() %>" />
                                                                                    <td> <%= subInfo.getName() != null ? subInfo.getName() : "" %> </td>
                                                                                    <% if (subInfo.getAddper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="addRight" value="<%=subInfo.getModuleId()%>" checked="true" id="add_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '1', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="addRight" value="<%=subInfo.getModuleId()%>" id="add_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '1', '<%=mcc%>');"></td>
                                                                                    <%} if (subInfo.getEditper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="editRight" value="<%=subInfo.getModuleId()%>" checked="true" id="edit_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '2', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="editRight" value="<%=subInfo.getModuleId()%>" id="edit_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '2', '<%=mcc%>');"></td>
                                                                                    <%}if (subInfo.getDeleteper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="deleteRight" value="<%=subInfo.getModuleId()%>" checked="true" id="delete_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '3', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="deleteRight" value="<%=subInfo.getModuleId()%>" id="delete_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '3', '<%=mcc%>');"></td>
                                                                                    <%}if (subInfo.getApproveper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="approveRight" value="<%=subInfo.getModuleId()%>" checked="true" id="approve_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '4', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="approveRight" value="<%=subInfo.getModuleId()%>" id="approve_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '4', '<%=mcc%>');"></td>

                                                                                    <% }if (subInfo.getViewper().equals("Y")) { %>
                                                                                    <td class="text-center"><input type="checkbox" class="form-check-input" name="viewRight" value="<%=subInfo.getModuleId()%>" checked="true" id="view_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '6', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                    <td class="text-center"><input type="checkbox" class="form-check-input" name="viewRight" value="<%=subInfo.getModuleId()%>" id="view_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '6', '<%=mcc%>');"></td>
                                                                                    <% } %>
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
                                                                                    mcc++;
    %>
                                                                                <tr>
                                                                                    <input type="hidden" name="moduleIdArr" value="<%=subInfo.getModuleId() %>" />
                                                                                    <td> <%= subInfo.getName() != null ? subInfo.getName() : "" %> </td>
                                                                                    <% if (subInfo.getAddper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="addRight" value="<%=subInfo.getModuleId()%>" checked="true" id="add_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '1', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="addRight" value="<%=subInfo.getModuleId()%>" id="add_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '1', '<%=mcc%>');"></td>
                                                                                    <%} if (subInfo.getEditper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="editRight" value="<%=subInfo.getModuleId()%>" checked="true" id="edit_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '2', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="editRight" value="<%=subInfo.getModuleId()%>" id="edit_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '2', '<%=mcc%>');"></td>
                                                                                    <%}if (subInfo.getDeleteper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="deleteRight" value="<%=subInfo.getModuleId()%>" checked="true" id="delete_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '3', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="deleteRight" value="<%=subInfo.getModuleId()%>" id="delete_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '3', '<%=mcc%>');"></td>
                                                                                    <%}if (subInfo.getApproveper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="approveRight" value="<%=subInfo.getModuleId()%>" checked="true" id="approve_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '4', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="approveRight" value="<%=subInfo.getModuleId()%>" id="approve_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '4', '<%=mcc%>');"></td>

                                                                                    <% }if (subInfo.getViewper().equals("Y")) { %>
                                                                                    <td class="text-center"><input type="checkbox" class="form-check-input" name="viewRight" value="<%=subInfo.getModuleId()%>" checked="true" id="view_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '6', '<%=mcc%>');"/></td>
                                                                                    <%} else {%>
                                                                                    <td class="text-center"><input type="checkbox" class="form-check-input" name="viewRight" value="<%=subInfo.getModuleId()%>" id="view_<%=temptype%>_<%=mcc%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '6', '<%=mcc%>');"></td>
                                                                                    <% } %>
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
                                                                        ArrayList sub_list = user.getSortedListByGroup(temptype, list); 
                                                                        subtotal = sub_list.size();
%>
                                                                    <tr>
                                                                        <td><strong><%=mType%></strong></td>
                                                                        <td><input class='form-check-input' type="checkbox" id="add_<%=temptype%>" onclick="javascript: selectAllModule('<%=temptype%>', '<%=subtotal%>', '1');"/>
                                                                            <span class='select-pdg'>Select All</span>
                                                                        </td>
                                                                        <td><input class='form-check-input' type="checkbox" id="edit_<%=temptype%>" onclick="javascript: selectAllModule('<%=temptype%>', '<%=subtotal%>', '2');"/>
                                                                            <span class='select-pdg'>Select All</span>
                                                                        </td>
                                                                        <td><input class='form-check-input' type="checkbox" id="delete_<%=temptype%>" onclick="javascript: selectAllModule('<%=temptype%>', '<%=subtotal%>', '3');"/>
                                                                            <span class='select-pdg'>Select All</span>
                                                                        </td>
                                                                        <td><input class='form-check-input' type="checkbox" id="approve_<%=temptype%>" onclick="javascript: selectAllModule('<%=temptype%>', '<%=subtotal%>', '4');"/>
                                                                            <span class='select-pdg'>Select All</span>
                                                                        </td>
                                                                        <td><input class='form-check-input' type="checkbox" id="view_<%=temptype%>" onclick="javascript: selectAllModule('<%=temptype%>', '<%=subtotal%>', '6');"/>
                                                                            <span class='select-pdg'>Select All</span>
                                                                        </td>
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
                                                                                <tr>
                                                                                    <input type="hidden" name="moduleIdArr" value="<%=subInfo.getModuleId() %>" />
                                                                                    <td> <%= subInfo.getName() != null ? subInfo.getName() : "" %> </td>
                                                                                    <% if (subInfo.getAddper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="addRight" value="<%=subInfo.getModuleId()%>" checked="true" id="add_<%=temptype%>_<%=i%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '1', '<%=i%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="addRight" value="<%=subInfo.getModuleId()%>" id="add_<%=temptype%>_<%=i%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '1', '<%=i%>');"></td>
                                                                                    <%} if (subInfo.getEditper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="editRight" value="<%=subInfo.getModuleId()%>" checked="true" id="edit_<%=temptype%>_<%=i%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '2', '<%=i%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="editRight" value="<%=subInfo.getModuleId()%>" id="edit_<%=temptype%>_<%=i%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '2', '<%=i%>');"></td>
                                                                                    <%}if (subInfo.getDeleteper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="deleteRight" value="<%=subInfo.getModuleId()%>" checked="true" id="delete_<%=temptype%>_<%=i%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '3', '<%=i%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="deleteRight" value="<%=subInfo.getModuleId()%>" id="delete_<%=temptype%>_<%=i%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '3', '<%=i%>');"></td>
                                                                                    <%}if (subInfo.getApproveper().equals("Y")) {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="approveRight" value="<%=subInfo.getModuleId()%>" checked="true" id="approve_<%=temptype%>_<%=i%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '4', '<%=i%>');"/></td>
                                                                                    <%} else {%>
                                                                                        <td class="text-center"><input type="checkbox" class="form-check-input" name="approveRight" value="<%=subInfo.getModuleId()%>" id="approve_<%=temptype%>_<%=i%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '4', '<%=i%>');"></td>

                                                                                    <% }if (subInfo.getViewper().equals("Y")) { %>
                                                                                    <td class="text-center"><input type="checkbox" class="form-check-input" name="viewRight" value="<%=subInfo.getModuleId()%>" checked="true" id="view_<%=temptype%>_<%=i%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '6', '<%=i%>');"/></td>
                                                                                    <%} else {%>
                                                                                    <td class="text-center"><input type="checkbox" class="form-check-input" name="viewRight" value="<%=subInfo.getModuleId()%>" id="view_<%=temptype%>_<%=i%>" onclick="javascript: getSelectedPermission('<%=temptype%>', '<%=subtotal%>', '6', '<%=i%>');"></td>
                                                                                    <% } %>
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
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="submitdiv">
                                            <a href="javascript: submitUserForm();" class="save_btn"><img src="../assets/images/save.png"> Save</a>
                                            <a href="javascript: goback();" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
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
        $(function () {
            $("#upload_link1").on('click', function (e) {
                e.preventDefault();
                $("#upload1:hidden").trigger('click');
            });
            $("#upload_link_2").on('click', function (e) {
                e.preventDefault();
                $("#upload2:hidden").trigger('click');
            });
        });
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#multiselect_dd').multiselect({
                includeSelectAllOption: true,
                //maxHeight: 400,
                dropUp: true,
                nonSelectedText: '- Select -',
                maxHeight: 200,
                enableFiltering: false,
                enableCaseInsensitiveFiltering: false,
                buttonWidth: '100%'
            });
            $('#position_rank').multiselect({
                includeSelectAllOption: true,
                //maxHeight: 400,
                dropUp: true,
                nonSelectedText: 'Select Position-Rank',
                maxHeight: 200,
                enableFiltering: false,
                enableCaseInsensitiveFiltering: false,
                buttonWidth: '100%'
            });
        });
    </script>
    <script type="text/javascript">
    function addLoadEvent(func) {
          var oldonload = window.onload;
          if (typeof window.onload != 'function') {
            window.onload = func;
          } else {
            window.onload = function() {
              if (oldonload) {
                oldonload();
              }
            }
          }
        }
        addLoadEvent(showhide());
        addLoadEvent(showhideclient());
        addLoadEvent(addalledit());        
        addLoadEvent(showhidecassessor());
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
