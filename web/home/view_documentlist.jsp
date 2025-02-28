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
             int mtp = 2, submtp = 7, ctp = 8;
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
                ArrayList doc_list = new ArrayList();
            if(request.getAttribute("CANDGOVDOCLIST") != null)
                doc_list = (ArrayList) request.getAttribute("CANDGOVDOCLIST");
            int doc_list_size = doc_list.size();

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
        <script type="text/javascript" src="../jsnew/home.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed registration_page">
    <html:form action="/home/HomeAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
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
        <html:hidden property="domodifygovdocumentdetail"/>
        <html:hidden property="doDeletegovdocumentdetail"/>
        <html:hidden property="govdocumentId"/>
        <html:hidden property="status" />
        <html:hidden property="fname"/>
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
                                                            <div class="main-heading">
                                                                <div class="add-btn">
                                                                    <h4>DOCUMENTS</h4>
                                                                </div>
                                                                <div class="pull_right float-end">
                                                                    <%
                                                                if(ipass ==2){}else{ %>
                                                                  <a href="javascript: modifydocumentdetailForm(-1);" class="add_btn"><i class="mdi mdi-plus"></i></a> <% } %>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-12">
                                                                <div class="table-responsive table-detail">
                                                                    <table class="table table-striped mb-0">
                                                                        <thead>
                                                                            <tr>
                                                                                <th width="%">Document Name</th>
                                                                                <th width="%">Number</th>
                                                                                <th width="%">Place of Issue</th>
                                                                                <th width="%">Issued By</th>
                                                                                <th width="%">Issue Date</th>
                                                                                <th width="%">Expiry Date</th>
                                                                                <th width="%" class="text-right">Actions</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
<%
                                                                        if(doc_list_size > 0)
                                                                        {
                                                                            CandidateInfo ainfo;
                                                                            String filename1;
                                                                            for(int i = 0; i < doc_list_size; i++)
                                                                            {
                                                                                ainfo = (CandidateInfo) doc_list.get(i);
                                                                                if(ainfo != null)
                                                                                {
                                                                                    filename1 = ainfo.getGovdocumentfile() != null ? ainfo.getGovdocumentfile() : "";
%>
                                                                            <tr>
                                                                                <td><%= ainfo.getDocumentname() != null ? ainfo.getDocumentname() : "" %></td>
                                                                                <td><%= ainfo.getDocumentno() != null ? ainfo.getDocumentno() : "" %></td>
                                                                                <td><%= ainfo.getPlaceofissue() != null ? ainfo.getPlaceofissue() : "" %></td>
                                                                                <td><%= ainfo.getIssuedby() != null ? ainfo.getIssuedby() : "" %></td>
                                                                                <td><%= ainfo.getDateofissue() != null ? ainfo.getDateofissue() : "" %></td>
                                                                                <td><%= ainfo.getDateofexpiry() != null ? ainfo.getDateofexpiry() : "" %></td>
                                                                                <td class="action_column">                                                                       
                                                                                    <% if (ainfo.getFilecount() > 0) {%><a class="mr_15" href="javascript:;" onclick="javascript: viewimgdoc('<%=ainfo.getGovdocumentId()%>');"  data-bs-toggle="modal" data-bs-target="#view_resume_list"><img src="../assets/images/attachment.png"></a><% } else {%>&nbsp;<% } %>
                                                                                        <%
                                                                                                if(ainfo.getPassflag() ==2){}else{
                                                                                         if (ainfo.getStatus() == 1) {%><a href="javascript: javascript: modifydocumentdetailForm('<%= ainfo.getGovdocumentId()%>');" class=" mr_15"><img src="../assets/images/pencil.png"/></a><% } else {%><a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a><%}%>
                                                                                        <%}%>

                                                                                    <span class="switch_bth">                                           
                                                                                        <div class="form-check form-switch">
                                                                                            <input class="form-check-input" type="checkbox" <% if(ainfo.getPassflag() ==2){%>disabled<%}%> role="switch" id="flexSwitchCheckDefault_<%=(i)%>" <% if( ainfo.getStatus() == 1){%> checked <% }%>   onclick="javascript: deletedocumentForm('<%= ainfo.getGovdocumentId()%>', '<%= ainfo.getStatus()%>');"/>
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
                                                                            <tr><td colspan='7'>No document details available.</td></tr>
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
                        <div id="view_resume_list" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
                            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                                    </div>
                                    <div class="modal-body">
                                        <div><a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen r_f_screen">Full Screen</a></div>
                                        <div class="row" id="viewfilesdiv">
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
