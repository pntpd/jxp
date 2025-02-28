<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.candidate.CandidateInfo" %>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="candidate" class="com.web.jxp.candidate.Candidate" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 2, submtp = 7, ctp = 8;
            if (session.getAttribute("CREWLOGIN") == null) {
    %>
    <jsp:forward page="/crewloginindex1.jsp"/>
    <%
        }
        ArrayList doc_list = new ArrayList();
        if (request.getAttribute("CANDGOVDOCLIST") != null) {
            doc_list = (ArrayList) request.getAttribute("CANDGOVDOCLIST");
        }
        int doc_list_size = doc_list.size();
        String file_path = candidate.getMainPath("view_candidate_file");
    %>
    <head>
        <meta charset="utf-8">
        <title><%= candidate.getMainPath("title") != null ? candidate.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/feedback.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="ocs_feedback vertical-collpsed crew_user bg_image">
    <html:form action="/feedback/FeedbackAction.do" onsubmit="return false;" styleClass="form-horizontal">
        <html:hidden property="status" />
        <html:hidden property="govdocumentId" />
        <html:hidden property="doAddGovDoc" />
        <html:hidden property="domodifygovdocumentdetail" />
        <html:hidden property="searchDoc" />
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 heading_title">
                                <h1>Profile >Documents</h1>                                
                            </div>
                            <div class="col-lg-2 mb_20">
                                <div class="row">
                                    <div class="col-sm-12 field_ic">
                                        <html:text property ="search2" styleClass="form-control" styleId="example-text-input" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                                   this.removeAttribute('readonly');
                                                   this.blur();
                                                   this.focus();
                                                   }"/>
                                        <a href="javascript: searchDocument();" class="input-group-text"><i class="mdi mdi-magnify"></i></a>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-2 mb_20">
                                <div class="row">
                                    <div class="col-sm-12 field_ic">
                                        <html:select styleClass="form-select" property="expiryId" styleId="single-prepend-text" onchange="javascript: searchDocument();">
                                            <html:option value="-1">Select</html:option>
                                            <html:option value="1">Expiring (10 days)</html:option>
                                            <html:option value="3">Expiring (90 days)</html:option>
                                            <html:option value="4">Expiring (180 days)</html:option>
                                            <html:option value="5">Expiring (365 days)</html:option>
                                            <html:option value="2">Expired </html:option>
                                        </html:select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                <div class="body-background">
                                    
                                    <div class="row">
                                         <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 mt_15">                                              
                                            <div class="pull_right float-end">
                                                <a href="javascript: modifydocumentdetailForm(-1);" class="add_btn"><i class="mdi mdi-plus"></i></a>
                                            </div>
                                        </div>
                                        <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 form_group mt_15">
                                            <div class="table-responsive table-detail">
                                                <table class="table table-striped mb-0">
                                                    <thead>
                                                        <tr>
                                                            <th width="26%">Document Name</th>
                                                            <th width="10%">Number</th>
                                                            <th width="17%">Place of Issue</th>
                                                            <th width="15%">Issued By</th>
                                                            <th width="10%">Issue Date</th>
                                                            <th width="9%">Expiry Date</th>
                                                            <th width="13%" class="text-center">Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%                                                            
                                                            if (doc_list_size > 0) {
                                                                CandidateInfo ainfo;
                                                                for (int i = 0; i < doc_list_size; i++) {
                                                                    ainfo = (CandidateInfo) doc_list.get(i);
                                                                    if (ainfo != null) {
                                                        %>
                                                        <tr>
                                                            <td><%= ainfo.getDocumentname() != null ? ainfo.getDocumentname() : "&nbsp;"%></td>
                                                            <td><%= ainfo.getDocumentno() != null ? ainfo.getDocumentno() : "&nbsp;"%></td>
                                                            <td><%= ainfo.getPlaceofissue() != null ? ainfo.getPlaceofissue() : "&nbsp;"%></td>
                                                            <td><%= ainfo.getIssuedby() != null ? ainfo.getIssuedby() : "&nbsp;"%></td>
                                                            <td><%= ainfo.getDateofissue() != null ? ainfo.getDateofissue() : ""%></td>
                                                            <td><%= ainfo.getDateofexpiry() != null ? ainfo.getDateofexpiry() : "&nbsp;"%></td>
                                                            <td class="action_column">                                                                       
                                                                <% if (ainfo.getFilecount() > 0) {%><a class="mr_15" href="javascript:;" onclick="javascript: viewimgdoc('<%=ainfo.getGovdocumentId()%>');"  data-bs-toggle="modal" data-bs-target="#view_resume_list"><img src="../assets/images/attachment.png"></a><% } else {%>&nbsp;<% } %>
                                                                    <%
                                                                        if (ainfo.getStatus() == 1) {%><a href="javascript: javascript: modifydocumentdetailForm('<%= ainfo.getGovdocumentId()%>');" class=" mr_15"><img src="../assets/images/pencil.png"/></a><% } else {%><a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a><%}%>
                                                                         <span class="switch_bth">                                           
                                                                    <div class="form-check form-switch">
                                                                        <input class="form-check-input" type="checkbox" disabled role="switch" id="flexSwitchCheckDefault" checked />                                                                        
                                                                    </div>
                                                                </span>
                                                                <%--
                                                                <span class="switch_bth">                                           
                                                                    <div class="form-check form-switch">
                                                                        <input class="form-check-input" type="checkbox" <% if (ainfo.getPassflag() == 2) {%>disabled<%}%> role="switch" id="flexSwitchCheckDefault_<%=(i)%>" <% if (ainfo.getStatus() == 1) {%> checked <% }%>   onclick="javascript: deletedocumentForm('<%= ainfo.getGovdocumentId()%>', '<%= ainfo.getStatus()%>');"/>
                                                                        
                                                                    </div>
                                                                </span>--%>                                                                    
                                                            </td>

                                                        </tr>    
                                                        <%
                                                                }
                                                            }
                                                        } else {
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
        <!-- JAVASCRIPT -->
        <script src="../assets/libs/jquery/jquery.min.js"></script>		
        <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/js/app.js"></script>
        
    </html:form>
</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
