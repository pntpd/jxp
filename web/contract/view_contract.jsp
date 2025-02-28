<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.contract.ContractInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="contract" class="com.web.jxp.contract.Contract" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 7, submtp = 98;
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

            ContractInfo info = null;
            if(request.getAttribute("CONTRACT_DETAIL") != null)
                info = (ContractInfo)request.getAttribute("CONTRACT_DETAIL");
                
            String viewpath = contract.getMainPath("view_contractfile");
    %>
    <head>
        <meta charset="utf-8">
        <title><%= contract.getMainPath("title") != null ? contract.getMainPath("title") : "" %></title>
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
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css"/>
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/contract.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/contract/ContractAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="doCancel"/>
        <html:hidden property="search"/>
        <html:hidden property="contractId"/>
        <html:hidden property="doDeleteFile"/>
        <!-- Begin page -->
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start"><a href="javascript: goback();" class="back_arrow"><img  src="../assets/images/back-arrow.png"/> View Contract</a></div>
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
                    </div>
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12">
                                <div class="body-background">
                                    <div class="row d-none1">  
<%
                                    if(info != null)
                                    {
%>
                                        <div class="row col-lg-12">
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                <label class="form_label">Client Name</label>
                                                <span class="form-control"><%= (info.getClientname() != null && !info.getClientname().equals("")) ? info.getClientname() : "&nbsp;" %></span>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                <label class="form_label">Asset Name</label>
                                                <span class="form-control"><%= (info.getAssetName() != null && !info.getAssetName().equals("")) ? info.getAssetName() : "&nbsp;" %></span>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                <label class="form_label">Name</label>
                                                <span class="form-control"><%= (info.getName() != null && !info.getName().equals("")) ? info.getName() : "&nbsp;" %></span>
                                            </div>
                                        </div>
                                            
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Work Experience Column</label>
                                            <span class="form-control"><%= contract.getNameFromId(info.getExpColumn()) != null && !contract.getNameFromId(info.getExpColumn()).equals("") ? contract.getNameFromId(info.getExpColumn()) : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Education Column</label>
                                            <span class="form-control"><%= contract.getEduFromId(info.getEduColumn()) != null && !contract.getEduFromId(info.getEduColumn()).equals("") ? contract.getEduFromId(info.getEduColumn()) : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Document Column</label>
                                            <span class="form-control"><%= contract.getDocFromId(info.getDocColumn()) != null && !contract.getDocFromId(info.getDocColumn()).equals("") ? contract.getDocFromId(info.getDocColumn()) : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Training and Certificate Column</label>
                                            <span class="form-control"><%= contract.getTrainingFromId(info.getTrainingColumn()) != null && !contract.getTrainingFromId(info.getTrainingColumn()).equals("") ? contract.getTrainingFromId(info.getTrainingColumn()) : "&nbsp;"%></span>
                                        </div>                                            
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Language Column</label>
                                            <span class="form-control"><%= contract.getLanguageFromId(info.getLanguageColumn()) != null && !contract.getLanguageFromId(info.getLanguageColumn()).equals("") ? contract.getLanguageFromId(info.getLanguageColumn()) : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Vaccine Column</label>
                                            <span class="form-control"><%= contract.getVaccineFromId(info.getVaccineColumn()) != null && !contract.getVaccineFromId(info.getVaccineColumn()).equals("") ? contract.getVaccineFromId(info.getVaccineColumn()) : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                            <label class="form_label">Vaccine Column</label>
                                            <span class="form-control"><%= contract.getNomineeFromId(info.getNomineeColumn()) != null && !contract.getNomineeFromId(info.getNomineeColumn()).equals("") ? contract.getNomineeFromId(info.getNomineeColumn()) : "&nbsp;"%></span>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                            <label class="form-label">Type:</label>
                                            <div class="col-md-8 control-label2">
                                                <span><%= info.getType() == 1 ? "Main Contract" : "Sub Contract" %></span>
                                            </div>
                                        </div>
                                        <%if(info.getType() == 2){%>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                <label class="form_label">Sub Contract</label>
                                                <span class="form-control"><%= (info.getRefName() != null && !info.getRefName().equals("")) ? info.getRefName() : "&nbsp;" %></span>
                                            </div>
                                        <%}%>                                      
                                        <div class="row">
                                            <div class="col-md-12 form-horizontal form_group">
                                                <div class="form-group">
                                                    <label class="control-label col-md-2 bold">Description:</label>
                                                    <div class="col-md-10 control-label2">
                                                        <span></span>
                                                        <span><%= info.getDescription() != null && !info.getDescription().equals("") ? info.getDescription() : "NA" %></span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-2 bold">Header Description:</label>
                                                <div class="col-md-10 control-label2">
                                                    <span></span>
                                                    <span><%= info.getDescription2() != null && !info.getDescription2().equals("") ? info.getDescription2() : "NA" %></span>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-2 bold">Footer Description:</label>
                                                <div class="col-md-10 control-label2">
                                                    <span></span>
                                                    <span><%= info.getDescription3() != null && !info.getDescription3().equals("") ? info.getDescription3() : "NA" %></span>
                                                </div>
                                            </div>
                                            <%if(info.getFilename() != null && !info.getFilename().equals("")){%>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                    <label class="form_label">Water Mark</label>
                                                    <span >
                                                        <a href="javascript:;" data-bs-toggle="modal" data-bs-target="#view_pdf" onclick="javascript: setIframe('<%=viewpath+info.getFilename()%>');">Preview</a>
                                                    </span>
                                                </div>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group" id="submitdiv">
                                                    <label class="form_label">Delete Water Mark</label>
                                                    <span >
                                                        <a href="javascript: deleteFile('<%=info.getContractId()%>');"><img src="../assets/images/remove.png" /></a>
                                                    </span>
                                                </div>
                                            <%}%>

                                        <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                            <label class="form_label">Status</label>
                                            <span class="form-control"><%= contract.getStatusById(info.getStatus()) %></span>
                                        </div>
                                        <%
                                                                            }
                                        %>
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
        <div id="view_pdf" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        <span class="resume_title"> File</span>
                        <div class="float-end">
                            <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                            <a id='diframe' href="" class="down_btn"><img src="../assets/images/download.png"/></a>
                        </div>
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
