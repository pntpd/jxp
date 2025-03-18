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
        try {
            int mtp = 2, submtp = 7, ctp = 3;
            String per = "N", addper = "N", editper = "N", deleteper = "N";
            if (session.getAttribute("LOGININFO") == null) {
    %>
    <jsp:forward page="/index1.jsp"/>
    <%
        } else {
            UserInfo uinfo = (UserInfo) session.getAttribute("LOGININFO");
            if (uinfo != null) {
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                deleteper = uinfo.getDeleteper() != null ? uinfo.getDeleteper() : "N";
            }
        }
        String message = "", clsmessage = "deleted-msg";
        if (request.getAttribute("MESSAGE") != null) {
            message = (String) request.getAttribute("MESSAGE");
            request.removeAttribute("MESSAGE");
        }
        if (message.toLowerCase().contains("success")) {
            message = "";
        }
        if (message != null && (message.toLowerCase()).indexOf("success") != -1) {
            clsmessage = "updated-msg";
        }
        ArrayList list = new ArrayList();
        if (request.getSession().getAttribute("MODULEPER_LIST") != null) {
            list = (ArrayList) request.getSession().getAttribute("MODULEPER_LIST");
        }
        String file_path = candidate.getMainPath("view_candidate_file");
        CandidateInfo healthinfo = null;
        if (session.getAttribute("CANDHEALTHINFO") != null) {
            healthinfo = (CandidateInfo) session.getAttribute("CANDHEALTHINFO");
        }
        int ipass = 0;
        if (session.getAttribute("PASS") != null) {
            ipass = Integer.parseInt((String) session.getAttribute("PASS"));
        }
        //For health list
        ArrayList filelist = new ArrayList();
        if (session.getAttribute("HEALTHFILELIST") != null) {
            filelist = (ArrayList) session.getAttribute("HEALTHFILELIST");
        }
        int size = filelist.size();
    %>
    <head>
        <meta charset="utf-8">
        <title><%= candidate.getMainPath("title") != null ? candidate.getMainPath("title") : ""%></title>
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
        <html:hidden property="doaddhealthdetail"/>
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
        <html:hidden property="healthfilehidden"/>
        <html:hidden property="candidatehealthId"/>
        <html:hidden property="statustype"/>
        <html:hidden property="onlineFlag"/>
        <html:hidden property="assettypeIdIndex"/>
        <html:hidden property="positionIdIndex"/>
        <html:hidden property="courseIndex"/>
        <html:hidden property="doDeleteHealthFile"/>
        <html:hidden property="healthfileId"/>
        <div id="layout-wrapper">
            <%@include file ="../header.jsp"%>
            <%@include file ="../sidemenu.jsp"%>
            <!-- Start right Content -->
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
                                            <% if (!message.equals("")) {%><div class="alert <%=clsmessage%> alert-dismissible fade show">
                                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button><%=message%>
                                            </div><% } %>
                                            <div class="main-heading m_30">
                                                <div class="add-btn">
                                                    <h4>HEALTH DETAILS</h4>
                                                </div>   
                                                <div class="edit_btn pull_right float-end">
                                                    <%
                                                        if (ipass == 2) {
                                                        } else {

                                                            if (editper.equals("Y")) {%> <a href="javascript: modifyhealthForm();"><img src="../assets/images/edit.png"/></a> <% } %>
                                                        <%
                                                            }
                                                        %>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Seaman Specific Medical Fitness</label>
                                                <span class="form-control"><%= healthinfo != null && healthinfo.getSsmf() != null && !healthinfo.getSsmf().equals("") ? healthinfo.getSsmf() : "&nbsp;"%></span>
                                            </div>
                                            <div class="col-lg-8 col-md-8 col-sm-8 col-4 form_group">
                                                <label class="form_label">OGUK Medical FTW</label>
                                                <span class="form-control"><%= healthinfo != null && healthinfo.getOgukmedicalftw() != null && !healthinfo.getOgukmedicalftw().equals("") ? healthinfo.getOgukmedicalftw() : "&nbsp;"%></span>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">OGUK Expiry</label>
                                                <div class="input-daterange input-group">
                                                    <span class="form-control"><%=  healthinfo != null && healthinfo.getOgukexp() != null && !healthinfo.getOgukexp().equals("") ? healthinfo.getOgukexp() : "&nbsp;"%></span>
                                                </div>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Medical Fitness Certificate</label>
                                                <span class="form-control"><%=  healthinfo != null && healthinfo.getMedifitcert() != null && !healthinfo.getMedifitcert().equals("") ? healthinfo.getMedifitcert() : "&nbsp;"%></span>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Medical Fitness Certificate Expiry</label>
                                                <div class="input-daterange input-group">
                                                    <span class="form-control"><%=  healthinfo != null && healthinfo.getMedifitcertexp() != null && !healthinfo.getMedifitcertexp().equals("") ? healthinfo.getMedifitcertexp() : " &nbsp;"%></span>
                                                </div>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Blood Group</label>
                                                <span class="form-control"><%=  healthinfo != null && healthinfo.getBloodgroup() != null && !healthinfo.getBloodgroup().equals("") ? healthinfo.getBloodgroup() : " &nbsp;"%></span>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Blood Pressure</label>
                                                <span class="form-control"><%=  healthinfo != null && healthinfo.getBloodpressure() != null && !healthinfo.getBloodpressure().equals("") ? healthinfo.getBloodpressure() : "&nbsp;"%></span>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Hypertension</label>
                                                <span class="form-control"><%= healthinfo != null && healthinfo.getHypertension() != null && !healthinfo.getHypertension().equals("") ? healthinfo.getHypertension() : "&nbsp;"%></span>
                                            </div>
                                        </div>
                                        <div class="row">	
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Diabetes</label>
                                                <span class="form-control"><%= healthinfo != null && healthinfo.getDiabetes() != null && !healthinfo.getDiabetes().equals("") ? healthinfo.getDiabetes() : "&nbsp;"%></span>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Smoking</label>
                                                <span class="form-control"><%= healthinfo != null && healthinfo.getSmoking() != null && !healthinfo.getSmoking().equals("") ? healthinfo.getSmoking() : "&nbsp;"%></span>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Covid-19 2 Doses</label>
                                                <span class="form-control"><%= healthinfo != null && healthinfo.getCov192doses() != null && !healthinfo.getCov192doses().equals("") ? healthinfo.getCov192doses() : "&nbsp;"%></span>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Height (in cm)</label>
                                                <span class="form-control"><%= healthinfo != null && healthinfo.getHeight() > 0 ? healthinfo.getHeight() : "&nbsp;"%></span>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                <label class="form_label">Weight (in Kg)</label>
                                                <span class="form-control"><%= healthinfo != null && healthinfo.getWeight() > 0 ? healthinfo.getWeight() : "&nbsp;"%></span>
                                            </div>
                                            <% if (healthinfo != null && healthinfo.getMfFilename() != null && !healthinfo.getMfFilename().equals("")) {%>
                                            <div class="col-lg-3 col-md-3 col-sm-6 col-12 text-left flex-end align-items-end edit_sec">
                                                <ul class="resume_attach">
                                                    <li><label class="form_label">File</label></li>
                                                    <li><a href="javascript:;" class="attache_btn"><i class="fas fa-paperclip"></i> Attachment <span class="attach_number"> <%=candidate.changeNum(1, 3)%></span></a></li>
                                                    <li>
                                                        <div class="down_prev text-end">
                                                            <a href="javascript:;" class="" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%=file_path + healthinfo.getMfFilename()%>');">Preview</a>
                                                        </div>
                                                    </li>
                                                </ul>
                                            </div>
                                            <%}%>
                                        </div>									

                                        <%
                                            if (size > 1) {
                                        %>                                   
                                        <div class="col-lg-6 mt_30">
                                            <div class="main-heading mb_10">
                                                <div class="add-btn">
                                                    <h4>FILE HISTORY</h4>
                                                </div>   
                                            </div>
                                            <div class="table-rep-plugin sort_table">
                                                <div class="table-responsive mb-0" data-bs-pattern="priority-columns">        
                                                    <table id="tech-companies-1" class="table table-striped">
                                                        <thead>
                                                            <tr>
                                                                <th width="%">
                                                                    <span><b>Date</b> </span>
                                                                </th>
                                                                <th width="%" class="action_column">
                                                                    <span><b>View</b> </span>
                                                                </th>
                                                                <th width="%" class="text-right">
                                                                    <span><b>Actions</b></span>
                                                                </th>
                                                            </tr>
                                                        </thead>
                                                        <tbody id="sort_id">
                                                            <%
                                                                CandidateInfo info;
                                                                for (int i = 0; i < size; i++) {
                                                                    info = (CandidateInfo) filelist.get(i);
                                                                    if (info != null) {
                                                            %>
                                                            <tr>
                                                                <td><%= info.getDate() != null ? info.getDate() : ""%></td>
                                                                <td class="action_column">
                                                                    <% if (!info.getFilename().equals("")) {%><a href="javascript:;" class="mr_15" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript:setIframe('<%=file_path + info.getFilename()%>');"><img src="../assets/images/attachment.png"/> </a><% } else { %><a href='javascript:;'><span style='width: 35px;'>&nbsp;</span></a><% } %>
                                                                </td>
                                                                <%if (deleteper.equals("Y")) {%>
                                                                <td class="action_column text-right">
                                                                    <%if (i > 0) {%><a href="javascript: deleteHealthFile('<%= info.getHealthfileId()%>');" class="mr_15"><img src="../assets/images/trash.png"> </a><%}%>                                                                       
                                                                </td>
                                                                <%}%>
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
                                        %>
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
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>