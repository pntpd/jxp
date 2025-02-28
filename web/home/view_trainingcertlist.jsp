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
            int mtp = 2, submtp = 7,ctp = 7;
            if (session.getAttribute("HOME_EMAILID") == null)
        {
%>
    <jsp:forward page="/homeindex1.jsp"/>
<%
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

                ArrayList trainingcert_list = new ArrayList();
            if(request.getAttribute("CANDTRAININGCERTLIST") != null)
                trainingcert_list = (ArrayList) request.getAttribute("CANDTRAININGCERTLIST");
            int trainingcert_list_size = trainingcert_list.size();

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
        <link href="https://fonts.googleapis.com/css2?family=Rubik:wght@300;400;500;600&display=swap" rel="stylesheet">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/home.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed registration_page">
    <html:form action="/home/HomeAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="candidateId"/>
        <html:hidden property="doSave"/>
        <html:hidden property="doModify"/>
        <html:hidden property="doCancel"/>
        <html:hidden property="doView"/>
        <html:hidden property="doViewBanklist"/>
        <html:hidden property="doViewlangdetail"/>
        <html:hidden property="doViewvaccinationlist"/>
        <html:hidden property="doViewgovdocumentlist"/>
        <html:hidden property="doViewhitchlist"/>
        <html:hidden property="doViewtrainingcertlist"/>
        <html:hidden property="doVieweducationlist"/>
        <html:hidden property="doViewexperiencelist"/>
        <html:hidden property="doViewhealthdetail"/>
        <html:hidden property="doaddtrainingcertdetail"/>
        <html:hidden property="doDeletetrainingcertdetail"/>
        <html:hidden property="trainingandcertId"/>
        <html:hidden property="status" />
        <div id="layout-wrapper">
            <%@include file ="../homeheader.jsp"%>
            <div class="main-content">
                <div class="page-content tab_panel">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12 tab_head_area">
                            <%@include file ="../homecandidatetab.jsp"%>
                        </div>
                    </div>
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background">
                                    <div class="row d-none1">                           
                                        <div class="col-lg-12">
                                            <div class="tab-content">
                                                <div class="tab-pane active">
                                                    <div class="m_30 mt_30">
                                                        <div class="row">
                                                            <% if(!message.equals("")) {%><div class="alert <%=clsmessage%> alert-dismissible fade show">
                                                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button><%=message%>
                                                            </div><% } %>
                                                            <div class="main-heading">
                                                                <div class="add-btn">
                                                                    <h4>TRAINING, CERTIFICATION & SAFETY COURSES</h4>
                                                                </div>
                                                                <div class="pull_right float-end">
                                                                    <%
                                                                    if(ipass ==2){}else{
                                                                    %>
                                                                    <a href="javascript: modifytrainingcertdetailForm(-1);" class="add_btn"><i class="mdi mdi-plus"></i></a><% }%>
                                                                </div>
                                                            </div>
                                                        </div>
                                                            <div class="col-lg-12">
                                                                <div class="table-responsive table-detail table-scrollable">
                                                                    <table class="table table-striped mb-0">
                                                                        <thead>
                                                                            <tr>
                                                                                <th width="%">Course Name</th>
                                                                                <th width="%">Type</th>
                                                                                <th width="%">Institution, Location</th>
                                                                                <th width="%">Issue Date</th>
                                                                                <th width="%">Expiry Date</th>
                                                                                <th width="%" class="text-right">Actions</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
<%
                                                                        if(trainingcert_list_size > 0)
                                                                        {
                                                                            CandidateInfo ainfo;
                                                                            String filep, url;
                                                                            for(int i = 0; i < trainingcert_list_size; i++)
                                                                            {
                                                                                ainfo = (CandidateInfo) trainingcert_list.get(i);
                                                                                if(ainfo != null)
                                                                                {
                                                                                    filep = "";
                                                                                    url = "";
                                                                                    if(ainfo.getCertifilename() != null && !ainfo.getCertifilename().equals(""))
                                                                                        filep = file_path+ainfo.getCertifilename();
                                                                                    else if(ainfo.getUrl() != null && !ainfo.getUrl().equals(""))
                                                                                        url = ainfo.getUrl();
%>
                                                                            <tr>
                                                                                <td><%= ainfo.getCoursename() != null ? ainfo.getCoursename() : "" %></td>
                                                                                <td><%= ainfo.getCoursetype() != null ? ainfo.getCoursetype() : "" %></td>
                                                                                <td><%= ainfo.getEduinstitute() != null ? ainfo.getEduinstitute() : "" %></td>
                                                                                <td><%= ainfo.getDateofissue() != null ? ainfo.getDateofissue() : "" %></td>
                                                                                <td><%= ainfo.getExpirydate() != null ? ainfo.getExpirydate() : "" %></td>
                                                                                <td class="action_column">                                                                                    
                                                                                    <% if(!filep.equals("")) {%><a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%=filep %>');"><img src="../assets/images/attachment.png"/> </a><% } else if(!url.equals("")) {%><a href="<%=url%>" class="mr_15" target="_blank"><img src="../assets/images/attachment.png"/> </a><% } else {%><a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a><%}%>
                                                                                    <%
                                                                                        if(ainfo.getPassflag() ==2){}else{
                                                                                     if (ainfo.getStatus() == 1) {%><a href="javascript: modifytrainingcertdetailForm('<%= ainfo.getTrainingandcertId()%>');" class="edit_mode mr_15"><img src="../assets/images/pencil.png"/></a><% }else {%><a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a><%}%>
                                                                                    <%}%>
                                                                                    <span class="switch_bth">
                                                                                        <div class="form-check form-switch">
                                                                                            <input class="form-check-input" type="checkbox" role="switch" <% if(ainfo.getPassflag() ==2){%>disabled<%}%> id="flexSwitchCheckDefault_<%=(i)%>" <% if( ainfo.getStatus() == 1){%> checked <% }%> onclick="javascript: deletetrainingcertForm('<%= ainfo.getTrainingandcertId()%>', '<%= ainfo.getStatus()%>');"/>
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
                                                                            <tr><td colspan='7'>No Training and Certification details available.</td></tr>
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
                                      </div> </div> </div> </div>                                  
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
                            addLoadEvent(setsidemenu('7'));
                            ;
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
