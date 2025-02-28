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
            int mtp = 2, submtp = 4, ctp = 9;
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
            ArrayList list = new ArrayList();
            if(request.getSession().getAttribute("MODULEPER_LIST") != null)
                list = (ArrayList)request.getSession().getAttribute("MODULEPER_LIST");
            String file_path = talentpool.getMainPath("view_candidate_file");
                ArrayList bank_list = new ArrayList();
            if(request.getAttribute("CANDBANKLIST") != null)
                bank_list = (ArrayList) request.getAttribute("CANDBANKLIST");
            int bank_list_size = bank_list.size();

            TalentpoolInfo dinfo = null;
            int dstatus = 0;
            if (session.getAttribute("CANDIDATE_DETAIL") != null) {
                dinfo = (TalentpoolInfo) session.getAttribute("CANDIDATE_DETAIL");
                dstatus = dinfo.getStatus();
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
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/talentpool/TalentpoolAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="doManageBankdetail"/>
        <html:hidden property="doSaveBankdetail"/>
        <html:hidden property="doDeleteBankdetail"/>
        <html:hidden property="bankdetailId"/>
        <html:hidden property="bankfilehidden" />
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
                                            <li><a href="javascript: exporttoexcelnew('9');"><img src="../assets/images/export-data.png"/> Export Data</a></li>
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
                                                    <h4>BANKS</h4>
                                                </div>
                                                <div class="pull_right float-end">
                                                    <% if(addper.equals("Y") && dstatus != 4) {%><a href="javascript: modifybankdetailForm(-1);" class="add_btn mr_25"><i class="mdi mdi-plus"></i></a><% } %>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-12" id="printArea">
                                            <div class="table-responsive table-detail">
                                                <table class="table table-striped mb-0">
                                                    <thead>
                                                        <tr>
                                                            <th width="%">Bank Name</th>
                                                            <th width="%">Account Type</th>
                                                            <th width="%">Branch</th>
                                                            <th width="%">IFSC</th>
                                                            <th width="%">Primary</th>
                                                            <th width="%" class="text-right">Actions</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%
                                                            if(bank_list_size > 0)
                                                            {
                                                                TalentpoolInfo ainfo;
                                                                String filename1;
                                                                for(int i = 0; i < bank_list_size; i++)
                                                                {
                                                                    ainfo = (TalentpoolInfo) bank_list.get(i);
                                                                    if(ainfo != null)
                                                                    {
                                                                        filename1 = ainfo.getBkFilename() != null ? ainfo.getBkFilename() : "";
                                                        %>
                                                        <tr>
                                                            <td><%= ainfo.getBankName() != null ? ainfo.getBankName() : "" %></td>
                                                            <td><%= ainfo.getAccountTypename() != null ? ainfo.getAccountTypename() : "" %></td>
                                                            <td><%= ainfo.getBranch() != null ? ainfo.getBranch() : "" %></td>
                                                            <td><%= ainfo.getIFSCCode() != null ? ainfo.getIFSCCode() : "" %></td>
                                                            <% if( ainfo.getPrimarybankId() == 1) {%><td class="primary_active"><i class="fa fa-check" aria-hidden="true"></i></td><% } else {%><td></td><% } %>
                                                            <td class="action_column">
                                                                <% if(!ainfo.getBkFilename().equals("")) {%><a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%=file_path+ainfo.getBkFilename() %>');"><img src="../assets/images/attachment.png"/> </a><% }  else {%><a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a><%}%>
                                                                <% if (editper.equals("Y") && ainfo.getStatus() == 1 && dstatus != 4){%><a href="javascript: modifybankdetailForm('<%= ainfo.getBankdetid()%>');" class=" mr_15"><img src="../assets/images/pencil.png"/></a><% } else {%><a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a><%}%>

                                                                <span class="switch_bth">  
                                                                    <% if (deleteper.equals("Y") && dstatus != 4) {%>
                                                                    <div class="form-check form-switch">
                                                                        <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault_<%=(i)%>" <% if( ainfo.getStatus() == 1){%>checked<% }%> onclick="javascript: deletebankForm('<%= ainfo.getBankdetid()%>', '<%= ainfo.getStatus()%>');"/>
                                                                    </div>
                                                                    <% } %>
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
                                                        <tr><td colspan='6'>No bank details available.</td></tr>
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
    <%@include file ="../footer.jsp"%>
    <div id="view_pdf" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    <span class="resume_title"> File</span>
                        <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                        <a id='diframe' href="" class="down_btn" download=""><img src="../assets/images/download.png"/></a>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <center> <iframe id='iframe' class="doc" src=""></iframe></center>
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