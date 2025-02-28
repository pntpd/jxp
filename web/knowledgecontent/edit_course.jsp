<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.knowledgecontent.KnowledgecontentInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="knowledgecontent" class="com.web.jxp.knowledgecontent.Knowledgecontent" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 10, submtp = 93, ctp = 3;
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
        String message = "", clsmessage = "red_font", assetIds = "", show_modal = "";
        if (request.getAttribute("MESSAGE") != null) {
            message = (String) request.getAttribute("MESSAGE");
            request.removeAttribute("MESSAGE");
        }
        if (message != null && (message.toLowerCase()).indexOf("success") != -1) {
            clsmessage = "updated-msg";
        }
        if (session.getAttribute("ASSET_IDs") != null) {
            assetIds = (String) session.getAttribute("ASSET_IDs");
        }
        ArrayList list = new ArrayList();
        if (session.getAttribute("TOPICATTACHMENTLIST") != null) {
            list = (ArrayList) session.getAttribute("TOPICATTACHMENTLIST");
        }
        if (request.getAttribute("SHOW") != null) {
            show_modal = (String) request.getAttribute("SHOW");
            request.removeAttribute("SHOW");
        }
        ArrayList assetlist = knowledgecontent.getAssetList();
    %>
    <head>
        <meta charset="utf-8">
        <title><%= knowledgecontent.getMainPath("title") != null ? knowledgecontent.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/drop/dropzone.min.css" rel="stylesheet" type="text/css" />
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css"/>
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/knowledgecontent.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/knowledgecontent/KnowledgecontentAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
            <html:hidden property="doView"/>
            <html:hidden property="categoryId"/>
            <html:hidden property="subcategoryId"/>
            <html:hidden property="courseId"/>
            <html:hidden property="doSaveCourse"/>
            <html:hidden property="doIndexCourse"/>
            <html:hidden property="doIndexSubcategory"/>
            <html:hidden property="doCancel"/>
            <html:hidden property="doAddFileList"/>
            <html:hidden property="doDeleteFileList"/>
            <html:hidden property="search"/>
            <html:hidden property="fname"/>
            <html:hidden property="tempcount"/>
            <!-- Begin page -->
            <div id="layout-wrapper">
                <%@include file="../header.jsp" %>
                <%@include file="../sidemenu.jsp" %>
                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content tab_panel">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12">
                                <div class="float-start"><a href="javascript: viewCoursecat(-1);" class="back_arrow"><img  src="../assets/images/back-arrow.png"/>Content Management</a></div>
                                <div class="float-end">                            
                                    <div class="toggled-off usefool_tool">
                                        <div class="toggle-title">
                                            <img src="../assets/images/left-arrow.png" class="fa-angle-left"/>
                                            <img src="../assets/images/right-arrow.png" class="fa-angle-right"/>
                                        </div>
                                        <!-- end toggle-title --->
                                        <div class="toggle-content">
                                            <h4>Useful Tools</h4>
                                            <%@include file ="../shortcutmenu_edit.jsp"%>
                                        </div>                                
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 col-xl-12 tab_head_area">
                                <ul class='nav nav-tabs nav-tabs-custom' id='tab_menu'>
                                    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 1) ? " active" : ""%>'>
                                        <a class='nav-link' href="javascript: showDetailcategory();">
                                            <span class='d-none d-md-block'>Category</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 2) ? " active" : ""%>'>
                                        <a class='nav-link' href="javascript: viewSubcategory();">
                                            <span class='d-none d-md-block'>Module</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 3) ? " active" : ""%>'>
                                        <a class='nav-link' href="javascript: viewCourse('-1');">
                                            <span class='d-none d-md-block'>Topics</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
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
                                                <% if (!message.equals("")) {%><div class="sbold <%=clsmessage%>">
                                                    <%=message%>
                                                </div><% } %>
                                                <div class="main-heading">
                                                    <div class="add-btn">
                                                        <h4>&nbsp;</h4>
                                                    </div> 
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Category Code</label>
                                                    <html:text property="categorycode" styleId="categorycode" styleClass="form-control" maxlength="100" readonly="true" />
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Category Name</label>
                                                    <html:text property="categoryname" styleId="categoryname" styleClass="form-control" maxlength="100" readonly="true" />
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Module Name</label>
                                                    <html:select property="esubcategoryId" styleId="esubcategoryId" styleClass="form-select">
                                                        <html:optionsCollection filter="false" property="subcategorys" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Topic Name</label>
                                                    <html:text property="topicname" styleId="topicname" styleClass="form-control" maxlength="100" />
                                                </div>
                                                <%
                                                    int nsize = 0;
                                                    nsize = assetlist.size();
                                                %>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Asset Type</label>
                                                    <select id="asset_type" name="assettype" multiple="multiple" class="form-select">
                                                        <%
                                                            if (nsize > 0) {
                                                                for (int i = 0; i < nsize; i++) {
                                                                    KnowledgecontentInfo info = (KnowledgecontentInfo) assetlist.get(i);
                                                                    if (info != null) {
                                                        %>
                                                        <option value="<%=info.getDdlValue()%>" <% if (knowledgecontent.checkToStr(assetIds, info.getDdlValue() + "")) {%>selected<%}%>><%= (info.getDdlLabel())%> </option>
                                                        <%
                                                                    }
                                                                }
                                                            }
                                                        %>
                                                    </select>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Topic Material (20MB max)</label>
                                                    <div class="row">
                                                        <div class="col-lg-6 col-md-6 col-sm-6 col-12" >
                                                            <a href="javascript:;" class="attache_btn uploaded_img<%= list.size() >0 ? "":"1"%>" onclick="javascript: getMultiFile();"><i class="fas fa-paperclip"></i> Attach</a> 
                                                            <input type="hidden" id="filecount" value="<%=list.size()%>" />
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                    <label class="form_label">Description</label>
                                                    <html:textarea property="cdescription" rows="5" styleId="cdescription" styleClass="form-control"></html:textarea>
                                                        <script type="text/javascript">
                                                            document.getElementById("cdescription").setAttribute('placeholder', '');
                                                            document.getElementById("cdescription").setAttribute('maxlength', '500');
                                                        </script>
                                                    </div>
                                                </div>


                                                <div class="row" id="submitdiv">
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                        <a href="javascript: submitcourseForm();" class="save_btn"><img src="../assets/images/save.png">Save</a>
                                                        <a href="javascript: viewCoursecat('-1');" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
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
            <!-- END layout-wrapper -->
        <%@include file="../footer.jsp" %>

        <div id="topic_material_files_modal" class="modal fade parameter_modal define_modal large_modal">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12" id="dMultiUpload">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="view_pdf" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <button data-bs-toggle="modal" data-bs-target="#topic_material_files_modal" type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                        <span class="resume_title"> File</span>
                        <a href="javascript:;" data-bs-toggle="fullscreen" class="full_screen">Full Screen</a>
                        <a id='diframe' href="" class="down_btn"><img src="../assets/images/download.png"/></a>
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
        <script src="../assets/drop/jquery.min.js"></script>	
        <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
        <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
        <script src="../assets/drop/dropzone.min.js" type="text/javascript"></script>  
        <script src="../assets/drop/app.min.js" type="text/javascript"></script>
        <script src="../assets/js/bootstrap-select.min.js"></script>
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
        <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script> 
        <script src="../assets/drop/form-dropzone.min.js" type="text/javascript"></script>
        <script src="../assets/js/sweetalert2.min.js"></script>
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
                    autoclose: "true",
                    orientation: "bottom",
                    format: "yyyy",
                    viewMode: "years",
                    minViewMode: "years"
                });
            });
        </script>
        <script type="text/javascript">
            $(document).ready(function () {
                $('#asset_type').multiselect({
                    nonSelectedText: '- Select -',
                    includeSelectAllOption: true,
                    maxHeight: 200,
                    enableFiltering: false,
                    enableCaseInsensitiveFiltering: false,
                    buttonWidth: '100%'
                });
            });
        </script>
        <% if (show_modal.equals("yes")) {%>
        <script type="text/javascript">getMultiFile();</script>
        <%}%>
    </html:form>
</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
