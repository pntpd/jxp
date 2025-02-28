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
            int mtp = 2, submtp = 4, ctp = 10;
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
            ArrayList list = new ArrayList();
            if(request.getSession().getAttribute("MODULEPER_LIST") != null)
                list = (ArrayList)request.getSession().getAttribute("MODULEPER_LIST");
    %>
    <head>
        <meta charset="utf-8">
        <title><%= talentpool.getMainPath("title") != null ? talentpool.getMainPath("title") : "" %></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <link href="../assets/drop/dropzone.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="../autofill/jquery-ui.min.css" />  <!-- Autofill-->
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/talentpool.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/talentpool/TalentpoolAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="domodifygovdocumentdetail"/>
        <html:hidden property="doSavegovdocumentdetail"/>
        <html:hidden property="doDeletegovdocumentdetail"/>
        <html:hidden property="govdocumentId"/>
        <html:hidden property="localFile" />
        <html:hidden property="fname"/>
        <html:hidden property="currentDate"/>
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
                                            <% if(!message.equals("")) {%><div class="sbold <%=clsmessage%>">
                                                <%=message%>
                                            </div><% } %>
                                            <div class="main-heading m_30">
                                                <div class="add-btn">
                                                    <h4>DOCUMENTS</h4>
                                                </div>
                                            </div>

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
                                                    <html:text property="cityName" styleId="cityName" styleClass="form-control" maxlength="100" onblur="if (this.value == '') {
                                                                    document.forms[0].placeofapplicationId.value = '0';
                                                                }"/>
                                                    <script type="text/javascript">
                                                        document.getElementById("cityName").setAttribute('placeholder', '');
                                                        document.getElementById("cityName").setAttribute('autocomplete', 'off');
                                                    </script>
                                                </div>
                                                <div class="col-lg-2 col-md-12 col-sm-12 col-12 form_group pd_left_null">
                                                            <a href="javascript:;" class="add_btn" data-bs-toggle="modal" data-bs-target="#city_modal"><i class="mdi mdi-plus"></i></a>
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
                                                <a href="javascript: openTab('6') ;" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
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
        <div id="city_modal" class="modal fade" tabindex="-1" data-bs-backdrop="static" aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false">
            <input type='hidden' name='mtype' />
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">City</h4> 
                        <button type="button" class="close close_modal_btn pull-right" data-bs-dismiss="modal" aria-hidden="true"><i class="ion ion-md-close"></i></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <label class="form_label">Country<span class="required">*</span></label>
                                <html:select property="countryId" styleId="countryId" styleClass="form-select" onchange="javascript: clearcity();" >
                                    <html:optionsCollection filter="false" property="countries" label="ddlLabel" value="ddlValue">
                                    </html:optionsCollection>
                                </html:select>
                            </div>
                            <div class="col-lg-12">
                                <div class="row">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                        <label class="form_label">Name</label>
                                        <input class="form-control" placeholder="" name='mname' maxlength="100"/>
                                    </div>
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-12 text-center"><a href="javascript:addtomasterajaxedu();" class="save_button mt_15"><img src="../assets/images/save.png"> Save</a></div>
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
            <script src="/jxp/assets/js/sweetalert2.min.js"></script>
        
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
                            url: "/jxp/ajax/client/autofillcity_vaccination.jsp",
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
                   if (trim(document.talentpoolForm.fname.value) != "")
                       document.getElementById("upload_link_image").className = "attache_btn uploaded_img";
                   else
                       document.getElementById("upload_link_image").className = "attache_btn";
               }
            </script>
            <script>
            Dropzone.autoDiscover = false;
            var val = "", localfname = "";
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
                                Swal.fire("Only .pdf, .jpeg, .jpg, .png are allowed.");
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
                                localfname += file.name;
                            } else
                            {
                                val += ("@#@" + base64String);
                                localfname += ("@#@" +file.name);
                            }
                            document.talentpoolForm.fname.value = val;
                            document.talentpoolForm.localFile.value = localfname;
                        };
                        reader.readAsDataURL(file);

                    });
                    this.on("error", function (file, message) {
                            swal.fire(message);
                            this.removeFile(file);
                            var reader = new FileReader();
                            reader.onload = function (event) {
                                var base64String = event.target.result;
                                var val1 = document.talentpoolForm.fname.value;
                                var fval = val1.replace("@#@" + base64String + "@#@", "");
                                fval = fval.replace("@#@" + base64String, "");
                                fval = fval.replace(base64String + "@#@", "");
                                fval = fval.replace(base64String, "");
                                val = fval;
                                document.talentpoolForm.fname.value = fval;
                                
                                var localFileval = document.talentpoolForm.localFile.value;
                                var localFile = localFileval.replace("@#@" + file.name + "@#@", "");
                                localFile = localFile.replace("@#@" + file.name, "");
                                localFile = localFile.replace(file.name + "@#@", "");
                                localFile = localFile.replace(file.name, "");
                                localfname = localFile;
                                document.talentpoolForm.localFile.value = localfname;
                            };
                            reader.readAsDataURL(e);
                        });
                    this.on("removedfile", function (e)
                    {
                        var reader = new FileReader();
                        reader.onload = function (event) {
                            var base64String = event.target.result;
                            var val1 = document.talentpoolForm.fname.value;
                            var fval = val1.replace("@#@" + base64String + "@#@", "");
                            fval = fval.replace("@#@" + base64String, "");
                            fval = fval.replace(base64String + "@#@", "");
                            fval = fval.replace(base64String, "");
                            val = fval;
                            document.talentpoolForm.fname.value = fval;
                            
                            var localFileval = document.talentpoolForm.localFile.value;
                            var localFile = localFileval.replace("@#@" + file.name + "@#@", "");
                            localFile = localFile.replace("@#@" + file.name, "");
                            localFile = localFile.replace(file.name + "@#@", "");
                            localFile = localFile.replace(file.name, "");
                            localfname = localFile;
                            document.talentpoolForm.localFile.value = localfname;
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
}
catch(Exception e)
{
    e.printStackTrace();
}
%>
</html>
