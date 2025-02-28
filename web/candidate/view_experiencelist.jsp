<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.candidate.CandidateInfo" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="candidate" class="com.web.jxp.candidate.Candidate" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 2, submtp = 7,ctp = 5;
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

            String file_path = candidate.getMainPath("view_candidate_file");
                ArrayList exp_list = new ArrayList();
            if(request.getAttribute("CANDEXPERIENCELIST") != null)
                exp_list = (ArrayList) request.getAttribute("CANDEXPERIENCELIST");
            int exp_list_size = exp_list.size();

            int ipass=0;
            if(session.getAttribute("PASS") != null)
                ipass = Integer.parseInt((String)session.getAttribute("PASS"));
    %>
    <head>
        <meta charset="utf-8">
        <title><%= candidate.getMainPath("title") != null ? candidate.getMainPath("title") : "" %></title>
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
        <script type="text/javascript" src="../jsnew/candidate.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/candidate/CandidateAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
            <html:hidden property="candidateId"/>
            <html:hidden property="doSave"/>
            <html:hidden property="doModify"/>
            <html:hidden property="doCancel"/>
            <html:hidden property="search"/>
            <html:hidden property="doView"/>
            <html:hidden property="doViewBanklist"/>
            <html:hidden property="doViewlangdetail"/>
            <html:hidden property="doViewvaccinationlist"/>
            <html:hidden property="doViewgovdocumentlist"/>
            <html:hidden property="doViewtrainingcertlist"/>
            <html:hidden property="doVieweducationlist"/>
            <html:hidden property="doViewexperiencelist"/>
            <html:hidden property="doViewhealthdetail"/>
            <html:hidden property="doViewNomineelist"/>
            <html:hidden property="doaddexperiencedetail"/>
            <html:hidden property="doDeleteexperiencedetail"/>
            <html:hidden property="experiencedetailId"/>
            <html:hidden property="status" />
            <html:hidden property="statustype"/>
            <html:hidden property="onlineFlag"/>
            <html:hidden property="assettypeIdIndex"/>
            <html:hidden property="positionIdIndex"/>
            <html:hidden property="courseIndex"/>
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
                                        Enroll Candidate
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
                                                <li><a href="javascript:;"><img src="../assets/images/print-screen.png"/> Print Screen</a></li>
                                                <li><a href="javascript:;"><img src="../assets/images/export-data.png"/> Export Data</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 col-xl-12 tab_head_area">
                                <%@include file ="../candidatetab.jsp"%>
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
                                                <div class="tab-content">
                                                    <div class="tab-pane active">
                                                        <div class="m_30">
                                                            <div class="row">
                                                                <div class="main-heading">
                                                                    <div class="add-btn">
                                                                        <h4>WORK EXPERIENCE DETAILS</h4>
                                                                    </div>
                                                                    <div class="pull_right float-end">
                                                                        <%
                                                                  if(ipass ==2){}else{
                                                                    if(addper.equals("Y")) {%> <a href="javascript: modifyexperienceForm(-1);" class="add_btn"><i class="mdi mdi-plus"></i></a> <% } }%>
                                                                    </div>
                                                                </div>
                                                            </div>	
                                                            <div class="col-lg-12">
                                                                <div class="table-responsive table-detail">
                                                                    <table class="table table-striped mb-0">
                                                                        <thead>
                                                                            <tr>
                                                                                <th width="12%">Position</th>
                                                                                <th width="15%">Department</th>
                                                                                <th width="20%">Company Name</th>
                                                                                <th width="20%">Asset Name</th>
                                                                                <th width="10%" class="text-center">Start Date</th>
                                                                                <th width="10%" class="text-center">End Date</th>																			
                                                                                <th width="13%" class="text-right">Actions</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                                                            <%
                                                                                    if(exp_list_size > 0)
                                                                                    {
                                                                                        CandidateInfo ainfo;
                                                                                        String filename1;
                                                                                        for(int i = 0; i < exp_list_size; i++)
                                                                                        {
                                                                                            ainfo = (CandidateInfo) exp_list.get(i);
                                                                                            if(ainfo != null)
                                                                                            {

                                                                            %>
                                                                            <tr>
                                                                                <td><%= ainfo.getPosition() != null ? ainfo.getPosition() : "" %></td>
                                                                                <td><%= ainfo.getDepartment() != null ? ainfo.getDepartment() : "" %></td>
                                                                                <td><%= ainfo.getCompanyname() != null ? ainfo.getCompanyname() : "" %></td>
                                                                                <td><%= ainfo.getAssetName() != null ? ainfo.getAssetName() : "" %></td>
                                                                                <td class="text-center"><%= ainfo.getWorkstartdate() != null ? ainfo.getWorkstartdate() : "" %></td>
                                                                                <td class="text-center"><%= ainfo.getWorkenddate() != null && !ainfo.getWorkenddate().equals("") ? ainfo.getWorkenddate() : "" %></td>
                                                                                <td class="action_column">                                                                                    
                                                                                    <% if(!ainfo.getExperiencefilename().equals("") ||!ainfo.getWorkfilename().equals("") ) {%><a href="javascript:;" onclick="javascript: viewworkexpfiles('<%=ainfo.getExperiencefilename()%>', '<%=ainfo.getWorkfilename()%>');" class="mr_15" data-bs-toggle="modal" data-bs-target="#view_resume_list"><img src="../assets/images/attachment.png"/></a><% } else {%><a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a><%}%>
                                                                                        <%
                                                                                        if(ainfo.getPassflag() ==2){}else{
                                                                                    
                                                                                            if (editper.equals("Y") && ainfo.getStatus() == 1) {%><a href="javascript: modifyexperienceForm('<%= ainfo.getExperiencedetailId()%>');" class="mr_15"><img src="../assets/images/pencil.png"/></a><% } else {%><a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a><%}%>
                                                                                        <%}%>                                                                                    
                                                                                    <span class="switch_bth">
                                                                                        <div class="form-check form-switch">
                                                                                            <input class="form-check-input" type="checkbox" role="switch" <% if(ainfo.getPassflag() ==2){%>disabled<%}%> id="flexSwitchCheckDefault_<%=(i)%>" <% if( ainfo.getStatus() == 1){%> checked <% }%> <% if(!editper.equals("Y")) {%> disabled="true" <% } %>  onclick="javascript: deleteexperienceForm('<%= ainfo.getExperiencedetailId()%>', '<%= ainfo.getStatus()%>');"/>
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
                                                                            <tr><td colspan='7'>No experience details available.</td></tr>
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
                        <%@include file ="../footer.jsp"%>
                        <div id="view_resume_list" class="modal fade view_resume_list" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                                    </div>
                                    <div class="modal-body">
                                        <div><a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen r_f_screen">Full Screen</a></div>
                                        <div class="row" id="viewexpdiv">
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
                        <script type="text/javascript">
                            function addLoadEvent(func) {
                            var oldonload = window.onload;
                            if (typeof window.onload != 'function') {
                            window.onload = func;
                            } else {
                            window.onload = function () {
                            if (oldonload) {
                            oldonload();
                            }
                            }
                            }
                            }
                            addLoadEvent(setsidemenu('5'));
                        </script>
                        <script>
                            $("#iframe").on("load", function() {
                            let head = $("#iframe").contents().find("head");
                            let css = '<style>img{margin: 0px auto;}</style>';
                            $(head).append(css);
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
