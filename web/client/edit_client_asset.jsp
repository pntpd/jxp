<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.base.StatsInfo"%>
<%@page import="com.web.jxp.client.ClientInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="client" class="com.web.jxp.client.Client" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try
        {
            int mtp = 7, submtp = 2,ctp = 2;
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
            ClientInfo info = null;
            if(session.getAttribute("CLIENT_DETAIL") != null)
                info = (ClientInfo)session.getAttribute("CLIENT_DETAIL");
            String message = "", clsmessage = "red_font";
            if (request.getAttribute("MESSAGE") != null)
            {
                message = (String)request.getAttribute("MESSAGE");
                request.removeAttribute("MESSAGE");
            }        
            if(message != null && (message.toLowerCase()).indexOf("success") != -1)
                clsmessage = "updated-msg";

            ArrayList list = new ArrayList();
            if(session.getAttribute("COLIST") != null)
            {
                list = (ArrayList) session.getAttribute("COLIST");
            }
            String ids1 = "", ids2 = "";
            if(request.getAttribute("MINFO") != null)
            {
                ClientInfo minfo = (ClientInfo) request.getAttribute("MINFO");
                request.removeAttribute("MINFO");
                if(minfo != null)
                {
                    ids1 = minfo.getCids() != null ? minfo.getCids() : "";
                    ids2 = minfo.getMids() != null ? minfo.getMids() : "";
                }
            }
    %>
    <head>
        <meta charset="utf-8">
        <title><%= client.getMainPath("title") != null ? client.getMainPath("title") : "" %></title>
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
        <link href="/jxp/assets/css/minimal.css" rel="stylesheet">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css"/>
        <script src="../jsnew/common.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
        <script type="text/javascript" src="../jsnew/client.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="vertical-collpsed">
    <html:form action="/client/ClientAction.do" onsubmit="return false;" styleClass="form-horizontal" enctype="multipart/form-data">
        <html:hidden property="doView"/>
        <html:hidden property="doModify"/>
        <html:hidden property="doAdd"/>
        <html:hidden property="search"/>
        <html:hidden property="clientId"/>
        <html:hidden property="clientassetId"/>
        <html:hidden property="doSaveAsset"/>
        <html:hidden property="countryIndexId"/>
        <html:hidden property="assettypeIndexId"/>
        <html:hidden property="fname"/>
        <!-- Begin page -->
        <div id="layout-wrapper">
            <%@include file="../header.jsp" %>
            <%@include file="../sidemenu.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content tab_panel">
                    <div class="row head_title_area">
                        <div class="col-md-12 col-xl-12">
                            <div class="float-start"><a href="javascript: gobackasset();" class="back_arrow"><img  src="../assets/images/back-arrow.png"/>Client Master</a></div>
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
                                    <a class='nav-link' href="javascript: showDetail('-1');">
                                        <span class='d-none d-md-block'>Client</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
                                    </a>
                                </li>

                                <li id='list_menu2' class='list_menu2 nav-item <%=(ctp == 2) ? " active" : ""%>'>
                                    <a class='nav-link' href="javascript: viewasset('-1');">
                                        <span class='d-none d-md-block'>Asset</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
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
                                            <div class="tab-content">                                            

                                                <div class="row m_30">
                                                    <div class="main-heading m_30">
                                                        <div class="add-btn">
                                                            <h4>ADD/EDIT ASSET</h4>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <% if(!message.equals("")) {%><div class="sbold <%=clsmessage%>">
                                                            <%=message%>
                                                        </div><% } %>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">ID</label>
                                                            <html:text property="clientassetcode" styleId="clientassetcode" styleClass="form-control" maxlength="10" readonly="true" />
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Name<span class="required">*</span></label>
                                                            <html:text property="name" styleId="name" styleClass="form-control" maxlength="100"/>
                                                            <script type="text/javascript">
                                                                document.getElementById("name").setAttribute('placeholder', '');
                                                            </script>
                                                        </div>
                                                            
                                                            
                                                <%--<%
                                                    int nsize = 0;
                                                    nsize = list.size();
                                                %>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                    <div class="row flex-end align-items-end">
                                                        <div class="col-lg-10 col-md-12 col-sm-12 col-12 form_group">
                                                            <label class="form_label">Co-ordinator from OCS<span class="required">*</span></label>
                                                            <div class="row">
                                                                <div class="col-lg-10 col-md-10 col-sm-10 col-10 noti_div">
                                                                    <select name="coordinatorIds" id="coordinatormultiselect_dd" multiple="multiple" class="form-select" >
                                                                        <%
                                                                            if (nsize > 0) 
                                                                            {
                                                                                for (int i = 0; i < nsize; i++) 
                                                                                {
                                                                                    ClientInfo info2 = (ClientInfo) list.get(i);
                                                                        %>
                                                                            <option value="<%=info2.getDdlValue()%>" <% if (client.checkToStr(ids1, info2.getDdlValue() + "")) {%>selected<%}%>><%= (info2.getDdlLabel())%> </option>
                                                                        <%
                                                                                }
                                                                            }
                                                                        %>
                                                                    </Select>
                                                                </div>                                                                
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                    <div class="row flex-end align-items-end">
                                                        <div class="col-lg-10 col-md-12 col-sm-12 col-12 form_group">
                                                            <label class="form_label">Manager<span class="required">*</span></label>
                                                            <div class="row">
                                                                <div class="col-lg-10 col-md-10 col-sm-10 col-10 noti_div">
                                                                    <select name="managerIds" id="managermultiselect_dd" multiple="multiple" class="form-select">
                                                                        <%
                                                                            if (nsize > 0) 
                                                                            {
                                                                                for (int i = 0; i < nsize; i++) 
                                                                                {
                                                                                    ClientInfo info3 = (ClientInfo) list.get(i);
                                                                        %>
                                                                            <option value="<%=info3.getDdlValue()%>" <% if (client.checkToStr(ids2, info3.getDdlValue() + "")) {%>selected<%}%>><%= (info3.getDdlLabel())%> </option>
                                                                        <%
                                                                                }
                                                                            }
                                                                        %>
                                                                    </Select>
                                                                </div>                                                                
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>--%>
                                               

                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Location<span class="required">*</span></label>
                                                            <html:select property="countryId" styleClass="form-select" onchange="javascript: setports();">
                                                                <html:option value="-1">- Select -</html:option>
                                                                <html:optionsCollection filter="false" property="countries" label="ddlLabel" value="ddlValue">
                                                                </html:optionsCollection>
                                                            </html:select>
                                                        </div>

                                                    </div>
                                                    <div class="row">

                                                        <div class="col-lg-8 col-md-8 col-sm-8 col-12">
                                                            <div class="row">
                                                                <div class="col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                                    <label class="form_label">Asset Type<span class="required">*</span></label>
                                                                    <html:select property="assettypeId" styleClass="form-select">
                                                                        <html:option value="-1">- Select -</html:option>
                                                                        <html:optionsCollection filter="false" property="assettypes" label="ddlLabel" value="ddlValue">
                                                                        </html:optionsCollection>
                                                                    </html:select>
                                                                </div>
                                                                <div class="col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                                    <label class="form_label">Port</label>
                                                                    <html:select property="portId" styleId = "setportsddl" styleClass="form-select">
                                                                        <html:optionsCollection filter="false" property="ports" label="ddlLabel" value="ddlValue">
                                                                        </html:optionsCollection>
                                                                    </html:select>
                                                                </div>
                                                                <div class="col-lg-6 col-md-6 col-sm-6 col-12 form_group">
                                                                    <label class="form_label">Invoicing Currency<span class="required">*</span></label>
                                                                    <html:select property="currencyId"  styleClass="form-select">
                                                                        <html:optionsCollection filter="false" property="currencies" label="ddlLabel" value="ddlValue">
                                                                        </html:optionsCollection>
                                                                    </html:select>
                                                                </div>

                                                                <div class="col-lg-6 col-md-6 col-sm-6 col-12">
                                                                    <div class="row">
                                                                        <div class="col-lg-8 col-md-8 col-sm-6 col-12 form_group">
                                                                            <label class="form_label">Images (5MB .jpeg/.png/.pdf)</label>
                                                                            <a href="javascript:;" data-bs-toggle="modal" id = "upload_link_image" data-bs-target="#drag_drop_modal" class="attache_btn text-center"><i class="fas fa-paperclip"></i> Attachment </a>
                                                                        </div>
                                                                        <div class="col-lg-4 col-md-4 col-sm-6 col-12 form_group">
                                                                            <label class="form_label">Delivery Year</label>
                                                                            <div class="input-daterange input-group">
                                                                                <html:text property="deliveryYear" styleId="deliveryYear" styleClass="form-control add-style wesl_dt date-add " maxlength="4" onkeypress="return allowPositiveNumber1(event);"/>
                                                                                <script type="text/javascript">
                                                                                    document.getElementById("deliveryYear").setAttribute('placeholder', 'YYYY');
                                                                                </script>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>                
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Description</label>
                                                            <html:textarea property="description" rows="5" styleId="description" styleClass="form-control"></html:textarea>
                                                            <script type="text/javascript">
                                                                document.getElementById("description").setAttribute('placeholder', '');
                                                                document.getElementById("description").setAttribute('maxlength', '500');
                                                            </script>
                                                        </div>
                                                    </div>
                                                    <div class="row">	
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Flag</label>
                                                            <html:text property="assetFlag" styleId="assetFlag" styleClass="form-control" maxlength="100"/>
                                                            <script type="text/javascript">
                                                                document.getElementById("assetFlag").setAttribute('placeholder', '');
                                                            </script>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Classification</label>
                                                            <html:text property="classification" styleId="classification" styleClass="form-control" maxlength="100"/>
                                                            <script type="text/javascript">
                                                                document.getElementById("classification").setAttribute('placeholder', '');
                                                            </script>
                                                        </div> 
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">Berths</label>
                                                            <html:text property="berths" styleId="berths" styleClass="form-control" maxlength="100"/>
                                                            <script type="text/javascript">
                                                                document.getElementById("berths").setAttribute('placeholder', '');
                                                            </script>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-4 form_group">
                                                            <label class="form_label">PoB / Lifeboat Capacity</label>
                                                            <html:text property="lifeboat" styleId="lifeboat" styleClass="form-control" maxlength="100"/>
                                                            <script type="text/javascript">
                                                                document.getElementById("lifeboat").setAttribute('placeholder', '');
                                                            </script>
                                                        </div>
                                                        <div class="col-lg-8 col-md-8 col-sm-8 col-12 form_group">
                                                            <label class="form_label">Crew Rotation URL</label>
                                                            <html:text property="url" maxlength="500" styleClass="form-control" styleId="url" />        
                                                        </div>
                                                    </div>                                                        
                                                    <div class="row">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12 form_group">
                                                            <label class="form_label">Training URL</label>
                                                            <html:text property="url_training" maxlength="500" styleClass="form-control" styleId="url_training" />        
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                            <label class="form_label">Help Number (Competency)<span class="required">*</span></label>
                                                            <html:text property="helpno" maxlength="50" styleClass="form-control" styleId="helpno" />        
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                            <label class="form_label">Help Email (Competency)<span class="required">*</span></label>
                                                            <html:text property="helpemail" maxlength="100" styleClass="form-control" styleId="helpemail" />        
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                            <label class="form_label">Rate Type<span class="required">*</span></label>
                                                            <html:select property="ratetype" styleId="ratetype" styleClass="form-select">
                                                                <html:option value="">- Select -</html:option>
                                                                <html:option value="Monthly">Monthly</html:option>
                                                                <html:option value="Day wise">Day wise</html:option>
                                                                <html:option value="Hourly">Hourly</html:option>
                                                            </html:select>
                                                        </div>                                                            
                                                    </div>      
                                                    <div class="row">
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                            <label class="form_label">Follow Crew Rota:<span class="required" aria-required="true">*</span></label>
                                                            <div class="mt-radio-inline radio_list">
                                                                <label class="mt-radio">
                                                                    <html:radio property="crewrota" value="1"/> Yes
                                                                    <span></span>
                                                                </label>
                                                                <label class="mt-radio">
                                                                    <html:radio property="crewrota" value="2"/> No
                                                                    <span></span>
                                                                </label>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">
                                                            <label class="form_label">Start Rota:<span class="required" aria-required="true">*</span></label>
                                                            <div class="mt-radio-inline radio_list">
                                                                <label class="mt-radio">
                                                                    <html:radio property="startrota" value="1"/> Manual
                                                                    <span></span>
                                                                </label>
                                                                <label class="mt-radio">
                                                                    <html:radio property="startrota" value="2"/> Automatic
                                                                    <span></span>
                                                                </label>
                                                            </div>
                                                        </div>                                                 
                                                        <div class="col-lg-4 col-md-4 col-sm-4 col-12 form_group">                                                            
                                                            <div>
                                                                <label class="form_label">Travel Allowance(%)</label>
                                                                <html:text property="allowance" maxlength="6" styleClass="form-control" styleId="allowance" onkeypress="return allowPositiveNumber1(event);"/>
                                                            </div>
                                                        </div>                                                 
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                                            <a href="javascript: submitassetform();" class="save_btn"><img src="../assets/images/save.png"> Save</a>
                                                            <a href="javascript: viewasset('-1');" class="cl_btn"><i class="ion ion-md-close"></i> Close</a>
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
                if (trim(document.clientForm.fname.value) != "")
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
                            } else
                            {
                                val += ("@#@" + base64String);
                            }
                            document.clientForm.fname.value = val;
                        };
                        reader.readAsDataURL(file);

                    });
                    this.on("error", function (file, message) {
                        swal.fire(message);
                        this.removeFile(file);
                        var reader = new FileReader();
                        reader.onload = function (event) {
                            var base64String = event.target.result;
                            var val1 = document.clientForm.fname.value;
                            var fval = val1.replace("@#@" + base64String + "@#@", "");
                            fval = fval.replace("@#@" + base64String, "");
                            fval = fval.replace(base64String + "@#@", "");
                            fval = fval.replace(base64String, "");
                            val = fval;
                            document.clientForm.fname.value = fval;
                        };
                        reader.readAsDataURL(e);
                    });
                    this.on("removedfile", function (e)
                    {
                        var reader = new FileReader();
                        reader.onload = function (event) {
                            var base64String = event.target.result;
                            var val1 = document.clientForm.fname.value;
                            var fval = val1.replace("@#@" + base64String + "@#@", "");
                            fval = fval.replace("@#@" + base64String, "");
                            fval = fval.replace(base64String + "@#@", "");
                            fval = fval.replace(base64String, "");
                            val = fval;
                            document.clientForm.fname.value = fval;
                        };
                        reader.readAsDataURL(e);
                    }
                    );

                },
                dictDefaultMessage: '<span class="text-center"><span class="font-lg visible-xs-block visible-sm-block visible-lg-block"><span class="font-lg"><i class="fa fa-caret-right text-danger"></i> Drop files <span class="font-xs">to upload</span></span><span>&nbsp&nbsp<h4 class="display-inline"> (Or Click)</h4></span>',
                dictResponseError: 'Error uploading file!'
            });
        </script>
        <script type="text/javascript">
            $(document).ready(function () {
                $('#coordinatormultiselect_dd').multiselect({
                    includeSelectAllOption: true,
                    //maxHeight: 400,
                    dropUp: true,
                    nonSelectedText: '- Select -',
                    maxHeight: 200,
                    enableFiltering: false,
                    enableCaseInsensitiveFiltering: false,
                    buttonWidth: '100%'
                });
            });
        </script>
        <script type="text/javascript">
            $(document).ready(function () {
                $('#managermultiselect_dd').multiselect({
                    includeSelectAllOption: true,
                    //maxHeight: 400,
                    dropUp: true,
                    nonSelectedText: '- Select -',
                    maxHeight: 200,
                    enableFiltering: false,
                    enableCaseInsensitiveFiltering: false,
                    buttonWidth: '100%'
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
