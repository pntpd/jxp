<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 2, submtp = 4, ctp = 11;
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
            String file_path = talentpool.getMainPath("view_candidate_file");
            TalentpoolInfo tpinfo = null;
             if (request.getAttribute("VERIFICATIONINFO") != null){
             tpinfo = (TalentpoolInfo) request.getAttribute("VERIFICATIONINFO");
            }
                ArrayList varify_list = new ArrayList();
            if(request.getAttribute("VERIFICATIONLIST") != null)
                varify_list = (ArrayList) request.getAttribute("VERIFICATIONLIST");
            int varify_list_size = varify_list.size();
    %>
    <head>
        <meta charset="utf-8">
        <title><%= talentpool.getMainPath("title") != null ? talentpool.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/talentpool.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/talentpool/TalentpoolAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
            <div id="layout-wrapper">
                <%@include file ="../header.jsp"%>
                <%@include file ="../sidemenu.jsp"%>
                <div class="main-content">
                    <div class="page-content tab_panel">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12">
                                <div class="float-start">
                                    <a href="javascript:goback();" class="back_arrow">
                                        <img  src="../assets/images/back-arrow.png"/> 
                                        <%@include file ="../talentpool_title.jsp"%>
                                    </a>
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
                                            <li><a href="javascript: openinnewtab();"><img src="../assets/images/open-in-new-tab.png"/> Open in New Tab</a></li>
                                            <li><a href="javascript: printPage('printArea');"><img src="../assets/images/print-screen.png"/> Print Screen</a></li>
                                            <li><a href="javascript: exporttoexcelnew('11');"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>	
                            <div class="col-md-12 col-xl-12 tab_head_area">
                                <%@include file ="../talentpooltab.jsp"%>
                            </div>
                        </div>
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-md-12 col-xl-12">
                                    <div class="body-background tab2_body">
                                        <div class="row d-none1">
                                            <div class="col-lg-12">
                                                <div class="tab-content">
                                                    <div class="tab-pane active">
                                                        <div class="m_30">
                                                            <div class="row">
                                                                <div class="main-heading">
                                                                    <div class="add-btn">
                                                                        <h4>VERIFICATION HISTORY</h4>
                                                                    </div>
<%--                                                                    <div class="edit_btn pull_right float-end ver_shiled_area">
                                                                        <a data-bs-toggle="dropdown" aria-expanded="false" href="javascript:;" class="add_btn dropdown-toggle"><img src="../assets/images/veri_shield.png"></a>
                                                                        <div class="dropdown-menu" style="">
                                                                            <a class="dropdown-item <%if (tpinfo.getCflag1() == 1 ){%>pending_text <%}else if(tpinfo.getCflag1() == 2){%>approve_text<%}else {%>default_text<%}%>" href="javascript:;">Personal</a>
                                                                            <a class="dropdown-item <%if (tpinfo.getCflag2() == 1 ){%>pending_text <%}else if(tpinfo.getCflag2() == 2){%>approve_text<%}else {%>default_text<%}%>" href="javascript:;">Languages</a>
                                                                            <a class="dropdown-item <%if (tpinfo.getCflag3() == 1 ){%>pending_text <%}else if(tpinfo.getCflag3() == 2){%>approve_text<%}else {%>default_text<%}%>" href="javascript:;">Health</a>
                                                                            <a class="dropdown-item <%if (tpinfo.getCflag4() == 1 ){%>pending_text <%}else if(tpinfo.getCflag4() == 2){%>approve_text<%}else {%>default_text<%}%>" href="javascript:;">Vaccination</a>
                                                                            <a class="dropdown-item <%if (tpinfo.getCflag5() == 1 ){%>pending_text <%}else if(tpinfo.getCflag5() == 2){%>approve_text<%}else {%>default_text<%}%>" href="javascript:;">Experience</a>
                                                                            <a class="dropdown-item <%if (tpinfo.getCflag6() == 1 ){%>pending_text <%}else if(tpinfo.getCflag6() == 2){%>approve_text<%}else {%>default_text<%}%>" href="javascript:;">Education</a>
                                                                            <a class="dropdown-item <%if (tpinfo.getCflag7() == 1 ){%>pending_text <%}else if(tpinfo.getCflag7() == 2){%>approve_text<%}else {%>default_text<%}%>" href="javascript:;">Certifications</a>
                                                                            
                                                                            <a class="dropdown-item <%if (tpinfo.getCflag9() == 1 ){%>pending_text <%}else if(tpinfo.getCflag9() == 2){%>approve_text<%}else {%>default_text<%}%>" href="javascript:;">Bank Details</a>
                                                                            <a class="dropdown-item <%if (tpinfo.getCflag10() == 1 ){%>pending_text <%}else if(tpinfo.getCflag10() == 2){%>approve_text<%}else {%>default_text<%}%>" href="javascript:;">Documents</a>
                                                                        </div>
                                                                    </div>--%>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-12 all_client_sec" id="all_client">
                                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group" id="printArea">
                                                                    <div class="table-responsive table-detail">
                                                                        <table class="table table-striped mb-0">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th width="10%">Date</th>
                                                                                    <th width="8%">Time</th>
                                                                                    <th width="15%">Username</th>
                                                                                    <th width="24%">Tab - Particulars</th>
                                                                                    <th width="20%">Authority</th>
                                                                                    <th width="15%">Status</th>
                                                                                    <th width="8%" class="text-right">Actions</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <%
                                                                                                                                                        if(varify_list_size > 0)
                                                                                                                                                        {
                                                                                                                                                            TalentpoolInfo ainfo;
                                                                                                                                                            for(int i = 0; i < varify_list_size; i++)
                                                                                                                                                            {
                                                                                                                                                                ainfo = (TalentpoolInfo) varify_list.get(i);
                                                                                                                                                                if(ainfo != null)
                                                                                                                                                                {
                                                                                %>
                                                                                <tr>
                                                                                    <td><%= ainfo.getDate() != null && !ainfo.getDate().equals("") ? ainfo.getDate() : "" %></td>
                                                                                    <td><%= ainfo.getTime() != null && !ainfo.getTime().equals("") ? ainfo.getTime() : "" %></td>
                                                                                    <td><%= ainfo.getName() != null && !ainfo.getName().equals("") ? ainfo.getName() : "" %></td>
                                                                                    <td><%= ainfo.getTabName() != null && !ainfo.getTabName().equals("") ? ainfo.getTabName() : "" %></td>
                                                                                    <td><%= ainfo.getAuthority() != null && !ainfo.getAuthority().equals("") ? ainfo.getAuthority() : "" %></td>
                                                                                    <td><%=(ainfo.getStatus() > 0 ? talentpool.getStatusById(ainfo.getStatus()) : "")%></td>
                                                                                    <td class="action_column">              
                                                                                        <% if(!ainfo.getFilename().equals("")) {%><a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%=file_path+ainfo.getFilename() %>');"><img src="../assets/images/attachment.png"/> </a><% } else { %><a href='javascript:;'><span style='width: 35px;'>&nbsp;</span></a><% } %>
                                                                                    </td>
                                                                                </tr>    
                                                                                <%
                                                                                        }
                                                                                    }
                                                                                }
                                                                                else
                                                                                {
                                                                                %>
                                                                                <tr><td colspan='7'>No verification details available.</td></tr>
                                                                                <%
                                                                                }
                                                                                %>
                                                                            </tbody>
                                                                        </table>
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
                    </div>
                </div>
            </div>
            <%@include file ="../footer.jsp"%>
            <div id="view_pdf" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                            <span class="resume_title">File</span>
                            <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                            <a id='diframe' href="" class="down_btn" download=""><img src="../assets/images/download.png"/></a>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <iframe id='iframe' class="doc" src=""></iframe>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
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
                $("#iframe").on("load", function () {
                    let head = $("#iframe").contents().find("head");
                    let css = '<style>img{margin: 0px auto;}</style>';
                    $(head).append(css);
                });
            </script>
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
                        orientation: "bottom"
                    });
                });
            </script>
            <script>
                $(function () {
                    $("#upload_link1").on('click', function (e) {
                        e.preventDefault();
                        $("#upload1:hidden").trigger('click');
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