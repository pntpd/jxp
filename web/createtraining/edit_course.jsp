<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.createtraining.CreatetrainingInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="createtraining" class="com.web.jxp.createtraining.Createtraining" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 7, submtp = 60, ctp = 3;
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
            String message = "", clsmessage = "red_font";
            if (request.getAttribute("MESSAGE") != null)
            {
                message = (String)request.getAttribute("MESSAGE");
                request.removeAttribute("MESSAGE");
            }        
            if(message != null && (message.toLowerCase()).indexOf("success") != -1)
                clsmessage = "updated-msg";
    %>
    <head>
        <meta charset="utf-8">
        <title><%= createtraining.getMainPath("title") != null ? createtraining.getMainPath("title") : "" %></title>
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
        <script type="text/javascript" src="../jsnew/createtraining.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
        <html:form action="/createtraining/CreatetrainingAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
            <html:hidden property="doView"/>
            <html:hidden property="categoryId"/>
            <html:hidden property="subcategoryId"/>
            <html:hidden property="courseId"/>
            <html:hidden property="doSaveCourse"/>
            <html:hidden property="doIndexCourse"/>
            <html:hidden property="doIndexSubcategory"/>
            <html:hidden property="doCancel"/>
            <html:hidden property="search"/>
            <html:hidden property="fname"/>
            <html:hidden property="efilehidden"/>
            <!-- Begin page -->
            <div id="layout-wrapper">
                <%@include file="../header.jsp" %>
                <%@include file="../sidemenu.jsp" %>
                <!-- Start right Content -->
                <div class="main-content">
                    <div class="page-content tab_panel">
                        <div class="row head_title_area">
                            <div class="col-md-12 col-xl-12">
                                <div class="float-start"><a href="javascript: viewCoursecat(-1);" class="back_arrow"><img  src="../assets/images/back-arrow.png"/>Create Trainings</a></div>
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
                                            <span class='d-none d-md-block'>Sub-Category</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                        </a>
                                    </li>
                                    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 3) ? " active" : ""%>'>
                                        <a class='nav-link' href="javascript: viewCourse('-1');">
                                            <span class='d-none d-md-block'>Courses</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
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
                                                <% if(!message.equals("")) {%><div class="sbold <%=clsmessage%>">
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
                                                    <label class="form_label">Sub-category Name</label>
                                                    <html:select property="esubcategoryId" styleId="esubcategoryId" styleClass="form-select">
                                                        <html:optionsCollection filter="false" property="subcategorys" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Course Name</label>
                                                    <html:select property="coursenameId" styleId="coursenameId" styleClass="form-select">
                                                        <html:optionsCollection filter="false" property="coursenames" label="ddlLabel" value="ddlValue">
                                                        </html:optionsCollection>
                                                    </html:select>
                                                </div>
                                                
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                    <label class="form_label">Attachments - optional (20MB max)</label>
                                                    <a href="javascript:;" data-bs-toggle="modal" id = "upload_link_image" data-bs-target="#drag_drop_modal" class="attache_btn text-center"><i class="fas fa-paperclip"></i> Attachment </a>
                                                </div>
                                                
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                     <label class="form_label">E-Learning File (Only zip 200 MB max)</label>                                                  
                                                        <html:file property="elearningfile" styleId="upload1" onchange="javascript: setClass('1');"/>
                                                        <a href="javascript:;" id="upload_link_1" class="attache_btn uploaded_img1"><i class="fas fa-paperclip"></i> Attach</a>
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

        <div id="drag_drop_modal" class="modal fade drag_drop_div" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <!-- <h4 class="modal-title">View File</h4> -->
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="dropzone dropzone-file-area" id="my-dropzone" action="##">
                                    <h3 class="sbold">
                                        <img src="../assets/images/drag_drop_upload.png"/>
                                        Drag and Drop <br/> <span>or browse your files</span>
                                    </h3>
                                    <div class="drop_list_save" id="dSave"><a href="javascript:void(0);" onclick="javascript: setClassRes();"  data-bs-dismiss="modal"><img src="../assets/images/save.png"> Save</a></div>
                                </div>
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
                    autoclose: "true",
                    orientation: "bottom",
                    format: "yyyy",
                    viewMode: "years",
                    minViewMode: "years"
                });
            });
         </script>
         <script> 
            function setClassRes()
            {
                if (trim(document.createtrainingForm.fname.value) != "")
                    document.getElementById("upload_link_image").className = "attache_btn uploaded_img";
                else
                    document.getElementById("upload_link_image").className = "attache_btn";
            }
         </script>
         
         <script>
             Dropzone.autoDiscover = false;
             var val = "";
             $("#my-dropzone").dropzone({
                 addRemoveLinks: true,
                 maxFilesize: 20,
                 maxFiles: 50,
                 parallelUploads: 50,
                 createImageThumbnails: !0,
                 acceptedFiles: ".png,.jpg,.jpeg,.pdf,.doc,.docx,.pptx,.ppt",
                 init: function () {
                     this.on("addedfile", function (file) {
                         if (file.name != "")
                         {
                             if (!(file.name).match(/(\.(pdf)|(jpg)|(jpeg)|(png)|(doc)|(ppt)|(pptx)|(docx))$/i))
                             {
                                   Swal.fire("Only .pdf, .jpeg, .jpg, .png, .doc, .docx, .ppt, .pptx are allowed.");
                                 return false;
                             }
                         }
                         var n = Dropzone.createElement("<a href='javascript:' class='trash_drop_list'><img src='../assets/images/trash.png'/></a>"),
                                 t = this;
                         n.addEventListener("click", function (n) {
                             n.preventDefault(),
                                     n.stopPropagation(),
                                     t.removeFile(file)
                         }),
                                 file.previewElement.appendChild(n)

                         var reader = new FileReader();
                         reader.onload = function (event) {
                             var base64String = event.target.result;
                             if (val == "")
                             {
                                 val += base64String;
                             } else
                             {
                                 val += ("@#@" + base64String);
                             }
                             document.createtrainingForm.fname.value = val;
                         };
                         reader.readAsDataURL(file);

                     });
                     this.on("error", function (file, message) {
                             swal.fire(message);
                             this.removeFile(file);
                             var reader = new FileReader();
                             reader.onload = function (event) {
                                 var base64String = event.target.result;
                                 var val1 = document.createtrainingForm.fname.value;
                                 var fval = val1.replace("@#@" + base64String + "@#@", "");
                                 fval = fval.replace("@#@" + base64String, "");
                                 fval = fval.replace(base64String + "@#@", "");
                                 fval = fval.replace(base64String, "");
                                 val = fval;
                                 document.createtrainingForm.fname.value = fval;
                             };
                             reader.readAsDataURL(e);
                         });
                     this.on("removedfile", function (e)
                     {
                         var reader = new FileReader();
                         reader.onload = function (event) {
                             var base64String = event.target.result;
                             var val1 = document.createtrainingForm.fname.value;
                             var fval = val1.replace("@#@" + base64String + "@#@", "");
                             fval = fval.replace("@#@" + base64String, "");
                             fval = fval.replace(base64String + "@#@", "");
                             fval = fval.replace(base64String, "");
                             val = fval;
                             document.createtrainingForm.fname.value = fval;
                         };
                         reader.readAsDataURL(e);
                     }
                     );

                 },
                 dictDefaultMessage: '<span class="text-center"><span class="font-lg visible-xs-block visible-sm-block visible-lg-block"><span class="font-lg"><i class="fa fa-caret-right text-danger"></i> Drop files <span class="font-xs">to upload</span></span><span>&nbsp&nbsp<h4 class="display-inline"> (Or Click)</h4></span>',
                 dictResponseError: 'Error uploading file!'
             });
         </script>
         
         <script>
             $(function () {
                 $("#upload_link_1").on('click', function (e) {
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
