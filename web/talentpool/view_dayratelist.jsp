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
            int mtp = 2, submtp = 4, ctp = 28;
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

            ArrayList alist = new ArrayList();
            if(request.getSession().getAttribute("MODULEPER_LIST") != null)
                alist = (ArrayList)request.getSession().getAttribute("MODULEPER_LIST");
            String file_path = talentpool.getMainPath("view_candidate_file");
                
            ArrayList list = new ArrayList();
            if(request.getAttribute("DAYRATELIST") != null)
                list = (ArrayList) request.getAttribute("DAYRATELIST");
            int list_size = list.size();
            
            TalentpoolInfo dinfo = null;
            int dstatus = 0, pid1 = 0, pid2= 0;
            if (session.getAttribute("CANDIDATE_DETAIL") != null) {
                dinfo = (TalentpoolInfo) session.getAttribute("CANDIDATE_DETAIL");
                dstatus = dinfo.getStatus();
                pid1 = dinfo.getPositionId();
                pid2 = dinfo.getPositionId2();
            }
            int dayrateId2 = 0;
            if (request.getAttribute("DAYRATE_ID") != null) {
                dayrateId2 = Integer.parseInt((String) request.getAttribute("DAYRATE_ID"));
            }
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
        <script type="text/javascript" src="../jsnew/ddl.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/talentpool/TalentpoolAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="doAddDayratedetail"/>
        <html:hidden property="doSaveDayrate"/>
        <html:hidden property="dayrateId"/>
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
                                            <li><a href="javascript: exporttoexcelnew('29');"><img src="../assets/images/export-data.png"/> Export Data</a></li>
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
                                <div class="body-background">
                                    <div class="row d-none1">

                                        <div class="col-lg-12">
                                            <% if(!message.equals("")) {%><div class="alert <%=clsmessage%> alert-dismissible fade show">
                                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button><%=message%>
                                            </div><% } %>
                                            <div class="main-heading">
                                                <div class="add-btn">
                                                    <h4>DAY RATE DETAILS</h4>
                                                </div>
                                                <div class="pull_right float-end">
                                                    <% if(addper.equals("Y") && dstatus != 4 && dinfo.getClientId() > 0) {%> 
                                                    <a href="javascript: modifyDayrateForm(-1);" class="add_btn mr_25"><i class="mdi mdi-plus"></i> </a>
                                                    <% } %>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-lg-12 all_client_sec" id="all_client">
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                <div class="table-responsive table-detail">
                                                    <table class="table table-striped mb-0">
                                                        <thead>
                                                            <tr>
                                                                <th width="30%">Position | Rank</th>
                                                                <th width="15%">From Date</th>
                                                                <th width="15%">To Date</th>
                                                                <th width="10%">Day Rate</th>
                                                                <th width="10%">Overtime</th>
                                                                <th width="10%">Allowance</th>
                                                                <th width="10%" class="text-right">Action</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
<%
                                                        if (list_size > 0) 
                                                        {
                                                            TalentpoolInfo info;
                                                            int cc1= 0, cc2= 0;
                                                            for (int i = 0; i < list_size; i++) 
                                                            {
                                                                info = (TalentpoolInfo) list.get(i);
                                                                if (info != null) 
                                                                {
                                                                    
%>                                                          
                                                                    <tr>
                                                                        <td><%= info.getPosition() != null ? info.getPosition() : ""%></td>
                                                                        <td><%= info.getFromDate() != null ? info.getFromDate() : ""%></td>
                                                                        <td><%= info.getToDate() != null && !info.getToDate().equals("") ? info.getToDate() : ""%></td>
                                                                        <td><%= info.getRate1()%></td>
                                                                        <td><%= info.getRate2()%></td>
                                                                        <td><%= info.getRate3()%></td>
                                                                        <td class="text-right action_column">                                                                            
<% 
                                                                        //if (editper.equals("Y") && ((pid1 == info.getPositionId() && cc1 ==0) || (pid2 == info.getPositionId() && cc2 ==0))) 
                                                                        if (editper.equals("Y")) 
                                                                        {
                                                                            /*if (info.getPositionId() == pid1) 
                                                                                cc1++;
                                                                            if (info.getPositionId() == pid2) 
                                                                                cc2++;*/
%>
                                                                                <%if(i== 0){%>
                                                                                    <a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#client_position" onclick="javascript: modifyDayrate('<%= info.getDayrateId()%>', '<%=dayrateId2%>', '1');" ><img src="../assets/images/pencil.png"/> </a>
                                                                                <%}else{%> 
                                                                                    <a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#client_position" onclick="javascript: modifyDayrate('<%= info.getDayrateId()%>', '<%=dayrateId2%>', '2');" ><img src="../assets/images/pencil.png"/> </a>
                                                                                <%}%>        
                                                                                        
                                                                                <a><img src="../assets/images/toggle.png" class="active_toggle"/> </a>
                                                                         
                                                                            <% }else{ %>
                                                                            <div class="switch_bth float-end tt">
                                                                                <div class="form-check form-switch">
                                                                                    <input class="form-check-input" type="checkbox" disabled="true"/>
                                                                                </div>
                                                                            </div>
                                                                        <%}%>
                                                                        </td>
                                                                    </tr>    
                                                            <%
                                                                    }
                                                                }
                                                            } else {
                                                            %>
                                                            <tr><td colspan='8'>No day rates details available.</td></tr>
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
        </div>
        <%@include file ="../footer.jsp"%>
        
        <div id="client_position" class="modal fade parameter_modal" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        </div>
                        <div class="modal-body" id="edit_div">

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