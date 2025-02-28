<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.client.ClientInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="client" class="com.web.jxp.client.Client" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 7, submtp = 2;
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
            ClientInfo info = null;
            if(session.getAttribute("CLIENT_DETAIL") != null)
                info = (ClientInfo)session.getAttribute("CLIENT_DETAIL");
            ArrayList asset_list = new ArrayList();
            if(session.getAttribute("ASSETLIST") != null)
                asset_list = (ArrayList) session.getAttribute("ASSETLIST");
            int asset_list_size = asset_list.size();
            String tabno = "1";
            if(session.getAttribute("TABNO") != null)
                tabno = (String)session.getAttribute("TABNO");
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
            if(request.getAttribute("CLIENTSAVEMODEL") != null)
            {
                thankyou = (String)request.getAttribute("CLIENTSAVEMODEL");
                request.removeAttribute("CLIENTSAVEMODEL");
            }
    
          
    %>
    <head>
        <meta charset="utf-8">
        <title><%= client.getMainPath("title") != null ? client.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/drop/dropzone.min.css" rel="stylesheet" type="text/css" />
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css"/>
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/client.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/client/ClientAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
            <html:hidden property="doCancel"/>
            <html:hidden property="doModify"/>
            <html:hidden property="doView"/>
            <html:hidden property="doAdd"/>
            <html:hidden property="search"/>
            <html:hidden property="clientId"/>
            <html:hidden property="doSaveAsset"/>
            <html:hidden property="doDeleteAsset"/>
            <html:hidden property="doModifyAsset"/>
            <html:hidden property="countryIndexId"/>
            <html:hidden property="assettypeIndexId"/>
            <html:hidden property="fname"/>
            <input type="hidden" name="srnoModal" id="srnoModal" value="-1" />
            <!-- Begin page -->
            <div id="layout-wrapper">
                <%@include file="../header.jsp" %>
                <%@include file="../sidemenu.jsp" %>
                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content tab_panel">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12">
                                <div class="float-start"><a href="javascript: goback();" class="back_arrow"><img  src="../assets/images/back-arrow.png"/> Client</a></div>
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
                                <ul class="nav nav-tabs nav-tabs-custom" role="tablist">
                                    <li class="nav-item">
                                        <a class="nav-link <% if(tabno.equals("1")) {%>active<% } %>" data-bs-toggle="tab" href="#tab1" role="tab">
                                            <span class="d-none d-md-block">Client</span><span class="d-block d-md-none"><i class="mdi mdi-home-variant h5"></i></span>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link <% if(tabno.equals("2")) {%>active<% } %>" data-bs-toggle="tab" href="#tab2" role="tab">
                                            <span class="d-none d-md-block">Asset</span><span class="d-block d-md-none"><i class="mdi mdi-account h5"></i></span>
                                        </a>
                                    </li>
                                </ul>
                            </div>	
                        </div>
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-md-12 col-xl-12">
                                    <div class="body-background">
                                        <div class="row d-none1">
                                            <div class="col-lg-12">
                                                <div class="tab-content">
                                                    <div class="tab-pane <% if(tabno.equals("1")) {%>active<% } %>" id="tab1" role="tabpanel">
                                                        <div class="row m_30">
                                                                <div class="main-heading">
                                                                    <div class="add-btn">
                                                                        <h4>CLIENT DETAILS</h4>
                                                                    </div>
                                                                    <div class="edit_btn pull_right float-end">
                                                                        <% if (editper.equals("Y")) {%><a href="javascript: modifyForm('-1');"><img src="../assets/images/edit.png"></a><% } %>
                                                                    </div>
                                                                </div>
                                                           
                                                            <div class="row">
                                                                <% if(info != null) {%>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Client ID</label>
                                                                    <span class="form-control"><%= info.getClientId() %></span>
                                                                </div>
                                                                <div class="col-lg-8 col-md-8 col-sm-8 col-12 form_group">
                                                                    <label class="form_label">Client Name</label>
                                                                    <span class="form-control"><%= info.getName() != null && !info.getName().equals("") ?  info.getName() : "&nbsp;" %></span>
                                                                </div>
                                                                <div class="col-lg-8 col-md-8 col-sm-8 col-12 form_group">
                                                                    <label class="form_label">Head Office Address</label>
                                                                    <span class="form-control"><%= info.getHeadofficeaddress() != null && !info.getHeadofficeaddress().equals("") ? info.getHeadofficeaddress() :  "&nbsp;" %> </span>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Country</label>
                                                                    <span class="form-control"><%= info.getCountryName() != null && !info.getCountryName().equals("") ? info.getCountryName() : "&nbsp;" %></span>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Incharge Name (point of contact)</label>
                                                                    <span class="form-control"><%= info.getInchargename() != null && !info.getInchargename().equals("") ? info.getInchargename() : "&nbsp;" %></span>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Position of Incharge (point of contact)</label>
                                                                    <span class="form-control"><%= info.getPosition() != null && !info.getPosition().equals("") ? info.getPosition() : "&nbsp;" %></span>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Incharge Mail</label>
                                                                    <span class="form-control"><%= info.getEmail() != null && !info.getEmail().equals("") ? info.getEmail() : "&nbsp;" %></span>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Client Contact Number</label>
                                                                    <span class="form-control"><%= info.getContact() != null ? "+"+info.getISDcode()+" "+info.getContact() : "" %></span>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Enrollment Date</label>
                                                                    <span class="form-control"><%= info.getDate() != null && !info.getDate().equals("") ? info.getDate() : "&nbsp;" %></span>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group media_links">
                                                                    <label class="form_label">Media Links</label>
                                                                    <div class="media_link">
                                                                        <ul>
                                                                            <% if(info.getLink1() != null && !info.getLink1().equals("")) {%><li><a href="<%=info.getLink1()%>" target='_blank'><i class="ion ion-ios-globe"></i></a></li><% } %>
                                                                            <% if(info.getLink2() != null && !info.getLink2().equals("")) {%><li><a href="<%=info.getLink2()%>" target='_blank'><i class="ion ion-md-locate"></i></a></li><% } %>
                                                                            <% if(info.getLink3() != null && !info.getLink3().equals("")) {%><li><a href="<%=info.getLink3()%>" target='_blank'><i class="ion ion-logo-instagram"></i></a></li><% } %>
                                                                            <% if(info.getLink4() != null && !info.getLink4().equals("")) {%><li><a href="<%=info.getLink4()%>" target='_blank'><i class="ion ion-logo-linkedin"></i></a></li><% } %>
                                                                            <% if(info.getLink5() != null && !info.getLink5().equals("")) {%><li><a href="<%=info.getLink5()%>" target='_blank'><i class="ion ion-md-link"></i></a></li><% } %>
                                                                            <% if(info.getLink6() != null && !info.getLink6().equals("")) {%><li><a href="javascript: showlink('6');"><i class="ion ion-md-add"></i></a></li><% } %>
                                                                        </ul>
                                                                    </div>
                                                                    <div id='linkid6' style="display:none;" class="media_view">
                                                                        <%=info.getLink6() != null && !info.getLink6().equals("") ? client.setLink(info.getLink6()) : "&nbsp;" %>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Client Co-ordinator from OCS</label>
                                                                    <span class="form-control"><%= info.getUserName() != null && !info.getUserName().equals("") ? info.getUserName() : "&nbsp;" %></span>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Client Manager</label>
                                                                    <span class="form-control"><%= info.getMids() != null && !info.getMids().equals("") ? info.getMids() : "&nbsp;" %></span>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Client Recruiter from OCS</label>
                                                                    <span class="form-control"><%= info.getRids() != null && !info.getRids().equals("") ? info.getRids() : "&nbsp;" %></span>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Short Name</label>
                                                                    <span class="form-control"><%= info.getShortName() != null && !info.getShortName().equals("") ? info.getShortName() : "&nbsp;" %></span>
                                                                </div>
                                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                    <label class="form_label">Color Code</label>
                                                                    <span class="form-control"><%= info.getColorCode() != null && !info.getColorCode().equals("") ? info.getColorCode() : "&nbsp;" %></span>
                                                                </div>
                                                                <% } %>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="tab-pane <% if(tabno.equals("2")) {%>active<% } %>" id="tab2" role="tabpanel">
                                                        <div class="row">
                                                            <div class="main-heading m_30">
                                                                <div class="add-btn">
                                                                    <h4>ASSET DETAILS</h4>
                                                                </div>
                                                                <div class="pull_right float-end">
                                                                    <% if (addper.equals("Y")) {%><a href="javascript: modifyAssetForm('-1');" class="add_btn"><i class="mdi mdi-plus"></i></a><%}%>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="row">
                                                            <% if(!message.equals("")) {%><div class="alert <%=clsmessage%> alert-dismissible fade show">
                                                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button><%=message%>
                                                            </div><% } %>
                                                        </div>
                                                        <div class="row">
                                                            <html:hidden property="clientassetId" />
                                                            <html:hidden property="status" />
                                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                                <div class="table-rep-plugin sort_table">
                                                                    <div class="table-responsive mb-0">
                                                                        <table class="table table-striped">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th width="7%"><b>ID</b>
                                                                                    <a href="javascript: sortFormCA('1', '2');" id="img_1_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                                    <a href="javascript: sortFormCA('1', '1');" id="img_1_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                                    </th>
                                                                                    <th width="13%"><b>Type</b>
                                                                                    <a href="javascript: sortFormCA('2', '2');" id="img_2_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                                    <a href="javascript: sortFormCA('2', '1');" id="img_2_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                                    </th>
                                                                                    <th width="27%"><span><b>Asset Name</b></span>
                                                                                    <a href="javascript: sortFormCA('3', '2');" id="img_3_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                                    <a href="javascript: sortFormCA('3', '1');" id="img_3_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a></th>
                                                                                    <th width="15%"><span><b>Location</b></span>
                                                                                    <a href="javascript: sortFormCA('4', '2');" id="img_4_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                                    <a href="javascript: sortFormCA('4', '1');" id="img_4_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                                    </th>
                                                                                    <th width="13%" class ="text-center"><span><b>Department</b></span>
                                                                                        <a href="javascript: sortFormCA('5', '2');" id="img_5_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                                        <a href="javascript: sortFormCA('5', '1');" id="img_5_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                                    </th> 
                                                                                    <th width="10%" class ="text-center"><span><b>Positions</b></span>
                                                                                        <a href="javascript: sortFormCA('6', '2');" id="img_6_2" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-up"></i></a>
                                                                                        <a href="javascript: sortFormCA('6', '1');" id="img_6_1" class="sort_arrow deactive_sort"><i class="ion ion-ios-arrow-down"></i></a>
                                                                                    </th>                                                                          
                                                                                    <th width="15%" class ="text-right"><span><b>Action</b></span></th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody id = "sortca_id">
<%
                                                                        if(asset_list_size > 0)
                                                                        {
                                                                            ClientInfo ainfo;
                                                                            for(int i = 0; i < asset_list_size; i++)
                                                                            {
                                                                                ainfo = (ClientInfo) asset_list.get(i);
                                                                                if(ainfo != null)
                                                                                {
%>
                                                                                <tr>
                                                                                    <td><%= client.changeNum(ainfo.getClientassetId(),3) %></td>
                                                                                    <td><%= ainfo.getAssettypeName() != null ? ainfo.getAssettypeName() : "" %></td>
                                                                                    <td><%= ainfo.getName() != null ? ainfo.getName() : "" %></td>
                                                                                    <td><%= ainfo.getCountryName() != null ? ainfo.getCountryName() : "" %></td>
                                                                                    <td class="assets_list text-center"><a href="javascript:;" onclick ="javascript: setdeptmodel('<%=ainfo.getClientassetId()%>','<%=ainfo.getAssettypeId()%>', '<%=(i+1)%>');" data-bs-toggle="modal" data-bs-target="#department_modal"><span id="deptspan_<%=(i+1)%>"><%= client.changeNum(ainfo.getDeptcount(), 2)%></span></a></td>
                                                                                    <td class="assets_list text-center"><a href="javascript:;" onclick ="javascript: setpositionmodel('<%=ainfo.getClientassetId()%>','<%=ainfo.getAssettypeId()%>');" data-bs-toggle="modal" data-bs-target="#client_position" ><%= client.changeNum(ainfo.getPositionCount(), 2)%></a></td>
                                                                                    <td class="action_column">
                                                                                        <a class="mr_15" href="javascript:;" onclick="javascript: viewimg('<%=ainfo.getClientassetId()%>');"  data-bs-toggle="modal" data-bs-target="#view_resume_list"><img src="../assets/images/attachment.png"></a>
                                                                                        <% if (editper.equals("Y") && ainfo.getStatus() == 1) {%><a href="javascript: modifyAssetForm('<%= ainfo.getClientassetId()%>');" class="mr_15"><img src="../assets/images/pencil.png"/></a><% } else {%><a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a><%}%>
                                                                                        <span class="switch_bth">
                                                                                            <div class="form-check form-switch">
                                                                                               <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault_<%=(i)%>" <% if(ainfo.getStatus() == 1){%>checked<% }%> <% if(!editper.equals("Y")) {%>disabled="true"<% } %> onclick="javascript: deleteAssetForm('<%= ainfo.getClientassetId()%>', '<%= ainfo.getStatus()%>','<%=i%>');"/>
                                                                                            </div>
                                                                                        </span>
                                                                                    </td>
                                                                                </tr>
<%
                                                                                }
                                                                            }
                                                                        }
                                                                        else
                                                                        {
%>
                                                                                <tr><td colspan='7'>No assets available.</td></tr>
<%
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
                        <!-- End Page-content -->
                    </div>
                    <!-- end main content-->
                </div>
            </div>
            <!-- END layout-wrapper -->
            <%@include file="../footer.jsp" %>
            <div id="view_resume_list" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                            <div class="row" id="viewfilesdiv">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="view_resume" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                    <div class="modal-content">
                        <div class="modal-header">
                            <!-- <h4 class="modal-title">View File</h4> -->
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-5">
                                    <form action="../assets/drop/upload.php" class="dropzone dropzone-file-area" id="my-dropzone">
                                        <h3 class="sbold">Drop files here or click to upload</h3>
                                    </form>
                                </div>
                                <div class="col-lg-7">
                                    <iframe  class="doc" src="../assets/pdf/1.pdf"></iframe>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            
            <div id="client_position" class="modal fade client_position" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12" id = "positionmodel">
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>           
            
            <div id="department_modal" class="modal fade client_position parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button onclick="javascript: setdeptcount();" type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12" id="deptmodal">
                                    
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="client_position_department" class="modal fade client_position parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content" id="positiondeptid">
                        
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
                                    <h3>Client Added</h3>
                                    <p>Go ahead and add assets under this client!</p>
                                    <a href="javascript: modifyAssetForm('-1');" class="msg_button" style="text-decoration: underline;">Add Asset</a>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            
            <script src="../assets/drop/jquery.min.js"></script>	
            <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
            <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
            <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
            <script src="../assets/drop/dropzone.min.js" type="text/javascript"></script>  
            <script src="../assets/drop/app.min.js" type="text/javascript"></script>
            <script src="../assets/js/app.js"></script>	
            <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script> 
            <script src="../assets/drop/form-dropzone.min.js" type="text/javascript"></script>
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
                $(window).on('load', function() {
                    $('#thank_you_modal').modal('show');
                });
            </script>
            <%}%>
            
            <script>
            $("#iframe").on("load", function() {
              let head = $("#iframe").contents().find("head");
              let css = '<style>img{margin: 0px auto;}</style>';
              $(head).append(css);
            });
            </script>
            <script>

                                                                                                        Dropzone.autoDiscover = false;
                                                                                                        var val = "";
                                                                                                        $("#my-dropzone").dropzone({
                                                                                                            addRemoveLinks: true,
                                                                                                            maxFiles: 50,
                                                                                                            parallelUploads: 50,
                                                                                                            createImageThumbnails: !0,
                                                                                                            acceptedFiles: ".png,.jpg,.jpeg,.pdf",
                                                                                                            init: function () {
                                                                                                                this.on("addedfile", function (file) {
                                                                                                                    if (file.name != "")
                                                                                                                    {
                                                                                                                        if (!(file.name).match(/(\.(pdf)|(jpg)|(jpeg)|(png))$/i))
                                                                                                                        {
                                                                                                                            alert("Only .pdf, .jpeg, .jpg, .png are allowed.");
                                                                                                                            return false;
                                                                                                                        }
                                                                                                                    }
                                                                                                                    var n = Dropzone.createElement("<a href='javascript:' class='trash_drop_list'><img src='../assets/images/trash.png'/></a>"),
                                                                                                                            t = this;
                                                                                                                    n.addEventListener("click", function (n) {
                                                                                                                        n.preventDefault(),
                                                                                                                                n.stopPropagation(),
                                                                                                                                t.removeFile(file)
                                                                                                                    }),
                                                                                                                            file.previewElement.appendChild(n)

                                                                                                                    var reader = new FileReader();
                                                                                                                    reader.onload = function (event) {
                                                                                                                        var base64String = event.target.result;
                                                                                                                        if (val == "")
                                                                                                                        {
                                                                                                                            val += base64String;
                                                                                                                        } else
                                                                                                                        {
                                                                                                                            val += ("@#@" + base64String);
                                                                                                                        }
                                                                                                                        document.clientForm.fname.value = val;
                                                                                                                    };
                                                                                                                    reader.readAsDataURL(file);

                                                                                                                });
                                                                                                                this.on("removedfile", function (e)
                                                                                                                {
                                                                                                                    var reader = new FileReader();
                                                                                                                    reader.onload = function (event) {
                                                                                                                        var base64String = event.target.result;
                                                                                                                        var val1 = document.clientForm.fname.value;
                                                                                                                        var fval = val1.replace("@#@" + base64String + "@#@", "");
                                                                                                                        fval = fval.replace("@#@" + base64String, "");
                                                                                                                        fval = fval.replace(base64String + "@#@", "");
                                                                                                                        fval = fval.replace(base64String, "");
                                                                                                                        document.clientForm.fname.value = fval;
                                                                                                                    };
                                                                                                                    reader.readAsDataURL(e);
                                                                                                                }
                                                                                                                );

                                                                                                            },
                                                                                                            dictDefaultMessage: '<span class="text-center"><span class="font-lg visible-xs-block visible-sm-block visible-lg-block"><span class="font-lg"><i class="fa fa-caret-right text-danger"></i> Drop files <span class="font-xs">to upload</span></span><span>&nbsp&nbsp<h4 class="display-inline"> (Or Click)</h4></span>',
                                                                                                            dictResponseError: 'Error uploading file!'
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
