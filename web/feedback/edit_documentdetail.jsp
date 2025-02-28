<%@page contentType="text/html"%>
<%@page language="java" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"  %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"  %>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.feedback.FeedbackInfo"%>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@page import="com.web.jxp.candidate.CandidateInfo"%>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="feedback" class="com.web.jxp.feedback.Feedback" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try {
            int mtp = 2, submtp = 7, ctp = 8;
          if (session.getAttribute("CREWLOGIN") == null) {
    %>
    <jsp:forward page="/crewloginindex1.jsp"/>
    <%        }
        String message = "", clsmessage = "red_font";
            if (request.getAttribute("MESSAGE") != null)
            {
                message = (String)request.getAttribute("MESSAGE");
                request.removeAttribute("MESSAGE");
            }        
            if(message != null && (message.toLowerCase()).indexOf("success") != -1)
                clsmessage = "updated-msg";
            ArrayList list = new ArrayList();
            if(request.getSession().getAttribute("MODULEPER_LIST") != null)
                list = (ArrayList)request.getSession().getAttribute("MODULEPER_LIST");
    %>
    <head>
        <meta charset="utf-8">
        <title><%= feedback.getMainPath("title") != null ? feedback.getMainPath("title") : ""%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/drop/dropzone.min.css" rel="stylesheet" type="text/css" />
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="../autofill/jquery-ui.min.css" />  <!-- Autofill-->
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/feedback.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="ocs_feedback vertical-collpsed crew_user bg_image">
    <html:form action="/feedback/FeedbackAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="doSavegovdocumentdetail" />
        <html:hidden property="currentDate"/>
        <html:hidden property="candidateId"/>
        <html:hidden property="govdocumentId"/>
        <html:hidden property="fname"/>
        <html:hidden property="expiryId"/>
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">

                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 heading_title">
                                <h1>Profile > Document</h1>
                            </div>

                            <div class="col-md-12 col-xl-12">
                                <div class="body-background">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="tab-content">
                                                <div class="tab-pane active">
                                                    <div class="">
                                                        <% if(!message.equals("")) {%><div class="sbold <%=clsmessage%>">
                                                            <%=message%>
                                                        </div><% } %>
                                                        <div class="row">
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Document Name<span class="required">*</span></label>
                                                                <html:select property="documentTypeId" styleClass="form-select" onchange="javascript: setDocumentissuedbyDDL();">
                                                                    <html:optionsCollection filter="false" property="documentTypes" label="ddlLabel" value="ddlValue">
                                                                    </html:optionsCollection>
                                                                </html:select>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Document Number<span class="required">*</span></label>
                                                                <html:text property="documentNo" styleId="documentNo" styleClass="form-control" maxlength="100"/>
                                                                <script type="text/javascript">
                                                                    document.getElementById("documentNo").setAttribute('placeholder', '');
                                                                </script>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <div class="row flex-end align-items-end">
                                                                    <div class="col-lg-10 col-md-12 col-sm-12 col-12 form_group">
                                                                        <label class="form_label">Place of Issue (City)</label>
                                                                        <html:hidden property="placeofapplicationId" />
                                                                        <html:text property="cityName" styleId="cityName" styleClass="form-control" maxlength="100" onblur="if (this.value == '') {document.feedbackForm.placeofapplicationId.value='0';}"/>
                                                                        <script type="text/javascript">
                                                                            document.getElementById("cityName").setAttribute('placeholder', '');
                                                                            document.getElementById("cityName").setAttribute('autocomplete', 'off');
                                                                        </script>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Date of Issue</label>
                                                                <div class="input-daterange input-group">
                                                                    <html:text property="dateofissue" styleId="dateofissue" styleClass="form-control add-style wesl_dt date-add" />
                                                                    <script type="text/javascript">
                                                                        document.getElementById("dateofissue").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                                    </script>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Date of Expiry</label>
                                                                <div class="input-daterange input-group">
                                                                    <html:text property="dateofexpiry" styleId="dateofexpiry" styleClass="form-control add-style wesl_dt date-add" />
                                                                    <script type="text/javascript">
                                                                        document.getElementById("dateofexpiry").setAttribute('placeholder', 'DD-MMM-YYYY');
                                                                    </script>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Attach Document (5MB) (.pdf/.jpeg/.png)</label>
                                                                <a href="javascript:;" data-bs-toggle="modal" id = "upload_link_image" data-bs-target="#drag_drop_modal" class="attache_btn text-center"><i class="fas fa-paperclip"></i> Attachment </a>
                                                            </div>
                                                            <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                                <label class="form_label">Document Issued by<span class="required">*</span></label>
                                                                <html:select property="documentissuedbyId" styleId="documentissuedbyId" styleClass="form-select">
                                                                    <html:optionsCollection filter="false" property="documentissuedbys" label="ddlLabel" value="ddlValue">
                                                                    </html:optionsCollection>
                                                                </html:select>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12" id="submitdiv">
                                                            <a href="javascript: submitdocumentform();" class="save_btn"><img src="../assets/images/save.png"> Save</a>
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
                    <!-- End Page-content -->
                </div>
                <!-- end main content-->
            </div>
            <!-- END layout-wrapper -->
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
            <script src="../autofill/jquery-ui.min.js" type="text/javascript"></script> <!-- autofill-->
            <script src="../assets/drop/dropzone.min.js" type="text/javascript"></script>  
            <script src="../assets/drop/app.min.js" type="text/javascript"></script>
            <script src="../assets/js/bootstrap-multiselect.js" type="text/javascript"></script>
            <script src="../assets/js/bootstrap-select.min.js"></script>
            <script src="../assets/js/bootstrap-datepicker.min.js"></script>
            <script src="../assets/drop/form-dropzone.min.js" type="text/javascript"></script>
            <script src="/jxp/assets/js/sweetalert2.min.js"></script>           `	
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

                $(function ()
                {
                    $("#cityName").autocomplete({
                        source: function (request, response) {
                            $.ajax({
                                url: "/jxp/ajax/feedback/autofillcity_vaccination.jsp",
                                type: 'post',
                                dataType: "json",
                                //data: JSON.stringify({"search": request.term, "countryId": $("#countryId").val()}),
                                data: JSON.stringify({"search": request.term}),
                                success: function (data) {
                                    response(data);
                                }
                            });
                        },
                        select: function (event, ui) {
                            //console.log('onHover :: '+JSON.stringify(ui,null,2));					
                            $('#cityName').val(ui.item.label); // display the selected text
                            $('input[name="placeofapplicationId"]').val(ui.item.value);
                            return false;
                        },
                        focus: function (event, ui)
                        {
                            //console.log('onFocus :: '+JSON.stringify(ui,null,2));					
                            $('#cityName').val(ui.item.label); // display the selected text
                            return false;
                        }
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
                addLoadEvent(setsidemenu('10'));
                ;
            </script>
            <script> 
           function setClassRes()
               {
                   if (trim(document.feedbackForm.fname.value) != "")
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
                maxFilesize: 5,
                maxFiles: 50,
                parallelUploads: 50,
                createImageThumbnails: !0,
                acceptedFiles: ".png,.jpg,.jpeg,.pdf",
                init: function () {
                    this.on("addedfile", function (file) {
                        if (file.name != "")
                        {
                            if (!(file.name).match(/(\.(pdf)|(jpg)|(jpeg)|(png))$/i))
                            {
                                alert("Only .pdf, .jpeg, .jpg, .png are allowed.");
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
                            document.feedbackForm.fname.value = val;
                        };
                        reader.readAsDataURL(file);

                    });
                    this.on("error", function (file, message) {
                            swal.fire(message);
                            this.removeFile(file);
                            var reader = new FileReader();
                            reader.onload = function (event) {
                                var base64String = event.target.result;
                                var val1 = document.feedbackForm.fname.value;
                                var fval = val1.replace("@#@" + base64String + "@#@", "");
                                fval = fval.replace("@#@" + base64String, "");
                                fval = fval.replace(base64String + "@#@", "");
                                fval = fval.replace(base64String, "");
                                val = fval;
                                document.feedbackForm.fname.value = fval;
                            };
                            reader.readAsDataURL(e);
                        });
                    this.on("removedfile", function (e)
                    {
                        var reader = new FileReader();
                        reader.onload = function (event) {
                            var base64String = event.target.result;
                            var val1 = document.feedbackForm.fname.value;
                            var fval = val1.replace("@#@" + base64String + "@#@", "");
                            fval = fval.replace("@#@" + base64String, "");
                            fval = fval.replace(base64String + "@#@", "");
                            fval = fval.replace(base64String, "");
                            val = fval;
                            document.feedbackForm.fname.value = fval;
                        };
                        reader.readAsDataURL(e);
                    }
                    );

                },
                dictDefaultMessage: '<span class="text-center"><span class="font-lg visible-xs-block visible-sm-block visible-lg-block"><span class="font-lg"><i class="fa fa-caret-right text-danger"></i> Drop files <span class="font-xs">to upload</span></span><span>&nbsp&nbsp<h4 class="display-inline"> (Or Click)</h4></span>',
                dictResponseError: 'Error uploading file!'
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
